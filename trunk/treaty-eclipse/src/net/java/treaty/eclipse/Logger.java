/*
 * Copyright (C) 2008 Jens Dietrich
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
 * Log utility.
 * @author Jens Dietrich
 */

public class Logger {
	   public static void info(String msg) {
		      info(msg, null);
	   }
	   public static void info(String msg, Throwable e) {
	      log(msg,e,Status.INFO);
	   }
	   
	   public static void warn(String msg) {
		      warn(msg, null);
	   }
	   public static void warn(String msg, Throwable e) {
	      log(msg,e,Status.WARNING);
	   }
	   
	   public static void error(String msg) {
		      error(msg, null);
	   }
	   public static void error(String msg, Throwable e) {
	      log(msg,e,Status.ERROR);
	   }
	   
	   public static void log(String msg, Throwable e,int status) {
		      Activator.getDefault().getLog().log(new Status(status, Activator.PLUGIN_ID, Status.OK, msg, e));
	   }


}
