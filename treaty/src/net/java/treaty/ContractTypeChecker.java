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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.java.treaty.action.ActionVocabulary;
import net.java.treaty.trigger.TriggerVocabulary;

/**
 * <p>
 * A {@link ContractTypeChecker} can be used to check that all actions and
 * triggers used in a {@link Contract} are defined in a {@link ActionVocabulary}
 * or {@link TriggerVocabulary} as well.
 * </p>
 * 
 * @author Claas Wilke
 */
public class ContractTypeChecker implements ContractVisitor {

	/**
	 * The {@link ActionVocabulary} used to check the availability of actions.
	 */
	private ActionVocabulary actionVocabulary;

	/** The current {@link Contract} that shall be checked. */
	private Contract currentContract;

	/**
	 * The {@link TriggerVocabulary} used to check the availability of triggers.
	 */
	private TriggerVocabulary triggerVocabulary;

	/**
	 * A set of {@link TreatyException} representing all warnings occurred during
	 * checking.
	 */
	private Set<TreatyException> warnings;

	/**
	 * <p>
	 * Creates a new {@link ContractTypeChecker}.
	 * </p>
	 * 
	 * @param actionVocabulary
	 *          The {@link ActionVocabulary} used to check the availability of
	 *          actions.
	 * @param triggerVocabulary
	 *          The {@link TriggerVocabulary} used to check the availability of
	 *          triggers.
	 */
	public ContractTypeChecker(ActionVocabulary actionVocabulary,
			TriggerVocabulary triggerVocabulary) {

		this.actionVocabulary = actionVocabulary;
		this.triggerVocabulary = triggerVocabulary;
	}

