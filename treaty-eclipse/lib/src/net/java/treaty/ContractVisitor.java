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

import java.net.URI;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * Contract visitor. Implements the visitor pattern.
 * </p>
 * 
 * @author Jens Dietrich
 */
public interface ContractVisitor {

	/**
	 * <p>
	 * Ends a visit of a {@link ConjunctiveCondition}
	 * </p>
	 * 
	 * @param condition
	 *          The {@link ConjunctiveCondition} that has been visited.
	 */
	public void endVisit(ConjunctiveCondition condition);

	/**
	 * <p>
	 * Ends a visit of a {@link Contract}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} that has been visited.
	 */
	public void endVisit(Contract contract);

	/**
	 * <p>
	 * Ends a visit of a {@link DisjunctiveCondition}
	 * </p>
	 * 
	 * @param condition
	 *          The {@link DisjunctiveCondition} that has been visited.
	 */
	public void endVisit(DisjunctiveCondition condition);

	/**
	 * <p>
	 * Ends a visit of a {@link ExistsCondition}
	 * </p>
	 * 
	 * @param condition
	 *          The {@link ExistsCondition} that has been visited.
	 */
	public void endVisit(ExistsCondition condition);

	/**
	 * <p>
	 * Ends a visit of a {@link NegatedCondition}
	 * </p>
	 * 
	 * @param condition
	 *          The {@link NegatedCondition} that has been visited.
	 */
	public void endVisit(NegatedCondition condition);

	/**
	 * <p>
	 * Ends a visit of a {@link PropertyCondition}
	 * </p>
	 * 
	 * @param condition
	 *          The {@link PropertyCondition} that has been visited.
	 */
	public void endVisit(PropertyCondition condition);

	/**
	 * <p>
	 * Ends a visit of a {@link RelationshipCondition}
	 * </p>
	 * 
	 * @param condition
	 *          The {@link RelationshipCondition} that has been visited.
	 */
	public void endVisit(RelationshipCondition condition);

	/**
	 * <p>
	 * Ends a visit of a {@link Resource}.
	 * </p>
	 * 
	 * @param resource
	 *          The {@link Resource} that has been visited.
	 */
	public void endVisit(Resource resource);

	/**
	 * <p>
	 * Ends a visit of an {@link ExclusiveDisjunctiveCondition}
	 * </p>
	 * 
	 * @param condition
	 *          The {@link ExclusiveDisjunctiveCondition} that has been visited.
	 */
	public void endVisit(ExclusiveDisjunctiveCondition condition);

	/**
	 * <p>
	 * Ends a visit of a List of {@link Condition}s.
	 * </p>
	 * 
	 * @param conditions
	 *          The {@link Condition}s that have been visited.
	 */
	public void endVisitConditions(Collection<Condition> conditions);

	/**
	 * <p>
	 * Ends a visit of consumer {@link Resource}s.
	 * </p>
	 * 
	 * @param resources
	 *          The consumer {@link Resource}s that have been visited.
	 */
	public void endVisitConsumerResources(Collection<Resource> resources);

	/**
	 * <p>
	 * Ends a visit of external (legislator) {@link Resource}s.
	 * </p>
	 * 
	 * @param resources
	 *          The external {@link Resource}s that have been visited.
	 */
	public void endVisitExternalResources(Collection<Resource> resources);

	/**
	 * <p>
	 * Ends a visit of an OnFailureAction.
	 * </p>
	 * 
	 * @param uri
	 *          The URI of the OnFailureAction that has been visited.
	 */
	public void endVisitOnFailureAction(URI uri);

	/**
	 * <p>
	 * Ends a visit of an OnSuccessAction.
	 * </p>
	 * 
	 * @param uri
	 *          The URI of the OnSuccessAction that has been visited.
	 */
	public void endVisitOnSuccessAction(URI uri);

	/**
	 * <p>
	 * Ends a visit of supplier {@link Resource}s.
	 * </p>
	 * 
	 * @param resources
	 *          The supplier {@link Resource}s that have been visited.
	 */
	public void endVisitSupplierResources(Collection<Resource> resources);

	/**
	 * <p>
	 * Ends a visit of a Trigger.
	 * </p>
	 * 
	 * @param uri
	 *          The URI of the Trigger that has been visited.
	 */
	public void endVisitTrigger(URI uri);

	/**
	 * <p>
	 * Visits a {@link ConjunctiveCondition}.
	 * </p>
	 * 
	 * @param condition
	 *          The {@link ConjunctiveCondition} that shall be visited.
	 * @return <code>true</code> if children elements of the {@link ConjunctiveCondition}
	 *         shall be visited as well.
	 */
	public boolean visit(ConjunctiveCondition condition);

	/**
	 * <p>
	 * Visits a {@link Contract}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} that shall be visited.
	 * @return <code>true</code> if children elements of the {@link Contract}
	 *         shall be visited as well.
	 */
	public boolean visit(Contract contract);

