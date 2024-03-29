/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package test.net.java.treaty.contractregistry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import net.java.treaty.ConnectorType;
import net.java.treaty.Contract;
import net.java.treaty.ContractReader;
import net.java.treaty.Role;
import net.java.treaty.TreatyException;
import net.java.treaty.contractregistry.ContractRegistry;
import net.java.treaty.contractregistry.ContractRegistry.UpdateType;
import net.java.treaty.trigger.TriggerRegistry;
import net.java.treaty.xml.XMLContractReader;

import org.junit.BeforeClass;
import org.junit.Test;

import test.net.java.treaty.mocks.ComponentMock;
import test.net.java.treaty.mocks.ConnectorMock;
import test.net.java.treaty.mocks.ResourceManagerMock;
import test.net.java.treaty.mocks.TriggerVocabularyMock;

/**
 * <p>
 * Contains test cases to test the {@link ContractRegistry}.
 * </p>
 * 
 * @author Claas Wilke
 */
public class ContractRegistryTests {

	/** The location of the test {@link Contract}s used for testing. */
	private static final String TEST_CONTRACT_LOCATION =
			"tests/test/net/java/treaty/mocks/";

	/** The name of a test {@link Contract} used for testing. */
	private static final String TEST_CONTRACT_01 =
			TEST_CONTRACT_LOCATION + "test03.contract";

	/** The name of a test {@link Contract} used for testing. */
	private static final String TEST_CONTRACT_02 =
			TEST_CONTRACT_LOCATION + "test07.contract";

	/** The URI of a trigger used for testing. */
	private static URI trigger1;

	/** The URI of a trigger used for testing. */
	private static URI trigger2;

	/** The URI of a trigger used for testing. */
	private static URI defaultTrigger;

	/** The URI of a trigger used for testing. */
	private static URI parentTrigger1;

	/** The {@link TriggerRegistry} used during testing. */
	private static TriggerRegistry triggerRegistry;

	/**
	 * <p>
	 * Initializes the test cases.
	 * </p>
	 */
	@BeforeClass
	public static void setUp() {

		TriggerVocabularyMock triggerVocabularyMock;
		triggerVocabularyMock = new TriggerVocabularyMock();

		triggerRegistry = new TriggerRegistry();
		triggerRegistry.addTriggerVocabulary(triggerVocabularyMock);

		trigger1 =
				triggerVocabularyMock.getTrigger(TriggerVocabularyMock.NAME_TRIGGER_1);

		trigger2 =
				triggerVocabularyMock.getTrigger(TriggerVocabularyMock.NAME_TRIGGER_2);

		defaultTrigger =
				triggerVocabularyMock
						.getTrigger(TriggerVocabularyMock.NAME_DEFAULT_TRIGGER);

		parentTrigger1 =
				triggerVocabularyMock
						.getTrigger(TriggerVocabularyMock.NAME_PARENT_TRIGGER_1);
	}

	/**
	 * <p>
	 * Tests the method
	 * {@link ContractRegistry#updateContract(net.java.treaty.contractregistry.ContractRegistry.UpdateType, net.java.treaty.Contract, net.java.treaty.Connector, net.java.treaty.Role)}
	 * by adding and removing a {@link Role#LEGISLATOR} {@link Contract} to the
	 * {@link ContractRegistry}.
	 * </p>
	 */
	@Test
	public void testUpdateRegistry01() {

		ComponentMock componentMock;
		componentMock = new ComponentMock("Component 1");

		ConnectorMock legistlatorMock;
		legistlatorMock =
				new ConnectorMock("Legislator 1", componentMock, ConnectorType.CONSUMER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_01);

		ResourceManagerMock resourceManagerMock;
		resourceManagerMock = new ResourceManagerMock();
		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(resourceManagerMock, triggerRegistry);

		/* Add the contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					legistlatorMock, Role.LEGISLATOR);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		/* The registry should know the contract. */
		assertTrue(contractRegistry.getContracts(legistlatorMock, Role.LEGISLATOR)
				.contains(contract));
		/* The contract should have constraints. */
		assertFalse(contractRegistry.getContracts(legistlatorMock, Role.LEGISLATOR)
				.iterator().next().getConstraints().isEmpty());
		assertFalse(contractRegistry.getContracts(legistlatorMock, Role.CONSUMER)
				.contains(contract));
		assertFalse(contractRegistry.getContracts(legistlatorMock, Role.SUPPLIER)
				.contains(contract));

		/* The legislator should be set as owner of the contract. */
		assertNotNull(contract.getOwner());
		assertEquals(legistlatorMock, contract.getOwner());

		/* Remove the contract. */
		try {
			contractRegistry.updateContract(UpdateType.REMOVE_CONTRACT, contract,
					legistlatorMock, Role.LEGISLATOR);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry.getContracts(legistlatorMock,
				Role.LEGISLATOR).size());
		assertEquals(0, contractRegistry.getContracts(legistlatorMock,
				Role.CONSUMER).size());
		assertEquals(0, contractRegistry.getContracts(legistlatorMock,
				Role.SUPPLIER).size());
	}