	/**
	 * <p>
	 * Checks a given {@link Collection} of {@link Contract}s. Meaning: checks if
	 * all used action and trigger types are available by the
	 * {@link ActionVocabulary} and {@link TriggerVocabulary} of this
	 * {@link ContractTypeChecker}.
	 * </p>
	 * 
	 * @param contracts
	 *          The {@link Set} of {@link Contract}s to be checked.
	 * @return A {@link Set} of {@link TreatyException} representing warnings
	 *         occurred during checking. If everything is okay, the {@link Set}
	 *         might be empty.
	 */
	public synchronized Set<TreatyException> checkContracts(
			Collection<Contract> contracts) {

		if (this.warnings == null) {
			this.warnings = new HashSet<TreatyException>();
		}

		else {
			this.warnings.clear();
		}

		for (Contract contract : contracts) {
			contract.accept(this);
		}
		// end for.

		return this.warnings;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Conjunction)
	 */
	public void endVisit(ConjunctiveCondition condition) {

		/* Do nothing on end. */
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Contract)
	 */
	public void endVisit(Contract contract) {

		/* Do nothing on end. */
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Disjunction)
	 */
	public void endVisit(DisjunctiveCondition condition) {

		/* Do nothing on end. */
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisit(net.java.treaty.ExistsCondition)
	 */
	public void endVisit(ExistsCondition condition) {

		/* Do nothing on end. */
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Negation)
	 */
	public void endVisit(NegatedCondition condition) {

		/* Do nothing on end. */
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisit(net.java.treaty.PropertyCondition)
	 */
	public void endVisit(PropertyCondition condition) {

		/* Do nothing on end. */
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisit(net.java.treaty.RelationshipCondition
	 * )
	 */
	public void endVisit(RelationshipCondition condition) {

		/* Do nothing on end. */
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Resource)
	 */
	public void endVisit(Resource resource) {

		/* Do nothing on end. */
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.XDisjunction)
	 */
	public void endVisit(ExclusiveDisjunctiveCondition condition) {

		/* Do nothing on end. */
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisitConditions(java.util.Collection)
	 */
	public void endVisitConditions(Collection<Condition> conditions) {

		/* Do nothing on end. */
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisitConsumerResources(java.util.Collection
	 * )
	 */
	public void endVisitConsumerResources(Collection<Resource> resources) {

		/* Do nothing on end. */
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisitExternalResources(java.util.Collection
	 * )
	 */
	public void endVisitExternalResources(Collection<Resource> resources) {

		/* Do nothing on end. */
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisitOnFailureAction(java.net.URI)
	 */
	public void endVisitOnFailureAction(URI uri) {

		/* Do nothing on end. */
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisitOnSuccessAction(java.net.URI)
	 */
	public void endVisitOnSuccessAction(URI uri) {

		/* Do nothing on end. */
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#endVisitSupplierResources(java.util.Collection
	 * )
	 */
	public void endVisitSupplierResources(Collection<Resource> resources) {

		/* Do nothing on end. */
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#endVisitTrigger(java.net.URI)
	 */
	public void endVisitTrigger(URI uri) {

		/* Do nothing on end. */
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Conjunction)
	 */
	public boolean visit(ConjunctiveCondition condition) {

		/* Do not visit conditions and their children. */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Contract)
	 */
	public boolean visit(Contract contract) {

		this.currentContract = contract;

		/* Visit some children. E.g., triggers. */
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Disjunction)
	 */
	public boolean visit(DisjunctiveCondition condition) {

		/* Do not visit Disjunctions and their children. */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.ExistsCondition)
	 */
	public boolean visit(ExistsCondition condition) {

		/* Do not visit ExistsConditions and their children. */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Negation)
	 */
	public boolean visit(NegatedCondition condition) {

		/* Do not visit Negations and their children. */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#visit(net.java.treaty.PropertyCondition)
	 */
	public boolean visit(PropertyCondition condition) {

		/* Do not visit PropertyConditions and their children. */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#visit(net.java.treaty.RelationshipCondition
	 * )
	 */
	public boolean visit(RelationshipCondition condition) {

		/* Do not visit RelationshipConditions and their children. */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Resource)
	 */
	public boolean visit(Resource resource) {

		/* Do not visit Resources and their children. */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.XDisjunction)
	 */
	public boolean visit(ExclusiveDisjunctiveCondition condition) {

		/* Do not visit XDisjunctions and their children. */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visitConditions(java.util.List)
	 */
	public boolean visitConditions(List<Condition> conditions) {

		/* Do not visit AbstractConditions and their children. */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#visitConsumerResources(java.util.Collection
	 * )
	 */
	public boolean visitConsumerResources(Collection<Resource> resources) {

		/* Do not visit consumer Resources and their children. */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#visitExternalResources(java.util.Collection
	 * )
	 */
	public boolean visitExternalResources(Collection<Resource> resources) {

		/* Do not visit external Resources and their children. */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visitOnFailureAction(java.net.URI)
	 */
	public boolean visitOnFailureAction(URI uri) {

		try {
			if (!this.actionVocabulary.getActions().contains(uri)) {
				this.warnings.add(new TreatyException("Action " + uri
						+ " used in Contract " + this.currentContract
						+ " as onFailure Action does not exist."));
			}
		}

		catch (TreatyException e) {

			this.warnings.add(new TreatyException(
					"Unexpected Exception during check of action " + uri, e));
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visitOnSuccessAction(java.net.URI)
	 */
	public boolean visitOnSuccessAction(URI uri) {

		try {
			if (!this.actionVocabulary.getActions().contains(uri)) {
				this.warnings.add(new TreatyException("Action " + uri
						+ " used in Contract " + this.currentContract
						+ " as onSuccess Action does not exist."));
			}
		}

		catch (TreatyException e) {

			this.warnings.add(new TreatyException(
					"Unexpected Exception during check of action " + uri, e));
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ContractVisitor#visitSupplierResources(java.util.Collection
	 * )
	 */
	public boolean visitSupplierResources(Collection<Resource> resources) {

		/* Do not visit supplier Resources and their children. */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractVisitor#visitTrigger(java.net.URI)
	 */
	public boolean visitTrigger(URI uri) {

		try {
			if (!this.triggerVocabulary.getTriggers().contains(uri)) {
				this.warnings
						.add(new TreatyException("Trigger " + uri + " used in Contract "
								+ this.currentContract + " does not exist."));
			}
		}

		catch (TreatyException e) {

			this.warnings.add(new TreatyException(
					"Unexpected Exception during check of trigger " + uri, e));
		}

		return true;
	}
}