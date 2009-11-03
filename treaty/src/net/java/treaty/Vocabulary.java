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
/**
 * Interface representing the types, relationships and properties used in contracts.
 * An implementation could use an OWL ontology. 
 * @author jens dietrich
 */
public interface Vocabulary {
	/**
	 * Get the types in the vocabulary.
	 * @return a collection of types
	 */
	Collection<URI> getTypes();
	/**
	 * Get the relationships (aka object properties) in the vocabulary.
	 * @return a collection of relationships
	 */
	Collection<URI> getRelationships();
	/**
	 * Get the properties (aka data properties) in the vocabulary.
	 * @return a collection of types
	 */
	Collection<URI> getProperties();
	/**
	 * Checks whether a property or relationship is defined for a certain type.
	 * Note that inheritance should be supported here.
	 * @param relationshipOrProperty
	 * @param domain
	 * @return
	 */
	boolean checkDomain(URI relationshipOrProperty, URI domain);
	/**
	 * Checks whether a relationship can link to a certain type,
	 * or a property can be of this type.
	 * The types could be built-in XMLSChema types representing primitives.
	 * Note that inheritance should be supported here.
	 * @param relationshipOrProperty
	 * @param range
	 * @return
	 */
	boolean checkRange(URI relationshipOrProperty, URI range);
	/**
	 * Get the domain for a property.
	 * @param relationshipOrProperty
	 * @return
	 */
	URI getDomain(URI relationshipOrProperty);
	/**
	 * Get the range for a property.
	 * @param relationshipOrProperty
	 * @return
	 */
	URI getRange(URI relationshipOrProperty);
	/**
	 * Get a collection of super types for a given type.
	 * @param type
	 * @return
	 */
	Collection<URI> getSupertypes(URI type);
	/**
	 * Get a collection of sub types for a given type.
	 * @param type
	 * @return
	 */
	Collection<URI> getSubtypes(URI type);
	/**
	 * Get a collection of super properties for a given relationship of property.
	 * @param relationshipOrProperty
	 * @return
	 */
	Collection<URI> getSuperProperties(URI relationshipOrProperty);
	/**
	 * Get a collection of sub properties for a given relationship of property.
	 * @param relationshipOrProperty
	 * @return
	 */
	Collection<URI> getSubProperties(URI relationshipOrProperty);
	
	
}
