/*
 * Copyright (C) 2008 Jens Dietrich
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

import net.java.treaty.AggregatedContract;
import net.java.treaty.Component;
import net.java.treaty.Connector;
import net.java.treaty.Contract;
import net.java.treaty.PropertySupport;
import net.java.treaty.SimpleContract;
import net.java.treaty.TreatyException;
import net.java.treaty.ContractReader;
import net.java.treaty.xml.XMLContractReader;

/**
 * Abstract superclass for extensions and extension points.
 * 
 * @author Jens Dietrich
 */

public abstract class EclipseConnector extends PropertySupport implements
		Connector {

	/** The {@link Contract} of this {@link EclipseConnector}. */
	protected Contract contract = null;

	/** The {@link EclipsePlugin} that owns this {@link EclipseConnector}. */
	private EclipsePlugin owner = null;

	/**
	 * <p>
	 * Adds a new {@link Contract} to this {@link EclipseConnector}.
	 * </p>
	 * 
	 * @param url
	 *          The location of the {@link Contract} as a {@link URL}.
	 * @param contractOwner
	 *          The owner of the {@link Contract}. If the owner is this
	 *          {@link EclipseConnector} the argument could be <code>null</code>.
	 * 
	 * @return The newly added {@link Contract}. TODO Proposal of Claas: This
	 *         method should get a Contract and not the URL and the Owner as
	 *         arguments. This would improve removal of Contracts without
	 *         returning the newly created {@link Contract} here.
	 */
	public void addContract(URL url, EclipseConnector contractOwner) {

		SimpleContract newContract;
		newContract = null;

		/* Check if the owner of this connector has been set. */
		if (this.owner != null) {

			/* Check if the given URL is not null. */
			if (url != null) {

				ContractReader reader;

				Logger.info("Loading contract from " + url);
				reader = new XMLContractReader(new EclipseResourceManager());

				/* Try to read the contract. */
				try {
					newContract = reader.read(url.openStream());
					newContract.setLocation(url);

					/* If the owner of the contract is not null, set the owner. */
					if (contractOwner != null) {
						newContract.setOwner(contractOwner);
					}

					this.configureNewContract(newContract);
				}

				catch (TreatyException e) {
					Logger.error("Exception loading contract from " + url, e);
				}

				catch (IOException e) {
					Logger.error("Exception loading contract from " + url, e);
				}
			}
			// no else (URL is null).
		}
		// no else (no owner of this connector).

		/* If no contract has been set yet, the new contract is the root contract. */
		if (this.contract == null) {
			this.contract = newContract;
		}

		/* Else aggregate the contract. */
		else {
			Logger.info("Aggregating contracts " + url + " and " + this.contract);

			this.contract = new AggregatedContract(this.contract, newContract);
			this.contract = this.contract.pack();
		}
	}

	/**
	 * <p>
	 * Removes a given {@link Contract} from this {@link EclipseConnector}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} that shall be removed.
	 */
	public void removeContract(Contract contract) {

		/* If no contract has been set, return false. */
		if (this.contract == null) {
			Logger.info("Could not remove Contract. Was already null.");
		}

		/* Else try to remove the contract. */
		else {
			this.contract = this.contract.subtractContract(contract);
		}
	}

	public static String getContractLocation(Connector c) {

		return "/META-INF/" + c.getId() + ".contract";
	}

	public Component getOwner() {

		return owner;
	}

	public void setOwner(EclipsePlugin owner) {

		this.owner = owner;
	}

	public EclipseConnector(EclipsePlugin owner) {

		super();
		this.owner = owner;
	}

	public Contract getContract() {

		return contract;
	}

	protected void configureNewContract(SimpleContract newContract) {

		// by default nothing to do here
	}
}