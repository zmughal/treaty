/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package test.net.java.treaty.xml;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.URI;
import java.util.Collection;
import net.java.treaty.Conjunction;
import net.java.treaty.Contract;
import net.java.treaty.ContractReader;
import net.java.treaty.ExistsCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.XDisjunction;
import net.java.treaty.vocabulary.CompositeContractOntology;
import net.java.treaty.vocabulary.builtins.BasicOpVocabulary;
import net.java.treaty.vocabulary.builtins.owl.OWLVocabulary;
import net.java.treaty.xml.XMLContractReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.net.java.treaty.vocabulary.TestVocabulary;

/**
 * Tests for XMLContractReader.
 * 
 * @author jens
 */
public class XMLContractReaderTests {

	private CompositeContractOntology voc = null;

	@Before
	public void setUp() throws Exception {

		voc = new CompositeContractOntology();
		voc.add(new BasicOpVocabulary(), "net.java.treaty");
		voc.add(new OWLVocabulary(), "net.java.treaty");
		voc.add(new TestVocabulary(), "net.java.treaty");
	}

	@After
	public void tearDown() throws Exception {

		voc = null;
	}

	@Test
	public void test1() throws Exception {

		InputStream in =
				XMLContractReaderTests.class
						.getResourceAsStream("/test/net/java/treaty/xml/contract1.contract");
		ContractReader reader = new XMLContractReader();
		Contract contract = reader.read(in, voc);

		assertEquals(new URI("event:osgi.activated"), contract.getTriggers().get(0));
		assertEquals(new URI("event:osgi.updated"), contract.getTriggers().get(1));
		assertEquals(2, contract.getTriggers().size());

		assertEquals(new URI("action:osgi.log.debug.$bundle"), contract
				.getOnVerificationSucceedsActions().get(0));
		assertEquals(1, contract.getOnVerificationSucceedsActions().size());

		assertEquals(new URI("action:osgi.uninstall.$bundle"), contract
				.getOnVerificationFailsActions().get(0));
		assertEquals(new URI("action:osgi.log.debug.$bundle"), contract
				.getOnVerificationFailsActions().get(1));
		assertEquals(2, contract.getOnVerificationFailsActions().size());

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
		assertTrue(contract.getConstraints().get(0) instanceof XDisjunction);

		XDisjunction xor = (XDisjunction) contract.getConstraints().get(0);
		assertEquals(2, xor.getParts().size());
		assertTrue(xor.getParts().get(0) instanceof Conjunction);
		assertTrue(xor.getParts().get(1) instanceof RelationshipCondition);

		Conjunction and = (Conjunction) xor.getParts().get(0);
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

		assertTrue(true);
	}

	private Resource findResource(Collection<Resource> resources, String id) {

		for (Resource r : resources) {
			if (r.getId().equals(id))
				return r;
		}
		return null;
	}

}
