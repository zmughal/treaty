/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.tests;

import net.java.treaty.eclipse.tests.action.AllActionTests;
import net.java.treaty.eclipse.tests.contractregistry.AllContractRegistryTests;
import net.java.treaty.eclipse.tests.trigger.AllTriggerTests;
import net.java.treaty.eclipse.tests.verification.AllVerificationTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * <p>
 * Provides a JUnit Test Suite containing all JUnit test cases of the Treaty
 * Eclipse implementation.
 * </p>
 * 
 * @author Claas Wilke
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( { AllActionTests.class, AllContractRegistryTests.class,
		AllTriggerTests.class, AllVerificationTests.class, TreatyScriptTests.class })
public class AllTreatyEclipseTests {
	/*
	 * This class remains completely empty, being used only as a holder for the
	 * above annotations.
	 */
}