/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.contractregistry;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import net.java.treaty.Connector;
import net.java.treaty.ConnectorType;
import net.java.treaty.Contract;
import net.java.treaty.ResourceManager;
import net.java.treaty.Role;
import net.java.treaty.TreatyException;
import net.java.treaty.event.LifeCycleEvent;

/**
 * <p>
 * The {@link ContractRegistry} manages dynamic add and remove of
 * {@link Contract}s and contracted {@link Connector}s. It can be used to select
 * all {@link Contract}s that must be verified after the occurrence of a
 * specific {@link LifeCycleEvent}.
 * </p>
 * 
 * @author Claas Wilke
 */
public class ContractRegistry {

	/**
	 * The different {@link UpdateType} that can be used to update this
	 * {@link ContractRegistry}.
	 */
	public enum UpdateType {
		ADD_CONTRACT, REMOVE_CONTRACT
	};

	/**
	 * All {@link Contract}s in respect to the {@link Connector} playing the
	 * {@link Role#CONSUMER} role.
	 */
	protected Map<Connector, LinkedHashSet<Contract>> myConsumerContracts =
			new HashMap<Connector, LinkedHashSet<Contract>>();

	/**
	 * All {@link Contract}s in respect to the {@link Connector} defining them
	 * (the {@link Role#LEGISLATOR}).
	 */
	protected Map<Connector, LinkedHashSet<Contract>> myLegislatorContracts =
			new HashMap<Connector, LinkedHashSet<Contract>>();

	/**
	 * All {@link Contract}s in respect to the {@link Connector} playing the
	 * {@link Role#SUPPLIER} role.
	 */
	protected Map<Connector, LinkedHashSet<Contract>> mySupplierContracts =
			new HashMap<Connector, LinkedHashSet<Contract>>();

	/**
	 * All {@link Contracts}s that are instances of a specific {@link Contract}s
	 */
	protected Map<Contract, HashSet<Contract>> instantiatedContracts =
			new HashMap<Contract, HashSet<Contract>>();

	/**
	 * Contains {@link Connector}s that are contracted by {@link Contract}s that
	 * are instances of other {@link Contract}s. This {@link Map} is required to
	 * remove all instantiated {@link Contract}s as well, if a parent
	 * {@link Contract} shall be removed.
	 */
	protected Map<Contract, HashSet<Connector>> contractedConnectors =
			new HashMap<Contract, HashSet<Connector>>();

	/**
	 * Boolean flag to indicate whether or not the {@link ContractRegistry} has
	 * been changed since the last notification of its
	 * {@link ContractRegistryListener}s.
	 */
	private boolean hasChanged;

	/** The {@link ContractRegistryListener}s of this {@link ContractRegistry}. */
	private Set<ContractRegistryListener> myListeners =
			new HashSet<ContractRegistryListener>();

	/** The current {@link ResourceManager} of this {@link ContractRegistry}. */
	private ResourceManager myResourceManager;

	/**
	 * <p>
	 * Creates a new {@link ContractRegistry}.
	 * </p>
	 * 
	 * @param resourceManager
	 *          The {@link ResourceManager} the {@link ContractRegistry} should
	 *          use to bind {@link Contract}s.
	 */
	public ContractRegistry(ResourceManager resourceManager) {

		this.myResourceManager = resourceManager;
	}

	/**
	 * <p>
	 * Adds a given {@link ContractRegistryListener} to this
	 * {@link ContractRegistry}.
	 * </p>
	 * 
	 * @param contractRegistryListener
	 *          The {@link ContractRegistryListener} that shall be added.
	 */
	public void addContractRegistryListener(
			ContractRegistryListener contractRegistryListener) {

		this.myListeners.add(contractRegistryListener);
	}

