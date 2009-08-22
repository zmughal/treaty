/*
Copyright (C) 2009 by Claas Wilke (info@claaswilke.de)

This file is part of the Treaty OCL Vocabulary which adapts Dresden OCL2 for
Eclipse to Treaty.

Dresden OCL2 for Eclipse is free software: you can redistribute it and/or modify 
it under the terms of the GNU Lesser General Public License as published by the 
Free Software Foundation, either version 3 of the License, or (at your option)
any later version.

Dresden OCL2 for Eclipse is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License 
for more details.

You should have received a copy of the GNU Lesser General Public License along 
with Dresden OCL2 for Eclipse. If not, see <http://www.gnu.org/licenses/>.
 */
package net.java.treaty.eclipse.vocabulary.ocl.test.dynamic;

import net.java.treaty.dynamic.IRuntimeContext;
import net.java.treaty.eclipse.vocabulary.ocl.OCLVocabulary;

/**
 * <p>
 * An exception that is thrown during an {@link OCLVocabulary}
 * {@link IRuntimeContext} test if the test case is violated.
 * </p>
 * 
 * @author Claas Wilke
 */
public class DynamicOclVocabularyTestException extends RuntimeException {

	/** The ID to serialize this class. */
	private static final long serialVersionUID = -5067522994677743917L;

	/**
	 * (non-Javadoc)
	 * 
	 * @see RuntimeException#RuntimeException(Exception)
	 */
	public DynamicOclVocabularyTestException(Exception e) {

		super(e);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see RuntimeException#RuntimeException(String)
	 */
	public DynamicOclVocabularyTestException(String msg) {

		super(msg);
	}
}