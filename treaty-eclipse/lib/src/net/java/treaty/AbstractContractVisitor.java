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

import java.net.URI;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * Useful superclass for implementing contract visitors.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class AbstractContractVisitor implements ContractVisitor {

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisitConditions(java.util.Collection)
	 */
	public void endVisitConditions(Collection<Condition> conditions) {
	
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Contract)
	 */
	public void endVisit(Contract contract) {

	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Disjunction)
	 */
	public void endVisit(DisjunctiveCondition condition) {
	
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Conjunction)
	 */
	public void endVisit(ConjunctiveCondition condition) {
	
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisit(net.java.treaty.ExistsCondition)
	 */
	public void endVisit(ExistsCondition condition) {
	
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Negation)
	 */
	public void endVisit(NegatedCondition condition) {
	
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisit(net.java.treaty.PropertyCondition)
	 */
	public void endVisit(PropertyCondition condition) {
	
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisit(net.java.treaty.RelationshipCondition
	 * )
	 */
	public void endVisit(RelationshipCondition relationshipCondition) {
	
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Resource)
	 */
	public void endVisit(Resource resource) {

	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.XDisjunction)
	 */
	public void endVisit(ExclusiveDisjunctiveCondition condition) {

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisitExtensionPointResources(java.util
	 * .Collection)
	 */
	public void endVisitConsumerResources(Collection<Resource> resources) {
	
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisitExternalResources(java.util.Collection
	 * )
	 */
	public void endVisitExternalResources(Collection<Resource> resources) {
	
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisitExtensionResources(java.util.Collection
	 * )
	 */
	public void endVisitSupplierResources(Collection<Resource> resources) {
	
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
	 * @see net.java.treaty.ContractVisitor#visitConditions(java.util.List)
	 */
	public boolean visitConditions(List<Condition> name) {
	
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Conjunction)
	 */
	public boolean visit(ConjunctiveCondition condition) {
	
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Contract)
	 */
	public boolean visit(Contract contract) {

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Disjunction)
	 */
	public boolean visit(DisjunctiveCondition condition) {
	
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.ExistsCondition)
	 */
	public boolean visit(ExistsCondition condition) {
	
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Negation)
	 */
	public boolean visit(NegatedCondition condition) {
	
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#visit(net.java.treaty.PropertyCondition)
	 */
	public boolean visit(PropertyCondition condition) {
	
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#visit(net.java.treaty.RelationshipCondition
	 * )
	 */
	public boolean visit(RelationshipCondition relationshipCondition) {
	
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Resource)
	 */
	public boolean visit(Resource resource) {

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.XDisjunction)
	 */
	public boolean visit(ExclusiveDisjunctiveCondition condition) {

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#visitExtensionPointResources(java.util.
	 * Collection)
	 */
	public boolean visitConsumerResources(Collection<Resource> resources) {
	
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#visitExternalResources(java.util.Collection
	 * )
	 */
	public boolean visitExternalResources(Collection<Resource> resources) {
	
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visitOnFailureAction(java.net.URI)
	 */
	public boolean visitOnFailureAction(URI uri) {

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visitOnSuccessAction(java.net.URI)
	 */
	public boolean visitOnSuccessAction(URI uri) {

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#visitExtensionResources(java.util.Collection
	 * )
	 */
	public boolean visitSupplierResources(Collection<Resource> resources) {
	
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visitTrigger(java.net.URI)
	 */
	public boolean visitTrigger(URI uri) {

		return true;
	}
}