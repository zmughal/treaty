/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.vocabulary.builtins.java;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.URI;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import net.java.treaty.*;
import net.java.treaty.vocabulary.ContractOntology;

/**
 * Contributes the Java vocabulary. Warning: this is not complete.
 * 
 * @author Jens Dietrich
 */

public class JavaVocabulary extends ContractOntology {

	/** The name space of the {@link JavaVocabulary}. */
	public static final String NAMESPACE = "http://www.treaty.org/java#";

	/** The relationship <code>implements</code> of the {@link JavaVocabulary}. */
	public static final String RELATIONSHIP_IMPLEMENTS = NAMESPACE + "implements";

	/** The type <code>AbstractType</code> of the {@link JavaVocabulary}. */
	public static final String TYPE_ABSTRACT_TYPE = NAMESPACE + "AbstractType";

	/** The type <code>InstantiableClass</code> of the {@link JavaVocabulary}. */
	public static final String TYPE_INSTANTIABLE_CLASS =
			NAMESPACE + "InstantiableClass";

	/** The location of the {@link JavaVocabulary}'s ontology. */
	private static final String ONTOLOGY_LOCATION =
			"/net/java/treaty/vocabulary/builtins/java/java.owl";

	/** The {@link OntModel} of the {@link JavaVocabulary}. */
	private OntModel myOntology = null;

	/**
	 * <p>
	 * Creates a new {@link JavaVocabulary}.
	 * </p>
	 */
	public JavaVocabulary() {

		super();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.ExistsCondition)
	 */
	public void check(ExistsCondition condition) throws VerificationException {

		Resource resource;
		resource = condition.getResource();

		/* Check preconditions. */
		assert resource.isInstantiated();
		assert resource.isLoaded();

		Class<?> clazz;
		clazz = (Class<?>) resource.getValue();

		/* Handle type that shall be checked. */
		if (TYPE_ABSTRACT_TYPE.equals(resource.getType().toString())) {
			checkAbstractType(resource, clazz);
		}

		else if (TYPE_INSTANTIABLE_CLASS.equals(resource.getType().toString())) {
			checkInstantiableClass(resource, clazz);
		}

		/* Else throw an exception. */
		else {
			throw new VerificationException("Unknown type of resource " + resource
					+ " cannot be checked using the JavaVocabulary.");
		}
		// end else.
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.PropertyCondition)
	 */
	public void check(PropertyCondition relationshipCondition)
			throws VerificationException {

		throw new VerificationException(
				"This Vocabulary does not define PropertyConditions.");
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.RelationshipCondition)
	 */
	public void check(RelationshipCondition condition)
			throws VerificationException {

		String relationshipName;
		relationshipName = condition.getRelationship().toString();

		Resource resource1;
		resource1 = condition.getResource1();

		Resource resource2;
		resource2 = condition.getResource2();

		/* Check preconditions. */
		assert resource1.isInstantiated();
		assert resource1.isLoaded();
		assert resource2.isInstantiated();
		assert resource2.isLoaded();

		if (RELATIONSHIP_IMPLEMENTS.equals(relationshipName)) {

			/* Check if class1 is instantiable. */
			Class<?> class1;
			class1 = (Class<?>) resource1.getValue();
			this.checkInstantiableClass(resource1, class1);

			/* Check if class2 is abstract. */
			Class<?> class2 = (Class<?>) resource2.getValue();
			this.checkAbstractType(resource2, class2);

			/* Check implements relationship. */
			if (class2.isAssignableFrom(class1)) {
				/* Success. Do nothing. */
			}

			/* Else throw an exception. */
			else {
				throw new VerificationException("Condition was not satisfied: "
						+ condition);
			}
			// end else.
		}

		/* Else throw an exception. */
		else {
			throw new VerificationException(
					"Predicate not supported by JavaVocabulary: " + relationshipName);
		}
		// end else.
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.vocabulary.ContractOntology#getOntology()
	 */
	public OntModel getOntology() {

		/* Probably initialize the ontology. */
		if (this.myOntology == null) {
			this.myOntology = ModelFactory.createOntologyModel();
			this.myOntology.read(JavaVocabulary.class.getResource(ONTOLOGY_LOCATION)
					.toString());
		}

		return this.myOntology;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ResourceLoader#load(java.net.URI, java.lang.String,
	 * net.java.treaty.Connector)
	 */
	public Object load(URI type, String name, Connector connector)
			throws ResourceLoaderException {

		/* Check if the given type belongs to this vocabulary at all. */
		if (!type.toString().startsWith(NAMESPACE)) {
			throw new ResourceLoaderException(
					"This vocabulary cannot be used to instantiate resources of this type: "
							+ type);
		}
		// no else.

		Component component;
		component = connector.getOwner();

		/* Try to load the class. */
		try {

			String className;
			className = name;

			String executable = null;

			/*
			 * Note: we remove postfixes starting with : and / in Eclipse, this is
			 * done in org.eclipse.core.internal.registry.ConfigurationElement.
			 */

			int index = name.indexOf(':');

			if (index != -1) {
				executable = name.substring(0, index).trim();
				// initData = prop.substring(i + 1).trim();
			}

			else {
				executable = name;
			}

			index = executable.indexOf('/');

			if (index != -1) {
				// contributorName = executable.substring(0, i).trim();
				className = executable.substring(index + 1).trim();
			}
			else {
				className = executable;
			}

			Class<?> clazz;
			clazz = component.loadClass(className);

			/* Analyze class types. */
			if (TYPE_ABSTRACT_TYPE.equals(type)) {
				if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
					return true;
				}

				else {
					throw new ResourceLoaderException(
							"This class can be loaded but is not abstract: " + name);
				}
			}

			else if (TYPE_INSTANTIABLE_CLASS.equals(type)) {

				try {
					clazz.newInstance();
				}

				catch (Exception e) {
					throw new ResourceLoaderException(
							"This class can be loaded but not instantiated: " + name);
				}
				// end catch.
			}

			else {
				return clazz;
			}
		}
		// end try.

		catch (ClassNotFoundException e) {
			throw new ResourceLoaderException("Cannot load class " + name
					+ " with classloader from component " + component.getId());
		}
		// end catch.

		throw new ResourceLoaderException("Cannot load Resource " + name
				+ " of Type " + type);
	}

