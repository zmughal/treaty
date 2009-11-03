/*
 * Copyright (C) 2008 Jens Dietrich
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

/**
 * Defines a vocabulary that can be used in contracts.
 * Describes the syntax (the types and their relationships) and the semantics (i.e., given two resources,
 * are they linked or not) of the vocabulary contributed. 
 * @author Jens Dietrich
 */

public interface ContractVocabulary extends Verifier, ResourceLoader {
	
	/**
	 * Get the types in the vocabulary.
	 * @return a collection of types
	 */
	Collection<URI> getTypes() throws TreatyException;
	/**
	 * Get the relationships (aka object properties) in the vocabulary.
	 * @return a collection of relationships
	 */
	Collection<URI> getRelationships() throws TreatyException;
	/**
	 * Get the properties (aka data properties) in the vocabulary.
	 * @return a collection of types
	 */
	Collection<URI> getProperties() throws TreatyException;
	/**
	 * Checks whether a property or relationship is defined for a certain type.
	 * Note that inheritance should be supported here.
	 * @param relationshipOrProperty
	 * @param domain
	 * @return
	 */
	boolean checkDomain(URI relationshipOrProperty, URI domain) throws TreatyException;
	/**
	 * Checks whether a relationship can link to a certain type,
	 * or a property can be of this type.
	 * The types could be built-in XMLSChema types representing primitives.
	 * Note that inheritance should be supported here.
	 * @param relationshipOrProperty
	 * @param range
	 * @return
	 */
	boolean checkRange(URI relationshipOrProperty, URI range) throws TreatyException;
	/**
	 * Get the domain for a property.
	 * @param relationshipOrProperty
	 * @return
	 */
	URI getDomain(URI relationshipOrProperty) throws TreatyException;
	/**
	 * Get the range for a property.
	 * @param relationshipOrProperty
	 * @return
	 */
	URI getRange(URI relationshipOrProperty) throws TreatyException;
	/**
	 * Get a collection of super types for a given type.
	 * @param type
	 * @return
	 */
	Collection<URI> getSupertypes(URI type) throws TreatyException;
	/**
	 * Get a collection of sub types for a given type.
	 * @param type
	 * @return
	 */
	Collection<URI> getSubtypes(URI type) throws TreatyException;
	/**
	 * Get a collection of super properties for a given relationship of property.
	 * @param relationshipOrProperty
	 * @return
	 */
	Collection<URI> getSuperProperties(URI relationshipOrProperty) throws TreatyException;
	/**
	 * Get a collection of sub properties for a given relationship of property.
	 * @param relationshipOrProperty
	 * @return
	 */
	Collection<URI> getSubProperties(URI relationshipOrProperty) throws TreatyException;



	
	

}
