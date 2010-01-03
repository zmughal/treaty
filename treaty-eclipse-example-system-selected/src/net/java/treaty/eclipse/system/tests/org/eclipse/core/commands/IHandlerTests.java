package net.java.treaty.eclipse.system.tests.org.eclipse.core.commands;

import static org.junit.Assert.assertTrue;

import org.eclipse.core.commands.IHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IHandlerTests {

	
	private IHandler handler = null;
	
	public IHandlerTests(IHandler handler) {
		super();
		this.handler = handler;
	}
	@Before
	public void setUp() {
	}
	@After
	public void tearDown() {
		handler = null;
	}
	@Test
	public void test1() {
		try {
			handler.execute(null);
			assertTrue(false);
		}
		catch (Exception x) {
			assertTrue(true);
		}
	}


}
