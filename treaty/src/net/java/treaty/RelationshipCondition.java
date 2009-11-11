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
import java.util.Map;

/**
 * <p>
 * Simple condition representing a binary predicate associating resources.
 * Example: aClass implements anInterface. Similar to OWL object properties.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class RelationshipCondition extends AbstractCondition {

	/** The first {@link Resource} of this {@link RelationshipCondition}. */
	private Resource myResource1 = null;

	/** The second {@link Resource} of this {@link RelationshipCondition}. */
	private Resource myResource2 = null;

	/** The type of relationship of this {@link RelationshipCondition}. */
	private URI myRelationshipType = null;

	/**
	 * <p>
	 * Creates a new {@link RelationshipCondition}.
	 * </p>
	 */
	public RelationshipCondition() {

		super();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Constraint#check(net.java.treaty.VerificationReport,
	 * net.java.treaty.Verifier, net.java.treaty.VerificationPolicy)
	 */
	public boolean check(VerificationReport report, Verifier verifier,
			VerificationPolicy policy) {

		boolean result;
		result = true;

		/* Check if resource1 is provided. */
		if (!this.getResource1().isProvided()) {

			report.log(this, VerificationResult.FAILURE, "Parameter "
					+ this.getResource1().getRef()
					+ " is not provided for the first resource.");

			result = false;
		}

		/* Check if resource2 is provided. */
		if (!this.getResource2().isProvided()) {

			report.log(this, VerificationResult.FAILURE, "Parameter "
					+ this.getResource2().getRef()
					+ " is not provided for the second resource");

			result = false;
		}

		/* If both resources are available, verify the relationship. */
		if (result) {

			try {
				verifier.check(this);
				report.log(this, VerificationResult.SUCCESS);
			}

			catch (VerificationException x) {
				report.log(this, VerificationResult.FAILURE, x.getMessage());

				result = false;
			}
			// end catch.
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Constraint#isInstantiated()
	 */
	public boolean isInstantiated() {

		boolean result;

		if (this.getResource1() != null && this.getResource2() != null) {
			result =
					this.getResource1().isInstantiated()
							&& this.getResource2().isInstantiated();
		}

		else {
			result = false;
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.AbstractCondition#replaceResources(java.util.Map)
	 */
	public AbstractCondition replaceResources(Map<String, Resource> resources) {

		RelationshipCondition result;
		result = new RelationshipCondition();

		result.setRelationship(this.getRelationship());

		if (this.getResource1() != null && !this.getResource1().isInstantiated()
				&& resources.containsKey(this.getResource1().getId())) {
			result.setResource1(resources.get(this.getResource1().getId()));
		}

		else {
			result.setResource1(this.getResource1());
		}

		if (this.getResource2() != null && !this.getResource2().isInstantiated()
				&& resources.containsKey(this.getResource2().getId())) {
			result.setResource2(resources.get(this.getResource2().getId()));
		}

		else {
			result.setResource2(this.getResource2());
		}

		return result;
	}

	public URI getRelationship() {

		return myRelationshipType;
	}

	public void setRelationship(URI relationship) {

		this.myRelationshipType = relationship;
	}

	public Resource getResource1() {

		return myResource1;
	}

	public void setResource1(Resource resource1) {

		this.myResource1 = resource1;
	}

	public Resource getResource2() {

		return myResource2;
	}

	public void setResource2(Resource resource2) {

		this.myResource2 = resource2;
	}

	public void accept(ContractVisitor visitor) {

		boolean f = visitor.visit(this);
		if (f) {
			this.getResource1().accept(visitor);
			this.getResource2().accept(visitor);
		}
		visitor.endVisit(this);

	}

	public String toString() {

		return new StringBuffer().append("aCondition(").append(
				this.myRelationshipType).append('(').append(this.myResource1).append(
				',').append(this.myResource2).append("))").toString();
	}
}