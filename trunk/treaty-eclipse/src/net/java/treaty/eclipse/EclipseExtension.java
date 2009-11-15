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

import net.java.treaty.Component;
import net.java.treaty.ConnectorType;

import org.eclipse.core.runtime.IExtension;

/**
 * <p>
 * Represents eclipse extensions. This is a wrapper class.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class EclipseExtension extends EclipseConnector implements
		Comparable<EclipseExtension> {

	/** The wrapped {@link IExtension} of this {@link EclipseExtension}. */
	private IExtension extension = null;

	/**
	 * The associated {@link EclipseExtensionPoint} of this
	 * {@link EclipseExtension}.
	 */
	private EclipseExtensionPoint extensionPoint = null;

	/**
	 * <p>
	 * Creates a new {@link EclipseExtension} for a given {@link EclipsePlugin}
	 * and a given {@link IExtension} that shall be wrapped.
	 * </p>
	 * 
	 * @param owner
	 *          The owner (an {@link EclipsePlugin}) of the created
	 *          {@link EclipseExtension}.
	 * @param extension
	 *          The wrapped {@link IExtension} of this {@link EclipseExtension}.
	 */
	public EclipseExtension(EclipsePlugin owner, IExtension extension) {

		super(owner);

		this.extension = extension;

		if (owner != null) {
			owner.addExtension(this);
		}
		// no else.

		this.setOwner(owner);
	}

	/**
	 * <p>
	 * {@link EclipseExtension} are compared by their owning {@link Component} and
	 * their ID.
	 * </p>
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(EclipseExtension eclipseExtension) {

		int result;
		result = 0;

		/* Probably compare the owners' IDs. */
		if (this.getOwner() != null && eclipseExtension.getOwner() != null) {

			result =
					this.getOwner().getId()
							.compareTo(eclipseExtension.getOwner().getId());
		}

		else if (this.getOwner() != null) {
			result = 1;
		}

		else if (eclipseExtension.getOwner() != null) {
			result = 1;
		}
		// no else.

		/* Probably compare the extensions' IDs. */
		if (result == 0) {

			if (this.getId() != null && eclipseExtension.getId() != null) {
				result = this.getId().compareTo(eclipseExtension.getId());
			}

			else if (this.getId() != null) {
				result = 1;
			}

			else if (eclipseExtension.getId() != null) {
				result = -1;
			}
		}
		// no else.

		return result;
	}

	public ConnectorType getType() {

		return ConnectorType.SUPPLIER;
	}

	public String getId() {

		return this.extension.getUniqueIdentifier();
	}

	/**
	 * <p>
	 * Returns the index of this {@link EclipseExtension} amongst the
	 * {@link EclipseExtension}s for the same {@link EclipseExtensionPoint}. This
	 * is used for generating XPath queries to query the plugin.xml
	 * </p>
	 * 
	 * @return The index of this {@link EclipseExtension} amongst the
	 *         {@link EclipseExtension}s for the same
	 *         {@link EclipseExtensionPoint}.
	 */
	public int getExtensionIndex() {

		/* XQuery semantics, so we start with 1. */
		int index = 1;

		for (EclipseExtension eclipseExtension : ((EclipsePlugin) this.getOwner())
				.getExtensions()) {

			/* If the current extension is this one, return the index */
			if (eclipseExtension == this) {
				return index;
			}

			/*
			 * If the current extension has the same extension point, increment the
			 * index.
			 */
			else if (eclipseExtension.getExtensionPoint() == this.getExtensionPoint()) {
				index = index + 1;
			}
			// no else.
		}

		/* If the extension has not been found, fail. */
		throw new IllegalStateException("cannot find extension");
	}

	/**
	 * <p>
	 * Returns the {@link EclipseExtensionPoint} this {@link EclipseExtension}
	 * belongs to.
	 * </p>
	 * 
	 * @return The {@link EclipseExtensionPoint} this {@link EclipseExtension}
	 *         belongs to.
	 */
	public EclipseExtensionPoint getExtensionPoint() {

		return extensionPoint;
	}

	/**
	 * <p>
	 * Returns the wrapped {@link IExtension} of this {@link EclipseExtension}.
	 * </p>
	 * 
	 * @return The wrapped {@link IExtension} of this {@link EclipseExtension}
	 *         .</p>
	 */
	public IExtension getWrappedExtension() {

		return this.extension;
	}

	/**
	 * <p>
	 * Sets the {@link EclipseExtensionPoint} this {@link EclipseExtension}
	 * belongs to.
	 * </p>
	 * 
	 * @param extensionPoint
	 *          The {@link EclipseExtensionPoint} this {@link EclipseExtension}
	 *          belongs to.
	 */
	public void setExtensionPoint(EclipseExtensionPoint extensionPoint) {

		this.extensionPoint = extensionPoint;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return new StringBuffer()
				.append(this.getClass().getName())
				.append('(')
				.append(
						this.extension == null ? "?" : this.extension.getUniqueIdentifier())
				.append(",defined in ").append(this.getOwner()).append(')').toString();
	}
}