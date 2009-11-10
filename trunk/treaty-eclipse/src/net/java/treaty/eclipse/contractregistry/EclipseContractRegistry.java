/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.contractregistry;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.java.treaty.Connector;
import net.java.treaty.ConnectorType;
import net.java.treaty.Contract;
import net.java.treaty.ContractVocabulary;
import net.java.treaty.Role;
import net.java.treaty.VerificationReport;
import net.java.treaty.contractregistry.ContractRegistry;
import net.java.treaty.eclipse.ContractVerificationSchedulingRule;
import net.java.treaty.eclipse.EclipseExtensionPoint;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.EclipseResourceManager;
import net.java.treaty.eclipse.EclipseVerifier;
import net.java.treaty.eclipse.jobs.VerificationJob;
import net.java.treaty.eclipse.jobs.VerificationJobListener;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;

/**
 * <p>
 * Extends the {@link ContractVocabulary} for Eclipse implementation.
 * </p>
 * 
 * @author Claas Wilke
 */
public final class EclipseContractRegistry extends ContractRegistry implements
		IRegistryEventListener {

	/** Singleton Instance of the {@link EclipseContractRegistry}. */
	private static EclipseContractRegistry myInstance;

	/**
	 * Contains all legislator {@link Contract}s that have not yet been bound to
	 * the contracted {@link EclipseExtensionPoint} because the
	 * {@link EclipseExtensionPoint}s {@link EclipsePlugin} has not been activated
	 * yet. The key is the ID of the contracted {@link EclipseExtensionPoint}.
	 */
	private Map<String, LinkedHashSet<Contract>> unboundLegislatorContracts =
			new LinkedHashMap<String, LinkedHashSet<Contract>>();

	/**
	 * Contains all {@link Connector}s that play the {@link Role#LEGISLATOR} and
	 * provided {@link Contract}s for other {@link Connector}s that have not been
	 * activated yet. The key is the legislator {@link Connector} and the value
	 * contains the Unique IDs of all {@link Connector}s that are contracted with
	 * unbound {@link Contract}s.
	 */
	private Map<Connector, HashSet<String>> legislatorsWithUnboundContracts =
			new HashMap<Connector, HashSet<String>>();

	/**
	 * <p>
	 * Private constructor for Singleton Pattern.
	 * </p>
	 */
	private EclipseContractRegistry() {

		super(new EclipseResourceManager());

		this.init();
	}

	/**
	 * <p>
	 * A helper method that initializes the {@link EclipseContractRegistry}.
	 * </p>
	 */
	private void init() {

		/* Register the registry as listener of the ExtensionRegistry. */
		org.eclipse.core.runtime.Platform.getExtensionRegistry().addListener(this);

		/* Do the initial startup. */
		UpdateJobInitializeContractRegistry contractRegistryStartUpJob;
		contractRegistryStartUpJob =
				new UpdateJobInitializeContractRegistry(
						"Initial ContractRegistry Startup");

		contractRegistryStartUpJob.schedule();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.
	 * runtime.IExtension[])
	 */
	public void added(IExtension[] extensions) {

		Job jobExtensionsAdded;
		jobExtensionsAdded =
				new UpdateJobExtensionsAdded(extensions,
						"Extensions Added. Update the ContractRegistry.");

		jobExtensionsAdded.schedule();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.
	 * runtime.IExtensionPoint[])
	 */
	public void added(IExtensionPoint[] extensionPoints) {

		Job jobExtensionPointsAdded;
		jobExtensionPointsAdded =
				new UpdateJobExtensionPointsAdded(extensionPoints,
						"ExtensionPoints Added. Update the ContractRegistry.");

		jobExtensionPointsAdded.schedule();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core
	 * .runtime.IExtension[])
	 */
	public void removed(IExtension[] extensions) {

		Job jobExtensionsRemoved;
		jobExtensionsRemoved =
				new UpdateJobExtensionsRemoved(extensions,
						"Extensions Removed. Update the ContractRegistry.");

		jobExtensionsRemoved.schedule();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core
	 * .runtime.IExtensionPoint[])
	 */
	public void removed(IExtensionPoint[] extensionPoints) {

		Job jobExtensionPointsRemoved;
		jobExtensionPointsRemoved =
				new UpdateJobExtensionPointsRemoved(extensionPoints,
						"ExtensionPoints Removed. Update the ContractRegistry.");

		jobExtensionPointsRemoved.schedule();
	}

	/**
	 * Contains the {@link Bundle} states that shall be interpreted by the
	 * {@link EclipseContractRegistry} as {@link Bundle}s, whose {@link Contract}s
	 * shall be registered.
	 */
	public static final List<Integer> ACTIVE_BUNDLE_STATES =
			Arrays.asList(new Integer[] { Bundle.ACTIVE });

	/**
	 * The {@link BundleEvent} type that shall cause an update of the
	 * {@link ContractRegistry} that adds all {@link Contract} provided by the
	 * given {@link Bundle}.
	 */
	public static final int ADD_BUNDLE_EVENT = BundleEvent.STARTED;

	/**
	 * The {@link BundleEvent} type that shall cause an update of the
	 * {@link ContractRegistry} that removes all {@link Contract} provided by the
	 * given {@link Bundle}.
	 */
	public static final int REMOVE_BUNDLE_EVENT = BundleEvent.STOPPED;

	/**
	 * <p>
	 * Clears the {@link EclipseContractRegistry}. <strong>Attention: The
	 * {@link EclipseContractRegistry} will not be reinitialized but only
	 * cleared!</strong>
	 * </p>
	 */
	public static void clear() {

		myInstance.contractedConnectors.clear();
		myInstance.instantiatedContracts.clear();
		myInstance.legislatorsWithUnboundContracts.clear();
		myInstance.myConsumerContracts.clear();
		myInstance.myLegislatorContracts.clear();
		myInstance.mySupplierContracts.clear();
	}

	/**
	 * <p>
	 * Returns the Singleton Instance of the {@link EclipseContractRegistry}.
	 * </p>
	 * 
	 * @return The Singleton Instance of the {@link EclipseContractRegistry}.
	 */
	public static EclipseContractRegistry getInstance() {

		if (myInstance == null) {
			myInstance = new EclipseContractRegistry();
		}
		// no else.

		return myInstance;
	}

	/**
	 * <p>
	 * Returns the {@link EclipsePlugin}s on that contracts are defined.
	 * </p>
	 * 
	 * @return The {@link EclipsePlugin}s on that contracts are defined.
	 */
	public LinkedHashSet<EclipsePlugin> getContractedEclipsePlugins() {

		LinkedHashSet<EclipsePlugin> result;

		result = new LinkedHashSet<EclipsePlugin>();

		/* Add the eclipse plug-ins of all contracted consumers. */
		for (Connector connector : this.myConsumerContracts.keySet()) {

			if (connector.getOwner() instanceof EclipsePlugin) {
				result.add((EclipsePlugin) connector.getOwner());
			}
			// no else.
		}
		// end for.

		return result;
	}

	/**
	 * <p>
	 * Returns all {@link Contract}s that are instantiated. Meaning: all
	 * {@link Contract}s that are bound to a {@link Connector} of the
	 * {@link ConnectorType#CONSUMER} and to a {@link Connector} of the
	 * {@link ConnectorType#SUPPLIER} as well.
	 * </p>
	 * 
	 * @return All {@link Contract}s that are instantiated.
	 */
	public Collection<Contract> getInstantiatedContracts() {

		Set<Contract> result;
		result = new LinkedHashSet<Contract>();

		/* Only supplier contracts could be instantiated. */
		for (Connector aSupplierConnector : this.mySupplierContracts.keySet()) {

			for (Contract contract : this.mySupplierContracts.get(aSupplierConnector)) {

				if (contract.isInstantiated()) {
					result.add(contract);
				}
				// no else.
			}
			// end for (iteration on contracts).
		}
		// end for (iteration on suppliers).

		return result;
	}

	/**
	 * <p>
	 * Checks if a given {@link Connector} (as its ID) has at least one unbound
	 * legislator {@link Contract}.
	 * </p>
	 * 
	 * @param connectorID
	 *          The unique ID of {@link Connector} that shall be checked.
	 * @return <code>true</code> if the given {@link Connector} has at least one
	 *         unbound legislator {@link Contract}
	 */
	public boolean hasUnboundLegislatorContracts(String connectorID) {

		return this.unboundLegislatorContracts.containsKey(connectorID);
	}

	/**
	 * <p>
	 * Checks if a given {@link Connector} plays a given {@link Role} (in at least
	 * one {@link Contract}).
	 * </p>
	 * 
	 * @param connector
	 *          The {@link Connector} that shall be checked.
	 * @param role
	 *          The {@link Role} that shall be checked.
	 * @return <code>true</code>, if a given {@link Connector} plays a given
	 *         {@link Role} (in at least one {@link Contract}).
	 */
	public boolean playsConnectorRole(Connector connector, Role role) {

		boolean result;

		switch (role) {

		case LEGISLATOR:

			result = this.myLegislatorContracts.containsKey(connector);
			break;

		case CONSUMER:

			result = this.myConsumerContracts.containsKey(connector);
			break;

		case SUPPLIER:

			result = this.mySupplierContracts.containsKey(connector);
			break;

		default:
			result = false;
		}
		// end switch.

		return result;
	}

	/**
	 * <p>
	 * This method can be called when this plug-in shall be de-activated.
	 * Unregisters the {@link EclipseContractRegistry} as listener of the
	 * ExtensionRegistry.
	 * </p>
	 */
	public void tearDown() {

		org.eclipse.core.runtime.Platform.getExtensionRegistry().removeListener(
				this);
	}

	/**
	 * FIXME Claas: Should this method be part of the
	 * {@link EclipseContractRegistry}? I think it should be located in the
	 * {@link EclipseVerifier} or a similar class.
	 * 
	 * <p>
	 * Verifies a Collection of given {@link Contract}s for a given
	 * {@link VerificationReport}, {@link VerificationJobListener} and
	 * {@link IJobChangeListener}.
	 * </p>
	 * 
	 * @param contracts
	 *          The {@link Contract}s that shall be verified.
	 * @param verificationReport
	 *          The {@link VerificationReport} used to store the results.
	 * @param verificationJobListener
	 *          A {@link VerificationJobListener} that can be used to observe the
	 *          progress.
	 * @param jobChangeListener
	 *          An {@link IJobChangeListener} that can be used to observe the
	 *          progress.
	 */
	public void verify(Collection<Contract> contracts,
			VerificationReport verificationReport,
			VerificationJobListener verificationJobListener,
			IJobChangeListener jobChangeListener) {

		VerificationJob job;
		job =
				new VerificationJob("Treaty component verification", contracts,
						verificationReport);

		job.addVerificationJobListener(verificationJobListener);
		job.addJobChangeListener(jobChangeListener);

		ISchedulingRule combinedRule;
		combinedRule = null;

		for (Contract contract : contracts) {
			MultiRule.combine(new ContractVerificationSchedulingRule(contract),
					combinedRule);
		}

		job.setRule(combinedRule);
		job.schedule();
	}

	/**
	 * <p>
	 * Adds a given {@link Contract} to the list of unbound legislator
	 * {@link Contract}s in respect to the {@link EclipseExtensionPoint}s unique
	 * ID that is contracted by the {@link Contract}.
	 * </p>
	 * 
	 * @param extensionPointID
	 *          The unique ID of the {@link EclipseExtensionPoint} that is
	 *          contracted.
	 * @param contract
	 *          The legislator {@link Contract} that shall be added.
	 * @return
	 */
	protected void addUnboundLegislatorContract(String extensionPointID,
			Contract contract) {

		/* Store the legislator contract. */
		LinkedHashSet<Contract> legislatorContracts;

		if (this.unboundLegislatorContracts.containsKey(extensionPointID)) {
			legislatorContracts =
					this.unboundLegislatorContracts.get(extensionPointID);
		}

		else {
			legislatorContracts = new LinkedHashSet<Contract>();
		}

		legislatorContracts.add(contract);
		this.unboundLegislatorContracts.put(extensionPointID, legislatorContracts);

		/* Also store the legislator itself in another map to improve resoning. */
		HashSet<String> contractedConnectors;

		if (this.legislatorsWithUnboundContracts.containsKey(contract.getOwner())) {
			contractedConnectors =
					this.legislatorsWithUnboundContracts.get(contract.getOwner());
		}

		else {
			contractedConnectors = new HashSet<String>();
		}

		contractedConnectors.add(extensionPointID);
		this.legislatorsWithUnboundContracts.put(contract.getOwner(),
				contractedConnectors);
	}

	/**
	 * <p>
	 * Returns all instances of a given {@link Contract} that belongs to a given
	 * {@link Connector}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} whose instances shall be returned.
	 * @param connector
	 *          The {@link Connector} to which the instances shall belong to.
	 * @return A {@link Set} of all found {@link Contract} instances.
	 */
	protected Set<Contract> getInstancesOfContract(Contract contract,
			Connector connector) {

		Set<Contract> result;
		result = new LinkedHashSet<Contract>();

		if (connector.getType().equals(ConnectorType.CONSUMER)) {

			Set<Contract> ownedContracts;
			ownedContracts = this.getContracts(connector, Role.CONSUMER);

			for (Contract ownedContract : ownedContracts) {

				if (ownedContract.equals(contract)
						|| ownedContract.getDefinition().equals(contract)) {

					result.add(ownedContract);
				}
				// no else.
			}
			// end for.
		}

		else if (connector.getType().equals(ConnectorType.SUPPLIER)) {

			Set<Contract> ownedContracts;
			ownedContracts = this.getContracts(connector, Role.SUPPLIER);

			for (Contract ownedContract : ownedContracts) {

				if (ownedContract.equals(contract)
						|| ownedContract.getDefinition().equals(contract)) {

					result.add(ownedContract);
				}
				// no else.
			}
			// end for.
		}
		// no else.

		return result;
	}

	/**
	 * <p>
	 * Removes and returns all unbound legislator {@link Contract}s from this
	 * {@link EclipseContractRegistry} that contract a given {@link Connector}.
	 * </p>
	 * 
	 * @param connector
	 *          The {@link Connector} whose legislator {@link Contract}s shall be
	 *          returned.
	 * @return The removed legislator {@link Contract}s.
	 */
	protected Set<Contract> removeUnboundLegislatorContractsForContractedConnector(
			Connector connector) {

		Set<Contract> result;
		result = new LinkedHashSet<Contract>();

		if (this.unboundLegislatorContracts.containsKey(connector.getId())) {

			result.addAll(this.unboundLegislatorContracts.remove(connector.getId()));

			/* Also remove the legislator's of the found contracts from a second map. */
			for (Contract legislatorContract : result) {

				this.removeIdFromLegislatorWithUnboundContracts(legislatorContract
						.getOwner(), connector.getId());
			}
		}
		// no else.

		return result;
	}

	/**
	 * <p>
	 * Removes and all unbound legislator {@link Contract}s from this
	 * {@link EclipseContractRegistry} that are owned by a given {@link Connector}
	 * .
	 * </p>
	 * 
	 * @param connector
	 *          The {@link Connector} whose legislator {@link Contract}s shall be
	 *          removed.
	 */
	protected void removeUnboundLegislatorContractsForLegislatorConnector(
			Connector connector) {

		if (this.legislatorsWithUnboundContracts.containsKey(connector)) {

			for (String contractedID : this.legislatorsWithUnboundContracts
					.remove(connector)) {

				if (this.unboundLegislatorContracts.containsKey(contractedID)) {

					LinkedHashSet<Contract> remainingContracts;
					LinkedHashSet<Contract> removedContracts;
					remainingContracts =
							this.unboundLegislatorContracts.get(contractedID);
					removedContracts = new LinkedHashSet<Contract>();

					for (Contract legislatorContract : remainingContracts) {

						if (legislatorContract.getOwner().equals(connector)) {
							removedContracts.add(legislatorContract);
						}
						// no else.
					}
					// end for.

					remainingContracts.removeAll(removedContracts);

					if (remainingContracts.size() > 0) {
						this.unboundLegislatorContracts.put(contractedID,
								remainingContracts);
					}

					else {
						this.unboundLegislatorContracts.remove(contractedID);
					}
				}
				// no else.
			}
			// end for.
		}
		// no else.
	}

	/**
	 * <p>
	 * A helper method that removes a given {@link Connector}s ID from the list of
	 * IDs for a given {@link Connector} playing the {@link Role#LEGISLATOR} in
	 * external {@link Contract}s.
	 * 
	 * @param legislator
	 *          The legislator {@link Connector} from that the ID shall be
	 *          removed.
	 * @param id
	 *          The ID that shall be removed.
	 */
	private void removeIdFromLegislatorWithUnboundContracts(Connector legislator,
			String id) {

		if (this.legislatorsWithUnboundContracts.containsKey(legislator)) {

			HashSet<String> contractedIDs;
			contractedIDs = this.legislatorsWithUnboundContracts.get(legislator);

			contractedIDs.remove(id);

			if (contractedIDs.size() > 0) {
				this.legislatorsWithUnboundContracts.put(legislator, contractedIDs);
			}

			else {
				this.legislatorsWithUnboundContracts.remove(legislator);
			}
		}
		// no else.
	}
}