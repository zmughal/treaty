package test.net.java.treaty.script;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import net.java.treaty.AbstractCondition;
import net.java.treaty.Conjunction;
import net.java.treaty.Contract;
import net.java.treaty.ContractVisitor;
import net.java.treaty.Disjunction;
import net.java.treaty.ExistsCondition;
import net.java.treaty.Negation;
import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.XDisjunction;

public class ContractValidator implements ContractVisitor {

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
	public boolean visitConditions(List<AbstractCondition> conditions) {

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
	 * @see net.java.treaty.ContractVisitor#visitTrigger(java.net.URI)
	 */
	public boolean visitTrigger(URI uri) {

		return true;
	}
}