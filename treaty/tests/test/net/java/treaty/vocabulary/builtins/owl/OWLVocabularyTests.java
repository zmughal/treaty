/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package test.net.java.treaty.vocabulary.builtins.owl;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.Collection;
import net.java.treaty.vocabulary.builtins.owl.OWLVocabulary;
import org.junit.Test;

/**
 * <p>
 * Tests for the {@link OWLVocabulary}.
 * </p>
 * 
 * @author jens dietrich
 */
public class OWLVocabularyTests {

	/**
	 * <p>
	 * Tests the method {@link OWLVocabulary#getTypes()}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Thrown, if the test case fails.
	 */
	@Test
	public void testTypes() throws Exception {

		OWLVocabulary vocabulary;
		vocabulary = new OWLVocabulary();

		Collection<URI> types;
		types = vocabulary.getTypes();

		assertNotNull(types);
		assertEquals(1, types.size());
		assertTrue(types.contains(new URI(OWLVocabulary.TYPE_NAME_ONTOLOGY)));
	}

	/**
	 * <p>
	 * Tests the method {@link OWLVocabulary#getSubtypes(URI)}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Thrown, if the test case fails.
	 */
	@Test
	public void testGetSubtypes() throws Exception {

		OWLVocabulary vocabulary;
		vocabulary = new OWLVocabulary();

		Collection<URI> types;
		types = vocabulary.getSubtypes(new URI(OWLVocabulary.TYPE_NAME_ONTOLOGY));

		assertNotNull(types);
		assertEquals(0, types.size());
	}

	/**
	 * <p>
	 * Tests the method {@link OWLVocabulary#getSupertypes(URI)}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Thrown, if the test case fails.
	 */
	@Test
	public void testGetSupertypes() throws Exception {

		OWLVocabulary vocabulary;
		vocabulary = new OWLVocabulary();

		Collection<URI> types;
		types = vocabulary.getSupertypes(new URI(OWLVocabulary.TYPE_NAME_ONTOLOGY));

		assertNotNull(types);
		assertTrue(types.contains(new URI("http://www.treaty.org#Resource")));
	}

	/**
	 * <p>
	 * Tests the method {@link OWLVocabulary#getProperties()}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Thrown, if the test case fails.
	 */
	@Test
	public void testProperties() throws Exception {

		OWLVocabulary vocabulary;
		vocabulary = new OWLVocabulary();

		Collection<URI> types;
		types = vocabulary.getProperties();

		assertNotNull(types);
		assertEquals(0, types.size());
	}

	/**
	 * <p>
	 * Tests the method {@link OWLVocabulary#getRelationships()}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Thrown, if the test case fails.
	 */
	@Test
	public void testRelationships() throws Exception {

		OWLVocabulary vocabulary;
		vocabulary = new OWLVocabulary();

		Collection<URI> types;
		types = vocabulary.getRelationships();

		assertNotNull(types);
		assertEquals(0, types.size());
	}
}