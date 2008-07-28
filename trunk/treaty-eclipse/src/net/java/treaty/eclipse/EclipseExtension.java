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

/**
 * Represents eclipse extensions. This is a wrapper class.
 * @author Jens Dietrich
 */

public class EclipseExtension extends EclipseConnector {
	
	private IExtension extension = null;
	
	public EclipseExtension(EclipsePlugin owner, IExtension extension) {
		super(owner);
		this.extension=extension;
		loadContract ();
		if (owner!=null) {
			owner.addExtension(this);
		}
	}

	@Override
	public ConnectorType getType() {
		return ConnectorType.SUPPLIER;
	}

	@Override
	public String getId() {
		return this.extension.getUniqueIdentifier();
	}


	

}
