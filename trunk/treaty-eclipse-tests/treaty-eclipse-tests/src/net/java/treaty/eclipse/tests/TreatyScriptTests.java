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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.Collection;

import net.java.treaty.ConjunctiveCondition;
import net.java.treaty.Contract;
import net.java.treaty.ContractVocabulary;
import net.java.treaty.ExclusiveDisjunctiveCondition;
import net.java.treaty.ExistsCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.eclipse.VocabularyRegistry;
import net.java.treaty.script.TreatyScript;

import org.junit.Test;

/**
 * <p>
 * Contains test cases for the {@link TreatyScript} that cannot be tested
 * without the Eclipse {@link ContractVocabulary}s.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class TreatyScriptTests {

	/**
	 * <p>
	 * Tests {@link TreatyScript} with a contract using the JUnit vocabulary.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testClockScript() throws Exception {

		Contract contract =
				TreatyScript
						.fromString(
								"// example contract used in prototype\n"
										+ "@dc:author=jens\n"
										+ "@dc:date=30/10/09\n"
										+ "on event:osgi.activated\n"
										+ "on event:osgi.updated\n"
										+ "consumer-resource Interface type=http://www.treaty.org/java#AbstractType name=net.java.treaty.eclipse.example.clock.DateFormatter\n"
										+ "consumer-resource QoSTests type=http://www.treaty.org/junit#TestCase name=net.java.treaty.eclipse.example.clock.DateFormatterPerformanceTests\n"
										+ "consumer-resource FunctionalTests type=http://www.treaty.org/junit#TestCase name=net.java.treaty.eclipse.example.clock.DateFormatterFunctionalTests\n"
										+ "consumer-resource DateFormatDef type=http://www.treaty.org/xml#XMLSchema name=/dateformat.xsd\n"
										+ "supplier-resource Formatter type=http://www.treaty.org/java#InstantiableClass ref=serviceprovider/@class\n"
										+ "supplier-resource FormatString type=http://www.treaty.org/xml#XMLInstance ref=serviceprovider/@formatdef\n"
										+ "external-resource DateFormatDef2 type=http://www.treaty.org/xml#XMLSchema name=/dateformat.xsd\n"
										+ "constraint (\n"
										+ "    (\n"
										+ "        Formatter http://www.treaty.org/java#implements Interface and mustexist Formatter\n"
										+ "        and Formatter http://www.treaty.org/junit#verifies FunctionalTests \n"
										+ "        and Formatter http://www.treaty.org/junit#verifies QoSTests\n"
										+ "    )\n"
										+ "    xor FormatString http://www.treaty.org/xml#instantiates DateFormatDef\n"
										+ ")\n" + "onfailure action:osgi.uninstall.$bundle\n"
										+ "onfailure action:osgi.log.debug.$bundle\n"
										+ "onsuccess action:osgi.log.debug.$bundle\n",
								VocabularyRegistry.INSTANCE);

		assertEquals("jens", contract.getProperty("dc:author"));
		assertEquals("30/10/09", contract.getProperty("dc:date"));

		assertEquals(2, contract.getTriggers().size());
		assertEquals(new URI("event:osgi.activated"), contract.getTriggers().get(0));
		assertEquals(new URI("event:osgi.updated"), contract.getTriggers().get(1));

		assertEquals(1, contract.getOnVerificationSucceedsActions().size());
		assertEquals(new URI("action:osgi.log.debug.$bundle"), contract
				.getOnVerificationSucceedsActions().get(0));

		assertEquals(2, contract.getOnVerificationFailsActions().size());
		assertEquals(new URI("action:osgi.uninstall.$bundle"), contract
				.getOnVerificationFailsActions().get(0));
		assertEquals(new URI("action:osgi.log.debug.$bundle"), contract
				.getOnVerificationFailsActions().get(1));

		assertEquals(4, contract.getConsumerResources().size());

		Resource r0 = findResource(contract.getConsumerResources(), "Interface");
		assertNotNull(r0);
		assertEquals(new URI("http://www.treaty.org/java#AbstractType"), r0
				.getType());
		assertEquals("net.java.treaty.eclipse.example.clock.DateFormatter", r0
				.getName());

		Resource r1 = findResource(contract.getConsumerResources(), "QoSTests");
		assertNotNull(r1);
		assertEquals(new URI("http://www.treaty.org/junit#TestCase"), r1.getType());
		assertEquals(
				"net.java.treaty.eclipse.example.clock.DateFormatterPerformanceTests",
				r1.getName());

		Resource r2 =
				findResource(contract.getConsumerResources(), "FunctionalTests");
		assertNotNull(r2);
		assertEquals(new URI("http://www.treaty.org/junit#TestCase"), r2.getType());
		assertEquals(
				"net.java.treaty.eclipse.example.clock.DateFormatterFunctionalTests",
				r2.getName());

		Resource r3 =
				findResource(contract.getConsumerResources(), "DateFormatDef");
		assertNotNull(r3);
		assertEquals(new URI("http://www.treaty.org/xml#XMLSchema"), r3.getType());
		assertEquals("/dateformat.xsd", r3.getName());

		assertEquals(2, contract.getSupplierResources().size());

		Resource r4 = findResource(contract.getSupplierResources(), "Formatter");
		assertNotNull(r4);
		assertEquals(new URI("http://www.treaty.org/java#InstantiableClass"), r4
				.getType());
		assertEquals("serviceprovider/@class", r4.getRef());

		Resource r5 = findResource(contract.getSupplierResources(), "FormatString");
		assertNotNull(r5);
		assertEquals(new URI("http://www.treaty.org/xml#XMLInstance"), r5.getType());
		assertEquals("serviceprovider/@formatdef", r5.getRef());

		assertEquals(1, contract.getConstraints().size());
		assertTrue(contract.getConstraints().get(0) instanceof ExclusiveDisjunctiveCondition);

		ExclusiveDisjunctiveCondition xor =
				(ExclusiveDisjunctiveCondition) contract.getConstraints().get(0);
		assertEquals(2, xor.getParts().size());
		assertTrue(xor.getParts().get(0) instanceof ConjunctiveCondition);
		assertTrue(xor.getParts().get(1) instanceof RelationshipCondition);

		ConjunctiveCondition and = (ConjunctiveCondition) xor.getParts().get(0);
		assertEquals(4, and.getParts().size());
		assertTrue(and.getParts().get(0) instanceof RelationshipCondition);
		assertTrue(and.getParts().get(1) instanceof ExistsCondition);
		assertTrue(and.getParts().get(2) instanceof RelationshipCondition);
		assertTrue(and.getParts().get(3) instanceof RelationshipCondition);

		RelationshipCondition rel0 = (RelationshipCondition) and.getParts().get(0);
		assertEquals("Formatter", rel0.getResource1().getId());
		assertEquals("Interface", rel0.getResource2().getId());
		assertEquals(new URI("http://www.treaty.org/java#implements"), rel0
				.getRelationship());

		ExistsCondition x1 = (ExistsCondition) and.getParts().get(1);
		assertEquals("Formatter", x1.getResource().getId());

		RelationshipCondition rel2 = (RelationshipCondition) and.getParts().get(2);
		assertEquals("Formatter", rel2.getResource1().getId());
		assertEquals("FunctionalTests", rel2.getResource2().getId());
		assertEquals(new URI("http://www.treaty.org/junit#verifies"), rel2
				.getRelationship());

		RelationshipCondition rel3 = (RelationshipCondition) and.getParts().get(3);
		assertEquals("Formatter", rel3.getResource1().getId());
		assertEquals("QoSTests", rel3.getResource2().getId());
		assertEquals(new URI("http://www.treaty.org/junit#verifies"), rel3
				.getRelationship());

		RelationshipCondition rel4 = (RelationshipCondition) xor.getParts().get(1);
		assertEquals("FormatString", rel4.getResource1().getId());
		assertEquals("DateFormatDef", rel4.getResource2().getId());
		assertEquals(new URI("http://www.treaty.org/xml#instantiates"), rel4
				.getRelationship());
	}

	/**
	 * <p>
	 * Helper method to find a {@link Resource} in a {@link Collection} of
	 * {@link Resource}s.
	 * 
	 * @param resources
	 *          The {@link Collection} of the {@link Resource}s.
	 * @param id
	 *          The id of the {@link Resource} that shall be found.
	 * @return The found {@link Resource} or <code>null</code>.
	 */
	private Resource findResource(Collection<Resource> resources, String id) {

		Resource result;
		result = null;

		for (Resource resource : resources) {

			if (resource.getId().equals(id)) {
				result = resource;
				break;
			}
			// no else.
		}
		// end for.

		return result;
	}
}