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

import java.net.URI;

import net.java.treaty.eclipse.vocabulary.ocl.OCLVocabulary;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
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

	/**
	 * <p>
	 * Returns the {@link Image} icon for a given type (as {@link URI}) of the
	 * {@link OCLVocabulary}.
	 * </p>
	 * 
	 * @param aType
	 *            The type whose icon shall be returned.
	 * @param isVariable
	 *            Indicates whether or not the resource is a variable.
	 * @return The icon as an {@link Image}.
	 */
	public Image getIcon(URI aType, boolean isVariable) {

		Image result;

		String typeName;
		ImageDescriptor anImageDescricptor;

		typeName = aType.toString();
		anImageDescricptor = null;

		/* Get the image depending on the given type. */
		
		/* Model types. */
		if (OCLVocabulary.TYPE_MODEL_UML2.equals(typeName)) {

			if (isVariable) {
				anImageDescricptor = this
						.getImageDescriptor("icons/uml2_model_var.gif");
			}

			else {
				anImageDescricptor = this.getImageDescriptor("icons/uml2_model.gif");
			}
		}

		else if (OCLVocabulary.TYPE_MODEL_EMF_ECORE.equals(typeName)) {

			if (isVariable) {
				anImageDescricptor = this
						.getImageDescriptor("icons/ecore_model_var.gif");
			}

			else {
				anImageDescricptor = this.getImageDescriptor("icons/ecore_model.gif");
			}
		}

		else if (OCLVocabulary.TYPE_MODEL_JAVA.equals(typeName)) {

			if (isVariable) {
				anImageDescricptor = this
						.getImageDescriptor("icons/java_model_var.gif");
			}

			else {
				anImageDescricptor = this.getImageDescriptor("icons/java_model.gif");
			}
		}

		/* Model instance types. */
		else if (OCLVocabulary.TYPE_MODELINSTANCE_JAVA.equals(typeName)) {

			if (isVariable) {
				anImageDescricptor = this
						.getImageDescriptor("icons/java_instance_var.gif");
			}

			else {
				anImageDescricptor = this
						.getImageDescriptor("icons/java_instance.gif");
			}
		}

		/* Constraint type. */
		else if (OCLVocabulary.TYPE_OCL_FILE.equals(typeName)) {

			if (isVariable) {
				anImageDescricptor = this
						.getImageDescriptor("icons/ocl_var.gif");
			}

			else {
				anImageDescricptor = this.getImageDescriptor("icons/ocl.gif");
			}
		}

		/* Create the image. */
		if (anImageDescricptor != null) {
			result = anImageDescricptor.createImage();
		}

		else {
			result = null;
		}

		return result;
	}

	/**
	 * <p>
	 * A helper method to get the {@link ImageDescriptor} for a given path as
	 * {@link String}.
	 * </p>
	 * 
	 * @param path
	 *            The path whose {@link ImageDescriptor} shall be returned.
	 * @return The {@link ImageDescriptor} for the given path.
	 */
	private ImageDescriptor getImageDescriptor(String path) {
		
		ImageDescriptor result;
		
		result = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				path);
		
		return result;
	}
}