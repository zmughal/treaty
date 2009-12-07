/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.exporter;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.java.treaty.eclipse.Constants;
import net.java.treaty.eclipse.Logger;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.osgi.framework.Bundle;

/**
 * <p>
 * The {@link ExporterRegistry} manages the instances of all different kinds of
 * {@link Exporter}s currently registered via {@link IExtension}s of the
 * {@link IExtensionPoint} <code>net.java.treaty.eclipse.exporter</code>.
 * 
 * @author Claas Wilke
 */
public class ExporterRegistry implements IRegistryEventListener {

	/** The singleton instance of the {@link ExporterRegistry}. */
	public static ExporterRegistry INSTANCE = new ExporterRegistry();

	/**
	 * The list of Instances of {@link Exporter}s mapped by the ID of their
	 * {@link IExtension} providing them.
	 */
	private Map<String, Set<Exporter>> myExporters = null;

	/** The {@link ExporterRegistryListener} of this {@link ExporterRegistry}. */
	private Set<ExporterRegistryListener> myListeners =
			new HashSet<ExporterRegistryListener>();

	/**
	 * <p>
	 * Private constructor for singleton pattern.
	 * </p>
	 */
	private ExporterRegistry() {

		/* Remains empty. */
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.
	 * runtime.IExtension[])
	 */
	public void added(IExtension[] extensions) {

		for (IExtension extension : extensions) {
			this.addExporters(extension);
		}
		// end for.
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.
	 * runtime.IExtensionPoint[])
	 */
	public void added(IExtensionPoint[] extensionPoints) {

		/* This method can be ignored, only listens to extensions. */
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core
	 * .runtime.IExtension[])
	 */
	public void removed(IExtension[] extensions) {

		for (IExtension extension : extensions) {

			if (this.myExporters.containsKey(extension.getUniqueIdentifier())) {

				for (Exporter exporter : this.myExporters.remove(extension
						.getUniqueIdentifier())) {

					this.notifiyRemovedExporter(exporter);
				}
				// end for.
			}
			// no else.
		}
		// end for.
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core
	 * .runtime.IExtensionPoint[])
	 */
	public void removed(IExtensionPoint[] extensionPoints) {

		/* This method can be ignored, only listens to extensions. */
	}

	/**
	 * <p>
	 * Adds an {@link ExporterRegistryListener} to this {@link ExporterRegistry}.
	 * </p>
	 * 
	 * @param listener
	 *          The {@link ExporterRegistryListener} that shall be added.
	 */
	public void addExporterRegistryListener(ExporterRegistryListener listener) {

		this.myListeners.add(listener);
	}

	/**
	 * <p>
	 * Returns all instances of the {@link Exporter} {@link Class} that are
	 * registered via the extension point
	 * <code>net.java.treaty.eclipse.exporter</code>.
	 * 
	 * @return All instances of the {@link Exporter} {@link Class} that are
	 *         registered via the extension point.
	 */
	public synchronized Collection<Exporter> getExporters() {

		Collection<Exporter> result;

		if (this.myExporters == null) {
			this.initialize();
		}
		// no else.

		result = new HashSet<Exporter>();

		for (Collection<Exporter> exporters : this.myExporters.values()) {
			result.addAll(exporters);
		}
		// end for.

		return result;
	}

	/**
	 * <p>
	 * Removes an {@link ExporterRegistryListener} to this
	 * {@link ExporterRegistry}.
	 * </p>
	 * 
	 * @param listener
	 *          The {@link ExporterRegistryListener} that shall be removed.
	 * @return <code>true</code> if the {@link ExporterRegistryListener} has been
	 *         removed successfully.
	 */
	public boolean removeExporterRegistryListener(
			ExporterRegistryListener listener) {

		return this.myListeners.remove(listener);
	}

	/**
	 * <p>
	 * This method can be called when this plug-in shall be de-activated.
	 * Unregisters the {@link ExporterRegistry} as listener of the
	 * ExtensionRegistry.
	 * </p>
	 */
	public void tearDown() {

		org.eclipse.core.runtime.Platform.getExtensionRegistry().removeListener(
				this);
	}

