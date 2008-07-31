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

import java.net.URL;

import net.java.treaty.verification.ConditionVerifier;
import net.java.treaty.verification.VerificationReport;
import net.java.treaty.verification.VerificationResult;


/**
 * Represents a simple contract between connectors (and therefore between components).
 * @author Jens Dietrich
 */

public class SimpleContract extends PropertySupport implements ConditionContext, Visitable, Contract {
	
	private java.util.Map<String,Resource> supplierResources =  new java.util.LinkedHashMap<String,Resource>();
	private java.util.Map<String,Resource> consumerResources =  new java.util.LinkedHashMap<String,Resource>();
	private java.util.List<AbstractCondition> constraints = new java.util.ArrayList<AbstractCondition>();
	private Connector consumer = null;
	private Connector supplier = null;
	private URL location = null; // the physical location of the contract
	
	public SimpleContract() {
		super();
	}	
	public SimpleContract(Connector consumer,Connector connector2,URL location) {
		super();
		this.location = location;
		assert(consumer.getType()==ConnectorType.CONSUMER);
		this.consumer = consumer;
		assert(consumer.getType()==ConnectorType.SUPPLIER);
		this.supplier = supplier;
		
	}

	
	/* (non-Javadoc)
	 * @see nz.ac.massey.treaty.ConditionOwner#addCondition(nz.ac.massey.treaty.AbstractCondition)
	 */
	public void addCondition(AbstractCondition c) {
		this.constraints.add(c);
	}
	public void addSupplierResource(Resource r) throws InvalidContractException {
		this.checkId(r);
		// if (!r.isResolved()) throw new InvalidContractException();
		r.setOwner(this.supplier);
		this.supplierResources.put(r.getId(),r);
	}
	public void addConsumerResource(Resource r) throws InvalidContractException {
		this.checkId(r);
		// if (!r.isResolved()) throw new InvalidContractException();
		r.setOwner(this.supplier);
		this.consumerResources.put(r.getId(),r);
	}
	public Resource getResource(String id) {
		Resource r = this.consumerResources.get(id);
		if (r==null) {
			r = this.supplierResources.get(id);
		}
		return r;
			
	}
	private void checkId(Resource r) throws InvalidContractException {
		if (this.consumerResources.containsKey(r.getId()))
			throw new InvalidContractException("A resource with this id is already registered as extension resource: " + r.getId());
		if (this.supplierResources.containsKey(r.getId()))
			throw new InvalidContractException("A resource with this id is already registered as extension point resource: " + r.getId());
	}
	public java.util.List<AbstractCondition> getConstraints() {
		return constraints;
	}
	public java.util.Collection<Resource> getConsumerResources() {
		return consumerResources.values();
	}
	public java.util.Collection<Resource> getSupplierResources() {
		return supplierResources.values();
	}
	/* (non-Javadoc)
	 * @see nz.ac.massey.treaty.IContract#accept(nz.ac.massey.treaty.ContractVisitor)
	 */
	public void accept(ContractVisitor visitor) {
		boolean f = visitor.visit(this);
		if (f) {
			if (visitor.visitExtensionPointResources(this.consumerResources.values())) {
				for (Resource r:this.consumerResources.values()) {
					r.accept(visitor);
				}
				visitor.endVisitExtensionPointResources(this.consumerResources.values());
			}
			if (visitor.visitExtensionResources(this.supplierResources.values())) {
				for (Resource r:this.supplierResources.values()) {
					r.accept(visitor);
				}
				visitor.endVisitExtensionResources(this.supplierResources.values());
			}
			if (visitor.visitConditions(this.constraints)) {
				for (AbstractCondition c:getConstraints()) {
					c.accept(visitor);
				}
				visitor.endVisitConditions(this.constraints);
			}
		}
		visitor.endVisit(this);
		
	}

	/* (non-Javadoc)
	 * @see nz.ac.massey.treaty.IContract#bindSupplier(nz.ac.massey.treaty.Connector, nz.ac.massey.treaty.verification.ResourceLoader)
	 */
	public Contract bindSupplier(Connector connector,ResourceManager loader) throws TreatyException {
		SimpleContract contract = new SimpleContract();
		contract.setLocation(this.getLocation());
		contract.consumerResources.putAll(this.consumerResources);
		for (Resource r:this.supplierResources.values()) {
			contract.addSupplierResource(r.instantiate(connector,loader));
		} 
		for (AbstractCondition c:this.constraints) {
			contract.addCondition(c.replaceResources(contract.supplierResources));
		}
		
		return contract;
	}
	/* (non-Javadoc)
	 * @see nz.ac.massey.treaty.IContract#bindConsumer(nz.ac.massey.treaty.Connector, nz.ac.massey.treaty.verification.ResourceLoader)
	 */
	public Contract bindConsumer(Connector connector,ResourceManager loader) throws TreatyException {
		SimpleContract contract = new SimpleContract();
		contract.setLocation(this.getLocation());
		contract.supplierResources.putAll(this.supplierResources);
		for (Resource r:this.consumerResources.values()) {
			contract.addConsumerResource(r.instantiate(connector,loader));
		} 
		for (AbstractCondition c:this.constraints) {
			contract.addCondition(c.replaceResources(contract.consumerResources));
		}
		
		return contract;
	}
	
	/* (non-Javadoc)
	 * @see nz.ac.massey.treaty.IContract#check(nz.ac.massey.treaty.verification.VerificationReport, nz.ac.massey.treaty.verification.ConditionVerifier)
	 */
	public boolean check(VerificationReport report,ConditionVerifier verifier) {
		boolean result = true;
		for (AbstractCondition p:this.constraints) 
			result = result && p.check(report,verifier); 
		if (result)
			report.log(this,VerificationResult.SUCCESS);
		else 
			report.log(this,VerificationResult.FAILURE,"some parts of this condition are not satisfied");
		return result;
	}



	/* (non-Javadoc)
	 * @see nz.ac.massey.treaty.IContract#getConsumer()
	 */
	public Connector getConsumer() {
		return consumer;
	}
	public void setConsumer(Connector consumer) {
		this.consumer = consumer;
	}
	/* (non-Javadoc)
	 * @see nz.ac.massey.treaty.IContract#getSupplier()
	 */
	public Connector getSupplier() {
		return supplier;
	}
	public void setSupplier(Connector supplier) {
		this.supplier = supplier;
	}
	/**
	 * Indicates whether the contract has unbound variables.
	 * If so, one of the bind methods must be used.
	 * @return a boolean
	 */
	public boolean isInstantiated() {
		for (Resource r:this.getSupplierResources()) {
			if (!r.isInstantiated()) {
				return false;
			}
		}
		for (Resource r:this.getConsumerResources()) {
			if (!r.isInstantiated()) {
				return false;
			}
		}
		return true;
	}
	public URL getLocation() {
		return location;
	}
	public void setLocation(URL location) {
		this.location = location;
	}
}
