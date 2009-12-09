/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package test.net.java.treaty.trigger.verification;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

import net.java.treaty.Component;
import net.java.treaty.Connector;
import net.java.treaty.ConnectorType;
import net.java.treaty.Contract;
import net.java.treaty.ContractReader;
import net.java.treaty.Role;
import net.java.treaty.TreatyException;
import net.java.treaty.contractregistry.ContractRegistry;
import net.java.treaty.contractregistry.ContractRegistry.UpdateType;
import net.java.treaty.trigger.AbstractTriggerVocabulary;
import net.java.treaty.trigger.TriggerRegistry;
import net.java.treaty.trigger.verification.AbstractTriggeredVerifier;
import net.java.treaty.trigger.verification.TriggeredVerifierListener;
import net.java.treaty.xml.XMLContractReader;

import org.junit.Test;

import test.net.java.treaty.contractregistry.VocabularyMock;
import test.net.java.treaty.mocks.ComponentMock;
import test.net.java.treaty.mocks.ConnectorMock;
import test.net.java.treaty.mocks.ResourceManagerMock;
import test.net.java.treaty.mocks.TriggerVocabularyMock;
import test.net.java.treaty.mocks.TriggeredVerifierMock;

/**
 * <p>
 * Test cases to test the class {@link AbstractTriggeredVerifier}.
 * </p>
 * 
 * @author Claas Wilke
 */
public class TriggeredVerifierTest implements TriggeredVerifierListener {

	/** The location of the {@link Contract} files used for testing. */
	private static final String TEST_CONTRACT_LOCATION =
			"tests/test/net/java/treaty/mocks/";

	/** The name of a {@link Contract} file used for testing. */
	private static final String TEST_CONTRACT_1 =
			TEST_CONTRACT_LOCATION + "test04.contract";

	/** The name of a {@link Contract} file used for testing. */
	private static final String TEST_CONTRACT_2 =
			TEST_CONTRACT_LOCATION + "test05.contract";

	/** The name of a {@link Contract} file used for testing. */
	private static final String TEST_CONTRACT_3 =
			TEST_CONTRACT_LOCATION + "test06.contract";

	/** The maximum loops to wait for a verification result. */
	private static final int WAIT_TOTAL_LOOPS = 20;

	/** The time to wait in a loop. */
	private static final long WAIT_PAUSE_LENGTH = 50;

	/** Contains the verification result used during testing. */
	private Boolean verificationResult;

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.event.verification.TriggeredVerifierListener#verificationFailed
	 * (java.net.URI, net.java.treaty.Contract)
	 */
	public void verificationFailed(URI triggerType, Contract contrac) {

		this.verificationResult = false;
	}

	/*
	 * (non-Javadoc)
	 * @seenet.java.treaty.event.verification.TriggeredVerifierListener#
	 * verificationSucceeded(java.net.URI, net.java.treaty.Contract)
	 */
	public void verificationSucceeded(URI triggerType, Contract contrac) {

		this.verificationResult = true;
	}

