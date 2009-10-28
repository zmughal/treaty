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

import java.util.HashSet;
import java.util.Set;

import net.java.treaty.ConnectorType;
import net.java.treaty.Contract;

import org.eclipse.core.runtime.IExtensionPoint;

/**
 * <p>
 * Represents eclipse extension points. This is a wrapper class.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class EclipseExtensionPoint extends EclipseConnector {

	/** The {@link EclipseExtension}s of this {@link EclipseExtensionPoint}. */
	private Set<EclipseExtension> extensions = new HashSet<EclipseExtension>();

	/** The {@link IExtensionPoint} wrapped by this {@link EclipseExtensionPoint}. */
	private IExtensionPoint extensionPoint = null;

	/**
	 * <p>
	 * Creates a new {@link EclipseExtensionPoint} for a given owning
	 * {@link EclipsePlugin} and a given {@link IExtensionPoint} that shall be
	 * wrapped.
	 * 
	 * @param owner
	 *          The {@link EclipsePlugin} of the created
	 *          {@link EclipseExtensionPoint}.
	 * @param extensionPoint
	 *          The wrapped {@link IExtensionPoint} of the created
	 *          {@link EclipseExtensionPoint}.
	 */
	public EclipseExtensionPoint(EclipsePlugin owner,
			IExtensionPoint extensionPoint) {

		super(owner);

		this.extensionPoint = extensionPoint;

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

		return this.extensionPoint.getUniqueIdentifier();
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

		this.extensions.add(eclipseExtension);
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

		return this.extensions;
	}

	/**
	 * <p>
	 * Returns the {@link IExtensionPoint} that is wrapped by this
	 * {@link EclipseExtensionPoint}.
	 * 
	 * @return The {@link IExtensionPoint} that is wrapped by this
	 *         {@link EclipseExtensionPoint}.
	 */
	public IExtensionPoint getWrappedExtensionPoint() {

		return this.extensionPoint;
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
		this.extensions.remove(eclipseExtension);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return new StringBuffer().append(this.getClass().getName()).append('(')
				.append(
						this.extensionPoint == null ? "?" : this.extensionPoint
								.getUniqueIdentifier()).append(",defined in ").append(
						this.getOwner()).append(')').toString();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.eclipse.EclipseConnector#configureNewContract(net.java.
	 * treaty.Contract)
	 */
	protected void configureNewContract(Contract newContract) {

		super.configureNewContract(newContract);

		newContract.setConsumer(this);
	}
}