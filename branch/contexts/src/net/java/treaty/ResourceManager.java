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



/**
 * Utility to resolve resource references suing component meta-data meta data.
 * @author Jens Dietrich
 */

public interface ResourceManager {
	/**
	 * Resolve a resource reference and return the resource name.
	 * This is usually done by looking up meta-data / component configuration files.
	 * After this method has been called successfully, the resource name will be !=null.
	 * @param type a type
	 * @param ref a reference to the object, e.g. an xpath expression or a property name in a meta data file
	 * @param connector the connector
	 * @return the resource name, e.g. a class name
	 * @throws VerificationException
	 */
	public String resolve(URI type,String ref,Connector connector) throws ResourceLoaderException;


	
}
