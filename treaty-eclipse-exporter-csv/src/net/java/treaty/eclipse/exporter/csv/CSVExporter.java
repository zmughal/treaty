/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.exporter.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.java.treaty.AbstractCondition;
import net.java.treaty.AbstractContractVisitor;
import net.java.treaty.Contract;
import net.java.treaty.ContractVisitor;
import net.java.treaty.ExistsCondition;
import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.VerificationResult;
import net.java.treaty.eclipse.Constants;
import net.java.treaty.eclipse.Logger;
import net.java.treaty.eclipse.exporter.Exporter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * <p>
 * Exports results into spreadsheets.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class CSVExporter extends Exporter {

	/** The treaty top-level domain. */
	private static final String TREATY_NAMESPACE = "http://www.treaty.org/";

	/** The location of the velocity template used for export. */
	private static final String VELOCITY_TEMPLATE_LOCATION =
			"net/java/treaty/eclipse/exporter/csv/csv.vm";

	private int exceptionCounter = 1;

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.eclipse.exporter.Exporter#export(java.util.Collection,
	 * java.io.File)
	 */
	public synchronized void export(Collection<Contract> contracts, File folder)
			throws IOException {

		try {
			/* reset counter! */
			exceptionCounter = 1;

			Map<String, List<ContractData>> contractsByExtensionPoint;
			contractsByExtensionPoint = new HashMap<String, List<ContractData>>();

			Map<String, List<String>> atomicConditions;
			atomicConditions = new HashMap<String, List<String>>();

			Map<String, List<String>> variableNames;
			variableNames = new HashMap<String, List<String>>();

			/* Group contract instances by contract definition. */
			for (Contract contract : contracts) {

				String extensionPointId;
				extensionPointId =
						contract.getConsumer() == null ? "anonymous_xp" : contract
								.getConsumer().getId();

				List<ContractData> contractInstances;
				contractInstances = contractsByExtensionPoint.get(extensionPointId);

				if (contractInstances == null) {

					contractInstances = new ArrayList<ContractData>();
					contractsByExtensionPoint.put(extensionPointId, contractInstances);
					atomicConditions.put(extensionPointId, this
							.getAtomicConditions(contract));
					variableNames.put(extensionPointId, this.getVariables(contract));
				}
				// no else.

				Contract[] parts;
				parts = new Contract[] { contract };

				/* Gather data and build contract data. */
				for (int index = 0; index < parts.length; index++) {

					Contract part;
					part = parts[index];

					ContractData contractData;
					contractData = new ContractData();

					contractData.extensionPointName = extensionPointId;
					contractData.pluginId = contract.getSupplier().getOwner().getId();
					contractData.extensionPointId =
							contract.getSupplier().getId() == null ? "anonymous" : contract
									.getSupplier().getId();

					String result;
					result = VerificationResult.UNKNOWN.toString();

					VerificationResult verificationResult;
					verificationResult =
							(VerificationResult) part
									.getProperty(Constants.VERIFICATION_RESULT);

					if (verificationResult != null) {
						result = verificationResult.toString();
					}
					// no else.

					contractData.verificationResult = result;
					contractData.conditionResults = this.getResults(part, folder);
					contractData.bindings = this.getVariableBindings(part);
					contractData.partNo = index + 1;

					contractInstances.add(contractData);
				}
				// end for (iteration on contract parts).
			}
			// end for (iteration on contracts).

			/* Load template. */
			VelocityEngine velocityEngine;

			velocityEngine = new VelocityEngine();
			velocityEngine.setProperty("resource.loader", "class");
			velocityEngine
					.setProperty("class.resource.loader.class",
							"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			velocityEngine.init();

			Template template;
			template = velocityEngine.getTemplate(VELOCITY_TEMPLATE_LOCATION);

			/* Bind variables for each extension point. */
			for (String extensionPointId : contractsByExtensionPoint.keySet()) {
				List<ContractData> contractInstances;
				contractInstances = contractsByExtensionPoint.get(extensionPointId);

				VelocityContext velocityContext;
				velocityContext = new VelocityContext();

				velocityContext.put("contracts", contractInstances);
				velocityContext.put("conditions", atomicConditions
						.get(extensionPointId));
				velocityContext.put("variables", variableNames.get(extensionPointId));

				File file;
				file =
						new File(folder.getAbsolutePath() + '/' + extensionPointId + ".csv");

				Writer writer;
				writer = new FileWriter(file);

				template.merge(velocityContext, writer);

				writer.close();
			}
			// end for (iteration on extension point IDs).
		}
		// end try.

		catch (Exception e) {
			String msg;
			msg = "Error during exporting Contracts to CSV.";

			net.java.treaty.eclipse.Logger.error(msg, e);

			throw new IOException(msg, e);
		}
		// end catch.
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

		return "Export verification results to CSV (spreadsheet)";
	}

	/**
	 * <p>
	 * Collects all atomic Conditions of a given {@link Contract}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} whose atomic conditions shall be returned.
	 * @return The atomive conditions as a {@link List} of {@link String}s.
	 */
	private List<String> getAtomicConditions(Contract contract) {

		final List<String> result;
		result = new ArrayList<String>();

		ContractVisitor visitor;
		visitor = new AbstractContractVisitor() {

			/*
			 * (non-Javadoc)
			 * @seenet.java.treaty.AbstractContractVisitor#visit(net.java.treaty.
			 * ExistsCondition)
			 */
			public boolean visit(ExistsCondition c) {

				result.add("must exist: " + getResourceAsString(c.getResource()));

				/* Visit children. */
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
				 * FIXME Claas: is this okay or can property conditions have multiple
				 * properties?
				 */
				buffer.append(propertyCondition.getProperty(propertyCondition
						.getPropertyNames().next()));
				buffer.append(' ');
				buffer.append(propertyCondition.getOperator());
				buffer.append(propertyCondition.getValue());

				result.add(buffer.toString());

				/* Visit children. */
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

				/* Visit children. */
				return true;
			}
		};

		contract.accept(visitor);

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
	 * Creates result files for the {@link AbstractCondition}s of a given
	 * {@link Contract} and returns a {@link List} containing the file names.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} whose {@link AbstractCondition}s shall be
	 *          visited.
	 * @param folder
	 *          The folder into that the results shall be saved.
	 * @return A {@link List} containing the file names.
	 */
	private List<String> getResults(Contract contract, final File folder) {

		final List<String> list = new ArrayList<String>();
		ContractVisitor visitor = new AbstractContractVisitor() {

			/*
			 * (non-Javadoc)
			 * @seenet.java.treaty.AbstractContractVisitor#visit(net.java.treaty.
			 * ExistsCondition)
			 */
			public boolean visit(ExistsCondition c) {

				this.doVisit(c);

				/* Visit children. */
				return true;
			}

			/*
			 * (non-Javadoc)
			 * @seenet.java.treaty.AbstractContractVisitor#visit(net.java.treaty.
			 * PropertyCondition)
			 */
			public boolean visit(PropertyCondition c) {

				this.doVisit(c);

				/* Visit children. */
				return true;
			}

			/*
			 * (non-Javadoc)
			 * @seenet.java.treaty.AbstractContractVisitor#visit(net.java.treaty.
			 * RelationshipCondition)
			 */
			public boolean visit(RelationshipCondition c) {

				this.doVisit(c);

				/* Visit children. */
				return true;
			}

			/**
			 * <p>
			 * Visits an AbstractCondition.
			 * </p>
			 * 
			 * @param condition
			 *          The AbstractCondition that shall be visited.
			 */
			private void doVisit(AbstractCondition condition) {

				list.add(this.getResult(condition));
			}

			/**
			 * <p>
			 * Create a result file for the AbstractCondition and returns the name of
			 * this file.
			 * </p>
			 * 
			 * @param condition
			 *          The AbstractCondition that shall be visited.
			 * @return The name of the file containing the result.
			 */
			private String getResult(AbstractCondition condition) {

				VerificationResult verificationResult;
				verificationResult =
						(VerificationResult) condition
								.getProperty(Constants.VERIFICATION_RESULT);

				Exception exception =
						(Exception) condition.getProperty(Constants.VERIFICATION_EXCEPTION);

				if (exception != null) {

					String fileName;
					fileName = "exception" + exceptionCounter + ".txt";

					File file;
					file = new File(folder.getAbsolutePath() + '/' + fileName);

					exceptionCounter = exceptionCounter + 1;

					Writer writer;

					try {
						writer = new FileWriter(file);
						exception.printStackTrace(new PrintWriter(writer));
						writer.close();
					}
					// end try.

					catch (IOException e) {
						Logger.error("Error during creation of result file.", e);

						return "exceptions (cannot write details)";
					}
					// end catch.

					return fileName;
				}

				else {
					return verificationResult == null ? "null" : verificationResult
							.toString();
				}
				// end else.
			}
		};

		contract.accept(visitor);

		return list;
	}

	/**
	 * <p>
	 * Collects all variable bindings for a given {@link Contract}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} whose variable bindings shall be returned.
	 * @return The bound variables as a {@link List} of {@link String}s.
	 */
	private List<String> getVariableBindings(Contract contract) {

		final List<String> result;
		result = new ArrayList<String>();

		final Set<String> variables;
		variables = new HashSet<String>();

		ContractVisitor visitor;
		visitor = new AbstractContractVisitor() {

			/*
			 * (non-Javadoc)
			 * @seenet.java.treaty.AbstractContractVisitor#visit(net.java.treaty.
			 * ExistsCondition)
			 */
			public boolean visit(ExistsCondition c) {

				this.add(c.getResource());

				/* Visit children. */
				return true;
			}

			/*
			 * (non-Javadoc)
			 * @seenet.java.treaty.AbstractContractVisitor#visit(net.java.treaty.
			 * PropertyCondition)
			 */
			public boolean visit(PropertyCondition c) {

				this.add(c.getResource());

				/* Visit children. */
				return true;
			}

			/*
			 * (non-Javadoc)
			 * @seenet.java.treaty.AbstractContractVisitor#visit(net.java.treaty.
			 * RelationshipCondition)
			 */
			public boolean visit(RelationshipCondition c) {

				this.add(c.getResource1());
				this.add(c.getResource2());

				/* Visit children. */
				return true;
			}

			/**
			 * <p>
			 * Probably adds a given Resource to the result.
			 * </p>
			 * 
			 * @param resource
			 *          The Resource that shall be added.
			 */
			private void add(Resource resource) {

				/* Avoid duplicates and only add bound variables. */
				if (resource.getRef() != null && !variables.contains(resource.getId())) {
					result.add(getResourceName(resource));
					variables.add(resource.getId());
				}
				// no else.
			}
		};

		contract.accept(visitor);

		return result;
	}

	/**
	 * <p>
	 * Collects all variables of a given {@link Contract}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} whose variables shall be collected.
	 * @return The variables as a {@link List} of {@link String}s.
	 */
	private List<String> getVariables(Contract contract) {

		final List<String> result;
		result = new ArrayList<String>();

		final Set<String> variables;
		variables = new HashSet<String>();

		ContractVisitor visitor;
		visitor = new AbstractContractVisitor() {

			/*
			 * (non-Javadoc)
			 * @seenet.java.treaty.AbstractContractVisitor#visit(net.java.treaty.
			 * ExistsCondition)
			 */
			public boolean visit(ExistsCondition c) {

				this.add(c.getResource());

				/* Visit children. */
				return true;
			}

			/*
			 * (non-Javadoc)
			 * @seenet.java.treaty.AbstractContractVisitor#visit(net.java.treaty.
			 * PropertyCondition)
			 */
			public boolean visit(PropertyCondition c) {

				this.add(c.getResource());

				/* Visit children. */
				return true;
			}

			/*
			 * (non-Javadoc)
			 * @seenet.java.treaty.AbstractContractVisitor#visit(net.java.treaty.
			 * RelationshipCondition)
			 */
			public boolean visit(RelationshipCondition c) {

				this.add(c.getResource1());
				this.add(c.getResource2());

				/* Visit children. */
				return true;
			}

			/**
			 * <p>
			 * Adds a given Resource to the result.
			 * </p>
			 * 
			 * @param resource
			 *          The Resource that shall be added.
			 */
			private void add(Resource resource) {

				/* Avoid duplicates. */
				if (resource.getRef() != null && !variables.contains(resource.getId())) {

					result.add("variable " + getResourceAsString(resource));
					variables.add(resource.getId());
				}
				// no else.
			}
		};

		contract.accept(visitor);

		return result;
	}
}