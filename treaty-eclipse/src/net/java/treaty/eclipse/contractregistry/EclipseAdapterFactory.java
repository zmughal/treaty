/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.contractregistry;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.EclipseExtensionPoint;
import net.java.treaty.eclipse.EclipsePlugin;

import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.osgi.framework.Bundle;

/**
 * <p>
 * This class provides methods to adapt given {@link Bundle}s,
 * {@link IExtensionPoint}s and {@link IExtension}s to {@link EclipsePlugin}s,
 * {@link EclipseExtensionPoint}s and {@link EclipseExtension}s.
 * </p>
 * 
 * @author Claas Wilke
 */
public class EclipseAdapterFactory {

	/** The Singleton Instance of the {@link EclipseAdapterFactory}. */
	private static EclipseAdapterFactory myInstance;

	/**
	 * The already adapted {@link EclipsePlugin}s of this
	 * {@link EclipseAdapterFactory}.
	 */
	private Map<Bundle, EclipsePlugin> myEclipsePlugins =
			new WeakHashMap<Bundle, EclipsePlugin>();

	/**
	 * The already adapted {@link EclipseExtensionPoint}s of this
	 * {@link EclipseAdapterFactory} identified by their unique identifier.
	 */
	private Map<String, EclipseExtensionPoint> myEclipseExtensionPoints =
			new HashMap<String, EclipseExtensionPoint>();

	/**
	 * The already adapted {@link EclipseExtension}s of this
	 * {@link EclipseAdapterFactory} identified by their unique identifier.
	 */
	private Map<String, EclipseExtension> myEclipseExtensions =
			new HashMap<String, EclipseExtension>();

	/**
	 * <p>
	 * Private constructor for singleton pattern.
	 * </p>
	 */
	private EclipseAdapterFactory() {

	}

	/**
	 * <p>
	 * Returns the Singleton instance of the {@link EclipseAdapterFactory}.
	 * </p>
	 * 
	 * @return The Singleton instance of the {@link EclipseAdapterFactory}.
	 */
	public static EclipseAdapterFactory getInstance() {

		if (myInstance == null) {
			myInstance = new EclipseAdapterFactory();
		}
		// no else.

		return myInstance;
	}

	/**
	 * <p>
	 * Creates an {@link EclipsePlugin} for a given {@link Bundle}.
	 * </p>
	 * 
	 * @param bundle
	 *          The {@link Bundle} the {@link EclipsePlugin} shall be created for.
	 * @return The created {@link EclipsePlugin}.
	 */
	public EclipsePlugin createEclipsePlugin(Bundle bundle) {

		EclipsePlugin result;

		/* Probably use a cached result. */
		if (this.myEclipsePlugins.containsKey(bundle)) {
			result = this.myEclipsePlugins.get(bundle);
		}

		else {
			result = new EclipsePlugin(bundle);

			/* Cache the result. */
			this.myEclipsePlugins.put(bundle, result);
		}
		// end else.

		return result;
	}

	/**
	 * <p>
	 * Creates an {@link EclipseExtensionPoint} for a given
	 * {@link IExtensionPoint}.
	 * </p>
	 * 
	 * @param bundle
	 *          The {@link IExtensionPoint} the {@link EclipseExtensionPoint}
	 *          shall be created for.
	 * @return The created {@link EclipseExtensionPoint}.
	 */
	public EclipseExtensionPoint createExtensionPoint(
			IExtensionPoint extensionPoint) {

		EclipseExtensionPoint result;

		/* Probably use a cached result. */
		if (this.myEclipseExtensionPoints.containsKey(extensionPoint
				.getUniqueIdentifier())) {
			result =
					this.myEclipseExtensionPoints.get(extensionPoint
							.getUniqueIdentifier());
		}

		else {
			IContributor contributor;
			Bundle bundle;
			EclipsePlugin eclipsePlugin;

			contributor = extensionPoint.getContributor();
			bundle =
					org.eclipse.core.runtime.Platform.getBundle(contributor.getName());
			eclipsePlugin = this.createEclipsePlugin(bundle);

			result = new EclipseExtensionPoint(eclipsePlugin, extensionPoint);

			/* Cache the result. */
			this.myEclipseExtensionPoints.put(extensionPoint.getUniqueIdentifier(),
					result);
		}
		// end else.

		return result;
	}

	/**
	 * <p>
	 * Creates an {@link EclipseExtension} for a given {@link IExtension}.
	 * </p>
	 * 
	 * @param bundle
	 *          The {@link IExtension} the {@link EclipseExtension} shall be
	 *          created for.
	 * @return The created {@link EclipseExtension}.
	 */
	public EclipseExtension createExtension(IExtension extension) {

		EclipseExtension result;

		/* Probably use a cached result. */
		if (this.myEclipseExtensions.containsKey(extension.getUniqueIdentifier())) {
			result = this.myEclipseExtensions.get(extension.getUniqueIdentifier());
		}

		else {
			IContributor contributor;
			Bundle bundle;
			EclipsePlugin eclipsePlugin;

			contributor = extension.getContributor();
			bundle =
					org.eclipse.core.runtime.Platform.getBundle(contributor.getName());
			eclipsePlugin = this.createEclipsePlugin(bundle);

			result = new EclipseExtension(eclipsePlugin, extension);

			/* Cache the result. */
			this.myEclipseExtensions.put(extension.getUniqueIdentifier(), result);
		}
		// end else.

		return result;
	}

	/**
	 * <p>
	 * Clears the cache of this {@link EclipseAdapterFactory}.
	 * </p>
	 * 
	 * <p>
	 * <strong> Please note that clearing the cache at the wrong time can cause
	 * problems like 'Object Schizophrenia'!</strong>
	 * </p>
	 */
	public void clearCache() {

		this.myEclipsePlugins.clear();
		this.myEclipseExtensionPoints.clear();
		this.myEclipseExtensions.clear();
	}
}