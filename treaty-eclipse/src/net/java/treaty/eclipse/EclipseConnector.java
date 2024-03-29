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

import java.util.LinkedHashSet;
import java.util.Set;

import net.java.treaty.Component;
import net.java.treaty.Connector;
import net.java.treaty.ConnectorType;
import net.java.treaty.Contract;
import net.java.treaty.PropertySupport;
import net.java.treaty.Role;
import net.java.treaty.eclipse.contractregistry.EclipseContractRegistry;

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
	 * <p>
	 * Returns the default {@link Contract} location of a given {@link Connector}
	 * 's {@link Contract}. This is the location of the {@link Contract}, if the
	 * owning {@link EclipsePlugin} of the {@link Connector} contracts the
	 * {@link Connector} itself.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} whose location shall be returned.
	 * @return The default {@link Contract} location of a given {@link Connector}.
	 */
	public static String getContractLocation(Connector contract) {

		return Constants.INTERNAL_CONTRACT_LOCATION_PREFIX + contract.getId()
				+ Constants.CONTRACT_LOCATION_SUFFIX;
	}

	/**
	 * <p>
	 * Creates a new {@link EclipseConnector} for a given owning
	 * {@link EclipsePlugin}.
	 * </p>
	 * 
	 * @param owner
	 *          The owning {@link EclipsePlugin} of this {@link EclipseConnector}.
	 */
	public EclipseConnector(EclipsePlugin owner) {

		super();
		this.owner = owner;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Connector#getOwner()
	 */
	public Component getOwner() {

		return this.owner;
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

	/**
	 * <p>
	 * Sets the owning {@link EclipsePlugin} of this {@link EclipseConnector}.
	 * </p>
	 * 
	 * @param owner
	 *          The owning {@link EclipsePlugin} of this {@link EclipseConnector}.
	 */
	public void setOwner(EclipsePlugin owner) {

		this.owner = owner;
	}
}