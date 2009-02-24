/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.contractmetrics;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.java.treaty.AbstractContractVisitor;
import net.java.treaty.Conjunction;
import net.java.treaty.Contract;
import net.java.treaty.ContractVisitor;
import net.java.treaty.Disjunction;
import net.java.treaty.ExistsCondition;
import net.java.treaty.Negation;
import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.SimpleContract;
import net.java.treaty.XDisjunction;



/**
 * Tool to extract metrics from contracts. 
 * @author Jens Dietrich
 */

public class MetricsTool {
	public int getDepth (Contract contract) {
		
		class DepthInspector extends AbstractContractVisitor {
			private int depth = -1;
			int maxdepth = 0;

			@Override
			public void endVisit(Conjunction condition) {
				this.depth = this.depth-1;
				super.endVisit(condition);
			}
			@Override
			public void endVisit(Disjunction condition) {
				this.depth = this.depth-1;
				super.endVisit(condition);
			}
			@Override
			public void endVisit(Negation condition) {
				this.depth = this.depth-1;
				super.endVisit(condition);
			}
			@Override
			public void endVisit(XDisjunction condition) {
				this.depth = this.depth-1;
				super.endVisit(condition);
			}
			@Override
			public boolean visit(Conjunction condition) {
				this.depth = this.depth+1;
				this.maxdepth = Math.max(this.maxdepth,this.depth);
				return super.visit(condition);
			}
			@Override
			public boolean visit(Disjunction condition) {
				this.depth = this.depth+1;
				this.maxdepth = Math.max(this.maxdepth,this.depth);
				return super.visit(condition);
			}

			@Override
			public boolean visit(Negation condition) {
				this.depth = this.depth+1;
				this.maxdepth = Math.max(this.maxdepth,this.depth);
				return super.visit(condition);
			}
			@Override
			public boolean visit(XDisjunction condition) {
				this.depth = this.depth+1;
				this.maxdepth = Math.max(this.maxdepth,this.depth);
				return super.visit(condition);
			}
			@Override
			public void endVisit(Contract contract) {
				this.depth = this.depth-1;
				if (hasImplicitConjunction(contract)) {
					this.depth = this.depth-1;
				}
				super.endVisit(contract);
			}
			@Override
			public boolean visit(Contract contract) {
				this.depth = this.depth+1;
				if (hasImplicitConjunction(contract)) {
					this.depth = this.depth+1;
				}
				this.maxdepth = Math.max(this.maxdepth,this.depth);
				return super.visit(contract);
			}
			private boolean hasImplicitConjunction(Contract contract) {
				return contract instanceof SimpleContract && ((SimpleContract)contract).getConstraints().size()>1;
			}
			
		};
		DepthInspector visitor = new DepthInspector();
		contract.accept(visitor);
		return visitor.maxdepth;
	} 
	
	public int getExistsConditionCount (Contract contract) {		
		class ExistsConditionCounter extends AbstractContractVisitor {
			private int counter = 0;
			@Override
			public boolean visit(ExistsCondition condition) {
				counter=counter+1;
				return super.visit(condition);
			}
		};
		ExistsConditionCounter visitor = new ExistsConditionCounter();
		contract.accept(visitor);
		return visitor.counter;
	} 
	
	public int getPropertyConditionCount (Contract contract) {		
		class PropertyConditionCounter extends AbstractContractVisitor {
			private int counter = 0;
			@Override
			public boolean visit(PropertyCondition condition) {
				counter=counter+1;
				return super.visit(condition);
			}
		};
		PropertyConditionCounter visitor = new PropertyConditionCounter();
		contract.accept(visitor);
		return visitor.counter;
	} 
	
	public int getRelationshipConditionCount (Contract contract) {		
		class RelationshipConditionCounter extends AbstractContractVisitor {
			private int counter = 0;
			@Override
			public boolean visit(RelationshipCondition condition) {
				counter=counter+1;
				return super.visit(condition);
			}
		};
		RelationshipConditionCounter visitor = new RelationshipConditionCounter();
		contract.accept(visitor);
		return visitor.counter;
	} 
	
