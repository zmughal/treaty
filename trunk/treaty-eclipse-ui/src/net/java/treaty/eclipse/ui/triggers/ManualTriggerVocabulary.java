/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.ui.triggers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.TreatyException;
import net.java.treaty.eclipse.Logger;
import net.java.treaty.eclipse.contractregistry.EclipseContractRegistry;
import net.java.treaty.eclipse.trigger.AbstractEclipseTriggerVocabulary;
import net.java.treaty.trigger.TriggerVocabulary;

/**
 * <p>
 * The {@link ManualTriggerVocabulary} provides a trigger to manually verify all
 * {@link Contract}s.
 * </p>
 * 
 * @author Claas Wilke
 */
public class ManualTriggerVocabulary extends AbstractEclipseTriggerVocabulary {

	/** The name space's name of the {@link ManualTriggerVocabulary}. */
	public static final String NAME_SPACE_NAME =
			"http://www.treaty.org/trigger/manual";

	/**
	 * The name of the trigger type representing the manual verification of all
	 * {@link Contract}s.
	 */
	public static final String TRIGGER_TYPE_VERIFY_ALL =
			NAME_SPACE_NAME + "#verifyAll";

	/**
	 * The name of the trigger type representing the manual verification of a
	 * {@link Set} of selected {@link Contract}s.
	 */
	public static final String TRIGGER_TYPE_VERIFY_SELECTED =
			NAME_SPACE_NAME + "#verifySelected";

	/**
	 * The Trigger types of this {@link TriggerVocabulary} as a {@link Map} of
	 * {@link URI}s identified by their {@link String} representation.
	 */
	private Map<String, URI> triggerTypes;

	/**
	 * <p>
	 * Creates a new {@link ManualTriggerVocabulary}.
	 * </p>
	 */
	public ManualTriggerVocabulary() {

		this.initialize();
	}

	/**
	 * <p>
	 * Initializes the {@link BundleTriggerVocabulary}.
	 * </p>
	 */
	private void initialize() {

		/* Probably initialize the trigger Types */
		if (this.triggerTypes == null) {

			this.triggerTypes = new HashMap<String, URI>();

			try {
				this.triggerTypes.put(TRIGGER_TYPE_VERIFY_ALL, new URI(
						TRIGGER_TYPE_VERIFY_ALL));
				this.triggerTypes.put(TRIGGER_TYPE_VERIFY_SELECTED, new URI(
						TRIGGER_TYPE_VERIFY_SELECTED));
			}

			catch (URISyntaxException e) {
				Logger.warn("Error during initialization of ManualTriggerVocabulary. "
						+ "Probably some trigger types are not available.", e);
			}
			// end catch.
		}
		// no else (already initialized).
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.trigger.TriggerVocabulary#getSubTriggers(java.net.URI)
	 */
	public Set<URI> getSubTriggers(URI triggerType) throws TreatyException {
	
		/* This vocabulary does not define hierarchical triggers. */
		return Collections.emptySet();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.trigger.TriggerVocabulary#getSuperTriggers(java.net.URI)
	 */
	public Set<URI> getSuperTriggers(URI triggerType) throws TreatyException {
	
		/* This vocabulary does not define hierarchical triggers. */
		return Collections.emptySet();
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
	 * @see
	 * net.java.treaty.trigger.TriggerVocabulary#isDefaultTrigger(java.net.URI)
	 */
	public boolean isDefaultTrigger(URI triggerType) {

		/* All triggers of this vocabulary are default triggers. */
		return this.triggerTypes.values().contains(triggerType);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.eclipse.trigger.AbstractEclipseTriggerVocabulary#tearDown()
	 */
	public void tearDown() {

		/* Do nothing. No listeners must be removed. */
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return NAME_SPACE_NAME;
	}

	/**
	 * <p>
	 * Fires a new trigger event that triggers the verification of all
	 * {@link Contract}s registered at the {@link EclipseContractRegistry}.
	 * </p>
	 */
	public void fireTriggerVerifyAll() {

		Set<Contract> contracts;
		contracts =
				new HashSet<Contract>(EclipseContractRegistry.getInstance()
						.getInstantiatedContracts());

		this.notifyEventListners(this.triggerTypes.get(TRIGGER_TYPE_VERIFY_ALL),
				contracts);
	}

	/**
	 * <p>
	 * Fires a new trigger event that triggers the verification of a {@link Set}
	 * of selected {@link Contract}s.
	 * </p>
	 * 
	 * @param contracts
	 *          The {@link Contract}s that shall be verified.
	 */
	public void fireTriggerVerifySelected(Set<Contract> contracts) {

		this.notifyEventListners(this.triggerTypes
				.get(TRIGGER_TYPE_VERIFY_SELECTED), contracts);
	}
}