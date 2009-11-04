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

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

/**
 * <p>
 * A {@link BundleListener} that checks if a started or stopped {@link Bundle}
 * uses or provides services that are specified with a Treaty contract or
 * defines external contracts. Probably, contracts are added to the
 * {@link EclipseContractRegistry}.
 * </p>
 * 
 * @author Claas Wilke
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

		EclipseContractRegistry.getInstance().update(event);
	}
}