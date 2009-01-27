/*
 * Copyright (C) 2008 Jens Dietrich
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
 * Negated condition
 * @author Jens Dietrich
 */

public class Negation extends AbstractCondition implements ConditionContext{
	private AbstractCondition negatedCondition = null;
	
	public Negation() {
		super();
	}
	
	public AbstractCondition replaceResources(Map<String,Resource> resources) {
		Negation c = new Negation();		
		c.setNegatedCondition(negatedCondition.replaceResources(resources));
		return c;
	}
	public void accept(ContractVisitor visitor) {
		boolean f = visitor.visit(this);
		if (f) {
			negatedCondition.accept(visitor);
		}
		visitor.endVisit(this);		
	}
	public boolean check(VerificationReport report,Verifier validator,VerificationPolicy policy) {
		boolean r = negatedCondition.check(report,validator,policy);
		if (!r)
			report.log(this,VerificationResult.SUCCESS);
		else 
			report.log(this,VerificationResult.FAILURE,"the negated part of the condition is satisfied");
		return !r;
	}
	/**
	 * Get the name of the logical connective used.
	 * @return
	 */
	public String getConnective() {
		return "not";
	}
	/**
	 * It is sufficient that one part is instantiated.
	 * This is useful if components do not supply resources necessary to 
	 * instantiate all parts.
	 */
	public boolean isInstantiated() {
		// this is to support "exists not" conditions 
		return negatedCondition instanceof ExistsCondition|| negatedCondition.isInstantiated();
	}
	public AbstractCondition getNegatedCondition() {
		return negatedCondition;
	}
	public void setNegatedCondition(AbstractCondition part) {
		this.negatedCondition = part;
	}
	public void addCondition(AbstractCondition c) {
		this.setNegatedCondition(c);
	}
}
