package net.java.treaty.eclipse.contractregistry;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.TreatyException;
import net.java.treaty.VerificationReport;
import net.java.treaty.eclipse.ContractVerificationSchedulingRule;
import net.java.treaty.eclipse.EclipseConnector;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.EclipseExtensionPoint;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.EclipseResourceManager;
import net.java.treaty.eclipse.Logger;
import net.java.treaty.eclipse.jobs.VerificationJob;
import net.java.treaty.eclipse.jobs.VerificationJobListener;
import net.java.treaty.event.LifeCycleEvent;

import org.eclipse.core.runtime.ContributorFactoryOSGi;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
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
public class ContractRegistry extends Observable {

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
	private static final String INTERNAL_CONTRACT_LOCATION_SUFFIX = ".contract";

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
	 * Contains all {@link IExtensionPoint}s (as their unique IDs) on that
	 * external {@link Contract}s have been defined by other {@link EclipsePlugin}
	 * s. The {@link EclipsePlugin} that defined a {@link Contract} can be
	 * accessed via the {@link Contract#getOwner()} method.
	 */
	private Map<String, Set<Contract>> unboundExternalContracts =
			new HashMap<String, Set<Contract>>();

	/**
	 * <p>
	 * Private constructor for Singleton Pattern.
	 * </p>
	 */
	private ContractRegistry() {

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

			/* Do the initial startup. */
			ContractRegistryStartUpJob contractRegistryStartUpJob;
			contractRegistryStartUpJob =
					new ContractRegistryStartUpJob("Initialt ContractRegistry Startup");

			contractRegistryStartUpJob.schedule();
		}
		// no else.

		return myInstance;
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

		Bundle bundle;
		EclipsePlugin eclipsePlugin;

		resultCandidates = new LinkedHashSet<Contract>();

		/* Get the bundle and the EclipsePlugin. */
		bundle =
				org.eclipse.core.runtime.Platform.getBundle(lifeCycleEvent
						.getComponentID());
		eclipsePlugin =
				EclipseAdapterFactory.getInstance().createEclipsePlugin(bundle);

		/* Iterate over the extension points of this bundle. */
		for (EclipseExtensionPoint eclipseExtensionPoint : eclipsePlugin
				.getExtensionPoints()) {

			/* Check if a contract is defined on the extension point. */
			if (this.contractedExtensionPoints.containsKey(eclipseExtensionPoint
					.getWrappedExtensionPoint())) {

				for (Contract unboundContract : this.contractedExtensionPoints
						.get(eclipseExtensionPoint.getWrappedExtensionPoint())) {
					/*
					 * Iterate on all extensions of the extension points and add their
					 * contracts that depend on this plug-in.
					 */
					for (EclipseExtension eclipseExtension : eclipseExtensionPoint
							.getExtensions()) {

						for (Contract boundContract : this.contractedExtensions
								.get(eclipseExtension.getWrappedExtension())) {

							/*
							 * Check if the contract matches to the extension point's
							 * contract.
							 */
							if (boundContract.equals(unboundContract)
									|| (boundContract.getDefinition() != null && boundContract
											.getDefinition().equals(unboundContract))) {

								resultCandidates.add(boundContract);
							}
							// no else (contract does not match).
						}
						// end for (iteration on bound contracts).
					}
					// end for (iteration on extensions).
				}
				// end for (iteration on unbound contracts).
			}
			// no else (no contract on extension point).
		}
		// end for (iteration on extension points)

		/*
		 * Get all contracts that are defined by other bundle on this bundle's
		 * extensions.
		 */
		for (EclipseExtension eclipseExtension : eclipsePlugin.getExtensions()) {

			if (this.contractedExtensions.containsKey(eclipseExtension
					.getWrappedExtension())) {

				resultCandidates.addAll(this.contractedExtensions.get(eclipseExtension
						.getWrappedExtension()));
			}
		}
		// end for (iteration on extensions).

		/* Filter the contracts depending on their event types. */
		result = new LinkedHashSet<Contract>();

		for (Contract contract : resultCandidates) {

			/* FIXME Claas: Filter the contracts depending on their event types. */
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
	 * Returns the {@link EclipsePlugin}s on that contracts are defined.
	 * </p>
	 * 
	 * @return The {@link EclipsePlugin}s on that contracts are defined.
	 */
	public Map<Bundle, EclipsePlugin> getContractedPlugins() {

		return this.contractedPlugins;
	}

	/**
	 * <p>
	 * Returns the {@link IExtensionPoint}s on which {@link Contract}s are
	 * defined.
	 * </p>
	 * 
	 * @return The {@link IExtensionPoint}s on which {@link Contract}s are
	 *         defined.
	 */
	public Map<IExtensionPoint, LinkedHashSet<Contract>> getContractedExtensionPoints() {

		return this.contractedExtensionPoints;
	}

	/**
	 * <p>
	 * Returns the {@link Contract}s in respect to the {@link IExtension} they are
	 * defined on.
	 * </p>
	 * 
	 * @return The {@link Contract}s in respect to the {@link IExtension} they are
	 *         defined on.
	 */
	public Map<IExtension, LinkedHashSet<Contract>> getContractedExtensions() {

		return this.contractedExtensions;
	}

	/**
	 * <p>
	 * Returns all {@link IExtensionPoint}s on that external {@link Contract}s
	 * have been defined by other {@link EclipsePlugin}s. The
	 * {@link EclipsePlugin} that defined a {@link Contract} can be accessed via
	 * the {@link Contract#getOwner()} method.
	 * </p>
	 * 
	 * @return All {@link IExtensionPoint}s on that external {@link Contract}s
	 *         have been defined by other {@link EclipsePlugin}s. The
	 *         {@link EclipsePlugin} that defined a {@link Contract} can be
	 *         accessed via the {@link Contract#getOwner()} method.
	 */
	public Map<IExtensionPoint, LinkedHashSet<Contract>> getBoundExternalContracts() {

		return this.boundExternalContracts;
	}

	/**
	 * <p>
	 * Returns all {@link IExtensionPoint}s (as their unique IDs) on that external
	 * {@link Contract}s have been defined by other {@link EclipsePlugin}s. The
	 * {@link EclipsePlugin} that defined a {@link Contract} can be accessed via
	 * the {@link Contract#getOwner()} method.
	 * </p>
	 * 
	 * @return All {@link IExtensionPoint}s (as their unique IDs) on that external
	 *         {@link Contract}s have been defined by other {@link EclipsePlugin}
	 *         s. The {@link EclipsePlugin} that defined a {@link Contract} can be
	 *         accessed via the {@link Contract#getOwner()} method.
	 */
	public Map<String, Set<Contract>> getUnboundExternalContracts() {

		return this.unboundExternalContracts;
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

			this.updateWithStartedBundle(event.getBundle());
			break;
		}

		case BundleEvent.STOPPED: {

			this.updateWithStoppedBundle(event.getBundle());
			break;
		}

		default: {
			String msg;

			msg = "Ignored BundleEvent of type ";
			msg += this.parseEvent(event.getType());
			msg += " for bundle ";
			msg += event.getBundle().toString();
			msg += ".";

			System.out.println(msg);
		}
		}
	}

