/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.vocabulary;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.java.treaty.Connector;
import net.java.treaty.ContractLogger;
import net.java.treaty.ContractVocabulary;
import net.java.treaty.ExistsCondition;
import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.ResourceLoaderException;
import net.java.treaty.TreatyException;
import net.java.treaty.VerificationException;

import com.hp.hpl.jena.ontology.AnnotationProperty;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

/**
 * <p>
 * Supports vocabulary composition. Each vocabulary contribution (=part) defines
 * types, relationships and methods, and an ontology is used to build a model
 * suitable for inference for these parts. add and remove methods can be used to
 * modify the methods. Careful: methods are not synchronised, this is the
 * responsibility of client apps. While it would make sense to synchronize
 * methods like add and remove, it is desirable that the check methods can run
 * in multiple threads.
 * </p>
 * 
 * @author jens dietrich
 */
public class CompositeContractOntology extends ContractOntology {

	public static final String OWNER = "http://www.treaty.org/owner";

	/**
	 * The {@link CompositeContractOntologyListener} of this
	 * {@link CompositeContractOntology}.
	 */
	private Set<CompositeContractOntologyListener> myListeners =
			new HashSet<CompositeContractOntologyListener>();

	/** The composite ontology of all added {@link ContractVocabulary}s. */
	private OntModel myOntology = ModelFactory.createOntologyModel();

