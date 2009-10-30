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
import java.util.*;
import net.java.treaty.*;
import net.java.treaty.eclipse.Constants;
import net.java.treaty.eclipse.EclipseInstantiationContext;
import net.java.treaty.eclipse.Exporter;

/**
 * Exports results to html pages.
 * @author Jens Dietrich
 */

public class HTMLExporter extends Exporter {
	
	private int exceptionCounter = 1; 
	
	enum Style {
		PLAIN,TREE,TREE_FAILED,TREE_SUCCESS,CONSUMER,SUPPLIER
	}
	
	@Override
	public String getName() {
		return "export verification results to HTML";
	}

	@Override
	public synchronized void export(Collection<Contract> contracts,File folder) throws IOException {
		try{
			exceptionCounter=1; // reset counter!
			
			// group contract instances by contract def
			Map<String,List<Contract>> contractsByXP = new TreeMap<String,List<Contract>>(); 
			//Map<String,List<String>> atomicConditions = new HashMap<String,List<String>>();
			//Map<String,List<String>> variableNames = new HashMap<String,List<String>>();
			
			// sort contracts
			for (Contract c:contracts) {
				String xpId = c.getConsumer()==null?"anonymous_xp":c.getConsumer().getId();
				List<Contract> instances = contractsByXP.get(xpId);
				if (instances==null) {
					instances = new ArrayList<Contract>();
					contractsByXP.put(xpId, instances);
					//atomicConditions.put(xpId,this.getAtomicConditions(c));
					//variableNames.put(xpId,this.getVariables(c));
				}
				instances.add(c);
			}
			// index
			createIndexPage(folder,contractsByXP);
			
			// pages
			for (String xp:contractsByXP.keySet()) {
				createResultsPage(folder,xp,contractsByXP.get(xp));
			}
			
			// style
			copyCSS(folder);
			
		}
		catch (Exception x) {
			net.java.treaty.eclipse.Logger.error("Error exporting", x);
		}
	}
	
	private void copyCSS(File folder) throws IOException {
		  InputStream in = HTMLExporter.class.getResourceAsStream("/css/treaty.css");
	      File f2 = new File(folder.getAbsoluteFile()+"/treaty.css");
	      OutputStream out = new FileOutputStream(f2);
	      byte[] buf = new byte[1024];
	      int len;
	      while ((len = in.read(buf)) > 0){
	        out.write(buf, 0, len);
	      }
	      in.close();
	      out.close();		
	}

	private void createResultsPage(File folder, String xp, List<Contract> contracts) throws FileNotFoundException {
		File file = new File(folder.getAbsolutePath()+"/"+getFileName(xp));
		PrintStream out = new PrintStream(new FileOutputStream(file));
		printHtmlHeader(out,"Verification results for extension point " +xp);
		
		// overview
		printSubHeader(out,"Overview");
		printTableHeader(out,"extension","result");
		for (int i=0;i<contracts.size();i++) {
			Contract c = contracts.get(i);
			String x = getInstanceLabel(c);
			String result = ""+c.getProperty(Constants.VERIFICATION_RESULT);
			printTableRow(out,createiLink(x,"instance"+(i+1)),result);
		}
		printTableFooter(out);
		
		// details
		for (int i=0;i<contracts.size();i++) {
			Contract c = contracts.get(i);
			createResultPage(out,folder,xp,c,contracts.size(),i);
		}
		printHtmlFooter(out);
		out.close();		
	}
	private void createResultPage(PrintStream out,File folder,String xp,Contract c, int noOfInstances,int instanceNo) {
		createResultPage(out, folder, xp, c, noOfInstances, instanceNo, 0,0);
	}
	private void createResultPage(PrintStream out,File folder,String xp,Contract c, int noOfInstances,int instanceNo, int noOfParts, int partNo) {
		String instLabel = "Contract instance "+(instanceNo+1)+"/"+noOfInstances;
		String supplLabel = "supplier: " +getInstanceLabel(c);
		String anch = partNo==0?("instance"+(instanceNo+1)):null;
		if (noOfParts==0) {
			printSubHeader(out,instLabel+", "+supplLabel,anch);
		}
		else {
			String partLabel = "part: "+(partNo+1)+"/"+noOfParts;
			if (partNo==0)
				printSubHeader(out,instLabel+", "+supplLabel+", "+partLabel,anch);	
			else
				printSubHeader(out,instLabel+", "+supplLabel+", "+partLabel);	
		}
		printSubSubHeader(out,"Resources");	
		printTableHeader(out,"id","type","owner","name","reference");

		for (Resource r:c.getConsumerResources()) {
			printTableRow(out,Style.CONSUMER,r.getId(),r.getType().toString(),xp,r.getName(),"n/a");
		}

		for (Resource r:c.getExternalResources()) {
			printTableRow(out,Style.CONSUMER,r.getId(),r.getType().toString(),this.getOwner(r),r.getName(),"n/a");
		}
	
		for (Resource r:c.getSupplierResources()) {
			String ref = r.getRef();
			if (r.getContext()!=null && r.getContext() instanceof EclipseInstantiationContext) {
				ref= ""+((EclipseInstantiationContext)r.getContext()).getPath()+"/"+ref;
			}
			printTableRow(out,Style.SUPPLIER,r.getId(),r.getType().toString(),getOwner(r),r.getName(),ref);
		}
		printTableFooter(out);
		
		VerificationResult result = (VerificationResult)c.getProperty(Constants.VERIFICATION_RESULT);
		String r = result==null?"unknown":result.toString();
		printSubSubHeader(out,"Verification Result: " + r);
		
		printContractTree(out,c,folder);
	}

