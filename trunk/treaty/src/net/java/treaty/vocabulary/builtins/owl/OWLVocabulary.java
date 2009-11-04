/*
 * Copyright (C) 2008 Jens Dietrich
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
 * Contributes the OWL vocabulary.
 * TODO: separate types for OWLLite, OWL-DL, OWL Full, instantiation relationship.
 * @author Jens Dietrich
 */

public class OWLVocabulary extends ContractOntology {

	public static final String NS = "http://www.treaty.org/owl#";
	// types
	public static final String ONTOLOGY = NS+"Ontology";
	
	private OntModel ontology = null;
	
	public OWLVocabulary() {
		super();
	}

	
	public Object load(URI type, String name, Connector connector) throws ResourceLoaderException {
		if (!type.toString().startsWith(NS)) 
			throw new ResourceLoaderException("This plugin cannot be used to instantiate resources of this type: " + type);
		try {
			return connector.getOwner().getResource(name);
		}
		catch (Exception x) {
			throw new ResourceLoaderException("Error loading ontology " + name + " from plugin " + connector.getOwner().getId(),x);
		}
	}
	
	public void check(PropertyCondition relationshipCondition) throws VerificationException {
		throw new VerificationException("This vocabulary does not define property conditions");
	}
	public void check(RelationshipCondition condition) throws VerificationException {
		throw new VerificationException("This vocabulary does not define relationship conditions");
	}

	public void check(ExistsCondition condition) throws VerificationException {
		Resource resource = condition.getResource();
		assert resource.isInstantiated();
		assert resource.isLoaded();
		if (ONTOLOGY.equals(resource.getType().toString())) {
			URL url = (URL)resource.getValue();
			OntModel model = ModelFactory.createOntologyModel();
			try {
				model.read(url.toString());
			} 
			catch (Exception x) {
				throw new VerificationException("The resource " + url + " cannot be parsed as ontology",x);
			}
			if (model.size()==0) throw new VerificationException("The resource " + url + " cannot be parsed as ontology - no statements found");
		}
	}
	
	@Override
	public OntModel getOntology() {
		if (ontology==null) {
			ontology = ModelFactory.createOntologyModel();
			ontology.read(OWLVocabulary.class.getResource("/net/java/treaty/vocabulary/builtins/owl/owl.owl").toString());
		}
		return ontology;
	}
}