	/**
	 * <p>
	 * A helper method that checks if a given {@link Class} is an abstract
	 * {@link Class} or an interface.
	 * </p>
	 * 
	 * @param resource
	 *          The {@link Resource} whose value represents the {@link Class} that
	 *          shall be checked.
	 * @param clazz
	 *          The {@link Class} that shall be checked.
	 * @throws VerificationException
	 *           Thrown, if the {@link Class} is neither an interface nor an
	 *           abstract {@link Class} or the given {@link Class} is
	 *           <code>null</code>.
	 */
	private void checkAbstractType(Resource resource, Class<?> clazz)
			throws VerificationException {

		if (clazz == null) {
			throw new VerificationException("The value of the Resource " + resource
					+ " was null.");
		}

		else if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
			return;
		}

		else {
			throw new VerificationException("The value of the Resource " + resource
					+ " is " + clazz + " - this is not an abstract Java Type.");
		}
	}

	/**
	 * <p>
	 * A helper method that checks if a given {@link Class} is an instantiable
	 * {@link Class}.
	 * </p>
	 * 
	 * @param resource
	 *          The {@link Resource} whose value the {@link Class} is that shall
	 *          be checked.
	 * @param clazz
	 *          The {@link Class} that shall be checked.
	 * @throws VerificationException
	 *           Thrown, if the given {@link Class} is no instantiable
	 *           {@link Class} or if the given {@link Class} is <code>null</code>.
	 */
	private void checkInstantiableClass(Resource resource, Class<?> clazz)
			throws VerificationException {

		if (clazz == null) {
			throw new VerificationException("The value of Resource " + resource
					+ " was null.");
		}

		else {

			/* Try to check if the class is instantiable. */
			try {

				if (Modifier.isAbstract(clazz.getModifiers())) {
					throw new VerificationException("The value of Resource " + resource
							+ " is " + clazz + " - This is not an instantiable Java Type.");
				}

				else if (clazz.isInterface()) {
					throw new VerificationException("The value of Resource " + resource
							+ " is " + clazz + " - This is not an instantiable Java Type.");
				}

				else {
					Constructor<?> constructor;
					constructor = clazz.getConstructor(new Class[0]);

					if (constructor == null
							|| !Modifier.isPublic(constructor.getModifiers())) {
						throw new VerificationException("The value of Resource " + resource
								+ " is " + clazz + " - This is not an instantiable Java Type.");
					}
					// no else.
				}
				// end else.
			}
			// end try.

			catch (Exception e) {
				throw new VerificationException("The value of Resource " + resource
						+ " is " + clazz + " - This is not an instantiable Java Type.");
			}
			// end catch.
		}
		// end else (class not null).
	}
}