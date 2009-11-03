/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse;


import java.net.URI;

import net.java.treaty.ContractVocabulary;
import net.java.treaty.TreatyException;
import net.java.treaty.vocabulary.CompositeContractOntology;


/**
 * Registry for contract vocabularies. 
 * Vocabulary contributions from different plugins are aggregated here.
 * @author jens dietrich
 */
public class VocabularyRegistry extends CompositeContractOntology  {
	public static VocabularyRegistry INSTANCE = new VocabularyRegistry();
	
	// overridden , super throws exception
	protected void reportDuplicateDef(String kind, URI resource,ContractVocabulary voc, ContractVocabulary part) throws TreatyException {
		StringBuffer b = new StringBuffer()
			.append("Attempt to redefine ")
			.append(kind)
			.append(" ")
			.append(resource)
			.append(" in ")
			.append(voc)
			.append(" - this is already defined in ")
			.append(part);
		Logger.warn(b.toString());
	}
}
