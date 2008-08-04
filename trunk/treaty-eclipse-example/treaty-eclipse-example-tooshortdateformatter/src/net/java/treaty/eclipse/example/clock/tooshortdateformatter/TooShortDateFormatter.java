/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.example.clock.tooshortdateformatter;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.java.treaty.eclipse.example.clock.DateFormatter;

/**
 * Simple implementation of the date formatter interface. Uses a short date format.
 * Only prints day and month, not the year.
 */

public class TooShortDateFormatter implements DateFormatter {
	private SimpleDateFormat DF = new SimpleDateFormat("dd.MM. HH.mm.ss");
	@Override
	public String format(Date date) {
		return DF.format(date);
	}
	@Override
	public String getName() {
		return "too short date format";
	}
}
