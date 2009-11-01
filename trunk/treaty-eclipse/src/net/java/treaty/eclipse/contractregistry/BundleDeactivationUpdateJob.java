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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.TreatyException;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.EclipseExtensionPoint;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.EclipseResourceManager;
import net.java.treaty.eclipse.Logger;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.osgi.framework.Bundle;

/**
 * <p>
 * A {@link Job} that is responsible to update the {@link ContractRegistry}
 * after the deactivation of a new {@link Bundle}.
 * </p>
 * 
 * @author Claas Wilke
 * 
 */
public class BundleDeactivationUpdateJob extends Job {

	/**
	 * The work of {@link BundleDeactivationUpdateJob}s to search for
	 * {@link Contract}s defined with a {@link Bundle}'s {@link IExtensionPoint}s.
	 */
	private static final int WORK_INTERNAL_CONTRACTS = 33000;

	/**
	 * The work of {@link BundleDeactivationUpdateJob}s to search for contracted
	 * {@link IExtension}s.
	 */
	private static final int WORK_CONTRACTED_EXTENSIONS = 33000;

	/**
	 * The work of {@link BundleDeactivationUpdateJob}s to search for external
	 * {@link Contract}s.
	 */
	private static final int WORK_EXTERNAL_CONTRACTS = 33000;

	/**
	 * The work of {@link BundleDeactivationUpdateJob}s to remove the
	 * {@link Bundle} from the {@link ContractRegistry}.
	 */
	private static final int WORK_BUNDLE_REMOVAL = 1000;

	/**
	 * The suffix of the file where {@link Contract}s are located inside the same
	 * plug-in, whose {@link IExtensionPoint} they contract.
	 */
	private static final String CONTRACT_LOCATION_SUFFIX = ".contract";

	/** The id of the {@link IExtensionPoint} used to provide external contracts. */
	private static final String EXTERNAL_CONTRACT_ID =
			"net.java.treaty.eclipse.contract";

	/**
	 * The {@link Bundle} to which this {@link BundleDeactivationUpdateJob}
	 * belongs to.
	 */
	private Bundle myBundle;

	/**
	 * <p>
	 * Creates a new {@link BundleDeactivationUpdateJob} for a given
	 * {@link Bundle} that is now active.
	 * </p>
	 * 
	 * @param bundle
	 *          The {@link Bundle} that has been activated.
	 * @param name
	 *          The name of this {@link Job}.
	 */
	public BundleDeactivationUpdateJob(Bundle bundle, String name) {

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
				+ WORK_CONTRACTED_EXTENSIONS + WORK_EXTERNAL_CONTRACTS
				+ WORK_BUNDLE_REMOVAL);

		// FIXME Claas: Remove try after debugging.
		try {
			/* Probably remove contracted extension points from the registry. */
			this.tryToRemoveContractedExtensionPoints(monitor);

			/* Probably remove contracted extensions from the registry. */
			this.tryToRemoveContractedExtensions(monitor);

			/* Probably remove external contracts from the registry. */
			this.tryToRemoveExternalContract(monitor);
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		/* Remove the contracted plug-in as well. */
		monitor.subTask("Remove bundle.");
		ContractRegistry.getInstance().removeContractedBundle(this.myBundle);
		monitor.worked(WORK_BUNDLE_REMOVAL);

		monitor.done();

		return Status.OK_STATUS;
	}

	/**
	 * <p>
	 * A helper method that checks if a given {@link Bundle} defines external
	 * treaty {@link Contract}s on {@link IExtensionPoint}s and removes them from
	 * the {@link Set} of bound and unbound external {@link Contract}s.
	 * </p>
	 * 
	 * @param monitor
	 *          The {@link IProgressMonitor} used to monitor this {@link Job}.
	 */
	private void tryToRemoveExternalContract(IProgressMonitor monitor) {

		/* Update monitor status. */
		monitor.subTask("Check for external Contracts.");

		EclipsePlugin eclipsePlugin;
		List<EclipseExtension> eclipseExtensions;

		/* Get the eclipse plugin. */
		eclipsePlugin =
				EclipseAdapterFactory.getInstance().createEclipsePlugin(this.myBundle);

		/* Get all extensions of the bundle. */
		eclipseExtensions = eclipsePlugin.getExtensions();

		/* If no extension point has been given at all, mark this task as worked. */
		if (eclipseExtensions.size() == 0) {
			monitor.worked(WORK_EXTERNAL_CONTRACTS);
		}
		// no else.

		/* Iterate on the extensions. */
		for (EclipseExtension eclipseExtension : eclipseExtensions) {

			/* Check if the extension provides external contracts. */
			if (eclipseExtension.getExtensionPoint() != null
					&& eclipseExtension.getExtensionPoint().getId().equals(
							EXTERNAL_CONTRACT_ID)) {

				/* Probably remove external contracts. */
				this.removeExternalContractsOfExtension(eclipseExtension
						.getWrappedExtension());
			}
			// no else (extension does not represent external contract).

			monitor.worked(WORK_EXTERNAL_CONTRACTS / eclipseExtensions.size());
		}
		// end for (iteration on extensions).
	}

