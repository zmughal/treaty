/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.action;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.java.treaty.action.ActionRegistry;
import net.java.treaty.action.ActionVocabulary;
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
 * The {@link EclipseActionRegistry} manages {@link ActionVocabulary}s
 * dynamically that are registered via the {@link IExtensionPoint}
 * {@link Constants#ACTION_VOCABULARY_EXTENSION_POINT_ID}
 * </p>
 * 
 * @author Claas Wilke
 */
public class EclipseActionRegistry extends ActionRegistry implements
		IRegistryEventListener {

	/** The singleton instance of the {@link EclipseActionRegistry}. */
	public static final EclipseActionRegistry INSTANCE =
			new EclipseActionRegistry();

	/**
	 * The list of Instances of {@link ActionVocabulary}s mapped by the ID of the
	 * {@link IExtension} providing them.
	 */
	private Map<String, Set<ActionVocabulary>> actionVocabulariesOfExtension =
			null;

	/**
	 * <p>
	 * Private constructor for singleton pattern.
	 * </p>
	 */
	private EclipseActionRegistry() {

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
			this.addActionVocabulary(extension);
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

			if (this.actionVocabulariesOfExtension.containsKey(extension
					.getUniqueIdentifier())) {

				for (ActionVocabulary actionVocabulary : this.actionVocabulariesOfExtension
						.remove(extension.getUniqueIdentifier())) {

					this.removeActionVocabulary(actionVocabulary);

					Logger.info("Removed ActionVocabulary " + actionVocabulary);
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
	 * Initializes the list of {@link ActionVocabulary} instances.
	 * </p>
	 */
	public void initialize() {

		if (this.actionVocabulariesOfExtension == null) {
			this.actionVocabulariesOfExtension =
					new HashMap<String, Set<ActionVocabulary>>();

			IExtensionRegistry extensionRegistry;
			extensionRegistry =
					org.eclipse.core.runtime.Platform.getExtensionRegistry();

			IExtensionPoint extensionPoint;

			/* Register the registry as listener of the extension registry. */
			extensionRegistry.addListener(this);

			extensionPoint =
					extensionRegistry
							.getExtensionPoint(Constants.ACTION_VOCABULARY_EXTENSION_POINT_ID);

			/*
			 * Add action vocabulary instances for each extension of the extension
			 * point.
			 */
			for (IExtension extension : extensionPoint.getExtensions()) {
				this.addActionVocabulary(extension);
			}
			// end for.
		}
		// no else.
	}

	/**
	 * <p>
	 * This method can be called when this plug-in shall be de-activated.
	 * Unregisters the {@link EclipseActionRegistry} as listener of the
	 * ExtensionRegistry.
	 * </p>
	 */
	public void tearDown() {

		org.eclipse.core.runtime.Platform.getExtensionRegistry().removeListener(
				this);
	}

	/**
	 * <p>
	 * Adds {@link ActionVocabulary} instances for a given {@link IExtension} to
	 * the list of {@link ActionVocabulary} instances.
	 * </p>
	 * 
	 * @param extension
	 *          The {@link IExtension} whose {@link ActionVocabulary}s shall be
	 *          added.
	 */
	private void addActionVocabulary(IExtension extension) {

		if (extension.isValid()
				&& extension.getExtensionPointUniqueIdentifier().equals(
						Constants.ACTION_VOCABULARY_EXTENSION_POINT_ID)) {

			Set<ActionVocabulary> actionVocabulariesOfExtension;
			actionVocabulariesOfExtension = new HashSet<ActionVocabulary>();

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

						if (ActionVocabulary.class.isAssignableFrom(clazz)) {
							ActionVocabulary actionVocabulary;
							actionVocabulary = (ActionVocabulary) clazz.newInstance();

							this.addActionVocabulary(actionVocabulary);
							actionVocabulariesOfExtension.add(actionVocabulary);
							Logger.info("Added new ActionVocabulary " + actionVocabulary);
						}
						// no else.
					}
					// no else.
				}
				// end for.

				/* Store all exporters of this extension. */
				this.actionVocabulariesOfExtension.put(extension.getUniqueIdentifier(),
						actionVocabulariesOfExtension);
			}
			// end try.

			catch (Exception e) {
				Logger.error("Error during loading ActionsVocabulary from " + pluginId,
						e);
			}
			// end catch.
		}
		// no else (wrong type of IExtension).
	}
}