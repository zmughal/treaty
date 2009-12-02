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

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.Role;
import net.java.treaty.TreatyException;
import net.java.treaty.contractregistry.ContractRegistry.UpdateType;
import net.java.treaty.eclipse.Constants;
import net.java.treaty.eclipse.EclipseContractFactory;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.EclipseExtensionPoint;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.Logger;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.osgi.framework.Bundle;

/**
 * <p>
 * A {@link Job} that is responsible to update the
 * {@link EclipseContractRegistry} after the add of an Array of
 * {@link IExtension}s.
 * </p>
 * 
 * @author Claas Wilke
 */
public class UpdateJobExtensionsAdded extends Job {

	/**
	 * The work of {@link UpdateJobExtensionsAdded}s to search for contracted
	 * {@link IExtension}s.
	 */
	private static final int WORK_CONTRACTED_EXTENSIONS = 33000;

	/**
	 * The work of {@link UpdateJobExtensionsAdded}s to search for external
	 * {@link Contract}s.
	 */
	private static final int WORK_EXTERNAL_CONTRACTS = 33000;

	/**
	 * The {@link EclipseExtension}s to which this
	 * {@link UpdateJobExtensionsAdded} belongs to.
	 */
	private Set<EclipseExtension> myEclipseExtensions;

	/**
	 * The {@link IExtension}s of the {@link EclipseExtension}s of this
	 * {@link UpdateJobExtensionsAdded}.
	 */
	private Map<EclipseExtension, IExtension> myMappedExtensions =
			new HashMap<EclipseExtension, IExtension>();