	/**
	 * <p>
	 * Returns a {@link Set} of all {@link Contract}s that must be validated after
	 * a given {@link LifeCycleEvent} occurred.
	 * </p>
	 * 
	 * @return A {@link Set} of all {@link Contract}s that must be validated after
	 *         a given {@link LifeCycleEvent} occurred.
	 */
	public Set<Contract> getAffectedContracts(LifeCycleEvent lifeCycleEvent) {

		Set<Contract> result;
		result = new LinkedHashSet<Contract>();

		/* Iterate on all connectors of the given component. */
		for (Connector connector : lifeCycleEvent.getComponent().getConnectors()) {

			switch (connector.getType()) {

			case CONSUMER:

				if (this.myConsumerContracts.containsKey(connector)) {

					for (Contract contract : this.myConsumerContracts.get(connector)) {

						result.addAll(this.getInstantiatedContracts(contract));
					}
					// end for.
				}
				// no else.

			case SUPPLIER:

				if (this.mySupplierContracts.containsKey(connector)) {

					for (Contract contract : this.mySupplierContracts.get(connector)) {

						result.addAll(this.getInstantiatedContracts(contract));
					}
					// end for.
				}
				// no else.

				break;
			}
			// end switch.
		}

		return result;
	}

	/**
	 * <p>
	 * Returns all {@link Contract}s of a given {@link Connector} for a given
	 * {@link Role} the {@link Connector} is playing.
	 * </p>
	 * 
	 * @param connector
	 *          The {@link Connector} whose {@link Contract}s shall be returned.
	 * @param role
	 *          The {@link Role} the {@link Connector} shall play in the
	 *          {@link Contract}s returned.
	 * @return All {@link Contract}s of a given {@link Connector} for a given
	 *         {@link Role} the {@link Connector} is playing.
	 */
	public Set<Contract> getContracts(Connector connector, Role role) {

		Set<Contract> result;
		result = new LinkedHashSet<Contract>();

		switch (role) {

		case CONSUMER:

			if (this.myConsumerContracts.containsKey(connector)) {

				try {
					result.addAll(this.myConsumerContracts.get(connector));
				}

				catch (ConcurrentModificationException e) {
					/*
					 * FIXME Claas: Probably log this problem. Else remove try after
					 * debugging.
					 */
					e.printStackTrace();
				}
			}
			// no else.

			break;

		case SUPPLIER:

			result = new LinkedHashSet<Contract>();

			if (this.mySupplierContracts.containsKey(connector)) {

				try {
					result.addAll(this.mySupplierContracts.get(connector));
				}

				catch (ConcurrentModificationException e) {
					/*
					 * FIXME Claas: Probably log this problem. Else remove try after
					 * debugging.
					 */
					e.printStackTrace();
				}
			}
			// no else.

			break;

		case LEGISLATOR:

			result = new LinkedHashSet<Contract>();

			if (this.myLegislatorContracts.containsKey(connector)) {

				try {
					result.addAll(this.myLegislatorContracts.get(connector));
				}

				catch (ConcurrentModificationException e) {
					/*
					 * FIXME Claas: Probably log this problem. Else remove try after
					 * debugging.
					 */
					e.printStackTrace();
				}
			}
			// no else.

			break;
		}
		// end switch.

		return result;
	}

	/**
	 * <p>
	 * Removes a given {@link ContractRegistryListener} to this
	 * {@link ContractRegistry}.
	 * </p>
	 * 
	 * @param contractRegistryListener
	 *          The {@link ContractRegistryListener} that shall be removed.
	 */
	public void removeContractRegistryListener(
			ContractRegistryListener contractRegistryListener) {

		this.myListeners.remove(contractRegistryListener);
	}

	/**
	 * <p>
	 * Sets the {@link ResourceManager} the {@link ContractRegistry} should use to
	 * bind {@link Contract}s.
	 * </p>
	 * 
	 * @param resourceManager
	 *          The {@link ResourceManager} the {@link ContractRegistry} should
	 *          use to bind {@link Contract}s.
	 */
	public void setResourceManager(ResourceManager resourceManager) {

		this.myResourceManager = resourceManager;
	}

