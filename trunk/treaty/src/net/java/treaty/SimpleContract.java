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
import java.util.ArrayList;
import java.util.List;



/**
 * Represents a simple contract between connectors (and therefore between components).
 * @author Jens Dietrich
 */

public class SimpleContract extends PropertySupport implements ConditionContext, Visitable, Contract {
	
	private java.util.Map<String,Resource> supplierResources =  new java.util.LinkedHashMap<String,Resource>();
	private java.util.Map<String,Resource> consumerResources =  new java.util.LinkedHashMap<String,Resource>();
	// these are other "external" resources, e.g. provided by additional contract plugins
	private java.util.Map<String,Resource> externalResources =  new java.util.LinkedHashMap<String,Resource>();
	private java.util.List<AbstractCondition> constraints = new java.util.ArrayList<AbstractCondition>();
	private Connector consumer = null;
	private Connector supplier = null;
	// the owner is used if a third party owns the contract
	private Connector owner = null;
	private URL location = null; // the physical location of the contract
	// contexts - they define how instantiation is done - this is mainly useful for supplier resources -
	// these are the resources that are usually instantiated
	private String consumerContext = null;
	// constants used to internally
	private static enum Role {
		SUPPLIER,CONSUMER,EXTERNAL
	}
	
	public String getConsumerContext() {
		return consumerContext;
	}
	public void setConsumerContext(String consumerContext) {
		this.consumerContext = consumerContext;
	}
	public String getSupplierContext() {
		return supplierContext;
	}
	public void setSupplierContext(String supplierContext) {
		this.supplierContext = supplierContext;
	}
	public String getExternalContext() {
		return externalContext;
	}
	public void setExternalContext(String externalContext) {
		this.externalContext = externalContext;
	}
	public void setSupplierResources(
			java.util.Map<String, Resource> supplierResources) {
		this.supplierResources = supplierResources;
	}
	public void setConsumerResources(
			java.util.Map<String, Resource> consumerResources) {
		this.consumerResources = consumerResources;
	}
	public void setExternalResources(
			java.util.Map<String, Resource> externalResources) {
		this.externalResources = externalResources;
	}
	private String supplierContext = null;
	private String externalContext = null;

