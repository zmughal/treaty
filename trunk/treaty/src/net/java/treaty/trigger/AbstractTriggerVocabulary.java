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

/**
 * <p>
 * The {@link AbstractTriggerVocabulary} implements the
 * {@link TriggerVocabulary} interface implementing the {@link EventListener}
 * management.
 * </p>
 * 
 * @author Claas Wilke
 */
public abstract class AbstractTriggerVocabulary implements TriggerVocabulary {

	/**
	 * The {@link EventListener}s of this {@link AbstractTriggerVocabulary}.
	 */
	private Set<EventListener> listeners = new HashSet<EventListener>();

	/**
	 * <p>
	 * Adds a {@link EventListener} to this {@link AbstractTriggerVocabulary}.
	 * </p>
	 * 
	 * @param listener
	 *          The {@link EventListener} that shall be added.
	 * @return <code>true</code> if the {@link EventListener} has been added
	 *         successfully.
	 */
	public boolean addEventListener(EventListener listener) {

		return this.listeners.add(listener);
	}

	/**
	 * <p>
	 * Removes a {@link EventListener} from this {@link AbstractTriggerVocabulary}
	 * .
	 * </p>
	 * 
	 * @param listener
	 *          The {@link EventListener} that shall be removed.
	 * @return <code>true</code> if the {@link EventListener} has been removed
	 *         successfully.
	 */
	public boolean removeEventListener(EventListener listener) {

		return this.listeners.remove(listener);
	}

	/**
	 * <p>
	 * Notifies all {@link EventListener}s of this
	 * {@link AbstractTriggerVocabulary}, that a new event that triggers a
	 * verification occurred.
	 * </p>
	 * 
	 * @param triggerType
	 *          The occurred type of trigger.
	 * @param the
	 *          {@link Contract}s that must be verified.
	 */
	public void notifyEventListners(URI triggerType, Set<Contract> contracts) {

		for (EventListener listener : this.listeners) {
			listener.update(triggerType, contracts);
		}
		// end for.
	}
}