/*
 * Copyright (C) 2008 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */


package nz.ac.massey.treaty;
import java.util.Map;

import nz.ac.massey.treaty.verification.ConditionVerifier;
import nz.ac.massey.treaty.verification.VerificationReport;

/**
 * Abstract superclass for simple and complex conditions.
 * @author <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</A>
 * @version 0.1 <27/04/2008>
 * @since 0.1
 */
public abstract class AbstractCondition implements Visitable {

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
	/**
	 * Check the condition.
	 * @param report a report that can be used to log details
	 * @param validator the validator that can be used to check atomic conditions
	 * @return whether the check suceeded
	 */
	public abstract boolean check(VerificationReport report,ConditionVerifier validator);


}