	private void printContractTree(final PrintStream out, final Contract c, final File folder) {
		ContractVisitor visitor = new ContractVisitor() {

			private Style getStyle(Annotatable a) {
				VerificationResult result = (VerificationResult)a.getProperty(Constants.VERIFICATION_RESULT);
				if (result==null) return Style.TREE;
				else if (result==VerificationResult.SUCCESS) return Style.TREE_SUCCESS;
				else if (result==VerificationResult.FAILURE) return Style.TREE_FAILED;
				else return Style.TREE; 
			}
			@Override
			public void endVisit(Contract contract) {
				
				if (generateNode(contract)) {
					printEndTreeNode(out,contract);
				}
			}

			private boolean generateNode(Contract contract) {
				return contract.getConstraints().size()>1;
			}
			@Override
			public void endVisit(Resource resource) {}

			@Override
			public void endVisit(Conjunction condition) {
				printEndTreeNode(out,condition);
			}

			@Override
			public void endVisit(Disjunction condition) {
				printEndTreeNode(out,condition);
			}

			@Override
			public void endVisit(XDisjunction condition) {
				printEndTreeNode(out,condition);
			}

			@Override
			public void endVisit(Negation condition) {
				printEndTreeNode(out,condition);
			}

			@Override
			public void endVisit(RelationshipCondition relationshipCondition) {
				//out.println("*** ending condition "+relationshipCondition);
				printTableFooter(out);
			}

			@Override
			public void endVisit(PropertyCondition condition) {
				//out.println("*** ending condition "+condition);
				printTableFooter(out);
			}

			@Override
			public void endVisit(ExistsCondition condition) {
				//out.println("*** ending condition "+condition);
				printTableFooter(out);
			}

			@Override
			public void endVisitConditions(Collection<AbstractCondition> conditions) {}

			@Override
			public void endVisitExtensionPointResources(Collection<Resource> resources) {}

			@Override
			public void endVisitExtensionResources(Collection<Resource> resources) {}

			@Override
			public void endVisitExternalResources(Collection<Resource> resources) {}

			@Override
			public boolean visit(Contract contract) {
				if (generateNode(contract)) {
					printStartTreeNode(out,getStyle(contract),"and",contract);
				}
				return true;
			}

			@Override
			public boolean visit(Resource resource) {
				return false;
			}

			@Override
			public boolean visit(Conjunction condition) {
				printStartTreeNode(out,getStyle(condition),"and",condition);
				return true;
			}

			@Override
			public boolean visit(Disjunction condition) {
				printStartTreeNode(out,getStyle(condition),"or",condition);
				return true;
			}

			@Override
			public boolean visit(XDisjunction condition) {
				printStartTreeNode(out,getStyle(condition),"xor",condition);
				return true;
			}

			@Override
			public boolean visit(Negation condition) {
				printStartTreeNode(out,getStyle(condition),"not",condition);
				return true;
			}

			@Override
			public boolean visit(RelationshipCondition r) {
				String errorRef = createErrorRef(r);
				printTableHeader(out,getStyle(r));
				if (errorRef==null) {
					printTableRow(out,
							r.getResource1().getId(),
							r.getRelationship().toString(),
							r.getResource2().getId()
						);
				}
				else {
					printTableRow(out,
							r.getResource1().getId(),
							r.getRelationship().toString(),
							r.getResource2().getId(),
							errorRef
						);
				}
				return false;
			}

			private String createErrorRef(Constraint c) {
				Exception err = (Exception)c.getProperty(Constants.VERIFICATION_EXCEPTION);
				if (err!=null) {
					String details = "_exception"+exceptionCounter+".html";
					exceptionCounter = exceptionCounter+1;
					File file = new File(folder.getAbsolutePath()+"/"+details);
					PrintStream out = null;
					try {
						out = new PrintStream(new FileOutputStream(file));
						printHtmlHeader(out,"Exception details");
						printException(out,err);
						printHtmlFooter(out);
						// link
						return new StringBuffer()
							.append("<a href=\"")
							.append(details)
							.append("\" target=\"_top\">")
							.append("exception details")
							.append("</a>")
							.toString();
					} catch (FileNotFoundException e) {
						// TODO
						e.printStackTrace();
					}
					finally {
						out.close();
					}
					
					
				}
				return null;
			}
			@Override
			public boolean visit(PropertyCondition p) {
				printTableHeader(out,getStyle(p));
				String errorRef = createErrorRef(p);
				String property = p.getProperty();
				property = property==null?"":"."+property;
				if (errorRef==null) {
					printTableRow(out,
							p.getResource().getId(),
							property,
							p.getOperator().getName(),
							p.getValue()
						);
				}
				else {
					printTableRow(out,
							p.getResource().getId(),
							property,
							p.getOperator(),
							p.getValue(),
							errorRef
						);					
				}
				return false;
			}

			@Override
			public boolean visit(ExistsCondition e) {
				printTableHeader(out,getStyle(e));
				String errorRef = createErrorRef(e);
				if (errorRef==null) {
					printTableRow(out,
							e.getResource().getId()+" must exist"
						);
				}
				else {
					printTableRow(out,
							e.getResource().getId()+" must exist",
							errorRef
						);					
				}
				return false;
			}

			@Override
			public boolean visitConditions(List<AbstractCondition> name) {
				return true;
			}

			@Override
			public boolean visitExtensionPointResources(Collection<Resource> resources) {
				return false;
			}

			@Override
			public boolean visitExtensionResources(	Collection<Resource> resources) {
				return false;
			}

			@Override
			public boolean visitExternalResources(Collection<Resource> resources) {
				return false;
			}
			@Override
			public void endVisitOnFailureAction(URI uri) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void endVisitOnSuccessAction(URI uri) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void endVisitTrigger(URI uri) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public boolean visitOnFailureAction(URI uri) {
				// TODO Auto-generated method stub
				return false;
			}
			@Override
			public boolean visitOnSuccessAction(URI uri) {
				// TODO Auto-generated method stub
				return false;
			}
			@Override
			public boolean visitTrigger(URI uri) {
				// TODO Auto-generated method stub
				return false;
			}
			
		};
		c.accept(visitor);
	}

