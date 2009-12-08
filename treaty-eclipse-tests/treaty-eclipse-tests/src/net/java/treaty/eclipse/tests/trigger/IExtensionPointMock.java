/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.tests.trigger;

import net.java.treaty.eclipse.tests.Activator;

import org.eclipse.core.runtime.ContributorFactoryOSGi;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IPluginDescriptor;
import org.eclipse.core.runtime.InvalidRegistryObjectException;

/**
 * <p>
 * Mock implementation to run tests with {@link IExtensionPoint}s.
 * 
 * @author Claas Wilke
 */

@SuppressWarnings("deprecation")
public class IExtensionPointMock implements IExtensionPoint {

	/** The ID of this {@link IExtensionPoint}. */
	private String id;

	/**
	 * <p>
	 * Creates a new {@link IExtensionPointMock}.
	 * </p>
	 * 
	 * @param id
	 *          The ID of this {@link IExtensionPoint}.
	 */
	public IExtensionPointMock(String id) {

		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionPoint#getConfigurationElements()
	 */
	public IConfigurationElement[] getConfigurationElements()
			throws InvalidRegistryObjectException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionPoint#getContributor()
	 */
	public IContributor getContributor() throws InvalidRegistryObjectException {

		/* All IExtensionPointMocks belong to this bundle. */
		return ContributorFactoryOSGi.createContributor(Activator.getDefault()
				.getBundle());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.core.runtime.IExtensionPoint#getDeclaringPluginDescriptor()
	 */
	public IPluginDescriptor getDeclaringPluginDescriptor()
			throws InvalidRegistryObjectException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.core.runtime.IExtensionPoint#getExtension(java.lang.String)
	 */
	public IExtension getExtension(String extensionId)
			throws InvalidRegistryObjectException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionPoint#getExtensions()
	 */
	public IExtension[] getExtensions() throws InvalidRegistryObjectException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionPoint#getLabel()
	 */
	public String getLabel() throws InvalidRegistryObjectException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionPoint#getNamespace()
	 */
	public String getNamespace() throws InvalidRegistryObjectException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionPoint#getNamespaceIdentifier()
	 */
	public String getNamespaceIdentifier() throws InvalidRegistryObjectException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionPoint#getSchemaReference()
	 */
	public String getSchemaReference() throws InvalidRegistryObjectException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionPoint#getSimpleIdentifier()
	 */
	public String getSimpleIdentifier() throws InvalidRegistryObjectException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionPoint#getUniqueIdentifier()
	 */
	public String getUniqueIdentifier() throws InvalidRegistryObjectException {

		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionPoint#isValid()
	 */
	public boolean isValid() {

		return true;
	}
}