/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package nz.ac.massey.treaty.example.lazyshortdate;

import java.text.DateFormat;
import java.util.Date;
import nz.ac.massey.treaty.example.DateFormatter;

/**
 * Simple implementation of the date formatter interface. Uses a short date format.
 * Slow implementation, takes > 100 ms to render a date.
 * @author Jens Dietrich
 * @version 0.1 <27/04/2008>
 * @since 0.1
 */

public class LazyShortDateFormatter implements DateFormatter {

	public String format(Date date) {

		try {
			Thread.currentThread().sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return DateFormat.getDateInstance(DateFormat.SHORT).format(date);

	}
}
