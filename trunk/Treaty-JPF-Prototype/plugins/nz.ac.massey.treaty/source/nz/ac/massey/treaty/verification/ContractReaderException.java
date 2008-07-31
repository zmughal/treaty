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

/**
 * Thrown if importing a contract from XML fails.
 * @author <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</A>
 * @version 0.1 <27/04/2008>
 * @since 0.1
 */

public class ContractReaderException extends Exception {

	public ContractReaderException() {
		super();
	}

	public ContractReaderException(String arg0) {
		super(arg0);
	}

	public ContractReaderException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ContractReaderException(Throwable arg0) {
		super(arg0);
	}

}