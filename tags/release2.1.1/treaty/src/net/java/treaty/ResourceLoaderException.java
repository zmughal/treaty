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
 * {@link ResourceLoaderException}s indicate problems when loading resources.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class ResourceLoaderException extends TreatyException {

	/** Generated serial ID used for serialization. */
	private static final long serialVersionUID = 201680964455844368L;

	/**
	 * <p>
	 * Creates a new {@link ResourceLoaderException}.
	 * </p>
	 */
	public ResourceLoaderException() {

		super();
	}

	/**
	 * <p>
	 * Creates a new {@link ResourceLoaderException} for a given message.
	 * </p>
	 * 
	 * @param msg
	 *          The message of the {@link ResourceLoaderException}.
	 */
	public ResourceLoaderException(String msg) {

		super(msg);
	}

	/**
	 * <p>
	 * Creates a new {@link ResourceLoaderException} for a given {@link Throwable}
	 * .
	 * </p>
	 * 
	 * @param cause
	 *          The cause of the {@link ResourceLoaderException}.
	 */
	public ResourceLoaderException(Throwable cause) {

		super(cause);
	}

	/**
	 * <p>
	 * Creates a new {@link ResourceLoaderException} for a given message and a
	 * given {@link Throwable}.
	 * </p>
	 * 
	 * @param msg
	 *          The message of the {@link ResourceLoaderException}.
	 * @param cause
	 *          The cause of the {@link ResourceLoaderException}.
	 */
	public ResourceLoaderException(String msg, Throwable cause) {

		super(msg, cause);
	}
}