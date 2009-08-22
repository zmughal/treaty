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

import net.java.treaty.RelationshipCondition;
import net.java.treaty.dynamic.IDynamicContractVocabulary;
import net.java.treaty.dynamic.OperationInvocationContext;
import net.java.treaty.dynamic.LifecycleEvent;
import net.java.treaty.eclipse.vocabulary.ocl.OCLVocabulary;
import net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model.BadTestClass;
import net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model.TestInterface;
import net.java.treaty.eclipse.vocabulary.ocl.test.dynamic.model.GoodTestClass;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

/**
 * <p>
 * This class is responsible to collect the context required to verify the
 * constraints on the test model using the {@link OCLVocabulary}.
 * </p>
 * 
 * <p>
 * This functionality should be implemented by Treaty in the future.
 * </p>
 * 
 * @author Claas Wilke
 */
public aspect ContextCollector {

	/**
	 * <p>
	 * Verifies the contract before a method's invocation.
	 * </p>
	 */
	before() : call(* TestInterface.*(..)) {

		OperationInvocationContext context;

		context = ContextCollector.createContext(thisJoinPoint, null);

		/* Get the vocabulary and check the contract. */
		try {
			IDynamicContractVocabulary oclVocabulary;
			RelationshipCondition condition;

			oclVocabulary =
					DynamicOclVocabularyTestServices.getInstance().getOclVocabulary();
			condition = loadRelationship(context.getSource());

			oclVocabulary.check(condition, LifecycleEvent.INTERFACE_INVOCATION,
					context);
		}

		catch (Exception e) {

			throw new DynamicOclVocabularyTestException(e);
		}
	}

	/**
	 * <p>
	 * Verifies the contract after a method's invocation.
	 * </p>
	 */
	after() returning (Object result): call(* TestInterface.*(..)) {

		OperationInvocationContext context;

		context = ContextCollector.createContext(thisJoinPoint, result);

		/* Get the vocabulary and check the contract. */
		try {
			IDynamicContractVocabulary oclVocabulary;
			RelationshipCondition condition;

			oclVocabulary =
					DynamicOclVocabularyTestServices.getInstance().getOclVocabulary();
			condition = loadRelationship(context.getSource());

			oclVocabulary.check(condition,
					LifecycleEvent.INTERFACE_INVOCATION_RETURN, context);
		}

		catch (Exception e) {

			throw new DynamicOclVocabularyTestException(e);
		}
	}

	/**
	 * <p>
	 * A helper method that creates the {@link OperationInvocationContext} to a
	 * given {@link JoinPoint} and eventually the result of the operation's
	 * invocation.
	 * </p>
	 * 
	 * @param joinPoint
	 *          The {@link JoinPoint} the {@link OperationInvocationContext} shall
	 *          be created for.
	 * @param invocationResult
	 *          The result of the operation's invocation or <code>null</code>.
	 * @return The created {@link OperationInvocationContext}.
	 */
	@SuppressWarnings("unused")
	private static OperationInvocationContext createContext(JoinPoint joinPoint,
			Object invocationResult) {

		OperationInvocationContext result;

		String signature;
		Object source;
		Object[] arguments;

		Class<?>[] argumentTypes;
		String[] argumentTypeNames;

		int index;

		signature = joinPoint.getSignature().getName();
		source = joinPoint.getTarget();
		arguments = joinPoint.getArgs();
		argumentTypes =
				((CodeSignature) joinPoint.getSignature()).getParameterTypes();

		argumentTypeNames = new String[argumentTypes.length];

		index = 0;
		for (Class<?> anArgumentType : argumentTypes) {
			argumentTypeNames[index] = anArgumentType.getCanonicalName();
			index++;
		}

		result =
				new OperationInvocationContext(signature, source, arguments,
						argumentTypeNames, invocationResult);

		return result;
	}

	/**
	 * <p>
	 * A helper method to load the {@link RelationshipCondition} that shall be
	 * verified.
	 * </p>
	 * 
	 * @param source
	 *          The source, whose {@link RelationshipCondition} shall be checked.
	 * @return The {@link RelationshipCondition} that shall be verified.
	 * @throws Exception
	 *           Thrown, if an error during {@link RelationshipCondition} loading
	 *           occurs.
	 */
	private static RelationshipCondition loadRelationship(Object source)
			throws Exception {

		RelationshipCondition condition;
		if (source instanceof GoodTestClass) {
			condition =
					DynamicOclVocabularyTestServices.getInstance()
							.getRelationshipGoodContract();
		}

		else if (source instanceof BadTestClass) {
			condition =
					DynamicOclVocabularyTestServices.getInstance()
							.getRelationshipBadContract();
		}

		else {
			String msg;

			msg = "Unknown kind of model instance to be verified. ";
			msg = "Instance was of class: " + source.getClass().getCanonicalName();

			throw new DynamicOclVocabularyTestException(msg);
		}

		return condition;
	}
}