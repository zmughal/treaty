/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package nz.ac.massey.treaty;

import nz.ac.massey.treaty.verification.ConditionVerifier;
import nz.ac.massey.treaty.verification.ResourceLoader;
import nz.ac.massey.treaty.verification.VerificationReport;
import nz.ac.massey.treaty.verification.VerificationResult;

import org.java.plugin.Plugin;
import org.java.plugin.registry.Extension;
import org.java.plugin.registry.ExtensionPoint;

/**
 * Represents a contract.
 * @author Jens Dietrich
 * @version 0.1 <27/04/2008>
 * @since 0.1
 */

public class Contract implements ConditionContext, Visitable {

	
	private String extensionPointId = null;
	private String pluginId = null;
	private String pluginId4Extension = null; // if the contract is instantiated
	private String contractLocation = null;
	
	private java.util.Map<String,Resource> extensionResources =  new java.util.LinkedHashMap<String,Resource>();
	private java.util.Map<String,Resource> extensionPointResources =  new java.util.LinkedHashMap<String,Resource>();
	private java.util.List<AbstractCondition> constraints = new java.util.ArrayList<AbstractCondition>();
	
	public Contract() {
		super();
	}	
	public Contract(String pluginId,String extensionPointId, String contractLocation) {
		super();
		this.extensionPointId = extensionPointId;
		this.contractLocation = contractLocation;
		this.pluginId = pluginId;
	}

	
	/* (non-Javadoc)
	 * @see nz.ac.massey.treaty.ConditionOwner#addCondition(nz.ac.massey.treaty.AbstractCondition)
	 */
	public void addCondition(AbstractCondition c) {
		this.constraints.add(c);
	}
	public void addExtensionResource(Resource r) throws InvalidContractException {
		this.checkId(r);
		r.setOwnedByExtensionpoint(false);
		this.extensionResources.put(r.getId(),r);
	}
	public void addExtensionPointResource(Resource r) throws InvalidContractException {
		this.checkId(r);
		if (!r.isResolved())
			throw new InvalidContractException();
		r.setOwnedByExtensionpoint(true);
		this.extensionPointResources.put(r.getId(),r);
	}
	public Resource getResource(String id) {
		Resource r = this.extensionPointResources.get(id);
		if (r==null) {
			r = this.extensionResources.get(id);
		}
		return r;
			
	}
	private void checkId(Resource r) throws InvalidContractException {
		if (this.extensionPointResources.containsKey(r.getId()))
			throw new InvalidContractException("A resource with this id is already registered as extension resource: " + r.getId());
		if (this.extensionResources.containsKey(r.getId()))
			throw new InvalidContractException("A resource with this id is already registered as extension point resource: " + r.getId());
	}
	java.util.List<AbstractCondition> getConstraints() {
		return constraints;
	}
	java.util.Collection<Resource> getExtensionPointResources() {
		return extensionPointResources.values();
	}
	java.util.Collection<Resource> getExtensionResources() {
		return extensionResources.values();
	}
	public void accept(ContractVisitor visitor) {
		boolean f = visitor.visit(this);
		if (f) {
			if (visitor.visitExtensionPointResources(this.extensionPointResources.values())) {
				for (Resource r:this.extensionPointResources.values()) {
					r.accept(visitor);
				}
				visitor.endVisitExtensionPointResources(this.extensionPointResources.values());
			}
			if (visitor.visitExtensionResources(this.extensionResources.values())) {
				for (Resource r:this.extensionResources.values()) {
					r.accept(visitor);
				}
				visitor.endVisitExtensionResources(this.extensionResources.values());
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
	public void loadExtensionPointResources(ExtensionPoint xp,Plugin plugin,ResourceLoader loader) throws InvalidContractException {
		for (Resource r:this.extensionPointResources.values())
			r.resolve(plugin,loader);
	}
	/**
	 * Instantiate this contract using an extension point.
	 * A new contract will be returned that has no variables. 
	 * @param extension
	 * @param plugin
	 * @param loader
	 * @return a new contract
	 * @throws InvalidContractException
	 */
	public Contract bind(Extension extension,Plugin plugin,ResourceLoader loader) throws InvalidContractException {
		Contract contract = new Contract();
		contract.setPluginId4Extension(plugin.getDescriptor().getId());
		contract.setLocation(this.getLocation());
		contract.setPluginId(this.getPluginId());
		contract.setExtensionPointId(this.getExtensionPointId());
		contract.extensionPointResources.putAll(this.extensionPointResources);
		for (Resource r:this.extensionResources.values()) {
			contract.addExtensionResource(r.bind(extension,plugin,loader));
		} 
		for (AbstractCondition c:this.constraints) {
			contract.addCondition(c.replaceResources(contract.extensionResources));
		}
		
		return contract;
	}
	/**
	 * Check this contract using a validator. Add the results to the report.
	 * @param report
	 * @param validator
	 * @return
	 */
	public boolean check(VerificationReport report,ConditionVerifier validator) {
		boolean result = true;
		for (AbstractCondition p:this.constraints) 
			result = result && p.check(report,validator); 
		if (result)
			report.log(this,VerificationResult.SUCCESS);
		else 
			report.log(this,VerificationResult.FAILURE,"some parts of this condition are not satisfied");
		return result;
	}
	public String getExtensionPointId() {
		return extensionPointId;
	}
	public void setExtensionPointId(String extensionPointId) {
		this.extensionPointId = extensionPointId;
	}
	public String getLocation() {
		return contractLocation;
	}
	public void setLocation(String location) {
		this.contractLocation = location;
	}
	public String getPluginId() {
		return pluginId;
	}
	public void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("aContract(pluginId=")
			.append(this.getPluginId())
			.append(",extensionpoint=")
			.append(this.getExtensionPointId())
			.append(",instantiated=")
			.append(this.isInstantiated()) 
			.append(')')
			.toString();
	}
	/**
	 * Check whether this contract is instantiated for some extension point.
	 * @return
	 */
	public boolean isInstantiated() {
		return getPluginId4Extension()!=null;
	}
	public String getPluginId4Extension() {
		return pluginId4Extension;
	}
	public void setPluginId4Extension(String pluginId4Extension) {
		this.pluginId4Extension = pluginId4Extension;
	}

}