	/**
	 * <p>
	 * Creates a new {@link UpdateJobExtensionsAdded} for a given array of
	 * {@link IExtension}s that have been added.
	 * </p>
	 * 
	 * @param extensions
	 *          The {@link IExtension}s that have been added.
	 * @param name
	 *          The name of this {@link Job}.
	 */
	public UpdateJobExtensionsAdded(IExtension[] extensions, String name) {

		super(name);

		Set<EclipseExtension> eclipseExtensions;
		eclipseExtensions = new HashSet<EclipseExtension>();

		boolean extensionWasInvalid;
		extensionWasInvalid = false;

		for (IExtension extension : extensions) {

			try {
				EclipseExtension eclipseExtension;
				eclipseExtension =
						EclipseAdapterFactory.getInstance().createExtension(extension);
				eclipseExtensions.add(eclipseExtension);

				this.myMappedExtensions.put(eclipseExtension, extension);
			}
			// end try.

			catch (EclipseConnectorAdaptationException e) {
				Logger.warn("IExtension " + extension
						+ " is invalid. Try to clean the ContractRegistry manually.", e);

				extensionWasInvalid = true;
			}
			// end catch.
		}
		// end for (iteration on extension points).

		this.myEclipseExtensions = eclipseExtensions;

		if (extensionWasInvalid) {
			Job jobCleanRegistry;
			jobCleanRegistry =
					new UpdateJobCleanContractRegistry(
							"Invalid Extension found. Clean the ContractRegistry.");

			jobCleanRegistry.schedule();
		}
		// no else.
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
		monitor.beginTask("Update ContracRegistry.", WORK_CONTRACTED_EXTENSIONS
				+ WORK_EXTERNAL_CONTRACTS);

		try {
			/* First, check for external contracts. */
			this.searchForLegislatorContracts(monitor);

			/*
			 * Afterwards, check for extensions that can be bound to already existing
			 * extension points and contracts.
			 */
			this.searchForExtensionsWithContracts(monitor);
		}

		/*
		 * Log every exception otherwhise the exception is lost, thus the Job is
		 * running in its own thread.
		 */
		catch (Exception e) {

			Logger.error("Unexpected Exception during update of Contract Registry.",
					e);
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

		/* If not extension has been given at all, mark this task as worked. */
		if (this.myEclipseExtensions == null
				|| this.myEclipseExtensions.size() == 0) {
			monitor.worked(WORK_EXTERNAL_CONTRACTS);
		}
		// no else.

		/* Iterate on the extensions and search for legislator contracts. */
		for (EclipseExtension eclipseExtension : this.myEclipseExtensions) {

			/* Check if the extension provides an external contract. */
			if (eclipseExtension.getExtensionPoint().getId().equals(
					Constants.LEGISLATOR_CONTRACT_EXTENSION_POINT_ID)) {

				/*
				 * Iterate through the attributes of the extension and try to load their
				 * contracts.
				 */
				for (IConfigurationElement extensionAttribute : this.myMappedExtensions
						.get(eclipseExtension).getConfigurationElements()) {

					try {
						String contractLocation;
						contractLocation =
								extensionAttribute
										.getAttribute(Constants.LEGISLATOR_CONTRACT_ATTRIBUTE_LOCATION);

						Bundle bundle;
						bundle = ((EclipsePlugin) eclipseExtension.getOwner()).getBundle();

						if (bundle != null && contractLocation != null) {

							URL contractUrl;
							contractUrl = bundle.getEntry(contractLocation);

							/* Check if the contract leads to an invalid URL. */
							if (contractUrl == null) {
								Logger.warn("No Legislator Contract found for location "
										+ contractUrl);
							}

							/* Else try to add the contract to the registry. */
							else {

								/* Add the contract to the resgistry. */
								Contract legislatorContract;
								legislatorContract =
										EclipseContractFactory.INSTANCE.createContract(contractUrl,
												eclipseExtension);

								EclipseContractRegistry.getInstance().updateContract(
										UpdateType.ADD_CONTRACT, legislatorContract,
										eclipseExtension, Role.LEGISLATOR);

								/* Search for a contracted extension point. */
								String extensionPointID;
								IExtensionPoint extensionPoint;

								extensionPointID =
										contractLocation.substring(contractLocation
												.lastIndexOf("/") + 1, contractLocation.length()
												- ".contract".length());
								extensionPoint =
										org.eclipse.core.runtime.Platform.getExtensionRegistry()
												.getExtensionPoint(extensionPointID);

								/*
								 * If no extension point has been found, store the unbound
								 * contract.
								 */
								if (extensionPoint == null) {

									/* Store the unbound legislator to bind it later on. */
									EclipseContractRegistry.getInstance()
											.addUnboundLegislatorContract(extensionPointID,
													legislatorContract);
								}

								/* Else add the legislator contract to the extension point. */
								else {
									try {
										EclipseExtensionPoint eclipseExtensionPoint;
										eclipseExtensionPoint =
												EclipseAdapterFactory.getInstance()
														.createExtensionPoint(extensionPoint);

										this.addContractToExtensionPoint(eclipseExtensionPoint,
												legislatorContract);
									}
									// end try.

									catch (EclipseConnectorAdaptationException e) {
										Logger
												.warn(
														"IExtensionPoint "
																+ extensionPoint
																+ " is invalid. Try to clean the ContractRegistry manually.",
														e);

										Job jobCleanRegistry;
										jobCleanRegistry =
												new UpdateJobCleanContractRegistry(
														"Invalid ExtensionPoint found. Clean the ContractRegistry.");

										jobCleanRegistry.schedule();
									}
									// end catch.
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

			monitor.worked(WORK_EXTERNAL_CONTRACTS / this.myEclipseExtensions.size());
		}
		// end for (iteration on extensions).
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

		/* If not extension has been given at all, mark this task as worked. */
		if (this.myEclipseExtensions == null
				|| this.myEclipseExtensions.size() == 0) {
			monitor.worked(WORK_CONTRACTED_EXTENSIONS);
		}
		// no else.

		/*
		 * Iterate through the extensions and check if their extension point has a
		 * contract.
		 */
		for (EclipseExtension eclipseExtension : this.myEclipseExtensions) {

			EclipseExtensionPoint eclipseExtensionPoint;
			eclipseExtensionPoint = eclipseExtension.getExtensionPoint();

			/* Check if the extension's extension point is contracted. */
			if (eclipseExtensionPoint.getContracts().size() > 0) {

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

			monitor.worked(WORK_CONTRACTED_EXTENSIONS
					/ this.myEclipseExtensions.size());
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

		IExtensionPoint extensionPoint;
		extensionPoint =
				org.eclipse.core.runtime.Platform.getExtensionRegistry()
						.getExtensionPoint(eclipseExtensionPoint.getId());

		boolean extensionWasInvalid;
		extensionWasInvalid = false;

		/*
		 * Don't iterate on the eclipse extensions here, they could not be added to
		 * the extension point yet!
		 */
		for (IExtension extension : extensionPoint.getExtensions()) {

			try {
				EclipseExtension eclipseExtension;
				eclipseExtension =
						EclipseAdapterFactory.getInstance().createExtension(extension);

				eclipseExtensionPoint.addExtension(eclipseExtension);

				EclipseContractRegistry.getInstance().updateContract(
						UpdateType.ADD_CONTRACT, contract, eclipseExtension, Role.SUPPLIER);
			}
			// end try.

			catch (EclipseConnectorAdaptationException e) {
				Logger.warn("IExtension " + extension
						+ " is invalid. Try to clean the ContractRegistry manually.", e);

				extensionWasInvalid = true;
			}

			catch (TreatyException e) {

				Logger.error("Unexpected TreatyException during adding a Contract "
						+ "to an ExtensionPoint.", e);
			}
		}
		// end for (iteration over contracted extensions).

		if (extensionWasInvalid) {
			Job jobCleanRegistry;
			jobCleanRegistry =
					new UpdateJobCleanContractRegistry(
							"Invalid Extension found. Clean the ContractRegistry.");

			jobCleanRegistry.schedule();
		}
		// no else.
	}
}