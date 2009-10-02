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

import static org.junit.Assert.fail;

import java.lang.reflect.Method;

import net.java.treaty.dynamic.IDynamicContractVocabulary;
import net.java.treaty.eclipse.vocabulary.ocl.OCLVocabulary;
import net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model.BadTestClass;
import net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model.GoodTestClass;
import net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model.TestInterface;

import org.junit.Test;

import tudresden.ocl20.pivot.pivotmodel.Constraint;

/**
 * <p>
 * Contains test cases that test the {@link IDynamicContractVocabulary}
 * implementation of the {@link OCLVocabulary}.
 * </p>
 * 
 * <p>
 * FIXME Claas: The test suite checks the following verification scenarios (or
 * combinations of them):
 * </p>
 * 
 * <ul>
 * <li>Tested Types of {@link Method}:
 * <ul>
 * <li>Method invocation without any argument</li>
 * <li>Method invocation with one argument</li>
 * <li>Method invocation with two arguments</li>
 * <li>Method invocation with no return type (void)</li>
 * <li>Method invocation with result</li>
 * <li>Method invocation with multiple output values</li>
 * <li>Method invocation using recursive method calls internally</li>
 * </ul>
 * </li>
 * 
 * <li>Tested Types of {@link Constraint}:
 * <ul>
 * <li>Verification of invariants</li>
 * <li>Verification of preconditions</li>
 * <li>TODO Verification of preconditions with LSP</li>
 * <li>Verification of postconditions</li>
 * <li>Verification of postconditions with special operations like atPre, TODO
 * allInstances, TODO oclIsNew.</li>
 * <li>Verification of constraints using body expressions.</li>
 * <li>Verification of constraints using definitions.</li>
 * <li>Verification of constraints using derive expressions.</li>
 * <li>Verification of constraints using let expressions.</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author Claas Wilke
 */
public class TestDynamicVerification {

	/**
	 * <p>
	 * Tests the contract verification of the method
	 * {@link TestInterface#methodWithoutArguments()}.
	 * </p>
	 */
	@Test
	public void testMethodWithoutArguments() {

		TestInterface goodClass;
		TestInterface badClass;

		goodClass = new GoodTestClass();
		badClass = new BadTestClass();

		/* Should not violated the contract. */
		goodClass.methodWithoutArguments();

		/* Should violate the contract. */
		try {
			String msg;

			badClass.methodWithoutArguments();

			/* Should not happen. */
			msg = "An expected VerificationException has not been thrown.";
			fail(msg);
		}

		catch (DynamicOclVocabularyTestException e) {
			/* Expected exception do nothing. */
		}
	}

	/**
	 * <p>
	 * Tests the contract verification of the method
	 * {@link TestInterface#methodWithOneArgument(Object)}.
	 * </p>
	 */
	@Test
	public void testMethodWithOneArgument() {

		TestInterface goodClass;
		TestInterface badClass;

		goodClass = new GoodTestClass();
		badClass = new BadTestClass();

		/* Should violate the contract. */
		try {
			String msg;

			goodClass.methodWithOneArgument(null);

			/* Should not happen. */
			msg = "An expected VerificationException has not been thrown.";
			fail(msg);
		}

		catch (DynamicOclVocabularyTestException e) {
			/* Expected exception do nothing. */
		}

		/* Should not violated the contract. */
		goodClass.methodWithOneArgument(1);

		/* Should violate the contract. */
		try {
			String msg;

			badClass.methodWithOneArgument(1);

			/* Should not happen. */
			msg = "An expected VerificationException has not been thrown.";
			fail(msg);
		}

		catch (DynamicOclVocabularyTestException e) {
			/* Expected exception do nothing. */
		}
	}

	/**
	 * <p>
	 * Tests the contract verification of the method
	 * {@link TestInterface#methodWithTwoArguments(String, String)}.
	 * </p>
	 */
	@Test
	public void testMethodWithTwoArguments() {

		TestInterface goodClass;
		TestInterface badClass;

		goodClass = new GoodTestClass();
		badClass = new BadTestClass();

		/* Should violate the contract. */
		try {
			String msg;

			goodClass.methodWithTwoArguments(null, null);

			/* Should not happen. */
			msg = "An expected VerificationException has not been thrown.";
			fail(msg);
		}

		catch (DynamicOclVocabularyTestException e) {
			/* Expected exception do nothing. */
		}

		/* Should violate the contract. */
		try {
			String msg;

			goodClass.methodWithTwoArguments("Fourty", null);

			/* Should not happen. */
			msg = "An expected VerificationException has not been thrown.";
			fail(msg);
		}

		catch (DynamicOclVocabularyTestException e) {
			/* Expected exception do nothing. */
		}

		/* Should violate the contract. */
		try {
			String msg;

			goodClass.methodWithTwoArguments(null, "Two");

			/* Should not happen. */
			msg = "An expected VerificationException has not been thrown.";
			fail(msg);
		}

		catch (DynamicOclVocabularyTestException e) {
			/* Expected exception do nothing. */
		}

		/* Should not violated the contract. */
		goodClass.methodWithTwoArguments("Fourty", "Two");

		/* Should violate the contract. */
		try {
			String msg;

			badClass.methodWithTwoArguments("Fourty", "Two");

			/* Should not happen. */
			msg = "An expected VerificationException has not been thrown.";
			fail(msg);
		}

		catch (DynamicOclVocabularyTestException e) {
			/* Expected exception do nothing. */
		}
	}

