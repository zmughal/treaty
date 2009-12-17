/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.trigger.verification;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.VerificationReport;
import net.java.treaty.trigger.EventListener;

/**
 * <p>
 * The {@link AbstractTriggeredVerifier} listens to {@link LifeCycleEvent}s and
 * verifies {@link Contract}s dynamically that are related to these
 * {@link LifeCycleEvent}s.
 * </p>
 * 
 * @author Claas Wilke
 */
public abstract class AbstractTriggeredVerifier implements EventListener {

	/**
	 * The {@link TriggeredVerifierListener}s of this
	 * {@link AbstractTriggeredVerifier}.
	 */
	private Set<TriggeredVerifierListener> listeners =
			new HashSet<TriggeredVerifierListener>();

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.event.EventListener#update(java.net.URI,
	 * java.util.Set)
	 */
	public void update(URI triggerType, Set<Contract> contracts) {

		this.verify(triggerType, contracts);
	}

	/**
	 * <p>
	 * Adds a {@link TriggeredVerifierListener} to this
	 * {@link AbstractTriggeredVerifier}.
	 * </p>
	 * 
	 * @param listener
	 *          The {@link TriggeredVerifierListener} to be added.
	 * @return <code>true</code> if the {@link TriggeredVerifierListener} has been
	 *         added.
	 */
	public boolean addListener(TriggeredVerifierListener listener) {

		return this.listeners.add(listener);
	}

	/**
	 * <p>
	 * Informs all registered {@link TriggeredVerifierListener} of this
	 * {@link AbstractTriggeredVerifier} that the verification of a given
	 * {@link Contract} caused by a given trigger has failed.
	 * </p>
	 * 
	 * @param triggerType
	 *          The {@link URI} of the trigger that shall be handled.
	 * @param contracts
	 *          All {@link Contract}s that must be verified after the caused
	 *          trigger.
	 */
	public void notifyListenersVerificationFailed(URI triggerType,
			Contract contract) {

		for (TriggeredVerifierListener listener : this.listeners) {
			listener.verificationFailed(triggerType, contract);
		}
		// end for.
	}

	/**
	 * <p>
	 * Informs all registered {@link TriggeredVerifierListener} of this
	 * {@link AbstractTriggeredVerifier} that the verification of a given
	 * {@link Contract} caused by a given trigger has succeeded.
	 * </p>
	 * 
	 * @param triggerType
	 *          The {@link URI} of the trigger that shall be handled.
	 * @param contracts
	 *          All {@link Contract}s that must be verified after the caused
	 *          trigger.
	 */
	public void notifyListenersVerificationSucceeded(URI triggerType,
			Contract contract) {

		for (TriggeredVerifierListener listener : this.listeners) {
			listener.verificationSucceeded(triggerType, contract);
		}
		// end for.
	}

	/**
	 * <p>
	 * Removed a {@link TriggeredVerifierListener} to this
	 * {@link AbstractTriggeredVerifier}.
	 * </p>
	 * 
	 * @param listener
	 *          The {@link TriggeredVerifierListener} to be removed.
	 * @return <code>true</code> if the {@link TriggeredVerifierListener} has been
	 *         removed.
	 */
	public boolean removeListener(TriggeredVerifierListener listener) {

		return this.listeners.remove(listener);
	}

	/**
	 * <p>
	 * Hook method to perform actions before the verification of a {@link Set} of
	 * {@link Contract}s triggered by a given trigger type (as a {@link URI}).
	 * </p>
	 * 
	 * @param triggerType
	 *          The {@link URI} of the trigger that shall be handled.
	 * @param contracts
	 *          All {@link Contract}s that must be verified after the caused
	 *          trigger.
	 */
	protected abstract void performActionBeginVerification(URI triggerType,
			Set<Contract> contracts);

	/**
	 * <p>
	 * Hook method to perform actions after the verification of a {@link Set} of
	 * {@link Contract}s triggered by a given trigger type (as a {@link URI}).
	 * </p>
	 * 
	 * @param triggerType
	 *          The {@link URI} of the trigger that shall be handled.
	 * @param verificationReports
	 *          The {@link VerificationReport}s of all {@link Contract}s that have
	 *          been verified.
	 */
	protected abstract void performActionEndVerification(URI triggerType,
			Set<VerificationReport> verificationReports);

	/**
	 * <p>
	 * Hook method to perform actions after the unsuccessful verification of one
	 * {@link Contract} triggered by a given trigger type (as a {@link URI}).
	 * </p>
	 * 
	 * @param triggerType
	 *          The {@link URI} of the trigger that shall be handled.
	 * @param contract
	 *          The {@link Contract}s that has been verified.
	 * @param verificationReport
	 *          The {@link VerificationReport} of the {@link Contract}s
	 *          verification.
	 */
	protected abstract void performActionVerificationOfContractFailed(
			URI triggerType, Contract contract, VerificationReport verificationReport);

	/**
	 * <p>
	 * Hook method to perform actions after the successful verification of one
	 * {@link Contract} triggered by a given trigger type (as a {@link URI}).
	 * </p>
	 * 
	 * @param triggerType
	 *          The {@link URI} of the trigger that shall be handled.
	 * @param contract
	 *          The {@link Contract}s that has been verified.
	 * @param verificationReport
	 *          The {@link VerificationReport} of the {@link Contract}s
	 *          verification.
	 */
	protected abstract void performActionVerificationOfContractSucceeded(
			URI triggerType, Contract contract, VerificationReport verificationReport);

	/**
	 * <p>
	 * Verifies a given {@link Set} of {@link Contract}s cause by a given trigger
	 * type (as a {@link URI}).
	 * </p>
	 * 
	 * @param triggerType
	 *          The {@link URI} of the trigger that shall be handled.
	 * @param contracts
	 *          All {@link Contract}s that must be verified after the caused
	 *          trigger.
	 */
	abstract protected void verify(URI triggerType, Set<Contract> contracts);
}