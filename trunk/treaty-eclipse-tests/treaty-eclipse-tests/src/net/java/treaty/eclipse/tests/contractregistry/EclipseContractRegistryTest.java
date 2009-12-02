/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.tests.contractregistry;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.java.treaty.Role;
import net.java.treaty.eclipse.contractregistry.EclipseAdapterFactory;
import net.java.treaty.eclipse.contractregistry.EclipseConnectorAdaptationException;
import net.java.treaty.eclipse.contractregistry.EclipseContractRegistry;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;

/**
 * <p>
 * This class provides some test cases to cause {@link BundleEvent}s that are
 * captured by the {@link EclipseContractRegistry} and test if the
 * {@link EclipseContractRegistry} handles these events right by updating
 * itself.
 * </p>
 * 
 * <p>
 * <strong>Please note, that the test cases provided here a sensible because
 * they test interacting threads running independently. Probably you have to
 * modify the {@link EclipseContractRegistryTest#pause()} method to get the
 * right results.</strong>
 * </p>
 * 
 * @author Claas Wilke
 */
public class EclipseContractRegistryTest {

	/** The ID of the {@link Bundle} containing the ContractRegistry. */
	private static final String ECLIPSE_TREATY_BUNDLE_ID =
			"net.java.treaty.eclipse";

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
	private static final String LEGISLATOR_BUNDLE_1_ID =
			"net.java.treaty.test.legislator1";

	/** The ID of an {@link IExtension} used during testing. */
	private static final String LEGISLATOR_BUNDLE_1_EXTENSION_1_ID =
			LEGISLATOR_BUNDLE_1_ID + ".xc1";

	/** The ID of a {@link Bundle} used during testing. */
	private static final String PROVIDER_BUNDLE_1_ID =
			"net.java.treaty.test.provider1";

	/** The ID of an {@link IExtension} used during testing. */
	private static final String PROVIDER_BUNDLE_1_EXTENSION_1_ID =
			PROVIDER_BUNDLE_1_ID + ".x1";

	/** The ID of a {@link Bundle} used during testing. */
	private static final String PROVIDER_BUNDLE_2_ID =
			"net.java.treaty.test.provider2";

	/** The ID of an {@link IExtension} used during testing. */
	private static final String PROVIDER_BUNDLE_2_EXTENSION_1_ID =
			PROVIDER_BUNDLE_2_ID + ".x1";

	/** The {@link IExtensionRegistry} used during testing. */
	private static IExtensionRegistry extensionRegistry =
			org.eclipse.core.runtime.Platform.getExtensionRegistry();

	/** The {@link Bundle} containing the {@link EclipseContractRegistry}. */
	private static Bundle eclipseTreatyBundle;

	/** A {@link Bundle} used during testing. */
	private static Bundle consumerBundle1;

	/** A {@link Bundle} used during testing. */
	private static Bundle consumerBundle2;

	/** A {@link Bundle} used during testing. */
	private static Bundle legislatorBundle1;

	/** A {@link Bundle} used during testing. */
	private static Bundle providerBundle1;

	/** A {@link Bundle} used during testing. */
	private static Bundle providerBundle2;

	/** An {@link IExtensionPoint} used during testing. */
	private static IExtensionPoint consumer1extensionPoint1;

	/** An {@link IExtensionPoint} used during testing. */
	private static IExtensionPoint consumer2extensionPoint1;

	/** An {@link IExtension} used during testing. */
	private static IExtension legislator1extension1;

	/** An {@link IExtension} used during testing. */
	private static IExtension provider1extension1;

	/** An {@link IExtension} used during testing. */
	private static IExtension provider2extension1;

