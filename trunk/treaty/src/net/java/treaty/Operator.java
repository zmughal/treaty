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

import java.util.StringTokenizer;

/**
 * Operators to be used in property conditions.
 * TODO: complete list 
 * @author Jens Dietrich
 */

public abstract class Operator {
	

	
	static class EQ extends Operator {
		public boolean compare(Object o1,Object o2) {
			return o1==null?o2==null:o1.equals(o2);
		}
		public String getName() {
			return "=";
		}
	};
	static class NEQ extends Operator {
		public boolean compare(Object o1,Object o2) {
			return o1==null?o2!=null:!o1.equals(o2);
		}
		public String getName() {
			return "!=";
		}
	};
	static class IN extends Operator {
		public boolean compare(Object o1,Object o2) {
			if (o1==null) return false;
			if (o2==null) return false;
			String e = o1.toString();
			for (StringTokenizer tok=new StringTokenizer(o2.toString(),",");tok.hasMoreTokens();) {
				if (e.equals(tok.nextToken())) {
					return true;
				}
			}
			return false;
		}
		public String getName() {
			return "IN";
		}
	};
	
	public static Operator[] INSTANCES = {
			new EQ(),new NEQ(),new IN()		
		};
		
		public static Operator getInstance(String name) {
			for (Operator o:INSTANCES) {
				if (o.getName().equalsIgnoreCase(name)) {
					return o;
				}
			}
			return null;
		}
		
	
	public abstract boolean compare(Object o1,Object o2);
	public abstract String getName();
}
