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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.Role;
import net.java.treaty.TreatyException;
import net.java.treaty.contractregistry.ContractRegistry.UpdateType;
import net.java.treaty.eclipse.EclipseExtension;
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
 * {@link EclipseContractRegistry} after the removal of an Array of
 * {@link IExtension}s.
 * </p>
 * 
 * @author Claas Wilke
 */
public class UpdateJobExtensionsRemoved extends Job {

	/**
	 * The work of {@link UpdateJobExtensionsRemoved}s to search for contracted
	 * {@link IExtension}s.
	 */
	private static final int WORK_CONTRACTED_EXTENSIONS = 33000;

	/**
	 * The work of {@link UpdateJobExtensionsRemoved}s to search for external
	 * {@link Contract}s.
	 */
	private static final int WORK_LEGISLATOR_CONTRACTS = 33000;

	/**
	 * The work of {@link UpdateJobExtensionsRemoved}s to remove the
	 * {@link Bundle} from the {@link EclipseContractRegistry}.
	 */
	private static final int WORK_EXTENSION_REMOVAL = 1000;

	/**
	 * The {@link EclipseExtension}s that this {@link UpdateJobExtensionsRemoved}
	 * shall handle.
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
	 * Creates a new {@link UpdateJobExtensionsRemoved} for a array of
	 * {@link IExtension}s.
	 * </p>
	 * 
	 * @param extensions
	 *          The {@link IExtension}s that this
	 *          {@link UpdateJobExtensionsRemoved} shall handle.
	 * @param name
	 *          The name of this {@link Job}.
	 */
	public UpdateJobExtensionsRemoved(IExtension[] extensions, String name) {

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
				+ WORK_LEGISLATOR_CONTRACTS + WORK_EXTENSION_REMOVAL);

		try {
			/* Probably remove contracted extensions from the registry. */
			this.searchForContractedExtensions(monitor);

			/* Probably remove legislator contracts from the registry. */
			this.searchForLegislatorContracts(monitor);

			this.removeExtensionsAndExtensionPoints(monitor);
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

		/* If not extension point has been given at all, mark this task as worked. */
		if (this.myEclipseExtensions == null
				|| this.myEclipseExtensions.size() == 0) {
			monitor.worked(WORK_CONTRACTED_EXTENSIONS);
		}
		// no else.

		/*
		 * Iterate on extensions and search for extensions that can now be removed
		 * again.
		 */
		for (EclipseExtension eclipseExtension : this.myEclipseExtensions) {

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
			// end for.

			monitor.worked(WORK_CONTRACTED_EXTENSIONS
					/ this.myEclipseExtensions.size());
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

		/* If no extension has been given at all, mark this task as worked. */
		if (this.myEclipseExtensions == null
				|| this.myEclipseExtensions.size() == 0) {
			monitor.worked(WORK_LEGISLATOR_CONTRACTS);
		}
		// no else.

		/* Iterate on the extensions. */
		for (EclipseExtension eclipseExtension : this.myEclipseExtensions) {

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

			monitor.worked(WORK_LEGISLATOR_CONTRACTS
					/ this.myEclipseExtensions.size());
		}
		// end for (iteration on extensions).
	}

	/**
	 * <p>
	 * A helper method that cleans the {@link EclipsePlugin}s by removing the
	 * {@link EclipseExtension}s.
	 * </p>
	 * 
	 * @param monitor
	 *          The {@link IProgressMonitor} used to monitor this {@link Job}.
	 */
	private void removeExtensionsAndExtensionPoints(IProgressMonitor monitor) {

		/* Update monitor status. */
		monitor.subTask("Remove Extensions.");

		if (this.myEclipseExtensions == null
				|| this.myEclipseExtensions.size() == 0) {
			monitor.worked(WORK_EXTENSION_REMOVAL);
		}
		// no else.

		for (EclipseExtension eclipseExtension : this.myEclipseExtensions) {

			EclipsePlugin eclipsePlugin;

			eclipsePlugin = (EclipsePlugin) eclipseExtension.getOwner();
			eclipsePlugin.removeExtension(eclipseExtension);

			EclipseAdapterFactory.getInstance().removeEclipseExtensionFromCache(
					eclipseExtension);

			monitor.worked(WORK_EXTENSION_REMOVAL / this.myEclipseExtensions.size());
		}
		// end for.
	}
}