	/**
	 * <p>
	 * Adds or removes a given {@link Contract} for a given {@link Connector} to
	 * this {@link ContractRegistry}.
	 * </p>
	 * 
	 * <ul>
	 * <li>If a {@link Contract} shall be added to a {@link Role#LEGISLATOR}, the
	 * {@link Contract}s owner is set to the given {@link Role#LEGISLATOR}.</li>
	 * <li>If a {@link Contract} shall be added to a {@link Role#CONSUMER}, the
	 * {@link Contract} is bound and the bound {@link Contract}'s definition is
	 * set to the given {@link Contract}.</li>
	 * 
	 * <li>If a {@link Contract} shall be removed with a given
	 * {@link Role#LEGISLATOR}, the {@link Contract} will be removed for all
	 * Consumers and Suppliers as well.</li>
	 * </ul>
	 * 
	 * @param updateType
	 *          Indicates whether the {@link Contract} shall be added or removed.
	 * @param contract
	 *          The {@link Contract} that shall be updated.
	 * @param connector
	 *          The {@link Connector} for that the {@link Contract} shall be
	 *          updated.
	 * @param role
	 *          The {@link Role} the {@link Connector} is playing in the
	 *          {@link Contract}.
	 */
	public synchronized void updateContract(UpdateType updateType,
			Contract contract, Connector connector, Role role) throws TreatyException {

		switch (updateType) {

		case ADD_CONTRACT:

			switch (role) {

			case LEGISLATOR:

				this.addContractToLegislator(contract, connector);
				break;

			case CONSUMER:

				this.addContractToConsumer(contract, connector);
				break;

			case SUPPLIER:

				this.addContractToSupplier(contract, connector);
				break;
			}
			// end switch (role).

			break;

		case REMOVE_CONTRACT:

			switch (role) {

			case LEGISLATOR:

				this.removeContractFromLegislator(contract, connector);
				break;

			case CONSUMER:

				this.removeContractFromConsumer(contract, connector);
				break;

			case SUPPLIER:

				this.removeContractFromSupplier(contract, connector);
				break;
			}
			// end switch (role).

			break;
		}
		// end switch (updateType).

		this.notifyContractRegistryListeners();
	}

	/**
	 * <p>
	 * Enables the flag that this {@link ContractRegistry} has been changed.
	 * </p>
	 */
	protected void setChanged() {

		this.hasChanged = true;
	}

	/**
	 * <p>
	 * This method can be used to notify all registered
	 * {@link ContractRegistryListener}s of this {@link ContractRegistry} that the
	 * {@link ContractRegistry} has been changed.
	 * </p>
	 */
	protected void notifyContractRegistryListeners() {

		if (this.hasChanged) {

			for (ContractRegistryListener listener : this.myListeners) {

				listener.update();
			}
			// end for.

			this.hasChanged = false;
		}
		// no else.
	}

	/**
	 * <p>
	 * A helper method that adds a given {@link Contract} to a {@link Connector}
	 * playing the {@link Role#LEGISLATOR}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} that shall be added.
	 * @param connector
	 *          The {@link Connector} to that the {@link Contract} shall be added.
	 */
	private void addContractToLegislator(Contract contract, Connector connector) {

		LinkedHashSet<Contract> contracts;

		if (this.myLegislatorContracts.containsKey(connector)) {
			contracts = this.myLegislatorContracts.get(connector);
		}

		else {
			contracts = new LinkedHashSet<Contract>();
		}

		/* Set the legislator as owner of the contract. */
		contract.setOwner(connector);

		contracts.add(contract);
		this.myLegislatorContracts.put(connector, contracts);

		this.setChanged();
	}

	/**
	 * <p>
	 * A helper method that adds a given {@link Contract} to a {@link Connector}
	 * playing the {@link Role#CONSUMER}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} that shall be added.
	 * @param connector
	 *          The {@link Connector} to that the {@link Contract} shall be added.
	 * @throws TreatyException
	 *           Thrown, if the given {@link Connector} isn't of the
	 *           {@link ConnectorType#CONSUMER}.
	 */
	private void addContractToConsumer(Contract contract, Connector connector)
			throws TreatyException {

		LinkedHashSet<Contract> contracts;

		/* Check if the connector is a consumer at all. */
		if (connector.getType().equals(ConnectorType.CONSUMER)) {

			if (this.myConsumerContracts.containsKey(connector)) {
				contracts = this.myConsumerContracts.get(connector);
			}

			else {
				contracts = new LinkedHashSet<Contract>();
			}

			/* Check if a supplier has been set yet. */
			if (contract.getSupplier() != null || contract.getOwner() != null) {

				for (Contract boundContract : contract.bindConsumer(connector,
						this.myResourceManager)) {

					this.addInstantiatedContract(contract, boundContract);
					contracts.add(boundContract);
				}
				// end for.

				this.addContractedConnector(connector, contract);
			}

			/* Else just set the consumer and add the contract. */
			else {
				contract.setConsumer(connector);
				contracts.add(contract);
			}

			this.myConsumerContracts.put(connector, contracts);
			this.setChanged();
		}

		/* Else throw an exception. */
		else {
			throw new TreatyException(
					"Cannot add contract with Consumer Role to a Connector that"
							+ "is not of the ConnectorType Consumer.");
		}
		// end else.
	}

