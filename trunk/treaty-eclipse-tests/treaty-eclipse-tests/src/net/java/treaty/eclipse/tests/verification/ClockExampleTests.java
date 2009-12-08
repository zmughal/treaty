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

import java.util.Collection;
import java.util.Collections;

import net.java.treaty.Contract;
import net.java.treaty.Role;
import net.java.treaty.VerificationReport;
import net.java.treaty.Verifier;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.contractregistry.EclipseAdapterFactory;
import net.java.treaty.eclipse.contractregistry.EclipseConnectorAdaptationException;
import net.java.treaty.eclipse.contractregistry.EclipseContractRegistry;
import net.java.treaty.eclipse.verification.EclipseVerificationReport;
import net.java.treaty.eclipse.verification.EclipseVerifier;
import net.java.treaty.eclipse.verification.VerificationJob;
import net.java.treaty.eclipse.verification.VerificationJobListener;

import org.eclipse.core.runtime.IExtension;
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
public class ClockExampleTests {

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

		/*
		 * (non-Javadoc)
		 * @seenet.java.treaty.eclipse.verification.VerificationJobListener#
		 * verificationOfContractFailed(net.java.treaty.Contract,
		 * net.java.treaty.VerificationReport)
		 */
		public void verificationOfContractFailed(Contract contract,
				VerificationReport verificationReport) {

			/* Do nothing. */
		}

