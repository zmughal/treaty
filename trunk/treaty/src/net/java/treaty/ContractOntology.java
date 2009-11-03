/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty;

import java.net.URI;
import java.util.Collection;

import com.hp.hpl.jena.ontology.OntModel;

/**
 * Contract vocabulary based on a formal ontology (OWL, RDFS or similar).
 * The ontology is represented as (jena) OntModel.
 * @author jens dietrich
 */

public abstract class ContractOntology implements ContractVocabulary {

	/**
	 * Get an ontology representing this vocabulary. This can be used for ontology reasoning other
	 * than subclass reasoning.
	 */
	public abstract OntModel getOntology() ;
	
	@Override
	public boolean checkDomain(URI relationshipOrProperty, URI domain) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkRange(URI relationshipOrProperty, URI range) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public URI getDomain(URI relationshipOrProperty) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<URI> getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getRange(URI relationshipOrProperty) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<URI> getRelationships() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<URI> getSubProperties(URI relationshipOrProperty) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<URI> getSubtypes(URI type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<URI> getSuperProperties(URI relationshipOrProperty) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<URI> getSupertypes(URI type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<URI> getTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void check(RelationshipCondition relationshipCondition)
			throws VerificationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void check(PropertyCondition relationshipCondition)
			throws VerificationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void check(ExistsCondition relationshipCondition)
			throws VerificationException {
		// TODO Auto-generated method stub

	}

	@Override
	public Object load(URI type, String name, Connector connector)
			throws ResourceLoaderException {
		// TODO Auto-generated method stub
		return null;
	}

}
