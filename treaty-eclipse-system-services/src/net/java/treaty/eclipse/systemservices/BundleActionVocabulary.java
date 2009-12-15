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
import net.java.treaty.TreatyException;
import net.java.treaty.VerificationReport;
import net.java.treaty.action.ActionVocabulary;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.Logger;

import org.osgi.framework.BundleException;

/**
 * <p>
 * Implements the {@link ActionVocabulary} interface and provides an action to
 * log verification results.
 * </p>
 * 
 * @author Claas Wilke
 */
public class BundleActionVocabulary implements ActionVocabulary {

	/** The name space's name of the {@link BundleActionVocabulary}. */
	public static final String NAME_SPACE_NAME =
			"http://www.treaty.org/action/bundle";

	/**
	 * The name of the action type representing an action to stop a consumer
	 * bundle of a violated {@link Contract}.
	 */
	public static final String ACTION_TYPE_STOP_CONSUMER_BUNDLE =
			NAME_SPACE_NAME + "#StopConsumerBundle";

	/**
	 * The name of the action type representing an action to stop a supplier
	 * bundle of a violated {@link Contract}.
	 */
	public static final String ACTION_TYPE_STOP_SUPPLIER_BUNDLE =
			NAME_SPACE_NAME + "#StopSupplierBundle";

	/**
	 * The name of the action type representing an action to uninstall a consumer
	 * bundle of a violated {@link Contract}.
	 */
	public static final String ACTION_TYPE_UNINSTALL_CONSUMER_BUNDLE =
			NAME_SPACE_NAME + "#UninstallConsumerBundle";

