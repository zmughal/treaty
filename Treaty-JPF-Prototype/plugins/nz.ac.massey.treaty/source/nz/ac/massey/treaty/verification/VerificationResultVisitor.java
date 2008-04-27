/*
 * Copyright (C) 2008 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package nz.ac.massey.treaty.verification;

import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import nz.ac.massey.treaty.*;
import nz.ac.massey.treaty.verification.VerificationReport.LogEntry;

/**
 * A contract visitor used as helper by the verification result printer.
 * @author <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</A>
 * @version 0.1 <27/04/2008>
 * @since 0.1
 */

public class VerificationResultVisitor implements ContractVisitor {
	private PrintStream out = System.out;
	private String root = null;
	private Map<Object,VerificationReport.LogEntry> verificationReport = null;
	
	public VerificationResultVisitor(PrintStream out, Map<Object, LogEntry> verificationReport, String root) {
		super();
		this.out = out;
		this.verificationReport = verificationReport;
		this.root = root.replace(" ","%20").replace("./","");
	}
	private String convertToRelativePath(Object p) {
		String path = p==null?null:p.toString();
		if (p==null)
			return null;
		if (root!=null && path.startsWith(root)) {
			return path.substring(root.toString().length());
		}
		return path;			
	}
	private void printAttribute(String name,Object value) {
		out.print(" ");
		out.print(name);
		out.print("=\"");
		out.print(value);
		out.print("\"");
	}
	private void printValidationResultInfo(Object context) {
		VerificationReport.LogEntry entry = this.verificationReport.get(context);
		if (entry!=null) {
			out.print("<verificationresult");
			VerificationResult result = entry.getResult();
			if (result==VerificationResult.FAILURE)
				this.printAttribute("result","failure");
			else if (result==VerificationResult.SUCCESS)
				this.printAttribute("result","success");
			else 
				this.printAttribute("result","unknown");
			this.printAttribute("remarks",entry.getRemarks());
			out.print("/>");
		}
	}
	
	public void print(Contract contract) {
		contract.accept(this);
	}
	
	public boolean visit(Contract contract) {
		out.println("<contract ");
		printAttribute("url",convertToRelativePath(contract.getLocation()));
		printAttribute("extensionPoint",contract.getExtensionPointId());
		printAttribute("pluginOfExtension",contract.getPluginId4Extension());
		printAttribute("plugin",contract.getPluginId());
		out.println(">");
		printValidationResultInfo(contract); 
		return true;
	}

	public boolean visit(Resource resource) {
		out.println("<resource ");
		printAttribute("id",resource.getId());
		printAttribute("isResolved",resource.isResolved());
		if (resource.getRef()!=null)
			printAttribute("ref",resource.getRef());
		if (resource.getName()!=null)
			printAttribute("name",resource.getName());
		if (resource.getValue()!=null)
			printAttribute("value",convertToRelativePath(resource.getValue()));
		printAttribute("type",resource.getType());
		printAttribute("owner",resource.isOwnedByExtensionpoint()?"extension point":"extension");
		out.println("/>");
		return true;
	}

	public boolean visitExtensionResources(Collection<Resource> resources) {
		return false;
	}

	public boolean visitExtensionPointResources(Collection<Resource> resources) {
		return false;
	}

	public boolean visitConditions(List<AbstractCondition> name) {
		return true;
	}

	public boolean visit(Conjunction condition) {
		out.println("<group");
		this.printAttribute("connective","and");
		out.println(">");
		printValidationResultInfo(condition);
		return true;
	}

	public boolean visit(Disjunction condition) {
		out.println("<group");
		this.printAttribute("connective","or");
		out.println(">");
		printValidationResultInfo(condition);
		return true;
	}

	public boolean visit(XDisjunction condition) {
		out.println("<group");
		this.printAttribute("connective","xor");
		out.println(">");
		printValidationResultInfo(condition);
		return true;
	}
	
	public boolean visit(Condition condition) {
		out.println("<condition");
		this.printAttribute("relationship", condition.getRelationship().toString());
		out.println(">");
		printValidationResultInfo(condition); 
		return true;
	}

	public boolean visit(PropertyCondition condition) {	
		out.println("<propertycondition");
		this.printAttribute("property", condition.getProperty().toString());
		this.printAttribute("operator", condition.getOperator().toString());
		this.printAttribute("value", condition.getValue());
		out.println(">");
		printValidationResultInfo(condition); 
		return true;
	}

	public void endVisit(Contract contract) {
		out.println("</contract>");
	}

	public void endVisit(Resource resource) {
		out.println("</resource>");
	}

	public void endVisitExtensionResources(Collection<Resource> resources) {
	}

	public void endVisitExtensionPointResources(Collection<Resource> resources) {
	}

	public void endVisit(Conjunction condition) {
		out.println("</group>");
	}

	public void endVisit(Disjunction condition) {
		out.println("</group>");
	} 
	
	public void endVisit(XDisjunction condition) {
		out.println("</group>");
	}
	
	public void endVisit(Condition condition) {
		out.println("</condition>");
	}

	public void endVisit(PropertyCondition condition) {
		out.println("</propertycondition>");
	}

	public void endVisitConditions(Collection<AbstractCondition> conditions) {
	}

}
