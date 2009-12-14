/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.verification;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.TreatyException;
import net.java.treaty.VerificationPolicy;
import net.java.treaty.VerificationReport;
import net.java.treaty.eclipse.Logger;
import net.java.treaty.eclipse.action.EclipseActionRegistry;
import net.java.treaty.trigger.verification.AbstractTriggeredVerifier;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;

public class TriggeredEclipseVerifier extends AbstractTriggeredVerifier {

	/** The singleton instance of the {@link TriggeredEclipseVerifier}. */
	public static final TriggeredEclipseVerifier INSTANCE =
			new TriggeredEclipseVerifier();

	/**
	 * The {@link VerificationPolicy} used by the {@link TriggeredEclipseVerifier}
	 * .
	 */
	private VerificationPolicy verificationPolicy = VerificationPolicy.DETAILED;

	/**
	 * <p>
	 * Private constructor for singleton pattern.
	 * </p>
	 */
	public TriggeredEclipseVerifier() {

		/* Remains empty. */
	}

	/**
	 * <p>
	 * Returns the {@link VerificationPolicy} used by the
	 * {@link TriggeredEclipseVerifier}.
	 * </p>
	 * 
	 * @return The {@link VerificationPolicy} used by the
	 *         {@link TriggeredEclipseVerifier}.
	 */
	public VerificationPolicy getVerificationPolicy() {

		return this.verificationPolicy;
	}

	/**
	 * <p>
	 * Sets the {@link VerificationPolicy} used by the
	 * {@link TriggeredEclipseVerifier}.
	 * </p>
	 * 
	 * @return The {@link VerificationPolicy} used by the
	 *         {@link TriggeredEclipseVerifier}.
	 */
	public void setVerificationPolicy(VerificationPolicy verificationPolicy) {

		this.verificationPolicy = verificationPolicy;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.trigger.verification.AbstractTriggeredVerifier#verify(java
	 * .net.URI, java.util.Set)
	 */
	protected void verify(URI triggerType, Set<Contract> contracts) {

		final URI triggerType2 = triggerType;
		final Set<Contract> contracts2 = contracts;

		VerificationReport verificationReport;
		verificationReport = new EclipseVerificationReport();

		/* A {@link VerificationJobListener} used during verification. */
		VerificationJobListener vListener = new VerificationJobListener() {

			/*
			 * (non-Javadoc)
			 * @seenet.java.treaty.eclipse.jobs.VerificationJobListener#
			 * verificationStatusChanged()
			 */
			public void verificationStatusChanged() {

				/* Remains empty. */
			}

			/*
			 * (non-Javadoc)
			 * @seenet.java.treaty.eclipse.verification.VerificationJobListener#
			 * verificationOfContractFailed(net.java.treaty.Contract,
			 * net.java.treaty.VerificationReport)
			 */
			public void verificationOfContractFailed(Contract contract,
					VerificationReport verificationReport) {

				performActionVerificationOfContractFailed(triggerType2, contract,
						verificationReport);
			}

			/*
			 * (non-Javadoc)
			 * @seenet.java.treaty.eclipse.verification.VerificationJobListener#
			 * verificationOfContractSucceeded(net.java.treaty.Contract,
			 * net.java.treaty.VerificationReport)
			 */
			public void verificationOfContractSucceeded(Contract contract,
					VerificationReport verificationReport) {

				performActionVerificationOfContractSucceeded(triggerType2, contract,
						verificationReport);
			}
		};

		/* A {@link IJobChangeListener} used during verification. */
		IJobChangeListener jListener = new IJobChangeListener() {

			/*
			 * (non-Javadoc)
			 * @see
			 * org.eclipse.core.runtime.jobs.IJobChangeListener#aboutToRun(org.eclipse
			 * .core.runtime.jobs.IJobChangeEvent)
			 */
			public void aboutToRun(IJobChangeEvent e) {

				performActionBeginVerification(triggerType2, contracts2);
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * org.eclipse.core.runtime.jobs.IJobChangeListener#awake(org.eclipse.
			 * core.runtime.jobs.IJobChangeEvent)
			 */
			public void awake(IJobChangeEvent e) {

				/* Do nothing. */
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * org.eclipse.core.runtime.jobs.IJobChangeListener#done(org.eclipse.core
			 * .runtime.jobs.IJobChangeEvent)
			 */
			public void done(IJobChangeEvent event) {

				VerificationJob verificationJob;
				verificationJob = (VerificationJob) event.getJob();

				performActionEndVerification(triggerType2,
						new HashSet<VerificationReport>(verificationJob
								.getVerificationReports()));
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * org.eclipse.core.runtime.jobs.IJobChangeListener#running(org.eclipse
			 * .core.runtime.jobs.IJobChangeEvent)
			 */
			public void running(IJobChangeEvent e) {

				/* Do nothing. */
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * org.eclipse.core.runtime.jobs.IJobChangeListener#scheduled(org.eclipse
			 * .core.runtime.jobs.IJobChangeEvent)
			 */
			public void scheduled(IJobChangeEvent e) {

				/* Do nothing. */
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * org.eclipse.core.runtime.jobs.IJobChangeListener#sleeping(org.eclipse
			 * .core.runtime.jobs.IJobChangeEvent)
			 */
			public void sleeping(IJobChangeEvent e) {

				/* Do nothing. */
			}
		};

		/* Verify the contracts. */
		EclipseVerifier.verify(contracts, verificationReport, vListener, jListener,
				this.verificationPolicy);
	}