	/**
	 * <p>
	 * Initializes all test cases of this test class.
	 * </p>
	 */
	@BeforeClass
	public static void setUp() {

		/* Start the initialization process of the registry first. */
		EclipseContractRegistry.getInstance();

		eclipseTreatyBundle =
				org.eclipse.core.runtime.Platform.getBundle(ECLIPSE_TREATY_BUNDLE_ID);

		consumerBundle1 =
				org.eclipse.core.runtime.Platform.getBundle(CONSUMER_BUNDLE_1_ID);

		consumerBundle2 =
				org.eclipse.core.runtime.Platform.getBundle(CONSUMER_BUNDLE_2_ID);

		legislatorBundle1 =
				org.eclipse.core.runtime.Platform.getBundle(LEGISLATOR_BUNDLE_1_ID);

		providerBundle1 =
				org.eclipse.core.runtime.Platform.getBundle(PROVIDER_BUNDLE_1_ID);

		providerBundle2 =
				org.eclipse.core.runtime.Platform.getBundle(PROVIDER_BUNDLE_2_ID);

		if (eclipseTreatyBundle == null || consumerBundle1 == null
				|| consumerBundle2 == null || legislatorBundle1 == null
				|| providerBundle1 == null || providerBundle2 == null) {

			String msg;
			msg = "Cannot find all required Bundles for testing. Testing failed.";

			throw new RuntimeException(msg);
		}
		// no else.

		consumer1extensionPoint1 =
				extensionRegistry
						.getExtensionPoint(CONSUMER_BUNDLE_1_EXTENSION_POINT1_ID);

		consumer2extensionPoint1 =
				extensionRegistry
						.getExtensionPoint(CONSUMER_BUNDLE_2_EXTENSION_POINT1_ID);

		if (consumer1extensionPoint1 == null || consumer2extensionPoint1 == null) {

			String msg;
			msg =
					"Cannot find all required IExtensionPoints for testing. Testing failed.";

			throw new RuntimeException(msg);
		}
		// no else.

		legislator1extension1 =
				extensionRegistry.getExtension(LEGISLATOR_BUNDLE_1_EXTENSION_1_ID);

		provider1extension1 =
				extensionRegistry.getExtension(PROVIDER_BUNDLE_1_EXTENSION_1_ID);

		provider2extension1 =
				extensionRegistry.getExtension(PROVIDER_BUNDLE_2_EXTENSION_1_ID);

		if (provider1extension1 == null || provider2extension1 == null) {

			String msg;
			msg = "Cannot find all required IExtensions for testing. Testing failed.";

			throw new RuntimeException(msg);
		}
		// no else.

		try {
			eclipseTreatyBundle.start();
		}

		catch (BundleException e) {

			String msg;
			msg = "Could not start the ContractRegistry's Bundle. Testing failed.";

			throw new RuntimeException(msg);
		}
	}

	/**
	 * <p>
	 * Reinitializes the {@link EclipseContractRegistry} after finishing testing.
	 * To avoid side effects in other test classes.
	 * </p>
	 */
	@AfterClass
	public static void tearDown() {

		EclipseContractRegistry.reinitialize();
	}

	/**
	 * <p>
	 * A helper method that stops the execution of this test class for the given
	 * number of seconds. (Internally uses {@link Thread#sleep(long)} but catches
	 * the {@link InterruptedException} as well.
	 * </p>
	 */
	private static void pause() {

		try {
			Thread.sleep(2000);
		}

		catch (InterruptedException e) {
			String msg;

			msg = "Cannot ensure that testcase behaves determinisitc. Test failed.";

			fail(msg);
		}
	}

	/**
	 * <p>
	 * A test case testing the {@link EclipseContractRegistry}.
	 * </p>
	 * 
	 * <p>
	 * Causes the following events.
	 * <ol>
	 * <li>Added the {@link IExtensionPoint} consumer1xp1.</li>
	 * </ol>
	 * </p>
	 * 
	 * @throws BundleException
	 */
	@Test
	public void testAddingConnectors01() throws BundleException,
			EclipseConnectorAdaptationException {

		/* Clear the registry to avoid side effects. */
		EclipseContractRegistry.clear();
		EclipseAdapterFactory.getInstance().clearCache();

		/* Simulate add of consumer1xp1. */
		IExtensionPoint[] extensionPoints;
		extensionPoints = new IExtensionPoint[1];
		extensionPoints[0] = consumer1extensionPoint1;
		EclipseContractRegistry.getInstance().added(extensionPoints);

		pause();
		pause();
		pause();

		/* The Registry should know the contracted bundle. */
		assertTrue(EclipseContractRegistry.getInstance()
				.getContractedEclipsePlugins().contains(
						EclipseAdapterFactory.getInstance().createEclipsePlugin(
								consumerBundle1)));

		/* The Contract of the Extension Point should not be empty. */
		assertFalse(EclipseAdapterFactory.getInstance().createExtensionPoint(
				consumer1extensionPoint1).getContracts().isEmpty());

		/* The Contract of the Extension should not be empty. */
		assertFalse(EclipseAdapterFactory.getInstance().createExtension(
				provider1extension1).getContracts().isEmpty());
	}

