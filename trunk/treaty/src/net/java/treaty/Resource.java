/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty;

import java.net.URI;

/**
 * <p>
 * Represents resources. A resource can be a variable. In this case, the ref
 * attribute describes how to bind the variable for a given extension. Binding
 * yields a name - the raw string value of this resource. A value is obtained by
 * loading the resource. The ref attribute points to data within a meta data
 * file (such as plugin.xml in eclipse plugins) of the component. This could be
 * a simple variable name or a complex path expression. <br>
 * For instance, a resource might only have a ref attribute with the value
 * "class". Given an extension, we would obtain the name com.example.MyClass by
 * looking up the "class" attribute in the plugin.xml of an extension. Then we
 * would obtain the value com.example.MyClass (a class, not a string) by loading
 * the class (we know that it is a class from teh type attribute).
 * </p>
 * 
 * @author Jens Dietrich
 */
public class Resource extends PropertySupport implements Visitable, Annotatable {

	/**
	 * The indicates if this {@link Resource} is provided by its owner, if the
	 * {@link Resource} is optional.
	 */
	private boolean isProvided = true;

	/** The {@link InstantiationContext} of this {@link Resource}. */
	private InstantiationContext myContext = null;

	/** The ID of this {@link Resource}. */
	private String myId = null;

	/** The name of this {@link Resource}. */
	private String myName = null;

	/** The owner ({@link Connector}) of this {@link Resource}. */
	private Connector myOwner = null;

	/** The Type (as a {@link URI}) of this {@link Resource}. */
	private URI myType = null;

	/** The value of this {@link Resource}. */
	private Object myValue = null;

	/**
	 * <p>
	 * Creates a new {@link Resource}.
	 * </p>
	 */
	public Resource() {

		super();
	}

	/**
	 * <p>
	 * Instantiate a resource. This will replace the variable by a name.
	 * </p>
	 * 
	 * @param connector
	 *          The connector (e.g. extension) which provides the {@link Resource}
	 *          .
	 * @param context
	 *          The {@link InstantiationContext}.
	 * @param mgr
	 *          The {@link ResourceManager} used.
	 * @return The instantiated {@link ResourceLoaderException} or
	 *         <code>null</code> in case the {@link Resource} is optional and is
	 *         not supplied by the connector.
	 * @throws TreatyException
	 *           Thrown, if the {@link Resource} cannot be instantiated.
	 */

	public Resource instantiate(Connector connector,
			InstantiationContext context, ResourceManager mgr)
			throws ResourceLoaderException {

		assert this.myValue == null;

		Resource result;
		result = new Resource();

		result.setId(this.getId());
		result.setType(this.getType());
		result.setRef(this.getRef());
		result.setOwner(connector);
		result.setContext(context);

		/* Check if a reference exists. */
		if (this.getRef() == null) {

			/* If no name exists as well, throw an exception. */
			if (this.getName() == null) {
				throw new ResourceLoaderException("Cannot instantiate " + this
						+ ", neither a refertence nor a reusable name exists.");
			}

			/* Else use the existing name. */
			else {
				result.setName(this.myName);
			}
			/*
			 * This means that we can "re-instantiate" resources that already have a
			 * name. This makes sense as the reference may resolve to a new name.
			 */
		}

		/* Else try to resolve the resource. */
		else {
			String name;
			name = mgr.resolve(this.getType(), this.getRef(), connector, context);

			if (name == null) {
				result.setProvided(false);
			}

			else {
				result.setName(name);
			}
			// end else.
		}
		// end else.

		return result;
	}

	private String ref = null;

	public String getId() {

		return myId;
	}

	public void setId(String id) {

		this.myId = id;
	}

	public String getName() {

		return myName;
	}

	public void setName(String name) {

		this.myName = name;
	}

	public String getRef() {

		return ref;
	}

	public void setRef(String ref) {

		this.ref = ref;
	}

	public URI getType() {

		return myType;
	}

	public void setType(URI type) {

		this.myType = type;
	}

	public InstantiationContext getContext() {

		return myContext;
	}

	public void setContext(InstantiationContext context) {

		this.myContext = context;
	}

	public boolean isInstantiated() {

		return myName != null;
	}

	public boolean isLoaded() {

		return myValue != null;
	}

	public void accept(ContractVisitor visitor) {

		visitor.visit(this);
	}

	public Object getValue() {

		return myValue;
	}

	public void setValue(Object value) {

		this.myValue = value;
	}

	public String toString() {

		StringBuffer b =
				new StringBuffer().append(this.isInstantiated() ? "" : "?").append(
						this.isInstantiated() ? this.getName() : this.getRef()).append(':')
						.append(this.getType());
		return b.toString();
	}

	public boolean isProvided() {

		return isProvided;
	}

	public void setProvided(boolean v) {

		this.isProvided = v;
	}

	public Connector getOwner() {

		return myOwner;
	}

	public void setOwner(Connector owner) {

		this.myOwner = owner;
	}

}
