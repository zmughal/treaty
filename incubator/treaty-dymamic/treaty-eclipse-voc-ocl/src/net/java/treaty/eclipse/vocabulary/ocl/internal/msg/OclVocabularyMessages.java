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
package net.java.treaty.eclipse.vocabulary.ocl.internal.msg;

import net.java.treaty.ContractVocabulary;

import org.eclipse.osgi.util.NLS;

/**
 * <p>
 * Constants to access localized messages for the OCL {@link ContractVocabulary}
 * .
 * </p>
 * 
 * @author Claas Wilke
 */
public class OclVocabularyMessages extends NLS {

	/* The canonical name of the property file. */
	private static final String PROPERTY_FILE =
			"net.java.treaty.eclipse.vocabulary.ocl.internal.msg.messages";

	/* Provided messages. */
	public static String INFO_NO_EXISTS_CONDITIONS_PROVIDED;
	public static String INFO_NO_PROPERTY_CONDITIONS_PROVIDED;

	public static String INFO_REALTIONSHIP_CONDITION_NOT_SUPPORTED;
	public static String INFO_TYPE_NOT_SUPPORTED;

	public static String ERROR_LOAD_RESOURCE_FAILED;

	public static String ERROR_LOAD_MODEL_WRONG_TYPE;
	public static String ERROR_LOAD_MODEL_FAILED;

	public static String ERROR_LOAD_JAVA_MODEL_NO_CLASS;
	public static String ERROR_LOAD_JAVA_MODEL_INVALID_LOCATION;
	public static String ERROR_LOAD_JAVA_MODEL_FAILED;

	public static String ERROR_LOAD_MODEL_INSTANCE_NO_ACTIVE_MODEL;
	public static String ERROR_LOAD_MODEL_INSTANCE_WRONG_TYPE;

	public static String ERROR_LOAD_JAVA_MODEL_INSTANCE_INVALID_LOCATION;

	public static String ERROR_LOAD_CONSTRAINTS_NO_ACTIVE_MODEL;
	public static String ERROR_LOAD_CONSTRAINTS_FAILED;

	public static String INFO_CHECK_IS_INSTANCE_OF_FAILED;
	public static String ERROR_CHECK_IS_INSTANCE_OF_NO_MODEL_INSTANCE;
	public static String ERROR_CHECK_IS_INSTANCE_OF_NO_MODEL;

	public static String INFO_CHECK_IS_CONTRACT_FULFILLED_FAILED;
	public static String ERROR_CHECK_IS_CONTRACT_FULFILLED_NO_CONSTRAINT_FILE;
	public static String ERROR_CHECK_IS_CONTRACT_FULFILLED_NO_MODEL_INSTANCE;
	public static String ERROR_CHECK_IS_CONTRACT_FULFILLED_METHOD_INVOCATION_FAILED;

	public static String ERROR_ADAPT_CONTEXT_NO_CONTEXT_FOR_WRONG_POINT_OF_EXECUTION;
	public static String ERROR_ADAPT_CONTEXT_NO_CLASS_FOR_MODEL_INSTACE_FOUND;
	public static String ERROR_ADAPT_CONTEXT_DURING_MODEL_OBJECT_INIALIZATION;
	public static String ERROR_ADAPT_CONTEXT_REFLECTION_INITIALIZATION_FAILED;
	public static String ERROR_ADAPT_CONTEXT_WRONG_TYPE_OF_CONTEXT;

	static {
		/* Initialize resource bundle. */
		NLS.initializeMessages(PROPERTY_FILE, OclVocabularyMessages.class);
	}

	private OclVocabularyMessages() {

		/* No implementation necessary. */
	}
}