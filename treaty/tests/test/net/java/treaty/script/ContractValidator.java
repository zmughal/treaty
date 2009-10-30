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

	@Override
	public void endVisit(Contract contract) {}

	@Override
	public void endVisit(Resource resource) {}

	@Override
	public void endVisit(Conjunction condition) {}

	@Override
	public void endVisit(Disjunction condition) {}

	@Override
	public void endVisit(XDisjunction condition) {}

	@Override
	public void endVisit(Negation condition) {}

	@Override
	public void endVisit(RelationshipCondition relationshipCondition) {}

	@Override
	public void endVisit(PropertyCondition condition) {}

	@Override
	public void endVisit(ExistsCondition condition) {}

	@Override
	public void endVisitConditions(Collection<AbstractCondition> conditions) {}

	@Override
	public void endVisitExtensionPointResources(Collection<Resource> resources) {}

	@Override
	public void endVisitExtensionResources(Collection<Resource> resources) {}

	@Override
	public void endVisitExternalResources(Collection<Resource> resources) {}

	@Override
	public void endVisitOnFailureAction(URI uri) {}

	@Override
	public void endVisitOnSuccessAction(URI uri) {}

	@Override
	public void endVisitTrigger(URI uri) {}

	@Override
	public boolean visit(Contract contract) { return true; }

	@Override
	public boolean visit(Resource resource) { return true; }

	@Override
	public boolean visit(Conjunction condition) { return true; }

	@Override
	public boolean visit(Disjunction condition) { return true; }

	@Override
	public boolean visit(XDisjunction condition) { return true; }

	@Override
	public boolean visit(Negation condition) { return true; }

	@Override
	public boolean visit(RelationshipCondition relationshipCondition) { return true; }

	@Override
	public boolean visit(PropertyCondition condition) { return true; }

	@Override
	public boolean visit(ExistsCondition condition) { return true; }

	@Override
	public boolean visitConditions(List<AbstractCondition> conditions) { return true; }

	@Override
	public boolean visitExtensionPointResources(Collection<Resource> resources) { return true; }

	@Override
	public boolean visitExtensionResources(Collection<Resource> resources) { return true; }

	@Override
	public boolean visitExternalResources(Collection<Resource> resources) { return true; }

	@Override
	public boolean visitOnFailureAction(URI uri) { return true; }

	@Override
	public boolean visitOnSuccessAction(URI uri) { return true; }
	
	@Override
	public boolean visitTrigger(URI uri) { return true; }

}
