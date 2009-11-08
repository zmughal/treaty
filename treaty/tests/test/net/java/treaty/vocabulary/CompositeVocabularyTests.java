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
import net.java.treaty.vocabulary.CompositeContractOntology;
import net.java.treaty.vocabulary.builtins.java.JavaVocabulary;
import net.java.treaty.vocabulary.builtins.owl.OWLVocabulary;

/**
 * Tests for composite vocabulary.
 * Also tests ontology reasoning.
 * @author jens dietrich
 */
public class CompositeVocabularyTests {

	private CompositeContractOntology voc = null;
	@Before
	public void setUp() throws Exception {
		voc = new CompositeContractOntology();
		voc.add(new JavaVocabulary(),"builtin-java");
		voc.add(new OWLVocabulary(),"builtin-owl");
		voc.add(new TestVocabulary(),"test");
	}
	@After 
	public void tearDown() throws Exception {
		voc = null;
	}	
	@Test
	public void testTypes() throws Exception {
		Collection<URI> types = voc.getTypes();
		assertEquals(5,types.size());
		assertTrue(types.contains(new URI("http://www.treaty.org/java#JavaType")));
		assertTrue(types.contains(new URI("http://www.treaty.org/java#AbstractType")));
		assertTrue(types.contains(new URI("http://www.treaty.org/java#InstantiableClass")));
		assertTrue(types.contains(new URI("http://www.treaty.org/owl#OWLType")));
		assertTrue(types.contains(new URI("http://www.treaty.org/test#JavaSpecialClass")));
	}
	
	@Test
	public void testRelationships() throws Exception {
		Collection<URI> types = voc.getRelationships();
		assertEquals(2,types.size());
		assertTrue(types.contains(new URI("http://www.treaty.org/java#implements")));
		assertTrue(types.contains(new URI("http://www.treaty.org/test#specialImplements")));
	}
	
	@Test
	public void testSubclasses() throws Exception {
		Collection<URI> types = voc.getSubtypes(new URI("http://www.treaty.org/java#JavaType"));
		assertEquals(3,types.size());
		assertTrue(types.contains(new URI("http://www.treaty.org/java#AbstractType")));
		assertTrue(types.contains(new URI("http://www.treaty.org/java#InstantiableClass")));
		assertTrue(types.contains(new URI("http://www.treaty.org/test#JavaSpecialClass")));
	}
	
	@Test
	public void testSuperclasses() throws Exception {
		Collection<URI> types = voc.getSupertypes(new URI("http://www.treaty.org/test#JavaSpecialClass"));
		//list(types);
		assertEquals(4,types.size());
		assertTrue(types.contains(new URI("http://www.treaty.org/java#InstantiableClass")));
		assertTrue(types.contains(new URI("http://www.treaty.org/java#JavaType")));
		assertTrue(types.contains(new URI("http://www.treaty.org#Resource")));
		assertTrue(types.contains(new URI("http://www.w3.org/2000/01/rdf-schema#Resource")));
	}
	
	@Test
	public void testSubProperties() throws Exception {
		URI superProp = new URI("http://www.treaty.org/java#implements");
		URI subProp = new URI("http://www.treaty.org/test#specialImplements");
		
		list(voc.getSubProperties(superProp));
		
		assertTrue(voc.getSubProperties(superProp).contains(subProp));
		assertEquals(1,voc.getSubProperties(superProp).size());
		
		assertTrue(voc.getSuperProperties(subProp).contains(superProp));
		assertEquals(1,voc.getSuperProperties(subProp).size());

	}
	
	@Test
	public void testOwnerAnnotations () throws Exception {
		assertEquals("builtin-java",voc.getOwnerAnnotation(new URI("http://www.treaty.org/java#InstantiableClass")));
		assertEquals("builtin-java",voc.getOwnerAnnotation(new URI("http://www.treaty.org/java#implements")));
		assertEquals("builtin-java",voc.getOwnerAnnotation(new URI("http://www.treaty.org/owl#OWLType")));
		assertEquals("builtin-java",voc.getOwnerAnnotation(new URI("http://www.treaty.org/test#specialImplements")));
	}
	
	// for debugging 
	private void list(Collection<URI> list) {
		for (URI u:list) System.out.println(u);
	}
}
