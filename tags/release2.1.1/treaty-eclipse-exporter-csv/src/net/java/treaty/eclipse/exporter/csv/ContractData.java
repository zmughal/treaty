/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.exporter.csv;

import java.util.List;

/**
 * <p>
 * Utility class to hold contract data.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class ContractData {

	/** The bindings of this contract. */
	public List<String> bindings = null;

	/** The condition results of this contract. */
	public List<String> conditionResults = null;

	/** The verification exception if any. */
	public String exception = null;

	/** The name of the contracted extension point. */
	public String extensionPointName = null;

	/** The ID of the contracted extension point. */
	public String extensionPointId = null;

	/**
	 * TODO Claas: Contracts cannot be aggregated anymore.
	 * 
	 * Indicates whether or not the contract is aggregated.
	 */
	public boolean isAggregated = false;

	/**
	 * TODO Claas: Contracts cannot be aggregated anymore.
	 * 
	 * The part number if this part belongs to an aggregated contract.
	 */
	public int partNo = 1;

	/** The ID of the contracted plug-in. */
	public String pluginId = null;

	/** The verification result of the Contract. */
	public String verificationResult = null;

	/**
	 * <p>
	 * Returns the bindings of this contract.
	 * </p>
	 * 
	 * @return The bindings of this contract.
	 */
	public List<String> getBindings() {

		return bindings;
	}

	/**
	 * <p>
	 * The condition results of this contract.
	 * </p>
	 * 
	 * @return The condition results of this contract.
	 */
	public List<String> getConditionResults() {

		return this.conditionResults;
	}

	/**
	 * <p>
	 * Returns the verification exception if any.
	 * </p>
	 * 
	 * @return The verification exception if any.
	 */
	public String getException() {

		return this.exception;
	}

	/**
	 * <p>
	 * Returns the ID of the contracted extension point.
	 * </p>
	 * 
	 * @return The ID of the contracted extension point.
	 */
	public String getExtensionPointId() {

		return this.extensionPointId;
	}

	/**
	 * <p>
	 * Returns the name of the contracted extension point.
	 * </p>
	 * 
	 * @return The name of the contracted extension point.
	 */
	public String getExtensionPointName() {

		return this.extensionPointName;
	}

	/**
	 * <p>
	 * Returns the part number if this part belongs to an aggregated contract.
	 * </p>
	 * 
	 * @return The part number if this part belongs to an aggregated contract.
	 */
	public int getPartNo() {

		return this.partNo;
	}

	/**
	 * <p>
	 * Returns the ID of the contracted plug-in.
	 * </p>
	 * 
	 * @return The ID of the contracted plug-in.
	 */
	public String getPluginId() {

		return this.pluginId;
	}

	/**
	 * <p>
	 * Returns the verification result of the Contract.
	 * </p>
	 * 
	 * @return The verification result of the Contract.
	 */
	public String getVerificationResult() {

		return this.verificationResult;
	}

	/**
	 * <p>
	 * Indicates whether or not the contract is aggregated.
	 * </p>
	 * 
	 * @return Whether or not the contract is aggregated.
	 */
	public boolean isAggregated() {

		return this.isAggregated;
	}
}