	/**
	 * <p>
	 * Tests the method
	 * {@link ContractRegistry#updateContract(net.java.treaty.contractregistry.ContractRegistry.UpdateType, net.java.treaty.Contract, net.java.treaty.Connector, net.java.treaty.Role)}
	 * by adding and removing a {@link Role#CONSUMER} {@link Contract} to the
	 * {@link ContractRegistry}.
	 * </p>
	 */
	@Test
	public void testUpdateRegistry02() {

		ComponentMock componentMock;
		componentMock = new ComponentMock("Component 1");

		ConnectorMock consumerMock;
		consumerMock =
				new ConnectorMock("Consumer 1", componentMock, ConnectorType.CONSUMER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_01);

		ResourceManagerMock resourceManagerMock;
		resourceManagerMock = new ResourceManagerMock();
		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(resourceManagerMock, triggerRegistry);

		/* Add the contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					consumerMock, Role.CONSUMER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertFalse(contractRegistry.getContracts(consumerMock, Role.LEGISLATOR)
				.contains(contract));
		/* The registry should know the contract. */
		assertTrue(contractRegistry.getContracts(consumerMock, Role.CONSUMER)
				.contains(contract));
		/* The contract should have constraints. */
		assertFalse(contractRegistry.getContracts(consumerMock, Role.CONSUMER)
				.iterator().next().getConstraints().isEmpty());
		assertFalse(contractRegistry.getContracts(consumerMock, Role.SUPPLIER)
				.contains(contract));

		/* The consumer should be set as consumer of the contract. */
		assertEquals(consumerMock, contractRegistry.getContracts(consumerMock,
				Role.CONSUMER).iterator().next().getConsumer());

		/* Remove the contract. */
		try {
			contractRegistry.updateContract(UpdateType.REMOVE_CONTRACT, contract,
					consumerMock, Role.CONSUMER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry
				.getContracts(consumerMock, Role.LEGISLATOR).size());
		assertEquals(0, contractRegistry.getContracts(consumerMock, Role.CONSUMER)
				.size());
		assertEquals(0, contractRegistry.getContracts(consumerMock, Role.SUPPLIER)
				.size());
	}

	/**
	 * <p>
	 * Tests the method
	 * {@link ContractRegistry#updateContract(net.java.treaty.contractregistry.ContractRegistry.UpdateType, net.java.treaty.Contract, net.java.treaty.Connector, net.java.treaty.Role)}
	 * by adding a {@link Role#CONSUMER} {@link Contract} with an
	 * {@link ConnectorType#SUPPLIER} to the {@link ContractRegistry}.
	 * </p>
	 */
	@Test
	public void testUpdateRegistry03() {

		ComponentMock componentMock;
		componentMock = new ComponentMock("Component 1");

		ConnectorMock supplierMock;
		supplierMock =
				new ConnectorMock("Supplier 1", componentMock, ConnectorType.SUPPLIER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_01);

		ResourceManagerMock resourceManagerMock;
		resourceManagerMock = new ResourceManagerMock();
		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(resourceManagerMock, triggerRegistry);

		/* Add the contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					supplierMock, Role.CONSUMER);
			fail("Expected TreatyException was not thrown.");
		}

		catch (TreatyException e) {
			/* Expected exception. */
		}
		// end catch.
	}

	/**
	 * <p>
	 * Tests the method
	 * {@link ContractRegistry#updateContract(net.java.treaty.contractregistry.ContractRegistry.UpdateType, net.java.treaty.Contract, net.java.treaty.Connector, net.java.treaty.Role)}
	 * by adding and removing a {@link Role#SUPPLIER} {@link Contract} to the
	 * {@link ContractRegistry}.
	 * </p>
	 */
	@Test
	public void testUpdateRegistry04() {

		ComponentMock componentMock;
		componentMock = new ComponentMock("Component 1");

		ConnectorMock supplierMock;
		supplierMock =
				new ConnectorMock("Supplier 1", componentMock, ConnectorType.SUPPLIER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_01);

		ResourceManagerMock resourceManagerMock;
		resourceManagerMock = new ResourceManagerMock();
		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(resourceManagerMock, triggerRegistry);

		/* Add the contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					supplierMock, Role.SUPPLIER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertFalse(contractRegistry.getContracts(supplierMock, Role.LEGISLATOR)
				.contains(contract));
		assertFalse(contractRegistry.getContracts(supplierMock, Role.CONSUMER)
				.contains(contract));
		/* The registry should know the contract. */
		assertTrue(contractRegistry.getContracts(supplierMock, Role.SUPPLIER)
				.contains(contract));
		/* The contract should have constraints. */
		assertFalse(contractRegistry.getContracts(supplierMock, Role.SUPPLIER)
				.iterator().next().getConstraints().isEmpty());

		/* The supplier should be set as supplier of the contract. */
		assertEquals(supplierMock, contractRegistry.getContracts(supplierMock,
				Role.SUPPLIER).iterator().next().getSupplier());

		/* Remove the contract. */
		try {
			contractRegistry.updateContract(UpdateType.REMOVE_CONTRACT, contract,
					supplierMock, Role.SUPPLIER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry
				.getContracts(supplierMock, Role.LEGISLATOR).size());
		assertEquals(0, contractRegistry.getContracts(supplierMock, Role.CONSUMER)
				.size());
		assertEquals(0, contractRegistry.getContracts(supplierMock, Role.SUPPLIER)
				.size());
	}

	/**
	 * <p>
	 * Tests the method
	 * {@link ContractRegistry#updateContract(net.java.treaty.contractregistry.ContractRegistry.UpdateType, net.java.treaty.Contract, net.java.treaty.Connector, net.java.treaty.Role)}
	 * by adding a {@link Role#SUPPLIER} {@link Contract} with an
	 * {@link ConnectorType#CONSUMER} to the {@link ContractRegistry}.
	 * </p>
	 */
	@Test
	public void testUpdateRegistry05() {

		ComponentMock componentMock;
		componentMock = new ComponentMock("Component 1");

		ConnectorMock consumerMock;
		consumerMock =
				new ConnectorMock("Consumer 1", componentMock, ConnectorType.CONSUMER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_01);

		ResourceManagerMock resourceManagerMock;
		resourceManagerMock = new ResourceManagerMock();
		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(resourceManagerMock, triggerRegistry);

		/* Add the contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					consumerMock, Role.SUPPLIER);
			fail("Expected TreatyException was not thrown.");
		}

		catch (TreatyException e) {
			/* Expected exception. */
		}
		// end catch.
	}

