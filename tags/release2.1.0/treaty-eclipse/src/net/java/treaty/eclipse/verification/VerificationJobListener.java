/*
 * Copyright (C) 2008-2009 Jens Dietrich
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
import net.java.treaty.VerificationReport;

/**
 * <p>
 * Interface for listener for verification.
 * </p>
 * 
 * @author Jens Dietrich
 */
public interface VerificationJobListener {

	public void verificationStatusChanged();

	/**
	 * <p>
	 * Informs the listener after the unsuccessful verification of one
	 * {@link Contract} triggered by a given trigger type (as a {@link URI}).
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract}s that has been verified.
	 * @param verificationReport
	 *          The {@link VerificationReport} of the {@link Contract}s
	 *          verification.
	 */
	public void verificationOfContractFailed(Contract contract,
			VerificationReport verificationReport);

	/**
	 * <p>
	 * Informs the listener after the successful verification of one
	 * {@link Contract} triggered by a given trigger type (as a {@link URI}).
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract}s that has been verified.
	 * @param verificationReport
	 *          The {@link VerificationReport} of the {@link Contract}s
	 *          verification.
	 */
	public void verificationOfContractSucceeded(Contract contract,
			VerificationReport verificationReport);
}