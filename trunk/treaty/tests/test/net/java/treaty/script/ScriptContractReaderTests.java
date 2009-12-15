/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package test.net.java.treaty.script;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Collection;

import net.java.treaty.ConjunctiveCondition;
import net.java.treaty.Contract;
import net.java.treaty.ContractReader;
import net.java.treaty.ExistsCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.script.ScriptContractReader;

import org.junit.Test;

import test.net.java.treaty.contractregistry.VocabularyMock;
import test.net.java.treaty.mocks.ResourceManagerMock;

/**
 * <p>
 * Contains test cases to test the {@link ScriptContractReader}.
 * </p>
 * 
 * @author Claas Wilke
 */
public class ScriptContractReaderTests {

	/**
	 * <p>
	 * Tests the {@link ScriptContractReader} by reading a simple {@link Contract}
	 * file.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRead01() throws Exception {

		Contract contract;
		contract = null;

		URL location;
		location = null;

		/* Try to read the contract. */
		File file;
		file = new File("tests/test/net/java/treaty/script/simple01.contract");

		location = file.toURI().toURL();

		ContractReader reader;
		reader = new ScriptContractReader(new ResourceManagerMock());

		contract = reader.read(location.openStream(), VocabularyMock.INSTANCE);

		assertNotNull(contract);

		assertEquals("jens", contract.getProperty("author"));

		assertEquals(2, contract.getTriggers().size());

		assertEquals(new URI("event:osgi.activated"), contract.getTriggers().get(0));
		assertEquals(new URI("event:osgi.updated"), contract.getTriggers().get(1));

		assertEquals(1, contract.getConsumerResources().size());

		Resource r0 = findResource(contract.getConsumerResources(), "Interface");
		assertNotNull(r0);
		assertEquals(new URI("http://www.treaty.org/java#AbstractType"), r0
				.getType());
		assertEquals("net.java.treaty.eclipse.example.clock.DateFormatter", r0
				.getName());

		assertEquals(1, contract.getSupplierResources().size());

		Resource r1 = findResource(contract.getSupplierResources(), "Formatter");
		assertNotNull(r1);
		assertEquals(new URI("http://www.treaty.org/java#InstantiableClass"), r1
				.getType());
		assertEquals("serviceprovider/@class", r1.getRef());

		assertEquals(1, contract.getOnVerificationFailsActions().size());
		assertEquals(new URI("action:osgi.uninstall.$bundle"), contract
				.getOnVerificationFailsActions().get(0));

		assertEquals(1, contract.getOnVerificationSucceedsActions().size());
		assertEquals(new URI("action:osgi.log.debug.$bundle"), contract
				.getOnVerificationSucceedsActions().get(0));

		ConjunctiveCondition conjunction =
				(ConjunctiveCondition) contract.getConstraints().get(0);
		assertEquals(2, conjunction.getParts().size());
		assertTrue(conjunction.getParts().get(0) instanceof RelationshipCondition);
		assertTrue(conjunction.getParts().get(1) instanceof ExistsCondition);

		RelationshipCondition relationshipCondition =
				(RelationshipCondition) conjunction.getParts().get(0);
		assertEquals(new URI("http://www.treaty.org/java#implements"),
				relationshipCondition.getRelationship());
		assertEquals("Formatter", relationshipCondition.getResource1().getId());
		assertEquals("Interface", relationshipCondition.getResource2().getId());

