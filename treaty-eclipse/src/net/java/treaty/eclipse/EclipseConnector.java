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

import java.io.IOException;
import java.net.URL;
import net.java.treaty.Component;
import net.java.treaty.Connector;
import net.java.treaty.Contract;
import net.java.treaty.SimpleContract;
import net.java.treaty.TreatyException;
import net.java.treaty.ContractReader;

/**
 * Abstract superclass for extensions and extension points.
 * @author Jens Dietrich
 */

public abstract class EclipseConnector implements Connector {
	
	public static String getContractLocation(Connector c) {
		return "/META-INF/"+c.getId()+".contract";
	}

	private EclipsePlugin owner = null;
	protected Contract contract = null;

	public Component getOwner() {
		return owner;
	}

	public void setOwner(EclipsePlugin owner) {
		this.owner = owner;
	}

	public EclipseConnector(EclipsePlugin owner) {
		super();
		this.owner = owner;
	}

	public Contract getContract() {
		return contract;
	}
	
	protected Contract loadContract () {
		EclipsePlugin owner = (EclipsePlugin)this.getOwner();
		if (owner!=null) {
			URL url = owner.getResource(getContractLocation(this));
			if (url!=null) {
				Logger.info("contract url found " + url);
				ContractReader reader = new ContractReader(new EclipseResourceManager());
				try {
					SimpleContract contract = reader.read(url.openStream());
					contract.setLocation(url);
					return contract;
				} catch (TreatyException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
		}
		return null;
	}

}
