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
import net.java.treaty.VerificationResult;
import net.java.treaty.eclipse.Constants;
import net.java.treaty.eclipse.Exporter;

/**
 * Default exporter.
 * @author Jens Dietrich
 */

public class CSVExporter extends Exporter {
	@Override
	public String getName() {
		return "export to CSV (spreadsheet)";
	}

	@Override
	public void export(Collection<Contract> contracts,File folder) throws IOException {
		try{
			int exceptionCounter = 1;
			
			// group contract instances by contract def
			Map<String,List<ContractData>> contractsByXP = new HashMap<String,List<ContractData>>(); 
			for (Contract c:contracts) {
				String xpId = c.getConsumer().getId();
				List<ContractData> instances = contractsByXP.get(xpId);
				if (instances==null) {
					instances = new ArrayList<ContractData>();
					contractsByXP.put(xpId, instances);
				}
				// collect and write exceptions
				List<String> exceptions = this.getExceptions(c);
				StringBuffer buf = new StringBuffer();
				for (String x:exceptions) {
					String fileName = "exception"+exceptionCounter+".txt";
					File file = new File(folder.getAbsolutePath()+'/'+fileName);
					exceptionCounter=exceptionCounter+1;
					Writer writer = new FileWriter(file);
					if (buf.length()>0) buf.append(" ");
					buf.append(fileName);
					writer.append(x);
					writer.close();
				}
				// gather data
				ContractData d = new ContractData();
				d.xp = xpId;
				d.x = c.getSupplier().getId();
				d.isAggregated = c instanceof AggregatedContract;
				String result = VerificationResult.UNKNOWN.toString();
				VerificationResult vr = (VerificationResult) c.getProperty(Constants.VERIFICATION_RESULT);
				if (vr!=null) {
					result = vr.toString();
				}
				d.result = result;
				d.exception=buf.toString();
				instances.add(d);
			}
			// load template
			VelocityEngine ve = new VelocityEngine();
			ve.setProperty("resource.loader","class");
			ve.setProperty("class.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			ve.init();
			Template template = ve.getTemplate("net/java/treaty/eclipse/defaultexporter/csv.vm");
			
			System.out.println("template loaded: "+template);
			
			
			// bind variables for each xp
			for (String xpId:contractsByXP.keySet()) {
				List<ContractData> instances = contractsByXP.get(xpId);				
				VelocityContext context = new VelocityContext();
				context.put("contracts",instances);
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
}