	/**
	 * <p>
	 * A test case testing the {@link EclipseContractRegistry}.
	 * </p>
	 * 
	 * <p>
	 * Causes the following events.
	 * <ol>
	 * <li>Added the {@link IExtension} provider1x1.</li>
	 * <li>Added the {@link IExtensionPoint} consumer1xp1.</li>
	 * </ol>
	 * </p>
	 * 
	 * @throws BundleException
	 */
	@Test
	public void testAddingConnectors02() throws BundleException,
			EclipseConnectorAdaptationException {

		/* Clear the registry to avoid side effects. */
		EclipseContractRegistry.clear();
		EclipseAdapterFactory.getInstance().clearCache();

		/* Simulate add of provider1x1. */
		IExtension[] extensions;
		extensions = new IExtension[1];
		extensions[0] = provider1extension1;
		EclipseContractRegistry.getInstance().added(extensions);

		pause();

		/* The Registry should not know the contracted bundle. */
		assertFalse(EclipseContractRegistry.getInstance()
				.getContractedEclipsePlugins().contains(
						EclipseAdapterFactory.getInstance().createEclipsePlugin(
								consumerBundle1)));

		/* The Contract of the Extension Point should be empty. */
		assertTrue(EclipseAdapterFactory.getInstance().createExtensionPoint(
				consumer1extensionPoint1).getContracts().isEmpty());

		/* The Contract of the Extension should be empty. */
		assertTrue(EclipseAdapterFactory.getInstance().createExtension(
				provider1extension1).getContracts().isEmpty());

		/* Simulate add of consumer1xp1. */
		IExtensionPoint[] extensionPoints;
		extensionPoints = new IExtensionPoint[1];
		extensionPoints[0] = consumer1extensionPoint1;
		EclipseContractRegistry.getInstance().added(extensionPoints);

		pause();

		/* The Registry should know the contracted bundle. */
		assertTrue(EclipseContractRegistry.getInstance()
				.getContractedEclipsePlugins().contains(
						EclipseAdapterFactory.getInstance().createEclipsePlugin(
								consumerBundle1)));

		/* The Contract of the Extension Point should not be empty. */
		assertFalse(EclipseAdapterFactory.getInstance().createExtensionPoint(
				consumer1extensionPoint1).getContracts().isEmpty());

		/* The Contract of the Extension should not be empty. */
		assertFalse(EclipseAdapterFactory.getInstance().createExtension(
				provider1extension1).getContracts().isEmpty());
	}

