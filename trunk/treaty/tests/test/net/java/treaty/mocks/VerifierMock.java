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

import net.java.treaty.ExistsCondition;
import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.VerificationException;
import net.java.treaty.Verifier;

/**
 * <p>
 * A mock implementation of the {@link Verifier} interface.
 * </p>
 * 
 * @author Claas Wilke
 */
public class VerifierMock implements Verifier {

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.RelationshipCondition)
	 */
	public void check(RelationshipCondition relationshipCondition)
			throws VerificationException {

		throw new VerificationException(
				"VeriferMock does not support RelationshipConditions.");
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.PropertyCondition)
	 */
	public void check(PropertyCondition propertyCondition)
			throws VerificationException {

		throw new VerificationException(
				"VeriferMock does not support PropertyConditions.");
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.ExistsCondition)
	 */
	public void check(ExistsCondition existsCondition)
			throws VerificationException {

		throw new VerificationException(
				"VeriferMock does not support ExistsConditions.");
	}
}