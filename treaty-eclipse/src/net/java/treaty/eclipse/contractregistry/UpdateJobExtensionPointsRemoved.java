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

import net.java.treaty.Contract;
import net.java.treaty.Role;
import net.java.treaty.TreatyException;
import net.java.treaty.contractregistry.ContractRegistry.UpdateType;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.EclipseExtensionPoint;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.Logger;

import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.osgi.framework.Bundle;

/**
 * <p>
 * A {@link Job} that is responsible to update the
 * {@link EclipseContractRegistry} after the removal of an array of
 * {@link IExtensionPoint}s.
 * </p>
 * 
 * @author Claas Wilke
 * 
 */
public class UpdateJobExtensionPointsRemoved extends Job {

	/**
	 * The work of {@link UpdateJobExtensionPointsRemoved}s to search for
	 * {@link Contract}s defined with a {@link Bundle}'s {@link IExtensionPoint}s.
	 */
	private static final int WORK_EXTENSION_POINTS = 33000;

	/**
	 * The work of {@link UpdateJobExtensionPointsRemoved}s to remove the
	 * {@link Bundle} from the {@link EclipseContractRegistry}.
	 */
	private static final int WORK_EXTENSION_POINT_REMOVAL = 1000;

	/**
	 * The {@link IExtensionPoint}s this {@link UpdateJobExtensionPointsRemoved}
	 * shall handle.
	 */
	private IExtensionPoint[] myExtensionPoints;

	/**
	 * <p>
	 * Creates a new {@link UpdateJobExtensionPointsRemoved} for a given array of
	 * {@link IExtensionPoint}s that have been removed.
	 * </p>
	 * 
	 * @param extensionPoints
	 *          The {@link IExtensionPoint}s that have been removed.
	 * @param name
	 *          The name of this {@link Job}.
	 */
	public UpdateJobExtensionPointsRemoved(IExtensionPoint[] extensionPoints,
			String name) {

		super(name);

		this.myExtensionPoints = extensionPoints;
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
		monitor.beginTask("Update ContracRegistry.", WORK_EXTENSION_POINTS
				+ WORK_EXTENSION_POINT_REMOVAL);

		try {
			/* Probably remove contracted extension points from the registry. */
			this.searchForContractedExtensionPoints(monitor);

			this.removeExtensionPoints(monitor);
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

		/* If not extension point has been given at all, mark this task as worked. */
		if (this.myExtensionPoints == null || this.myExtensionPoints.length == 0) {
			monitor.worked(WORK_EXTENSION_POINTS);
		}
		// no else.

		/* Search for extension points that can now be removed again. */
		for (IExtensionPoint extensionPoint : this.myExtensionPoints) {

			EclipseExtensionPoint eclipseExtensionPoint;
			eclipseExtensionPoint =
					EclipseAdapterFactory.getInstance().createExtensionPoint(
							extensionPoint);

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

			monitor.worked(WORK_EXTENSION_POINTS / this.myExtensionPoints.length);
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
	 * A helper method that cleans the {@link EclipsePlugin}s by removing the
	 * removed {@link EclipseExtensionPoint}s.
	 * </p>
	 * 
	 * @param monitor
	 *          The {@link IProgressMonitor} used to monitor this {@link Job}.
	 */
	private void removeExtensionPoints(IProgressMonitor monitor) {

		/* Update monitor status. */
		monitor.subTask("Remove Extensions Points.");

		if (this.myExtensionPoints == null || this.myExtensionPoints.length == 0) {
			monitor.worked(WORK_EXTENSION_POINT_REMOVAL);
		}
		// no else.

		for (IExtensionPoint extensionPoint : this.myExtensionPoints) {

			EclipseExtensionPoint eclipseExtensionPoint;
			eclipseExtensionPoint =
					EclipseAdapterFactory.getInstance().createExtensionPoint(
							extensionPoint);

			EclipsePlugin eclipsePlugin;
			eclipsePlugin = (EclipsePlugin) eclipseExtensionPoint.getOwner();

			eclipsePlugin.removeExtensionPoint(eclipseExtensionPoint);

			monitor.worked(WORK_EXTENSION_POINT_REMOVAL
					/ this.myExtensionPoints.length);
		}
		// end for.
	}
}