	public int getDisjunctionCount (Contract contract) {		
		class DisjunctionCounter extends AbstractContractVisitor {
			private int counter = 0;
			@Override
			public boolean visit(Disjunction condition) {
				counter=counter+1;
				return super.visit(condition);
			}
		};
		DisjunctionCounter visitor = new DisjunctionCounter();
		contract.accept(visitor);
		return visitor.counter;
	} 
	
	public int getXDisjunctionCount (Contract contract) {		
		class XDisjunctionCounter extends AbstractContractVisitor {
			private int counter = 0;
			@Override
			public boolean visit(XDisjunction condition) {
				counter=counter+1;
				return super.visit(condition);
			}
		};
		XDisjunctionCounter visitor = new XDisjunctionCounter();
		contract.accept(visitor);
		return visitor.counter;
	} 
	
	public int getNegationCount (Contract contract) {		
		class NegationCounter extends AbstractContractVisitor {
			private int counter = 0;
			@Override
			public boolean visit(Negation condition) {
				counter=counter+1;
				return super.visit(condition);
			}
		};
		NegationCounter visitor = new NegationCounter();
		contract.accept(visitor);
		return visitor.counter;
	} 
	
	public int getTypeCount (Contract contract,final URI type) {		
		class TypeCounter extends AbstractContractVisitor {
			int counter = 0; 
			Set<Resource> counted = new HashSet<Resource>();
			private void count(Resource r) {
				if (r.getType().equals(type) && !counted.contains(r)) {
					counter=counter+1;
					counted.add(r);
				}				
			}
			@Override
			public boolean visit(ExistsCondition condition) {
				count(condition.getResource());
				return super.visit(condition);
			}
			@Override
			public boolean visit(PropertyCondition condition) {
				count(condition.getResource());
				return super.visit(condition);
			}
			@Override
			public boolean visit(RelationshipCondition condition) {
				count(condition.getResource1());
				count(condition.getResource2());
				return super.visit(condition);
			}
		};
		TypeCounter visitor = new TypeCounter();
		contract.accept(visitor);
		return visitor.counter;
	} 
	
	public int getRelationshipCount (Contract contract,final URI relationship) {		
		class RelationshipCounter extends AbstractContractVisitor {
			int counter = 0; 
			
			@Override
			public boolean visit(RelationshipCondition condition) {
				URI r = condition.getRelationship();
				if (r.equals(relationship)) {
					counter=counter+1;
				}
				return super.visit(condition);
			}
		};
		RelationshipCounter visitor = new RelationshipCounter();
		contract.accept(visitor);
		return visitor.counter;
	} 
	
	public void collectUsedTypes (Contract contract,final Set<URI> set) {
		class TypeFinder extends AbstractContractVisitor {
			@Override
			public boolean visit(ExistsCondition condition) {
				set.add(condition.getResource().getType());
				return super.visit(condition);
			}
			@Override
			public boolean visit(PropertyCondition condition) {
				set.add(condition.getResource().getType());
				return super.visit(condition);
			}
			@Override
			public boolean visit(RelationshipCondition condition) {
				set.add(condition.getResource1().getType());
				set.add(condition.getResource2().getType());
				return super.visit(condition);
			}
		};
		TypeFinder visitor = new TypeFinder();
		contract.accept(visitor);
	} 
	public void collectUsedRelationships (Contract contract,final Set<URI> set) {
		class RelationshipFinder extends AbstractContractVisitor {
			@Override
			public boolean visit(RelationshipCondition condition) {
				set.add(condition.getRelationship());
				return super.visit(condition);
			}
		};
		RelationshipFinder visitor = new RelationshipFinder();
		contract.accept(visitor);
	} 
	
}
