/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.exporter.html;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.core.runtime.IExtensionPoint;

import net.java.treaty.AbstractCondition;
import net.java.treaty.AbstractContractVisitor;
import net.java.treaty.Annotatable;
import net.java.treaty.Conjunction;
import net.java.treaty.Connector;
import net.java.treaty.Constraint;
import net.java.treaty.Contract;
import net.java.treaty.ContractVisitor;
import net.java.treaty.Disjunction;
import net.java.treaty.ExistsCondition;
import net.java.treaty.Negation;
import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.VerificationResult;
import net.java.treaty.XDisjunction;
import net.java.treaty.eclipse.Constants;
import net.java.treaty.eclipse.EclipseInstantiationContext;
import net.java.treaty.eclipse.Logger;
import net.java.treaty.eclipse.exporter.Exporter;

/**
 * <p>
 * Exports Treaty Eclipse verification results to HTML pages.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class HTMLExporter extends Exporter {

	/**
	 * <p>
	 * Represents the different styles that can be used to display HTML elements
	 * like tables.
	 * </p>
	 * 
	 * @author Jens Dietrich
	 */
	private enum Style {
		PLAIN, TREE, TREE_FAILED, TREE_SUCCESS, CONSUMER, SUPPLIER
	}

	/** The treaty top-level domain. */
	private static final String TREATY_NAMESPACE = "http://www.treaty.org/";

	/** Used to count the exceptions. */
	private int myExceptionCounter = 1;

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.eclipse.exporter.Exporter#export(java.util.Collection,
	 * java.io.File)
	 */
	public synchronized void export(Collection<Contract> contracts, File folder)
			throws IOException {

		/* Reset counter! */
		myExceptionCounter = 1;

		/* Group contract instances by contract definitions. */
		Map<String, List<Contract>> contractsByExtensionPoint =
				new TreeMap<String, List<Contract>>();
		// Map<String,List<String>> atomicConditions = new
		// HashMap<String,List<String>>();
		// Map<String,List<String>> variableNames = new
		// HashMap<String,List<String>>();

		/* Sort contracts. */
		for (Contract contract : contracts) {

			String extensionPointId;
			extensionPointId =
					contract.getConsumer() == null ? "anonymous_xp" : contract
							.getConsumer().getId();

			List<Contract> contractInstances;
			contractInstances = contractsByExtensionPoint.get(extensionPointId);

			if (contractInstances == null) {
				contractInstances = new ArrayList<Contract>();
				contractsByExtensionPoint.put(extensionPointId, contractInstances);
				// atomicConditions.put(xpId,this.getAtomicConditions(c));
				// variableNames.put(xpId,this.getVariables(c));
			}

			contractInstances.add(contract);
		}
		// end for (sort iteration on contracts).

		/* Create the index page. */
		this.createIndexPage(folder, contractsByExtensionPoint);

		/* Create pages for contracted extension points. */
		for (String extensionPoint : contractsByExtensionPoint.keySet()) {
			this.createResultsPage(folder, extensionPoint, contractsByExtensionPoint
					.get(extensionPoint));
		}
		// end for.

		/* Create the CSS file. */
		this.copyCSS(folder);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.eclipse.exporter.Exporter#exportToFolder()
	 */
	public boolean exportToFolder() {

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.eclipse.exporter.Exporter#getFilterExtensions()
	 */
	public String[] getFilterExtensions() {

		return new String[] {};
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.eclipse.exporter.Exporter#getFilterNames()
	 */
	public String[] getFilterNames() {

		return new String[] {};
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.eclipse.exporter.Exporter#getName()
	 */
	public String getName() {

		return "Export Verification Results to HTML";
	}

	/**
	 * <p>
	 * Copies the required style sheet file to a given folder.
	 * </p>
	 * 
	 * @param folder
	 *          The folder to that the style sheet file shall be copied.
	 * @throws IOException
	 *           Thrown, if the copy process fails.
	 */
	private void copyCSS(File folder) throws IOException {

		InputStream inputStream;
		inputStream = HTMLExporter.class.getResourceAsStream("/css/treaty.css");

		File cssFolder;
		cssFolder = new File(folder.getAbsoluteFile() + "/treaty.css");

		OutputStream ouputStream;
		ouputStream = new FileOutputStream(cssFolder);

		byte[] buffer;
		buffer = new byte[1024];

		int length;

		while ((length = inputStream.read(buffer)) > 0) {
			ouputStream.write(buffer, 0, length);
		}
		// end while.

		inputStream.close();
		ouputStream.close();
	}

	/**
	 * <p>
	 * Creates and returns a {@link String} representing a hyper link to an anchor
	 * for a given label and a given reference.
	 * </p>
	 * 
	 * @param label
	 *          The label of the created link.
	 * @param link
	 *          The reference of the created link.
	 * @return The created link as a {@link String}.
	 */
	private String createAnchorLink(String label, String link) {

		return new StringBuffer().append("<a href=\"#").append(link).append("\">")
				.append(label).append("</a>").toString();
	}

	/**
	 * <p>
	 * Creates an index page for an overview of all verified {@link Contract}s and
	 * {@link IExtensionPoint}s.
	 * </p>
	 * 
	 * @param folder
	 *          The folder into that all pages shall be exported.
	 * @param contractsByXP
	 *          The {@link Contract}s, mapped by their {@link IExtensionPoint}s
	 *          IDs.
	 * @throws IOException
	 *           Thrown, if the page creation fails.
	 */
	private void createIndexPage(File folder,
			Map<String, List<Contract>> contractsByXP) throws IOException {

		List<String> extensionPointIDs;

		extensionPointIDs = new ArrayList<String>();
		extensionPointIDs.addAll(contractsByXP.keySet());

		Collections.sort(extensionPointIDs);

		File indexPageFile;
		indexPageFile = new File(folder.getAbsolutePath() + "/index.html");

		PrintStream out;
		out = new PrintStream(new FileOutputStream(indexPageFile));

		this.printHtmlHeader(out, "Verification Results");
		this.printTableHeader(out, "Extension Point", "Contract Instances",
				"Verification failed");

		/* Iterate on the extension points. */
		for (String xp : extensionPointIDs) {

			List<Contract> contractInstances;
			contractInstances = contractsByXP.get(xp);

			int instanceCount;
			instanceCount = contractInstances.size();

			int failedCount = 0;
			for (Contract instance : contractInstances) {

				if (instance.getProperty(Constants.VERIFICATION_RESULT) == VerificationResult.FAILURE) {
					failedCount = failedCount + 1;
				}
				// no else.
			}
			// end for (iteration on contracts).

			this.printTableRow(out, createLink(xp, getFileName(xp)), instanceCount,
					failedCount);
		}
		// end for (iteration on extension point ids).

		this.printTableFooter(out);
		this.printHtmlFooter(out);

		out.close();
	}

	/**
	 * <p>
	 * Creates and returns a {@link String} representing a hyper link for a given
	 * label and a given reference.
	 * </p>
	 * 
	 * @param label
	 *          The label of the created link.
	 * @param link
	 *          The reference of the created link.
	 * @return The created link as a {@link String}.
	 */
	private String createLink(String label, String link) {

		return new StringBuffer().append("<a href=\"").append(link).append("\">")
				.append(label).append("</a>").toString();
	}

	/**
	 * <p>
	 * Creates a result page for a given extension point and a contract.
	 * </p>
	 * 
	 * @param out
	 *          The {@link PrintStream} used for output.
	 * @param folder
	 *          The folder into that the page shall be created.
	 * @param extensionPointID
	 *          The ID of the {@link IExtensionPoint}.
	 * @param contract
	 *          The {@link Contract} whose result page shall be created.
	 * @param noOfInstances
	 *          The number of instances the {@link Contract} has.
	 * @param instanceNo
	 *          The number of this {@link Contract} instance.
	 */
	private void createResultPage(PrintStream out, File folder,
			String extensionPointID, Contract contract, int noOfInstances,
			int instanceNo) {

		this.createResultPage(out, folder, extensionPointID, contract,
				noOfInstances, instanceNo, 0, 0);
	}

	/**
	 * <p>
	 * Creates a result page for a given extension point and a contract.
	 * </p>
	 * 
	 * @param out
	 *          The {@link PrintStream} used for output.
	 * @param folder
	 *          The folder into that the page shall be created.
	 * @param extensionPointID
	 *          The ID of the {@link IExtensionPoint}.
	 * @param contract
	 *          The {@link Contract} whose result page shall be created.
	 * @param noOfInstances
	 *          The number of instances the {@link Contract} has.
	 * @param instanceNo
	 *          The number of this {@link Contract} instance.
	 * @param noOfParts
	 *          The number of parts the {@link Contract} has.
	 * @param partNo
	 *          The number of the current part.
	 */
	private void createResultPage(PrintStream out, File folder,
			String extensionPointID, Contract contract, int noOfInstances,
			int instanceNo, int noOfParts, int partNo) {

		String instanceLabel;
		instanceLabel =
				"Contract instance " + (instanceNo + 1) + "/" + noOfInstances;

		String supplierLabel;
		supplierLabel = "supplier: " + getInstanceLabel(contract);

		String anchorName;
		anchorName = partNo == 0 ? ("instance" + (instanceNo + 1)) : null;

		/* Print a heading. */
		if (noOfParts == 0) {
			this
					.printSubHeader(out, instanceLabel + ", " + supplierLabel, anchorName);
		}

		else {
			String partLabel;
			partLabel = "part: " + (partNo + 1) + "/" + noOfParts;

			/* Print a heading (with or without anchor. */
			if (partNo == 0) {
				this.printSubHeader(out, instanceLabel + ", " + supplierLabel + ", "
						+ partLabel, anchorName);
			}

			else {
				this.printSubHeader(out, instanceLabel + ", " + supplierLabel + ", "
						+ partLabel);
			}
			// end else.
		}
		// end else.

		this.printSubSubHeader(out, "Resources");
		this.printTableHeader(out, "id", "type", "owner", "name", "reference");

		for (Resource resource : contract.getConsumerResources()) {
			this.printTableRow(out, Style.CONSUMER, resource.getId(), resource
					.getType().toString(), extensionPointID, resource.getName(), "n/a");
		}
		// end for (iteration on consumer resources).

		for (Resource resource : contract.getExternalResources()) {
			this.printTableRow(out, Style.CONSUMER, resource.getId(), resource
					.getType().toString(), this.getOwner(resource), resource.getName(),
					"n/a");
		}
		// end for (iteration on external resources).

		for (Resource resource : contract.getSupplierResources()) {

			String reference;
			reference = resource.getRef();

			if (resource.getContext() != null
					&& resource.getContext() instanceof EclipseInstantiationContext) {

				reference =
						""
								+ ((EclipseInstantiationContext) resource.getContext())
										.getPath() + "/" + reference;
			}
			// no else.

			this.printTableRow(out, Style.SUPPLIER, resource.getId(), resource
					.getType().toString(), getOwner(resource), resource.getName(),
					reference);
		}
		// end for (iteration on supplier resources).

		this.printTableFooter(out);

		VerificationResult verificationResult;
		verificationResult =
				(VerificationResult) contract
						.getProperty(Constants.VERIFICATION_RESULT);

		String verificationResultAsString;
		verificationResultAsString =
				verificationResult == null ? "unknown" : verificationResult.toString();

		this.printSubSubHeader(out, "Verification Result: "
				+ verificationResultAsString);

		this.printContractTree(out, contract, folder);
	}

	/**
	 * <p>
	 * Creates a results page for a given extension point and a given {@link List}
	 * of {@link Contract}s.
	 * </p>
	 * 
	 * @param folder
	 *          The folder into that the results page shall be created.
	 * @param extensionPointID
	 *          The ID of the {@link IExtensionPoint} for that the result page
	 *          shall be created.
	 * @param contracts
	 *          The {@link Contract}s that shall be contained in the results page.
	 * @throws FileNotFoundException
	 *           Thrown, if the given folder does not exists.
	 */
	private void createResultsPage(File folder, String extensionPointID,
			List<Contract> contracts) throws FileNotFoundException {

		File resultPageFile;
		resultPageFile =
				new File(folder.getAbsolutePath() + "/" + getFileName(extensionPointID));

		PrintStream out;
		out = new PrintStream(new FileOutputStream(resultPageFile));

		this.printHtmlHeader(out, "Verification results for Extension Point "
				+ extensionPointID);

		/* Display Overview. */
		this.printSubHeader(out, "Overview");
		this.printTableHeader(out, "extension", "result");

		for (int index = 0; index < contracts.size(); index++) {

			Contract contract;
			contract = contracts.get(index);

			String instanceLabel;
			instanceLabel = getInstanceLabel(contract);

			String verificationResult;
			verificationResult =
					"" + contract.getProperty(Constants.VERIFICATION_RESULT);

			this.printTableRow(out, createAnchorLink(instanceLabel, "instance"
					+ (index + 1)), verificationResult);
		}
		// end for (iteration on contracts).

		this.printTableFooter(out);

		/* Display details. */
		for (int index = 0; index < contracts.size(); index++) {

			Contract contract;
			contract = contracts.get(index);

			this.createResultPage(out, folder, extensionPointID, contract, contracts
					.size(), index);
		}
		// end for (iteration on contracts).

		this.printHtmlFooter(out);

		out.close();
	}

	/**
	 * <p>
	 * Returns the name of the created filed for a given extension point's ID.
	 * </p>
	 * 
	 * @param extensionPointID
	 *          The ID of the {@link IExtensionPoint}.
	 * @return The file name of the {@link File} containing the results for the
	 *         given {@link IExtensionPoint}.
	 */
	private String getFileName(String extensionPointID) {

		return extensionPointID + ".html";
	}

	/**
	 * <p>
	 * Returns the label for a given {@link Contract} instance.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} whose label shall be returned.
	 * @return The label of a given {@link Contract}.
	 */
	private String getInstanceLabel(Contract contract) {

		String result;

		Connector connector;
		connector = contract.getSupplier();

		if (connector == null) {
			result = "?";
		}

		else {
			result = connector.getOwner().getId();
		}
		// end else.

		return result;
	}

	/**
	 * <p>
	 * Returns the owner of a given {@link Resource}.
	 * </p>
	 * 
	 * @param resource
	 *          The {@link Resource} whose owner shall be returned.
	 * @return The Owner (an {@link Object}).
	 */
	private Object getOwner(Resource resource) {

		Object result;
		result = null;

		Connector connector;
		connector = resource.getOwner();

		if (connector != null) {
			result = connector.getOwner().getId();
		}
		// no else.

		return result;
	}

	/**
	 * <p>
	 * Converts a given {@link Resource} into a {@link String} representing the
	 * {@link Resource}.
	 * </p>
	 * 
	 * @param resource
	 *          The {@link Resource} that shall be converted.
	 * @return A {@link String} representing the {@link Resource}.
	 */
	private String getResourceAsString(Resource resource) {

		StringBuffer buffer;
		buffer = new StringBuffer();

		if (resource.getRef() != null) {
			buffer.append('?');
			buffer.append(resource.getRef());
		}

		else if (resource.getName() != null) {
			buffer.append(resource.getName());
		}
		// no else.

		return buffer.toString();
	}

	/**
	 * <p>
	 * Returns the name of the given {@link Resource} or a default value, if the
	 * name of the {@link Resource} is not set.
	 * </p>
	 * 
	 * @param resource
	 *          The {@link Resource} whose name shall be returned,
	 * @return The name or a default value, if the name of the {@link Resource} is
	 *         not set.
	 */
	private String getResourceName(Resource resource) {

		if (resource.getRef() != null && resource.getName() != null) {
			return resource.getName();
		}

		else {
			return "?";
		}
		// end else.
	}

	/**
	 * <p>
	 * Returns the short name of a given vocabulary elements type (e.g. a Type or
	 * a Relationship) by probably removing the top-level domain from the given
	 * URI.
	 * </p>
	 * 
	 * @param uri
	 *          The URI (as a {@link String}) that shall be shortened.
	 * @return A probably shorter form of the given URI (as a {@link String}).
	 */
	private String getShortNameOfVocabularyElement(String uri) {

		if (uri.startsWith(TREATY_NAMESPACE)) {
			return uri.substring(TREATY_NAMESPACE.length());
		}

		else {
			return uri;
		}
		// end else.
	}

	/**
	 * <p>
	 * Used to print the tree for a given {@link Contract}.
	 * </p>
	 * 
	 * @param outputStream
	 *          The {@link PrintStream} used to print the tree.
	 * @param contract
	 *          The {@link Contract} whose tree shall be printed.
	 * @param folder
	 *          The directory into which the stream shall be printed.
	 */
	private void printContractTree(final PrintStream outputStream,
			final Contract contract, final File folder) {

		/* Create the visitor used to print the tree. */
		ContractVisitor contractVisitor;
		contractVisitor = new ContractVisitor() {

			/*
			 * (non-Javadoc)
			 * @see
			 * net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Conjunction)
			 */
			public void endVisit(Conjunction condition) {

				/* Print an end node for the conjunction. */
				printEndTreeNode(outputStream, condition);
			}

			/*
			 * (non-Javadoc)
			 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Contract)
			 */
			public void endVisit(Contract contract) {

				/*
				 * Probably print an aggregation end node for all the contained
				 * constraints.
				 */
				if (this.hasMultipleConstraints(contract)) {
					printEndTreeNode(outputStream, contract);
				}
				// no else.
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Disjunction)
			 */
			public void endVisit(Disjunction condition) {

				/* Print an end node for the disjunction. */
				printEndTreeNode(outputStream, condition);
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * net.java.treaty.ContractVisitor#endVisit(net.java.treaty.ExistsCondition
			 * )
			 */
			public void endVisit(ExistsCondition condition) {

				// out.println("*** ending condition "+condition);
				printTableFooter(outputStream);
			}

			/*
			 * (non-Javadoc)
			 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Negation)
			 */
			public void endVisit(Negation condition) {

				/* Print an end node for the negation. */
				printEndTreeNode(outputStream, condition);
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * net.java.treaty.ContractVisitor#endVisit(net.java.treaty.PropertyCondition
			 * )
			 */
			public void endVisit(PropertyCondition condition) {

				// out.println("*** ending condition "+condition);
				printTableFooter(outputStream);
			}

			/*
			 * (non-Javadoc)
			 * @seenet.java.treaty.ContractVisitor#endVisit(net.java.treaty.
			 * RelationshipCondition)
			 */
			public void endVisit(RelationshipCondition relationshipCondition) {

				// out.println("*** ending condition "+relationshipCondition);
				printTableFooter(outputStream);
			}

			/*
			 * (non-Javadoc)
			 * @see net.java.treaty.ContractVisitor#endVisit(net.java.treaty.Resource)
			 */
			public void endVisit(Resource resource) {

				/* Do nothing to print end of resources. */
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * net.java.treaty.ContractVisitor#endVisit(net.java.treaty.XDisjunction)
			 */
			public void endVisit(XDisjunction condition) {

				/* Print an end node for the xdisjunction. */
				printEndTreeNode(outputStream, condition);
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * net.java.treaty.ContractVisitor#endVisitConditions(java.util.Collection
			 * )
			 */
			public void endVisitConditions(Collection<AbstractCondition> conditions) {

				/* Do nothing. */
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * net.java.treaty.ContractVisitor#endVisitConsumerResources(java.util
			 * .Collection)
			 */
			public void endVisitConsumerResources(Collection<Resource> resources) {

				/* TODO Probably do something here? */
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * net.java.treaty.ContractVisitor#endVisitExternalResources(java.util
			 * .Collection)
			 */
			public void endVisitExternalResources(Collection<Resource> resources) {

				/* Do nothing. */
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * net.java.treaty.ContractVisitor#endVisitOnFailureAction(java.net.URI)
			 */
			public void endVisitOnFailureAction(URI uri) {

				/* TODO Probably do something here? */
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * net.java.treaty.ContractVisitor#endVisitOnSuccessAction(java.net.URI)
			 */
			public void endVisitOnSuccessAction(URI uri) {

				/* TODO Probably do something here? */
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * net.java.treaty.ContractVisitor#endVisitSupplierResources(java.util
			 * .Collection)
			 */
			public void endVisitSupplierResources(Collection<Resource> resources) {

				/* TODO Probably do something here? */
			}

			/*
			 * (non-Javadoc)
			 * @see net.java.treaty.ContractVisitor#endVisitTrigger(java.net.URI)
			 */
			public void endVisitTrigger(URI uri) {

				/* TODO Probably do something here? */
			}

			/*
			 * (non-Javadoc)
			 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Conjunction)
			 */
			public boolean visit(Conjunction condition) {

				/* Print an and node for the conjunction. */
				printStartTreeNode(outputStream, getStyle(condition), "and", condition);

				/* Visit children. */
				return true;
			}

			/*
			 * (non-Javadoc)
			 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Contract)
			 */
			public boolean visit(Contract contract) {

				/* Probably print an aggregation node for all the contained constraints. */
				if (this.hasMultipleConstraints(contract)) {
					printStartTreeNode(outputStream, getStyle(contract), "and", contract);
				}
				// no else.

				/* Visit children. */
				return true;
			}

			/*
			 * (non-Javadoc)
			 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Disjunction)
			 */
			public boolean visit(Disjunction condition) {

				/* Print an or node for the disjunction. */
				printStartTreeNode(outputStream, getStyle(condition), "or", condition);

				/* Visit children. */
				return true;
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * net.java.treaty.ContractVisitor#visit(net.java.treaty.ExistsCondition)
			 */
			public boolean visit(ExistsCondition e) {

				printTableHeader(outputStream, getStyle(e));

				String errorReference;
				errorReference = this.createErrorRef(e);

				if (errorReference == null) {
					printTableRow(outputStream, e.getResource().getId() + " must exist");
				}

				else {
					printTableRow(outputStream, e.getResource().getId() + " must exist",
							errorReference);
				}
				// end else.

				/* Don't visit children. */
				return false;
			}

			/*
			 * (non-Javadoc)
			 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Negation)
			 */
			public boolean visit(Negation condition) {

				/* Print a not node for the negation. */
				printStartTreeNode(outputStream, getStyle(condition), "not", condition);

				/* Visit children. */
				return true;
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * net.java.treaty.ContractVisitor#visit(net.java.treaty.PropertyCondition
			 * )
			 */
			public boolean visit(PropertyCondition propertyCondition) {

				printTableHeader(outputStream, getStyle(propertyCondition));

				/* Create a reference to the error page. */
				String errorRef;
				errorRef = this.createErrorRef(propertyCondition);

				/* Get the property of the condition. */
				/*
				 * FIXME Claas: Probably this must be changed. Can PropertyConditions
				 * have multiple properties?
				 */
				String property;
				property = propertyCondition.getPropertyNames().next();

				if (property == null) {
					property = "";
				}

				else {
					property = "." + property;
				}

				/* Display the condition or the condition and the error. */
				if (errorRef == null) {
					printTableRow(outputStream, propertyCondition.getResource().getId(),
							property, propertyCondition.getOperator().toString(),
							propertyCondition.getValue());
				}
				else {
					printTableRow(outputStream, propertyCondition.getResource().getId(),
							property, propertyCondition.getOperator(), propertyCondition
									.getValue(), errorRef);
				}

				/* Don't visit children. */
				return false;
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * net.java.treaty.ContractVisitor#visit(net.java.treaty.RelationshipCondition
			 * )
			 */
			public boolean visit(RelationshipCondition relationshipCondition) {

				/* Probably create a reference to an error page. */
				String errorRef;
				errorRef = createErrorRef(relationshipCondition);

				printTableHeader(outputStream, getStyle(relationshipCondition));

				/* Display the condition or the condition and the error. */
				if (errorRef == null) {
					printTableRow(outputStream, relationshipCondition.getResource1()
							.getId(), relationshipCondition.getRelationship().toString(),
							relationshipCondition.getResource2().getId());
				}

				else {
					printTableRow(outputStream, relationshipCondition.getResource1()
							.getId(), relationshipCondition.getRelationship().toString(),
							relationshipCondition.getResource2().getId(), errorRef);
				}

				/* Don't visit children. */
				return false;
			}

			/*
			 * (non-Javadoc)
			 * @see net.java.treaty.ContractVisitor#visit(net.java.treaty.Resource)
			 */
			public boolean visit(Resource resource) {

				/* Don't visit children. */
				return false;
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * net.java.treaty.ContractVisitor#visit(net.java.treaty.XDisjunction)
			 */
			public boolean visit(XDisjunction condition) {

				/* Print an xor node for the xdisjunction. */
				printStartTreeNode(outputStream, getStyle(condition), "xor", condition);

				/* Visit children. */
				return true;
			}

			/*
			 * (non-Javadoc)
			 * @see net.java.treaty.ContractVisitor#visitConditions(java.util.List)
			 */
			public boolean visitConditions(List<AbstractCondition> name) {

				/* Visit children. */
				return true;
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * net.java.treaty.ContractVisitor#visitConsumerResources(java.util.Collection
			 * )
			 */
			public boolean visitConsumerResources(Collection<Resource> resources) {

				/* TODO Probably do something here? */
				return false;
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * net.java.treaty.ContractVisitor#visitExternalResources(java.util.Collection
			 * )
			 */
			public boolean visitExternalResources(Collection<Resource> resources) {

				/* Don't visit children. */
				return false;
			}

			/*
			 * (non-Javadoc)
			 * @see net.java.treaty.ContractVisitor#visitOnFailureAction(java.net.URI)
			 */
			public boolean visitOnFailureAction(URI uri) {

				/* TODO Probably do something here? */
				return false;
			}

			/*
			 * (non-Javadoc)
			 * @see net.java.treaty.ContractVisitor#visitOnSuccessAction(java.net.URI)
			 */
			public boolean visitOnSuccessAction(URI uri) {

				/* TODO Probably do something here? */
				return false;
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * net.java.treaty.ContractVisitor#visitSupplierResources(java.util.Collection
			 * )
			 */
			public boolean visitSupplierResources(Collection<Resource> resources) {

				/* TODO Probably do something here? */
				return false;
			}

			/*
			 * (non-Javadoc)
			 * @see net.java.treaty.ContractVisitor#visitTrigger(java.net.URI)
			 */
			public boolean visitTrigger(URI uri) {

				/* TODO Probably do something here? */
				return false;
			}

			/**
			 * <p>
			 * Creates the reference to the verification exception of a given
			 * Constraint.
			 * </p>
			 * 
			 * @param constraint
			 *          The Constraint for that the error reference shall be created.
			 * @return The reference to the verification exception of a given
			 *         Constraint.
			 */
			private String createErrorRef(Constraint constraint) {

				String result;

				Exception verificationException;
				verificationException =
						(Exception) constraint
								.getProperty(Constants.VERIFICATION_EXCEPTION);

				if (verificationException != null) {

					String detailsPageName;
					detailsPageName = "_exception" + myExceptionCounter + ".html";

					myExceptionCounter = myExceptionCounter + 1;

					File errorPageFile;
					errorPageFile =
							new File(folder.getAbsolutePath() + "/" + detailsPageName);

					PrintStream out;
					out = null;

					/* Try to create the error page. */
					try {
						out = new PrintStream(new FileOutputStream(errorPageFile));
						printHtmlHeader(out, "Exception details");
						printException(out, verificationException);
						printHtmlFooter(out);

						/* Link the page. */
						StringBuffer buffer;

						buffer = new StringBuffer();
						buffer.append("<a href=\"");
						buffer.append(detailsPageName);
						buffer.append("\" target=\"_top\">");
						buffer.append("exception details");
						buffer.append("</a>");

						result = buffer.toString();
					}
					// end try.

					catch (FileNotFoundException e) {
						Logger.error(
								"Error during creating an VerificationException page.", e);

						result = null;
					}
					// end catch.

					finally {
						out.close();
					}
					// end finaly.
				}

				/* No exception set. return null. */
				else {
					result = null;
				}
				// end else.

				return result;
			}

			/**
			 * <p>
			 * Returns the used CSS style for a given Annotatable (Depends on its
			 * verification result).
			 * </p>
			 * 
			 * @param annotatable
			 *          The Annotatable for that the Style shall be returned.
			 * @return The Style for the Annotatable.
			 */
			private Style getStyle(Annotatable annotatable) {

				Style result;

				VerificationResult verificationResult;
				verificationResult =
						(VerificationResult) annotatable
								.getProperty(Constants.VERIFICATION_RESULT);

				if (verificationResult == null) {
					result = Style.TREE;
				}

				else {

					switch (verificationResult) {

					case SUCCESS:
						result = Style.TREE_SUCCESS;
						break;

					case FAILURE:
						result = Style.TREE_FAILED;
						break;

					default:
						result = Style.TREE;
					}
					// end switch.
				}
				// end else.

				return result;
			}

			/**
			 * <p>
			 * A helper method used to check if a given {@link Contract} has multiple
			 * constraints.
			 * </p>
			 * 
			 * @param contract
			 *          The {@link Contract} that shall be checked.
			 * @return <code>true</code> if the given {@link Contract} has at least
			 *         two constraints.
			 */
			private boolean hasMultipleConstraints(Contract contract) {

				return contract.getConstraints().size() > 1;
			}

		};
		contract.accept(contractVisitor);
	}

	/**
	 * <p>
	 * Prints the end of a tree node to a given {@link PrintStream}.
	 * </p>
	 * 
	 * @param out
	 *          The {@link PrintStream} used for output.
	 * @param node
	 *          The node to be printed.
	 * 
	 * @see HTMLExporter#printStartTreeNode(PrintStream, Style, String, Object)
	 */
	private void printEndTreeNode(PrintStream out, Object node) {

		out.println("</td></tr>");
		out.println("</table>");
		// debug
		// out.println("--- end " + node + " ---");
	}

	/**
	 * <p>
	 * Prints a given {@link Exception} to a given {@link PrintStream}.
	 * </p>
	 * 
	 * @param out
	 *          The {@link PrintStream} used for output.
	 * @param exception
	 *          The {@link Exception} that shall be printed.
	 */
	private void printException(PrintStream out, Exception exception) {

		exception.printStackTrace(out);
	}

	/**
	 * <p>
	 * Prints an HTML footer for a given {@link PrintStream}.
	 * </p>
	 * 
	 * @param out
	 *          The {@link PrintStream} used for output.
	 */
	private void printHtmlFooter(PrintStream out) {

		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * <p>
	 * Prints an HTML header for a given {@link PrintStream} and a given title.
	 * </p>
	 * 
	 * @param out
	 *          The {@link PrintStream} used for output.
	 * @param title
	 *          The title of the HTML page.
	 */
	private void printHtmlHeader(PrintStream out, String title) {

		out.println("<html>");
		out.println("<head>");
		out
				.println("<link rel=\"StyleSheet\" href=\"treaty.css\" TYPE=\"text/css\" MEDIA=\"screen\">");
		out.println("</head>");
		out.println("<body>");
		out.print("<h1>");
		out.print(title);
		out.println("</h1>");
	}

	/**
	 * <p>
	 * Prints the begin of a tree node to a given {@link PrintStream}.
	 * </p>
	 * 
	 * @param out
	 *          The {@link PrintStream} used for output.
	 * @param style
	 *          The {@link Style} used.
	 * @param label
	 *          The label of the node.
	 * @param node
	 *          The node to be printed.
	 */
	private void printStartTreeNode(PrintStream out, Style style, String label,
			Object node) {

		// debug
		// out.println("--- start " + node + " ---");

		out.print("<table  cellspacing=\"0\" width=\"100%\" class=\"");
		out.print(style);
		out.println("\">");
		out.print("<tr><td>");
		out.print(label);
		out.println("</td><td>");
	}

	/**
	 * <p>
	 * Prints a sub heading for a given string.
	 * </p>
	 * 
	 * @param out
	 *          The {@link PrintStream} used for output.
	 * @param string
	 *          The string that shall be headed.
	 */
	private void printSubHeader(PrintStream out, String string) {

		this.printSubHeader(out, string, null);
	}

	/**
	 * <p>
	 * Prints a sub heading for a given string and probably an anchor for a given
	 * anchor name.
	 * </p>
	 * 
	 * @param out
	 *          The {@link PrintStream} used for output.
	 * @param string
	 *          The string that shall be headed.
	 * @param anchorName
	 *          The name of the anchor belonging to the heading (can be
	 *          <code>null</code>).
	 */
	private void printSubHeader(PrintStream out, String string, String anchorName) {

		out.print("<h2>");

		if (anchorName != null) {
			out.print("<a name=\"");
			out.print(anchorName);
			out.print("\">");
		}
		// no else.

		out.print(string);

		if (anchorName != null) {
			out.print("</a>");
		}
		// no else.

		out.println("</h2>");
	}

	/**
	 * <p>
	 * Prints a sub sub heading for a given string.
	 * </p>
	 * 
	 * @param out
	 *          The {@link PrintStream} used for output.
	 * @param string
	 *          The string that shall be headed.
	 */
	private void printSubSubHeader(PrintStream out, String string) {

		out.print("<h3>");
		out.print(string);
		out.println("</h3>");
	}

	/**
	 * <p>
	 * Prints the footer of a table for a given {@link PrintStream}.
	 * </p>
	 * 
	 * @param out
	 *          The {@link PrintStream} used for output.
	 */
	private void printTableFooter(PrintStream out) {

		out.println("</table>");
	}

	/**
	 * <p>
	 * Prints the header of a table for a given {@link PrintStream} and a given
	 * array of labels.
	 * </p>
	 * 
	 * @param out
	 *          The {@link PrintStream} used for output.
	 * @param labels
	 *          The labels of the table header.
	 */
	private void printTableHeader(PrintStream out, String... labels) {

		this.printTableHeader(out, Style.PLAIN, labels);
	}

	/**
	 * <p>
	 * Prints the header of a table for a given {@link PrintStream} and a given
	 * array of labels.
	 * </p>
	 * 
	 * @param out
	 *          The {@link PrintStream} used for output.
	 * @param style
	 *          The {@link Style} of the printed table.
	 * @param labels
	 *          The labels of the table header.
	 */
	private void printTableHeader(PrintStream out, Style style, String... labels) {

		out.print("<table cellspacing=\"0\" class=\"");
		out.print(style);
		out.println("\">");

		if (labels.length > 0) {
			out.print("<tr>");

			for (String l : labels) {
				out.print("<th>");
				out.print(l);
				out.print("</th>");
			}
			// end for.

			out.println("</tr>");
		}
		// no else.
	}

	/**
	 * <p>
	 * Prints the header of a table row for a given {@link PrintStream} and a
	 * given array of entries.
	 * </p>
	 * 
	 * @param out
	 *          The {@link PrintStream} used for output.
	 * @param entries
	 *          The entries of the table row.
	 */
	private void printTableRow(PrintStream out, Object... entries) {

		this.printTableRow(out, Style.PLAIN, entries);
	}

	/**
	 * <p>
	 * Prints the header of a table row for a given {@link PrintStream} and a
	 * given array of entries.
	 * </p>
	 * 
	 * @param out
	 *          The {@link PrintStream} used for output.
	 * @param style
	 *          The {@link Style} of the printed table.
	 * @param entries
	 *          The entries of the table row.
	 */
	private void printTableRow(PrintStream out, Style style, Object... entries) {

		out.print("<tr");

		if (style == null) {
			out.print(">");
		}

		else {
			out.print(" class=\"");
			out.print(style);
			out.print("\">");
		}

		for (Object l : entries) {
			out.print("<td>");
			out.print(l);
			out.print("</td>");
		}
		// end for.

		out.println("</tr>");
	}

	/**
	 * TODO Claas: This method is not used. It should be removed probably.
	 * 
	 * <p>
	 * Returns a {@link List} containing the atomic conditions of a given
	 * {@link Contract} as {@link String}s.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} whose atomic conditions shall be returned.
	 * @return A {@link List} containing the atomic conditions of a given
	 *         {@link Contract} as {@link String}s.
	 */
	private List<String> getAtomicConditions(Contract contract) {

		final List<String> result;
		result = new ArrayList<String>();

		/* Use a visitor to create the result. */
		ContractVisitor contractVisitor;
		contractVisitor = new AbstractContractVisitor() {

			/*
			 * (non-Javadoc)
			 * @seenet.java.treaty.AbstractContractVisitor#visit(net.java.treaty.
			 * ExistsCondition)
			 */
			public boolean visit(ExistsCondition c) {

				result.add("must exist: " + getResourceAsString(c.getResource()));

				return true;
			}

			/*
			 * (non-Javadoc)
			 * @seenet.java.treaty.AbstractContractVisitor#visit(net.java.treaty.
			 * PropertyCondition)
			 */
			public boolean visit(PropertyCondition propertyCondition) {

				StringBuffer buffer;
				buffer = new StringBuffer();

				buffer.append(getResourceAsString(propertyCondition.getResource()));
				buffer.append('.');
				/*
				 * FIXME Claas: probably this is wrong? Can PropertyConditions have
				 * multiple properties?
				 */
				buffer.append(propertyCondition.getProperty(propertyCondition
						.getPropertyNames().next()));
				buffer.append(' ');
				buffer.append(propertyCondition.getOperator());
				buffer.append(propertyCondition.getValue());

				result.add(buffer.toString());

				return true;
			}

			/*
			 * (non-Javadoc)
			 * @seenet.java.treaty.AbstractContractVisitor#visit(net.java.treaty.
			 * RelationshipCondition)
			 */
			public boolean visit(RelationshipCondition c) {

				StringBuffer buffer;
				buffer = new StringBuffer();
				buffer.append(getResourceAsString(c.getResource1()));
				buffer.append(' ');
				buffer.append(getShortNameOfVocabularyElement(c.getRelationship()
						.toString()));
				buffer.append(' ');
				buffer.append(getResourceAsString(c.getResource2()));

				result.add(buffer.toString());

				return true;
			}
		};

		contract.accept(contractVisitor);

		return result;
	}

	/*
	 * TODO Claas: This method is not used. It should be removed probably.
	 */
	private List<String> getExceptions(Contract contract) {

		final List<String> exceptions = new ArrayList<String>();
		ContractVisitor visitor = new AbstractContractVisitor() {

			@Override
			public boolean visit(ExistsCondition c) {

				doVisit(c);
				return true;
			}

			@Override
			public boolean visit(PropertyCondition c) {

				doVisit(c);
				return true;
			}

			@Override
			public boolean visit(RelationshipCondition c) {

				doVisit(c);
				return true;
			}

			private void doVisit(AbstractCondition c) {

				Exception x =
						(Exception) c.getProperty(Constants.VERIFICATION_EXCEPTION);
				if (x != null) {
					StringWriter writer = new StringWriter();
					x.printStackTrace(new PrintWriter(writer));
					exceptions.add(writer.toString());
				}
			}
		};
		contract.accept(visitor);
		return exceptions;
	}

	/*
	 * TODO Claas: This method is not used. It should be removed probably.
	 */
	private List<String> getResults(Contract contract, final File folder) {

		final List<String> list = new ArrayList<String>();
		ContractVisitor visitor = new AbstractContractVisitor() {

			@Override
			public boolean visit(ExistsCondition c) {

				doVisit(c);
				return true;
			}

			@Override
			public boolean visit(PropertyCondition c) {

				doVisit(c);
				return true;
			}

			@Override
			public boolean visit(RelationshipCondition c) {

				doVisit(c);
				return true;
			}

			private void doVisit(AbstractCondition c) {

				list.add(getResult(c));
			}

			private String getResult(AbstractCondition c) {

				VerificationResult r =
						(VerificationResult) c.getProperty(Constants.VERIFICATION_RESULT);
				Exception x =
						(Exception) c.getProperty(Constants.VERIFICATION_EXCEPTION);
				if (x != null) {
					String fileName = "exception" + myExceptionCounter + ".txt";
					File file = new File(folder.getAbsolutePath() + '/' + fileName);
					myExceptionCounter = myExceptionCounter + 1;
					Writer writer;
					try {
						writer = new FileWriter(file);
						x.printStackTrace(new PrintWriter(writer));
						writer.close();
					} catch (IOException e) {
						return "exceptions (cannot write details)";
					}

					return fileName;
				}
				else {
					return r == null ? "null" : r.toString();
				}
			}
		};
		contract.accept(visitor);
		return list;
	}

	/*
	 * TODO Claas: This method is not used. It should be removed probably.
	 */
	private List<String> getVariableBindings(Contract contract) {

		final List<String> list = new ArrayList<String>();
		final Set<String> vars = new HashSet<String>();
		ContractVisitor visitor = new AbstractContractVisitor() {

			@Override
			public boolean visit(ExistsCondition c) {

				add(c.getResource());
				return true;
			}

			@Override
			public boolean visit(PropertyCondition c) {

				add(c.getResource());
				return true;
			}

			@Override
			public boolean visit(RelationshipCondition c) {

				add(c.getResource1());
				add(c.getResource2());
				return true;
			}

			private void add(Resource r) {

				if (r.getRef() != null && !vars.contains(r.getId())) {
					list.add(getResourceName(r));
					vars.add(r.getId());
				}
			}
		};
		contract.accept(visitor);
		return list;
	}

	/*
	 * TODO Claas: This method is not used. It should be removed probably.
	 */
	private List<String> getVariables(Contract contract) {

		final List<String> list = new ArrayList<String>();
		final Set<String> vars = new HashSet<String>();
		ContractVisitor visitor = new AbstractContractVisitor() {

			@Override
			public boolean visit(ExistsCondition c) {

				add(c.getResource());
				return true;
			}

			@Override
			public boolean visit(PropertyCondition c) {

				add(c.getResource());
				return true;
			}

			@Override
			public boolean visit(RelationshipCondition c) {

				add(c.getResource1());
				add(c.getResource2());
				return true;
			}

			private void add(Resource r) {

				if (r.getRef() != null && !vars.contains(r.getId())) {
					list.add("variable " + getResourceAsString(r));
					vars.add(r.getId());
				}
			}
		};
		contract.accept(visitor);
		return list;
	}
}