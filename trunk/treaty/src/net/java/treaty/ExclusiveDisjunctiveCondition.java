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
 * Exclusive disjunction (xor).
 * </p>
 * 
 * @author Jens Dietrich
 */
public class ExclusiveDisjunctiveCondition extends ComplexCondition {

	/**
	 * <p>
	 * Creates a new {@link ExclusiveDisjunctiveCondition}.
	 * </p>
	 */
	public ExclusiveDisjunctiveCondition() {

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

		int okCount;
		okCount = 0;

		/* Count the conditions that can be verified successfully. */
		for (Condition condition : this.myParts) {
			okCount = okCount + (condition.check(report, verifier, policy) ? 1 : 0);
		}
		// end for.

		/* Check if exactly one condition has been verified successfully. */
		if (okCount == 1) {
			report.log(this, VerificationResult.SUCCESS);
		}

		else if (okCount == 0) {
			report.log(this, VerificationResult.FAILURE,
					"No part of this condition is satisfied.");
		}

		else {
			report.log(this, VerificationResult.FAILURE,
					"Too many parts of this condition are satisfied.");
		}
		// end else.

		return okCount == 1;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ComplexCondition#getConnective()
	 */
	public String getConnective() {
	
		return "xor";
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.AbstractCondition#replaceResources(java.util.Map)
	 */
	public Condition replaceResources(Map<String, Resource> resources) {

		ExclusiveDisjunctiveCondition result = new ExclusiveDisjunctiveCondition();

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