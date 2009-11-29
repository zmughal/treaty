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
import java.net.URISyntaxException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import net.java.treaty.ContractLogger;

/**
 * <p>
 * Operators to be used in property conditions.
 * </p>
 * 
 * TODO: complete list
 * 
 * @author Jens Dietrich
 */
public abstract class BuiltInOperator {

	/** The name of the built-in name space. */
	public static final String BUILTIN_NAME_SPACE =
			"http://www.treaty.org/builtin/#";

	/** Equal operator. */
	static class EQ extends BuiltInOperator {

		/** The URI of this {@link BuiltInOperator}. */
		private URI myURI = buildURI("eq");

		/*
		 * (non-Javadoc)
		 * @see
		 * net.java.treaty.vocabulary.builtins.BuiltInOperator#compare(java.lang
		 * .Object, java.lang.Object)
		 */
		public boolean compare(Object o1, Object o2) {

			return o1 == null ? o2 == null : o1.equals(o2);
		}

		/*
		 * (non-Javadoc)
		 * @see net.java.treaty.vocabulary.builtins.BuiltInOperator#getName()
		 */
		public String getName() {

			return "=";
		}

		/*
		 * (non-Javadoc)
		 * @see net.java.treaty.vocabulary.builtins.BuiltInOperator#getURI()
		 */
		public URI getURI() {

			return this.myURI;
		}
	};

	/** Not-Equal operator. */
	static class NEQ extends BuiltInOperator {

		/** The URI of this {@link BuiltInOperator}. */
		private URI myURI = buildURI("neq");

		/*
		 * (non-Javadoc)
		 * @see
		 * net.java.treaty.vocabulary.builtins.BuiltInOperator#compare(java.lang
		 * .Object, java.lang.Object)
		 */
		public boolean compare(Object o1, Object o2) {

			return o1 == null ? o2 != null : !o1.equals(o2);
		}

		/*
		 * (non-Javadoc)
		 * @see net.java.treaty.vocabulary.builtins.BuiltInOperator#getName()
		 */
		public String getName() {

			return "!=";
		}

		/*
		 * (non-Javadoc)
		 * @see net.java.treaty.vocabulary.builtins.BuiltInOperator#getURI()
		 */
		public URI getURI() {

			return this.myURI;
		}
	};

	/** in operator. */
	static class IN extends BuiltInOperator {

		/** The URI of this {@link BuiltInOperator}. */
		private URI myURI = buildURI("in");

		/*
		 * (non-Javadoc)
		 * @see
		 * net.java.treaty.vocabulary.builtins.BuiltInOperator#compare(java.lang
		 * .Object, java.lang.Object)
		 */
		public boolean compare(Object o1, Object o2) {

			if (o1 == null)
				return false;
			if (o2 == null)
				return false;
			String e = o1.toString();
			for (StringTokenizer tok = new StringTokenizer(o2.toString(), ","); tok
					.hasMoreTokens();) {
				if (e.equals(tok.nextToken().trim())) {
					return true;
				}
			}
			return false;
		}

		/*
		 * (non-Javadoc)
		 * @see net.java.treaty.vocabulary.builtins.BuiltInOperator#getName()
		 */
		public String getName() {

			return "IN";
		}

		/*
		 * (non-Javadoc)
		 * @see net.java.treaty.vocabulary.builtins.BuiltInOperator#getURI()
		 */
		public URI getURI() {

			return this.myURI;
		}
	};

	/** Regex operator. */
	static class REGEX extends BuiltInOperator {

		/** The URI of this {@link BuiltInOperator}. */
		private URI myURI = buildURI("matches");

		/*
		 * (non-Javadoc)
		 * @see
		 * net.java.treaty.vocabulary.builtins.BuiltInOperator#compare(java.lang
		 * .Object, java.lang.Object)
		 */
		public boolean compare(Object o1, Object o2) {

			if (!(o1 instanceof String)) {
				throw new IllegalArgumentException(
						"the first parameter must be a string");
			}
			if (!(o2 instanceof String)) {
				throw new IllegalArgumentException(
						"the second parameter must be a string");
			}
			return Pattern.matches((String) o2, (String) o1);

		}

		/*
		 * (non-Javadoc)
		 * @see net.java.treaty.vocabulary.builtins.BuiltInOperator#getName()
		 */
		public String getName() {

			return "matches";
		}

		/*
		 * (non-Javadoc)
		 * @see net.java.treaty.vocabulary.builtins.BuiltInOperator#getURI()
		 */
		public URI getURI() {

			return this.myURI;
		}
	};

	/** Less-Than operator. */
	static class LT extends BuiltInOperator {

		/** The URI of this {@link BuiltInOperator}. */
		private URI myURI = buildURI("lt");

		/*
		 * (non-Javadoc)
		 * @see
		 * net.java.treaty.vocabulary.builtins.BuiltInOperator#compare(java.lang
		 * .Object, java.lang.Object)
		 */
		public boolean compare(Object o1, Object o2) {

			double d1 = toDouble(o1);
			double d2 = toDouble(o2);
			return d1 < d2;
		}

		/*
		 * (non-Javadoc)
		 * @see net.java.treaty.vocabulary.builtins.BuiltInOperator#getName()
		 */
		public String getName() {

			return "lt";
		}