	/**
	 * <p>
	 * Adds {@link Exporter} instances for a given {@link IExtension} to the list
	 * of {@link Exporter} instances.
	 * </p>
	 * 
	 * @param extension
	 *          The {@link IExtension} whose {@link Exporter}s shall be added.
	 */
	private void addExporters(IExtension extension) {

		if (extension.isValid()
				&& extension.getExtensionPointUniqueIdentifier().equals(
						Constants.EXPORTER_EXTENSION_POINT_ID)) {

			Set<Exporter> exportersOfExtension;
			exportersOfExtension = new HashSet<Exporter>();

			Set<Exporter> newExportersOfExtension;
			newExportersOfExtension = new HashSet<Exporter>();

			String pluginId;
			pluginId = extension.getContributor().getName();

			/* Try to get the exporter. */
			try {
				for (IConfigurationElement configurationElement : extension
						.getConfigurationElements()) {

					String className;
					className = configurationElement.getAttribute("class");

					Bundle bundle;
					bundle = org.eclipse.core.runtime.Platform.getBundle(pluginId);

					if (bundle != null && className != null) {
						Class<?> clazz;
						clazz = bundle.loadClass(className);

						if (Exporter.class.isAssignableFrom(clazz)) {
							Exporter exporter;
							exporter = (Exporter) clazz.newInstance();

							/*
							 * Add the exporter and probably store it for notify, if its a new
							 * exporter.
							 */
							if (exportersOfExtension.add(exporter)) {
								newExportersOfExtension.add(exporter);
							}
							// no else.
						}
						// no else.
					}
					// no else.
				}
				// end for.

				/* Store all exporters of this extension. */
				this.myExporters.put(extension.getUniqueIdentifier(),
						exportersOfExtension);

				/* Notify all listeners of all newly added exporters. */
				for (Exporter exporter : newExportersOfExtension) {
					this.notifiyAddedExporter(exporter);
				}
				// end for.
			}
			// end try.

			catch (Exception e) {
				Logger.error("Error loading Exporter from " + pluginId, e);
			}
			// end catch.
		}
		// no else (wrong type of IExtension).
	}

	/**
	 * <p>
	 * Initializes the list of {@link Exporter} instances.
	 * </p>
	 */
	private void initialize() {

		this.myExporters = new HashMap<String, Set<Exporter>>();

		IExtensionRegistry extensionRegistry;
		extensionRegistry =
				org.eclipse.core.runtime.Platform.getExtensionRegistry();

		IExtensionPoint extensionPoint;

		/* Register the registry as listener of the extension registry. */
		extensionRegistry.addListener(this);

		extensionPoint =
				extensionRegistry
						.getExtensionPoint(Constants.EXPORTER_EXTENSION_POINT_ID);

		/* Add exporter instances for each extension of the extension point. */
		for (IExtension extension : extensionPoint.getExtensions()) {
			this.addExporters(extension);
		}
		// end for.
	}

	/**
	 * <p>
	 * Notifies all registered {@link ExporterRegistryListener}s, that an
	 * {@link Exporter} has been added to this {@link ExporterRegistry}.
	 * </p>
	 * 
	 * @param exporter
	 *          The {@link Exporter} that has been added.
	 */
	private void notifiyAddedExporter(Exporter exporter) {

		for (ExporterRegistryListener listener : this.myListeners) {
			listener.exporterAdded(exporter);
		}
		// end for.
	}

	/**
	 * <p>
	 * Notifies all registered {@link ExporterRegistryListener}s, that an
	 * {@link Exporter} has been removed to this {@link ExporterRegistry}.
	 * </p>
	 * 
	 * @param exporter
	 *          The {@link Exporter} that has been removed.
	 */
	private void notifiyRemovedExporter(Exporter exporter) {

		for (ExporterRegistryListener listener : this.myListeners) {
			listener.exporterRemoved(exporter);
		}
		// end for.
	}
}