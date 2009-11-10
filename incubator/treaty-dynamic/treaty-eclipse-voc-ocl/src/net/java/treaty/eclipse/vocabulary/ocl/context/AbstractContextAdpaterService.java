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
package net.java.treaty.eclipse.vocabulary.ocl.context;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.events.Namespace;

import net.java.treaty.dynamic.LifecycleEvent;
import net.java.treaty.dynamic.OperationInvocationContext;
import tudresden.ocl20.pivot.modelbus.ModelAccessException;
import tudresden.ocl20.pivot.modelbus.model.IModel;
import tudresden.ocl20.pivot.modelbus.modelinstance.IModelInstance;
import tudresden.ocl20.pivot.modelbus.modelinstance.types.IModelInstanceElement;
import tudresden.ocl20.pivot.pivotmodel.Operation;
import tudresden.ocl20.pivot.pivotmodel.Type;

/**
 * <p>
 * An abstract implementation that adapts a given
 * {@link OperationInvocationContext} to to {@link OclInterpretationContext} for
 * Java {@link IModelInstance}s.
 * </p>
 * 
 * @author Claas Wilke
 */
public abstract class AbstractContextAdpaterService implements
		IContextAdapterService {

	/**
	 * The {@link IModelInstance} to that this {@link IContextAdapterService}
	 * shall adapt given {@link OperationInvocationContext}s.
	 */
	protected IModelInstance myModelInstance;

	/**
	 * <p>
	 * Crates a new {@link AbstractContextAdpaterService}.
	 * </p>
	 * 
	 * @param modelInstance
	 *          The {@link IModelInstance} this {@link IContextAdapterService}
	 *          shall adapt to.
	 */
	public AbstractContextAdpaterService(IModelInstance modelInstance) {

		this.myModelInstance = modelInstance;
	}

	/*
	 * (non-Javadoc)
	 * @seenet.java.treaty.eclipse.vocabulary.ocl.context.IContextAdapterService#
	 * adaptContext(net.java.treaty.dynamic.OperationInvocationContext,
	 * net.java.treaty.dynamic.PointOfExecution)
	 */
	public OclInterpretationContext adaptContext(
			OperationInvocationContext runtimeContext, LifecycleEvent pointOfExecution) {

		OclInterpretationContext result;

		IModelInstanceElement modelSource;
		Operation modelOperation;
		IModelInstanceElement[] modelArguments;
		IModelInstanceElement modelResult;

		int index;

		/* Adapt the source. */
		modelSource = this.adaptObjectToModelObject(runtimeContext.getSource());

		/* Adapt the arguments. */
		modelArguments =
				new IModelInstanceElement[runtimeContext.getArguments().length];

		index = 0;

		for (Object anArgument : runtimeContext.getArguments()) {
			modelArguments[index] = this.adaptObjectToModelObject(anArgument);
			index++;
		}

		/* Find the operation. */
		modelOperation =
				this.findOperationInModel(modelSource, runtimeContext
						.getMethodSignature(), runtimeContext.getArgumentsTypes());

		/* Adapt the result. */
		modelResult = this.adaptObjectToModelObject(runtimeContext.getResult());

		result =
				new OclInterpretationContext(pointOfExecution, modelSource,
						modelOperation, modelArguments, modelResult);

		return result;
	}

	/**
	 * <p>
	 * Adapts a given {@link Object} to an {@link IModelInstanceElement} of the
	 * current {@link IModelInstance}.
	 * </p>
	 * 
	 * @param source
	 *          The {@link Object} that shall be adapted.
	 * @return The adapted {@link IModelInstanceElement}.
	 */
	protected abstract IModelInstanceElement adaptObjectToModelObject(
			Object anObject);

	/**
	 * <p>
	 * A helper method that converts a given qualified name (in the format of the
	 * implementation language of the {@link IModelInstance} (e.g., a Java
	 * canonical name)) into a qualified name as a {@link List} of
	 * {@link Namespace} names that can be used to identify the {@link Type} in
	 * the {@link IModel}.
	 * 
	 * @param aQualifiedName
	 *          The name that shall be converted.
	 * @return The converted qualified name.
	 */
	protected abstract List<String> convertQualifiedName(String aQualifiedName);

	/**
	 * <p>
	 * A helper method that searches in the {@link IModel} for an
	 * {@link Operation} of the given <code>sourceObject</code> with the given
	 * <code>operationName</code> and the given <code>arguments</code>.
	 * </p>
	 * 
	 * @param sourceObject
	 *          The {@link IModelInstanceElement} to that the searched
	 *          {@link Operation} belongs to.
	 * @param operationName
	 *          The simple name of the {@link Operation}.
	 * @param argumentTypeNames
	 *          The names of the {@link Type}s of the arguments of the Operation.
	 * @return The found {@link Operation} or <code>null</code>.
	 */
	protected Operation findOperationInModel(IModelInstanceElement sourceObject,
			String operationName, String[] argumentTypeNames) {

		Operation result;
		result = null;

		if (sourceObject != null && operationName != null) {

			List<Type> argumentTypes;

			argumentTypes = new ArrayList<Type>();

			/* Find the types of all arguments in the model. */
			for (String anArgumentTypeName : argumentTypeNames) {
				argumentTypes.add(this.findTypeInModel(anArgumentTypeName));
			}

			/* Try to find the operation in the model. */
			for (Type aType : sourceObject.getTypes()) {
				result = aType.lookupOperation(operationName, argumentTypes);

				if (result != null) {
					break;
				}
				// no else.
			}
		}

		return result;
	}

	/**
	 * <p>
	 * A helper method that searches for a {@link Type} in the {@link IModel}
	 * which is identified by its given qualified name (in the format of the
	 * {@link IModelInstance} implementation language).
	 * </p>
	 * 
	 * @param aQualifiedName
	 *          The qualified name of the {@link Type} (in the format of the
	 *          {@link IModelInstance} implementation language).
	 * @return The found {@link Type} or <code>null</code>.
	 */
	private Type findTypeInModel(String aQualifiedName) {

		Type result;
		List<String> qualifiedPath;

		qualifiedPath = this.convertQualifiedName(aQualifiedName);

		try {
			result = this.myModelInstance.getModel().findType(qualifiedPath);
		}

		catch (ModelAccessException e) {
			result = null;
		}

		return result;
	}
}