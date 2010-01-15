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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import net.java.treaty.Contract;
import net.java.treaty.ContractReader;
import net.java.treaty.TreatyException;
import net.java.treaty.action.ActionRegistry;
import net.java.treaty.action.ActionVocabulary;
import net.java.treaty.xml.XMLContractReader;

import org.junit.Test;

import test.net.java.treaty.contractregistry.VocabularyMock;

/**
 * <p>
 * Test cases to test the {@link ActionRegistry}.
 * </p>
 * 
 * @author Claas Wilke
 */
public class ActionRegistryTest {

	/** The location of the {@link Contract} files used for testing. */
	private static final String TEST_CONTRACT_LOCATION =
			"tests/test/net/java/treaty/mocks/";

	/** The name of a {@link Contract} file used for testing. */
	private static final String TEST_CONTRACT_1 =
			TEST_CONTRACT_LOCATION + "test08.contract";

	/**
	 * <p>
	 * Tests the method
	 * {@link ActionRegistry#getOnFailureActions(net.java.treaty.Contract)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test
	public void testGetOnFailureActions01() throws TreatyException,
			URISyntaxException {

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_1);

		ActionRegistry actionRegistry;
		actionRegistry = new ActionRegistry();

		ActionVocabulary actionVocabulary;
		actionVocabulary = new ActionOntologyMock();

		actionRegistry.addActionVocabulary(actionVocabulary);

		List<URI> actions;
		actions = actionRegistry.getOnFailureActions(contract);

		assertNotNull(actions);
		assertEquals(4, actions.size());

		assertEquals(new URI(ActionOntologyMock.NAME_BEFORE_ACTION), actions.get(0));
		assertEquals(new URI(ActionOntologyMock.NAME_ON_FAILURE_ACTION), actions
				.get(1));
		assertEquals(new URI(ActionOntologyMock.NAME_AFTER_ACTION), actions.get(2));
		assertEquals(new URI(ActionOntologyMock.NAME_DEFAULT_ON_ALL_ACTION),
				actions.get(3));
	}

	/**
	 * <p>
	 * Tests the method
	 * {@link ActionRegistry#getOnSuccessActions(net.java.treaty.Contract)}.
	 * </p>
	 * 
	 * @throws URISyntaxException
	 * @throws TreatyException
	 */
	@Test
	public void testGetOnSuccessActions01() throws TreatyException,
			URISyntaxException {

		Contract contract;
		contract = this.createTestContract(TEST_CONTRACT_1);

		ActionRegistry actionRegistry;
		actionRegistry = new ActionRegistry();

		ActionVocabulary actionVocabulary;
		actionVocabulary = new ActionOntologyMock();

		actionRegistry.addActionVocabulary(actionVocabulary);

		List<URI> actions;
		actions = actionRegistry.getOnSuccessActions(contract);

		assertNotNull(actions);
		assertEquals(4, actions.size());

		assertEquals(new URI(ActionOntologyMock.NAME_BEFORE_ACTION), actions.get(0));
		assertEquals(new URI(ActionOntologyMock.NAME_ON_SUCCESS_ACTION), actions
				.get(1));
		assertEquals(new URI(ActionOntologyMock.NAME_AFTER_ACTION), actions.get(2));
		assertEquals(new URI(ActionOntologyMock.NAME_DEFAULT_ON_ALL_ACTION),
				actions.get(3));
	}

	/**
	 * <p>
	 * A helper method that initializes a simple {@link Contract} used during
	 * testing.
	 * </p>
	 * 
	 * @param The
	 *          name of the {@link Contract} file that shall be created.
	 * @return A simple {@link Contract} used during testing.
	 */
	private Contract createTestContract(String fileName) {

		Contract result;
		result = null;

		URL location;
		location = null;

		/* Try to read the contract. */
		try {
			File file;
			file = new File(fileName);

			location = file.toURI().toURL();

			ContractReader reader;
			reader = new XMLContractReader();

			result = reader.read(location.openStream(), VocabularyMock.INSTANCE);
			result.setLocation(location);
		}

		catch (TreatyException e) {
			fail("Exception loading contract from " + location + ":" + e.getMessage());
		}

		catch (IOException e) {
			fail("Exception loading contract from " + location + ":" + e.getMessage());
		}

		return result;
	}
}