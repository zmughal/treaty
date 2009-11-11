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
 * A Negated condition.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class Negation extends AbstractCondition implements ConditionContext {

	/** The {@link AbstractCondition} that is negated by this {@link Negation}. */
	private AbstractCondition myNegatedCondition = null;

	/**
	 * <p>
	 * Creates a new {@link Negation}.
	 * </p>
	 */
	public Negation() {

		super();
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
	public AbstractCondition replaceResources(Map<String, Resource> resources) {

		Negation result;

		result = new Negation();
		result.setNegatedCondition(this.myNegatedCondition
				.replaceResources(resources));

		return result;
	}

	public void accept(ContractVisitor visitor) {

		boolean f = visitor.visit(this);
		if (f) {
			myNegatedCondition.accept(visitor);
		}
		visitor.endVisit(this);
	}

	public boolean check(VerificationReport report, Verifier validator,
			VerificationPolicy policy) {

		boolean r = myNegatedCondition.check(report, validator, policy);
		if (!r)
			report.log(this, VerificationResult.SUCCESS);
		else
			report.log(this, VerificationResult.FAILURE,
					"the negated part of the condition is satisfied");
		return !r;
	}

	/**
	 * Get the name of the logical connective used.
	 * 
	 * @return
	 */
	public String getConnective() {

		return "not";
	}

	public AbstractCondition getNegatedCondition() {

		return myNegatedCondition;
	}

	public void setNegatedCondition(AbstractCondition part) {

		this.myNegatedCondition = part;
	}

	public void addCondition(AbstractCondition c) {

		this.setNegatedCondition(c);
	}
}
