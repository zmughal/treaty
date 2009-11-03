/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.util;

import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import net.java.treaty.AbstractContractVisitor;
import net.java.treaty.Contract;
import net.java.treaty.ContractVisitor;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.TreatyException;
import net.java.treaty.ContractVocabulary;
/**
 * A set of vocabulary related utilities.
 * @author jens dietrich
 */
public class VocabularyUtilities {
	/**
	 * Find types in the contract not defined in
	 * the vocabulary.
	 * @param c
	 * @param v
	 * @return
	 */
	public static Collection<URI> findUndefinedTypes(Contract c,ContractVocabulary v) {
		final Collection<URI> coll = new HashSet<URI>();
		ContractVisitor visitor = new AbstractContractVisitor() {
			@Override
			public boolean visit(Resource resource) {
				super.visit(resource);
				coll.add(resource.getType());
				return true;
			}
		};
		c.accept(visitor);
		return coll;
	}
	
	/**
	 * Find relationship types in the contract not defined in
	 * the vocabulary.
	 * @param c
	 * @param v
	 * @return
	 */
	public static Collection<URI> findUndefinedRelationships(Contract c,ContractVocabulary v) {
		final Collection<URI> coll = new HashSet<URI>();
		ContractVisitor visitor = new AbstractContractVisitor() {
			@Override
			public boolean visit(RelationshipCondition rel) {
				super.visit(rel);
				coll.add(rel.getRelationship());
				return true;
			}

		};
		c.accept(visitor);
		return coll;
	}
	/**
	 * Check whether two types are valid domain and range types for a relationship.
	 * @param voc
	 * @param relationship
	 * @param type1
	 * @param type2
	 * @throws TreatyException
	 */
	public void checkTypeSafety(ContractVocabulary voc, URI relationship, URI type1, URI type2) throws TreatyException {
		URI domain = voc.getDomain(relationship);
		URI range = voc.getDomain(relationship);
		if (!(domain.equals(type1) || voc.getSupertypes(type1).contains(domain))) 
			throw new TreatyException(""+type1+ " is not a valid domain type for " + relationship);
		if (!(range.equals(type2) || voc.getSupertypes(type2).contains(range))) 
			throw new TreatyException(""+type2+ " is not a valid range type for " + relationship);
	}
	

}