	/**
	 * <p>
	 * A helper method that adds a given {@link Contract} to a {@link Connector}
	 * playing the {@link Role#SUPPLIER}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} that shall be added.
	 * @param connector
	 *          The {@link Connector} to that the {@link Contract} shall be added.
	 * @throws TreatyException
	 *           Thrown, if the given {@link Connector} isn't of the
	 *           {@link ConnectorType#SUPPLIER}.
	 */
	private void addContractToSupplier(Contract contract, Connector connector)
			throws TreatyException {

		LinkedHashSet<Contract> contracts;

		/* Check if the connector is a supplier at all. */
		if (connector.getType().equals(ConnectorType.SUPPLIER)) {

			if (this.mySupplierContracts.containsKey(connector)) {
				contracts = this.mySupplierContracts.get(connector);
			}

			else {
				contracts = new LinkedHashSet<Contract>();
			}

			/* Check if a consumer has been set yet. */
			if (contract.getConsumer() != null || contract.getOwner() != null) {

				for (Contract boundContract : contract.bindSupplier(connector,
						this.myResourceManager)) {

					this.addInstantiatedContract(contract, boundContract);
					contracts.add(boundContract);
				}
				// end for.

				this.addContractedConnector(connector, contract);
			}

			/* Else just set the supplier and add the contract. */
			else {
				contract.setSupplier(connector);
				contracts.add(contract);
			}

			this.mySupplierContracts.put(connector, contracts);
			this.setChanged();
		}

		/* Else throw an exception. */
		else {
			throw new TreatyException(
					"Cannot add contract with Supplier Role to a Connector that"
							+ "is not of the ConnectorType Supplier.");
		}
		// end else.
	}

	/**
	 * <p>
	 * A helper method that adds a new instance {@link Contract} of a given
	 * {@link Contract} to the collection of instantiated {@link Contract}s.
	 * </p>
	 * 
	 * @param parentContract
	 *          The parent {@link Contract}.
	 * @param instanceContract
	 *          The instance of the parent {@link Contract} that shall be stored.
	 */
	private void addInstantiatedContract(Contract parentContract,
			Contract instanceContract) {

		HashSet<Contract> instantiatedContracts;

		if (this.instantiatedContracts.containsKey(parentContract)) {
			instantiatedContracts = this.instantiatedContracts.get(parentContract);
		}

		else {
			instantiatedContracts = new HashSet<Contract>();
		}

		instantiatedContracts.add(instanceContract);
		this.instantiatedContracts.put(parentContract, instantiatedContracts);

		this.setChanged();
	}

	/**
	 * <p>
	 * A helper method that adds a {@link Connector}s that is contracted by a
	 * {@link Contract} that is an instance of another (the given)
	 * {@link Contract}. This is required to remove all instantiated
	 * {@link Contract}s as well, if a parent {@link Contract} shall be removed.
	 * </p>
	 * 
	 * @param connector
	 *          The {@link Connector} that shall be added.
	 * @param contract
	 *          The {@link Contract}, the {@link Connector} is contracted with by
	 *          having an instance of it.
	 */
	private void addContractedConnector(Connector connector, Contract contract) {

		HashSet<Connector> contractedConnectors;

		if (this.contractedConnectors.containsKey(contract)) {
			contractedConnectors = this.contractedConnectors.get(contract);
		}

		else {
			contractedConnectors = new HashSet<Connector>();
		}

		contractedConnectors.add(connector);
		this.contractedConnectors.put(contract, contractedConnectors);

		this.setChanged();
	}

