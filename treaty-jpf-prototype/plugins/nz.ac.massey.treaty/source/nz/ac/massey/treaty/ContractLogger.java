/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */


package nz.ac.massey.treaty;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Defines a static log4j logger used to log contract management and validation.
 * @author Jens Dietrich
 * @version 0.1 <27/04/2008>
 * @since 0.1
 */


public class ContractLogger {
	public static final Logger LOGGER = Logger.getLogger(Plugin.class);
	static {
		LOGGER.addAppender(new ConsoleAppender(new PatternLayout("%r [%t] %-5p %c %x - %m%n"),"System.out"));
		LOGGER.setLevel(Level.DEBUG);
	}
}
