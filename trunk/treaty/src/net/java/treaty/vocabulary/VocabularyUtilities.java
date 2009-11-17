/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.vocabulary;

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
 * <p>
 * A set of vocabulary related utilities.
 * </p>
 * 
 * @author jens dietrich
 */
public class VocabularyUtilities {

	/**
	 * <p>
	 * Checks whether two types are valid domain and range types for a given
	 * relationship.
	 * </p>
	 * 
	 * @param voc
	 *          The {@link ContractVocabulary} used.
	 * @param relationship
	 *          The relationship (as a {@link URI}) to be checked.
	 * @param type1
	 *          The first type (as a {@link URI}) to be checked.
	 * @param type2
	 *          The second type (as a {@link URI}) to be checked.
	 * @throws TreatyException
	 *           Thrown, if the check fails.
	 */
	public static void checkTypeSafety(ContractVocabulary voc, URI relationship,
			URI type1, URI type2) throws TreatyException {
	
		URI domain;
		domain = voc.getDomain(relationship);
	
		URI range;
		range = voc.getRange(relationship);
	
		if (!(domain.equals(type1) || voc.getSupertypes(type1).contains(domain))) {
			throw new TreatyException("" + type1 + " is not a valid domain type for "
					+ relationship);
		}
	
		else if (!(range.equals(type2) || voc.getSupertypes(type2).contains(range))) {
			throw new TreatyException("" + type2 + " is not a valid range type for "
					+ relationship);
		}
		// no else (success).
	}

	/**
	 * <p>
	 * Finds relationship types in a given {@link Contract} that are not defined
	 * in a given {@link ContractVocabulary}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} whose relationships shall be checked.
	 * @param vocabulary
	 *          The {@link ContractVocabulary} whose relationships shall be
	 *          checked.
	 */
	public static Collection<URI> findUndefinedRelationships(Contract c,
			ContractVocabulary v) {

		final Collection<URI> result;
		result = new HashSet<URI>();

		ContractVisitor visitor;
		visitor = new AbstractContractVisitor() {

			/*
			 * (non-Javadoc)
			 * @seenet.java.treaty.AbstractContractVisitor#visit(net.java.treaty.
			 * RelationshipCondition)
			 */
			public boolean visit(RelationshipCondition rel) {

				super.visit(rel);

				result.add(rel.getRelationship());

				return true;
			}
		};

		c.accept(visitor);

		return result;
	}

	/**
	 * <p>
	 * Find types in a given {@link Contract} not defined in the given
	 * {@link ContractVocabulary}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} whose types shall be checked.
	 * @param vocabulary
	 *          The {@link ContractVocabulary} whose types shall be checked.
	 */
	public static Collection<URI> findUndefinedTypes(Contract contract,
			ContractVocabulary vocabulary) {

		final Collection<URI> result;
		result = new HashSet<URI>();

		ContractVisitor visitor;

		visitor = new AbstractContractVisitor() {

			/*
			 * (non-Javadoc)
			 * @see
			 * net.java.treaty.AbstractContractVisitor#visit(net.java.treaty.Resource)
			 */
			public boolean visit(Resource resource) {

				super.visit(resource);
				result.add(resource.getType());

				return true;
			}
		};

		contract.accept(visitor);

		return result;
	}
}