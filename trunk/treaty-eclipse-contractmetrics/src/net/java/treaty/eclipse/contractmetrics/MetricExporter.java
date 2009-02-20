/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.contractmetrics;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.java.treaty.Component;
import net.java.treaty.Connector;
import net.java.treaty.Contract;
import net.java.treaty.eclipse.Exporter;
import java.net.URI;

/**
 * Gather metrics and export them to a CSV file. 
 * @author Jens Dietrich
 */

public class MetricExporter extends Exporter {

	@Override
	public synchronized void export(Collection<Contract> contracts,File file) throws IOException {
		try{

			Collection<Contract> definitions = new HashSet<Contract>();
			
			for (Contract c:contracts) {
				Contract def = c.getDefinition();
				if (def==null) {
					System.out.println("no definition found for " + c);
				}
				else {
					definitions.add(def);
				}
			}
			// prepare - collect types and relationships
			MetricsTool tool = new MetricsTool();
			Set<URI> types = new HashSet<URI>();
			Set<URI> relationships = new HashSet<URI>();
			for (Contract c:definitions) {
				tool.collectUsedRelationships(c,relationships);
				tool.collectUsedTypes(c,types);
			}

			PrintStream out =new PrintStream(new FileOutputStream(file));
			// header
			out.print("extension point,plugin,contract depth,");
			out.print("exist cond.,property cond.,relationship cond.,");
			out.print("ORs,XORs,NOTs");
			// types
			for (URI t:types) {
				out.print(",type ");
				out.print(t);
			}
			for (URI r:relationships) {
				out.print(",rel ");
				out.print(r);
			}
			out.println();
			// body
			for (Contract c:definitions) {
				Connector xp = c.getConsumer();
				Component owner = xp==null?null:xp.getOwner();
				
				out.print(xp==null?"null":xp.getId());
				out.print(',');
				out.print(owner==null?"null":owner.getId());
				out.print(',');
				out.print(tool.getDepth(c));
				out.print(',');
				out.print(tool.getExistsConditionCount(c));
				out.print(',');
				out.print(tool.getPropertyConditionCount(c));
				out.print(',');
				out.print(tool.getRelationshipConditionCount(c));
				out.print(',');
				out.print(tool.getDisjunctionCount(c));
				out.print(',');
				out.print(tool.getXDisjunctionCount(c));
				out.print(',');
				out.print(tool.getNegationCount(c));				
				for (URI t:types) {
					out.print(',');
					out.print(tool.getTypeCount(c,t));
				}
				for (URI r:relationships) {
					out.print(',');
					out.print(tool.getRelationshipCount(c,r));
				}				
				out.println();
			}
			out.close();
			
		}
		catch (Exception x) {
			net.java.treaty.eclipse.Logger.error("Error exporting", x);
		}
	}

	@Override
	public boolean exportToFolder() {
		return false;
	}

	@Override
	public String[] getFilterExtensions() {
		return new String[]{"csv"};
	}

	@Override
	public String[] getFilterNames() {
		return new String[]{"comma separated values"};
	}

	@Override
	public String getName() {
		return "export contract metrics to CSV (spreadsheet)";
	}

}