	/**
	 * <p>
	 * Visits a {@link DisjunctiveCondition}.
	 * </p>
	 * 
	 * @param condition
	 *          The {@link DisjunctiveCondition} that shall be visited.
	 * @return <code>true</code> if children elements of the {@link DisjunctiveCondition}
	 *         shall be visited as well.
	 */
	public boolean visit(DisjunctiveCondition condition);

	/**
	 * <p>
	 * Visits a {@link ExistsCondition}.
	 * </p>
	 * 
	 * @param condition
	 *          The {@link ExistsCondition} that shall be visited.
	 * @return <code>true</code> if children elements of the
	 *         {@link ExistsCondition} shall be visited as well.
	 */
	public boolean visit(ExistsCondition condition);

	/**
	 * <p>
	 * Visits a {@link NegatedCondition}.
	 * </p>
	 * 
	 * @param condition
	 *          The {@link NegatedCondition} that shall be visited.
	 * @return <code>true</code> if children elements of the {@link NegatedCondition}
	 *         shall be visited as well.
	 */
	public boolean visit(NegatedCondition condition);

	/**
	 * <p>
	 * Visits a {@link PropertyCondition}.
	 * </p>
	 * 
	 * @param condition
	 *          The {@link PropertyCondition} that shall be visited.
	 * @return <code>true</code> if children elements of the
	 *         {@link PropertyCondition} shall be visited as well.
	 */
	public boolean visit(PropertyCondition condition);

	/**
	 * <p>
	 * Visits a {@link RelationshipCondition}.
	 * </p>
	 * 
	 * @param condition
	 *          The {@link RelationshipCondition} that shall be visited.
	 * @return <code>true</code> if children elements of the
	 *         {@link RelationshipCondition} shall be visited as well.
	 */
	public boolean visit(RelationshipCondition condition);

	/**
	 * <p>
	 * Visits a {@link Resource}.
	 * </p>
	 * 
	 * @param resource
	 *          The {@link Resource} that shall be visited.
	 * @return <code>true</code> if children elements of the {@link Resource}
	 *         shall be visited as well.
	 */
	public boolean visit(Resource resource);

	/**
	 * <p>
	 * Visits a {@link ExclusiveDisjunctiveCondition}.
	 * </p>
	 * 
	 * @param condition
	 *          The {@link ExclusiveDisjunctiveCondition} that shall be visited.
	 * @return <code>true</code> if children elements of the {@link ExclusiveDisjunctiveCondition}
	 *         shall be visited as well.
	 */
	public boolean visit(ExclusiveDisjunctiveCondition condition);

	/**
	 * <p>
	 * Visits a List of {@link Condition}s.
	 * </p>
	 * 
	 * @param conditions
	 *          The List of {@link Condition}s that shall be visited.
	 * @return <code>true</code> if children elements of the
	 *         {@link Condition}s shall be visited as well.
	 */
	public boolean visitConditions(List<Condition> conditions);

	/**
	 * <p>
	 * Visits the consumer {@link Resource}s.
	 * </p>
	 * 
	 * @param resources
	 *          The consumer {@link Resource}s that shall be visited.
	 * @return <code>true</code> if children elements of the {@link Resource}s
	 *         shall be visited as well.
	 */
	public boolean visitConsumerResources(Collection<Resource> resources);

	/**
	 * <p>
	 * Visits the external (legislator) {@link Resource}s.
	 * </p>
	 * 
	 * @param resources
	 *          The external (legislator) {@link Resource}s that shall be visited.
	 * @return <code>true</code> if children elements of the {@link Resource}s
	 *         shall be visited as well.
	 */
	public boolean visitExternalResources(Collection<Resource> resources);

	/**
	 * <p>
	 * Visits an OnFailureAction.
	 * </p>
	 * 
	 * @param uri
	 *          The {@link URI} of the OnFailureAction that shall be visited.
	 * @return <code>true</code> if children elements of the OnFailureAction shall
	 *         be visited as well.
	 */
	public boolean visitOnFailureAction(URI uri);

	/**
	 * <p>
	 * Visits an OnSuccesAction.
	 * </p>
	 * 
	 * @param uri
	 *          The {@link URI} of the OnSuccesAction that shall be visited.
	 * @return <code>true</code> if children elements of the OnSuccesAction shall
	 *         be visited as well.
	 */
	public boolean visitOnSuccessAction(URI uri);

	/**
	 * <p>
	 * Visits the supplier {@link Resource}s.
	 * </p>
	 * 
	 * @param resources
	 *          The supplier {@link Resource}s that shall be visited.
	 * @return <code>true</code> if children elements of the {@link Resource}s.
	 *         shall be visited as well.
	 */
	public boolean visitSupplierResources(Collection<Resource> resources);

	/**
	 * <p>
	 * Visits a Trigger.
	 * </p>
	 * 
	 * @param uri
	 *          The {@link URI} of the Trigger that shall be visited.
	 * @return <code>true</code> if children elements of the Trigger shall be
	 *         visited as well.
	 */
	public boolean visitTrigger(URI uri);
}