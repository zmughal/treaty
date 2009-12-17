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

import org.jdom.Element;

import net.java.treaty.InstantiationContext;

/**
 * <p>
 * The {@link EclipseInstantiationContext} is a wrapper around an node in a
 * <code>plugin.xml</code>. It will be used as a context node when evaluating
 * xpath expressions defining resource references.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class EclipseInstantiationContext implements InstantiationContext {

	/** The wrapped context node. */
	private Element contextNode = null;

	/** The path of the wrapped context node. */
	private String path = null;

	/**
	 * <p>
	 * Creates a new {@link EclipseInstantiationContext}.
	 * </p>
	 * 
	 * @param contextNode
	 *          The wrapped context node.
	 * @param path
	 *          The path of the wrapped context node.
	 */
	public EclipseInstantiationContext(Element contextNode, String path) {

		super();

		this.contextNode = contextNode;
		this.path = path;
	}

	/**
	 * <p>
	 * Returns the wrapped context node.
	 * </p>
	 * 
	 * @return The wrapped context node.
	 */
	public Element getContextNode() {

		return this.contextNode;
	}

	/**
	 * <p>
	 * Returns the path of the wrapped context node.
	 * </p>
	 * 
	 * @return The path of the wrapped context node.
	 */
	public String getPath() {

		return this.path;
	}

	/**
	 * <p>
	 * Sets the wrapped context node.
	 * </p>
	 * 
	 * @return The wrapped context node.
	 */
	public void setContextNode(Element contextNode) {

		this.contextNode = contextNode;
	}

	/**
	 * <p>
	 * Sets the path of the wrapped context node.
	 * </p>
	 * 
	 * @return The path of the wrapped context node.
	 */
	public void setPath(String path) {

		this.path = path;
	}

	public String toString() {

		return super.toString() + '(' + path + ')';
	}
}