	private void printException(PrintStream out, Exception err) {
		err.printStackTrace(out);
	}

	private Object getOwner(Resource r) {
		Connector c = r.getOwner();
		if (c==null) return null;
		else return c.getOwner().getId();
	}

	private String getInstanceLabel(Contract c) {
		Connector con = c.getSupplier();
		if (con==null) return "?";
		else return con.getOwner().getId();
	}
	private void printSubHeader(PrintStream out, String string) {
		this.printSubHeader(out, string,null);
	}
	private void printSubHeader(PrintStream out, String string,String name) {
		out.print("<h2>");
		if (name!=null) {
			out.print("<a name=\"");
			out.print(name);
			out.print("\">");
		}
		out.print(string);
		if (name!=null) {
			out.print("</a>");
		}
		out.println("</h2>");
	}
	
	private void printSubSubHeader(PrintStream out, String string) {
		out.print("<h3>");
		out.print(string);
		out.println("</h3>");
	}

	private String getFileName(String xp) {
		return xp+".html";
	}
	private String createLink(String label,String link) {
		return new StringBuffer()
			.append("<a href=\"")
			.append(link)
			.append("\">")
			.append(label)
			.append("</a>")
			.toString();
	}

	private String createiLink(String label,String link) {
		return new StringBuffer()
			.append("<a href=\"#")
			.append(link)
			.append("\">")
			.append(label)
			.append("</a>")
			.toString();
	}
	
