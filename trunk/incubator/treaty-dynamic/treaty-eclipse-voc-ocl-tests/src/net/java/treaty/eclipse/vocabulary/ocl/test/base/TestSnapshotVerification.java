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
package net.java.treaty.eclipse.vocabulary.ocl.test.base;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.List;

import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.eclipse.EclipseConnector;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.vocabulary.ocl.OCLVocabulary;
import net.java.treaty.eclipse.vocabulary.ocl.test.OclVocabularyTestPlugin;
import net.java.treaty.eclipse.vocabulary.ocl.test.base.model.TestClass;
import net.java.treaty.eclipse.vocabulary.ocl.test.base.model.TestInterface;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.Bundle;

import tudresden.ocl20.pivot.modelbus.model.IModel;
import tudresden.ocl20.pivot.modelbus.modelinstance.IModelInstance;

/**
 * <p>
 * Basis tests for the Treaty OCL vocabulary.
 * </p>
 * 
 * <p>
 * This file contains test cases to test the loading of resources of
 * <ul>
 * <li>The different models (and meta-models),</li>
 * <li>The different model instances,</li>
 * <li>Constraint files on different models.</li>
 * </ul>
 * </p>
 * 
 * @author Claas Wilke
 */
public class TestSnapshotVerification {

	/** The path of the EMF Ecore {@link IModel} used for testing. */
	private static String EMF_ECORE_MODEL_PATH = "resources/testmodel.ecore";

	/**
	 * The name of the Java {@link Class} used as {@link IModelInstance} for
	 * testing.
	 */
	private static String MODEL_INSTANCE_PATH =
			TestClass.class.getCanonicalName();

	/** The name of the Java {@link Class} used as {@link IModel} for testing. */
	private static String JAVA_MODEL_PATH =
			TestInterface.class.getCanonicalName();

	/** The path of the OCL file used for testing. */
	private static String OCL_FILE_PATH = "resources/testmodel.ocl";

	/** The path of the UML2 {@link IModel} used for testing. */
	private static String UML_MODEL_PATH = "resources/testmodel.uml";

	/** The {@link EclipseConnector} used during the tests. */
	private static EclipseConnector connector = null;

	/** The {@link OCLVocabulary} use for this test class. */
	private static OCLVocabulary oclVocabulary = null;

	/** The {@link EclipsePlugin} used during the tests. */
	private static EclipsePlugin plugin = null;

	/**
	 * <p>
	 * Prepares the test cases of this test class.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUp() throws Exception {

		String pluginID;
		Bundle bundle;

		IExtensionRegistry extensionRegistry;
		IExtension extension;

		oclVocabulary = new OCLVocabulary();

		/*
		 * Get the bundle of the OclVocabulary and initialize is as eclipse plug-in.
		 */
		pluginID = OclVocabularyTestPlugin.PLUGIN_ID;
		bundle = org.eclipse.core.runtime.Platform.getBundle(pluginID);

		plugin = new EclipsePlugin(bundle);

		/* Get the extension provided by the OCL vocabulary. */
		extensionRegistry =
				org.eclipse.core.runtime.Platform.getExtensionRegistry();
		extension =
				extensionRegistry
						.getExtension("net.java.treaty.eclipse.vocabulary.ocl");

