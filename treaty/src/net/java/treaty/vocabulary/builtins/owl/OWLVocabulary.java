/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.vocabulary.builtins.owl;

import java.net.URI;
import java.net.URL;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import net.java.treaty.*;
import net.java.treaty.vocabulary.ContractOntology;

/**
 * <p>
 * Contributes the OWL vocabulary.
 * </p>
 * 
 * TODO: separate types for OWLLite, OWL-DL, OWL Full, instantiation
 * relationship.
 * 
 * @author Jens Dietrich
 */
public class OWLVocabulary extends ContractOntology {

	/** The name space of this {@link ContractOntology}. */
	public static final String NAMESPACE_NAME = "http://www.treaty.org/owl#";

	/** The name of the type ontology. */
	public static final String TYPE_NAME_ONTOLOGY = NAMESPACE_NAME + "Ontology";

	/** The location of the ontology belonging to the {@link OWLVocabulary}. */
	private static final String ONTOLOGY_LOCATION =
			"/net/java/treaty/vocabulary/builtins/owl/owl.owl";

	/** The {@link OntModel} of the {@link OWLVocabulary}. */
	private OntModel ontology = null;

	/**
	 * <p>
	 * Creates a new {@link OWLVocabulary}.
	 * </p>
	 */
	public OWLVocabulary() {

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

		/* Check if the given resource is an ontology. */
		if (TYPE_NAME_ONTOLOGY.equals(resource.getType().toString())) {

			/* Get the ontology's location. */
			URL ontologyLocation;
			ontologyLocation = (URL) resource.getValue();

			OntModel model;
			model = ModelFactory.createOntologyModel();

			/* Try to load the ontology. */
			try {
				model.read(ontologyLocation.toString());
			}
			// end try.

			catch (Exception x) {
				throw new VerificationException("The resource " + ontologyLocation
						+ " cannot be parsed as ontology", x);
			}
			// end catch.

			if (model.size() == 0) {
				throw new VerificationException("The resource " + ontologyLocation
						+ " cannot be parsed as ontology - no statements found");
			}
			// no else.
		}

		/* Else throw an exception. */
		else {
			throw new VerificationException("Unknown type of resource: " + resource
					+ ". The resource cannot be verified using the OWLVocabulary.");
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

		throw new VerificationException(
				"This vocabulary does not define relationship conditions");
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.vocabulary.ContractOntology#getOntology()
	 */
	public OntModel getOntology() {

		/* Probably initialize the ontology. */
		if (this.ontology == null) {

			this.ontology = ModelFactory.createOntologyModel();
			this.ontology.read(OWLVocabulary.class.getResource(ONTOLOGY_LOCATION)
					.toString());
		}
		// no else.

		return this.ontology;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ResourceLoader#load(java.net.URI, java.lang.String,
	 * net.java.treaty.Connector)
	 */
	public Object load(URI type, String name, Connector connector)
			throws ResourceLoaderException {

		/*
		 * Type must not be inspected, vocabulary only provides one type. As long as
		 * the name space is okay, the result will be right.
		 */
		if (!type.toString().startsWith(NAMESPACE_NAME)) {
			throw new ResourceLoaderException(
					"This plugin cannot be used to instantiate resources of this type: "
							+ type);
		}
		// no else.

		/* Try to load the resource. */
		try {
			return connector.getOwner().getResource(name);
		}
		// end try.

		catch (Exception e) {
			throw new ResourceLoaderException("Error loading ontology " + name
					+ " from plugin " + connector.getOwner().getId(), e);
		}
		// end catch.
	}
}