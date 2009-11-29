/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package test.net.java.treaty.vocabulary.builtins.java;

import static org.junit.Assert.*;
import java.net.URI;
import java.util.Collection;

import net.java.treaty.vocabulary.builtins.java.JavaVocabulary;

import org.junit.Test;

/**
 * Tests for Java vocabulary.
 * @author jens dietrich
 */
public class JavaVocabularyTests {
	@Test
	public void testTypes() throws Exception {
		JavaVocabulary jv = new JavaVocabulary();
		Collection<URI> types = jv.getTypes();
		assertEquals(3,types.size());
		assertTrue(types.contains(new URI("http://www.treaty.org/java#JavaType")));
		assertTrue(types.contains(new URI("http://www.treaty.org/java#AbstractType")));
		assertTrue(types.contains(new URI("http://www.treaty.org/java#InstantiableClass")));
	}
	
	@Test
	public void testRelationships() throws Exception {
		JavaVocabulary jv = new JavaVocabulary();
		Collection<URI> types = jv.getRelationships();
		assertEquals(1,types.size());
		assertTrue(types.contains(new URI("http://www.treaty.org/java#implements")));
	}
	
	@Test
	public void testProperties() throws Exception {
		JavaVocabulary jv = new JavaVocabulary();
		Collection<URI> types = jv.getProperties();
		assertEquals(0,types.size());
	}
	
	@Test
	public void testSubclasses() throws Exception {
		JavaVocabulary jv = new JavaVocabulary();
		Collection<URI> types = jv.getSubTypes(new URI("http://www.treaty.org/java#JavaType"));
		assertEquals(2,types.size());
		assertTrue(types.contains(new URI("http://www.treaty.org/java#AbstractType")));
		assertTrue(types.contains(new URI("http://www.treaty.org/java#InstantiableClass")));
	}
	
	@Test
	public void testSuperclasses() throws Exception {
		JavaVocabulary jv = new JavaVocabulary();
		Collection<URI> types = jv.getSuperTypes(new URI("http://www.treaty.org/java#AbstractType"));
		//list(types);
		assertEquals(3,types.size());
		assertTrue(types.contains(new URI("http://www.treaty.org/java#JavaType")));
		assertTrue(types.contains(new URI("http://www.treaty.org#Resource")));
		assertTrue(types.contains(new URI("http://www.w3.org/2000/01/rdf-schema#Resource")));
	}
	
	@Test
	public void testDomain() throws Exception {
		JavaVocabulary jv = new JavaVocabulary();
		URI domain = jv.getDomain(new URI("http://www.treaty.org/java#implements"));
		assertEquals(new URI("http://www.treaty.org/java#InstantiableClass"),domain);
	}
	
	@Test
	public void testRange() throws Exception {
		JavaVocabulary jv = new JavaVocabulary();
		URI range = jv.getRange(new URI("http://www.treaty.org/java#implements"));
		assertEquals(new URI("http://www.treaty.org/java#AbstractType"),range);
	}
	// for debugging 
	private void list(Collection<URI> list) {
		for (URI u:list) System.out.println(u);
	}
}
