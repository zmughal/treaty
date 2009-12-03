/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */
package net.java.treaty.eclipse.vocabulary.java.ui;

import java.net.URI;

import net.java.treaty.vocabulary.builtins.java.JavaVocabulary;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * <p>
 * Provider for icons representing types in user interfaces. Extensions provide
 * concrete subclasses.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class IconProvider extends net.java.treaty.eclipse.views.IconProvider {

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.eclipse.views.IconProvider#getIcon(java.net.URI,
	 * boolean)
	 */
	public Image getIcon(URI type, boolean isVariable) {

		Image result;

		String typeURI;
		typeURI = type.toString();

		ImageDescriptor imageDescriptor = null;

		if (JavaVocabulary.TYPE_INSTANTIABLE_CLASS.equals(typeURI)) {

			if (isVariable) {
				imageDescriptor = this.getImageDescriptor("icons/class_var.gif");
			}

			else {
				imageDescriptor = this.getImageDescriptor("icons/class.gif");
			}
		}
		else if (JavaVocabulary.TYPE_ABSTRACT_TYPE.equals(typeURI)) {

			if (isVariable) {
				imageDescriptor = this.getImageDescriptor("icons/interface_var.gif");
			}

			else {
				imageDescriptor = this.getImageDescriptor("icons/interface.gif");
			}
		}

		if (imageDescriptor != null) {
			result = imageDescriptor.createImage();
		}

		else {
			result = null;
		}

		return result;
	}

	/**
	 * <p>
	 * Returns the {@link ImageDescriptor} for a given path.
	 * </p>
	 * 
	 * @param path
	 *          The path whose {@link ImageDescriptor} shall be returned.
	 * @return The {@link ImageDescriptor} for a given path.
	 */
	private ImageDescriptor getImageDescriptor(String path) {

		return AbstractUIPlugin
				.imageDescriptorFromPlugin(Activator.PLUGIN_ID, path);
	}
}