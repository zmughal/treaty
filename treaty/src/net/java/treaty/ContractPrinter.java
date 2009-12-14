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

import java.io.PrintStream;
import java.net.URI;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * Utility to pretty print a contract.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class ContractPrinter implements ContractVisitor {

	/** The current offset of whitespaces used during printing. */
	private int offset = 0;
	/** The {@link PrintStream} used for output of the {@link Contract}. */
	private PrintStream out = System.out;

	/**
	 * <p>
	 * Creates a new {@link ContractPrinter}.
	 * </p>
	 */
	public ContractPrinter() {

		super();
	}

	/**
	 * <p>
	 * Creates a new {@link ContractPrinter} with a given {@link PrintStream} used
	 * for output.
	 * </p>
	 * 
	 * @param out
	 *          The {@link PrintStream} used for output.
	 */
	public ContractPrinter(PrintStream out) {

		super();
		this.out = out;
	}

	/**
	 * <p>
	 * Prints a given {@link Contract} using this {@link ContractPrinter}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} that shall be printed.
	 */
	public void print(Contract contract) {

		contract.accept(this);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Conjunction)
	 */
	public void endVisit(Conjunction condition) {

		this.decrementOffset();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Contract)
	 */
	public void endVisit(Contract contract) {

		this.decrementOffset();

		out.println("}");
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Disjunction)
	 */
	public void endVisit(Disjunction condition) {

		this.decrementOffset();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisit(net.java.treaty.ExistsCondition)
	 */
	public void endVisit(ExistsCondition condition) {

		/* Remains empty. */
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Negation)
	 */
	public void endVisit(Negation condition) {

		this.decrementOffset();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisit(net.java.treaty.PropertyCondition)
	 */
	public void endVisit(PropertyCondition condition) {

		/* Remains empty. */
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisit(net.java.treaty.RelationshipCondition
	 * )
	 */
	public void endVisit(RelationshipCondition relationshipCondition) {

		/* Remains empty. */
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Resource)
	 */
	public void endVisit(Resource resource) {

		/* Remains empty. */
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.XDisjunction)
	 */
	public void endVisit(XDisjunction condition) {

		this.decrementOffset();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisitConditions(java.util.Collection)
	 */
	public void endVisitConditions(Collection<Condition> conditions) {

		this.decrementOffset();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisitConsumerResources(java.util.Collection
	 * )
	 */
	public void endVisitConsumerResources(Collection<Resource> resources) {

		this.decrementOffset();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisitExternalResources(java.util.Collection
	 * )
	 */
	public void endVisitExternalResources(Collection<Resource> resources) {

		this.decrementOffset();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisitOnFailureAction(java.net.URI)
	 */
	public void endVisitOnFailureAction(URI uri) {

		/* Remains empty. */
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisitOnSuccessAction(java.net.URI)
	 */
	public void endVisitOnSuccessAction(URI uri) {

		/* Remains empty. */
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisitSupplierResources(java.util.Collection
	 * )
	 */
	public void endVisitSupplierResources(Collection<Resource> resources) {

		this.decrementOffset();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisitTrigger(java.net.URI)
	 */
	public void endVisitTrigger(URI uri) {

		/* Remains empty. */
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Contract)
	 */
	public boolean visit(Contract contract) {

		out.println("A CONTRACT {");
		out.print("url=");
		out.print(contract.getLocation());

		this.incrementOffset();

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Conjunction)
	 */
	public boolean visit(Conjunction condition) {

		out.println("and");

		this.incrementOffset();

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Disjunction)
	 */
	public boolean visit(Disjunction condition) {

		out.println("or");

		this.incrementOffset();

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.ExistsCondition)
	 */
	public boolean visit(ExistsCondition condition) {

		out.println(condition);

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Negation)
	 */
	public boolean visit(Negation condition) {

		out.println("not");

		this.incrementOffset();

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#visit(net.java.treaty.PropertyCondition)
	 */
	public boolean visit(PropertyCondition condition) {

		out.println(condition);

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#visit(net.java.treaty.RelationshipCondition
	 * )
	 */
	public boolean visit(RelationshipCondition relationshipCondition) {

		this.printOffeset();

		out.println("condition");

		this.incrementOffset();
		this.printOffeset();

		out.print("condition: ");
		out.println(relationshipCondition.getRelationship());

		this.printOffeset();

		out.print("resource1: ");
		this.visit(relationshipCondition.getResource1());

		this.printOffeset();

		out.print("resource2: ");
		this.visit(relationshipCondition.getResource2());

		this.decrementOffset();

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Resource)
	 */
	public boolean visit(Resource resource) {

		this.printOffeset();

		out.print(resource.isInstantiated() ? "" : "?");
		out.print(resource.isInstantiated() ? resource.getName() : resource
				.getRef());
		out.print(":");
		out.println(resource.getType());

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.XDisjunction)
	 */
	public boolean visit(XDisjunction condition) {

		out.println("xor");

		this.incrementOffset();

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visitConditions(java.util.List)
	 */
	public boolean visitConditions(List<Condition> name) {

		out.println("conditions:");

		this.incrementOffset();

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#visitConsumerResources(java.util.Collection
	 * )
	 */
	public boolean visitConsumerResources(Collection<Resource> resources) {

		out.println("extension point resources:");

		this.incrementOffset();

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#visitExternalResources(java.util.Collection
	 * )
	 */
	public boolean visitExternalResources(Collection<Resource> resources) {

		out.println("external resources:");

		this.incrementOffset();

		return true;
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
	 * @see
	 * net.java.treaty.ContractVisitor#visitSupplierResources(java.util.Collection
	 * )
	 */
	public boolean visitSupplierResources(Collection<Resource> resources) {

		out.println("extension resources:");

		incrementOffset();

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visitTrigger(java.net.URI)
	 */
	public boolean visitTrigger(URI uri) {

		out.println(uri);

		return false;
	}

	/**
	 * <p>
	 * Decrements the offset (used in a method to alter the offset width easily).
	 * </p>
	 */
	private void decrementOffset() {

		this.offset--;
	}

	/**
	 * <p>
	 * Increments the offset (used in a method to alter the offset width easily).
	 * </p>
	 */
	private void incrementOffset() {

		this.offset++;
	}

	/**
	 * <p>
	 * Prints the current offset of white spaces to the <code>out</code>
	 * {@link PrintStream}.
	 * </p>
	 */
	private void printOffeset() {

		for (int i = 0; i < this.offset; i++) {
			this.out.print(" ");
		}
		// end for.
	}
}