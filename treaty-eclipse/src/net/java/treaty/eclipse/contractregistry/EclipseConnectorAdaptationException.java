/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */
package net.java.treaty.eclipse.contractregistry;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;

/**
 * <p>
 * An {@link EclipseConnectorAdaptationException} is thrown by the
 * {@link EclipseAdapterFactory}, if an {@link IExtensionPoint} or
 * {@link IExtension} that shall be adapted is invalid.
 * </p>
 * 
 * @author Claas Wilke
 */
public class EclipseConnectorAdaptationException extends Exception {

	/** Generated ID for serialization. */
	private static final long serialVersionUID = -8480028207478871355L;

	/**
	 * <p>
	 * Creates a new {@link EclipseConnectorAdaptationException}.
	 * </p>
	 * 
	 * @param msg
	 *          The message of the {@link EclipseConnectorAdaptationException}.
	 * @param cause
	 *          The cause (a {@link Throwable}) of the
	 *          {@link EclipseConnectorAdaptationException}.
	 */
	public EclipseConnectorAdaptationException(String msg, Throwable cause) {

		super(msg, cause);
	}
}