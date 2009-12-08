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
import net.java.treaty.VerificationReport;

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
	 * {@link Contract}s before starting their verification job.
	 * </p>
	 * 
	 * @param actionType
	 *          The type ({@link URI}) of the action.
	 * @return <code>true</code>, if the action is universal before starting a
	 *         verification job.
	 */
	public boolean isUniversalActionOnBeginVerification(URI actionType);

	/**
	 * <p>
	 * Returns <code>true</code> if the given {@link URI} represents an action
	 * defined by this {@link ActionVocabulary} which shall be executed for all
	 * {@link Contract}s after finishing a verification job.
	 * </p>
	 * 
	 * @param actionType
	 *          The type ({@link URI}) of the action.
	 * @return <code>true</code>, if the action is universal after finishing a
	 *         verification job.
	 */
	public boolean isUniversalActionOnEndVerification(URI actionType);

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
	 * {@link Set} of {@link Contract}s after their verification.
	 * <p>
	 * 
	 * @param triggerType
	 *          The type of trigger that caused the verification.
	 * @param actionType
	 *          The type of the action to be performed.
	 * @param contractsToVerify
	 *          The {@link Set} of {@link Contract}s that has been verified.
	 * @param failedContracts
	 *          The {@link Set} of {@link Contract}s whose verification failed.
	 */
	public void performActionAfterVerification(URI triggerType, URI actionType,
			Set<Contract> contractsToVerify, Set<Contract> failedContracts);

	/**
	 * <p>
	 * Performs an action of the given type (as a {@link URI}) for a given
	 * {@link Set} of {@link Contract}s before their verification.
	 * <p>
	 * 
	 * @param triggerType
	 *          The type of trigger that caused the verification.
	 * @param actionType
	 *          The type of the action to be performed.
	 * @param contractsToVerify
	 *          The {@link Set} of {@link Contract}s that shall be verified.
	 */
	public void performActionBeforeVerification(URI triggerType, URI actionType,
			Set<Contract> contractsToVerify);

	/**
	 * <p>
	 * Performs an action of the given type (as a {@link URI}) for a given
	 * {@link Contract} with a given {@link VerificationReport} whose verification
	 * failed.
	 * <p>
	 * 
	 * @param triggerType
	 *          The type of trigger that caused the verification.
	 * @param actionType
	 *          The type of the action to be performed.
	 * @param contract
	 *          The {@link Contract}s that has been verified.
	 * @param verificationReport
	 *          The {@link VerificationReport} of the {@link Contract}s
	 *          verification.
	 */
	public void performActionOnFailure(URI triggerType, URI actionType,
			Contract contract, VerificationReport verificationReport);

	/**
	 * <p>
	 * Performs an action of the given type (as a {@link URI}) for a given
	 * {@link Contract} with a given {@link VerificationReport} whose verification
	 * succeeded.
	 * <p>
	 * 
	 * @param triggerType
	 *          The type of trigger that caused the verification.
	 * @param actionType
	 *          The type of the action to be performed.
	 * @param contract
	 *          The {@link Contract}s that has been verified.
	 * @param verificationReport
	 *          The {@link VerificationReport} of the {@link Contract}s
	 *          verification.
	 */
	public void performActionOnSuccess(URI triggerType, URI actionType,
			Contract contract, VerificationReport verificationReport);
}