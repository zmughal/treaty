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
import org.eclipse.core.runtime.InvalidRegistryObjectException;
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
	 * TODO Claas: Caching here can probably cause problems in long living
	 * environments.
	 * 
	 * The already adapted {@link EclipsePlugin}s of this
	 * {@link EclipseAdapterFactory}.
	 */
	private Map<Bundle, EclipsePlugin> myEclipsePlugins =
			new WeakHashMap<Bundle, EclipsePlugin>();

	/**
	 * The already adapted {@link EclipseExtensionPoint}s of this
	 * {@link EclipseAdapterFactory} identified by their {@link IExtensionPoint}'s
	 * unique ID.
	 */
	private Map<String, EclipseExtensionPoint> myEclipseExtensionPoints =
			new HashMap<String, EclipseExtensionPoint>();

	/**
	 * The already adapted {@link EclipseExtension}s of this
	 * {@link EclipseAdapterFactory} identified by their {@link IExtension}.
	 */
	private Map<IExtension, EclipseExtension> myEclipseExtensions =
			new HashMap<IExtension, EclipseExtension>();

	/**
	 * Maps {@link IExtension}s without an identifier to a generated unique
	 * identifier.
	 */
	private Map<IExtension, String> extensionIDs =
			new WeakHashMap<IExtension, String>();

	/** Counter to generate unique identifiers for {@link IExtension}s. */
	private int unnamedExtensionCounter = 0;

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
	 * @throws EclipseConnectorAdaptationException
	 *           Thrown, if the given {@link IExtensionPoint} is invalid and
	 *           cannot be adapted.
	 */
	public EclipseExtensionPoint createExtensionPoint(
			IExtensionPoint extensionPoint)
			throws EclipseConnectorAdaptationException {

		EclipseExtensionPoint result;

		try {
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

				result =
						new EclipseExtensionPoint(eclipsePlugin, extensionPoint
								.getUniqueIdentifier());

				/* Cache the result. */
				this.myEclipseExtensionPoints.put(extensionPoint.getUniqueIdentifier(),
						result);
			}
			// end else.
		}
		// end try

		catch (InvalidRegistryObjectException e) {
			throw new EclipseConnectorAdaptationException(
					"The given IExtensionPoint was invalid. Cannot adapt "
							+ "an EclipseExtensionPoint.", e);
		}
		// end catch.

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
	 * @throws EclipseConnectorAdaptationException
	 *           Thrown, if the given {@link IExtension} is invalid and cannot be
	 *           adapted.
	 */
	public EclipseExtension createExtension(IExtension extension)
			throws EclipseConnectorAdaptationException {

		EclipseExtension result;

		try {
			String uniqueIdentifier;
			uniqueIdentifier = extension.getUniqueIdentifier();

			if (uniqueIdentifier == null || uniqueIdentifier.length() == 0) {
				uniqueIdentifier = this.getUniqueIdentifier(extension);
			}
			// no else.

			/* Probably use a cached result. */
			if (this.myEclipseExtensions.containsKey(extension)) {
				result = this.myEclipseExtensions.get(extension);
			}

			else {
				IContributor contributor;
				Bundle bundle;
				EclipsePlugin eclipsePlugin;

				contributor = extension.getContributor();
				bundle =
						org.eclipse.core.runtime.Platform.getBundle(contributor.getName());
				eclipsePlugin = this.createEclipsePlugin(bundle);

				result = new EclipseExtension(eclipsePlugin, uniqueIdentifier);

				IExtensionPoint extensionPoint;
				extensionPoint =
						org.eclipse.core.runtime.Platform.getExtensionRegistry()
								.getExtensionPoint(
										extension.getExtensionPointUniqueIdentifier());
				result.setExtensionPoint(this.createExtensionPoint(extensionPoint));

				/* Cache the result. */
				this.myEclipseExtensions.put(extension, result);
			}
			// end else.
		}
		// end try

		catch (InvalidRegistryObjectException e) {
			throw new EclipseConnectorAdaptationException("The given IExtension "
					+ extension + " was invalid. Cannot adapt " + "an EclipseExtension.",
					e);
		}
		// end catch.

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

	/**
	 * <p>
	 * Removes a given {@link EclipseExtension} from the cache of this
	 * {@link EclipseAdapterFactory}.
	 * </p>
	 * 
	 * @param eclipseExtension
	 *          The {@link EclipseExtension} that shall be removed.
	 */
	public void removeEclipseExtensionFromCache(EclipseExtension eclipseExtension) {

		IExtension extesionToBeRemoved;
		extesionToBeRemoved = null;

		for (IExtension extension : this.myEclipseExtensions.keySet()) {

			if (this.myEclipseExtensions.get(extension).equals(eclipseExtension)) {
				extesionToBeRemoved = extension;
				break;
			}
			// no else.
		}
		// end for.

		if (extesionToBeRemoved != null) {
			this.myEclipseExtensions.remove(extesionToBeRemoved);
			this.extensionIDs.remove(extesionToBeRemoved);
		}
		// no else.
	}

	/**
	 * <p>
	 * Removes a given {@link EclipseExtensionPoint} from the cache of this
	 * {@link EclipseAdapterFactory}.
	 * </p>
	 * 
	 * @param eclipseExtensionPoint
	 *          The {@link EclipseExtensionPoint} that shall be removed.
	 */
	public void removeEclipseExtensionPointFromCache(
			EclipseExtensionPoint eclipseExtensionPoint) {

		String idToBeRemoved;
		idToBeRemoved = null;

		for (String extensionPointID : this.myEclipseExtensionPoints.keySet()) {

			if (this.myEclipseExtensionPoints.get(extensionPointID).equals(
					eclipseExtensionPoint)) {
				idToBeRemoved = extensionPointID;
				break;
			}
			// no else.
		}
		// end for.

		if (idToBeRemoved != null) {
			this.myEclipseExtensionPoints.remove(idToBeRemoved);
		}
		// no else.
	}

	/**
	 * <p>
	 * (Probably creates and) returns a unique ID for the given {@link IExtension}
	 * .
	 * </p>
	 * 
	 * @param extension
	 *          The {@link IExtension} for that an ID shall be returned.
	 * @return The unique ID.
	 * @throws InvalidRegistryObjectException
	 *           Thrown, if the given {@link IExtension} is invalid.
	 */
	private String getUniqueIdentifier(IExtension extension)
			throws InvalidRegistryObjectException {

		String result;

		if (this.extensionIDs.containsKey(extension)) {
			result = this.extensionIDs.get(extension);
		}

		else {
			StringBuffer buffer;
			buffer = new StringBuffer();

			buffer.append(extension.getExtensionPointUniqueIdentifier());
			buffer.append(".unnamedExtension_" + this.unnamedExtensionCounter++);

			result = buffer.toString();
		}

		return result;
	}
}