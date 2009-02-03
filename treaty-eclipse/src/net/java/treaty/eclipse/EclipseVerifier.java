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

/**
 * Verifier for Eclipse.
 * @author Jens Dietrich
 */

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import net.java.treaty.*;

public class EclipseVerifier implements Verifier,ResourceLoader {
	private List<ContractVocabulary> vocContributions = null;
	public EclipseVerifier () {
		super();
		loadVocabularyContributions();
	}
	private void loadVocabularyContributions() {
		vocContributions = new ArrayList<ContractVocabulary>();
		IExtensionPoint xp = Platform.getExtensionRegistry().getExtensionPoint("net.java.treaty.eclipse.vocabulary");
		for (IExtension x:xp.getExtensions()) {
			IConfigurationElement[] attributes = x.getConfigurationElements();	
			String pluginId = x.getContributor().getName();
			for (int j=0;j<attributes.length;j++) {
				IConfigurationElement p = attributes[j];
				String clazz = p.getAttribute("class");
				try {
					Class c = Platform.getBundle(pluginId).loadClass(clazz);
					vocContributions.add((ContractVocabulary)c.newInstance());
				}
				catch (ClassNotFoundException ex) {
					Logger.error("Cannot load vocabulary contribution "+clazz +" provided by bundle " + pluginId, ex);
				}
				catch (IllegalAccessException ex) {
					Logger.error("Cannot instantiate vocabulary contribution "+clazz +" provided by bundle " + pluginId, ex);
				}
				catch (InstantiationException ex) {
					Logger.error("Cannot instantiate vocabulary contribution "+clazz +" provided by bundle " + pluginId, ex);
				}
			}
		}
	}

	
	public void check(RelationshipCondition condition) throws VerificationException {
		URI uri = condition.getRelationship();
		try {
			ContractVocabulary voc = this.findVocabularyForProperty(uri);
			voc.check(condition); 
			//System.out.println("checked ok: " + condition);
			condition.setProperty(Constants.VERIFICATION_RESULT,VerificationResult.SUCCESS);
			condition.removeProperty(Constants.VERIFICATION_EXCEPTION);
		}
		catch (VerificationException x) {
			
			condition.setProperty(Constants.VERIFICATION_RESULT,VerificationResult.FAILURE);
			condition.setProperty(Constants.VERIFICATION_EXCEPTION, x);
			throw (VerificationException)x.fillInStackTrace();
		}
		return;
	}
	
	public void check(PropertyCondition condition) throws VerificationException {
		
			Operator operator = condition.getOperator();
			String value = condition.getValue();
			String property = condition.getProperty();
			Object obj = null;
			if (property==null) {
				obj = condition.getResource().getValue(); 
			}
			if (!operator.compare(obj,value)) {
				condition.setProperty(Constants.VERIFICATION_RESULT,VerificationResult.FAILURE);
				throw new VerificationException("Verification failed, condition is not satisfied: "+condition);
			}

	}
	
	private void checkBuiltInDatatype(Object v,URI type) throws VerificationException {
		String t = type.toString();
		// FIXME - remove this tmp hack
		if (t.equals("http://www.w3.org/2001/XMLSchema:string")) {
			System.out.println("oudated data type URI used !!: http://www.w3.org/2001/XMLSchema#string");
		}
		if (t.equals("http://www.w3.org/2001/XMLSchema#string")) {
			return; 
		}
		// int should be used !! see http://www.w3.org/TR/xmlschema-2/
		else if (t.equals("http://www.w3.org/2001/XMLSchema#int") || t.equals("http://www.w3.org/2001/XMLSchema#int")) {
			if (v instanceof Integer) return;
			try {
				Integer.parseInt(v.toString());
			}
			catch (NumberFormatException x) {
				throw new VerificationException(v.toString() + " is not an integer");
			}
		}
		else if (t.equals("http://www.w3.org/2001/XMLSchema#double")) {
			if (v instanceof Double) return;
			try {
				Double.parseDouble(v.toString());
			}
			catch (NumberFormatException x) {
				throw new VerificationException(v.toString() + " is not a double");
			}
		}
		else if (t.equals("http://www.w3.org/2001/XMLSchema#boolean")) {
			if (v instanceof Double) return;
			try {
				Boolean.parseBoolean(v.toString());
			}
			catch (NumberFormatException x) {
				throw new VerificationException(v.toString() + " is not a boolean");
			}
		}
		else throw new VerificationException("unsupported data type: " + type);
	}
	public void check(ExistsCondition condition) throws VerificationException {
		Resource resource = condition.getResource();
		URI type = resource.getType();
		
		// built-in type string
		if (type.toString().startsWith("http://www.w3.org/2001/XMLSchema")) {
			assert resource.isInstantiated();
			assert resource.isLoaded();
			if (resource.getValue()==null)
				throw new VerificationException("Resource value should not be null");
			checkBuiltInDatatype(resource.getValue(),type);
			return;
		}
		
		try {
			ContractVocabulary voc = this.findVocabularyForType(resource.getType());
			voc.check(condition); 
			//System.out.println("checked ok: " + condition);
		}
		catch (VerificationException x) {
			condition.setProperty(Constants.VERIFICATION_RESULT,VerificationResult.FAILURE);
			condition.setProperty(Constants.VERIFICATION_EXCEPTION, x);
			throw (VerificationException)x.fillInStackTrace();
		}
		return;
	}
	
	private ContractVocabulary findVocabularyForProperty(URI uri)  throws VerificationException {	
		for (ContractVocabulary voc:vocContributions) {
			if (voc.getContributedPredicates().contains(uri)) {
				return voc;
			}
		}
		throw new VerificationException("No vocabulary found to check condition with predicate " + uri);
	}
	private ContractVocabulary findVocabularyForType(URI uri)  throws VerificationException {	
		for (ContractVocabulary voc:vocContributions) {
			if (voc.getContributedTypes().contains(uri)) {
				return voc;
			}
		}
		throw new VerificationException("No vocabulary found to check condition with predicate " + uri);
	}
	
	public Object load(URI type, String name, Connector connector) throws ResourceLoaderException {
		// built-in type string
		String t = type.toString();
		if (t.equals("http://www.w3.org/2001/XMLSchema#string")) {
			return name;
		}
		else if (t.equals("http://www.w3.org/2001/XMLSchema#int") || t.equals("http://www.w3.org/2001/XMLSchema#integer")) {
			try {
				return Integer.parseInt(name);
			}
			catch (NumberFormatException x) {
				throw new ResourceLoaderException(x);
			}
		}
		else if (t.equals("http://www.w3.org/2001/XMLSchema#double")) {
			try {
				return Double.parseDouble(name);
			}
			catch (NumberFormatException x) {
				throw new ResourceLoaderException(x);
			}
		}
		else if (t.equals("http://www.w3.org/2001/XMLSchema#boolean")) {
			return Boolean.parseBoolean(name);
		}
		
		// load plugin types
		for (ContractVocabulary voc:vocContributions) {
			if (voc.getContributedTypes().contains(type)) {
				return voc.load(type,name,connector); // this may throw an exception
			}
		}
		throw new ResourceLoaderException("No vocabulary found to load resource " + type);
	}



}
