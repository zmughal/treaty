/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.event.verification;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.ContractLogger;
import net.java.treaty.event.EventListener;

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

		for (Contract contract : contracts) {
			if (this.verify(contract)) {

				/* Propagate positive result. */
				ContractLogger.LOGGER.info("Contract " + contract
						+ " has been verified successfully.");

				for (TriggeredVerifierListener listener : this.listeners) {
					listener.verificationSucceeded(triggerType, contract);
				}
				// end for.
			}

			else {

				/* Propagate negative result. */
				ContractLogger.LOGGER.info("Verification of Contract " + contract
						+ " failed.");

				for (TriggeredVerifierListener listener : this.listeners) {
					listener.verificationFailed(triggerType, contract);
				}
				// end for.
			}
		}
		// end for.
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
	 * Verifies a given {@link Contract}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} that shall be verified.
	 * @return <code>true</code> if the {@link Contract} has been verified
	 *         successfully.
	 */
	abstract protected boolean verify(Contract contract);
}