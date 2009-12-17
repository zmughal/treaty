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
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.VerificationReport;
import net.java.treaty.action.ActionOntology;
import net.java.treaty.eclipse.Logger;
import net.java.treaty.eclipse.ui.Activator;
import net.java.treaty.eclipse.views.ContractView;

import org.osgi.framework.Bundle;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class UIActionVocabulary extends ActionOntology {

	/** The name space's name of the {@link UIActionVocabulary}. */
	public static final String NAME_SPACE_NAME =
			"http://www.treaty.org/action/ui";

	/**
	 * The name of the action type representing an action to display a
	 * verification result.
	 */
	public static final String ACTION_TYPE_SHOW_VERIFICATION_RESULT =
			NAME_SPACE_NAME + "#ShowResult";

	/** The location of this {@link ActionOntology}'s ontology. */
	private static final String ONTOLOGY_LOCATION = "vocabulary/ui.owl";

	/**
	 * The {@link ContractView} on which the actions of this
	 * {@link UIActionVocabulary} shall be performed.
	 */
	private ContractView contractView;

	/** The {@link OntModel} of this {@link ActionOntology}. */
	private OntModel myOntology = null;

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
	 * @see net.java.treaty.action.ActionVocabulary#after(java.net.URI,
	 * java.net.URI, java.util.Set)
	 */
	public void after(URI triggerType, URI actionType,
			Set<VerificationReport> verificationReports) {

		if (ACTION_TYPE_SHOW_VERIFICATION_RESULT.equals(actionType.toString())) {

			if (this.contractView != null) {

				contractView.update();
				contractView.reportVerificationResult(verificationReports, triggerType);
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
}