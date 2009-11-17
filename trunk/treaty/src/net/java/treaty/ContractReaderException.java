/*
 * Copyright (C) 2008-2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty;

/**
 * <p>
 * {@link ContractReaderException}s are thrown if importing a {@link Contract}
 * (e.g., from XML) fails.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class ContractReaderException extends TreatyException {

	/** Generated serial ID used for serialization. */
	private static final long serialVersionUID = -5856798965475303094L;

	/**
	 * <p>
	 * Creates a new {@link ContractReaderException}.
	 * </p>
	 */
	public ContractReaderException() {

		super();
	}

	/**
	 * <p>
	 * Creates a new {@link ContractReaderException} for a given message.
	 * </p>
	 * 
	 * @param msg
	 *          The message of the {@link ContractReaderException}.
	 */
	public ContractReaderException(String msg) {

		super(msg);
	}

	/**
	 * <p>
	 * Creates a new {@link ContractReaderException} for a given {@link Throwable}
	 * .
	 * </p>
	 * 
	 * @param cause
	 *          The cause of the {@link ContractReaderException}.
	 */
	public ContractReaderException(Throwable cause) {

		super(cause);
	}

	/**
	 * <p>
	 * Creates a new {@link ContractReaderException} for a given message and a
	 * given {@link Throwable}.
	 * </p>
	 * 
	 * @param msg
	 *          The message of the {@link ContractReaderException}.
	 * @param cause
	 *          The cause of the {@link ContractReaderException}.
	 */
	public ContractReaderException(String msg, Throwable cause) {

		super(msg, cause);
	}
}