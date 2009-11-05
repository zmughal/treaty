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
 * <p>
 * Provider for icons representing types in user interfaces. Extensions provide
 * concrete subclasses.
 * </p>
 * 
 * @author Jens Dietrich
 */
public abstract class IconProvider {

	/** A {@link List} of {@link IconProvider}s used already. */
	private static List<IconProvider> providers = null;

	/** A {@link List} of Icons used already. */
	private static Map<String, Image> icons = new HashMap<String, Image>();

	/**
	 * <p>
	 * Searches for an icon (as an {@link Image}) for a given type (as {@link URI}
	 * ).
	 * </p>
	 * 
	 * @param type
	 *          The type whose icon shall be returned.
	 * @param isVariable
	 *          Indicates whether or not the resource is a variable.
	 * @return The icon (as an {@link Image}) for a given type (as {@link URI}).
	 */
	public static Image findIcon(URI type, boolean isVariable) {

		Image result;
		String name;

		name = type.toString();

		if (isVariable) {
			name += "_v";
		}

		else {
			name += "_c";
		}

		result = icons.get(name);

		if (result == null) {

			for (IconProvider provider : getProviders()) {
				result = provider.getIcon(type, isVariable);

				if (result != null) {
					icons.put(name, result);
					break;
				}
				// no else.
			}
			// end for.
		}
		// no else.

		return result;
	}

	/**
	 * <p>
	 * Clears the lists of {@link IconProvider}s and icons. Disposes all
	 * {@link Image}s.
	 * </p>
	 */
	public static void release() {

		providers = null;

		for (Image icon : icons.values()) {
			icon.dispose();
		}
		// end for.

		icons.clear();
	}

	/**
	 * <p>
	 * Returns the icon (as an {@link Image}) for a given type (as {@link URI}).
	 * </p>
	 * 
	 * @param type
	 *          The type whose icon shall be returned.
	 * @param isVariable
	 *          Indicates whether or not the resource is a variable.
	 * @return The icon (as an {@link Image}) for a given type (as {@link URI}).
	 */
	protected abstract Image getIcon(URI type, boolean isVariable);

	/**
	 * <p>
	 * Returns all {@link IconProvider}s that are currently connected to the
	 * Treaty Framework.
	 * </p>
	 * 
	 * @return All {@link IconProvider}s that are currently connected to the
	 *         Treaty Framework.
	 */
	private static List<IconProvider> getProviders() {

		/* Probably initialize the IconProviders. */
		/*
		 * FIXME Claas: Probably this method should be updated if a new vocabulary
		 * occurs dynamically.
		 */
		if (providers == null) {

			providers = new ArrayList<IconProvider>();

			IExtensionRegistry extensionRegistry;
			IExtensionPoint extensionPoint;

			extensionRegistry =
					org.eclipse.core.runtime.Platform.getExtensionRegistry();
			extensionPoint =
					extensionRegistry
							.getExtensionPoint("net.java.treaty.eclipse.typeicons");

			/* Iterate on all extensions that provide icon providers. */
			for (IExtension extension : extensionPoint.getExtensions()) {

				String pluginId;
				pluginId = extension.getContributor().getName();

				try {
					IConfigurationElement[] attributes;
					attributes = extension.getConfigurationElements();

					for (int index = 0; index < attributes.length; index++) {

						IConfigurationElement configurationElement;
						configurationElement = attributes[index];

						String clazzName;
						clazzName = configurationElement.getAttribute("class");

						Bundle bundle;
						bundle = org.eclipse.core.runtime.Platform.getBundle(pluginId);

						/* Try to load the provider class. */
						Class<?> clazz;
						clazz = bundle.loadClass(clazzName);

						/* Probably add it to the list of IconProviders. */
						if (IconProvider.class.isAssignableFrom(clazz)) {
							providers.add((IconProvider) clazz.newInstance());
						}
						// no else.
					}
					// end for.
				}

				catch (Exception e) {
					Logger.error("Error loading icon provider from " + pluginId, e);
				}
				// end catch.
			}
			// end for (iteration on extensions).
		}
		// no else (no initialization).

		return providers;
	}
}