	/**
	 * <p>
	 * A helper method that returns all {@link Contract}s that are instances of a
	 * given {@link Contract} or the {@link Contract} itself if he is
	 * instantiated.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} whose instantiated {@link Contract}s shall be
	 *          returned.
	 * @return All instantiated {@link Contract}s of the given {@link Contract}.
	 */
	private Set<Contract> getInstantiatedContracts(Contract contract) {

		Set<Contract> result;
		result = new LinkedHashSet<Contract>();

		/* Check if the contract itself is completely instantiated. */
		if (!contract.isShadow() && contract.getConsumer() != null
				&& contract.getSupplier() != null) {
			result.add(contract);
		}

		/* Else probably add instances of the abstract contract. */
		else if (this.instantiatedContracts.containsKey(contract)) {

			for (Contract instantiatedContract : this.instantiatedContracts
					.get(contract)) {
				result.addAll(this.getInstantiatedContracts(instantiatedContract));
			}
			// end for.
		}
		// no else.

		return result;
	}

	/**
	 * <p>
	 * A helper method that removes a given {@link Contract} from a given
	 * {@link Connector} of playing the {@link Role#LEGISLATOR}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} that shall be removed.
	 * @param connector
	 *          The {@link Connector} from that the {@link Contract} shall be
	 *          removed.
	 */
	private void removeContractFromLegislator(Contract contract,
			Connector connector) {

		if (this.myLegislatorContracts.containsKey(connector)) {

			LinkedHashSet<Contract> contracts;

			contracts = this.myLegislatorContracts.get(connector);
			contracts.remove(contract);

			if (contracts.size() > 0) {
				this.myLegislatorContracts.put(connector, contracts);
			}

			else {
				this.myLegislatorContracts.remove(connector);
			}

			/*
			 * Probably remove the contract from other connectors instantiating it as
			 * well.
			 */
			if (this.contractedConnectors.containsKey(contract)) {

				for (Connector contractedConnector : this.contractedConnectors
						.get(contract)) {

					switch (contractedConnector.getType()) {

					case CONSUMER:

						this.removeContractFromConsumer(contract, contractedConnector);
						break;

					case SUPPLIER:

						this.removeContractFromSupplier(contract, contractedConnector);
						break;
					}
					// end switch.
				}
				// end for.
			}
			// no else.

			this.setChanged();
		}
		// no else.
	}

	/**
	 * <p>
	 * A helper method that removes a given {@link Contract} from a given
	 * {@link Connector} of the {@link ConnectorType#CONSUMER}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} that shall be removed.
	 * @param connector
	 *          The {@link Connector} from that the {@link Contract} shall be
	 *          removed.
	 * @return <code>true</code> if the contract has been removed.
	 */
	private boolean removeContractFromConsumer(Contract contract,
			Connector connector) {

		boolean result;
		result = false;

		/* Check if the given Contract is attached to the given connector. */
		if (this.myConsumerContracts.containsKey(connector)
				&& this.myConsumerContracts.get(connector).contains(contract)) {

			LinkedHashSet<Contract> contracts;

			contracts = this.myConsumerContracts.get(connector);
			result = contracts.remove(contract);

			if (contracts.size() > 0) {
				this.myConsumerContracts.put(connector, contracts);
			}

			else {
				this.myConsumerContracts.remove(connector);
			}

			/*
			 * Probably remove the contract from other suppliers instantiating it as
			 * well.
			 */
			if (this.contractedConnectors.containsKey(contract)) {

				for (Connector contractedConnector : this.contractedConnectors
						.get(contract)) {

					switch (contractedConnector.getType()) {

					case SUPPLIER:

						this.removeContractFromSupplier(contract, contractedConnector);
						break;
					}
					// end switch.
				}
				// end for.
			}
			// no else.

			this.setChanged();
		}

		/* Check if the given contract has instances bound to the given connector. */
		else if (this.instantiatedContracts.containsKey(contract)) {

			for (Contract childrenContract : new HashSet<Contract>(
					this.instantiatedContracts.get(contract))) {

				if (this.removeContractFromConsumer(childrenContract, connector)) {
					result = true;

					this.removeInstantiatedContract(contract, childrenContract);
					this.removesContractedConnector(connector, contract);

					break;
				}
				// no else.
			}
		}
		// no else.

		return result;
	}

