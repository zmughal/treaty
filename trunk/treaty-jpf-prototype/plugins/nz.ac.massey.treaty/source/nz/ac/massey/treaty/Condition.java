/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package nz.ac.massey.treaty;

import java.net.URI;
import java.util.Map;

import nz.ac.massey.treaty.verification.ConditionVerifier;
import nz.ac.massey.treaty.verification.VerificationException;
import nz.ac.massey.treaty.verification.VerificationReport;
import nz.ac.massey.treaty.verification.VerificationResult;

/**
 * Simple condition representing a binary predicate associating resources.
 * Example: aClass implements anInterface. Similar to OWL object properties.
 * @author Jens Dietrich
 * @version 0.1 <27/04/2008>
 * @since 0.1
 */

public class Condition extends AbstractCondition {

	private Resource resource1 = null;
	private Resource resource2 = null;
	private URI relationship = null;	
	public Condition() {
		super();
	}

	public URI getRelationship() {
		return relationship;
	}

	public void setRelationship(URI relationship) {
		this.relationship = relationship;
	}

	public Resource getResource1() {
		return resource1;
	}

	public void setResource1(Resource resource1) {
		this.resource1 = resource1;
	}

	public Resource getResource2() {
		return resource2;
	}

	public void setResource2(Resource resource2) {
		this.resource2 = resource2;
	}

	public void accept(ContractVisitor visitor) {
		boolean f = visitor.visit(this);
		if (f) {
			this.getResource1().accept(visitor);
			this.getResource2().accept(visitor);
		}
		visitor.endVisit(this);
				
	}
	public String toString() {
		return new StringBuffer().append("aCondition(")
			.append(this.relationship)
			.append('(')
			.append(this.resource1)
			.append(',')
			.append(this.resource2)
			.append("))")
			.toString();
	}
	/**
	 * Replace the resources referenced in conditions by the resources in the map.
	 * The original condition will not be changed, a new condition will be returned.
	 * @param resources a map of resources
	 * @return a condition
	 */
	public  AbstractCondition replaceResources(Map<String,Resource> resources) {
		Condition c = new Condition();
		c.setRelationship(this.getRelationship());
		
		if (!this.getResource1().isResolved())
			c.setResource1(resources.get(this.getResource1().getId()));
		else 
			c.setResource1(this.getResource1());
		
		if (!this.getResource2().isResolved())
			c.setResource2(resources.get(this.getResource2().getId()));
		else 
			c.setResource2(this.getResource2());
		
		return c;
	}
	
	public boolean check(VerificationReport report,ConditionVerifier validator) {
		boolean result = true;
		if (this.getResource1().isNotProvidedByExtension()) {
			report.log(this,VerificationResult.FAILURE,"Parameter " + this.getResource1().getRef() + " is not provided for the first resource");
			result = false;
		}
		if (this.getResource2().isNotProvidedByExtension()) {
			report.log(this,VerificationResult.FAILURE,"Parameter " + this.getResource2().getRef() + " is not provided for the second resource");
			result = false;
		}
		if (!result)
			return false;
		try {
			validator.check(this);
			report.log(this,VerificationResult.SUCCESS);
			return true;
		}
		catch (VerificationException x) {
			report.log(this,VerificationResult.FAILURE,x.getMessage());
			return false;
		}
		
	}
	

}