	/**
	 * <p>
	 * A test case testing the {@link EclipseContractRegistry}.
	 * </p>
	 * 
	 * <p>
	 * Causes the following events.
	 * <ol>
	 * <li>Added the {@link IExtensionPoint} consumer2xp1.</li>
	 * <li>Added the {@link IExtension} legislator1xc1.</li>
	 * </ol>
	 * </p>
	 * 
	 * @throws BundleException
	 */
	@Test
	public void testAddingConnectors03() throws BundleException,
			EclipseConnectorAdaptationException {

		/* Clear the registry to avoid side effects. */
		EclipseContractRegistry.clear();
		EclipseAdapterFactory.getInstance().clearCache();

		/* Simulate add of consumer2x1. */
		IExtensionPoint[] extensionPoints;
		extensionPoints = new IExtensionPoint[1];
		extensionPoints[0] = consumer2extensionPoint1;
		EclipseContractRegistry.getInstance().added(extensionPoints);

		pause();

		/* The Registry should not know the contracted bundle. */
		assertFalse(EclipseContractRegistry.getInstance()
				.getContractedEclipsePlugins().contains(
						EclipseAdapterFactory.getInstance().createEclipsePlugin(
								consumerBundle2)));

		/* The Contract of the Extension Point should be empty. */
		assertTrue(EclipseAdapterFactory.getInstance().createExtensionPoint(
				consumer2extensionPoint1).getContracts().isEmpty());

		/* The Registry should not know the external contract. */
		assertFalse(EclipseContractRegistry.getInstance().playsConnectorRole(
				EclipseAdapterFactory.getInstance().createExtension(
						legislator1extension1), Role.LEGISLATOR));

		/* The registry should not know an unbound legislator contract. */
		assertFalse(EclipseContractRegistry.getInstance()
				.hasUnboundLegislatorContracts(
						consumer2extensionPoint1.getUniqueIdentifier()));

		/* The Contract of the Extension should be empty. */
		assertTrue(EclipseAdapterFactory.getInstance().createExtension(
				provider2extension1).getContracts().isEmpty());

		/* Simulate add of legislator1xc1. */
		IExtension[] extensions;
		extensions = new IExtension[1];
		extensions[0] = legislator1extension1;
		EclipseContractRegistry.getInstance().added(extensions);

		pause();

		/* The Registry should know the contracted bundle. */
		assertTrue(EclipseContractRegistry.getInstance()
				.getContractedEclipsePlugins().contains(
						EclipseAdapterFactory.getInstance().createEclipsePlugin(
								consumerBundle2)));

		/* The Contract of the Extension Point should not be empty. */
		assertFalse(EclipseAdapterFactory.getInstance().createExtensionPoint(
				consumer2extensionPoint1).getContracts().isEmpty());

		/* The Registry should know the external contract. */
		assertTrue(EclipseContractRegistry.getInstance().playsConnectorRole(
				EclipseAdapterFactory.getInstance().createExtension(
						legislator1extension1), Role.LEGISLATOR));

		/* The registry should not know an unbound legislator contract. */
		assertFalse(EclipseContractRegistry.getInstance()
				.hasUnboundLegislatorContracts(
						consumer2extensionPoint1.getUniqueIdentifier()));

		/* The Contract of the Extension should not be empty. */
		assertFalse(EclipseAdapterFactory.getInstance().createExtension(
				provider2extension1).getContracts().isEmpty());
	}

	/**
	 * <p>
	 * A test case testing the {@link EclipseContractRegistry}.
	 * </p>
	 * 
	 * <p>
	 * Causes the following events.
	 * <ol>
	 * <li>Removed the {@link IExtension} provider1x1.</li>
	 * <li>Removed the {@link IExtensionPoint} consumer1xp1.</li>
	 * </ol>
	 * </p>
	 * 
	 * @throws BundleException
	 */
	@Test
	public void testRemovingConnectors01() throws BundleException,
			EclipseConnectorAdaptationException {

		this.addAllConnectors();

		/* Simulate removal of provider1x1. */
		IExtension[] extensions;
		extensions = new IExtension[1];
		extensions[0] = provider1extension1;
		EclipseContractRegistry.getInstance().removed(extensions);

		pause();

		/* The Registry should know the contracted bundle. */
		assertTrue(EclipseContractRegistry.getInstance()
				.getContractedEclipsePlugins().contains(
						EclipseAdapterFactory.getInstance().createEclipsePlugin(
								consumerBundle1)));

		/* The Contract of the Extension Point should not be empty. */
		assertFalse(EclipseAdapterFactory.getInstance().createExtensionPoint(
				consumer1extensionPoint1).getContracts().isEmpty());

		/* The Contract of the Extension should be empty. */
		assertTrue(EclipseAdapterFactory.getInstance().createExtension(
				provider1extension1).getContracts().isEmpty());

		/* Simulate removal of consumer1xp1. */
		IExtensionPoint[] extensionPoints;
		extensionPoints = new IExtensionPoint[1];
		extensionPoints[0] = consumer1extensionPoint1;
		EclipseContractRegistry.getInstance().removed(extensionPoints);

		pause();

		/* The Registry should not know the contracted bundle. */
		assertFalse(EclipseContractRegistry.getInstance()
				.getContractedEclipsePlugins().contains(
						EclipseAdapterFactory.getInstance().createEclipsePlugin(
								consumerBundle1)));

		/* The Contract of the Extension Point should be empty. */
		assertTrue(EclipseAdapterFactory.getInstance().createExtensionPoint(
				consumer1extensionPoint1).getContracts().isEmpty());
	}

