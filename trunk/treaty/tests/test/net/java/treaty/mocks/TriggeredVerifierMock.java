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

import java.net.URI;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.Resource;
import net.java.treaty.VerificationPolicy;
import net.java.treaty.VerificationReport;
import net.java.treaty.trigger.verification.AbstractTriggeredVerifier;

/**
 * <p>
 * A mock implementation of the {@link AbstractTriggeredVerifier} that verifies
 * {@link Contract}s without loading {@link Resource}s.
 * </p>
 * 
 * @author Claas Wilke
 */
public class TriggeredVerifierMock extends AbstractTriggeredVerifier {

	/*
	 * (non-Javadoc)
	 * @seenet.java.treaty.trigger.verification.AbstractTriggeredVerifier#
	 * performActionBeginVerification(java.net.URI, java.util.Set)
	 */
	protected void performActionBeginVerification(URI triggerType,
			Set<Contract> contracts) {

		/* Remains empty. */
	}

	/*
	 * (non-Javadoc)
	 * @seenet.java.treaty.trigger.verification.AbstractTriggeredVerifier#
	 * performActionEndVerification(java.net.URI, java.util.Set, java.util.Set)
	 */
	protected void performActionEndVerification(URI triggerType,
			Set<Contract> verfiedContracts, Set<Contract> failedContracts) {

		/* Remains empty. */
	}

	/*
	 * (non-Javadoc)
	 * @seenet.java.treaty.trigger.verification.AbstractTriggeredVerifier#
	 * performActionVerificationOfContractFailed(java.net.URI,
	 * net.java.treaty.Contract, net.java.treaty.VerificationReport)
	 */
	protected void performActionVerificationOfContractFailed(URI triggerType,
			Contract contract, VerificationReport verificationReport) {

		/* Remains empty. */
	}

	/*
	 * (non-Javadoc)
	 * @seenet.java.treaty.trigger.verification.AbstractTriggeredVerifier#
	 * performActionVerificationOfContractSucceeded(java.net.URI,
	 * net.java.treaty.Contract, net.java.treaty.VerificationReport)
	 */
	protected void performActionVerificationOfContractSucceeded(URI triggerType,
			Contract contract, VerificationReport verificationReport) {

		/* Remains empty. */
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.trigger.verification.AbstractTriggeredVerifier#verify(java
	 * .net.URI, java.util.Set)
	 */
	protected void verify(URI triggerType, Set<Contract> contracts) {

		for (Contract contract : contracts) {
			boolean result;
			result =
					contract.check(new VerificationReportMock(contract),
							new VerifierMock(), VerificationPolicy.FAST);

			if (result) {
				this.notifyListenersVerificationSucceeded(triggerType, contract);
			}

			else {
				this.notifyListenersVerificationFailed(triggerType, contract);
			}
		}
		// end for.
	}
}