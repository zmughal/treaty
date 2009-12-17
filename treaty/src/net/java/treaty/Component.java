/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty;

import java.net.URL;
import java.util.List;

/**
 * <p>
 * Abstract component interface.
 * </p>
 * 
 * @author Jens Dietrich
 */
public interface Component extends Annotatable {

	/**
	 * <p>
	 * Returns all {@link Connector}s belonging to this {@link Component}.
	 * </p>
	 * 
	 * @return All {@link Connector}s belonging to this {@link Component}.
	 */
	List<Connector> getConnectors();

	/**
	 * <p>
	 * Returns a unique ID that can be used to identify this {@link Component}.
	 * </p>
	 * 
	 * @return A unique ID.
	 */
	String getId();

	/**
	 * <p>
	 * Returns the resource (as a {@link URL}) for the given name.
	 * </p>
	 * 
	 * @param path
	 *          The path whose resource shall be returned.
	 * @return The resource (as a {@link URL}).
	 */
	URL getResource(String path);

	/**
	 * <p>
	 * Loads a (Java) {@link Class} using the components {@link ClassLoader}. This
	 * method somehow contradicts the platform-independent design of treaty. It is
	 * therefore optional. E.g., clients should be prepared to handle an
	 * {@link UnsupportedOperationException}.
	 * 
	 * @param className
	 *          A fully qualified {@link Class} name.
	 * @return The loaded {@link Class} if found.
	 * @throws ClassNotFoundException
	 *           Thrown, if the given className cannot be found.
	 * @throws UnsupportedOperationException
	 *           Thrown, if this method is not supported by a Treaty
	 *           implementation.
	 */
	Class<?> loadClass(String className) throws ClassNotFoundException,
			UnsupportedOperationException;
}