	/**
	 * <p>
	 * A test case testing the {@link EclipseContractRegistry}.
	 * </p>
	 * 
	 * <p>
	 * Causes the following events.
	 * <ol>
	 * <li>Removed the {@link IExtensionPoint} consumer1xp1.</li>
	 * </ol>
	 * </p>
	 * 
	 * @throws BundleException
	 */
	@Test
	public void testRemovingConnectors02() throws BundleException,
			EclipseConnectorAdaptationException {

		this.addAllConnectors();

		/* Simulate removal of consumer1xp1. */
		IExtensionPoint[] extensionPoints;
		extensionPoints = new IExtensionPoint[1];
		extensionPoints[0] = consumer1extensionPoint1;
		EclipseContractRegistry.getInstance().removed(extensionPoints);

		pause();

		/* The Registry should not know the contracted bundle. */
		assertFalse(EclipseContractRegistry.getInstance()
				.getContractedEclipsePlugins().contains(
						EclipseAdapterFactory.getInstance().createEclipsePlugin(
								consumerBundle1)));

		/* The Contract of the Extension Point should be empty. */
		assertTrue(EclipseAdapterFactory.getInstance().createExtensionPoint(
				consumer1extensionPoint1).getContracts().isEmpty());

		/* The Contract of the Extension should be empty. */
		assertTrue(EclipseAdapterFactory.getInstance().createExtension(
				provider1extension1).getContracts().isEmpty());
	}

	/**
	 * <p>
	 * A test case testing the {@link EclipseContractRegistry}.
	 * </p>
	 * 
	 * <p>
	 * Causes the following events.
	 * <ol>
	 * <li>Removed the {@link IExtension} provider2x1.</li>
	 * <li>Removed the {@link IExtensionPoint} consumer2xp1.</li>
	 * <li>Removed the {@link IExtension} legislator1xc1.</li>
	 * </ol>
	 * </p>
	 * 
	 * @throws BundleException
	 */
	@Test
	public void testRemovingConnectors03() throws BundleException,
			EclipseConnectorAdaptationException {

		this.addAllConnectors();

		/* Simulate removal of provider2x1. */
		IExtension[] extensions;
		extensions = new IExtension[1];
		extensions[0] = provider2extension1;
		EclipseContractRegistry.getInstance().removed(extensions);

		pause();

		/* The Registry should know the contracted bundle. */
		assertTrue(EclipseContractRegistry.getInstance()
				.getContractedEclipsePlugins().contains(
						EclipseAdapterFactory.getInstance().createEclipsePlugin(
								consumerBundle2)));

		/* The Contract of the Extension Point should not be empty. */
		assertFalse(EclipseAdapterFactory.getInstance().createExtensionPoint(
				consumer2extensionPoint1).getContracts().isEmpty());

		/* The Registry should know the external contract. */
		assertTrue(EclipseContractRegistry.getInstance().playsConnectorRole(
				EclipseAdapterFactory.getInstance().createExtension(
						legislator1extension1), Role.LEGISLATOR));

		/* The registry should not know an unbound legislator contract. */
		assertFalse(EclipseContractRegistry.getInstance()
				.hasUnboundLegislatorContracts(
						consumer2extensionPoint1.getUniqueIdentifier()));

		/* The Contract of the Extension should be empty. */
		assertTrue(EclipseAdapterFactory.getInstance().createExtension(
				provider2extension1).getContracts().isEmpty());

		/* Simulate removal of consumer2x1. */
		IExtensionPoint[] extensionPoints;
		extensionPoints = new IExtensionPoint[1];
		extensionPoints[0] = consumer2extensionPoint1;
		EclipseContractRegistry.getInstance().removed(extensionPoints);

		pause();

		/* The Registry should not know the contracted bundle. */
		assertFalse(EclipseContractRegistry.getInstance()
				.getContractedEclipsePlugins().contains(
						EclipseAdapterFactory.getInstance().createEclipsePlugin(
								consumerBundle2)));

		/* The Contract of the Extension Point should be empty. */
		assertTrue(EclipseAdapterFactory.getInstance().createExtensionPoint(
				consumer2extensionPoint1).getContracts().isEmpty());

		/* The Registry should know the external contract. */
		assertTrue(EclipseContractRegistry.getInstance().playsConnectorRole(
				EclipseAdapterFactory.getInstance().createExtension(
						legislator1extension1), Role.LEGISLATOR));

		/* The registry should know an unbound legislator contract. */
		assertTrue(EclipseContractRegistry.getInstance()
				.hasUnboundLegislatorContracts(
						consumer2extensionPoint1.getUniqueIdentifier()));

		/* Simulate removal of legislator1xc1. */
		extensions[0] = legislator1extension1;
		EclipseContractRegistry.getInstance().removed(extensions);

		pause();

		/* The Registry should not know the external contract. */
		assertFalse(EclipseContractRegistry.getInstance().playsConnectorRole(
				EclipseAdapterFactory.getInstance().createExtension(
						legislator1extension1), Role.LEGISLATOR));

		/* The registry should not know an unbound legislator contract. */
		assertFalse(EclipseContractRegistry.getInstance()
				.hasUnboundLegislatorContracts(
						consumer2extensionPoint1.getUniqueIdentifier()));
	}

