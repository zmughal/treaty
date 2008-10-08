/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */
package net.java.treaty.eclipse.views;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.java.treaty.eclipse.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;

/**
 * Provider for icons representing types in user interfaces.
 * Extensions provide concrete subclasses.
 * @author Jens Dietrich
 */

public abstract class IconProvider {
	
	private static List<IconProvider> providers = null;
	private static Map<String,Image> icons = new HashMap<String,Image>();
	
	/**
	 * Get the icon
	 * @param type the type
	 * @param isVariable whether the resource is a variable
	 * @return
	 */
	protected abstract Image getIcon(URI type,boolean isVariable) ;
	
	/**
	 * Get the icon
	 * @param type the type
	 * @param isVariable whether the resource is a variable
	 * @return
	 */
	public static Image findIcon(URI type,boolean isVariable) {
		String name = type.toString()+(isVariable?"_v":"_c");
		Image i = icons.get(name);
		if (i==null) {
			for (IconProvider p:getProviders()) {
				i = p.getIcon(type, isVariable);
				if (i!=null) {
					icons.put(name,i);
					return i;
				}
			}
		}
		return i;
	}
	
	
	private static List<IconProvider> getProviders() {
		if (providers==null) {
			providers = new ArrayList<IconProvider>();
			
			// extensions
			IExtensionRegistry registry = org.eclipse.core.runtime.Platform.getExtensionRegistry();
			IExtensionPoint xp = registry.getExtensionPoint("net.java.treaty.eclipse.typeicons");
			
			for (IExtension x:xp.getExtensions()) {
				String pluginId = x.getContributor().getName();
				try {
					IConfigurationElement[] attributes = x.getConfigurationElements();
					for (int j=0;j<attributes.length;j++) {
						IConfigurationElement p = attributes[j];
						String clazzName = p.getAttribute("class");	
						Bundle bundle = org.eclipse.core.runtime.Platform.getBundle(pluginId);
						Class clazz = bundle.loadClass(clazzName);
						if (IconProvider.class.isAssignableFrom(clazz)) {
							providers.add((IconProvider)clazz.newInstance());
						}
					}
				}
				catch (Exception e) {
					Logger.error("Error loading icon provider from "+pluginId,e);
				}			
			}
			
		}
		return providers;
	}
	
	public static void release() {
		providers = null;
		for (Image i:icons.values()) {
			i.dispose();
		}
		icons.clear();
	}
	
}
