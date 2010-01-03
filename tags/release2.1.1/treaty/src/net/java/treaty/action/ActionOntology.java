/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.action;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.java.treaty.TreatyException;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;

/**
 * <p>
 * An implementation of the {@link ActionVocabulary} interface based on an OWL
 * ontology file.
 * </p>
 * 
 * @author Claas Wilke
 */
public abstract class ActionOntology implements ActionVocabulary {

	/** The label used in Ontology files to label after actions. */
	private static final String LABEL_AFTER_ACTION = "after";

	/** The label used in Ontology files to label before actions. */
	private static final String LABEL_BEFORE_ACTION = "before";

	/** The label used in Ontology files to label default on failure actions. */
	private static final String LABEL_DEFAULT_ON_FAILURE = "defaultOnFailure";

	/** The label used in Ontology files to label default on success actions. */
	private static final String LABEL_DEFAULT_ON_SUCCESS = "defaultOnSuccess";

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#getActions()
	 */
	public Set<URI> getActions() throws TreatyException {

		Set<URI> result;
		result = new HashSet<URI>();

		OntModel model;
		model = getOntology();

		for (Iterator<OntClass> iterator = model.listClasses(); iterator.hasNext();) {

			try {
				result.add(new URI(iterator.next().getURI()));
			} catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
			// end catch.
		}
		// end for.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#getDescription(java.net.URI)
	 */
	public String getDescription(URI actionType) throws TreatyException {

		String result;

		OntClass triggerClass;
		triggerClass = this.getOntology().getOntClass(actionType.toString());

		if (triggerClass == null) {

			throw new TreatyException("Action type " + actionType
					+ " cannot be found.");
		}

		else {

			/* Parameter can be null. Don't care about language. */
			result = triggerClass.getComment(null);
		}

		if (result == null) {
			result = "";
		}
		// no else.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#isAfterAction(java.net.URI)
	 */
	public boolean isAfterAction(URI actionType) throws TreatyException {

		boolean result;

		OntClass triggerClass;
		triggerClass = this.getOntology().getOntClass(actionType.toString());

		if (triggerClass != null) {

			result = triggerClass.hasLabel(LABEL_AFTER_ACTION, null);
		}

		else {
			throw new TreatyException("Unknown action type " + actionType);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#isBeforeAction(java.net.URI)
	 */
	public boolean isBeforeAction(URI actionType) throws TreatyException {

		boolean result;

		OntClass triggerClass;
		triggerClass = this.getOntology().getOntClass(actionType.toString());

		if (triggerClass != null) {

			result = triggerClass.hasLabel(LABEL_BEFORE_ACTION, null);
		}

		else {
			throw new TreatyException("Unknown action type " + actionType);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.action.ActionVocabulary#isDefaultOnFailure(java.net.URI)
	 */
	public boolean isDefaultOnFailure(URI actionType) throws TreatyException {

		boolean result;

		OntClass triggerClass;
		triggerClass = this.getOntology().getOntClass(actionType.toString());

		if (triggerClass != null) {

			result = triggerClass.hasLabel(LABEL_DEFAULT_ON_FAILURE, null);
		}

		else {
			throw new TreatyException("Unknown action type " + actionType);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.action.ActionVocabulary#isDefaultOnSuccess(java.net.URI)
	 */
	public boolean isDefaultOnSuccess(URI actionType) throws TreatyException {

		boolean result;

		OntClass triggerClass;
		triggerClass = this.getOntology().getOntClass(actionType.toString());

		if (triggerClass != null) {

			result = triggerClass.hasLabel(LABEL_DEFAULT_ON_SUCCESS, null);
		}

		else {
			throw new TreatyException("Unknown action type " + actionType);
		}

		return result;
	}

	/**
	 * <p>
	 * Returns an {@link OntModel} representing this {@link ActionVocabulary}.
	 * This can be used for ontology reasoning other than subclass reasoning.
	 * </p>
	 */
	public abstract OntModel getOntology();
}