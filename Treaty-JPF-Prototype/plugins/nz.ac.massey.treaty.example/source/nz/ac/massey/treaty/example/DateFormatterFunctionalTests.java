/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package nz.ac.massey.treaty.example;

import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for date formatter.
 * @author Jens Dietrich
 * @version 0.1 <26/04/2008>
 * @since 0.1
 */

public class DateFormatterFunctionalTests {
	
	private DateFormatter formatter = null;
	private Date testedDate = null;
	
	public DateFormatterFunctionalTests(DateFormatter formatter) {
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
	@Test
	// at least the last two digits of the year should be printed
	public void test1() {
		String s = formatter.format(testedDate);
		assertTrue(s.contains("87"));
	}
	// the month should be printed either as a number or using its (emglish) name
	@Test
	public void test2() {
		String s = formatter.format(testedDate);
		assertTrue(s.contains("7") || s.contains("July"));
	}
	// the name should be printed
	@Test
	public void test3() {
		String s = formatter.format(testedDate);
		assertTrue(s.contains("21"));
	}
}
