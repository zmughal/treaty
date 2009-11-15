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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Represents a contract between connectors (and therefore between components).
 * Used to represent both, contract templates and instantiated contracts. This
 * can be queried with {@link Contract#isInstantiated()}.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class Contract extends PropertySupport implements ConditionContext,
		Visitable, Comparable<Contract> {

	/** The resources of the consumer {@link Connector} of this {@link Contract}. */
	private java.util.Map<String, Resource> myConsumerResources =
			new java.util.LinkedHashMap<String, Resource>();

	/**
	 * The resources of the probably existing legislator {@link Connector} of this
	 * {@link Contract}.
	 */
	private java.util.Map<String, Resource> myLegislatorResources =
			new java.util.LinkedHashMap<String, Resource>();

	/**
	 * The owner of this {@link Contract}. The owner is set when the
	 * {@link Contract} is defined by a third party {@link Connector} playing the
	 * {@link Role#LEGISLATOR} role.
	 */
	private Connector myOwner = null;

	/** The resources of the supplier {@link Connector} of this {@link Contract}. */
	private java.util.Map<String, Resource> mySupplierResources =
			new java.util.LinkedHashMap<String, Resource>();

	/**
	 * <p>
	 * Creates a new {@link Contract}.
	 * </p>
	 */
	public Contract() {

		super();
	}

	/**
	 * <p>
	 * Creates a new {@link Contract} for a given consumer (as a {@link Connector}
	 * ) and a given {@link URL} as the {@link Contract}'s location.
	 * </p>
	 * 
	 * @param consumer
	 *          The consumer of this {@link Contract} (as a {@link Connector}).
	 * @param connector2
	 *          FIXME Claas: This argument is never used.
	 * @param location
	 *          The {@link URL} where the contract is located.
	 */
	public Contract(Connector consumer, Connector connector2, URL location) {

		super();
		this.location = location;
		assert (consumer.getType() == ConnectorType.CONSUMER);
		this.consumer = consumer;
		assert (consumer.getType() == ConnectorType.SUPPLIER);
	}

	/**
	 * <p>
	 * Compares this {@link Contract} and a given {@link Contract} by comparing
	 * their owners ({@link Connector}s), and their location's.
	 * </p>
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Contract aContract) {

		int result;
		result = 0;

		/* Probably compare the owners. */
		if (this.getOwner() != null && aContract.getOwner() != null) {

			result = this.getOwner().getId().compareTo(aContract.getOwner().getId());
		}

		else if (this.getOwner() != null) {
			result = 1;
		}

		else if (aContract.getOwner() != null) {
			result = -1;
		}
		// no else.

		/* Probably compare the location. */
		if (result == 0) {
			if (this.getLocation() != null && aContract.getLocation() != null) {
				result =
						this.getLocation().toString().compareTo(
								aContract.getLocation().toString());
			}

			else if (this.getLocation() != null) {
				result = 1;
			}

			else if (aContract.getLocation() != null) {
				result = -1;
			}
			// no else.
		}
		// no else.

		/* Probably compare the consumer. */
		if (result == 0) {
			if (this.getConsumer() != null && aContract.getConsumer() != null) {
				result =
						this.getConsumer().getId().compareTo(
								aContract.getConsumer().getId().toString());
			}

			else if (this.getConsumer() != null) {
				result = 1;
			}

			else if (aContract.getConsumer() != null) {
				result = -1;
			}
			// no else.
		}
		// no else.

		/* Probably compare the supplier. */
		if (result == 0) {
			if (this.getSupplier() != null && aContract.getSupplier() != null) {
				result =
						this.getSupplier().getId().compareTo(
								aContract.getSupplier().getId().toString());
			}

			else if (this.getSupplier() != null) {
				result = 1;
			}

			else if (aContract.getSupplier() != null) {
				result = -1;
			}
			// no else.
		}
		// no else.

		return result;
	}

	/**
	 * <p>
	 * Instantiates the {@link Contract} for a consumer.
	 * </p>
	 * 
	 * @param consumer
	 *          The consumer {@link Connector}.
	 * @param loader
	 *          The {@link ResourceManager} used.
	 * @return A {@link List} of contracts. >1 if multiple instantiation contexts
	 *         exist Post condition: all {@link Contract}s returned are
	 *         instantiated
	 * @throws TreatyException
	 *           Thrown, if the binding fails.
	 */
	public List<Contract> bindConsumer(Connector consumer, ResourceManager loader)
			throws TreatyException {

		return this.bind(consumer, loader, Role.CONSUMER);
	}

	/**
	 * <p>
	 * Instantiate the {@link Contract} for a supplier.
	 * </p>
	 * 
	 * @param supplier
	 *          The supplier {@link Connector}.
	 * @param loader
	 *          The {@link ResourceManager} used.
	 * @return A {@link List} of {@link Contract}s. >1 if multiple instantiation
	 *         contexts exist Post condition: all {@link Contract}s returned are
	 *         instantiated.
	 * @throws TreatyException
	 *           Thrown, if the binding fails.
	 */
	public List<Contract> bindSupplier(Connector supplier, ResourceManager loader)
			throws TreatyException {

		return this.bind(supplier, loader, Role.SUPPLIER);
	}

	/*
	 * (non-Javadoc)
	 * @seenz.ac.massey.treaty.Contract#check(nz.ac.massey.treaty.verification.
	 * VerificationReport, nz.ac.massey.treaty.verification.ConditionVerifier)
	 */
	public boolean check(VerificationReport report, Verifier verifier,
			VerificationPolicy policy) {

		boolean result;

		/* Check if the contract has been instantiated at all. */
		if (this.isInstantiated()) {

			report.setContract(this);
			result = true;

			/* FIXME Claas: No difference between if and else clause. */
			if (policy == VerificationPolicy.DETAILED) {
				for (AbstractCondition abstractCondition : this.constraints) {
					result = result & abstractCondition.check(report, verifier, policy);
				}
				// end for.
			}
			else {
				for (AbstractCondition abstractCondition : this.constraints) {
					result = result && abstractCondition.check(report, verifier, policy);
				}
				// end for.
			}
			// end else.

			if (result) {
				report.log(this, VerificationResult.SUCCESS);
			}

			else {
				report.log(this, VerificationResult.FAILURE,
						"Some parts of this condition are not satisfied.");
			}
		}

		else {
			result = false;

			report.log(this, VerificationResult.UNKNOWN,
					"The Contract has not been instantiated.");
		}

		return result;
	}

	/**
	 * <p>
	 * Indicates whether or not the {@link Contract} has unbound variables. If so,
	 * one of the bind methods must be used.
	 * </p>
	 * 
	 * @return <code>true</code> if the {@link Contract} has no unbound variables.
	 */
	public boolean isInstantiated() {

		/*
		 * TODO Claas: What should this method return if the list of constraints is
		 * empty?
		 */
		boolean result;
		result = true;

		for (AbstractCondition condition : this.getConstraints()) {

			if (!condition.isInstantiated()) {
				result = false;
				break;
			}
			// no else.
		}
		// end for.

		return result;
	}

	/**
	 * <p>
	 * Sets the owner of this {@link Contract}. Each {@link Contract} is owned by
	 * the {@link Connector} that provides the {@link Contract}.
	 * </p>
	 * 
	 * @param owner
	 *          The owner of this {@link Contract}.
	 */
	public void setOwner(Connector owner) {

		this.myOwner = owner;

		for (Resource resource : myLegislatorResources.values()) {
			resource.setOwner(owner);
		}
	}

	/**
	 * <p>
	 * A helper method to bind this {@link Contract} to a {@link Connector}.
	 * </p>
	 * 
	 * @param connector
	 *          The {@link Connector} that shall be bound.
	 * @param loader
	 *          The {@link ResourceManager} used to load {@link Resource}s during
	 *          {@link Contract} verification.
	 * @param role
	 *          The {@link Role} to that the {@link Connector} shall be bound.
	 * @return A {@link List} of {@link Contract}s. >1 if multiple instantiation
	 *         contexts exist Post condition: all {@link Contract}s returned are
	 *         instantiated.
	 * @throws TreatyException
	 *           Thrown, if the binding fails.
	 */
	private List<Contract> bind(Connector connector, ResourceManager loader,
			Role role) throws TreatyException {

		String contextIdentifier;
		contextIdentifier = null;

		switch (role) {

		case CONSUMER:
			contextIdentifier = this.getConsumerContext();
			break;

		case SUPPLIER:
			contextIdentifier = this.getSupplierContext();
			break;

		default:
			contextIdentifier = this.getExternalContext();
		}
		// end switch.

		List<InstantiationContext> contexts;
		contexts = loader.getInstantiationContexts(connector, contextIdentifier);

		List<Contract> result;
		result = new ArrayList<Contract>(contexts.size());

		/* Built instantiated contracts. */
		for (InstantiationContext context : contexts) {

			Contract contract;

			contract = new Contract();
			result.add(contract);

			/* Set supplier and consumer. */
			switch (role) {

			case CONSUMER:
				contract.setConsumer(connector);
				contract.setSupplier(this.getSupplier());
				break;

			case SUPPLIER:
				contract.setConsumer(this.getConsumer());
				contract.setSupplier(connector);
				break;

			default:
				contract.setConsumer(this.getConsumer());
				contract.setSupplier(this.getSupplier());
			}

			/* Set owner, location and definition. */
			contract.setOwner(this.getOwner());
			contract.setLocation(this.getLocation());
			contract.setDefinition(this);

			/* Instantiate the consumer resources. */
			if (role == Role.CONSUMER) {

				for (Resource consumerResource : this.myConsumerResources.values()) {

					/* Probably instantiate the resource. */
					if (consumerResource.isInstantiated()) {
						contract.addConsumerResource(consumerResource);
					}

					else {
						Resource instance;
						instance = consumerResource.instantiate(connector, context, loader);
						contract.addConsumerResource(instance);
					}
				}
				// end for.
			}

			else {
				contract.myConsumerResources.putAll(this.myConsumerResources);
			}

			/* Instantiate the legislator resources. */
			if (role == Role.LEGISLATOR) {

				for (Resource legislatorResource : this.myLegislatorResources.values()) {

					/* Probably instantiate the resource. */
					if (legislatorResource.isInstantiated()) {
						contract.addExternalResource(legislatorResource);
					}

					else {
						Resource instance;
						instance =
								legislatorResource.instantiate(connector, context, loader);
						contract.addExternalResource(instance);
					}
				}
				// end for.
			}

			else {
				contract.myLegislatorResources.putAll(this.myLegislatorResources);
			}

			/* Instantiate the supplier resources. */
			if (role == Role.SUPPLIER) {

				for (Resource supplierResource : this.mySupplierResources.values()) {
					/* Probably instantiate the resource. */
					if (supplierResource.isInstantiated()) {
						contract.addSupplierResource(supplierResource);
					}

					else {
						Resource instance;
						instance = supplierResource.instantiate(connector, context, loader);
						contract.addSupplierResource(instance);
					}
				}
				// end for.
			}

			else {
				contract.mySupplierResources.putAll(this.mySupplierResources);
			}

			/* Replace add all instantiated resources. */
			for (AbstractCondition condition : this.constraints) {

				switch (role) {

				case CONSUMER:
					contract.addCondition(condition
							.replaceResources(contract.myConsumerResources));
					break;

				case LEGISLATOR:
					contract.addCondition(condition
							.replaceResources(contract.myLegislatorResources));
					break;

				case SUPPLIER:
					contract.addCondition(condition
							.replaceResources(contract.mySupplierResources));
					break;

				default:
					contract.addCondition(condition);
				}
				// end switch.
			}
			// no else.

			assert contract.isInstantiated();
		}
		// end for (iteration on contexts).

		return result;
	}

	private java.util.List<AbstractCondition> constraints =
			new java.util.ArrayList<AbstractCondition>();
	// active elements
	// events and actions are represented as URIs - framework implementations
	// have to provide the semantics for them

	// events triggering verification, such as life cycle events (installed,
	// activated, updated, ..)
	private java.util.List<URI> triggers = new java.util.ArrayList<URI>();

	// actions that will be executed depending on the outcome of verification
	private java.util.List<URI> onVerificationFailsActions =
			new java.util.ArrayList<URI>();
	private java.util.List<URI> onVerificationSucceedsActions =
			new java.util.ArrayList<URI>();

	private Connector consumer = null;
	private Connector supplier = null;

	private URL location = null; // the physical location of the contract
	// contexts - they define how instantiation is done - this is mainly useful
	// for supplier resources -
	// these are the resources that are usually instantiated
	private String consumerContext = null;
	// this is the definition of instantiated contracts
	private Contract definition = null;
	// flag indicating that contracts refer types and predicates that are unknown
	// this is used when vocabularies are defined in a modular manner
	// it is then possible that contracts are loaded, but the referenced variables
	// are not available
	// if this flag is true, it is generally not possible to check/verify these
	// contracts
	private boolean shadow = false;

	public String getConsumerContext() {

		return consumerContext;
	}

	public void setConsumerContext(String consumerContext) {

		this.consumerContext = consumerContext;
	}

	public String getSupplierContext() {

		return supplierContext;
	}

	public void setSupplierContext(String supplierContext) {

		this.supplierContext = supplierContext;
	}

	public String getExternalContext() {

		return externalContext;
	}

	public void setExternalContext(String externalContext) {

		this.externalContext = externalContext;
	}

	public void setSupplierResources(
			java.util.Map<String, Resource> supplierResources) {

		this.mySupplierResources = supplierResources;
	}

	public void setConsumerResources(
			java.util.Map<String, Resource> consumerResources) {

		this.myConsumerResources = consumerResources;
	}

	public void setExternalResources(
			java.util.Map<String, Resource> externalResources) {

		this.myLegislatorResources = externalResources;
	}

	private String supplierContext = null;
	private String externalContext = null;

	public Connector getOwner() {

		return myOwner;
	}

	/*
	 * (non-Javadoc)
	 * @seenz.ac.massey.treaty.ConditionOwner#addCondition(nz.ac.massey.treaty.
	 * AbstractCondition)
	 */
	public void addCondition(AbstractCondition c) {

		this.constraints.add(c);
	}

	public void addSupplierResource(Resource r) throws InvalidContractException {

		this.checkId(r);
		r.setOwner(this.supplier);
		this.mySupplierResources.put(r.getId(), r);
	}

	public void addConsumerResource(Resource r) throws InvalidContractException {

		this.checkId(r);
		if (!r.isInstantiated())
			throw new InvalidContractException(
					"External resources cannot be variables");
		r.setOwner(this.consumer);
		this.myConsumerResources.put(r.getId(), r);
	}

	public void addTrigger(URI uri) throws InvalidContractException {

		this.triggers.add(uri);
	}

	public void addOnVerificationFailsAction(URI uri)
			throws InvalidContractException {

		this.onVerificationFailsActions.add(uri);
	}

	public void addOnVerificationSucceedsAction(URI uri)
			throws InvalidContractException {

		this.onVerificationSucceedsActions.add(uri);
	}

	public void addExternalResource(Resource r) throws InvalidContractException {

		this.checkId(r);
		r.setOwner(myOwner);
		this.myLegislatorResources.put(r.getId(), r);
	}

	public java.util.Collection<Resource> getConsumerResources() {

		return myConsumerResources.values();
	}

	public java.util.Collection<Resource> getSupplierResources() {

		return mySupplierResources.values();
	}

	public java.util.Collection<Resource> getExternalResources() {

		return myLegislatorResources.values();
	}

	public Resource getResource(String id) {

		Resource r = this.myConsumerResources.get(id);
		if (r == null) {
			r = this.mySupplierResources.get(id);
		}
		if (r == null) {
			r = this.myLegislatorResources.get(id);
		}
		return r;

	}

	private void checkId(Resource r) throws InvalidContractException {

		if (this.myConsumerResources.containsKey(r.getId()))
			throw new InvalidContractException(
					"A resource with this id is already registered as extension resource: "
							+ r.getId());
		else if (this.mySupplierResources.containsKey(r.getId()))
			throw new InvalidContractException(
					"A resource with this id is already registered as extension point resource: "
							+ r.getId());
		else if (this.myLegislatorResources.containsKey(r.getId()))
			throw new InvalidContractException(
					"A resource with this id is already registered as external resource: "
							+ r.getId());
	}

	public java.util.List<AbstractCondition> getConstraints() {

		return constraints;
	}

	/**
	 * Accept a contract visitor.
	 * 
	 * @param visitor
	 *          a visitor
	 */
	public void accept(ContractVisitor visitor) {

		boolean f = visitor.visit(this);
		if (f) {
			if (visitor.visitConsumerResources(this.myConsumerResources.values())) {
				for (Resource r : this.myConsumerResources.values()) {
					r.accept(visitor);
				}
				visitor.endVisitConsumerResources(this.myConsumerResources.values());
			}
			if (visitor.visitSupplierResources(this.mySupplierResources.values())) {
				for (Resource r : this.mySupplierResources.values()) {
					r.accept(visitor);
				}
				visitor.endVisitSupplierResources(this.mySupplierResources.values());
			}
			if (visitor.visitExternalResources(this.mySupplierResources.values())) {
				for (Resource r : this.myLegislatorResources.values()) {
					r.accept(visitor);
				}
				visitor.endVisitExternalResources(this.mySupplierResources.values());
			}
			if (visitor.visitConditions(this.constraints)) {
				for (AbstractCondition c : getConstraints()) {
					c.accept(visitor);
				}
				visitor.endVisitConditions(this.constraints);
			}
		}
		visitor.endVisit(this);

	}

	/**
	 * Convert the contract to an equivalent, more compact form. This is not yet
	 * fully recursive, it just removes top-level conjunction in order to flatten
	 * the structure.
	 * 
	 * @return a contract
	 */
	public Contract pack() {

		boolean f = true;
		for (AbstractCondition c : constraints) {
			f = f && (c instanceof Conjunction);
		}
		if (f) {
			Contract sc = new Contract();
			sc.consumer = this.consumer;
			sc.supplier = this.supplier;
			sc.myOwner = this.myOwner;
			sc.consumerContext = this.consumerContext;
			sc.externalContext = this.externalContext;
			sc.supplierContext = this.supplierContext;
			sc.myConsumerResources = this.myConsumerResources;
			sc.mySupplierResources = this.mySupplierResources;
			sc.myLegislatorResources = this.myLegislatorResources;
			sc.constraints = new ArrayList<AbstractCondition>();
			for (AbstractCondition c : constraints) {
				Conjunction conj = (Conjunction) c;
				for (AbstractCondition cp : conj.getParts()) {
					sc.constraints.add(cp);
				}
			}
			return sc;
		}
		else {
			return this;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see nz.ac.massey.treaty.IContract#getConsumer()
	 */
	public Connector getConsumer() {

		return consumer;
	}

	public void setConsumer(Connector consumer) {

		this.consumer = consumer;
		for (Resource r : myConsumerResources.values()) {
			r.setOwner(consumer);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see nz.ac.massey.treaty.IContract#getSupplier()
	 */
	public Connector getSupplier() {

		return supplier;
	}

	public void setSupplier(Connector supplier) {

		this.supplier = supplier;
		for (Resource r : mySupplierResources.values()) {
			r.setOwner(supplier);
		}
	}

	public URL getLocation() {

		return location;
	}

	public void setLocation(URL location) {

		this.location = location;
	}

	@Override
	public String toString() {

		if (this.location == null)
			return super.toString();
		else {
			return new StringBuffer().append(super.toString()).append('(').append(
					this.location).append(')').toString();
		}
	}

	public Contract getDefinition() {

		return definition;
	}

	public void setDefinition(Contract def) {

		assert (isInstantiated() || def == null);
		this.definition = def;
	}

	public void setTriggers(java.util.List<URI> triggers) {

		this.triggers = triggers;
	}

	public void setOnVerificationFailsActions(
			java.util.List<URI> onVerificationFailsActions) {

		this.onVerificationFailsActions = onVerificationFailsActions;
	}

	public void setOnVerificationSucceedsActions(
			java.util.List<URI> onVerificationSucceedsActions) {

		this.onVerificationSucceedsActions = onVerificationSucceedsActions;
	}

	public java.util.List<URI> getTriggers() {

		return triggers;
	}

	public java.util.List<URI> getOnVerificationFailsActions() {

		return onVerificationFailsActions;
	}

	public java.util.List<URI> getOnVerificationSucceedsActions() {

		return onVerificationSucceedsActions;
	}

	public boolean isShadow() {

		return shadow;
	}

	public void setShadow(boolean isShadowContract) {

		this.shadow = isShadowContract;
	}

}