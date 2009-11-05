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

import java.net.URL;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.Role;
import net.java.treaty.TreatyException;
import net.java.treaty.contractregistry.ContractRegistry.UpdateType;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.EclipseExtensionPoint;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.Logger;

import org.eclipse.core.runtime.ContributorFactoryOSGi;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.osgi.framework.Bundle;

/**
 * <p>
 * A {@link Job} that is responsible to update the
 * {@link EclipseContractRegistry} after the activation of a new {@link Bundle}.
 * </p>
 * 
 * @author Claas Wilke
 * 
 */
public class BundleActivationUpdateJob extends Job {

	/**
	 * The work of {@link BundleActivationUpdateJob}s to search for
	 * {@link Contract}s defined with a {@link Bundle}'s {@link IExtensionPoint}s.
	 */
	private static final int WORK_INTERNAL_CONTRACTS = 33000;

	/**
	 * The work of {@link BundleActivationUpdateJob}s to search for contracted
	 * {@link IExtension}s.
	 */
	private static final int WORK_CONTRACTED_EXTENSIONS = 33000;

	/**
	 * The work of {@link BundleActivationUpdateJob}s to search for external
	 * {@link Contract}s.
	 */
	private static final int WORK_EXTERNAL_CONTRACTS = 33000;

	/** The id of the {@link IExtensionPoint} used to provide external contracts. */
	private static final String LEGISLATOR_CONTRACT_EXTENSION_POINT_ID =
			"net.java.treaty.eclipse.contract";

	/**
	 * The name of the attribute of {@link IExtension}s used to provide external
	 * contracts that lead to the contract definition.
	 */
	private static final String LEGISLATOR_CONTRACT_ATTRIBUTE_LOCATION =
			"location";

	/**
	 * The prefix of the location where {@link Contract}s are located inside the
	 * same plug-in, whose {@link IExtensionPoint} they contract. Generally
	 * speaking, this leads to the folder inside the plug-in, where the
	 * {@link Contract} is located.
	 */
	private static final String INTERNAL_CONTRACT_LOCATION_PREFIX = "/META-INF/";

	/**
	 * The suffix of the file where {@link Contract}s are located inside the same
	 * plug-in, whose {@link IExtensionPoint} they contract.
	 */
	private static final String CONTRACT_LOCATION_SUFFIX = ".contract";

	/**
	 * The {@link Bundle} to which this {@link BundleActivationUpdateJob} belongs
	 * to.
	 */
	private Bundle myBundle;

