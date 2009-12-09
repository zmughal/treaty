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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.java.treaty.ContractLogger;
import net.java.treaty.TreatyException;
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
	public static final String NAME_SPACE = "http://www.treaty.org/trigger/test";

	/** The name of a test trigger. */
	public static final String NAME_DEFAULT_TRIGGER =
			NAME_SPACE + "#defaultTrigger";

	/** The name of a test trigger. */
	public static final String NAME_PARENT_TRIGGER_1 =
			NAME_SPACE + "#parentTrigger1";

	/** The name of a test trigger. */
	public static final String NAME_TRIGGER_1 = NAME_SPACE + "#trigger1";

	/** The name of a test trigger. */
	public static final String NAME_TRIGGER_2 = NAME_SPACE + "#trigger2";

	/** The trigger types of this {@link TriggerVocabulary}. */
	private Map<String, URI> triggerTypes;

	/**
	 * <p>
	 * Creates a new {@link TriggerVocabularyMock}.
	 * </p>
	 */
	public TriggerVocabularyMock() {

		this.initialize();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.event.TriggerVocabulary#getTriggerTypes()
	 */
	public Set<URI> getTriggers() {

		return new HashSet<URI>(this.triggerTypes.values());
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.trigger.TriggerVocabulary#getSubTriggers(java.net.URI)
	 */
	public Set<URI> getSubTriggers(URI triggerType) throws TreatyException {

		Set<URI> result;
		result = new HashSet<URI>();

		/* ParentTrigger1 is super trigger of Trigger1. */
		if (this.triggerTypes.get(NAME_PARENT_TRIGGER_1).equals(triggerType)) {
			result.add(this.getTrigger(NAME_TRIGGER_1));
		}
		// no else.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.trigger.TriggerVocabulary#getSuperTriggers(java.net.URI)
	 */
	public Set<URI> getSuperTriggers(URI triggerType) throws TreatyException {

		Set<URI> result;
		result = new HashSet<URI>();

		/* ParentTrigger1 is super trigger of Trigger1. */
		if (this.triggerTypes.get(NAME_TRIGGER_1).equals(triggerType)) {
			result.add(this.getTrigger(NAME_PARENT_TRIGGER_1));
		}
		// no else.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.trigger.TriggerVocabulary#isDefaultTrigger(java.net.URI)
	 */
	public boolean isDefaultTrigger(URI triggerType) {

		/** Return true only for the default trigger. */
		return triggerType.toString().equals(NAME_DEFAULT_TRIGGER);
	}

	/**
	 * <p>
	 * Returns the {@link URI} for a given trigger's name or <code>null</code> if
	 * the trigger does not exist.
	 * </p>
	 * 
	 * @param name
	 *          The trigger's name.
	 * @return The {@link URI} or <code>null</code>.
	 */
	public URI getTrigger(String name) {

		URI result;
		result = null;

		for (URI triggerType : this.getTriggers()) {
			if (triggerType.toString().equals(name)) {
				result = triggerType;
				break;
			}
			// no else.
		}
		// end for.

		return result;
	}

	/**
	 * <p>
	 * Initializes this {@link TriggerVocabularyMock}.
	 * </p>
	 */
	private void initialize() {

		/* Probably initialize. */
		if (this.triggerTypes == null) {
			this.triggerTypes = new HashMap<String, URI>();

			try {
				this.triggerTypes.put(NAME_TRIGGER_1, new URI(NAME_TRIGGER_1));
				this.triggerTypes.put(NAME_TRIGGER_2, new URI(NAME_TRIGGER_2));
				this.triggerTypes.put(NAME_DEFAULT_TRIGGER, new URI(
						NAME_DEFAULT_TRIGGER));
				this.triggerTypes.put(NAME_PARENT_TRIGGER_1, new URI(
						NAME_PARENT_TRIGGER_1));
			}

			catch (URISyntaxException e) {
				ContractLogger.LOGGER.error(
						"Unexpected URISyntaxException during testing.", e);

				this.triggerTypes = null;
			}
		}
		// no else.
	}
}