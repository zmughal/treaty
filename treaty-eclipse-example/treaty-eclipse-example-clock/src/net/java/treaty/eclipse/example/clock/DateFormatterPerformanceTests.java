/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.example.clock;

import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Performance (quality of service) tests for date formatter.
 * @author Jens Dietrich
 */

public class DateFormatterPerformanceTests {
	
	private DateFormatter formatter = null;
	private Date testedDate = null;
	
	public DateFormatterPerformanceTests(DateFormatter formatter) {
		super();
		this.formatter = formatter;
	}
	@Before
	public void setUp() {
		testedDate = new GregorianCalendar(1987,6,21).getTime();
	}
	@After
	public void tearDown() {
		testedDate = null;
		formatter = null;
	}
	// date formatting should not take more than 10 ms
	@Test(timeout=10)
	public void testSpeed() {
		formatter.format(testedDate);
	}

}
