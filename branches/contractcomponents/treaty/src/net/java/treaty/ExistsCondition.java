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

import java.net.URI;
import java.util.Map;


/**
 * Condition to check the existence and the type of mandatory resources.
 * This is useful for resources that must exist but are not referenced in other constraints.
 * @author Jens Dietrich
 */

public class ExistsCondition extends AbstractCondition {

	private Resource resource = null;

	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public void accept(ContractVisitor visitor) {
		boolean f = visitor.visit(this);
		if (f) {
			this.getResource().accept(visitor);
		}
		visitor.endVisit(this);
				
	}
	public String toString() {
		return new StringBuffer().append("aCondition(")
			.append(this.resource)
			.append(" exists)")
			.toString();
	}
	/**
	 * Replace the resources referenced in conditions by the resources in the map.
	 * The original condition will not be changed, a new condition will be returned.
	 * @param resources a map of resources
	 * @return a condition
	 */
	public  AbstractCondition replaceResources(Map<String,Resource> resources) {
		ExistsCondition c = new ExistsCondition();
		
		if (!this.getResource().isInstantiated())
			c.setResource(resources.get(this.getResource().getId()));
		else 
			c.setResource(this.getResource());
		
		return c;
	}
	
	public boolean check(VerificationReport report,Verifier verifier) {
		boolean result = true;
		if (!this.getResource().isProvided()) {
			report.log(this,VerificationResult.FAILURE,"Parameter " + this.getResource().getRef() + " is not provided for the resource");
			result = false;
		}
		if (!result)
			return false;
		try {
			verifier.check(this);
			report.log(this,VerificationResult.SUCCESS);
			return true;
		}
		catch (VerificationException x) {
			report.log(this,VerificationResult.FAILURE,x.getMessage());
			return false;
		}
		
	}
	
	public boolean isInstantiated() {
		return this.getResource().isInstantiated();
	}
	

}
