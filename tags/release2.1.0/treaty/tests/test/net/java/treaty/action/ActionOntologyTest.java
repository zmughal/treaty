/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package test.net.java.treaty.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import net.java.treaty.TreatyException;
import net.java.treaty.action.ActionOntology;
import net.java.treaty.trigger.TriggerOntology;

/**
 * <p>
 * Test cases to test the {@link TriggerOntology}.
 * </p>
 * 
 * @author Claas Wilke
 */
public class ActionOntologyTest {

	/**
	 * <p>
	 * Tests the method {@link ActionOntology#getActions()}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test
	public void testGetActions01() throws TreatyException, URISyntaxException {

		ActionOntology actionOntology;
		actionOntology = new ActionOntologyMock();

		assertNotNull(actionOntology.getActions());
		assertEquals(6, actionOntology.getActions().size());

		assertTrue(actionOntology.getActions().contains(
				new URI(ActionOntologyMock.NAME_AFTER_ACTION)));
		assertTrue(actionOntology.getActions().contains(
				new URI(ActionOntologyMock.NAME_BEFORE_ACTION)));
		assertTrue(actionOntology.getActions().contains(
				new URI(ActionOntologyMock.NAME_DEFAULT_ON_ALL_ACTION)));
		assertTrue(actionOntology.getActions().contains(
				new URI(ActionOntologyMock.NAME_NO_DEFAULT_ACTION)));
		assertTrue(actionOntology.getActions().contains(
				new URI(ActionOntologyMock.NAME_ON_FAILURE_ACTION)));
		assertTrue(actionOntology.getActions().contains(
				new URI(ActionOntologyMock.NAME_ON_SUCCESS_ACTION)));
		assertFalse(actionOntology.getActions().contains(
				new URI(ActionOntologyMock.NAME_UNDEFINED_ACTION)));
	}

	/**
	 * <p>
	 * Tests the method {@link ActionOntology#getDescription(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test
	public void testGetDescription01() throws TreatyException, URISyntaxException {

		ActionOntology actionOntology;
		actionOntology = new ActionOntologyMock();

		assertNotNull(actionOntology.getDescription(new URI(
				ActionOntologyMock.NAME_NO_DEFAULT_ACTION)));
		assertEquals("", actionOntology.getDescription(new URI(
				ActionOntologyMock.NAME_NO_DEFAULT_ACTION)));
	}

	/**
	 * <p>
	 * Tests the method {@link ActionOntology#getDescription(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test
	public void testGetDescription02() throws TreatyException, URISyntaxException {

		ActionOntology actionOntology;
		actionOntology = new ActionOntologyMock();

		assertNotNull(actionOntology.getDescription(new URI(
				ActionOntologyMock.NAME_AFTER_ACTION)));
		assertEquals("A default action after verification.", actionOntology
				.getDescription(new URI(ActionOntologyMock.NAME_AFTER_ACTION)));
	}

	/**
	 * <p>
	 * Tests the method {@link ActionOntology#getDescription(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test(expected = TreatyException.class)
	public void testGetDescription03() throws TreatyException, URISyntaxException {

		ActionOntology actionOntology;
		actionOntology = new ActionOntologyMock();

		/* Should cause an exception. */
		actionOntology.getDescription(new URI(
				ActionOntologyMock.NAME_UNDEFINED_ACTION));
	}

	/**
	 * <p>
	 * Tests the method {@link ActionOntology#isAfterAction(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test
	public void testIsAfterAction01() throws TreatyException, URISyntaxException {

		ActionOntology actionOntology;
		actionOntology = new ActionOntologyMock();

		assertTrue(actionOntology.isAfterAction(new URI(
				ActionOntologyMock.NAME_AFTER_ACTION)));
		assertTrue(actionOntology.isAfterAction(new URI(
				ActionOntologyMock.NAME_DEFAULT_ON_ALL_ACTION)));
		assertFalse(actionOntology.isAfterAction(new URI(
				ActionOntologyMock.NAME_BEFORE_ACTION)));
		assertFalse(actionOntology.isAfterAction(new URI(
				ActionOntologyMock.NAME_NO_DEFAULT_ACTION)));
		assertFalse(actionOntology.isAfterAction(new URI(
				ActionOntologyMock.NAME_ON_FAILURE_ACTION)));
		assertFalse(actionOntology.isAfterAction(new URI(
				ActionOntologyMock.NAME_ON_SUCCESS_ACTION)));
	}

	/**
	 * <p>
	 * Tests the method {@link ActionOntology#isAfterAction(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test(expected = TreatyException.class)
	public void testIsAfterAction02() throws TreatyException, URISyntaxException {

		ActionOntology actionOntology;
		actionOntology = new ActionOntologyMock();

		/* Should cause an exception. */
		actionOntology.isAfterAction(new URI(
				ActionOntologyMock.NAME_UNDEFINED_ACTION));
	}

	/**
	 * <p>
	 * Tests the method {@link ActionOntology#isBeforeAction(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test
	public void testIsBeforeAction01() throws TreatyException, URISyntaxException {

		ActionOntology actionOntology;
		actionOntology = new ActionOntologyMock();

		assertTrue(actionOntology.isBeforeAction(new URI(
				ActionOntologyMock.NAME_BEFORE_ACTION)));
		assertTrue(actionOntology.isBeforeAction(new URI(
				ActionOntologyMock.NAME_DEFAULT_ON_ALL_ACTION)));
		assertFalse(actionOntology.isBeforeAction(new URI(
				ActionOntologyMock.NAME_AFTER_ACTION)));
		assertFalse(actionOntology.isBeforeAction(new URI(
				ActionOntologyMock.NAME_NO_DEFAULT_ACTION)));
		assertFalse(actionOntology.isBeforeAction(new URI(
				ActionOntologyMock.NAME_ON_FAILURE_ACTION)));
		assertFalse(actionOntology.isBeforeAction(new URI(
				ActionOntologyMock.NAME_ON_SUCCESS_ACTION)));
	}

	/**
	 * <p>
	 * Tests the method {@link ActionOntology#isBeforeAction(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test(expected = TreatyException.class)
	public void testIsBeforeAction02() throws TreatyException, URISyntaxException {

		ActionOntology actionOntology;
		actionOntology = new ActionOntologyMock();

		/* Should cause an exception. */
		actionOntology.isBeforeAction(new URI(
				ActionOntologyMock.NAME_UNDEFINED_ACTION));
	}

	/**
	 * <p>
	 * Tests the method {@link ActionOntology#isDefaultOnFailure(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test
	public void testIsDefaultOnFailure01() throws TreatyException,
			URISyntaxException {

		ActionOntology actionOntology;
		actionOntology = new ActionOntologyMock();

		assertTrue(actionOntology.isDefaultOnFailure(new URI(
				ActionOntologyMock.NAME_ON_FAILURE_ACTION)));
		assertTrue(actionOntology.isDefaultOnFailure(new URI(
				ActionOntologyMock.NAME_DEFAULT_ON_ALL_ACTION)));
		assertFalse(actionOntology.isDefaultOnFailure(new URI(
				ActionOntologyMock.NAME_AFTER_ACTION)));
		assertFalse(actionOntology.isDefaultOnFailure(new URI(
				ActionOntologyMock.NAME_NO_DEFAULT_ACTION)));
		assertFalse(actionOntology.isDefaultOnFailure(new URI(
				ActionOntologyMock.NAME_BEFORE_ACTION)));
		assertFalse(actionOntology.isDefaultOnFailure(new URI(
				ActionOntologyMock.NAME_ON_SUCCESS_ACTION)));
	}

	/**
	 * <p>
	 * Tests the method {@link ActionOntology#isDefaultOnFailure(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test(expected = TreatyException.class)
	public void testIsDefaultOnFailure02() throws TreatyException,
			URISyntaxException {

		ActionOntology actionOntology;
		actionOntology = new ActionOntologyMock();

		/* Should cause an exception. */
		actionOntology.isDefaultOnFailure(new URI(
				ActionOntologyMock.NAME_UNDEFINED_ACTION));
	}

	/**
	 * <p>
	 * Tests the method {@link ActionOntology#isDefaultOnSuccess(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test
	public void testIsDefaultOnSuccess01() throws TreatyException,
			URISyntaxException {

		ActionOntology actionOntology;
		actionOntology = new ActionOntologyMock();

		assertTrue(actionOntology.isDefaultOnSuccess(new URI(
				ActionOntologyMock.NAME_ON_SUCCESS_ACTION)));
		assertTrue(actionOntology.isDefaultOnSuccess(new URI(
				ActionOntologyMock.NAME_DEFAULT_ON_ALL_ACTION)));
		assertFalse(actionOntology.isDefaultOnSuccess(new URI(
				ActionOntologyMock.NAME_AFTER_ACTION)));
		assertFalse(actionOntology.isDefaultOnSuccess(new URI(
				ActionOntologyMock.NAME_NO_DEFAULT_ACTION)));
		assertFalse(actionOntology.isDefaultOnSuccess(new URI(
				ActionOntologyMock.NAME_BEFORE_ACTION)));
		assertFalse(actionOntology.isDefaultOnSuccess(new URI(
				ActionOntologyMock.NAME_ON_FAILURE_ACTION)));
	}

	/**
	 * <p>
	 * Tests the method {@link ActionOntology#isDefaultOnSuccess(URI)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test(expected = TreatyException.class)
	public void testIsDefaultOnSuccess02() throws TreatyException,
			URISyntaxException {

		ActionOntology actionOntology;
		actionOntology = new ActionOntologyMock();

		/* Should cause an exception. */
		actionOntology.isDefaultOnSuccess(new URI(
				ActionOntologyMock.NAME_UNDEFINED_ACTION));
	}
}