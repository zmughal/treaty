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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.java.treaty.Contract;
import net.java.treaty.SimpleContract;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.osgi.framework.Bundle;
import org.semanticweb.owl.model.OWLOntology;

/**
 * Utility class that extracts plugins with contracts.
 * @author Jens Dietrich
 */

class Builder {
	public Collection<EclipsePlugin> extractContracts() {
		
		Map<String,EclipsePlugin> plugins = new HashMap<String,EclipsePlugin>();
		Map<String,EclipseExtensionPoint> extensionPoints = new HashMap<String,EclipseExtensionPoint>();
		Map<String,EclipseExtension> extensions = new HashMap<String,EclipseExtension>();
		
		IExtensionRegistry registry = org.eclipse.core.runtime.Platform.getExtensionRegistry();
		IExtensionPoint[] xpoints = registry.getExtensionPoints();
		for (IExtensionPoint xpoint:xpoints) {
			IContributor c = xpoint.getContributor();
			Bundle b = org.eclipse.core.runtime.Platform.getBundle(c.getName());
			plugins.put(b.getSymbolicName(),new EclipsePlugin(b));
			// also register the plugins with extension points
			for (IExtension x:xpoint.getExtensions()) {
				c = x.getContributor();
				b = org.eclipse.core.runtime.Platform.getBundle(c.getName());
				plugins.put(b.getSymbolicName(),new EclipsePlugin(b));				
			}
		}
		for (IExtensionPoint xpoint:xpoints) {
			IContributor c = xpoint.getContributor();
			Bundle b = org.eclipse.core.runtime.Platform.getBundle(c.getName());
			EclipsePlugin tplugin = plugins.get(b.getSymbolicName());
			EclipseExtensionPoint txp = new EclipseExtensionPoint(tplugin,xpoint);
			extensionPoints.put(xpoint.getUniqueIdentifier(), txp);
			String contractName = "/META-INF/"+txp.getId()+".contract";
			URL contractURL = tplugin.getResource(contractName);
			if (contractURL!=null) {
				txp.addContract(contractURL,null);
			}
			for (IExtension x:xpoint.getExtensions()) {
				c = x.getContributor();
				b = org.eclipse.core.runtime.Platform.getBundle(c.getName());
				tplugin = plugins.get(b.getSymbolicName());
				EclipseExtension tx = new EclipseExtension(tplugin,x);
				txp.addExtension(tx);
				extensions.put(tx.getId(),tx);
			}					
		}	
		
		// look external contracts supplied by third parties
		IExtensionPoint contractXP = registry.getExtensionPoint("net.java.treaty.eclipse.contract");
		for (IExtension contractExtension:contractXP.getExtensions()) {
			String pluginId = contractExtension.getContributor().getName();
			try {
				IConfigurationElement[] attributes = contractExtension.getConfigurationElements();
				for (int j=0;j<attributes.length;j++) {
					IConfigurationElement p = attributes[j];
					String loc = p.getAttribute("location");	
					Bundle bundle = org.eclipse.core.runtime.Platform.getBundle(pluginId);
					if (bundle!=null && loc!=null) {
						URL url = bundle.getEntry(loc);
						if (url==null) {
							Logger.warn("No contract found for location "+url);
						}
						else {
							// check naming pattern
							String xpName = loc.substring(0,loc.length()-".contract".length());
							EclipseExtensionPoint xp = extensionPoints.get(xpName);
							if (xp==null) {
								Logger.warn("No extension point found for contract "+loc + " defined in " + bundle.getSymbolicName());
							}
							else {
								EclipseExtension x = extensions.get(contractExtension.getUniqueIdentifier());
								xp.addContract(url,x);
							}
						}
					}
				}
			}
			catch (Exception e) {
				Logger.error("Error loading vocabulary from "+pluginId,e);
			}
		}
		
		// filter contracts
		Collection<EclipsePlugin> l = new ArrayList<EclipsePlugin>();
		for (EclipsePlugin p:plugins.values()) {
			if (p.hasContracts()) {
				l.add(p);
			}
		}
		
		
		// instantiate contracts
		// TODO: perhaps this is better done in separate thread
		EclipseResourceManager eclipseMgr = new EclipseResourceManager();
		for (EclipsePlugin p:l) {
			for (EclipseExtensionPoint xp:p.getExtensionPoints()) {
				Contract c = xp.getContract();
				for (EclipseExtension x:xp.getExtensions()) {
					try {
						Contract instantiatedContract = c.bindSupplier(x,eclipseMgr);
						x.setContract(instantiatedContract);
					}
					catch (Exception e) {
						Logger.error("Error instantiating contract "+c+" for extension "+x.getId(), e);
					}
				}
			}
		}
		
		return l;
	}
}
