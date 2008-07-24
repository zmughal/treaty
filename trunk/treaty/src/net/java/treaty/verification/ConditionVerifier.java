/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.verification;

import net.java.treaty.Condition;

/**
 * Algorithm used to check conditions.
 * Implementing classes provide the semantics for relationships and are usually provided by
 * extensions of the validation extension point.
 * @author Jens Dietrich
 */

public interface ConditionVerifier {
	/**
	 * Check a condition. 
	 * The relationship used in the condition must be in the list contributed predicates. 
	 * @param condition
	 * @throws VerificationException
	 */
	public void check(Condition condition) throws VerificationException; 
}
