/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse;

import net.java.treaty.Contract;

import org.eclipse.core.runtime.jobs.ISchedulingRule;

/**
 * Helper class used to synchronise verification jobs.
 * @author Jens Dietrich
 */


class  ContractVerificationSchedulingRule implements ISchedulingRule {
	private Contract contract = null;
	public ContractVerificationSchedulingRule(Contract contract) {
		super();
		this.contract = contract;
	}

	@Override
	public boolean contains(ISchedulingRule rule) {
		return this.equals(rule);
	}

	@Override
	public boolean isConflicting(ISchedulingRule rule) {
		return this.equals(rule);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contract == null) ? 0 : contract.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContractVerificationSchedulingRule other = (ContractVerificationSchedulingRule) obj;
		if (contract == null) {
			if (other.contract != null)
				return false;
		} else if (!contract.equals(other.contract))
			return false;
		return true;
	}
	
	
}