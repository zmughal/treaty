/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package test.net.java.treaty.mocks;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.java.treaty.Component;
import net.java.treaty.Connector;
import net.java.treaty.contractregistry.ContractRegistry;

/**
 * <p>
 * A mock implementation of {@link Component} to test the
 * {@link ContractRegistry}.
 * </p>
 * 
 * @author Claas Wilke
 */
public class ComponentMock implements Component {

	/** The {@link Connector}s of this {@link ComponentMock}. */
	private List<Connector> myConnectors = new ArrayList<Connector>();

	/** The ID of this {@link ComponentMock}. */
	private String myID;

	/**
	 * <p>
	 * Creates a new {@link ComponentMock}.
	 * </p>
	 * 
	 * @param id
	 *          The ID of the {@link ComponentMock}.
	 */
	public ComponentMock(String id) {

		this.myID = id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Component#getConnectors()
	 */
	public List<Connector> getConnectors() {

		return this.myConnectors;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Component#getId()
	 */
	public String getId() {

		return this.myID;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Component#getResource(java.lang.String)
	 */
	public URL getResource(String adr) {

		/* A ComponentMock cannot have resources. */
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Annotatable#getProperty(java.lang.String)
	 */
	public Object getProperty(String key) {

		/* A ComponentMock cannot have properties. */
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Annotatable#getPropertyNames()
	 */
	public Iterator<String> getPropertyNames() {

		return new ArrayList<String>().iterator();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Component#loadClass(java.lang.String)
	 */
	public Class<?> loadClass(String className) throws ClassNotFoundException {

		/* The ComponentMock cannot load classes. */
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Annotatable#removeProperty(java.lang.String)
	 */
	public Object removeProperty(String key) {

		/* A ComponentMock cannot have properties. */
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Annotatable#setProperty(java.lang.String,
	 * java.lang.Object)
	 */
	public void setProperty(String key, Object value) {

		/* A ComponentMock cannot have properties. */
	}

	/**
	 * <p>
	 * Adds a given {@link Connector} to this {@link ComponentMock}.
	 * </p>
	 * 
	 * @param connector
	 *          The {@link Connector} that shall be added.
	 */
	public void addConnector(Connector connector) {

		if (!this.myConnectors.contains(connector)) {
			this.myConnectors.add(connector);
		}
		// no else.
	}

	/**
	 * <p>
	 * Removes a given {@link Connector} to this {@link ComponentMock}.
	 * </p>
	 * 
	 * @param connector
	 *          The {@link Connector} that shall be removed.
	 */
	public void removeConnector(Connector connector) {

		this.myConnectors.remove(connector);
	}
}