/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.vocabulary.builtins;

import java.net.URI;

import net.java.treaty.Connector;
import net.java.treaty.ContractLogger;
import net.java.treaty.ContractVocabulary;
import net.java.treaty.ExistsCondition;
import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.ResourceLoaderException;
import net.java.treaty.VerificationException;
import net.java.treaty.vocabulary.ContractOntology;
import net.java.treaty.vocabulary.builtins.java.JavaVocabulary;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 * <p>
 * Built-in {@link ContractVocabulary} for basic operations.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class BasicOpVocabulary extends ContractOntology {

	/** FIXME Claas: Can this namespace be used?
	 * 
	 * The name space of the {@link BasicOpVocabulary}. */
	public static final String NAME_SPACE_NAME =
			"http://www.treaty.org/basic";

	/** The name of the Boolean type of the {@link BasicOpVocabulary}. */
	public static final String TYPE_NAME_BOOLEAN = NAME_SPACE_NAME + "#boolean";

	/** The name of the Double type of the {@link BasicOpVocabulary}. */
	public static final String TYPE_NAME_DOUBLE = NAME_SPACE_NAME + "#double";

	/** The name of the Int type of the {@link BasicOpVocabulary}. */
	public static final String TYPE_NAME_INT = NAME_SPACE_NAME + "#int";

	/** The name of the Integer type of the {@link BasicOpVocabulary}. */
	public static final String TYPE_NAME_INTEGER = NAME_SPACE_NAME + "#integer";

	/** The name of the String type of the {@link BasicOpVocabulary}. */
	public static final String TYPE_NAME_STRING = NAME_SPACE_NAME + "#string";

	/** The location of the {@link BasicOpVocabulary}'s ontology. */
	private static final String ONTOLOGY_LOCATION =
			"/net/java/treaty/vocabulary/builtins/basic.owl";

	/** The {@link OntModel} of the {@link BasicOpVocabulary}. */
	private OntModel myOntology = null;

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.ExistsCondition)
	 */
	public void check(ExistsCondition existsCondition)
			throws VerificationException {

		Resource resource;
		resource = existsCondition.getResource();

		URI type;
		type = resource.getType();

		if (type.toString().startsWith(BasicOpVocabulary.NAME_SPACE_NAME)) {

			assert resource.isInstantiated();
			assert resource.isLoaded();

			if (resource.getValue() == null) {
				throw new VerificationException("Resource value should not be null");
			}

			this.checkBuiltInDatatype(resource.getValue(), type);
		}

		else {
			throw new VerificationException(
					"The basic operations vocabularies does not support the type " + type
							+ ".");
		}
		// end else.
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.RelationshipCondition)
	 */
	public void check(RelationshipCondition relationshipCondition)
			throws VerificationException {

		throw new VerificationException(
				"The basic operations vocabularies does not define relationships.");
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.PropertyCondition)
	 */
	public void check(PropertyCondition condition) throws VerificationException {

		URI operatorURI;
		operatorURI = condition.getOperator();

		BuiltInOperator operator;
		operator = BuiltInOperators.INSTANCE.getInstance(operatorURI);

		if (operator == null) {
			throw new VerificationException(
					"Cannot check condition, this is not a built in opperation: "
							+ operator);
		}
		// no else.

		Object resource;
		resource = condition.getResource().getValue();

		if (!operator.compare(resource, condition.getValue())) {
			throw new VerificationException("Verification failed for " + condition);
		}
		// no else (success).
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

		Object result;

		String typeName;
		typeName = type.toString();

		if (typeName.equals(TYPE_NAME_STRING)) {
			result = name;
		}

		else if (typeName.equals(TYPE_NAME_INT)
				|| typeName.equals(TYPE_NAME_INTEGER)) {

			try {
				result = Integer.parseInt(name);
			}

			catch (NumberFormatException x) {
				throw new ResourceLoaderException(x);
			}
		}

		else if (typeName.equals(TYPE_NAME_DOUBLE)) {

			try {
				result = Double.parseDouble(name);
			}

			catch (NumberFormatException x) {
				throw new ResourceLoaderException(x);
			}
		}

		else if (typeName.equals(TYPE_NAME_BOOLEAN)) {
			result = Boolean.parseBoolean(name);
		}

		else {
			throw new ResourceLoaderException(
					"The basic operations vocabularies does not support the type " + name
							+ ".");
		}
		// end else.

		return result;
	}

	/**
	 * <p>
	 * A helper method to verify values for built-in data types.
	 * </p>
	 * 
	 * @param value
	 *          The value that shall be checked.
	 * @param type
	 *          The type (a {@link URI}) the value belongs to.
	 * @throws VerificationException
	 *           Thrown, if the verification fails.
	 */
	private void checkBuiltInDatatype(Object value, URI type)
			throws VerificationException {

		String typeName;
		typeName = type.toString();

		if (typeName.equals("http://www.w3.org/2001/XMLSchema:string")) {
			ContractLogger.LOGGER
					.warn("oudated data type URI used !!: http://www.w3.org/2001/XMLSchema#string");
		}
		// no else.

		if (typeName.equals(TYPE_NAME_STRING)) {
			return;
		}

		else if (typeName.equals(TYPE_NAME_INT)
				|| typeName.equals(TYPE_NAME_INTEGER)) {

			if (value instanceof Integer) {
				return;
			}

			try {
				Integer.parseInt(value.toString());
			}

			catch (NumberFormatException x) {
				throw new VerificationException(value.toString()
						+ " is not an Integer.");
			}
		}

		else if (typeName.equals(TYPE_NAME_DOUBLE)) {

			if (value instanceof Double) {
				return;
			}

			try {
				Double.parseDouble(value.toString());
			}

			catch (NumberFormatException x) {
				throw new VerificationException(value.toString() + " is not a double");
			}
		}

		else if (typeName.equals(TYPE_NAME_BOOLEAN)) {

			if (value instanceof Boolean) {
				return;
			}

			try {
				Boolean.parseBoolean(value.toString());
			}

			catch (NumberFormatException x) {
				throw new VerificationException(value.toString() + " is not a boolean");
			}
		}

		else {
			throw new VerificationException("Unsupported data type: " + type);
		}
		// end else.
	}
}