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

import net.java.treaty.eclipse.action.EclipseActionRegistry;
import net.java.treaty.eclipse.contractregistry.EclipseContractRegistry;
import net.java.treaty.eclipse.exporter.ExporterRegistry;
import net.java.treaty.eclipse.trigger.EclipseTriggerRegistry;

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
			"net.java.treaty.eclipse.contractregistry";

	/** The shared instance. */
	private static Activator plugin;

	/**
	 * <p>
	 * The constructor.
	 * </p>
	 */
	public Activator() {

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {

		super.start(context);
		plugin = this;

		/* Start the trigger registry. */
		EclipseTriggerRegistry.INSTANCE.initialize();

		/* Start the action registry. */
		EclipseActionRegistry.INSTANCE.initialize();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {

		EclipseContractRegistry.getInstance().tearDown();
		VocabularyRegistry.INSTANCE.tearDown();
		ExporterRegistry.INSTANCE.tearDown();

		EclipseTriggerRegistry.INSTANCE.tearDown();
		EclipseActionRegistry.INSTANCE.tearDown();

		plugin = null;
		super.stop(context);
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