/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.contractregistry;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.VerificationReport;
import net.java.treaty.eclipse.ContractVerificationSchedulingRule;
import net.java.treaty.eclipse.EclipseConnector;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.EclipseExtensionPoint;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.EclipseVerifier;
import net.java.treaty.eclipse.Logger;
import net.java.treaty.eclipse.jobs.VerificationJob;
import net.java.treaty.eclipse.jobs.VerificationJobListener;
import net.java.treaty.event.LifeCycleEvent;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;

/**
 * <p>
 * A registry used to add and remove dynamically Treaty contracts during
 * {@link Bundle}'s life cycle. The {@link ContractRegistry} can extends
 * {@link Observable}. Thus, it can be listened by other classes like the
 * ContractView.
 * </p>
 * 
 * @author Claas Wilke
 */
public final class ContractRegistry extends Observable {

	/** Singleton Instance of the {@link ContractRegistry}. */
	private static ContractRegistry myInstance;

	/** The {@link EclipsePlugin}s on that contracts are defined. */
	private Map<Bundle, EclipsePlugin> contractedPlugins =
			new LinkedHashMap<Bundle, EclipsePlugin>();

	/** The {@link IExtensionPoint}s on which {@link Contract}s are defined. */
	private Map<IExtensionPoint, LinkedHashSet<Contract>> contractedExtensionPoints =
			new LinkedHashMap<IExtensionPoint, LinkedHashSet<Contract>>();

	/** The {@link IExtension}s on which {@link Contract}s are defined. */
	private Map<IExtension, LinkedHashSet<Contract>> contractedExtensions =
			new LinkedHashMap<IExtension, LinkedHashSet<Contract>>();

	/**
	 * Contains all {@link IExtensionPoint}s on that external {@link Contract}s
	 * have been defined by other {@link EclipsePlugin}s. The
	 * {@link EclipsePlugin} that defined a {@link Contract} can be accessed via
	 * the {@link Contract#getOwner()} method.
	 */
	private Map<IExtensionPoint, LinkedHashSet<Contract>> boundExternalContracts =
			new HashMap<IExtensionPoint, LinkedHashSet<Contract>>();

	/**
	 * Contains all external {@link Contract}s in relation to the
	 * {@link IExtension} that defines the external {@link Contract}.
	 */
	private Map<IExtension, LinkedHashSet<Contract>> externalContracts =
			new HashMap<IExtension, LinkedHashSet<Contract>>();

	/**
	 * Contains all {@link IExtensionPoint}s (as their unique IDs) on that
	 * external {@link Contract}s have been defined by other {@link EclipsePlugin}
	 * s. The {@link EclipsePlugin} that defined a {@link Contract} can be
	 * accessed via the {@link Contract#getOwner()} method.
	 */
	private Map<String, LinkedHashSet<Contract>> unboundExternalContracts =
			new HashMap<String, LinkedHashSet<Contract>>();

	/**
	 * <p>
	 * Private constructor for Singleton Pattern.
	 * </p>
	 */
	private ContractRegistry() {

		this.init();
	}

	/**
	 * <p>
	 * A helper method that initializes the {@link ContractRegistry}.
	 * </p>
	 */
	private void init() {

		/* Do the initial startup. */
		ContractRegistryStartUpJob contractRegistryStartUpJob;
		contractRegistryStartUpJob =
				new ContractRegistryStartUpJob("Initial ContractRegistry Startup");

		contractRegistryStartUpJob.schedule();
	}

	/**
	 * <p>
	 * Returns the Singleton Instance of the {@link ContractRegistry}.
	 * </p>
	 * 
	 * @return The Singleton Instance of the {@link ContractRegistry}.
	 */
	public static ContractRegistry getInstance() {

		if (myInstance == null) {
			myInstance = new ContractRegistry();
		}
		// no else.

		return myInstance;
	}

	/**
	 * <p>
	 * Clears the {@link ContractRegistry}.
	 * </p>
	 */
	public void clear() {

		this.contractedExtensionPoints.clear();
		this.contractedExtensions.clear();
		this.contractedPlugins.clear();

		this.boundExternalContracts.clear();
		this.unboundExternalContracts.clear();
	}

