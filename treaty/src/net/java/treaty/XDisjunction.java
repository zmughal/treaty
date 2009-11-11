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
 * Exclusive disjunction (xor).
 * </p>
 * 
 * @author Jens Dietrich
 */
public class XDisjunction extends ComplexCondition {

	/**
	 * <p>
	 * Creates a new {@link XDisjunction}.
	 * </p>
	 */
	public XDisjunction() {

		super();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.AbstractCondition#replaceResources(java.util.Map)
	 */
	public AbstractCondition replaceResources(Map<String, Resource> resources) {

		XDisjunction result = new XDisjunction();

		for (AbstractCondition condition : this.parts) {
			result.addCondition(condition.replaceResources(resources));
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ComplexCondition#isInstantiated()
	 */
	@Override
	public boolean isInstantiated() {
	
		boolean result;
	
		if (this.parts == null) {
			result = false;
		}
	
		else if (this.parts.size() == 0) {
			result = true;
		}
	
		else {
			result = false;
			for (AbstractCondition part : this.parts) {
				if (part.isInstantiated()) {
					result = true;
					break;
				}
				// no else.
			}
			// end for.
		}
		// end else.
	
		return result;
	}

	public void accept(ContractVisitor visitor) {

		boolean f = visitor.visit(this);
		if (f) {
			for (AbstractCondition p : parts)
				p.accept(visitor);
		}
		visitor.endVisit(this);
	}

	public boolean check(VerificationReport report, Verifier verifier,
			VerificationPolicy policy) {

		int okcount = 0;
		for (AbstractCondition p : parts) {
			okcount = okcount + (p.check(report, verifier, policy) ? 1 : 0);
		}
		if (okcount == 1)
			report.log(this, VerificationResult.SUCCESS);
		else if (okcount == 0)
			report.log(this, VerificationResult.FAILURE,
					"no part of this condition is satisfied");
		else
			report.log(this, VerificationResult.FAILURE,
					"too many parts of this condition are satisfied");
		return okcount == 1;
	}

	/**
	 * Get the name of the logical connective used.
	 * 
	 * @return
	 */
	public String getConnective() {

		return "xor";
	}
}
