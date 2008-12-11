/*
 * Copyright (C) 2008 Jens Dietrich
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
 * Interface for a general-purpose annotation mechanism.  
 * @author Jens Dietrich
 */


public interface Annotatable {

	/**
	 * Add a new property.public boolean isInstantiated()
	 * @param key
	 * @param value
	 */
	public abstract void setProperty(String key, Object value);

	/**
	 * Removes a property.
	 * @param key
	 * @return the value of the removed annotation or null if there is no such property
	 */
	public abstract Object removeProperty(String key);

	/**
	 * Get the annotation for a given key.
	 * @param key
	 * @return
	 */
	public abstract Object getProperty(String key);

	/**
	 * Get the property keys.
	 * @return an iterator
	 */
	public abstract Iterator<String> getPropertyNames();

	
}