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
import net.java.treaty.Annotatable;
import net.java.treaty.ComplexCondition;
import net.java.treaty.Connector;
import net.java.treaty.Contract;
import net.java.treaty.Resource;
import net.java.treaty.ResourceLoader;
import net.java.treaty.ResourceLoaderException;
import net.java.treaty.VerificationPolicy;
import net.java.treaty.VerificationReport;
import net.java.treaty.VerificationResult;
import net.java.treaty.eclipse.EclipseVerifier;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * Verification job.
 * 
 * @author Jens Dietrich
 */

public class VerificationJob extends Job {

	private Collection<Contract> contracts = null;
	private VerificationReport verReport = null;
	private List<Contract> failedContracts = new ArrayList<Contract>();
	private List<Contract> doneContracts = new ArrayList<Contract>();
	private List<VerificationJobListener> jobListeners =
			new ArrayList<VerificationJobListener>();

	public void addVerificationJobListener(VerificationJobListener l) {

		jobListeners.add(l);
	}

	public void removeVerificationJobListener(VerificationJobListener l) {

		jobListeners.remove(l);
	}

	private void statusChanged() {

		for (VerificationJobListener l : jobListeners) {
			l.verificationStatusChanged();
		}
	}

	public VerificationJob(String name, Collection<Contract> contracts,
			VerificationReport verReport) {

		super(name);
		this.contracts = contracts;
		this.verReport = verReport;
	}

	protected IStatus run(IProgressMonitor monitor) {

		monitor.beginTask("run verification", (2 * contracts.size()) + 3);

		monitor.subTask("Reset verification status");
		for (Contract c : contracts) {
			resetVerificationStatus(c);
		}
		statusChanged();
		if (monitor.isCanceled())
			return Status.CANCEL_STATUS;

		monitor.subTask("loading installed vocabularies");
		EclipseVerifier verifier = new EclipseVerifier();
		monitor.worked(3);

		// loading resources
		for (Contract c : contracts) {
			// System.out.println("loading resources in " + c);
			try {
				loadResources(c, verifier);
			} catch (ResourceLoaderException e) {
				// do not set result to failure - even if resources cannot be loaded,
				// contract
				// could still succeed, e.g. when conditions are disjunctions
				// c.setProperty(VERIFICATION_RESULT,VerificationResult.FAILURE);
				c.setProperty(VERIFICATION_EXCEPTION, e);
			}
			monitor.worked(1);
			if (monitor.isCanceled())
				return Status.CANCEL_STATUS;
		}

		monitor.subTask("checking contracts");
		for (Contract c : contracts) {
			// System.out.println("checking contract " + c);
			// TODO: contracts also fail when mandatory resources cannot be loaded
			boolean result =
					c.check(verReport, verifier, VerificationPolicy.DETAILED);
			if (!result)
				failedContracts.add(c);
			doneContracts.add(c);
			propagateResults(c);
			monitor.worked(1);
			statusChanged();
			if (monitor.isCanceled())
				return Status.CANCEL_STATUS;
		}
		return Status.OK_STATUS;
	}

	private void combineResults(Annotatable a, Annotatable part) {

		VerificationResult r =
				(VerificationResult) part.getProperty(VERIFICATION_RESULT);
		if (r == null) {
			r = VerificationResult.UNKNOWN;
		}
		VerificationResult old =
				(VerificationResult) a.getProperty(VERIFICATION_RESULT);
		if (old == null) {
			a.setProperty(VERIFICATION_RESULT, r);
		}
		else if (r == VerificationResult.FAILURE) {
			a.setProperty(VERIFICATION_RESULT, VerificationResult.FAILURE);
		}
		else if (r == VerificationResult.SUCCESS
				&& old == VerificationResult.SUCCESS) {
			// keep success
		}
		else if (r == VerificationResult.UNKNOWN
				&& old == VerificationResult.SUCCESS) {
			a.setProperty(VERIFICATION_RESULT, VerificationResult.UNKNOWN);
		}
	}

	private void propagateResults(Contract c) {

		for (AbstractCondition part : c.getConstraints()) {
			combineResults(c, part);
			combineResults(c.getSupplier(), c);
			combineResults(c.getSupplier().getOwner(), c.getSupplier());
			combineResults(c.getConsumer(), c);
			combineResults(c.getConsumer().getOwner(), c.getSupplier());
		}

	}

	private void resetVerificationStatus(Contract c) {

		c.removeProperty(VERIFICATION_RESULT);
		c.removeProperty(VERIFICATION_EXCEPTION);

		for (AbstractCondition cond : c.getConstraints()) {
			resetVerificationStatus(cond);
		}
		c.getSupplier().removeProperty(VERIFICATION_RESULT);
		c.getConsumer().removeProperty(VERIFICATION_RESULT);
		if (c.getOwner() != null) {
			c.getOwner().removeProperty(VERIFICATION_RESULT);
		}
		c.getSupplier().getOwner().removeProperty(VERIFICATION_RESULT);
		c.getConsumer().getOwner().removeProperty(VERIFICATION_RESULT);
	}

	private void resetVerificationStatus(AbstractCondition c) {

		c.removeProperty(VERIFICATION_RESULT);
		c.removeProperty(VERIFICATION_EXCEPTION);
		if (c instanceof ComplexCondition) {
			ComplexCondition cc = (ComplexCondition) c;
			for (AbstractCondition part : cc.getParts()) {
				resetVerificationStatus(part);
			}
		}
	}

	private void loadResources(Contract c, ResourceLoader l)
			throws ResourceLoaderException {

		for (Resource r : c.getConsumerResources()) {
			loadResource(c, l, c.getConsumer(), r);
		}
		for (Resource r : c.getSupplierResources()) {
			loadResource(c, l, c.getSupplier(), r);
		}
		for (Resource r : c.getExternalResources()) {
			loadResource(c, l, r.getOwner(), r);
		}

	}

	/**
	 * FIXME Claas parameter c is never used.
	 * 
	 * @param c
	 * @param l
	 * @param con
	 * @param r
	 */
	private void loadResource(Contract c, ResourceLoader l, Connector con,
			Resource r) {

		if (r.isProvided() && !r.isLoaded()) {
			try {
				Object value = l.load(r.getType(), r.getName(), con);
				r.setValue(value);
			} catch (ResourceLoaderException x) {
				r.setProperty(VERIFICATION_EXCEPTION, x);
			}
		}
	}

	public Collection<Contract> getContracts() {

		return contracts;
	}

	public VerificationReport getVerReport() {

		return verReport;
	}

	public List<Contract> getFailedContracts() {

		return failedContracts;
	}

	public List<Contract> getDoneContracts() {

		return doneContracts;
	}
}