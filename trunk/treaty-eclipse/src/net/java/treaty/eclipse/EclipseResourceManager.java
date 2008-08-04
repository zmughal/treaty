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
import java.util.List;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.osgi.framework.Bundle;
import net.java.treaty.ResourceManager;
import net.java.treaty.ResourceLoaderException;
/**
 * Utility to extract resources.
 * @author Jens Dietrich
 */

public class EclipseResourceManager implements ResourceManager {

	@Override
	public String resolve(URI type, String ref,net.java.treaty.Connector connector)	throws ResourceLoaderException {
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
			doc = builder.build(url.openStream());
		} catch (Exception e) {
			throw new ResourceLoaderException("Cannot parse plugin.xml",e);
		} 
		// prepend xpath expression to select plugin node
		// example (serviceprovider/@class is the actual resource reference):
		// /plugin/extension[@point="net.java.treaty.eclipse.example.clock.dateformatter"]/serviceprovider/@class
		StringBuffer query = new StringBuffer();
		query.append("/plugin/extension[@point=\"");
		query.append(extension.getExtensionPoint().getId());
		query.append("\"]/");
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

}
