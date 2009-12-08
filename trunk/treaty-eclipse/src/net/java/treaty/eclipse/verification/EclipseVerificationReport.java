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

import static net.java.treaty.eclipse.Constants.VERIFICATION_RESULT;
import net.java.treaty.Annotatable;
import net.java.treaty.Contract;
import net.java.treaty.VerificationException;
import net.java.treaty.VerificationReport;
import net.java.treaty.VerificationResult;
import net.java.treaty.eclipse.Constants;

/**
 * <p>
 * A {@link VerificationReport} implementation for the Treaty Eclipse
 * implementation.
 * </p>
 * 
 * @author Claas Wilke
 */
public class EclipseVerificationReport implements VerificationReport {

	/** The {@link Contract} of this {@link EclipseVerificationReport}. */
	private Contract contract = null;

	/** The {@link VerificationResult} of this {@link VerificationReport}. */
	private VerificationResult verificationResult = VerificationResult.SUCCESS;

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.VerificationReport#getContract()
	 */
	public Contract getContract() {

		return this.contract;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.VerificationReport#getVerificationResult()
	 */
	public VerificationResult getVerificationResult() {

		return this.verificationResult;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.VerificationReport#log(java.lang.Object,
	 * net.java.treaty.VerificationResult, java.lang.String[])
	 */
	public void log(Object context, VerificationResult result, String... remarks) {

		if (context instanceof Annotatable) {
			((Annotatable) context).setProperty(VERIFICATION_RESULT, result);

			if (VerificationResult.FAILURE == result && remarks.length > 0) {
				((Annotatable) context).setProperty(Constants.VERIFICATION_EXCEPTION,
						new VerificationException(remarks[0]));
			}
			// no else.
		}
		// no else.

		this.verificationResult = result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.VerificationReport#setContract(net.java.treaty.Contract )
	 */
	public void setContract(Contract contract) {

		this.contract = contract;
	}
}