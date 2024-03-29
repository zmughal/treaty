/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package nz.ac.massey.treaty.verification;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import nz.ac.massey.treaty.Condition;
import nz.ac.massey.treaty.ConditionContext;
import nz.ac.massey.treaty.Conjunction;
import nz.ac.massey.treaty.Contract;
import nz.ac.massey.treaty.Disjunction;
import nz.ac.massey.treaty.InvalidContractException;
import nz.ac.massey.treaty.PropertyCondition;
import nz.ac.massey.treaty.Resource;
import nz.ac.massey.treaty.XDisjunction;

import org.java.plugin.registry.ExtensionPoint;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

/**
 * Utility class used to read contracts from xml files.
 * @author Jens Dietrich
 * @version 0.1 <27/04/2008>
 * @since 0.1
 */

public class ContractReader  {
	public ContractReader() {
		super();
	} 
	/**
	 * Read a contract from an input stream.
	 * @param in
	 * @param xp
	 * @return a contract
	 * @throws IOException
	 */
	public Contract read (ExtensionPoint xp,InputStream in) throws ContractReaderException {
		SAXBuilder builder = new SAXBuilder();
		builder.setValidation(false);
		try {
			Document doc = builder.build(in);
			return read(xp,doc);
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
	private Contract read(ExtensionPoint xp,Document doc) throws ContractReaderException,InvalidContractException,JDOMException {
		Contract contract = new Contract();
		
		// read extension resources
		XPath xpath =  createXPath("/contract/extension/resource");
		List<Element> nodes = xpath.selectNodes(doc);
		List<Resource> resources = readResources(nodes);
		for (Resource r:resources)
			contract.addExtensionResource(r);
		// read extension point resources
		xpath =  createXPath("/contract/extensionpoint/resource");
		nodes = xpath.selectNodes(doc);
		resources = readResources(nodes);
		for (Resource r:resources)
			contract.addExtensionPointResource(r);
		
		// read conditions
		Element eConstraints = doc.getRootElement().getChild("constraints");
		
		try {
			readConditions(eConstraints,contract,contract);
		}  catch (URISyntaxException e) {
			throw new InvalidContractException(e);
		}
		
		return contract;
	}
	private void readConditions(Element node, ConditionContext context, Contract contract) throws JDOMException, InvalidContractException, URISyntaxException {
		XPath xpath =  createXPath("condition|propertyCondition|and|or|xor");
		List<Element> nodes = xpath.selectNodes(node);
		for (Element e:nodes) {
			if (e.getName().equals("propertycondition")){
				PropertyCondition condition = new PropertyCondition();
				String resourceRef = e.getAttributeValue("resource");
				Resource resource = contract.getResource(resourceRef);
				if (resource==null)
					throw new InvalidContractException("Invalid resource reference in condition: " + resourceRef);
				condition.setResource(resource);
				String property = e.getAttributeValue("property");
				condition.setProperty(new URI(property));
				String operator = e.getAttributeValue("operator");
				condition.setOperator(new URI(operator));
				String value = e.getAttributeValue("value");
				condition.setValue(value);
				context.addCondition(condition);
			}
			else if (e.getName().equals("condition")){
				Condition condition = new Condition();
				
				String resourceRef1 = e.getAttributeValue("resource1");
				Resource resource1 = contract.getResource(resourceRef1);
				if (resource1==null)
					throw new InvalidContractException("Invalid resource reference at position 1 in condition: " + condition);
				condition.setResource1(resource1);
				
				String resourceRef2 = e.getAttributeValue("resource2");				
				Resource resource2 = contract.getResource(resourceRef2);
				if (resource2==null)
					throw new InvalidContractException("Invalid resource reference at position 2 in condition: " + condition);
				condition.setResource2(resource2);
				String relationship = e.getAttributeValue("relationship");
				condition.setRelationship(new URI(relationship));
				
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
		}
		
	}
	private List<Resource> readResources(List<Element> nodes) throws InvalidContractException{
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
	

}
