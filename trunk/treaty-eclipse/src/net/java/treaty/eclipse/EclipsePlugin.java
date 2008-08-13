package net.java.treaty.eclipse;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;

import net.java.treaty.Component;
import net.java.treaty.Connector;
import net.java.treaty.Contract;

/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

/**
 * Represents plugins. This is a wrapper class.
 * @author Jens Dietrich
 */

public class EclipsePlugin implements Component {

	private List<EclipseExtensionPoint> extensionPoints = new ArrayList<EclipseExtensionPoint>();
	private List<EclipseExtension> extensions = new ArrayList<EclipseExtension>();
	private Bundle bundle = null;
	
	public EclipsePlugin(Bundle bundle) {
		super();
		this.bundle = bundle;
	}

	void addExtension(EclipseExtension x) {
		this.extensions.add(x);
	}
	
	void addExtensionPoint(EclipseExtensionPoint xp) {
		this.extensionPoints.add(xp);
	}
	
	public String getId() {
		return this.bundle.getSymbolicName();
	}

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}
	
	public URL getResource(String adr) {
		return this.getBundle().getEntry(adr);
	}
	
	public boolean hasContracts() {
		for (EclipseExtensionPoint xp:this.extensionPoints) {
			if (xp.hasContracts()) return true;
		}
		return false;
	}

	public List<EclipseExtensionPoint> getExtensionPoints() {
		return extensionPoints;
	}

	public List<EclipseExtension> getExtensions() {
		return extensions;
	}
	public List<Connector> getConnectors() {
		List<Connector> connectors = new ArrayList<Connector>();
		connectors.addAll(this.getExtensionPoints());
		connectors.addAll(this.getExtensions());
		return connectors;
	}
	/**
	 * Get a list of instantiated contracts (instantiated by plugins providing extensions).
	 * @return a list
	 */
	public List<Contract> getInstantiatedContracts() {
		List<Contract> contracts = new ArrayList<Contract>();
		for (EclipseExtensionPoint xp:this.getExtensionPoints()) {
			for (EclipseExtension x:xp.getExtensions()) {
				Contract c = x.getContract();
				if (c!=null && c.isInstantiated()) {
					contracts.add(c);
				}
			}
		}
		return contracts;
	}
	


}
