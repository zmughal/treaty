/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.vocabulary.junit;

import net.java.treaty.eclipse.VocabularyRegistry;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * <p>
 * The activator class controls the plug-in life cycle.
 * </p>
 * 
 * @author Claas Wilke
 */
public class Activator extends Plugin {

	/** The plug-in ID. */
	public static final String PLUGIN_ID =
			"net.java.treaty.eclipse.vocabulary.junit";

	/** The shared instance. */
	private static Activator plugin;

	/**
	 * <p>
	 * The constructor.
	 * </p>
	 */
	public Activator() {

		/* Remains empty. */
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {

		super.start(context);
		plugin = this;

		/* Register the vocabulary add the VocabularyRegistry. */
		VocabularyRegistry.INSTANCE.add(JUnitVocabulary.INSTANCE,
				Activator.PLUGIN_ID);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {

		plugin = null;
		super.stop(context);

		/* Register the vocabulary add the VocabularyRegistry. */
		VocabularyRegistry.INSTANCE.remove(JUnitVocabulary.INSTANCE);
	}

	/**
	 * <p>
	 * Returns the shared instance.
	 * </p>
	 * 
	 * @return The shared instance.
	 */
	public static Activator getDefault() {

		return plugin;
	}
}