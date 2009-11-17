/*
 * Copyright (C) 2008-2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty;

import java.net.URI;
import java.util.List;

/**
 * <p>
 * Utility to resolve resource references suing component meta-data meta data.
 * </p>
 * 
 * @author Jens Dietrich
 */
public interface ResourceManager {

	/**
	 * <p>
	 * Returns a {@link List} of {@link InstantiationContext}s. Using
	 * {@link InstantiationContext}s allows suppliers to define multiple
	 * extensions. I.e., there will be one {@link Contract} instance per context.
	 * If this is not supported by the {@link Component} model, a singleton
	 * containing the default context should be returned.
	 * </p>
	 * 
	 * @param connector
	 *          The {@link Connector}.
	 * @param contextDef
	 *          The context definition, e.g. an XPath expression defining a
	 *          context node in a configuration file like plugin.xml (in Eclipse).
	 * @return A {@link List} of {@link InstantiationContext}s.
	 * 
	 * @see InstantiationContext#DEFAULT_CONTEXT
	 */
	public List<InstantiationContext> getInstantiationContexts(
			Connector connector, String contextDefinition)
			throws ResourceLoaderException;

	/**
	 * <p>
	 * Resolves a {@link Resource} reference and returns the {@link Resource}'s
	 * name. This is usually done by looking up meta-data / component
	 * configuration files. After this method has been called successfully, the
	 * resource name will be <code>!=null</code>.
	 * </p>
	 * 
	 * @param type
	 *          The type (as a {@link URI}) of the {@link Resource} being
	 *          resolved.
	 * @param reference
	 *          A reference to the object, e.g. an XPath expression or a property
	 *          name in a meta-data file.
	 * @param connector
	 *          The {@link Connector} owning the resource.
	 * @param context
	 *          The {@link InstantiationContext} used.
	 * @return The {@link Resource}'s name, e.g. a {@link Class} name.
	 * @throws ResourceLoaderException
	 *           Thrown, if the resolving process fails.
	 */
	public String resolve(URI type, String reference, Connector connector,
			InstantiationContext context) throws ResourceLoaderException;
}