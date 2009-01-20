/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.defaultexporter;

import java.util.List;

/**
 * Utility class to hold contract data.
 * @author Jens Dietrich
 */

public class ContractData {
	
	public String xp = null;
	public String x = null;
	public boolean isAggregated = false;
	public String result = null;
	public String exception = null;
	public List<String> conditionResults = null;
	public List<String> bindings = null;
	
	public List<String> getBindings() {
		return bindings;
	}
	public List<String> getConditionResults() {
		return conditionResults;
	}
	public String getException() {
		return exception;
	}
	public String getResult() {
		return result;
	}
	public String getXp() {
		return xp;
	}
	public String getX() {
		return x;
	}
	public boolean isAggregated() {
		return isAggregated;
	}

}