	/**
	 * <p>
	 * Returns a {@link Set} of all {@link Contract}s that must be validated after
	 * a given {@link LifeCycleEvent} occurred.
	 * </p>
	 * 
	 * @return A {@link Set} of all {@link Contract}s that must be validated after
	 *         a given {@link LifeCycleEvent} occurred.
	 */
	public LinkedHashSet<Contract> getAffectedContracts(
			LifeCycleEvent lifeCycleEvent) {

		LinkedHashSet<Contract> result;
		LinkedHashSet<Contract> resultCandidates;

		EclipsePlugin eclipsePlugin;

		resultCandidates = new LinkedHashSet<Contract>();

		/* Get the bundle and the EclipsePlugin. */
		eclipsePlugin = (EclipsePlugin) lifeCycleEvent.getComponent();

		/*
		 * Collect contracts of the extensions of all extension points of this
		 * bundle.
		 */
		for (EclipseExtensionPoint eclipseExtensionPoint : eclipsePlugin
				.getExtensionPoints()) {

			/* Iterate on the extension point's extensions. */
			for (EclipseExtension eclipseExtension : eclipseExtensionPoint
					.getExtensions()) {
				resultCandidates.addAll(this.getOwnedContracts(eclipseExtension));
			}
			// end for.
		}
		// end for.

		/*
		 * Get all contracts that are defined by other bundle on the bundle's
		 * extensions.
		 */
		for (EclipseExtension eclipseExtension : eclipsePlugin.getExtensions()) {

			resultCandidates.addAll(this.getOwnedContracts(eclipseExtension));
		}
		// end for (iteration on extensions).

		/* Filter the contracts depending on their event types. */
		result = new LinkedHashSet<Contract>();

		for (Contract contract : resultCandidates) {

			/*
			 * FIXME Claas: Filter the contracts depending on their event types when
			 * event types are available.
			 */
			// /* Only add contracts of the right type to the result. */
			// if (contract.getEventTypes().contains(lifeCycleEvent.getType())) {
			// result.add(contract);
			// }
			// // no else.
			result.add(contract);
		}

		return result;
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
		result.addAll(this.contractedPlugins.values());

		return result;
	}

	/**
	 * <p>
	 * Returns the {@link IExtensionPoint}s on that contracts are defined.
	 * </p>
	 * 
	 * @return The {@linkIExtensionPoint}s on that contracts are defined.
	 */
	public LinkedHashSet<IExtensionPoint> getContractedExtensionPoints() {

		LinkedHashSet<IExtensionPoint> result;

		result = new LinkedHashSet<IExtensionPoint>();
		result.addAll(this.contractedExtensionPoints.keySet());

		return result;
	}

	/**
	 * <p>
	 * Returns the {@link Contract}s that are owned by a given
	 * {@link EclipseConnector}. The term 'owns' means that the
	 * {@link EclipseConnector} defines these {@link Contract}s or is bound to
	 * these {@link Contract}s.
	 * </p>
	 * 
	 * @param eclipseConnector
	 *          The {@link EclipseConnector} those {@link Contract}s shall be
	 *          returned.
	 * @return The owned {@link Contract}s of the given {@link EclipseConnector}.
	 */
	public LinkedHashSet<Contract> getOwnedContracts(
			EclipseConnector eclipseConnector) {

		LinkedHashSet<Contract> result;
		result = new LinkedHashSet<Contract>();

		/* Check if the given connector is an EclipseExtensionPoint. */
		if (eclipseConnector instanceof EclipseExtensionPoint) {

			IExtensionPoint extensionPoint;
			EclipseExtensionPoint eclipseExtensionPoint;

			eclipseExtensionPoint = (EclipseExtensionPoint) eclipseConnector;
			extensionPoint = eclipseExtensionPoint.getWrappedExtensionPoint();

			/* Probably add contracts defined by this EclipseExtensionPoint. */
			if (this.contractedExtensionPoints.containsKey(extensionPoint)) {
				result.addAll(this.contractedExtensionPoints.get(extensionPoint));
			}
			// no else.

			/* Probably add bound external contracts. */
			if (this.boundExternalContracts.containsKey(extensionPoint)) {
				result.addAll(this.boundExternalContracts.get(extensionPoint));
			}
			// no else.
		}

		/* Else check if the given connector is an EclipseExtension. */
		else if (eclipseConnector instanceof EclipseExtension) {

			EclipseExtension eclipseExtension;
			IExtension extension;

			eclipseExtension = (EclipseExtension) eclipseConnector;
			extension = eclipseExtension.getWrappedExtension();

			/* Probably add all contracts defined on the given EclipseExtension. */
			if (this.contractedExtensions.containsKey(extension)) {
				result.addAll(this.contractedExtensions.get(extension));
			}
			// no else.
		}
		// no else (nor extension point nor extension given).

		return result;
	}

