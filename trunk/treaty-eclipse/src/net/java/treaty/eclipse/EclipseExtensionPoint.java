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

import java.util.HashSet;
import java.util.Set;

import net.java.treaty.ConnectorType;
import net.java.treaty.Contract;

/**
 * <p>
 * Represents eclipse extension points. This is a wrapper class.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class EclipseExtensionPoint extends EclipseConnector {

	/** The {@link EclipseExtension}s of this {@link EclipseExtensionPoint}. */
	private Set<EclipseExtension> myEclipseExtensions =
			new HashSet<EclipseExtension>();

	/** The ID of this {@link EclipseExtensionPoint}. */
	private String myID = null;

	/**
	 * <p>
	 * Creates a new {@link EclipseExtensionPoint} for a given owning
	 * {@link EclipsePlugin} and a given id.
	 * </p>
	 * 
	 * @param owner
	 *          The {@link EclipsePlugin} of the created
	 *          {@link EclipseExtensionPoint}.
	 * @param id
	 *          The ID of the created {@link EclipseExtensionPoint}.
	 */
	public EclipseExtensionPoint(EclipsePlugin owner, String id) {

		super(owner);

		this.myID = id;

		if (owner != null) {
			owner.addExtensionPoint(this);
		}
		// no else.
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Connector#getId()
	 */
	public String getId() {

		return this.myID;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Connector#getType()
	 */
	public ConnectorType getType() {

		return ConnectorType.CONSUMER;
	}

	/**
	 * <p>
	 * Adds a given {@link EclipseExtension} to this {@link EclipseExtensionPoint}
	 * .
	 * </p>
	 * 
	 * @param eclipseExtension
	 *          The {@link EclipseExtension} that shall be added.
	 */
	public void addExtension(EclipseExtension eclipseExtension) {

		this.myEclipseExtensions.add(eclipseExtension);
		eclipseExtension.setExtensionPoint(this);
	}

	/**
	 * <p>
	 * Returns a Set of all {@link EclipseExtension}s that belong to this
	 * {@link EclipseExtensionPoint}.
	 * </p>
	 * 
	 * @return A Set of all {@link EclipseExtension}s that belong to this
	 *         {@link EclipseExtensionPoint}.
	 */
	public Set<EclipseExtension> getExtensions() {

		return new HashSet<EclipseExtension>(this.myEclipseExtensions);
	}

	/**
	 * <p>
	 * Checks whether or not this {@link EclipseExtensionPoint} has any
	 * {@link Contract}s defined on it.
	 * </p>
	 * 
	 * @return <code>true</code> if at least one {@link Contract} is defined on
	 *         this {@link EclipseExtensionPoint}.
	 */
	public boolean hasContracts() {

		boolean result;

		if (this.getContracts() != null && this.getContracts().size() > 0) {
			result = true;
		}

		/* If no contract has been found, check if any extension has a contract. */
		else {

			result = false;

			for (EclipseExtension extension : this.getExtensions()) {

				if (extension.getContracts() != null
						&& extension.getContracts().size() > 0) {
					result = true;
					break;
				}
				// no else.
			}
			// end for.
		}
		// end else.

		return result;
	}

	/**
	 * <p>
	 * Removes a given {@link EclipseExtension} from this
	 * {@link EclipseExtensionPoint}.
	 * </p>
	 * 
	 * @param eclipseExtension
	 *          The {@link EclipseExtension} that shall be removed.
	 */
	public void removeExtension(EclipseExtension eclipseExtension) {

		eclipseExtension.setExtensionPoint(null);
		this.myEclipseExtensions.remove(eclipseExtension);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return new StringBuffer().append(this.getClass().getName()).append('(')
				.append(this.getId() == null ? "?" : this.getId()).append(
						",defined in ").append(this.getOwner()).append(')').toString();
	}
}