/*
Copyright (C) 2009 by Claas Wilke (info@claaswilke.de)

This file is part of the Treaty OCL Vocabulary which adapts Dresden OCL2 for
Eclipse to Treaty.

Dresden OCL2 for Eclipse is free software: you can redistribute it and/or modify 
it under the terms of the GNU Lesser General Public License as published by the 
Free Software Foundation, either version 3 of the License, or (at your option)
any later version.

Dresden OCL2 for Eclipse is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License 
for more details.

You should have received a copy of the GNU Lesser General Public License along 
with Dresden OCL2 for Eclipse. If not, see <http://www.gnu.org/licenses/>.
 */
package net.java.treaty.eclipse.vocabulary.ocl.internal;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import net.java.treaty.Connector;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.ResourceLoaderException;
import net.java.treaty.VerificationException;
import net.java.treaty.dynamic.IRuntimeContext;
import net.java.treaty.dynamic.LifecycleEvent;
import net.java.treaty.dynamic.OperationInvocationContext;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.vocabulary.ocl.OCLVocabulary;
import net.java.treaty.eclipse.vocabulary.ocl.context.IContextAdapterService;
import net.java.treaty.eclipse.vocabulary.ocl.context.JavaContextAdpaterService;
import net.java.treaty.eclipse.vocabulary.ocl.context.OclInterpretationContext;
import net.java.treaty.eclipse.vocabulary.ocl.internal.msg.OclVocabularyMessages;

import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Bundle;

import tudresden.ocl20.pivot.essentialocl.standardlibrary.OclAny;
import tudresden.ocl20.pivot.essentialocl.standardlibrary.OclBoolean;
import tudresden.ocl20.pivot.interpreter.IInterpretationResult;
import tudresden.ocl20.pivot.interpreter.IOclInterpreter;
import tudresden.ocl20.pivot.interpreter.OclInterpreterPlugin;
import tudresden.ocl20.pivot.metamodels.ecore.EcoreMetamodelPlugin;
import tudresden.ocl20.pivot.metamodels.java.JavaMetaModelPlugin;
import tudresden.ocl20.pivot.metamodels.uml2.UML2MetamodelPlugin;
import tudresden.ocl20.pivot.modelbus.ModelAccessException;
import tudresden.ocl20.pivot.modelbus.ModelBusPlugin;
import tudresden.ocl20.pivot.modelbus.metamodel.IMetamodel;
import tudresden.ocl20.pivot.modelbus.model.IModel;
import tudresden.ocl20.pivot.modelbus.model.IModelProvider;
import tudresden.ocl20.pivot.modelbus.modelinstance.IModelInstance;
import tudresden.ocl20.pivot.modelbus.modelinstance.IModelInstanceProvider;
import tudresden.ocl20.pivot.modelbus.modelinstance.exception.TypeNotFoundInModelException;
import tudresden.ocl20.pivot.modelbus.modelinstance.types.IModelInstanceElement;
import tudresden.ocl20.pivot.modelinstancetype.java.JavaModelInstanceTypePlugin;
import tudresden.ocl20.pivot.ocl2parser.parser.OCL2Parser;
import tudresden.ocl20.pivot.pivotmodel.Constraint;
import tudresden.ocl20.pivot.pivotmodel.ConstraintKind;
import tudresden.ocl20.pivot.pivotmodel.Operation;
import tudresden.ocl20.pivot.pivotmodel.Parameter;

/**
 * <p>
 * This class manages all resources ({@link IMetamodel}s, {@link IModel}s,
 * {@link IModelInstance}s, and {@link Constraint}s) which are required by the
 * {@link OCLVocabulary}.
 * </p>
 * 
 * @author Claas Wilke
 * 
 */
public class OCLToolkitManager {

	/**
	 * The {@link Constraint}s of the constraint files which have already been
	 * loaded. <strong>This is a {@link WeakHashMap}! If a {@link URL} is not
	 * available anymore, the {@link Constraint} can be collected by the garbage
	 * collector.</strong>
	 */
	private Map<URL, List<Constraint>> cachedConstraintFiles =
			new WeakHashMap<URL, List<Constraint>>();

	/**
	 * The {@link IModelInstance}s which have already been loaded identified by a
	 * unique {@link String} (a combinatation of their model's name and their
	 * {@link URL} or canonical name).
	 */
	private Map<String, IModelInstance> cachedModelInstances =
			new HashMap<String, IModelInstance>();

