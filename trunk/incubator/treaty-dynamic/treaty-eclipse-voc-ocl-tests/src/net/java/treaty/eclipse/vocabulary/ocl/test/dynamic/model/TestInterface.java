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
package net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model;

import java.util.Collection;

import net.java.treaty.dynamic.IDynamicContractVocabulary;
import net.java.treaty.eclipse.vocabulary.ocl.OCLVocabulary;

/**
 * <p>
 * An interface used to test the Treaty {@link OCLVocabulary} implmenetation of
 * the {@link IDynamicContractVocabulary} interface.
 * </p>
 * 
 * @author Claas Wilke
 */
public interface TestInterface {

	public static int STATIC_ATTRIBUTE = 0;

	/**
	 * 
	 * <p>
	 * Returns the private {@link Integer} attribute1.
	 * </p>
	 * 
	 * @return The private {@link Integer} attribute1.
	 */
	public int getAttribute1();

	/**
	 * 
	 * <p>
	 * Returns the private {@link Integer} attribute1 plus 1. Used to test the
	 * verification of a body expression.
	 * </p>
	 * 
	 * @return The private {@link Integer} attribute1 plus 1.
	 */
	public int getAttribute1plus1();

	/**
	 * <p>
	 * A method without arguments.
	 * </p>
	 * 
	 * @return Should return a <code>true</code> value.
	 */
	public boolean methodWithoutArguments();

	/**
	 * <p>
	 * A method with one argument.
	 * </p>
	 * 
	 * @param anArgument
	 *          the {@link Object} that shall be returned.
	 * @return Should return the given argument.
	 */
	public Object methodWithOneArgument(Object anArgument);

	/**
	 * <p>
	 * A method with two arguments.
	 * </p>
	 * 
	 * @param arg1
	 *          The first argument.
	 * @param arg2
	 *          The second argument.
	 * @return Should return the concatenation of the given arguments.
	 */
	public String methodWithTwoArguments(String arg1, String arg2);

	/**
	 * <p>
	 * A method that has no result (<code>void</code>).
	 * </p>
	 * 
	 * <p>
	 * Should increment the attribute1 that is available using the method
	 * {@link TestInterface#getAttribute1}.
	 * </p>
	 */
	public void methodWithoutResult();

	/**
	 * <p>
	 * A method with multiple results.
	 * </p>
	 * 
	 * @return Should return a {@link Collection} containing the {@link Integer}s
	 *         from 1 to 5.
	 */
	public int[] methodWithMultipleResults();

	/**
	 * <p>
	 * A method with recursively calls itself.
	 * </p>
	 * 
	 * @param nMoreCalls
	 *          Says how often the method will called recursively.
	 * @return Returns the same value as given as <code>nMoreCalls</code>.
	 */
	public int methodWithRecursion(int nMoreCalls);

	/**
	 * <p>
	 * A method that can be invoked to test a <code>body</code> expression.
	 * </p>
	 */
	public void methodToTestBodyExpression();

	/**
	 * <p>
	 * A method that can be invoked to test a <code>def</code> expression.
	 * </p>
	 */
	public void methodToTestDefExpression();

	/**
	 * <p>
	 * A method that can be invoked to test a <code>derive</code> expression.
	 * </p>
	 */
	public void methodToTestDeriveExpression();

	/**
	 * <p>
	 * A method that can be invoked to test a <code>derive</code> expression.
	 * </p>
	 */
	public void methodToTestLetExpression();
}