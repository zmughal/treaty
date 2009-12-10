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
import java.util.HashSet;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.TreatyException;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.Logger;
import net.java.treaty.eclipse.contractregistry.EclipseAdapterFactory;
import net.java.treaty.eclipse.contractregistry.EclipseContractRegistry;
import net.java.treaty.eclipse.trigger.AbstractEclipseTriggerVocabulary;
import net.java.treaty.eclipse.trigger.EclipseTriggerRegistry;
import net.java.treaty.trigger.TriggerOntology;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;

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

	/**
	 * The name of the trigger type representing the starting of a {@link Bundle}.
	 */
	public static final String TRIGGER_TYPE_BUNDLE_STARTING =
			NAME_SPACE_NAME + "#BundleStarting";

	/** The name of the trigger type representing the start of a {@link Bundle}. */
	public static final String TRIGGER_TYPE_BUNDLE_STARTED =
			NAME_SPACE_NAME + "#BundleStarted";

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

	/** The name of the trigger type representing any type of {@link BundleEvent}. */
	public static final String TRIGGER_TYPE_CONSUMER_BUNDLE_EVENT =
			NAME_SPACE_NAME + "#ConsumerBundleEvent";

	/** The name of the trigger type representing the install of a {@link Bundle}. */
	public static final String TRIGGER_TYPE_CONSUMER_BUNDLE_INSTALLED =
			NAME_SPACE_NAME + "#ConsumerBundleInstalled";

	/**
	 * The name of the trigger type representing the lazy activation of a
	 * {@link Bundle}.
	 */
	public static final String TRIGGER_TYPE_CONSUMER_BUNDLE_LAZY_ACTIVATION =
			NAME_SPACE_NAME + "#ConsumerBundleLazyActivation";

	/**
	 * The name of the trigger type representing the resolving of a {@link Bundle}
	 * .
	 */
	public static final String TRIGGER_TYPE_CONSUMER_BUNDLE_RESOLVED =
			NAME_SPACE_NAME + "#ConsumerBundleResolved";

	/**
	 * The name of the trigger type representing the starting of a {@link Bundle}.
	 */
	public static final String TRIGGER_TYPE_CONSUMER_BUNDLE_STARTING =
			NAME_SPACE_NAME + "#ConsumerBundleStarting";

	/** The name of the trigger type representing the start of a {@link Bundle}. */
	public static final String TRIGGER_TYPE_CONSUMER_BUNDLE_STARTED =
			NAME_SPACE_NAME + "#ConsumerBundleStarted";

	/** The name of the trigger type representing the start of a {@link Bundle}. */
	public static final String TRIGGER_TYPE_CONSUMER_BUNDLE_STOPPED =
			NAME_SPACE_NAME + "#ConsumerBundleStopped";

	/**
	 * The name of the trigger type representing the stopping of a {@link Bundle}.
	 */
	public static final String TRIGGER_TYPE_CONSUMER_BUNDLE_STOPPING =
			NAME_SPACE_NAME + "#ConsumerBundleStopping";

	/**
	 * The name of the trigger type representing the uninstall of a {@link Bundle}
	 * .
	 */
	public static final String TRIGGER_TYPE_CONSUMER_BUNDLE_UNINSTALLED =
			NAME_SPACE_NAME + "#ConsumerBundleUninstalled";

	/**
	 * The name of the trigger type representing the unresolving of a
	 * {@link Bundle}.
	 */
	public static final String TRIGGER_TYPE_CONSUMER_BUNDLE_UNRESOLVED =
			NAME_SPACE_NAME + "#ConsumerBundleUnresolved";

	/**
	 * The name of the trigger type representing the updating of a {@link Bundle}.
	 */
	public static final String TRIGGER_TYPE_CONSUMER_BUNDLE_UPDATED =
			NAME_SPACE_NAME + "#ConsumerBundleUpdated";

	/** The name of the trigger type representing any type of {@link BundleEvent}. */
	public static final String TRIGGER_TYPE_SUPPLIER_BUNDLE_EVENT =
			NAME_SPACE_NAME + "#SupplierBundleEvent";

	/** The name of the trigger type representing the install of a {@link Bundle}. */
	public static final String TRIGGER_TYPE_SUPPLIER_BUNDLE_INSTALLED =
			NAME_SPACE_NAME + "#SupplierBundleInstalled";

	/**
	 * The name of the trigger type representing the lazy activation of a
	 * {@link Bundle}.
	 */
	public static final String TRIGGER_TYPE_SUPPLIER_BUNDLE_LAZY_ACTIVATION =
			NAME_SPACE_NAME + "#SupplierBundleLazyActivation";

	/**
	 * The name of the trigger type representing the resolving of a {@link Bundle}
	 * .
	 */
	public static final String TRIGGER_TYPE_SUPPLIER_BUNDLE_RESOLVED =
			NAME_SPACE_NAME + "#SupplierBundleResolved";

	/**
	 * The name of the trigger type representing the starting of a {@link Bundle}.
	 */
	public static final String TRIGGER_TYPE_SUPPLIER_BUNDLE_STARTING =
			NAME_SPACE_NAME + "#SupplierBundleStarting";

	/** The name of the trigger type representing the start of a {@link Bundle}. */
	public static final String TRIGGER_TYPE_SUPPLIER_BUNDLE_STARTED =
			NAME_SPACE_NAME + "#SupplierBundleStarted";

	/** The name of the trigger type representing the start of a {@link Bundle}. */
	public static final String TRIGGER_TYPE_SUPPLIER_BUNDLE_STOPPED =
			NAME_SPACE_NAME + "#SupplierBundleStopped";

	/**
	 * The name of the trigger type representing the stopping of a {@link Bundle}.
	 */
	public static final String TRIGGER_TYPE_SUPPLIER_BUNDLE_STOPPING =
			NAME_SPACE_NAME + "#SupplierBundleStopping";

	/**
	 * The name of the trigger type representing the uninstall of a {@link Bundle}
	 * .
	 */
	public static final String TRIGGER_TYPE_SUPPLIER_BUNDLE_UNINSTALLED =
			NAME_SPACE_NAME + "#SupplierBundleUninstalled";

	/**
	 * The name of the trigger type representing the unresolving of a
	 * {@link Bundle}.
	 */
	public static final String TRIGGER_TYPE_SUPPLIER_BUNDLE_UNRESOLVED =
			NAME_SPACE_NAME + "#SupplierBundleUnresolved";

	/**
	 * The name of the trigger type representing the updating of a {@link Bundle}.
	 */
	public static final String TRIGGER_TYPE_SUPPLIER_BUNDLE_UPDATED =
			NAME_SPACE_NAME + "#SupplierBundleUpdated";

	/** The location of this {@link TriggerOntology}'s ontology. */
	private static final String ONTOLOGY_LOCATION = "vocabulary/bundle.owl";

	/** The {@link OntModel} of this {@link TriggerOntology}. */
	private OntModel myOntology = null;

	/**
	 * <p>
	 * Creates a new {@link BundleTriggerVocabulary}.
	 * </p>
	 */
	public BundleTriggerVocabulary() {

		super();

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

		try {
			URI triggerType;
			Set<Contract> contracts;

			EclipsePlugin eclipsePlugin;
			eclipsePlugin =
					EclipseAdapterFactory.getInstance().createEclipsePlugin(
							event.getBundle());

			/* Fire a bundle trigger depending on event type. */
			switch (event.getType()) {

			case BundleEvent.INSTALLED:

				triggerType = new URI(TRIGGER_TYPE_BUNDLE_INSTALLED);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.ANY,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);

				triggerType = new URI(TRIGGER_TYPE_CONSUMER_BUNDLE_INSTALLED);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.CONSUMER,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);

				triggerType = new URI(TRIGGER_TYPE_SUPPLIER_BUNDLE_INSTALLED);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.SUPPLIER,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);
				break;

			case BundleEvent.LAZY_ACTIVATION:

				triggerType = new URI(TRIGGER_TYPE_BUNDLE_LAZY_ACTIVATION);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.ANY,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);

				triggerType = new URI(TRIGGER_TYPE_CONSUMER_BUNDLE_LAZY_ACTIVATION);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.CONSUMER,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);

				triggerType = new URI(TRIGGER_TYPE_SUPPLIER_BUNDLE_LAZY_ACTIVATION);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.SUPPLIER,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);
				break;

			case BundleEvent.RESOLVED:

				triggerType = new URI(TRIGGER_TYPE_BUNDLE_RESOLVED);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.ANY,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);

				triggerType = new URI(TRIGGER_TYPE_CONSUMER_BUNDLE_RESOLVED);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.CONSUMER,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);

				triggerType = new URI(TRIGGER_TYPE_SUPPLIER_BUNDLE_RESOLVED);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.SUPPLIER,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);
				break;

			case BundleEvent.STARTED:

				triggerType = new URI(TRIGGER_TYPE_BUNDLE_STARTED);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.ANY,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);

				triggerType = new URI(TRIGGER_TYPE_CONSUMER_BUNDLE_STARTED);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.CONSUMER,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);

				triggerType = new URI(TRIGGER_TYPE_SUPPLIER_BUNDLE_STARTED);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.SUPPLIER,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);
				break;

			case BundleEvent.STARTING:

				triggerType = new URI(TRIGGER_TYPE_BUNDLE_STARTING);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.ANY,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);

				triggerType = new URI(TRIGGER_TYPE_CONSUMER_BUNDLE_STARTING);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.CONSUMER,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);

				triggerType = new URI(TRIGGER_TYPE_SUPPLIER_BUNDLE_STARTING);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.SUPPLIER,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);
				break;

			case BundleEvent.STOPPED:

				triggerType = new URI(TRIGGER_TYPE_BUNDLE_STOPPED);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.ANY,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);

				triggerType = new URI(TRIGGER_TYPE_CONSUMER_BUNDLE_STOPPED);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.CONSUMER,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);

				triggerType = new URI(TRIGGER_TYPE_SUPPLIER_BUNDLE_STOPPED);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.SUPPLIER,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);
				break;

			case BundleEvent.STOPPING:

				triggerType = new URI(TRIGGER_TYPE_BUNDLE_STOPPING);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.ANY,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);

				triggerType = new URI(TRIGGER_TYPE_CONSUMER_BUNDLE_STOPPING);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.CONSUMER,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);

				triggerType = new URI(TRIGGER_TYPE_SUPPLIER_BUNDLE_STOPPING);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.SUPPLIER,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);
				break;

			case BundleEvent.UNINSTALLED:

				triggerType = new URI(TRIGGER_TYPE_BUNDLE_UNINSTALLED);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.ANY,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);

				triggerType = new URI(TRIGGER_TYPE_CONSUMER_BUNDLE_UNINSTALLED);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.CONSUMER,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);

				triggerType = new URI(TRIGGER_TYPE_SUPPLIER_BUNDLE_UNINSTALLED);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.SUPPLIER,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);
				break;

			case BundleEvent.UNRESOLVED:

				triggerType = new URI(TRIGGER_TYPE_BUNDLE_UNRESOLVED);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.ANY,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);

				triggerType = new URI(TRIGGER_TYPE_CONSUMER_BUNDLE_UNRESOLVED);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.CONSUMER,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);

				triggerType = new URI(TRIGGER_TYPE_SUPPLIER_BUNDLE_UNRESOLVED);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.SUPPLIER,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);
				break;

			case BundleEvent.UPDATED:

				triggerType = new URI(TRIGGER_TYPE_BUNDLE_UPDATED);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.ANY,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);

				triggerType = new URI(TRIGGER_TYPE_CONSUMER_BUNDLE_UPDATED);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.CONSUMER,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);

				triggerType = new URI(TRIGGER_TYPE_SUPPLIER_BUNDLE_UPDATED);
				contracts =
						this.getContractsForRole(eclipsePlugin, BundleRole.SUPPLIER,
								triggerType);
				this.fireTrigger(event, triggerType, contracts);
				break;

			default:
				triggerType = null;
			}
			// end switch.
		}
		// end try.

		catch (URISyntaxException e) {
			Logger.error("Unexpected Exception during triggering BundleEvent "
					+ event, e);
		}
		// end catch.
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.trigger.TriggerOntology#getOntology()
	 */
	public OntModel getOntology() {

		/* Probably load the ontology. */
		if (this.myOntology == null) {
			Bundle myBundle;
			myBundle = Activator.getDefault().getBundle();

			this.myOntology = ModelFactory.createOntologyModel();
			this.myOntology.read(myBundle.getResource(ONTOLOGY_LOCATION).toString());
		}
		// no else.

		return this.myOntology;
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
	 * @param contracts
	 *          The {@link Contract}s for that the trigger shall be fired.
	 */
	private void fireTrigger(BundleEvent event, URI triggerType,
			Set<Contract> contracts) {

		if (contracts.size() > 0) {
			this.notifyEventListners(triggerType, contracts);
		}
		// no else.
	}

	/** Different roles a Bundle can play in a {@link Contract}. */
	enum BundleRole {
		CONSUMER, SUPPLIER, ANY
	};

	/**
	 * <p>
	 * Returns all {@link Contract}s for that a given {@link EclipsePlugin} plays
	 * a given {@link BundleRole}.
	 * </p>
	 * 
	 * @param eclipsePlugin
	 *          The {@link EclipsePlugin}.
	 * @param role
	 *          The {@link BundleRole}.
	 * @param triggerType
	 *          A {@link URI} representing the triggerType whose {@link Contract}s
	 *          shall be returned.
	 * @return A {@link Set} of {@link Contract}s.
	 */
	private Set<Contract> getContractsForRole(EclipsePlugin eclipsePlugin,
			BundleRole role, URI triggerType) {

		Set<Contract> result;
		result = new HashSet<Contract>();

		try {
			Set<Contract> affectedContracts;

			affectedContracts =
					EclipseContractRegistry.getInstance().getAffectedContracts(
							triggerType, eclipsePlugin);

			/* Filter according to the role. */
			switch (role) {

			case CONSUMER:
				for (Contract contract : affectedContracts) {
					if (contract.getConsumer().getOwner().equals(eclipsePlugin)
							&& !contract.getSupplier().getOwner().equals(eclipsePlugin)) {
						result.add(contract);
					}
					// no else.
				}
				// end for.
				break;

			case SUPPLIER:
				for (Contract contract : affectedContracts) {
					if (!contract.getConsumer().getOwner().equals(eclipsePlugin)
							&& contract.getSupplier().getOwner().equals(eclipsePlugin)) {
						result.add(contract);
					}
					// no else.
				}
				// end for.
				break;

			case ANY:
				
				result = affectedContracts;
				break;

			// no default.
			}
		}

		catch (TreatyException e) {
			Logger.error("Unexpected TreatyException during BundleEvent handling."
					+ " Trigger will not be fired.", e);
		}

		return result;
	}
}