/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */


package net.java.treaty.eclipse;

import java.net.URI;
import net.java.treaty.ResourceLoader;
import net.java.treaty.ResourceLoaderException;
/**
 * Utility to extract resources.
 * @author Jens Dietrich
 */

public class EclipseResourceLoader implements ResourceLoader {

	@Override
	public Object load(URI type, String name, net.java.treaty.Connector connector)	throws ResourceLoaderException {
		return null;
	}

}
