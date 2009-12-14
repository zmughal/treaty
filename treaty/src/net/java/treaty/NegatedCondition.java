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
 * A Negated condition.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class NegatedCondition extends Condition implements ConditionContext {

	/** The {@link Condition} that is negated by this {@link NegatedCondition}. */
	private Condition myNegatedCondition = null;

	/**
	 * <p>
	 * Creates a new {@link NegatedCondition}.
	 * </p>
	 */
	public NegatedCondition() {

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
			this.myNegatedCondition.accept(visitor);
		}
		// no else.

		visitor.endVisit(this);
	}

	/**
	 * <p>
	 * Will set the {@link Condition} that is negated by this
	 * {@link NegatedCondition}.
	 * </p>
	 * 
	 * @see net.java.treaty.ConditionContext#addCondition(net.java.treaty.Condition
	 *      )
	 */
	public void addCondition(Condition condition) {

		this.setNegatedCondition(condition);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Constraint#check(net.java.treaty.VerificationReport,
	 * net.java.treaty.Verifier, net.java.treaty.VerificationPolicy)
	 */
	public boolean check(VerificationReport report, Verifier validator,
			VerificationPolicy policy) {

		boolean negatedResult;
		negatedResult = this.myNegatedCondition.check(report, validator, policy);

		if (!negatedResult) {
			report.log(this, VerificationResult.SUCCESS);
		}

		else {
			report.log(this, VerificationResult.FAILURE,
					"The negated part of the condition is not satisfied.");
		}

		return !negatedResult;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Constraint#isInstantiated()
	 */
	public boolean isInstantiated() {

		boolean result;

		if (this.myNegatedCondition == null) {
			result = false;
		}

		/*
		 * It is sufficient that one part is instantiated. This is useful if
		 * components do not supply resources necessary to instantiate all parts.
		 * This is to support "exists not" conditions.
		 */
		else {
			result =
					(myNegatedCondition instanceof ExistsCondition || myNegatedCondition
							.isInstantiated());
		}
		// end else.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.AbstractCondition#replaceResources(java.util.Map)
	 */
	public Condition replaceResources(Map<String, Resource> resources) {

		NegatedCondition result;

		result = new NegatedCondition();
		result.setNegatedCondition(this.myNegatedCondition
				.replaceResources(resources));

		return result;
	}

	/**
	 * <p>
	 * Returns the name of the logical connective used.
	 * </p>
	 * 
	 * @return The name of the logical connective used.
	 */
	public String getConnective() {

		return "not";
	}

	/**
	 * <p>
	 * Returns the {@link Condition} that is negated by this
	 * {@link NegatedCondition}.
	 * </p>
	 * 
	 * @return The {@link Condition} that is negated by this
	 *         {@link NegatedCondition}.
	 */
	public Condition getNegatedCondition() {

		return this.myNegatedCondition;
	}

	/**
	 * <p>
	 * Sets the {@link Condition} that is negated by this {@link NegatedCondition}
	 * .
	 * </p>
	 * 
	 * @param negatedCondition
	 *          The {@link Condition} that is negated by this
	 *          {@link NegatedCondition}.
	 */
	public void setNegatedCondition(Condition negatedCondition) {

		this.myNegatedCondition = negatedCondition;
	}
}