/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.verification;

import java.net.URI;
import java.util.Collection;

import net.java.treaty.Connector;
import net.java.treaty.Contract;
import net.java.treaty.ExistsCondition;
import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.ResourceLoader;
import net.java.treaty.ResourceLoaderException;
import net.java.treaty.VerificationException;
import net.java.treaty.VerificationReport;
import net.java.treaty.VerificationResult;
import net.java.treaty.Verifier;
import net.java.treaty.eclipse.Constants;
import net.java.treaty.eclipse.ContractVerificationSchedulingRule;
import net.java.treaty.eclipse.VocabularyRegistry;

import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.MultiRule;

/**
 * <p>
 * The {@link EclipseVerifier} implements the {@link Verifier} interface for the
 * Treaty Eclipse implementation. It can be used to verify {@link Contract}s.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class EclipseVerifier implements Verifier, ResourceLoader {

	/**
	 * <p>
	 * Creates a new {@link EclipseVerifier}.
	 * </p>
	 */
	public EclipseVerifier() {

		super();
	}

	/**
	 * <p>
	 * Verifies a Collection of given {@link Contract}s for a given
	 * {@link VerificationReport}, {@link VerificationJobListener} and
	 * {@link IJobChangeListener}.
	 * </p>
	 * 
	 * @param contracts
	 *          The {@link Contract}s that shall be verified.
	 * @param verificationReport
	 *          The {@link VerificationReport} used to store the results.
	 * @param verificationJobListener
	 *          A {@link VerificationJobListener} that can be used to observe the
	 *          progress.
	 * @param jobChangeListener
	 *          An {@link IJobChangeListener} that can be used to observe the
	 *          progress.
	 */
	public static void verify(Collection<Contract> contracts,
			VerificationReport verificationReport,
			VerificationJobListener verificationJobListener,
			IJobChangeListener jobChangeListener) {

		VerificationJob job;
		job =
				new VerificationJob("Treaty Component Verification", contracts,
						verificationReport);

		job.addVerificationJobListener(verificationJobListener);
		job.addJobChangeListener(jobChangeListener);

		ISchedulingRule combinedRule;
		combinedRule = null;

		for (Contract contract : contracts) {
			MultiRule.combine(new ContractVerificationSchedulingRule(contract),
					combinedRule);
		}

		job.setRule(combinedRule);
		job.schedule();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.ExistsCondition)
	 */
	public void check(ExistsCondition condition) throws VerificationException {

		try {
			VocabularyRegistry.INSTANCE.check(condition);

			condition.setProperty(Constants.VERIFICATION_RESULT,
					VerificationResult.SUCCESS);

			/* Probably remove old exceptions. */
			condition.removeProperty(Constants.VERIFICATION_EXCEPTION);
		}
		// end try.

		catch (VerificationException x) {

			/* Set failure and exception. */
			condition.setProperty(Constants.VERIFICATION_RESULT,
					VerificationResult.FAILURE);
			condition.setProperty(Constants.VERIFICATION_EXCEPTION, x);

			throw (VerificationException) x.fillInStackTrace();
		}
		// end catch.
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.PropertyCondition)
	 */
	public void check(PropertyCondition condition) throws VerificationException {

		/* Try to verify the condition. */
		try {
			VocabularyRegistry.INSTANCE.check(condition);

			condition.setProperty(Constants.VERIFICATION_RESULT,
					VerificationResult.SUCCESS);

			/* Probably remove old exceptions. */
			condition.removeProperty(Constants.VERIFICATION_EXCEPTION);
		}
		// end try.

		catch (VerificationException e) {

			/* Set failure and exception. */
			condition.setProperty(Constants.VERIFICATION_RESULT,
					VerificationResult.FAILURE);
			condition.setProperty(Constants.VERIFICATION_EXCEPTION, e);

			throw (VerificationException) e.fillInStackTrace();
		}
		// end catch.
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.RelationshipCondition)
	 */
	public void check(RelationshipCondition condition)
			throws VerificationException {

		/* Try to verify the condition. */
		try {
			VocabularyRegistry.INSTANCE.check(condition);

			condition.setProperty(Constants.VERIFICATION_RESULT,
					VerificationResult.SUCCESS);

			/* Probably remove old exceptions. */
			condition.removeProperty(Constants.VERIFICATION_EXCEPTION);
		}
		// end try.

		catch (VerificationException e) {

			/* Set failure and exception. */
			condition.setProperty(Constants.VERIFICATION_RESULT,
					VerificationResult.FAILURE);
			condition.setProperty(Constants.VERIFICATION_EXCEPTION, e);

			throw (VerificationException) e.fillInStackTrace();
		}
		// end catch.
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ResourceLoader#load(java.net.URI, java.lang.String,
	 * net.java.treaty.Connector)
	 */
	public Object load(URI type, String name, Connector connector)
			throws ResourceLoaderException {

		return VocabularyRegistry.INSTANCE.load(type, name, connector);
	}
}