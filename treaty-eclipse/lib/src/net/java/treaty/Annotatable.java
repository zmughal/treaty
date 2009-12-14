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

import java.util.Iterator;

/**
 * <p>
 * Interface for a general-purpose annotation mechanism.
 * </p>
 * 
 * @author Jens Dietrich
 */
public interface Annotatable {

	/**
	 * <p>
	 * Returns the annotation for a given key.
	 * </p>
	 * 
	 * @param key
	 *          The key whose value shall be returned.
	 * @return The annotation for a given key.
	 */
	public abstract Object getProperty(String key);

	/**
	 * <p>
	 * Returns the keys of all properties currently set for this
	 * {@link Annotatable}.
	 * </p>
	 * 
	 * @return The names of all properties as an {@link Iterator}.
	 */
	public abstract Iterator<String> getPropertyNames();

	/**
	 * <p>
	 * Removes a property with a given key.
	 * </p>
	 * 
	 * @param key
	 *          The key of the property that shall be remove.
	 * @return The value of the removed annotation or <code>null</code> if there
	 *         is no such property.
	 */
	public abstract Object removeProperty(String key);

	/**
	 * <p>
	 * Adds a new property with a given key and value.
	 * </p>
	 * 
	 * @param key
	 *          The key of the property that shall be set.
	 * @param value
	 *          The value of the property that shall be set.
	 */
	public abstract void setProperty(String key, Object value);
}