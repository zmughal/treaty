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

import java.util.Set;

import net.java.treaty.Contract;
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
 * {@link EclipseContractRegistry} after the removal of an
 * {@link IExtensionPoint} or {@link IExtension} that is invalid and thus cannot
 * be identyfied.
 * </p>
 * 
 * @author Claas Wilke
 */
public class UpdateJobCleanContractRegistry extends Job {

	/**
	 * The work of {@link UpdateJobCleanContractRegistry}s to search for
	 * {@link Contract}s defined with a {@link Bundle}'s {@link IExtensionPoint}s.
	 */
	private static final int WORK_ECLIPSE_PLUGINS = 33000;

	/**
	 * <p>
	 * Creates a new {@link UpdateJobCleanContractRegistry}.
	 * </p>
	 * 
	 * @param name
	 *          The name of this {@link Job}.
	 */
	public UpdateJobCleanContractRegistry(String name) {

		super(name);
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
		monitor.beginTask("Clean the ContracRegistry.", WORK_ECLIPSE_PLUGINS);

		Set<EclipsePlugin> contractedPlugins;
		contractedPlugins =
				EclipseContractRegistry.getInstance().getContractedEclipsePlugins();

		for (EclipsePlugin eclipsePlugin : contractedPlugins) {

			switch (eclipsePlugin.getBundle().getState()) {

			case Bundle.UNINSTALLED:

				/* FIXME Claas: Implement a registry clean/reset here. */
				Logger.warn("Found uninstalled bundle: " + eclipsePlugin);
				break;
			// no default case;
			}
			// end switch.

			monitor.worked(WORK_ECLIPSE_PLUGINS / contractedPlugins.size());
		}
		// end for.

		monitor.done();

		return Status.OK_STATUS;
	}
}