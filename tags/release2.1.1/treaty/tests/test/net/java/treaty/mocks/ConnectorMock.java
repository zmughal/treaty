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

import java.util.ArrayList;
import java.util.Iterator;

import net.java.treaty.Component;
import net.java.treaty.Connector;
import net.java.treaty.ConnectorType;

/**
 * <p>
 * A mock implementation of {@link Connector} to test the
 * {@link ContractRegistry}.
 * </p>
 * 
 * @author Claas Wilke
 */
public class ConnectorMock implements Connector {

	/** The ID of this {@link ConnectorMock}. */
	private String myID;

	/** The {@link Component} of this {@link ConnectorMock}. */
	private Component myOwner;

	/** The {@link ConnectorType} of this {@link ConnectorMock}. */
	private ConnectorType myType;

	/**
	 * <p>
	 * Creates a new {@link Connector}.
	 * </p>
	 * 
	 * @param id
	 *          The ID of the {@link Connector}.
	 * @param owner
	 *          The {@link Component} of the {@link ConnectorMock}.
	 * @param type
	 *          The {@link ConnectorType} of the {@link ConnectorMock}.
	 */
	public ConnectorMock(String id, Component owner, ConnectorType type) {

		this.myID = id;
		this.myOwner = owner;
		this.myType = type;

		((ComponentMock) this.myOwner).addConnector(this);
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
	 * @see net.java.treaty.Connector#getOwner()
	 */
	public Component getOwner() {

		return this.myOwner;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Annotatable#getProperty(java.lang.String)
	 */
	public Object getProperty(String key) {

		/* The mock cannot have properties. */
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Annotatable#getPropertyNames()
	 */
	public Iterator<String> getPropertyNames() {

		/* The mock cannot have properties. */
		return new ArrayList<String>().iterator();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Connector#getType()
	 */
	public ConnectorType getType() {

		return this.myType;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Annotatable#removeProperty(java.lang.String)
	 */
	public Object removeProperty(String key) {

		/* The mock cannot have properties. */
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Annotatable#setProperty(java.lang.String,
	 * java.lang.Object)
	 */
	public void setProperty(String key, Object value) {

		/* The mock cannot have properties. */
	}
}