	private void createIndexPage(File folder,Map<String, List<Contract>> contractsByXP) throws IOException {
		List<String> extensionPoints = new ArrayList<String>();
		extensionPoints.addAll(contractsByXP.keySet());
		Collections.sort(extensionPoints);
		File file = new File(folder.getAbsolutePath()+"/index.html");
		PrintStream out = new PrintStream(new FileOutputStream(file));
		printHtmlHeader(out,"Verification results");
		printTableHeader(out,"extension point","contract instances","verification failed");
		for (String xp:extensionPoints) {
			List<Contract> instances = contractsByXP.get(xp);
			int instanceCount = instances.size();
			int failedCount = 0;
			for (Contract instance:instances) {
				if (instance.getProperty(Constants.VERIFICATION_RESULT) == VerificationResult.FAILURE) {
					failedCount = failedCount+1;
				}
			}
			printTableRow(out,createLink(xp,getFileName(xp)),instanceCount,failedCount);
		}
		printTableFooter(out);
		printHtmlFooter(out);
		out.close();		
	}

	private void printTableFooter(PrintStream out) {
		out.println("</table>");		
	}
	
	private void printTableRow(PrintStream out, Style style, Object... entries) {
		out.print("<tr");
		if (style==null) {
			out.print(">");
		}
		else {
			out.print(" class=\"");
			out.print(style);
			out.print("\">");
		}
		for (Object l:entries) {
			out.print("<td>");
			out.print(l);
			out.print("</td>");
		}
		out.println("</tr>");
	}
	
	private void printTableRow(PrintStream out, Object... entries) {
		this.printTableRow(out, Style.PLAIN, entries);
	}
	
	private void printTableHeader(PrintStream out, String... labels) {
		this.printTableHeader(out,Style.PLAIN,labels);
	}
	
	private void printTableHeader(PrintStream out, Style style, String... labels) {
		out.print("<table cellspacing=\"0\" class=\"");
		out.print(style);
		out.println("\">");
		if (labels.length>0) {
			out.print("<tr>");
			for (String l:labels) {
				out.print("<th>");
				out.print(l);
				out.print("</th>");
			}
			out.println("</tr>");
		}
	}

	private void printStartTreeNode(PrintStream out, Style style, String label,Object node) {
		// debug
		//out.println("--- start " + node + " ---");
		
		out.print("<table  cellspacing=\"0\" width=\"100%\" class=\"");
		out.print(style);
		out.println("\">");
		out.print("<tr><td>");
		out.print(label);
		out.println("</td><td>");
	}

	private void printEndTreeNode(PrintStream out,Object node) {
		out.println("</td></tr>");
		out.println("</table>");
		// debug
		//out.println("--- end " + node + " ---");
	}
	
	private void printHtmlFooter(PrintStream out) {
		out.println("</body>");
		out.println("</html>");
	}

	private void printHtmlHeader(PrintStream out, String title) {
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"StyleSheet\" href=\"treaty.css\" TYPE=\"text/css\" MEDIA=\"screen\">");
		out.println("</head>");
		out.println("<body>");
		out.print("<h1>");
		out.print(title);
		out.println("</h1>");
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
			public boolean visit(ExistsCondition c) {
				list.add("must exist: "+print(c.getResource()));
				return true;
			}
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