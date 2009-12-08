/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.ui.actions;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.VerificationReport;
import net.java.treaty.action.ActionVocabulary;
import net.java.treaty.eclipse.Logger;
import net.java.treaty.eclipse.views.ContractView;

public class UIActionVocabulary implements ActionVocabulary {

	/** The name space's name of the {@link UIActionVocabulary}. */
	public static final String NAME_SPACE_NAME =
			"http://www.treaty.org/action/ui";

	/**
	 * The name of the action type representing an action to display a
	 * verification result.
	 */
	public static final String ACTION_TYPE_SHOW_VERIFICATION_RESULT =
			NAME_SPACE_NAME + "#ShowResult";

	/**
	 * The action types of this {@link ActionVocabulary} as a {@link Map} of
	 * {@link URI}s identified by their {@link String} representation.
	 */
	private Map<String, URI> actionTypes;

	/**
	 * The {@link ContractView} on which the actions of this
	 * {@link UIActionVocabulary} shall be performed.
	 */
	private ContractView contractView;

	/**
	 * <p>
	 * Creates a new {@link UIActionVocabulary}.
	 * </p>
	 */
	public UIActionVocabulary() {

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

		if (ACTION_TYPE_SHOW_VERIFICATION_RESULT.equals(actionType.toString())) {

			if (this.contractView != null) {

				contractView.update();
				contractView.reportVerificationResult(verificationReports);
			}

			else {
				Logger
						.warn("ContractView to display verification result was not available.");
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

		/* This vocabulary does not provide any actions on begin verification. */
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#isAfterAction(java.net.URI)
	 */
	public boolean isAfterAction(URI actionType) {

		/* The show verification action is universal on end. */
		return ACTION_TYPE_SHOW_VERIFICATION_RESULT.equals(actionType.toString());
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#isBeforeAction(java.net.URI)
	 */
	public boolean isBeforeAction(URI actionType) {

		/* This vocabulary does not provide any actions on begin verification. */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#perform(java.net.URI,
	 * java.net.URI, net.java.treaty.VerificationReport)
	 */
	public void perform(URI triggerType, URI actionType,
			VerificationReport verificationReport) {

		/*
		 * This vocabulary does not provide any actions on verification of single
		 * contracts.
		 */
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.action.ActionVocabulary#isDefaultOnFailure(java.net.URI)
	 */
	public boolean isDefaultOnFailure(URI actionType) {

		/* This vocabulary does not provide any actions on failed verification. */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.action.ActionVocabulary#isDefaultOnSuccess(java.net.URI)
	 */
	public boolean isDefaultOnSuccess(URI actionType) {

		/* This vocabulary does not provide any actions on successful verification. */
		return false;
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
	 * Sets the {@link ContractView} on which the actions of this
	 * {@link UIActionVocabulary} shall be performed.
	 * </p>
	 * 
	 * @param contractView
	 *          The {@link ContractView} on which the actions of this
	 *          {@link UIActionVocabulary} shall be performed.
	 */
	public void setContractView(ContractView contractView) {

		this.contractView = contractView;
	}

	/**
	 * <p>
	 * Initializes the {@link BundleTriggerVocabulary}.
	 * </p>
	 */
	private void initialize() {

		/* Probably initialize the action Types */
		if (this.actionTypes == null) {

			this.actionTypes = new HashMap<String, URI>();

			try {
				this.actionTypes.put(ACTION_TYPE_SHOW_VERIFICATION_RESULT, new URI(
						ACTION_TYPE_SHOW_VERIFICATION_RESULT));
			}

			catch (URISyntaxException e) {
				Logger.warn("Error during initialization of UIActionVocabulary. "
						+ "Probably some action types are not available.", e);
			}
			// end catch.
		}
		// no else (already initialized).
	}
}