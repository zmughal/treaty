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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.Role;
import net.java.treaty.TreatyException;
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
public class BundleTriggerVocabularyTest {

	/** The ID of a {@link Bundle} used during testing. */
	private static final String CONSUMER_BUNDLE_1_ID =
			"net.java.treaty.test.consumer1";

	/** The ID of an {@link IExtensionPoint} used during testing. */
	private static final String CONSUMER_BUNDLE_1_EXTENSION_POINT1_ID =
			CONSUMER_BUNDLE_1_ID + ".xp1";

	/** The ID of a {@link Bundle} used during testing. */
	private static final String CONSUMER_BUNDLE_2_ID =
			"net.java.treaty.test.consumer2";

	/** The ID of an {@link IExtensionPoint} used during testing. */
	private static final String CONSUMER_BUNDLE_2_EXTENSION_POINT1_ID =
			CONSUMER_BUNDLE_2_ID + ".xp1";

	/** The ID of a {@link Bundle} used during testing. */
	private static final String PROVIDER_BUNDLE_1_ID =
			"net.java.treaty.test.provider1";

	/** The ID of an {@link IExtension} used during testing. */
	private static final String PROVIDER_BUNDLE_1_EXTENSION_1_ID =
			PROVIDER_BUNDLE_1_ID + ".x1";

	/** The ID of a {@link Bundle} used during testing. */
	private static final String PROVIDER_BUNDLE_2_ID =
			"net.java.treaty.test.provider1";

	/** The ID of an {@link IExtension} used during testing. */
	private static final String PROVIDER_BUNDLE_2_EXTENSION_1_ID =
			PROVIDER_BUNDLE_1_ID + ".x1";

	/** The maximum loops to wait for a verification result. */
	private static final int WAIT_TOTAL_LOOPS = 80;

	/** The time to wait in a loop. */
	private static final long WAIT_PAUSE_LENGTH = 50;

	/** A {@link Bundle} used during testing. */
	private static Bundle consumerBundle1;

	/** A {@link Bundle} used during testing. */
	private static Bundle consumerBundle2;

	/** The {@link IExtensionRegistry} used during testing. */
	private static IExtensionRegistry extensionRegistry =
			org.eclipse.core.runtime.Platform.getExtensionRegistry();

	/** A {@link Bundle} used during testing. */
	private static Bundle providerBundle1;

	/** A {@link Bundle} used during testing. */
	private static Bundle providerBundle2;

	/** An {@link IExtensionPoint} used during testing. */
	private static IExtensionPoint extensionPoint1;

	/** An {@link IExtensionPoint} used during testing. */
	private static IExtensionPoint extensionPoint2;

	/** An {@link EclipseExtensionPoint} used during testing. */
	private static EclipseExtensionPoint eclipseExtensionPoint1;

	/** An {@link EclipseExtensionPoint} used during testing. */
	private static EclipseExtensionPoint eclipseExtensionPoint2;

	/** An {@link IExtension} used during testing. */
	private static IExtension extension1;

	/** An {@link IExtension} used during testing. */
	private static IExtension extension2;

	/** Contains the triggers that have been fired. */
	private List<URI> eventsFired = new ArrayList<URI>();

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

		consumerBundle2 =
				org.eclipse.core.runtime.Platform.getBundle(CONSUMER_BUNDLE_2_ID);

		providerBundle1 =
				org.eclipse.core.runtime.Platform.getBundle(PROVIDER_BUNDLE_1_ID);

		providerBundle2 =
				org.eclipse.core.runtime.Platform.getBundle(PROVIDER_BUNDLE_2_ID);

		if (consumerBundle1 == null || providerBundle1 == null
				|| consumerBundle2 == null || providerBundle2 == null) {

			String msg;
			msg = "Cannot find all required Bundles for testing. Testing failed.";

			throw new RuntimeException(msg);
		}
		// no else.

		extensionPoint1 =
				extensionRegistry
						.getExtensionPoint(CONSUMER_BUNDLE_1_EXTENSION_POINT1_ID);

