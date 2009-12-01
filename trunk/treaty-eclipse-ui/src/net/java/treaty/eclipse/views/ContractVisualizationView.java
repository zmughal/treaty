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
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import net.java.treaty.AbstractCondition;
import net.java.treaty.Annotatable;
import net.java.treaty.Connector;
import net.java.treaty.Contract;
import net.java.treaty.ExistsCondition;
import net.java.treaty.PropertyCondition;
import net.java.treaty.PropertySupport;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.VerificationResult;
import net.java.treaty.eclipse.Constants;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.EclipseExtensionPoint;
import net.java.treaty.viz.ContractView.Node;

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
	 */
	public ContractVisualizationView() {

		super();
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
	protected Paint getEdgePaint(Node source, Node target) {

		Paint result;

		VerificationResult verificationResult;
		verificationResult = this.getStatus(target);

		switch (verificationResult) {

		case FAILURE:
			result = Color.RED;
			break;

		case SUCCESS:
			result = Color.GREEN;
			break;

		default:
			result = Color.YELLOW;
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.viz.ContractView#getEdgeStroke(net.java.treaty.viz.ContractView
	 * .Node, net.java.treaty.viz.ContractView.Node)
	 */
	protected Stroke getEdgeStroke(Node source, Node target) {

		Stroke result;

		VerificationResult verificationResult;
		verificationResult = this.getStatus(target);

		switch (verificationResult) {

		case FAILURE:
			result = new BasicStroke(2);
			break;

		case SUCCESS:
			result = new BasicStroke(2);
			break;

		default:
			result = new BasicStroke(1);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.viz.ContractView#getToolTip(net.java.treaty.viz.ContractView
	 * .CompositionNodeType, net.java.treaty.AbstractCondition)
	 */
	protected String getToolTip(CompositionNodeType nodeType,
			AbstractCondition condition) {

		Map<String, Object> properties;
		properties = new LinkedHashMap<String, Object>();
		properties.put("type", nodeType.name());

		/* Probably display a verification exception. */
		String verificationException;
		verificationException = this.getVerificationException(condition);

		if (verificationException.length() > 0) {
			properties.put("failure", verificationException);
		}
		// no else.

		return this.asHTMLTable(properties);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.viz.ContractView#getToolTip(net.java.treaty.Connector)
	 */
	protected String getToolTip(Connector connector) {

		Map<String, Object> properties = new LinkedHashMap<String, Object>();

		if (connector != null) {

			if (connector instanceof EclipseExtensionPoint) {
				properties.put("type", "ExtensionPoint");
			}

			else if (connector instanceof EclipseExtension) {
				properties.put("type", "Extension");
			}
			// no else.

			properties.put("id", connector.getId());
		}

		else {
			properties.put("type", "unbound");
		}
		// end else.

		return this.asHTMLTable(properties);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.viz.ContractView#getToolTip(net.java.treaty.ExistsCondition
	 * )
	 */
	protected String getToolTip(ExistsCondition existsCondition) {

		Map<String, Object> properties;
		properties = new LinkedHashMap<String, Object>();
		properties.put("resource", existsCondition.getResource().getId());

		/* Probably display a verification exception. */
		String verificationException;
		verificationException = this.getVerificationException(existsCondition);

		if (verificationException.length() > 0) {
			properties.put("failure", verificationException);
		}
		// no else.

		return this.asHTMLTable(properties);
	}

	/*
	 * (non-Javadoc)
	 * @seenet.java.treaty.viz.ContractView#getToolTip(net.java.treaty.
	 * RelationshipCondition)
	 */
	protected String getToolTip(RelationshipCondition relationshipCondition) {

		Map<String, Object> properties;
		properties = new LinkedHashMap<String, Object>();

		properties.put("id", relationshipCondition.getRelationship());

		/* Probably display a verification exception. */
		String verificationException;
		verificationException =
				this.getVerificationException(relationshipCondition);

		if (verificationException.length() > 0) {
			properties.put("failure", verificationException);
		}
		// no else.

		return this.asHTMLTable(properties);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.viz.ContractView#getToolTip(net.java.treaty.Resource)
	 */
	protected String getToolTip(Resource resource) {

		Map<String, Object> properties = new LinkedHashMap<String, Object>();

		properties.put("id", resource.getId());
		properties.put("name", resource.getName() == null ? "-" : resource
				.getName());
		properties.put("ref", resource.getRef() == null ? "-" : resource.getRef());
		properties.put("type", resource.getType());

		/* Probably display a verification exception. */
		String verificationException;
		verificationException = this.getVerificationException(resource);

		if (verificationException.length() > 0) {
			properties.put("failure", verificationException);
		}
		// no else.

		return this.asHTMLTable(properties);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.viz.ContractView#getToolTip(net.java.treaty.PropertyCondition
	 * )
	 */
	protected String getToolTip(PropertyCondition propertyCondition) {

		Map<String, Object> properties;
		properties = new LinkedHashMap<String, Object>();

		properties.put("operator", propertyCondition.getOperator());
		properties.put("value", propertyCondition.getValue());
		properties.put("value type", propertyCondition.getValue() == null ? "null"
				: propertyCondition.getValue().getClass());

		/* Probably display a verification exception. */
		String verificationException;
		verificationException = this.getVerificationException(propertyCondition);

		if (verificationException.length() > 0) {
			properties.put("failure", verificationException);
		}
		// no else.

		return this.asHTMLTable(properties);
	}

	/**
	 * <p>
	 * A helper method that returns the {@link VerificationResult} for a given
	 * {@link Node}.
	 * </p>
	 * 
	 * @param node
	 *          The {@link Node} whose {@link VerificationResult} shall be
	 *          returned.
	 * @return The {@link VerificationResult} for the given {@link Node}.
	 */
	private VerificationResult getStatus(Node node) {

		VerificationResult result;

		Annotatable annotatable;
		annotatable = node.getObject();

		if (annotatable == null) {
			result = VerificationResult.UNKNOWN;
		}

		else if (annotatable instanceof Resource) {

			result = VerificationResult.UNKNOWN;

			/* Look for referenced conditions. */
			for (Node aSuccessor : this.graph.getSuccessors(node)) {

				result = this.getStatus(aSuccessor);

				if (result == VerificationResult.FAILURE) {
					break;
				}
				// no else.
			}
			// end for.
		}

		else {
			result =
					(VerificationResult) annotatable.getProperty(VERIFICATION_RESULT);

			if (result == null) {
				result = VerificationResult.UNKNOWN;
			}
			// no else.
		}
		// end else.

		return result;
	}

	/**
	 * <p>
	 * A helper method that returns a {@link String} describing the probably
	 * existing verification exception of a given {@link PropertySupport}.
	 * </p>
	 * 
	 * @param propertySupport
	 *          The {@link PropertySupport} whose exception shall be returned.
	 * @return The verification exception as a {@link String} or an empty
	 *         {@link String}.
	 */
	private String getVerificationException(PropertySupport propertySupport) {

		StringBuffer buffer;
		buffer = new StringBuffer();

		if (propertySupport.getProperty(Constants.VERIFICATION_EXCEPTION) != null
				&& propertySupport.getProperty(Constants.VERIFICATION_EXCEPTION) instanceof Exception) {

			Throwable exception =
					(Exception) propertySupport
							.getProperty(Constants.VERIFICATION_EXCEPTION);

			buffer.append("<font color=\"red\">");

			while (exception != null) {

				if (exception.getMessage() != null
						&& exception.getMessage().length() > 0) {
					buffer.append(exception.getMessage());
				}

				else {
					buffer.append(exception.getClass().getSimpleName());
				}

				if (exception.getCause() != null
						&& exception.getCause().equals(exception)) {
					break;
				}

				else {
					exception = exception.getCause();

					if (exception != null) {
						buffer.append("<br />");
						buffer.append("<i>caused by:</i><br />");
					}
					// no else.
				}
				// end else.
			}
			// end while.

			buffer.append("</font>");
		}
		// no else.

		return buffer.toString();
	}
}