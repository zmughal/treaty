/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */
package net.java.treaty.eclipse.vocabulary.junit.ui;

import java.net.URI;
import java.net.URL;

import net.java.treaty.eclipse.vocabulary.junit.JUnitVocabulary;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Provider for icons representing types in user interfaces. Extensions provide
 * concrete subclasses.
 * 
 * @author Jens Dietrich
 */

public class IconProvider extends net.java.treaty.eclipse.views.IconProvider {

	/**
	 * Get the icon
	 * 
	 * @param type
	 *          the type
	 * @param isVariable
	 *          whether the resource is a variable
	 * @return
	 */
	protected Image getIcon(URI type, boolean isVariable) {

		String typeName;
		typeName = type.toString();

		ImageDescriptor imageDescriptor;
		imageDescriptor = null;

		if (JUnitVocabulary.TESTCASE.equals(typeName)) {

			if (isVariable) {
				imageDescriptor = this.getImageDescriptor("icons/junit_var.gif");
			}

			else {
				imageDescriptor = this.getImageDescriptor("icons/junit.gif");
			}
		}
		// no else.

		if (imageDescriptor != null) {
			return imageDescriptor.createImage();
		}
		// no else.

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.eclipse.views.IconProvider#getIconURL(java.net.URI,
	 * boolean)
	 */
	protected URL getIconURL(URI type, boolean isVariable) {

		URL result;

		String typeName;
		typeName = type.toString();

		result = null;

		if (JUnitVocabulary.TESTCASE.equals(typeName)) {
			if (isVariable) {
				result =
						Activator.getDefault().getBundle().getResource(
								"icons/junit_var.gif");
			}

			else {
				result =
						Activator.getDefault().getBundle().getResource("icons/junit.gif");
			}
		}

		return result;
	}

	private ImageDescriptor getImageDescriptor(String path) {

		return AbstractUIPlugin
				.imageDescriptorFromPlugin(Activator.PLUGIN_ID, path);
	}
}
