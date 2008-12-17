/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.jobs;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import net.java.treaty.Contract;
import net.java.treaty.eclipse.ContractRepository;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.EclipseExtensionPoint;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.EclipseResourceManager;
import net.java.treaty.eclipse.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.osgi.framework.Bundle;

/**
 * Job to scan for and load contracts.
 * @author Jens Dietrich
 */

public class ContractLoadingJob extends Job {
	
	private Collection<EclipsePlugin> plugins = new Vector<EclipsePlugin>();
	
	public Collection<EclipsePlugin> getPlugins() {
		return plugins;
	}

	public ContractLoadingJob(String name) {
		super(name);
	}

    protected IStatus run(IProgressMonitor monitor) {
    	monitor.beginTask("Analysing contracts",13000);
    	
    	monitor.subTask("Analysing plugins");
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
			
			// tmp starts
			if (xpoint.getExtensions().length>10)
				System.out.println(""+xpoint.getUniqueIdentifier()+" has "+xpoint.getExtensions().length+" extensions");
			// tmp ends 
			
			for (IExtension x:xpoint.getExtensions()) {
				c = x.getContributor();
				b = org.eclipse.core.runtime.Platform.getBundle(c.getName());
				plugins.put(b.getSymbolicName(),new EclipsePlugin(b));				
			}
		}
		monitor.worked(500);
		
		monitor.subTask("Searching for contracts in extension points");
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
		monitor.worked(1000);
		
		monitor.subTask("Searching for external contracts");
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
		monitor.worked(1000);
		
		// filter contracts
		monitor.subTask("Filtering contracts");
		Collection<EclipsePlugin> l = new ArrayList<EclipsePlugin>();
		for (EclipsePlugin p:plugins.values()) {
			if (p.hasContracts()) {
				l.add(p);
			}
		}
		monitor.worked(5);
		
		
		// instantiate contracts
		// compute steps
		int counter = 0;
		for (EclipsePlugin p:l) {
			for (EclipseExtensionPoint xp:p.getExtensionPoints()) {
				if (xp.hasContracts()) {
					for (EclipseExtension x:xp.getExtensions()) {
						counter = counter+1;
					}
				}
			}
		}
		monitor.subTask("Instantiating contracts");
		EclipseResourceManager eclipseMgr = new EclipseResourceManager();
		int increment = 10000/counter;
		for (EclipsePlugin p:l) {
			for (EclipseExtensionPoint xp:p.getExtensionPoints()) {
				if (xp.hasContracts()) {
					Contract c = xp.getContract();
					for (EclipseExtension x:xp.getExtensions()) {
						try {
							Contract instantiatedContract = c.bindSupplier(x,eclipseMgr);
							x.setContract(instantiatedContract);
							monitor.worked(increment);
						}
						catch (Exception e) {
							Logger.error("Error instantiating contract "+c+" for extension "+x.getId(), e);
						}
					}
				}
			}
			
		}
		
		
		
       	
		ContractRepository rep = new ContractRepository(l);
		rep.install();
		
       	
       	monitor.done();
    	return Status.OK_STATUS;	        	 
     }
}
