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
import net.java.treaty.TreatyException;
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
	 * Returns a description of the given action type (as a {@link URI}) or
	 * returns an empty {@link String} if no description has been set.
	 * 
	 * @param actionType
	 *          The action (as a {@link URI}) whose description shall be returned.
	 * @return The description or an empty {@link String}.
	 * @throws TreatyException
	 *           Thrown, if the given {@link URI} is not valid for this
	 *           {@link ActionVocabulary}
	 */
	public String getDescription(URI actionType) throws TreatyException;

	/**
	 * <p>
	 * Returns a {@link Set} containing the {@link URI}s of all action types this
	 * {@link ActionVocabulary} defines.
	 * </p>
	 * 
	 * @return A {@link Set} containing the {@link URI}s of all action types this
	 *         {@link ActionVocabulary} defines.
	 * @param TreatyException
	 *          Thrown if the initialization of this {@link ActionVocabulary}
	 *          failed.
	 */
	public Set<URI> getActionTypes() throws TreatyException;

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
	 * @throws TreatyException
	 *           Thrown if the given action does not exist in this
	 *           {@link ActionVocabulary}.
	 */
	public boolean isBeforeAction(URI actionType) throws TreatyException;

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
	 * @throws TreatyException
	 *           Thrown if the given action does not exist in this
	 *           {@link ActionVocabulary}.
	 */
	public boolean isAfterAction(URI actionType) throws TreatyException;

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
	 * @throws TreatyException
	 *           Thrown if the given action does not exist in this
	 *           {@link ActionVocabulary}.
	 */
	public boolean isDefaultOnFailure(URI actionType) throws TreatyException;

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
	 * @throws TreatyException
	 *           Thrown if the given action does not exist in this
	 *           {@link ActionVocabulary}.
	 */
	public boolean isDefaultOnSuccess(URI actionType) throws TreatyException;

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
	 * @param verificationReports
	 *          The {@link Set} of {@link VerificationReport}s describing the
	 *          {@link Contract}s that have been verified.
	 * @throws TreatyException
	 *           Thrown if the given action does not exist in this
	 *           {@link ActionVocabulary}.
	 */
	public void after(URI triggerType, URI actionType,
			Set<VerificationReport> verificationReports) throws TreatyException;

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
	 * @throws TreatyException
	 *           Thrown if the given action does not exist in this
	 *           {@link ActionVocabulary}.
	 */
	public void before(URI triggerType, URI actionType,
			Set<Contract> contractsToVerify) throws TreatyException;

	/**
	 * <p>
	 * Performs an action of the given type (as a {@link URI}) for a given
	 * {@link Contract} by a given {@link VerificationReport} after its
	 * verification.
	 * <p>
	 * 
	 * @param triggerType
	 *          The type of trigger that caused the verification.
	 * @param actionType
	 *          The type of the action to be performed.
	 * @param verificationReport
	 *          The {@link VerificationReport} of the {@link Contract}s
	 *          verification.
	 * @throws TreatyException
	 *           Thrown if the given action does not exist in this
	 *           {@link ActionVocabulary}.
	 */
	public void perform(URI triggerType, URI actionType,
			VerificationReport verificationReport) throws TreatyException;
}