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

import net.java.treaty.verification.ConditionVerifier;
import net.java.treaty.verification.VerificationReport;
import net.java.treaty.verification.VerificationResult;

/**
 * A simple value condition. Like a data property in RDF. 
 * @author Jens Dietrich
 */


public class PropertyCondition extends AbstractCondition {

	private Resource resource = null;
	private URI property = null;
	private URI operator = null;
	private String value = null;
	public PropertyCondition() {
		super();
	}
	public URI getOperator() {
		return operator;
	}
	public void setOperator(URI operator) {
		this.operator = operator;
	}
	public URI getProperty() {
		return property;
	}
	public void setProperty(URI property) {
		this.property = property;
	}
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void accept(ContractVisitor visitor) {
		visitor.visit(this)	;	
	}
	
	public  AbstractCondition replaceResources(Map<String,Resource> resources) {
		PropertyCondition c = new PropertyCondition();
		c.setOperator(this.getOperator());
		c.setProperty(this.getProperty());
		c.setValue(this.getValue());
		
		if (!this.getResource().isResolved())
			c.setResource(resources.get(this.getResource().getId()));
		else
			c.setResource(this.getResource());
		
		return c;
	}
	public boolean check(VerificationReport report,ConditionVerifier validator) {
		report.log(this,VerificationResult.UNKNOWN,"the validation of property conditions is not yet supported");
		return true;
	}

}
