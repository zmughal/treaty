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

import java.io.PrintStream;
import java.net.URI;
import java.util.Collection;
import java.util.List;

/**
 * Utility to pretty print a contract.
 * 
 * @author Jens Dietrich
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

		this.offset = offset + 1;
	}

	private void dos() {

		this.offset = offset - 1;
	}

	private void pos() {

		for (int i = 0; i < offset; i++)
			out.print(" ");
	}

	public boolean visit(Resource resource) {

		pos();
		out.print(resource.isInstantiated() ? "" : "?");
		out.print(resource.isInstantiated() ? resource.getName() : resource
				.getRef());
		out.print(":");
		out.println(resource.getType());
		return true;
	}

	public boolean visitSupplierResources(Collection<Resource> resources) {

		out.println("extension resources:");
		ios();
		return true;
	}

	public boolean visitConsumerResources(Collection<Resource> resources) {

		out.println("extension point resources:");
		ios();
		return true;
	}

	public boolean visitExternalResources(Collection<Resource> resources) {

		out.println("external resources:");
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

	public boolean visit(Negation condition) {

		out.println("not");
		ios();
		return true;
	}

	public boolean visit(RelationshipCondition relationshipCondition) {

		pos();
		out.println("condition");
		ios();
		pos();
		out.print("condition: ");
		out.println(relationshipCondition.getRelationship());
		pos();
		out.print("resource1: ");
		visit(relationshipCondition.getResource1());
		pos();
		out.print("resource2: ");
		visit(relationshipCondition.getResource2());
		dos();
		return true;
	}

	public boolean visit(PropertyCondition condition) {

		out.println(condition);
		return true;
	}

	public boolean visit(ExistsCondition condition) {

		out.println(condition);
		return true;
	}

	public void endVisit(Contract contract) {

		dos();
		out.println("}");
	}

	public void endVisit(Resource resource) {

	}

	public void endVisitSupplierResources(Collection<Resource> resources) {

		dos();
	}

	public void endVisitExternalResources(Collection<Resource> resources) {

		dos();
	}

	public void endVisitConsumerResources(Collection<Resource> resources) {

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

	public void endVisit(Negation condition) {

		dos();
	}

	public void endVisit(RelationshipCondition relationshipCondition) {

	}

	public void endVisit(ExistsCondition condition) {

	}

	public void endVisit(PropertyCondition condition) {

	}

	public void endVisitConditions(Collection<AbstractCondition> conditions) {

		dos();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisitOnFailureAction(java.net.URI)
	 */
	public void endVisitOnFailureAction(URI uri) {

	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisitOnSuccessAction(java.net.URI)
	 */
	public void endVisitOnSuccessAction(URI uri) {

	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisitTrigger(java.net.URI)
	 */
	public void endVisitTrigger(URI uri) {

	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visitOnFailureAction(java.net.URI)
	 */
	public boolean visitOnFailureAction(URI uri) {

		out.println(uri);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visitOnSuccessAction(java.net.URI)
	 */
	public boolean visitOnSuccessAction(URI uri) {

		out.println(uri);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visitTrigger(java.net.URI)
	 */
	public boolean visitTrigger(URI uri) {

		out.println(uri);
		return false;
	}
}