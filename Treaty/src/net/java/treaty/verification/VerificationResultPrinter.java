/*
 * Copyright (C) 2008 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.verification;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.java.treaty.SimpleContract;

/**
 * Used to export verification results into XML files.
 * @author <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</A>
 * @version 0.1 <27/04/2008>
 * @since 0.1
 */

public class VerificationResultPrinter {
	
	public String print(Map<SimpleContract,VerificationReport> results) throws IOException {
		String location = new File("verificationresults.xml").toURI().toString();
		PrintStream out = new PrintStream(new FileOutputStream("verificationresults.xml"));
		String root = new File(".").toURL().toString();
		
		out.println("<?xml version=\"1.0\" ?>");
		out.println("<?xml-stylesheet type=\"text/xsl\" href=\"verificationresults.xsl\"?>");
		out.println("<verificationresults>");
		for (Map.Entry<SimpleContract,VerificationReport> result:results.entrySet()) {
			SimpleContract contract = result.getKey();
			Collection<VerificationReport.LogEntry> entries = result.getValue().getEntries();
			Map<Object,VerificationReport.LogEntry> entriesByObject = new HashMap<Object,VerificationReport.LogEntry>();
			for (VerificationReport.LogEntry entry:entries)
				entriesByObject.put(entry.getContext(),entry);
			VerificationResultVisitor visitor = new VerificationResultVisitor(out,entriesByObject,root);
			contract.accept(visitor);
		}
		out.println("</verificationresults>");
		out.close();
		return location;
	}

}
