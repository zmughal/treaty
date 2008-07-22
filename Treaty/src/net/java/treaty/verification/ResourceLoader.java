/*
 * Copyright (C) 2008 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.verification;

import java.net.URI;

import net.java.treaty.Component;


/**
 * Turns raw values into objects that represent the respective types.
 * @author Jens Dietrich
 */

public interface ResourceLoader {
	/**
	 * Create an object. 
	 * @param type a type
	 * @param name the value of the object as string, e.g. a class name
	 * @param plugin the plugin, e.g. it may provide the classloader
	 * @return the object created for the name, e.g. an instance of java.lang.Class
	 * @throws VerificationException
	 */
	public Object load(URI type,String name,Component plugin) throws VerificationException;

	
}
