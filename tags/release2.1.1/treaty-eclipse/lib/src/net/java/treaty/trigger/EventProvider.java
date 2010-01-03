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
import java.util.Set;

import net.java.treaty.Contract;

/**
 * <p>
 * An {@link EventProvider} provides {@link LifeCycleEvent}s (either by causing
 * the events itself or by listening to other events and adapting them to
 * {@link LifeCycleEvent}s). A {@link EventProvider} can be listened by
 * {@link EventListener}s.
 * </p>
 * 
 * @author Claas Wilke
 */
public interface EventProvider {

	/**
	 * <p>
	 * Adds a {@link EventListener} to this {@link EventProvider}.
	 * </p>
	 * 
	 * @param listener
	 *          The {@link EventListener} that shall be added.
	 * @return <code>true</code> if the {@link EventListener} has been added
	 *         successfully.
	 */
	public boolean addEventListener(EventListener listener);

	/**
	 * <p>
	 * Notifies all {@link EventListener}s of this {@link EventProvider}, that a
	 * new trigger event.
	 * </p>
	 * 
	 * @param triggerType
	 *          The occurred type of trigger (a {@link URI}).
	 * @param The
	 *          {@link Contract}s that must be verified after the event occurred.
	 */
	public void notifyEventListners(URI triggerType, Set<Contract> contracts);

	/**
	 * <p>
	 * Removes a {@link EventListener} from this {@link EventProvider}.
	 * </p>
	 * 
	 * @param listener
	 *          The {@link EventListener} that shall be removed.
	 * @return <code>true</code> if the {@link EventListener} has been removed
	 *         successfully.
	 */
	public boolean removeEventListener(EventListener listener);
}