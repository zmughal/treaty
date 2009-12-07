/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.trigger;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * The {@link TriggerRegistry} manages {@link TriggerVocabulary}s.
 * </p>
 * 
 * @author Claas Wilke
 */
public class TriggerRegistry {

	/**
	 * The {@link TriggerRegistryListener}s of this {@link TriggerRegistry} .
	 */
	private Set<TriggerRegistryListener> listeners =
			new HashSet<TriggerRegistryListener>();

	/** The {@link TriggerVocabulary}s of this {@link TriggerRegistry}. */
	private Set<TriggerVocabulary> triggerVocabularies =
			new HashSet<TriggerVocabulary>();

	/**
	 * <p>
	 * Adds a new Listener to this {@link TriggerRegistry}.
	 * </p>
	 * 
	 * @param listener
	 *          The {@link TriggerRegistryListener} that shall be added.
	 */
	public void addListener(TriggerRegistryListener listener) {

		this.listeners.add(listener);
	}

	/**
	 * <p>
	 * Adds a new {@link TriggerVocabulary} to this {@link TriggerRegistry}.
	 * </p>
	 * 
	 * @param triggerVocabulary
	 *          The {@link TriggerVocabulary} that shall be added.
	 * @return <code>true</code> if the {@link TriggerVocabulary} has been added
	 *         successfully.
	 */
	public boolean addTriggerVocabulary(TriggerVocabulary triggerVocabulary) {

		boolean result;

		result = this.triggerVocabularies.add(triggerVocabulary);

		if (result) {
			this.notifiyAddedTriggerVocabulary(triggerVocabulary);
		}
		// no else.

		return result;
	}

	/**
	 * <p>
	 * Returns a {@link Set} containing the {@link URI}s of all triggers currently
	 * registered in this {@link TriggerRegistry}.
	 * </p>
	 * 
	 * @return A {@link Set} containing the {@link URI}s of all triggers currently
	 *         registered in this {@link TriggerRegistry}.
	 */
	public Set<URI> getTriggers() {

		Set<URI> result;
		result = new HashSet<URI>();

		for (TriggerVocabulary triggerVocabulary : this.triggerVocabularies) {

			result.addAll(triggerVocabulary.getTriggerTypes());
		}
		// end for.

		return result;
	}

	/**
	 * <p>
	 * Returns all {@link TriggerVocabulary}s of this {@link TriggerRegistry}.
	 * </p>
	 * 
	 * @return All {@link TriggerVocabulary}s of this {@link TriggerRegistry}.
	 */
	public Set<TriggerVocabulary> getTriggerVocabularies() {

		return this.triggerVocabularies;
	}

	/**
	 * <p>
	 * Removes a Listener from this {@link TriggerRegistry}.
	 * </p>
	 * 
	 * @param listener
	 *          The {@link TriggerRegistryListener} that shall be removed.
	 */
	public void removeListener(TriggerRegistryListener listener) {

		this.listeners.add(listener);
	}

	/**
	 * <p>
	 * Removes a {@link TriggerVocabulary} from this {@link TriggerRegistry}.
	 * </p>
	 * 
	 * @param triggerVocabulary
	 *          The {@link TriggerVocabulary} that shall be removed.
	 * @return <code>true</code> if the {@link TriggerVocabulary} has been removed
	 *         successfully.
	 */
	public boolean removeTriggerVocabulary(TriggerVocabulary triggerVocabulary) {

		boolean result;

		result = this.triggerVocabularies.remove(triggerVocabulary);

		if (result) {
			this.notifiyRemovedTriggerVocabulary(triggerVocabulary);
		}
		// no else.

		return result;
	}

	/**
	 * <p>
	 * Notifies all registered {@link TriggerRegistryListener}s, that a
	 * {@link TriggerVocabulary} has been added to this {@link TriggerRegistry}.
	 * </p>
	 * 
	 * @param triggerVocabulary
	 *          The {@link TriggerVocabulary} that has been added.
	 */
	private void notifiyAddedTriggerVocabulary(TriggerVocabulary triggerVocabulary) {

		for (TriggerRegistryListener listener : this.listeners) {
			listener.triggerVocabularyAdded(triggerVocabulary);
		}
		// end for.
	}

	/**
	 * <p>
	 * Notifies all registered {@link TriggerRegistryListener}s, that a
	 * {@link TriggerVocabulary} has been removed to this {@link TriggerRegistry}.
	 * </p>
	 * 
	 * @param exporter
	 *          The {@link Exporter} that has been removed.
	 */
	private void notifiyRemovedTriggerVocabulary(
			TriggerVocabulary triggerVocabulary) {

		for (TriggerRegistryListener listener : this.listeners) {
			listener.triggerVocabularyRemoved(triggerVocabulary);
		}
		// end for.
	}
}