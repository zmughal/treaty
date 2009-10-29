package net.java.treaty.eclipse.contractregistry;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.EclipseExtensionPoint;
import net.java.treaty.eclipse.EclipsePlugin;

import org.eclipse.core.runtime.ContributorFactoryOSGi;
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

		/* Probably remove contracted extension points from the registry. */
		this.tryToRemoveContractedExtensionPoints(monitor);

		/* Probably remove contracted extensions from the registry. */
		this.tryToRemoveContractedExtensions(monitor);

		/* Probably remove external contracts from the registry. */
		this.tryToRemoveExternalContract(monitor);

		/* Remove the contracted plug-in as well. */
		monitor.subTask("Remove bundle.");
		ContractRegistry.getInstance().removeContractedBundle(this.myBundle);
		monitor.worked(WORK_BUNDLE_REMOVAL);

		/* Probably inform listeners. */
		ContractRegistry.getInstance().notifyObservers();

		monitor.done();

		return Status.OK_STATUS;
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

		IContributor contributor;
		IExtensionPoint[] extensionPoints;

		IExtensionRegistry extensionRegistry;

		/* Get the contributor for the bundle. */
		contributor = ContributorFactoryOSGi.createContributor(this.myBundle);

		/* Get the extension registry. */
		extensionRegistry =
				org.eclipse.core.runtime.Platform.getExtensionRegistry();

		/* Get all extensions points of the bundle. */
		extensionPoints = extensionRegistry.getExtensionPoints(contributor);

		/* If not extension point has been given at all, mark this task as worked. */
		if (extensionPoints.length == 0) {
			monitor.worked(WORK_INTERNAL_CONTRACTS);
		}
		// no else.

		/* Search for extension points that can now be removed again. */
		for (IExtensionPoint extensionPoint : extensionRegistry
				.getExtensionPoints(contributor)) {

			if (ContractRegistry.getInstance().hasExtensionPointContracts(
					extensionPoint)) {

				EclipseExtensionPoint eclipseExtensionPoint;
				eclipseExtensionPoint =
						EclipseAdapterFactory.getInstance().createExtensionPoint(
								extensionPoint);

				/*
				 * Remove the extension points extensions and also un-instantiate their
				 * contracts.
				 */
				this.removeContractedExtensionsOfExtensionPoint(eclipseExtensionPoint);

				/* Remove the contracted extension point. */
				ContractRegistry.getInstance().removeAllContractsFromExtensionPoint(
						extensionPoint);
			}
			// no else.

			/* Check if the extension point has external contracts. */
			if (ContractRegistry.getInstance().hasExtensionPointExternalContracts(
					extensionPoint)) {

				/* Un-bind the external contracts. */
				ContractRegistry.getInstance().addUnboundExternalContracts(
						extensionPoint.getUniqueIdentifier(),
						ContractRegistry.getInstance().removeAllBoundExternalContracts(
								extensionPoint));
			}
			// no else.

			monitor.worked(WORK_INTERNAL_CONTRACTS / extensionPoints.length);
		}
		// end for.
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

		IContributor contributor;

		IExtensionRegistry extensionRegistry;
		IExtension[] extensions;

		/* Get the contributor for the bundle. */
		contributor = ContributorFactoryOSGi.createContributor(this.myBundle);

		/* Get the extension registry. */
		extensionRegistry =
				org.eclipse.core.runtime.Platform.getExtensionRegistry();

		/* Get all extensions of the bundle. */
		extensions = extensionRegistry.getExtensions(contributor);

		/* If not extension point has been given at all, mark this task as worked. */
		if (extensions.length == 0) {
			monitor.worked(WORK_CONTRACTED_EXTENSIONS);
		}
		// no else.

		/*
		 * Iterate on extensions and search for extensions that can now be removed
		 * again.
		 */
		for (IExtension extension : extensions) {

			if (ContractRegistry.getInstance().hasExtensionContracts(extension)) {

				/* Remove the contract from the extension. */
				ContractRegistry.getInstance().removeAllContractsFromExtension(
						extension);
			}
			// no else.

			monitor.worked(WORK_CONTRACTED_EXTENSIONS / extensions.length);
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
	private void tryToRemoveExternalContract(IProgressMonitor monitor) {

		/* Update monitor status. */
		monitor.subTask("Check for external Contracts.");

		IContributor contributor;

		IExtensionRegistry extensionRegistry;
		IExtension[] extensions;

		/* Get the contributor for the bundle. */
		contributor = ContributorFactoryOSGi.createContributor(this.myBundle);

		/* Get the extension registry. */
		extensionRegistry =
				org.eclipse.core.runtime.Platform.getExtensionRegistry();

		/* Get all extensions of the bundle. */
		extensions = extensionRegistry.getExtensions(contributor);

		/* If not extension point has been given at all, mark this task as worked. */
		if (extensions.length == 0) {
			monitor.worked(WORK_EXTERNAL_CONTRACTS);
		}
		// no else.

		/* Iterate on the extensions. */
		for (IExtension legislatorExtension : extensionRegistry
				.getExtensions(contributor)) {

			/* Check if the extension provides external contracts. */
			if (legislatorExtension.getExtensionPointUniqueIdentifier().equals(
					EXTERNAL_CONTRACT_ID)) {

				/* Probably remove bound external contracts. */
				this.removeBoundExternalContractsOfExtension(legislatorExtension);

				/* Also check if the extension provides unbound contracts. */
				this.removeUnboundExternalContracts(legislatorExtension);
			}
			// no else (extension does not represent external contract).

			monitor.worked(WORK_EXTERNAL_CONTRACTS / extensions.length);
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
	 * FIXME Claas: Improve this method avoid so many for statements.
	 * 
	 * <p>
	 * A helper method that removes all external {@link Contract}s from the
	 * {@link ContractRegistry} that are defined by a given {@link IExtension}.
	 * </p>
	 * 
	 * @param legislatorExtension
	 *          The {@link IExtension} whose external {@link Contract}s shall be
	 *          removed.
	 */
	private void removeBoundExternalContractsOfExtension(
			IExtension legislatorExtension) {

		EclipseExtension legislatorEclipseExtension;
		legislatorEclipseExtension =
				EclipseAdapterFactory.getInstance()
						.createExtension(legislatorExtension);

		/* Iterate through all defined external contracts. */
		for (IExtensionPoint contractedExtensionPoint : ContractRegistry
				.getInstance().getBoundExternalContracts().keySet()) {

			Set<Contract> removedContracts;
			removedContracts = new HashSet<Contract>();

			/* Iterate through all contracts of the contracted extension point. */
			for (Contract externalContract : ContractRegistry.getInstance()
					.getExternalContractsOfExtensionPoint(contractedExtensionPoint)) {

				if (externalContract.getOwner().equals(legislatorEclipseExtension)) {

					EclipseExtensionPoint contractedEclipseExtensionPoint;
					contractedEclipseExtensionPoint =
							EclipseAdapterFactory.getInstance().createExtensionPoint(
									contractedExtensionPoint);

					/* Un-instantiate the contract for all its extensions. */
					for (EclipseExtension contractedEclipseExtension : contractedEclipseExtensionPoint
							.getExtensions()) {

						ContractRegistry.getInstance().removeAllContractsFromExtension(
								contractedEclipseExtension.getWrappedExtension());
					}
					// end for.

					/* Remove the external contract from the extension. */
					ContractRegistry.getInstance().removeContractFromExtensionPoint(
							contractedExtensionPoint, externalContract);

					/* Probably remove the contracted plug-in as well. */
					EclipsePlugin contractedPlugin;

					contractedPlugin =
							(EclipsePlugin) contractedEclipseExtensionPoint.getOwner();

					this.probablyRemovePluginFromRegistry(contractedPlugin);

					removedContracts.add(externalContract);
				}
				// no else (external contract defined by other extension).

				/* Probably remove the external contracts from the registry. */
				for (Contract contract : removedContracts) {
					ContractRegistry.getInstance().removeBoundExternalContract(
							contractedExtensionPoint, contract);
				}
				// end for.
			}
			// end for (iteration on external contracts).
		}
		// end for (iteration on contracted extensions).
	}

	/**
	 * FIXME Claas: Improve this method avoid so many for statements.
	 * <p>
	 * A helper method that removes all unbound external {@link Contract}s of a
	 * given {@link IExtension} from the {@link ContractRegistry}.
	 * 
	 * @param legislatorExtension
	 *          The {@link IExtension} whose unbound external {@link Contract}s
	 *          shall be removed.
	 */
	private void removeUnboundExternalContracts(IExtension legislatorExtension) {

		EclipseExtension legislatorEclipseExtension;
		legislatorEclipseExtension =
				EclipseAdapterFactory.getInstance()
						.createExtension(legislatorExtension);

		for (String contractedExtensionPointID : ContractRegistry.getInstance()
				.getUnboundExternalContracts().keySet()) {

			for (Contract externalContract : ContractRegistry.getInstance()
					.getUnboundExternalContractsOfExtensionPoint(
							contractedExtensionPointID)) {

				Set<Contract> removedContracts;
				removedContracts = new HashSet<Contract>();

				if (externalContract.getOwner().equals(legislatorEclipseExtension)) {

					removedContracts.add(externalContract);
				}
				// no else.

				for (Contract contract : removedContracts) {
					ContractRegistry.getInstance().removeUnboundExternalContract(
							contractedExtensionPointID, contract);
				}
				// end for.
			}
			// end for (iteration on external contracts).
		}
		// end for (iteration on extension point ids).
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
				.getContractedExtensionPoints().keySet()) {

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
}