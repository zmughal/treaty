/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.verification;

import static net.java.treaty.eclipse.Constants.VERIFICATION_EXCEPTION;
import static net.java.treaty.eclipse.Constants.VERIFICATION_RESULT;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.java.treaty.Condition;
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

	/** The {@link VerificationPolicy}s of this {@link VerificationJob}. */
	private VerificationPolicy verificationPolicy;

	/** The {@link VerificationReport}s to report the results. */
	private List<VerificationReport> verificationReports = null;

	/**
	 * <p>
	 * Creates a new {@link VerificationJob}.
	 * </p>
	 * 
	 * @param name
	 *          The name of the {@link VerificationJob}.
	 * @param contracts
	 *          The {@link Contract}s that shall be verified.
	 * @param verificationPolicy
	 *          The {@link VerificationPolicy}s of this {@link VerificationJob}.
	 */
	public VerificationJob(String name, Collection<Contract> contracts,
			VerificationPolicy verificationPolicy) {

		super(name);

		this.contracts = contracts;
		this.verificationReports = new ArrayList<VerificationReport>();
		this.verificationPolicy = verificationPolicy;
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
	 * Returns the {@link Contract}s of this {@link VerificationJob}.
	 * </p>
	 * 
	 * @return The {@link Contract}s of this {@link VerificationJob}.
	 */
	public Collection<Contract> getContracts() {

		return this.contracts;
	}

	/**
	 * <p>
	 * Returns all {@link Contract}s whose verification was done.
	 * </p>
	 * 
	 * @return All {@link Contract}s whose verification was done.
	 */
	public List<Contract> getDoneContracts() {

		return this.doneContracts;
	}

	/**
	 * <p>
	 * Returns all {@link Contract}s whose verification failed.
	 * </p>
	 * 
	 * @return All {@link Contract}s whose verification failed.
	 */
	public List<Contract> getFailedContracts() {

		return this.failedContracts;
	}

	/**
	 * <p>
	 * Returns the {@link VerificationReport}s of this {@link VerificationJob}.
	 * </p>
	 * 
	 * @return The {@link VerificationReport}s of this {@link VerificationJob}.
	 */
	public List<VerificationReport> getVerificationReports() {

		return this.verificationReports;
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
				// no else.
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
			EclipseVerificationReport verificationReport;
			verificationReport = new EclipseVerificationReport();

			boolean result;

			result = contract.check(verificationReport, verifier, verificationPolicy);
			this.verificationReports.add(verificationReport);

			if (result) {
				/* Inform listeners. */
				for (VerificationJobListener listener : this.myListeners) {
					listener
							.verificationOfContractSucceeded(contract, verificationReport);
				}
				// end for.
			}

			else {
				this.failedContracts.add(contract);

				/* Inform listeners. */
				for (VerificationJobListener listener : this.myListeners) {
					listener.verificationOfContractFailed(contract, verificationReport);
				}
				// end for.
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
	 * Combines the verification results for a given {@link Annotatable} and its
	 * part (also {@link Annotatable}s).
	 * </p>
	 * 
	 * @param annotable
	 *          The {@link Annotatable} for that the results shall be combined.
	 * @param part
	 *          The part of that the result shall be combined.
	 */
	private void combineVerificationResults(Annotatable annotable,
			Annotatable part) {

		VerificationResult verificationResult;
		verificationResult =
				(VerificationResult) part.getProperty(VERIFICATION_RESULT);

		if (verificationResult == null) {
			verificationResult = VerificationResult.UNKNOWN;
		}
		// no else.

		VerificationResult oldVerificationResult;
		oldVerificationResult =
				(VerificationResult) annotable.getProperty(VERIFICATION_RESULT);

		if (oldVerificationResult == null) {
			annotable.setProperty(VERIFICATION_RESULT, verificationResult);
		}

		else if (verificationResult == VerificationResult.FAILURE) {
			annotable.setProperty(VERIFICATION_RESULT, VerificationResult.FAILURE);
			/* Propagate failures. */
		}

		else if (verificationResult == VerificationResult.SUCCESS
				&& oldVerificationResult == VerificationResult.SUCCESS) {
			/* Keep successes. */
		}

		else if (verificationResult == VerificationResult.UNKNOWN
				&& oldVerificationResult == VerificationResult.SUCCESS) {

			/* Override successes with unknown, but not failures. */
			annotable.setProperty(VERIFICATION_RESULT, VerificationResult.UNKNOWN);
		}
		// no else.
	}

	/**
	 * <p>
	 * A helper method that loads the {@link Resource}s required to verify a given
	 * {@link Contract}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} whose {@link Resource}s shall be loaded.
	 * @param loader
	 *          The {@link ResourceLoader} used to load the {@link Resource}s.
	 * @throws ResourceLoaderException
	 *           Thrown, if the loading fails.
	 */
	private void loadResources(Contract contract, ResourceLoader loader)
			throws ResourceLoaderException {

		/* Probably load consumer resources. */
		for (Resource resource : contract.getConsumerResources()) {
			this.loadResource(loader, contract.getConsumer(), resource);
		}
		// end for.

		/* Probably load supplier resources. */
		for (Resource resource : contract.getSupplierResources()) {
			this.loadResource(loader, contract.getSupplier(), resource);
		}
		// end for.

		/* Probably load external resources (from legislator plug-ins). */
		for (Resource resource : contract.getExternalResources()) {
			loadResource(loader, resource.getOwner(), resource);
		}
		// end for.
	}

	/**
	 * <p>
	 * Loads a given {@link Resource} from a given {@link Connector} using a given
	 * {@link ResourceLoader}.
	 * </p>
	 * 
	 * @param loader
	 *          The {@link ResourceLoader} used to load the {@link Resource}.
	 * @param connector
	 *          The {@link Connector} from that the {@link Resource} shall be
	 *          loaded.
	 * @param resource
	 *          The {@link Resource} that shall be loaded.
	 */
	private void loadResource(ResourceLoader loader, Connector connector,
			Resource resource) {

		/* Check if the resource has been loaded already. */
		if (resource.isProvided() && !resource.isLoaded()) {

			/* Try to load the resource. */
			try {
				Object value;

				value = loader.load(resource.getType(), resource.getName(), connector);
				resource.setValue(value);
			}

			catch (ResourceLoaderException x) {
				resource.setProperty(VERIFICATION_EXCEPTION, x);
			}
			// end catch.
		}
		// no else.
	}

	/**
	 * <p>
	 * Resets the verification status of a given {@link AbstractCondition}.
	 * </p>
	 * 
	 * @param condition
	 *          The {@link AbstractCondition} whose verification status shall be
	 *          reset.
	 */
	private void resetVerificationStatus(Condition condition) {

		condition.removeProperty(VERIFICATION_RESULT);
		condition.removeProperty(VERIFICATION_EXCEPTION);

		if (condition instanceof ComplexCondition) {

			ComplexCondition complexCondition;
			complexCondition = (ComplexCondition) condition;

			for (Condition part : complexCondition.getParts()) {
				resetVerificationStatus(part);
			}
			// end for.
		}
		// no else.
	}

	/**
	 * <p>
	 * Resets the verification status of a given {@link Contract}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} whose verification status shall be reset.
	 */
	private void resetVerificationStatus(Contract contract) {

		contract.removeProperty(VERIFICATION_RESULT);
		contract.removeProperty(VERIFICATION_EXCEPTION);

		for (Condition condition : contract.getConstraints()) {
			resetVerificationStatus(condition);
		}
		// end for.

		if (contract.getSupplier() != null) {
			contract.getSupplier().removeProperty(VERIFICATION_RESULT);

			if (contract.getSupplier().getOwner() != null) {
				contract.getSupplier().getOwner().removeProperty(VERIFICATION_RESULT);
			}
			// no else.
		}
		// no else.

		if (contract.getConsumer() != null) {
			contract.getConsumer().removeProperty(VERIFICATION_RESULT);

			if (contract.getConsumer().getOwner() != null) {
				contract.getConsumer().getOwner().removeProperty(VERIFICATION_RESULT);
			}
			// no else.
		}
		// no else.

		if (contract.getOwner() != null) {
			contract.getOwner().removeProperty(VERIFICATION_RESULT);
		}
		// no else.
	}

	/**
	 * <p>
	 * Propagates the {@link VerificationResult} of a {@link Contract} to all
	 * contained {@link AbstractCondition}s.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} for whose {@link AbstractCondition}s the
	 *          {@link VerificationResult}s shall be propagated.
	 */
	private void propagateResults(Contract contract) {

		for (Condition condition : contract.getConstraints()) {

			this.combineVerificationResults(contract, condition);

			this.combineVerificationResults(contract.getSupplier(), contract);
			this.combineVerificationResults(contract.getSupplier().getOwner(),
					contract.getSupplier());

			this.combineVerificationResults(contract.getConsumer(), contract);
			this.combineVerificationResults(contract.getConsumer().getOwner(),
					contract.getSupplier());
		}
		// no else.
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
}