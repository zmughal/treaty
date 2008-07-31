/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package nz.ac.massey.treaty.example.tooshortdate;

import java.text.SimpleDateFormat;
import java.util.Date;
import nz.ac.massey.treaty.example.DateFormatter;

/**
 * Simple implementation of the date formatter interface. Uses a short date format.
 * Only prints day and month, not the year.
 * @author Jens Dietrich
 * @version 0.1 <28/04/2008>
 * @since 0.1
 */

public class TooShortDateFormatter implements DateFormatter {
	private SimpleDateFormat DF = new SimpleDateFormat("dd.MM");
	public String format(Date date) {

		return DF.format(date);

	}
}
