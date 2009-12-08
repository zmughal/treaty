/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.tests.trigger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.ContractReader;
import net.java.treaty.Role;
import net.java.treaty.TreatyException;
import net.java.treaty.contractregistry.ContractRegistry.UpdateType;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.EclipseExtensionPoint;
import net.java.treaty.eclipse.VocabularyRegistry;
import net.java.treaty.eclipse.contractregistry.EclipseAdapterFactory;
import net.java.treaty.eclipse.contractregistry.EclipseConnectorAdaptationException;
import net.java.treaty.eclipse.contractregistry.EclipseContractRegistry;
import net.java.treaty.eclipse.tests.Activator;
import net.java.treaty.eclipse.trigger.EclipseTriggerRegistry;
import net.java.treaty.eclipse.verification.TriggeredEclipseVerifier;
import net.java.treaty.trigger.verification.AbstractTriggeredVerifier;
import net.java.treaty.trigger.verification.TriggeredVerifierListener;
import net.java.treaty.xml.XMLContractReader;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.Bundle;

/**
 * <p>
 * Test cases to test the class {@link AbstractTriggeredVerifier}.
 * </p>
 * 
 * @author Claas Wilke
 */
public class TriggeredVerifierTest implements TriggeredVerifierListener {

	/** The ID of a {@link Bundle} used during testing. */
	private static final String CONSUMER_BUNDLE_1_ID =
			"net.java.treaty.test.consumer1";

	/** The ID of an {@link IExtensionPoint} used during testing. */
	private static final String CONSUMER_BUNDLE_1_EXTENSION_POINT1_ID =
			CONSUMER_BUNDLE_1_ID + ".xp1";

	/** The ID of a {@link Bundle} used during testing. */
	private static final String PROVIDER_BUNDLE_1_ID =
			"net.java.treaty.test.provider1";

	/** The ID of an {@link IExtension} used during testing. */
	private static final String PROVIDER_BUNDLE_1_EXTENSION_1_ID =
			PROVIDER_BUNDLE_1_ID + ".x1";

	/** The location of the {@link Contract} files used for testing. */
	private static final String TEST_CONTRACT_LOCATION =
			"resources/testcontracts/";

	/** The name of a {@link Contract} file used for testing. */
	private static final String TEST_CONTRACT_1 =
			TEST_CONTRACT_LOCATION + "test01.contract";

	/** The name of a {@link Contract} file used for testing. */
	private static final String TEST_CONTRACT_2 =
			TEST_CONTRACT_LOCATION + "test02.contract";

	/** The maximum loops to wait for a verification result. */
	private static final int WAIT_TOTAL_LOOPS = 100;

	/** The time to wait in a loop. */
	private static final long WAIT_PAUSE_LENGTH = 50;

	/** A {@link Bundle} used during testing. */
	private static Bundle consumerBundle1;

	/** The {@link IExtensionRegistry} used during testing. */
	private static IExtensionRegistry extensionRegistry =
			org.eclipse.core.runtime.Platform.getExtensionRegistry();

	/** A {@link Bundle} used during testing. */
	private static Bundle providerBundle1;

	/** An {@link IExtensionPoint} used during testing. */
	private static IExtensionPoint extensionPoint1;

	/** An {@link EclipseExtensionPoint} used during testing. */
	private static EclipseExtensionPoint eclipseExtensionPoint1;

	/** An {@link IExtension} used during testing. */
	private static IExtension extension1;

	/** An {@link EclipseExtension} used during testing. */
	private static EclipseExtension eclipseExtension1;

	/** A {@link Contract} used during testing. */
	private static Contract contract1;

	/** A {@link Contract} used during testing. */
	private static Contract contract2;

	/** The {@link TriggeredVerifier} used for testing. */
	private static TriggerVocabularyMock triggerVocabularyMock;

	/** Contains the verification result used during testing. */
	private static Boolean verificationResult;

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.event.verification.TriggeredVerifierListener#verificationFailed
	 * (java.net.URI, net.java.treaty.Contract)
	 */
	public void verificationFailed(URI triggerType, Contract contrac) {

		verificationResult = false;
	}

	/*
	 * (non-Javadoc)
	 * @seenet.java.treaty.event.verification.TriggeredVerifierListener#
	 * verificationSucceeded(java.net.URI, net.java.treaty.Contract)
	 */
	public void verificationSucceeded(URI triggerType, Contract contrac) {

		verificationResult = true;
	}

