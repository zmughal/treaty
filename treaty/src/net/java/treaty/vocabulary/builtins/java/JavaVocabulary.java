/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.vocabulary.builtins.java;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import net.java.treaty.*;
import net.java.treaty.vocabulary.ContractOntology;


/**
 * Contributes the Java vocabulary. Warning: this is not complete.
 * @author Jens Dietrich
 */

public class JavaVocabulary extends ContractOntology {

	public static final String NS = "http://www.treaty.org/java#";
	// types
	public static final String ABSTRACT_TYPE = NS+"AbstractType";
	public static final String INSTANTIABLE_CLASS = NS+"InstantiableClass";
	// relationships
	public static final String IMPLEMENTS = NS+"implements";
	// ontology
	private OntModel ontology = null;

	
	public JavaVocabulary() {
		super();
	}

	public Object load(URI type, String name, Connector connector) throws ResourceLoaderException  {
		if (!type.toString().startsWith(NS)) 
			throw new ResourceLoaderException("This plugin cannot be used to instantiate resources of this type: " + type);
		
		Component c = connector.getOwner();
		
		try {
			
			// note: we remove postfixes starting with : and /
			// in Eclipse, this is done in 
			// org.eclipse.core.internal.registry.ConfigurationElement
			
			String className = name;
			String executable = null;
			int i = name.indexOf(':');
			if (i != -1) {
				executable = name.substring(0, i).trim();
				// initData = prop.substring(i + 1).trim();
			} else
				executable = name;

			i = executable.indexOf('/');
			if (i != -1) {
				// contributorName = executable.substring(0, i).trim();
				className = executable.substring(i + 1).trim();
			} else
				className = executable;
			
			Class clazz = c.loadClass(className);
			
			//analyse class types
			if (ABSTRACT_TYPE.equals(type)) {
				if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers()))
					return true;
				else 
					throw new ResourceLoaderException("This class can be loaded but is not abstract: " + name);
			}
			else if (INSTANTIABLE_CLASS.equals(type)) {
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
			throw new ResourceLoaderException("Cannot load class " + name + " with classloader from component " + c.getId());	
		}
		throw new ResourceLoaderException("Cannot load resource " + name + " of type " + type);
	}

	public void check(RelationshipCondition condition) throws VerificationException {
		String rel = condition.getRelationship().toString();
		Resource res1 = condition.getResource1();
		Resource res2 = condition.getResource2();
		assert res1.isInstantiated();
		assert res1.isLoaded();
		assert res2.isInstantiated();
		assert res2.isLoaded();
		if (IMPLEMENTS.equals(rel)) {
			Class class1 = (Class)res1.getValue();
			checkInstantiableClass(res1,class1);
			Class class2 = (Class)res2.getValue();
			checkAbstractType(res2,class2);
			if (class2.isAssignableFrom(class1)) {}
			else throw new VerificationException("condition not satisfied + " + condition);
		}
		else 
			throw new VerificationException("predicate not supported + " + rel);
	}

	public void check(PropertyCondition relationshipCondition) throws VerificationException {
		throw new VerificationException("This vocabulary does not define property conditions");
	}
	
	public void check(ExistsCondition condition) throws VerificationException {
		Resource resource = condition.getResource();
		assert resource.isInstantiated();
		assert resource.isLoaded();
		Class clazz = (Class)resource.getValue();
		if (ABSTRACT_TYPE.equals(resource.getType().toString())) {
			checkAbstractType(resource,clazz);
		}
		else if (INSTANTIABLE_CLASS.equals(resource.getType().toString())) {
			checkInstantiableClass(resource,clazz);
		}
	}
	
	private void checkAbstractType(Resource r,Class clazz) throws VerificationException {
		if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers()))
			return;
		else 
			throw new VerificationException("The value of resource "+r+" is " + clazz + " - this is not an abstract java type");
	}
	
	private void checkInstantiableClass(Resource r,Class clazz) throws VerificationException {
		try {
			if (Modifier.isAbstract(clazz.getModifiers())) {
				throw new VerificationException("The value of resource "+r+" is " + clazz + " - this is an abstract (and not an instantiable) java type");
			}
			if (clazz.isInterface()) {
				throw new VerificationException("The value of resource "+r+" is " + clazz + " - this is an interface and not an instantiable java type");
			}
			Constructor constructor = clazz.getConstructor(new Class[]{});
			if (constructor==null || !Modifier.isPublic(constructor.getModifiers())) {
				throw new VerificationException("The value of resource "+r+" is " + clazz + " - this is not an instantiable java type");
			}
		}
		catch (Exception x) {
			throw new VerificationException("The value of resource "+r+" is " + clazz + " - this is not an instantiable java type");
		}
	}

	@Override
	public OntModel getOntology() {
		if (ontology==null) {
			ontology = ModelFactory.createOntologyModel();
			ontology.read(JavaVocabulary.class.getResource("/net/java/treaty/vocabulary/builtins/java/java.owl").toString());
		}
		return ontology;
	}
	public static void main(String[] args) throws Exception {
		JavaVocabulary jv = new JavaVocabulary();
		for (java.net.URI u:jv.getRelationships()) {
			System.out.println("relationship: "+u);
		}
		for (java.net.URI u:jv.getTypes()) {
			System.out.println("type: "+u);
		}
		for (java.net.URI u:jv.getProperties()) {
			System.out.println("property: "+u);
		}
	}
	

}
