/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package nz.ac.massey.treaty;

import java.net.URI;

import nz.ac.massey.treaty.verification.ResourceLoader;
import nz.ac.massey.treaty.verification.VerificationException;

import org.java.plugin.Plugin;
import org.java.plugin.registry.Extension;
import org.java.plugin.registry.Extension.Parameter;

/**
 * Represents resources. A resource can be a variable. In this case, the ref attribute describes how to bind the
 * variable for a given extension. Binding yields a name - the raw string value of this resource. A value is obtained by 
 * loading the resource. The ref attribute points to data within plugin.xml (here this is simply the variable
 * name, but could also be an xpath expression).
 * <br>
 * For instance, a resource might only have a ref attribute with the value "class".
 * Given an extension, we would obtain the name com.example.MyClass by looking up the "class" attribute in
 * the plugin.xml of an extension. Then we would obtain the value com.example.MyClass (a class, not a string) 
 * by loading the class (we know that it is a class from teh type attribute).    
 * @author Jens Dietrich
 * @version 0.1 <27/04/2008>
 * @since 0.1
 */

public class Resource implements Visitable {

	private String id = null;
	private URI type = null;
	private String name = null;
	private String ref = null;
	private Object value = null;
	private boolean ownedByExtensionpoint = true;
	private boolean notProvidedByExtension = false; // in case this is an optional parameter not actually provided by an extension
	
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
	
	public boolean isResolved() {
		return name!=null;
	}
	public boolean isLoaded() {
		return value!=null;
	}

	public void accept(ContractVisitor visitor) {
		visitor.visit(this);
	}

	public boolean isOwnedByExtensionpoint() {
		return ownedByExtensionpoint;
	}

	public void setOwnedByExtensionpoint(boolean ownedByExtensionpoint) {
		this.ownedByExtensionpoint = ownedByExtensionpoint;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	public Resource bind (Extension extension,Plugin plugin,ResourceLoader loader) throws InvalidContractException {
		assert name == null;
		assert value== null;
		assert !this.isOwnedByExtensionpoint();
		Resource r = new Resource();
		r.setId(this.getId());
		r.setType(this.getType());
		Parameter param = extension.getParameter(this.getRef());
		r.setRef(this.getRef());
		r.setOwnedByExtensionpoint(false);
		if (param==null)
			r.setNotProvidedByExtension(true);
		else {
			r.setName(param.rawValue());
			r.resolve(plugin,loader);
		}
		return r;
	}
	public void resolve (Plugin plugin,ResourceLoader loader) throws InvalidContractException {
		if (this.value==null) {
			assert name != null;
			try {
				setValue(loader.load(getType(),getName(),plugin));
			} catch (VerificationException e) {
				throw new InvalidContractException("Error loading resource value " + getName() + " for type " + getType(),e);
			}
		}
	}
	public String toString() {
		StringBuffer b =  new StringBuffer()
			.append(this.isResolved()?"":"?")
			.append(this.isResolved()?this.getName():this.getRef())
			.append(':')
			.append(this.getType());
		return b.toString();
	}

	public boolean isNotProvidedByExtension() {
		return notProvidedByExtension;
	}

	public void setNotProvidedByExtension(boolean notProvidedByExtension) {
		this.notProvidedByExtension = notProvidedByExtension;
	}
	

}
