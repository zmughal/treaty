/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package test.net.java.treaty.trigger;

import net.java.treaty.trigger.TriggerOntology;
import net.java.treaty.trigger.TriggerVocabulary;
import net.java.treaty.vocabulary.builtins.java.JavaVocabulary;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class TriggerOntologyMock extends TriggerOntology {

	/** The name space of this {@link TriggerVocabulary}. */
	public static final String NAME_SPACE = "http://www.treaty.org/trigger/mock";

	/** The name of a test trigger. */
	public static final String NAME_SUPER_TRIGGER = NAME_SPACE + "#SuperTrigger";

	/** The name of a test trigger. */
	public static final String NAME_SUB_TRIGGER = NAME_SPACE + "#SubTrigger";

	/** The name of a test trigger. */
	public static final String NAME_DEFAULT_TRIGGER =
			NAME_SPACE + "#DefaultTrigger";

	/**
	 * The name of a test trigger that <strong>IS NOT</strong> defined by this
	 * {@link TriggerOntology}.
	 */
	public static final String NAME_UNDEFINED_TRIGGER = NAME_SPACE + "#Undefined";

	/** The location of this {@link TriggerOntology}'s ontology. */
	private static final String ONTOLOGY_LOCATION =
			"/test/net/java/treaty/trigger/mock.owl";

	/** The {@link OntModel} of the {@link JavaVocabulary}. */
	private OntModel myOntology = null;

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.trigger.TriggerOntology#getOntology()
	 */
	public OntModel getOntology() {

		/* Probably initialize the ontology. */
		if (this.myOntology == null) {
			this.myOntology = ModelFactory.createOntologyModel();
			this.myOntology.read(TriggerOntologyMock.class.getResource(
					ONTOLOGY_LOCATION).toString());
		}
		// no else.

		return this.myOntology;
	}
}