	/**
	 * <p>
	 * Initializes all test cases of this test class.
	 * </p>
	 */
	@BeforeClass
	public static void setUp() throws Exception {

		/* Start the initialization process of the registry first. */
		EclipseContractRegistry.getInstance();

		consumerBundle1 =
				org.eclipse.core.runtime.Platform.getBundle(CONSUMER_BUNDLE_1_ID);

		providerBundle1 =
				org.eclipse.core.runtime.Platform.getBundle(PROVIDER_BUNDLE_1_ID);

		if (consumerBundle1 == null || providerBundle1 == null) {

			String msg;
			msg = "Cannot find all required Bundles for testing. Testing failed.";

			throw new RuntimeException(msg);
		}
		// no else.

		extensionPoint1 =
				extensionRegistry
						.getExtensionPoint(CONSUMER_BUNDLE_1_EXTENSION_POINT1_ID);

		if (extensionPoint1 == null) {

			String msg;
			msg =
					"Cannot find all required IExtensionPoints for testing. Testing failed.";

			throw new RuntimeException(msg);
		}
		// no else.

		extension1 =
				extensionRegistry.getExtension(PROVIDER_BUNDLE_1_EXTENSION_1_ID);

		if (extension1 == null) {

			String msg;
			msg = "Cannot find all required IExtensions for testing. Testing failed.";

			throw new RuntimeException(msg);
		}
		// no else.

		try {
			eclipseExtensionPoint1 =
					EclipseAdapterFactory.getInstance().createExtensionPoint(
							extensionPoint1);
			eclipseExtension1 =
					EclipseAdapterFactory.getInstance().createExtension(extension1);
		}

		catch (EclipseConnectorAdaptationException e) {
			throw new RuntimeException(
					"Could not adapt all required extensions and extension points."
							+ " Test failed.", e);
		}

		/* Create the TriggerVocabulary. */
		triggerVocabularyMock = new TriggerVocabularyMock();
		EclipseTriggerRegistry.INSTANCE.addTriggerVocabulary(triggerVocabularyMock);
		triggerVocabularyMock.addEventListener(TriggeredEclipseVerifier.INSTANCE);
	}

	/**
	 * <p>
	 * Removes newly added {@link Contract}s from the
	 * {@link EclipseContractRegistry} to avoid side effects.
	 * </p>
	 * 
	 * @throws TreatyException
	 */
	public void tearDown() throws TreatyException {

		/* Remove the contracts from the registry. */
		EclipseContractRegistry.getInstance().updateContract(
				UpdateType.REMOVE_CONTRACT, contract1, eclipseExtensionPoint1,
				Role.CONSUMER);

		/* Remove the contracts from the registry. */
		EclipseContractRegistry.getInstance().updateContract(
				UpdateType.REMOVE_CONTRACT, contract2, eclipseExtensionPoint1,
				Role.CONSUMER);

		triggerVocabularyMock
				.removeEventListener(TriggeredEclipseVerifier.INSTANCE);
		EclipseTriggerRegistry.INSTANCE
				.removeTriggerVocabulary(triggerVocabularyMock);
	}

	/**
	 * <p>
	 * A test case testing the Triggered Verification of the
	 * {@link AbstractTriggeredVerifier}.
	 * </p>
	 * 
	 * <p>
	 * Tests the triggered verification of a {@link Contract} that is triggered by
	 * a trigger that is explicitly defined in the {@link Contract} and results in
	 * a positive verification result.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testVerification01() throws Exception {

		/* Prepare test data. */
		contract1 = createTestContract(TEST_CONTRACT_1);

		/* Add the contract to the registry. */
		EclipseContractRegistry.getInstance().updateContract(
				UpdateType.ADD_CONTRACT, contract1, eclipseExtensionPoint1,
				Role.CONSUMER);
		EclipseContractRegistry.getInstance().updateContract(
				UpdateType.ADD_CONTRACT, contract1, eclipseExtension1, Role.SUPPLIER);

		/* Get the verifier. */
		AbstractTriggeredVerifier triggeredVerifier;
		triggeredVerifier = TriggeredEclipseVerifier.INSTANCE;

		/* Register as listener. */
		triggeredVerifier.addListener(this);

		/* Reset the verification result. */
		verificationResult = null;

		/* Trigger verification. */
		URI triggerType;
		triggerType = new URI(TriggerVocabularyMock.NAME_TRIGGER_1);

		/* Try to get the instantiated contract. */
		int loops;
		loops = 0;

		Set<Contract> contracts;
		contracts = new HashSet<Contract>();

