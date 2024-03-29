/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package test.net.java.treaty.contractregistry;

import java.net.URI;

import net.java.treaty.ContractVocabulary;
import net.java.treaty.TreatyException;
import net.java.treaty.vocabulary.CompositeContractOntology;
import net.java.treaty.vocabulary.builtins.BasicOpVocabulary;
import net.java.treaty.vocabulary.builtins.java.JavaVocabulary;
import net.java.treaty.vocabulary.builtins.owl.OWLVocabulary;

/**
 * <p>
 * A Mock implementation of {@link ContractVocabulary}.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class VocabularyMock extends CompositeContractOntology {

	/** The singleton instance of the {@link VocabularyMock}. */
	public static VocabularyMock INSTANCE = new VocabularyMock();

	/**
	 * <p>
	 * Creates a new {@link VocabularyMock}. Private constructor for singleton
	 * pattern.
	 * </p>
	 */
	private VocabularyMock() {

		super();

		try {
			this.add(new BasicOpVocabulary(), "net.java.treaty");
			this.add(new JavaVocabulary(), "net.java.treaty");
			this.add(new OWLVocabulary(), "net.java.treaty");
		}

		catch (TreatyException x) {
			/* Do nothing. */
		}
	}

	/**
	 * <p>
	 * Overridden, super method throws exception. This implementation just logs a
	 * warning.
	 * </p>
	 */
	@Override
	protected void reportDuplicateDef(String kind, URI resource,
			ContractVocabulary voc, ContractVocabulary part) throws TreatyException {

		StringBuffer buffer;

		buffer = new StringBuffer();
		buffer.append("Attempt to redefine ");
		buffer.append(kind);
		buffer.append(" ");
		buffer.append(resource);
		buffer.append(" in ");
		buffer.append(voc);
		buffer.append(" - this is already defined in ");
		buffer.append(part);

		System.out.println(buffer.toString());
	}
}