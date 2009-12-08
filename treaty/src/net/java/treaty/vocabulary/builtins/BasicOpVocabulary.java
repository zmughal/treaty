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
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.java.treaty.Connector;
import net.java.treaty.ContractLogger;
import net.java.treaty.ContractVocabulary;
import net.java.treaty.ExistsCondition;
import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.ResourceLoaderException;
import net.java.treaty.TreatyException;
import net.java.treaty.VerificationException;

/**
 * <p>
 * Built-in {@link ContractVocabulary} for basic operations.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class BasicOpVocabulary implements ContractVocabulary {

	/** The name space of the {@link BasicOpVocabulary}. */
	public static final String NAME_SPACE_NAME =
			"http://www.w3.org/2001/XMLSchema";

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

	/** The default domain of the {@link BasicOpVocabulary}. */
	private URI default_domain;

	/** The default range of the {@link BasicOpVocabulary}. */
	private URI default_range;

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
	 * @see net.java.treaty.ContractVocabulary#checkDomain(java.net.URI,
	 * java.net.URI)
	 */
	public boolean checkDomain(URI relationshipOrProperty, URI domain)
			throws TreatyException {

		/* It does not matter if it is defined here. */
		return this.definesProperty(relationshipOrProperty);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#checkRange(java.net.URI,
	 * java.net.URI)
	 */
	public boolean checkRange(URI relationshipOrProperty, URI range)
			throws TreatyException {

		/* It does not matter if it is defined here. */
		return this.definesProperty(relationshipOrProperty);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getDomain(java.net.URI)
	 */
	public URI getDomain(URI relationshipOrProperty) throws TreatyException {

		if (this.default_domain == null) {

			try {
				/* Everything goes, types are not enforced for built-in ops. */
				this.default_domain = new URI("http://www.w3.org/2002/07/owl#");
			} catch (URISyntaxException e) {
				throw new TreatyException();
			}
			// end catch.
		}
		// no else.

		return this.default_domain;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getProperties()
	 */
	public Set<URI> getProperties() throws TreatyException {

		return BuiltInOperators.INSTANCE.getOpIds();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getRange(java.net.URI)
	 */
	public URI getRange(URI relationshipOrProperty) throws TreatyException {

		if (this.default_range == null) {

			try {
				/* Everything goes, types are not enforced for built-in ops. */
				this.default_range = new URI("http://www.w3.org/2002/07/owl#");
			}

			catch (URISyntaxException e) {
				throw new TreatyException();
			}
			// end catch.
		}
		// no else.

		return this.default_range;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getRelationships()
	 */
	public Set<URI> getRelationships() throws TreatyException {

		return Collections.emptySet();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getSubProperties(java.net.URI)
	 */
	public Set<URI> getSubProperties(URI relationshipOrProperty)
			throws TreatyException {

		return Collections.emptySet();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getSubtypes(java.net.URI)
	 */
	public Set<URI> getSubTypes(URI type) throws TreatyException {

		Set<URI> result;
		result = new HashSet<URI>();

		String name;
		name = type.toString();

		if (name.equals(TYPE_NAME_DOUBLE)) {

			try {
				result.add(new URI(TYPE_NAME_INT));
				result.add(new URI(TYPE_NAME_INTEGER));
			}

			catch (URISyntaxException e) {
				ContractLogger.LOGGER.error(
						"Error during returning super types of type in BasicOpVocabulary.",
						e);
			}
			// end catch.
		}
		// no else.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getSuperProperties(java.net.URI)
	 */
	public Set<URI> getSuperProperties(URI relationshipOrProperty)
			throws TreatyException {

		return Collections.emptySet();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getSupertypes(java.net.URI)
	 */
	public Set<URI> getSuperTypes(URI type) throws TreatyException {

		Set<URI> result;
		result = new HashSet<URI>();

		String name;
		name = type.toString();

		if (name.equals(TYPE_NAME_INT) || name.equals(TYPE_NAME_INTEGER)) {

			try {
				result.add(new URI(TYPE_NAME_DOUBLE));
			}

			catch (URISyntaxException e) {
				ContractLogger.LOGGER.error(
						"Error during returning super types of type in BasicOpVocabulary.",
						e);
			}
			// end catch.
		}
		// no else.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getTypes()
	 */
	public Set<URI> getTypes() throws TreatyException {

		Set<URI> result;

		result = new HashSet<URI>();

		try {
			result.add(new URI(TYPE_NAME_BOOLEAN));
			result.add(new URI(TYPE_NAME_DOUBLE));
			result.add(new URI(TYPE_NAME_INT));
			result.add(new URI(TYPE_NAME_INTEGER));
			result.add(new URI(TYPE_NAME_STRING));
		}

		catch (URISyntaxException e) {
			ContractLogger.LOGGER.error(
					"Error during returning types of BasicOpVocabulary.", e);
		}

		return result;
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

	/**
	 * <p>
	 * Checks, whether or not the {@link BasicOpVocabulary} defines a built-in
	 * operator for the given {@link URI}.
	 * </p>
	 * 
	 * @param uri
	 *          The {@link URI} for that a built-in operator shall be found.
	 */
	private boolean definesProperty(URI uri) {

		return BuiltInOperators.INSTANCE.getInstance(uri) != null;
	}
}