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
package net.java.treaty.eclipse.vocabulary.ocl.test.dynamic;

import java.net.URI;
import java.net.URISyntaxException;

import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.dynamic.IRuntimeContext;
import net.java.treaty.eclipse.EclipseConnector;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.vocabulary.ocl.OCLVocabulary;
import net.java.treaty.eclipse.vocabulary.ocl.test.OclVocabularyTestPlugin;
import net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model.BadTestClass;
import net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model.GoodTestClass;
import net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model.TestInterface;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.osgi.framework.Bundle;

import tudresden.ocl20.pivot.modelbus.IModel;
import tudresden.ocl20.pivot.modelbus.IModelInstance;

/**
 * <p>
 * Provides some methods that can be used to perform tests on the Treaty
 * {@link OCLVocabulary} testing the vocabulary with an {@link IRuntimeContext}.
 * </p>
 * 
 * @author Claas Wilke
 */
public class DynamicOclVocabularyTestServices {

	/**
	 * The Java {@link Class} used as {@link IModelInstance} for testing in
	 * negative test cases.
	 */
	private static Class<?> BAD_MODEL_INSTANCE = BadTestClass.class;

	/**
	 * The name of the Java {@link Class} used as {@link IModelInstance} for
	 * testing in negative test cases.
	 */
	private static String BAD_MODEL_INSTANCE_PATH =
			BAD_MODEL_INSTANCE.getCanonicalName();

	/** The name of the Java {@link Class} used as {@link IModel} for testing. */
	private static String JAVA_MODEL_PATH =
			TestInterface.class.getCanonicalName();

	/**
	 * The Java {@link Class} used as {@link IModelInstance} for testing in
	 * positive test cases.
	 */
	private static Class<?> GOOD_MODEL_INSTANCE = GoodTestClass.class;

	/**
	 * The name of the Java {@link Class} used as {@link IModelInstance} for
	 * testing in positive test cases.
	 */
	private static String GOOD_MODEL_INSTANCE_PATH =
			GOOD_MODEL_INSTANCE.getCanonicalName();

	/** The path of the OCL file used for testing. */
	private static String OCL_FILE_PATH = "resources/dynamictest/dynamictest.ocl";

	private static DynamicOclVocabularyTestServices myInstance;

	/** The {@link EclipsePlugin} used during the tests. */
	private EclipsePlugin myPlugin;

	/** The {@link EclipseConnector} used during the tests. */
	private EclipseConnector myConnector;

	/** The {@link OCLVocabulary} use for this test class. */
	private OCLVocabulary myOclVocabulary;

	/** The {@link Resource} representing the Java model. */
	private Resource myResourceJavaModel;

	/**
	 * The {@link Resource} representing the Java model instance that fulfills the
	 * constraints (or their implementation dependent part).
	 */
	private Resource myResourceGoodModelInstance;

	/**
	 * The {@link Resource} representing the Java model instance that does not
	 * fulfill the constraints (or their implementation dependent part).
	 */
	private Resource myResourceBadModelInstance;

	/**
	 * The {@link Resource} representing OCL constraint file.
	 */
	private Resource myResourceOclConstraints;

	/**
	 * The {@link RelationshipCondition} that describes the
	 * <code>instanceOf</code> {@link RelationshipCondition} between the Java
	 * Model and the good Java Model Instance.
	 */
	private RelationshipCondition myRelationshipGoodModelInstance;

	/**
	 * The {@link RelationshipCondition} that describes the
	 * <code>instanceOf</code> {@link RelationshipCondition} between the Java
	 * Model and the bad Java Model Instance.
	 */
	private RelationshipCondition myRelationshipBadModelInstance;

	/**
	 * The {@link RelationshipCondition} that describes the
	 * <code>fulfillsContract</code> {@link RelationshipCondition} between the
	 * Java Model and the good Java Model Instance.
	 */
	private RelationshipCondition myRelationshipGoodContract;

	/**
	 * The {@link RelationshipCondition} that describes the
	 * <code>fulfillsContract</code> {@link RelationshipCondition} between the
	 * Java Model and the bad Java Model Instance.
	 */
	private RelationshipCondition myRelationshipBadContract;

