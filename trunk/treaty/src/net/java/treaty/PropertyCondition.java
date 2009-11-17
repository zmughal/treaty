/*
 * Copyright (C) 2008-2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty;

import java.net.URI;
import java.util.Map;

/**
 * <p>
 * A simple value condition. Like a data property in RDF.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class PropertyCondition extends AbstractCondition {

	/** The {@link Resource} of the {@link PropertyCondition}. */
	private Resource myResource = null;

	/** The operator (as a {@link URI}) of the {@link PropertyCondition}. */
	private URI myOperator = null;

	/** The value of the {@link PropertyCondition}. */
	private Object myValue = null;

	/**
	 * <p>
	 * Creates a new {@link PropertyCondition}.
	 * </p>
	 */
	public PropertyCondition() {

		super();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Visitable#accept(net.java.treaty.ContractVisitor)
	 */
	public void accept(ContractVisitor visitor) {

		boolean willVisitChildren;
		willVisitChildren = visitor.visit(this);

		if (willVisitChildren) {
			this.getResource().accept(visitor);
		}
		// no else.

		visitor.endVisit(this);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Constraint#check(net.java.treaty.VerificationReport,
	 * net.java.treaty.Verifier, net.java.treaty.VerificationPolicy)
	 */
	public boolean check(VerificationReport report, Verifier verifier,
			VerificationPolicy policy) {

		boolean result;

		if (!this.getResource().isProvided()) {

			report.log(this, VerificationResult.FAILURE, "Parameter "
					+ this.getResource().getRef()
					+ " is not provided for the first resource");
			result = false;
		}

		else {

			try {
				verifier.check(this);
				report.log(this, VerificationResult.SUCCESS);
				result = true;
			}
			// end try.

			catch (VerificationException x) {
				report.log(this, VerificationResult.FAILURE, x.getMessage());
				result = false;
			}
			// end catch.
		}
		// no else.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Constraint#isInstantiated()
	 */
	public boolean isInstantiated() {

		boolean result;

		if (this.getResource() == null) {
			result = false;
		}

		else {
			result = this.getResource().isInstantiated();
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.AbstractCondition#replaceResources(java.util.Map)
	 */
	public AbstractCondition replaceResources(Map<String, Resource> resources) {

		PropertyCondition result;

		result = new PropertyCondition();

		result.setOperator(this.getOperator());
		result.setValue(this.getValue());

		if (this.getResource() != null && !this.getResource().isInstantiated()
				&& resources.containsKey(this.getResource().getId())) {
			result.setResource(resources.get(this.getResource().getId()));
		}

		else {
			result.setResource(this.getResource());
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	
		StringBuffer buffer;
		buffer = new StringBuffer();
	
		buffer.append("aPropertyCondition(");
		buffer.append(' ').append(this.myOperator.toString()).append(' ').append(
				this.myValue);
	
		return buffer.toString();
	}

	/**
	 * <p>
	 * Returns the {@link URI} of the operator of this {@link PropertyCondition}.
	 * </p>
	 * 
	 * @return The {@link URI} of the operator of this {@link PropertyCondition}.
	 */
	public URI getOperator() {

		return this.myOperator;
	}

	/**
	 * <p>
	 * Returns the {@link Resource} of this {@link PropertyCondition}.
	 * </p>
	 * 
	 * @return The {@link Resource} of this {@link PropertyCondition}.
	 */
	public Resource getResource() {

		return this.myResource;
	}

	/**
	 * <p>
	 * Returns the value of this {@link PropertyCondition}.
	 * </p>
	 * 
	 * @return The value of this {@link PropertyCondition}.
	 */
	public Object getValue() {

		return this.myValue;
	}

	/**
	 * <p>
	 * Sets the {@link URI} of the operator of this {@link PropertyCondition}.
	 * </p>
	 * 
	 * @param operator
	 *          The {@link URI} of the operator of this {@link PropertyCondition}.
	 */
	public void setOperator(URI operator) {

		this.myOperator = operator;
	}

	/**
	 * <p>
	 * Sets the value of this {@link PropertyCondition}.
	 * </p>
	 * 
	 * @param value
	 *          The value of this {@link PropertyCondition}.
	 */
	public void setValue(Object value) {

		this.myValue = value;
	}

	/**
	 * <p>
	 * Sets the {@link Resource} of this {@link PropertyCondition}.
	 * </p>
	 * 
	 * @param resource
	 *          The {@link Resource} of this {@link PropertyCondition}.
	 */
	public void setResource(Resource resource) {

		this.myResource = resource;
	}
}