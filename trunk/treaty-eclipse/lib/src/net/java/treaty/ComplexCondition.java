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

import java.util.ArrayList;
import java.util.List;

/**
 *<p>
 * Abstract superclass for complex conditions (conjunction, disjunction etc).
 * </p>
 * 
 * @author Jens Dietrich
 */
public abstract class ComplexCondition extends Condition implements
		ConditionContext {

	/** All parts ({@link Condition}s) of this {@link ComplexCondition}. */
	protected List<Condition> myParts =
			new ArrayList<Condition>();

	/**
	 * <p>
	 * Creates a new {@link ComplexCondition}.
	 * </p>
	 */
	public ComplexCondition() {

		super();
	}

	/**
	 * <p>
	 * Adds a given {@link Condition} to this {@link ComplexCondition}.
	 * </p>
	 * 
	 * @param condition
	 *          The {@link Condition} that shall be added.
	 */
	public void addCondition(Condition condition) {

		this.myParts.add(condition);
	}

	/**
	 * <p>
	 * Checks whether or not this {@link ComplexCondition} is instantiated. A
	 * {@link ComplexCondition} is instantiated if all its
	 * {@link Condition}s are instantiated.
	 * </p>
	 * 
	 * @return <code>true</code> if this {@link ComplexCondition} is instantiated.
	 */
	public boolean isInstantiated() {

		boolean result;
		result = true;

		for (Condition condition : this.myParts) {

			if (!condition.isInstantiated()) {
				result = false;
				break;
			}
			// no else.
		}
		// end for.

		return result;
	}

	/**
	 * <p>
	 * Returns the name of the logical connective used for this
	 * {@link ComplexCondition}.
	 * </p>
	 * 
	 * @return The name of the logical connective used for this
	 *         {@link ComplexCondition}.
	 */
	public abstract String getConnective();

	/**
	 * <p>
	 * Returns all parts (the {@link Condition}s of this
	 * {@link ComplexCondition}).
	 * </p>
	 * 
	 * @return The {@link Condition}s of this {@link ComplexCondition}
	 */
	public List<Condition> getParts() {

		return this.myParts;
	}
}