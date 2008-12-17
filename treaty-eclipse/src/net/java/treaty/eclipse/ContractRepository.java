/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.MultiRule;
import net.java.treaty.Contract;
import net.java.treaty.VerificationReport;
import net.java.treaty.eclipse.jobs.ContractLoadingJob;
import net.java.treaty.eclipse.jobs.VerificationJob;
import net.java.treaty.eclipse.jobs.VerificationJobListener;
/**
 * Contract repository. This is a singleton. This class manages all contracts found in the workspace
 * and has the API to run verification.
 * @author Jens Dietrich
 */
public class ContractRepository {
	private Collection<EclipsePlugin> plugins = null;
	
	public ContractRepository(Collection<EclipsePlugin> pluginsWithContracts) {
		super();
		this.plugins = pluginsWithContracts;
	}
	private static ContractRepository DEFAULT_INSTANCE = null;
	
	public void install() {
		DEFAULT_INSTANCE = this;
	}
	
	public Collection<EclipsePlugin> getPluginsWithContracts() {
		return plugins;
	}
	public Collection<Contract> getInstantiatedContracts() {
		List<Contract> contracts = new ArrayList<Contract>();
		for (EclipsePlugin plugin:getPluginsWithContracts()) {
			for (EclipseExtensionPoint xp:plugin.getExtensionPoints()) {
				for (EclipseExtension x:xp.getExtensions()) {
					Contract c = x.getContract();
					if (c!=null && c.isInstantiated()) {
						contracts.add(c);
					}
				}
			}
		}
		return contracts;
	}
	
	
	public static void reset(IJobChangeListener listener) {
		final ContractLoadingJob job = new ContractLoadingJob("Loading contracts");
		job.addJobChangeListener(listener);
		job.schedule();
	}
	public static ContractRepository getDefault()  {
		return DEFAULT_INSTANCE;
	}
	
	public void verify(Collection<Contract> contracts,VerificationReport verReport,VerificationJobListener vListener,IJobChangeListener jListener) {
		VerificationJob job = new VerificationJob("Treaty component verification",contracts,verReport);
		job.addVerificationJobListener(vListener);
		job.addJobChangeListener(jListener);
		ISchedulingRule combinedRule = null;
		for (Contract contract:contracts) {
			MultiRule.combine(new ContractVerificationSchedulingRule(contract),combinedRule);
		}
		job.setRule(combinedRule);
		job.schedule();
	}
	
	
}