	/**
	 * <p>
	 * Creates a new {@link BundleActivationUpdateJob} for a given {@link Bundle}
	 * that is now active.
	 * </p>
	 * 
	 * @param bundle
	 *          The {@link Bundle} that has been activated.
	 * @param name
	 *          The name of this {@link Job}.
	 */
	public BundleActivationUpdateJob(Bundle bundle, String name) {

		super(name);

		this.myBundle = bundle;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor
	 * )
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {

		/* Update monitor status. */
		monitor.beginTask("Update ContracRegistry.", WORK_INTERNAL_CONTRACTS
				+ WORK_CONTRACTED_EXTENSIONS + WORK_EXTERNAL_CONTRACTS);

		// FIXME Claas: Remove try after debugging.
		try {
			/* First, check if the bundle provides external contracts. */
			this.searchForLegislatorContracts(monitor);

			/*
			 * Afterwards, check if the bundle provides extension points that contract
			 * themselves.
			 */
			this.searchForExtensionPointsWithContracts(monitor);

			/*
			 * Afterwards, check if the bundle provides extensions that can be bound
			 * to already existing extension points and contracts.
			 */
			this.searchForExtensionsWithContracts(monitor);
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		monitor.done();

		return Status.OK_STATUS;
	}

	/**
	 * <p>
	 * A helper method that checks if a given {@link Bundle} defines external
	 * treaty contracts on {@link IExtensionPoint}s and adds them to the
	 * {@link Set} of bound or unbound external {@link Contract}s, depending on
	 * whether or not the contracted {@link IExtensionPoint} has been loaded
	 * before or not.
	 * </p>
	 * 
	 * @param monitor
	 *          The {@link IProgressMonitor} used to monitor this {@link Job}.
	 */
	private void searchForLegislatorContracts(IProgressMonitor monitor) {

		/* Update monitor status. */
		monitor.subTask("Search for Legislator Contracts in Extensions.");

		IContributor contributor;
		IExtension[] extensions;

		IExtensionRegistry extensionRegistry;

		/* Get the contributor for the bundle. */
		contributor = ContributorFactoryOSGi.createContributor(this.myBundle);

		/* Get the extension registry. */
		extensionRegistry =
				org.eclipse.core.runtime.Platform.getExtensionRegistry();

		/* Get the bundle's extensions. */
		extensions = extensionRegistry.getExtensions(contributor);

		/* If not extension has been given at all, mark this task as worked. */
		if (extensions.length == 0) {
			monitor.worked(WORK_EXTERNAL_CONTRACTS);
		}
		// no else.

		/* Iterate on the extensions and search for legislator contracts. */
		for (IExtension extension : extensions) {

			/* Check if the extension provides an external contract. */
			if (extension.getExtensionPointUniqueIdentifier().equals(
					LEGISLATOR_CONTRACT_EXTENSION_POINT_ID)) {

				/*
				 * Iterate through the attributes of the extension and try to load their
				 * contracts.
				 */
				for (IConfigurationElement extensionAttribute : extension
						.getConfigurationElements()) {

					try {
						String contractLocation;
						contractLocation =
								extensionAttribute
										.getAttribute(LEGISLATOR_CONTRACT_ATTRIBUTE_LOCATION);

						if (this.myBundle != null && contractLocation != null) {

							URL contractUrl;
							contractUrl = this.myBundle.getEntry(contractLocation);

							/* Check if the contract leads to an invalid URL. */
							if (contractUrl == null) {
								Logger.warn("No Legislator Contract found for location "
										+ contractUrl);
							}

							/* Else try to add the contract to the registry. */
							else {
								EclipseExtension legislatorExtension;

								legislatorExtension =
										EclipseAdapterFactory.getInstance().createExtension(
												extension);

								/* Add the contract to the resgistry. */
								Contract legislatorContract;
								legislatorContract = new Contract();
								legislatorContract.setLocation(contractUrl);
								legislatorContract.setOwner(legislatorExtension);

								EclipseContractRegistry.getInstance().updateContract(
										UpdateType.ADD_CONTRACT, legislatorContract,
										legislatorExtension, Role.LEGISLATOR);

								/* Search for a contracted extension point. */
								String extensionPointID;
								IExtensionPoint extensionPoint;

								extensionPointID =
										contractLocation.substring(contractLocation
												.lastIndexOf("/") + 1, contractLocation.length()
												- ".contract".length());
								extensionPoint =
										extensionRegistry.getExtensionPoint(extensionPointID);

								/* If no extension point has been found, log the error. */
								if (extensionPoint == null
										|| !EclipseContractRegistry.ACTIVE_BUNDLE_STATES
												.contains(org.eclipse.core.runtime.Platform.getBundle(
														extensionPoint.getContributor().getName())
														.getState())) {

									Logger.warn("No extension point with id " + extensionPointID
											+ " found for external contract " + contractLocation
											+ ".");

									/* Store the unbound legislator to bind it later on. */
									EclipseContractRegistry.getInstance()
											.addUnboundLegislatorContract(extensionPointID,
													legislatorContract);
								}

								/* Else add the legislator contract to the extension point. */
								else {
									EclipseExtensionPoint eclipseExtensionPoint;
									eclipseExtensionPoint =
											EclipseAdapterFactory.getInstance().createExtensionPoint(
													extensionPoint);

									this.addContractToExtensionPoint(eclipseExtensionPoint,
											legislatorContract);
								}
								// end else (legislator contract).
							}
							// end else (valid location).
						}
						// no else (bundle or location was null).
					}
					// end try.

					catch (TreatyException e) {

						Logger.error("Error during initialization of legislator Contract.",
								e);
					}
					// end catch.
				}
				// end for (iteration on extension attributes).
			}
			// no else (no external contract).

			monitor.worked(WORK_EXTERNAL_CONTRACTS / extensions.length);
		}
		// end for (iteration on extensions).
	}

	/**
	 * <p>
	 * A helper method that checks if a given {@link Bundle} contains contracted
	 * {@link IExtensionPoint}s and adds them to the
	 * {@link EclipseContractRegistry}.
	 * </p>
	 * 
	 * @param monitor
	 *          The {@link IProgressMonitor} used to monitor this {@link Job}.
	 */
	private void searchForExtensionPointsWithContracts(IProgressMonitor monitor) {

		/* Update monitor status. */
		monitor.subTask("Search for contracted ExtensionPoints.");

		IContributor contributor;
		IExtensionRegistry extensionRegistry;

		EclipsePlugin eclipsePlugin;
		IExtensionPoint[] extensionPoints;

		/* Get the contributor for the bundle. */
		contributor = ContributorFactoryOSGi.createContributor(this.myBundle);

		/* Get the extension registry. */
		extensionRegistry =
				org.eclipse.core.runtime.Platform.getExtensionRegistry();

		/* Get the EclipsePlugin. */
		eclipsePlugin =
				EclipseAdapterFactory.getInstance().createEclipsePlugin(this.myBundle);

		/*
		 * Get all extension points of the bundle and check for self provided
		 * contracts.
		 */
		extensionPoints = extensionRegistry.getExtensionPoints(contributor);

		/* If not extension point has been given at all, mark this task as worked. */
		if (extensionPoints.length == 0) {
			monitor.worked(WORK_INTERNAL_CONTRACTS);
		}
		// no else.

		for (IExtensionPoint extensionPoint : extensionPoints) {

			String contractName;
			URL contractURL;

			contractName =
					INTERNAL_CONTRACT_LOCATION_PREFIX
							+ extensionPoint.getUniqueIdentifier() + CONTRACT_LOCATION_SUFFIX;
			contractURL = eclipsePlugin.getResource(contractName);

			EclipseExtensionPoint eclipseExtensionPoint;
			eclipseExtensionPoint =
					EclipseAdapterFactory.getInstance().createExtensionPoint(
							extensionPoint);

			/* If a contract has been found, add it to the extension point. */
			if (contractURL != null) {

				Contract contract;

				contract =
						EclipseExtensionPoint.createContract(contractURL,
								eclipseExtensionPoint);

				this.addContractToExtensionPoint(eclipseExtensionPoint, contract);
			}
			// no else (extension point not contracted by own plug-in).

			/*
			 * Also check if the extension point has further unbound legislator
			 * contracts.
			 */
			for (Contract legislatorContract : EclipseContractRegistry.getInstance()
					.removeUnboundLegislatorContractsForContractedConnector(
							eclipseExtensionPoint)) {

				this.addContractToExtensionPoint(eclipseExtensionPoint,
						legislatorContract);
			}
			// no else (no unbound legislator contracts).

			monitor.worked(WORK_INTERNAL_CONTRACTS / extensionPoints.length);
		}
		// end for (iteration on external contracts).
	}

	/**
	 * <p>
	 * A helper method that checks if a given {@link Bundle} contains contracted
	 * {@link IExtension}s and adds them to the {@link EclipseContractRegistry}.
	 * </p>
	 * 
	 * @param monitor
	 *          The {@link IProgressMonitor} used to monitor this {@link Job}.
	 */
	private void searchForExtensionsWithContracts(IProgressMonitor monitor) {

		/* Update monitor status. */
		monitor.subTask("Search for contracted Extensions.");

		IContributor contributor;
		IExtensionRegistry extensionRegistry;
		IExtension[] extensions;

		/* Get the contributor for the bundle. */
		contributor = ContributorFactoryOSGi.createContributor(this.myBundle);

		/* Get the extension registry. */
		extensionRegistry =
				org.eclipse.core.runtime.Platform.getExtensionRegistry();

		/* Get the extensions of the bundle. */
		extensions = extensionRegistry.getExtensions(contributor);

		/* If not extension has been given at all, mark this task as worked. */
		if (extensions.length == 0) {
			monitor.worked(WORK_CONTRACTED_EXTENSIONS);
		}
		// no else.

		/*
		 * Iterate through the extensions and check if their extension point has a
		 * contract.
		 */
		for (IExtension extension : extensions) {

			IExtensionPoint extensionPoint;
			extensionPoint =
					extensionRegistry.getExtensionPoint(extension
							.getExtensionPointUniqueIdentifier());

			EclipseExtensionPoint eclipseExtensionPoint;
			eclipseExtensionPoint =
					EclipseAdapterFactory.getInstance().createExtensionPoint(
							extensionPoint);

			/* Check if the extension's extension point is contracted. */
			if (eclipseExtensionPoint.getContracts().size() > 0) {

				EclipseExtension eclipseExtension;

				eclipseExtension =
						EclipseAdapterFactory.getInstance().createExtension(extension);

				eclipseExtensionPoint.addExtension(eclipseExtension);

				for (Contract contract : eclipseExtensionPoint.getContracts()) {

					try {
						EclipseContractRegistry.getInstance().updateContract(
								UpdateType.ADD_CONTRACT, contract, eclipseExtension,
								Role.SUPPLIER);
					}

					catch (TreatyException e) {

						Logger.error("Unexpected TreatyException occurred during adding "
								+ "Contract of ExtensionPoint to Extension.", e);
					}
				}
				// end for.
			}
			// no else (extension not contracted).

			monitor.worked(WORK_CONTRACTED_EXTENSIONS / extensions.length);
		}
		// end for.
	}

	/**
	 * <p>
	 * A helper method that adds a given legislator {@link Contract} to a given
	 * {@link EclipseExtensionPoint}.
	 * </p>
	 * 
	 * @param eclipseExtensionPoint
	 *          The {@link EclipseExtensionPoint} to that a legislator
	 *          {@link Contract} shall be bound.
	 * @param contract
	 *          The {@link Contract} that shall be bound to the
	 *          {@link IExtensionPoint}.
	 */
	private void addContractToExtensionPoint(
			EclipseExtensionPoint eclipseExtensionPoint, Contract contract) {

		try {
			/* Add the external contract to the extension point in the registry. */
			EclipseContractRegistry.getInstance().updateContract(
					UpdateType.ADD_CONTRACT, contract, eclipseExtensionPoint,
					Role.CONSUMER);

			/* For contracted extension points also adapt their extensions. */
			for (Contract instantiatedContract : EclipseContractRegistry
					.getInstance()
					.getInstancesOfContract(contract, eclipseExtensionPoint)) {

				this.addContractToExtensionsOfExtensionPoint(eclipseExtensionPoint,
						instantiatedContract);
			}
		}
		// end try.

		catch (TreatyException e) {

			Logger.error("Unepected TreatyException during add of legislator "
					+ "Contract to ExtensionPoint.", e);
		}
		// end catch.
	}

	/**
	 * <p>
	 * A helper method that iterates over the {@link EclipseExtension}s of a given
	 * {@link EclipseExtensionPoint} adds a given {@link Contract} to them.
	 * </p>
	 * 
	 * @param eclipseExtensionPoint
	 *          The {@link EclipseExtensionPoint} to whose
	 *          {@link EclipseExtension}s the Contract shall be added.
	 */
	private void addContractToExtensionsOfExtensionPoint(
			EclipseExtensionPoint eclipseExtensionPoint, Contract contract) {

		/*
		 * Don't iterate on the eclipse extensions here, they could not be added to
		 * the extension point yet!
		 */
		for (IExtension extension : eclipseExtensionPoint
				.getWrappedExtensionPoint().getExtensions()) {

			try {
				EclipseExtension eclipseExtension;
				eclipseExtension =
						EclipseAdapterFactory.getInstance().createExtension(extension);

				/*
				 * Only add the Contract to the extension if the extension's plug'in is
				 * in the right state.
				 */
				if (EclipseContractRegistry.ACTIVE_BUNDLE_STATES
						.contains(((EclipsePlugin) eclipseExtension.getOwner()).getBundle()
								.getState())) {
					eclipseExtensionPoint.addExtension(eclipseExtension);

					EclipseContractRegistry.getInstance().updateContract(
							UpdateType.ADD_CONTRACT, contract, eclipseExtension,
							Role.SUPPLIER);
				}
				// no else.
			}

			catch (TreatyException e) {

				Logger.error("Unexpected TreatyException during adding a Contract "
						+ "to an ExtensionPoint.", e);
			}
		}
		// end for (iteration over contracted extensions).
	}
}