	/**
	 * <p>
	 * Tests the contract verification of the method
	 * {@link TestInterface#methodWithoutResult()}.
	 * </p>
	 */
	@Test
	public void testMethodWithoutResult() {

		TestInterface goodClass;
		TestInterface badClass;

		goodClass = new GoodTestClass();
		badClass = new BadTestClass();

		/* Should violate the contract. */
		try {
			String msg;

			badClass.methodWithoutResult();

			/* Should not happen. */
			msg = "An expected VerificationException has not been thrown.";
			fail(msg);
		}

		catch (DynamicOclVocabularyTestException e) {
			/* Expected exception do nothing. */
		}

		/* Should not violate the contract. */
		goodClass.methodWithoutResult();
	}

	/**
	 * <p>
	 * Tests the contract verification of the method
	 * {@link TestInterface#methodWithMultipleResults()}.
	 * </p>
	 */
	@Test
	public void testMethodWithMultipleResults() {

		TestInterface goodClass;
		TestInterface badClass;

		goodClass = new GoodTestClass();
		badClass = new BadTestClass();

		/* Should violate the contract. */
		try {
			String msg;

			badClass.methodWithMultipleResults();

			/* Should not happen. */
			msg = "An expected VerificationException has not been thrown.";
			fail(msg);
		}

		catch (DynamicOclVocabularyTestException e) {
			/* Expected exception do nothing. */
		}

		/* Should not violate the contract. */
		goodClass.methodWithMultipleResults();
	}

	/**
	 * <p>
	 * Tests the contract verification of the method
	 * {@link TestInterface#methodWithRecursion(int)}.
	 * </p>
	 */
	@Test
	public void testMethodWithRescursion() {

		TestInterface goodClass;
		TestInterface badClass;

		goodClass = new GoodTestClass();
		badClass = new BadTestClass();

		/* Should violate the contract. */
		try {
			String msg;

			goodClass.methodWithRecursion(-1);

			/* Should not happen. */
			msg = "An expected VerificationException has not been thrown.";
			fail(msg);
		}

		catch (DynamicOclVocabularyTestException e) {
			/* Expected exception do nothing. */
		}

		/* Should violate the contract. */
		try {
			String msg;

			badClass.methodWithRecursion(2);

			/* Should not happen. */
			msg = "An expected VerificationException has not been thrown.";
			fail(msg);
		}

		catch (DynamicOclVocabularyTestException e) {
			/* Expected exception do nothing. */
		}

		/* Should not violate the contract. */
		goodClass.methodWithRecursion(3);
	}

	/**
	 * <p>
	 * Tests the contract verification of the method
	 * {@link TestInterface#methodToTestBodyExpression()}.
	 * </p>
	 */
	@Test
	public void testBodyExpression() {

		TestInterface goodClass;

		goodClass = new GoodTestClass();

		/* Should not violate the contract. */
		goodClass.methodToTestBodyExpression();
	}

	/**
	 * <p>
	 * Tests the contract verification of the method
	 * {@link TestInterface#methodToTestDefExpression()}.
	 * </p>
	 */
	@Test
	public void testDefExpression() {

		TestInterface goodClass;

		goodClass = new GoodTestClass();

		/* Should not violate the contract. */
		goodClass.methodToTestDefExpression();
	}

	/**
	 * <p>
	 * Tests the contract verification of the method
	 * {@link TestInterface#methodToTestDeriveExpression()}.
	 * </p>
	 */
	@Test
	public void testDeriveExpression() {

		TestInterface goodClass;

		goodClass = new GoodTestClass();

		/* Should not violate the contract. */
		goodClass.methodToTestDeriveExpression();
	}

	/**
	 * <p>
	 * Tests the contract verification of the method
	 * {@link TestInterface#methodToTestLetExpression()}.
	 * </p>
	 */
	@Test
	public void testLetExpression() {

		TestInterface goodClass;

		goodClass = new GoodTestClass();

		/* Should not violate the contract. */
		goodClass.methodToTestLetExpression();
	}
}