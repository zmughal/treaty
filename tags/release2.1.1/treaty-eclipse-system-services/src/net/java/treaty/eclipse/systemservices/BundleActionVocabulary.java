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
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.VerificationReport;
import net.java.treaty.action.ActionOntology;
import net.java.treaty.action.ActionVocabulary;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.Logger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 * <p>
 * Implements the {@link ActionVocabulary} interface and provides an action to
 * log verification results.
 * </p>
 * 
 * @author Claas Wilke
 */
public class BundleActionVocabulary extends ActionOntology {

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

	/** The location of this {@link ActionOntology}'s ontology. */
	private static final String ONTOLOGY_LOCATION = "vocabulary/bundleAction.owl";

	/** The {@link OntModel} of this {@link ActionOntology}. */
	private OntModel myOntology = null;

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
	 * @see net.java.treaty.action.ActionOntology#getOntology()
	 */
	public OntModel getOntology() {

		/* Probably load the ontology. */
		if (this.myOntology == null) {
			Bundle myBundle;
			myBundle = Activator.getDefault().getBundle();

			this.myOntology = ModelFactory.createOntologyModel();
			this.myOntology.read(myBundle.getResource(ONTOLOGY_LOCATION).toString());
		}
		// no else.

		return this.myOntology;
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
}