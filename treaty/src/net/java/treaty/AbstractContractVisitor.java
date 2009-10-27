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
 * Useful superclass for implementing contract visitors.
 * 
 * @author Jens Dietrich
 */
public class AbstractContractVisitor implements ContractVisitor {

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Contract)
	 */
	public void endVisit(Contract contract) {

	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Resource)
	 */
	public void endVisit(Resource resource) {

	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Conjunction)
	 */
	public void endVisit(Conjunction condition) {

	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Disjunction)
	 */
	public void endVisit(Disjunction condition) {

	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.XDisjunction)
	 */
	public void endVisit(XDisjunction condition) {

	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Negation)
	 */
	public void endVisit(Negation condition) {

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
	 * @see
	 * net.java.treaty.ContractVisitor#endVisit(net.java.treaty.PropertyCondition)
	 */
	public void endVisit(PropertyCondition condition) {

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
	 * @see
	 * net.java.treaty.ContractVisitor#endVisitConditions(java.util.Collection)
	 */
	public void endVisitConditions(Collection<AbstractCondition> conditions) {

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisitExtensionPointResources(java.util
	 * .Collection)
	 */
	public void endVisitExtensionPointResources(Collection<Resource> resources) {

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisitExtensionResources(java.util.Collection
	 * )
	 */
	public void endVisitExtensionResources(Collection<Resource> resources) {

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
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Contract)
	 */
	public boolean visit(Contract contract) {

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
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Conjunction)
	 */
	public boolean visit(Conjunction condition) {

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Disjunction)
	 */
	public boolean visit(Disjunction condition) {

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.XDisjunction)
	 */
	public boolean visit(XDisjunction condition) {

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Negation)
	 */
	public boolean visit(Negation condition) {

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
	 * @see
	 * net.java.treaty.ContractVisitor#visit(net.java.treaty.PropertyCondition)
	 */
	public boolean visit(PropertyCondition condition) {

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
	 * @see net.java.treaty.ContractVisitor#visitConditions(java.util.List)
	 */
	public boolean visitConditions(List<AbstractCondition> name) {

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#visitExtensionPointResources(java.util.
	 * Collection)
	 */
	public boolean visitExtensionPointResources(Collection<Resource> resources) {

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#visitExtensionResources(java.util.Collection
	 * )
	 */
	public boolean visitExtensionResources(Collection<Resource> resources) {

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

	@Override
	public void endVisitOnFailureAction(URI uri) {
	}

	@Override
	public void endVisitOnSuccessAction(URI uri) {
	}

	@Override
	public void endVisitTrigger(URI uri) {
		
	}

	@Override
	public boolean visitOnFailureAction(URI uri) {
		return true;
	}

	@Override
	public boolean visitOnSuccessAction(URI uri) {
		return true;
	}

	@Override
	public boolean visitTrigger(URI uri) {
		return true;
	}
}