	public Connector getOwner() {
		return owner;
	}
	public void setOwner(Connector owner) {
		this.owner = owner;
		for (Resource r:externalResources.values()) {
			r.setOwner(owner);
		}
	}
	
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
		r.setOwner(this.supplier);
		this.supplierResources.put(r.getId(),r);
	}
	public void addConsumerResource(Resource r) throws InvalidContractException {
		this.checkId(r);
		if (!r.isInstantiated()) throw new InvalidContractException("External resources cannot be variables");
		r.setOwner(this.consumer);
		this.consumerResources.put(r.getId(),r);
	}
	public void addExternalResource(Resource r) throws InvalidContractException {
		this.checkId(r);
		r.setOwner(owner);
		this.externalResources.put(r.getId(),r);
	}
    public java.util.Collection<Resource> getConsumerResources() {
        return consumerResources.values();
	}
	public java.util.Collection<Resource> getSupplierResources() {
	        return supplierResources.values();
	}
	public java.util.Collection<Resource> getExternalResources() {
        return externalResources.values();
	}

	public Resource getResource(String id) {
		Resource r = this.consumerResources.get(id);
		if (r==null) {
			r = this.supplierResources.get(id);
		}
		if (r==null) {
			r = this.externalResources.get(id);
		}
		return r;
			
	}
	private void checkId(Resource r) throws InvalidContractException {
		if (this.consumerResources.containsKey(r.getId()))
			throw new InvalidContractException("A resource with this id is already registered as extension resource: " + r.getId());
		else if (this.supplierResources.containsKey(r.getId()))
			throw new InvalidContractException("A resource with this id is already registered as extension point resource: " + r.getId());
		else if (this.externalResources.containsKey(r.getId()))
			throw new InvalidContractException("A resource with this id is already registered as external resource: " + r.getId());
	}
	public java.util.List<AbstractCondition> getConstraints() {
		return constraints;
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
			if (visitor.visitExternalResources(this.supplierResources.values())) {
				for (Resource r:this.externalResources.values()) {
					r.accept(visitor);
				}
				visitor.endVisitExternalResources(this.supplierResources.values());
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
		return bind(connector,loader,Role.SUPPLIER);
	}
	
	private Contract bind(Connector connector,ResourceManager loader, Role role) throws TreatyException {
		String contextDef = null;
		if (role==Role.CONSUMER) contextDef = this.getConsumerContext();
		else if (role==Role.SUPPLIER) contextDef = this.getSupplierContext();
		else if (role==Role.EXTERNAL) contextDef = this.getExternalContext();

		List<InstantiationContext> contexts = loader.getInstantiationContexts(connector,contextDef);
		List<SimpleContract> contracts = new ArrayList<SimpleContract>(contexts.size()); // list, one per context
		// built instantiated contracts
		for (InstantiationContext context:contexts) {
			SimpleContract contract = new SimpleContract();
			contracts.add(contract);
			contract.setConsumer(this.getConsumer());
			contract.setSupplier(connector);
			contract.setOwner(this.getOwner());
			contract.setLocation(this.getLocation());
			if (role==Role.CONSUMER) {
				for (Resource r:this.consumerResources.values()) {
					Resource instance = r.instantiate(connector,context,loader);
					contract.addConsumerResource(instance);
				} 
			}
			else {
				contract.consumerResources.putAll(this.consumerResources);
			}
			if (role==Role.EXTERNAL) {
				for (Resource r:this.externalResources.values()) {
					Resource instance = r.instantiate(connector,context,loader);
					contract.addExternalResource(instance);
				} 				
			}
			else {
				contract.externalResources.putAll(this.externalResources);
			}
			if (role==Role.SUPPLIER) {
				for (Resource r:this.supplierResources.values()) {
					Resource instance = r.instantiate(connector,context,loader);
					contract.addSupplierResource(instance);
				} 
			}
			else {
				contract.supplierResources.putAll(this.supplierResources);
			}
			
			for (AbstractCondition c:this.constraints) {
				contract.addCondition(c.replaceResources(contract.supplierResources));
			}
		}
		if (contracts.size()==1) {
			return contracts.get(0);
		}
		else {
			Contract[] parts = new Contract[contracts.size()];
			contracts.toArray(parts);
			return new AggregatedContract(parts);
		}

	}
	

	/* (non-Javadoc)
	 * @see nz.ac.massey.treaty.Contract#bindConsumer(nz.ac.massey.treaty.Connector, nz.ac.massey.treaty.verification.ResourceLoader)
	 */
	public Contract bindConsumer(Connector connector,ResourceManager loader) throws TreatyException {
		return bind(connector,loader,Role.CONSUMER);
	}
	
	/* (non-Javadoc)
	 * @see nz.ac.massey.treaty.Contract#check(nz.ac.massey.treaty.verification.VerificationReport, nz.ac.massey.treaty.verification.ConditionVerifier)
	 */
	public boolean check(VerificationReport report,Verifier verifier,VerificationPolicy policy) {
		report.setContract(this);
		boolean result = true;
		if (policy==VerificationPolicy.DETAILED) {
			for (AbstractCondition p:this.constraints) 
				result = result & p.check(report,verifier,policy); 
		}
		else {
			for (AbstractCondition p:this.constraints) 
				result = result && p.check(report,verifier,policy); 			
		}
		if (result)
			report.log(this,VerificationResult.SUCCESS);
		else 
			report.log(this,VerificationResult.FAILURE,"some parts of this condition are not satisfied");
		return result;
	}

	/**
	 * Convert the contract to an equivalent, more compact form.
	 * This is not yet fully recursive, it just removes top-level conjunction
	 * in order to flatten the structure.
	 * @return a contract
	 */
	public Contract pack() {
		boolean f = true;
		for (AbstractCondition c:constraints) {
			f = f && (c instanceof Conjunction);
		}
		if (f) {
			SimpleContract sc = new SimpleContract();
			sc.consumer = this.consumer;
			sc.supplier = this.supplier;
			sc.owner = this.owner;
			sc.consumerContext = this.consumerContext;
			sc.externalContext = this.externalContext;
			sc.supplierContext = this.supplierContext;
			sc.consumerResources = this.consumerResources;
			sc.supplierResources = this.supplierResources;
			sc.externalResources = this.externalResources;
			sc.constraints = new ArrayList<AbstractCondition>();
			for (AbstractCondition c:constraints) {
				Conjunction conj = (Conjunction)c;
				for (AbstractCondition cp:conj.getParts()) {
					sc.constraints.add(cp);
				}
			}
			return sc;
		}
		else {
			return this;
		}
	}

	/* (non-Javadoc)
	 * @see nz.ac.massey.treaty.IContract#getConsumer()
	 */
	public Connector getConsumer() {
		return consumer;
	}
	public void setConsumer(Connector consumer) {
		this.consumer = consumer;
		for (Resource r:consumerResources.values()) {
			r.setOwner(consumer);
		}
	}
	/* (non-Javadoc)
	 * @see nz.ac.massey.treaty.IContract#getSupplier()
	 */
	public Connector getSupplier() {
		return supplier;
	}
	public void setSupplier(Connector supplier) {
		this.supplier = supplier;
		for (Resource r:supplierResources.values()) {
			r.setOwner(supplier);
		}
	}
	/**
	 * Indicates whether the contract has unbound variables.
	 * If so, one of the bind methods must be used.
	 * @return a boolean
	 */
	public boolean isInstantiated() {
		for (AbstractCondition c:this.getConstraints()) {
			if (!c.isInstantiated()) {
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
	@Override
	public String toString() {
		if (this.location==null)
			return super.toString();
		else {
			return new StringBuffer()
				.append(super.toString())
				.append('(')
				.append(this.location)
				.append(')')
				.toString();			
		}
	}
	
	
}
