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
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import net.java.treaty.ContractVocabulary;
import net.java.treaty.TreatyException;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;

/**
 * <p>
 * Represents {@link ContractVocabulary}s based on a formal ontology (OWL, RDFS
 * or similar). The ontology is represented as a (jena) {@link OntModel}.
 * </p>
 * 
 * @author jens dietrich
 */
public abstract class ContractOntology implements ContractVocabulary {

	/**
	 * <p>
	 * Returns an {@link OntModel} representing this vocabulary. This can be used
	 * for ontology reasoning other than subclass reasoning.
	 * </p>
	 */
	public abstract OntModel getOntology();

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#checkDomain(java.net.URI,
	 * java.net.URI)
	 */
	public boolean checkDomain(URI relationshipOrProperty, URI domain)
			throws TreatyException {

		boolean result;

		OntModel model;
		model = getOntology();

		ObjectProperty property;
		property = model.getObjectProperty(relationshipOrProperty.toString());

		if (property == null) {
			result = false;
		}

		else {
			result = domain.toString().equals(property.getDomain().getURI());
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#checkRange(java.net.URI,
	 * java.net.URI)
	 */
	public boolean checkRange(URI relationshipOrProperty, URI range)
			throws TreatyException {

		boolean result;

		OntModel model;
		model = getOntology();

		ObjectProperty property;
		property = model.getObjectProperty(relationshipOrProperty.toString());

		if (property == null) {
			result = false;
		}

		else {
			result = range.toString().equals(property.getRange().getURI());
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getDomain(java.net.URI)
	 */
	public URI getDomain(URI relationshipOrProperty) throws TreatyException {

		OntModel model;
		model = getOntology();

		ObjectProperty property;
		property = model.getObjectProperty(relationshipOrProperty.toString());

		if (property != null) {

			try {
				return new URI(property.getDomain().getURI());
			}

			catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
			// and catch.
		}
		// no else.

		DatatypeProperty dataProperty =
				model.getDatatypeProperty(relationshipOrProperty.toString());

		if (dataProperty != null) {

			try {
				return new URI(dataProperty.getDomain().getURI());
			}

			catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
			// end catch.
		}
		// no else.

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getProperties()
	 */
	public Collection<URI> getProperties() throws TreatyException {

		Collection<URI> result;
		result = new HashSet<URI>();

		OntModel model;
		model = getOntology();

		for (Iterator<DatatypeProperty> iterator = model.listDatatypeProperties(); iterator
				.hasNext();) {

			try {
				result.add(new URI(iterator.next().getURI()));
			}

			catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
			// end catch.
		}
		// end for.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getRange(java.net.URI)
	 */
	public URI getRange(URI relationshipOrProperty) throws TreatyException {

		OntModel model;
		model = getOntology();

		ObjectProperty property;
		property = model.getObjectProperty(relationshipOrProperty.toString());

		if (property != null) {
			try {
				return new URI(property.getRange().getURI());
			}

			catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
			// end catch.
		}
		// no else.

		DatatypeProperty dataProperty;
		dataProperty = model.getDatatypeProperty(relationshipOrProperty.toString());

		if (dataProperty != null) {
			try {
				return new URI(dataProperty.getRange().getURI());
			}

			catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
			// end catch.
		}
		// no else.

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getRelationships()
	 */
	public Collection<URI> getRelationships() throws TreatyException {

		Collection<URI> result;
		result = new HashSet<URI>();

		OntModel model;
		model = getOntology();

		for (Iterator<ObjectProperty> iterator = model.listObjectProperties(); iterator
				.hasNext();) {

			try {
				result.add(new URI(iterator.next().getURI()));
			}

			catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
			// end catch.
		}
		// end for.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getSubProperties(java.net.URI)
	 */
	public Collection<URI> getSubProperties(URI relationshipOrProperty)
			throws TreatyException {

		Collection<URI> result;
		result = new HashSet<URI>();

		OntModel model;
		model = getOntology();

		OntProperty property;
		property = model.getOntProperty(relationshipOrProperty.toString());

		for (Iterator<? extends OntProperty> iterator =
				property.listSubProperties(false); iterator.hasNext();) {

			try {
				URI next;
				next = new URI(iterator.next().getURI());

				/* It seems that jena returns the URI itself. */
				if (!relationshipOrProperty.equals(next)) {
					result.add(next);
				}
				// no else.
			}

			catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
			// end catch.
		}
		// end for.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getSubtypes(java.net.URI)
	 */
	public Collection<URI> getSubtypes(URI type) throws TreatyException {

		Collection<URI> result;
		result = new HashSet<URI>();

		OntModel model;
		model = getOntology();

		OntClass clazz;
		clazz = model.getOntClass(type.toString());

		for (Iterator<OntClass> iterator = clazz.listSubClasses(false); iterator
				.hasNext();) {

			try {
				result.add(new URI(iterator.next().getURI()));
			}

			catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
			// end catch.
		}
		// end for.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getSuperProperties(java.net.URI)
	 */
	public Collection<URI> getSuperProperties(URI relationshipOrProperty)
			throws TreatyException {

		Collection<URI> result;
		result = new HashSet<URI>();

		OntModel model;
		model = getOntology();

		OntProperty property;
		property = model.getOntProperty(relationshipOrProperty.toString());

		for (Iterator<? extends OntProperty> iterator =
				property.listSuperProperties(false); iterator.hasNext();) {

			try {
				result.add(new URI(iterator.next().getURI()));
			}

			catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
			// end catch.
		}
		// end for.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getSupertypes(java.net.URI)
	 */
	public Collection<URI> getSupertypes(URI type) throws TreatyException {

		Collection<URI> result;
		result = new HashSet<URI>();

		OntModel model;
		model = getOntology();

		OntClass clazz;
		clazz = model.getOntClass(type.toString());

		for (Iterator<OntClass> iterator = clazz.listSuperClasses(false); iterator
				.hasNext();) {

			try {
				result.add(new URI(iterator.next().getURI()));
			}

			catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
			// end catch.
		}
		// end for.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getTypes()
	 */
	public Collection<URI> getTypes() throws TreatyException {

		Collection<URI> result;
		result = new HashSet<URI>();

		OntModel model;
		model = getOntology();

		for (Iterator<OntClass> iterator = model.listClasses(); iterator.hasNext();) {

			try {
				result.add(new URI(iterator.next().getURI()));
			} catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
			// end catch.
		}
		// end for.

		return result;
	}
}