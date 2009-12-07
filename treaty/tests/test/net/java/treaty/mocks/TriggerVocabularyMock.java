/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package test.net.java.treaty.mocks;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import net.java.treaty.ContractLogger;
import net.java.treaty.trigger.AbstractTriggerVocabulary;
import net.java.treaty.trigger.TriggerVocabulary;

/**
 * <p>
 * A mock implementation for a {@link TriggerVocabulary}.
 * </p>
 * 
 * @author Claas Wilke
 */
public class TriggerVocabularyMock extends AbstractTriggerVocabulary {

	/** The name space of this {@link TriggerVocabulary}. */
	public static final String NAME_SPACE =
			"http://www.treaty.org/test/net/java/treaty/event";

	/** The name of a test trigger. */
	public static final String NAME_TRIGGER_1 = NAME_SPACE + "#trigger1";

	/** The name of a test trigger. */
	public static final String NAME_TRIGGER_2 = NAME_SPACE + "#trigger2";

	/** The trigger types of this {@link TriggerVocabulary}. */
	private Set<URI> triggerTypes;

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.event.TriggerVocabulary#getTriggerTypes()
	 */
	public Set<URI> getTriggerTypes() {

		/* Probably initialize. */
		if (this.triggerTypes == null) {
			this.triggerTypes = new HashSet<URI>();

			try {
				this.triggerTypes.add(new URI(NAME_TRIGGER_1));
				this.triggerTypes.add(new URI(NAME_TRIGGER_2));
			}

			catch (URISyntaxException e) {
				ContractLogger.LOGGER.error(
						"Unexpected URISyntaxException during testing.", e);

				this.triggerTypes = null;
			}
		}
		// no else.

		return this.triggerTypes;
	}
}