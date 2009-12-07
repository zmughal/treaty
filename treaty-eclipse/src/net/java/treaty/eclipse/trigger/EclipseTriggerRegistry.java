/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.trigger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.java.treaty.eclipse.Constants;
import net.java.treaty.eclipse.Logger;
import net.java.treaty.eclipse.exporter.Exporter;
import net.java.treaty.trigger.TriggerRegistry;
import net.java.treaty.trigger.TriggerVocabulary;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.osgi.framework.Bundle;

/**
 * <p>
 * The {@link EclipseTriggerRegistry} manages {@link TriggerVocabulary}s
 * dynamically that are registered via the {@link IExtensionPoint}
 * {@link Constants#TRIGGER_VOCABULARY_EXTENSION_POINT_ID}
 * </p>
 * 
 * @author Claas Wilke
 */
public class EclipseTriggerRegistry extends TriggerRegistry implements
		IRegistryEventListener {

	/** The singleton instance of the {@link EclipseTriggerRegistry}. */
	public static final EclipseTriggerRegistry INSTANCE =
			new EclipseTriggerRegistry();

	/**
	 * The list of Instances of {@link TriggerVocabulary}s mapped by the ID of the
	 * {@link IExtension} providing them.
	 */
	private Map<String, Set<AbstractEclipseTriggerVocabulary>> triggerVocabulariesOfExtension =
			null;

	/**
	 * The {@link TriggerRegistryListener}s of this {@link EclipseTriggerRegistry}
	 * .
	 */
	private Set<TriggerRegistryListener> myListeners =
			new HashSet<TriggerRegistryListener>();

	/**
	 * <p>
	 * Private constructor for singleton pattern.
	 * </p>
	 */
	private EclipseTriggerRegistry() {

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
			this.addTriggerVocabulary(extension);
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

			if (this.triggerVocabulariesOfExtension.containsKey(extension
					.getUniqueIdentifier())) {

				for (AbstractEclipseTriggerVocabulary triggerVocabulary : this.triggerVocabulariesOfExtension
						.remove(extension.getUniqueIdentifier())) {
					
					triggerVocabulary.tearDown();

					this.removeTriggerVocabulary(triggerVocabulary);
					this.notifiyRemovedTriggerVocabulary(triggerVocabulary);
					
					Logger.info("Removed TriggerVocabulary " + triggerVocabulary);
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
	 * Initializes the list of {@link TriggerVocabulary} instances.
	 * </p>
	 */
	public void initialize() {

		if (this.triggerVocabulariesOfExtension == null) {
			this.triggerVocabulariesOfExtension =
					new HashMap<String, Set<AbstractEclipseTriggerVocabulary>>();

			IExtensionRegistry extensionRegistry;
			extensionRegistry =
					org.eclipse.core.runtime.Platform.getExtensionRegistry();

			IExtensionPoint extensionPoint;

			/* Register the registry as listener of the extension registry. */
			extensionRegistry.addListener(this);

			extensionPoint =
					extensionRegistry
							.getExtensionPoint(Constants.TRIGGER_VOCABULARY_EXTENSION_POINT_ID);

			/* Add exporter instances for each extension of the extension point. */
			for (IExtension extension : extensionPoint.getExtensions()) {
				this.addTriggerVocabulary(extension);
			}
			// end for.
		}
		// no else.
	}

	/**
	 * <p>
	 * This method can be called when this plug-in shall be de-activated.
	 * Unregisters the {@link EclipseTriggerRegistry} as listener of the
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
	private void addTriggerVocabulary(IExtension extension) {

		if (extension.isValid()
				&& extension.getExtensionPointUniqueIdentifier().equals(
						Constants.TRIGGER_VOCABULARY_EXTENSION_POINT_ID)) {

			Set<AbstractEclipseTriggerVocabulary> triggerVocabulariesOfExtension;
			triggerVocabulariesOfExtension = new HashSet<AbstractEclipseTriggerVocabulary>();

			Set<AbstractEclipseTriggerVocabulary> newTriggerVocabulariesOfExtension;
			newTriggerVocabulariesOfExtension = new HashSet<AbstractEclipseTriggerVocabulary>();

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

						if (TriggerVocabulary.class.isAssignableFrom(clazz)) {
							AbstractEclipseTriggerVocabulary triggerVocabulary;
							triggerVocabulary = (AbstractEclipseTriggerVocabulary) clazz.newInstance();

							/*
							 * Add the TriggerVocabulary and probably store it for notify, if
							 * its a new TriggerVocabulary.
							 */
							if (triggerVocabulariesOfExtension.add(triggerVocabulary)) {
								newTriggerVocabulariesOfExtension.add(triggerVocabulary);
								this.addTriggerVocabulary(triggerVocabulary);
								
								Logger.info("Added new TriggerVocabulary " + triggerVocabulary);
							}
							// no else.
						}
						// no else.
					}
					// no else.
				}
				// end for.

				/* Store all exporters of this extension. */
				this.triggerVocabulariesOfExtension.put(
						extension.getUniqueIdentifier(), triggerVocabulariesOfExtension);

				/* Notify all listeners of all newly added exporters. */
				for (TriggerVocabulary triggerVocabulary : newTriggerVocabulariesOfExtension) {
					this.notifiyAddedTriggerVocabulary(triggerVocabulary);
				}
				// end for.
			}
			// end try.

			catch (Exception e) {
				Logger.error("Error during loading TriggerVocabulary from " + pluginId,
						e);
			}
			// end catch.
		}
		// no else (wrong type of IExtension).
	}

	/**
	 * <p>
	 * Notifies all registered {@link TriggerRegistryListener}s, that a
	 * {@link TriggerVocabulary} has been added to this
	 * {@link EclipseTriggerRegistry}.
	 * </p>
	 * 
	 * @param triggerVocabulary
	 *          The {@link TriggerVocabulary} that has been added.
	 */
	private void notifiyAddedTriggerVocabulary(TriggerVocabulary triggerVocabulary) {

		for (TriggerRegistryListener listener : this.myListeners) {
			listener.triggerVocabularyAdded(triggerVocabulary);
		}
		// end for.
	}

	/**
	 * <p>
	 * Notifies all registered {@link TriggerRegistryListener}s, that a
	 * {@link TriggerVocabulary} has been removed to this
	 * {@link EclipseTriggerRegistry}.
	 * </p>
	 * 
	 * @param exporter
	 *          The {@link Exporter} that has been removed.
	 */
	private void notifiyRemovedTriggerVocabulary(
			TriggerVocabulary triggerVocabulary) {

		for (TriggerRegistryListener listener : this.myListeners) {
			listener.triggerVocabularyRemoved(triggerVocabulary);
		}
		// end for.
	}
}