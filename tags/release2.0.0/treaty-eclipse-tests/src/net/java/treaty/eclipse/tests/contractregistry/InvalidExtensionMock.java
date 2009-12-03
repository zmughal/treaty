/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.tests.contractregistry;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IPluginDescriptor;
import org.eclipse.core.runtime.InvalidRegistryObjectException;

/**
 * <p>
 * A mock implementing the {@link IExtension} interface with invalid behavior.
 * </p>
 * 
 * @author Claas Wilke
 */
@SuppressWarnings("deprecation")
public class InvalidExtensionMock implements IExtension {

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtension#getConfigurationElements()
	 */
	public IConfigurationElement[] getConfigurationElements()
			throws InvalidRegistryObjectException {

		throw new InvalidRegistryObjectException();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtension#getContributor()
	 */
	public IContributor getContributor() throws InvalidRegistryObjectException {

		throw new InvalidRegistryObjectException();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtension#getDeclaringPluginDescriptor()
	 */
	public IPluginDescriptor getDeclaringPluginDescriptor()
			throws InvalidRegistryObjectException {

		throw new InvalidRegistryObjectException();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.core.runtime.IExtension#getExtensionPointUniqueIdentifier()
	 */
	public String getExtensionPointUniqueIdentifier()
			throws InvalidRegistryObjectException {

		throw new InvalidRegistryObjectException();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtension#getLabel()
	 */
	public String getLabel() throws InvalidRegistryObjectException {

		throw new InvalidRegistryObjectException();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtension#getNamespace()
	 */
	public String getNamespace() throws InvalidRegistryObjectException {

		throw new InvalidRegistryObjectException();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtension#getNamespaceIdentifier()
	 */
	public String getNamespaceIdentifier() throws InvalidRegistryObjectException {

		throw new InvalidRegistryObjectException();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtension#getSimpleIdentifier()
	 */
	public String getSimpleIdentifier() throws InvalidRegistryObjectException {

		throw new InvalidRegistryObjectException();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtension#getUniqueIdentifier()
	 */
	public String getUniqueIdentifier() throws InvalidRegistryObjectException {

		throw new InvalidRegistryObjectException();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtension#isValid()
	 */
	public boolean isValid() {

		return false;
	}
}