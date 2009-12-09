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

import net.java.treaty.Contract;
import net.java.treaty.TreatyException;

/**
 * <p>
 * The {@link TriggerRegistry} manages {@link TriggerVocabulary}s.
 * </p>
 * 
 * @author Claas Wilke
 */
public class TriggerRegistry implements TriggerVocabulary {

	/**
	 * The {@link EventListener}s of this {@link TriggerRegistry} .
	 */
	private Set<EventListener> eventlisteners = new HashSet<EventListener>();

	/**
	 * The {@link TriggerRegistryListener}s of this {@link TriggerRegistry} .
	 */
	private Set<TriggerRegistryListener> registrylisteners =
			new HashSet<TriggerRegistryListener>();

	/** The {@link TriggerVocabulary}s of this {@link TriggerRegistry}. */
	private Set<TriggerVocabulary> triggerVocabularies =
			new HashSet<TriggerVocabulary>();

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.trigger.EventProvider#addEventListener(net.java.treaty.
	 * trigger.EventListener)
	 */
	public boolean addEventListener(EventListener listener) {

		return this.eventlisteners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.trigger.TriggerVocabulary#getTriggerTypes()
	 */
	public Set<URI> getTriggers() throws TreatyException {

		Set<URI> result;
		result = new HashSet<URI>();

		for (TriggerVocabulary triggerVocabulary : this.triggerVocabularies) {

			result.addAll(triggerVocabulary.getTriggers());
		}
		// end for.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.trigger.TriggerVocabulary#getSubTriggers(java.net.URI)
	 */
	public Set<URI> getSubTriggers(URI triggerType) throws TreatyException {

		TriggerVocabulary triggerVocabulary;
		triggerVocabulary = this.getTriggerVocabulary(triggerType);

		return triggerVocabulary.getSubTriggers(triggerType);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.trigger.TriggerVocabulary#getSuperTriggers(java.net.URI)
	 */
	public Set<URI> getSuperTriggers(URI triggerType) throws TreatyException {

		TriggerVocabulary triggerVocabulary;
		triggerVocabulary = this.getTriggerVocabulary(triggerType);

		return triggerVocabulary.getSubTriggers(triggerType);
	}

	/**
	 * <p>
	 * Evaluates to <code>true</code> if the given trigger type represents a
	 * default trigger.
	 * </p>
	 * 
	 * @see TriggerVocabulary#isDefaultTrigger(URI)
	 * 
	 * @param triggerType
	 *          The type ({@link URI}) of the trigger that shall be checked.
	 * @return <code>true</code> if the trigger is a default trigger.
	 */
	public boolean isDefaultTrigger(URI triggerType) throws TreatyException {

		boolean result;

		TriggerVocabulary triggerVocabulary;
		triggerVocabulary = this.getTriggerVocabulary(triggerType);

		if (triggerVocabulary != null) {
			result = triggerVocabulary.isDefaultTrigger(triggerType);
		}

		else {
			throw new TreatyException("Unknown trigger type: " + triggerType);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.trigger.EventProvider#notifyEventListners(java.net.URI,
	 * java.util.Set)
	 */
	public void notifyEventListners(URI triggerType, Set<Contract> contracts) {

		for (EventListener listener : this.eventlisteners) {
			listener.update(triggerType, contracts);
		}
		// no else.
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.trigger.EventProvider#removeEventListener(net.java.treaty
	 * .trigger.EventListener)
	 */
	public boolean removeEventListener(EventListener listener) {

		return this.eventlisteners.remove(listener);
	}

	/**
	 * <p>
	 * Adds a new Listener to this {@link TriggerRegistry}.
	 * </p>
	 * 
	 * @param listener
	 *          The {@link TriggerRegistryListener} that shall be added.
	 */
	public void addListener(TriggerRegistryListener listener) {

		this.registrylisteners.add(listener);
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
	 * Returns the {@link TriggerVocabulary} that defines a given trigger (by its
	 * {@link URI})..
	 * </p>
	 * 
	 * @param triggerType
	 *          The trigger type whose {@link TriggerVocabulary} shall be
	 *          returned.
	 * @return The defining {@link TriggerVocabulary}.
	 * @throws TreatyException
	 *           Thrown if the given trigger cannot be found.
	 */
	public TriggerVocabulary getTriggerVocabulary(URI triggerType)
			throws TreatyException {

		TriggerVocabulary result;
		result = null;

		for (TriggerVocabulary triggerVocabulary : this.triggerVocabularies) {

			if (triggerVocabulary.getTriggers().contains(triggerType)) {
				result = triggerVocabulary;
				break;
			}
			// no else.
		}
		// end for.

		if (result == null) {
			throw new TreatyException("The given trigger " + triggerType
					+ " cannot be found.");
		}

		return result;
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

		this.registrylisteners.add(listener);
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

		for (TriggerRegistryListener listener : this.registrylisteners) {
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

		for (TriggerRegistryListener listener : this.registrylisteners) {
			listener.triggerVocabularyRemoved(triggerVocabulary);
		}
		// end for.
	}
}