	/**
	 * <p>
	 * Private constructor used for singleton pattern.
	 * </p>
	 */
	private DynamicOclVocabularyTestServices() {

	}

	/**
	 * <p>
	 * Returns the singleton instance of the
	 * {@link DynamicOclVocabularyTestServices}.
	 * </p>
	 * 
	 * @return The singleton instance of the
	 *         {@link DynamicOclVocabularyTestServices}.
	 * @throws Exception
	 *           Thrown if the instance's initialization fails.
	 */
	public static DynamicOclVocabularyTestServices getInstance() throws Exception {

		/* Eventually initialize the instance. */
		if (myInstance == null) {
			myInstance = new DynamicOclVocabularyTestServices();

			myInstance.initialize();
		}
		// no else.

		return myInstance;
	}

	/**
	 * <p>
	 * Returns the {@link OCLVocabulary} used during testing.
	 * </p>
	 * 
	 * @return The {@link OCLVocabulary} used during testing.
	 */
	public OCLVocabulary getOclVocabulary() {

		return this.myOclVocabulary;
	}

	/**
	 * <p>
	 * Returns the {@link RelationshipCondition} that describes the
	 * <code>instanceOf</code> {@link RelationshipCondition} between the Java
	 * Model and the good Java Model Instance.
	 * </p>
	 * 
	 * @return The {@link RelationshipCondition} that describes the
	 *         <code>instanceOf</code> {@link RelationshipCondition} between the
	 *         Java Model and the good Java Model Instance.
	 */
	public RelationshipCondition getRelationshipGoodModelInstance() {

		return this.myRelationshipGoodModelInstance;
	}

	/**
	 * <p>
	 * Returns the {@link RelationshipCondition} that describes the
	 * <code>instanceOf</code> {@link RelationshipCondition} between the Java
	 * Model and the bad Java Model Instance.
	 * </p>
	 * 
	 * @return The {@link RelationshipCondition} that describes the
	 *         <code>instanceOf</code> {@link RelationshipCondition} between the
	 *         Java Model and the bad Java Model Instance.
	 */
	public RelationshipCondition getRelationshipBadModelInstance() {

		return this.myRelationshipBadModelInstance;
	}

	/**
	 * <p>
	 * Returns the {@link RelationshipCondition} that describes the
	 * <code>fulfillsContract</code> {@link RelationshipCondition} between the
	 * Java Model and the good Java Model Instance.
	 * </p>
	 * 
	 * @return The {@link RelationshipCondition} that describes the
	 *         <code>fulfillsContract</code> {@link RelationshipCondition} between
	 *         the Java Model and the good Java Model Instance.
	 */
	public RelationshipCondition getRelationshipGoodContract() {

		return this.myRelationshipGoodContract;
	}

	/**
	 * <p>
	 * Returns the {@link RelationshipCondition} that describes the
	 * <code>fulfillsContract</code> {@link RelationshipCondition} between the
	 * Java Model and the bad Java Model Instance.
	 * </p>
	 * 
	 * @return The {@link RelationshipCondition} that describes the
	 *         <code>fulfillsContract</code> {@link RelationshipCondition} between
	 *         the Java Model and the bad Java Model Instance.
	 */
	public RelationshipCondition getRelationshipBadContract() {

		return this.myRelationshipBadContract;
	}

	/**
	 * <p>
	 * Prepares the test cases of this test class.
	 * </p>
	 * 
	 * @throws Exception
	 *           Thrown, if the initialization fails.
	 */
	private void initialize() throws Exception {

		String pluginID;
		Bundle bundle;

		IExtensionRegistry extensionRegistry;
		IExtension extension;

		this.myOclVocabulary = new OCLVocabulary();

		/*
		 * Get the bundle of the OclVocabulary and initialize is as eclipse plug-in.
		 */
		pluginID = OclVocabularyTestPlugin.PLUGIN_ID;
		bundle = org.eclipse.core.runtime.Platform.getBundle(pluginID);

		this.myPlugin = new EclipsePlugin(bundle);

		/* Get the extension provided by the OCL vocabulary. */
		extensionRegistry =
				org.eclipse.core.runtime.Platform.getExtensionRegistry();
		extension =
				extensionRegistry
						.getExtension("net.java.treaty.eclipse.vocabulary.ocl");

		/* Create the connector which provides the extension. */
		this.myConnector = new EclipseExtension(myPlugin, extension);

		/* Load resources. */
		this.loadResources();

		/* Create Relationships. */
		createRelationships();
	}

