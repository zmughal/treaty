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

import net.java.treaty.ExistsCondition;
import net.java.treaty.PropertyCondition;
import net.java.treaty.Resource;
import net.java.treaty.VerificationException;
import net.java.treaty.vocabulary.builtins.BasicOpVocabulary;
import net.java.treaty.vocabulary.builtins.BuiltInOperators;
import org.junit.Test;

/**
 * <p>
 * Test cases for built-in ops.
 * </p>
 * 
 * @author jens dietrich
 */
public class BuiltInOpTests {

	/**
	 * <p>
	 * Tests the method {@link BasicOpVocabulary#getTypes()}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Probably thrown, if the test fails.
	 */
	@Test
	public void testGetTypes() throws Exception {

		BasicOpVocabulary vocabulary;
		vocabulary = new BasicOpVocabulary();

		assertEquals(5, vocabulary.getTypes().size());
	}

	/**
	 * <p>
	 * Tests the method {@link BasicOpVocabulary#getSuperTypes()}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Probably thrown, if the test fails.
	 */
	@Test
	public void testGetSuperTypes() throws Exception {

		BasicOpVocabulary vocabulary;
		vocabulary = new BasicOpVocabulary();

		URI aURI;

		aURI = new URI(BasicOpVocabulary.TYPE_NAME_BOOLEAN);
		assertEquals(0, vocabulary.getSuperTypes(aURI).size());

		aURI = new URI(BasicOpVocabulary.TYPE_NAME_DOUBLE);
		assertEquals(0, vocabulary.getSuperTypes(aURI).size());

		aURI = new URI(BasicOpVocabulary.TYPE_NAME_INT);
		assertEquals(1, vocabulary.getSuperTypes(aURI).size());
		assertTrue(vocabulary.getSuperTypes(aURI).contains(
				new URI(BasicOpVocabulary.TYPE_NAME_DOUBLE)));

		aURI = new URI(BasicOpVocabulary.TYPE_NAME_INTEGER);
		assertEquals(1, vocabulary.getSuperTypes(aURI).size());
		assertTrue(vocabulary.getSuperTypes(aURI).contains(
				new URI(BasicOpVocabulary.TYPE_NAME_DOUBLE)));

		aURI = new URI(BasicOpVocabulary.TYPE_NAME_STRING);
		assertEquals(0, vocabulary.getSuperTypes(aURI).size());
	}

	/**
	 * <p>
	 * Tests the method {@link BasicOpVocabulary#getSubTypes()}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Probably thrown, if the test fails.
	 */
	@Test
	public void testGetSubTypes() throws Exception {

		BasicOpVocabulary vocabulary;
		vocabulary = new BasicOpVocabulary();

		URI aURI;

		aURI = new URI(BasicOpVocabulary.TYPE_NAME_BOOLEAN);
		assertEquals(0, vocabulary.getSubTypes(aURI).size());

		aURI = new URI(BasicOpVocabulary.TYPE_NAME_DOUBLE);
		assertEquals(2, vocabulary.getSubTypes(aURI).size());
		assertTrue(vocabulary.getSubTypes(aURI).contains(
				new URI(BasicOpVocabulary.TYPE_NAME_INT)));
		assertTrue(vocabulary.getSubTypes(aURI).contains(
				new URI(BasicOpVocabulary.TYPE_NAME_INTEGER)));

		aURI = new URI(BasicOpVocabulary.TYPE_NAME_INT);
		assertEquals(0, vocabulary.getSubTypes(aURI).size());

		aURI = new URI(BasicOpVocabulary.TYPE_NAME_INTEGER);
		assertEquals(0, vocabulary.getSubTypes(aURI).size());

		aURI = new URI(BasicOpVocabulary.TYPE_NAME_STRING);
		assertEquals(0, vocabulary.getSubTypes(aURI).size());
	}

	/**
	 * <p>
	 * Tests the method {@link BasicOpVocabulary#getProperties()}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Probably thrown, if the test fails.
	 */
	@Test
	public void testGetProperties() throws Exception {

		BasicOpVocabulary vocabulary;
		vocabulary = new BasicOpVocabulary();

		assertEquals(0, vocabulary.getRelationships().size());
		assertEquals(BuiltInOperators.INSTANCE.getOpIds().size(), vocabulary
				.getProperties().size());
	}

	/**
	 * <p>
	 * Tests the verification of a built-in {@link ExistsCondition}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Probably thrown, if the test fails.
	 */
	@Test
	public void testExistsConditionVerification01() throws Exception {

		BasicOpVocabulary vocabulary;
		vocabulary = new BasicOpVocabulary();

		ExistsCondition condition = new ExistsCondition();

		Resource resource;

		resource = new Resource();
		resource.setType(new URI(BasicOpVocabulary.TYPE_NAME_BOOLEAN));
		resource.setValue(true);

		condition.setResource(resource);

		/* Should not fail. */
		vocabulary.check(condition);
	}

	/**
	 * <p>
	 * Tests the verification of a built-in {@link ExistsCondition}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Probably thrown, if the test fails.
	 */
	@Test
	public void testExistsConditionVerification02() throws Exception {

		BasicOpVocabulary vocabulary;
		vocabulary = new BasicOpVocabulary();

		ExistsCondition condition = new ExistsCondition();

		Resource resource;

		resource = new Resource();
		resource.setType(new URI(BasicOpVocabulary.TYPE_NAME_BOOLEAN));
		resource.setValue("false");

		condition.setResource(resource);

		/* Should not fail. */
		vocabulary.check(condition);
	}

