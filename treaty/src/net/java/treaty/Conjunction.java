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
 * Conjunction (and) used in {@link Contract}'s conditions.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class Conjunction extends ComplexCondition {

	/**
	 * <p>
	 * Creates a new {@link Conjunction}.
	 * </p>
	 */
	public Conjunction() {

		super();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Constraint#check(net.java.treaty.VerificationReport,
	 * net.java.treaty.Verifier, net.java.treaty.VerificationPolicy)
	 */
	public boolean check(VerificationReport report, Verifier verifier,
			VerificationPolicy policy) {

		boolean result;

		/* FIXME Claas: No difference between if and else clause. */
		if (policy == VerificationPolicy.DETAILED) {

			result = true;

			for (AbstractCondition abstractCondition : this.parts) {
				result = result & abstractCondition.check(report, verifier, policy);
			}
			// end for.

			if (result) {
				report.log(this, VerificationResult.SUCCESS);
			}

			else {
				report.log(this, VerificationResult.FAILURE,
						"Some parts of this condition are not satisfied.");
			}
			// end else.
		}

		else {

			result = true;

			for (AbstractCondition abstractCondition : this.parts) {
				result = result & abstractCondition.check(report, verifier, policy);
			}
			// end for.

			if (result) {
				report.log(this, VerificationResult.SUCCESS);
			}

			else {
				report.log(this, VerificationResult.FAILURE,
						"Some parts of this condition are not satisfied.");
			}
			// end else.
		}
		// end else.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.AbstractCondition#replaceResources(java.util.Map)
	 */
	public AbstractCondition replaceResources(Map<String, Resource> resources) {

		Conjunction result = new Conjunction();

		for (AbstractCondition condition : this.parts) {
			result.addCondition(condition.replaceResources(resources));
		}

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

	/**
	 * Get the name of the logical connective used.
	 * 
	 * @return
	 */
	public String getConnective() {

		return "and";
	}

}
