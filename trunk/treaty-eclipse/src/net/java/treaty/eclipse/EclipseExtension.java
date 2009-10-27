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

import org.eclipse.core.runtime.IExtension;
import net.java.treaty.ConnectorType;
import net.java.treaty.Contract;

/**
 * <p>
 * Represents eclipse extensions. This is a wrapper class.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class EclipseExtension extends EclipseConnector {

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
	 * Returns the wrapped {@link IExtension} of this {@link EclipseExtension}.
	 * </p>
	 * 
	 * @return The wrapped {@link IExtension} of this {@link EclipseExtension}
	 *         .</p>
	 */
	public IExtension getWrappedExtension() {

		return this.extension;
	}

	public ConnectorType getType() {

		return ConnectorType.SUPPLIER;
	}

	public String getId() {

		return this.extension.getUniqueIdentifier();
	}

	public EclipseExtensionPoint getExtensionPoint() {

		return extensionPoint;
	}

	public void setExtensionPoint(EclipseExtensionPoint extensionPoint) {

		this.extensionPoint = extensionPoint;
	}

	public void setContract(Contract c) {

		this.contract = c;
	}

	@Override
	public String toString() {

		return new StringBuffer()
				.append(this.getClass().getName())
				.append('(')
				.append(
						this.extension == null ? "?" : this.extension.getUniqueIdentifier())
				.append(",defined in ").append(this.getOwner()).append(')').toString();
	}

	// get the index of this extension amongst the extensions for the same
	// extension point
	public int getExtensionIndex() {

		int i = 1; // this is used for generating xpath queries to query plugin.xml,
		// so we start with 1
		for (EclipseExtension x : ((EclipsePlugin) this.getOwner()).getExtensions()) {
			if (x == this) {
				return i;
			}
			else if (x.getExtensionPoint() == this.getExtensionPoint()) {
				i = i + 1;
			}
		}
		throw new IllegalStateException("cannot find extension");

	}

}
