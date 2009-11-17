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
 * Superclass for {@link TreatyException}s.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class TreatyException extends Exception {

	/** Generated serial ID used for serialization. */
	private static final long serialVersionUID = -1048543561688409221L;

	/**
	 * <p>
	 * Creates a new {@link TreatyException}.
	 * </p>
	 */
	public TreatyException() {

		super();
	}

	/**
	 * <p>
	 * Creates a new {@link TreatyException} for a given message.
	 * </p>
	 * 
	 * @param msg
	 *          The message of the {@link TreatyException}.
	 */
	public TreatyException(String msg) {

		super(msg);
	}

	/**
	 * <p>
	 * Creates a new {@link TreatyException} for a given {@link Throwable} .
	 * </p>
	 * 
	 * @param cause
	 *          The cause of the {@link TreatyException}.
	 */
	public TreatyException(Throwable cause) {

		super(cause);
	}

	/**
	 * <p>
	 * Creates a new {@link TreatyException} for a given message and a given
	 * {@link Throwable}.
	 * </p>
	 * 
	 * @param msg
	 *          The message of the {@link TreatyException}.
	 * @param cause
	 *          The cause of the {@link TreatyException}.
	 */
	public TreatyException(String msg, Throwable cause) {

		super(msg, cause);
	}
}