/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package test.net.java.treaty.vocabulary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.net.URI;
import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import net.java.treaty.vocabulary.CompositeContractOntology;
import net.java.treaty.vocabulary.builtins.java.JavaVocabulary;
import net.java.treaty.vocabulary.builtins.owl.OWLVocabulary;

/**
 * <p>
 * Tests for the class {@link CompositeContractOntology}. Also tests ontology
 * reasoning.
 * </p>
 * 
 * @author jens dietrich
 */
public class CompositeVocabularyTests {

	private CompositeContractOntology voc = null;

	@Before
	public void setUp() throws Exception {

		voc = new CompositeContractOntology();
		voc.add(new JavaVocabulary(), "builtin-java");
		voc.add(new OWLVocabulary(), "builtin-owl");
		voc.add(new TestVocabulary(), "test");
	}

	@After
	public void tearDown() throws Exception {

		voc = null;
	}

	/**
	 * <p>
	 * Test the method {@link CompositeContractOntology#getOwnerAnnotation(URI)}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Thrown, if the test case fails.
	 */
	@Test
	public void testOwnerAnnotations() throws Exception {

		assertEquals("builtin-java", voc.getOwnerAnnotation(new URI(
				JavaVocabulary.INSTANTIABLE_CLASS)));
		assertEquals("builtin-java", voc.getOwnerAnnotation(new URI(
				JavaVocabulary.IMPLEMENTS)));

		assertEquals("builtin-owl", voc.getOwnerAnnotation(new URI(
				OWLVocabulary.TYPE_NAME_ONTOLOGY)));

		assertEquals("test", voc.getOwnerAnnotation(new URI(
				"http://www.treaty.org/test#specialImplements")));
	}

	/**
	 * <p>
	 * A test case that tests that the {@link CompositeContractOntology} contains
	 * the types of the basis vocabularies.
	 * </p>
	 * 
	 * @throws Exception
	 *           Thrown, if the Test Case fails.
	 */
	@Test
	public void testTypes() throws Exception {

		Collection<URI> types = voc.getTypes();
		assertEquals(5, types.size());

		assertTrue(types.contains(new URI("http://www.treaty.org/java#JavaType")));
		assertTrue(types.contains(new URI(JavaVocabulary.ABSTRACT_TYPE)));
		assertTrue(types.contains(new URI(JavaVocabulary.INSTANTIABLE_CLASS)));
		assertTrue(types.contains(new URI(OWLVocabulary.TYPE_NAME_ONTOLOGY)));
		assertTrue(types.contains(new URI(
				"http://www.treaty.org/test#JavaSpecialClass")));
	}

	@Test
	public void testRelationships() throws Exception {

		Collection<URI> types = voc.getRelationships();
		assertEquals(2, types.size());
		assertTrue(types.contains(new URI("http://www.treaty.org/java#implements")));
		assertTrue(types.contains(new URI(
				"http://www.treaty.org/test#specialImplements")));
	}

	@Test
	public void testSubclasses() throws Exception {

		Collection<URI> types =
				voc.getSubtypes(new URI("http://www.treaty.org/java#JavaType"));
		assertEquals(3, types.size());
		assertTrue(types
				.contains(new URI("http://www.treaty.org/java#AbstractType")));
		assertTrue(types.contains(new URI(
				"http://www.treaty.org/java#InstantiableClass")));
		assertTrue(types.contains(new URI(
				"http://www.treaty.org/test#JavaSpecialClass")));
	}

	@Test
	public void testSuperclasses() throws Exception {

		Collection<URI> types =
				voc
						.getSupertypes(new URI(
								"http://www.treaty.org/test#JavaSpecialClass"));
		// list(types);
		assertEquals(4, types.size());
		assertTrue(types.contains(new URI(
				"http://www.treaty.org/java#InstantiableClass")));
		assertTrue(types.contains(new URI("http://www.treaty.org/java#JavaType")));
		assertTrue(types.contains(new URI("http://www.treaty.org#Resource")));
		assertTrue(types.contains(new URI(
				"http://www.w3.org/2000/01/rdf-schema#Resource")));
	}

	@Test
	public void testSubProperties() throws Exception {

		URI superProp = new URI("http://www.treaty.org/java#implements");
		URI subProp = new URI("http://www.treaty.org/test#specialImplements");

		list(voc.getSubProperties(superProp));

		assertTrue(voc.getSubProperties(superProp).contains(subProp));
		assertEquals(1, voc.getSubProperties(superProp).size());

		assertTrue(voc.getSuperProperties(subProp).contains(superProp));
		assertEquals(1, voc.getSuperProperties(subProp).size());

	}

	/**
	 * <p>
	 * A helper method for debugging that prints a given {@link List} of
	 * {@link URI}s on <code>System.out</code>.
	 * </p>
	 * 
	 * @param list
	 *          The {@link URI}s that shall be printed.
	 */
	private void list(Collection<URI> list) {

		for (URI u : list) {
			System.out.println(u);
		}
		// end for.
	}
}