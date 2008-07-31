/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.verification;

import java.net.URI;
import java.util.Collection;
import net.java.treaty.ResourceLoader;

/**
 * Defines a vocabulary that can be used in contracts.
 * Describes the syntax (the types and their relationships) and the semantics (i.e., given two resources,
 * are they linked or not) of the vocabulary contributed. 
 * @author Jens Dietrich
 */

public interface ContractVocabulary extends Verifier, ResourceLoader{
	/**
	 * Get a collection of all contributed predicates. 
	 * The check method must be able to check all conditions with predicates from the collection returned. 
	 * @return a collection
	 */
	public Collection<URI> getContributedPredicates();
	/**
	 * Get a collection of all types. 
	 * @return a collection
	 */
	public Collection<URI> getContributedTypes();


	
	

}
