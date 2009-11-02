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
import net.java.treaty.Vocabulary;
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
	public static Collection<URI> findUndefinedTypes(Contract c,Vocabulary v) {
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
	public static Collection<URI> findUndefinedRelationships(Contract c,Vocabulary v) {
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
	

}
