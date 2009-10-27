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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contracts that are composed from sub contracts.
 * 
 * @author Jens Dietrich
 */

public class AggregatedContract extends PropertySupport implements Contract {

	/** All {@link Contract}s contained in this {@link AggregatedContract}. */
	private List<Contract> parts = null;

	private Contract definition = null;

	/**
	 * <p>
	 * Creates a new {@link AggregatedContract}.
	 * </p>
	 * 
	 * @param parts
	 *          The {@link Contract}s that are part of this
	 *          {@link AggregatedContract}.
	 */
	public AggregatedContract(Contract... parts) {

		super();

		this.parts = Arrays.asList(parts);
	}

	/*
	 * (non-Javadoc)
	 * @see nz.ac.massey.treaty.Contract#bindConsumer(Connector,ResourceManager)
	 */
	public Contract bindConsumer(Connector connector, ResourceManager loader)
			throws TreatyException {

		Contract result;
		List<Contract> boundParts;

		boundParts = new ArrayList<Contract>();

		for (Contract part : this.parts) {
			boundParts.add(part.bindConsumer(connector, loader));
		}

		result = new AggregatedContract(boundParts.toArray(new Contract[0])).pack();
		result.setDefinition(this);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see nz.ac.massey.treaty.Contract#bindConsumer(Connector,ResourceManager)
	 */
	public Contract bindSupplier(Connector connector, ResourceManager loader)
			throws TreatyException {

		Contract result;
		List<Contract> boundParts = new ArrayList<Contract>();

		for (Contract part : this.parts) {
			boundParts.add(part.bindSupplier(connector, loader));
		}

		result = new AggregatedContract(boundParts.toArray(new Contract[0])).pack();
		result.setDefinition(this);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Contract#pack()
	 */
	public Contract pack() {

		Contract result;

		if (this.parts.size() == 1) {
			result = this.parts.get(0).pack();
		}

		else {

			List<Contract> newParts;
			newParts = new ArrayList<Contract>();

			for (Contract part : this.parts) {
				newParts.add(part.pack());
			}

			result = new AggregatedContract(newParts.toArray(new Contract[0]));
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Contract#subtractContract(net.java.treaty.Contract)
	 */
	public Contract subtractContract(Contract contract) {

		Contract result;
		List<Contract> newParts;

		newParts = new ArrayList<Contract>(this.parts);

		/*
		 * Iterate through the parts and search for a contract similar to the given
		 * contract.
		 */
		for (int index = 0; index < newParts.size(); index++) {

			if (newParts.get(index).equals(contract)) {
				newParts.remove(index);
				break;
			}
		}

		if (newParts.size() == 0) {
			result = null;
		}

		else {
			result = new AggregatedContract(newParts.toArray(new Contract[0])).pack();
		}

		return result;
	}

	/**
	 * <p>
	 * Returns the parts of this {@link AggregatedContract}.
	 * </p>
	 * 
	 * @return The parts of this {@link AggregatedContract}.
	 */
	public Contract[] getParts() {

		return parts.toArray(new Contract[0]);
	}

	public Contract getDefinition() {

		return definition;
	}

	public void setDefinition(Contract def) {

		this.definition = def;
	}

	public void accept(ContractVisitor visitor) {

		boolean f = visitor.visit(this);
		if (f) {
			for (Contract p : parts) {
				p.accept(visitor);
			}
		}
		visitor.endVisit(this);

	}

	/**
	 * Check this contract using a verifier. Add the results to the report.
	 * 
	 * @param report
	 * @param verifier
	 * @param policy
	 * @return
	 */
	public boolean check(VerificationReport report, Verifier verifier,
			VerificationPolicy policy) {

		boolean result = true;
		for (Contract part : parts) {
			// TODO - now we hard coded & instead of && - this is slower
			// but gives us more info
			// this should be configurable
			result = result & part.check(report, verifier, policy);
		}
		return result;
	}

	public Connector getConsumer() {

		Connector c = null;
		for (Contract p : parts) {
			if (c != null) {
				if (c != p.getConsumer())
					throw new IllegalStateException(
							"All parts of a contract must have the same consumer");
			}
			else {
				c = p.getConsumer();
			}
		}
		return c;
	}

	public Connector getSupplier() {

		Connector c = null;
		for (Contract p : parts) {
			if (c != null) {
				if (c != p.getSupplier())
					throw new IllegalStateException(
							"All parts of a contract must have the same supplier");
			}
			else {
				c = p.getSupplier();
			}
		}
		return c;
	}

	public boolean isInstantiated() {

		for (Contract part : parts) {
			if (!part.isInstantiated()) {
				return false;
			}
		}
		return true;
	}
}