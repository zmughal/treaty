/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package test.net.java.treaty.mocks;

import net.java.treaty.Contract;
import net.java.treaty.VerificationReport;
import net.java.treaty.VerificationResult;

/**
 * <p>
 * A mock implementation of the interface {@link VerificationReport}.
 * </p>
 * 
 * @author Claas Wilke
 */
public class VerificationReportMock implements VerificationReport {

	/** The {@link Contract} of this {@link VerificationReportMock}. */
	private Contract contract;

	/**
	 * <p>
	 * Creates a new {@link VerificationReportMock}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} of this {@link VerificationReportMock}.
	 */
	public VerificationReportMock(Contract contract) {

		this.contract = contract;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.VerificationReport#getContract()
	 */
	public Contract getContract() {

		return this.contract;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.VerificationReport#log(java.lang.Object,
	 * net.java.treaty.VerificationResult, java.lang.String[])
	 */
	public void log(Object context, VerificationResult result, String... remarks) {

		/* Do nothing. */
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.VerificationReport#setContract(net.java.treaty.Contract)
	 */
	public void setContract(Contract contract) {

		this.contract = contract;
	}
}