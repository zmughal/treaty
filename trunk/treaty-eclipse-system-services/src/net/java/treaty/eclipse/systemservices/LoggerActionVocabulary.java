/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.systemservices;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.VerificationReport;
import net.java.treaty.VerificationResult;
import net.java.treaty.action.ActionVocabulary;
import net.java.treaty.eclipse.Logger;

/**
 * <p>
 * Implements the {@link ActionVocabulary} interface and provides an action to
 * log verification results.
 * </p>
 * 
 * @author Claas Wilke
 */
public class LoggerActionVocabulary implements ActionVocabulary {

	/** The name space's name of the {@link LoggerActionVocabulary}. */
	public static final String NAME_SPACE_NAME =
			"http://www.treaty.org/action/logger";

	/**
	 * The name of the action type representing an action to log a verification
	 * result.
	 */
	public static final String ACTION_TYPE_LOG_INFO =
			NAME_SPACE_NAME + "#LogInfo";

	/**
	 * The name of the action type representing an action to log a verification
	 * result.
	 */
	public static final String ACTION_TYPE_LOG_WARNING =
			NAME_SPACE_NAME + "#LogWarning";

	/**
	 * The name of the action type representing an action to log a verification
	 * result.
	 */
	public static final String ACTION_TYPE_LOG_ERROR =
			NAME_SPACE_NAME + "#LogError";

	/**
	 * The action types of this {@link ActionVocabulary} as a {@link Map} of
	 * {@link URI}s identified by their {@link String} representation.
	 */
	private Map<String, URI> actionTypes;

	/**
	 * <p>
	 * Creates a new {@link BundleTriggerVocabulary}.
	 * </p>
	 */
	public LoggerActionVocabulary() {

		this.initialize();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#getActionTypes()
	 */
	public Set<URI> getActionTypes() {

		return new HashSet<URI>(this.actionTypes.values());
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#after(java.net.URI,
	 * java.net.URI, java.util.Set)
	 */
	public void after(URI triggerType, URI actionType,
			Set<VerificationReport> verificationReports) {

		if (this.getActionTypes().contains(actionType)) {

			StringBuffer buffer;
			buffer = new StringBuffer();

			buffer.append("Verification finished. ");
			buffer.append(verificationReports.size());
			buffer.append(" Contracts were verified, verification of ");

			int failedContracts;
			failedContracts = 0;

			for (VerificationReport verificationReport : verificationReports) {

				if (verificationReport.getVerificationResult() != VerificationResult.SUCCESS) {
					failedContracts++;
				}
				// no else.
			}
			// end for.

			buffer.append(failedContracts + " Contracts failed. ");
			buffer.append("Verification was triggered by trigger ");
			buffer.append(triggerType.toString());

			if (ACTION_TYPE_LOG_ERROR.equals(actionType.toString())) {
				Logger.error(buffer.toString());
			}

			else if (ACTION_TYPE_LOG_INFO.equals(actionType.toString())) {
				Logger.info(buffer.toString());
			}

			else if (ACTION_TYPE_LOG_WARNING.equals(actionType.toString())) {
				Logger.warn(buffer.toString());
			}
		}
		// no else (wrong type of action).
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#before(java.net.URI,
	 * java.net.URI, java.util.Set)
	 */
	public void before(URI triggerType, URI actionType,
			Set<Contract> contractsToVerify) {

		if (this.getActionTypes().contains(actionType)) {

			StringBuffer buffer;
			buffer = new StringBuffer();

			buffer.append("Verification started. ");
			buffer.append(contractsToVerify.size());
			buffer.append(" Contracts will be verified. ");
			buffer.append("Verification was triggered by trigger ");
			buffer.append(triggerType.toString());

			if (ACTION_TYPE_LOG_ERROR.equals(actionType.toString())) {
				Logger.error(buffer.toString());
			}

			else if (ACTION_TYPE_LOG_INFO.equals(actionType.toString())) {
				Logger.info(buffer.toString());
			}

			else if (ACTION_TYPE_LOG_WARNING.equals(actionType.toString())) {
				Logger.warn(buffer.toString());
			}
		}
		// no else (wrong type of action).
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#isAfterAction(java.net.URI)
	 */
	public boolean isAfterAction(URI actionType) {

		/* Only the log info action is universal on end of verification. */
		return ACTION_TYPE_LOG_INFO.equals(actionType.toString());
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#isBeforeAction(java.net.URI)
	 */
	public boolean isBeforeAction(URI actionType) {

		/* Only the log info action is universal on begin of verification. */
		return ACTION_TYPE_LOG_INFO.equals(actionType.toString());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.action.ActionVocabulary#isDefaultOnFailure(java.net.URI)
	 */
	public boolean isDefaultOnFailure(URI actionType) {

		/* Only the log warning action is universal on success. */
		return ACTION_TYPE_LOG_WARNING.equals(actionType.toString());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.action.ActionVocabulary#isDefaultOnSuccess(java.net.URI)
	 */
	public boolean isDefaultOnSuccess(URI actionType) {

		/* Only the log info action is universal on success. */
		return ACTION_TYPE_LOG_INFO.equals(actionType.toString());
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#perform(java.net.URI,
	 * java.net.URI, net.java.treaty.VerificationReport)
	 */
	public void perform(URI triggerType, URI actionType,
			VerificationReport verificationReport) {

		if (verificationReport.getVerificationResult() == VerificationResult.SUCCESS) {
			if (ACTION_TYPE_LOG_INFO.equals(actionType.toString())) {

				StringBuffer buffer;
				buffer = new StringBuffer();

				buffer.append("Verification of Contract ");
				buffer.append(verificationReport.getContract());
				buffer.append(" succeeded. ");
				buffer.append("Verification was triggered by trigger ");
				buffer.append(triggerType.toString());

				Logger.info(buffer.toString());
			}
			// no else (wrong type of action).
		}

		else {
			if (ACTION_TYPE_LOG_WARNING.equals(actionType.toString())) {

				StringBuffer buffer;
				buffer = new StringBuffer();

				buffer.append("Verification of Contract ");
				buffer.append(verificationReport.getContract());
				buffer.append(" failed. ");
				buffer.append("Verification was triggered by trigger ");
				buffer.append(triggerType.toString());

				Logger.warn(buffer.toString());
			}
			// no else (wrong type of action).
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return NAME_SPACE_NAME;
	}

	/**
	 * <p>
	 * Initializes the {@link LoggerActionVocabulary}.
	 * </p>
	 */
	private void initialize() {

		/* Probably initialize the action Types */
		if (this.actionTypes == null) {

			this.actionTypes = new HashMap<String, URI>();

			try {
				this.actionTypes.put(ACTION_TYPE_LOG_INFO,
						new URI(ACTION_TYPE_LOG_INFO));
				this.actionTypes.put(ACTION_TYPE_LOG_WARNING, new URI(
						ACTION_TYPE_LOG_WARNING));
				this.actionTypes.put(ACTION_TYPE_LOG_ERROR, new URI(
						ACTION_TYPE_LOG_ERROR));
			}

			catch (URISyntaxException e) {
				Logger.warn("Error during initialization of LoggerActionVocabulary. "
						+ "Probably some action types are not available.", e);
			}
			// end catch.
		}
		// no else (already initialized).
	}
}