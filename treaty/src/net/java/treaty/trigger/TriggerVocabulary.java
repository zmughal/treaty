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
 * A {@link TriggerVocabulary} provides trigger types that can be used to
 * trigger the verification of {@link Contract}s dynamically.
 * </p>
 * 
 * @author Claas Wilke
 */
public interface TriggerVocabulary extends EventProvider {

	/**
	 * <p>
	 * Returns a {@link Set} containing the {@link URI}s of all trigger types this
	 * {@link TriggerVocabulary} defines.
	 * </p>
	 * 
	 * @return A {@link Set} containing the {@link URI}s of all trigger types this
	 *         {@link TriggerVocabulary} defines.
	 */
	public Set<URI> getTriggerTypes();
}