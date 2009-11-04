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
 * Tests for the OWL vocabulary.
 * @author jens dietrich
 */
public class OWLVocabularyTests {
	@Test
	public void testTypes() throws Exception {
		OWLVocabulary jv = new OWLVocabulary();
		Collection<URI> types = jv.getTypes();
		assertEquals(1,types.size());
		assertTrue(types.contains(new URI("http://www.treaty.org/owl#OWLType")));
	}
	
	@Test
	public void testRelationships() throws Exception {
		OWLVocabulary jv = new OWLVocabulary();
		Collection<URI> types = jv.getRelationships();
		assertEquals(0,types.size());
	}
	
	@Test
	public void testProperties() throws Exception {
		OWLVocabulary jv = new OWLVocabulary();
		Collection<URI> types = jv.getProperties();
		assertEquals(0,types.size());
	}
	
	@Test
	public void testSubclasses() throws Exception {
		OWLVocabulary jv = new OWLVocabulary();
		Collection<URI> types = jv.getSubtypes(new URI("http://www.treaty.org/owl#OWLType"));
		assertEquals(0,types.size());
	}
	
	@Test
	public void testSuperclasses() throws Exception {
		OWLVocabulary jv = new OWLVocabulary();
		Collection<URI> types = jv.getSupertypes(new URI("http://www.treaty.org/owl#OWLType"));
		//list(types);
		assertTrue(types.contains(new URI("http://www.treaty.org#Resource")));
	}

}
