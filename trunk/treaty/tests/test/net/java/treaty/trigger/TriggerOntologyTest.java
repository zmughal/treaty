/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package test.net.java.treaty.trigger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import net.java.treaty.TreatyException;
import net.java.treaty.trigger.TriggerOntology;

/**
 * <p>
 * Test cases to test the {@link TriggerOntology}.
 * </p>
 * 
 * @author Claas Wilke
 */
public class TriggerOntologyTest {

	/**
	 * <p>
	 * Tests the method {@link TriggerOntology#getTriggers()}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test
	public void testGetTriggers01() throws TreatyException, URISyntaxException {

		TriggerOntology triggerOntology;
		triggerOntology = new TriggerOntologyMock();

		assertNotNull(triggerOntology.getTriggers());
		assertEquals(3, triggerOntology.getTriggers().size());

		assertTrue(triggerOntology.getTriggers().contains(
				new URI(TriggerOntologyMock.NAME_SUB_TRIGGER)));
		assertTrue(triggerOntology.getTriggers().contains(
				new URI(TriggerOntologyMock.NAME_SUPER_TRIGGER)));
		assertTrue(triggerOntology.getTriggers().contains(
				new URI(TriggerOntologyMock.NAME_DEFAULT_TRIGGER)));
		assertFalse(triggerOntology.getTriggers().contains(
				new URI(TriggerOntologyMock.NAME_UNDEFINED_TRIGGER)));
	}

	/**
	 * <p>
	 * Tests the method {@link TriggerOntology#getSubTriggers()}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test
	public void testGetSubTriggers01() throws TreatyException, URISyntaxException {

		TriggerOntology triggerOntology;
		triggerOntology = new TriggerOntologyMock();

		assertNotNull(triggerOntology.getSubTriggers(new URI(
				TriggerOntologyMock.NAME_SUPER_TRIGGER)));
		assertEquals(1, triggerOntology.getSubTriggers(
				new URI(TriggerOntologyMock.NAME_SUPER_TRIGGER)).size());

		assertTrue(triggerOntology.getSubTriggers(
				new URI(TriggerOntologyMock.NAME_SUPER_TRIGGER)).contains(
				new URI(TriggerOntologyMock.NAME_SUB_TRIGGER)));
	}

	/**
	 * <p>
	 * Tests the method {@link TriggerOntology#getSubTriggers()}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test
	public void testGetSubTriggers02() throws TreatyException, URISyntaxException {

		TriggerOntology triggerOntology;
		triggerOntology = new TriggerOntologyMock();

		assertNotNull(triggerOntology.getSubTriggers(new URI(
				TriggerOntologyMock.NAME_SUB_TRIGGER)));
		assertEquals(0, triggerOntology.getSubTriggers(
				new URI(TriggerOntologyMock.NAME_SUB_TRIGGER)).size());
	}

	/**
	 * <p>
	 * Tests the method {@link TriggerOntology#getSuperTriggers()}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test
	public void testGetSuperTriggers01() throws TreatyException,
			URISyntaxException {

		TriggerOntology triggerOntology;
		triggerOntology = new TriggerOntologyMock();

		assertNotNull(triggerOntology.getSuperTriggers(new URI(
				TriggerOntologyMock.NAME_SUB_TRIGGER)));
		assertEquals(1, triggerOntology.getSuperTriggers(
				new URI(TriggerOntologyMock.NAME_SUB_TRIGGER)).size());

		assertTrue(triggerOntology.getSuperTriggers(
				new URI(TriggerOntologyMock.NAME_SUB_TRIGGER)).contains(
				new URI(TriggerOntologyMock.NAME_SUPER_TRIGGER)));
	}

	/**
	 * <p>
	 * Tests the method {@link TriggerOntology#getSuperTriggers()}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test
	public void testGetSuperTriggers02() throws TreatyException,
			URISyntaxException {

		TriggerOntology triggerOntology;
		triggerOntology = new TriggerOntologyMock();

		assertNotNull(triggerOntology.getSuperTriggers(new URI(
				TriggerOntologyMock.NAME_SUPER_TRIGGER)));
		assertEquals(0, triggerOntology.getSuperTriggers(
				new URI(TriggerOntologyMock.NAME_SUPER_TRIGGER)).size());
	}

	/**
	 * <p>
	 * Tests the method {@link TriggerOntology#getDescription(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test
	public void testGetDescription01() throws TreatyException, URISyntaxException {

		TriggerOntology triggerOntology;
		triggerOntology = new TriggerOntologyMock();

		assertNotNull(triggerOntology.getDescription(new URI(
				TriggerOntologyMock.NAME_SUPER_TRIGGER)));
		assertEquals("", triggerOntology.getDescription(new URI(
				TriggerOntologyMock.NAME_SUPER_TRIGGER)));
	}

	/**
	 * <p>
	 * Tests the method {@link TriggerOntology#getDescription(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test
	public void testGetDescription02() throws TreatyException, URISyntaxException {

		TriggerOntology triggerOntology;
		triggerOntology = new TriggerOntologyMock();

		assertNotNull(triggerOntology.getDescription(new URI(
				TriggerOntologyMock.NAME_SUB_TRIGGER)));
		assertEquals("A Subclass for testing.", triggerOntology
				.getDescription(new URI(TriggerOntologyMock.NAME_SUB_TRIGGER)));
	}

	/**
	 * <p>
	 * Tests the method {@link TriggerOntology#getDescription(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test(expected = TreatyException.class)
	public void testGetDescription03() throws TreatyException, URISyntaxException {

		TriggerOntology triggerOntology;
		triggerOntology = new TriggerOntologyMock();

		/* Should cause an exception. */
		triggerOntology.getDescription(new URI(
				TriggerOntologyMock.NAME_UNDEFINED_TRIGGER));
	}

	/**
	 * <p>
	 * Tests the method {@link TriggerOntology#isDefaultTrigger(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test
	public void testIsDefaultTrigger01() throws TreatyException,
			URISyntaxException {

		TriggerOntology triggerOntology;
		triggerOntology = new TriggerOntologyMock();

		assertFalse(triggerOntology.isDefaultTrigger(new URI(
				TriggerOntologyMock.NAME_SUB_TRIGGER)));
	}

	/**
	 * <p>
	 * Tests the method {@link TriggerOntology#isDefaultTrigger(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test
	public void testIsDefaultTrigger02() throws TreatyException,
			URISyntaxException {

		TriggerOntology triggerOntology;
		triggerOntology = new TriggerOntologyMock();

		assertTrue(triggerOntology.isDefaultTrigger(new URI(
				TriggerOntologyMock.NAME_DEFAULT_TRIGGER)));
	}

	/**
	 * <p>
	 * Tests the method {@link TriggerOntology#isDefaultTrigger(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test(expected = TreatyException.class)
	public void testIsDefaultTrigger03() throws TreatyException,
			URISyntaxException {

		TriggerOntology triggerOntology;
		triggerOntology = new TriggerOntologyMock();

		/* Should cause an exception. */
		triggerOntology.isDefaultTrigger(new URI(
				TriggerOntologyMock.NAME_UNDEFINED_TRIGGER));
	}
}