	/**
	 * The {@link Class}es belonging to the {@link IModelInstance}s which have
	 * already been loaded identified by a unique {@link String} (a combinatation
	 * of their model's name and their {@link URL} or canonical name).
	 * <strong>This is a {@link WeakHashMap}! If an {@link IModelInstance} is not
	 * available anymore, the {@link Class} can be collected by the garbage
	 * collector.</strong>
	 */
	private Map<IModelInstance, Class<?>> cachedModelInstanceClasses =
			new WeakHashMap<IModelInstance, Class<?>>();

	/**
	 * The {@link IModel}s which have already been loaded identified by a unique
	 * {@link String} (their {@link URL} or canonical name).
	 */
	private Map<String, IModel> cachedModels = new HashMap<String, IModel>();

	/**
	 * Each {@link IModelInstance} has its own {@link IOclInterpreter} which is
	 * stored in this {@link Map}. <strong>This is a {@link WeakHashMap}! If an
	 * {@link IModelInstance} is not available anymore, the
	 * {@link IOclInterpreter} can be collected by the garbage collector.</strong>
	 */
	private Map<IModelInstance, IOclInterpreter> myInterpreters =
			new WeakHashMap<IModelInstance, IOclInterpreter>();

	/**
	 * <p>
	 * Loads an {@link IModel} for a given {@link URL}.
	 * </p>
	 * 
	 * @param resourceURL
	 *          The {@link URL} which represents the {@link IModel} that shall be
	 *          loaded.
	 * @param typeName
	 *          The name of the vocabulary type which is used as meta model.
	 * @return The loaded {@link IModel}
	 * @throws ResourceLoaderException
	 *           Thrown, if the resource can not be found or is not an
	 *           {@link IModel}.
	 */
	public IModel loadModel(URL resourceURL, String typeName)
			throws ResourceLoaderException {

		IModel result;

		/* Check if the model has already been loaded. */
		if (cachedModels.containsKey(resourceURL.toString())) {
			result = cachedModels.get(resourceURL.toString());
		}

		/* Else Try to load the model. */
		else {
			IModelProvider modelProvider;

			/* Get the model provider depending on the model type. */
			if (typeName.equals(OCLVocabulary.TYPE_MODEL_EMF_ECORE)) {

				IMetamodel emfMetaModel;

				emfMetaModel =
						ModelBusPlugin.getMetamodelRegistry().getMetamodel(
								EcoreMetamodelPlugin.ID);
				modelProvider = emfMetaModel.getModelProvider();
			}

			else if (typeName.equals(OCLVocabulary.TYPE_MODEL_UML2)) {

				IMetamodel uml2MetaModel;

				uml2MetaModel =
						ModelBusPlugin.getMetamodelRegistry().getMetamodel(
								UML2MetamodelPlugin.ID);
				modelProvider = uml2MetaModel.getModelProvider();
			}

			else {
				String msg;

				msg =
						NLS.bind(OclVocabularyMessages.ERROR_LOAD_MODEL_WRONG_TYPE,
								typeName, resourceURL);

				throw new ResourceLoaderException(msg);
			}

			/* Try to load the model. */
			try {
				result = modelProvider.getModel(resourceURL);

				ModelBusPlugin.getModelRegistry().addModel(result);
				cachedModels.put(resourceURL.toString(), result);
			}

			catch (ModelAccessException e) {
				String msg;

				msg =
						NLS.bind(OclVocabularyMessages.ERROR_LOAD_MODEL_FAILED,
								resourceURL, e.getMessage());

				throw new ResourceLoaderException(msg);
			}
		}

		/* Set the loaded model as active. */
		ModelBusPlugin.getModelRegistry().setActiveModel(result);

		return result;
	}

