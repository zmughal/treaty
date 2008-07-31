/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.vocabulary.xml;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import net.java.treaty.Condition;
import net.java.treaty.Connector;
import net.java.treaty.Resource;
import net.java.treaty.ResourceLoaderException;
import net.java.treaty.verification.ContractVocabulary;
import net.java.treaty.verification.VerificationException;


/**
 * Contributes the XML vocabulary.
 * Warning: this is not complete, support for DTD,XPATH and XSLT is missing. TODO
 * @author Jens Dietrich
 */

public class XMLVocabulary implements  ContractVocabulary {

	public static final String NS = "http://www.massey.ac.nz/se/plugincontracts/xml/";
	// types
	public static final String INSTANCE = NS+"XMLInstance";
	public static final String SCHEMA = NS+"XMLSchema";
	// relationships
	public static final String INSTANTIATES = NS+"instantiates";
	// registry
	private Collection<URI> predicates = null;
	private Collection<URI> types = null;
	
	public XMLVocabulary() {
		super();
	}

	public Collection<URI> getContributedPredicates() {
		if (predicates==null) {
			predicates = new ArrayList<URI>();
			try {
				predicates.add(new URI(INSTANTIATES));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return predicates;
	}

	public Collection<URI> getContributedTypes() {
		if (types==null) {
			types = new ArrayList<URI>();
			try {
				types.add(new URI(SCHEMA));
				types.add(new URI(INSTANCE));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return types;
	}


	public void check(Condition condition) throws VerificationException {
		String rel = condition.getRelationship().toString();
		Resource res1 = condition.getResource1();
		Resource res2 = condition.getResource2();
		assert res1.isInstantiated();
		assert res1.isLoaded();
		assert res2.isInstantiated();
		assert res2.isLoaded();
		if (INSTANTIATES.equals(rel)) {
			try {
				URL schemaURL = (URL)res2.getValue();
				URL instanceURL = (URL)res1.getValue();
				SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		        Schema schema = factory.newSchema(schemaURL);
		        javax.xml.validation.Validator validator = schema.newValidator();
		        InputStream in = instanceURL.openStream();
		        Source source = new StreamSource(in);
		        validator.validate(source);
		        try {
		        	in.close();
		        }
		        catch (Exception x){}
			} catch (Exception x) {
				throw new VerificationException("Validation of the XML document failed" ,x);
			}
			

		}
		else 
			throw new VerificationException("predicate not supported + " + rel);
	}
	
	public Object load(URI type, String name, Connector connector) throws ResourceLoaderException {
		if (!type.toString().startsWith(NS)) 
			throw new ResourceLoaderException("This plugin cannot be used to instantiate resources of this type: " + type);
		
		try {
			return connector.getOwner().getResource(name);
		}
		catch (Exception x) {
			throw new ResourceLoaderException("Cannot locate xml resource: "+name,x);
		}
	}
	
	

}
