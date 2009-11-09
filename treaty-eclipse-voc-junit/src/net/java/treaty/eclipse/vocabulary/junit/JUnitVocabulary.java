/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.vocabulary.junit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;

import net.java.treaty.Connector;
import net.java.treaty.ContractVocabulary;
import net.java.treaty.ExistsCondition;
import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.ResourceLoaderException;
import net.java.treaty.VerificationException;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.vocabulary.ContractOntology;

import org.osgi.framework.Bundle;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 * Contributes the JUnit vocabulary.
 * 
 * @author Jens Dietrich
 */

public class JUnitVocabulary extends ContractOntology implements
		ContractVocabulary {

	/** The singleton Instance of the {@link JUnitVocabulary}. */
	public static JUnitVocabulary INSTANCE = new JUnitVocabulary();

	public static final String NS = "http://www.treaty.org/junit#";
	// types
	public static final String TESTCASE = NS + "TestCase";
	// types defined elsewhere
	public static final String INSTANTIABLE_CLASS =
			"http://www.treaty.org/java#InstantiableClass";

	// relationships
	public static final String VERIFIES = NS + "verifies";

	/**
	 * The {@link Resource} location of the ontology file of the
	 * {@link OCLVocabulary}.
	 */
	private static final String RESOURCE_ONTOLOGY = "./vocabulary/junit.owl";

	/** The {@link OntModel} belonging to the {@link OCLVocabulary}. */
	private OntModel myOntology = null;

	/**
	 * <p>
	 * Private Constructor for Singleton pattern.
	 * </p>
	 */
	private JUnitVocabulary() {

		super();
	}

	public void check(RelationshipCondition condition)
			throws VerificationException {

		String rel = condition.getRelationship().toString();
		Resource res1 = condition.getResource1();
		Resource res2 = condition.getResource2();
		assert res1.isInstantiated();
		assert res1.isLoaded();
		assert res2.isInstantiated();
		assert res2.isLoaded();
		if (VERIFIES.equals(rel)) {
			Class<?> test = (Class<?>) res2.getValue();
			this.checkJUnit4Type(res2, test);
			Class<?> tested = (Class<?>) res1.getValue();
			try {
				boolean result = new TestRunner().run(test, tested);
				if (!result) {
					throw new VerificationException("Implementation class " + tested
							+ " failed test " + test);
				}
			} catch (Exception x) {
				throw new VerificationException("", x);
			}

		}

	}

	public Object load(URI type, String name, Connector connector)
			throws ResourceLoaderException {

		if (!type.toString().startsWith(NS))
			throw new ResourceLoaderException(
					"This plugin cannot be used to instantiate resources of this type: "
							+ type);
		Bundle b = ((EclipsePlugin) connector.getOwner()).getBundle();

		try {
			Class<?> clazz = b.loadClass(name);
			if (INSTANTIABLE_CLASS.equals(type)) {
				try {
					return clazz;
				} catch (Exception x) {
					throw new ResourceLoaderException(
							"This class can be loaded but not instantiated: " + name);
				}
			}
			else if (TESTCASE.equals(type)) {
				try {
					clazz.newInstance();
				} catch (Exception x) {
					throw new ResourceLoaderException(
							"This class can be loaded but not instantiated: " + name);
				}
			}
			else
				return clazz;
		} catch (ClassNotFoundException e) {
			throw new ResourceLoaderException("Cannot load class " + name
					+ " with classloader from plugin " + b.getBundleId());
		}
		throw new ResourceLoaderException("Cannot load resource " + name
				+ " of type " + type);
	}

	public void check(PropertyCondition relationshipCondition)
			throws VerificationException {

		throw new VerificationException(
				"This vocabulary does not define property conditions");
	}

	public void check(ExistsCondition condition) throws VerificationException {

		Resource resource = condition.getResource();
		assert resource.isInstantiated();
		assert resource.isLoaded();
		Class<?> clazz = (Class<?>) resource.getValue();
		if (TESTCASE.equals(resource.getType().toString())) {
			checkJUnit4Type(resource, clazz);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.vocabulary.ContractOntology#getOntology()
	 */
	@Override
	public OntModel getOntology() {

		/* Probably load the ontology. */
		if (this.myOntology == null) {
			this.myOntology = ModelFactory.createOntologyModel();
			this.myOntology.read(Activator.getDefault().getBundle().getResource(
					RESOURCE_ONTOLOGY).toString());
		}
		// no else.

		return this.myOntology;
	}

	private void checkJUnit4Type(Resource resource, Class<?> clazz)
			throws VerificationException {

		if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers()))
			throw new VerificationException("The value of resource " + resource
					+ " is " + clazz + " - this is class should not be abstract");
		if (!Modifier.isPublic(clazz.getModifiers())) {
			throw new VerificationException("The value of resource " + resource
					+ " is " + clazz + " - this class must be public");
		}
		// check for constructor with parameter used for dependency injection
		boolean ok = false;
		for (Constructor<?> constructor : clazz.getConstructors()) {
			if (Modifier.isPublic(constructor.getModifiers())
					&& constructor.getParameterTypes().length == 1) {
				ok = true;
				break;
			}
		}
		if (!ok) {
			throw new VerificationException(
					"The value of resource "
							+ resource
							+ " is "
							+ clazz
							+ " - this class must have a public constructor with one parameter to inject the object to be tested");
		}
		// check that at least one test case exists
		ok = false;
		for (Method method : clazz.getMethods()) {
			if (Modifier.isPublic(method.getModifiers())) {
				for (Annotation anno : method.getAnnotations()) {
					if ("org.junit.Test".equals(anno.annotationType().getName())) {
						ok = true;
						break;
					}
				}

			}
		}
		if (!ok) {
			throw new VerificationException(
					"The value of resource "
							+ resource
							+ " is "
							+ clazz
							+ " - this class must have at least one test method annotated with @org.junit.Test");
		}
	}

}
