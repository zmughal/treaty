package net.java.treaty.eclipse.contractregistry;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

/**
 * <p>
 * A {@link BundleListener} that checks if a started or stopped {@link Bundle}
 * uses or provides services that are specified with a Treaty contract or
 * defines external contracts. Probably, contracts are added to the
 * {@link ContractRegistry}.
 * </p>
 * 
 * @author Claas Wilke
 * 
 */
public class BundleEventContractListener implements BundleListener {

	/** The Singleton instance of {@link BundleEventContractListener}. */
	private static BundleEventContractListener myInstance;

	/**
	 * <p>
	 * Private constructor for Singleton Pattern.
	 * </p>
	 */
	private BundleEventContractListener() {

	}

	/**
	 * <p>
	 * Returns the Singleton Instance of {@link BundleEventContractListener}.
	 * </p>
	 * 
	 * @return The Singleton Instance of {@link BundleEventContractListener}.
	 */
	public static BundleEventContractListener getInstance() {

		if (myInstance == null) {
			myInstance = new BundleEventContractListener();
		}
		// no else.

		return myInstance;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.osgi.framework.BundleListener#bundleChanged(org.osgi.framework.BundleEvent
	 * )
	 */
	public void bundleChanged(BundleEvent event) {

		ContractRegistry.getInstance().update(event);
	}
}