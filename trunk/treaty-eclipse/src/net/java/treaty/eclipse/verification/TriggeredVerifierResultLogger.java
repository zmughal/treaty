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

import net.java.treaty.Contract;
import net.java.treaty.eclipse.Logger;
import net.java.treaty.trigger.verification.TriggeredVerifierListener;

/**
 * <p>
 * Implements the {@link TriggeredVerifierListener} interface and listens to the
 * {@link TriggeredEclipseVerifier}. Loggs verification results.
 * </p>
 * 
 * @author Claas Wilke
 */
public class TriggeredVerifierResultLogger implements TriggeredVerifierListener {

	/** The singleton instance of the {@link TriggeredVerifierResultLogger}. */
	public static final TriggeredVerifierResultLogger INSTANCE =
			new TriggeredVerifierResultLogger();

	/**
	 * <p>
	 * Private constructor for singleton pattern.
	 * </p>
	 */
	private TriggeredVerifierResultLogger() {

		/* Remains empty. */
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.event.verification.TriggeredVerifierListener#verificationFailed
	 * (java.net.URI, net.java.treaty.Contract)
	 */
	public void verificationFailed(URI triggerType, Contract contract) {

		Logger.info("Verification of Contract " + contract + " cause by event "
				+ triggerType + " failed.");
	}

	/*
	 * (non-Javadoc)
	 * @seenet.java.treaty.event.verification.TriggeredVerifierListener#
	 * verificationSucceeded(java.net.URI, net.java.treaty.Contract)
	 */
	public void verificationSucceeded(URI triggerType, Contract contract) {

		Logger.info("Verification of Contract " + contract + " cause by event "
				+ triggerType + " succeeded.");
	}
}