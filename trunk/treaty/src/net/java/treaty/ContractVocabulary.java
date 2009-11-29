/*
 * Copyright (C) 2008-2009 Jens Dietrich
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
 * <p>
 * Defines a vocabulary that can be used in {@link Contract}s. Describes the
 * syntax (the types and their relationships) and the semantics (i.e., given two
 * resources, are they linked or not) of the vocabulary contributed.
 * </p>
 * 
 * @author Jens Dietrich
 */
public interface ContractVocabulary extends Verifier, ResourceLoader {

	/**
	 * <p>
	 * Checks whether a property or relationship is defined for a certain type.
	 * Note that inheritance should be supported here.
	 * </p>
	 * 
	 * @param relationshipOrProperty
	 *          The {@link URI} of the relation or property that shall be checked.
	 * @param domain
	 *          The domain for that the relation or property shall be checked.
	 * @return <code>true</code> if the given relation or property is defined for
	 *         the given domain.
	 * @throws TreatyException
	 *           Thrown, if the evaluation fails with an {@link Exception}.
	 */
	boolean checkDomain(URI relationshipOrProperty, URI domain)
			throws TreatyException;

	/**
	 * <p>
	 * Checks whether a relationship can link to a certain type, or a property can
	 * be of this type. The types could be built-in XMLSChema types representing
	 * primitives. Note that inheritance should be supported here.
	 * </p>
	 * 
	 * @param relationshipOrProperty
	 *          The relationship or property that shall be checked.
	 * @param range
	 *          The range for that the relationship or property shall be checked.
	 * @return <code>true</code> if the given relation or property can link to/is
	 *         of the given range.
	 * @throws TreatyException
	 *           Thrown, if the evaluation fails with an {@link Exception}.
	 */
	boolean checkRange(URI relationshipOrProperty, URI range)
			throws TreatyException;

	/**
	 * <p>
	 * Returns the domain for a given relationship or property (as its {@link URI}
	 * ).
	 * </p>
	 * 
	 * @param relationshipOrProperty
	 *          The {@link URI} of the relationship or property whose domain shall
	 *          be returned.
	 * @return The domain as a {@link URI}
	 * @throws TreatyException
	 *           Thrown if the given {@link URI} does lead to a valid relationship
	 *           nor property.
	 */
	URI getDomain(URI relationshipOrProperty) throws TreatyException;

	/**
	 * <p>
	 * Returns the {@link URI}s of the properties of this
	 * {@link ContractVocabulary}.
	 * </p>
	 * 
	 * @return The properties of this {@link ContractVocabulary}.
	 */
	Collection<URI> getProperties() throws TreatyException;

	/**
	 * <p>
	 * Returns the range for a given relationship or property (as its {@link URI}
	 * ).
	 * </p>
	 * 
	 * @param relationshipOrProperty
	 *          The {@link URI} of the relationship or property whose range shall
	 *          be returned.
	 * @return The range as a {@link URI}
	 * @throws TreatyException
	 *           Thrown if the given {@link URI} does lead to a valid relationship
	 *           nor property.
	 */
	URI getRange(URI relationshipOrProperty) throws TreatyException;

	/**
	 * <p>
	 * Returns the {@link URI}s of the relationships of this
	 * {@link ContractVocabulary}.
	 * </p>
	 * 
	 * @return The relationships of this {@link ContractVocabulary}.
	 */
	Collection<URI> getRelationships() throws TreatyException;

	/**
	 * <p>
	 * Returns the sub properties for a given property (as its {@link URI}).
	 * </p>
	 * 
	 * @param type
	 *          The {@link URI} of the property whose sub properties shall be
	 *          returned.
	 * @return The sub properties as a {@link Collection} of {@link URI}
	 * @throws TreatyException
	 *           Thrown if the given {@link URI} does lead to a valid property.
	 */
	Collection<URI> getSubProperties(URI relationshipOrProperty)
			throws TreatyException;

	/**
	 * <p>
	 * Returns the sub types for a given type (as its {@link URI}).
	 * </p>
	 * 
	 * @param type
	 *          The {@link URI} of the type whose sub types shall be returned.
	 * @return The sub types as a {@link Collection} of {@link URI}
	 * @throws TreatyException
	 *           Thrown if the given {@link URI} does lead to a valid type.
	 */
	Collection<URI> getSubTypes(URI type) throws TreatyException;

	/**
	 * <p>
	 * Returns the super properties for a given property (as its {@link URI}).
	 * </p>
	 * 
	 * @param type
	 *          The {@link URI} of the property whose super properties shall be
	 *          returned.
	 * @return The super properties as a {@link Collection} of {@link URI}
	 * @throws TreatyException
	 *           Thrown if the given {@link URI} does lead to a valid property.
	 */
	Collection<URI> getSuperProperties(URI relationshipOrProperty)
			throws TreatyException;

	/**
	 * <p>
	 * Returns the super types for a given type (as its {@link URI}).
	 * </p>
	 * 
	 * @param type
	 *          The {@link URI} of the type whose super types shall be returned.
	 * @return The super types as a {@link Collection} of {@link URI}
	 * @throws TreatyException
	 *           Thrown if the given {@link URI} does lead to a valid type.
	 */
	Collection<URI> getSuperTypes(URI type) throws TreatyException;

	/**
	 * <p>
	 * Returns the {@link URI}s of the types of this {@link ContractVocabulary}.
	 * </p>
	 * 
	 * @return The types of this {@link ContractVocabulary}.
	 */
	Collection<URI> getTypes() throws TreatyException;
}