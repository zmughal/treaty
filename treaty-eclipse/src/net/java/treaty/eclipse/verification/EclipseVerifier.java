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
import net.java.treaty.Resource;
import net.java.treaty.ResourceLoader;
import net.java.treaty.ResourceLoaderException;
import net.java.treaty.VerificationException;
import net.java.treaty.VerificationReport;
import net.java.treaty.VerificationResult;
import net.java.treaty.Verifier;
import net.java.treaty.eclipse.Constants;
import net.java.treaty.eclipse.ContractVerificationSchedulingRule;
import net.java.treaty.eclipse.Logger;
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

		Resource resource;
		resource = condition.getResource();

		URI type;
		type = resource.getType();

		/* FIXME Claas: Is this still necessary? */
		/* Handle the built-in types separately. */
		if (type.toString().startsWith("http://www.w3.org/2001/XMLSchema")) {

			assert resource.isInstantiated();
			assert resource.isLoaded();

			if (resource.getValue() == null) {
				throw new VerificationException("Resource value should not be null");
			}

			this.checkBuiltInDatatype(resource.getValue(), type);
		}

		/* Else try to verify the condition. */
		else {
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
		// end else.
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

		Object result;

		String typeName;
		typeName = type.toString();

		/* FIXME Claas: Is this still necessary? */
		/* Handle built-in types especially. */
		if (typeName.equals("http://www.w3.org/2001/XMLSchema#string")) {
			result = name;
		}

		else if (typeName.equals("http://www.w3.org/2001/XMLSchema#int")
				|| typeName.equals("http://www.w3.org/2001/XMLSchema#integer")) {

			try {
				result = Integer.parseInt(name);
			}

			catch (NumberFormatException x) {
				throw new ResourceLoaderException(x);
			}
		}

		else if (typeName.equals("http://www.w3.org/2001/XMLSchema#double")) {

			try {
				result = Double.parseDouble(name);
			}

			catch (NumberFormatException x) {
				throw new ResourceLoaderException(x);
			}
		}

		else if (typeName.equals("http://www.w3.org/2001/XMLSchema#boolean")) {
			result = Boolean.parseBoolean(name);
		}

		/* Else delegate to the vocabularies. */
		else {
			result = VocabularyRegistry.INSTANCE.load(type, name, connector);
		}

		return result;
	}

	/**
	 * FIXME Claas: Is this method still necessary?
	 * 
	 * <p>
	 * A helper method to verify values for built-in data types.
	 * </p>
	 * 
	 * @param value
	 *          The value that shall be checked.
	 * @param type
	 *          The type (a {@link URI}) the value belongs to.
	 * @throws VerificationException
	 *           Thrown, if the verification fails.
	 */
	private void checkBuiltInDatatype(Object value, URI type)
			throws VerificationException {

		String typeName;
		typeName = type.toString();

		if (typeName.equals("http://www.w3.org/2001/XMLSchema:string")) {
			Logger
					.warn("oudated data type URI used !!: http://www.w3.org/2001/XMLSchema#string");
		}
		// no else.

		if (typeName.equals("http://www.w3.org/2001/XMLSchema#string")) {
			return;
		}

		/* int should be used ! see: http://www.w3.org/TR/xmlschema-2/ */
		else if (typeName.equals("http://www.w3.org/2001/XMLSchema#int")
				|| typeName.equals("http://www.w3.org/2001/XMLSchema#int")) {

			if (value instanceof Integer) {
				return;
			}

			try {
				Integer.parseInt(value.toString());
			}

			catch (NumberFormatException x) {
				throw new VerificationException(value.toString()
						+ " is not an Integer.");
			}
		}

		else if (typeName.equals("http://www.w3.org/2001/XMLSchema#double")) {

			if (value instanceof Double) {
				return;
			}

			try {
				Double.parseDouble(value.toString());
			}

			catch (NumberFormatException x) {
				throw new VerificationException(value.toString() + " is not a double");
			}
		}

		else if (typeName.equals("http://www.w3.org/2001/XMLSchema#boolean")) {

			if (value instanceof Boolean) {
				return;
			}

			try {
				Boolean.parseBoolean(value.toString());
			}

			catch (NumberFormatException x) {
				throw new VerificationException(value.toString() + " is not a boolean");
			}
		}

		else {
			throw new VerificationException("Unsupported data type: " + type);
		}
	}
}