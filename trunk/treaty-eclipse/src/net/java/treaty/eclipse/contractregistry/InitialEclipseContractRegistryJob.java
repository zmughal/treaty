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

import java.util.LinkedHashSet;

import javax.management.monitor.Monitor;

import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;

/**
 * <p>
 * The {@link InitialEclipseContractRegistryJob} is used to initialize the
 * {@link EclipseContractRegistry} to capture all {@link BundleEvent}s that did happen
 * before the {@link EclipseContractRegistry} has been started.
 * </p>
 * 
 * <p>
 * The {@link InitialEclipseContractRegistryJob} can also be used to re-initialize the
 * {@link EclipseContractRegistry}.
 * </p>
 * 
 * @author Claas Wilke
 */
public class InitialEclipseContractRegistryJob extends Job {

	/**
	 * <p>
	 * Creates a new {@link InitialEclipseContractRegistryJob} with a given name.
	 * </p>
	 * 
	 * @param name
	 *          The name of the {@link InitialEclipseContractRegistryJob}.
	 */
	public InitialEclipseContractRegistryJob(String name) {

		super(name);
	}

	/**
	 * <p>
	 * Performs the collection of all required information from the
	 * {@link IExtensionRegistry}.
	 * </p>
	 */
	protected IStatus run(IProgressMonitor monitor) {

		LinkedHashSet<Bundle> bundles;
		int totalWork;

		/* Update monitor status. */
		totalWork = 10000;
		monitor.beginTask("Analysing contracts", totalWork + 1000);

		bundles = this.collectPlugins(monitor);

		/* Probably cancel the job. */
		if (monitor.isCanceled()) {
			EclipseContractRegistry.clear();
			return Status.CANCEL_STATUS;
		}
		// no else.

		int checkedBundles = 0;

		/* Notify the registry about all of these bundles that are already active. */
		for (Bundle bundle : bundles) {

			if (bundle.getState() == Bundle.ACTIVE) {
				BundleEvent bundleEvent;
				bundleEvent = new BundleEvent(BundleEvent.STARTED, bundle);

				EclipseContractRegistry.getInstance().update(bundleEvent);
			}
			// no else.

			checkedBundles++;
			monitor.worked(totalWork / bundles.size() * checkedBundles);
		}

		monitor.done();
		return Status.OK_STATUS;
	}

	/**
	 * <p>
	 * A helper method that collects all {@link Bundle}s that provide either
	 * extensions or extension points.
	 * </p>
	 * 
	 * @param monitor
	 *          The {@link Monitor} of the {@link Job} that uses this method.
	 * @return A {@link LinkedHashSet} of all {@link Bundle}s that provide either
	 *         extensions or extension points.
	 */
	private LinkedHashSet<Bundle> collectPlugins(IProgressMonitor monitor) {

		LinkedHashSet<Bundle> result;
		IExtensionRegistry registry;

		monitor.subTask("Analysing plugins");

		registry = org.eclipse.core.runtime.Platform.getExtensionRegistry();
		result = new LinkedHashSet<Bundle>();

		/* Collect all plug-ins that provide either extensions or extension points. */
		for (IExtensionPoint extensionPoint : registry.getExtensionPoints()) {

			IContributor contributor;
			Bundle bundle;

			contributor = extensionPoint.getContributor();
			bundle =
					org.eclipse.core.runtime.Platform.getBundle(contributor.getName());

			result.add(bundle);

			/*
			 * Iterate on all extensions of the extension point and add their bundles
			 * as well.
			 */
			for (IExtension extension : extensionPoint.getExtensions()) {

				contributor = extension.getContributor();
				bundle =
						org.eclipse.core.runtime.Platform.getBundle(contributor.getName());

				result.add(bundle);
			}
			// end for (extensions).
		}
		// end for (extension points).

		monitor.worked(1000);

		return result;
	}
}