	/**
	 * <p>
	 * A helper method that checks if a given {@link Bundle} contains contracted
	 * {@link IExtension}s and removes them from the {@link ContractRegistry}.
	 * </p>
	 * 
	 * @param monitor
	 *          The {@link IProgressMonitor} used to monitor this {@link Job}.
	 */
	private void tryToRemoveContractedExtensions(IProgressMonitor monitor) {

		/* Update monitor status. */
		monitor.subTask("Check for contracted Extensions.");

		EclipsePlugin eclipsePlugin;
		List<EclipseExtension> eclipseExtensions;

		/* Get the eclipse plugin. */
		eclipsePlugin =
				EclipseAdapterFactory.getInstance().createEclipsePlugin(this.myBundle);

		/* Get all extensions of the bundle. */
		eclipseExtensions = eclipsePlugin.getExtensions();

		/* If not extension point has been given at all, mark this task as worked. */
		if (eclipseExtensions.size() == 0) {
			monitor.worked(WORK_CONTRACTED_EXTENSIONS);
		}
		// no else.

		/*
		 * Iterate on extensions and search for extensions that can now be removed
		 * again.
		 */
		for (EclipseExtension eclipseExtension : eclipseExtensions) {

			if (eclipseExtension.getContracts().size() > 0) {

				/* Remove the contract from the extension. */
				ContractRegistry.getInstance().removeAllContractsFromExtension(
						eclipseExtension.getWrappedExtension());
			}
			// no else.

			monitor.worked(WORK_CONTRACTED_EXTENSIONS / eclipseExtensions.size());
		}
		// end for.
	}

	/**
	 * <p>
	 * A helper method that checks if a given {@link Bundle} contains contracted
	 * {@link IExtensionPoint}s and removes them from the {@link ContractRegistry}
	 * .
	 * </p>
	 * 
	 * @param monitor
	 *          The {@link IProgressMonitor} used to monitor this {@link Job}.
	 */
	private void tryToRemoveContractedExtensionPoints(IProgressMonitor monitor) {

		/* Update monitor status. */
		monitor.subTask("Check for contracted ExtensionPoints.");

		EclipsePlugin eclipsePlugin;
		List<EclipseExtensionPoint> eclipseExtensionPoints;

		/* Get the EclipsePlugin for the bundle. */
		eclipsePlugin =
				EclipseAdapterFactory.getInstance().createEclipsePlugin(this.myBundle);

		/* Get all extensions points of the plug-in. */
		eclipseExtensionPoints =
				new ArrayList<EclipseExtensionPoint>(eclipsePlugin.getExtensionPoints());

		/* If not extension point has been given at all, mark this task as worked. */
		if (eclipseExtensionPoints.size() == 0) {
			monitor.worked(WORK_INTERNAL_CONTRACTS);
		}
		// no else.

		/* Search for extension points that can now be removed again. */
		for (EclipseExtensionPoint eclipseExtensionPoint : eclipseExtensionPoints) {

			if (eclipseExtensionPoint.getContracts().size() > 0) {

				/*
				 * Remove the extension points extensions and also un-instantiate their
				 * contracts.
				 */
				this.removeContractedExtensionsOfExtensionPoint(eclipseExtensionPoint);

				/* Remove the contracted extension point. */
				ContractRegistry.getInstance().removeAllContractsFromExtensionPoint(
						eclipseExtensionPoint.getWrappedExtensionPoint());
			}
			// no else.

			/* Check if the extension point has external contracts. */
			if (ContractRegistry.getInstance().hasExtensionPointExternalContracts(
					eclipseExtensionPoint.getWrappedExtensionPoint())) {

				/* Un-bind the external contracts. */
				ContractRegistry.getInstance().addUnboundExternalContracts(
						eclipseExtensionPoint.getId(),
						ContractRegistry.getInstance().removeAllBoundExternalContracts(
								eclipseExtensionPoint.getWrappedExtensionPoint()));
			}
			// no else.

			monitor.worked(WORK_INTERNAL_CONTRACTS / eclipseExtensionPoints.size());
		}
		// end for.
	}

