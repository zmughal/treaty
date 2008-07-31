/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package nz.ac.massey.treaty.verification;

import java.util.*;

import nz.ac.massey.treaty.Contract;

/**
 * The verification report is used to collect information during verification.
 * It can then be used to visualise and store verification results.
 * @author Jens Dietrich
 * @version 0.1 <27/04/2008>
 * @since 0.1
 */

public class VerificationReport {

	private String extensionId = null;
	private String extensionPointId = null;
	private Contract contract = null;
	public class LogEntry {
		private String extensionId = null;
		private String extensionPointId = null;
		private Contract contract = null;
		private Object context = null;
		private VerificationResult result = null;
		private String remarks = null;

		public Contract getContract() {
			return contract;
		}
		public String getRemarks() {
			return remarks;
		}
		public VerificationResult getResult() {
			return result;
		}
		public String getExtensionId() {
			return extensionId;
		}
		public String getExtensionPointId() {
			return extensionPointId;
		}
		public Object getContext() {
			return context;
		}

	}
	private List<LogEntry> entries = new ArrayList<LogEntry>();
	
	public VerificationReport() {
		super();
	}
	
	public void log(Object context,VerificationResult result,String... remarks){
		LogEntry entry = new LogEntry();
		entry.contract = contract;
		entry.extensionId = extensionId;
		entry.extensionPointId = extensionPointId;
		entry.context = context;
		entry.result = result;
		if (remarks.length==0)
			entry.remarks = "";
		else if (remarks.length==1)
			entry.remarks=remarks[0];
		else {
			StringBuffer s = new StringBuffer();
			for (String r:remarks)
				s.append(r);
			entry.remarks = s.toString();
		}
		
		this.entries.add(entry);
		
		// debug
		/**
		System.out.print(result);
		System.out.print(" ");
		System.out.print(context);
		for (String r:remarks)
			System.out.print(r);
		System.out.println();
		*/
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public List<LogEntry> getEntries() {
		return entries;
	}


	public String getExtensionId() {
		return extensionId;
	}

	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
	}

	public String getExtensionPointId() {
		return extensionPointId;
	}

	public void setExtensionPointId(String extensionPointId) {
		this.extensionPointId = extensionPointId;
	}


}
