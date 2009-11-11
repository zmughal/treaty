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

import java.util.Map;

/**
 * <p>
 * Condition to check the existence and the type of mandatory resources. This is
 * useful for resources that must exist but are not referenced in other
 * constraints.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class ExistsCondition extends AbstractCondition {

	/** The {@link Resource} of this {@link ExistsCondition}. */
	private Resource myResource = null;

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Constraint#isInstantiated()
	 */
	public boolean isInstantiated() {

		/*
		 * The condition is instantiated as well, if its resource does not exist.
		 * Because to check that it is sense at all.
		 */
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.AbstractCondition#replaceResources(java.util.Map)
	 */
	public AbstractCondition replaceResources(Map<String, Resource> resources) {

		ExistsCondition result;
		result = new ExistsCondition();

		if (this.getResource() != null && !this.getResource().isInstantiated()
				&& resources.containsKey(this.getResource().getId())) {
			result.setResource(resources.get(this.getResource().getId()));
		}

		else {
			result.setResource(this.getResource());
		}

		return result;
	}

	/**
	 * <p>
	 * Returns the {@link Resource} of this {@link ExistsCondition}.
	 * </p>
	 * 
	 * @return The {@link Resource} of this {@link ExistsCondition}.
	 */
	public Resource getResource() {

		return myResource;
	}

	/**
	 * <p>
	 * Sets the {@link Resource} of this {@link ExistsCondition}.
	 * </p>
	 * 
	 * @return The {@link Resource} of this {@link ExistsCondition}.
	 */
	public void setResource(Resource resource) {

		this.myResource = resource;
	}

	public void accept(ContractVisitor visitor) {

		boolean f = visitor.visit(this);
		if (f) {
			this.getResource().accept(visitor);
		}
		visitor.endVisit(this);

	}

	public String toString() {

		return new StringBuffer().append("aCondition(").append(this.myResource)
				.append(" exists)").toString();
	}

	public boolean check(VerificationReport report, Verifier verifier,
			VerificationPolicy policy) {

		boolean result = true;
		if (!this.getResource().isProvided()) {
			report.log(this, VerificationResult.FAILURE, "Parameter "
					+ this.getResource().getRef() + " is not provided for the resource");
			result = false;
		}
		if (!result)
			return false;
		try {
			verifier.check(this);
			report.log(this, VerificationResult.SUCCESS);
			return true;
		} catch (VerificationException x) {
			report.log(this, VerificationResult.FAILURE, x.getMessage());
			return false;
		}

	}
}