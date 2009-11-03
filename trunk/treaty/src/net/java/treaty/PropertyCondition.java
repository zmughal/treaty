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
 * A simple value condition. Like a data property in RDF. 
 * @author Jens Dietrich
 */


public class PropertyCondition extends AbstractCondition {

	private Resource resource = null;
	private URI operator = null;
	private Object value = null;
	public PropertyCondition() {
		super();
	}
	public URI getOperator() {
		return operator;
	}
	public void setOperator(URI operator) {
		this.operator = operator;
	}
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public void accept(ContractVisitor visitor) {
		boolean f = visitor.visit(this);
		if (f) {
			this.getResource().accept(visitor);
		}
		visitor.endVisit(this);
	}
	
	public  AbstractCondition replaceResources(Map<String,Resource> resources) {
		PropertyCondition c = new PropertyCondition();
		c.setOperator(this.getOperator());
		c.setValue(this.getValue());
		
		if (!this.getResource().isInstantiated())
			c.setResource(resources.get(this.getResource().getId()));
		else
			c.setResource(this.getResource());
		
		return c;
	}
	public boolean check(VerificationReport report,Verifier verifier,VerificationPolicy policy) {
		boolean result = true;
		if (!this.getResource().isProvided()) {
			report.log(this,VerificationResult.FAILURE,"Parameter " + this.getResource().getRef() + " is not provided for the first resource");
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
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("aPropertyCondition(");
		buf.append(' ')
			.append(this.operator.toString())
			.append(' ')
			.append(this.value);
		return buf.toString();
	}
	
}