	/**
	 * <p>
	 * Checks, if a given {@link IExtensionPoint} has external {@link Contract}s
	 * in the {@link ContractRegistry}.
	 * </p>
	 * 
	 * <p>
	 * This method is public visible for test reasons only. It shuoldn't be that
	 * interesting for regular clients.
	 * </p>
	 * 
	 * @param extensionPoint
	 *          The {@link IExtensionPoint} that shall be checked.
	 * @return <code>true</code> if the given {@link IExtensionPoint} has external
	 *         {@link Contract}s in the {@link ContractRegistry}.
	 */
	public boolean hasExtensionPointExternalContracts(
			IExtensionPoint extensionPoint) {

		return this.boundExternalContracts.containsKey(extensionPoint);
	}

	/**
	 * <p>
	 * Checks, if a given {@link IExtensionPoint} (by its ID) has external
	 * {@link Contract}s in the {@link ContractRegistry} that have not been bound
	 * to the {@link IExtensionPoint} yet.
	 * </p>
	 * 
	 * <p>
	 * This method is public visible for test reasons only. It shuoldn't be that
	 * interesting for regular clients.
	 * </p>
	 * 
	 * @param extensionPointID
	 *          The unique ID of the {@link IExtensionPoint} that shall be
	 *          checked.
	 * @return <code>true</code> if the given {@link IExtensionPoint} has unbound
	 *         external {@link Contract}s in the {@link ContractRegistry}.
	 */
	public boolean hasExtensionPointUnboundExternalContracts(
			String extensionPointID) {

		return this.unboundExternalContracts.containsKey(extensionPointID);
	}

	/**
	 * <p>
	 * Updates this {@link ContractRegistry} with a given {@link BundleEvent}.
	 * E.g., if the {@link BundleEvent} represents a new started {@link Bundle},
	 * the contracts of this {@link Bundle} will be added to the
	 * {@link ContractRegistry}. If the {@link Bundle} has been stopped, probably
	 * provided contracts will be removed again.
	 * </p>
	 * 
	 * @param event
	 *          The {@link BundleEvent} used to update the
	 *          {@link ContractRegistry}.
	 */
	public synchronized void update(BundleEvent event) {

		switch (event.getType()) {

		case BundleEvent.STARTED: {

			Job bundleActivationJob;
			bundleActivationJob =
					new BundleActivationUpdateJob(event.getBundle(),
							"Update Registry. New active Bundle.");

			bundleActivationJob.schedule();

			break;
		}

		case BundleEvent.STOPPED: {

			Job bundleDeactivationJob;
			bundleDeactivationJob =
					new BundleDeactivationUpdateJob(event.getBundle(),
							"Update Registry. New stopped Bundle.");

			bundleDeactivationJob.schedule();

			break;
		}

		default: {
			String msg;

			msg = "Ignored BundleEvent of type ";
			msg += this.getBundleEventTypeName(event.getType());
			msg += " for bundle ";
			msg += event.getBundle().toString();
			msg += ".";

			Logger.info(msg);
		}
		}
		// end switch.

		this.notifyObservers();
	}

	/**
	 * FIXME Claas: Should this method be part of the {@link ContractRegistry}? I
	 * think it should be located in the {@link EclipseVerifier} or a similar
	 * class.
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
	 * This method can be used to add a {@link Contract} to the list of bound
	 * external {@link Contract}s.
	 * </p>
	 * 
	 * @param extensionPoint
	 *          The {@link IExtensionPoint} to that the contract shall be added.
	 * @param contract
	 *          The {@link Contract} that shall be added.
	 */
	protected void addBoundExternalContract(
			IExtensionPoint contractedExtensionPoint, Contract contract) {

		LinkedHashSet<Contract> boundContracts;

		if (this.boundExternalContracts.containsKey(contractedExtensionPoint)) {
			boundContracts =
					this.boundExternalContracts.get(contractedExtensionPoints);
		}

		else {
			boundContracts = new LinkedHashSet<Contract>();
		}

		boundContracts.add(contract);
		this.boundExternalContracts.put(contractedExtensionPoint, boundContracts);

		/* Add the contract to the list of all external contracts as well. */
		this.addExternalContract(contract);

		/* Update change status. */
		this.setChanged();
	}