		while (contracts.size() == 0) {

			for (Contract contract : EclipseContractRegistry.getInstance()
					.getContracts(eclipseExtension1, Role.SUPPLIER)) {
				if (contract.getDefinition().equals(contract1)) {
					contracts.add(contract);
				}
				// no else.
			}
			// end for.

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
				fail("Contract for test was not available.");
			}
			// no else.
		}

		triggerVocabularyMock.fireTrigger(triggerType, contracts);

		/* Wait for result. */
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
		assertTrue(verificationResult);

		/* Unregister as listener. */
		triggeredVerifier.removeListener(this);
	}

	/**
	 * <p>
	 * A test case testing the Triggered Verification of the
	 * {@link AbstractTriggeredVerifier}.
	 * </p>
	 * 
	 * <p>
	 * Tests the triggered verification of a {@link Contract} that is triggered by
	 * a trigger that is explicitly defined in the {@link Contract} and results in
	 * a negative verification result.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testVerification02() throws Exception {

		/* Prepare test data. */
		contract2 = createTestContract(TEST_CONTRACT_2);

		/* Add the contract to the registry. */
		EclipseContractRegistry.getInstance().updateContract(
				UpdateType.ADD_CONTRACT, contract2, eclipseExtensionPoint1,
				Role.CONSUMER);
		EclipseContractRegistry.getInstance().updateContract(
				UpdateType.ADD_CONTRACT, contract2, eclipseExtension1, Role.SUPPLIER);

		/* Get the verifier. */
		AbstractTriggeredVerifier triggeredVerifier;
		triggeredVerifier = TriggeredEclipseVerifier.INSTANCE;

		/* Register as listener. */
		triggeredVerifier.addListener(this);

		/* Reset the verification result. */
		verificationResult = null;

		/* Trigger verification. */
		URI triggerType;
		triggerType = new URI(TriggerVocabularyMock.NAME_TRIGGER_1);

		/* Try to get the instantiated contract. */
		int loops;
		loops = 0;

		Set<Contract> contracts;
		contracts = new HashSet<Contract>();

		while (contracts.size() == 0) {

			for (Contract contract : EclipseContractRegistry.getInstance()
					.getContracts(eclipseExtension1, Role.SUPPLIER)) {
				if (contract.getDefinition().equals(contract2)) {
					contracts.add(contract);
				}
				// no else.
			}
			// end for.

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
				fail("Contract for test was not available.");
			}
			// no else.
		}

		triggerVocabularyMock.fireTrigger(triggerType, contracts);

		/* Wait for result. */
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
		assertFalse(verificationResult);

		/* Unregister as listener. */
		triggeredVerifier.removeListener(this);
	}

	/**
	 * <p>
	 * A test case testing the Triggered Verification of the
	 * {@link AbstractTriggeredVerifier}.
	 * </p>
	 * 
	 * <p>
	 * Tests the triggered verification of a {@link Contract} that is triggered by
	 * a default trigger that is not explicitly defined in the {@link Contract}
	 * and results in a positive verification result.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testVerification03() throws Exception {

		/* Prepare test data. */
		contract1 = createTestContract(TEST_CONTRACT_1);

		/* Add the contract to the registry. */
		EclipseContractRegistry.getInstance().updateContract(
				UpdateType.ADD_CONTRACT, contract1, eclipseExtensionPoint1,
				Role.CONSUMER);
		EclipseContractRegistry.getInstance().updateContract(
				UpdateType.ADD_CONTRACT, contract1, eclipseExtension1, Role.SUPPLIER);

		/* Get the verifier. */
		AbstractTriggeredVerifier triggeredVerifier;
		triggeredVerifier = TriggeredEclipseVerifier.INSTANCE;

		/* Register as listener. */
		triggeredVerifier.addListener(this);

		/* Reset the verification result. */
		verificationResult = null;

		/* Trigger verification. */
		URI triggerType;
		triggerType = new URI(TriggerVocabularyMock.NAME_DEFAULT_TRIGGER);

		/* Try to get the instantiated contract. */
		int loops;
		loops = 0;

		Set<Contract> contracts;
		contracts = new HashSet<Contract>();

		while (contracts.size() == 0) {

			for (Contract contract : EclipseContractRegistry.getInstance()
					.getContracts(eclipseExtension1, Role.SUPPLIER)) {
				if (contract.getDefinition().equals(contract1)) {
					contracts.add(contract);
				}
				// no else.
			}
			// end for.

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
				fail("Contract for test was not available.");
			}
			// no else.
		}

		triggerVocabularyMock.fireTrigger(triggerType, contracts);

		/* Wait for result. */
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
		assertTrue(verificationResult);

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
	private static Contract createTestContract(String fileName) throws Exception {

		Contract result;
		result = null;

		URL location;
		location = null;

		/* Try to read the contract. */
		location = Activator.getDefault().getBundle().getResource(fileName);

		ContractReader reader;
		reader = new XMLContractReader();

		result = reader.read(location.openStream(), VocabularyRegistry.INSTANCE);
		result.setLocation(location);

		return result;
	}
}