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
		Visitable {

	/** The resources of the consumer {@link Connector} of this {@link Contract}. */
	private java.util.Map<String, Resource> consumerResources =
			new java.util.LinkedHashMap<String, Resource>();

	/**
	 * The resources of the probably existing legislator {@link Connector} of this
	 * {@link Contract}.
	 */
	private java.util.Map<String, Resource> legislatorResources =
			new java.util.LinkedHashMap<String, Resource>();

	/** The resources of the supplier {@link Connector} of this {@link Contract}. */
	private java.util.Map<String, Resource> supplierResources =
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

	/*
	 * (non-Javadoc)
	 * @seenz.ac.massey.treaty.Contract#check(nz.ac.massey.treaty.verification.
	 * VerificationReport, nz.ac.massey.treaty.verification.ConditionVerifier)
	 */
	public boolean check(VerificationReport report, Verifier verifier,
			VerificationPolicy policy) {

		boolean result;

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

		this.owner = owner;

		for (Resource resource : legislatorResources.values()) {
			resource.setOwner(owner);
		}
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

				for (Resource consumerResource : this.consumerResources.values()) {

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
				contract.consumerResources.putAll(this.consumerResources);
			}

			/* Instantiate the legislator resources. */
			if (role == Role.LEGISLATOR) {

				for (Resource legislatorResource : this.legislatorResources.values()) {

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
				contract.legislatorResources.putAll(this.legislatorResources);
			}

			/* Instantiate the supplier resources. */
			if (role == Role.SUPPLIER) {

				for (Resource supplierResource : this.supplierResources.values()) {
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
				contract.supplierResources.putAll(this.supplierResources);
			}

			/* Replace add all instantiated resources. */
			for (AbstractCondition condition : this.constraints) {

				switch (role) {

				case CONSUMER:
					contract.addCondition(condition
							.replaceResources(contract.consumerResources));
					break;

				case LEGISLATOR:
					contract.addCondition(condition
							.replaceResources(contract.legislatorResources));
					break;

				case SUPPLIER:
					contract.addCondition(condition
							.replaceResources(contract.supplierResources));
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
	// the owner is used if a third party owns the contract
	private Connector owner = null;
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

		this.supplierResources = supplierResources;
	}

	public void setConsumerResources(
			java.util.Map<String, Resource> consumerResources) {

		this.consumerResources = consumerResources;
	}

	public void setExternalResources(
			java.util.Map<String, Resource> externalResources) {

		this.legislatorResources = externalResources;
	}

	private String supplierContext = null;
	private String externalContext = null;

	public Connector getOwner() {

		return owner;
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
		this.supplierResources.put(r.getId(), r);
	}

	public void addConsumerResource(Resource r) throws InvalidContractException {

		this.checkId(r);
		if (!r.isInstantiated())
			throw new InvalidContractException(
					"External resources cannot be variables");
		r.setOwner(this.consumer);
		this.consumerResources.put(r.getId(), r);
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
		r.setOwner(owner);
		this.legislatorResources.put(r.getId(), r);
	}

	public java.util.Collection<Resource> getConsumerResources() {

		return consumerResources.values();
	}

	public java.util.Collection<Resource> getSupplierResources() {

		return supplierResources.values();
	}

	public java.util.Collection<Resource> getExternalResources() {

		return legislatorResources.values();
	}

	public Resource getResource(String id) {

		Resource r = this.consumerResources.get(id);
		if (r == null) {
			r = this.supplierResources.get(id);
		}
		if (r == null) {
			r = this.legislatorResources.get(id);
		}
		return r;

	}

	private void checkId(Resource r) throws InvalidContractException {

		if (this.consumerResources.containsKey(r.getId()))
			throw new InvalidContractException(
					"A resource with this id is already registered as extension resource: "
							+ r.getId());
		else if (this.supplierResources.containsKey(r.getId()))
			throw new InvalidContractException(
					"A resource with this id is already registered as extension point resource: "
							+ r.getId());
		else if (this.legislatorResources.containsKey(r.getId()))
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
			if (visitor.visitConsumerResources(this.consumerResources.values())) {
				for (Resource r : this.consumerResources.values()) {
					r.accept(visitor);
				}
				visitor.endVisitConsumerResources(this.consumerResources.values());
			}
			if (visitor.visitSupplierResources(this.supplierResources.values())) {
				for (Resource r : this.supplierResources.values()) {
					r.accept(visitor);
				}
				visitor.endVisitSupplierResources(this.supplierResources.values());
			}
			if (visitor.visitExternalResources(this.supplierResources.values())) {
				for (Resource r : this.legislatorResources.values()) {
					r.accept(visitor);
				}
				visitor.endVisitExternalResources(this.supplierResources.values());
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
			sc.owner = this.owner;
			sc.consumerContext = this.consumerContext;
			sc.externalContext = this.externalContext;
			sc.supplierContext = this.supplierContext;
			sc.consumerResources = this.consumerResources;
			sc.supplierResources = this.supplierResources;
			sc.legislatorResources = this.legislatorResources;
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
		for (Resource r : consumerResources.values()) {
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
		for (Resource r : supplierResources.values()) {
			r.setOwner(supplier);
		}
	}

	/**
	 * Indicates whether the contract has unbound variables. If so, one of the
	 * bind methods must be used.
	 * 
	 * @return a boolean
	 */
	public boolean isInstantiated() {

		for (AbstractCondition c : this.getConstraints()) {
			if (!c.isInstantiated()) {
				return false;
			}
		}
		return true;
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