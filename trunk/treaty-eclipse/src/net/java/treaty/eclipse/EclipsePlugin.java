package net.java.treaty.eclipse;

/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

import java.net.URL;
import java.util.ArrayList;
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
	private List<EclipseExtensionPoint> extensionPoints =
			new ArrayList<EclipseExtensionPoint>();

	/** The {@link EclipseExtension}s of this {@link EclipsePlugin}. */
	private List<EclipseExtension> extensions = new ArrayList<EclipseExtension>();

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

	/**
	 * <p>
	 * Returns a {@link List} of instantiated {@link Contract}s (instantiated by
	 * plug-ins providing extensions).
	 * </p>
	 * 
	 * @return A {@link List} of instantiated {@link Contract}s (instantiated by
	 *         plug-ins providing extensions).
	 */
	public List<Contract> getInstantiatedContracts() {

		List<Contract> result;
		result = new ArrayList<Contract>();

		for (EclipseExtensionPoint eclipseExtensionPoint : this
				.getExtensionPoints()) {

			for (EclipseExtension eclipseExtension : eclipseExtensionPoint
					.getExtensions()) {

				for (Contract contract : eclipseExtension.getContracts()) {

					/* Check if the contract is instantiated. */
					if (contract != null/* && c.isInstantiated() */) {
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

	void addExtension(EclipseExtension x) {

		this.extensions.add(x);
	}

	void addExtensionPoint(EclipseExtensionPoint xp) {

		this.extensionPoints.add(xp);
	}

	public String getId() {

		return this.bundle.getSymbolicName();
	}

	public Bundle getBundle() {

		return bundle;
	}

	public void setBundle(Bundle bundle) {

		this.bundle = bundle;
	}

	public URL getResource(String adr) {

		return this.getBundle().getEntry(adr);
	}

	public boolean hasContracts() {

		for (EclipseExtensionPoint xp : this.extensionPoints) {
			if (xp.hasContracts())
				return true;
		}
		return false;
	}

	public List<EclipseExtensionPoint> getExtensionPoints() {

		return extensionPoints;
	}

	public List<EclipseExtension> getExtensions() {

		return extensions;
	}

	public List<Connector> getConnectors() {

		List<Connector> connectors = new ArrayList<Connector>();
		connectors.addAll(this.getExtensionPoints());
		connectors.addAll(this.getExtensions());
		return connectors;
	}

	@Override
	public String toString() {

		return new StringBuffer().append(this.getClass().getName()).append('(')
				.append(getId()).append(')').toString();
	}

}