		/* Create the connector which provides the extension. */
		connector = new EclipseExtension(plugin, extension);
	}

	/**
	 * <p>
	 * Finishes all test cases provided by this test class.
	 * </p>
	 */
	@AfterClass
	public static void tearDown() throws Exception {

		oclVocabulary = null;
		plugin = null;
		connector = null;
	}

	/**
	 * <p>
	 * Test the load mechanism of the {@link OCLVocabulary} with an UML2 model
	 * resource.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLoad01() throws Exception {

		Object loadedObject;

		URI type;

		type = new URI(OCLVocabulary.TYPE_MODEL_UML2);

		loadedObject = oclVocabulary.load(type, UML_MODEL_PATH, connector);

		/* Check if the loaded object is not null and an instance of IModel. */
		assertNotNull(loadedObject);
		assertTrue(loadedObject instanceof IModel);
	}

	/**
	 * <p>
	 * Test the load mechanism of the {@link OCLVocabulary} with a Java
	 * {@link IModelInstance} for an UML2 {@link IModel}.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLoad02() throws Exception {

		Object loadedObject;

		URI type;

		/* First load an UML2 model. */
		type = new URI(OCLVocabulary.TYPE_MODEL_UML2);

		loadedObject = oclVocabulary.load(type, UML_MODEL_PATH, connector);

		/* Then, try to load a model instance. */
		type = new URI(OCLVocabulary.TYPE_MODELINSTANCE_JAVA);
		loadedObject = oclVocabulary.load(type, MODEL_INSTANCE_PATH, connector);

		/*
		 * Check if the loaded object is not null and an instance of IModelInstance.
		 */
		assertNotNull(loadedObject);
		assertTrue(loadedObject instanceof IModelInstance);
	}

	/**
	 * <p>
	 * Test the load mechanism of the {@link OCLVocabulary} with a Java
	 * {@link IModelInstance} for an UML2 {@link IModel} and OCL constraints.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLoad03() throws Exception {

		Object loadedObject;

		URI type;

		/* First load an UML2 model. */
		type = new URI(OCLVocabulary.TYPE_MODEL_UML2);

		loadedObject = oclVocabulary.load(type, UML_MODEL_PATH, connector);

		/* Then, try to load a constraint file. */
		type = new URI(OCLVocabulary.TYPE_OCL_FILE);
		loadedObject = oclVocabulary.load(type, OCL_FILE_PATH, connector);

		/* Check if the loaded object is not null and an instance of IModel. */
		assertNotNull(loadedObject);
		assertTrue(loadedObject instanceof List<?>);
	}

	/**
	 * <p>
	 * Test the load mechanism of the {@link OCLVocabulary} with an EMF Ecore
	 * model resource.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLoad04() throws Exception {

		Object loadedObject;

		URI type;

		type = new URI(OCLVocabulary.TYPE_MODEL_EMF_ECORE);

		loadedObject = oclVocabulary.load(type, EMF_ECORE_MODEL_PATH, connector);

		/* Check if the loaded object is not null and an instance of IModel. */
		assertNotNull(loadedObject);
		assertTrue(loadedObject instanceof IModel);
	}

	/**
	 * <p>
	 * Test the load mechanism of the {@link OCLVocabulary} with a Java instance
	 * for an EMF Ecore model.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLoad05() throws Exception {

		Object loadedObject;

		URI type;

		/* First load an UML2 model. */
		type = new URI(OCLVocabulary.TYPE_MODEL_EMF_ECORE);

		loadedObject = oclVocabulary.load(type, EMF_ECORE_MODEL_PATH, connector);

		/* Then, try to load a model instance. */
		type = new URI(OCLVocabulary.TYPE_MODELINSTANCE_JAVA);
		loadedObject = oclVocabulary.load(type, MODEL_INSTANCE_PATH, connector);

		/*
		 * Check if the loaded object is not null and an instance of IModelInstance.
		 */
		assertNotNull(loadedObject);
		assertTrue(loadedObject instanceof IModelInstance);
	}

	/**
	 * <p>
	 * Test the load mechanism of the {@link OCLVocabulary} with a Java model.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLoad06() throws Exception {

		Object loadedObject;

		URI type;

		/* Load a Java model. */
		type = new URI(OCLVocabulary.TYPE_MODEL_JAVA);

		loadedObject = oclVocabulary.load(type, JAVA_MODEL_PATH, connector);

		/* Check if the loaded object is not null and an instance of IModel. */
		assertNotNull(loadedObject);
		assertTrue(loadedObject instanceof IModel);
	}

	/**
	 * <p>
	 * Test the load mechanism of the {@link OCLVocabulary} with a Java instance
	 * for a Java model.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLoad07() throws Exception {

		Object loadedObject;

		URI type;

		/* First load a Java model. */
		type = new URI(OCLVocabulary.TYPE_MODEL_JAVA);

		loadedObject = oclVocabulary.load(type, JAVA_MODEL_PATH, connector);

		/* Then, try to load a model instance. */
		type = new URI(OCLVocabulary.TYPE_MODELINSTANCE_JAVA);
		loadedObject = oclVocabulary.load(type, MODEL_INSTANCE_PATH, connector);

		/*
		 * Check if the loaded object is not null and an instance of IModelInstance.
		 */
		assertNotNull(loadedObject);
		assertTrue(loadedObject instanceof IModelInstance);
	}

	/**
	 * <p>
	 * Test to evaluate a relationship/predicate defined by the
	 * {@link OCLVocabulary} between an UML2 model and a Java model instance.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRelationship01() throws Exception {

		RelationshipCondition relationship;

		Resource resource1;
		Resource resource2;

		/* Create the resources. */
		resource2 =
				this.createResource(OCLVocabulary.TYPE_MODEL_UML2, UML_MODEL_PATH);
		resource1 =
				this.createResource(OCLVocabulary.TYPE_MODELINSTANCE_JAVA,
						MODEL_INSTANCE_PATH);

		/* Create the relationship. */
		relationship = new RelationshipCondition();

		relationship.setResource1(resource1);
		relationship.setResource2(resource2);

		relationship.setRelationship(new URI(OCLVocabulary.PREDICATE_INSTANCE_OF));

		/* The check must finish without exceptions. */
		oclVocabulary.check(relationship);
	}

	/**
	 * <p>
	 * Test to evaluate a relationship/predicate defined by the
	 * {@link OCLVocabulary} between an UML2 model, a Java model instance, and OCL
	 * constraints.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRelationship02() throws Exception {

		RelationshipCondition relationship;

		Resource resource1;
		Resource resource2;

		/*
		 * The UML2 model is no needed resource but must be loaded, before the
		 * instance and the constraints can be checked.
		 */
		this.createResource(OCLVocabulary.TYPE_MODEL_UML2, UML_MODEL_PATH);

		/* Create the resources. */
		resource1 =
				this.createResource(OCLVocabulary.TYPE_MODELINSTANCE_JAVA,
						MODEL_INSTANCE_PATH);
		resource2 = this.createResource(OCLVocabulary.TYPE_OCL_FILE, OCL_FILE_PATH);

		/* Create the relationship. */
		relationship = new RelationshipCondition();

		relationship.setResource1(resource1);
		relationship.setResource2(resource2);

		relationship.setRelationship(new URI(
				OCLVocabulary.PREDICATE_FULFILLS_CONTRACT));

		/* The check must finish without exceptions. */
		oclVocabulary.check(relationship);
	}

	/**
	 * <p>
	 * Test to evaluate a relationship/predicate defined by the
	 * {@link OCLVocabulary} between an EMF Ecore model and a Java model instance.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRelationship03() throws Exception {

		RelationshipCondition relationship;

		Resource resource1;
		Resource resource2;

		/* Create the resources. */
		resource2 =
				this.createResource(OCLVocabulary.TYPE_MODEL_EMF_ECORE,
						EMF_ECORE_MODEL_PATH);
		resource1 =
				this.createResource(OCLVocabulary.TYPE_MODELINSTANCE_JAVA,
						MODEL_INSTANCE_PATH);

		/* Create the relationship. */
		relationship = new RelationshipCondition();

		relationship.setResource1(resource1);
		relationship.setResource2(resource2);

		relationship.setRelationship(new URI(OCLVocabulary.PREDICATE_INSTANCE_OF));

		/* The check must finish without exceptions. */
		oclVocabulary.check(relationship);
	}

	/**
	 * <p>
	 * Test to evaluate a relationship/predicate defined by the
	 * {@link OCLVocabulary} between an Java {@link IModel} and a Java
	 * {@link IModelInstance}.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRelationship04() throws Exception {

		RelationshipCondition relationship;

		Resource resource1;
		Resource resource2;

		/* Create the resources. */
		resource2 =
				this.createResource(OCLVocabulary.TYPE_MODEL_JAVA, JAVA_MODEL_PATH);
		resource1 =
				this.createResource(OCLVocabulary.TYPE_MODELINSTANCE_JAVA,
						MODEL_INSTANCE_PATH);

		/* Create the relationship. */
		relationship = new RelationshipCondition();

		relationship.setResource1(resource1);
		relationship.setResource2(resource2);

		relationship.setRelationship(new URI(OCLVocabulary.PREDICATE_INSTANCE_OF));

		/* The check must finish without exceptions. */
		oclVocabulary.check(relationship);
	}

	/**
	 * <p>
	 * Test to evaluate a relationship/predicate defined by the
	 * {@link OCLVocabulary} between an EMF Ecore model, a Java model instance,
	 * and OCL constraints.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRelationship05() throws Exception {

		RelationshipCondition relationship;

		Resource resource1;
		Resource resource2;

		/*
		 * The UML2 model is no needed resource but must be loaded, before the
		 * instance and the constraints can be checked.
		 */
		this.createResource(OCLVocabulary.TYPE_MODEL_EMF_ECORE,
				EMF_ECORE_MODEL_PATH);

		/* Create the resources. */
		resource1 =
				this.createResource(OCLVocabulary.TYPE_MODELINSTANCE_JAVA,
						MODEL_INSTANCE_PATH);
		resource2 = this.createResource(OCLVocabulary.TYPE_OCL_FILE, OCL_FILE_PATH);

		/* Create the relationship. */
		relationship = new RelationshipCondition();

		relationship.setResource1(resource1);
		relationship.setResource2(resource2);

		relationship.setRelationship(new URI(
				OCLVocabulary.PREDICATE_FULFILLS_CONTRACT));

		/* The check must finish without exceptions. */
		oclVocabulary.check(relationship);
	}

	/**
	 * <p>
	 * Test to evaluate a relationship/predicate defined by the
	 * {@link OCLVocabulary} between a Java model, a Java model instance, and OCL
	 * constraints.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRelationship06() throws Exception {

		RelationshipCondition relationship;

		Resource resource1;
		Resource resource2;

		/*
		 * The UML2 model is no needed resource but must be loaded, before the
		 * instance and the constraints can be checked.
		 */
		this.createResource(OCLVocabulary.TYPE_MODEL_JAVA, JAVA_MODEL_PATH);

		/* Create the resources. */
		resource1 =
				this.createResource(OCLVocabulary.TYPE_MODELINSTANCE_JAVA,
						MODEL_INSTANCE_PATH);
		resource2 = this.createResource(OCLVocabulary.TYPE_OCL_FILE, OCL_FILE_PATH);

		/* Create the relationship. */
		relationship = new RelationshipCondition();

		relationship.setResource1(resource1);
		relationship.setResource2(resource2);

		relationship.setRelationship(new URI(
				OCLVocabulary.PREDICATE_FULFILLS_CONTRACT));

		/* The check must finish without exceptions. */
		oclVocabulary.check(relationship);
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
		value = oclVocabulary.load(result.getType(), aName, connector);
		result.setValue(value);

		return result;
	}
}