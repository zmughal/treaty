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
package net.java.treaty.eclipse.vocabulary.ocl.test.base.model;

import net.java.treaty.eclipse.vocabulary.ocl.OCLVocabulary;
import tudresden.ocl20.pivot.modelbus.model.IModel;

/**
 * <p>
 * A simple {@link Class} used as Java {@link IModel} to test the Treaty
 * {@link OCLVocabulary}.
 * </p>
 * 
 * @author Claas Wilke
 */
public class TestClass implements TestInterface {

	/**
	 * <p>
	 * Creates a new {@link TestClass}.
	 * </p>
	 */
	public TestClass() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.yava.treaty.eclipse.vocabulary.ocl.test.model.TestInterface#getName()
	 */
	public String getName() {
		return TestClass.class.getCanonicalName();
	}
}