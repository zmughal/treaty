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
package net.java.treaty.eclipse.vocabulary.ocl.context;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.type.PrimitiveType;

import net.java.treaty.dynamic.OperationInvocationContext;
import tudresden.ocl20.pivot.metamodels.java.JavaMetaModelPlugin;
import tudresden.ocl20.pivot.modelbus.IModelBusConstants;
import tudresden.ocl20.pivot.modelbus.IModelInstance;
import tudresden.ocl20.pivot.modelbus.IModelObject;
import tudresden.ocl20.pivot.modelbus.ModelAccessException;
import tudresden.ocl20.pivot.modelinstancetype.java.JavaModelInstanceTypePlugin;
import tudresden.ocl20.pivot.pivotmodel.PrimitiveTypeKind;

/**
 * <p>
 * The interface for factories that adapt a given
 * {@link OperationInvocationContext} to to {@link OclInterpretationContext} for
 * Java {@link IModelInstance}s.
 * </p>
 * 
 * @author Claas Wilke
 */
public class JavaContextAdpaterService extends AbstractContextAdpaterService {

	/**
	 * <p>
	 * Crates a new {@link JavaContextAdpaterService}.
	 * </p>
	 * 
	 * @param modelInstance
	 *          The {@link IModelInstance} this {@link IContextAdapterService}
	 *          shall adapt to.
	 */
	public JavaContextAdpaterService(IModelInstance modelInstance) {

		super(modelInstance);
	}

	/**
	 * <p>
	 * Adapts a given {@link Object} to an {@link IModelObject} of the current
	 * {@link IModelInstance}.
	 * </p>
	 * 
	 * @param source
	 *          The {@link Object} that shall be adapted.
	 * @return The adapted {@link IModelObject}.
	 */
	protected IModelObject adaptObjectToModelObject(Object anObject) {

		IModelObject result;

		if (anObject != null) {

			try {
				result =
						JavaModelInstanceTypePlugin.addModelObjectToInstance(anObject,
								this.myModelInstance);
			}

			catch (ModelAccessException e) {
				result = null;
			}

		}

		/* Given Object is null. */
		else {
			result = null;
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.eclipse.vocabulary.ocl.context.AbstractContextAdpaterService
	 * #convertQualifiedName(java.lang.String)
	 */
	protected List<String> convertQualifiedName(String aQualifiedName) {

		List<String> result;
		String aPrimitiveName;

		result = new ArrayList<String>();

		/* Convert into a PrimitiveType name. */
		aPrimitiveName = this.convertPrimitiveTypeName(aQualifiedName);

		/* If no primitiveType name has found, create the qualified path. */
		if (aPrimitiveName.equals(aQualifiedName)) {
			/* Split the canonical name into name spaces. */
			for (String aNameSpacesName : aQualifiedName.split("\\.")) {
				result.add(aNameSpacesName);
			}

			/* Add the name of the root package. */
			result.add(0, IModelBusConstants.ROOT_PACKAGE_NAME);
		}

		/* Else return the name of the PrimitiveType. */
		else {
			result.add(aPrimitiveName);
		}

		return result;
	}

	/**
	 * <p>
	 * A helper method that converts a given canonical name into the name of a
	 * {@link PrimitiveType} of the pivot model, if the given name can be adapted
	 * to a {@link PrimitiveType}. Else the given qualifiedName is returned again.
	 * </p>
	 * 
	 * @param aQualifiedName
	 *          A given qualified name (a canonical Java {@link Class}' name).
	 * @return The name of a {@link PrimitiveType} or the given qualified name.
	 */
	private String convertPrimitiveTypeName(String aQualifiedName) {

		/* Search in the Boolean types. */
		for (Class<?> aBooleanClass : JavaMetaModelPlugin.BOOLEAN_CLASSES) {
			if (aBooleanClass.getCanonicalName().equals(aQualifiedName)) {
				return PrimitiveTypeKind.BOOLEAN.getName();
			}
		}

		/* Search in the Integer types. */
		for (Class<?> anIntegerClass : JavaMetaModelPlugin.INTEGER_CLASSES) {
			if (anIntegerClass.getCanonicalName().equals(aQualifiedName)) {
				return PrimitiveTypeKind.INTEGER.getName();
			}
		}

		/* Search in the Real types. */
		for (Class<?> aRealClass : JavaMetaModelPlugin.REAL_CLASSES) {
			if (aRealClass.getCanonicalName().equals(aQualifiedName)) {
				return PrimitiveTypeKind.REAL.getName();
			}
		}

		/* Search in the String types. */
		for (Class<?> aStringClass : JavaMetaModelPlugin.STRING_CLASSES) {
			if (aStringClass.getCanonicalName().equals(aQualifiedName)) {
				return PrimitiveTypeKind.STRING.getName();
			}
		}

		/* Else return the name itself. */
		return aQualifiedName;
	}
}