	/*
	 * (non-Javadoc)
	 * @seenet.java.treaty.trigger.verification.AbstractTriggeredVerifier#
	 * performActionBeginVerification(java.net.URI, java.util.Set)
	 */
	protected void performActionBeginVerification(URI triggerType,
			Set<Contract> contracts) {

		try {
			for (URI actionType : EclipseActionRegistry.INSTANCE.getBeforeActions()) {
				EclipseActionRegistry.INSTANCE.before(triggerType, actionType,
						contracts);
			}
		}

		catch (TreatyException e) {
			Logger.error("Unexpected TreatyException during performing actions.", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @seenet.java.treaty.trigger.verification.AbstractTriggeredVerifier#
	 * performActionEndVerification(java.net.URI, java.util.Set, java.util.Set)
	 */
	protected void performActionEndVerification(URI triggerType,
			Set<VerificationReport> verificationReports) {

		try {
			for (URI actionType : EclipseActionRegistry.INSTANCE.getAfterActions()) {
				EclipseActionRegistry.INSTANCE.after(triggerType, actionType,
						verificationReports);
			}
		}

		catch (TreatyException e) {
			Logger.error("Unexpected TreatyException during performing actions.", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @seenet.java.treaty.trigger.verification.AbstractTriggeredVerifier#
	 * performActionVerificationOfContractFailed(java.net.URI,
	 * net.java.treaty.Contract, net.java.treaty.VerificationReport)
	 */
	protected void performActionVerificationOfContractFailed(URI triggerType,
			Contract contract, VerificationReport verificationReport) {

		try {
			for (URI actionType : EclipseActionRegistry.INSTANCE
					.getOnFailureActions(contract)) {
				EclipseActionRegistry.INSTANCE.perform(triggerType, actionType,
						verificationReport);
			}
		}

		catch (TreatyException e) {
			Logger.error("Unexpected TreatyException during performing actions.", e);
		}

		this.notifyListenersVerificationSucceeded(triggerType, contract);
	}

	/*
	 * (non-Javadoc)
	 * @seenet.java.treaty.trigger.verification.AbstractTriggeredVerifier#
	 * performActionVerificationOfContractSucceeded(java.net.URI,
	 * net.java.treaty.Contract, net.java.treaty.VerificationReport)
	 */
	protected void performActionVerificationOfContractSucceeded(URI triggerType,
			Contract contract, VerificationReport verificationReport) {

		try {
			for (URI actionType : EclipseActionRegistry.INSTANCE
					.getOnSuccessActions(contract)) {
				EclipseActionRegistry.INSTANCE.perform(triggerType, actionType,
						verificationReport);
			}
		}

		catch (TreatyException e) {
			Logger.error("Unexpected TreatyException during performing actions.", e);
		}

		this.notifyListenersVerificationSucceeded(triggerType, contract);
	}
}