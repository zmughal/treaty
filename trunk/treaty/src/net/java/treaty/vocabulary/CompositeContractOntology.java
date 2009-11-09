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
import java.util.List;
import net.java.treaty.Connector;
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
 * Supports vocabulary composition. Each vocabulary contribution (=part) defines
 * types, relationships and methods, and an ontology is used to build a model
 * suitable for inference for these parts. add and remove methods can be used to
 * modify the methods. Careful: methods are not synchronised, this is the
 * responsibility of client apps. While it would make sense to synchronize
 * methods like add and remove, it is desirable that the check methods can run
 * in multiple threads.
 * 
 * @author jens dietrich
 */

public class CompositeContractOntology extends ContractOntology {

	public static final String OWNER = "http://www.treaty.org/owner";
	
	private List<ContractVocabulary> vocabularyContributions =
			new ArrayList<ContractVocabulary>();

	private OntModel ontology = ModelFactory.createOntologyModel();;

	public void add(ContractVocabulary voc,String owner) throws TreatyException {
		// check whether types or predicates are defined twice
		for (URI uri : voc.getTypes()) {
			for (ContractVocabulary part : vocabularyContributions) {
				if (part.getTypes().contains(uri)) {
					reportDuplicateDef("type", uri, voc, part);
				}
			}
		}
		for (URI uri : voc.getProperties()) {
			for (ContractVocabulary part : vocabularyContributions) {
				if (part.getProperties().contains(uri)) {
					reportDuplicateDef("property", uri, voc, part);
				}
			}
		}
		for (URI uri : voc.getRelationships()) {
			for (ContractVocabulary part : vocabularyContributions) {
				if (part.getRelationships().contains(uri)) {
					reportDuplicateDef("relationship", uri, voc, part);
				}
			}
		}

		this.vocabularyContributions.add(voc);
		
		// we accept non ontologies here to support "lightweight" plugins
		// TODO log warning
		if (voc instanceof ContractOntology) {
			ContractOntology ont = (ContractOntology)voc;
			this.ontology.addSubModel(ont.getOntology());
			
			// add annotations to ontology
			AnnotationProperty ann = ont.getOntology().createAnnotationProperty(OWNER);
			addOwnerAnnotation(ont.getOntology(),ann,ont.getTypes(),owner);
			addOwnerAnnotation(ont.getOntology(),ann,ont.getRelationships(),owner);
			addOwnerAnnotation(ont.getOntology(),ann,ont.getProperties(),owner);
		}
	}
	
	private void addOwnerAnnotation(OntModel model,AnnotationProperty ann,Collection<URI> resources,String owner) {
		for (URI uri : resources) {
			model.add(model.getResource(uri.toString()),ann,owner);
		}		
	}

	// can be overridden, e.g. just logging warning might be enough
	protected void reportDuplicateDef(String kind, URI resource,
			ContractVocabulary voc, ContractVocabulary part) throws TreatyException {

		StringBuffer b =
				new StringBuffer().append("Attempt to redefine ").append(kind).append(
						" ").append(resource).append(" in ").append(voc).append(
						" - this is already defined in ").append(part);
		throw new TreatyException(b.toString());
	}

	public boolean remove(ContractOntology voc) throws TreatyException {

		boolean result = this.vocabularyContributions.remove(voc);
		
		// we accept non ontologies here to support "lightweight" plugins
		// TODO log warning
		if (voc instanceof ContractOntology) {
			this.ontology.removeSubModel(((ContractOntology)voc).getOntology());
		}

		return result;
	}

	public List<ContractVocabulary> getVocabularyContributions() {

		return vocabularyContributions;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.RelationshipCondition)
	 */
	public void check(RelationshipCondition condition)
			throws VerificationException {

		ContractVocabulary voc = null;
		voc = this.findVocabularyForRelationship(condition.getRelationship());
		voc.check(condition);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.PropertyCondition)
	 */
	public void check(PropertyCondition condition) throws VerificationException {

		ContractVocabulary voc = null;
		voc = this.findVocabularyForProperty(condition.getOperator());
		voc.check(condition);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.ExistsCondition)
	 */
	public void check(ExistsCondition condition) throws VerificationException {

		ContractVocabulary voc = null;
		synchronized (this) {
			voc = this.findVocabularyForType(condition.getResource().getType());
		}
		voc.check(condition);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ResourceLoader#load(java.net.URI, java.lang.String,
	 * net.java.treaty.Connector)
	 */
	public Object load(URI type, String name, Connector connector)
			throws ResourceLoaderException {

		for (ContractVocabulary voc : vocabularyContributions) {
			try {
				if (voc.getTypes().contains(type)) {
					return voc.load(type, name, connector); // this may throw an exception
				}
			} catch (TreatyException e) {
				throw new ResourceLoaderException(e);
			}
		}
		throw new ResourceLoaderException(
				"No vocabulary found to load resource of type " + type);
	}

	private ContractVocabulary findVocabularyForProperty(URI uri)
			throws VerificationException {

		for (ContractVocabulary voc : vocabularyContributions) {
			try {
				if (voc.getProperties().contains(uri)) {
					return voc;
				}
			} catch (TreatyException e) {
				throw new VerificationException(e);
			}
		}
		throw new VerificationException(
				"No vocabulary found to check condition with property " + uri);
	}

	private ContractVocabulary findVocabularyForRelationship(URI uri)
			throws VerificationException {

		for (ContractVocabulary voc : vocabularyContributions) {
			try {
				if (voc.getRelationships().contains(uri)) {
					return voc;
				}
			} catch (TreatyException e) {
				throw new VerificationException(e);
			}
		}
		throw new VerificationException(
				"No vocabulary found to check condition with relationship " + uri);
	}

	private ContractVocabulary findVocabularyForType(URI uri)
			throws VerificationException {

		for (ContractVocabulary voc : vocabularyContributions) {
			try {
				if (voc.getTypes().contains(uri)) {
					return voc;
				}
			} catch (TreatyException e) {
				throw new VerificationException(e);
			}
		}
		throw new VerificationException("No vocabulary found for type " + uri);
	}

	@Override
	public OntModel getOntology() {

		return ontology;
	}
	/**
	 * Get the owner annotation of a types, property or relationship.
	 * @param resource
	 * @return
	 */
	public String getOwnerAnnotation(URI resource) {
		AnnotationProperty ann = ontology.getAnnotationProperty(CompositeContractOntology.OWNER);
		if (ann==null) return null;
		Resource r = ontology.getResource("http://www.treaty.org/java#InstantiableClass");
		if (r==null) return null;
		Statement s = r.getProperty(ann);
		if (s==null) return null;
		String t = s.getString();
		return t;
	}
}
