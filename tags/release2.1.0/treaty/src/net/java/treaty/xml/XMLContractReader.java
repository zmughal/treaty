/*
 * Copyright (C) 2008-2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.xml;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import net.java.treaty.ConditionContext;
import net.java.treaty.ConjunctiveCondition;
import net.java.treaty.Contract;
import net.java.treaty.ContractReader;
import net.java.treaty.ContractReaderException;
import net.java.treaty.ContractVocabulary;
import net.java.treaty.DisjunctiveCondition;
import net.java.treaty.ExistsCondition;
import net.java.treaty.InvalidContractException;
import net.java.treaty.NegatedCondition;
import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.TreatyException;
import net.java.treaty.ExclusiveDisjunctiveCondition;
import net.java.treaty.vocabulary.builtins.BuiltInOperators;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

/**
 * <p>
 * Utility class used to read contracts from XML files.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class XMLContractReader implements ContractReader {

	/**
	 * <p>
	 * Creates a new {@link XMLContractReader}.
	 * </p>
	 */
	public XMLContractReader() {

		super();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractReader#read(java.io.InputStream,
	 * net.java.treaty.ContractVocabulary)
	 */
	public Contract read(InputStream in, ContractVocabulary voc)
			throws TreatyException {

		SAXBuilder builder;
		builder = new SAXBuilder();
		builder.setValidation(false);

		try {
			Document document;
			document = builder.build(in);

			return read(document);
		}

		catch (Exception e) {
			throw new ContractReaderException(e);
		}
		// end catch.
	}

	/**
	 * <p>
	 * Creates an {@link XPath} for a given expression.
	 * </p>
	 * 
	 * @param expression
	 *          The expression as a {@link String}.
	 * @return The created {@link XPath}
	 * @throws JDOMException
	 *           Thrown, if the {@link XPath} creation fails.
	 */
	private XPath createXPath(String expression) throws JDOMException {

		XPath result;
		result = XPath.newInstance(expression);

		return result;
	}

	/**
	 * <p>
	 * Reads a {@link Contract} from a given {@link Document}.
	 * </p>
	 * 
	 * @param document
	 *          The {@link Document} representing the {@link Contract}.
	 * @return The read {@link Contract}.
	 * @throws TreatyException
	 *           Thrown if the reading process fails.
	 * @throws JDOMException
	 *           Thrown if the reading process fails.
	 */
	private Contract read(Document document) throws TreatyException,
			JDOMException {

		Contract result;
		result = new Contract();

		List<Resource> resources;
		String context;

		/* Read supplier resources. */
		resources = this.readResources(document, "/contract/supplier/resource");

		for (Resource resource : resources) {
			result.addSupplierResource(resource);
		}
		// end for.

		context = this.readContext(document, "/contract/supplier/context");

		if (context != null) {
			result.setSupplierContext(context);
		}
		// no else.

		/* Read consumer resources. */
		resources = this.readResources(document, "/contract/consumer/resource");

		for (Resource resource : resources) {
			result.addConsumerResource(resource);
		}
		// end for.

		context = this.readContext(document, "/contract/consumer/context");

		if (context != null) {
			result.setConsumerContext(context);
		}
		// no else.

		/* Read legislator resources. */
		resources = this.readResources(document, "/contract/external/resource");

		for (Resource resource : resources) {
			result.addExternalResource(resource);
		}
		// end for.

		context = readContext(document, "/contract/supplier/context");

		if (context != null) {
			result.setExternalContext(context);
		}
		// no else.

		/* Read conditions. */
		Element eConstraints;
		eConstraints = document.getRootElement().getChild("constraints");

		try {
			this.readConditions(eConstraints, result, result);
		}

		catch (URISyntaxException e) {
			throw new InvalidContractException(e);
		}
		// end catch.

		List<URI> uris;

		/* Read triggers. */
		uris = this.readURIs(document, "/contract/trigger");
		result.setTriggers(uris);

		/* Read actions. */
		uris = this.readURIs(document, "/contract/onSuccess");
		result.setOnVerificationSucceedsActions(uris);

		uris = this.readURIs(document, "/contract/onFailure");
		result.setOnVerificationFailsActions(uris);

		return result;
	}

	/**
	 * <p>
	 * Reads conditions from a given {@link Element} node and a given
	 * {@link ConditionContext} into a given {@link Contract}.
	 * </p>
	 * 
	 * @param node
	 *          The node whose Conditions shall be read.
	 * @param context
	 *          The {@link ConditionContext}.
	 * @param contract
	 *          The {@link Contract} for that the Conditions shall be read.
	 * @throws JDOMException
	 *           Thrown, if reading fails.
	 * @throws InvalidContractException
	 *           Thrown, if reading fails.
	 * @throws URISyntaxException
	 *           Thrown, if reading fails.
	 */
	@SuppressWarnings("unchecked")
	private void readConditions(Element node, ConditionContext context,
			Contract contract) throws JDOMException, InvalidContractException,
			URISyntaxException {

		List<Element> nodes;
		nodes = node.getChildren();

		for (Element element : nodes) {

			/* Probably read a property. */
			if (element.getName().equals("property")) {

				PropertyCondition condition;
				condition = new PropertyCondition();

				String resourceReference;
				resourceReference = element.getAttributeValue("resource");

				Resource resource;
				resource = contract.getResource(resourceReference);

				if (resource == null) {
					throw new InvalidContractException(
							"Invalid resource reference in condition: " + resourceReference);
				}

				condition.setResource(resource);

				String operator;
				operator = element.getAttributeValue("operator");

				/* try to find out whether this is a URI or a predefined alias. */
				URI operatorURI;
				operatorURI = BuiltInOperators.INSTANCE.getURI(operator);

				if (operatorURI == null) {

					try {
						condition.setOperator(new URI(operator));
					}

					catch (Exception x) {
						throw new InvalidContractException(
								"Invalid operator symbol encountered: " + operator);
					}
					// end catch.
				}

				else {
					condition.setOperator(operatorURI);
				}

				String value;
				value = element.getAttributeValue("value");

				condition.setValue(value);
				context.addCondition(condition);
			}

			/* Else probably read a relationship. */
			else if (element.getName().equals("relationship")) {

				RelationshipCondition relationshipCondition;
				relationshipCondition = new RelationshipCondition();

				String resourceReference1;
				resourceReference1 = element.getAttributeValue("resource1");

				Resource resource1;
				resource1 = contract.getResource(resourceReference1);

				if (resource1 == null) {
					throw new InvalidContractException(
							"Invalid resource reference at position 1 in condition: "
									+ relationshipCondition);
				}
				// no else.

				relationshipCondition.setResource1(resource1);

				String resourceReference2;
				resourceReference2 = element.getAttributeValue("resource2");

				Resource resource2;
				resource2 = contract.getResource(resourceReference2);

				if (resource2 == null) {
					throw new InvalidContractException(
							"Invalid resource reference at position 2 in condition: "
									+ relationshipCondition);
				}
				// no else.

				relationshipCondition.setResource2(resource2);

				String relationship;
				relationship = element.getAttributeValue("type");

				relationshipCondition.setRelationship(new URI(relationship));

				context.addCondition(relationshipCondition);
			}

			/* Else probably read an exists condition. */
			else if (element.getName().equals("mustExist")) {

				ExistsCondition condition;
				condition = new ExistsCondition();

				String resourceReference;
				resourceReference = element.getAttributeValue("resource");

				Resource resource;
				resource = contract.getResource(resourceReference);

				if (resource == null) {
					throw new InvalidContractException(
							"Invalid resource reference in must exist condition: "
									+ condition);
				}

				condition.setResource(resource);

				context.addCondition(condition);
			}

			/* Else probably read an and. */
			else if (element.getName().equals("and")) {

				ConjunctiveCondition conjunction;
				conjunction = new ConjunctiveCondition();

				this.readConditions(element, conjunction, contract);

				context.addCondition(conjunction);
			}

			/* Else probably read an or. */
			else if (element.getName().equals("or")) {

				DisjunctiveCondition disjunction;
				disjunction = new DisjunctiveCondition();

				this.readConditions(element, disjunction, contract);

				context.addCondition(disjunction);
			}

			/* Else probably read an xor. */
			else if (element.getName().equals("xor")) {

				ExclusiveDisjunctiveCondition xdisjunction;
				xdisjunction = new ExclusiveDisjunctiveCondition();

				this.readConditions(element, xdisjunction, contract);

				context.addCondition(xdisjunction);
			}

			/* Else probably read a not. */
			else if (element.getName().equals("not")) {

				NegatedCondition negation;
				negation = new NegatedCondition();

				this.readConditions(element, negation, contract);

				context.addCondition(negation);
			}
			// no else.
		}
		// end for (iteration on nodes).
	}

	/**
	 * <p>
	 * Reads the context for a given XPath expression and a given {@link Document}
	 * 
	 * @param document
	 *          The document whose {@link URI}s shall be read.
	 * @param xpath
	 *          The XPath expression as a String.
	 * @return A {@link List} of read {@link URI}s.
	 * @throws JDOMException
	 *           Thrown, if reading fails.
	 */
	private String readContext(Document document, String xpath)
			throws JDOMException {

		String result;

		XPath xPath;
		xPath = this.createXPath(xpath);

		Element node;
		node = (Element) xPath.selectSingleNode(document);

		if (node != null) {
			result = node.getText();
		}

		else {
			result = null;
		}

		return result;
	}

	/**
	 * <p>
	 * Read recourees for a given {@link Document} and a given XPath expression.
	 * </p>
	 * 
	 * @param document
	 *          The document whose {@link URI}s shall be read.
	 * @param xpath
	 *          The XPath expression as a String.
	 * @return A {@link List} of read {@link Resource}s.
	 * @throws InvalidContractException
	 *           Thrown, if reading fails.
	 * @throws JDOMException
	 *           Thrown, if reading fails.
	 */
	@SuppressWarnings("unchecked")
	private List<Resource> readResources(Document document, String xpath)
			throws InvalidContractException, JDOMException {

		XPath xPath;
		xPath = createXPath(xpath);

		List<Element> nodes;
		nodes = xPath.selectNodes(document);

		List<Resource> resources;
		resources = new ArrayList<Resource>();

		for (Element node : nodes) {

			Resource resource;
			resource = new Resource();

			String id;
			id = node.getAttributeValue("id");
			resource.setId(id);

			String type = node.getChild("type").getText();

			try {
				resource.setType(new URI(type));
			}

			catch (URISyntaxException e) {
				throw new InvalidContractException("Invalid URI " + type);
			}

			String name;
			name = node.getChildText("name");
			resource.setName(name);

			if (name == null) {
				String reference;
				reference = node.getChildText("ref");
				resource.setRef(reference);
			}
			// no else.

			resources.add(resource);
		}
		// end for (iteration on nodes).

		return resources;
	}

	/**
	 * <p>
	 * Reads the {@link URI}s for a given XPath expression and a given
	 * {@link Document}.
	 * </p>
	 * 
	 * @param document
	 *          The document whose {@link URI}s shall be read.
	 * @param xpath
	 *          The XPath expression as a String.
	 * @return A {@link List} of read {@link URI}s.
	 * @throws InvalidContractException
	 *           Thrown, if reading fails.
	 * @throws JDOMException
	 *           Thrown, if reading fails.
	 */
	@SuppressWarnings("unchecked")
	private List<URI> readURIs(Document document, String xpath)
			throws InvalidContractException, JDOMException {

		List<URI> result;

		XPath xPath;
		xPath = this.createXPath(xpath);

		List<Element> nodes;
		nodes = xPath.selectNodes(document);

		result = new ArrayList<URI>();

		for (Element node : nodes) {

			String uriAsString;
			uriAsString = node.getTextTrim();
			try {
				result.add(new URI(uriAsString));
			}

			catch (URISyntaxException e) {
				throw new InvalidContractException("Invalid URI " + uriAsString);
			}
			// end catch.
		}
		// end for (iteration on nodes).

		return result;
	}
}