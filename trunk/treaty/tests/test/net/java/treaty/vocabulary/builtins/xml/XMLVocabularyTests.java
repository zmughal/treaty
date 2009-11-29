/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package test.net.java.treaty.vocabulary.builtins.xml;

import static org.junit.Assert.*;
import java.net.URI;
import java.util.Collection;
import net.java.treaty.vocabulary.builtins.xml.XMLVocabulary;

import org.junit.Test;

/**
 * Tests for Java vocabulary.
 * @author jens dietrich
 */
public class XMLVocabularyTests {
	@Test
	public void testTypes() throws Exception {
		XMLVocabulary jv = new XMLVocabulary();
		Collection<URI> types = jv.getTypes();
		assertEquals(3,types.size());
		assertTrue(types.contains(new URI("http://www.treaty.org/xml#XMLInstance")));
		assertTrue(types.contains(new URI("http://www.treaty.org/xml#XMLSchema")));
		assertTrue(types.contains(new URI("http://www.treaty.org/xml#DTD")));
	}
	
	@Test
	public void testRelationships() throws Exception {
		XMLVocabulary jv = new XMLVocabulary();
		Collection<URI> types = jv.getRelationships();
		assertEquals(2,types.size());
		assertTrue(types.contains(new URI("http://www.treaty.org/xml#instantiates")));
		assertTrue(types.contains(new URI("http://www.treaty.org/xml#instantiatesDTD")));
	}
	
	@Test
	public void testProperties() throws Exception {
		XMLVocabulary jv = new XMLVocabulary();
		Collection<URI> types = jv.getProperties();
		assertEquals(0,types.size());
	}
	
	@Test
	public void testSubclasses() throws Exception {
		XMLVocabulary jv = new XMLVocabulary();
		Collection<URI> types = jv.getSubTypes(new URI("http://www.treaty.org/xml#XMLInstance"));
		assertEquals(1,types.size());
		assertTrue(types.contains(new URI("http://www.treaty.org/xml#XMLSchema")));
	}
	
	@Test
	public void testSuperclasses() throws Exception {
		XMLVocabulary jv = new XMLVocabulary();
		Collection<URI> types = jv.getSuperTypes(new URI("http://www.treaty.org/xml#XMLSchema"));
		//list(types);
		assertEquals(3,types.size());
		assertTrue(types.contains(new URI("http://www.treaty.org/xml#XMLInstance")));
		assertTrue(types.contains(new URI("http://www.treaty.org#Resource")));
		assertTrue(types.contains(new URI("http://www.w3.org/2000/01/rdf-schema#Resource")));
	}
	
	@Test
	public void testDomain1() throws Exception {
		XMLVocabulary jv = new XMLVocabulary();
		URI domain = jv.getDomain(new URI("http://www.treaty.org/xml#instantiates"));
		assertEquals(new URI("http://www.treaty.org/xml#XMLInstance"),domain);
	}
	@Test
	public void testDomain2() throws Exception {
		XMLVocabulary jv = new XMLVocabulary();
		URI domain = jv.getDomain(new URI("http://www.treaty.org/xml#instantiatesDTD"));
		assertEquals(new URI("http://www.treaty.org/xml#XMLInstance"),domain);
	}
	
	@Test
	public void testRange1() throws Exception {
		XMLVocabulary jv = new XMLVocabulary();
		URI range = jv.getRange(new URI("http://www.treaty.org/xml#instantiates"));
		assertEquals(new URI("http://www.treaty.org/xml#XMLSchema"),range);
	}
	
	@Test
	public void testRange2() throws Exception {
		XMLVocabulary jv = new XMLVocabulary();
		URI range = jv.getRange(new URI("http://www.treaty.org/xml#instantiatesDTD"));
		assertEquals(new URI("http://www.treaty.org/xml#DTD"),range);
	}

}
