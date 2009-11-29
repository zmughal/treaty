/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.vocabulary.builtins;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import net.java.treaty.vocabulary.builtins.BuiltInOperator.EQ;
import net.java.treaty.vocabulary.builtins.BuiltInOperator.GT;
import net.java.treaty.vocabulary.builtins.BuiltInOperator.GTE;
import net.java.treaty.vocabulary.builtins.BuiltInOperator.IN;
import net.java.treaty.vocabulary.builtins.BuiltInOperator.LT;
import net.java.treaty.vocabulary.builtins.BuiltInOperator.LTE;
import net.java.treaty.vocabulary.builtins.BuiltInOperator.NEQ;
import net.java.treaty.vocabulary.builtins.BuiltInOperator.REGEX;

/**
 * <p>
 * A registry for {@link BuiltInOperator}s.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class BuiltInOperators {

	/** The singleton Instance of {@link BuiltInOperators}. */
	public static final BuiltInOperators INSTANCE = new BuiltInOperators();

	/** All {@link BuiltInOperator}s as an Array. */
	private BuiltInOperator[] operators =
			{ new EQ(), new NEQ(), new IN(), new REGEX(), new LT(), new GT(),
					new LTE(), new GTE() };

	/** The {@link BuiltInOperator}s mapped by their name. */
	private Map<String, BuiltInOperator> operatorsByName = null;

	/** The {@link BuiltInOperator}s mapped by their {@link URI}. */
	private Map<URI, BuiltInOperator> operatorsByURI = null;

	/**
	 * <p>
	 * Private constructor for singleton pattern.
	 * </p>
	 */
	private BuiltInOperators() {

		super();

		this.operatorsByName = new HashMap<String, BuiltInOperator>();
		this.operatorsByURI = new HashMap<URI, BuiltInOperator>();

		for (BuiltInOperator operator : this.operators) {
			this.operatorsByName.put(operator.getName(), operator);
			this.operatorsByURI.put(operator.getURI(), operator);
		}
		// no else.
	}

	/**
	 * <p>
	 * Returns the {@link BuiltInOperator} for a given name or <code>null</code>
	 * if the {@link BuiltInOperator} does not exsist.
	 * </p>
	 * 
	 * @param name
	 *          The name of the {@link BuiltInOperator} that shall be returned.
	 * @return The {@link BuiltInOperator} or <code>null</code>.
	 */
	public BuiltInOperator getInstance(String name) {

		return this.operatorsByName.get(name);
	}

	/**
	 * <p>
	 * Returns the {@link BuiltInOperator} for a given {@link URI} or
	 * <code>null</code> if the {@link BuiltInOperator} does not exsist.
	 * </p>
	 * 
	 * @param id
	 *          The ID of the {@link BuiltInOperator} that shall be returned.
	 * @return The {@link BuiltInOperator} or <code>null</code>.
	 */
	public BuiltInOperator getInstance(URI id) {

		return this.operatorsByURI.get(id);
	}

	/**
	 * <p>
	 * Returns the {@link URI} of a given {@link BuiltInOperator}'s name or
	 * <code>null</code> if the {@link BuiltInOperator} does not exsist.
	 * </p>
	 * 
	 * @param name
	 *          The name of the {@link BuiltInOperator}.
	 * @return The {@link URI} or <code>null</code>.
	 */
	public URI getURI(String name) {

		URI result;

		BuiltInOperator operator;
		operator = this.operatorsByName.get(name);

		if (operator == null) {
			result = null;
		}

		else {
			result = operator.getURI();
		}

		return result;
	}

	/**
	 * <p>
	 * Returns the {@link URI}s of all {@link BuiltInOperator}s.
	 * </p>
	 * 
	 * @return The {@link URI}s of all {@link BuiltInOperator}s.
	 */
	public Collection<URI> getOpIds() {

		return this.operatorsByURI.keySet();
	}
}