	/**
	 * <p>
	 * Loads an Java {@link IModel} for a given canonical class name.
	 * </p>
	 * 
	 * @param className
	 *          The name of the {@link Class} which represents the
	 *          {@link IModelInstance} that shall be loaded.
	 * @param connector
	 *          The used {@link Connector}.
	 * @return The loaded {@link IModel}
	 * @throws ResourceLoaderException
	 *           Thrown, if the resource can not be found or is not an
	 *           {@link IModel}.
	 */
	public IModel loadJavaModel(String className, Connector connector)
			throws ResourceLoaderException {

		IModel result;

		/* Check if the model has already been loaded. */
		if (cachedModels.containsKey(className)) {
			result = cachedModels.get(className);
		}

		/* Else try to load the model. */
		else {
			Bundle connectorBundle;
			Class<?> modelClass;

			/* Get the bundle of the given connector. */
			connectorBundle = ((EclipsePlugin) connector.getOwner()).getBundle();

			/* Try to load the given class and import the class as IModel. */
			try {
				boolean isModelAlreadyAdded;

				modelClass = connectorBundle.loadClass(className);
				result = JavaMetaModelPlugin.createJavaModel(modelClass);

				/* Eventually add the loaded model to the model registry. */
				isModelAlreadyAdded = false;

				for (IModel aModel : ModelBusPlugin.getModelRegistry().getModels()) {
					if (aModel.equals(result)) {
						isModelAlreadyAdded = true;
						break;
					}
					// no else.
				}

				if (!isModelAlreadyAdded) {
					ModelBusPlugin.getModelRegistry().addModel(result);
				}
				// no else.
			}

			catch (ClassNotFoundException e1) {
				String msg;

				msg =
						NLS.bind(OclVocabularyMessages.ERROR_LOAD_JAVA_MODEL_NO_CLASS,
								className);

				throw new ResourceLoaderException(msg);
			}

			catch (ModelAccessException e) {
				String msg;

				msg =
						NLS.bind(OclVocabularyMessages.ERROR_LOAD_JAVA_MODEL_FAILED,
								className, e.getMessage());

				throw new ResourceLoaderException(msg);
			}

			/* Cache the loaded model to improve performance. */
			cachedModels.put(className, result);
		}
		// end else.

		/* Set the loaded model as active. */
		ModelBusPlugin.getModelRegistry().setActiveModel(result);

		return result;
	}

	/**
	 * <p>
	 * Loads a Java {@link IModelInstance} for a given {@link URL}.
	 * </p>
	 * 
	 * @param className
	 *          The name of the {@link Class} which represents the
	 *          {@link IModelInstance} that shall be loaded.
	 * @param connector
	 *          The used {@link Connector}.
	 * @return The loaded {@link IModelInstance}
	 * @throws ResourceLoaderException
	 *           Thrown, if the resource can not be found or is not an
	 *           {@link IModelInstance} or no active {@link IModel} has been set.
	 */
	public IModelInstance loadJavaModelInstance(String className,
			Connector connector) throws ResourceLoaderException {

		IModelInstance result;

		IModel activeModel;

		/* Try to get the active IModel. */
		activeModel = ModelBusPlugin.getModelRegistry().getActiveModel();

		if (activeModel == null) {
			throw new ResourceLoaderException(
					OclVocabularyMessages.ERROR_LOAD_MODEL_INSTANCE_NO_ACTIVE_MODEL);
		}
		// no else.

		/* Check if the model instance has already been loaded. */
		else if (cachedModelInstances.containsKey(activeModel.getDisplayName()
				+ ":" + className)) {
			result =
					cachedModelInstances.get(activeModel.getDisplayName() + ":"
							+ className);
		}

		/* Else try to load the instance. */
		else {

			Bundle connectorBundle;
			Class<?> modelInstanceClass;

			/* Get the bundle of the given connector. */
			connectorBundle = ((EclipsePlugin) connector.getOwner()).getBundle();

			/* Try to create a model instance. */
			try {
				IModelInstanceProvider modelInstanceProvider;

				modelInstanceClass = connectorBundle.loadClass(className);

				/* Get the IModelInstanceProvider of the Model Instance Type. */
				modelInstanceProvider =
						ModelBusPlugin.getModelInstanceTypeRegistry().getModelInstanceType(
								JavaModelInstanceTypePlugin.PLUGIN_ID)
								.getModelInstanceProvider();

				/*
				 * Create an empty model instance. The Model Objects are registered
				 * later on.
				 */
				result = modelInstanceProvider.createEmptyModelInstance(activeModel);

				/* Add the model instance to the registry. */
				ModelBusPlugin.getModelInstanceRegistry().addModelInstance(activeModel,
						result);

				/* Store the loaded model instance to improve performance. */
				this.cachedModelInstances.put(activeModel.getDisplayName() + ":"
						+ className, result);

				/* Store the class of the model instance as well. */
				this.cachedModelInstanceClasses.put(result, modelInstanceClass);
			}

			catch (ClassNotFoundException e) {
				String msg;

				msg =
						NLS.bind(
								OclVocabularyMessages.ERROR_LOAD_JAVA_MODEL_INVALID_LOCATION,
								className, e.getMessage());

				throw new ResourceLoaderException(msg);
			}
		}

		/* Set the loaded model instance as active. */
		ModelBusPlugin.getModelInstanceRegistry().setActiveModelInstance(
				activeModel, result);

		return result;
	}

