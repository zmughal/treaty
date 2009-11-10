/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import net.java.treaty.ContractVocabulary;
import net.java.treaty.TreatyException;
import net.java.treaty.vocabulary.CompositeContractOntology;
import net.java.treaty.vocabulary.ContractOntology;
import net.java.treaty.vocabulary.builtins.BasicOpVocabulary;
import net.java.treaty.vocabulary.builtins.java.JavaVocabulary;
import net.java.treaty.vocabulary.builtins.owl.OWLVocabulary;

import org.eclipse.core.runtime.ContributorFactoryOSGi;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.InvalidRegistryObjectException;

/**
 * <p>
 * Registry for contract vocabularies. Vocabulary contributions from different
 * plug-ins are aggregated here.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class VocabularyRegistry extends CompositeContractOntology implements
		IRegistryEventListener {

	/** The singleton instance of the {@link VocabularyRegistry}. */
	public static VocabularyRegistry INSTANCE = new VocabularyRegistry();

	/**
	 * Contains all aready added external {@link ContractVocabulary}s identified
	 * by the ID of the {@link IExtension}s providing them.
	 */
	private Map<String, ContractVocabulary> externalVocabularies =
			new HashMap<String, ContractVocabulary>();

	/**
	 * <p>
	 * Creates a new {@link VocabularyRegistry}. Private constructor for singleton
	 * pattern.
	 * </p>
	 */
	private VocabularyRegistry() {

		super();

		try {
			this.add(new BasicOpVocabulary(), "net.java.treaty");
			this.add(new JavaVocabulary(), "net.java.treaty");
			this.add(new OWLVocabulary(), "net.java.treaty");
		}

		catch (TreatyException x) {
			Logger.error("cannot install bootstrap vocabularies");
		}

		org.eclipse.core.runtime.Platform.getExtensionRegistry().addListener(this,
				Constants.VOCABULARY_EXTENSION_POINT_ID);

		/* All extensions that have already been found should be added as well. */
		this.initialize();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.
	 * runtime.IExtension[])
	 */
	public void added(IExtension[] extensions) {

		/* Check if any of the extensions provide a new vocabulary. */
		for (IExtension extension : extensions) {

			if (extension.getExtensionPointUniqueIdentifier().equals(
					Constants.VOCABULARY_EXTENSION_POINT_ID)) {

				this.addVocabulary(extension);
			}
			// no else.
		}
		// end for.
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.
	 * runtime.IExtensionPoint[])
	 */
	public void added(IExtensionPoint[] extensionPoints) {

		/* The VocabularyRegistry is not interested in IExtensionPoints. */
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core
	 * .runtime.IExtension[])
	 */
	public void removed(IExtension[] extensions) {

		/* Check if any of the extensions provide a vocabulary. */
		for (IExtension extension : extensions) {

			if (extension.getExtensionPointUniqueIdentifier().equals(
					Constants.VOCABULARY_EXTENSION_POINT_ID)) {

				this.removeVocabulary(extension);
			}
			// no else.
		}
		// end for.
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core
	 * .runtime.IExtensionPoint[])
	 */
	public void removed(IExtensionPoint[] extensionPoints) {

		/* The VocabularyRegistry is not interested in IExtensionPoints. */
	}

	/**
	 * <p>
	 * This method can be called when this plug-in shall be de-activated.
	 * Unregisters the {@link VocabularyRegistry} as listener of the
	 * ExtensionRegistry.
	 * </p>
	 */
	public void tearDown() {
	
		org.eclipse.core.runtime.Platform.getExtensionRegistry().removeListener(
				this);
	}

	/**
	 * <p>
	 * Overridden, super method throws exception. This implementation just logs a
	 * warning.
	 * </p>
	 */
	@Override
	protected void reportDuplicateDef(String kind, URI resource,
			ContractVocabulary voc, ContractVocabulary part) throws TreatyException {

		StringBuffer buffer;

		buffer = new StringBuffer();
		buffer.append("Attempt to redefine ");
		buffer.append(kind);
		buffer.append(" ");
		buffer.append(resource);
		buffer.append(" in ");
		buffer.append(voc);
		buffer.append(" - this is already defined in ");
		buffer.append(part);

		Logger.warn(buffer.toString());
	}

	/**
	 * <p>
	 * A helper method to be called to add a new {@link ContractVocabulary}
	 * provided by a given {@link IExtension}.
	 * </p>
	 * 
	 * @param extension
	 *          The {@link IExtension} that provides the
	 *          {@link ContractVocabulary}.
	 */
	private void addVocabulary(IExtension extension) {

		if (!this.externalVocabularies.containsKey(extension.getUniqueIdentifier())) {
			/* Search for the class attribute to load the vocabulary. */
			for (IConfigurationElement configurationElement : extension
					.getConfigurationElements()) {

				configurationElement.getAttributeNames();

				String className;
				className =
						configurationElement
								.getAttribute(Constants.VOCABULARY_EXTENSION_POINT_CLASS_ATTRIBUTE);

				if (className != null) {

					/* Try to load the class. */
					try {
						Class<?> vocabularyClass;
						Constructor<?> vocabularyConstructor;
						ContractVocabulary vocabulary;

						vocabularyClass =
								ContributorFactoryOSGi.resolve(extension.getContributor())
										.loadClass(className);

						vocabularyConstructor =
								vocabularyClass.getConstructor(new Class[0]);
						vocabulary =
								(ContractVocabulary) vocabularyConstructor
										.newInstance(new Object[0]);

						/* Add the vocabulary. */
						this.add(vocabulary, ContributorFactoryOSGi.resolve(
								extension.getContributor()).getSymbolicName());

						/* Add the vocabulary to the list of external vocabularies. */
						this.externalVocabularies.put(extension.getUniqueIdentifier(),
								vocabulary);

						/* Log an info. */
						Logger.info("Added new Vocabulary: " + vocabulary);
					}

					catch (InvalidRegistryObjectException e) {

						Logger.error(
								"Error during adding Vocabulary to VocabularyRegistry.", e);
					}

					catch (ClassNotFoundException e) {

						Logger.error(
								"Error during adding Vocabulary to VocabularyRegistry.", e);
					}

					catch (SecurityException e) {

						Logger.error(
								"Error during adding Vocabulary to VocabularyRegistry.", e);
					}

					catch (NoSuchMethodException e) {

						Logger.error(
								"Error during adding Vocabulary to VocabularyRegistry.", e);
					}

					catch (IllegalArgumentException e) {

						Logger.error(
								"Error during adding Vocabulary to VocabularyRegistry.", e);
					}

					catch (InstantiationException e) {

						Logger.error(
								"Error during adding Vocabulary to VocabularyRegistry.", e);
					}

					catch (IllegalAccessException e) {

						Logger.error(
								"Error during adding Vocabulary to VocabularyRegistry.", e);
					}

					catch (InvocationTargetException e) {

						Logger.error(
								"Error during adding Vocabulary to VocabularyRegistry.", e);
					}

					catch (TreatyException e) {

						Logger.error(
								"Error during adding Vocabulary to VocabularyRegistry.", e);
					}
					// end catch.
				}
				// no else (className is null).
			}
			// end for (iteration on configuration elements).
		}
		// no else (vocabulary already added).
	}

	/**
	 * <p>
	 * Initializes the {@link VocabularyRegistry} by searching for provided
	 * {@link ContractVocabulary}s in the ExtensionRegistry.
	 * </p>
	 */
	private void initialize() {

		for (IExtension extension : org.eclipse.core.runtime.Platform
				.getExtensionRegistry().getExtensionPoint(
						Constants.VOCABULARY_EXTENSION_POINT_ID).getExtensions()) {

			this.addVocabulary(extension);
		}
		// no else.
	}

	/**
	 * <p>
	 * A helper method to be called to remove a {@link ContractVocabulary}
	 * provided by a given {@link IExtension}.
	 * </p>
	 * 
	 * @param extension
	 *          The {@link IExtension} that provides the
	 *          {@link ContractVocabulary}.
	 */
	private void removeVocabulary(IExtension extension) {

		/* Check if the vocabulary must be removed at all. */
		if (this.externalVocabularies.containsKey(extension.getUniqueIdentifier())) {

			ContractVocabulary vocabulary;

			/*
			 * FIXME Claas: Currently this cast is not type safe. Nevertheless, all
			 * existing implementations extend ContractOntology. Should'nt we enforce
			 * this constraint for all vocabularies?
			 */
			/* Remove the vocabulary. */
			try {
				this.remove((ContractOntology) this.externalVocabularies.get(extension
						.getUniqueIdentifier()));
			}

			catch (InvalidRegistryObjectException e) {

				Logger.error(
						"Error during removing Vocabulary from VocabularyRegistry.", e);
			}

			catch (TreatyException e) {

				Logger.error(
						"Error during removing Vocabulary from VocabularyRegistry.", e);
			}

			/* Add the vocabulary to the list of external vocabularies. */
			vocabulary =
					this.externalVocabularies.remove(extension.getUniqueIdentifier());

			/* Log an info. */
			Logger.info("Removed Vocabulary: " + vocabulary);
		}
		// no else.
	}
}