	/**
	 * <p>
	 * A helper method that loads the resources required by this
	 * {@link DynamicOclVocabularyTestServices}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Thrown, if a {@link Resource} can not be loaded.
	 */
	private void loadResources() throws Exception {

		/* Load the Java Model resource. */
		myResourceJavaModel =
				this.createResource(OCLVocabulary.TYPE_MODEL_JAVA, JAVA_MODEL_PATH);

		/* Load the good model instance */
		myResourceGoodModelInstance =
				this.createResource(OCLVocabulary.TYPE_MODELINSTANCE_JAVA,
						GOOD_MODEL_INSTANCE_PATH);

		/* Load the bad model instance */
		myResourceBadModelInstance =
				this.createResource(OCLVocabulary.TYPE_MODELINSTANCE_JAVA,
						BAD_MODEL_INSTANCE_PATH);

		/* Load the OCL constraints. */
		myResourceOclConstraints =
				this.createResource(OCLVocabulary.TYPE_OCL_FILE, OCL_FILE_PATH);
	}

	/**
	 * <p>
	 * A helper method which tries to create a resource to a given type and path.
	 * </p>
	 * 
	 * @param aType
	 *          The type of {@link Resource} which shall be created.
	 * @param aName
	 *          The path where the {@link Resource} is located.
	 * @return The create {@link Resource}.
	 * @throws Exception
	 */
	private Resource createResource(String aType, String aName) throws Exception {

		Resource result;
		Object value;

		result = new Resource();

		/* Set name and type of the resource. */
		result.setType(new URI(aType));
		result.setName(aName);

		/* Load the value and set it. */
		value =
				this.myOclVocabulary.load(result.getType(), aName, this.myConnector);
		result.setValue(value);

		return result;
	}

	/**
	 * <p>
	 * A helper method that creates the {@link RelationshipCondition}s provided by
	 * this {@link DynamicOclVocabularyTestServices}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Thrown, if a {@link RelationshipCondition} can not be created.
	 */
	private void createRelationships() throws URISyntaxException {

		myRelationshipGoodModelInstance = new RelationshipCondition();
		myRelationshipGoodModelInstance.setResource1(myResourceGoodModelInstance);
		myRelationshipGoodModelInstance.setResource2(myResourceJavaModel);
		myRelationshipGoodModelInstance.setRelationship(new URI(
				OCLVocabulary.PREDICATE_INSTANCE_OF));

		myRelationshipBadModelInstance = new RelationshipCondition();
		myRelationshipBadModelInstance.setResource1(myResourceBadModelInstance);
		myRelationshipBadModelInstance.setResource2(myResourceJavaModel);
		myRelationshipBadModelInstance.setRelationship(new URI(
				OCLVocabulary.PREDICATE_INSTANCE_OF));

		myRelationshipGoodContract = new RelationshipCondition();
		myRelationshipGoodContract.setResource1(myResourceGoodModelInstance);
		myRelationshipGoodContract.setResource2(myResourceOclConstraints);
		myRelationshipGoodContract.setRelationship(new URI(
				OCLVocabulary.PREDICATE_FULFILLS_CONTRACT));

		myRelationshipBadContract = new RelationshipCondition();
		myRelationshipBadContract.setResource1(myResourceBadModelInstance);
		myRelationshipBadContract.setResource2(myResourceOclConstraints);
		myRelationshipBadContract.setRelationship(new URI(
				OCLVocabulary.PREDICATE_FULFILLS_CONTRACT));
	}
}