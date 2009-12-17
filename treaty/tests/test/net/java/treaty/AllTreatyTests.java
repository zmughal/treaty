/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package test.net.java.treaty;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.net.java.treaty.action.ActionOntologyTest;
import test.net.java.treaty.contractregistry.AllContractRegistryTests;
import test.net.java.treaty.script.AllScriptTests;
import test.net.java.treaty.trigger.AllTriggerTests;
import test.net.java.treaty.vocabulary.AllVocabularyTests;
import test.net.java.treaty.xml.AllXmlTests;

/**
 * <p>
 * Provides a JUnit Test Suite containing all JUnit test cases of the Treaty
 * Framework.
 * </p>
 * 
 * @author Claas Wilke
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( { ActionOntologyTest.class, ContractTests.class,
		AllContractRegistryTests.class, AllTriggerTests.class,
		AllScriptTests.class, AllVocabularyTests.class, AllXmlTests.class })
public class AllTreatyTests {
	/*
	 * This class remains completely empty, being used only as a holder for the
	 * above annotations.
	 */
}