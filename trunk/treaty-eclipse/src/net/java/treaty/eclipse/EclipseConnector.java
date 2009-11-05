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
import java.util.LinkedHashSet;
import java.util.Set;

import net.java.treaty.Component;
import net.java.treaty.Connector;
import net.java.treaty.ConnectorType;
import net.java.treaty.Contract;
import net.java.treaty.ContractReader;
import net.java.treaty.PropertySupport;
import net.java.treaty.Role;
import net.java.treaty.TreatyException;
import net.java.treaty.eclipse.contractregistry.EclipseContractRegistry;
import net.java.treaty.xml.XMLContractReader;

/**
 * <p>
 * Abstract superclass for extensions and extension points.
 * </p>
 * 
 * @author Jens Dietrich
 */
public abstract class EclipseConnector extends PropertySupport implements
		Connector {

	/** The {@link EclipsePlugin} that owns this {@link EclipseConnector}. */
	private EclipsePlugin owner = null;

	/**
	 * FIXME Claas: Probably extract this method into its own class.
	 * 
	 * <p>
	 * Creates a new {@link Contract} for a given {@link URL}
	 * </p>
	 * 
	 * @param location
	 *          The {@link URL} of this {@link SimpleContract}.
	 * @param contractOwner
	 *          The Owner (an {@link EclipseConnector}) of the created
	 *          {@link Contract}.
	 * @param
	 * 
	 * @return The created {@link Contract} or <code>null</code> if the given
	 *         {@link URL} cannot be loaded.
	 * 
	 */
	public static Contract createContract(URL location,	EclipseConnector contractOwner) {

		Contract result;
		result = null;

		/* Check if the given URL is not null. */
		if (location != null) {

			ContractReader reader;

			Logger.info("Loading contract from " + location);
			reader = new XMLContractReader();

			/* Try to read the contract. */
			try {
				result = reader.read(location.openStream(),VocabularyRegistry.INSTANCE);
				result.setLocation(location);

				/* If the owner of the contract is not null, set the owner. */
				if (contractOwner != null) {
					result.setOwner(contractOwner);

					contractOwner.configureNewContract(result);
				}
				// no else.
			}

			catch (TreatyException e) {
				Logger.error("Exception loading contract from " + location, e);
			}

			catch (IOException e) {
				Logger.error("Exception loading contract from " + location, e);
			}
		}
		// no else (URL is null).

		return result;
	}

	protected void configureNewContract(Contract newContract) {

		// by default nothing to do here
	}

	/**
	 * <p>
	 * Returns a {@link Set} containing all {@link Contract}s defined on this
	 * {@link EclipseConnector}.
	 * </p>
	 * 
	 * @return A {@link Set} containing all {@link Contract}s defined on this
	 *         {@link EclipseConnector}.
	 */
	public Set<Contract> getContracts() {

		LinkedHashSet<Contract> result;
		result = new LinkedHashSet<Contract>();

		if (this.getType().equals(ConnectorType.CONSUMER)) {
			result.addAll(EclipseContractRegistry.getInstance().getContracts(this,
					Role.CONSUMER));
		}

		else if (this.getType().equals(ConnectorType.SUPPLIER)) {
			result.addAll(EclipseContractRegistry.getInstance().getContracts(this,
					Role.SUPPLIER));
		}
		// no else.

		return result;
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
}