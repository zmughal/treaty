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

/**
 * <p>
 * The {@link ActionRegistry} manages {@link ActionVocabulary}s.
 * </p>
 * 
 * @author Claas Wilke
 */
public class ActionRegistry {

	/** The {@link ActionVocabulary}s of this {@link ActionRegistry}. */
	private Set<ActionVocabulary> actionVocabularies =
			new HashSet<ActionVocabulary>();

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
	 */
	public ActionVocabulary getActionVocabulary(URI action) {

		ActionVocabulary result;
		result = null;

		for (ActionVocabulary actionVocabulary : this.actionVocabularies) {

			if (actionVocabulary.getActionTypes().contains(action)) {
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
	 * Returns a {@link Set} containing the {@link URI}s of all actions currently
	 * registered in this {@link ActionRegistry}.
	 * </p>
	 * 
	 * @return A {@link Set} containing the {@link URI}s of all actions currently
	 *         registered in this {@link ActionRegistry}.
	 */
	public Set<URI> getActions() {

		Set<URI> result;
		result = new HashSet<URI>();

		for (ActionVocabulary triggerVocabulary : this.actionVocabularies) {

			result.addAll(triggerVocabulary.getActionTypes());
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
	 * Returns all actions that shall be performed for the beginning of a
	 * verification. Because only default actions can be performed in this
	 * situation, no parameter is required.
	 * </p>
	 * 
	 * @return All actions that shall be performed for the beginning of a
	 *         verification as a {@link Set} of {@link URI}s.
	 */
	public Set<URI> getActionsOnBeginVerification() {

		Set<URI> result;
		result = new HashSet<URI>();

		for (ActionVocabulary actionVocabulary : this.actionVocabularies) {

			for (URI actionType : actionVocabulary.getActionTypes()) {

				if (actionVocabulary.isUniversalActionOnBeginVerification(actionType)) {
					result.add(actionType);
				}
				// no else.
			}
			// end for.
		}
		// end for.

		return result;
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
	 */
	public Set<URI> getActionsOnEndVerification() {

		Set<URI> result;
		result = new HashSet<URI>();

		for (ActionVocabulary actionVocabulary : this.actionVocabularies) {

			for (URI actionType : actionVocabulary.getActionTypes()) {

				if (actionVocabulary.isUniversalActionOnEndVerification(actionType)) {
					result.add(actionType);
				}
				// no else.
			}
			// end for.
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
	 */
	public Set<URI> getActionsOnFailure(Contract contract) {

		Set<URI> result;
		result = new HashSet<URI>();

		for (ActionVocabulary actionVocabulary : this.actionVocabularies) {

			for (URI actionType : actionVocabulary.getActionTypes()) {

				if (actionVocabulary.isUniversalActionOnFailure(actionType)
						|| contract.getOnVerificationFailsActions().contains(actionType)) {
					result.add(actionType);
				}
				// no else.
			}
			// end for.
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
	 */
	public Set<URI> getActionsOnSuccess(Contract contract) {

		Set<URI> result;
		result = new HashSet<URI>();

		for (ActionVocabulary actionVocabulary : this.actionVocabularies) {

			for (URI actionType : actionVocabulary.getActionTypes()) {

				if (actionVocabulary.isUniversalActionOnSuccess(actionType)
						|| contract.getOnVerificationSucceedsActions().contains(actionType)) {
					result.add(actionType);
				}
				// no else.
			}
			// end for.
		}
		// end for.

		return result;
	}

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
	public boolean isUniversalActionOnBeginVerification(URI actionType) {

		boolean result;

		ActionVocabulary actionVocabulary;
		actionVocabulary = this.getActionVocabulary(actionType);

		if (actionVocabulary != null) {
			result =
					actionVocabulary.isUniversalActionOnBeginVerification(actionType);
		}

		else {
			result = false;
		}

		return result;
	}

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
	public boolean isUniversalActionOnEndVerification(URI actionType) {

		boolean result;

		ActionVocabulary actionVocabulary;
		actionVocabulary = this.getActionVocabulary(actionType);

		if (actionVocabulary != null) {
			result = actionVocabulary.isUniversalActionOnEndVerification(actionType);
		}

		else {
			result = false;
		}

		return result;
	}

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
	public boolean isUniversalActionOnFailure(URI actionType) {

		boolean result;

		ActionVocabulary actionVocabulary;
		actionVocabulary = this.getActionVocabulary(actionType);

		if (actionVocabulary != null) {
			result = actionVocabulary.isUniversalActionOnFailure(actionType);
		}

		else {
			result = false;
		}

		return result;
	}

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
	public boolean isUniversalActionOnSuccess(URI actionType) {

		boolean result;

		ActionVocabulary actionVocabulary;
		actionVocabulary = this.getActionVocabulary(actionType);

		if (actionVocabulary != null) {
			result = actionVocabulary.isUniversalActionOnSuccess(actionType);
		}

		else {
			result = false;
		}

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