	/**
	 * <p>
	 * Loads a constraint file for a given {@link URL}.
	 * </p>
	 * 
	 * @param resourceURL
	 *          The {@link URL} which represents the constraint file that shall be
	 *          loaded.
	 * @return The {@link IModel} of the loaded constraint file.
	 * @throws ResourceLoaderException
	 *           Thrown, if the resource can not be found or is not an constraint
	 *           file or no active {@link IModel} has been set.
	 */
	public List<Constraint> loadConstraintFile(URL resourceURL)
			throws ResourceLoaderException {

		List<Constraint> result;

		IModel activeModel;

		/* Get the active model. */
		activeModel = ModelBusPlugin.getModelRegistry().getActiveModel();

		if (activeModel == null) {
			throw new ResourceLoaderException(
					OclVocabularyMessages.ERROR_LOAD_CONSTRAINTS_NO_ACTIVE_MODEL);
		}
		// no else.

		/* Check if the constraints have already been loaded. */
		else if (cachedConstraintFiles.containsKey(resourceURL)) {
			result = cachedConstraintFiles.get(resourceURL);
		}

		/* Else try to load the constraint file. */
		else {

			/* Try to parse the constraints. */
			try {
				List<Constraint> constraintsBeforeParsing;
				List<Constraint> constraintsAfterParsing;

				OCL2Parser myOclParser;

				Reader resourceReader;
				URLConnection resourceConection;

				/* Create a reader to adapt resource to the parser. */
				resourceConection = resourceURL.openConnection();
				resourceReader =
						new InputStreamReader(resourceConection.getInputStream());

				/* Get all constraints which are already in the model. */
				constraintsBeforeParsing =
						activeModel.getRootNamespace().getOwnedAndNestedRules();

				/* Parse the resource. */
				myOclParser = new OCL2Parser(activeModel, resourceReader);
				myOclParser.parse();

				/* Get all constraints which are in the model. */
				constraintsAfterParsing =
						activeModel.getRootNamespace().getOwnedAndNestedRules();

				/* Remove the old constraints from the result. */
				result = constraintsAfterParsing;
				result.removeAll(constraintsBeforeParsing);

				/* Add the new loaded constraints to the cache. */
				cachedConstraintFiles.put(resourceURL, result);
			}

			catch (Exception e) {
				String msg;

				msg =
						NLS.bind(OclVocabularyMessages.ERROR_LOAD_CONSTRAINTS_FAILED,
								resourceURL, e.getMessage());

				throw new ResourceLoaderException(msg);
			}
		}
		// end else.

		return result;
	}

	/**
	 * <p>
	 * Verifies the {@link RelationshipCondition} <code>instanceOf</code> for two
	 * given resources.
	 * </p>
	 * 
	 * @param resource1
	 *          The instance {@link Resource}.
	 * @param resource2
	 *          The model resource {@link Resource}.
	 * @throws VerificationException
	 *           Thrown, if the <code>instanceOf</code>
	 *           {@link RelationshipCondition} is not fulfilled.
	 */
	public void checkIsInstanceOf(Resource resource1, Resource resource2)
			throws VerificationException {

		/* Check if resource2 is an IModel. */
		if (resource2.getValue() instanceof IModel) {

			IModel aModel;
			aModel = (IModel) resource2.getValue();

			/* Check if resource1 is an IModelInstance. */
			if (resource1.getValue() instanceof IModelInstance) {

				IModelInstance aModelInstance;
				aModelInstance = (IModelInstance) resource1.getValue();

				if (!aModelInstance.isInstanceOf(aModel)) {
					String msg;

					msg =
							NLS.bind(OclVocabularyMessages.INFO_CHECK_IS_INSTANCE_OF_FAILED,
									resource1, resource2);

					throw new VerificationException(msg);
				}
				// no else.
			}

			/* Else resource1 is not an IModelInstance. */
			else {
				String msg;

				msg =
						NLS
								.bind(
										OclVocabularyMessages.ERROR_CHECK_IS_INSTANCE_OF_NO_MODEL_INSTANCE,
										resource1, resource1.getValue());

				throw new VerificationException(msg);
			}
		}

		/* Else resource2 is not an IModel. */
		else {
			String msg;

			msg =
					NLS.bind(OclVocabularyMessages.ERROR_CHECK_IS_INSTANCE_OF_NO_MODEL,
							resource2, resource2.getValue());

			throw new VerificationException(msg);
		}
	}

