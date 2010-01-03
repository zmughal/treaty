/*
 * Copyright (C) 2008-2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty;

/**
 * <p>
 * The {@link VerificationReport} is used to collect information during
 * verification. It can then be used to visualize and store verification
 * results.
 * </p>
 * 
 * @author Jens Dietrich
 */
public interface VerificationReport {

	/**
	 * <p>
	 * Returns the {@link Contract} used for this {@link VerificationReport}.
	 * </p>
	 * 
	 * @return The {@link Contract} used for this {@link VerificationReport}.
	 */
	public Contract getContract();

	/**
	 * <p>
	 * Returns the {@link VerificationResult} of this {@link VerificationReport}.
	 * </p>
	 * 
	 * @return The {@link VerificationResult} of this {@link VerificationReport}.
	 */
	public VerificationResult getVerificationResult();

	/**
	 * <p>
	 * Logs an event during verification.
	 * </p>
	 * 
	 * @param context
	 *          The context ({@link Object}) of the logged event.
	 * @param result
	 *          The {@link VerificationResult} of the logged event.
	 * @param remarks
	 *          Further information as an array of {@link String}s.
	 */
	public void log(Object context, VerificationResult result, String... remarks);

	/**
	 * <p>
	 * Sets the {@link Contract} for this {@link VerificationReport}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} used for this {@link VerificationReport}.
	 */
	public void setContract(Contract contract);
}