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
	 * @see net.java.treaty.Visitable#accept(net.java.treaty.ContractVisitor)
	 */
	public void accept(ContractVisitor visitor) {

		boolean visitSuccessfull;
		visitSuccessfull = visitor.visit(this);

		/* Probably visit all parts as well. */
		if (visitSuccessfull) {

			for (Condition condition : this.myParts) {
				condition.accept(visitor);
			}
			// end for.
		}
		// no else.

		visitor.endVisit(this);
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

			for (Condition abstractCondition : this.myParts) {
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

			for (Condition abstractCondition : this.myParts) {
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
	 * @see net.java.treaty.ComplexCondition#getConnective()
	 */
	public String getConnective() {
	
		return "and";
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.AbstractCondition#replaceResources(java.util.Map)
	 */
	public Condition replaceResources(Map<String, Resource> resources) {

		Conjunction result;
		result = new Conjunction();

		for (Condition condition : this.myParts) {
			result.addCondition(condition.replaceResources(resources));
		}
		// end for.

		return result;
	}
}