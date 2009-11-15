/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.java.treaty.Contract;
import net.java.treaty.ContractReader;
import net.java.treaty.TreatyException;
import net.java.treaty.xml.XMLContractReader;

/**
 * <p>
 * The {@link EclipseContractFactory} is responsible to create new
 * {@link Contract}s for {@link EclipseConnector}s.
 * </p>
 * 
 * @author Claas Wilke
 */
public class EclipseContractFactory {

	/** The singleton instance of the {@link EclipseContractFactory}. */
	public static EclipseContractFactory INSTANCE = new EclipseContractFactory();

	private Map<URL, Contract> myCachedContracts = new HashMap<URL, Contract>();

	/**
	 * <p>
	 * Creates a new {@link EclipseContractFactory}. Private constructor for
	 * singleton pattern.
	 * </p>
	 */
	private EclipseContractFactory() {

		/* Remains empty. */
	}

	/**
	 * <p>
	 * Creates a new {@link Contract} for a given {@link URL} and a given
	 * {@link EclipseConnector}.
	 * </p>
	 * 
	 * @param location
	 *          The {@link URL} of this {@link SimpleContract}.
	 * @param owner
	 *          The owner (an {@link EclipseConnector}) of the created
	 *          {@link Contract}.
	 * @return The created {@link Contract} or <code>null</code> if the given
	 *         {@link URL} cannot be loaded.
	 * 
	 */
	public Contract createContract(URL location, EclipseConnector owner) {

		Contract result;

		/* Probably use a cached result. */
		if (this.myCachedContracts.containsKey(location)) {
			result = this.myCachedContracts.get(location);
		}

		/* Else try to load the contract. */
		else {
			result = null;

			/* Check if the given URL is not null. */
			if (location != null) {

				ContractReader reader;

				Logger.info("Loading contract from " + location);
				reader = new XMLContractReader();

				/* Try to read the contract. */
				try {
					result =
							reader.read(location.openStream(), VocabularyRegistry.INSTANCE);
					result.setLocation(location);

					/* If the owner of the contract is not null, set the owner. */
					if (owner != null) {
						result.setOwner(owner);
					}
					// no else.

					/* Cache the result. */
					this.myCachedContracts.put(location, result);
				}

				catch (TreatyException e) {
					Logger.error("Exception loading contract from " + location, e);
				}

				catch (IOException e) {
					Logger.error("Exception loading contract from " + location, e);
				}
			}
			// no else (URL is null).
		}

		return result;
	}
}