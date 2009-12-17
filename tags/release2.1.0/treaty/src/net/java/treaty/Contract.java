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
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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


	/**
	 * Contains all {@link AbstractCondition}s that belong to this
	 * {@link Contract} after the {@link Contract} has been read.
	 */
	private List<Condition> myConstraints =
			new ArrayList<Condition>();

	/**
	 * The {@link Connector} playing the {@link Role#CONSUMER} role in this
	 * {@link Contract}.
	 */
	private Connector myConsumer = null;

	/**
	 * Consumer Context. Defines how instantiation is done - this is mainly useful
	 * for supplier resources. These are the resources that are usually
	 * instantiated.
	 */
	private String myConsumerContext = null;

	/** The resources of the consumer {@link Connector} of this {@link Contract}. */
	private Map<String, Resource> myConsumerResources =
			new LinkedHashMap<String, Resource>();

	/**
	 * A {@link Contract} can probably have another {@link Contract} as
	 * definition, e.g. a {@link Contract} this {@link Contract} has been
	 * instantiated of.
	 */
	private Contract myDefinition = null;

	/**
	 * External (legislator) Context. Defines how instantiation is done - this is
	 * mainly useful for supplier resources. These are the resources that are
	 * usually instantiated.
	 */
	private String myExternalContext = null;

	/**
	 * The resources of the probably existing legislator {@link Connector} of this
	 * {@link Contract}.
	 */
	private Map<String, Resource> myLegislatorResources =
			new LinkedHashMap<String, Resource>();

	/** The physical location of the {@link Contract}. */
	private URL myLocation = null;

	/**
	 * Actions (as {@link URI}s) that will be executed depending on the failed
	 * outcome of verification.
	 */
	private List<URI> myOnVerificationFailsActions = new ArrayList<URI>();

	/**
	 * Actions (as {@link URI}s) that will be executed depending on the
	 * successfull outcome of verification.
	 */
	private List<URI> myOnVerificationSucceedsActions = new ArrayList<URI>();

	/**
	 * The owner of this {@link Contract}. The owner is set when the
	 * {@link Contract} is defined by a third party {@link Connector} playing the
	 * {@link Role#LEGISLATOR} role.
	 */
	private Connector myOwner = null;

	/**
	 * The {@link Connector} playing the {@link Role#SUPPLIER} role in this
	 * {@link Contract}.
	 */
	private Connector mySupplier = null;

	/**
	 * Supplier Context. Defines how instantiation is done - this is mainly useful
	 * for supplier resources. These are the resources that are usually
	 * instantiated.
	 */
	private String mySupplierContext = null;

	/** The resources of the supplier {@link Connector} of this {@link Contract}. */
	private Map<String, Resource> mySupplierResources =
			new LinkedHashMap<String, Resource>();

	/**
	 * Contains all events (as {@link URI}s) that trigger the verification of this
	 * {@link Contract}, such as life cycle events (installed, // activated,
	 * updated, ...).
	 */
	private List<URI> myTriggers = new ArrayList<URI>();

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
		this.myLocation = location;
		assert (consumer.getType() == ConnectorType.CONSUMER);
		this.myConsumer = consumer;
		assert (consumer.getType() == ConnectorType.SUPPLIER);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Visitable#accept(net.java.treaty.ContractVisitor)
	 */
	public void accept(ContractVisitor visitor) {

		boolean willVisitorEnter;
		willVisitorEnter = visitor.visit(this);

		/* Probably visit the elements of this Contract. */
		if (willVisitorEnter) {

			/* Probably visit consumer resources. */
			if (visitor.visitConsumerResources(this.myConsumerResources.values())) {

				for (Resource resource : this.myConsumerResources.values()) {
					resource.accept(visitor);
				}

				visitor.endVisitConsumerResources(this.myConsumerResources.values());
			}
			// no else.

			/* Probably visit supplier resources. */
			if (visitor.visitSupplierResources(this.mySupplierResources.values())) {

				for (Resource resource : this.mySupplierResources.values()) {
					resource.accept(visitor);
				}

				visitor.endVisitSupplierResources(this.mySupplierResources.values());
			}
			// no else.

			/* Probably visit legislator resources. */

			if (visitor.visitExternalResources(this.mySupplierResources.values())) {

				for (Resource r : this.myLegislatorResources.values()) {
					r.accept(visitor);
				}

				visitor.endVisitExternalResources(this.mySupplierResources.values());
			}
			// no else.

			/* Probably visit the conditions of this Contract. */
			if (visitor.visitConditions(this.myConstraints)) {

				for (Condition condition : getConstraints()) {
					condition.accept(visitor);
				}

				visitor.endVisitConditions(this.myConstraints);
			}
			// no else.

			/* Visit the triggers of this Contract. */
			for (URI triggerType : this.myTriggers) {
				visitor.visitTrigger(triggerType);
			}
			// end for.

			/* Visit the onFailureActions of this Contract. */
			for (URI actionType : this.myOnVerificationFailsActions) {
				visitor.visitOnFailureAction(actionType);
			}
			// end for.

			/* Visit the onSuccessActions of this Contract. */
			for (URI actionType : this.myOnVerificationSucceedsActions) {
				visitor.visitOnSuccessAction(actionType);
			}
			// end for.
		}
		// no else (visitor will not enter).

		visitor.endVisit(this);
	}

	/*
	 * (non-Javadoc)
	 * @seenz.ac.massey.treaty.ConditionOwner#addCondition(nz.ac.massey.treaty.
	 * Condition)
	 */
	public void addCondition(Condition condition) {

		this.myConstraints.add(condition);
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

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		if (this.myLocation == null) {
			return super.toString();
		}

		else {
			return new StringBuffer().append(super.toString()).append('(').append(
					this.myLocation).append(')').toString();
		}
	}

	/**
	 * <p>
	 * Adds a consumer {@link Resource} to this {@link Contract}.
	 * </p>
	 * 
	 * @param resource
	 *          The {@link Resource} that shall be added.
	 * @throws InvalidContractException
	 *           Thrown if a given {@link Resource} has already been added to this
	 *           {@link Contract}.
	 */
	public void addConsumerResource(Resource resource)
			throws InvalidContractException {

		this.checkId(resource);

		// FIXME Claas: Either this check is wrong or at least its message.
		if (!resource.isInstantiated()) {
			throw new InvalidContractException(
					"External resources cannot be variables");
		}
		// no else.

		resource.setOwner(this.myConsumer);
		this.myConsumerResources.put(resource.getId(), resource);
	}

	/**
	 * <p>
	 * Adds a external (legislator) {@link Resource} to this {@link Contract}.
	 * </p>
	 * 
	 * @param resource
	 *          The {@link Resource} that shall be added.
	 * @throws InvalidContractException
	 *           Thrown if a given {@link Resource} has already been added to this
	 *           {@link Contract}.
	 */
	public void addExternalResource(Resource resource)
			throws InvalidContractException {

		this.checkId(resource);

		resource.setOwner(this.myOwner);
		this.myLegislatorResources.put(resource.getId(), resource);
	}

	/**
	 * <p>
	 * Adds an action (as {@link URI}s) that will be executed depending on the
	 * failed outcome of verification.
	 * </p>
	 * 
	 * @param uri
	 *          The action (as a {@link URI}) that shall be added.
	 */
	public void addOnVerificationFailsAction(URI uri)
			throws InvalidContractException {

		this.myOnVerificationFailsActions.add(uri);
	}

	/**
	 * <p>
	 * Adds an action (as {@link URI}s) that will be executed depending on the
	 * successful outcome of verification.
	 * </p>
	 * 
	 * @param uri
	 *          The action (as a {@link URI}) that shall be added.
	 */
	public void addOnVerificationSucceedsAction(URI uri)
			throws InvalidContractException {

		this.myOnVerificationSucceedsActions.add(uri);
	}

	/**
	 * <p>
	 * Adds a supplier {@link Resource} to this {@link Contract}.
	 * </p>
	 * 
	 * @param resource
	 *          The {@link Resource} that shall be added.
	 * @throws InvalidContractException
	 *           Thrown if a given {@link Resource} has already been added to this
	 *           {@link Contract}.
	 */
	public void addSupplierResource(Resource resource)
			throws InvalidContractException {

		this.checkId(resource);

		resource.setOwner(this.mySupplier);
		this.mySupplierResources.put(resource.getId(), resource);
	}

	/**
	 * <p>
	 * Adds a tigger to this {@link Contract}. Triggers describe all events (as
	 * {@link URI}s) that trigger the verification of this {@link Contract}, such
	 * as life cycle events (installed, activated, updated, ...).
	 * </p>
	 * 
	 * @param uri
	 *          The {@link URI} of the Trigger that shall be added.
	 */
	public void addTrigger(URI uri) throws InvalidContractException {

		this.myTriggers.add(uri);
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

			if (policy == VerificationPolicy.DETAILED) {
				for (Condition Condition : this.myConstraints) {
					result = result & Condition.check(report, verifier, policy);
				}
				// end for.
			}
			else {
				for (Condition Condition : this.myConstraints) {
					result = result && Condition.check(report, verifier, policy);
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
	 * Returns all {@link Condition}s that belong to this {@link Contract}
	 * after the {@link Contract} has been read.
	 * </p>
	 * 
	 * @return All {@link Condition}s that belong to this {@link Contract}
	 *         .
	 */
	public List<Condition> getConstraints() {

		return this.myConstraints;
	}

	/**
	 * <p>
	 * Returns the {@link Connector} playing the {@link Role#CONSUMER} role in
	 * this {@link Contract}.
	 * </p>
	 * 
	 * @return The {@link Connector} playing the {@link Role#CONSUMER} role in
	 *         this {@link Contract}.
	 */
	public Connector getConsumer() {

		return this.myConsumer;
	}

	/**
	 * <p>
	 * Returns the Consumer Context. Defines how instantiation is done - this is
	 * mainly useful for supplier resources. These are the resources that are
	 * usually instantiated.
	 * </p>
	 * 
	 * @return The Consumer Context.
	 */
	public String getConsumerContext() {

		return this.myConsumerContext;
	}

	/**
	 * <p>
	 * Returns the {@link Resource}s of the consumer {@link Connector} of this
	 * {@link Contract}.
	 * </p>
	 * 
	 * @param The
	 *          {@link Resource}s of the consumer {@link Connector} of this
	 *          {@link Contract}.
	 */
	public Collection<Resource> getConsumerResources() {

		return this.myConsumerResources.values();
	}

	/**
	 * <p>
	 * Returns the definition of this {@link Contract}. A {@link Contract} can
	 * probably have another {@link Contract} as definition, e.g. a
	 * {@link Contract} this {@link Contract} has been instantiated of.
	 * </p>
	 * 
	 * @return The definition of this {@link Contract}.
	 */
	public Contract getDefinition() {

		return this.myDefinition;
	}

	/**
	 * <p>
	 * Returns the external (legislator) Context. Defines how instantiation is
	 * done - this is mainly useful for supplier resources. These are the
	 * resources that are usually instantiated.
	 * </p>
	 * 
	 * @return The external Context.
	 */
	public String getExternalContext() {

		return this.myExternalContext;
	}

	/**
	 * <p>
	 * Returns the {@link Resource}s of the legislator {@link Connector} of this
	 * {@link Contract}.
	 * </p>
	 * 
	 * @param The
	 *          {@link Resource}s of the legislator {@link Connector} of this
	 *          {@link Contract}.
	 */
	public Collection<Resource> getExternalResources() {

		return this.myLegislatorResources.values();
	}

	/**
	 * <p>
	 * Returns the physical location of the {@link Contract}.
	 * </p>
	 * 
	 * @return The physical location of the {@link Contract}.
	 */
	public URL getLocation() {

		return this.myLocation;
	}

	/**
	 * <p>
	 * Returns the actions (as {@link URI}s) that will be executed depending on
	 * the failed outcome of verification.
	 * </p>
	 * 
	 * @return The actions (as a {@link URI})s.
	 */
	public List<URI> getOnVerificationFailsActions() {

		return this.myOnVerificationFailsActions;
	}

	/**
	 * <p>
	 * Returns the actions (as {@link URI}s) that will be executed depending on
	 * the successful outcome of verification.
	 * </p>
	 * 
	 * @return The actions (as a {@link URI})s.
	 */
	public List<URI> getOnVerificationSucceedsActions() {

		return this.myOnVerificationSucceedsActions;
	}

	/**
	 * <p>
	 * Returns the owning {@link Connector} of this {@link Contract}. The owner is
	 * usually the {@link Connector} that provides the {@link Contract}
	 * definition. This can be any of the {@link Connector}s playing the
	 * {@link Role#CONSUMER}, {@link Role#SUPPLIER} or {@link Role#LEGISLATOR}
	 * role in this {@link Contract}.
	 * </p>
	 * 
	 * @return The owning {@link Connector} of this {@link Contract}.
	 */
	public Connector getOwner() {

		return this.myOwner;
	}

	/**
	 * <p>
	 * Returns the {@link Resource} for a given ID. The {@link Resource} could be
	 * a {@link Resource} of the {@link Connector} playing the
	 * {@link Role#CONSUMER}, {@link Role#SUPPLIER} or the {@link Role#LEGISLATOR}
	 * role in this {@link Contract}.
	 * </p>
	 * 
	 * @param id
	 *          The ID of the {@link Resource} that shall be returned.
	 * @return The {@link Resource} for a given ID or <code>null</code> if the
	 *         given ID does not exists.
	 */
	public Resource getResource(String id) {

		Resource result;

		result = this.myConsumerResources.get(id);

		if (result == null) {
			result = this.mySupplierResources.get(id);
		}
		// no else.

		if (result == null) {
			result = this.myLegislatorResources.get(id);
		}
		// no else.

		return result;
	}

	/**
	 * <p>
	 * Returns the {@link Connector} playing the {@link Role#SUPPLIER} role in
	 * this {@link Contract}.
	 * </p>
	 * 
	 * @return The {@link Connector} playing the {@link Role#SUPPLIER} role in
	 *         this {@link Contract}.
	 */
	public Connector getSupplier() {

		return this.mySupplier;
	}

	/**
	 * <p>
	 * Returns the Supplier Context. Defines how instantiation is done - this is
	 * mainly useful for supplier resources. These are the resources that are
	 * usually instantiated.
	 * </p>
	 * 
	 * @return The Supplier Context.
	 */
	public String getSupplierContext() {

		return this.mySupplierContext;
	}

	/**
	 * <p>
	 * Returns the {@link Resource}s of the supplier {@link Connector} of this
	 * {@link Contract}.
	 * </p>
	 * 
	 * @param The
	 *          {@link Resource}s of the supllier {@link Connector} of this
	 *          {@link Contract}.
	 */
	public Collection<Resource> getSupplierResources() {

		return this.mySupplierResources.values();
	}

	/**
	 * <p>
	 * Returns the triggers of this {@link Contract}. Triggers describe all events
	 * (as {@link URI}s) that trigger the verification of this {@link Contract},
	 * such as life cycle events (installed, activated, updated, ...).
	 * </p>
	 * 
	 * @reuslt The {@link URI}s of the Triggers that shall be set.
	 */
	public java.util.List<URI> getTriggers() {

		return this.myTriggers;
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

		boolean result;
		result = true;

		for (Condition condition : this.getConstraints()) {

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
	 * Convert the {@link Contract} to an equivalent, more compact form. This is
	 * not yet fully recursive, it just removes top-level conjunction in order to
	 * flatten the structure.
	 * </p>
	 * 
	 * @return An equivalent {@link Contract} in a probably more compact form.
	 */
	public Contract pack() {

		Contract result;
		result = this;

		boolean hasOnlyConjunctions;
		hasOnlyConjunctions = true;

		for (Condition constraint : myConstraints) {
			hasOnlyConjunctions =
					hasOnlyConjunctions && (constraint instanceof ConjunctiveCondition);
		}

		/*
		 * Probably remove top-level conjunctions (the contract becomes the
		 * conjunction itself.
		 */
		if (hasOnlyConjunctions) {

			result = new Contract();

			result.myConsumer = this.myConsumer;
			result.mySupplier = this.mySupplier;
			result.myOwner = this.myOwner;
			result.myConsumerContext = this.myConsumerContext;
			result.myExternalContext = this.myExternalContext;
			result.mySupplierContext = this.mySupplierContext;
			result.myConsumerResources = this.myConsumerResources;
			result.mySupplierResources = this.mySupplierResources;
			result.myLegislatorResources = this.myLegislatorResources;
			result.myConstraints = new ArrayList<Condition>();

			for (Condition condition : myConstraints) {
				ConjunctiveCondition conjunction = (ConjunctiveCondition) condition;

				for (Condition conjunctionPart : conjunction.getParts()) {
					result.myConstraints.add(conjunctionPart);
				}
				// end for.
			}
			// end for.
		}
		// no else.

		return result;
	}

	/**
	 * <p>
	 * Sets the {@link Connector} playing the {@link Role#CONSUMER} role in this
	 * {@link Contract}.
	 * </p>
	 * 
	 * @param consumer
	 *          The {@link Connector} playing the {@link Role#CONSUMER} role in
	 *          this {@link Contract}.
	 */
	public void setConsumer(Connector consumer) {

		this.myConsumer = consumer;

		for (Resource resources : this.myConsumerResources.values()) {
			resources.setOwner(consumer);
		}
		// end for.
	}

	/**
	 * <p>
	 * Sets the Consumer Context. Defines how instantiation is done - this is
	 * mainly useful for supplier resources. These are the resources that are
	 * usually instantiated.
	 * </p>
	 * 
	 * @param consumerContext
	 *          The Consumer Context to be set.
	 */
	public void setConsumerContext(String consumerContext) {

		this.myConsumerContext = consumerContext;
	}

	/**
	 * <p>
	 * Sets the {@link Resource}s (variables) of the consumer {@link Connector} of
	 * this {@link Contract}.
	 * </p>
	 * 
	 * @param consumerResources
	 *          The consumer {@link Resource}s as a given {@link Map} identified
	 *          by their name.
	 */
	public void setConsumerResources(Map<String, Resource> consumerResources) {

		this.myConsumerResources = consumerResources;
	}

	/**
	 * <p>
	 * Sets the definition of this {@link Contract}. A {@link Contract} can
	 * probably have another {@link Contract} as definition, e.g. a
	 * {@link Contract} this {@link Contract} has been instantiated of.
	 * </p>
	 * 
	 * @param definition
	 *          The definition of this {@link Contract}.
	 */
	public void setDefinition(Contract definition) {

		assert (this.isInstantiated() || definition == null);

		this.myDefinition = definition;
	}

	/**
	 * <p>
	 * Sets the external (legislator) Context. Defines how instantiation is done -
	 * this is mainly useful for supplier resources. These are the resources that
	 * are usually instantiated.
	 * </p>
	 * 
	 * @param externalContext
	 *          The external Context to be set.
	 */
	public void setExternalContext(String externalContext) {

		this.myExternalContext = externalContext;
	}

	/**
	 * <p>
	 * Sets the {@link Resource}s (variables) of the legislator {@link Connector}
	 * of this {@link Contract}.
	 * </p>
	 * 
	 * @param externalResources
	 *          The legislator {@link Resource}s as a given {@link Map} identified
	 *          by their name.
	 */
	public void setExternalResources(Map<String, Resource> externalResources) {

		this.myLegislatorResources = externalResources;
	}

	/**
	 * <p>
	 * Sets the physical location of the {@link Contract}.
	 * </p>
	 * 
	 * @param location
	 *          The physical location of this {@link Contract} (as a {@link URL}).
	 */
	public void setLocation(URL location) {

		this.myLocation = location;
	}

	/**
	 * <p>
	 * Sets the actions (as {@link URI}s) that will be executed depending on the
	 * failed outcome of verification.
	 * </p>
	 * 
	 * @param onVerificationFailsActions
	 *          The actions (as a {@link URI})s that shall be set.
	 */
	public void setOnVerificationFailsActions(List<URI> onVerificationFailsActions) {

		this.myOnVerificationFailsActions = onVerificationFailsActions;
	}

	/**
	 * <p>
	 * Sets the actions (as {@link URI}s) that will be executed depending on the
	 * successful outcome of verification.
	 * </p>
	 * 
	 * @param onVerificationSucceedsActions
	 *          The actions (as a {@link URI})s that shall be set.
	 */
	public void setOnVerificationSucceedsActions(
			List<URI> onVerificationSucceedsActions) {

		this.myOnVerificationSucceedsActions = onVerificationSucceedsActions;
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
	 * Sets the {@link Connector} playing the {@link Role#SUPPLIER} role in this
	 * {@link Contract}.
	 * </p>
	 * 
	 * @param supplier
	 *          The {@link Connector} playing the {@link Role#SUPPLIER} role in
	 *          this {@link Contract}.
	 */
	public void setSupplier(Connector supplier) {

		this.mySupplier = supplier;

		for (Resource resource : this.mySupplierResources.values()) {
			resource.setOwner(supplier);
		}
		// end for.
	}

	/**
	 * <p>
	 * Sets the Supplier Context. Defines how instantiation is done - this is
	 * mainly useful for supplier resources. These are the resources that are
	 * usually instantiated.
	 * </p>
	 * 
	 * @param supplierContext
	 *          The Supplier Context to be set.
	 */
	public void setSupplierContext(String supplierContext) {

		this.mySupplierContext = supplierContext;
	}

	/**
	 * <p>
	 * Sets the {@link Resource}s (variables) of the supplier {@link Connector} of
	 * this {@link Contract}.
	 * </p>
	 * 
	 * @param supplierResources
	 *          The supplier {@link Resource}s as a given {@link Map} identified
	 *          by their name.
	 */
	public void setSupplierResources(Map<String, Resource> supplierResources) {

		this.mySupplierResources = supplierResources;
	}

	/**
	 * <p>
	 * Sets the triggers of this {@link Contract}. Triggers describe all events
	 * (as {@link URI}s) that trigger the verification of this {@link Contract},
	 * such as life cycle events (installed, activated, updated, ...).
	 * </p>
	 * 
	 * @param triggers
	 *          The {@link URI}s of the Triggers that shall be added.
	 */
	public void setTriggers(List<URI> triggers) {

		this.myTriggers = triggers;
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

			/* Set triggers and actions. */
			contract.setTriggers(this.getTriggers());
			contract.setOnVerificationFailsActions(this
					.getOnVerificationFailsActions());
			contract.setOnVerificationSucceedsActions(this
					.getOnVerificationSucceedsActions());

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
			for (Condition condition : this.myConstraints) {

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

	/**
	 * <p>
	 * Checks whether or not a given {@link Resource} has already been added to
	 * this {@link Contract}.
	 * </p>
	 * 
	 * @param resource
	 *          The {@link Resource} that shall be checked.
	 * @throws InvalidContractException
	 *           Thrown if a given {@link Resource} has already been added to this
	 *           {@link Contract}.
	 */
	private void checkId(Resource resource) throws InvalidContractException {

		if (this.myConsumerResources.containsKey(resource.getId())) {
			throw new InvalidContractException(
					"A resource with this id is already registered as extension resource: "
							+ resource.getId());
		}

		else if (this.mySupplierResources.containsKey(resource.getId())) {
			throw new InvalidContractException(
					"A resource with this id is already registered as extension point resource: "
							+ resource.getId());
		}

		else if (this.myLegislatorResources.containsKey(resource.getId())) {
			throw new InvalidContractException(
					"A resource with this id is already registered as external resource: "
							+ resource.getId());
		}
		// no else (success).
	}
}