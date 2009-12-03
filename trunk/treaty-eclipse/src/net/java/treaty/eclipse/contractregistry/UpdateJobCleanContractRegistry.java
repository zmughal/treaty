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

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

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
	 * The work of {@link UpdateJobCleanContractRegistry}s to Clean the
	 * {@link EclipseContractRegistry}.
	 */
	private static final int WORK_CLEAN = 33000;

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
		monitor.beginTask("Clean the ContracRegistry.", WORK_CLEAN);

		EclipseContractRegistry.clear();
		EclipseAdapterFactory.getInstance().clearCache();

		UpdateJobInitializeContractRegistry contractRegistryStartUpJob;
		contractRegistryStartUpJob =
				new UpdateJobInitializeContractRegistry(
						"Re-initial ContractRegistry startup");
		contractRegistryStartUpJob.setRule(new ContractRegistryAccess(true));

		contractRegistryStartUpJob.schedule();

		monitor.done();

		return Status.OK_STATUS;
	}
}