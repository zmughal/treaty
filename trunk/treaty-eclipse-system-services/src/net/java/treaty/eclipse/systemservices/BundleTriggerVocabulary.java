/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.systemservices;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.TreatyException;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.Logger;
import net.java.treaty.eclipse.contractregistry.EclipseAdapterFactory;
import net.java.treaty.eclipse.contractregistry.EclipseContractRegistry;
import net.java.treaty.eclipse.trigger.AbstractEclipseTriggerVocabulary;
import net.java.treaty.eclipse.trigger.EclipseTriggerRegistry;
import net.java.treaty.trigger.TriggerVocabulary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

/**
 * <p>
 * The {@link BundleTriggerVocabulary} provides Triggers that represent
 * {@link BundleEvent}s.
 * </p>
 * 
 * @author Claas Wilke
 */
public class BundleTriggerVocabulary extends AbstractEclipseTriggerVocabulary
		implements BundleListener {

	/** The name space's name of the {@link BundleTriggerVocabulary}. */
	public static final String NAME_SPACE_NAME =
			"http://www.treaty.org/trigger/bundle";

	/** The name of the trigger type representing any type of {@link BundleEvent}. */
	public static final String TRIGGER_TYPE_BUNDLE_EVENT =
			NAME_SPACE_NAME + "#BundleEvent";

	/** The name of the trigger type representing the install of a {@link Bundle}. */
	public static final String TRIGGER_TYPE_BUNDLE_INSTALLED =
			NAME_SPACE_NAME + "#BundleInstalled";

	/**
	 * The name of the trigger type representing the lazy activation of a
	 * {@link Bundle}.
	 */
	public static final String TRIGGER_TYPE_BUNDLE_LAZY_ACTIVATION =
			NAME_SPACE_NAME + "#BundleLazyActivation";

	/**
	 * The name of the trigger type representing the resolving of a {@link Bundle}
	 * .
	 */
	public static final String TRIGGER_TYPE_BUNDLE_RESOLVED =
			NAME_SPACE_NAME + "#BundleResolved";

	/** The name of the trigger type representing the start of a {@link Bundle}. */
	public static final String TRIGGER_TYPE_BUNDLE_STARTED =
			NAME_SPACE_NAME + "#BundleStarted";

	/**
	 * The name of the trigger type representing the starting of a {@link Bundle}.
	 */
	public static final String TRIGGER_TYPE_BUNDLE_STARTING =
			NAME_SPACE_NAME + "#BundleStarting";

	/** The name of the trigger type representing the start of a {@link Bundle}. */
	public static final String TRIGGER_TYPE_BUNDLE_STOPPED =
			NAME_SPACE_NAME + "#BundleStopped";

	/**
	 * The name of the trigger type representing the stopping of a {@link Bundle}.
	 */
	public static final String TRIGGER_TYPE_BUNDLE_STOPPING =
			NAME_SPACE_NAME + "#BundleStopping";

	/**
	 * The name of the trigger type representing the uninstall of a {@link Bundle}
	 * .
	 */
	public static final String TRIGGER_TYPE_BUNDLE_UNINSTALLED =
			NAME_SPACE_NAME + "#BundleUninstalled";

	/**
	 * The name of the trigger type representing the unresolving of a
	 * {@link Bundle}.
	 */
	public static final String TRIGGER_TYPE_BUNDLE_UNRESOLVED =
			NAME_SPACE_NAME + "#BundleUnresolved";

	/**
	 * The name of the trigger type representing the updating of a {@link Bundle}.
	 */
	public static final String TRIGGER_TYPE_BUNDLE_UPDATED =
			NAME_SPACE_NAME + "#BundleUpdated";

	/**
	 * The Trigger types of this {@link TriggerVocabulary} as a {@link Map} of
	 * {@link URI}s identified by their {@link String} representation.
	 */
	private Map<String, URI> triggerTypes;

	/**
	 * <p>
	 * Creates a new {@link BundleTriggerVocabulary}.
	 * </p>
	 */
	public BundleTriggerVocabulary() {

		this.initialize();

		Activator.getDefault().getBundle().getBundleContext().addBundleListener(
				this);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.osgi.framework.BundleListener#bundleChanged(org.osgi.framework.BundleEvent
	 * )
	 */
	public void bundleChanged(BundleEvent event) {

		URI triggerType;

		/* Fire general bundle event trigger. */
		triggerType = this.triggerTypes.get(TRIGGER_TYPE_BUNDLE_EVENT);
		this.fireTrigger(event, triggerType);

		/* Fire a type specific trigger as well. */
		switch (event.getType()) {

		case BundleEvent.INSTALLED:

			triggerType = this.triggerTypes.get(TRIGGER_TYPE_BUNDLE_INSTALLED);
			break;

		case BundleEvent.LAZY_ACTIVATION:

			triggerType = this.triggerTypes.get(TRIGGER_TYPE_BUNDLE_LAZY_ACTIVATION);
			break;

		case BundleEvent.RESOLVED:

			triggerType = this.triggerTypes.get(TRIGGER_TYPE_BUNDLE_RESOLVED);
			break;

		case BundleEvent.STARTED:

			triggerType = this.triggerTypes.get(TRIGGER_TYPE_BUNDLE_STARTED);
			break;

		case BundleEvent.STARTING:

			triggerType = this.triggerTypes.get(TRIGGER_TYPE_BUNDLE_STARTING);
			break;

		case BundleEvent.STOPPED:

			triggerType = this.triggerTypes.get(TRIGGER_TYPE_BUNDLE_STOPPED);
			break;

		case BundleEvent.STOPPING:

			triggerType = this.triggerTypes.get(TRIGGER_TYPE_BUNDLE_STOPPING);
			break;

		case BundleEvent.UNINSTALLED:

			triggerType = this.triggerTypes.get(TRIGGER_TYPE_BUNDLE_UNINSTALLED);
			break;

		case BundleEvent.UNRESOLVED:

			triggerType = this.triggerTypes.get(TRIGGER_TYPE_BUNDLE_UNRESOLVED);
			break;

		case BundleEvent.UPDATED:

			triggerType = this.triggerTypes.get(TRIGGER_TYPE_BUNDLE_UPDATED);
			break;

		default:
			triggerType = null;
		}
		// end switch.

		if (triggerType != null) {
			this.fireTrigger(event, triggerType);
		}
		// no else.
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.trigger.TriggerVocabulary#getSubTriggers(java.net.URI)
	 */
	public Set<URI> getSubTriggers(URI triggerType) throws TreatyException {

		Set<URI> result;

		/*
		 * If the given trigger is the parent bundle event trigger, return all other
		 * triggers.
		 */
		if (this.triggerTypes.get(TRIGGER_TYPE_BUNDLE_EVENT).equals(triggerType)) {
			result = this.getTriggers();
			result.remove(triggerType);
		}

		else {
			result = Collections.emptySet();
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.trigger.TriggerVocabulary#getSuperTriggers(java.net.URI)
	 */
	public Set<URI> getSuperTriggers(URI triggerType) throws TreatyException {

		Set<URI> result;

		if (this.getTriggers().contains(triggerType)) {

			result = new HashSet<URI>();

			if (!this.triggerTypes.get(TRIGGER_TYPE_BUNDLE_EVENT).equals(triggerType)) {
				result.add(this.triggerTypes.get(TRIGGER_TYPE_BUNDLE_EVENT));
			}
			// no else.
		}

		else {
			throw new TreatyException("Unknown trigger " + triggerType);
		}

		return result;
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

		/* This vocabulary does not define any default triggers. */
		return false;
	}

	/**
	 * <p>
	 * Called before the {@link EclipseTriggerRegistry} is torn down.
	 * </p>
	 */
	public void tearDown() {

		Activator.getDefault().getBundle().getBundleContext().removeBundleListener(
				this);

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
	 * Helper method to fire a trigger for a given {@link BundleEvent} and a given
	 * type of trigger.
	 * </p>
	 * 
	 * @param event
	 *          The {@link BundleEvent} causing the trigger.
	 * @param triggerType
	 *          The type of trigger to be fired.
	 */
	private void fireTrigger(BundleEvent event, URI triggerType) {

		EclipsePlugin eclipsePlugin;
		eclipsePlugin =
				EclipseAdapterFactory.getInstance().createEclipsePlugin(
						event.getBundle());

		Set<Contract> contracts;
		try {
			contracts =
					EclipseContractRegistry.getInstance().getAffectedContracts(
							triggerType, eclipsePlugin);
		}

		catch (TreatyException e) {
			contracts = Collections.emptySet();
			Logger.error("Unexpected TreatyException during BundleEvent handling."
					+ " Trigger will not be fired.", e);
		}

		if (contracts.size() > 0) {
			this.notifyEventListners(triggerType, contracts);
		}
		// no else.
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
				this.triggerTypes.put(TRIGGER_TYPE_BUNDLE_EVENT, new URI(
						TRIGGER_TYPE_BUNDLE_EVENT));
				this.triggerTypes.put(TRIGGER_TYPE_BUNDLE_INSTALLED, new URI(
						TRIGGER_TYPE_BUNDLE_INSTALLED));
				this.triggerTypes.put(TRIGGER_TYPE_BUNDLE_LAZY_ACTIVATION, new URI(
						TRIGGER_TYPE_BUNDLE_LAZY_ACTIVATION));
				this.triggerTypes.put(TRIGGER_TYPE_BUNDLE_RESOLVED, new URI(
						TRIGGER_TYPE_BUNDLE_RESOLVED));
				this.triggerTypes.put(TRIGGER_TYPE_BUNDLE_STARTED, new URI(
						TRIGGER_TYPE_BUNDLE_STARTED));
				this.triggerTypes.put(TRIGGER_TYPE_BUNDLE_STARTING, new URI(
						TRIGGER_TYPE_BUNDLE_STARTING));
				this.triggerTypes.put(TRIGGER_TYPE_BUNDLE_STOPPED, new URI(
						TRIGGER_TYPE_BUNDLE_STOPPED));
				this.triggerTypes.put(TRIGGER_TYPE_BUNDLE_STOPPING, new URI(
						TRIGGER_TYPE_BUNDLE_STOPPING));
				this.triggerTypes.put(TRIGGER_TYPE_BUNDLE_UNINSTALLED, new URI(
						TRIGGER_TYPE_BUNDLE_UNINSTALLED));
				this.triggerTypes.put(TRIGGER_TYPE_BUNDLE_UNRESOLVED, new URI(
						TRIGGER_TYPE_BUNDLE_UNRESOLVED));
				this.triggerTypes.put(TRIGGER_TYPE_BUNDLE_UPDATED, new URI(
						TRIGGER_TYPE_BUNDLE_UPDATED));
			}

			catch (URISyntaxException e) {
				Logger.warn("Error during initialization of BundleTriggerVocabulary. "
						+ "Probably some trigger types are not available.", e);
			}
			// end catch.
		}
		// no else (already initialized).
	}
}