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

/**
 * Operators to be used in property conditions.
 * TODO: complete list 
 * @author Jens Dietrich
 */

public abstract class BuiltInOperator {
	
	public static final String OP = "http://www.treaty.org/builtin/#";

	
	static class EQ extends BuiltInOperator {
		public boolean compare(Object o1,Object o2) {
			return o1==null?o2==null:o1.equals(o2);
		}
		public String getName() {
			return "=";
		}
		private URI uri = buildURI("eq");
		public URI getURI() {
			return uri;
		}
	};
	static class NEQ extends BuiltInOperator {
		public boolean compare(Object o1,Object o2) {
			return o1==null?o2!=null:!o1.equals(o2);
		}
		public String getName() {
			return "!=";
		}
		private URI uri = buildURI("neq");
		public URI getURI() {
			return uri;
		}
	};
	static class IN extends BuiltInOperator {
		public boolean compare(Object o1,Object o2) {
			if (o1==null) return false;
			if (o2==null) return false;
			String e = o1.toString();
			for (StringTokenizer tok=new StringTokenizer(o2.toString(),",");tok.hasMoreTokens();) {
				if (e.equals(tok.nextToken().trim())) {
					return true;
				}
			}
			return false;
		}
		public String getName() {
			return "IN";
		}
		private URI uri = buildURI("in");
		public URI getURI() {
			return uri;
		}
	};
	
	static class REGEX extends BuiltInOperator {
		public boolean compare(Object o1,Object o2) {
			if (!(o1 instanceof String)) {
				throw new IllegalArgumentException("the first parameter must be a string");
			}
			if (!(o2 instanceof String)) {
				throw new IllegalArgumentException("the second parameter must be a string");
			}
			return Pattern.matches((String)o2,(String)o1);
			
		}
		public String getName() {
			return "matches";
		}
		private URI uri = buildURI("matches");
		public URI getURI() {
			return uri;
		}
	};
	
	static class LT extends BuiltInOperator {
		public boolean compare(Object o1,Object o2) {
			double d1 = toDouble(o1);
			double d2 = toDouble(o2);
			return d1<d2;
		}
		public String getName() {
			return "lt";
		}
		private URI uri = buildURI("lt");
		public URI getURI() {
			return uri;
		}
	};
	static class LTE extends BuiltInOperator {
		public boolean compare(Object o1,Object o2) {
			double d1 = toDouble(o1);
			double d2 = toDouble(o2);
			return d1<=d2;
		}
		public String getName() {
			return "lte";
		}
		private URI uri = buildURI("lte");
		public URI getURI() {
			return uri;
		}
	};
	static class GT extends BuiltInOperator {
		public boolean compare(Object o1,Object o2) {
			double d1 = toDouble(o1);
			double d2 = toDouble(o2);
			return d1>d2;
		}
		public String getName() {
			return "gt";
		}
		private URI uri = buildURI("gt");
		public URI getURI() {
			return uri;
		}
	};
	static class GTE extends BuiltInOperator {
		public boolean compare(Object o1,Object o2) {
			double d1 = toDouble(o1);
			double d2 = toDouble(o2);
			return d1>=d2;
		}
		public String getName() {
			return "gte";
		}
		private URI uri = buildURI("lte");
		public URI getURI() {
			return uri;
		}
	};
	
	public abstract boolean compare(Object o1,Object o2);
	public abstract String getName();
	public abstract URI getURI();
	
	private static double toDouble(Object obj) {
		if (obj instanceof String) {
			return Double.parseDouble((String) obj);
		}
		else if (obj instanceof Double) {
			return (Double)obj;
		}
		else if (obj instanceof Integer) {
			return ((Integer)obj).doubleValue();
		}
		else throw new IllegalArgumentException("double parameter expected here");
	}
	
	private static URI buildURI(String string) {
		try {
			return new URI(OP+string);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
}
