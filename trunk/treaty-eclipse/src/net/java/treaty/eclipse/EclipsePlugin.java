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

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import net.java.treaty.Component;
import net.java.treaty.Connector;
import net.java.treaty.Contract;
import net.java.treaty.PropertySupport;

import org.osgi.framework.Bundle;

/**
 * Represents plugins. This is a wrapper class.
 * 
 * @author Jens Dietrich
 */

public class EclipsePlugin extends PropertySupport implements Component {

	/** The {@link EclipseExtensionPoint}s of this {@link EclipsePlugin}. */
	private LinkedHashSet<EclipseExtensionPoint> extensionPoints =
			new LinkedHashSet<EclipseExtensionPoint>();

	/** The {@link EclipseExtension}s of this {@link EclipsePlugin}. */
	private LinkedHashSet<EclipseExtension> extensions =
			new LinkedHashSet<EclipseExtension>();

	/** The {@link Bundle} wrapped by this {@link EclipsePlugin}. */
	private Bundle bundle = null;

	/**
	 * <p>
	 * Creates a new {@link EclipsePlugin}.
	 * </p>
	 * 
	 * @param bundle
	 *          The {@link Bundle} wrapped by this {@link EclipsePlugin}.
	 */
	public EclipsePlugin(Bundle bundle) {

		super();

		this.bundle = bundle;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Component#getConnectors()
	 */
	public List<Connector> getConnectors() {

		List<Connector> connectors = new ArrayList<Connector>();
		connectors.addAll(this.getExtensionPoints());
		connectors.addAll(this.getExtensions());
		return connectors;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Component#getId()
	 */
	public String getId() {

		return this.bundle.getSymbolicName();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Component#getResource(java.lang.String)
	 */
	public URL getResource(String adr) {

		return this.getBundle().getEntry(adr);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Component#loadClass(java.lang.String)
	 */
	public Class<?> loadClass(String className) throws ClassNotFoundException {

		return this.bundle.loadClass(className);
	}

	/**
	 * <p>
	 * Adds a given {@link EclipseExtension} to this {@link EclipsePlugin}.
	 * </p>
	 * 
	 * @param eclipseExtension
	 *          The {@link EclipseExtension} that shall be added.
	 */
	public void addExtension(EclipseExtension eclipseExtension) {

		this.extensions.add(eclipseExtension);
	}

	/**
	 * <p>
	 * Adds a given {@link EclipseExtensionPoint} to this {@link EclipsePlugin}.
	 * </p>
	 * 
	 * @param eclipseExtensionPoint
	 *          The {@link EclipseExtensionPoint} that shall be added.
	 */
	public void addExtensionPoint(EclipseExtensionPoint eclipseExtensionPoint) {

		this.extensionPoints.add(eclipseExtensionPoint);
	}

	/**
	 * <p>
	 * Returns the {@link Bundle} this {@link EclipsePlugin} wraps.
	 * </p>
	 * 
	 * @return The {@link Bundle} this {@link EclipsePlugin} wraps.
	 */
	public Bundle getBundle() {

		return this.bundle;
	}

	/**
	 * <p>
	 * Returns all {@link EclipseExtension}s of this {@link EclipsePlugin}.
	 * </p>
	 * 
	 * @return All {@link EclipseExtension}s of this {@link EclipsePlugin}.
	 */
	public List<EclipseExtension> getExtensions() {

		return new ArrayList<EclipseExtension>(this.extensions);
	}

	/**
	 * <p>
	 * Returns all {@link EclipseExtensionPoint}s of this {@link EclipsePlugin}.
	 * </p>
	 * 
	 * @return All {@link EclipseExtensionPoint}s of this {@link EclipsePlugin}.
	 */
	public List<EclipseExtensionPoint> getExtensionPoints() {

		return new ArrayList<EclipseExtensionPoint>(this.extensionPoints);
	}

	/**
	 * <p>
	 * Returns a {@link List} of {@link Contract}s provided by this
	 * {@link EclipseExtensionPoint} and instantiated by plug-ins providing
	 * extensions.
	 * </p>
	 * 
	 * @return A {@link List} of {@link Contract}s.
	 */
	public List<Contract> getInstantiatedContracts() {

		List<Contract> result;
		result = new ArrayList<Contract>();

		for (EclipseExtensionPoint eclipseExtensionPoint : this
				.getExtensionPoints()) {

			for (EclipseExtension eclipseExtension : eclipseExtensionPoint
					.getExtensions()) {

				for (Contract contract : eclipseExtension.getContracts()) {

					if (contract.isInstantiated()) {
						result.add(contract);
					}
					// no else.
				}
				// end for (iteration on contracts).
			}
			// end for (iteration on extensions).
		}
		// end for (iteration on extension points).

		return result;
	}

	/**
	 * <p>
	 * Returns, whether or not this {@link EclipsePlugin} has
	 * {@link EclipseExtensionPoint}s that are contracted.
	 * </p>
	 * 
	 * @return <code>true</code> if this {@link EclipsePlugin} has
	 *         {@link EclipseExtensionPoint}s that are contracted.
	 */
	public boolean hasContracts() {

		boolean result;
		result = false;

		for (EclipseExtensionPoint eclipseExtensionPoint : this.extensionPoints) {

			if (eclipseExtensionPoint.hasContracts()) {
				result = true;
				break;
			}
			// no else.
		}
		// end for.

		return result;
	}

	/**
	 * <p>
	 * Removes a given {@link EclipseExtension} from this {@link EclipsePlugin}.
	 * </p>
	 * 
	 * @param eclipseExtension
	 *          The {@link EclipseExtension} that shall be removed.
	 * @return <code>true</code> if the {@link EclipseExtension} has been removed.
	 */
	public boolean removeExtension(EclipseExtension eclipseExtension) {

		return this.extensions.remove(eclipseExtension);
	}

	/**
	 * <p>
	 * Removes a given {@link EclipseExtensionPoint} to this {@link EclipsePlugin}
	 * .
	 * </p>
	 * 
	 * @param eclipseExtensionPoint
	 *          The {@link EclipseExtensionPoint} that shall be removed.
	 * @return <code>true</code> if the {@link EclipseExtensionPoint} has been
	 *         removed.
	 */
	public boolean removeExtensionPoint(
			EclipseExtensionPoint eclipseExtensionPoint) {

		return this.extensionPoints.remove(eclipseExtensionPoint);
	}

	/**
	 * <p>
	 * Sets the {@link Bundle} this {@link EclipsePlugin} wraps.
	 * </p>
	 * 
	 * @param bundle
	 *          The {@link Bundle} this {@link EclipsePlugin} wraps.
	 */
	public void setBundle(Bundle bundle) {

		this.bundle = bundle;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return new StringBuffer().append(this.getClass().getName()).append('(')
				.append(getId()).append(')').toString();
	}
}