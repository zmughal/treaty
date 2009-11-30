/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.views;

import static net.java.treaty.eclipse.Constants.VERIFICATION_RESULT;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.java.treaty.AbstractCondition;
import net.java.treaty.Annotatable;
import net.java.treaty.ComplexCondition;
import net.java.treaty.Contract;
import net.java.treaty.ExistsCondition;
import net.java.treaty.Negation;
import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.VerificationResult;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.snippets.Snippet156;

/**
 * <p>
 * The {@link ContractVisualizationView} can be used to display one
 * {@link Contract} instance in a graph-based view.
 * </p>
 * 
 * @author Claas Wilke
 */
public class ContractVisualizationView extends net.java.treaty.viz.ContractView {

	/** Generated ID used for serialization. */
	private static final long serialVersionUID = 916862140623747905L;

	/**
	 * <p>
	 * Creates a new {@link ContractVisualizationView} for a given
	 * {@link Contract}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} of the {@link ContractVisualizationView}.
	 */
	public ContractVisualizationView(Contract contract) {

		super();

		this.setModel(contract);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.viz.ContractView#getIcon(net.java.treaty.Resource)
	 */
	protected Icon getIcon(Resource resource) {

		Icon result;

		boolean isVariable;
		isVariable = resource.getName() == null;

		Image image;
		image = IconProvider.findIcon(resource.getType(), isVariable);

		if (image != null) {
			result = new ImageIcon(Snippet156.convertToAWT(image.getImageData()));
		}

		else {
			result = null;
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.viz.ContractView#getEdgePaint(net.java.treaty.Annotatable,
	 * net.java.treaty.Annotatable)
	 */
	protected Paint getEdgePaint(Annotatable source, Annotatable target) {

		Paint result;

		Annotatable verifiedCondition;
		verifiedCondition = this.findAnnotatableForResult(source, target);

		Object verificationResult;
		verificationResult = null;

		if (verifiedCondition != null) {
			verificationResult = verifiedCondition.getProperty(VERIFICATION_RESULT);
		}
		// no else.

		if (verificationResult == VerificationResult.FAILURE) {
			result = Color.RED;
		}

		else if (verificationResult == VerificationResult.SUCCESS) {
			result = Color.GREEN;
		}

		else {
			result = Color.GRAY;
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.viz.ContractView#getEdgeStroke(net.java.treaty.Annotatable,
	 * net.java.treaty.Annotatable)
	 */
	protected Stroke getEdgeStroke(Annotatable source, Annotatable target) {

		Stroke result;

		Annotatable verifiedCondition;
		verifiedCondition = this.findAnnotatableForResult(source, target);

		Object verificationResult;
		verificationResult = null;

		if (verifiedCondition != null) {
			verificationResult = verifiedCondition.getProperty(VERIFICATION_RESULT);
		}
		// no else.

		if (verificationResult == VerificationResult.FAILURE) {
			result = new BasicStroke(2);
		}

		else if (verificationResult == VerificationResult.SUCCESS) {
			result = new BasicStroke(2);
		}

		else {
			result = new BasicStroke(1);
		}

		return result;
	}

	/**
	 * <p>
	 * A helper method that searches the right {@link Annotatable} to color or
	 * stroke an edge between two given {@link Annotatable}s.
	 * </p>
	 * 
	 * @param source
	 *          The source {@link Annotatable} of the edge.
	 * @param target
	 *          The target {@link Annotatable} of the edge.
	 * @return The found {@link Annotatable} or <code>null</code>.
	 */
	private Annotatable findAnnotatableForResult(Annotatable source,
			Annotatable target) {

		Annotatable result;
		result = null;

		/* If the target is a condition it provides the result. */
		if (target instanceof AbstractCondition) {
			result = target;
		}

		/* If the target is a resource, search the condition of the resource. */
		else if (target instanceof Resource) {

			/*
			 * If the source is a complex condition, search the condition of the
			 * resource.
			 */
			if (source instanceof ComplexCondition) {
				result =
						this.findConditionOfResource((ComplexCondition) source,
								(Resource) target);
			}

			/* Else the source condition provides the result. */
			else {
				result = source;
			}
		}
		return result;
	}

	/**
	 * <p>
	 * A helper method that searches the {@link AbstractCondition} to which a
	 * given {@link Resource} belongs to. The {@link AbstractCondition} can be a
	 * given {@link AbstractCondition} or a part of this {@link AbstractCondition}
	 * , if it is a {@link ComplexCondition}.
	 * </p>
	 * 
	 * @param condition
	 *          The {@link AbstractCondition} in whose parts shall be searched
	 *          for.
	 * @param resource
	 *          The {@link Resource} whose {@link AbstractCondition} shall be
	 *          found.
	 * @return The found {@link AbstractCondition} that ownes the given
	 *         {@link Resource} or <code>null</code>.
	 */
	private Annotatable findConditionOfResource(AbstractCondition condition,
			Resource resource) {

		Annotatable result;
		result = null;

		/* If the condition is complex, search in its parts. */
		if (condition instanceof ComplexCondition) {

			ComplexCondition complexCondition;
			complexCondition = (ComplexCondition) condition;

			for (AbstractCondition part : complexCondition.getParts()) {

				result = this.findConditionOfResource(part, resource);

				if (result != null) {
					break;
				}
				// no else.
			}
			// end for.
		}

		/* If the condition is an ExistsCondition, check its resource. */
		else if (condition instanceof ExistsCondition) {

			ExistsCondition existsCondition;
			existsCondition = (ExistsCondition) condition;

			if (existsCondition.getResource().equals(resource)) {
				result = existsCondition;
			}
			// no else.
		}

		/* If the condition is an Negation, check its negated condition. */
		else if (condition instanceof Negation) {

			Negation negation;
			negation = (Negation) condition;

			result =
					this
							.findConditionOfResource(negation.getNegatedCondition(), resource);
		}

		/* If the condition is an PropertyCondition, check its resource. */
		else if (condition instanceof PropertyCondition) {

			PropertyCondition propertyCondition;
			propertyCondition = (PropertyCondition) condition;

			if (propertyCondition.getResource().equals(resource)) {
				result = propertyCondition;
			}
			// no else.
		}

		/* If the condition is an RelationshipCondition, check its resources. */
		else if (condition instanceof RelationshipCondition) {

			RelationshipCondition relationshipCondition;
			relationshipCondition = (RelationshipCondition) condition;

			if (relationshipCondition.getResource1().equals(resource)
					|| relationshipCondition.getResource2().equals(resource)) {
				result = relationshipCondition;
			}
			// no else.
		}
		// no else.

		return result;
	}
}