	/**
	 * <p>
	 * A test case testing the Triggered Verification of the
	 * {@link AbstractTriggeredVerifier}.
	 * </p>
	 * 
	 * @throws TreatyException
	 *           If thrown, the test case fails.
	 * @throws URISyntaxException
	 *           If thrown, the test case fails.
	 */
	@Test
	public void testVerification01() throws TreatyException, URISyntaxException {

		/* Prepare test data. */
		TriggerRegistry triggerRegistry;
		triggerRegistry = new TriggerRegistry();

		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(new ResourceManagerMock(), triggerRegistry);

		Component consumerComponent;
		consumerComponent = new ComponentMock("consumercomponent");

		Connector consumer;
		consumer =
				new ConnectorMock("consumercomponent.consumer", consumerComponent,
						ConnectorType.CONSUMER);

		Component supplierComponent;
		supplierComponent = new ComponentMock("suppliercomponent");

		Connector supplier;
		supplier =
				new ConnectorMock("suppliercomponent.supplier", supplierComponent,
						ConnectorType.SUPPLIER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_1);

		contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
				consumer, Role.CONSUMER);
		contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contractRegistry
				.getContracts(consumer, Role.CONSUMER).iterator().next(), supplier,
				Role.SUPPLIER);

		/* Create the verifier. */
		AbstractTriggeredVerifier triggeredVerifier;
		triggeredVerifier = new TriggeredVerifierMock();

		/* Register as listener. */
		triggeredVerifier.addListener(this);

		/* Reset the verification result. */
		this.verificationResult = null;

		/* Trigger verification. */
		URI triggerType;
		triggerType = new URI(TriggerVocabularyMock.NAME_TRIGGER_1);

		AbstractTriggerVocabulary triggerVocabulary;
		triggerVocabulary = new TriggerVocabularyMock();
		triggerVocabulary.addEventListener(triggeredVerifier);
		triggerRegistry.addTriggerVocabulary(triggerVocabulary);

		Set<Contract> contracts;
		contracts =
				contractRegistry.getAffectedContracts(triggerType, consumerComponent);

		triggerVocabulary.notifyEventListners(triggerType, contracts);

		/* Wait for result. */
		int loops;
		loops = 0;

		while (verificationResult == null) {
			/* Wait some time to improve CPU performance. */
			try {
				Thread.sleep(WAIT_PAUSE_LENGTH);
			}

			catch (InterruptedException e) {
				fail("Cannot ensure that testcase behaves determinisitc. Test failed.");
			}
			// end catch.

			loops++;
			if (loops > WAIT_TOTAL_LOOPS) {
				fail("Verification result was not available.");
			}
			// no else.
		}

		/* Result should be positive. */
		assertTrue(this.verificationResult);

		/* Unregister as listener. */
		triggeredVerifier.removeListener(this);
	}

	/**
	 * <p>
	 * A test case testing the Triggered Verification of the
	 * {@link AbstractTriggeredVerifier}.
	 * </p>
	 * 
	 * @throws TreatyException
	 *           If thrown, the test case fails.
	 * @throws URISyntaxException
	 *           If thrown, the test case fails.
	 */
	@Test
	public void testVerification02() throws TreatyException, URISyntaxException {

		/* Prepare test data. */
		TriggerRegistry triggerRegistry;
		triggerRegistry = new TriggerRegistry();

		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(new ResourceManagerMock(), triggerRegistry);

		Component consumerComponent;
		consumerComponent = new ComponentMock("consumercomponent");

		Connector consumer;
		consumer =
				new ConnectorMock("consumercomponent.consumer", consumerComponent,
						ConnectorType.CONSUMER);

		Component supplierComponent;
		supplierComponent = new ComponentMock("suppliercomponent");

		Connector supplier;
		supplier =
				new ConnectorMock("suppliercomponent.supplier", supplierComponent,
						ConnectorType.SUPPLIER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_2);

		contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
				consumer, Role.CONSUMER);
		contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contractRegistry
				.getContracts(consumer, Role.CONSUMER).iterator().next(), supplier,
				Role.SUPPLIER);

		/* Create the verifier. */
		AbstractTriggeredVerifier triggeredVerifier;
		triggeredVerifier = new TriggeredVerifierMock();

		/* Register as listener. */
		triggeredVerifier.addListener(this);

		/* Reset the verification result. */
		this.verificationResult = null;

		/* Trigger verification. */
		URI triggerType;
		triggerType = new URI(TriggerVocabularyMock.NAME_TRIGGER_2);

		AbstractTriggerVocabulary triggerVocabulary;
		triggerVocabulary = new TriggerVocabularyMock();
		triggerVocabulary.addEventListener(triggeredVerifier);
		triggerRegistry.addTriggerVocabulary(triggerVocabulary);

		Set<Contract> contracts;
		contracts =
				contractRegistry.getAffectedContracts(triggerType, consumerComponent);

		triggerVocabulary.notifyEventListners(triggerType, contracts);

		/* Wait for result. */
		int loops;
		loops = 0;

		while (verificationResult == null) {
			/* Wait some time to improve CPU performance. */
			try {
				Thread.sleep(WAIT_PAUSE_LENGTH);
			}

			catch (InterruptedException e) {
				fail("Cannot ensure that testcase behaves determinisitc. Test failed.");
			}
			// end catch.

			loops++;
			if (loops > WAIT_TOTAL_LOOPS) {
				fail("Verification result was not available.");
			}
			// no else.
		}

		/* Result should be negative. */
		assertFalse(this.verificationResult);

		/* Unregister as listener. */
		triggeredVerifier.removeListener(this);
	}

	/**
	 * <p>
	 * A test case testing the Triggered Verification of the
	 * {@link AbstractTriggeredVerifier} with a super trigger defined in a
	 * {@link Contract}.
	 * </p>
	 * 
	 * @throws TreatyException
	 *           If thrown, the test case fails.
	 * @throws URISyntaxException
	 *           If thrown, the test case fails.
	 */
	@Test
	public void testVerification03() throws TreatyException, URISyntaxException {

		/* Prepare test data. */
		TriggerRegistry triggerRegistry;
		triggerRegistry = new TriggerRegistry();

		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(new ResourceManagerMock(), triggerRegistry);

		Component consumerComponent;
		consumerComponent = new ComponentMock("consumercomponent");

		Connector consumer;
		consumer =
				new ConnectorMock("consumercomponent.consumer", consumerComponent,
						ConnectorType.CONSUMER);

		Component supplierComponent;
		supplierComponent = new ComponentMock("suppliercomponent");

		Connector supplier;
		supplier =
				new ConnectorMock("suppliercomponent.supplier", supplierComponent,
						ConnectorType.SUPPLIER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_3);

		contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
				consumer, Role.CONSUMER);
		contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contractRegistry
				.getContracts(consumer, Role.CONSUMER).iterator().next(), supplier,
				Role.SUPPLIER);

		/* Create the verifier. */
		AbstractTriggeredVerifier triggeredVerifier;
		triggeredVerifier = new TriggeredVerifierMock();

		/* Register as listener. */
		triggeredVerifier.addListener(this);

		/* Reset the verification result. */
		this.verificationResult = null;

		/* Trigger verification. */
		URI triggerType;
		triggerType = new URI(TriggerVocabularyMock.NAME_PARENT_TRIGGER_1);

		AbstractTriggerVocabulary triggerVocabulary;
		triggerVocabulary = new TriggerVocabularyMock();
		triggerVocabulary.addEventListener(triggeredVerifier);
		triggerRegistry.addTriggerVocabulary(triggerVocabulary);

		Set<Contract> contracts;
		contracts =
				contractRegistry.getAffectedContracts(triggerType, consumerComponent);

		triggerVocabulary.notifyEventListners(triggerType, contracts);

		/* Wait for result. */
		int loops;
		loops = 0;

		while (verificationResult == null) {
			/* Wait some time to improve CPU performance. */
			try {
				Thread.sleep(WAIT_PAUSE_LENGTH);
			}

			catch (InterruptedException e) {
				fail("Cannot ensure that testcase behaves determinisitc. Test failed.");
			}
			// end catch.

			loops++;
			if (loops > WAIT_TOTAL_LOOPS) {
				fail("Verification result was not available.");
			}
			// no else.
		}

		/* Result should be positive. */
		assertTrue(this.verificationResult);

		/* Unregister as listener. */
		triggeredVerifier.removeListener(this);
	}

	/**
	 * <p>
	 * A test case testing the Triggered Verification of the
	 * {@link AbstractTriggeredVerifier} with a sub trigger of a super trigger
	 * defined in a {@link Contract}.
	 * </p>
	 * 
	 * @throws TreatyException
	 *           If thrown, the test case fails.
	 * @throws URISyntaxException
	 *           If thrown, the test case fails.
	 */
	@Test
	public void testVerification04() throws TreatyException, URISyntaxException {

		/* Prepare test data. */
		TriggerRegistry triggerRegistry;
		triggerRegistry = new TriggerRegistry();

		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(new ResourceManagerMock(), triggerRegistry);

		Component consumerComponent;
		consumerComponent = new ComponentMock("consumercomponent");

		Connector consumer;
		consumer =
				new ConnectorMock("consumercomponent.consumer", consumerComponent,
						ConnectorType.CONSUMER);

		Component supplierComponent;
		supplierComponent = new ComponentMock("suppliercomponent");

		Connector supplier;
		supplier =
				new ConnectorMock("suppliercomponent.supplier", supplierComponent,
						ConnectorType.SUPPLIER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_3);

		contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
				consumer, Role.CONSUMER);
		contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contractRegistry
				.getContracts(consumer, Role.CONSUMER).iterator().next(), supplier,
				Role.SUPPLIER);

		/* Create the verifier. */
		AbstractTriggeredVerifier triggeredVerifier;
		triggeredVerifier = new TriggeredVerifierMock();

		/* Register as listener. */
		triggeredVerifier.addListener(this);

		/* Reset the verification result. */
		this.verificationResult = null;

		/* Should Trigger verification. */
		URI triggerType;
		triggerType = new URI(TriggerVocabularyMock.NAME_TRIGGER_1);

		AbstractTriggerVocabulary triggerVocabulary;
		triggerVocabulary = new TriggerVocabularyMock();
		triggerVocabulary.addEventListener(triggeredVerifier);
		triggerRegistry.addTriggerVocabulary(triggerVocabulary);

		Set<Contract> contracts;
		contracts =
				contractRegistry.getAffectedContracts(triggerType, consumerComponent);

		triggerVocabulary.notifyEventListners(triggerType, contracts);

		/* Wait for result. */
		int loops;
		loops = 0;

		while (verificationResult == null) {
			/* Wait some time to improve CPU performance. */
			try {
				Thread.sleep(WAIT_PAUSE_LENGTH);
			}

			catch (InterruptedException e) {
				fail("Cannot ensure that testcase behaves determinisitc. Test failed.");
			}
			// end catch.

			loops++;
			if (loops > WAIT_TOTAL_LOOPS) {
				fail("Verification result was not available.");
			}
			// no else.
		}

		/* Result should be positive. */
		assertTrue(this.verificationResult);

		/* Unregister as listener. */
		triggeredVerifier.removeListener(this);
	}

	/**
	 * <p>
	 * A test case testing the Triggered Verification of the
	 * {@link AbstractTriggeredVerifier} with a trigger that is not a sub trigger
	 * of a super trigger defined in a {@link Contract}.
	 * </p>
	 * 
	 * @throws TreatyException
	 *           If thrown, the test case fails.
	 * @throws URISyntaxException
	 *           If thrown, the test case fails.
	 */
	@Test
	public void testVerification05() throws TreatyException, URISyntaxException {

		/* Prepare test data. */
		TriggerRegistry triggerRegistry;
		triggerRegistry = new TriggerRegistry();

		ContractRegistry contractRegistry;
		contractRegistry =
				new ContractRegistry(new ResourceManagerMock(), triggerRegistry);

		Component consumerComponent;
		consumerComponent = new ComponentMock("consumercomponent");

		Connector consumer;
		consumer =
				new ConnectorMock("consumercomponent.consumer", consumerComponent,
						ConnectorType.CONSUMER);

		Component supplierComponent;
		supplierComponent = new ComponentMock("suppliercomponent");

		Connector supplier;
		supplier =
				new ConnectorMock("suppliercomponent.supplier", supplierComponent,
						ConnectorType.SUPPLIER);

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_3);

		contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contract,
				consumer, Role.CONSUMER);
		contractRegistry.updateContract(UpdateType.ADD_CONTRACT, contractRegistry
				.getContracts(consumer, Role.CONSUMER).iterator().next(), supplier,
				Role.SUPPLIER);

		/* Create the verifier. */
		AbstractTriggeredVerifier triggeredVerifier;
		triggeredVerifier = new TriggeredVerifierMock();

		/* Register as listener. */
		triggeredVerifier.addListener(this);

		/* Reset the verification result. */
		this.verificationResult = null;

		/* Should not Trigger verification. */
		URI triggerType;
		triggerType = new URI(TriggerVocabularyMock.NAME_TRIGGER_2);

		AbstractTriggerVocabulary triggerVocabulary;
		triggerVocabulary = new TriggerVocabularyMock();
		triggerVocabulary.addEventListener(triggeredVerifier);
		triggerRegistry.addTriggerVocabulary(triggerVocabulary);

		Set<Contract> contracts;
		contracts =
				contractRegistry.getAffectedContracts(triggerType, consumerComponent);

		triggerVocabulary.notifyEventListners(triggerType, contracts);

		/* Wait for result. */
		int loops;
		loops = 0;

		while (verificationResult == null) {
			/* Wait some time to improve CPU performance. */
			try {
				Thread.sleep(WAIT_PAUSE_LENGTH);
			}

			catch (InterruptedException e) {
				fail("Cannot ensure that testcase behaves determinisitc. Test failed.");
			}
			// end catch.

			loops++;
			if (loops > WAIT_TOTAL_LOOPS) {
				break;
			}
			// no else.
		}

		/* Result should be null. */
		assertNull(this.verificationResult);

		/* Unregister as listener. */
		triggeredVerifier.removeListener(this);
	}

	/**
	 * <p>
	 * A helper method that initializes a simple {@link Contract} used during
	 * testing.
	 * </p>
	 * 
	 * @param The
	 *          name of the {@link Contract} file that shall be created.
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