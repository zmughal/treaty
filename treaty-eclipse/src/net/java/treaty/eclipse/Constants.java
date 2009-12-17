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

import net.java.treaty.Contract;
import net.java.treaty.ContractVocabulary;
import net.java.treaty.eclipse.exporter.Exporter;
import net.java.treaty.trigger.TriggerVocabulary;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;

/**
 * <p>
 * These interface is used to provide some Constant definitions.
 * </p>
 * 
 * @author Jens Dietrich
 */
public interface Constants {

	/**
	 * The ID of the {@link IExtensionPoint} that can be used to register
	 * {@link TriggerVocabulary}s.
	 */
	static final String ACTION_VOCABULARY_EXTENSION_POINT_ID =
			"net.java.treaty.eclipse.actionvocabulary";

	/**
	 * The suffix of the file where {@link Contract}s are located inside the same
	 * plug-in, whose {@link IExtensionPoint} they contract.
	 */
	static final String CONTRACT_LOCATION_SUFFIX = ".contract";

	/**
	 * The ID of the {@link IExtensionPoint} that can be used to register
	 * {@link Exporter}s.
	 */
	static final String EXPORTER_EXTENSION_POINT_ID =
			"net.java.treaty.eclipse.exporter";

	/**
	 * The prefix of the location where {@link Contract}s are located inside the
	 * same plug-in, whose {@link IExtensionPoint} they contract. Generally
	 * speaking, this leads to the folder inside the plug-in, where the
	 * {@link Contract} is located.
	 */
	static final String INTERNAL_CONTRACT_LOCATION_PREFIX = "/META-INF/";

	/** The id of the {@link IExtensionPoint} used to provide external contracts. */
	static final String LEGISLATOR_CONTRACT_EXTENSION_POINT_ID =
			"net.java.treaty.eclipse.contract";

	/**
	 * The name of the attribute of {@link IExtension}s used to provide external
	 * contracts that lead to the contract definition.
	 */
	static final String LEGISLATOR_CONTRACT_ATTRIBUTE_LOCATION = "location";

	/**
	 * The ID of the {@link IExtensionPoint} that can be used to register
	 * {@link TriggerVocabulary}s.
	 */
	static final String TRIGGER_VOCABULARY_EXTENSION_POINT_ID =
			"net.java.treaty.eclipse.triggervocabulary";

	/** Key used to annotate verification results. */
	public static final String VERIFICATION_RESULT =
			"net.java.treaty.eclipse.verification.result";

	/** Key used to annotate verification exceptions. */
	public static final String VERIFICATION_EXCEPTION =
			"net.java.treaty.eclipse.verification.exception";

	/**
	 * The ID of the {@link IExtensionPoint} that can be used to extend the
	 * {@link ContractVocabulary}.
	 */
	public static final String VOCABULARY_EXTENSION_POINT_ID =
			"net.java.treaty.eclipse.vocabulary";

	/**
	 * The attribute of the {@link IExtensionPoint} that can be used to extend the
	 * {@link ContractVocabulary} which provides the {@link ContractVocabulary}
	 * implementation.
	 */
	public static final String VOCABULARY_EXTENSION_POINT_CLASS_ATTRIBUTE =
			"class";
}