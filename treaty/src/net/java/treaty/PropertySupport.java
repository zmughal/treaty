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

import java.util.*;

/**
 * Interface for classes that can be annotated.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public abstract class PropertySupport implements Constraint {
	
	private Map<String,Object> properties = new HashMap<String,Object>();
	
	/* (non-Javadoc)
	 * @see net.java.treaty.Constraint#addProperty(java.lang.String, java.lang.Object)
	 */
	public void addProperty(String key, Object value) {
		this.properties.put(key,value);
	}

	/* (non-Javadoc)
	 * @see net.java.treaty.Constraint#removeProperty(java.lang.String)
	 */
	public Object removeProperty(String key) {
		return this.properties.remove(key);
	}
	
	/* (non-Javadoc)
	 * @see net.java.treaty.Constraint#getAnnotation(java.lang.String)
	 */
	public Object getAnnotation(String key) {
		return this.properties.get(key);
	}
	/* (non-Javadoc)
	 * @see net.java.treaty.Constraint#getPropertyNames()
	 */
	public Iterator<String> getPropertyNames() {
		return this.properties.keySet().iterator();
	}

}