	/**
	 * <p>
	 * Tests the verification of a built-in {@link ExistsCondition}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Probably thrown, if the test fails.
	 */
	@Test
	public void testExistsConditionVerification03() throws Exception {

		BasicOpVocabulary vocabulary;
		vocabulary = new BasicOpVocabulary();

		ExistsCondition condition = new ExistsCondition();

		Resource resource;

		resource = new Resource();
		resource.setType(new URI(BasicOpVocabulary.TYPE_NAME_DOUBLE));
		resource.setValue(42);

		condition.setResource(resource);

		/* Should not fail. */
		vocabulary.check(condition);
	}

	/**
	 * <p>
	 * Tests the verification of a built-in {@link ExistsCondition}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Probably thrown, if the test fails.
	 */
	@Test(expected = VerificationException.class)
	public void testExistsConditionVerification04() throws Exception {

		BasicOpVocabulary vocabulary;
		vocabulary = new BasicOpVocabulary();

		ExistsCondition condition = new ExistsCondition();

		Resource resource;

		resource = new Resource();
		resource.setType(new URI(BasicOpVocabulary.TYPE_NAME_DOUBLE));
		resource.setValue("fourty-two");

		condition.setResource(resource);

		/* Should fail. */
		vocabulary.check(condition);
	}

	/**
	 * <p>
	 * Tests the verification of a built-in {@link ExistsCondition}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Probably thrown, if the test fails.
	 */
	@Test
	public void testExistsConditionVerification05() throws Exception {

		BasicOpVocabulary vocabulary;
		vocabulary = new BasicOpVocabulary();

		ExistsCondition condition = new ExistsCondition();

		Resource resource;

		resource = new Resource();
		resource.setType(new URI(BasicOpVocabulary.TYPE_NAME_INT));
		resource.setValue(42);

		condition.setResource(resource);

		/* Should not fail. */
		vocabulary.check(condition);
	}

	/**
	 * <p>
	 * Tests the verification of a built-in {@link ExistsCondition}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Probably thrown, if the test fails.
	 */
	@Test
	public void testExistsConditionVerification06() throws Exception {

		BasicOpVocabulary vocabulary;
		vocabulary = new BasicOpVocabulary();

		ExistsCondition condition = new ExistsCondition();

		Resource resource;

		resource = new Resource();
		resource.setType(new URI(BasicOpVocabulary.TYPE_NAME_INTEGER));
		resource.setValue(42);

		condition.setResource(resource);

		/* Should not fail. */
		vocabulary.check(condition);
	}

	/**
	 * <p>
	 * Tests the verification of a built-in {@link ExistsCondition}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Probably thrown, if the test fails.
	 */
	@Test
	public void testExistsConditionVerification07() throws Exception {

		BasicOpVocabulary vocabulary;
		vocabulary = new BasicOpVocabulary();

		ExistsCondition condition = new ExistsCondition();

		Resource resource;

		resource = new Resource();
		resource.setType(new URI(BasicOpVocabulary.TYPE_NAME_STRING));
		resource.setValue("something");

		condition.setResource(resource);

		/* Should not fail. */
		vocabulary.check(condition);
	}

	/**
	 * <p>
	 * Tests the verification of a built-in {@link ExistsCondition}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Probably thrown, if the test fails.
	 */
	@Test(expected = VerificationException.class)
	public void testExistsConditionVerification08() throws Exception {

		BasicOpVocabulary vocabulary;
		vocabulary = new BasicOpVocabulary();

		ExistsCondition condition = new ExistsCondition();

		Resource resource;

		resource = new Resource();
		resource.setType(new URI(BasicOpVocabulary.TYPE_NAME_STRING));
		resource.setValue(null);

		condition.setResource(resource);

		/* Should fail. */
		vocabulary.check(condition);
	}

	/**
	 * <p>
	 * Tests the verification of a built-in {@link PropertyCondition}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Probably thrown, if the test fails.
	 */
	@Test
	public void testPropertyConditionVerification01() throws Exception {

		BasicOpVocabulary vocabulary;
		vocabulary = new BasicOpVocabulary();

		PropertyCondition condition = new PropertyCondition();

		condition.setOperator(new URI("http://www.treaty.org/builtin/#lt"));
		condition.setValue(42);

		Resource resource;

		resource = new Resource();
		resource.setValue(41);

		condition.setResource(resource);

		/* Should not fail. */
		vocabulary.check(condition);
	}

	/**
	 * <p>
	 * Tests the verification of a built-in {@link PropertyCondition}.
	 * </p>
	 * 
	 * @throws Exception
	 *           Probably thrown, if the test fails.
	 */
	@Test(expected = VerificationException.class)
	public void testPropertyConditionVerification02() throws Exception {

		BasicOpVocabulary vocabulary;
		vocabulary = new BasicOpVocabulary();

		PropertyCondition condition;
		condition = new PropertyCondition();
		condition.setOperator(new URI("http://www.treaty.org/builtin/#lt"));
		condition.setValue(41);

		Resource resource = new Resource();

		resource.setValue(42);
		condition.setResource(resource);

		/* Should fail. */
		vocabulary.check(condition);
	}
}