		/*
		 * (non-Javadoc)
		 * @see net.java.treaty.vocabulary.builtins.BuiltInOperator#getURI()
		 */
		public URI getURI() {

			return this.myURI;
		}
	};

	/** Less-Than-Equal operator. */
	static class LTE extends BuiltInOperator {

		/** The URI of this {@link BuiltInOperator}. */
		private URI myURI = buildURI("lte");

		/*
		 * (non-Javadoc)
		 * @see
		 * net.java.treaty.vocabulary.builtins.BuiltInOperator#compare(java.lang
		 * .Object, java.lang.Object)
		 */
		public boolean compare(Object o1, Object o2) {

			double d1 = toDouble(o1);
			double d2 = toDouble(o2);
			return d1 <= d2;
		}

		/*
		 * (non-Javadoc)
		 * @see net.java.treaty.vocabulary.builtins.BuiltInOperator#getName()
		 */
		public String getName() {

			return "lte";
		}

		/*
		 * (non-Javadoc)
		 * @see net.java.treaty.vocabulary.builtins.BuiltInOperator#getURI()
		 */
		public URI getURI() {

			return this.myURI;
		}
	};

	/** Greater-Than operator. */
	static class GT extends BuiltInOperator {

		/** The URI of this {@link BuiltInOperator}. */
		private URI myURI = buildURI("gt");

		/*
		 * (non-Javadoc)
		 * @see
		 * net.java.treaty.vocabulary.builtins.BuiltInOperator#compare(java.lang
		 * .Object, java.lang.Object)
		 */
		public boolean compare(Object o1, Object o2) {

			double d1 = toDouble(o1);
			double d2 = toDouble(o2);
			return d1 > d2;
		}

		/*
		 * (non-Javadoc)
		 * @see net.java.treaty.vocabulary.builtins.BuiltInOperator#getName()
		 */
		public String getName() {

			return "gt";
		}

		/*
		 * (non-Javadoc)
		 * @see net.java.treaty.vocabulary.builtins.BuiltInOperator#getURI()
		 */
		public URI getURI() {

			return this.myURI;
		}
	};

	/** Greater-Than operator. */
	static class GTE extends BuiltInOperator {

		/** The URI of this {@link BuiltInOperator}. */
		private URI myURI = buildURI("gte");

		/*
		 * (non-Javadoc)
		 * @see
		 * net.java.treaty.vocabulary.builtins.BuiltInOperator#compare(java.lang
		 * .Object, java.lang.Object)
		 */
		public boolean compare(Object o1, Object o2) {

			double d1 = toDouble(o1);
			double d2 = toDouble(o2);
			return d1 >= d2;
		}

		/*
		 * (non-Javadoc)
		 * @see net.java.treaty.vocabulary.builtins.BuiltInOperator#getName()
		 */
		public String getName() {

			return "gte";
		}

		/*
		 * (non-Javadoc)
		 * @see net.java.treaty.vocabulary.builtins.BuiltInOperator#getURI()
		 */
		public URI getURI() {

			return this.myURI;
		}
	};

	/**
	 * <p>
	 * Evaluates this operator on two given {@link Object}s that shall be
	 * compared.
	 * 
	 * @param object1
	 * @param object2
	 * @return <code>true</code>, if the operator results in <code>true</code>.
	 */
	public abstract boolean compare(Object object1, Object object2);

	/**
	 * <p>
	 * Returns the name of this {@link BuiltInOperator}.
	 * </p>
	 * 
	 * @return The name of this {@link BuiltInOperator}.
	 */
	public abstract String getName();

	/**
	 * <p>
	 * Returns the {@link URI} of this {@link BuiltInOperator}.
	 * </p>
	 * 
	 * @return The {@link URI} of this {@link BuiltInOperator}.
	 */
	public abstract URI getURI();

	/**
	 * <p>
	 * A helper method that builds a {@link URI} for a given {@link String}.
	 * </p>
	 * 
	 * @param string
	 *          The {@link String}.
	 * @return The resulting {@link URI} or <code>null</code> if an
	 *         {@link Exception} occurred.
	 */
	private static URI buildURI(String string) {
	
		URI result;
	
		try {
			result = new URI(BUILTIN_NAME_SPACE + string);
		}
	
		catch (URISyntaxException e) {
	
			ContractLogger.LOGGER.error(
					"Error during creating URI in BuiltInOperator.", e);
	
			result = null;
		}
		// end catch.
	
		return result;
	}

	/**
	 * <p>
	 * A helper method that parses a given {@link Object} into a {@link Double}.
	 * </p>
	 * 
	 * @param object
	 *          The {@link Object} that shall be converted.
	 * @return The resulting {@link Double}.
	 * @throws IllegalArgumentException
	 *           thrown, if the converting is not possible.
	 */
	private static double toDouble(Object object) throws IllegalArgumentException {

		double result;

		if (object instanceof String) {
			result = Double.parseDouble((String) object);
		}

		else if (object instanceof Double) {
			result = (Double) object;
		}

		else if (object instanceof Integer) {
			result = ((Integer) object).doubleValue();
		}

		/* Else throw an exception. */
		else {
			throw new IllegalArgumentException("double parameter expected here");
		}

		return result;
	}
}