	/**
	 * <p>
	 * Tests the method
	 * {@link ContractRegistry#updateContract(net.java.treaty.contractregistry.ContractRegistry.UpdateType, net.java.treaty.Contract, net.java.treaty.Connector, net.java.treaty.Role)}
	 * by adding and removing a {@link Role#LEGISLATOR} and a
	 * {@link Role#CONSUMER} {@link Contract} to the {@link ContractRegistry}.
	 * </p>
	 */
	@Test
	public void testUpdateRegistry06() {

		ComponentMock componentMock;
		componentMock = new ComponentMock("Component 1");

		ConnectorMock legistlatorMock;
		legistlatorMock =
				new ConnectorMock("Legislator 1", componentMock, ConnectorType.SUPPLIER);

		ConnectorMock consumerMock;
		consumerMock =
				new ConnectorMock("Consumer 1", componentMock, ConnectorType.CONSUMER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_01);

		ResourceManagerMock resourceManagerMock;
		resourceManagerMock = new ResourceManagerMock();
		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(resourceManagerMock, triggerRegistry);

		/* Add the legislator contract and the consumer contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					legistlatorMock, Role.LEGISLATOR);
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					consumerMock, Role.CONSUMER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		/* The registry should know the contract. */
		assertTrue(contractRegistry.getContracts(legistlatorMock, Role.LEGISLATOR)
				.contains(contract));
		/* The contract should have constraints. */
		assertFalse(contractRegistry.getContracts(legistlatorMock, Role.LEGISLATOR)
				.iterator().next().getConstraints().isEmpty());

		assertFalse(contractRegistry.getContracts(consumerMock, Role.LEGISLATOR)
				.contains(contract));

		assertFalse(contractRegistry.getContracts(legistlatorMock, Role.CONSUMER)
				.contains(contract));

		/*
		 * The consumer's contract should have the legislator's contract as
		 * definition.
		 */
		assertEquals(contract, contractRegistry.getContracts(consumerMock,
				Role.CONSUMER).iterator().next().getDefinition());

		/* The consumer's contract should have constraints. */
		assertFalse(contractRegistry.getContracts(consumerMock, Role.CONSUMER)
				.iterator().next().getConstraints().isEmpty());

		/* Remove the contracts. */
		try {
			contractRegistry.updateContract(UpdateType.REMOVE_CONTRACT, contract,
					legistlatorMock, Role.LEGISLATOR);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry.getContracts(legistlatorMock,
				Role.LEGISLATOR).size());
		assertEquals(0, contractRegistry.getContracts(legistlatorMock,
				Role.CONSUMER).size());
		assertEquals(0, contractRegistry.getContracts(legistlatorMock,
				Role.SUPPLIER).size());

		assertEquals(0, contractRegistry
				.getContracts(consumerMock, Role.LEGISLATOR).size());
		assertEquals(0, contractRegistry.getContracts(consumerMock, Role.CONSUMER)
				.size());
		assertEquals(0, contractRegistry.getContracts(consumerMock, Role.SUPPLIER)
				.size());
	}

	/**
	 * <p>
	 * Tests the method
	 * {@link ContractRegistry#updateContract(net.java.treaty.contractregistry.ContractRegistry.UpdateType, net.java.treaty.Contract, net.java.treaty.Connector, net.java.treaty.Role)}
	 * by adding and removing a {@link Role#LEGISLATOR} and a
	 * {@link Role#SUPPLIER} {@link Contract} to the {@link ContractRegistry}.
	 * </p>
	 */
	@Test
	public void testUpdateRegistry07() {

		ComponentMock componentMock;
		componentMock = new ComponentMock("Component 1");

		ConnectorMock legistlatorMock;
		legistlatorMock =
				new ConnectorMock("Legislator 1", componentMock, ConnectorType.SUPPLIER);

		ConnectorMock supplierMock;
		supplierMock =
				new ConnectorMock("Supplier 1", componentMock, ConnectorType.SUPPLIER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_01);

		ResourceManagerMock resourceManagerMock;
		resourceManagerMock = new ResourceManagerMock();
		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(resourceManagerMock, triggerRegistry);

		/* Add the legislator contract and the supplier contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					legistlatorMock, Role.LEGISLATOR);
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					supplierMock, Role.SUPPLIER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertTrue(contractRegistry.getContracts(legistlatorMock, Role.LEGISLATOR)
				.contains(contract));
		assertFalse(contractRegistry.getContracts(supplierMock, Role.LEGISLATOR)
				.contains(contract));
		assertFalse(contractRegistry.getContracts(legistlatorMock, Role.CONSUMER)
				.contains(contract));

		/*
		 * The supplier's contract should have the legislator's contract as
		 * definition.
		 */
		assertEquals(contract, contractRegistry.getContracts(supplierMock,
				Role.SUPPLIER).iterator().next().getDefinition());

		/* The supplier's contract should have constraints. */
		assertFalse(contractRegistry.getContracts(supplierMock, Role.SUPPLIER)
				.iterator().next().getConstraints().isEmpty());

		/* Remove the contracts. */
		try {
			contractRegistry.updateContract(UpdateType.REMOVE_CONTRACT, contract,
					legistlatorMock, Role.LEGISLATOR);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry.getContracts(legistlatorMock,
				Role.LEGISLATOR).size());
		assertEquals(0, contractRegistry.getContracts(legistlatorMock,
				Role.CONSUMER).size());
		assertEquals(0, contractRegistry.getContracts(legistlatorMock,
				Role.SUPPLIER).size());

		assertEquals(0, contractRegistry
				.getContracts(supplierMock, Role.LEGISLATOR).size());
		assertEquals(0, contractRegistry.getContracts(supplierMock, Role.CONSUMER)
				.size());
		assertEquals(0, contractRegistry.getContracts(supplierMock, Role.SUPPLIER)
				.size());
	}

