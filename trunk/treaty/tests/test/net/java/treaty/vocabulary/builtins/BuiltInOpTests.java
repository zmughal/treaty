/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package test.net.java.treaty.vocabulary.builtins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.net.URI;
import net.java.treaty.PropertyCondition;
import net.java.treaty.Resource;
import net.java.treaty.VerificationException;
import net.java.treaty.vocabulary.builtins.BasicOpVocabulary;
import net.java.treaty.vocabulary.builtins.BuiltInOperators;
import org.junit.Test;

/**
 * Test cases for built-in ops.
 * @author jens dietrich
 */
public class BuiltInOpTests {
	@Test
	public void testTypes() throws Exception {
		BasicOpVocabulary v = new BasicOpVocabulary();
		assertEquals(0,v.getTypes().size());
		assertEquals(0,v.getRelationships().size());
		assertEquals(BuiltInOperators.INSTANCE.getOpIds().size(),v.getProperties().size());
	}
	
	@Test
	public void testVerification1() throws Exception {
		BasicOpVocabulary v = new BasicOpVocabulary();
		PropertyCondition c = new PropertyCondition();
		c.setOperator(new URI("http://www.treaty.org/builtin/#lt"));
		c.setValue(42);
		Resource r = new Resource();
		r.setValue(41);
		c.setResource(r);
		
		v.check(c);
		assertTrue(true); // check should not throw exception
	}
	
	@Test(expected=VerificationException.class)
	public void testVerification2() throws Exception {
		BasicOpVocabulary v = new BasicOpVocabulary();
		PropertyCondition c = new PropertyCondition();
		c.setOperator(new URI("http://www.treaty.org/builtin/#lt"));
		c.setValue(41);
		Resource r = new Resource();
		r.setValue(42);
		c.setResource(r);
		
		v.check(c);
	}
}
