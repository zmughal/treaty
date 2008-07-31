/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.vocabulary.junit;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import org.osgi.framework.Bundle;
import net.java.treaty.*;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.verification.ContractVocabulary;
import net.java.treaty.verification.VerificationException;

/**
 * Contributes the JUnit vocabulary.
 * @author Jens Dietrich
 */

public class JUnitVocabulary implements ContractVocabulary {
	
	
	public static final String NS = "http://www.massey.ac.nz/se/plugincontracts/junit/";
	// types
	public static final String TESTCASE = NS+"TestCase";
	// types defined elsewhere
	public static final String INSTANTIABLE_CLASS = "http://www.massey.ac.nz/se/plugincontracts/java/InstantiableClass";
	
	// relationships
	public static final String VERIFIES = NS+"verifies";
	// registry
	private Collection<URI> predicates = null;
	private Collection<URI> types = null;
	
	public JUnitVocabulary() {
		super();
	}

	public Collection<URI> getContributedPredicates() {
		if (predicates==null) {
			predicates = new ArrayList<URI>();
			try {
				predicates.add(new URI(VERIFIES));
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
				types.add(new URI(TESTCASE));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return types;
	}


	public void check(RelationshipCondition condition) throws VerificationException {
		String rel = condition.getRelationship().toString();
		Resource res1 = condition.getResource1();
		Resource res2 = condition.getResource2();
		assert res1.isInstantiated();
		assert res1.isLoaded();
		assert res2.isInstantiated();
		assert res2.isLoaded();
		if (VERIFIES.equals(rel)) {		
			Class test = (Class)res2.getValue();
			Class tested = (Class)res1.getValue();
			try {
				boolean result = new TestRunner().run(test, tested);
				if (!result) {
					throw new VerificationException("Implementation class "+tested+" failed test "+test);
				}
			}
			catch (Exception x) {
				throw new VerificationException("",x);
			}
			
		}
	
	}
	

	public Object load(URI type, String name,Connector connector) throws ResourceLoaderException {
		if (!type.toString().startsWith(NS)) 
			throw new ResourceLoaderException("This plugin cannot be used to instantiate resources of this type: " + type);
		Bundle b = ((EclipsePlugin)connector.getOwner()).getBundle();
		
		 try {
			Class clazz = b.loadClass(name);
			if (INSTANTIABLE_CLASS.equals(type)) {
				try {
					return clazz;
				}
				catch (Exception x) {
					throw new ResourceLoaderException("This class can be loaded but not instantiated: " + name);
				}
			}
			else if (TESTCASE.equals(type)) {
				try {
					clazz.newInstance();
				}
				catch (Exception x) {
					throw new ResourceLoaderException("This class can be loaded but not instantiated: " + name);
				}				
			}
			else 
				return clazz;
		} catch (ClassNotFoundException e) {
			throw new ResourceLoaderException("Cannot load class " + name + " with classloader from plugin " + b.getBundleId());
		}
		throw new ResourceLoaderException("Cannot load resource " + name + " of type " + type);
	}
	
	@Override
	public void check(PropertyCondition relationshipCondition) throws VerificationException {
		throw new VerificationException("This vocabulary does not define property conditions");
	}

}
