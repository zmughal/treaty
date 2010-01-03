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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.java.treaty.Contract;
import net.java.treaty.ContractReader;
import net.java.treaty.TreatyException;
import net.java.treaty.script.ScriptContractReader;
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

	/** The different {@link ContractReader}s used to read {@link Contract}s. */
	private List<ContractReader> contractReaders;

	/**
	 * <p>
	 * Creates a new {@link EclipseContractFactory}. Private constructor for
	 * singleton pattern.
	 * </p>
	 */
	private EclipseContractFactory() {

		this.contractReaders = new LinkedList<ContractReader>();

		this.contractReaders.add(new XMLContractReader());
		this.contractReaders.add(new ScriptContractReader(
				EclipseResourceManager.INSTANCE));
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

		try {
			/* Check if the given URL is not null. */
			if (location != null) {

				Logger.info("Loading contract from " + location);

				result = this.readContract(location);
				result.setLocation(location);

				/* If the owner of the contract is not null, set the owner. */
				if (owner != null) {
					result.setOwner(owner);
				}
				// no else.
			}

			else {
				result = null;
			}
			// end else.
		}
		// end try.

		catch (TreatyException e) {
			Logger.error("Cannot create Contract.", e);
			result = null;
		}
		// end catch.

		return result;
	}

	/**
	 * <p>
	 * Tries to read a {@link Contract} from a given location.
	 * </p>
	 * 
	 * @param location
	 *          The {@link URL} location from that the Contract shall be read.
	 * @return The read {@link Contract}.
	 * @throws TreatyException
	 *           Thrown, if the {@link Contract} cannot be read.
	 */
	private Contract readContract(URL location) throws TreatyException {

		Contract result;
		result = null;

		/* Should not happen unless the list of readers has been altered. */
		if (this.contractReaders.size() == 0) {
			Logger.warn("No ContractReader found to read Contracts.");
		}
		// no else.

		else {
			Iterator<ContractReader> readerIterator;
			readerIterator = this.contractReaders.iterator();

			while (readerIterator.hasNext()) {

				try {
					result =
							readerIterator.next().read(location.openStream(),
									VocabularyRegistry.INSTANCE);

					/* If the loading was successfull, break the chain. */
					if (result != null) {
						break;
					}
					// no else.
				}

				catch (TreatyException e) {
					/* Do nothing. Try the next reader. */
				}

				catch (IOException e) {
					throw new TreatyException(
							"The given URL cannot be read as a Contract.", e);
				}
				// end catch.
			}
			// end while.
		}
		// end else.

		if (result == null) {
			throw new TreatyException(
					"None of the ContractReaders was able to read the Contract "
							+ "from the location " + location);
		}
		// no else.

		return result;
	}
}