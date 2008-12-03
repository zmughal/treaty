/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */
package net.java.treaty.eclipse.vocabulary.xml.ui;

import java.net.URI;

import net.java.treaty.eclipse.vocabulary.xml.XMLVocabulary;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Provider for icons representing types in user interfaces.
 * Extensions provide concrete subclasses.
 * @author Jens Dietrich
 */

public class IconProvider extends net.java.treaty.eclipse.views.IconProvider {
	
	/**
	 * Get the icon
	 * @param type the type
	 * @param isVariable whether the resource is a variable
	 * @return
	 */
	public Image getIcon(URI type,boolean isVariable) {
		String t = type.toString();
		ImageDescriptor d = null;
		if (XMLVocabulary.SCHEMA.equals(t)) {
			d = isVariable?getImageDescriptor("icons/xsd_var.gif"):getImageDescriptor("icons/xsd.gif");
		}
		else if (XMLVocabulary.INSTANCE.equals(t)) {
			d = isVariable?getImageDescriptor("icons/xml_var.gif"):getImageDescriptor("icons/xml.gif");
		}
		if (d!=null) {
			return d.createImage();
		}
		return null;
	}
	
	private ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, path); 
	}
}