/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse;

import org.eclipse.core.runtime.Status;

/**
 * <p>
 * {@link Logger} utility for the Eclipse Treaty implementation.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class Logger {

	/**
	 * <p>
	 * Logs an error.
	 * </p>
	 * 
	 * @param msg
	 *          The error to be logged.
	 */
	public static void error(String msg) {

		error(msg, null);
	}

	/**
	 * <p>
	 * Logs an error.
	 * </p>
	 * 
	 * @param msg
	 *          The error to be logged.
	 * @param e
	 *          The {@link Throwable} to be logged.
	 */
	public static void error(String msg, Throwable e) {

		log(msg, e, Status.ERROR);
	}

	/**
	 * <p>
	 * Logs an info message.
	 * </p>
	 * 
	 * @param msg
	 *          The info message to be logged.
	 */
	public static void info(String msg) {

		info(msg, null);
	}

	/**
	 * <p>
	 * Logs an info message for a given {@link Throwable}.
	 * </p>
	 * 
	 * @param msg
	 *          The info message to be logged.
	 * @param e
	 *          The {@link Throwable} to be logged.
	 */
	public static void info(String msg, Throwable e) {

		log(msg, e, Status.INFO);
	}

	/**
	 * <p>
	 * Logs a given message for a given {@link Throwable} and a given status.
	 * </p>
	 * 
	 * @param msg
	 *          The message to be logged.
	 * @param e
	 *          The {@link Throwable} to be logged.
	 * @param status
	 *          The status to be logged.
	 */
	public static void log(String msg, Throwable e, int status) {

		Activator.getDefault().getLog().log(
				new Status(status, Activator.PLUGIN_ID, Status.OK, msg, e));
	}

	/**
	 * <p>
	 * Logs a warning.
	 * </p>
	 * 
	 * @param msg
	 *          The warning to be logged.
	 */
	public static void warn(String msg) {

		warn(msg, null);
	}

	/**
	 * <p>
	 * Logs a warning for a given {@link Throwable}.
	 * </p>
	 * 
	 * @param msg
	 *          The warning to be logged.
	 * @param e
	 *          The {@link Throwable} to be logged.
	 */
	public static void warn(String msg, Throwable e) {

		log(msg, e, Status.WARNING);
	}
}