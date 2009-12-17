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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import net.java.treaty.Condition;
import net.java.treaty.Component;
import net.java.treaty.Connector;
import net.java.treaty.ConnectorType;
import net.java.treaty.Contract;
import net.java.treaty.ContractReader;
import net.java.treaty.ExistsCondition;
import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.ResourceManager;
import net.java.treaty.TreatyException;
import net.java.treaty.xml.XMLContractReader;

import org.junit.Test;

import test.net.java.treaty.contractregistry.VocabularyMock;
import test.net.java.treaty.mocks.ComponentMock;
import test.net.java.treaty.mocks.ConnectorMock;
import test.net.java.treaty.mocks.ResourceManagerMock;

/**
 * <p>
 * Provides test cases to test the class {@link Contract}.
 * </p>
 * 
 * @author Claas Wilke
 */
public class ContractTests {

	/**
	 * <p>
	 * A test case testing the method
	 * {@link Contract#bindConsumer(net.java.treaty.Connector, net.java.treaty.ResourceManager)}
	 * .
	 * </p>
	 * 
	 * @throws TreatyException
	 *           Thrown, if the test case fails.
	 */
	@Test
	public void testBindConsumer01() throws TreatyException {

		Contract contract;
		contract = this.createTestContract();

		Component component;
		component = new ComponentMock("component1");

		Connector consumer;
		consumer =
				new ConnectorMock("consumer1", component, ConnectorType.CONSUMER);

		ResourceManager resourceManager;
		resourceManager = new ResourceManagerMock();

		List<Contract> boundContracts;
		boundContracts = contract.bindConsumer(consumer, resourceManager);

		assertNotNull(boundContracts);
		assertEquals(1, boundContracts.size());

		Contract boundContract;
		boundContract = boundContracts.get(0);

		assertNotNull(boundContract);

		/* The contract should have bound consumer resources. */
		assertNotNull(boundContract.getConsumerResources());
		assertEquals(1, boundContract.getConsumerResources().size());
	}

	/**
	 * <p>
	 * A test case testing the method
	 * {@link Contract#bindSupplier(net.java.treaty.Connector, net.java.treaty.ResourceManager)}
	 * .
	 * </p>
	 * 
	 * @throws TreatyException
	 *           Thrown, if the test case fails.
	 */
	@Test
	public void testBindSupplier01() throws TreatyException {

		Contract contract;
		contract = this.createTestContract();

		Component component;
		component = new ComponentMock("component1");

		Connector supplier;
		supplier =
				new ConnectorMock("supplier1", component, ConnectorType.SUPPLIER);

		ResourceManager resourceManager;
		resourceManager = new ResourceManagerMock();

		List<Contract> boundContracts;
		boundContracts = contract.bindSupplier(supplier, resourceManager);

		assertNotNull(boundContracts);
		assertEquals(1, boundContracts.size());

		Contract boundContract;
		boundContract = boundContracts.get(0);

		assertNotNull(boundContract);

		/* The contract should have bound supplier resources. */
		assertNotNull(boundContract.getSupplierResources());
		assertEquals(1, boundContract.getSupplierResources().size());
	}

	/**
	 * <p>
	 * A test case testing the methods
	 * {@link Contract#bindConsumer(Connector, ResourceManager)} and
	 * {@link Contract#bindSupplier(Connector, ResourceManager)}.
	 * </p>
	 * 
	 * @throws TreatyException
	 *           Thrown, if the test case fails.
	 */
	@Test
	public void testBindConsumerAndSupplier01() throws TreatyException {

		Contract contract;
		contract = this.createTestContract();

		Component component;
		component = new ComponentMock("component1");

		Connector consumer;
		consumer =
				new ConnectorMock("consumer1", component, ConnectorType.CONSUMER);

		Connector supplier;
		supplier =
				new ConnectorMock("supplier1", component, ConnectorType.SUPPLIER);

		ResourceManager resourceManager;
		resourceManager = new ResourceManagerMock();

		List<Contract> boundContracts;

		/* Bind the consumer. */
		boundContracts = contract.bindConsumer(consumer, resourceManager);

		assertNotNull(boundContracts);
		assertEquals(1, boundContracts.size());

		Contract boundContract;
		boundContract = boundContracts.get(0);

		assertNotNull(boundContract);

		/* Bind the supplier. */
		boundContracts = boundContract.bindSupplier(supplier, resourceManager);

		assertNotNull(boundContracts);
		assertEquals(1, boundContracts.size());

		boundContract = boundContracts.get(0);

		assertNotNull(boundContract);

		/* The contract should have bound consumer resources. */
		assertNotNull(boundContract.getConsumerResources());
		assertEquals(1, boundContract.getConsumerResources().size());

		/* The contract should have bound supplier resources. */
		assertNotNull(boundContract.getSupplierResources());
		assertEquals(1, boundContract.getConsumerResources().size());

		/*
		 * All conditions of the contract should be bound to the consumer or the
		 * supplier resources.
		 */
		for (Condition condition : boundContract.getConstraints()) {

			if (condition instanceof ExistsCondition) {
				ExistsCondition existsCondition;
				existsCondition = (ExistsCondition) condition;

				assertTrue(boundContract.getConsumerResources().contains(
						existsCondition.getResource())
						|| boundContract.getSupplierResources().contains(
								existsCondition.getResource()));
			}

			else if (condition instanceof PropertyCondition) {
				PropertyCondition propertyCondition;
				propertyCondition = (PropertyCondition) condition;

				assertTrue(boundContract.getConsumerResources().contains(
						propertyCondition.getResource())
						|| boundContract.getSupplierResources().contains(
								propertyCondition.getResource()));
			}

			else if (condition instanceof RelationshipCondition) {
				RelationshipCondition relationshipCondition;
				relationshipCondition = (RelationshipCondition) condition;

				assertTrue(boundContract.getConsumerResources().contains(
						relationshipCondition.getResource1())
						|| boundContract.getSupplierResources().contains(
								relationshipCondition.getResource1()));

				assertTrue(boundContract.getConsumerResources().contains(
						relationshipCondition.getResource2())
						|| boundContract.getSupplierResources().contains(
								relationshipCondition.getResource2()));
			}
			// no else.
		}
		// end for.
	}

	/**
	 * <p>
	 * A helper method that initializes a simple {@link Contract} used during
	 * testing.
	 * </p>
	 * 
	 * @return A simple {@link Contract} used during testing.
	 */
	private Contract createTestContract() {

		Contract result;
		result = null;

		URL location;
		location = null;

		/* Try to read the contract. */
		try {
			File file;
			file = new File("tests/test/net/java/treaty/mocks/test02.contract");

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