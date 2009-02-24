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


/**
 * Contracts that are composed from sub contracts.
 * @author Jens Dietrich
 */

public class AggregatedContract extends PropertySupport implements Contract{
	
	private Contract[] parts = null;
	private Contract definition = null;




	public AggregatedContract(Contract... parts) {
		super();
		this.parts = parts;
	}

	public Contract getDefinition() {
		return definition;
	}

	public void setDefinition(Contract def) {
		this.definition = def;
	}

	public void accept(ContractVisitor visitor) {
		boolean f = visitor.visit(this);
		if (f) {
			for (Contract p:parts) {
				p.accept(visitor);
			}
		}
		visitor.endVisit(this);
		
	}

	/* (non-Javadoc)
	 * @see nz.ac.massey.treaty.Contract#bindConsumer(Connector,ResourceManager) 
	 */
	public Contract bindSupplier(Connector connector,ResourceManager loader) throws TreatyException {
		Contract[] boundParts = new Contract[parts.length];
		for (int i=0;i<parts.length;i++) {
			boundParts[i] = parts[i].bindSupplier(connector,loader);
		}
		Contract result = new AggregatedContract(boundParts).pack();
		result.setDefinition(this);
		return result;
	}

	/* (non-Javadoc)
	 * @see nz.ac.massey.treaty.Contract#bindConsumer(Connector,ResourceManager) 
	 */
	public Contract bindConsumer(Connector connector,ResourceManager loader) throws TreatyException{
		Contract[] boundParts = new Contract[parts.length];
		for (int i=0;i<parts.length;i++) {
			boundParts[i] = parts[i].bindConsumer(connector,loader);
		}
		Contract result = new AggregatedContract(boundParts).pack();
		result.setDefinition(this);
		return result;
	}

	/**
	 * Check this contract using a verifier. Add the results to the report.
	 * @param report
	 * @param verifier
	 * @param policy
	 * @return
	 */
	public boolean check(VerificationReport report,Verifier verifier,VerificationPolicy policy) {
		boolean result = true;
		for (Contract part:parts) {
			// TODO - now we hard coded & instead of && - this is slower
			// but gives us more info
			// this should be configurable
			result = result&part.check(report, verifier,policy);
		}
		return result;
	}


	public Connector getConsumer() {
		Connector c = null;
		for (Contract p:parts) {
			if (c!=null) {
				if (c!=p.getConsumer()) throw new IllegalStateException("All parts of a contract must have the same consumer");
			}
			else {
				c=p.getConsumer();
			}
		}
		return c;
	}

	public Connector getSupplier(){
		Connector c = null;
		for (Contract p:parts) {
			if (c!=null) {
				if (c!=p.getSupplier()) throw new IllegalStateException("All parts of a contract must have the same supplier");
			}
			else {
				c=p.getSupplier();
			}
		}
		return c;
	}

	public Contract[] getParts() {
		return parts;
	}
	
	public boolean isInstantiated() {
		for (Contract part:parts) {
			if (!part.isInstantiated()) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Convert the contract to an equivalent, more compact form.
	 * @return a contract
	 */
	public Contract pack() {
		if (parts.length==1) {
			return parts[0].pack();
		}
		Contract[] newParts = new Contract[parts.length];
		for (int i=0;i<parts.length;i++) {
			newParts[i]=parts[i].pack();
		}
		return new AggregatedContract(newParts);
	}
}