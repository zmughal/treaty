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

/**
 * <p>
 * Interface used to listen to the {@link ActionRegistry}.
 * </p>
 * 
 * @author Claas Wilke
 */
public interface ActionRegistryListener {

	/**
	 * <p>
	 * Method called, if a {@link ActionVocabulary} has been added to the
	 * {@link ActionVocabulary}.
	 * </p>
	 * 
	 * @param actionVocabulary
	 *          The {@link ActionVocabulary} that has been added to the
	 *          {@link TriggerRegistry}.
	 */
	public void actionVocabularyAdded(ActionVocabulary actionVocabulary);

	/**
	 * <p>
	 * Method called, if an {@link ActionVocabulary} has been removed from the
	 * {@link ActionRegistry}.
	 * </p>
	 * 
	 * @param actionVocabulary
	 *          The {@link ActionVocabulary} that has been removed from the
	 *          {@link ActionRegistry}.
	 */
	public void triggerVocabularyRemoved(ActionVocabulary triggerVocabulary);
}