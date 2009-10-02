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
package net.java.treaty.eclipse.vocabulary.ocl;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import net.java.treaty.Connector;
import net.java.treaty.ContractVocabulary;
import net.java.treaty.ExistsCondition;
import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.ResourceLoaderException;
import net.java.treaty.VerificationException;
import net.java.treaty.dynamic.AbstractDynamicContractVocabulary;
import net.java.treaty.dynamic.IRuntimeContext;
import net.java.treaty.dynamic.LifecycleEvent;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.vocabulary.ocl.internal.OCLToolkitManager;
import net.java.treaty.eclipse.vocabulary.ocl.internal.msg.OclVocabularyMessages;

import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Bundle;

/**
 * <p>
 * A {@link ContractVocabulary} which supports the use of OCL constraints in
 * Treaty Contracts.
 * </p>
 * 
 * @author Claas Wilke
 */
public class OCLVocabulary extends AbstractDynamicContractVocabulary {

	/** The name space of the {@link OCLVocabulary}. */
	public static final String NAMESPACE = "http://www.treaty.org/ocl#";

	/** The relationships fulfillsContract of the {@link OCLVocabulary}. */
	public static final String PREDICATE_FULFILLS_CONTRACT =
			NAMESPACE + "fulfillsContract";

	/** The relationships instanceof of the {@link OCLVocabulary}. */
	public static final String PREDICATE_INSTANCE_OF = NAMESPACE + "instanceOf";

	/** The EMF Ecore model type of the {@link OCLVocabulary}. */
	public static final String TYPE_MODEL_EMF_ECORE = NAMESPACE + "EMFEcoreModel";

	/** The EMF Ecore model type of the {@link OCLVocabulary}. */
	public static final String TYPE_MODEL_JAVA = NAMESPACE + "JavaModel";

	/** The UML2 model type of the {@link OCLVocabulary}. */
	public static final String TYPE_MODEL_UML2 = NAMESPACE + "UML2Model";

	/** The Java model instance type of the {@link OCLVocabulary}. */
	public static final String TYPE_MODELINSTANCE_JAVA =
			NAMESPACE + "JavaInstance";

	/** The OCL type of the {@link OCLVocabulary}. */
	public static final String TYPE_OCL_FILE = NAMESPACE + "OCLFile";

	/** The {@link OCLToolkitManager} of this {@link OCLVocabulary}. */
	private OCLToolkitManager myOclToolkitManager;

	/** A {@link Collection} of all predicates of the {@link OCLVocabulary}. */
	private Collection<URI> myPredicates = null;

	/** A {@link Collection} of all types of the {@link OCLVocabulary}. */
	private Collection<URI> myTypes = null;

