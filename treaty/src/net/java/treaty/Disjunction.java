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
	 * @see net.java.treaty.Visitable#accept(net.java.treaty.ContractVisitor)
	 */
	public void accept(ContractVisitor visitor) {

		boolean willVisitChildren;
		willVisitChildren = visitor.visit(this);

		if (willVisitChildren) {

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
		result = false;

		if (policy == VerificationPolicy.DETAILED) {

			for (Condition condition : this.myParts) {
				result = result | condition.check(report, verifier, policy);
			}

			if (result) {
				report.log(this, VerificationResult.SUCCESS);
			}

			else {
				report.log(this, VerificationResult.FAILURE,
						"no part of this condition is satisfied");
			}
			// end else.
		}

		else {

			for (Condition condition : this.myParts) {
				result = result || condition.check(report, verifier, policy);
			}

			if (result) {
				report.log(this, VerificationResult.SUCCESS);
			}

			else {
				report.log(this, VerificationResult.FAILURE,
						"no part of this condition is satisfied");
			}
			// end else.
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ComplexCondition#getConnective()
	 */
	public String getConnective() {
	
		return "or";
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.AbstractCondition#replaceResources(java.util.Map)
	 */
	public Condition replaceResources(Map<String, Resource> resources) {

		Disjunction result = new Disjunction();

		for (Condition condition : this.myParts) {
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

		if (this.myParts == null) {
			result = false;
		}

		else if (this.myParts.size() == 0) {
			result = true;
		}

		else {
			result = false;

			for (Condition part : this.myParts) {

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
}