	/**
	 * 
	 * <p>
	 * Verifies the {@link RelationshipCondition} <code>contractFulfilled</code>
	 * for two given resources.
	 * </p>
	 * 
	 * @param resource1
	 *          The instance {@link Resource}.
	 * @param resource2
	 *          The constraint resource {@link Resource}.
	 * @param pointOfExecution
	 *          The {@link LifecycleEvent} during run-time, at that this contract
	 *          shall be verified.
	 * @param runtimeContext
	 *          The {@link IRuntimeContext} of the contract verification.
	 * @throws VerificationException
	 *           Thrown, if the <code>contractFulfilled</code>
	 *           {@link RelationshipCondition} is not fulfilled.
	 */
	@SuppressWarnings("unchecked")
	public void checkIsContractFulfilled(Resource resource1, Resource resource2,
			LifecycleEvent pointOfExecution, IRuntimeContext runtimeContext)
			throws VerificationException {

		/* Check if resource1 is an IModelInstance. */
		if (resource1.getValue() instanceof IModelInstance) {

			/* Check if resource2 is a List. */
			if (resource2.getValue() instanceof List) {

				IModelInstance aModelInstance;
				List<Constraint> constraints;
				OclInterpretationContext interpretationContext;

				aModelInstance = (IModelInstance) resource1.getValue();
				constraints = (List<Constraint>) resource2.getValue();

				interpretationContext =
						this.adaptContext(resource1, pointOfExecution, runtimeContext);

				/* Try to interpret the constraints for the IModelInstance. */
				this.checkConstraints(aModelInstance, constraints,
						interpretationContext);
			}

			/* Else resource2 is not a List of Constraints. */
			else {
				String msg;

				msg =
						NLS
								.bind(
										OclVocabularyMessages.ERROR_CHECK_IS_CONTRACT_FULFILLED_NO_CONSTRAINT_FILE,
										resource2, resource2.getValue());

				throw new VerificationException(msg);
			}
		}

		/* Else resource1 is not an IModelInstance. */
		else {
			String msg;

			msg =
					NLS
							.bind(
									OclVocabularyMessages.ERROR_CHECK_IS_CONTRACT_FULFILLED_NO_MODEL_INSTANCE,
									resource1, resource1.getValue());

			throw new VerificationException(msg);
		}
	}

	/**
	 * <p>
	 * A helper method that adapts a given {@link IRuntimeContext} into an
	 * {@link OclInterpretationContext}.
	 * </p>
	 * 
	 * @param resource
	 *          The {@link Resource} representing the {@link IModelInstance}
	 *          required for the adaptation.
	 * @param pointOfExecution
	 *          The {@link LifecycleEvent} of the adapted context.
	 * @param runtimeContext
	 *          The {@link IRuntimeContext} that shall be adapted.
	 * @return The adapted {@link OclInterpretationContext}.
	 * @throws VerificationException
	 *           Thrown, if a wrong {@link Resource} type or a wrong type of
	 *           {@link IRuntimeContext} is given for adaptation.
	 */
	private OclInterpretationContext adaptContext(Resource resource,
			LifecycleEvent pointOfExecution, IRuntimeContext runtimeContext)
			throws VerificationException {

		OclInterpretationContext result;

		/* Check if the runtime context is null. */
		if (runtimeContext == null) {

			result = this.createContext(resource, pointOfExecution);
		}

		/* Check if the context can be adapted. */
		else if (runtimeContext instanceof OperationInvocationContext) {

			/* Adapt the context. */
			if (resource.getType().toString().equals(
					OCLVocabulary.TYPE_MODELINSTANCE_JAVA)) {

				IModelInstance modelInstance;
				IContextAdapterService adaptetService;
				OperationInvocationContext invocationContext;

				modelInstance = (IModelInstance) resource.getValue();

				adaptetService = new JavaContextAdpaterService(modelInstance);
				invocationContext = (OperationInvocationContext) runtimeContext;

				result =
						adaptetService.adaptContext(invocationContext, pointOfExecution);
			}

			/* Else throw an exception (wrong type of model instance). */
			else {
				String msg;

				msg = OclVocabularyMessages.ERROR_LOAD_MODEL_INSTANCE_WRONG_TYPE;
				msg += NLS.bind(msg, resource.getType());

				throw new VerificationException(msg);
			}
		}

		/* Else throw an exception (Wrong type of runtime context). */
		else {
			String msg;

			msg = OclVocabularyMessages.ERROR_ADAPT_CONTEXT_WRONG_TYPE_OF_CONTEXT;
			msg += NLS.bind(msg, runtimeContext.getClass());

			throw new VerificationException(msg);
		}

		return result;
	}

