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
 * <p>
 * A {@link Job} to verify a given {@link Collection} of {@link Contract}s.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class VerificationJob extends Job {

	/** The Contracts that shall be verified. */
	private Collection<Contract> contracts = null;

	/** The done {@link Contract}s. */
	private List<Contract> doneContracts = new ArrayList<Contract>();

	/** The failed {@link Contract}s. */
	private List<Contract> failedContracts = new ArrayList<Contract>();

	/** The {@link VerificationJobListener}s of this {@link VerificationJob}. */
	private List<VerificationJobListener> myListeners =
			new ArrayList<VerificationJobListener>();

	/** The {@link VerificationReport} to report the results. */
	private VerificationReport verificationReport = null;

	/**
	 * <p>
	 * Creates a new {@link VerificationJob}.
	 * </p>
	 * 
	 * @param name
	 *          The name of the {@link VerificationJob}.
	 * @param contracts
	 *          The {@link Contract}s that shall be verified.
	 * @param verificationReport
	 *          The {@link VerificationReport} to report the result.
	 */
	public VerificationJob(String name, Collection<Contract> contracts,
			VerificationReport verificationReport) {

		super(name);

		this.contracts = contracts;
		this.verificationReport = verificationReport;
	}

	/**
	 * <p>
	 * Adds a new {@link VerificationJobListener} to this {@link VerificationJob}.
	 * </p>
	 * 
	 * @param listener
	 *          The {@link VerificationJobListener} that shall be added.
	 */
	public void addVerificationJobListener(VerificationJobListener listener) {

		this.myListeners.add(listener);
	}

	/**
	 * <p>
	 * Removes a {@link VerificationJobListener} from this {@link VerificationJob}
	 * .
	 * </p>
	 * 
	 * @param listener
	 *          The {@link VerificationJobListener} that shall be removed.
	 */
	public void removeVerificationJobListener(VerificationJobListener listener) {

		this.myListeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor
	 * )
	 */
	protected IStatus run(IProgressMonitor monitor) {
	
		monitor.beginTask("Run Verification.", (2 * contracts.size()) + 3);
	
		monitor.subTask("Reset verification status.");
	
		for (Contract contract : this.contracts) {
			this.resetVerificationStatus(contract);
		}
		// end for.
	
		/* Notify Listeners. */
		this.statusChanged();
	
		/* Probably cancel. */
		if (monitor.isCanceled()) {
			return Status.CANCEL_STATUS;
		}
		// no else.
	
		monitor.subTask("Loading installed Vocabularies.");
	
		EclipseVerifier verifier;
		verifier = new EclipseVerifier();
	
		monitor.worked(3);
	
		/* Loading resources. */
		for (Contract contract : this.contracts) {
	
			try {
				this.loadResources(contract, verifier);
			}
	
			catch (ResourceLoaderException e) {
				/*
				 * Do not set result to failure - even if resources cannot be loaded,
				 * the contract could still succeed, e.g. when conditions are
				 * disjunctions.
				 */
				contract.setProperty(VERIFICATION_EXCEPTION, e);
			}
	
			monitor.worked(1);
	
			/* Probably cancel. */
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			// no else.
		}
	
		monitor.subTask("Checking Contracts.");
	
		for (Contract contract : contracts) {
	
			/* TODO: contracts also fail when mandatory resources cannot be loaded. */
			boolean result;
			result =
					contract.check(verificationReport, verifier,
							VerificationPolicy.DETAILED);
	
			if (!result) {
				this.failedContracts.add(contract);
			}
	
			this.doneContracts.add(contract);
	
			this.propagateResults(contract);
	
			monitor.worked(1);
	
			/* Notify Listeners. */
			this.statusChanged();
	
			/* Probably cancel. */
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			// no else.
		}
	
		return Status.OK_STATUS;
	}

	/**
	 * <p>
	 * Used to notify all register {@link VerificationJobListener}s.
	 * </p>
	 */
	private void statusChanged() {

		for (VerificationJobListener listener : this.myListeners) {
			listener.verificationStatusChanged();
		}
		// end for.
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

		return verificationReport;
	}

	public List<Contract> getFailedContracts() {

		return failedContracts;
	}

	public List<Contract> getDoneContracts() {

		return doneContracts;
	}
}