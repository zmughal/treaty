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
 * Interface to organise a map associating resources with resource instances by context.
 * For instance, assume that resource refs are XPath expressions like /actionset/action/@name and 
 * /actionset/action/@class.
 * Then a context manager might group all resource instances together that have the same action
 * (the context could be described by the XPath expression actionset/action.  
 * @author Jens Dietrich
 */

public interface ContextManager {
	
	/**
	 * Take a map of resource instantiations, and split into several maps. Each association in the original
	 * map is exactly in one of the values in the result map. All resource instantiations with the same context
	 * are in the same map.
	 * @param a map of resource instantiations
	 * @return a map associating context names with resource instantiations
	 * @throws ContextManagerException
	 */
	public Map<String,Map<Resource,Collection<Resource>>> cluster(Map<Resource,Collection<Resource>> instances) throws ContextManagerException;


	
}