	/**
	 * <p>
	 * A helper method that creates a new {@link OclInterpretationContext} if no
	 * {@link IRuntimeContext} was given for adaptation.
	 * </p>
	 * 
	 * @param resource
	 *          The {@link Resource} representing the {@link IModelInstance} of
	 *          the {@link OclInterpretationContext} that shall be created.
	 * @param pointOfExecution
	 *          The {@link LifecycleEvent} for that the
	 *          {@link OclInterpretationContext} shall be created.
	 * @return A new created {@link OclInterpretationContext}.
	 * @throws VerificationException
	 *           Thrown, if the wrong type of {@link Resource} or the wrong type
	 *           of {@link LifecycleEvent} is given.
	 */
	private OclInterpretationContext createContext(Resource resource,
			LifecycleEvent pointOfExecution) throws VerificationException {

		OclInterpretationContext result;

		/* Check if the point of execution is snapshot verification. */
		if (pointOfExecution.equals(LifecycleEvent.MANUAL_VERIFICATION)) {

			if (resource.getType().toString().equals(
					OCLVocabulary.TYPE_MODELINSTANCE_JAVA)) {

				IModelInstanceElement modelInstanceElement;
				IModelInstance modelInstance;

				modelInstance = (IModelInstance) resource.getValue();

				/* Get the class of the modelInstance. */
				if (this.cachedModelInstanceClasses.containsKey(modelInstance)) {

					Class<?> modelInstanceClass;
					Object javaModelObject;

					modelInstanceClass =
							this.cachedModelInstanceClasses.get(modelInstance);

					/* Try to create a new IModelObject using reflection. */
					try {
						javaModelObject = modelInstanceClass.newInstance();

						modelInstanceElement =
								modelInstance.addModelInstanceElement(javaModelObject);
						result =
								new OclInterpretationContext(pointOfExecution,
										modelInstanceElement, null, null, null);
					}

					catch (InstantiationException e) {
						String msg;

						msg =
								OclVocabularyMessages.ERROR_ADAPT_CONTEXT_REFLECTION_INITIALIZATION_FAILED;
						msg = NLS.bind(msg, modelInstanceClass);

						throw new VerificationException(msg);
					}

					catch (IllegalAccessException e) {
						String msg;

						msg =
								OclVocabularyMessages.ERROR_ADAPT_CONTEXT_REFLECTION_INITIALIZATION_FAILED;
						msg = NLS.bind(msg, modelInstanceClass);

						throw new VerificationException(msg);
					}

					catch (TypeNotFoundInModelException e) {
						String msg;

						msg =
								OclVocabularyMessages.ERROR_ADAPT_CONTEXT_DURING_MODEL_OBJECT_INIALIZATION;
						msg = NLS.bind(msg, e.getMessage());

						throw new VerificationException(msg);
					}
				}

				/* Else throw an exception (no class for model instance found). */
				else {
					String msg;

					msg =
							OclVocabularyMessages.ERROR_ADAPT_CONTEXT_NO_CLASS_FOR_MODEL_INSTACE_FOUND;
					msg = NLS.bind(msg, modelInstance);

					throw new VerificationException(msg);
				}
			}

			/* Else throw an exception (unknown model instance type). */
			else {
				String msg;

				msg = OclVocabularyMessages.ERROR_LOAD_MODEL_INSTANCE_WRONG_TYPE;
				msg += NLS.bind(msg, resource.getType());

				throw new VerificationException(msg);
			}
		}

		/*
		 * Else throw an exception (no context for wrong type of point of
		 * execution).
		 */
		else {
			String msg;

			msg =
					OclVocabularyMessages.ERROR_ADAPT_CONTEXT_NO_CONTEXT_FOR_WRONG_POINT_OF_EXECUTION;
			msg = NLS.bind(msg, pointOfExecution);

			throw new VerificationException(msg);
		}
		return result;
	}