	/**
	 * <p>
	 * A test case testing the {@link EclipseContractRegistry}.
	 * </p>
	 * 
	 * <p>
	 * Causes the following events.
	 * <ol>
	 * <li>Removed the {@link IExtension} legislator1xc1.</li>
	 * </ol>
	 * </p>
	 * 
	 * @throws BundleException
	 */
	@Test
	public void testRemovingConnectors04() throws BundleException,
			EclipseConnectorAdaptationException {

		this.addAllConnectors();

		/* Simulate removal of legislator1x1. */
		IExtension[] extensions;
		extensions = new IExtension[1];
		extensions[0] = legislator1extension1;
		EclipseContractRegistry.getInstance().removed(extensions);

		pause();

		/* The Registry should not know the contracted bundle. */
		assertFalse(EclipseContractRegistry.getInstance()
				.getContractedEclipsePlugins().contains(
						EclipseAdapterFactory.getInstance().createEclipsePlugin(
								consumerBundle2)));

		/* The Contract of the Extension Point should be empty. */
		assertTrue(EclipseAdapterFactory.getInstance().createExtensionPoint(
				consumer2extensionPoint1).getContracts().isEmpty());

		/* The Registry should not know the external contract. */
		assertFalse(EclipseContractRegistry.getInstance().playsConnectorRole(
				EclipseAdapterFactory.getInstance().createExtension(
						legislator1extension1), Role.LEGISLATOR));

		/* The registry should not know an unbound legislator contract. */
		assertFalse(EclipseContractRegistry.getInstance()
				.hasUnboundLegislatorContracts(
						consumer2extensionPoint1.getUniqueIdentifier()));

		/* The Contract of the Extension should be empty. */
		assertTrue(EclipseAdapterFactory.getInstance().createExtension(
				provider2extension1).getContracts().isEmpty());
	}

	/**
	 * <p>
	 * A helper method that sends the registry the information that all tested
	 * extensions and extension points have been added.
	 * </p>
	 * 
	 * @throws BundleException
	 */
	private void addAllConnectors() throws BundleException {

		/* Clear the registry to avoid side effects. */
		EclipseContractRegistry.clear();
		EclipseAdapterFactory.getInstance().clearCache();

		/* Simulate removal of legislator1xc1. */
		IExtension[] extensions;
		extensions = new IExtension[1];
		extensions[0] = legislator1extension1;
		EclipseContractRegistry.getInstance().added(extensions);

		pause();

		/* Simulate removal of consumer1xp1 and consumer2xp1. */
		IExtensionPoint[] extensionPoints;
		extensionPoints = new IExtensionPoint[2];
		extensionPoints = new IExtensionPoint[2];
		extensionPoints[0] = consumer1extensionPoint1;
		extensionPoints[1] = consumer2extensionPoint1;
		EclipseContractRegistry.getInstance().added(extensionPoints);

		pause();

		/* Simulate removal of provider1x1 and provider2x1. */
		extensions = new IExtension[2];
		extensions[0] = provider1extension1;
		extensions[1] = provider2extension1;
		EclipseContractRegistry.getInstance().added(extensionPoints);

		pause();
	}
}