	/**
	 * <p>
	 * A helper method that removes a given {@link Contract} from a given
	 * {@link Connector} of the {@link ConnectorType#SUPPLIER}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} that shall be removed.
	 * @param connector
	 *          The {@link Connector} from that the {@link Contract} shall be
	 *          removed.
	 * @return <code>true</code> if the contract has been removed.
	 */
	private boolean removeContractFromSupplier(Contract contract,
			Connector connector) {

		boolean result;
		result = false;

		/* Check if the given Contract is attached to the given connector. */
		if (this.mySupplierContracts.containsKey(connector)
				&& this.mySupplierContracts.get(connector).contains(contract)) {

			LinkedHashSet<Contract> contracts;

			contracts = this.mySupplierContracts.get(connector);
			result = contracts.remove(contract);

			if (contracts.size() > 0) {
				this.mySupplierContracts.put(connector, contracts);
			}

			else {
				this.mySupplierContracts.remove(connector);
			}

			/*
			 * Probably remove the contract from other consumers instantiating it as
			 * well.
			 */
			if (this.contractedConnectors.containsKey(contract)) {

				for (Connector contractedConnector : this.contractedConnectors
						.get(contract)) {

					switch (contractedConnector.getType()) {

					case CONSUMER:

						this.removeContractFromConsumer(contract, contractedConnector);
						break;
					}
					// end switch.
				}
				// end for.
			}
			// no else.

			this.setChanged();
		}

		/* Check if the given contract has instances bound to the given connector. */
		else if (this.instantiatedContracts.containsKey(contract)) {

			for (Contract childrenContract : new HashSet<Contract>(
					this.instantiatedContracts.get(contract))) {

				if (this.removeContractFromSupplier(childrenContract, connector)) {
					result = true;

					this.removeInstantiatedContract(contract, childrenContract);
					this.removesContractedConnector(connector, contract);

					break;
				}
				// no else.
			}
		}
		// no else.

		return result;
	}

	/**
	 * <p>
	 * A helper method that removes a new instance {@link Contract} of a given
	 * {@link Contract} to the collection of instantiated {@link Contract}s.
	 * </p>
	 * 
	 * @param parentContract
	 *          The parent {@link Contract}.
	 * @param instanceContract
	 *          The instance of the parent {@link Contract} that shall be removed.
	 */
	private void removeInstantiatedContract(Contract parentContract,
			Contract instanceContract) {

		if (this.instantiatedContracts.containsKey(parentContract)) {

			HashSet<Contract> instantiatedContracts;

			instantiatedContracts = this.instantiatedContracts.get(parentContract);
			instantiatedContracts.remove(instanceContract);

			if (instantiatedContracts.size() > 0) {
				this.instantiatedContracts.put(parentContract, instantiatedContracts);
			}

			else {
				this.instantiatedContracts.remove(parentContract);
			}

			this.setChanged();
		}
		// no else.
	}

	/**
	 * <p>
	 * A helper method that removes a {@link Connector}s that is contracted by a
	 * {@link Contract} that is an instance of another {@link Contract}. This is
	 * required to remove all instantiated {@link Contract}s as well, if a parent
	 * {@link Contract} shall be removed.
	 * </p>
	 * 
	 * @param connector
	 *          The {@link Connector} that shall be removed.
	 * @param contract
	 *          The {@link Contract} the {@link Connector} is contracted with by
	 *          having an instance of it.
	 */
	private void removesContractedConnector(Connector connector, Contract contract) {

		if (this.contractedConnectors.containsKey(contract)) {

			HashSet<Connector> contractedConnectors;

			contractedConnectors = this.contractedConnectors.get(contract);
			contractedConnectors.add(connector);

			if (contractedConnectors.size() > 0) {
				this.contractedConnectors.put(contract, contractedConnectors);
			}

			else {
				this.contractedConnectors.remove(contract);
			}

			this.setChanged();
		}
		// no else.
	}
}