	/**
	 * <p>
	 * Interprets all {@link Constraint}s for a given {@link IModelInstance}.
	 * </p>
	 * 
	 * @param aModelInstance
	 *          The {@link aModelInstance} which's objects shall be interpreted.
	 * @param constraints
	 *          A {@link List} containing {@link Constraint}s which shall be
	 *          interpreted
	 * @param interpretationContext
	 *          The {@link OclInterpretationContext} of the contract verification.
	 * @throws VerificationException
	 *           Thrown, if an Operation's invocation fails during interpretation.
	 */
	private void checkConstraints(IModelInstance aModelInstance,
			List<Constraint> constraints,
			OclInterpretationContext interpretationContext)
			throws VerificationException {

		IOclInterpreter anInterpreter;

		/* Get the interpreter for this IModelInstance. */
		if (this.myInterpreters.containsKey(aModelInstance)) {
			anInterpreter = this.myInterpreters.get(aModelInstance);
		}

		/* Else create a new one. */
		else {
			anInterpreter = OclInterpreterPlugin.createInterpreter(aModelInstance);
			this.myInterpreters.put(aModelInstance, anInterpreter);
		}

		/*
		 * Prepare definitions, initial expressions, derived values, and body
		 * expressions.
		 */
		anInterpreter.prepareConstraints(constraints);

		/*
		 * If the pointOfExection is an INTERFACE_INVOCATION, interpret invariants,
		 * preconditions and prepare post conditions.
		 */
		if (interpretationContext.getPointOfExecution().equals(
				LifecycleEvent.INTERFACE_INVOCATION)) {

			/*
			 * Push a new local environment to ensure that the evaluation of pre- and
			 * postconditions is done in the same environment.
			 */
			anInterpreter.pushLocalEnvironment();

			this.checkConstraintsBeforeMethodInvocation(constraints,
					interpretationContext, anInterpreter);

			/*
			 * Prepare postconditions before a methods invocation (eventually @pre
			 * values or instances must be stored).
			 */
			anInterpreter.preparePostConditions(interpretationContext
					.getModelInstanceElement(), interpretationContext.getOperation(),
					interpretationContext.getArguments(), constraints);
		}

		/*
		 * Else if the pointOfExection is an INTERFACE_INVOCATION_RESULT, interpret
		 * postconditions and invariants.
		 */
		else if (interpretationContext.getPointOfExecution().equals(
				LifecycleEvent.INTERFACE_INVOCATION_RETURN)) {
			this.checkConstraintsAfterMethodInvocation(constraints,
					interpretationContext, anInterpreter);

			/*
			 * Pop a new local environment to ensure that the evaluation following
			 * constraints is done in a more global environment.
			 */
			anInterpreter.popLocalEnvironment();
		}

		/* Else interpret only invariants. */
		else {
			this.checkInterpretationResults(anInterpreter.interpretConstraintsOfKind(
					constraints, interpretationContext.getModelInstanceElement(),
					ConstraintKind.INVARIANT));
		}
	}

	/**
	 * <p>
	 * A helper method that checks all preconditions and invariants before a
	 * method's invocation specified by the given {@link OclInterpretationContext}
	 * .
	 * </p>
	 * 
	 * @param constraints
	 *          The preconditions and invariants that shall be verified.
	 * @param interpretationContext
	 *          The {@link OclInterpretationContext} specifying the
	 *          {@link Operation}, its {@link Parameter} values.
	 * @param anInterpreter
	 *          The {@link IOclInterpreter} used for verification.
	 * @throws VerificationException
	 *           Thrown, if at least one {@link Constraint} has been violated.
	 */
	private void checkConstraintsBeforeMethodInvocation(
			List<Constraint> constraints,
			OclInterpretationContext interpretationContext,
			IOclInterpreter anInterpreter) throws VerificationException {

		/* Check all invariants. */
		this.checkInterpretationResults(anInterpreter.interpretConstraintsOfKind(
				constraints, interpretationContext.getModelInstanceElement(),
				ConstraintKind.INVARIANT));

		/* Check all preconditions. */
		this
				.checkInterpretationResults(anInterpreter.interpretPreConditions(
						interpretationContext.getModelInstanceElement(),
						interpretationContext.getOperation(), interpretationContext
								.getArguments(), constraints));
	}

