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
 * Registry for built-in operators.
 * @author Jens Dietrich
 */

public class BuiltInOperators {
	
	public static final BuiltInOperators INSTANCE = new BuiltInOperators();
	
	private BuiltInOperator[] ops = {
		new EQ(),new NEQ(),new IN(), new REGEX(), new LT(), new GT(), new LTE(), new GTE()	
	};
	private Map<String,BuiltInOperator> opsByName = null;
	private Map<URI,BuiltInOperator> opsByURI = null;

	private BuiltInOperators() {
		super();
		opsByName = new HashMap<String,BuiltInOperator>();
		opsByURI = new HashMap<URI,BuiltInOperator>();		
		for (BuiltInOperator op:ops) {
			opsByName.put(op.getName(),op);
			opsByURI.put(op.getURI(),op);
		}
	}
	
	public BuiltInOperator getInstance(String name) {
		return this.opsByName.get(name);
	}
	public BuiltInOperator getInstance(URI id) {
		return this.opsByURI.get(id);
	}
	public URI getURI(String name) {
		BuiltInOperator op = this.opsByName.get(name);
		return op==null?null:op.getURI();
	}
	public Collection<URI> getOpIds() {
		return this.opsByURI.keySet();
	}
	
	
}
