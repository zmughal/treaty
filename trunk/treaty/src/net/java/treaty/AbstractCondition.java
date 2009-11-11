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

import java.util.Map;

/**
 * <p>
 * Abstract superclass for simple and complex conditions.
 * </p>
 * 
 * @author Jens Dietrich
 */
public abstract class AbstractCondition extends PropertySupport implements
		Visitable, Constraint {

	/**
	 * <p>
	 * Creates a new {@link AbstractCondition}.
	 * </p>
	 */
	public AbstractCondition() {

		super();
	}

	/**
	 * <p>
	 * Replaces the {@link Resource}s referenced in conditions by the
	 * {@link Resource}s in the given {@link Map}. The original
	 * {@link AbstractCondition} will not be changed, a new
	 * {@link AbstractCondition} will be returned.
	 * </p>
	 * 
	 * @param resources
	 *          A {@link Map} of {@link Resource}s that shall be replaced.
	 */
	public abstract AbstractCondition replaceResources(
			Map<String, Resource> resources);
}