/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */
package net.java.treaty.eclipse.vocabulary.owl.ui;

import java.net.URI;
import java.net.URL;

import net.java.treaty.vocabulary.builtins.owl.OWLVocabulary;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * <p>
 * Provider for icons representing types in user interfaces. Extensions provide
 * concrete subclasses.
 * </p>
 * 
 * @author Claas Wilke
 */
public class IconProvider extends net.java.treaty.eclipse.views.IconProvider {

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.eclipse.views.IconProvider#getIcon(java.net.URI,
	 * boolean)
	 */
	protected org.eclipse.swt.graphics.Image getIcon(URI type, boolean isVariable) {

		String typeName = type.toString();
		ImageDescriptor imageDescriptor = null;

		if (OWLVocabulary.TYPE_NAME_ONTOLOGY.equals(typeName)) {

			if (isVariable) {
				imageDescriptor = getImageDescriptor("icons/ontology_var.gif");
			}

			else {
				imageDescriptor = getImageDescriptor("icons/ontology.gif");
			}
		}
		// no else.

		if (imageDescriptor != null) {
			return imageDescriptor.createImage();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.eclipse.views.IconProvider#getIconURL(java.net.URI,
	 * boolean)
	 */
	protected URL getIconURL(URI type, boolean isVariable) {

		URL result;
		result = null;

		String typeName = type.toString();

		if (OWLVocabulary.TYPE_NAME_ONTOLOGY.equals(typeName)) {

			if (isVariable) {
				result =
						Activator.getDefault().getBundle().getResource(
								"icons/ontology_var.gif");
			}

			else {
				result =
						Activator.getDefault().getBundle()
								.getResource("icons/ontology.gif");
			}
		}
		// no else.

		return result;
	}

	/**
	 * <p>
	 * Returns an {@link ImageDescriptor} for a given path.
	 * </p>
	 * 
	 * @param path
	 *          The path for that an {@link ImageDescriptor} shall be returned.
	 * @return An {@link ImageDescriptor} for a given path.
	 */
	private ImageDescriptor getImageDescriptor(String path) {

		return AbstractUIPlugin
				.imageDescriptorFromPlugin(Activator.PLUGIN_ID, path);
	}
}