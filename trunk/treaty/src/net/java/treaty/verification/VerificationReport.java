/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.verification;

import net.java.treaty.Contract;

/**
 * The verification report is used to collect information during verification.
 * It can then be used to visualise and store verification results.
 * @author Jens Dietrich
 */

public interface VerificationReport {

	
	public void log(Object context,VerificationResult result,String... remarks);

	public Contract getContract();

	public void setContract(Contract contract);








}
