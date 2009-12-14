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

import net.java.treaty.Contract;

/**
 * <p>
 * A {@link TriggeredVerifierListener} is a listener of the
 * {@link AbstractTriggeredVerifier}.
 * </p>
 * 
 * @author Claas Wilke
 */
public interface TriggeredVerifierListener {

	/**
	 * <p>
	 * Informs the {@link TriggeredVerifierListener} that the verification of a
	 * given {@link Contract} caused by a given trigger type (a {@link URI})
	 * failed.
	 * </p>
	 * 
	 * @param triggerType
	 *          The {@link URI} of the Trigger that caused the verification.
	 * @param verificationReport
	 *          The {@link Contract} whose verification failed.
	 */
	public void verificationFailed(URI triggerType, Contract contract);

	/**
	 * <p>
	 * Informs the {@link TriggeredVerifierListener} that the verification of a
	 * given {@link Contract} caused by a given trigger type (a {@link URI})
	 * succeeded.
	 * </p>
	 * 
	 * @param triggerType
	 *          The {@link URI} of the Trigger that caused the verification.
	 * @param verificationReport
	 *          The {@link Contract} whose verification failed.
	 */
	public void verificationSucceeded(URI triggerType, Contract contract);
}