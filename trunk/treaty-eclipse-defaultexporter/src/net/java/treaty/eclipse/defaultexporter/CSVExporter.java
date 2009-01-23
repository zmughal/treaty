/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.defaultexporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import net.java.treaty.AbstractCondition;
import net.java.treaty.AbstractContractVisitor;
import net.java.treaty.AggregatedContract;
import net.java.treaty.Contract;
import net.java.treaty.ContractVisitor;
import net.java.treaty.ExistsCondition;
import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.VerificationResult;
import net.java.treaty.eclipse.Constants;
import net.java.treaty.eclipse.Exporter;

/**
 * Exports results into spreadsheets.
 * @author Jens Dietrich
 */

public class CSVExporter extends Exporter {
	
	private int exceptionCounter = 1; 
	
	@Override
	public String getName() {
		return "export to CSV (spreadsheet)";
	}

	@Override
	public synchronized void export(Collection<Contract> contracts,File folder) throws IOException {
		try{
			exceptionCounter=1; // reset counter!
			
			// group contract instances by contract def
			Map<String,List<ContractData>> contractsByXP = new HashMap<String,List<ContractData>>(); 
			Map<String,List<String>> atomicConditions = new HashMap<String,List<String>>();
			Map<String,List<String>> variableNames = new HashMap<String,List<String>>();
			
			for (Contract c:contracts) {
				String xpId = c.getConsumer()==null?"anonymous_xp":c.getConsumer().getId();
				List<ContractData> instances = contractsByXP.get(xpId);
				if (instances==null) {
					instances = new ArrayList<ContractData>();
					contractsByXP.put(xpId, instances);
					atomicConditions.put(xpId,this.getAtomicConditions(c));
					variableNames.put(xpId,this.getVariables(c));
				}

				// gather data
				ContractData d = new ContractData();
				d.xp = xpId;
				d.pluginId = c.getSupplier().getOwner().getId();
				d.extensionPointId = c.getSupplier().getId()==null?"anonymous":c.getSupplier().getId();
				d.isAggregated = c instanceof AggregatedContract;
				String result = VerificationResult.UNKNOWN.toString();
				VerificationResult vr = (VerificationResult) c.getProperty(Constants.VERIFICATION_RESULT);
				if (vr!=null) {
					result = vr.toString();
				}
				d.result = result;
				d.conditionResults=this.getResults(c,folder);
				d.bindings=this.getVariableBindings(c);
				instances.add(d);
			}
			// load template
			VelocityEngine ve = new VelocityEngine();
			ve.setProperty("resource.loader","class");
			ve.setProperty("class.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			ve.init();
			Template template = ve.getTemplate("net/java/treaty/eclipse/defaultexporter/csv.vm");
			
			// bind variables for each xp
			for (String xpId:contractsByXP.keySet()) {
				List<ContractData> instances = contractsByXP.get(xpId);				
				VelocityContext context = new VelocityContext();
				context.put("contracts",instances);
				context.put("conditions",atomicConditions.get(xpId));
				context.put("variables",variableNames.get(xpId));
				File file = new File(folder.getAbsolutePath()+'/'+xpId+".csv");
				Writer writer = new FileWriter(file);
				template.merge( context, writer );
				writer.close();
			}
			
		}
		catch (Exception x) {
			x.printStackTrace();
		}
	}
	
	public boolean exportToFolder(){
		return true;
	}
	public String[] getFilterExtensions() {
		return new String[]{};
	}
	public String[] getFilterNames(){
		return new String[]{};
	}
	
	private List<String> getExceptions(Contract contract) {
		final List<String> exceptions = new ArrayList<String> ();
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
				Exception x = (Exception) c.getProperty(Constants.VERIFICATION_EXCEPTION);
				if (x!=null) {
					StringWriter writer = new StringWriter();
					x.printStackTrace(new PrintWriter(writer));
					exceptions.add(writer.toString());
				}
			}
		};
		contract.accept(visitor);
		return exceptions;
	} 
	private List<String> getAtomicConditions(Contract contract) {
		final List<String> list = new ArrayList<String> ();
		ContractVisitor visitor = new AbstractContractVisitor() {
			@Override
			public boolean visit(ExistsCondition c) {
				list.add("must exist: "+print(c.getResource()));
				return true;
			}
			@Override
			public boolean visit(PropertyCondition c) {
				StringBuffer b = new StringBuffer();
				b.append(print(c.getResource()));
				b.append('.');
				b.append(c.getProperty());
				b.append(' ');
				b.append(c.getOperator());
				b.append(c.getValue());
				list.add(b.toString());
				return true;
			}
			@Override
			public boolean visit(RelationshipCondition c) {
				StringBuffer b = new StringBuffer();
				b.append(print(c.getResource1()));
				b.append(' ');
				b.append(printShort(c.getRelationship().toString()));
				b.append(' ');
				b.append(print(c.getResource2()));
				list.add(b.toString());
				return true;
			}
		};
		contract.accept(visitor);
		return list;
	} 
	
	private List<String> getVariables(Contract contract) {
		final List<String> list = new ArrayList<String> ();
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
				if (r.getRef()!=null && !vars.contains(r.getId())) {
					list.add("variable "+print(r));
					vars.add(r.getId());
				}
			}
		};
		contract.accept(visitor);
		return list;
	} 
	
	private List<String> getVariableBindings(Contract contract) {
		final List<String> list = new ArrayList<String> ();
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
				if (r.getRef()!=null && !vars.contains(r.getId())) {
					list.add(print2(r));
					vars.add(r.getId());
				}
			}
		};
		contract.accept(visitor);
		return list;
	} 
	
	private List<String> getResults(Contract contract,final File folder) {
		final List<String> list = new ArrayList<String> ();
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
				VerificationResult r = (VerificationResult) c.getProperty(Constants.VERIFICATION_RESULT);
				Exception x = (Exception) c.getProperty(Constants.VERIFICATION_EXCEPTION);
				if (x!=null) {
					String fileName = "exception"+exceptionCounter+".txt";
					File file = new File(folder.getAbsolutePath()+'/'+fileName);
					exceptionCounter=exceptionCounter+1;
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
					return r==null?"null":r.toString();
				}
			}
		};
		contract.accept(visitor);
		return list;
	} 
	
	private String printShort(String s) {
		String NAMESPACE = "http://www.treaty.org/";
		if (s.startsWith(NAMESPACE)) return s.substring(NAMESPACE.length());
		else return s;
	}
	private String print(Resource r) {
		StringBuffer b = new StringBuffer();
		if (r.getRef()!=null) {
			b.append('?');
			b.append(r.getRef());
		}
		else if (r.getName()!=null) {
			b.append(r.getName());
		}
		/*
		b.append('[');
		b.append(printShort(r.getType().toString()));
		b.append(']');
		*/
		return b.toString();
	}
	private String print2(Resource r) {
		if (r.getRef()!=null && r.getName()!=null) {
			return r.getName();
		}
		else {
			return "?";
		}
	}

	
}