	/**
	 * The name of the action type representing an action to uninstall a supplier
	 * bundle of a violated {@link Contract}.
	 */
	public static final String ACTION_TYPE_UNINSTALL_SUPPLIER_BUNDLE =
			NAME_SPACE_NAME + "#UninstallSupplierBundle";

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
	public BundleActionVocabulary() {

		this.initialize();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#after(java.net.URI,
	 * java.net.URI, java.util.Set)
	 */
	public void after(URI triggerType, URI actionType,
			Set<VerificationReport> verificationReports) {

		/* Do nothing. */
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#before(java.net.URI,
	 * java.net.URI, java.util.Set)
	 */
	public void before(URI triggerType, URI actionType,
			Set<Contract> contractsToVerify) {

		/* Do nothing. */
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
	 * @see net.java.treaty.action.ActionVocabulary#getDescription(java.net.URI)
	 */
	public String getDescription(URI actionType) throws TreatyException {

		String result;

		if (this.actionTypes.get(ACTION_TYPE_STOP_CONSUMER_BUNDLE).equals(
				actionType)) {
			result = "Stops the consumer's bundle of the verified contract.";
		}

		else if (this.actionTypes.get(ACTION_TYPE_STOP_SUPPLIER_BUNDLE).equals(
				actionType)) {
			result = "Stops the supplier's bundle of the verified contract.";
		}

		else if (this.actionTypes.get(ACTION_TYPE_UNINSTALL_CONSUMER_BUNDLE)
				.equals(actionType)) {
			result = "Uninstalls the consumer's bundle of the verified contract.";
		}

		else if (this.actionTypes.get(ACTION_TYPE_UNINSTALL_SUPPLIER_BUNDLE)
				.equals(actionType)) {
			result = "Uninstalls the consumer's bundle of the verified contract.";
		}

		else {
			throw new TreatyException("Unknown action type " + actionType);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#isAfterAction(java.net.URI)
	 */
	public boolean isAfterAction(URI actionType) {

		/* This vocabulary does not define any after actions. */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#isBeforeAction(java.net.URI)
	 */
	public boolean isBeforeAction(URI actionType) {

		/* This vocabulary does not define any before actions. */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.action.ActionVocabulary#isDefaultOnFailure(java.net.URI)
	 */
	public boolean isDefaultOnFailure(URI actionType) {

		/* This vocabulary does not define any default actions. */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.action.ActionVocabulary#isDefaultOnSuccess(java.net.URI)
	 */
	public boolean isDefaultOnSuccess(URI actionType) {

		/* This vocabulary does not define any default actions. */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#perform(java.net.URI,
	 * java.net.URI, net.java.treaty.VerificationReport)
	 */
	public void perform(URI triggerType, URI actionType,
			VerificationReport verificationReport) {

		if (ACTION_TYPE_STOP_CONSUMER_BUNDLE.equals(actionType.toString())) {

			try {
				((EclipsePlugin) verificationReport.getContract().getConsumer()
						.getOwner()).getBundle().stop();
			}

			catch (NullPointerException e) {
				Logger.error("Unexpected NullPointerException during execution of "
						+ "BundleAction.", e);
			}

			catch (BundleException e) {
				Logger.error("Unexpected BundleException during execution of "
						+ "BundleAction.", e);
			}
		}

		else if (ACTION_TYPE_STOP_SUPPLIER_BUNDLE.equals(actionType.toString())) {

			try {
				((EclipsePlugin) verificationReport.getContract().getSupplier()
						.getOwner()).getBundle().stop();
			}

			catch (NullPointerException e) {
				Logger.error("Unexpected NullPointerException during execution of "
						+ "BundleAction.", e);
			}

			catch (BundleException e) {
				Logger.error("Unexpected BundleException during execution of "
						+ "BundleAction.", e);
			}
		}

		else if (ACTION_TYPE_UNINSTALL_CONSUMER_BUNDLE
				.equals(actionType.toString())) {

			try {
				((EclipsePlugin) verificationReport.getContract().getConsumer()
						.getOwner()).getBundle().uninstall();
			}

			catch (NullPointerException e) {
				Logger.error("Unexpected NullPointerException during execution of "
						+ "BundleAction.", e);
			}

			catch (BundleException e) {
				Logger.error("Unexpected BundleException during execution of "
						+ "BundleAction.", e);
			}
		}

		else if (ACTION_TYPE_UNINSTALL_SUPPLIER_BUNDLE
				.equals(actionType.toString())) {

			try {
				((EclipsePlugin) verificationReport.getContract().getSupplier()
						.getOwner()).getBundle().uninstall();
			}

			catch (NullPointerException e) {
				Logger.error("Unexpected NullPointerException during execution of "
						+ "BundleAction.", e);
			}

			catch (BundleException e) {
				Logger.error("Unexpected BundleException during execution of "
						+ "BundleAction.", e);
			}
		}
		// no else (wrong type of action).
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
	 * Initializes the {@link BundleActionVocabulary}.
	 * </p>
	 */
	private void initialize() {

		/* Probably initialize the action Types */
		if (this.actionTypes == null) {

			this.actionTypes = new HashMap<String, URI>();

			try {
				this.actionTypes.put(ACTION_TYPE_STOP_CONSUMER_BUNDLE, new URI(
						ACTION_TYPE_STOP_CONSUMER_BUNDLE));
				this.actionTypes.put(ACTION_TYPE_STOP_SUPPLIER_BUNDLE, new URI(
						ACTION_TYPE_STOP_SUPPLIER_BUNDLE));
				this.actionTypes.put(ACTION_TYPE_UNINSTALL_CONSUMER_BUNDLE, new URI(
						ACTION_TYPE_UNINSTALL_CONSUMER_BUNDLE));
				this.actionTypes.put(ACTION_TYPE_UNINSTALL_SUPPLIER_BUNDLE, new URI(
						ACTION_TYPE_UNINSTALL_SUPPLIER_BUNDLE));
			}

			catch (URISyntaxException e) {
				Logger.warn("Error during initialization of BundleActionVocabulary. "
						+ "Probably some action types are not available.", e);
			}
			// end catch.
		}
		// no else (already initialized).
	}
}