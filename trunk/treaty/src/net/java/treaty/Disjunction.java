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

import java.util.Map;

/**
 * <p>
 * Disjunction (or).
 * </p>
 * 
 * @author Jens Dietrich
 */
public class Disjunction extends ComplexCondition {

	/**
	 * <p>
	 * Creates a new {@link Disjunction}.
	 * </p>
	 */
	public Disjunction() {

		super();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.AbstractCondition#replaceResources(java.util.Map)
	 */
	public AbstractCondition replaceResources(Map<String, Resource> resources) {

		Disjunction result = new Disjunction();

		for (AbstractCondition condition : this.parts) {
			result.addCondition(condition.replaceResources(resources));
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ComplexCondition#isInstantiated()
	 */
	@Override
	public boolean isInstantiated() {

		boolean result;

		if (this.parts == null) {
			result = false;
		}

		else if (this.parts.size() == 0) {
			result = true;
		}

		else {
			result = false;

			for (AbstractCondition part : this.parts) {

				if (part.isInstantiated()) {
					result = true;
					break;
				}
				// no else.
			}
			// end for.
		}
		// end else.

		return result;
	}

	public void accept(ContractVisitor visitor) {

		boolean f = visitor.visit(this);
		if (f) {
			for (AbstractCondition p : parts)
				p.accept(visitor);
		}
		visitor.endVisit(this);
	}

	public boolean check(VerificationReport report, Verifier verifier,
			VerificationPolicy policy) {

		if (policy == VerificationPolicy.DETAILED) {
			boolean result = false;
			for (AbstractCondition p : parts)
				result = result | p.check(report, verifier, policy);
			if (result)
				report.log(this, VerificationResult.SUCCESS);
			else
				report.log(this, VerificationResult.FAILURE,
						"no part of this condition is satisfied");
			return result;
		}
		else {
			boolean result = false;
			for (AbstractCondition p : parts)
				result = result || p.check(report, verifier, policy);
			if (result)
				report.log(this, VerificationResult.SUCCESS);
			else
				report.log(this, VerificationResult.FAILURE,
						"no part of this condition is satisfied");
			return result;
		}
	}

	/**
	 * Get the name of the logical connective used.
	 * 
	 * @return
	 */
	public String getConnective() {

		return "or";
	}
}