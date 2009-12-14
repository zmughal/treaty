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
 * A {@link EventListener} listens to {@link LifeCycleEvent}s and performs an
 * update when a {@link LifeCycleEvent} occurred.
 * </p>
 * 
 * @author Claas Wilke
 */
public interface EventListener {

	/**
	 * <p>
	 * Called when a trigger event occurred whose {@link TriggerVocabulary} this
	 * {@link EventListener} is listening to.
	 * </p>
	 * 
	 * @param triggerType
	 *          The {@link URI} of the trigger that shall be handled.
	 * @param contracts
	 *          All {@link Contract}s that must be verified after the caused
	 *          trigger.
	 */
	public void update(URI triggerType, Set<Contract> contracts);
}