	/**
	 * <p>
	 * Tests the method
	 * {@link ContractRegistry#updateContract(net.java.treaty.contractregistry.ContractRegistry.UpdateType, net.java.treaty.Contract, net.java.treaty.Connector, net.java.treaty.Role)}
	 * by adding and removing a {@link Role#LEGISLATOR}, a {@link Role#CONSUMER}
	 * and a {@link Role#SUPPLIER} {@link Contract} to the
	 * {@link ContractRegistry}.
	 * </p>
	 */
	@Test
	public void testUpdateRegistry08() {

		ComponentMock componentMock;
		componentMock = new ComponentMock("Component 1");

		ConnectorMock legistlatorMock;
		legistlatorMock =
				new ConnectorMock("Legislator 1", componentMock, ConnectorType.SUPPLIER);

		ConnectorMock consumerMock;
		consumerMock =
				new ConnectorMock("Consumer 1", componentMock, ConnectorType.CONSUMER);

		ConnectorMock supplierMock;
		supplierMock =
				new ConnectorMock("Supplier 1", componentMock, ConnectorType.SUPPLIER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_01);

		ResourceManagerMock resourceManagerMock;
		resourceManagerMock = new ResourceManagerMock();
		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(resourceManagerMock, triggerRegistry);

		/*
		 * Add the legislator contract, the consumer contract and the supplier
		 * contract.
		 */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					legistlatorMock, Role.LEGISLATOR);
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					consumerMock, Role.CONSUMER);
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contractRegistry
					.getContracts(consumerMock, Role.CONSUMER).iterator().next(),
					supplierMock, Role.SUPPLIER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertTrue(contractRegistry.getContracts(legistlatorMock, Role.LEGISLATOR)
				.contains(contract));
		assertFalse(contractRegistry.getContracts(consumerMock, Role.LEGISLATOR)
				.contains(contract));
		assertFalse(contractRegistry.getContracts(supplierMock, Role.LEGISLATOR)
				.contains(contract));
		assertFalse(contractRegistry.getContracts(legistlatorMock, Role.CONSUMER)
				.contains(contract));

		/*
		 * The consumer's contract should have the legislator's contract as
		 * definition.
		 */
		assertEquals(contract, contractRegistry.getContracts(consumerMock,
				Role.CONSUMER).iterator().next().getDefinition());

		/* The consumer's contract should have constraints. */
		assertFalse(contractRegistry.getContracts(consumerMock, Role.CONSUMER)
				.iterator().next().getConstraints().isEmpty());

		/*
		 * The supplier's contract should have the consumer's contract as
		 * definition.
		 */
		assertEquals(contractRegistry.getContracts(consumerMock, Role.CONSUMER)
				.iterator().next(), contractRegistry.getContracts(supplierMock,
				Role.SUPPLIER).iterator().next().getDefinition());

		/* The supplier's contract should have constraints. */
		assertFalse(contractRegistry.getContracts(supplierMock, Role.SUPPLIER)
				.iterator().next().getConstraints().isEmpty());

		/* Remove the contracts. */
		try {
			contractRegistry.updateContract(UpdateType.REMOVE_CONTRACT, contract,
					legistlatorMock, Role.LEGISLATOR);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry.getContracts(legistlatorMock,
				Role.LEGISLATOR).size());
		assertEquals(0, contractRegistry.getContracts(legistlatorMock,
				Role.CONSUMER).size());
		assertEquals(0, contractRegistry.getContracts(legistlatorMock,
				Role.SUPPLIER).size());

		assertEquals(0, contractRegistry
				.getContracts(consumerMock, Role.LEGISLATOR).size());
		assertEquals(0, contractRegistry.getContracts(consumerMock, Role.CONSUMER)
				.size());
		assertEquals(0, contractRegistry.getContracts(consumerMock, Role.SUPPLIER)
				.size());

		assertEquals(0, contractRegistry
				.getContracts(supplierMock, Role.LEGISLATOR).size());
		assertEquals(0, contractRegistry.getContracts(supplierMock, Role.CONSUMER)
				.size());
		assertEquals(0, contractRegistry.getContracts(supplierMock, Role.SUPPLIER)
				.size());
	}

	/**
	 * <p>
	 * Tests the method
	 * {@link ContractRegistry#updateContract(net.java.treaty.contractregistry.ContractRegistry.UpdateType, net.java.treaty.Contract, net.java.treaty.Connector, net.java.treaty.Role)}
	 * by adding and removing a {@link Role#LEGISLATOR}, a {@link Role#CONSUMER}
	 * and a {@link Role#SUPPLIER} {@link Contract} to the
	 * {@link ContractRegistry}.
	 * </p>
	 */
	@Test
	public void testUpdateRegistry09() {

		ComponentMock componentMock;
		componentMock = new ComponentMock("Component 1");

		ConnectorMock legistlatorMock;
		legistlatorMock =
				new ConnectorMock("Legislator 1", componentMock, ConnectorType.SUPPLIER);

		ConnectorMock consumerMock;
		consumerMock =
				new ConnectorMock("Consumer 1", componentMock, ConnectorType.CONSUMER);

		ConnectorMock supplierMock;
		supplierMock =
				new ConnectorMock("Supplier 1", componentMock, ConnectorType.SUPPLIER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_01);

		ResourceManagerMock resourceManagerMock;
		resourceManagerMock = new ResourceManagerMock();
		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(resourceManagerMock, triggerRegistry);

		/*
		 * Add the legislator contract, the consumer contract and the supplier
		 * contract.
		 */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					legistlatorMock, Role.LEGISLATOR);
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					supplierMock, Role.SUPPLIER);
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contractRegistry
					.getContracts(supplierMock, Role.SUPPLIER).iterator().next(),
					consumerMock, Role.CONSUMER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertTrue(contractRegistry.getContracts(legistlatorMock, Role.LEGISLATOR)
				.contains(contract));
		assertFalse(contractRegistry.getContracts(consumerMock, Role.LEGISLATOR)
				.contains(contract));
		assertFalse(contractRegistry.getContracts(supplierMock, Role.LEGISLATOR)
				.contains(contract));
		assertFalse(contractRegistry.getContracts(legistlatorMock, Role.SUPPLIER)
				.contains(contract));

		/*
		 * The supplier's contract should have the legislator's contract as
		 * definition.
		 */
		assertEquals(contract, contractRegistry.getContracts(supplierMock,
				Role.SUPPLIER).iterator().next().getDefinition());

		/* The supplier's contract should have constraints. */
		assertFalse(contractRegistry.getContracts(supplierMock, Role.SUPPLIER)
				.iterator().next().getConstraints().isEmpty());

		/*
		 * The consumer's contract should have the supplier's contract as
		 * definition.
		 */
		assertEquals(contractRegistry.getContracts(supplierMock, Role.SUPPLIER)
				.iterator().next(), contractRegistry.getContracts(consumerMock,
				Role.CONSUMER).iterator().next().getDefinition());

		/* The consumer's contract should have constraints. */
		assertFalse(contractRegistry.getContracts(consumerMock, Role.CONSUMER)
				.iterator().next().getConstraints().isEmpty());

		/* Remove the contracts. */
		try {
			contractRegistry.updateContract(UpdateType.REMOVE_CONTRACT, contract,
					legistlatorMock, Role.LEGISLATOR);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry.getContracts(legistlatorMock,
				Role.LEGISLATOR).size());
		assertEquals(0, contractRegistry.getContracts(legistlatorMock,
				Role.CONSUMER).size());
		assertEquals(0, contractRegistry.getContracts(legistlatorMock,
				Role.SUPPLIER).size());

		assertEquals(0, contractRegistry
				.getContracts(consumerMock, Role.LEGISLATOR).size());
		assertEquals(0, contractRegistry.getContracts(consumerMock, Role.CONSUMER)
				.size());
		assertEquals(0, contractRegistry.getContracts(consumerMock, Role.SUPPLIER)
				.size());

		assertEquals(0, contractRegistry
				.getContracts(supplierMock, Role.LEGISLATOR).size());
		assertEquals(0, contractRegistry.getContracts(supplierMock, Role.CONSUMER)
				.size());
		assertEquals(0, contractRegistry.getContracts(supplierMock, Role.SUPPLIER)
				.size());
	}

	/**
	 * <p>
	 * Tests the method
	 * {@link ContractRegistry#getAffectedContracts(net.java.treaty.event.LifeCycleEvent)}
	 * .
	 * </p>
	 */
	@Test
	public void testGetAffectedContracts01() throws TreatyException {

		ComponentMock consumerComponentMock;
		consumerComponentMock = new ComponentMock("Consumer Component 1");

		ComponentMock supplier1ComponentMock;
		supplier1ComponentMock = new ComponentMock("Supplier Component 1");

		ComponentMock supplier2ComponentMock;
		supplier2ComponentMock = new ComponentMock("Supplier Component 2");

		ConnectorMock consumerMock;
		consumerMock =
				new ConnectorMock("Consumer 1", consumerComponentMock,
						ConnectorType.CONSUMER);

		ConnectorMock supplierMock1;
		supplierMock1 =
				new ConnectorMock("Supplier 1", supplier1ComponentMock,
						ConnectorType.SUPPLIER);

		ConnectorMock supplierMock2;
		supplierMock2 =
				new ConnectorMock("Supplier 2", supplier2ComponentMock,
						ConnectorType.SUPPLIER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_01);

		ResourceManagerMock resourceManagerMock;
		resourceManagerMock = new ResourceManagerMock();
		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(resourceManagerMock, triggerRegistry);

		/* Add the consumer contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					consumerMock, Role.CONSUMER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				consumerComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				supplier1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				supplier2ComponentMock).size());

		/* Add a supplier contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					supplierMock1, Role.SUPPLIER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				consumerComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				supplier1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				supplier2ComponentMock).size());

		/* Add a supplier contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					supplierMock2, Role.SUPPLIER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(2, contractRegistry.getAffectedContracts(trigger1,
				consumerComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				supplier1ComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				supplier2ComponentMock).size());
	}

	/**
	 * <p>
	 * Tests the method
	 * {@link ContractRegistry#getAffectedContracts(net.java.treaty.event.LifeCycleEvent)}
	 * .
	 * </p>
	 */
	@Test
	public void testGetAffectedContracts02() throws TreatyException {

		ComponentMock consumer1ComponentMock;
		consumer1ComponentMock = new ComponentMock("Consumer Component 1");

		ComponentMock consumer2ComponentMock;
		consumer2ComponentMock = new ComponentMock("Consumer Component 2");

		ComponentMock supplier1ComponentMock;
		supplier1ComponentMock = new ComponentMock("Supplier Component 1");

		ConnectorMock consumerMock1;
		consumerMock1 =
				new ConnectorMock("Consumer 1", consumer1ComponentMock,
						ConnectorType.CONSUMER);

		ConnectorMock consumerMock2;
		consumerMock2 =
				new ConnectorMock("Consumer 2", consumer2ComponentMock,
						ConnectorType.CONSUMER);

		ConnectorMock supplierMock1;
		supplierMock1 =
				new ConnectorMock("Supplier 1", supplier1ComponentMock,
						ConnectorType.SUPPLIER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_01);

		ResourceManagerMock resourceManagerMock;
		resourceManagerMock = new ResourceManagerMock();
		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(resourceManagerMock, triggerRegistry);

		/* Add the supplier contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					supplierMock1, Role.SUPPLIER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				consumer1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				supplier1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				consumer2ComponentMock).size());

		/* Add a consumer contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					consumerMock1, Role.CONSUMER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				consumer1ComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				supplier1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				consumer2ComponentMock).size());

		/* Add a consumer contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					consumerMock2, Role.CONSUMER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				consumer1ComponentMock).size());
		assertEquals(2, contractRegistry.getAffectedContracts(trigger1,
				supplier1ComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				consumer2ComponentMock).size());
	}

	/**
	 * <p>
	 * Tests the method
	 * {@link ContractRegistry#getAffectedContracts(net.java.treaty.event.LifeCycleEvent)}
	 * .
	 * </p>
	 */
	@Test
	public void testGetAffectedContracts03() throws TreatyException {

		ComponentMock legislator1ComponentMock;
		legislator1ComponentMock = new ComponentMock("Legislator Component 1");

		ComponentMock consumer1ComponentMock;
		consumer1ComponentMock = new ComponentMock("Consumer Component 1");

		ComponentMock supplier1ComponentMock;
		supplier1ComponentMock = new ComponentMock("Supplier Component 1");

		ComponentMock supplier2ComponentMock;
		supplier2ComponentMock = new ComponentMock("Supplier Component 2");

		ConnectorMock legislator1Mock;
		legislator1Mock =
				new ConnectorMock("Legislator 1", legislator1ComponentMock,
						ConnectorType.CONSUMER);

		ConnectorMock consumer1Mock;
		consumer1Mock =
				new ConnectorMock("Consumer 1", consumer1ComponentMock,
						ConnectorType.CONSUMER);

		ConnectorMock supplierMock1;
		supplierMock1 =
				new ConnectorMock("Supplier 1", supplier1ComponentMock,
						ConnectorType.SUPPLIER);

		ConnectorMock supplierMock2;
		supplierMock2 =
				new ConnectorMock("Supplier 2", supplier2ComponentMock,
						ConnectorType.SUPPLIER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_01);

		ResourceManagerMock resourceManagerMock;
		resourceManagerMock = new ResourceManagerMock();
		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(resourceManagerMock, triggerRegistry);

		/* Add the legislator contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					legislator1Mock, Role.LEGISLATOR);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				legislator1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				consumer1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				supplier1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				supplier2ComponentMock).size());

		/* Add the consumer contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					consumer1Mock, Role.CONSUMER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				legislator1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				consumer1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				supplier1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				supplier2ComponentMock).size());

		/* Add a supplier contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contractRegistry
					.getContracts(consumer1Mock, Role.CONSUMER).iterator().next(),
					supplierMock1, Role.SUPPLIER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				legislator1ComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				consumer1ComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				supplier1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				supplier2ComponentMock).size());

		/* Add a supplier contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contractRegistry
					.getContracts(consumer1Mock, Role.CONSUMER).iterator().next(),
					supplierMock2, Role.SUPPLIER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				legislator1ComponentMock).size());
		assertEquals(2, contractRegistry.getAffectedContracts(trigger1,
				consumer1ComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				supplier1ComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				supplier2ComponentMock).size());
	}

	/**
	 * <p>
	 * Tests the method
	 * {@link ContractRegistry#getAffectedContracts(net.java.treaty.event.LifeCycleEvent)}
	 * .
	 * </p>
	 */
	@Test
	public void testGetAffectedContracts04() throws TreatyException {

		ComponentMock legislator1ComponentMock;
		legislator1ComponentMock = new ComponentMock("Legislator Component 1");

		ComponentMock consumer1ComponentMock;
		consumer1ComponentMock = new ComponentMock("Consumer Component 1");

		ComponentMock consumer2ComponentMock;
		consumer2ComponentMock = new ComponentMock("Consumer Component 2");

		ComponentMock supplier1ComponentMock;
		supplier1ComponentMock = new ComponentMock("Supplier Component 1");

		ConnectorMock legislator1Mock;
		legislator1Mock =
				new ConnectorMock("Legislator 1", legislator1ComponentMock,
						ConnectorType.CONSUMER);

		ConnectorMock consumerMock1;
		consumerMock1 =
				new ConnectorMock("Consumer 1", consumer1ComponentMock,
						ConnectorType.CONSUMER);

		ConnectorMock consumerMock2;
		consumerMock2 =
				new ConnectorMock("Consumer 2", consumer2ComponentMock,
						ConnectorType.CONSUMER);

		ConnectorMock supplierMock1;
		supplierMock1 =
				new ConnectorMock("Supplier 1", supplier1ComponentMock,
						ConnectorType.SUPPLIER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_01);

		ResourceManagerMock resourceManagerMock;
		resourceManagerMock = new ResourceManagerMock();
		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(resourceManagerMock, triggerRegistry);

		/* Add the legislator contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					legislator1Mock, Role.LEGISLATOR);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				legislator1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				supplier1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				consumer1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				consumer2ComponentMock).size());

		/* Add the supplier contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					supplierMock1, Role.SUPPLIER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				legislator1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				supplier1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				consumer1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				consumer2ComponentMock).size());

		/* Add a consumer contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contractRegistry
					.getContracts(supplierMock1, Role.SUPPLIER).iterator().next(),
					consumerMock1, Role.CONSUMER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				legislator1ComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				supplier1ComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				consumer1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				consumer2ComponentMock).size());

		/* Add a consumer contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contractRegistry
					.getContracts(supplierMock1, Role.SUPPLIER).iterator().next(),
					consumerMock2, Role.CONSUMER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				legislator1ComponentMock).size());
		assertEquals(2, contractRegistry.getAffectedContracts(trigger1,
				supplier1ComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				consumer1ComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				consumer2ComponentMock).size());
	}

	/**
	 * <p>
	 * Tests the method
	 * {@link ContractRegistry#getAffectedContracts(net.java.treaty.event.LifeCycleEvent)}
	 * .
	 * </p>
	 */
	@Test
	public void testGetAffectedContracts05() throws TreatyException {

		ComponentMock consumerComponentMock;
		consumerComponentMock = new ComponentMock("Consumer Component 1");

		ComponentMock supplier1ComponentMock;
		supplier1ComponentMock = new ComponentMock("Supplier Component 1");

		ComponentMock supplier2ComponentMock;
		supplier2ComponentMock = new ComponentMock("Supplier Component 2");

		ConnectorMock consumerMock;
		consumerMock =
				new ConnectorMock("Consumer 1", consumerComponentMock,
						ConnectorType.CONSUMER);

		ConnectorMock supplierMock1;
		supplierMock1 =
				new ConnectorMock("Supplier 1", supplier1ComponentMock,
						ConnectorType.SUPPLIER);

		ConnectorMock supplierMock2;
		supplierMock2 =
				new ConnectorMock("Supplier 2", supplier2ComponentMock,
						ConnectorType.SUPPLIER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_01);

		ResourceManagerMock resourceManagerMock;
		resourceManagerMock = new ResourceManagerMock();
		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(resourceManagerMock, triggerRegistry);

		/* Add the consumer contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					consumerMock, Role.CONSUMER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				consumerComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				supplier1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				supplier2ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger2,
				consumerComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger2,
				supplier1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger2,
				supplier2ComponentMock).size());

		/* Add a supplier contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					supplierMock1, Role.SUPPLIER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				consumerComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				supplier1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				supplier2ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger2,
				consumerComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger2,
				supplier1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger2,
				supplier2ComponentMock).size());

		/* Add a supplier contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					supplierMock2, Role.SUPPLIER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(2, contractRegistry.getAffectedContracts(trigger1,
				consumerComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				supplier1ComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				supplier2ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger2,
				consumerComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger2,
				supplier1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger2,
				supplier2ComponentMock).size());
	}

	/**
	 * <p>
	 * Tests the method
	 * {@link ContractRegistry#getAffectedContracts(net.java.treaty.event.LifeCycleEvent)}
	 * .
	 * </p>
	 * 
	 * <p>
	 * Tests the method using a trigger that is defined as default trigger and
	 * thus must not be defined in the {@link Contract}.
	 * </p>
	 */
	@Test
	public void testGetAffectedContracts06() throws TreatyException {

		ComponentMock consumerComponentMock;
		consumerComponentMock = new ComponentMock("Consumer Component 1");

		ComponentMock supplier1ComponentMock;
		supplier1ComponentMock = new ComponentMock("Supplier Component 1");

		ComponentMock supplier2ComponentMock;
		supplier2ComponentMock = new ComponentMock("Supplier Component 2");

		ConnectorMock consumerMock;
		consumerMock =
				new ConnectorMock("Consumer 1", consumerComponentMock,
						ConnectorType.CONSUMER);

		ConnectorMock supplierMock1;
		supplierMock1 =
				new ConnectorMock("Supplier 1", supplier1ComponentMock,
						ConnectorType.SUPPLIER);

		ConnectorMock supplierMock2;
		supplierMock2 =
				new ConnectorMock("Supplier 2", supplier2ComponentMock,
						ConnectorType.SUPPLIER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_01);

		ResourceManagerMock resourceManagerMock;
		resourceManagerMock = new ResourceManagerMock();
		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(resourceManagerMock, triggerRegistry);

		/* Add the consumer contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					consumerMock, Role.CONSUMER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry.getAffectedContracts(defaultTrigger,
				consumerComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(defaultTrigger,
				supplier1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(defaultTrigger,
				supplier2ComponentMock).size());

		/* Add a supplier contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					supplierMock1, Role.SUPPLIER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(1, contractRegistry.getAffectedContracts(defaultTrigger,
				consumerComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(defaultTrigger,
				supplier1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(defaultTrigger,
				supplier2ComponentMock).size());

		/* Add a supplier contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					supplierMock2, Role.SUPPLIER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(2, contractRegistry.getAffectedContracts(defaultTrigger,
				consumerComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(defaultTrigger,
				supplier1ComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(defaultTrigger,
				supplier2ComponentMock).size());
	}

	/**
	 * <p>
	 * Tests the method
	 * {@link ContractRegistry#getAffectedContracts(net.java.treaty.event.LifeCycleEvent)}
	 * .
	 * </p>
	 * 
	 * <p>
	 * Tests the method using a trigger that is not defined in the
	 * {@link Contract} but has a super trigger that is defined in the
	 * {@link Contract}.
	 * </p>
	 */
	@Test
	public void testGetAffectedContracts07() throws TreatyException {

		ComponentMock consumerComponentMock;
		consumerComponentMock = new ComponentMock("Consumer Component 1");

		ComponentMock supplier1ComponentMock;
		supplier1ComponentMock = new ComponentMock("Supplier Component 1");

		ComponentMock supplier2ComponentMock;
		supplier2ComponentMock = new ComponentMock("Supplier Component 2");

		ConnectorMock consumerMock;
		consumerMock =
				new ConnectorMock("Consumer 1", consumerComponentMock,
						ConnectorType.CONSUMER);

		ConnectorMock supplierMock1;
		supplierMock1 =
				new ConnectorMock("Supplier 1", supplier1ComponentMock,
						ConnectorType.SUPPLIER);

		ConnectorMock supplierMock2;
		supplierMock2 =
				new ConnectorMock("Supplier 2", supplier2ComponentMock,
						ConnectorType.SUPPLIER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_02);

		ResourceManagerMock resourceManagerMock;
		resourceManagerMock = new ResourceManagerMock();
		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(resourceManagerMock, triggerRegistry);

		/* Add the consumer contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					consumerMock, Role.CONSUMER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				consumerComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				supplier1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				supplier2ComponentMock).size());

		/* Add a supplier contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					supplierMock1, Role.SUPPLIER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				consumerComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				supplier1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(trigger1,
				supplier2ComponentMock).size());

		/* Add a supplier contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					supplierMock2, Role.SUPPLIER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(2, contractRegistry.getAffectedContracts(trigger1,
				consumerComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				supplier1ComponentMock).size());
		assertEquals(1, contractRegistry.getAffectedContracts(trigger1,
				supplier2ComponentMock).size());
	}

	/**
	 * <p>
	 * Tests the method
	 * {@link ContractRegistry#getAffectedContracts(net.java.treaty.event.LifeCycleEvent)}
	 * .
	 * </p>
	 * 
	 * <p>
	 * Tests the method using a trigger that is not defined in the
	 * {@link Contract} but is a super trigger of the trigger that is defined in
	 * the {@link Contract}.
	 * </p>
	 */
	@Test
	public void testGetAffectedContracts08() throws TreatyException {

		ComponentMock consumerComponentMock;
		consumerComponentMock = new ComponentMock("Consumer Component 1");

		ComponentMock supplier1ComponentMock;
		supplier1ComponentMock = new ComponentMock("Supplier Component 1");

		ComponentMock supplier2ComponentMock;
		supplier2ComponentMock = new ComponentMock("Supplier Component 2");

		ConnectorMock consumerMock;
		consumerMock =
				new ConnectorMock("Consumer 1", consumerComponentMock,
						ConnectorType.CONSUMER);

		ConnectorMock supplierMock1;
		supplierMock1 =
				new ConnectorMock("Supplier 1", supplier1ComponentMock,
						ConnectorType.SUPPLIER);

		ConnectorMock supplierMock2;
		supplierMock2 =
				new ConnectorMock("Supplier 2", supplier2ComponentMock,
						ConnectorType.SUPPLIER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_01);

		ResourceManagerMock resourceManagerMock;
		resourceManagerMock = new ResourceManagerMock();
		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(resourceManagerMock, triggerRegistry);

		/* Add the consumer contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					consumerMock, Role.CONSUMER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry.getAffectedContracts(parentTrigger1,
				consumerComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(parentTrigger1,
				supplier1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(parentTrigger1,
				supplier2ComponentMock).size());

		/* Add a supplier contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					supplierMock1, Role.SUPPLIER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry.getAffectedContracts(parentTrigger1,
				consumerComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(parentTrigger1,
				supplier1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(parentTrigger1,
				supplier2ComponentMock).size());

		/* Add a supplier contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					supplierMock2, Role.SUPPLIER);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertEquals(0, contractRegistry.getAffectedContracts(parentTrigger1,
				consumerComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(parentTrigger1,
				supplier1ComponentMock).size());
		assertEquals(0, contractRegistry.getAffectedContracts(parentTrigger1,
				supplier2ComponentMock).size());
	}

	/**
	 * <p>
	 * A helper method that initializes a simple {@link Contract} used during
	 * testing.
	 * </p>
	 * 
	 * @param fileName
	 *          The file Name of the {@link Contract} that shall be created.
	 * @return A simple {@link Contract} used during testing.
	 */
	private Contract createTestContract(String fileName) {

		Contract result;
		result = null;

		URL location;
		location = null;

		/* Try to read the contract. */
		try {
			File file;
			file = new File(fileName);

			location = file.toURI().toURL();

			ContractReader reader;
			reader = new XMLContractReader();

			result = reader.read(location.openStream(), VocabularyMock.INSTANCE);
			result.setLocation(location);
		}

		catch (TreatyException e) {
			fail("Exception loading contract from " + location + ":" + e.getMessage());
		}

		catch (IOException e) {
			fail("Exception loading contract from " + location + ":" + e.getMessage());
		}

		return result;
	}
}