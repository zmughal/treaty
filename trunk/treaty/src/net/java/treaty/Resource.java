/*
 * Copyright (C) 2008 Jens Dietrich
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
 * Represents resources. A resource can be a variable. In this case, the ref attribute describes how to bind the
 * variable for a given extension. Binding yields a name - the raw string value of this resource. A value is obtained by 
 * loading the resource. The ref attribute points to data within a meta data file (such as plugin.xml in eclipse plugins)
 * of the component. This could be a simple variable name or a complex path expression.
 * <br>
 * For instance, a resource might only have a ref attribute with the value "class".
 * Given an extension, we would obtain the name com.example.MyClass by looking up the "class" attribute in
 * the plugin.xml of an extension. Then we would obtain the value com.example.MyClass (a class, not a string) 
 * by loading the class (we know that it is a class from teh type attribute).    
 * @author Jens Dietrich
 */

public class Resource implements Visitable {

	private String id = null;
	private URI type = null;
	private String name = null;
	private String ref = null;
	private Object value = null;
	private Connector owner = null;
	private boolean provided = true; // in case this is an optional parameter not actually provided by an extension
	
	public Resource() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public URI getType() {
		return type;
	}

	public void setType(URI type) {
		this.type = type;
	}
	
	public boolean isInstantiated() {
		return name!=null;
	}
	public boolean isLoaded() {
		return value!=null;
	}

	public void accept(ContractVisitor visitor) {
		visitor.visit(this);
	}


	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String toString() {
		StringBuffer b =  new StringBuffer()
			.append(this.isInstantiated()?"":"?")
			.append(this.isInstantiated()?this.getName():this.getRef())
			.append(':')
			.append(this.getType());
		return b.toString();
	}

	public boolean isProvided() {
		return provided;
	}

	public void setProvided(boolean v) {
		this.provided = v;
	}

	public Connector getOwner() {
		return owner;
	}

	public void setOwner(Connector owner) {
		this.owner = owner;
	}
	/**
	 * Instantiate a resource. This will replace the variable by a name.
	 * @param connector the connector (e.g. extension) 
	 * @param mgr the resource manager
	 * @return the instantiated resource or null in case the resource is optional and
	 * is not supplied by the connector
	 * @throws TreatyException if the resource cannot be instantiated
	 */
	public Resource instantiate (Connector connector,ResourceManager mgr) throws ResourceLoaderException {

		assert value== null;
		Resource r = new Resource();
		r.setId(this.getId());
		r.setType(this.getType());
		r.setRef(this.getRef());
		r.setOwner(connector);
		String name = mgr.resolve(getType(),getRef(),connector);
		if (name==null) {
			r.setProvided(false);
		}
		else {
			r.setName(name);
		}
		return r;
	}



}
