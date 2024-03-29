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

import java.util.*;

/**
 * <p>
 * Abstract superclass for classes that can be annotated.
 * </p>
 * 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public abstract class PropertySupport implements Annotatable {

	/** The annotated properties of this {@link PropertySupport}. */
	private Map<String, Object> myProperties = new HashMap<String, Object>();

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Constraint#getAnnotation(java.lang.String)
	 */
	public Object getProperty(String key) {

		return this.myProperties.get(key);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Constraint#getPropertyNames()
	 */
	public Iterator<String> getPropertyNames() {

		return this.myProperties.keySet().iterator();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Constraint#removeProperty(java.lang.String)
	 */
	public Object removeProperty(String key) {

		return this.myProperties.remove(key);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Constraint#addProperty(java.lang.String,
	 * java.lang.Object)
	 */
	public void setProperty(String key, Object value) {

		this.myProperties.put(key, value);
	}
}