		ExistsCondition existsCondition =
				(ExistsCondition) conjunction.getParts().get(1);
		assertEquals("Formatter", existsCondition.getResource().getId());
	}

	/**
	 * <p>
	 * Tests the {@link ScriptContractReader} by reading a simple {@link Contract}
	 * file.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRead02() throws Exception {
	
		Contract contract;
		contract = null;
	
		URL location;
		location = null;
	
		/* Try to read the contract. */
		File file;
		file = new File("tests/test/net/java/treaty/script/simple02.contract");
	
		location = file.toURI().toURL();
	
		ContractReader reader;
		reader = new ScriptContractReader(new ResourceManagerMock());
	
		contract = reader.read(location.openStream(), VocabularyMock.INSTANCE);
	
		assertNotNull(contract);
	
		assertEquals("jens", contract.getProperty("author"));
	
		assertEquals(2, contract.getTriggers().size());
	
		assertEquals(new URI("event:osgi.activated"), contract.getTriggers().get(0));
		assertEquals(new URI("event:osgi.updated"), contract.getTriggers().get(1));
	
		assertEquals(1, contract.getConsumerResources().size());
	
		Resource r0 = findResource(contract.getConsumerResources(), "Interface");
		assertNotNull(r0);
		assertEquals(new URI("http://www.treaty.org/java#AbstractType"), r0
				.getType());
		assertEquals("net.java.treaty.eclipse.example.clock.DateFormatter", r0
				.getName());
	
		assertEquals(1, contract.getSupplierResources().size());
	
		Resource r1 = findResource(contract.getSupplierResources(), "Formatter");
		assertNotNull(r1);
		assertEquals(new URI("http://www.treaty.org/java#InstantiableClass"), r1
				.getType());
		assertEquals("serviceprovider/@class", r1.getRef());
	
		assertEquals(1, contract.getOnVerificationFailsActions().size());
		assertEquals(new URI("action:osgi.uninstall.$bundle"), contract
				.getOnVerificationFailsActions().get(0));
	
		assertEquals(1, contract.getOnVerificationSucceedsActions().size());
		assertEquals(new URI("action:osgi.log.debug.$bundle"), contract
				.getOnVerificationSucceedsActions().get(0));
	
		ConjunctiveCondition conjunction =
				(ConjunctiveCondition) contract.getConstraints().get(0);
		assertEquals(2, conjunction.getParts().size());
		assertTrue(conjunction.getParts().get(0) instanceof RelationshipCondition);
		assertTrue(conjunction.getParts().get(1) instanceof ExistsCondition);
	
		RelationshipCondition relationshipCondition =
				(RelationshipCondition) conjunction.getParts().get(0);
		assertEquals(new URI("http://www.treaty.org/java#implements"),
				relationshipCondition.getRelationship());
		assertEquals("Formatter", relationshipCondition.getResource1().getId());
		assertEquals("Interface", relationshipCondition.getResource2().getId());
	
		ExistsCondition existsCondition =
				(ExistsCondition) conjunction.getParts().get(1);
		assertEquals("Formatter", existsCondition.getResource().getId());
	}

	/**
	 * <p>
	 * Tests the {@link ScriptContractReader} by reading a simple {@link Contract}
	 * file.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRead03() throws Exception {
	
		Contract contract;
		contract = null;
	
		URL location;
		location = null;
	
		/* Try to read the contract. */
		File file;
		file = new File("tests/test/net/java/treaty/script/simple03.contract");
	
		location = file.toURI().toURL();
	
		ContractReader reader;
		reader = new ScriptContractReader(new ResourceManagerMock());
	
		contract = reader.read(location.openStream(), VocabularyMock.INSTANCE);
	
		assertNotNull(contract);
	
		assertEquals("jens", contract.getProperty("author"));
	
		assertEquals(2, contract.getTriggers().size());
	
		assertEquals(new URI("event:osgi.activated"), contract.getTriggers().get(0));
		assertEquals(new URI("event:osgi.updated"), contract.getTriggers().get(1));
	
		assertEquals(1, contract.getConsumerResources().size());
	
		Resource r0 = findResource(contract.getConsumerResources(), "Interface");
		assertNotNull(r0);
		assertEquals(new URI("http://www.treaty.org/java#AbstractType"), r0
				.getType());
		assertEquals("net.java.treaty.eclipse.example.clock.DateFormatter", r0
				.getName());
	
		assertEquals(1, contract.getSupplierResources().size());
	
		Resource r1 = findResource(contract.getSupplierResources(), "Formatter");
		assertNotNull(r1);
		assertEquals(new URI("http://www.treaty.org/java#InstantiableClass"), r1
				.getType());
		assertEquals("serviceprovider/@class", r1.getRef());
	
		assertEquals(1, contract.getOnVerificationFailsActions().size());
		assertEquals(new URI("action:osgi.uninstall.$bundle"), contract
				.getOnVerificationFailsActions().get(0));
	
		assertEquals(1, contract.getOnVerificationSucceedsActions().size());
		assertEquals(new URI("action:osgi.log.debug.$bundle"), contract
				.getOnVerificationSucceedsActions().get(0));
	
		ConjunctiveCondition conjunction =
				(ConjunctiveCondition) contract.getConstraints().get(0);
		assertEquals(2, conjunction.getParts().size());
		assertTrue(conjunction.getParts().get(0) instanceof RelationshipCondition);
		assertTrue(conjunction.getParts().get(1) instanceof ExistsCondition);
	
		RelationshipCondition relationshipCondition =
				(RelationshipCondition) conjunction.getParts().get(0);
		assertEquals(new URI("http://www.treaty.org/java#implements"),
				relationshipCondition.getRelationship());
		assertEquals("Formatter", relationshipCondition.getResource1().getId());
		assertEquals("Interface", relationshipCondition.getResource2().getId());
	
		ExistsCondition existsCondition =
				(ExistsCondition) conjunction.getParts().get(1);
		assertEquals("Formatter", existsCondition.getResource().getId());
	}

	/**
	 * <p>
	 * Tests the {@link ScriptContractReader} by reading a simple {@link Contract}
	 * file.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRead04() throws Exception {
	
		Contract contract;
		contract = null;
	
		URL location;
		location = null;
	
		/* Try to read the contract. */
		File file;
		file = new File("tests/test/net/java/treaty/script/simple04.contract");
	
		location = file.toURI().toURL();
	
		ContractReader reader;
		reader = new ScriptContractReader(new ResourceManagerMock());
	
		contract = reader.read(location.openStream(), VocabularyMock.INSTANCE);
	
		assertNotNull(contract);
	
		assertEquals("jens", contract.getProperty("author"));
	
		assertEquals(2, contract.getTriggers().size());
	
		assertEquals(new URI("event:osgi.activated"), contract.getTriggers().get(0));
		assertEquals(new URI("event:osgi.updated"), contract.getTriggers().get(1));
	
		assertEquals(1, contract.getConsumerResources().size());
	
		Resource r0 = findResource(contract.getConsumerResources(), "Interface");
		assertNotNull(r0);
		assertEquals(new URI("http://www.treaty.org/java#AbstractType"), r0
				.getType());
		assertEquals("net.java.treaty.eclipse.example.clock.DateFormatter", r0
				.getName());
	
		assertEquals(1, contract.getSupplierResources().size());
	
		Resource r1 = findResource(contract.getSupplierResources(), "Formatter");
		assertNotNull(r1);
		assertEquals(new URI("http://www.treaty.org/java#InstantiableClass"), r1
				.getType());
		assertEquals("serviceprovider/@class", r1.getRef());
	
		assertEquals(1, contract.getOnVerificationFailsActions().size());
		assertEquals(new URI("action:osgi.uninstall.$bundle"), contract
				.getOnVerificationFailsActions().get(0));
	
		assertEquals(1, contract.getOnVerificationSucceedsActions().size());
		assertEquals(new URI("action:osgi.log.debug.$bundle"), contract
				.getOnVerificationSucceedsActions().get(0));
	
		ConjunctiveCondition conjunction =
				(ConjunctiveCondition) contract.getConstraints().get(0);
		assertEquals(2, conjunction.getParts().size());
		assertTrue(conjunction.getParts().get(0) instanceof RelationshipCondition);
		assertTrue(conjunction.getParts().get(1) instanceof ExistsCondition);
	
		RelationshipCondition relationshipCondition =
				(RelationshipCondition) conjunction.getParts().get(0);
		assertEquals(new URI("http://www.treaty.org/java#implements"),
				relationshipCondition.getRelationship());
		assertEquals("Formatter", relationshipCondition.getResource1().getId());
		assertEquals("Interface", relationshipCondition.getResource2().getId());
	
		ExistsCondition existsCondition =
				(ExistsCondition) conjunction.getParts().get(1);
		assertEquals("Formatter", existsCondition.getResource().getId());
	}

	/**
	 * <p>
	 * Tests the {@link ScriptContractReader} by reading a simple {@link Contract}
	 * file.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRead05() throws Exception {
	
		Contract contract;
		contract = null;
	
		URL location;
		location = null;
	
		/* Try to read the contract. */
		File file;
		file = new File("tests/test/net/java/treaty/script/simple05.contract");
	
		location = file.toURI().toURL();
	
		ContractReader reader;
		reader = new ScriptContractReader(new ResourceManagerMock());
	
		contract = reader.read(location.openStream(), VocabularyMock.INSTANCE);
	
		assertNotNull(contract);
	
		assertEquals("jens", contract.getProperty("author"));
	
		assertEquals(2, contract.getTriggers().size());
	
		assertEquals(new URI("event:osgi.activated"), contract.getTriggers().get(0));
		assertEquals(new URI("event:osgi.updated"), contract.getTriggers().get(1));
	
		assertEquals(1, contract.getConsumerResources().size());
	
		Resource r0 = findResource(contract.getConsumerResources(), "Interface");
		assertNotNull(r0);
		assertEquals(new URI("http://www.treaty.org/java#AbstractType"), r0
				.getType());
		assertEquals("net.java.treaty.eclipse.example.clock.DateFormatter", r0
				.getName());
	
		assertEquals(1, contract.getSupplierResources().size());
	
		Resource r1 = findResource(contract.getSupplierResources(), "Formatter");
		assertNotNull(r1);
		assertEquals(new URI("http://www.treaty.org/java#InstantiableClass"), r1
				.getType());
		assertEquals("serviceprovider/@class", r1.getRef());
	
		assertEquals(1, contract.getOnVerificationFailsActions().size());
		assertEquals(new URI("action:osgi.uninstall.$bundle"), contract
				.getOnVerificationFailsActions().get(0));
	
		assertEquals(1, contract.getOnVerificationSucceedsActions().size());
		assertEquals(new URI("action:osgi.log.debug.$bundle"), contract
				.getOnVerificationSucceedsActions().get(0));
	
		ConjunctiveCondition conjunction =
				(ConjunctiveCondition) contract.getConstraints().get(0);
		assertEquals(2, conjunction.getParts().size());
		assertTrue(conjunction.getParts().get(0) instanceof RelationshipCondition);
		assertTrue(conjunction.getParts().get(1) instanceof ExistsCondition);
	
		RelationshipCondition relationshipCondition =
				(RelationshipCondition) conjunction.getParts().get(0);
		assertEquals(new URI("http://www.treaty.org/java#implements"),
				relationshipCondition.getRelationship());
		assertEquals("Formatter", relationshipCondition.getResource1().getId());
		assertEquals("Interface", relationshipCondition.getResource2().getId());
	
		ExistsCondition existsCondition =
				(ExistsCondition) conjunction.getParts().get(1);
		assertEquals("Formatter", existsCondition.getResource().getId());
	}

	/**
	 * <p>
	 * Tests the {@link ScriptContractReader} by reading a simple {@link Contract}
	 * file.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRead06() throws Exception {
	
		Contract contract;
		contract = null;
	
		URL location;
		location = null;
	
		/* Try to read the contract. */
		File file;
		file = new File("tests/test/net/java/treaty/script/simple06.contract");
	
		location = file.toURI().toURL();
	
		ContractReader reader;
		reader = new ScriptContractReader(new ResourceManagerMock());
	
		contract = reader.read(location.openStream(), VocabularyMock.INSTANCE);
	
		assertNotNull(contract);
	
		assertEquals("jens", contract.getProperty("author"));
	
		assertEquals(2, contract.getTriggers().size());
	
		assertEquals(new URI("event:osgi.activated"), contract.getTriggers().get(0));
		assertEquals(new URI("event:osgi.updated"), contract.getTriggers().get(1));
	
		assertEquals(1, contract.getConsumerResources().size());
	
		Resource r0 = findResource(contract.getConsumerResources(), "Interface");
		assertNotNull(r0);
		assertEquals(new URI("http://www.treaty.org/java#AbstractType"), r0
				.getType());
		assertEquals("net.java.treaty.eclipse.example.clock.DateFormatter", r0
				.getName());
	
		assertEquals(1, contract.getSupplierResources().size());
	
		Resource r1 = findResource(contract.getSupplierResources(), "Formatter");
		assertNotNull(r1);
		assertEquals(new URI("http://www.treaty.org/java#InstantiableClass"), r1
				.getType());
		assertEquals("serviceprovider/@class", r1.getRef());
	
		assertEquals(1, contract.getOnVerificationFailsActions().size());
		assertEquals(new URI("action:osgi.uninstall.$bundle"), contract
				.getOnVerificationFailsActions().get(0));
	
		assertEquals(1, contract.getOnVerificationSucceedsActions().size());
		assertEquals(new URI("action:osgi.log.debug.$bundle"), contract
				.getOnVerificationSucceedsActions().get(0));
	
		ConjunctiveCondition conjunction =
				(ConjunctiveCondition) contract.getConstraints().get(0);
		assertEquals(2, conjunction.getParts().size());
		assertTrue(conjunction.getParts().get(0) instanceof RelationshipCondition);
		assertTrue(conjunction.getParts().get(1) instanceof ExistsCondition);
	
		RelationshipCondition relationshipCondition =
				(RelationshipCondition) conjunction.getParts().get(0);
		assertEquals(new URI("http://www.treaty.org/java#implements"),
				relationshipCondition.getRelationship());
		assertEquals("Formatter", relationshipCondition.getResource1().getId());
		assertEquals("Interface", relationshipCondition.getResource2().getId());
	
		ExistsCondition existsCondition =
				(ExistsCondition) conjunction.getParts().get(1);
		assertEquals("Formatter", existsCondition.getResource().getId());
	}

	/**
	 * <p>
	 * Tests the {@link ScriptContractReader} by reading a simple {@link Contract}
	 * file.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRead07() throws Exception {
	
		Contract contract;
		contract = null;
	
		URL location;
		location = null;
	
		/* Try to read the contract. */
		File file;
		file = new File("tests/test/net/java/treaty/script/simple07.contract");
	
		location = file.toURI().toURL();
	
		ContractReader reader;
		reader = new ScriptContractReader(new ResourceManagerMock());
	
		contract = reader.read(location.openStream(), VocabularyMock.INSTANCE);
	
		assertNotNull(contract);
	
		assertEquals("jens", contract.getProperty("author"));
	
		assertEquals(2, contract.getTriggers().size());
	
		assertEquals(new URI("osgi.activated"), contract.getTriggers().get(0));
		assertEquals(new URI("osgi.updated"), contract.getTriggers().get(1));
	
		assertEquals(1, contract.getConsumerResources().size());
	
		Resource r0 = findResource(contract.getConsumerResources(), "Interface");
		assertNotNull(r0);
		assertEquals(new URI("http://www.treaty.org/java#AbstractType"), r0
				.getType());
		assertEquals("net.java.treaty.eclipse.example.clock.DateFormatter", r0
				.getName());
	
		assertEquals(1, contract.getSupplierResources().size());
	
		Resource r1 = findResource(contract.getSupplierResources(), "Formatter");
		assertNotNull(r1);
		assertEquals(new URI("http://www.treaty.org/java#InstantiableClass"), r1
				.getType());
		assertEquals("serviceprovider/@class", r1.getRef());
	
		assertEquals(1, contract.getOnVerificationFailsActions().size());
		assertEquals(new URI("action:osgi.uninstall.$bundle"), contract
				.getOnVerificationFailsActions().get(0));
	
		assertEquals(1, contract.getOnVerificationSucceedsActions().size());
		assertEquals(new URI("action:osgi.log.debug.$bundle"), contract
				.getOnVerificationSucceedsActions().get(0));
	
		ConjunctiveCondition conjunction =
				(ConjunctiveCondition) contract.getConstraints().get(0);
		assertEquals(2, conjunction.getParts().size());
		assertTrue(conjunction.getParts().get(0) instanceof RelationshipCondition);
		assertTrue(conjunction.getParts().get(1) instanceof ExistsCondition);
	
		RelationshipCondition relationshipCondition =
				(RelationshipCondition) conjunction.getParts().get(0);
		assertEquals(new URI("http://www.treaty.org/java#implements"),
				relationshipCondition.getRelationship());
		assertEquals("Formatter", relationshipCondition.getResource1().getId());
		assertEquals("Interface", relationshipCondition.getResource2().getId());
	
		ExistsCondition existsCondition =
				(ExistsCondition) conjunction.getParts().get(1);
		assertEquals("Formatter", existsCondition.getResource().getId());
	}

	/**
	 * <p>
	 * Tests the {@link ScriptContractReader} by reading a simple {@link Contract}
	 * file.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRead08() throws Exception {
	
		Contract contract;
		contract = null;
	
		URL location;
		location = null;
	
		/* Try to read the contract. */
		File file;
		file = new File("tests/test/net/java/treaty/script/simple08.contract");
	
		location = file.toURI().toURL();
	
		ContractReader reader;
		reader = new ScriptContractReader(new ResourceManagerMock());
	
		contract = reader.read(location.openStream(), VocabularyMock.INSTANCE);
	
		assertNotNull(contract);
	
		assertEquals("jens", contract.getProperty("author"));
	
		assertEquals(2, contract.getTriggers().size());
	
		assertEquals(new URI("event:osgi.activated"), contract.getTriggers().get(0));
		assertEquals(new URI("event:osgi.updated"), contract.getTriggers().get(1));
	
		assertEquals(1, contract.getConsumerResources().size());
	
		Resource r0 = findResource(contract.getConsumerResources(), "Interface");
		assertNotNull(r0);
		assertEquals(new URI("http://www.treaty.org/java#AbstractType"), r0
				.getType());
		assertEquals("net.java.treaty.eclipse.example.clock.DateFormatter", r0
				.getName());
	
		assertEquals(1, contract.getSupplierResources().size());
	
		Resource r1 = findResource(contract.getSupplierResources(), "Formatter");
		assertNotNull(r1);
		assertEquals(new URI("http://www.treaty.org/java#InstantiableClass"), r1
				.getType());
		assertEquals("serviceprovider/@class", r1.getRef());
	
		assertEquals(1, contract.getOnVerificationFailsActions().size());
		assertEquals(new URI("osgi.uninstall.$bundle"), contract
				.getOnVerificationFailsActions().get(0));
	
		assertEquals(1, contract.getOnVerificationSucceedsActions().size());
		assertEquals(new URI("osgi.log.debug.$bundle"), contract
				.getOnVerificationSucceedsActions().get(0));
	
		ConjunctiveCondition conjunction =
				(ConjunctiveCondition) contract.getConstraints().get(0);
		assertEquals(2, conjunction.getParts().size());
		assertTrue(conjunction.getParts().get(0) instanceof RelationshipCondition);
		assertTrue(conjunction.getParts().get(1) instanceof ExistsCondition);
	
		RelationshipCondition relationshipCondition =
				(RelationshipCondition) conjunction.getParts().get(0);
		assertEquals(new URI("http://www.treaty.org/java#implements"),
				relationshipCondition.getRelationship());
		assertEquals("Formatter", relationshipCondition.getResource1().getId());
		assertEquals("Interface", relationshipCondition.getResource2().getId());
	
		ExistsCondition existsCondition =
				(ExistsCondition) conjunction.getParts().get(1);
		assertEquals("Formatter", existsCondition.getResource().getId());
	}

	/**
	 * <p>
	 * Tests the {@link ScriptContractReader} by reading a simple {@link Contract}
	 * file.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRead09() throws Exception {
	
		Contract contract;
		contract = null;
	
		URL location;
		location = null;
	
		/* Try to read the contract. */
		File file;
		file = new File("tests/test/net/java/treaty/script/simple09.contract");
	
		location = file.toURI().toURL();
	
		ContractReader reader;
		reader = new ScriptContractReader(new ResourceManagerMock());
	
		contract = reader.read(location.openStream(), VocabularyMock.INSTANCE);
	
		assertNotNull(contract);
	
		assertEquals("jens dietrich", contract.getProperty("author"));
	
		assertEquals(2, contract.getTriggers().size());
	
		assertEquals(new URI("event:osgi.activated"), contract.getTriggers().get(0));
		assertEquals(new URI("event:osgi.updated"), contract.getTriggers().get(1));
	
		assertEquals(1, contract.getConsumerResources().size());
	
		Resource r0 = findResource(contract.getConsumerResources(), "Interface");
		assertNotNull(r0);
		assertEquals(new URI("http://www.treaty.org/java#AbstractType"), r0
				.getType());
		assertEquals("net.java.treaty.eclipse.example.clock.DateFormatter", r0
				.getName());
	
		assertEquals(1, contract.getSupplierResources().size());
	
		Resource r1 = findResource(contract.getSupplierResources(), "Formatter");
		assertNotNull(r1);
		assertEquals(new URI("http://www.treaty.org/java#InstantiableClass"), r1
				.getType());
		assertEquals("serviceprovider/@class", r1.getRef());
	
		assertEquals(1, contract.getOnVerificationFailsActions().size());
		assertEquals(new URI("action:osgi.uninstall.$bundle"), contract
				.getOnVerificationFailsActions().get(0));
	
		assertEquals(1, contract.getOnVerificationSucceedsActions().size());
		assertEquals(new URI("action:osgi.log.debug.$bundle"), contract
				.getOnVerificationSucceedsActions().get(0));
	
		ConjunctiveCondition conjunction =
				(ConjunctiveCondition) contract.getConstraints().get(0);
		assertEquals(2, conjunction.getParts().size());
		assertTrue(conjunction.getParts().get(0) instanceof RelationshipCondition);
		assertTrue(conjunction.getParts().get(1) instanceof ExistsCondition);
	
		RelationshipCondition relationshipCondition =
				(RelationshipCondition) conjunction.getParts().get(0);
		assertEquals(new URI("http://www.treaty.org/java#implements"),
				relationshipCondition.getRelationship());
		assertEquals("Formatter", relationshipCondition.getResource1().getId());
		assertEquals("Interface", relationshipCondition.getResource2().getId());
	
		ExistsCondition existsCondition =
				(ExistsCondition) conjunction.getParts().get(1);
		assertEquals("Formatter", existsCondition.getResource().getId());
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