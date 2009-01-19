/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.osgi.framework.Bundle;

import net.java.treaty.Contract;

/**
 * Export instantiated contracts, including verification results.
 * @author Jens Dietrich
 */

public abstract class Exporter {
	private static List<Exporter> INSTANCES = null;
	public static List<Exporter> getInstances() {
		synchronized (Exporter.class) {
			if (INSTANCES==null) {
				INSTANCES=new ArrayList<Exporter>();
				IExtensionRegistry registry = org.eclipse.core.runtime.Platform.getExtensionRegistry();
				IExtensionPoint xp = registry.getExtensionPoint("net.java.treaty.eclipse.exporter");
				for (IExtension x:xp.getExtensions()) {
					String pluginId = x.getContributor().getName();
					try {
						IConfigurationElement[] attributes = x.getConfigurationElements();
						for (int j=0;j<attributes.length;j++) {
							IConfigurationElement p = attributes[j];
							String loc = p.getAttribute("class");	
							Bundle bundle = org.eclipse.core.runtime.Platform.getBundle(pluginId);
							if (bundle!=null && loc!=null) {
								Class<Exporter> clazz = bundle.loadClass(loc);
								Exporter exporter = clazz.newInstance();
								INSTANCES.add(exporter);
							}
						}
					}
					catch (Exception e) {
						Logger.error("Error loading vocabulary from "+pluginId,e);
					}	
				}
			}
			return INSTANCES;
		}
	}
	public abstract void export(Collection<Contract> contracts, File file) throws IOException;
	public abstract boolean exportToFolder();
	// this is only used if exportToFolder() returns false
	public abstract String[] getFilterExtensions();
	public abstract String[] getFilterNames();
	
	public abstract String getName();
}