	/**
	 * <p>
	 * A helper method that iterates over the {@link EclipseExtension}s of a given
	 * {@link EclipseExtensionPoint} and removes the {@link EclipseExtension}s'
	 * {@link Contract}s from the {@link EclipseExtension}s.
	 * </p>
	 * 
	 * @param eclipseExtensionPoint
	 *          The {@link EclipseExtensionPoint} whose {@link EclipseExtension}
	 *          shall be checked.
	 */
	private void removeContractedExtensionsOfExtensionPoint(
			EclipseExtensionPoint eclipseExtensionPoint) {

		/*
		 * Must iterate on the IExtensions here to avoid trouble with removal of
		 * Objects during the iteration on the same collection.
		 */
		for (IExtension extension : eclipseExtensionPoint
				.getWrappedExtensionPoint().getExtensions()) {

			/* Check if the extension is contracted. */
			if (ContractRegistry.getInstance().hasExtensionContracts(extension)) {

				EclipseExtension eclipseExtension;
				eclipseExtension =
						EclipseAdapterFactory.getInstance().createExtension(extension);

				ContractRegistry.getInstance().removeAllContractsFromExtension(
						eclipseExtension.getWrappedExtension());

				/* Also remove the extension from the extension point. */
				eclipseExtensionPoint.removeExtension(eclipseExtension);
			}
			// no else (extension has not been contracted).
		}
		// end for (iteration over contracted extensions).
	}

	/**
	 * <p>
	 * A helper method that removes all external {@link Contract}s from the
	 * {@link ContractRegistry} that are defined by a given {@link IExtension}.
	 * </p>
	 * 
	 * @param legislatorExtension
	 *          The {@link IExtension} whose external {@link Contract}s shall be
	 *          removed.
	 */
	private void removeExternalContractsOfExtension(IExtension legislatorExtension) {

		/* Iterate through all defined external contracts of the given extension. */
		for (Contract externalContract : ContractRegistry.getInstance()
				.getExternalContractsOfExtension(legislatorExtension)) {

			/* Check if the contract is bound. */
			if (externalContract.getConsumer() != null) {

				EclipsePlugin contractedPlugin;
				EclipseExtensionPoint contractedEclipseExtensionPoint;

				/* Get the consumer of the external contract. */
				contractedEclipseExtensionPoint =
						(EclipseExtensionPoint) externalContract.getConsumer();

				/* Un-instantiate the contract for all its extensions. */
				for (EclipseExtension contractedEclipseExtension : contractedEclipseExtensionPoint
						.getExtensions()) {

					this.updateOrInstantiateContracts(contractedEclipseExtension);
				}
				// end for.

				/* Remove the external contract from the extension point. */
				ContractRegistry.getInstance().removeContractFromExtensionPoint(
						contractedEclipseExtensionPoint.getWrappedExtensionPoint(),
						externalContract);

				/* Probably remove the contracted plug-in as well. */
				contractedPlugin =
						(EclipsePlugin) contractedEclipseExtensionPoint.getOwner();

				this.probablyRemovePluginFromRegistry(contractedPlugin);

				ContractRegistry.getInstance().removeBoundExternalContract(
						contractedEclipseExtensionPoint.getWrappedExtensionPoint(),
						externalContract);
			}

			/* Else remove the unbound contract. */
			else {
				String contractedExtensionPointID;

				contractedExtensionPointID = externalContract.getLocation().toString();
				contractedExtensionPointID =
						contractedExtensionPointID.substring(contractedExtensionPointID
								.lastIndexOf("/") + 1, contractedExtensionPointID
								.lastIndexOf(CONTRACT_LOCATION_SUFFIX));

				ContractRegistry.getInstance().removeUnboundExternalContract(
						contractedExtensionPointID, externalContract);
			}
			// end else.
		}
		// end for (iteration on external contracts).
	}

	/**
	 * <p>
	 * A helper method that checks whether a given {@link EclipsePlugin} is not
	 * involved in any contract anymore and probably removes the
	 * {@link EclipsePlugin} from the {@link Map} of contracted plug-ins.
	 * </p>
	 * 
	 * @param plugin
	 *          The {@link EclipsePlugin} that shall be checked.
	 */
	private void probablyRemovePluginFromRegistry(EclipsePlugin plugin) {

		boolean isInvolvedInContract = false;

		/* Check if the plug-in still has contracted extension points. */
		for (IExtensionPoint extensionPoint : ContractRegistry.getInstance()
				.getContractedExtensionPoints()) {

			EclipseExtensionPoint eclipseExtensionPoint;
			eclipseExtensionPoint =
					EclipseAdapterFactory.getInstance().createExtensionPoint(
							extensionPoint);

			if (eclipseExtensionPoint.getOwner().equals(plugin)) {
				isInvolvedInContract = true;
				break;
			}
			// no else.
		}
		// end for.

		/* Probably remove the plug-in. */
		if (!isInvolvedInContract) {
			ContractRegistry.getInstance().removeContractedBundle(plugin.getBundle());
		}
		// no else.
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

		/* Else remove all contracts from the extension. */
		else {

			ContractRegistry.getInstance().removeAllContractsFromExtension(
					eclipseExtension.getWrappedExtension());
		}
		// end else.
	}
}