	/**
	 * <p>
	 * Transform the given value of a {@link BundleEvent}'s type into the name of
	 * the represented type.
	 * </p>
	 * 
	 * @param type
	 *          The type whose name shall be returned.
	 * @return The name of the given type.
	 */
	private String parseEvent(int type) {

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

	/**
	 * <p>
	 * Updates this {@link ContractRegistry} by adding all information of a newly
	 * started {@link Bundle}.
	 * </p>
	 * 
	 * @param bundle
	 *          The {@link Bundle} whose information shall be added to the
	 *          {@link ContractRegistry}.
	 */
	private void updateWithStartedBundle(Bundle bundle) {

		/*
		 * First check if the bundle provides extension points that contract
		 * themselves or belong to existing external contracts.
		 */
		this.tryToAddContractedExtensionPoints(bundle);

		/*
		 * Afterwards, check if the bundle provides extensions that can be bound to
		 * already existing extension points and contracts.
		 */
		this.tryToAddContractedExtensions(bundle);

		/*
		 * Afterwards, check if the bundle provides external contracts (whether or
		 * not they can be bound already to the contracted extension points).
		 */
		this.tryToAddExternalContracts(bundle);

		/* Probably inform listeners. */
		this.notifyObservers();
	}

	/**
	 * <p>
	 * Updates this {@link ContractRegistry} by removing all information of a
	 * stopped {@link Bundle}.
	 * </p>
	 * 
	 * @param bundle
	 *          The {@link Bundle} whose information shall be removed to the
	 *          {@link ContractRegistry}.
	 */
	private void updateWithStoppedBundle(Bundle bundle) {

		try {
			/* Probably remove contracted extension points from the registry. */
			this.tryToRemoveContractedExtensionPoints(bundle);

			/* Probably remove contracted extensions from the registry. */
			this.tryToRemoveContractedExtensions(bundle);

			/* Probably remove external contracts from the registry. */
			this.tryToRemoveExternalContract(bundle);
		}

		catch (Exception e) {

			/* FIXME Claas: Remove this print statement after debugging. */
			e.printStackTrace();

			Logger.error("Error occuring during handle of event on bundle " + bundle,
					e);
		}

		finally {
			/* Remove the contracted plug-in as well. */
			this.contractedPlugins.remove(bundle);
		}

		/* Probably inform listeners. */
		this.notifyObservers();
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
	 * @param bundle
	 *          The {@link Bundle} that shall be checked.
	 */
	private void tryToAddContractedExtensionPoints(Bundle bundle) {

		IContributor contributor;
		IExtensionRegistry extensionRegistry;
		EclipsePlugin eclipsePlugin;

		/* Get the contributor for the bundle. */
		contributor = ContributorFactoryOSGi.createContributor(bundle);

		/* Get the extension registry. */
		extensionRegistry =
				org.eclipse.core.runtime.Platform.getExtensionRegistry();

		/* Get the EclipsePlugin. */
		eclipsePlugin =
				EclipseAdapterFactory.getInstance().createEclipsePlugin(bundle);

		/*
		 * Get all extension points of the bundle and check for self provided
		 * contracts.
		 */
		for (IExtensionPoint extensionPoint : extensionRegistry
				.getExtensionPoints(contributor)) {

			String contractName;
			URL contractURL;

			contractName =
					INTERNAL_CONTRACT_LOCATION_PREFIX
							+ extensionPoint.getUniqueIdentifier()
							+ INTERNAL_CONTRACT_LOCATION_SUFFIX;
			contractURL = eclipsePlugin.getResource(contractName);

			/* If a contract has been found, add it to the extension point. */
			if (contractURL != null) {

				this.addContractToExtensionPoint(extensionPoint, contractURL);

				/* Register the plug-in as contracted. */
				this.contractedPlugins.put(bundle, eclipsePlugin);

				/* Set status to changed. */
				this.setChanged();
			}
			// no else (extension point not contracted by own plug-in).

			/*
			 * Check as well, if an external contract that is not bound yet exists.
			 */
			if (this.unboundExternalContracts.containsKey(extensionPoint
					.getUniqueIdentifier())) {

				/*
				 * Iterate through all external contracts and add them to the extension
				 * point.
				 */
				for (Contract externalContract : this.unboundExternalContracts
						.get(extensionPoint.getUniqueIdentifier())) {

					this.addExternalContractToExtensionPoint(extensionPoint,
							externalContract);
				}
				// end for (iteration on found external contracts).

				/* Store the extension point's plug-in as contracted. */
				this.contractedPlugins.put(bundle, eclipsePlugin);

				/* Remove the external contract from the unbound contracts. */
				this.unboundExternalContracts.remove(extensionPoint
						.getUniqueIdentifier());

				/* Set status to changed. */
				this.setChanged();
			}
			// no else (no external contract found).
		}
		// end for (iteration on external contracts).
	}

	/**
	 * <p>
	 * A helper method that checks if a given {@link Bundle} contains contracted
	 * {@link IExtension}s and adds them to the {@link ContractRegistry}.
	 * </p>
	 * 
	 * @param bundle
	 *          The {@link Bundle} that shall be checked.
	 */
	private void tryToAddContractedExtensions(Bundle bundle) {

		IContributor contributor;
		IExtensionRegistry extensionRegistry;

		/* Get the contributor for the bundle. */
		contributor = ContributorFactoryOSGi.createContributor(bundle);

		/* Get the extension registry. */
		extensionRegistry =
				org.eclipse.core.runtime.Platform.getExtensionRegistry();

		/*
		 * Iterate through the extensions of the given bundle and check if they are
		 * contracted by their extension point.
		 */
		for (IExtension extension : extensionRegistry.getExtensions(contributor)) {

			IExtensionPoint extensionPoint;
			extensionPoint =
					extensionRegistry.getExtensionPoint(extension
							.getExtensionPointUniqueIdentifier());

			/*
			 * Check if the extension's extension point is contracted (includes that
			 * the extension point's plug-in is active.
			 */
			if (this.contractedExtensionPoints.containsKey(extensionPoint)) {

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

				/* Store the new found contracted extension. */
				this.contractedExtensions.put(extension, eclipseExtension
						.getContracts());

				/* Set status to changed. */
				this.setChanged();
			}
			// no else (extension not contracted).
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
	 * @param bundle
	 *          The {@link Bundle} that shall be checked for external
	 *          {@link Contract}s.
	 */
	private void tryToAddExternalContracts(Bundle bundle) {

		IContributor contributor;
		IExtensionRegistry extensionRegistry;

		/* Get the contributor for the bundle. */
		contributor = ContributorFactoryOSGi.createContributor(bundle);

		/* Get the extension registry. */
		extensionRegistry =
				org.eclipse.core.runtime.Platform.getExtensionRegistry();

		/* Iterate through all extensions of the bundle. */
		for (IExtension extension : extensionRegistry.getExtensions(contributor)) {

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

					if (bundle != null && contractLocation != null) {

						URL contractUrl;
						contractUrl = bundle.getEntry(contractLocation);

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

								this.contractedPlugins.put(contractedEclipsePlugin.getBundle(),
										contractedEclipsePlugin);

								/* Set status to changed. */
								this.setChanged();
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

		LinkedHashSet<Contract> definedContracts;
		Contract newContract;

		eclipseExtensionPoint =
				EclipseAdapterFactory.getInstance()
						.createExtensionPoint(extensionPoint);
		newContract =
				EclipseExtensionPoint
						.createContract(contractURL, eclipseExtensionPoint);

		/* Store the new contract in the registry. */
		if (this.contractedExtensionPoints.containsKey(extensionPoint)) {
			definedContracts = this.contractedExtensionPoints.get(extensionPoint);
		}

		else {
			definedContracts = new LinkedHashSet<Contract>();
		}

		definedContracts.add(newContract);
		this.contractedExtensionPoints.put(extensionPoint, definedContracts);

		/*
		 * For contracted extension points also adapt their extensions (if any
		 * extension has been found already).
		 */
		this.addOrUpdateContractedExtensionsOfExtensionPoint(extensionPoint);
	}

	/**
	 * <p>
	 * A helper method that adds a given external {@link Contract} to a given
	 * {@link IExtensionPoint}.
	 * </p>
	 * 
	 * @param contractedExtensionPoint
	 *          The {@link IExtensionPoint} to that an external {@link Contract}
	 *          shall be bound.
	 * @param externalContract
	 *          The {@link Contract} that shall be bound to the
	 *          {@link IExtensionPoint}.
	 */
	private void addExternalContractToExtensionPoint(
			IExtensionPoint contractedExtensionPoint, Contract externalContract) {

		LinkedHashSet<Contract> definedContracts;

		/* Add the external contract to the extension point in the registry. */
		definedContracts = new LinkedHashSet<Contract>();

		if (this.contractedExtensionPoints.containsKey(contractedExtensionPoint)) {
			definedContracts =
					this.contractedExtensionPoints.get(contractedExtensionPoint);
		}

		else {
			definedContracts = new LinkedHashSet<Contract>();
		}

		definedContracts.add(externalContract);
		this.contractedExtensionPoints.put(contractedExtensionPoint,
				definedContracts);

		/*
		 * For contracted extension points also adapt their extensions (if any
		 * extension has been found already).
		 */
		this
				.addOrUpdateContractedExtensionsOfExtensionPoint(contractedExtensionPoint);

		/* Add the newly bound external contract to the registry. */
		LinkedHashSet<Contract> boundContracts;

		if (this.boundExternalContracts.containsKey(contractedExtensionPoint)) {
			boundContracts =
					this.boundExternalContracts.get(contractedExtensionPoints);
		}

		else {
			boundContracts = new LinkedHashSet<Contract>();
		}

		boundContracts.add(externalContract);
		this.boundExternalContracts.put(contractedExtensionPoint, boundContracts);

		/* Update change status. */
		this.setChanged();
	}

	/**
	 * <p>
	 * A helper method that iterates over the {@link IExtension}s of a given
	 * {@link IExtensionPoint} and checks if the {@link IExtensionPoint}'s
	 * {@link Contract} should be instantiated for them.
	 * </p>
	 * 
	 * @param extensionPoint
	 *          The {@link IExtensionPoint} whose {@link IExtension} shall be
	 *          checked.
	 */
	private void addOrUpdateContractedExtensionsOfExtensionPoint(
			IExtensionPoint extensionPoint) {

		EclipseExtensionPoint eclipseExtensionPoint;
		eclipseExtensionPoint =
				EclipseAdapterFactory.getInstance()
						.createExtensionPoint(extensionPoint);

		for (IExtension extension : extensionPoint.getExtensions()) {

			/* Check if the extension's bundle is already active. */
			if (org.eclipse.core.runtime.Platform.getBundle(
					extension.getContributor().getName()).getState() == Bundle.ACTIVE) {

				EclipseExtension eclipseExtension;

				eclipseExtension =
						EclipseAdapterFactory.getInstance().createExtension(extension);
				eclipseExtensionPoint.addExtension(eclipseExtension);

				/* Instantiate the extension's contract for the found extension. */
				this.updateOrInstantiateContracts(eclipseExtension);

				/* Store the found contracted extension. */
				this.contractedExtensions.put(extension, eclipseExtension
						.getContracts());

				/* Update change status. */
				this.setChanged();
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

		Set<Contract> externalContractsOfExtension;

		if (this.unboundExternalContracts.containsKey(contractedExtensionPointID)) {
			externalContractsOfExtension =
					this.unboundExternalContracts.get(contractedExtensionPointID);
		}

		else {
			externalContractsOfExtension = new HashSet<Contract>();
		}

		Contract externalContract;

		externalContract = new Contract();
		externalContract.setLocation(contractUrl);
		externalContract.setOwner(legislatorExtension);

		externalContractsOfExtension.add(externalContract);

		this.unboundExternalContracts.put(contractedExtensionPointID,
				externalContractsOfExtension);

		/* Set status to changed. */
		this.setChanged();
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
		if (this.contractedExtensionPoints.containsKey(eclipseExtensionPoint
				.getWrappedExtensionPoint())) {

			/* Reinitialize all contracts of the extension point. */
			for (Contract contract : this.contractedExtensionPoints
					.get(eclipseExtensionPoint.getWrappedExtensionPoint())) {

				LinkedHashSet<Contract> instantiatedContracts;
				instantiatedContracts = new LinkedHashSet<Contract>();

				/* Try to instantiate the given contract. */
				try {
					List<Contract> newInstantiatedContracts;

					newInstantiatedContracts =
							contract.bindSupplier(eclipseExtension, eclipseMgr);
					instantiatedContracts.addAll(newInstantiatedContracts);

					this.contractedExtensions.put(eclipseExtension.getWrappedExtension(),
							instantiatedContracts);

					/* Update changed status. */
					this.setChanged();
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

	/**
	 * <p>
	 * A helper method that checks if a given {@link Bundle} contains contracted
	 * {@link IExtensionPoint}s and removes them from the {@link ContractRegistry}
	 * .
	 * </p>
	 * 
	 * @param bundle
	 *          The {@link Bundle} that shall be checked.
	 */
	private void tryToRemoveContractedExtensionPoints(Bundle bundle) {

		IContributor contributor;
		IExtensionRegistry extensionRegistry;

		/* Get the contributor for the bundle. */
		contributor = ContributorFactoryOSGi.createContributor(bundle);

		/* Get the extension registry. */
		extensionRegistry =
				org.eclipse.core.runtime.Platform.getExtensionRegistry();

		/*
		 * Iterate through all constrained extension points that can now be removed
		 * again.
		 */
		for (IExtensionPoint extensionPoint : extensionRegistry
				.getExtensionPoints(contributor)) {

			if (this.contractedExtensionPoints.containsKey(extensionPoint)) {

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
				this.contractedExtensionPoints.remove(extensionPoint);

				/* Update changed status. */
				this.setChanged();
			}
			// no else.

			/* Check if the extension point has external contracts. */
			if (this.boundExternalContracts.containsKey(extensionPoint)) {

				/* Un-bind the external contracts. */
				this.unboundExternalContracts.put(extensionPoint.getUniqueIdentifier(),
						this.boundExternalContracts.get(extensionPoint));

				/* Remove the bound external contracts from the registry. */
				this.boundExternalContracts.remove(extensionPoint);

				/* Update changed status. */
				this.setChanged();
			}
			// no else.
		}
		// end for.
	}

	/**
	 * <p>
	 * A helper method that checks if a given {@link Bundle} contains contracted
	 * {@link IExtension}s and removes them from the {@link ContractRegistry}.
	 * </p>
	 * 
	 * @param bundle
	 *          The {@link Bundle} that shall be checked.
	 */
	private void tryToRemoveContractedExtensions(Bundle bundle) {

		IContributor contributor;
		IExtensionRegistry extensionRegistry;

		/* Get the contributor for the bundle. */
		contributor = ContributorFactoryOSGi.createContributor(bundle);

		/* Get the extension registry. */
		extensionRegistry =
				org.eclipse.core.runtime.Platform.getExtensionRegistry();

		/* Iterate through all constrained extensions that can now be removed again. */
		for (IExtension extension : extensionRegistry.getExtensions(contributor)) {

			if (this.contractedExtensions.containsKey(extension)) {

				EclipseExtension eclipseExtension;
				eclipseExtension =
						EclipseAdapterFactory.getInstance().createExtension(extension);

				/* Remove the contract from the extension. */
				this.uninstantiateContract(eclipseExtension);
			}
			// no else.
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
	 * @param bundle
	 *          The {@link Bundle} that shall be checked for external
	 *          {@link Contract}s.
	 */
	private void tryToRemoveExternalContract(Bundle bundle) {

		IContributor contributor;
		IExtensionRegistry extensionRegistry;

		/* Get the contributor for the bundle. */
		contributor = ContributorFactoryOSGi.createContributor(bundle);

		/* Get the extension registry. */
		extensionRegistry =
				org.eclipse.core.runtime.Platform.getExtensionRegistry();

		/* Iterate over the bundle's extensions. */
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
			if (this.contractedExtensions.containsKey(extension)) {

				EclipseExtension eclipseExtension;
				eclipseExtension =
						EclipseAdapterFactory.getInstance().createExtension(extension);

				this.uninstantiateContract(eclipseExtension);

				/* Also remove the extension from the extension point. */
				eclipseExtensionPoint.removeExtension(eclipseExtension);

				/* Update change status. */
				this.setChanged();
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
		for (IExtensionPoint contractedExtensionPoint : this.boundExternalContracts
				.keySet()) {

			Set<Contract> removedContracts;
			removedContracts = new HashSet<Contract>();

			/* Iterate through all contracts of the contracted extension point. */
			for (Contract externalContract : this.boundExternalContracts
					.get(contractedExtensionPoint)) {

				if (externalContract.getOwner().equals(legislatorEclipseExtension)) {

					LinkedHashSet<Contract> definedContracts;

					EclipseExtensionPoint contractedEclipseExtensionPoint;
					contractedEclipseExtensionPoint =
							EclipseAdapterFactory.getInstance().createExtensionPoint(
									contractedExtensionPoint);

					/* Un-instantiate the contract for all its extensions. */
					for (EclipseExtension contractedEclipseExtension : contractedEclipseExtensionPoint
							.getExtensions()) {

						this.uninstantiateContract(contractedEclipseExtension);
					}
					// end for.

					/* Remove the contract from the extension point. */
					definedContracts =
							this.contractedExtensionPoints.get(contractedExtensionPoint);
					definedContracts.remove(externalContract);

					/* Probably remove the contracted extension point. */
					if (definedContracts.size() == 0) {
						this.contractedExtensionPoints.remove(contractedExtensionPoint);

						/* Probably remove the contracted plug-in as well. */
						EclipsePlugin contractedPlugin;

						contractedPlugin =
								(EclipsePlugin) contractedEclipseExtensionPoint.getOwner();

						this.probablyRemovePluginFromRegistry(contractedPlugin);
					}

					/* Else only remove the contract from the registry. */
					else {
						this.contractedExtensionPoints.put(contractedExtensionPoint,
								definedContracts);
					}

					removedContracts.add(externalContract);
				}
				// no else (external contract defined by other extension).

				/* Probably remove the external contracts from the registry. */
				if (removedContracts.size() > 0) {

					LinkedHashSet<Contract> boundContracts;
					boundContracts =
							this.boundExternalContracts.get(contractedExtensionPoint);

					boundContracts.removeAll(removedContracts);

					if (boundContracts.size() > 0) {
						this.boundExternalContracts.put(contractedExtensionPoint,
								boundContracts);
					}

					else {
						this.boundExternalContracts.remove(contractedExtensionPoint);
					}

					/* Update change status. */
					this.setChanged();
				}
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

		for (String contractedExtensionPointID : this.unboundExternalContracts
				.keySet()) {

			for (Contract externalContract : this.unboundExternalContracts
					.get(contractedExtensionPointID)) {

				Set<Contract> removedContracts;
				removedContracts = new HashSet<Contract>();

				if (externalContract.getOwner().equals(legislatorEclipseExtension)) {

					removedContracts.add(externalContract);
				}
				// no else.

				Set<Contract> unboundContracts;

				unboundContracts =
						this.unboundExternalContracts.get(contractedExtensionPointID);
				unboundContracts.removeAll(removedContracts);

				if (unboundContracts.size() > 0) {
					this.unboundExternalContracts.put(contractedExtensionPointID,
							unboundContracts);
				}

				else {
					this.unboundExternalContracts.remove(contractedExtensionPointID);
				}

				/* Set status to changed. */
				this.setChanged();
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
		for (IExtensionPoint extensionPoint : this.contractedExtensionPoints
				.keySet()) {

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
			this.contractedPlugins.remove(plugin.getBundle());
			this.setChanged();
		}
		// no else.
	}

	/**
	 * <p>
	 * A helper method that un-instantiates the {@link Contract} for a given
	 * {@link EclipseExtension}.
	 * </p>
	 * 
	 * @param eclipseExtension
	 *          The {@link EclipseExtension} for that the {@link Contract} shall
	 *          be un-instantiated.
	 */
	private void uninstantiateContract(EclipseExtension eclipseExtension) {

		/* Probably un-instantiate the contract. */
		if (eclipseExtension.getContracts() != null
				&& eclipseExtension.getContracts().size() > 0) {

			/*
			 * Remove all contracts from the extension and the extension from the
			 * registry.
			 */
			this.contractedExtensions.remove(eclipseExtension.getWrappedExtension());

			/* Update change status. */
			this.setChanged();
		}
		// no else (contract already initialized).
	}

	/**
	 * <p>
	 * This method can be used to externally add a {@link Bundle} to the
	 * {@link Set} of contracted {@link Bundle}s.
	 * </p>
	 * 
	 * @param bundle
	 *          The {@link Bundle} that shall be added.
	 */
	protected void addContractedBunlde(Bundle bundle) {

		EclipsePlugin eclipsePlugin;
		eclipsePlugin =
				EclipseAdapterFactory.getInstance().createEclipsePlugin(bundle);

		this.contractedPlugins.put(bundle, eclipsePlugin);
	}

	/**
	 * <p>
	 * Resets the {@link ContractRegistry} to an empty status.
	 * </p>
	 */
	public void reset() {

		this.contractedExtensionPoints.clear();
		this.contractedExtensions.clear();
		this.contractedPlugins.clear();

		this.boundExternalContracts.clear();
		this.unboundExternalContracts.clear();

		EclipseAdapterFactory.getInstance().clearCache();
	}

	/**
	 * <p>
	 * Verifes a Collection of given {@link Contract}s for a given
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
	 *          An {@link IJobChangeListener} that can be used to obersve the
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
}