	/**
	 * <p>
	 * This method can be used to add a {@link Bundle} to the {@link Set} of
	 * contracted {@link Bundle}s externally.
	 * </p>
	 * 
	 * @param bundle
	 *          The {@link Bundle} that shall be added.
	 */
	protected void addContractedBundle(Bundle bundle) {

		EclipsePlugin eclipsePlugin;
		eclipsePlugin =
				EclipseAdapterFactory.getInstance().createEclipsePlugin(bundle);

		this.contractedPlugins.put(bundle, eclipsePlugin);

		/* Update change status. */
		this.setChanged();
	}

	/**
	 * <p>
	 * This method can be used to add a {@link Contract} to an
	 * {@link IExtensionPoint} externally.
	 * </p>
	 * 
	 * @param extensionPoint
	 *          The {@link IExtensionPoint} to that the contract shall be added.
	 * @param contract
	 *          The {@link Contract} that shall be added.
	 */
	protected void addContractToExtensionPoint(IExtensionPoint extensionPoint,
			Contract contract) {

		LinkedHashSet<Contract> definedContracts;

		definedContracts = this.contractedExtensionPoints.get(extensionPoint);

		if (definedContracts == null) {
			definedContracts = new LinkedHashSet<Contract>();
		}
		// no else.

		definedContracts.add(contract);
		this.contractedExtensionPoints.put(extensionPoint, definedContracts);

		/* Update change status. */
		this.setChanged();
	}

	/**
	 * <p>
	 * This method can be used to add a {@link Contract} to the list of unbound
	 * external {@link Contract}s.
	 * </p>
	 * 
	 * @param extensionPointID
	 *          The ID of the {@link IExtensionPoint} to that the contract shall
	 *          be added.
	 * @param contract
	 *          The {@link Contract} that shall be added.
	 */
	protected void addUnboundExternalContract(String extensionPointID,
			Contract contract) {

		LinkedHashSet<Contract> externalContractsOfExtension;

		if (this.unboundExternalContracts.containsKey(extensionPointID)) {
			externalContractsOfExtension =
					this.unboundExternalContracts.get(extensionPointID);
		}

		else {
			externalContractsOfExtension = new LinkedHashSet<Contract>();
		}

		/* Probably unbind the contract. */
		contract.setConsumer(null);
		externalContractsOfExtension.add(contract);

		this.unboundExternalContracts.put(extensionPointID,
				externalContractsOfExtension);

		/* Add the contract to the list of all external contracts as well. */
		this.addExternalContract(contract);

		/* Update change status. */
		this.setChanged();
	}

	/**
	 * <p>
	 * This method can be used to add a {@link LinkedHashSet} of {@link Contract}s
	 * to the list of unbound external {@link Contract}s.
	 * </p>
	 * 
	 * @param extensionPointID
	 *          The ID of the {@link IExtensionPoint} to that the contract shall
	 *          be added.
	 * @param contracts
	 *          The {@link Contract}s that shall be added.
	 */
	protected void addUnboundExternalContracts(String extensionPointID,
			LinkedHashSet<Contract> contracts) {

		LinkedHashSet<Contract> externalContractsOfExtension;

		if (this.unboundExternalContracts.containsKey(extensionPointID)) {
			externalContractsOfExtension =
					this.unboundExternalContracts.get(extensionPointID);
		}

		else {
			externalContractsOfExtension = new LinkedHashSet<Contract>();
		}

		/* Probably unbind the contracts. */
		for (Contract contract : contracts) {
			contract.setConsumer(null);
		}

		externalContractsOfExtension.addAll(contracts);

		this.unboundExternalContracts.put(extensionPointID,
				externalContractsOfExtension);

		/* Add the contracts to the list of all external contracts as well. */
		for (Contract contract : contracts) {
			this.addExternalContract(contract);
		}
		// end for.

		/* Update change status. */
		this.setChanged();
	}

