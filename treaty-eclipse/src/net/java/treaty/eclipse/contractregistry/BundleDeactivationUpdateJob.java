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
import java.util.List;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.Role;
import net.java.treaty.TreatyException;
import net.java.treaty.contractregistry.ContractRegistry.UpdateType;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.EclipseExtensionPoint;
import net.java.treaty.eclipse.EclipsePlugin;
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
 * A {@link Job} that is responsible to update the
 * {@link EclipseContractRegistry} after the deactivation of a new
 * {@link Bundle}.
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
	private static final int WORK_LEGISLATOR_CONTRACTS = 33000;

	/**
	 * The work of {@link BundleDeactivationUpdateJob}s to remove the
	 * {@link Bundle} from the {@link EclipseContractRegistry}.
	 */
	private static final int WORK_BUNDLE_REMOVAL = 1000;

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
				+ WORK_CONTRACTED_EXTENSIONS + WORK_LEGISLATOR_CONTRACTS
				+ WORK_BUNDLE_REMOVAL);

		// FIXME Claas: Remove try after debugging.
		try {
			/* Probably remove contracted extensions from the registry. */
			this.searchForContractedExtensions(monitor);

			/* Probably remove contracted extension points from the registry. */
			this.searchForContractedExtensionPoints(monitor);

			/* Probably remove legislator contracts from the registry. */
			this.searchForLegislatorContracts(monitor);

			this.removeExtensionsAndExtensionPoints(monitor);
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
	 * {@link IExtension}s and removes them from the
	 * {@link EclipseContractRegistry}.
	 * </p>
	 * 
	 * @param monitor
	 *          The {@link IProgressMonitor} used to monitor this {@link Job}.
	 */
	private void searchForContractedExtensions(IProgressMonitor monitor) {

		/* Update monitor status. */
		monitor.subTask("Search for contracted Extensions.");

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

			/* Remove all contract from the extension. */
			for (Contract contract : eclipseExtension.getContracts()) {

				try {
					EclipseContractRegistry.getInstance().updateContract(
							UpdateType.REMOVE_CONTRACT, contract, eclipseExtension,
							Role.SUPPLIER);
				}

				catch (TreatyException e) {

					Logger.error("Unpected TreatyException during removel of "
							+ "Contract from Extension.", e);
				}
			}

			monitor.worked(WORK_CONTRACTED_EXTENSIONS / eclipseExtensions.size());
		}
		// end for.
	}

	/**
	 * <p>
	 * A helper method that checks if a given {@link Bundle} contains contracted
	 * {@link IExtensionPoint}s and removes them from the
	 * {@link EclipseContractRegistry} .
	 * </p>
	 * 
	 * @param monitor
	 *          The {@link IProgressMonitor} used to monitor this {@link Job}.
	 */
	private void searchForContractedExtensionPoints(IProgressMonitor monitor) {

		/* Update monitor status. */
		monitor.subTask("Search for contracted ExtensionPoints.");

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
				this
						.removeContractsFromExtensionsOfExtensionPoint(eclipseExtensionPoint);

				/* Remove all Contracts from the Extension Point. */
				for (Contract contract : eclipseExtensionPoint.getContracts()) {

					try {
						EclipseContractRegistry.getInstance().updateContract(
								UpdateType.REMOVE_CONTRACT, contract, eclipseExtensionPoint,
								Role.CONSUMER);
					}

					catch (TreatyException e) {

						Logger.error(
								"Unexpected TreatyException during removal of Contracts "
										+ "from ExtensionPoint.", e);
					}
					// end catch.

					/* Check if the contract was an external contract. */
					if (contract.getDefinition() != null
							&& contract.getSupplier() == null) {

						/* Register the legislator contract as unbound. */
						EclipseContractRegistry.getInstance().addUnboundLegislatorContract(
								eclipseExtensionPoint.getId(), contract.getDefinition());
					}
					// no else.
				}
				// end for.
			}
			// no else.

			monitor.worked(WORK_INTERNAL_CONTRACTS / eclipseExtensionPoints.size());
		}
		// end for.
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
	private void searchForLegislatorContracts(IProgressMonitor monitor) {

		/* Update monitor status. */
		monitor.subTask("Search for legislator Contracts.");

		EclipsePlugin eclipsePlugin;
		List<EclipseExtension> eclipseExtensions;

		/* Get the eclipse plug-in. */
		eclipsePlugin =
				EclipseAdapterFactory.getInstance().createEclipsePlugin(this.myBundle);

		/* Get all extensions of the bundle. */
		eclipseExtensions = eclipsePlugin.getExtensions();

		/* If no extension point has been given at all, mark this task as worked. */
		if (eclipseExtensions.size() == 0) {
			monitor.worked(WORK_LEGISLATOR_CONTRACTS);
		}
		// no else.

		/* Iterate on the extensions. */
		for (EclipseExtension eclipseExtension : eclipseExtensions) {

			/* Probably remove legislator contracts. */
			for (Contract contract : EclipseContractRegistry.getInstance()
					.getContracts(eclipseExtension, Role.LEGISLATOR)) {

				/* Remove the external contract. */
				try {
					EclipseContractRegistry.getInstance().updateContract(
							UpdateType.REMOVE_CONTRACT, contract, eclipseExtension,
							Role.LEGISLATOR);
				}

				catch (TreatyException e) {

					Logger.error("Unexpected TreatyException during removal of "
							+ "legislator Contract from Extensions.", e);
				}
			}
			// end for.

			/* Probably remove unbound legislator contracts as well. */
			EclipseContractRegistry.getInstance()
					.removeUnboundLegislatorContractsForLegislatorConnector(
							eclipseExtension);

			monitor.worked(WORK_LEGISLATOR_CONTRACTS / eclipseExtensions.size());
		}
		// end for (iteration on extensions).
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
	private void removeContractsFromExtensionsOfExtensionPoint(
			EclipseExtensionPoint eclipseExtensionPoint) {

		/*
		 * Must iterate on the IExtensions here to avoid trouble with removal of
		 * Objects during the iteration on the same collection.
		 */
		for (EclipseExtension eclipseExtension : eclipseExtensionPoint
				.getExtensions()) {

			for (Contract contract : eclipseExtension.getContracts()) {

				try {
					EclipseContractRegistry.getInstance().updateContract(
							UpdateType.REMOVE_CONTRACT, contract, eclipseExtension,
							Role.SUPPLIER);
				}

				catch (TreatyException e) {

					Logger.error("Unpected TreatyException during removel of "
							+ "Contract from Extension.", e);
				}
				// end catch.
			}
			// end for.

			/* Also remove the extension from the extension point. */
			eclipseExtensionPoint.removeExtension(eclipseExtension);
		}
		// end for (iteration over contracted extensions).
	}

	/**
	 * <p>
	 * A helper method that cleans the {@link EclipsePlugin} by removing all its
	 * {@link EclipseExtensionPoint}s and {@link EclipseExtension}s.
	 * </p>
	 * 
	 * @param monitor
	 *          The {@link IProgressMonitor} used to monitor this {@link Job}.
	 */
	private void removeExtensionsAndExtensionPoints(IProgressMonitor monitor) {

		/* Update monitor status. */
		monitor.subTask("Remove Extensions and Extension Points.");

		EclipsePlugin eclipsePlugin;
		List<EclipseExtensionPoint> eclipseExtensionPoints;
		List<EclipseExtension> eclipseExtensions;

		/* Get the eclipse plugin. */
		eclipsePlugin =
				EclipseAdapterFactory.getInstance().createEclipsePlugin(this.myBundle);

		eclipseExtensionPoints = eclipsePlugin.getExtensionPoints();
		eclipseExtensions = eclipsePlugin.getExtensions();

		if (eclipseExtensionPoints.size() + eclipseExtensions.size() == 0) {
			monitor.worked(WORK_BUNDLE_REMOVAL);
		}
		// no else.

		for (EclipseExtensionPoint eclipseExtensionPoint : eclipseExtensionPoints) {

			eclipsePlugin.removeExtensionPoint(eclipseExtensionPoint);

			monitor.worked(WORK_BUNDLE_REMOVAL
					/ (eclipseExtensionPoints.size() + eclipseExtensions.size()));
		}
		// end for.

		for (EclipseExtension eclipseExtension : eclipseExtensions) {

			eclipsePlugin.removeExtension(eclipseExtension);

			monitor.worked(WORK_BUNDLE_REMOVAL
					/ (eclipseExtensionPoints.size() + eclipseExtensions.size()));
		}
		// end for.
	}
}