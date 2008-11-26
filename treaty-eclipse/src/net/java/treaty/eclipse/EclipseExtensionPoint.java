/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IExtensionPoint;
import net.java.treaty.ConnectorType;
import net.java.treaty.Contract;
import net.java.treaty.SimpleContract;

/**
 * Represents eclipse extension points. This is a wrapper class.
 * @author Jens Dietrich
 */


public class EclipseExtensionPoint extends EclipseConnector {
	
	private List<EclipseExtension> extensions = new ArrayList<EclipseExtension>();
	private IExtensionPoint extensionPoint = null;
	
	public void addExtension(EclipseExtension x) {
		this.extensions.add(x);
		x.setExtensionPoint(this);
	}
	
	public EclipseExtensionPoint(EclipsePlugin owner, IExtensionPoint xp) {
		super(owner);
		this.extensionPoint = xp;
		owner.addExtensionPoint(this);
	}
	
	public ConnectorType getType() {
		return ConnectorType.CONSUMER;
	}

	public List<EclipseExtension> getExtensions() {
		return extensions;
	}
	
	public boolean hasContracts() {
		if (this.getContract()!=null) {
			return true;
		}
		for (EclipseExtension x:this.getExtensions()) {
			if (x.getContract()!=null) {
				return true;
			}
		}
		return false;
	}

	public String getId() {
		return this.extensionPoint.getUniqueIdentifier();
	}
	
	protected void configureNewContract(SimpleContract newContract) {
		super.configureNewContract(newContract);
		newContract.setConsumer(this);
	}
	
	@Override
	public String toString() {
		return new StringBuffer()
			.append(this.getClass())
			.append('(')
			.append(this.extensionPoint==null?"?":this.extensionPoint.getUniqueIdentifier())
			.append(",defined in ")
			.append(this.getOwner())
			.append(')')
			.toString();
	}
	
}
