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
import net.java.treaty.Resource;
import net.java.treaty.VerificationPolicy;
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
	 * @see
	 * net.java.treaty.event.verification.AbstractTriggeredVerifier#verify(net
	 * .java.treaty.Contract)
	 */
	protected boolean verify(Contract contract) {

		return contract.check(new VerificationReportMock(contract),
				new VerifierMock(), VerificationPolicy.FAST);
	}
}