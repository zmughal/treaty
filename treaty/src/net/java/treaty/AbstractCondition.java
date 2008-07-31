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
import java.util.Map;

/**
 * Abstract superclass for simple and complex conditions.
 * @author Jens Dietrich
 */
public abstract class AbstractCondition extends PropertySupport implements Visitable,Constraint {

	public AbstractCondition() {
		super();
	}
	
	/**
	 * Replace the resources referenced in conditions by the resources in the map.
	 * The original condition will not be changed, a new condition will be returned.
	 * @param resources a map of resources
	 * @return a condition
	 */
	
	public abstract AbstractCondition replaceResources(Map<String,Resource> resources) ; 



}
