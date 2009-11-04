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
 * Contract vocabulary based on a formal ontology (OWL, RDFS or similar). The
 * ontology is represented as (jena) OntModel.
 * 
 * @author jens dietrich
 */

public abstract class ContractOntology implements ContractVocabulary {

	/**
	 * Get an ontology representing this vocabulary. This can be used for ontology
	 * reasoning other than subclass reasoning.
	 */
	public abstract OntModel getOntology();

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#checkDomain(java.net.URI,
	 * java.net.URI)
	 */
	public boolean checkDomain(URI relationshipOrProperty, URI domain)
			throws TreatyException {

		OntModel model = getOntology();
		ObjectProperty prop =
				model.getObjectProperty(relationshipOrProperty.toString());
		if (prop == null)
			return false;
		return domain.toString().equals(prop.getDomain().getURI());
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#checkRange(java.net.URI,
	 * java.net.URI)
	 */
	public boolean checkRange(URI relationshipOrProperty, URI range)
			throws TreatyException {

		OntModel model = getOntology();
		ObjectProperty prop =
				model.getObjectProperty(relationshipOrProperty.toString());
		if (prop == null)
			return false;
		return range.toString().equals(prop.getRange().getURI());
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getDomain(java.net.URI)
	 */
	public URI getDomain(URI relationshipOrProperty) throws TreatyException {

		OntModel model = getOntology();
		ObjectProperty prop =
				model.getObjectProperty(relationshipOrProperty.toString());
		if (prop != null)
			try {
				return new URI(prop.getDomain().getURI());
			} catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
		DatatypeProperty dprop =
				model.getDatatypeProperty(relationshipOrProperty.toString());
		if (dprop != null)
			try {
				return new URI(dprop.getDomain().getURI());
			} catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getProperties()
	 */
	public Collection<URI> getProperties() throws TreatyException {

		OntModel model = getOntology();
		Collection<URI> props = new HashSet<URI>();
		for (Iterator<DatatypeProperty> iter = model.listDatatypeProperties(); iter
				.hasNext();) {
			try {
				props.add(new URI(iter.next().getURI()));
			} catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
		}
		return props;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getRange(java.net.URI)
	 */
	public URI getRange(URI relationshipOrProperty) throws TreatyException {

		OntModel model = getOntology();
		ObjectProperty prop =
				model.getObjectProperty(relationshipOrProperty.toString());
		if (prop != null)
			try {
				return new URI(prop.getRange().getURI());
			} catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
		DatatypeProperty dprop =
				model.getDatatypeProperty(relationshipOrProperty.toString());
		if (dprop != null)
			try {
				return new URI(dprop.getRange().getURI());
			} catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getRelationships()
	 */
	public Collection<URI> getRelationships() throws TreatyException {

		OntModel model = getOntology();
		Collection<URI> props = new HashSet<URI>();
		for (Iterator<ObjectProperty> iter = model.listObjectProperties(); iter
				.hasNext();) {
			try {
				props.add(new URI(iter.next().getURI()));
			} catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
		}
		return props;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getSubProperties(java.net.URI)
	 */
	public Collection<URI> getSubProperties(URI relationshipOrProperty)
			throws TreatyException {

		OntModel model = getOntology();
		OntProperty prop = model.getOntProperty(relationshipOrProperty.toString());
		Collection<URI> props = new HashSet<URI>();
		for (Iterator<? extends OntProperty> iter = prop.listSubProperties(false); iter
				.hasNext();) {
			try {
				props.add(new URI(iter.next().getURI()));
			} catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
		}
		return props;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getSubtypes(java.net.URI)
	 */
	public Collection<URI> getSubtypes(URI type) throws TreatyException {

		OntModel model = getOntology();
		OntClass clazz = model.getOntClass(type.toString());
		Collection<URI> clazzes = new HashSet<URI>();
		for (Iterator<OntClass> iter = clazz.listSubClasses(false); iter.hasNext();) {
			try {
				clazzes.add(new URI(iter.next().getURI()));
			} catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
		}
		return clazzes;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getSuperProperties(java.net.URI)
	 */
	public Collection<URI> getSuperProperties(URI relationshipOrProperty)
			throws TreatyException {

		OntModel model = getOntology();
		OntProperty prop = model.getOntProperty(relationshipOrProperty.toString());
		Collection<URI> props = new HashSet<URI>();
		for (Iterator<? extends OntProperty> iter = prop.listSuperProperties(false); iter
				.hasNext();) {
			try {
				props.add(new URI(iter.next().getURI()));
			} catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
		}
		return props;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getSupertypes(java.net.URI)
	 */
	public Collection<URI> getSupertypes(URI type) throws TreatyException {

		OntModel model = getOntology();
		OntClass clazz = model.getOntClass(type.toString());
		Collection<URI> clazzes = new HashSet<URI>();
		for (Iterator<OntClass> iter = clazz.listSuperClasses(false); iter
				.hasNext();) {
			try {
				clazzes.add(new URI(iter.next().getURI()));
			} catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
		}
		return clazzes;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVocabulary#getTypes()
	 */
	public Collection<URI> getTypes() throws TreatyException {

		OntModel model = getOntology();
		Collection<URI> classes = new HashSet<URI>();
		for (Iterator<OntClass> iter = model.listClasses(); iter.hasNext();) {
			try {
				classes.add(new URI(iter.next().getURI()));
			} catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
		}
		return classes;
	}
}