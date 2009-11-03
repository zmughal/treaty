/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse;

import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.osgi.framework.Bundle;
import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.io.OWLOntologyOutputTarget;
import org.semanticweb.owl.io.RDFXMLOntologyFormat;
import org.semanticweb.owl.model.*;
import org.semanticweb.owl.util.OWLOntologyMerger;

/**
 * The contract vocabulary built from contributing plugins.
 * @author Jens Dietrich
 * @deprecated use ContractVocabularyRegistry instead
 */
public class Vocabulary {
	private static Vocabulary defaultInstance = null;
	private OWLOntology ontology = null;
	private String owl = null; // this is the generated "raw" owl
	public static String OWNER_ANNOTATION = "net.java.treaty.eclipse.contributingplugin";
	public static Vocabulary getDefault() {
		synchronized (Vocabulary.class) {
			if (defaultInstance==null) {
				defaultInstance = new Vocabulary();
			}
			return defaultInstance;
		}
	}
	public static void reset () {
		synchronized (Vocabulary.class) {
			defaultInstance=null;
		}
	}
	private Vocabulary() {
		super();
		load();
	}
	
	private synchronized void load() {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		URI physicalURI = null;
		Bundle bundle = null;
		OWLDataFactory factory = manager.getOWLDataFactory();
		try {
			// load core
			bundle = org.eclipse.core.runtime.Platform.getBundle(Activator.PLUGIN_ID);
			physicalURI = bundle.getEntry("/vocabulary/core.owl").toURI();
			ontology = manager.loadOntologyFromPhysicalURI(physicalURI);
			addOwnerAnnotation(manager,factory,ontology,Activator.PLUGIN_ID);
		}
		catch (Exception x) {
			Logger.error("Error loading core contract vocabulary",x);
		}
		
		// extensions
		IExtensionRegistry registry = org.eclipse.core.runtime.Platform.getExtensionRegistry();
		IExtensionPoint xp = registry.getExtensionPoint("net.java.treaty.eclipse.vocabulary");
		
		for (IExtension x:xp.getExtensions()) {
			String pluginId = x.getContributor().getName();
			try {
				IConfigurationElement[] attributes = x.getConfigurationElements();
				for (int j=0;j<attributes.length;j++) {
					IConfigurationElement p = attributes[j];
					String loc = p.getAttribute("ontology");	
					bundle = org.eclipse.core.runtime.Platform.getBundle(pluginId);
					if (bundle!=null && loc!=null) {
						URL url = bundle.getEntry(loc);
						if (url==null) {
							Logger.warn("No vocabulary resource found for location "+url);
						}
						else {
							physicalURI = url.toURI();						
							OWLOntology ont = manager.loadOntologyFromPhysicalURI(physicalURI);
							addOwnerAnnotation(manager,factory,ont,pluginId);
						}
					}
				}
			}
			catch (Exception e) {
				Logger.error("Error loading vocabulary from "+pluginId,e);
			}			
		}
		// merge
		
		URI mergedOntologyURI = URI.create("http://www.treaty.org/merged");// TODO better URL
		OWLOntologyMerger merger = new OWLOntologyMerger(manager);
		try {
			ontology = merger.createMergedOntology(manager, mergedOntologyURI);
		}
		catch (Exception x) {
			Logger.warn("Error merging ontology",x);
		}

	}
	private void addOwnerAnnotation(OWLOntologyManager manager,OWLDataFactory factory, OWLOntology ont,String pluginId) {
		for (OWLClass c:ont.getReferencedClasses()) {
			java.util.Set<OWLClassAxiom> axioms = ont.getAxioms(c);
			if (axioms!=null && axioms.size()>0) { // only annotate classes defined here
				addOwnerAnnotation(manager,factory,ont,pluginId,c);
			}
		}
		for (OWLObjectProperty p:ont.getReferencedObjectProperties()) {
			java.util.Set<OWLObjectPropertyAxiom> axioms = ont.getAxioms(p);
			if (axioms!=null && axioms.size()>0) { // only annotate properties defined here
				addOwnerAnnotation(manager,factory,ont,pluginId,p);
			}
		}
		for (OWLDataProperty p:ont.getReferencedDataProperties()) {
			java.util.Set<OWLDataPropertyAxiom> axioms = ont.getAxioms(p);
			if (axioms!=null && axioms.size()>0) { // only annotate properties defined here
				addOwnerAnnotation(manager,factory,ont,pluginId,p);
			}
		}
	}
	private void addOwnerAnnotation(OWLOntologyManager manager,OWLDataFactory factory, OWLOntology ont, String pluginId,OWLEntity e) {
		OWLAnnotation anno = factory.getOWLLabelAnnotation(OWNER_ANNOTATION,pluginId);
		OWLAxiom ax = factory.getOWLEntityAnnotationAxiom(e, anno);
		try {
			manager.applyChange(new AddAxiom(ont, ax));
		}
		catch (Exception x) {
			Logger.warn("Error adding owner annotation to " + e,x);
		}
		
	}
	public OWLOntology getOntology() {
		return ontology;
	}
	public synchronized String getOWL() {
		if (owl==null) {
			OWLOntologyManager man = OWLManager.createOWLOntologyManager();
			final StringWriter writer = new StringWriter();
			OWLOntologyOutputTarget target = new OWLOntologyOutputTarget() {
				public OutputStream getOutputStream() {
					return null;
				}
				public URI getPhysicalURI() {
					return null;
				}
				public Writer getWriter() {
					return writer;
				}
				public boolean isOutputStreamAvailable() {
					return false;
				}
				public boolean isPhysicalURIAvailable() {
					return false;
				}
				public boolean isWriterAvailable() {
					return true;
				}
			};
			try {
				man.saveOntology(ontology, new RDFXMLOntologyFormat(),target);

				owl=writer.getBuffer().toString();
			} catch (Exception e) {
				Logger.error("Error storing merged ontology", e);
				owl="";
			}

		}
		return owl;
	}
}