		/*
		 * (non-Javadoc)
		 * @seenet.java.treaty.eclipse.verification.VerificationJobListener#
		 * verificationOfContractSucceeded(net.java.treaty.Contract,
		 * net.java.treaty.VerificationReport)
		 */
		public void verificationOfContractSucceeded(Contract contract,
				VerificationReport verificationReport) {

			/* Do nothing. */
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

	/** The ID of the clock example {@link Bundle}. */
	private static final String BUNDLE_CLOCK_EXAMPLE_ID =
			"net.java.treaty.eclipse.example.clock";

	/** The ID of the fancy date formatter {@link Bundle}. */
	private static final String BUNDLE_FANCY_DATEFORMATTER_ID =
			"net.java.treaty.eclipse.example.clock.fancydateformatter";

	/** The ID of the lazy date formatter {@link IExtension}. */
	private static final String BUNDLE_LAZY_DATEFORMATTER_ID =
			"net.java.treaty.eclipse.example.clock.lazydateformatter";

	/** The ID of the lazy date formatter {@link Bundle}. */
	private static final String BUNDLE_LONG_DATEFORMATTER_ID =
			"net.java.treaty.eclipse.example.clock.longdateformatter";

	/** The ID of the lazy date formatter {@link Bundle}. */
	private static final String BUNDLE_SHORT_DATEFORMATTER_ID =
			"net.java.treaty.eclipse.example.clock.shortdateformatter";

	/** The ID of the lazy date formatter {@link Bundle}. */
	private static final String BUNDLE_TOOSHORT_DATEFORMATTER_ID =
			"net.java.treaty.eclipse.example.clock.tooshortdateformatter";

	/** The ID of the fancy date formatter {@link IExtension}. */
	private static final String EXTENSION_FANCY_DATEFORMATTER_ID =
			"net.java.treaty.eclipse.example.clock.fancydateformatter";

	/** The ID of the lazy date formatter {@link IExtension}. */
	private static final String EXTENSION_LAZY_DATEFORMATTER_ID =
			"net.java.treaty.eclipse.example.clock.lazydateformatter";

	/** The ID of the lazy date formatter {@link IExtension}. */
	private static final String EXTENSION_LONG_DATEFORMATTER_ID =
			"net.java.treaty.eclipse.example.clock.longdateformatter";

	/** The ID of the lazy date formatter {@link IExtension}. */
	private static final String EXTENSION_SHORT_DATEFORMATTER_ID =
			"net.java.treaty.eclipse.example.clock.shortdateformatter";

	/** The ID of the lazy date formatter {@link IExtension}. */
	private static final String EXTENSION_TOOSHORT_DATEFORMATTER_ID =
			"net.java.treaty.eclipse.example.clock.tooshortdateformatter";

	/** An {@link EclipseExtension} whose {@link Contract}s shall be verified. */
	private static EclipseExtension extension_fancyDateFormatter;

	/** An {@link EclipseExtension} whose {@link Contract}s shall be verified. */
	private static EclipseExtension extension_lazyDateFormatter;

	/** An {@link EclipseExtension} whose {@link Contract}s shall be verified. */
	private static EclipseExtension extension_longDateFormatter;

	/** An {@link EclipseExtension} whose {@link Contract}s shall be verified. */
	private static EclipseExtension extension_shortDateFormatter;

	/** An {@link EclipseExtension} whose {@link Contract}s shall be verified. */
	private static EclipseExtension extension_tooshortDateFormatter;

	/**
	 * A boolean that indicates if the {@link VerificationJob} has already
	 * finished its work.
	 */
	private boolean hasJobFinished;

	/** The {@link VerificationJob} of the last done verification. */
	private VerificationJob verificationJob;

	/**
	 * <p>
	 * Initializes all test cases of the {@link ClockExampleTests}.
	 * </p>
	 */
	@BeforeClass
	public static void setUp() throws EclipseConnectorAdaptationException {

		/* Initialize the ContractRegistry. */
		EclipseContractRegistry.getInstance();

		/* Check if all required bundles exist. */
		Bundle clockBundle;
		clockBundle =
				org.eclipse.core.runtime.Platform.getBundle(BUNDLE_CLOCK_EXAMPLE_ID);

		Bundle fanzyDateFormatterBundle;
		fanzyDateFormatterBundle =
				org.eclipse.core.runtime.Platform
						.getBundle(BUNDLE_FANCY_DATEFORMATTER_ID);

		Bundle lazyDateFormatterBundle;
		lazyDateFormatterBundle =
				org.eclipse.core.runtime.Platform
						.getBundle(BUNDLE_LAZY_DATEFORMATTER_ID);

		Bundle longDateFormatterBundle;
		longDateFormatterBundle =
				org.eclipse.core.runtime.Platform
						.getBundle(BUNDLE_LONG_DATEFORMATTER_ID);

		Bundle shortDateFormatterBundle;
		shortDateFormatterBundle =
				org.eclipse.core.runtime.Platform
						.getBundle(BUNDLE_SHORT_DATEFORMATTER_ID);

		Bundle tooShortDateFormatterBundle;
		tooShortDateFormatterBundle =
				org.eclipse.core.runtime.Platform
						.getBundle(BUNDLE_TOOSHORT_DATEFORMATTER_ID);

		if (clockBundle == null || fanzyDateFormatterBundle == null
				|| lazyDateFormatterBundle == null || longDateFormatterBundle == null
				|| shortDateFormatterBundle == null
				|| tooShortDateFormatterBundle == null) {

			String msg;
			msg = "Cannot find all required Bundles for testing. Testing failed.";

			throw new RuntimeException(msg);
		}
		// no else.

		IExtensionRegistry extensionRegistry;
		extensionRegistry =
				org.eclipse.core.runtime.Platform.getExtensionRegistry();

		/* Get the fancy date formatter extension. */
		IExtension extension;
		extension =
				extensionRegistry.getExtension(EXTENSION_FANCY_DATEFORMATTER_ID);

		extension_fancyDateFormatter =
				EclipseAdapterFactory.getInstance().createExtension(extension);

		/* Get the lazy date formatter extension. */
		extension = extensionRegistry.getExtension(EXTENSION_LAZY_DATEFORMATTER_ID);

		extension_lazyDateFormatter =
				EclipseAdapterFactory.getInstance().createExtension(extension);

		/* Get the long date formatter extension. */
		extension = extensionRegistry.getExtension(EXTENSION_LONG_DATEFORMATTER_ID);

		extension_longDateFormatter =
				EclipseAdapterFactory.getInstance().createExtension(extension);

		/* Get the short date formatter extension. */
		extension =
				extensionRegistry.getExtension(EXTENSION_SHORT_DATEFORMATTER_ID);

		extension_shortDateFormatter =
				EclipseAdapterFactory.getInstance().createExtension(extension);

		/* Get the too short date formatter extension. */
		extension =
				extensionRegistry.getExtension(EXTENSION_TOOSHORT_DATEFORMATTER_ID);

		extension_tooshortDateFormatter =
				EclipseAdapterFactory.getInstance().createExtension(extension);
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

		Collection<Contract> contracts;
		contracts = Collections.emptySet();

		/* Wait till the contract registry is ready. */
		int loops = 0;

		while (contracts.size() != 1) {
			contracts =
					EclipseContractRegistry.getInstance().getContracts(
							extension_fancyDateFormatter, Role.SUPPLIER);

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

		/* One 1 contracts should be done, and none of them should have failed. */
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

		Collection<Contract> contracts;
		contracts = Collections.emptySet();

		/* Wait till the contract registry is ready. */
		int loops = 0;

		while (contracts.size() != 1) {
			contracts =
					EclipseContractRegistry.getInstance().getContracts(
							extension_lazyDateFormatter, Role.SUPPLIER);

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

		/* One 1 contracts should be done, and 1 of them should have failed. */
		assertNotNull(this.verificationJob);
		assertEquals(1, this.verificationJob.getDoneContracts().size());
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

		Collection<Contract> contracts;
		contracts = Collections.emptySet();

		/* Wait till the contract registry is ready. */
		int loops = 0;

		while (contracts.size() != 1) {
			contracts =
					EclipseContractRegistry.getInstance().getContracts(
							extension_longDateFormatter, Role.SUPPLIER);

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

		/* One 1 contracts should be done, and none of them should have failed. */
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
	public void testVerify04() {

		Collection<Contract> contracts;
		contracts = Collections.emptySet();

		/* Wait till the contract registry is ready. */
		int loops = 0;

		while (contracts.size() != 1) {
			contracts =
					EclipseContractRegistry.getInstance().getContracts(
							extension_shortDateFormatter, Role.SUPPLIER);

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

		/* One 1 contracts should be done, and none of them should have failed. */
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
	public void testVerify05() {

		Collection<Contract> contracts;
		contracts = Collections.emptySet();

		/* Wait till the contract registry is ready. */
		int loops = 0;

		while (contracts.size() != 1) {
			contracts =
					EclipseContractRegistry.getInstance().getContracts(
							extension_tooshortDateFormatter, Role.SUPPLIER);

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

		/* One 1 contracts should be done, and 1 of them should have failed. */
		assertNotNull(this.verificationJob);
		assertEquals(1, this.verificationJob.getDoneContracts().size());
		assertEquals(1, this.verificationJob.getFailedContracts().size());
	}
}