	/**
	 * <p>
	 * Returns all external {@link Contract}s defined by the given
	 * {@link IExtension}.
	 * </p>
	 * 
	 * @param extension
	 *          The {@link IExtension} whose external {@link Contract}s shall be
	 *          returned.
	 * @return The external {@link Contract}s as a {@link LinkedHashSet}.
	 */
	protected LinkedHashSet<Contract> getExternalContractsOfExtension(
			IExtension extension) {

		LinkedHashSet<Contract> result;
		result = new LinkedHashSet<Contract>();

		if (this.externalContracts.containsKey(extension)) {
			result.addAll(this.externalContracts.get(extension));
		}
		// no else.

		return result;
	}

	/**
	 * <p>
	 * Returns all external {@link Contract}s of a given {@link IExtensionPoint}.
	 * </p>
	 * 
	 * @param extensionPoint
	 *          The {@link IExtensionPoint} whose external {@link Contract}s shall
	 *          be returned.
	 * @return The external {@link Contract}s as a {@link LinkedHashSet}.
	 */
	protected LinkedHashSet<Contract> getExternalContractsOfExtensionPoint(
			IExtensionPoint extensionPoint) {

		LinkedHashSet<Contract> result;
		result = new LinkedHashSet<Contract>();

		if (this.boundExternalContracts.containsKey(extensionPoint)) {
			result.addAll(this.boundExternalContracts.get(extensionPoint));
		}
		// no else.

		return result;
	}

	/**
	 * <p>
	 * Returns all external {@link Contract}s of a given {@link IExtensionPoint}'s
	 * ID that are already defined but not bound yet because the
	 * {@link IExtensionPoint}'s {@link Bundle} has not been activated yet.
	 * </p>
	 * 
	 * @param extensionPointID
	 *          The unique ID of the {@link IExtensionPoint} whose external
	 *          {@link Contract}s shall be returned.
	 * @return The unbound external {@link Contract}s as a {@link LinkedHashSet}.
	 */
	protected LinkedHashSet<Contract> getUnboundExternalContractsOfExtensionPoint(
			String extensionPointID) {

		LinkedHashSet<Contract> result;
		result = new LinkedHashSet<Contract>();

		if (this.unboundExternalContracts.containsKey(extensionPointID)) {
			result.addAll(this.unboundExternalContracts.get(extensionPointID));
		}
		// no else.

		return result;
	}

	/**
	 * <p>
	 * Checks if a given {@link IExtension} has registered {@link Contract}s in
	 * the {@link ContractRegistry}.
	 * </p>
	 * 
	 * @param extensionPoint
	 *          The {@link IExtension} that shall be checked.
	 * @return <code>true</code> if the {@link IExtension} has {@link Contract}s
	 *         in the {@link ContractRegistry}.
	 */
	protected boolean hasExtensionContracts(IExtension extension) {

		return this.contractedExtensions.containsKey(extension);
	}

	/**
	 * <p>
	 * Checks if a given {@link IExtensionPoint} has registered {@link Contract}s
	 * in the {@link ContractRegistry}.
	 * </p>
	 * 
	 * @param extensionPoint
	 *          The {@link IExtensionPoint} that shall be checked.
	 * @return <code>true</code> if the {@link IExtensionPoint} has
	 *         {@link Contract}s in the {@link ContractRegistry}.
	 */
	protected boolean hasExtensionPointContracts(IExtensionPoint extensionPoint) {

		return this.contractedExtensionPoints.containsKey(extensionPoint);
	}

	/**
	 * <p>
	 * A helper method that removes all bound external {@link Contract}s from the
	 * {@link ContractRegistry} for a given {@link IExtensionPoint}.
	 * </p>
	 * 
	 * @param extensionPoint
	 *          The {@link IExtensionPoint} for which all bound external
	 *          {@link Contract}s shall be removed.
	 * @return Returns the removed {@link Contract}s as a {@link LinkedHashSet}.
	 */
	protected LinkedHashSet<Contract> removeAllBoundExternalContracts(
			IExtensionPoint extensionPoint) {

		LinkedHashSet<Contract> result;

		result = this.boundExternalContracts.remove(extensionPoint);

		/* Remove the contracts from the list of all external contracts as well. */
		for (Contract contract : result) {
			this.removeExternalContract(contract);
		}

		/* Update change status. */
		this.setChanged();

		return result;
	}