	/**
	 * All {@link ContractVocabulary}s that are part of the
	 * {@link CompositeContractOntology}.
	 */
	private List<ContractVocabulary> myVocabularyContributions =
			new ArrayList<ContractVocabulary>();

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.ExistsCondition)
	 */
	public void check(ExistsCondition condition) throws VerificationException {

		ContractVocabulary vocabulary;

		synchronized (this) {
			vocabulary =
					this.findVocabularyForType(condition.getResource().getType());
		}
		vocabulary.check(condition);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.PropertyCondition)
	 */
	public void check(PropertyCondition condition) throws VerificationException {

		ContractVocabulary vocabulary;

		vocabulary = this.findVocabularyForProperty(condition.getOperator());
		vocabulary.check(condition);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.RelationshipCondition)
	 */
	public void check(RelationshipCondition condition)
			throws VerificationException {

		ContractVocabulary vocabulary;

		vocabulary =
				this.findVocabularyForRelationship(condition.getRelationship());
		vocabulary.check(condition);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.vocabulary.ContractOntology#getOntology()
	 */
	public OntModel getOntology() {

		return myOntology;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ResourceLoader#load(java.net.URI, java.lang.String,
	 * net.java.treaty.Connector)
	 */
	public Object load(URI type, String name, Connector connector)
			throws ResourceLoaderException {

		/* Try to find a vocabulary that supports the given type. */
		for (ContractVocabulary vocabulary : this.myVocabularyContributions) {

			/* Probably Try to load the type's instance. */
			try {

				if (vocabulary.getTypes().contains(type)) {
					return vocabulary.load(type, name, connector);
				}
				// no else.
			}
			// end try.

			catch (TreatyException e) {
				throw new ResourceLoaderException(e);
			}
		}
		// end for.

		/* If no result has been returned yet, throw an exception. */
		throw new ResourceLoaderException(
				"No vocabulary found to load resource of type " + type);
	}

	/**
	 * <p>
	 * Adds a new {@link ContractVocabulary} to the
	 * {@link CompositeContractOntology}.
	 * </p>
	 * 
	 * @param vocabulary
	 *          The {@link ContractVocabulary} that shall be added.
	 * @param owner
	 *          The owner of this {@link ContractVocabulary}. An unique ID (as a
	 *          {@link String}) should be used here.
	 * @throws TreatyException
	 *           Thrown if adding the {@link ContractVocabulary} fails.
	 */
	public void add(ContractVocabulary vocabulary, String owner)
			throws TreatyException {

		/* Check whether any types or predicates are defined twice. */
		for (URI typeUri : vocabulary.getTypes()) {

			for (ContractVocabulary part : this.myVocabularyContributions) {

				if (part.getTypes().contains(typeUri)) {
					reportDuplicateDef("type", typeUri, vocabulary, part);
				}
				// no else.
			}
			// end for.
		}
		// end for.

		for (URI propertyUri : vocabulary.getProperties()) {

			for (ContractVocabulary part : this.myVocabularyContributions) {

				if (part.getProperties().contains(propertyUri)) {
					reportDuplicateDef("property", propertyUri, vocabulary, part);
				}
				// no else.
			}
			// end for.
		}
		// end for.

		for (URI relationshipUri : vocabulary.getRelationships()) {

			for (ContractVocabulary part : this.myVocabularyContributions) {

				if (part.getRelationships().contains(relationshipUri)) {
					reportDuplicateDef("relationship", relationshipUri, vocabulary, part);
				}
				// no else.
			}
			// end for.
		}
		// end for.

		this.myVocabularyContributions.add(vocabulary);

		/* We accept non ontologies here to support "lightweight" plug-ins. */
		if (vocabulary instanceof ContractOntology) {

			ContractOntology contractOntology;
			contractOntology = (ContractOntology) vocabulary;

			this.myOntology.addSubModel(contractOntology.getOntology());

			/* Add annotations to ontology. */
			AnnotationProperty annotationProperty;
			annotationProperty =
					contractOntology.getOntology().createAnnotationProperty(OWNER);
			this.addOwnerAnnotation(contractOntology.getOntology(),
					annotationProperty, contractOntology.getTypes(), owner);
			this.addOwnerAnnotation(contractOntology.getOntology(),
					annotationProperty, contractOntology.getRelationships(), owner);
			this.addOwnerAnnotation(contractOntology.getOntology(),
					annotationProperty, contractOntology.getProperties(), owner);
		}

		else {
			/* Log a warning. */
			ContractLogger.LOGGER.warn("The vocabulary " + vocabulary
					+ " has been added to the CompositeContractOntology "
					+ "although it is not a ContractOntology.");
		}

		this.notifyListeners();
	}

	/**
	 * <p>
	 * Adds a new {@link CompositeContractOntologyListener} to this
	 * {@link CompositeContractOntology}.
	 * </p>
	 * 
	 * @param listener
	 *          The {@link CompositeContractOntologyListener} that shall be added.
	 */
	public void addListener(CompositeContractOntologyListener listener) {

		this.myListeners.add(listener);
	}

	/**
	 * <p>
	 * Returns the owner annotation of a type, property or relationship.
	 * </p>
	 * 
	 * @param resourceURI
	 *          The the {@link URI} of the resource whose owner annotation shall
	 *          be returned.
	 * @return The annotation of a type, property or relationship.
	 */
	public String getOwnerAnnotation(URI resourceURI) {

		String result;

		AnnotationProperty annotationProperty;
		annotationProperty =
				myOntology.getAnnotationProperty(CompositeContractOntology.OWNER);

		if (annotationProperty == null) {
			result = null;
		}

		else {
			Resource resource;
			resource = myOntology.getResource(resourceURI.toString());

			if (resource == null) {
				result = null;
			}

			else {
				Statement statement;
				statement = resource.getProperty(annotationProperty);

				if (statement == null) {
					result = null;
				}

				else {
					result = statement.getString();
				}
				// end else.
			}
			// end else.
		}
		// end else.

		return result;
	}

	/**
	 * <p>
	 * Returns all {@link ContractVocabulary}s that are part of the
	 * {@link CompositeContractOntology}.
	 * </p>
	 * 
	 * @return All {@link ContractVocabulary}s that are part of the
	 *         {@link CompositeContractOntology}.
	 */
	public List<ContractVocabulary> getVocabularyContributions() {

		return this.myVocabularyContributions;
	}

	/**
	 * <p>
	 * Notifies all {@link CompositeContractOntologyListener}s that the
	 * {@link CompositeContractOntology}'s state has been changed.
	 * </p>
	 */
	public void notifyListeners() {

		for (CompositeContractOntologyListener listener : this.myListeners) {
			listener.update();
		}
		// end for.
	}

	/**
	 * <p>
	 * Removes a {@link ContractVocabulary} from this
	 * {@link CompositeContractOntology}.
	 * </p>
	 * 
	 * @param vocabulary
	 *          The {@link ContractVocabulary} that shall be removed.
	 * @return <code>true</code> if the given {@link ContractVocabulary} has been
	 *         removed.
	 * @throws TreatyException
	 *           Thrown, if the removal fails caused by an {@link Exception}.
	 */
	public boolean remove(ContractVocabulary vocabulary) throws TreatyException {

		boolean result;
		result = this.myVocabularyContributions.remove(vocabulary);

		/* We accept non ontologies here to support "lightweight" plug-ins. */
		if (vocabulary instanceof ContractOntology) {
			this.myOntology.removeSubModel(((ContractOntology) vocabulary)
					.getOntology());
		}

		else {
			/* Log a warning. */
			ContractLogger.LOGGER.warn("The vocabulary " + vocabulary
					+ " has been removed from the CompositeContractOntology "
					+ "although it is not a ContractOntology.");
		}

		this.notifyListeners();

		return result;
	}

	/**
	 * <p>
	 * Removes a {@link CompositeContractOntologyListener} to this
	 * {@link CompositeContractOntology}.
	 * </p>
	 * 
	 * @param listener
	 *          The {@link CompositeContractOntologyListener} that shall be
	 *          removed.
	 */
	public void removeListener(CompositeContractOntologyListener listener) {

		this.myListeners.remove(listener);
	}

	/**
	 * <p>
	 * This method reports that a given resource (as a {@link URI}) already exists
	 * an throws a {@link TreatyException}.
	 * </p>
	 * 
	 * <p>
	 * This method can be overridden, e.g. just logging warning might be enough.
	 * </p>
	 * 
	 * @param kind
	 *          The kind of the duplicate definition.
	 * @param resource
	 *          The resource (as a {@link URI}) of the duplicate definition.
	 * @param vocabulary
	 *          The {@link ContractVocabulary} that contains both definitions.
	 * @param part
	 *          The {@link ContractVocabulary} (part) that provides the duplicate
	 *          definition.
	 * @throws TreatyException
	 *           Thrown to report the duplicate.
	 */
	protected void reportDuplicateDef(String kind, URI resource,
			ContractVocabulary vocabulary, ContractVocabulary part)
			throws TreatyException {

		StringBuffer b =
				new StringBuffer().append("Attempt to redefine ").append(kind).append(
						" ").append(resource).append(" in ").append(vocabulary).append(
						" - this is already defined in ").append(part);
		throw new TreatyException(b.toString());
	}

	/**
	 * <p>
	 * Adds a given {@link AnnotationProperty} to a given {@link Collection} of
	 * resources (as {@link URI}) in a given {@link OntModel}.
	 * </p>
	 * 
	 * @param model
	 *          The {@link OntModel} to that the {@link AnnotationProperty} shall
	 *          be added.
	 * @param annotation
	 *          The {@link AnnotationProperty} that shall be added.
	 * @param resources
	 *          The resources (as {@link URI}s) to that the
	 *          {@link AnnotationProperty} shall bed added.
	 * @param owner
	 *          The owner value that shall be annotated.
	 */
	private void addOwnerAnnotation(OntModel model,
			AnnotationProperty annotation, Collection<URI> resources, String owner) {

		for (URI resourceUri : resources) {
			model.add(model.getResource(resourceUri.toString()), annotation, owner);
		}
		// end for.
	}

	/**
	 * <p>
	 * Tries to find a {@link ContractVocabulary} (part) for a given property (as
	 * a {@link URI}).
	 * </p>
	 * 
	 * @param uri
	 *          The {@link URI} of the property.
	 * @return The found {@link ContractVocabulary} containing the property.
	 * @throws VerificationException
	 *           Thrown, if no {@link ContractVocabulary} containing the property
	 *           can be found.
	 */
	private ContractVocabulary findVocabularyForProperty(URI uri)
			throws VerificationException {

		for (ContractVocabulary vocabulary : this.myVocabularyContributions) {

			try {
				if (vocabulary.getProperties().contains(uri)) {
					return vocabulary;
				}
				// no else.
			}

			catch (TreatyException e) {
				throw new VerificationException(e);
			}
			// end catch.
		}
		// end for.

		/* If no result has been returned yet, throw an exception. */
		throw new VerificationException(
				"No vocabulary found to check condition with property " + uri);
	}

	/**
	 * <p>
	 * Tries to find a {@link ContractVocabulary} (part) for a given relationship
	 * (as a {@link URI}).
	 * </p>
	 * 
	 * @param uri
	 *          The {@link URI} of the relationship.
	 * @return The found {@link ContractVocabulary} containing the relationship.
	 * @throws VerificationException
	 *           Thrown, if no {@link ContractVocabulary} containing the
	 *           relationship can be found.
	 */
	private ContractVocabulary findVocabularyForRelationship(URI uri)
			throws VerificationException {

		for (ContractVocabulary vocabulary : this.myVocabularyContributions) {

			try {

				if (vocabulary.getRelationships().contains(uri)) {
					return vocabulary;
				}
				// no else.
			}

			catch (TreatyException e) {
				throw new VerificationException(e);
			}
			// end catch.
		}
		// end for.

		/* If no result has been returned yet, throw an exception. */
		throw new VerificationException(
				"No vocabulary found to check condition with relationship " + uri);
	}

	/**
	 * <p>
	 * Tries to find a {@link ContractVocabulary} (part) for a given type (as a
	 * {@link URI}).
	 * </p>
	 * 
	 * @param uri
	 *          The {@link URI} of the type.
	 * @return The found {@link ContractVocabulary} containing the type.
	 * @throws VerificationException
	 *           Thrown, if no {@link ContractVocabulary} containing the type can
	 *           be found.
	 */
	private ContractVocabulary findVocabularyForType(URI uri)
			throws VerificationException {

		for (ContractVocabulary vocabulary : this.myVocabularyContributions) {

			try {

				if (vocabulary.getTypes().contains(uri)) {
					return vocabulary;
				}
				// no else.
			}

			catch (TreatyException e) {
				throw new VerificationException(e);
			}
			// end catch.
		}
		// end for.

		/* If no result has been returned yet, throw an exception. */
		throw new VerificationException("No vocabulary found for type " + uri);
	}
}