/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.vocabulary.builtins.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import net.java.treaty.Connector;
import net.java.treaty.ContractVocabulary;
import net.java.treaty.ExistsCondition;
import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.ResourceLoaderException;
import net.java.treaty.VerificationException;
import net.java.treaty.vocabulary.ContractOntology;
import net.java.treaty.vocabulary.builtins.java.JavaVocabulary;

import org.iso_relax.verifier.Verifier;
import org.iso_relax.verifier.VerifierFactory;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.sun.msv.reader.GrammarReaderController;
import com.sun.msv.reader.dtd.DTDReader;

/**
 * <p>
 * Contributes the XML vocabulary. TODO Warning: this is not complete, support
 * for DTD,XPATH and XSLT is missing.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class XMLVocabulary extends ContractOntology implements
		ContractVocabulary {

	/**
	 * <p>
	 * Private helper class that implements an {@link GrammarReaderController}.
	 * </p>
	 * 
	 * @author Jens Dietrich
	 */
	private class Controller implements GrammarReaderController {
	
		/**
		 * Indicates if this {@link Controller} has had {@link Exception}s or
		 * messages.
		 */
		private boolean hadException = false;
	
		/** The Exceptions of this {@link Controller}. */
		private List<Exception> myExceptions = new ArrayList<Exception>();
	
		/** The messages of this {@link Controller}. */
		private List<String> myMessages = new ArrayList<String>();
	
		/**
		 * <p>
		 * Creates a new {@link Controller}.
		 * </p>
		 */
		public Controller() {
	
			super();
		}
	
		/*
		 * (non-Javadoc)
		 * @see
		 * com.sun.msv.reader.GrammarReaderController#error(org.xml.sax.Locator[],
		 * java.lang.String, java.lang.Exception)
		 */
		public void error(Locator[] l, String msg, Exception exception) {
	
			if (exception != null) {
				this.myExceptions.add(exception);
			}
			// no else.
	
			if (msg != null) {
				this.myMessages.add(msg);
			}
			// no else.
	
			this.hadException = true;
		}
	
		/*
		 * (non-Javadoc)
		 * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String,
		 * java.lang.String)
		 */
		public InputSource resolveEntity(String arg0, String arg1)
				throws SAXException, IOException {
	
			/* Do nothing here. */
			return null;
		}
	
		/*
		 * (non-Javadoc)
		 * @see
		 * com.sun.msv.reader.GrammarReaderController#warning(org.xml.sax.Locator[],
		 * java.lang.String)
		 */
		public void warning(Locator[] arg0, String arg1) {
	
			/* Do nothing here. */
		}
	
		/**
		 * <p>
		 * Returns the first Error message of this {@link Controller} if any.
		 * </p>
		 * 
		 * @return The first Error message of this {@link Controller} if any else
		 *         <code>null</code>.
		 */
		public String getFirstErrorMessage() {
	
			String result;
	
			if (this.myExceptions.size() > 0) {
				result = this.myExceptions.get(0).getMessage();
			}
	
			else if (this.myMessages.size() > 0) {
				result = this.myMessages.get(0);
			}
	
			else if (this.hadException) {
				result = "";
			}
	
			else {
				result = null;
			}
	
			return result;
		}
	}

	/** The name space of the {@link XMLVocabulary}. */
	public static final String NAMESPACE_NAME = "http://www.treaty.org/xml#";

	/** The name of the Relationship type instantiates for DTDs. */
	public static final String RELATIONSHIP_NAME_INSTANTIATES_DTD =
			NAMESPACE_NAME + "instantiatesDTD";

	/** The name of the Relationship type instantiates for XML Schemas. */
	public static final String RELATIONSHIP_NAME_INSTANTIATES_XML_SCHEMA =
			NAMESPACE_NAME + "instantiates";

	/** The name of the XML Instance type. */
	public static final String TYPE_NAME_XML_INSTANCE =
			NAMESPACE_NAME + "XMLInstance";

	/** The name of the XML Schema type. */
	public static final String TYPE_NAME_XML_SCHEMA =
			NAMESPACE_NAME + "XMLSchema";

	/** The name of the DTD type. */
	public static final String TYPE_NAME_DTD = NAMESPACE_NAME + "DTD";

	/** The Location of the ontology of the {@link XMLVocabulary}. */
	private static final String ONTOLOGY_LOCATION =
			"/net/java/treaty/vocabulary/builtins/xml/xml.owl";

	/** The {@link OntModel} of the {@link XMLVocabulary}. */
	private OntModel myOntology = null;

	/**
	 * <p>
	 * Creates a new {@link XMLVocabulary}.
	 * </p>
	 */
	public XMLVocabulary() {

		super();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.ExistsCondition)
	 */
	public void check(ExistsCondition condition) throws VerificationException {

		Resource resource;
		resource = condition.getResource();

		assert resource.isInstantiated();
		assert resource.isLoaded();

		URL resourceLocation;
		resourceLocation = (URL) resource.getValue();

		/* Probably check an XML Schema type. */
		if (TYPE_NAME_XML_SCHEMA.equals(resource.getType().toString())) {

			SchemaFactory schemaFactory;
			schemaFactory =
					SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

			try {
				schemaFactory.newSchema(resourceLocation);
			}
			// end try.

			catch (Exception x) {
				throw new VerificationException("The schema " + resourceLocation
						+ " cannot be parsed", x);
			}
			// end catch.
		}

		/* Else probably check a DTD Type. */
		else if (TYPE_NAME_DTD.equals(resource.getType().toString())) {

			Controller controller;
			controller = new Controller();

			DTDReader.parse(new InputSource(resourceLocation.toString()), controller);

			String errorMessage;
			errorMessage = controller.getFirstErrorMessage();

			if (errorMessage != null) {
				throw new VerificationException("The dtd " + resourceLocation
						+ " cannot be parsed: " + errorMessage);
			}
			// no else.
		}

		/* Else probably check an XML Instance type. */
		else if (TYPE_NAME_XML_INSTANCE.equals(resource.getType().toString())) {

			try {
				DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
						resourceLocation.openStream());
			}
			// end try.

			catch (Exception x) {
				throw new VerificationException("The xml document " + resourceLocation
						+ " cannot be parsed", x);
			}
			// end catch.
		}

		/* Else throw an exception. */
		else {
			throw new VerificationException(
					"Unknown type of Resource for Existconditon: " + resource);
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
				"This vocabulary does not define property conditions");
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.RelationshipCondition)
	 */
	public void check(RelationshipCondition condition)
			throws VerificationException {

		String reletionshipName;
		reletionshipName = condition.getRelationship().toString();

		Resource resource1;
		resource1 = condition.getResource1();

		Resource resource2;
		resource2 = condition.getResource2();

		assert resource1.isInstantiated();
		assert resource1.isLoaded();
		assert resource2.isInstantiated();
		assert resource2.isLoaded();

		/* Probably check instance of XML Schema relationship. */
		if (RELATIONSHIP_NAME_INSTANTIATES_XML_SCHEMA.equals(reletionshipName)) {

			/* Try to check the relationship. */
			try {
				URL schemaURL;
				schemaURL = (URL) resource2.getValue();

				URL instanceURL;
				instanceURL = (URL) resource1.getValue();

				SchemaFactory schemaFactory;
				schemaFactory =
						SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

				if (schemaURL == null) {
					throw new VerificationException(
							"Cannot validate XML instance against schema - schema URL is null for resource "
									+ resource2.getName());
				}

				else if (instanceURL == null) {
					throw new VerificationException(
							"Cannot validate XML instance against schema - instance URL is null for resource "
									+ resource1.getName());
				}

				else if (schemaFactory == null) {
					throw new VerificationException(
							"Cannot validate XML instance against schema - cannot load XML Schema factory");
				}

				else {
					Schema schema;
					schema = schemaFactory.newSchema(schemaURL);

					javax.xml.validation.Validator validator;
					validator = schema.newValidator();

					InputStream inputStream;
					inputStream = instanceURL.openStream();

					Source source;
					source = new StreamSource(inputStream);

					validator.validate(source);

					try {
						inputStream.close();
					}

					catch (Exception e) {
						/* Do nothing here. */
					}
					// end catch.
				}
				// end else.
			}
			// end try.

			catch (Exception x) {
				throw new VerificationException(
						"Validation of the XML document against XMLSchema failed", x);
			}
			// end catch.
		}

		/* Else probably check an instance of DTD Relationship. */
		else if (RELATIONSHIP_NAME_INSTANTIATES_DTD.equals(reletionshipName)) {

			/* Try to check the relationship. */
			try {
				URL dtdURL;
				dtdURL = (URL) resource2.getValue();

				URL instanceURL;
				instanceURL = (URL) resource1.getValue();

				VerifierFactory verifierFactory;
				verifierFactory =
						VerifierFactory.newInstance("http://www.w3.org/XML/1998/namespace");

				org.iso_relax.verifier.Schema schema;
				schema = verifierFactory.compileSchema(dtdURL.toString());

				Verifier verifier;
				verifier = schema.newVerifier();

				if (!verifier.verify(instanceURL.toString())) {
					throw new VerificationException(
							"Validation of the XML document against DTD failed");
				}
				// no else.
			}
			// end try.

			catch (Exception x) {
				throw new VerificationException(
						"Validation of the XML document against DTD failed", x);
			}
			// end catch.
		}

		/* Else throw an exception. */
		else {
			throw new VerificationException("predicate not supported + "
					+ reletionshipName);
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
		// no else.

		return this.myOntology;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ResourceLoader#load(java.net.URI, java.lang.String,
	 * net.java.treaty.Connector)
	 */
	public Object load(URI type, String name, Connector connector)
			throws ResourceLoaderException {

		/* Check for the right name space. */
		if (!type.toString().startsWith(NAMESPACE_NAME)) {

			throw new ResourceLoaderException(
					"This plugin cannot be used to instantiate resources of this type: "
							+ type);
		}
		// no else.

		Object result;

		/* Try to load the resource. */
		try {
			result = connector.getOwner().getResource(name);
		}
		// end try.

		catch (Exception x) {
			throw new ResourceLoaderException("Cannot locate xml resource: " + name,
					x);
		}
		// end catch.

		return result;
	}
}