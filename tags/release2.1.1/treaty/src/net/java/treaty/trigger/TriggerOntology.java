/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.trigger;

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
 * An implementation of the {@link TriggerVocabulary} interface based on an OWL
 * ontology file.
 * </p>
 * 
 * @author Claas Wilke
 */
public abstract class TriggerOntology extends AbstractTriggerVocabulary {

	/** The label used in Ontology files to label default triggers. */
	private static final String LABEL_DEFAULT_TRIGGER = "default";

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.trigger.TriggerVocabulary#getDescription(java.net.URI)
	 */
	public String getDescription(URI triggerType) throws TreatyException {

		String result;

		OntClass triggerClass;
		triggerClass = this.getOntology().getOntClass(triggerType.toString());

		if (triggerClass == null) {

			throw new TreatyException("Trigger type " + triggerType
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
	 * @see net.java.treaty.trigger.TriggerVocabulary#getSubTriggers(java.net.URI)
	 */
	public Set<URI> getSubTriggers(URI triggerType) throws TreatyException {

		Set<URI> result;
		result = new HashSet<URI>();

		OntModel model;
		model = getOntology();

		OntClass clazz;
		clazz = model.getOntClass(triggerType.toString());

		for (Iterator<OntClass> iterator = clazz.listSubClasses(false); iterator
				.hasNext();) {

			try {
				result.add(new URI(iterator.next().getURI()));
			}

			catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
			// end catch.
		}
		// end for.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.trigger.TriggerVocabulary#getSuperTriggers(java.net.URI)
	 */
	public Set<URI> getSuperTriggers(URI triggerType) throws TreatyException {

		Set<URI> result;
		result = new HashSet<URI>();

		OntModel model;
		model = getOntology();

		OntClass clazz;
		clazz = model.getOntClass(triggerType.toString());

		for (Iterator<OntClass> iterator = clazz.listSuperClasses(false); iterator
				.hasNext();) {

			try {
				result.add(new URI(iterator.next().getURI()));
			}

			catch (URISyntaxException e) {
				throw new TreatyException(e);
			}
			// end catch.
		}
		// end for.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.trigger.TriggerVocabulary#getTriggers()
	 */
	public Set<URI> getTriggers() throws TreatyException {

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
	 * @see
	 * net.java.treaty.trigger.TriggerVocabulary#isDefaultTrigger(java.net.URI)
	 */
	public boolean isDefaultTrigger(URI triggerType) throws TreatyException {

		boolean result;

		OntClass triggerClass;
		triggerClass = this.getOntology().getOntClass(triggerType.toString());

		if (triggerClass != null) {

			result = triggerClass.hasLabel(LABEL_DEFAULT_TRIGGER, null);
		}

		else {
			throw new TreatyException("Unknown trigger type " + triggerType);
		}

		return result;
	}

	/**
	 * <p>
	 * Returns an {@link OntModel} representing this {@link TriggerVocabulary}.
	 * This can be used for ontology reasoning other than subclass reasoning.
	 * </p>
	 */
	public abstract OntModel getOntology();
}