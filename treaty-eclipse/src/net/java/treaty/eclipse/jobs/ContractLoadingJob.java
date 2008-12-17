/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.jobs;

import static net.java.treaty.eclipse.Constants.VERIFICATION_EXCEPTION;
import static net.java.treaty.eclipse.Constants.VERIFICATION_RESULT;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.java.treaty.AbstractCondition;
import net.java.treaty.AggregatedContract;
import net.java.treaty.Annotatable;
import net.java.treaty.ComplexCondition;
import net.java.treaty.Connector;
import net.java.treaty.Contract;
import net.java.treaty.Resource;
import net.java.treaty.ResourceLoader;
import net.java.treaty.ResourceLoaderException;
import net.java.treaty.SimpleContract;
import net.java.treaty.VerificationPolicy;
import net.java.treaty.VerificationReport;
import net.java.treaty.VerificationResult;
import net.java.treaty.eclipse.ContractRepository;
import net.java.treaty.eclipse.EclipseVerifier;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * Job to load contracts.
 * @author Jens Dietrich
 */

public class ContractLoadingJob extends Job {
	
	public ContractLoadingJob(String name) {
		super(name);
	}

    protected IStatus run(IProgressMonitor monitor) {
    	monitor.beginTask("run verification", 100);
    	monitor.subTask("Loading contracts");
    	ContractRepository.getDefault(); // trigger lazy initialisation
       	monitor.worked(100);
       	monitor.done();
    	return Status.OK_STATUS;	        	 
     }
}
