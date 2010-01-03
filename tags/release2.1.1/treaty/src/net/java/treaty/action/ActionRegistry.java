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
import java.util.HashSet;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.TreatyException;
import net.java.treaty.VerificationReport;

/**
 * <p>
 * The {@link ActionRegistry} manages {@link ActionVocabulary}s.
 * </p>
 * 
 * @author Claas Wilke
 */
public class ActionRegistry implements ActionVocabulary {

	/** The {@link ActionVocabulary}s of this {@link ActionRegistry}. */
	private Set<ActionVocabulary> actionVocabularies =
			new HashSet<ActionVocabulary>();

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#after(java.net.URI,
	 * java.net.URI, java.util.Set)
	 */
	public void after(URI triggerType, URI actionType,
			Set<VerificationReport> verificationReports) throws TreatyException {

		this.getActionVocabulary(actionType).after(triggerType, actionType,
				verificationReports);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#before(java.net.URI,
	 * java.net.URI, java.util.Set)
	 */
	public void before(URI triggerType, URI actionType,
			Set<Contract> contractsToVerify) throws TreatyException {

		this.getActionVocabulary(actionType).before(triggerType, actionType,
				contractsToVerify);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#getActionTypes()
	 */
	public Set<URI> getActions() throws TreatyException {

		Set<URI> result;
		result = new HashSet<URI>();

		for (ActionVocabulary triggerVocabulary : this.actionVocabularies) {

			result.addAll(triggerVocabulary.getActions());
		}
		// end for.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#getDescription(java.net.URI)
	 */
	public String getDescription(URI actionType) throws TreatyException {

		return this.getActionVocabulary(actionType).getDescription(actionType);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#isAfterAction(java.net.URI)
	 */
	public boolean isAfterAction(URI actionType) throws TreatyException {

		boolean result;

		ActionVocabulary actionVocabulary;
		actionVocabulary = this.getActionVocabulary(actionType);

		if (actionVocabulary != null) {
			result = actionVocabulary.isAfterAction(actionType);
		}

		else {
			result = false;
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#isBeforeAction(java.net.URI)
	 */
	public boolean isBeforeAction(URI actionType) throws TreatyException {

		boolean result;

		ActionVocabulary actionVocabulary;
		actionVocabulary = this.getActionVocabulary(actionType);

		if (actionVocabulary != null) {
			result = actionVocabulary.isBeforeAction(actionType);
		}

		else {
			result = false;
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.action.ActionVocabulary#isDefaultOnFailure(java.net.URI)
	 */
	public boolean isDefaultOnFailure(URI actionType) throws TreatyException {

		boolean result;

		ActionVocabulary actionVocabulary;
		actionVocabulary = this.getActionVocabulary(actionType);

		if (actionVocabulary != null) {
			result = actionVocabulary.isDefaultOnFailure(actionType);
		}

		else {
			result = false;
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.action.ActionVocabulary#isDefaultOnSuccess(java.net.URI)
	 */
	public boolean isDefaultOnSuccess(URI actionType) throws TreatyException {

		boolean result;

		ActionVocabulary actionVocabulary;
		actionVocabulary = this.getActionVocabulary(actionType);

		if (actionVocabulary != null) {
			result = actionVocabulary.isDefaultOnSuccess(actionType);
		}

		else {
			result = false;
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#perform(java.net.URI,
	 * java.net.URI, net.java.treaty.VerificationReport)
	 */
	public void perform(URI triggerType, URI actionType,
			VerificationReport verificationReport) throws TreatyException {

		this.getActionVocabulary(actionType).perform(triggerType, actionType,
				verificationReport);
	}

	/**
	 * <p>
	 * Adds a new {@link ActionVocabulary} to this {@link ActionRegistry}.
	 * </p>
	 * 
	 * @param actionVocabulary
	 *          The {@link ActionVocabulary} that shall be added.
	 * @return <code>true</code> if the {@link ActionVocabulary} has been added
	 *         successfully.
	 */
	public boolean addActionVocabulary(ActionVocabulary actionVocabulary) {

		return this.actionVocabularies.add(actionVocabulary);
	}

	/**
	 * <p>
	 * Returns the {@link ActionVocabulary} that defines a given action.
	 * </p>
	 * 
	 * @param action
	 *          The type {@link URI} of action whose {@link ActionVocabulary}
	 *          shall be returned.
	 * @return The found {@link ActionVocabulary} or <code>null</code>.
	 * @throws TreatyException
	 *           Thrown if the given action does not exist in this
	 *           {@link ActionVocabulary}.
	 */
	public ActionVocabulary getActionVocabulary(URI action)
			throws TreatyException {

		ActionVocabulary result;
		result = null;

		for (ActionVocabulary actionVocabulary : this.actionVocabularies) {

			if (actionVocabulary.getActions().contains(action)) {
				result = actionVocabulary;
				break;
			}
			// no else.
		}
		// end for.

		return result;
	}

	/**
	 * <p>
	 * Returns all {@link ActionVocabulary}s of this {@link ActionRegistry}.
	 * </p>
	 * 
	 * @return All {@link ActionVocabulary}s of this {@link ActionRegistry}.
	 */
	public Set<ActionVocabulary> getActionVocabularies() {

		return this.actionVocabularies;
	}

	/**
	 * <p>
	 * Returns all actions that shall be performed after finishing a verification.
	 * Because only default actions can be performed in this situation, no
	 * parameter is required.
	 * </p>
	 * 
	 * @return All actions that shall be performed after finishing a verification
	 *         as a {@link Set} of {@link URI}s.
	 * @throws TreatyException
	 *           Throw, if the initialization of the {@link ActionRegistry} or at
	 *           least one of its {@link ActionVocabulary}s failed.
	 */
	public Set<URI> getAfterActions() throws TreatyException {

		Set<URI> result;
		result = new HashSet<URI>();

		for (URI actionType : this.getActions()) {

			if (this.isAfterAction(actionType)) {
				result.add(actionType);
			}
			// no else.
		}
		// end for.

		return result;
	}

	/**
	 * <p>
	 * Returns all actions that shall be performed for the beginning of a
	 * verification. Because only default actions can be performed in this
	 * situation, no parameter is required.
	 * </p>
	 * 
	 * @return All actions that shall be performed for the beginning of a
	 *         verification as a {@link Set} of {@link URI}s.
	 * @throws TreatyException
	 *           Throw, if the initialization of the {@link ActionRegistry} or at
	 *           least one of its {@link ActionVocabulary}s failed.
	 */
	public Set<URI> getBeforeActions() throws TreatyException {

		Set<URI> result;
		result = new HashSet<URI>();

		for (URI actionType : this.getActions()) {

			if (this.isBeforeAction(actionType)) {
				result.add(actionType);
			}
			// no else.
		}
		// end for.

		return result;
	}

	/**
	 * <p>
	 * Returns all actions that shall be performed after finishing a
	 * {@link Contract}'s verification not successfully.
	 * </p>
	 * 
	 * @param The
	 *          {@link Contract} for which the actions shall be returned.
	 * @return All actions that shall be performed after finishing a
	 *         {@link Contract}'s verification not successfully.
	 * @throws TreatyException
	 *           Throw, if the initialization of the {@link ActionRegistry} or at
	 *           least one of its {@link ActionVocabulary}s failed.
	 */
	public Set<URI> getOnFailureActions(Contract contract) throws TreatyException {

		Set<URI> result;
		result = new HashSet<URI>();

		for (URI actionType : this.getActions()) {

			if (this.isDefaultOnFailure(actionType)
					|| contract.getOnVerificationFailsActions().contains(actionType)) {
				result.add(actionType);
			}
			// no else.
		}
		// end for.

		return result;
	}

	/**
	 * <p>
	 * Returns all actions that shall be performed after finishing a
	 * {@link Contract}'s verification successfully.
	 * </p>
	 * 
	 * @param The
	 *          {@link Contract} for which the actions shall be returned.
	 * @return All actions that shall be performed after finishing a
	 *         {@link Contract}'s verification successfully.
	 * @throws TreatyException
	 *           Throw, if the initialization of the {@link ActionRegistry} or at
	 *           least one of its {@link ActionVocabulary}s failed.
	 */
	public Set<URI> getOnSuccessActions(Contract contract) throws TreatyException {

		Set<URI> result;
		result = new HashSet<URI>();

		for (URI actionType : this.getActions()) {

			if (this.isDefaultOnSuccess(actionType)
					|| contract.getOnVerificationSucceedsActions().contains(actionType)) {
				result.add(actionType);
			}
			// no else.
		}
		// end for.

		return result;
	}

	/**
	 * <p>
	 * Removes a {@link ActionVocabulary} from this {@link ActionRegistry}.
	 * </p>
	 * 
	 * @param actionVocabulary
	 *          The {@link ActionVocabulary} that shall be removed.
	 * @return <code>true</code> if the {@link ActionVocabulary} has been removed
	 *         successfully.
	 */
	public boolean removeActionVocabulary(ActionVocabulary actionVocabulary) {

		return this.actionVocabularies.remove(actionVocabulary);
	}
}