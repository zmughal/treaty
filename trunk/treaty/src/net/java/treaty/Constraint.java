/*
 * Copyright (C) 2009 Jens Dietrich
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
 * Interface for contracts and conditions.
 * </p>
 * 
 * @author Jens Dietrich
 */
public interface Constraint extends Visitable, Annotatable {

	/**
	 * <p>
	 * Checks the condition.
	 * </p>
	 * 
	 * @param report
	 *          A {@link VerificationReport} that can be used to log details.
	 * @param verifier
	 *          The {@link Verifier} that can be used to check atomic conditions.
	 * @param policy
	 *          The {@link VerificationPolicy} used.
	 * @return Whether the check succeeded or not.
	 */
	public abstract boolean check(VerificationReport report, Verifier verifier,
			VerificationPolicy policy);

	/**
	 * <p>
	 * Indicates whether all resources have been instantiated.
	 * </p>
	 * 
	 * @return <code>true</code> if all resources have been instantiated.
	 */
	public boolean isInstantiated();
}