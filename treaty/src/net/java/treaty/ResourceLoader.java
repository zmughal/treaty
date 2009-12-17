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

/**
 * <p>
 * Utility to load {@link Resource}s.
 * </p>
 * 
 * @author Jens Dietrich
 */
public interface ResourceLoader {

	/**
	 * <p>
	 * Loads a {@link Resource}. For instance, if the {@link Resource} is a
	 * {@link Class} this method will return the {@link Class} object loaded with
	 * the {@link ClassLoader} of the {@link Component} owning the
	 * {@link Connector}. After this method has been called successfully, the
	 * resource value will be <code>!= null</code>.
	 * </p>
	 * 
	 * @param type
	 *          The type (as a {@link URI}) of the {@link Resource} to be loaded.
	 * @param name
	 *          The {@link Resource}'s name, e.g. a {@link Class}' name.
	 * @param connector
	 *          The {@link Connector} owning the resource, e.g. it may provide the
	 *          required {@link ClassLoader}.
	 * @param checkType
	 *          If <code>true</code> the given type of the {@link Resource} will
	 *          be checked.
	 * @return The {@link Object} created for the name, e.g. an instance of
	 *         {@link Class}.
	 * @throws ResourceLoaderException
	 *           Thrown, if the loading process fails.
	 */
	public Object load(URI type, String name, Connector connector)
			throws ResourceLoaderException;
}