	/**
	 * <p>
	 * This method can be used to remove all {@link Contract}s from an
	 * {@link IExtension}.
	 * </p>
	 * 
	 * @param extensionPoint
	 *          The {@link IExtension} from that all {@link Contract}s shall be
	 *          removed.
	 */
	protected void removeAllContractsFromExtension(IExtension extension) {

		this.contractedExtensions.remove(extension);

		/* Update change status. */
		this.setChanged();
	}

	/**
	 * <p>
	 * This method can be used to remove all {@link Contract}s from an
	 * {@link IExtensionPoint}.
	 * </p>
	 * 
	 * @param extensionPoint
	 *          The {@link IExtensionPoint} from that all {@link Contract}s shall
	 *          be removed.
	 */
	protected void removeAllContractsFromExtensionPoint(
			IExtensionPoint extensionPoint) {

		this.contractedExtensionPoints.remove(extensionPoint);

		/* Update change status. */
		this.setChanged();
	}

	/**
	 * <p>
	 * A helper method that removes all unbound external {@link Contract}s from
	 * the {@link ContractRegistry} for a given {@link IExtensionPoint}s
	 * identifier.
	 * </p>
	 * 
	 * @param uniqueIdentifier
	 *          The unique ID of the {@link IExtensionPoint} for which all unbound
	 *          external {@link Contract}s shall be removed.
	 */
	protected void removeAllUnboundExternalContracts(String uniqueIdentifier) {

		LinkedHashSet<Contract> contracts;

		contracts = this.unboundExternalContracts.remove(uniqueIdentifier);

		/* Remove the contracts from the list of all external contracts as well. */
		for (Contract contract : contracts) {
			this.removeExternalContract(contract);
		}

		/* Update change status. */
		this.setChanged();
	}

	/**
	 * <p>
	 * A helper method that removes a given bound external {@link Contract}s from
	 * the {@link ContractRegistry} for a given {@link IExtensionPoint}.
	 * </p>
	 * 
	 * @param extensionPoint
	 *          The {@link IExtensionPoint} for which the bound external
	 *          {@link Contract}s shall be removed.
	 * @param contract
	 *          The {@link Contract} that shall be removed.
	 */
	protected void removeBoundExternalContract(IExtensionPoint extensionPoint,
			Contract contract) {

		if (this.boundExternalContracts.containsKey(extensionPoint)) {

			LinkedHashSet<Contract> boundContracts;
			boundContracts = this.boundExternalContracts.get(extensionPoint);

			boundContracts.remove(contract);

			if (boundContracts.size() > 0) {
				this.boundExternalContracts.put(extensionPoint, boundContracts);
			}

			else {
				this.boundExternalContracts.remove(extensionPoint);
			}

			/* Remove the contract from the list of all external contracts as well. */
			this.removeExternalContract(contract);

			/* Update change status. */
			this.setChanged();
		}
		// no else.
	}

	/**
	 * <p>
	 * Removes a given {@link Bundle} from the List of contracted {@link Bundle}s.
	 * </p>
	 * 
	 * @param bundle
	 *          The {@link Bundle} that shall be removed.
	 */
	protected void removeContractedBundle(Bundle bundle) {

		this.contractedPlugins.remove(bundle);

		/* Update change status. */
		this.setChanged();
	}

	/**
	 * <p>
	 * Removes a given {@link Contract} from a given {@link IExtensionPoint}.
	 * </p>
	 * 
	 * @param extensionPoint
	 *          The {@link IExtensionPoint} from that the {@link Contract} shall
	 *          be removed.
	 * @param contract
	 *          The {@link Contract} that shall be removed.
	 */
	protected void removeContractFromExtensionPoint(
			IExtensionPoint extensionPoint, Contract contract) {

		LinkedHashSet<Contract> definedContracts;

		/* Remove the contract from the extension point. */
		if (this.contractedExtensionPoints.containsKey(extensionPoint)) {

			definedContracts = this.contractedExtensionPoints.get(extensionPoint);
			definedContracts.remove(contract);

			/* Probably remove the contracted extension point. */
			if (definedContracts.size() == 0) {
				this.contractedExtensionPoints.remove(extensionPoint);
			}

			/* Else only remove the contract from the registry. */
			else {
				this.contractedExtensionPoints.put(extensionPoint, definedContracts);
			}
			// end else.
		}
		// no else.

		/* Update change status. */
		this.setChanged();
	}

