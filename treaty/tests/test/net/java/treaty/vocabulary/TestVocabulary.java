/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package test.net.java.treaty.vocabulary;

import java.net.URI;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import net.java.treaty.*;
import net.java.treaty.vocabulary.ContractOntology;


/**
 * Contributes the OWL vocabulary.
 * TODO: separate types for OWLLite, OWL-DL, OWL Full, instantiation relationship.
 * @author Jens Dietrich
 */

public class TestVocabulary extends ContractOntology {

	private OntModel ontology = null;
	
	public TestVocabulary() {
		super();
	}

	
	public Object load(URI type, String name, Connector connector) throws ResourceLoaderException {
		throw new ResourceLoaderException("Operation not supported - this is for testing only");
	}
	
	public void check(PropertyCondition relationshipCondition) throws VerificationException {
		throw new VerificationException("Operation not supported - this is for testing only");
	}
	public void check(RelationshipCondition condition) throws VerificationException {
		throw new VerificationException("Operation not supported - this is for testing only");
	}

	public void check(ExistsCondition condition) throws VerificationException {
		throw new VerificationException("Operation not supported - this is for testing only");
	}
	
	@Override
	public OntModel getOntology() {
		if (ontology==null) {
			ontology = ModelFactory.createOntologyModel();
			ontology.read(TestVocabulary.class.getResource("/test/net/java/treaty/vocabulary/test.owl").toString());
		}
		return ontology;
	}
}
