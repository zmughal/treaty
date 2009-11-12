/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.vocabulary.xml;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;

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

import org.iso_relax.verifier.Verifier;
import org.iso_relax.verifier.VerifierFactory;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.sun.msv.driver.textui.DebugController;
import com.sun.msv.reader.dtd.DTDReader;

/**
 * Contributes the XML vocabulary. Warning: this is not complete, support for
 * DTD,XPATH and XSLT is missing. TODO
 * 
 * @author Jens Dietrich
 * @deprecated The {@link XMLVocabulary} plug-in has been deprecated since the
 *             Treaty core now supports a built-in XML vocabulary itself.
 */
@Deprecated
public class XMLVocabulary extends ContractOntology implements
		ContractVocabulary {

	public static final String NS = "http://www.treaty.org/xml#";
	// types
	public static final String XML_INSTANCE = NS + "XMLInstance";
	public static final String SCHEMA = NS + "XMLSchema";
	public static final String DTD = NS + "DTD";
	// relationships
	public static final String INSTANTIATES = NS + "instantiates";
	public static final String INSTANTIATES_DTD = NS + "instantiatesDTD";

	/**
	 * The {@link Resource} location of the ontology file of the
	 * {@link OCLVocabulary}.
	 */
	private static final String RESOURCE_ONTOLOGY = "./vocabulary/xml.owl";

	/** The {@link OntModel} belonging to the {@link OCLVocabulary}. */
	private OntModel myOntology = null;

	class Controller extends DebugController {

		Controller() {

			super(false);
		}

		private Exception exception = null;
		private String exceptionMessage = null;

		public String getExceptionMessage() {

			return exceptionMessage;
		}

		public Exception getException() {

			return exception;
		}

		@Override
		public void error(Locator[] l, String s, Exception x) {

			super.error(l, s, x);
			// only record first exception
			if (exception == null) {
				exception = x;
			}
			if (exceptionMessage == null) {
				exceptionMessage = s;
			}
		}

		@Override
		public void warning(Locator[] l, String s) {

			super.warning(l, s);
		}

	};

	/**
	 * <p>
	 * Creates a new {@link XMLVocabulary}.
	 * </p>
	 */
	public XMLVocabulary() {

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
		if (INSTANTIATES.equals(rel)) {
			try {
				URL schemaURL = (URL) res2.getValue();
				URL instanceURL = (URL) res1.getValue();
				SchemaFactory factory =
						SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
				if (schemaURL == null) {
					throw new VerificationException(
							"Cannot validate XML instance against schema - schema URL is null for resource "
									+ res2.getName());
				}
				else if (instanceURL == null) {
					throw new VerificationException(
							"Cannot validate XML instance against schema - instance URL is null for resource "
									+ res1.getName());
				}
				else if (factory == null) {
					throw new VerificationException(
							"Cannot validate XML instance against schema - cannot load XML Schema factory");
				}
				else {
					Schema schema = factory.newSchema(schemaURL);
					javax.xml.validation.Validator validator = schema.newValidator();
					InputStream in = instanceURL.openStream();
					Source source = new StreamSource(in);
					validator.validate(source);
					try {
						in.close();
					} catch (Exception x) {
					}
				}
			} catch (Exception x) {
				Logger.info("XML validation against XMLSchema has failed", x);
				throw new VerificationException(
						"Validation of the XML document against XMLSchema failed", x);
			}
		}
		else if (INSTANTIATES_DTD.equals(rel)) {
			try {
				URL schemaURL = (URL) res2.getValue();
				URL instanceURL = (URL) res1.getValue();
				VerifierFactory factory =
						VerifierFactory.newInstance("http://www.w3.org/XML/1998/namespace");
				org.iso_relax.verifier.Schema schema =
						factory.compileSchema(schemaURL.toString());
				Verifier verifier = schema.newVerifier();

				if (!verifier.verify(instanceURL.toString())) {
					throw new VerificationException(
							"Validation of the XML document against DTD failed");
				}
			} catch (Exception x) {
				Logger.info("XML validation against DTD has failed", x);
				throw new VerificationException(
						"Validation of the XML document against DTD failed", x);
			}
		}
		else
			throw new VerificationException("predicate not supported + " + rel);
	}

	public Object load(URI type, String name, Connector connector)
			throws ResourceLoaderException {

		if (!type.toString().startsWith(NS))
			throw new ResourceLoaderException(
					"This plugin cannot be used to instantiate resources of this type: "
							+ type);
		try {
			Object value = connector.getOwner().getResource(name);
			Logger.info("loaded resource " + name + " : " + value + " from plugin "
					+ connector.getOwner().getId());
			return value;
		} catch (Exception x) {
			throw new ResourceLoaderException("Cannot locate xml resource: " + name,
					x);
		}
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
		URL url = (URL) resource.getValue();
		if (SCHEMA.equals(resource.getType().toString())) {
			SchemaFactory factory =
					SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			try {
				factory.newSchema(url);
			} catch (Exception x) {
				throw new VerificationException("The schema " + url
						+ " cannot be parsed", x);
			}
		}
		else if (DTD.equals(resource.getType().toString())) {
			Controller controller = new Controller();
			DTDReader.parse(new InputSource(url.toString()), controller);
			Exception x = controller.getException();
			String msg = controller.getExceptionMessage();
			if (x != null) {
				throw new VerificationException("The dtd " + url + " cannot be parsed",
						x);
			}
			else if (msg != null) {
				throw new VerificationException("The dtd " + url
						+ " cannot be parsed, details: " + msg);
			}
		}
		else if (XML_INSTANCE.equals(resource.getType().toString())) {
			try {
				DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
						url.openStream());
			} catch (Exception x) {
				throw new VerificationException("The xml document " + url
						+ " cannot be parsed", x);
			}
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
}