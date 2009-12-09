/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.ui.triggers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.eclipse.Logger;
import net.java.treaty.eclipse.contractregistry.EclipseContractRegistry;
import net.java.treaty.eclipse.trigger.AbstractEclipseTriggerVocabulary;
import net.java.treaty.eclipse.ui.Activator;
import net.java.treaty.trigger.TriggerOntology;

import org.osgi.framework.Bundle;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 * <p>
 * The {@link ManualTriggerVocabulary} provides a trigger to manually verify all
 * {@link Contract}s.
 * </p>
 * 
 * @author Claas Wilke
 */
public class ManualTriggerVocabulary extends AbstractEclipseTriggerVocabulary {

	/** The name space's name of the {@link ManualTriggerVocabulary}. */
	public static final String NAME_SPACE_NAME =
			"http://www.treaty.org/trigger/manual";

	/**
	 * The name of the trigger type representing the manual verification of all
	 * {@link Contract}s.
	 */
	public static final String TRIGGER_TYPE_VERIFY_ALL =
			NAME_SPACE_NAME + "#VerifyAll";

	/**
	 * The name of the trigger type representing the manual verification of a
	 * {@link Set} of selected {@link Contract}s.
	 */
	public static final String TRIGGER_TYPE_VERIFY_SELECTED =
			NAME_SPACE_NAME + "#VerifySelected";

	/** The location of this {@link TriggerOntology}'s ontology. */
	private static final String ONTOLOGY_LOCATION = "vocabulary/manual.owl";

	/** The {@link OntModel} of this {@link TriggerOntology}. */
	private OntModel myOntology = null;

	/**
	 * <p>
	 * Creates a new {@link ManualTriggerVocabulary}.
	 * </p>
	 */
	public ManualTriggerVocabulary() {

		super();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.trigger.TriggerOntology#getOntology()
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
	 * @see
	 * net.java.treaty.trigger.TriggerVocabulary#isDefaultTrigger(java.net.URI)
	 */
	public boolean isDefaultTrigger(URI triggerType) {

		boolean result;

		/* All triggers of this vocabulary are default triggers. */
		result = TRIGGER_TYPE_VERIFY_ALL.equals(triggerType.toString());
		result |= TRIGGER_TYPE_VERIFY_SELECTED.equals(triggerType.toString());

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.eclipse.trigger.AbstractEclipseTriggerVocabulary#tearDown()
	 */
	public void tearDown() {

		/* Do nothing. No listeners must be removed. */
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
	 * Fires a new trigger event that triggers the verification of all
	 * {@link Contract}s registered at the {@link EclipseContractRegistry}.
	 * </p>
	 */
	public void fireTriggerVerifyAll() {

		Set<Contract> contracts;
		contracts =
				new HashSet<Contract>(EclipseContractRegistry.getInstance()
						.getInstantiatedContracts());

		try {
			this.notifyEventListners(new URI(TRIGGER_TYPE_VERIFY_ALL), contracts);
		}

		catch (URISyntaxException e) {
			Logger.error("Unexpected Exception during fire Verify_All trigger.", e);
		}
	}

	/**
	 * <p>
	 * Fires a new trigger event that triggers the verification of a {@link Set}
	 * of selected {@link Contract}s.
	 * </p>
	 * 
	 * @param contracts
	 *          The {@link Contract}s that shall be verified.
	 */
	public void fireTriggerVerifySelected(Set<Contract> contracts) {

		try {
			this
					.notifyEventListners(new URI(TRIGGER_TYPE_VERIFY_SELECTED), contracts);
		}

		catch (URISyntaxException e) {
			Logger.error("Unexpected Exception during fire Verify_All trigger.", e);
		}
	}
}