	/**
	 * <p>
	 * A helper method that removes a given unbound external {@link Contract}s
	 * from the {@link ContractRegistry} for a given {@link IExtensionPoint}'s ID.
	 * </p>
	 * 
	 * @param extensionPointID
	 *          The unique ID of {@link IExtensionPoint} for which the unbound
	 *          external {@link Contract}s shall be removed.
	 * @param contract
	 *          The {@link Contract} that shall be removed.
	 */
	protected void removeUnboundExternalContract(String extensionPointID,
			Contract contract) {

		LinkedHashSet<Contract> boundContracts;
		boundContracts = this.unboundExternalContracts.get(extensionPointID);

		boundContracts.remove(contract);

		if (boundContracts.size() > 0) {
			this.unboundExternalContracts.put(extensionPointID, boundContracts);
		}

		else {
			this.unboundExternalContracts.remove(extensionPointID);
		}

		/* Remove the contract from the list of all external contracts as well. */
		this.removeExternalContract(contract);

		/* Update change status. */
		this.setChanged();
	}

	/**
	 * <p>
	 * This method can be used to set the {@link Contract} of an
	 * {@link IExtension} externally.
	 * </p>
	 * 
	 * @param extension
	 *          The {@link IExtension} whose {@link Contract}s shall be set.
	 * @param contracts
	 *          The {@link Contract}s that shall be set.
	 */
	protected void setContractsOfExtension(IExtension extension,
			LinkedHashSet<Contract> contracts) {

		this.contractedExtensions.put(extension, contracts);

		/* Update change status. */
		this.setChanged();
	}

	/**
	 * <p>
	 * This method can be used to add a {@link Contract} to the list of external
	 * {@link Contract}s.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} that shall be added.
	 */
	private void addExternalContract(Contract contract) {

		LinkedHashSet<Contract> externalContractsOfExtension;
		IExtension legislatorExtension;

		legislatorExtension =
				((EclipseExtension) contract.getOwner()).getWrappedExtension();

		if (this.externalContracts.containsKey(legislatorExtension)) {
			externalContractsOfExtension =
					this.externalContracts.get(legislatorExtension);
		}

		else {
			externalContractsOfExtension = new LinkedHashSet<Contract>();
		}

		externalContractsOfExtension.add(contract);

		this.externalContracts.put(legislatorExtension,
				externalContractsOfExtension);

		/* Update change status. */
		this.setChanged();
	}

	/**
	 * <p>
	 * This method can be used to remove a {@link Contract} from the list of
	 * external {@link Contract}s.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} that shall be added.
	 */
	private void removeExternalContract(Contract contract) {

		LinkedHashSet<Contract> externalContractsOfExtension;
		IExtension legislatorExtension;

		legislatorExtension =
				((EclipseExtension) contract.getOwner()).getWrappedExtension();

		if (this.externalContracts.containsKey(legislatorExtension)) {
			externalContractsOfExtension =
					this.externalContracts.get(legislatorExtension);

			externalContractsOfExtension.remove(contract);

			if (externalContractsOfExtension.size() == 0) {
				this.externalContracts.remove(legislatorExtension);
			}

			else {
				this.externalContracts.put(legislatorExtension,
						externalContractsOfExtension);
			}

			/* Update change status. */
			this.setChanged();
		}
		// no else.
	}

	/**
	 * <p>
	 * Transforms the given value of a {@link BundleEvent}'s type into the name of
	 * the represented type.
	 * </p>
	 * 
	 * @param type
	 *          The type whose name shall be returned.
	 * @return The name of the given type.
	 */
	private String getBundleEventTypeName(int type) {

		switch (type) {

		case BundleEvent.INSTALLED:
			return "INSTALLED";
		case BundleEvent.LAZY_ACTIVATION:
			return "LAZY_ACTIVATION";
		case BundleEvent.RESOLVED:
			return "RESOLVED";
		case BundleEvent.STARTED:
			return "STARTED";
		case BundleEvent.STARTING:
			return "STARTING";
		case BundleEvent.STOPPED:
			return "STOPPED";
		case BundleEvent.STOPPING:
			return "STOPPING";
		case BundleEvent.UNINSTALLED:
			return "UNINSTALLED";
		case BundleEvent.UNRESOLVED:
			return "UNRESOLVED";
		case BundleEvent.UPDATED:
			return "UPDATED";
		default:
			return "UNKNOWN";
		}
	}
}