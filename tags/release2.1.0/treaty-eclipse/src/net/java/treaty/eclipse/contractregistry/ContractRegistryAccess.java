/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.contractregistry;

import org.eclipse.core.runtime.jobs.ISchedulingRule;

/**
 * <p>
 * An {@link ISchedulingRule} that describes access to the
 * {@link EclipseContractRegistry}.
 * </p>
 * 
 * @author Claas Wilke
 */
public class ContractRegistryAccess implements ISchedulingRule {

	/**
	 * Indicates whether or not this rule requires exclusive access to the
	 * {@link EclipseContractRegistry}.
	 */
	private boolean exclusiveAccess;

	/**
	 * <p>
	 * Creates a new {@link ContractRegistryAccess}.
	 * </p>
	 * 
	 * @param exclusiveAccess
	 *          Indicates whether or not this rule requires exclusive access to
	 *          the {@link EclipseContractRegistry}.
	 */
	public ContractRegistryAccess(boolean exclusiveAccess) {

		this.exclusiveAccess = exclusiveAccess;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.core.runtime.jobs.ISchedulingRule#contains(org.eclipse.core
	 * .runtime.jobs.ISchedulingRule)
	 */
	public boolean contains(ISchedulingRule rule) {

		boolean result;

		if (this == rule) {
			result = true;
		}

		else {
			result = false;
		}

		return result;
	}

	@Override
	public boolean isConflicting(ISchedulingRule rule) {

		boolean result;

		if (this == rule) {
			result = true;
		}

		else if (rule instanceof ContractRegistryAccess) {

			ContractRegistryAccess accessRule;
			accessRule = (ContractRegistryAccess) rule;

			result = this.exclusiveAccess || accessRule.exclusiveAccess;
		}

		else {
			result = false;
		}

		return result;
	}
}