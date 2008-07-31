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

import java.util.Collection;
import java.util.List;

/**
 * Contract visitor. Implements the visitor pattern.
 * @author Jens Dietrich
 */

public interface ContractVisitor {
	public boolean visit(Contract contract);
	public boolean visit(Resource resource);
	public boolean visitExtensionResources(Collection<Resource> resources);
	public boolean visitExtensionPointResources(Collection<Resource> resources);
	public boolean visitConditions(List<AbstractCondition> name);
	public boolean visit(Conjunction condition);
	public boolean visit(Disjunction condition);
	public boolean visit(XDisjunction condition);
	public boolean visit(RelationshipCondition relationshipCondition);
	public boolean visit(PropertyCondition condition);
	
	public void endVisit(Contract contract);
	public void endVisit(Resource resource);
	public void endVisitExtensionResources(Collection<Resource> resources);
	public void endVisitExtensionPointResources(Collection<Resource> resources);
	public void endVisit(Conjunction condition);
	public void endVisit(Disjunction condition);
	public void endVisit(XDisjunction condition);
	public void endVisit(RelationshipCondition relationshipCondition);
	public void endVisit(PropertyCondition condition);
	public void endVisitConditions(Collection<AbstractCondition> conditions);
}
