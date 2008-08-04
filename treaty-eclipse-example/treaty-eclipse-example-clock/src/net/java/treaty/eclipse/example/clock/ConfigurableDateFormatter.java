/*
 * Copyright (C) 2008 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.example.clock;

import java.util.Date;

/**
 * Generic date formatter that uses a format string. Plugins provide this string in a simple xml document.
 * This is an alternative to providing an implementation of the DateFormatter interface.
 * @author Jens Dietrich
 */

public class ConfigurableDateFormatter implements DateFormatter {

	private java.text.SimpleDateFormat format = null;
	public ConfigurableDateFormatter(String format) {
		super();
		this.format = new java.text.SimpleDateFormat(format);
	}
	public String format(Date date) {
		return format.format(date);
	}
	@Override
	public String getName() {
		return "date formatter configured by an xml file";
	}

}
