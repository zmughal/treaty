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

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

/**
 * Utility class used to read contracts from xml files.
 * @author Jens Dietrich
 */

public class ContractReader  {
	
	private ResourceManager loader = null;
	
	public ContractReader(ResourceManager loader) {
		super();
		this.loader = loader;
	} 
	/**
	 * Read a contract from an input stream.
	 * @param in
	 * @return
	 * @throws TreatyException
	 */
	public SimpleContract read (InputStream in) throws TreatyException {
		SAXBuilder builder = new SAXBuilder();
		builder.setValidation(false);
		try {
			Document doc = builder.build(in);
			return read(doc);
		} catch (Exception e) {
			throw new ContractReaderException(e);
		} 
	}
	private void log(String msg) {
		System.out.println(msg);
	}
	private XPath createXPath(String expression) throws JDOMException {
		XPath xpath =  XPath.newInstance(expression);
		// xpath.addNamespace(SWRLX);
		return xpath;
	} 
	private SimpleContract read(Document doc) throws TreatyException,JDOMException {
		SimpleContract contract = new SimpleContract();
		
		// read extension resources
		List<Resource> resources = readResources(doc,"/contract/supplier/resource");
		for (Resource r:resources) {
			contract.addSupplierResource(r);
		}
		String context = readContext(doc,"/contract/supplier/context");
		if (context!=null) {
			contract.setSupplierContext(context);
		}
		// read extension point resources
		resources = readResources(doc,"/contract/consumer/resource");
		for (Resource r:resources) {
			contract.addConsumerResource(r);
		}
		context = readContext(doc,"/contract/consumer/context");
		if (context!=null) {
			contract.setConsumerContext(context);
		}
		// read third party resources
		resources = readResources(doc,"/contract/external/resource");
		for (Resource r:resources) {
			contract.addExternalResource(r);
		}
		context = readContext(doc,"/contract/supplier/context");
		if (context!=null) {
			contract.setExternalContext(context);
		}
		
		// read conditions
		Element eConstraints = doc.getRootElement().getChild("constraints");
		
		try {
			readConditions(eConstraints,contract,contract);
		}  catch (URISyntaxException e) {
			throw new InvalidContractException(e);
		}
		
		return contract;
	}

	private String readContext(Document doc, String xpath) throws JDOMException {
		XPath xp =  createXPath(xpath);
		Element node = (Element)xp.selectSingleNode(doc);
		if (node!=null) {
			return node.getText();
		}
		return null;
	}
	private void readConditions(Element node, ConditionContext context, SimpleContract contract) throws JDOMException, InvalidContractException, URISyntaxException {
		
		List<Element> nodes = node.getChildren();
		for (Element e:nodes) {
			if (e.getName().equals("property")){
				PropertyCondition condition = new PropertyCondition();
				String resourceRef = e.getAttributeValue("resource");
				Resource resource = contract.getResource(resourceRef);
				if (resource==null)
					throw new InvalidContractException("Invalid resource reference in condition: " + resourceRef);
				condition.setResource(resource);
				String property = e.getAttributeValue("type");
				condition.setProperty(property);
				String operator = e.getAttributeValue("operator");
				Operator op = Operator.getInstance(operator);
				if (op==null) {
					throw new InvalidContractException("Invalid operator symbol encountered: " + operator);
				}
				else {
					condition.setOperator(op);
				}
				String value = e.getAttributeValue("value");
				condition.setValue(value);
				context.addCondition(condition);
			}
			else if (e.getName().equals("relationship")){
				RelationshipCondition relationshipCondition = new RelationshipCondition();
				
				String resourceRef1 = e.getAttributeValue("resource1");
				Resource resource1 = contract.getResource(resourceRef1);
				if (resource1==null)
					throw new InvalidContractException("Invalid resource reference at position 1 in condition: " + relationshipCondition);
				relationshipCondition.setResource1(resource1);
				
				String resourceRef2 = e.getAttributeValue("resource2");				
				Resource resource2 = contract.getResource(resourceRef2);
				if (resource2==null)
					throw new InvalidContractException("Invalid resource reference at position 2 in condition: " + relationshipCondition);
				relationshipCondition.setResource2(resource2);
				String relationship = e.getAttributeValue("type");
				relationshipCondition.setRelationship(new URI(relationship));
				
				context.addCondition(relationshipCondition);
			}
			else if (e.getName().equals("mustExist")){
				ExistsCondition condition = new ExistsCondition();
				
				String resourceRef = e.getAttributeValue("resource");
				Resource resource = contract.getResource(resourceRef);
				if (resource==null)
					throw new InvalidContractException("Invalid resource reference in must exist condition: " + condition);
				condition.setResource(resource);
				
				context.addCondition(condition);
			}
			else if (e.getName().equals("and")) {
				Conjunction conjunction = new Conjunction();
				readConditions(e,conjunction,contract);
				context.addCondition(conjunction);
			}
			else if (e.getName().equals("or")) {
				Disjunction disjunction = new Disjunction();
				readConditions(e,disjunction,contract);
				context.addCondition(disjunction);
			}
			else if (e.getName().equals("xor")) {
				XDisjunction xdisjunction = new XDisjunction();
				readConditions(e,xdisjunction,contract);
				context.addCondition(xdisjunction);
			}
			else if (e.getName().equals("not")) {
				Negation neg = new Negation();
				readConditions(e,neg,contract);
				context.addCondition(neg);
			}
		}
	}
	private List<Resource> readResources(Document doc,String xpath) throws InvalidContractException, JDOMException{
		XPath xp =  createXPath(xpath);
		List<Element> nodes = xp.selectNodes(doc);
		
		List<Resource> resources = new ArrayList<Resource>();
		for (Element node:nodes) {
			Resource r = new Resource();
			String id = node.getAttributeValue("id");
			r.setId(id);
			String type = node.getChild("type").getText();
			try {
				r.setType(new URI(type));
			} catch (URISyntaxException e) {
				throw new InvalidContractException("Invalid URI " + type);
			}
			String name = node.getChildText("name");
			r.setName(name);
			if (name==null) {
				String ref = node.getChildText("ref");
				r.setRef(ref);
			}
			
			resources.add(r);
		}
		return resources;
	}
	public ResourceManager getLoader() {
		return loader;
	} 
	

}
