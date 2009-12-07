/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.action;

import java.net.URI;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.VerificationResult;

/**
 * <p>
 * An {@link ActionVocabulary} provides action types that can be used to perform
 * specific actions after a {@link Contract}s verification has been successfull
 * or has failed.
 * </p>
 * 
 * @author Claas Wilke
 */
public interface ActionVocabulary {

	/**
	 * <p>
	 * Returns a {@link Set} containing the {@link URI}s of all action types this
	 * {@link ActionVocabulary} defines.
	 * </p>
	 * 
	 * @return A {@link Set} containing the {@link URI}s of all action types this
	 *         {@link ActionVocabulary} defines.
	 */
	public Set<URI> getActionTypes();

	/**
	 * <p>
	 * Returns <code>true</code> if the given {@link URI} represents an action
	 * defined by this {@link ActionVocabulary} which shall be executed for all
	 * {@link Contract}s after unsuccessful verification ignoring the fact,
	 * whether or not the action is defined by the {@link Contract} itself.
	 * </p>
	 * 
	 * @param actionType
	 *          The type ({@link URI}) of the action.
	 * @return <code>true</code>, if the action is universal for unsuccessful
	 *         verification results.
	 */
	public boolean isUniversalActionOnFailure(URI actionType);

	/**
	 * <p>
	 * Returns <code>true</code> if the given {@link URI} represents an action
	 * defined by this {@link ActionVocabulary} which shall be executed for all
	 * {@link Contract}s after successful verification ignoring the fact, whether
	 * or not the action is defined by the {@link Contract} itself.
	 * </p>
	 * 
	 * @param actionType
	 *          The type ({@link URI}) of the action.
	 * @return <code>true</code>, if the action is universal for successful
	 *         verification results.
	 */
	public boolean isUniversalActionOnSuccess(URI actionType);

	/**
	 * <p>
	 * Performs an action of the given type (as a {@link URI}) for a given
	 * {@link Contract} with a given {@link VerificationResult}.
	 * <p>
	 * 
	 * @param actionType
	 *          The type of the action to be performed.
	 * @param contract
	 *          The {@link Contract} that has been verified.
	 * @param verificationResult
	 *          The {@link VerificationResult} of the {@link Contract}.
	 */
	public void performAction(URI actionType, Contract contract,
			VerificationResult verificationResult);
}