	/**
	 * <p>
	 * A helper method that checks all postconditions and invariants after a
	 * method's invocation specified by the given {@link OclInterpretationContext}
	 * .
	 * </p>
	 * 
	 * @param constraints
	 *          The postconditions and invariants that shall be verified.
	 * @param interpretationContext
	 *          The {@link OclInterpretationContext} specifying the
	 *          {@link Operation}, its {@link Parameter} values and eventually its
	 *          result value.
	 * @param anInterpreter
	 *          The {@link IOclInterpreter} used for verification.
	 * @throws VerificationException
	 *           Thrown, if at least one {@link Constraint} has been violated.
	 */
	private void checkConstraintsAfterMethodInvocation(
			List<Constraint> constraints,
			OclInterpretationContext interpretationContext,
			IOclInterpreter anInterpreter) throws VerificationException {

		/* Check all postconditions. */
		this.checkInterpretationResults(anInterpreter.interpretPostConditions(
				interpretationContext.getModelInstanceElement(), interpretationContext
						.getOperation(), interpretationContext.getArguments(),
				interpretationContext.getResult(), constraints));

		/* Check all invariants. */
		this.checkInterpretationResults(anInterpreter.interpretConstraintsOfKind(
				constraints, interpretationContext.getModelInstanceElement(),
				ConstraintKind.INVARIANT));
	}

	/**
	 * <p>
	 * Evaluates a List of {@link IInterpretationResult}s and throws a
	 * {@link VerificationException} if a {@link Constraint} has been interpreted
	 * as violated.
	 * </p>
	 * 
	 * @param interpretationResults
	 *          The {@link List} of {@link IInterpretationResult}s.
	 * @throws VerificationException
	 *           Thrown, if a {@link Constraint} has been violated.
	 */
	private void checkInterpretationResults(
			List<IInterpretationResult> interpretationResults)
			throws VerificationException {

		for (IInterpretationResult aResult : interpretationResults) {

			Constraint aConstraint;

			aConstraint = aResult.getConstraint();

			/*
			 * Only check the results of invariants, pre- and postconditions. The
			 * other constraints are evaluated inside the interpretation process.
			 */
			if (aConstraint.getKind().equals(ConstraintKind.INVARIANT)
					|| aConstraint.getKind().equals(ConstraintKind.PRECONDITION)
					|| aConstraint.getKind().equals(ConstraintKind.POSTCONDITION)) {

				OclAny anOclResult;
				IModelInstanceElement aModelInstanceElement;

				boolean isResultTrue;

				anOclResult = aResult.getResult();
				aModelInstanceElement = aResult.getModelObject();

				/* Get the boolean result. */
				isResultTrue = false;

				if (anOclResult instanceof OclBoolean) {
					OclBoolean aBooleanResult;

					aBooleanResult = (OclBoolean) anOclResult;

					/* Handle undefined and invalid results as well. */
					if (!aBooleanResult.oclIsInvalid().isTrue()
							&& !aBooleanResult.oclIsUndefined().isTrue()) {
						isResultTrue = aBooleanResult.isTrue();
					}

					else {
						isResultTrue = false;
					}
				}
				// no else.

				/* Check if the result is true, else fail. */
				if (!isResultTrue) {

					String msg;
					Object args[];

					args = new Object[3];

					args[0] = aConstraint.getName();
					args[1] = anOclResult;
					args[2] = aModelInstanceElement;

					msg =
							NLS
									.bind(
											OclVocabularyMessages.INFO_CHECK_IS_CONTRACT_FULFILLED_FAILED,
											args);

					throw new VerificationException(msg);
				}
				// no else.
			}
			// no else.
		}
		// end for.
	}
}