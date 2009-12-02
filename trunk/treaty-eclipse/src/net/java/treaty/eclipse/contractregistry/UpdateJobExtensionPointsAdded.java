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
import java.util.HashSet;
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
 * {@link IExtensionPoint}s.
 * </p>
 * 
 * @author Claas Wilke
 */
public class UpdateJobExtensionPointsAdded extends Job {

	/**
	 * The work of {@link UpdateJobExtensionPointsAdded}s to search for
	 * {@link Contract}s defined with a {@link Bundle}'s {@link IExtensionPoint}s.
	 */
	private static final int TOTAL_WORK = 10000;

	/**
	 * The {@link EclipseExtensionPoint}s belonging to this
	 * {@link UpdateJobExtensionPointsAdded}.
	 */
	private Set<EclipseExtensionPoint> myExtensionPoints;

	/**
	 * <p>
	 * Creates a new {@link UpdateJobExtensionPointsAdded} for a given array of
	 * {@link IExtensionPoint}s that have been added.
	 * </p>
	 * 
	 * @param extensionPoints
	 *          The {@link Bundle} that has been activated.
	 * @param name
	 *          The name of this {@link Job}.
	 */
	public UpdateJobExtensionPointsAdded(IExtensionPoint[] extensionPoints,
			String name) {

		super(name);

		Set<EclipseExtensionPoint> eclipseExtensionPoints;
		eclipseExtensionPoints = new HashSet<EclipseExtensionPoint>();

		boolean extensionPointWasInvalid;
		extensionPointWasInvalid = false;

		for (IExtensionPoint extensionPoint : extensionPoints) {

			try {
				EclipseExtensionPoint eclipseExtensionPoint;
				eclipseExtensionPoint =
						EclipseAdapterFactory.getInstance().createExtensionPoint(
								extensionPoint);
				eclipseExtensionPoints.add(eclipseExtensionPoint);
			}
			// end try.

			catch (EclipseConnectorAdaptationException e) {
				Logger.warn("IExtensionPoint " + extensionPoint
						+ " is invalid. Try to clean the ContractRegistry manually.", e);

				extensionPointWasInvalid = true;
			}
			// end catch.
		}
		// end for (iteration on extension points).

		this.myExtensionPoints = eclipseExtensionPoints;

		if (extensionPointWasInvalid) {
			Job jobCleanRegistry;
			jobCleanRegistry =
					new UpdateJobCleanContractRegistry(
							"Invalid ExtensionPoint found. Clean the ContractRegistry.");

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
		monitor.beginTask("Update ContracRegistry.", TOTAL_WORK);

		try {
			/*
			 * Afterwards, check if the bundle provides extension points that contract
			 * themselves.
			 */
			this.searchForExtensionPointsWithContracts(monitor);
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

		/* If not extension point has been given at all, mark this task as worked. */
		if (this.myExtensionPoints == null || this.myExtensionPoints.size() == 0) {
			monitor.worked(TOTAL_WORK);
		}
		// no else.

		for (EclipseExtensionPoint eclipseExtensionPoint : this.myExtensionPoints) {

			String contractName;
			contractName =
					Constants.INTERNAL_CONTRACT_LOCATION_PREFIX
							+ eclipseExtensionPoint.getId()
							+ Constants.CONTRACT_LOCATION_SUFFIX;

			Bundle bundle;
			bundle = ((EclipsePlugin) eclipseExtensionPoint.getOwner()).getBundle();

			URL contractURL;
			contractURL = bundle.getEntry(contractName);

			/* If a contract has been found, add it to the extension point. */
			if (contractURL != null) {

				Contract contract;

				contract =
						EclipseContractFactory.INSTANCE.createContract(contractURL,
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

			monitor.worked(TOTAL_WORK / this.myExtensionPoints.size());
		}
		// end for (iteration on external contracts).
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
			/* Add the contract to the extension point in the registry. */
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
			// end for.
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
		/*
		 * Don't iterate on the eclipse extensions here, they could not be added to
		 * the extension point yet!
		 */
		for (IExtension extension : extensionPoint.getExtensions()) {

			boolean wasExtensionInvalid;
			wasExtensionInvalid = false;

			try {
				EclipseExtension eclipseExtension;
				eclipseExtension =
						EclipseAdapterFactory.getInstance().createExtension(extension);

				eclipseExtensionPoint.addExtension(eclipseExtension);

				EclipseContractRegistry.getInstance().updateContract(
						UpdateType.ADD_CONTRACT, contract, eclipseExtension, Role.SUPPLIER);
			}
			// end try.

			catch (EclipseConnectorAdaptationException e1) {
				Logger.warn("IExtension " + extension
						+ " was invalid. Try to clean the ContractRegistry manually.", e1);

				wasExtensionInvalid = true;
			}

			catch (TreatyException e) {

				Logger.error("Unexpected TreatyException during adding a Contract "
						+ "to an ExtensionPoint.", e);
			}

			if (wasExtensionInvalid) {
				Job jobCleanRegistry;
				jobCleanRegistry =
						new UpdateJobCleanContractRegistry(
								"Invalid Extension found. Clean the ContractRegistry.");

				jobCleanRegistry.schedule();
			}
			// no else.
		}
		// end for (iteration over contracted extensions).
	}
}