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

import java.net.URI;

import net.java.treaty.verification.VerificationException;


/**
 * Utility to load resources.
 * @author Jens Dietrich
 */

public interface ResourceLoader {
	/**
	 * Load a resource. For instance, if the resource is a class this method will return the class
	 * object loaded with the classloader of the component owning the connector.
	 * After this method has been called successfully, the resource value will be !=null.
	 * @param type a type
	 * @param name the resource name, e.g. a class name
	 * @param connector the connector, e.g. it may provide the classloader
	 * @return the object created for the name, e.g. an instance of java.lang.Class
	 * @throws VerificationException
	 */
	public Object load(URI type,String name,Connector connector) throws ResourceLoaderException;


	
}
