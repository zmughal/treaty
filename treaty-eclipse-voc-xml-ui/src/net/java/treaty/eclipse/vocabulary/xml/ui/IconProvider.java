/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */
package net.java.treaty.eclipse.vocabulary.xml.ui;

import java.net.URI;

import net.java.treaty.vocabulary.builtins.xml.XMLVocabulary;

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

	/**
	 * <p>
	 * Returns the icon for a given type (as {@link URI}).
	 * </p>
	 * 
	 * @param type
	 *          The type (as {@link URI}) whose icon shall be returned.
	 * @param isVariable
	 *          Indicates whether or not the resource is a variable.
	 * @return The icon for a given type (as {@link URI}).
	 */
	public Image getIcon(URI type, boolean isVariable) {

		String typeName;
		typeName = type.toString();

		ImageDescriptor imageDescriptor;
		imageDescriptor = null;

		if (XMLVocabulary.TYPE_NAME_XML_SCHEMA.equals(typeName)) {
			imageDescriptor =
					isVariable ? getImageDescriptor("icons/xsd_var.gif")
							: getImageDescriptor("icons/xsd.gif");
		}

		else if (XMLVocabulary.TYPE_NAME_XML_INSTANCE.equals(typeName)) {
			imageDescriptor =
					isVariable ? getImageDescriptor("icons/xml_var.gif")
							: getImageDescriptor("icons/xml.gif");
		}

		else if (XMLVocabulary.TYPE_NAME_DTD.equals(typeName)) {
			imageDescriptor =
					isVariable ? getImageDescriptor("icons/dtd_var.gif")
							: getImageDescriptor("icons/dtd.gif");
		}
		// no else.

		if (imageDescriptor != null) {
			return imageDescriptor.createImage();
		}

		else {
			return null;
		}
	}

	/**
	 * <p>
	 * Returns an {@link ImageDescriptor} for a given path.
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