	/**
	 * <p>
	 * Creates a new {@link OCLVocabulary}.
	 * </p>
	 */
	public OCLVocabulary() {

		super();

		this.myOclToolkitManager = new OCLToolkitManager();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.dynamic.IDynamicContractVocabulary#check(net.java.treaty
	 * .ExistsCondition, net.java.treaty.dynamic.PointOfExecution,
	 * net.java.treaty.dynamic.IRuntimeContext)
	 */
	public void check(ExistsCondition relationshipCondition,
			LifecycleEvent pointOfExecution, IRuntimeContext runtimeContext)
			throws VerificationException {

		/*
		 * The OCLVocabulary does not support any exists conditions. Thus, an
		 * exception will be thrown.
		 */
		throw new VerificationException(
				OclVocabularyMessages.INFO_NO_EXISTS_CONDITIONS_PROVIDED);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.dynamic.IDynamicContractVocabulary#check(net.java.treaty
	 * .PropertyCondition, net.java.treaty.dynamic.PointOfExecution,
	 * net.java.treaty.dynamic.IRuntimeContext)
	 */
	public void check(PropertyCondition relationshipCondition,
			LifecycleEvent pointOfExecution, IRuntimeContext runtimeContext)
			throws VerificationException {

		/*
		 * The OCLVocabulary does not support any property conditions. Thus, an
		 * exception will be thrown.
		 */
		throw new VerificationException(
				OclVocabularyMessages.INFO_NO_PROPERTY_CONDITIONS_PROVIDED);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.dynamic.IDynamicContractVocabulary#check(net.java.treaty
	 * .RelationshipCondition, net.java.treaty.dynamic.PointOfExecution,
	 * net.java.treaty.dynamic.IRuntimeContext)
	 */
	public void check(RelationshipCondition relationshipCondition,
			LifecycleEvent pointOfExecution, IRuntimeContext runtimeContext)
			throws VerificationException {

		String relationShip;

		Resource resource1;
		Resource resource2;

		/* Get the relationship and the resources. */
		relationShip = relationshipCondition.getRelationship().toString();
		resource1 = relationshipCondition.getResource1();
		resource2 = relationshipCondition.getResource2();

		/* Check if both resources are instantiated and loaded. */
		assert resource1.isInstantiated();
		assert resource1.isLoaded();
		assert resource2.isInstantiated();
		assert resource2.isLoaded();

		/*
		 * Check if the given relationship equals any predicate of the OCL
		 * Vocabulary and evaluate the predicate.
		 */
		if (PREDICATE_INSTANCE_OF.equals(relationShip)) {

			/* Delegate the verification to the toolkit manager. */
			this.myOclToolkitManager.checkIsInstanceOf(resource1, resource2);
		}

		else if (PREDICATE_FULFILLS_CONTRACT.equals(relationShip)) {

			/* Delegate the verification to the toolkit manager. */
			this.myOclToolkitManager.checkIsContractFulfilled(resource1, resource2,
					pointOfExecution, runtimeContext);
		}

		/* Else throw an exception. */
		else {
			String msg;

			msg =
					NLS.bind(
							OclVocabularyMessages.INFO_REALTIONSHIP_CONDITION_NOT_SUPPORTED,
							relationShip.toString());

			throw new VerificationException(msg);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getContributedPredicates()
	 */
	public Collection<URI> getContributedPredicates() {

		/* Eventually initialize the predicate collection. */
		if (this.myPredicates == null) {

			this.myPredicates = new ArrayList<URI>();

			/* Try to add all predicates as URIs to the collection. */
			try {
				this.myPredicates.add(new URI(PREDICATE_INSTANCE_OF));
				this.myPredicates.add(new URI(PREDICATE_FULFILLS_CONTRACT));
			}

			catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		// no else.

		return this.myPredicates;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getContributedTypes()
	 */
	public Collection<URI> getContributedTypes() {

		/* Eventually initialize the types collection. */
		if (this.myTypes == null) {

			this.myTypes = new ArrayList<URI>();

			/* Try to add all types as URIs to the collection. */
			try {
				this.myTypes.add(new URI(TYPE_MODEL_UML2));
				this.myTypes.add(new URI(TYPE_MODEL_EMF_ECORE));
				this.myTypes.add(new URI(TYPE_MODEL_JAVA));
				this.myTypes.add(new URI(TYPE_MODELINSTANCE_JAVA));
				this.myTypes.add(new URI(TYPE_OCL_FILE));
			}

			catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		// no else.

		return this.myTypes;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ResourceLoader#load(java.net.URI, java.lang.String,
	 * net.java.treaty.Connector)
	 */
	public Object load(URI type, String name, Connector connector)
			throws ResourceLoaderException {

		/*
		 * Check if the given URI leads to the name space of the OCL vocabulary.
		 * Else throw an exception.
		 */
		if (!type.toString().startsWith(NAMESPACE)) {
			String msg;

			msg =
					NLS.bind(OclVocabularyMessages.INFO_TYPE_NOT_SUPPORTED, type
							.toString());

			throw new ResourceLoaderException(msg);
		}
		// no else.

		Object result;
		Bundle connectorBundle;
		String typeName;

		result = null;

		/* Try to get the connector's bundle. */
		connectorBundle = ((EclipsePlugin) connector.getOwner()).getBundle();

		/* Convert the type into a string. */
		typeName = type.toString();

		/* Check if the loaded resource match to an UML2 or EMF Ecore model. */
		if (TYPE_MODEL_UML2.equals(typeName)
				|| TYPE_MODEL_EMF_ECORE.equals(typeName)) {

			/* Try to find the given resource. */
			URL resourceURL;
			resourceURL = connectorBundle.getResource(name);

			/* Load the model. */
			result = this.myOclToolkitManager.loadModel(resourceURL, typeName);
		}

		/* Else check if the loaded resource match to a Java model. */
		else if (TYPE_MODEL_JAVA.equals(typeName)) {
			result = this.myOclToolkitManager.loadJavaModel(name, connector);
		}

		/* Else check if the loaded resource match to a Java model instance. */
		else if (TYPE_MODELINSTANCE_JAVA.equals(typeName)) {
			result = this.myOclToolkitManager.loadJavaModelInstance(name, connector);
		}

		/* Else check if the loaded resource match to an OCL constraint file. */
		else if (TYPE_OCL_FILE.equals(typeName)) {

			URL resourceURL;

			/* Try to find the given resource. */
			resourceURL = connectorBundle.getResource(name);

			result = this.myOclToolkitManager.loadConstraintFile(resourceURL);
		}
		// no else.

		/* If no resource has been loaded, throw an exception. */
		if (result == null) {
			String msg;

			msg =
					NLS.bind(OclVocabularyMessages.ERROR_LOAD_RESOURCE_FAILED, name, type
							.toString());

			throw new ResourceLoaderException(msg);
		}
		// no else.

		return result;
	}
}