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

import java.util.Collection;
import java.util.List;

/**
 * Useful superclass for implementing contract visitors.
 * @author Jens Dietrich
 */

public class AbstractContractVisitor implements ContractVisitor {

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
	public boolean visit(Contract contract) {
		return true;
	}

	@Override
	public boolean visit(Resource resource) {
		
		return true;
	}

	@Override
	public boolean visit(Conjunction condition) {
		
		return true;
	}

	@Override
	public boolean visit(Disjunction condition) {
		
		return true;
	}

	@Override
	public boolean visit(XDisjunction condition) {
		
		return true;
	}

	@Override
	public boolean visit(Negation condition) {
		
		return true;
	}

	@Override
	public boolean visit(RelationshipCondition relationshipCondition) {
		
		return true;
	}

	@Override
	public boolean visit(PropertyCondition condition) {
		
		return true;
	}

	@Override
	public boolean visit(ExistsCondition condition) {
		
		return true;
	}

	@Override
	public boolean visitConditions(List<AbstractCondition> name) {
		
		return true;
	}

	@Override
	public boolean visitExtensionPointResources(Collection<Resource> resources) {
		
		return true;
	}

	@Override
	public boolean visitExtensionResources(Collection<Resource> resources) {
		
		return true;
	}

	@Override
	public boolean visitExternalResources(Collection<Resource> resources) {
		
		return true;
	}

}
