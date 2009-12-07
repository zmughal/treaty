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