		extensionPoint2 =
				extensionRegistry
						.getExtensionPoint(CONSUMER_BUNDLE_2_EXTENSION_POINT1_ID);

		if (extensionPoint1 == null || extensionPoint2 == null) {

			String msg;
			msg =
					"Cannot find all required IExtensionPoints for testing. Testing failed.";

			throw new RuntimeException(msg);
		}
		// no else.

		extension1 =
				extensionRegistry.getExtension(PROVIDER_BUNDLE_1_EXTENSION_1_ID);

		extension2 =
				extensionRegistry.getExtension(PROVIDER_BUNDLE_2_EXTENSION_1_ID);

		if (extension1 == null || extension2 == null) {

			String msg;
			msg = "Cannot find all required IExtensions for testing. Testing failed.";

			throw new RuntimeException(msg);
		}
		// no else.

		try {
			eclipseExtensionPoint1 =
					EclipseAdapterFactory.getInstance().createExtensionPoint(
							extensionPoint1);

			eclipseExtensionPoint2 =
					EclipseAdapterFactory.getInstance().createExtensionPoint(
							extensionPoint2);
		}

		catch (EclipseConnectorAdaptationException e) {
			throw new RuntimeException(
					"Could not adapt all required extensions and extension points."
							+ " Test failed.", e);
		}
	}

	/**
	 * <p>
	 * Tests the method {@link BundleTriggerVocabulary#getSubTriggers(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 */
	@Test
	public void testSubTriggers01() throws TreatyException, URISyntaxException {

		BundleTriggerVocabulary bundleTriggerVocabulary;
		bundleTriggerVocabulary = new BundleTriggerVocabulary();

		Set<URI> subTriggers;
		subTriggers =
				bundleTriggerVocabulary.getSubTriggers(new URI(
						BundleTriggerVocabulary.TRIGGER_TYPE_BUNDLE_EVENT));

		assertNotNull(subTriggers);
		assertEquals(32, subTriggers.size());

		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_BUNDLE_INSTALLED)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_BUNDLE_LAZY_ACTIVATION)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_BUNDLE_RESOLVED)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_BUNDLE_STARTED)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_BUNDLE_STARTING)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_BUNDLE_STOPPED)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_BUNDLE_STOPPING)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_BUNDLE_UNINSTALLED)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_BUNDLE_UNRESOLVED)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_BUNDLE_UPDATED)));

		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_CONSUMER_BUNDLE_EVENT)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_CONSUMER_BUNDLE_INSTALLED)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_CONSUMER_BUNDLE_LAZY_ACTIVATION)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_CONSUMER_BUNDLE_RESOLVED)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_CONSUMER_BUNDLE_STARTED)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_CONSUMER_BUNDLE_STARTING)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_CONSUMER_BUNDLE_STOPPED)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_CONSUMER_BUNDLE_STOPPING)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_CONSUMER_BUNDLE_UNINSTALLED)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_CONSUMER_BUNDLE_UNRESOLVED)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_CONSUMER_BUNDLE_UPDATED)));

		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_SUPPLIER_BUNDLE_EVENT)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_SUPPLIER_BUNDLE_INSTALLED)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_SUPPLIER_BUNDLE_LAZY_ACTIVATION)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_SUPPLIER_BUNDLE_RESOLVED)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_SUPPLIER_BUNDLE_STARTED)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_SUPPLIER_BUNDLE_STARTING)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_SUPPLIER_BUNDLE_STOPPED)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_SUPPLIER_BUNDLE_STOPPING)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_SUPPLIER_BUNDLE_UNINSTALLED)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_SUPPLIER_BUNDLE_UNRESOLVED)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_SUPPLIER_BUNDLE_UPDATED)));
	}

	/**
	 * <p>
	 * Tests the method {@link BundleTriggerVocabulary#getSubTriggers(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 */
	@Test
	public void testSubTriggers02() throws TreatyException, URISyntaxException {

		BundleTriggerVocabulary bundleTriggerVocabulary;
		bundleTriggerVocabulary = new BundleTriggerVocabulary();

		Set<URI> subTriggers;
		subTriggers =
				bundleTriggerVocabulary.getSubTriggers(new URI(
						BundleTriggerVocabulary.TRIGGER_TYPE_BUNDLE_INSTALLED));

		assertNotNull(subTriggers);
		assertEquals(2, subTriggers.size());

		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_CONSUMER_BUNDLE_INSTALLED)));
		assertTrue(subTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_SUPPLIER_BUNDLE_INSTALLED)));
	}

	/**
	 * <p>
	 * Tests the method {@link BundleTriggerVocabulary#getSubTriggers(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 */
	@Test
	public void testSubTriggers03() throws TreatyException, URISyntaxException {

		BundleTriggerVocabulary bundleTriggerVocabulary;
		bundleTriggerVocabulary = new BundleTriggerVocabulary();

		Set<URI> subTriggers;
		subTriggers =
				bundleTriggerVocabulary.getSubTriggers(new URI(
						BundleTriggerVocabulary.TRIGGER_TYPE_CONSUMER_BUNDLE_INSTALLED));

		assertNotNull(subTriggers);
		assertEquals(0, subTriggers.size());
	}

	/**
	 * <p>
	 * Tests the method {@link BundleTriggerVocabulary#getSubTriggers(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 */
	@Test
	public void testSuperTriggers01() throws TreatyException, URISyntaxException {

		BundleTriggerVocabulary bundleTriggerVocabulary;
		bundleTriggerVocabulary = new BundleTriggerVocabulary();

		Set<URI> superTriggers;
		superTriggers =
				bundleTriggerVocabulary.getSuperTriggers(new URI(
						BundleTriggerVocabulary.TRIGGER_TYPE_BUNDLE_INSTALLED));

		assertNotNull(superTriggers);
		assertEquals(1, superTriggers.size());

		assertTrue(superTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_BUNDLE_EVENT)));
	}

	/**
	 * <p>
	 * Tests the method {@link BundleTriggerVocabulary#getSubTriggers(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 */
	@Test
	public void testSuperTriggers02() throws TreatyException, URISyntaxException {

		BundleTriggerVocabulary bundleTriggerVocabulary;
		bundleTriggerVocabulary = new BundleTriggerVocabulary();

		Set<URI> superTriggers;
		superTriggers =
				bundleTriggerVocabulary.getSuperTriggers(new URI(
						BundleTriggerVocabulary.TRIGGER_TYPE_BUNDLE_EVENT));

		assertNotNull(superTriggers);
		assertEquals(0, superTriggers.size());
	}

	/**
	 * <p>
	 * Tests the method {@link BundleTriggerVocabulary#getSubTriggers(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 */
	@Test
	public void testSuperTriggers03() throws TreatyException, URISyntaxException {

		BundleTriggerVocabulary bundleTriggerVocabulary;
		bundleTriggerVocabulary = new BundleTriggerVocabulary();

		Set<URI> superTriggers;
		superTriggers =
				bundleTriggerVocabulary.getSuperTriggers(new URI(
						BundleTriggerVocabulary.TRIGGER_TYPE_CONSUMER_BUNDLE_INSTALLED));

		assertNotNull(superTriggers);
		assertEquals(3, superTriggers.size());

		assertTrue(superTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_BUNDLE_EVENT)));
		assertTrue(superTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_BUNDLE_INSTALLED)));
		assertTrue(superTriggers.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_CONSUMER_BUNDLE_EVENT)));
	}

	/**
	 * <p>
	 * Tests that a {@link BundleEvent} causes the right trigger for verification
	 * if the trigger is defined in a contract of the {@link Bundle}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 */
	@Test
	public void testBundleEvent01() throws URISyntaxException {

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

		this.eventsFired.clear();

		/* Register a listener. */
		bundleTriggerVocabulary.addEventListener(new EventListener() {

			/*
			 * (non-Javadoc)
			 * @see net.java.treaty.trigger.EventListener#update(java.net.URI,
			 * java.util.Set)
			 */
			public void update(URI triggerType, Set<Contract> contracts) {

				eventsFired.add(triggerType);
			}
		});

		/* Cause Event. */
		BundleEvent bundleEvent;
		bundleEvent = new BundleEvent(BundleEvent.STARTED, consumerBundle1);

		bundleTriggerVocabulary.bundleChanged(bundleEvent);

		/* Wait for result. */
		for (int index = 0; index <= WAIT_TOTAL_LOOPS; index++) {

			/* Wait some time to improve CPU performance. */
			try {
				Thread.sleep(WAIT_PAUSE_LENGTH);
			}

			catch (InterruptedException e) {
				fail("Cannot ensure that testcase behaves determinisitc. Test failed.");
			}
			// end catch.
		}
		// end while.

		assertFalse(eventsFired.isEmpty());
		assertEquals(1, eventsFired.size());

		assertTrue(eventsFired.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_BUNDLE_STARTED)));
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

		this.eventsFired.clear();

		/* Register a listener. */
		bundleTriggerVocabulary.addEventListener(new EventListener() {

			/*
			 * (non-Javadoc)
			 * @see net.java.treaty.trigger.EventListener#update(java.net.URI,
			 * java.util.Set)
			 */
			public void update(URI triggerType, Set<Contract> contracts) {

				eventsFired.add(triggerType);
			}
		});

		/* Cause Event. */
		BundleEvent bundleEvent;
		bundleEvent = new BundleEvent(BundleEvent.UNRESOLVED, consumerBundle1);

		bundleTriggerVocabulary.bundleChanged(bundleEvent);

		/* Wait for result. */
		for (int index = 0; index < WAIT_TOTAL_LOOPS; index++) {

			/* Wait some time to improve CPU performance. */
			try {
				Thread.sleep(WAIT_PAUSE_LENGTH);
			}

			catch (InterruptedException e) {
				fail("Cannot ensure that testcase behaves determinisitc. Test failed.");
			}
			// end catch.
		}
		// end while.

		assertTrue(eventsFired.isEmpty());
	}

	/**
	 * <p>
	 * Tests that a {@link BundleEvent} causes the right trigger for verification
	 * if the trigger not defined but a super trigger of the trigger is defined in
	 * a contract of the {@link Bundle}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 */
	@Test
	public void testBundleEvent03() throws URISyntaxException {

		/* Wait for the ContractRegistry getting ready. */
		int loops;
		loops = 0;

		Set<Contract> contracts;
		contracts = Collections.emptySet();

		while (contracts.size() == 0) {

			contracts =
					EclipseContractRegistry.getInstance().getContracts(
							eclipseExtensionPoint2, Role.CONSUMER);

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

		this.eventsFired.clear();

		/* Register a listener. */
		bundleTriggerVocabulary.addEventListener(new EventListener() {

			/*
			 * (non-Javadoc)
			 * @see net.java.treaty.trigger.EventListener#update(java.net.URI,
			 * java.util.Set)
			 */
			public void update(URI triggerType, Set<Contract> contracts) {

				eventsFired.add(triggerType);
			}
		});

		/* Cause Event. */
		BundleEvent bundleEvent;
		bundleEvent = new BundleEvent(BundleEvent.STARTED, consumerBundle1);

		bundleTriggerVocabulary.bundleChanged(bundleEvent);

		/* Wait for result. */
		for (int index = 0; index < WAIT_TOTAL_LOOPS; index++) {

			/* Wait some time to improve CPU performance. */
			try {
				Thread.sleep(WAIT_PAUSE_LENGTH);
			}

			catch (InterruptedException e) {
				fail("Cannot ensure that testcase behaves determinisitc. Test failed.");
			}
			// end catch.
		}
		// end while.

		assertFalse(eventsFired.isEmpty());
		assertEquals(1, eventsFired.size());

		assertTrue(eventsFired.contains(new URI(
				BundleTriggerVocabulary.TRIGGER_TYPE_BUNDLE_STARTED)));
	}
}