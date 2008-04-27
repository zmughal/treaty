/*
 * Copyright (C) 2008 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package nz.ac.massey.treaty;

import java.io.PrintStream;
import java.util.Collection;
import java.util.List;

/**
 * Utility to pretty print a contract.
 * @author <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</A>
 * @version 0.1 <27/04/2008>
 * @since 0.1
 */

public class ContractPrinter implements ContractVisitor {
	private PrintStream out = System.out;
	private int offset = 0;
	
	public ContractPrinter() {
		super();
	}
	public ContractPrinter(PrintStream out) {
		super();
		this.out = out;
	}
	
	public void print(Contract contract) {
		contract.accept(this);
	}
	
	public boolean visit(Contract contract) {
		out.println("A CONTRACT {");
		out.print("url=");
		out.print(contract.getLocation());
		ios();
		return true;
	}

	private void ios() {
		this.offset = offset+1;		
	}
	private void dos() {
		this.offset = offset-1;		
	}
	private void pos() {
		for (int i=0;i<offset;i++)	
			out.print(" ");
	}

	public boolean visit(Resource resource) {
		pos();
		out.print(resource.isResolved()?"":"?");
		out.print(resource.isResolved()?resource.getName():resource.getRef());
		out.print(":");
		out.println(resource.getType());
		return true;
	}

	public boolean visitExtensionResources(Collection<Resource> resources) {
		out.println("extension resources:");
		ios();
		return true;
	}

	public boolean visitExtensionPointResources(Collection<Resource> resources) {
		out.println("extension point resources:");
		ios();
		return true;
	}

	public boolean visitConditions(List<AbstractCondition> name) {
		out.println("conditions:");
		ios();
		return true;
	}

	public boolean visit(Conjunction condition) {
		out.println("and");
		ios();
		return true;
	}

	public boolean visit(Disjunction condition) {
		out.println("or");
		ios();
		return true;
	}

	public boolean visit(XDisjunction condition) {
		out.println("xor");
		ios();
		return true;
	}
	
	public boolean visit(Condition condition) {
		pos();
		out.println("condition");
		ios();
		pos();
		out.print("condition: ");
		out.println(condition.getRelationship());
		pos();
		out.print("resource1: ");
		visit(condition.getResource1());
		pos();
		out.print("resource2: ");
		visit(condition.getResource2());
		dos();
		return true;
	}

	public boolean visit(PropertyCondition condition) {	
		out.println(condition);
		return true;
	}

	public void endVisit(Contract contract) {
		dos();
		out.println("}");
	}

	public void endVisit(Resource resource) {
		
	}

	public void endVisitExtensionResources(Collection<Resource> resources) {
		dos();
	}

	public void endVisitExtensionPointResources(Collection<Resource> resources) {
		dos();
	}

	public void endVisit(Conjunction condition) {
		dos();
	}

	public void endVisit(Disjunction condition) {
		dos();
	} 
	
	public void endVisit(XDisjunction condition) {
		dos();
	}
	
	public void endVisit(Condition condition) {
		
	}

	public void endVisit(PropertyCondition condition) {
		
	}

	public void endVisitConditions(Collection<AbstractCondition> conditions) {
		dos();
	}



}
