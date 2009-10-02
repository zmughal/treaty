/*
Copyright (C) 2009 by Claas Wilke (info@claaswilke.de)

This file is part of the Treaty OCL Vocabulary which adapts Dresden OCL2 for
Eclipse to Treaty.

Dresden OCL2 for Eclipse is free software: you can redistribute it and/or modify 
it under the terms of the GNU Lesser General Public License as published by the 
Free Software Foundation, either version 3 of the License, or (at your option)
any later version.

Dresden OCL2 for Eclipse is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License 
for more details.

You should have received a copy of the GNU Lesser General Public License along 
with Dresden OCL2 for Eclipse. If not, see <http://www.gnu.org/licenses/>.
 */
package net.java.treaty.eclipse.vocabulary.ocl.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * <p>
 * The atcivator of the GUI of the OCL Treaty Vocabulary.
 * </p>
 * 
 * @author Claas Wilke
 */
public class Activator extends AbstractUIPlugin {

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "net.java.treaty.eclipse.vocabulary.ocl.ui";

	/** The shared instance. */
	private static Activator plugin;

	/**
	 * <p>The constructor.</p>
	 */
	public Activator() {
	}

	/**
	 * <p>Returns the shared instance.</p>
	 * 
	 * @return The shared instance.
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
}