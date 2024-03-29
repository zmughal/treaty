/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.example.clock.lazydateformatter;

import java.text.DateFormat;
import java.util.Date;
import net.java.treaty.eclipse.example.clock.DateFormatter;

/**
 * Simple implementation of the date formatter interface. Uses a short date format.
 * Slow implementation, takes > 100 ms to render a date.
 * @author Jens Dietrich
 */

public class LazyShortDateFormatter implements DateFormatter {

	public String format(Date date) {

		try {
			Thread.sleep(1100);
		} catch (InterruptedException e) {}
		return DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.MEDIUM).format(date);

	}
	public String getName() {
		return "lazy date format";
	}
}
