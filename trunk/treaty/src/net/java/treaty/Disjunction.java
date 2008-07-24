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

import net.java.treaty.verification.ConditionVerifier;
import net.java.treaty.verification.VerificationReport;
import net.java.treaty.verification.VerificationResult;

/**
 * Disjunction (or).
 * @author Jens Dietrich
 */

public class Disjunction extends ComplexCondition {

	public Disjunction() {
		super();
	}
	public  AbstractCondition replaceResources(Map<String,Resource> resources) {
		Disjunction c = new Disjunction();		
		for (AbstractCondition p:this.parts) 
			c.addCondition(p.replaceResources(resources));
		return c;
	}
	public void accept(ContractVisitor visitor) {
		boolean f = visitor.visit(this);
		if (f) {
			for (AbstractCondition p:parts) 
				p.accept(visitor);
		}
		visitor.endVisit(this);		
	}
	public boolean check(VerificationReport report,ConditionVerifier validator) {
		boolean result = false;
		for (AbstractCondition p:parts) 
			result = result || p.check(report,validator);
		if (result)
			report.log(this,VerificationResult.SUCCESS);
		else 
			report.log(this,VerificationResult.FAILURE,"no part of this condition is satisfied");
		return result;
	}
	
}