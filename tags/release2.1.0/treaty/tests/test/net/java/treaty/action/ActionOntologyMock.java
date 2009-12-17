/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package test.net.java.treaty.action;

import java.net.URI;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.TreatyException;
import net.java.treaty.VerificationReport;
import net.java.treaty.action.ActionOntology;
import net.java.treaty.action.ActionVocabulary;
import net.java.treaty.trigger.TriggerOntology;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class ActionOntologyMock extends ActionOntology {

	/** The name space of this {@link ActionVocabulary}. */
	public static final String NAME_SPACE = "http://www.treaty.org/action/mock";

	/** The name of a test action. */
	public static final String NAME_AFTER_ACTION = NAME_SPACE + "#AfterAction";

	/** The name of a test action. */
	public static final String NAME_BEFORE_ACTION = NAME_SPACE + "#BeforeAction";

	/** The name of a test action. */
	public static final String NAME_NO_DEFAULT_ACTION =
			NAME_SPACE + "#NoDefaultAction";

	/** The name of a test action. */
	public static final String NAME_DEFAULT_ON_ALL_ACTION =
			NAME_SPACE + "#DefaultOnAllAction";

	/** The name of a test action. */
	public static final String NAME_ON_FAILURE_ACTION =
			NAME_SPACE + "#OnFailureAction";

	/** The name of a test action. */
	public static final String NAME_ON_SUCCESS_ACTION =
			NAME_SPACE + "#OnSuccessAction";

	/**
	 * The name of a test action that <strong>IS NOT</strong> defined by this
	 * {@link ActionOntology}.
	 */
	public static final String NAME_UNDEFINED_ACTION =
			NAME_SPACE + "#UndefinedAction";

	/** The location of this {@link TriggerOntology}'s ontology. */
	private static final String ONTOLOGY_LOCATION =
			"/test/net/java/treaty/action/mock.owl";

	/** The {@link OntModel} of this {@link ActionOntology}. */
	private OntModel myOntology = null;

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#after(java.net.URI,
	 * java.net.URI, java.util.Set)
	 */
	public void after(URI triggerType, URI actionType,
			Set<VerificationReport> verificationReports) throws TreatyException {

		/* Do nothing. */
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#before(java.net.URI,
	 * java.net.URI, java.util.Set)
	 */
	public void before(URI triggerType, URI actionType,
			Set<Contract> contractsToVerify) throws TreatyException {

		/* Do nothing. */
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionOntology#getOntology()
	 */
	public OntModel getOntology() {

		/* Probably initialize the ontology. */
		if (this.myOntology == null) {
			this.myOntology = ModelFactory.createOntologyModel();
			this.myOntology.read(ActionOntologyMock.class.getResource(
					ONTOLOGY_LOCATION).toString());
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
			VerificationReport verificationReport) throws TreatyException {

		/* Do nothing. */
	}
}