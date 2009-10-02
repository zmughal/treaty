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

import net.java.treaty.dynamic.IDynamicContractVocabulary;
import net.java.treaty.eclipse.vocabulary.ocl.OCLVocabulary;

/**
 * <p>
 * A class used to test the Treaty {@link OCLVocabulary} implementation of the
 * {@link IDynamicContractVocabulary} interface that <b>does not fulfill</b> the
 * implementation dependent constraints.
 * </p>
 * 
 * @author Claas Wilke
 */
public class BadTestClass implements TestInterface {

	/**
	 * An attribute to test the side effects of the method
	 * {@link TestInterface#methodWithoutResult()}.
	 */
	private int attribute1 = 0;

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model.ContextTestInterface
	 * #getAtrribute1()
	 */
	public int getAttribute1() {

		return this.attribute1;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model.ContextTestInterface
	 * #getAttribute1plus1()
	 */
	public int getAttribute1plus1() {

		/* Implementation is realized by OCL body expression. */

		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model.ContextTestInterface
	 * #methodWithoutArguments()
	 */
	public boolean methodWithoutArguments() {

		/* Violates the post condition. */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model.ContextTestInterface
	 * #methodWithOneArgument(java.lang.Object)
	 */
	public Object methodWithOneArgument(Object anArgument) {

		/* Violates the post condition. */
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model.ContextTestInterface
	 * #methodWithTwoArguments(java.lang.String, java.lang.String)
	 */
	public String methodWithTwoArguments(String arg1, String arg2) {

		/* Violates the post condition. */
		return arg2 + arg1;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model.ContextTestInterface
	 * #methodWithoutResult()
	 */
	public void methodWithoutResult() {

		/* Violates the postcondition. */
		this.attribute1--;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model.ContextTestInterface
	 * #methodWithMultipleResults()
	 */
	public int[] methodWithMultipleResults() {

		int[] result;

		result = new int[5];

		for (int index = 0; index < 5; index++) {
			/* Violates the postcondition. */
			result[index] = -(index + 1);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model.ContextTestInterface
	 * #methodWithRecursion(int)
	 */
	public int methodWithRecursion(int nMoreCalls) {

		int result;

		if (nMoreCalls > 0) {
			result = this.methodWithRecursion(nMoreCalls - 1);

			/* Violates the postcondition. */
			// result++;
		}

		else {
			result = 0;
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model.ContextTestInterface
	 * #methodToTestBodyExpression()
	 */
	public void methodToTestBodyExpression() {

		/* No implementation required. */
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model.ContextTestInterface
	 * #methodToTestDefExpression()
	 */
	public void methodToTestDefExpression() {

		/* No implementation required. */
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model.ContextTestInterface
	 * #methodToTestDeriveExpression()
	 */
	public void methodToTestDeriveExpression() {

		/* No implementation required. */
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model.ContextTestInterface
	 * #methodToTestLetExpression()
	 */
	public void methodToTestLetExpression() {

		/* No implementation required. */
	}
}