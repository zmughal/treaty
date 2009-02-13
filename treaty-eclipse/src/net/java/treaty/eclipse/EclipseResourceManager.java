/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */


package net.java.treaty.eclipse;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.osgi.framework.Bundle;

import net.java.treaty.Connector;
import net.java.treaty.InstantiationContext;
import net.java.treaty.ResourceManager;
import net.java.treaty.ResourceLoaderException;
/**
 * Utility to extract resources.
 * @author Jens Dietrich
 */

public class EclipseResourceManager implements ResourceManager {

	public String resolve(URI type, String ref,net.java.treaty.Connector connector,InstantiationContext context) throws ResourceLoaderException {
		EclipseExtension extension = (EclipseExtension)connector;
		Document doc = this.loadPluginMetaData(connector);
		if (context==null || context==InstantiationContext.DEFAULT_CONTEXT) {
			// prepend xpath expression to select plugin node
			// example (serviceprovider/@class is the actual resource reference):
			// /plugin/extension[@point="net.java.treaty.eclipse.example.clock.dateformatter"]/serviceprovider/@class
			
			// NOTE !!: we use the index in the xpath queries, this index orgininates from the EclipseRegistry
			// and we assume that the order of the extensions is the same as in plugin.xml!!
			
			StringBuffer query = this.createXPath(extension);
			query.append(ref);
			XPath xpath;
			try {
				xpath = XPath.newInstance(query.toString());
				List<Element> nodes = xpath.selectNodes(doc);
				if (nodes.size()==0) {
					Logger.info("No resource references found in plugin.xml for " + ref + " - check xpath");
					return null;
				}
				Object node = nodes.get(0);
				if (node instanceof Element) {
					return ((Element)node).getValue();
				}
				else if (node instanceof Attribute) {
					return ((Attribute)node).getValue();
				}
				else throw new ResourceLoaderException("Exception reading resource name from plugin.xml - check xpath reference");
			} catch (JDOMException e) {
				throw new ResourceLoaderException("Exception reading resource name from plugin.xml - check xpath reference",e);
			}
		}
		else if (context instanceof EclipseInstantiationContext) {
			EclipseInstantiationContext eContext = (EclipseInstantiationContext)context;
			XPath xpath;
			try {
				xpath = XPath.newInstance(ref);
				List<Element> nodes = xpath.selectNodes(eContext.getContextNode());
				if (nodes.size()==0) {
					Logger.info("No resource references found in plugin.xml for " + ref + " in context " + eContext.getContextNode() + " - check xpath");
					return null;
				}
				Object node = nodes.get(0);
				if (node instanceof Element) {
					return ((Element)node).getValue();
				}
				else if (node instanceof Attribute) {
					return ((Attribute)node).getValue();
				}
				else throw new ResourceLoaderException("Exception reading resource name from plugin.xml - check xpath reference");
			} catch (JDOMException e) {
				throw new ResourceLoaderException("Exception reading resource name from plugin.xml - check xpath reference",e);
			}			
		}
		else throw new ResourceLoaderException();
		
	}

	@Override
	public List<InstantiationContext> getInstantiationContexts(Connector connector, String contextDefinition) throws ResourceLoaderException {
		if (contextDefinition==null) {
			List<InstantiationContext> contexts = new ArrayList<InstantiationContext>();
			contexts.add(InstantiationContext.DEFAULT_CONTEXT);
			return contexts;
		}
		
		EclipseExtension extension = (EclipseExtension)connector;
		Document doc = this.loadPluginMetaData(connector);
		// prepend xpath expression to select plugin node
		// example (serviceprovider/@class is the actual resource reference):
		// /plugin/extension[@point="net.java.treaty.eclipse.example.clock.dateformatter"]/serviceprovider/@class
		StringBuffer query = this.createXPath(extension);
		query.append(contextDefinition);
		try {
			XPath xpath = XPath.newInstance(query.toString());
			List<Element> nodes = xpath.selectNodes(doc);
			List<InstantiationContext> contexts = new ArrayList<InstantiationContext>();
			for (Element node: nodes) {
				contexts.add(new EclipseInstantiationContext(node));
			}
			return contexts;
		} catch (JDOMException e) {
			throw new ResourceLoaderException("Exception reading resource name from plugin.xml - check xpath reference",e);
		}
	}
	// TODO cache
	private Document loadPluginMetaData(Connector connector) throws ResourceLoaderException {
		if (! (connector instanceof EclipseExtension)) {
			throw new ResourceLoaderException("This resource loader can only bind resources from EclipseExtensions");
		}
		EclipseExtension extension = (EclipseExtension)connector;
		EclipsePlugin component = (EclipsePlugin)extension.getOwner();
		Bundle bundle = component.getBundle();
		
		URL url = component.getResource("plugin.xml");
		
		SAXBuilder builder = new SAXBuilder();
		builder.setValidation(false);
		Document doc = null;
		try {
			return builder.build(url.openStream());
		} catch (Exception e) {
			throw new ResourceLoaderException("Cannot parse plugin.xml",e);
		} 
		
	}

	private StringBuffer createXPath(EclipseExtension extension) {
		StringBuffer query = new StringBuffer();
		query.append("/plugin/extension[@point=\"");
		query.append(extension.getExtensionPoint().getId());
		query.append("\"][");
		query.append(extension.getExtensionIndex());
		query.append("]/");
		return query;
	}
}
