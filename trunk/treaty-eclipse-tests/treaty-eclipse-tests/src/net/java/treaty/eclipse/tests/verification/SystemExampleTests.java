/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.tests.verification;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.java.treaty.Contract;
import net.java.treaty.Role;
import net.java.treaty.VerificationReport;
import net.java.treaty.Verifier;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.contractregistry.EclipseAdapterFactory;
import net.java.treaty.eclipse.contractregistry.EclipseContractRegistry;
import net.java.treaty.eclipse.verification.EclipseVerificationReport;
import net.java.treaty.eclipse.verification.EclipseVerifier;
import net.java.treaty.eclipse.verification.VerificationJob;
import net.java.treaty.eclipse.verification.VerificationJobListener;

import org.eclipse.core.runtime.ContributorFactoryOSGi;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.Bundle;

/**
 * <p>
 * Provides test cases to test the {@link Verifier} class using the Clock
 * Example bundles.
 * </p>
 * 
 * @author Claas Wilke
 */
public class SystemExampleTests {

	/**
	 * <p>
	 * A private {@link IJobChangeListener} to listen to the
	 * {@link EclipseVerifier}.
	 * </p>
	 */
	private class TestJobChangeListener implements IJobChangeListener {

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.core.runtime.jobs.IJobChangeListener#aboutToRun(org.eclipse
		 * .core.runtime.jobs.IJobChangeEvent)
		 */
		public void aboutToRun(IJobChangeEvent event) {

			/* Do nothing. */
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.core.runtime.jobs.IJobChangeListener#awake(org.eclipse.core
		 * .runtime.jobs.IJobChangeEvent)
		 */
		public void awake(IJobChangeEvent event) {

			/* Do nothing. */
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.core.runtime.jobs.IJobChangeListener#done(org.eclipse.core
		 * .runtime.jobs.IJobChangeEvent)
		 */
		public void done(IJobChangeEvent event) {

			verificationJob = (VerificationJob) event.getJob();
			hasJobFinished = true;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.core.runtime.jobs.IJobChangeListener#running(org.eclipse.
		 * core.runtime.jobs.IJobChangeEvent)
		 */
		public void running(IJobChangeEvent event) {

			/* Do nothing. */
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.core.runtime.jobs.IJobChangeListener#scheduled(org.eclipse
		 * .core.runtime.jobs.IJobChangeEvent)
		 */
		public void scheduled(IJobChangeEvent event) {

			/* Do nothing. */
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.core.runtime.jobs.IJobChangeListener#sleeping(org.eclipse
		 * .core.runtime.jobs.IJobChangeEvent)
		 */
		public void sleeping(IJobChangeEvent event) {

			/* Do nothing. */
		}
	}

	/**
	 * <p>
	 * A private {@link VerificationJobListener} to listen to the
	 * {@link EclipseVerifier}.
	 * </p>
	 */
	private class TestVerificationJobListener implements VerificationJobListener {

		/*
		 * (non-Javadoc)
		 * @seenet.java.treaty.eclipse.verification.VerificationJobListener#
		 * verificationStatusChanged()
		 */
		public void verificationStatusChanged() {

			/*
			 * We are only interested in the final result, thus don't do anything
			 * here.
			 */
		}
	}

	/**
	 * The length of the pause between to loops during searching a
	 * {@link Contract} for testing.
	 */
	private static final int CONTRACT_SEARCH_PAUSE_LENGTH = 100;

	/**
	 * The number of total loops to search a {@link Contract} for testing before
	 * fail of the test case.
	 */
	private static final int CONTRACT_SEARCH_TOTAL_LOOPS = 100;

	/** The ID of the Eclipse Help System {@link Bundle}. */
	private static final String BUNDLE_ECLIPSE_HELP_ID = "org.eclipse.help";

	/** The ID of the Eclipse Help System {@link Bundle}. */
	private static final String BUNDLE_TREATY_SYSTEM_EXAMPLE =
			"net.java.treaty.eclipse.system_selected";

	/** The ID of the Treaty Eclipse UI {@link Bundle}. */
	private static final String BUNDLE_TREATY_ECLIPSE_UI_ID =
			"net.java.treaty.eclipse.ui";

	/** The ID of the Eclipse JDT User {@link Bundle}. */
	private static final String BUNDLE_ECLIPSE_JDT_DOC_USER_ID =
			"org.eclipse.jdt.doc.user";

	/** The ID of the Eclipse JDT User {@link Bundle}. */
	private static final String BUNDLE_ECLIPSE_PLATFORM_DOC_USER_ID =
			"org.eclipse.platform.doc.user";

	/** The ID of an {@link IExtensionPoint} used during testing. */
	private static final String EXTENSION_POINT_ECLIPSE_HELP_TOC_ID =
			"org.eclipse.help.toc";

	/** Some {@link EclipseExtension}s whose {@link Contract}s shall be verified. */
	private static List<EclipseExtension> extensions_treatyEclipseHelpToc;

	/** Some {@link EclipseExtension}s whose {@link Contract}s shall be verified. */
	private static List<EclipseExtension> extensions_eclipseJDTDocUser;

	/** Some {@link EclipseExtension}s whose {@link Contract}s shall be verified. */
	private static List<EclipseExtension> extensions_eclipsePlatformDocUser;

	/**
	 * A boolean that indicates if the {@link VerificationJob} has already
	 * finished its work.
	 */
	private boolean hasJobFinished;

	/** The {@link VerificationJob} of the last done verification. */
	private VerificationJob verificationJob;

	/**
	 * <p>
	 * Initializes all test cases of the {@link SystemExampleTests}.
	 * </p>
	 */
	@BeforeClass
	public static void setUp() {

		/* Initialize the ContractRegistry. */
		EclipseContractRegistry.getInstance();

		/* Check if all required bundles exist. */
		Bundle eclipseHelpBundle;
		eclipseHelpBundle =
				org.eclipse.core.runtime.Platform.getBundle(BUNDLE_ECLIPSE_HELP_ID);

		Bundle treatySystemExample;
		treatySystemExample =
				org.eclipse.core.runtime.Platform
						.getBundle(BUNDLE_TREATY_SYSTEM_EXAMPLE);

		Bundle treatyEclipseUIBundle;
		treatyEclipseUIBundle =
				org.eclipse.core.runtime.Platform
						.getBundle(BUNDLE_TREATY_ECLIPSE_UI_ID);

		Bundle eclipseJDTDocUserBundle;
		eclipseJDTDocUserBundle =
				org.eclipse.core.runtime.Platform
						.getBundle(BUNDLE_ECLIPSE_JDT_DOC_USER_ID);

		Bundle eclipsePlatformDocUserBundle;
		eclipsePlatformDocUserBundle =
				org.eclipse.core.runtime.Platform
						.getBundle(BUNDLE_ECLIPSE_PLATFORM_DOC_USER_ID);

		if (eclipseHelpBundle == null || treatySystemExample == null
				|| treatyEclipseUIBundle == null
				|| eclipsePlatformDocUserBundle == null) {

			String msg;
			msg = "Cannot find all required Bundles for testing. Testing failed.";

			throw new RuntimeException(msg);
		}
		// no else.

		IExtensionRegistry extensionRegistry;
		extensionRegistry =
				org.eclipse.core.runtime.Platform.getExtensionRegistry();

		IExtension[] extensions;
		IContributor contributor;

		/* Get the TOC extension of the treaty eclipse UI bundle. */
		contributor =
				ContributorFactoryOSGi.createContributor(treatyEclipseUIBundle);
		extensions = extensionRegistry.getExtensions(contributor);

		extensions_treatyEclipseHelpToc = new ArrayList<EclipseExtension>();

		for (IExtension extension : extensions) {

			if (extension.getExtensionPointUniqueIdentifier().equals(
					EXTENSION_POINT_ECLIPSE_HELP_TOC_ID)) {

				extensions_treatyEclipseHelpToc.add(EclipseAdapterFactory.getInstance()
						.createExtension(extension));
			}
			// no else.
		}
		// end for.

		/* Get the TOC extensions of the Eclipse JDT Doc User bundle. */
		contributor =
				ContributorFactoryOSGi.createContributor(eclipseJDTDocUserBundle);
		extensions = extensionRegistry.getExtensions(contributor);

		extensions_eclipseJDTDocUser = new ArrayList<EclipseExtension>();

		for (IExtension extension : extensions) {

			if (extension.getExtensionPointUniqueIdentifier().equals(
					EXTENSION_POINT_ECLIPSE_HELP_TOC_ID)) {

				extensions_eclipseJDTDocUser.add(EclipseAdapterFactory.getInstance()
						.createExtension(extension));
			}
			// no else.
		}
		// end for.

		/* Get the TOC extensions of the Eclipse Platform Doc User bundle. */
		contributor =
				ContributorFactoryOSGi.createContributor(eclipsePlatformDocUserBundle);
		extensions = extensionRegistry.getExtensions(contributor);

		extensions_eclipsePlatformDocUser = new ArrayList<EclipseExtension>();

		for (IExtension extension : extensions) {

			if (extension.getExtensionPointUniqueIdentifier().equals(
					EXTENSION_POINT_ECLIPSE_HELP_TOC_ID)) {

				extensions_eclipsePlatformDocUser.add(EclipseAdapterFactory
						.getInstance().createExtension(extension));
			}
			// no else.
		}
		// end for.
	}

	/**
	 * <p>
	 * Tests the method
	 * {@link EclipseVerifier#verify(Collection, VerificationReport, VerificationJobListener, IJobChangeListener)}
	 * method.
	 * </p>
	 */
	@Test
	public void testVerify01() {

		/* Ensure preconditions. */
		assertEquals("The plug-in 'net.java.treaty.ui' should provide exactly one "
				+ "Extension of the ExtensionPoint "
				+ EXTENSION_POINT_ECLIPSE_HELP_TOC_ID + ".", 1,
				extensions_treatyEclipseHelpToc.size());

		Collection<Contract> contracts;
		contracts = Collections.emptySet();

		/* Wait till the contract registry is ready. */
		int loops = 0;

		while (contracts.size() != 1) {
			contracts =
					EclipseContractRegistry.getInstance().getContracts(
							extensions_treatyEclipseHelpToc.get(0), Role.SUPPLIER);

			/* Wait some time to improve CPU performance. */
			try {
				Thread.sleep(CONTRACT_SEARCH_PAUSE_LENGTH);
			}

			catch (InterruptedException e) {
				fail("Cannot ensure that testcase behaves determinisitc. Test failed.");
			}
			// end catch.

			loops++;
			if (loops > CONTRACT_SEARCH_TOTAL_LOOPS) {
				fail("Contract for test was not available. Test failed.");
			}
			// no else.
		}

		VerificationReport verificationReport;
		verificationReport = new EclipseVerificationReport();

		VerificationJobListener verificationJobListener;
		verificationJobListener = new TestVerificationJobListener();

		IJobChangeListener jobChangeListener;
		jobChangeListener = new TestJobChangeListener();

		this.hasJobFinished = false;

		EclipseVerifier.verify(contracts, verificationReport,
				verificationJobListener, jobChangeListener);

		/* Wait till the job finished. */
		while (!this.hasJobFinished) {

			/* Wait some time to improve CPU performance. */
			try {
				Thread.sleep(100);
			}

			catch (InterruptedException e) {
				fail("Cannot ensure that testcase behaves determinisitc. Test failed.");
			}
			// end catch.
		}
		// end while.

		/* One contracts should be done, and none of them should have failed. */
		assertNotNull(this.verificationJob);
		assertEquals(1, this.verificationJob.getDoneContracts().size());
		assertEquals(0, this.verificationJob.getFailedContracts().size());
	}

	/**
	 * <p>
	 * Tests the method
	 * {@link EclipseVerifier#verify(Collection, VerificationReport, VerificationJobListener, IJobChangeListener)}
	 * method.
	 * </p>
	 */
	@Test
	public void testVerify02() {

		/* Ensure preconditions. */
		assertEquals(
				"The plug-in 'org.eclipse.jdt.doc.user' should provide exactly two "
						+ "Extensions of the ExtensionPoint "
						+ EXTENSION_POINT_ECLIPSE_HELP_TOC_ID + ".", 2,
				extensions_eclipseJDTDocUser.size());

		Collection<Contract> contracts;
		contracts = Collections.emptySet();

		/* Wait till the contract registry is ready. */
		int loops = 0;

		while (contracts.size() != 2) {
			contracts =
					EclipseContractRegistry.getInstance().getContracts(
							extensions_eclipseJDTDocUser.get(0), Role.SUPPLIER);
			contracts.addAll(EclipseContractRegistry.getInstance().getContracts(
					extensions_eclipseJDTDocUser.get(1), Role.SUPPLIER));

			/* Wait some time to improve CPU performance. */
			try {
				Thread.sleep(CONTRACT_SEARCH_PAUSE_LENGTH);
			}

			catch (InterruptedException e) {
				fail("Cannot ensure that testcase behaves determinisitc. Test failed.");
			}
			// end catch.

			loops++;
			if (loops > CONTRACT_SEARCH_TOTAL_LOOPS) {
				fail("Contracts for test were not available. Test failed.");
			}
			// no else.
		}

		VerificationReport verificationReport;
		verificationReport = new EclipseVerificationReport();

		VerificationJobListener verificationJobListener;
		verificationJobListener = new TestVerificationJobListener();

		IJobChangeListener jobChangeListener;
		jobChangeListener = new TestJobChangeListener();

		this.hasJobFinished = false;

		EclipseVerifier.verify(contracts, verificationReport,
				verificationJobListener, jobChangeListener);

		/* Wait till the job finished. */
		while (!this.hasJobFinished) {

			/* Wait some time to improve CPU performance. */
			try {
				Thread.sleep(100);
			}

			catch (InterruptedException e) {
				fail("Cannot ensure that testcase behaves determinisitc. Test failed.");
			}
			// end catch.
		}
		// end while.

		/* Two contracts should be done, and one of them should have failed. */
		assertNotNull(this.verificationJob);
		assertEquals(2, this.verificationJob.getDoneContracts().size());
		assertEquals(1, this.verificationJob.getFailedContracts().size());
	}

	/**
	 * <p>
	 * Tests the method
	 * {@link EclipseVerifier#verify(Collection, VerificationReport, VerificationJobListener, IJobChangeListener)}
	 * method.
	 * </p>
	 */
	@Test
	public void testVerify03() {

		/* Ensure preconditions. */
		assertEquals(
				"The plug-in 'org.eclipse.platform.doc.user' should provide exactly two "
						+ "Extensions of the ExtensionPoint "
						+ EXTENSION_POINT_ECLIPSE_HELP_TOC_ID + ".", 2,
				extensions_eclipsePlatformDocUser.size());

		Collection<Contract> contracts;
		contracts = Collections.emptySet();

		/* Wait till the contract registry is ready. */
		int loops = 0;

		while (contracts.size() != 2) {
			contracts =
					EclipseContractRegistry.getInstance().getContracts(
							extensions_eclipsePlatformDocUser.get(0), Role.SUPPLIER);
			contracts.addAll(EclipseContractRegistry.getInstance().getContracts(
					extensions_eclipsePlatformDocUser.get(1), Role.SUPPLIER));

			/* Wait some time to improve CPU performance. */
			try {
				Thread.sleep(CONTRACT_SEARCH_PAUSE_LENGTH);
			}

			catch (InterruptedException e) {
				fail("Cannot ensure that testcase behaves determinisitc. Test failed.");
			}
			// end catch.

			loops++;
			if (loops > CONTRACT_SEARCH_TOTAL_LOOPS) {
				fail("Contracts for test were not available. Test failed.");
			}
			// no else.
		}

		VerificationReport verificationReport;
		verificationReport = new EclipseVerificationReport();

		VerificationJobListener verificationJobListener;
		verificationJobListener = new TestVerificationJobListener();

		IJobChangeListener jobChangeListener;
		jobChangeListener = new TestJobChangeListener();

		this.hasJobFinished = false;

		EclipseVerifier.verify(contracts, verificationReport,
				verificationJobListener, jobChangeListener);

		/* Wait till the job finished. */
		while (!this.hasJobFinished) {

			/* Wait some time to improve CPU performance. */
			try {
				Thread.sleep(100);
			}

			catch (InterruptedException e) {
				fail("Cannot ensure that testcase behaves determinisitc. Test failed.");
			}
			// end catch.
		}
		// end while.

		/* Two contracts should be done, and two of them should have failed. */
		assertNotNull(this.verificationJob);
		assertEquals(2, this.verificationJob.getDoneContracts().size());
		assertEquals(2, this.verificationJob.getFailedContracts().size());
	}
}