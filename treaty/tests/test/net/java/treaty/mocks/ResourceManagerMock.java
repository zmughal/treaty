/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package test.net.java.treaty.mocks;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import net.java.treaty.Connector;
import net.java.treaty.InstantiationContext;
import net.java.treaty.ResourceLoaderException;
import net.java.treaty.ResourceManager;

/**
 * <p>
 * A mock implementation of the {@link ResourceManager} to test the
 * {@link ContractRegistry}.
 * </p>
 * 
 * @author Claas Wilke
 */
public class ResourceManagerMock implements ResourceManager {

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ResourceManager#resolve(java.net.URI,
	 * java.lang.String, net.java.treaty.Connector,
	 * net.java.treaty.InstantiationContext)
	 */
	public String resolve(URI type, String ref, Connector connector,
			InstantiationContext context) throws ResourceLoaderException {

		// TODO
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ResourceManager#getInstantiationContexts(net.java.treaty
	 * .Connector, java.lang.String)
	 */
	public List<InstantiationContext> getInstantiationContexts(
			Connector connector, String contextDefinition)
			throws ResourceLoaderException {

		List<InstantiationContext> result;
		result = new ArrayList<InstantiationContext>();

		result.add(InstantiationContext.DEFAULT_CONTEXT);

		return result;
	}
}