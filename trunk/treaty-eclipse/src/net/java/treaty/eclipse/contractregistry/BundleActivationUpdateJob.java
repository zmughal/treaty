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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.TreatyException;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.EclipseExtensionPoint;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.EclipseResourceManager;
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
 * A {@link Job} that is responsible to update the {@link ContractRegistry}
 * after the activation of a new {@link Bundle}.
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
	private static final String EXTERNAL_CONTRACT_ID =
			"net.java.treaty.eclipse.contract";

	/**
	 * The name of the attribute of {@link IExtension}s used to provide external
	 * contracts that lead to the contract definition.
	 */
	private static final String EXTERNAL_CONTRACT_ATTRIBUTE_LOCATION = "location";

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
			/*
			 * First check if the bundle provides extension points that contract
			 * themselves or belong to existing external contracts.
			 */
			this.tryToAddContractedExtensionPoints(monitor);

			/*
			 * Afterwards, check if the bundle provides extensions that can be bound
			 * to already existing extension points and contracts.
			 */
			this.tryToAddContractedExtensions(monitor);

			/*
			 * Afterwards, check if the bundle provides external contracts (whether or
			 * not they can be bound already to the contracted extension points).
			 */
			this.tryToAddExternalContracts(monitor);
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		monitor.done();

		return Status.OK_STATUS;
	}

	/**
	 * <p>
	 * A helper method that checks if a given {@link Bundle} contains contracted
	 * {@link IExtensionPoint}s and adds them to the {@link ContractRegistry}.
	 * Contracted {@link IExtensionPoint} could provide their own {@link Contract}
	 * or could belong to external {@link Contract}s that have been defined
	 * before.
	 * </p>
	 * 
	 * @param monitor
	 *          The {@link IProgressMonitor} used to monitor this {@link Job}.
	 */
	private void tryToAddContractedExtensionPoints(IProgressMonitor monitor) {

		/* Update monitor status. */
		monitor.subTask("Check for contracted ExtensionPoints.");

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

			/* If a contract has been found, add it to the extension point. */
			if (contractURL != null) {

				this.addContractToExtensionPoint(extensionPoint, contractURL);

				/* Register the plug-in as contracted. */
				ContractRegistry.getInstance().addContractedBundle(this.myBundle);
			}
			// no else (extension point not contracted by own plug-in).

			/*
			 * Check as well, if an external contract that is not bound yet exists.
			 */
			if (ContractRegistry.getInstance()
					.hasExtensionPointUnboundExternalContracts(
							extensionPoint.getUniqueIdentifier())) {

				/*
				 * Iterate through all external contracts and add them to the extension
				 * point.
				 */
				for (Contract externalContract : ContractRegistry.getInstance()
						.getUnboundExternalContractsOfExtensionPoint(
								extensionPoint.getUniqueIdentifier())) {

					this.addExternalContractToExtensionPoint(extensionPoint,
							externalContract);
				}
				// end for (iteration on found external contracts).

				/* Store the extension point's plug-in as contracted. */
				ContractRegistry.getInstance().addContractedBundle(this.myBundle);

				/* Remove the external contract from the unbound contracts. */
				ContractRegistry.getInstance().removeAllUnboundExternalContracts(
						extensionPoint.getUniqueIdentifier());
			}
			// no else (no external contract found).

			monitor.worked(WORK_INTERNAL_CONTRACTS / extensionPoints.length);
		}
		// end for (iteration on external contracts).
	}

	/**
	 * <p>
	 * A helper method that checks if a given {@link Bundle} contains contracted
	 * {@link IExtension}s and adds them to the {@link ContractRegistry}.
	 * </p>
	 * 
	 * @param monitor
	 *          The {@link IProgressMonitor} used to monitor this {@link Job}.
	 */
	private void tryToAddContractedExtensions(IProgressMonitor monitor) {

		/* Update monitor status. */
		monitor.subTask("Check for contracted Extensions.");

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

			/*
			 * Check if the extension's extension point is contracted (includes that
			 * the extension point's plug-in is active.
			 */
			if (ContractRegistry.getInstance().hasExtensionPointContracts(
					extensionPoint)) {

				EclipseExtensionPoint eclipseExtensionPoint;
				EclipseExtension eclipseExtension;

				eclipseExtensionPoint =
						EclipseAdapterFactory.getInstance().createExtensionPoint(
								extensionPoint);
				eclipseExtension =
						EclipseAdapterFactory.getInstance().createExtension(extension);

				eclipseExtensionPoint.addExtension(eclipseExtension);

				/* Instantiate the contract of the extension. */
				this.updateOrInstantiateContracts(eclipseExtension);
			}
			// no else (extension not contracted).

			monitor.worked(WORK_CONTRACTED_EXTENSIONS / extensions.length);
		}
		// end for.
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
	private void tryToAddExternalContracts(IProgressMonitor monitor) {

		/* Update monitor status. */
		monitor.subTask("Check for external contracted ExtensionPoints.");

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

		/* Iterate on the extensions and search for external contracts. */
		for (IExtension extension : extensions) {

			/* Check if the extension provides an external contract. */
			if (extension.getExtensionPointUniqueIdentifier().equals(
					EXTERNAL_CONTRACT_ID)) {

				/*
				 * Iterate through the attributes of the extension and try to load their
				 * contracts.
				 */
				for (IConfigurationElement extensionAttribute : extension
						.getConfigurationElements()) {

					String contractLocation;
					contractLocation =
							extensionAttribute
									.getAttribute(EXTERNAL_CONTRACT_ATTRIBUTE_LOCATION);

					if (this.myBundle != null && contractLocation != null) {

						URL contractUrl;
						contractUrl = this.myBundle.getEntry(contractLocation);

						/* Check if the contract leads to an invalid URL. */
						if (contractUrl == null) {
							Logger.warn("No contract found for location " + contractUrl);
						}

						/* Else add the contract to the registry. */
						else {

							String contractedExtensionPointName;
							IExtensionPoint contractedExtensionPoint;
							EclipseExtension legislatorExtension;

							legislatorExtension =
									EclipseAdapterFactory.getInstance()
											.createExtension(extension);

							/* Check contract naming pattern. */
							contractedExtensionPointName =
									contractLocation.substring(
											contractLocation.lastIndexOf("/") + 1, contractLocation
													.length()
													- ".contract".length());
							contractedExtensionPoint =
									extensionRegistry
											.getExtensionPoint(contractedExtensionPointName);

							/*
							 * Check if no extension point for the given name has been found
							 * or if the found ExtensionPoint's bundle has not been activated
							 * yet.
							 */
							if (contractedExtensionPoint == null
									|| org.eclipse.core.runtime.Platform.getBundle(
											contractedExtensionPoint.getContributor().getName())
											.getState() != Bundle.ACTIVE) {

								this.addUnboundExternalContract(contractUrl,
										legislatorExtension, contractedExtensionPointName);
							}

							/* Else add the external contract to the extension point. */
							else {

								EclipseExtensionPoint contractedEclipseExtensionPoint;
								Contract externalContract;

								contractedEclipseExtensionPoint =
										EclipseAdapterFactory.getInstance().createExtensionPoint(
												contractedExtensionPoint);

								externalContract = new Contract();
								externalContract.setLocation(contractUrl);
								externalContract.setOwner(legislatorExtension);

								this.addExternalContractToExtensionPoint(
										contractedExtensionPoint, externalContract);

								/* Store the contracted plug-in as well. */
								EclipsePlugin contractedEclipsePlugin;
								contractedEclipsePlugin =
										(EclipsePlugin) contractedEclipseExtensionPoint.getOwner();

								ContractRegistry.getInstance().addContractedBundle(
										contractedEclipsePlugin.getBundle());
							}
							// end else (bound or unbound external contract).
						}
						// end else (valid location).
					}
					// no else (bundle or location was null).
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
	 * A helper method that adds a given {@link Contract} (as its {@link URL}) to
	 * a given {@link IExtensionPoint}.
	 * </p>
	 * 
	 * @param extensionPoint
	 *          The IEP to that the contract shall be added.
	 * @param contractURL
	 *          The {@link URL} of the {@link Contract} that shall be added.
	 */
	private void addContractToExtensionPoint(IExtensionPoint extensionPoint,
			URL contractURL) {

		EclipseExtensionPoint eclipseExtensionPoint;
		Contract newContract;

		eclipseExtensionPoint =
				EclipseAdapterFactory.getInstance()
						.createExtensionPoint(extensionPoint);

		newContract =
				EclipseExtensionPoint
						.createContract(contractURL, eclipseExtensionPoint);

		/* Store the new contract in the registry. */
		newContract.setConsumer(eclipseExtensionPoint);
		ContractRegistry.getInstance().addContractToExtensionPoint(extensionPoint,
				newContract);

		/*
		 * For contracted extension points also adapt their extensions (if any
		 * extension has been found already).
		 */
		this.addOrUpdateContractedExtensionsOfExtensionPoint(eclipseExtensionPoint);
	}

	/**
	 * <p>
	 * A helper method that adds a given external {@link Contract} to a given
	 * {@link IExtensionPoint}.
	 * </p>
	 * 
	 * @param extensionPoint
	 *          The {@link IExtensionPoint} to that an external {@link Contract}
	 *          shall be bound.
	 * @param contract
	 *          The {@link Contract} that shall be bound to the
	 *          {@link IExtensionPoint}.
	 */
	private void addExternalContractToExtensionPoint(
			IExtensionPoint extensionPoint, Contract contract) {

		EclipseExtensionPoint eclipseExtensionPoint;
		eclipseExtensionPoint =
				EclipseAdapterFactory.getInstance()
						.createExtensionPoint(extensionPoint);

		/* Add the external contract to the extension point in the registry. */
		contract.setConsumer(eclipseExtensionPoint);
		ContractRegistry.getInstance().addContractToExtensionPoint(extensionPoint,
				contract);

		/*
		 * For contracted extension points also adapt their extensions (if any
		 * extension has been found already).
		 */
		this.addOrUpdateContractedExtensionsOfExtensionPoint(eclipseExtensionPoint);

		/* Add the newly bound external contract to the registry. */
		ContractRegistry.getInstance().addBoundExternalContract(extensionPoint,
				contract);
	}

	/**
	 * <p>
	 * A helper method that iterates over the {@link EclipseExtension}s of a given
	 * {@link EclipseExtensionPoint} and checks if the
	 * {@link EclipseExtensionPoint}'s {@link Contract} should be instantiated for
	 * them.
	 * </p>
	 * 
	 * @param extensionPoint
	 *          The {@link IExtensionPoint} whose {@link IExtension} shall be
	 *          checked.
	 */
	private void addOrUpdateContractedExtensionsOfExtensionPoint(
			EclipseExtensionPoint eclipseExtensionPoint) {

		/*
		 * Do not iterate on the eclipse extensions! They are added in this for loop
		 * and are not available yet.
		 */
		for (IExtension extension : eclipseExtensionPoint
				.getWrappedExtensionPoint().getExtensions()) {

			/* Check if the extension's bundle is already active. */
			if (org.eclipse.core.runtime.Platform.getBundle(
					extension.getContributor().getName()).getState() == Bundle.ACTIVE) {

				EclipseExtension eclipseExtension;

				eclipseExtension =
						EclipseAdapterFactory.getInstance().createExtension(extension);
				eclipseExtensionPoint.addExtension(eclipseExtension);

				/* Instantiate the extension's contract for the found extension. */
				this.updateOrInstantiateContracts(eclipseExtension);
			}
			// no else (extensions bundle is not active yet).
		}
		// end for (iteration over contracted extensions).
	}

	/**
	 * <p>
	 * A helper method that adds an given external {@link Contract} as unbound to
	 * the {@link ContractRegistry}.
	 * </p>
	 * 
	 * @param contractUrl
	 *          The {@link URL} of the external {@link Contract}
	 * @param legislatorExtension
	 *          The legislator {@link EclipseExtension} that defines the external
	 *          {@link Contract}.
	 * @param contractedExtensionPointID
	 *          The ID of the {@link IExtensionPoint} that is contracted by the
	 *          external {@link Contract}.
	 */
	private void addUnboundExternalContract(URL contractUrl,
			EclipseExtension legislatorExtension, String contractedExtensionPointID) {

		Contract externalContract;

		externalContract = new Contract();
		externalContract.setLocation(contractUrl);
		externalContract.setOwner(legislatorExtension);

		ContractRegistry.getInstance().addUnboundExternalContract(
				contractedExtensionPointID, externalContract);
	}

	/**
	 * <p>
	 * A helper method that instantiates or updates an
	 * {@link EclipseExtensionPoint}'s {@link Contract}s for a given
	 * {@link EclipseExtension}.
	 * </p>
	 * 
	 * @param eclipseExtension
	 *          The {@link EclipseExtension} for that the {@link Contract} shall
	 *          be instantiated.
	 */
	private void updateOrInstantiateContracts(EclipseExtension eclipseExtension) {

		EclipseResourceManager eclipseMgr;
		eclipseMgr = new EclipseResourceManager();

		EclipseExtensionPoint eclipseExtensionPoint;
		eclipseExtensionPoint = eclipseExtension.getExtensionPoint();

		/* Check if a contract has defined on the given extension point at all. */
		if (ContractRegistry.getInstance().hasExtensionPointContracts(
				eclipseExtensionPoint.getWrappedExtensionPoint())) {

			/* Reinitialize all contracts of the extension point. */
			for (Contract contract : eclipseExtensionPoint.getContracts()) {

				LinkedHashSet<Contract> instantiatedContracts;
				instantiatedContracts = new LinkedHashSet<Contract>();

				/* Try to instantiate the given contract. */
				try {
					List<Contract> newInstantiatedContracts;

					newInstantiatedContracts =
							contract.bindSupplier(eclipseExtension, eclipseMgr);
					instantiatedContracts.addAll(newInstantiatedContracts);

					ContractRegistry.getInstance().setContractsOfExtension(
							eclipseExtension.getWrappedExtension(), instantiatedContracts);
				}

				catch (TreatyException e) {
					Logger.error("Error instantiating contract " + contract
							+ " for extension " + eclipseExtension.getId(), e);
				}
				// end catch.
			}
			// end for (iteration on contracts).
		}
		// no else (no defined contract found).
	}
}