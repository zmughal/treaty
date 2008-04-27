/*
 * Copyright (C) 2008 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package nz.ac.massey.treaty;

/**
 * Exception indicating that a contract is not valid.
 * @author <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</A>
 * @version 0.1 <27/04/2008>
 * @since 0.1
 */


public class InvalidContractException extends Exception {

	public InvalidContractException() {
		super();
	}

	public InvalidContractException(String arg0) {
		super(arg0);
	}

	public InvalidContractException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InvalidContractException(Throwable arg0) {
		super(arg0);
	}

}
