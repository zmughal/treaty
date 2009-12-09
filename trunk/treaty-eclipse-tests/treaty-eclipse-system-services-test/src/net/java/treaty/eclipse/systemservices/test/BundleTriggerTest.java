/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.systemservices.test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import java.util.Collections;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.Role;
import net.java.treaty.TreatyException;
import net.java.treaty.contractregistry.ContractRegistry.UpdateType;
import net.java.treaty.eclipse.EclipseExtensionPoint;
import net.java.treaty.eclipse.contractregistry.EclipseAdapterFactory;
import net.java.treaty.eclipse.contractregistry.EclipseConnectorAdaptationException;
import net.java.treaty.eclipse.contractregistry.EclipseContractRegistry;
import net.java.treaty.eclipse.systemservices.BundleTriggerVocabulary;
import net.java.treaty.trigger.EventListener;
import net.java.treaty.trigger.verification.AbstractTriggeredVerifier;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;

/**
 * <p>
 * Test cases to test the class {@link AbstractTriggeredVerifier}.
 * </p>
 * 
 * @author Claas Wilke
 */
public class BundleTriggerTest {

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

	/** The maximum loops to wait for a verification result. */
	private static final int WAIT_TOTAL_LOOPS = 40;

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

	/** A {@link Contract} used during testing. */
	private static Contract contract1;

	/** A {@link Contract} used during testing. */
	private static Contract contract2;

	/** Indicates whether or not an expected event has been fired. */
	private Boolean wasRightEventFired;

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
		}

		catch (EclipseConnectorAdaptationException e) {
			throw new RuntimeException(
					"Could not adapt all required extensions and extension points."
							+ " Test failed.", e);
		}
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
	}

	/**
	 * <p>
	 * Tests that a {@link BundleEvent} causes the right trigger for verification
	 * if the trigger is defined in a contract of the {@link Bundle}.
	 * </p>
	 */
	@Test
	public void testBundleEvent01() {

		/* Wait for the ContractRegistry getting ready. */
		int loops;
		loops = 0;

		Set<Contract> contracts;
		contracts = Collections.emptySet();

		while (contracts.size() == 0) {

			contracts =
					EclipseContractRegistry.getInstance().getContracts(
							eclipseExtensionPoint1, Role.CONSUMER);

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
				fail("Required Contract for test not available.");
			}
			// no else.
		}
		// end while.

		BundleTriggerVocabulary bundleTriggerVocabulary;
		bundleTriggerVocabulary = new BundleTriggerVocabulary();

		this.wasRightEventFired = null;

		/* Register a listener. */
		bundleTriggerVocabulary.addEventListener(new EventListener() {

			/*
			 * (non-Javadoc)
			 * @see net.java.treaty.trigger.EventListener#update(java.net.URI,
			 * java.util.Set)
			 */
			public void update(URI triggerType, Set<Contract> contracts) {

				wasRightEventFired =
						BundleTriggerVocabulary.TRIGGER_TYPE_BUNDLE_STARTED
								.equals(triggerType.toString());
			}
		});

		/* Cause Event. */
		BundleEvent bundleEvent;
		bundleEvent = new BundleEvent(BundleEvent.STARTED, consumerBundle1);

		bundleTriggerVocabulary.bundleChanged(bundleEvent);

		/* Wait for result. */
		loops = 0;

		while (wasRightEventFired == null) {

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
				fail("Triggered event was not available.");
			}
			// no else.
		}
		// end while.

		assertTrue(wasRightEventFired);
	}

	/**
	 * <p>
	 * Tests that a {@link BundleEvent} causes the no trigger for verification if
	 * the {@link BundleEvent}'s trigger is not defined in a contract of the
	 * {@link Bundle}.
	 * </p>
	 */
	@Test
	public void testBundleEvent02() {

		/* Wait for the ContractRegistry getting ready. */
		int loops;
		loops = 0;

		Set<Contract> contracts;
		contracts = Collections.emptySet();

		while (contracts.size() == 0) {

			contracts =
					EclipseContractRegistry.getInstance().getContracts(
							eclipseExtensionPoint1, Role.CONSUMER);

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
				fail("Required Contract for test not available.");
			}
			// no else.
		}
		// end while.

		BundleTriggerVocabulary bundleTriggerVocabulary;
		bundleTriggerVocabulary = new BundleTriggerVocabulary();

		this.wasRightEventFired = null;

		/* Register a listener. */
		bundleTriggerVocabulary.addEventListener(new EventListener() {

			/*
			 * (non-Javadoc)
			 * @see net.java.treaty.trigger.EventListener#update(java.net.URI,
			 * java.util.Set)
			 */
			public void update(URI triggerType, Set<Contract> contracts) {

				wasRightEventFired =
						BundleTriggerVocabulary.TRIGGER_TYPE_BUNDLE_STARTED
								.equals(triggerType.toString());
			}
		});

		/* Cause Event. */
		BundleEvent bundleEvent;
		bundleEvent = new BundleEvent(BundleEvent.UNRESOLVED, consumerBundle1);

		bundleTriggerVocabulary.bundleChanged(bundleEvent);

		/* Wait for result. */
		loops = 0;

		while (wasRightEventFired == null) {

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
		// end while.

		assertNull(wasRightEventFired);
	}
}