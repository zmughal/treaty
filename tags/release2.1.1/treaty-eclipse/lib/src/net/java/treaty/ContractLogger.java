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

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * <p>
 * Defines a static log4j logger used to log contract management and validation.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class ContractLogger {

	/** The {@link Logger} used to Log failures and warnings. */
	public static final Logger LOGGER = Logger.getLogger(Component.class);

	/** Static initialization. */
	static {
		LOGGER.addAppender(new ConsoleAppender(new PatternLayout(
				"%r [%t] %-5p %c %x - %m%n"), "System.out"));
		LOGGER.setLevel(Level.DEBUG);
	}
}