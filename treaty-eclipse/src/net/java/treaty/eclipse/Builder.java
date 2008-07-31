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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.osgi.framework.Bundle;

/**
 * Utility class that extracts plugins with contracts.
 * @author Jens Dietrich
 */

public class Builder {
	public Collection<EclipsePlugin> extractContracts() {
		
		Map<String,EclipsePlugin> plugins = new HashMap<String,EclipsePlugin>();
		
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
			for (IExtension x:xpoint.getExtensions()) {
				c = x.getContributor();
				b = org.eclipse.core.runtime.Platform.getBundle(c.getName());
				tplugin = plugins.get(b.getSymbolicName());
				EclipseExtension tx = new EclipseExtension(tplugin,x);
				txp.addExtension(tx);
			}					
		}	
		
		// filter contracts
		Collection<EclipsePlugin> l = new ArrayList<EclipsePlugin>();
		for (EclipsePlugin p:plugins.values()) {
			if (p.hasContracts()) {
				l.add(p);
			}
		}
		return l;
		
	}
}
