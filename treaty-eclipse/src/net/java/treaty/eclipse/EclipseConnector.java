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

import net.java.treaty.AggregatedContract;
import net.java.treaty.Component;
import net.java.treaty.Connector;
import net.java.treaty.Contract;
import net.java.treaty.PropertySupport;
import net.java.treaty.SimpleContract;
import net.java.treaty.TreatyException;
import net.java.treaty.ContractReader;

/**
 * Abstract superclass for extensions and extension points.
 * @author Jens Dietrich
 */

public abstract class EclipseConnector extends PropertySupport implements Connector {
	
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
	
	public void addContract (URL url,EclipseConnector contractOwner) {
		SimpleContract newContract = null; 
		if (owner!=null) {
			if (url!=null) {
				Logger.info("Loading contract from " + url);
				ContractReader reader = new ContractReader(new EclipseResourceManager());
				try {
					newContract = reader.read(url.openStream());
					newContract.setLocation(url);
					if (contractOwner!=null) {
						newContract.setOwner(contractOwner);
					}
					configureNewContract(newContract);
				} catch (TreatyException e) {
					Logger.error("Exception loading contract from " + url,e);
				} catch (IOException e) {
					Logger.error("Exception loading contract from " + url,e);
				}	
			}
		}
		if (contract==null) {
			contract = newContract;
		}
		else {
			// contract aggregation
			Logger.info("Aggregating contracts " + url + " and " + this.contract);
			this.contract = new AggregatedContract(this.contract,newContract);
			
		}
	}

	protected void configureNewContract(SimpleContract newContract) {
		// by default nothing to do here
	}

}
