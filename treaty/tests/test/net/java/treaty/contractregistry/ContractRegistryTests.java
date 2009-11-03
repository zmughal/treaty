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
import net.java.treaty.ConnectorType;
import net.java.treaty.Contract;
import net.java.treaty.Role;
import net.java.treaty.TreatyException;
import net.java.treaty.contractregistry.ContractRegistry;
import net.java.treaty.contractregistry.ContractRegistry.UpdateType;

import org.junit.Test;

/**
 * <p>
 * Contains test cases to test the {@link ContractRegistry}.
 * </p>
 * 
 * @author Claas Wilke
 */
public class ContractRegistryTests {

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
		contract = new Contract();

		ContractRegistry contractRegistry;
		contractRegistry = new ContractRegistry();

		/* Add the contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					legistlatorMock, Role.LEGISLATOR);
		}

		catch (TreatyException e) {
			fail("Unexpected TreatyException: " + e.getMessage());
		}
		// end catch.

		assertTrue(contractRegistry.getContracts(legistlatorMock, Role.LEGISLATOR)
				.contains(contract));
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
		contract = new Contract();

		ContractRegistry contractRegistry;
		contractRegistry = new ContractRegistry();

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
		assertTrue(contractRegistry.getContracts(consumerMock, Role.CONSUMER)
				.contains(contract));
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

		ConnectorMock consumerMock;
		consumerMock =
				new ConnectorMock("Supplier 1", componentMock, ConnectorType.SUPPLIER);

		Contract contract;
		contract = new Contract();

		ContractRegistry contractRegistry;
		contractRegistry = new ContractRegistry();

		/* Add the contract. */
		try {
			contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
					consumerMock, Role.CONSUMER);
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
		contract = new Contract();

		ContractRegistry contractRegistry;
		contractRegistry = new ContractRegistry();

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
		assertTrue(contractRegistry.getContracts(supplierMock, Role.SUPPLIER)
				.contains(contract));

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
		contract = new Contract();

		ContractRegistry contractRegistry;
		contractRegistry = new ContractRegistry();

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
		contract = new Contract();

		ContractRegistry contractRegistry;
		contractRegistry = new ContractRegistry();

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

		assertTrue(contractRegistry.getContracts(legistlatorMock, Role.LEGISLATOR)
				.contains(contract));
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
		contract = new Contract();

		ContractRegistry contractRegistry;
		contractRegistry = new ContractRegistry();

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
		contract = new Contract();

		ContractRegistry contractRegistry;
		contractRegistry = new ContractRegistry();

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

		/*
		 * The supplier's contract should have the consumer's contract as
		 * definition.
		 */
		assertEquals(contractRegistry.getContracts(consumerMock, Role.CONSUMER)
				.iterator().next(), contractRegistry.getContracts(supplierMock,
				Role.SUPPLIER).iterator().next().getDefinition());

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
		contract = new Contract();

		ContractRegistry contractRegistry;
		contractRegistry = new ContractRegistry();

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

		/*
		 * The consumer's contract should have the supplier's contract as
		 * definition.
		 */
		assertEquals(contractRegistry.getContracts(supplierMock, Role.SUPPLIER)
				.iterator().next(), contractRegistry.getContracts(consumerMock,
				Role.CONSUMER).iterator().next().getDefinition());

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
}