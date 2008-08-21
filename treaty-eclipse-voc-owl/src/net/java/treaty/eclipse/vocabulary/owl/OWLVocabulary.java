/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.vocabulary.owl;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.osgi.framework.Bundle;
import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.semanticweb.owl.model.OWLOntologyManager;
import net.java.treaty.*;
import net.java.treaty.eclipse.Activator;
import net.java.treaty.eclipse.EclipsePlugin;


/**
 * Contributes the OWL vocabulary.
 * TODO: separate types for OWLLite, OWL-DL, OWL Full, instantiation relationship.
 * @author Jens Dietrich
 */

public class OWLVocabulary implements  ContractVocabulary {

	public static final String NS = "http://www.treaty.org/owl#";
	// types
	public static final String ONTOLOGY = NS+"Ontology";

	// registry
	private Collection<URI> predicates = Collections.unmodifiableCollection(new ArrayList<URI>());
	private Collection<URI> types = null;
	
	public OWLVocabulary() {
		super();
	}

	public Collection<URI> getContributedPredicates() {
		return predicates;
	}

	public Collection<URI> getContributedTypes() {
		if (types==null) {
			types = new ArrayList<URI>();
			try {
				types.add(new URI(ONTOLOGY));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return types;
	}


	public void check(RelationshipCondition condition) throws VerificationException {
		throw new VerificationException("This vocabulary does not define relationship conditions");
	}
	
	public Object load(URI type, String name, Connector connector) throws ResourceLoaderException {
		if (!type.toString().startsWith(NS)) 
			throw new ResourceLoaderException("This plugin cannot be used to instantiate resources of this type: " + type);
		try {
			// check whether this is a OWL resource - try to parse it
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			EclipsePlugin plugin = (EclipsePlugin) connector.getOwner();
			Bundle bundle = plugin.getBundle();
			return bundle.getEntry(name);
		}
		catch (Exception x) {
			Logger.error("Error loading ontology " + name + " from plugin " + connector.getOwner().getId());
			throw new ResourceLoaderException("Error loading ontology " + name + " from plugin " + connector.getOwner().getId(),x);
		}
	}
	
	public void check(PropertyCondition relationshipCondition) throws VerificationException {
		throw new VerificationException("This vocabulary does not define property conditions");
	}

	public void check(ExistsCondition condition) throws VerificationException {
		Resource resource = condition.getResource();
		assert resource.isInstantiated();
		assert resource.isLoaded();
		if (ONTOLOGY.equals(resource.getType().toString())) {
			URL url = (URL)resource.getValue();
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			try {
				manager.loadOntologyFromPhysicalURI(url.toURI());
			} 
			catch (Exception x) {
				throw new VerificationException("The resource " + url + " cannot be parsed as ontology",x);
			}
		}
	}
}
