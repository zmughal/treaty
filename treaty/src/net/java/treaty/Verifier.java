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
 * Algorithm used to check conditions. Implementing classes provide the
 * semantics for relationships and are usually provided by extensions of the
 * validation extension point.
 * </p>
 * 
 * @author Jens Dietrich
 */
public interface Verifier {

	/**
	 * @deprecated This enumeration is deprecated. Use {@link VerificationPolicy}
	 *             instead.
	 * 
	 *             TODO Claas: Remove this enumeration when all dependencies have
	 *             been removed.
	 */
	public enum Policy {
		DETAILED, FAST
	}

	/**
	 * <p>
	 * Checks a {@link RelationshipCondition}.
	 * </p>
	 * 
	 * @param relationshipCondition
	 *          The {@link RelationshipCondition} that shall be checked.
	 * @throws VerificationException
	 *           Thrown only if the verification fails.
	 */
	public void check(RelationshipCondition relationshipCondition)
			throws VerificationException;

	/**
	 * <p>
	 * Checks a {@link PropertyCondition}.
	 * </p>
	 * 
	 * @param propertyCondition
	 *          The {@link PropertyCondition} that shall be checked.
	 * @throws VerificationException
	 *           Thrown only if the verification fails.
	 */
	public void check(PropertyCondition propertyCondition)
			throws VerificationException;

	/**
	 * <p>
	 * Checks an {@link ExistsCondition}.
	 * </p>
	 * 
	 * @param existsCondition
	 *          The {@link ExistsCondition} that shall be checked.
	 * @throws VerificationException
	 *           Thrown only if the verification fails.
	 */
	public void check(ExistsCondition existsCondition)
			throws VerificationException;
}