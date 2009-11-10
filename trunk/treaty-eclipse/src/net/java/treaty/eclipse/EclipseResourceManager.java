/*
 * Copyright (C) 2009 Jens Dietrich
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

import net.java.treaty.Connector;
import net.java.treaty.InstantiationContext;
import net.java.treaty.ResourceLoaderException;
import net.java.treaty.ResourceManager;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

/**
 * <p>
 * Utility to extract resources.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class EclipseResourceManager implements ResourceManager {

	/** The singleton instance of the {@link EclipseResourceManager}. */
	public static EclipseResourceManager INSTANCE = new EclipseResourceManager();

	/**
	 * <p>
	 * Private constructor for singleton pattern.
	 * </p>
	 */
	private EclipseResourceManager() {

		/* Remains empty. */
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ResourceManager#resolve(java.net.URI,
	 * java.lang.String, net.java.treaty.Connector,
	 * net.java.treaty.InstantiationContext)
	 */
	@SuppressWarnings("unchecked")
	public String resolve(URI type, String reference, Connector connector,
			InstantiationContext context) throws ResourceLoaderException {

		EclipseExtension eclipseExtension;
		Document document;

		eclipseExtension = (EclipseExtension) connector;
		document = this.loadPluginMetaData(connector);

		/* Probably use the default context. */
		if (context == null || context == InstantiationContext.DEFAULT_CONTEXT) {
			/* Prepend xpath expression to select plug-in node. */
			/* Example (serviceprovider/@class is the actual resource reference): */
			/*
			 * /plugin/extension[@point="net.java.treaty.eclipse.example.clock.dateformatter"
			 * ]/serviceprovider/@class
			 */

			/*
			 * NOTE!!: we use the index in the xpath queries, this index orgininates
			 * from the EclipseRegistry and we assume that the order of the extensions
			 * is the same as in plugin.xml!!
			 */

			/* Create the query for the given extension and reference. */
			StringBuffer query;
			query = this.createXPath(eclipseExtension);
			query.append(reference);

			try {
				XPath xpath;
				xpath = XPath.newInstance(query.toString());

				List<Element> nodes;
				nodes = xpath.selectNodes(document);

				if (nodes.size() == 0) {
					return null;
				}

				Object node = nodes.get(0);

				if (node instanceof Element) {
					return ((Element) node).getValue();
				}

				else if (node instanceof Attribute) {
					return ((Attribute) node).getValue();
				}

				else {
					throw new ResourceLoaderException(
							"Exception reading resource name from plugin.xml - "
									+ "check xpath reference");
				}
			}
			// end try.

			catch (JDOMException e) {
				throw new ResourceLoaderException(
						"Exception reading resource name from plugin.xml - "
								+ "check xpath reference", e);
			}
			// end catch.
		}

		/* Else use another context. */
		else if (context instanceof EclipseInstantiationContext) {

			EclipseInstantiationContext eclipseContext;
			eclipseContext = (EclipseInstantiationContext) context;

			try {
				XPath xpath;
				xpath = XPath.newInstance(reference);

				List<Element> nodes =
						xpath.selectNodes(eclipseContext.getContextNode());

				if (nodes.size() == 0) {
					return null;
				}

				Object node;
				node = nodes.get(0);

				if (node instanceof Element) {
					return ((Element) node).getValue();
				}

				else if (node instanceof Attribute) {
					return ((Attribute) node).getValue();
				}

				else {
					throw new ResourceLoaderException(
							"Exception reading resource name from plugin.xml - "
									+ "check xpath reference");
				}
			}
			// end try

			catch (JDOMException e) {
				throw new ResourceLoaderException(
						"Exception reading resource name from plugin.xml - "
								+ "check xpath reference", e);
			}
			// end catch.
		}

		/* Else throw an exception. */
		else {
			throw new ResourceLoaderException(
					"Unknown kind of InstantiationContext: " + context);
		}
		// end else.
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.ResourceManager#getInstantiationContexts(net.java.treaty
	 * .Connector, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<InstantiationContext> getInstantiationContexts(
			Connector connector, String contextDefinition)
			throws ResourceLoaderException {

		if (contextDefinition == null || !(connector instanceof EclipseExtension)) {

			List<InstantiationContext> contexts;

			contexts = new ArrayList<InstantiationContext>();
			contexts.add(InstantiationContext.DEFAULT_CONTEXT);

			return contexts;
		}

		EclipseExtension eclipseExtension;
		eclipseExtension = (EclipseExtension) connector;

		Document document = this.loadPluginMetaData(connector);

		/* Prepend xpath expression to select plugin node. */
		/* Example (serviceprovider/@class is the actual resource reference): */
		/*
		 * /plugin/extension[@point="net.java.treaty.eclipse.example.clock.dateformatter"
		 * ]/serviceprovider/@class
		 */

		StringBuffer query;

		query = this.createXPath(eclipseExtension);
		query.append(contextDefinition);

		try {

			XPath xpath;
			xpath = XPath.newInstance(query.toString());

			List<Element> nodes;
			nodes = xpath.selectNodes(document);

			List<InstantiationContext> contexts;
			contexts = new ArrayList<InstantiationContext>();

			int counter = 1;

			for (Element node : nodes) {
				String fullXpath;
				fullXpath = query.toString() + '[' + counter + ']';

				contexts.add(new EclipseInstantiationContext(node, fullXpath));
				counter = counter + 1;
			}

			return contexts;
		}
		// end try.

		catch (JDOMException e) {
			throw new ResourceLoaderException(
					"Exception reading resource name from plugin.xml - "
							+ "check xpath reference", e);
		}
		// end catch.
	}

	/**
	 * <p>
	 * A helper method that returns the xpath expression for a given
	 * {@link EclipseExtension}.
	 * </p>
	 * 
	 * @param extension
	 *          The {@link EclipseExtension} for that the xpath shall be returned.
	 * @return The xpath as a {@link StringBuffer}.
	 */
	private StringBuffer createXPath(EclipseExtension extension) {

		StringBuffer result = new StringBuffer();

		result.append("/plugin/extension[@point=\"");
		result.append(extension.getExtensionPoint().getId());
		result.append("\"][");
		result.append(extension.getExtensionIndex());
		result.append("]/");

		return result;
	}

	/**
	 * <p>
	 * A helper method that loads the meta data from a {@link EclipsePlugin}.
	 * </p>
	 * 
	 * @param connector
	 *          The {@link Connector} of those {@link EclipsePlugin} the meta data
	 *          shall be loaded.
	 * @return The loaded meta data as a {@link Document}.
	 * @throws ResourceLoaderException
	 *           Thrown, if the given {@link Connector} is not an
	 *           {@link EclipseConnector}.
	 */
	// TODO Cache the results here.
	private Document loadPluginMetaData(Connector connector)
			throws ResourceLoaderException {

		if (!(connector instanceof EclipseExtension)) {
			throw new ResourceLoaderException(
					"This resource loader can only bind resources from EclipseExtensions");
		}
		// no else.

		EclipseExtension eclipseExtension;
		eclipseExtension = (EclipseExtension) connector;

		EclipsePlugin eclipsePlugin;
		eclipsePlugin = (EclipsePlugin) eclipseExtension.getOwner();

		URL url;
		url = eclipsePlugin.getResource("plugin.xml");

		SAXBuilder builder;

		builder = new SAXBuilder();
		builder.setValidation(false);

		/* Try to open the document. */
		try {
			return builder.build(url.openStream());
		}

		catch (Exception e) {
			throw new ResourceLoaderException("Cannot parse plugin.xml", e);
		}
	}
}