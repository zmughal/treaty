package net.java.treaty.schema.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXParseException;

public class SchemaTests {
	private Schema schema = null;
	@Before
	public void setUp() throws Throwable {
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		File file = new File("treaty.xsd");
		try {
			schema = factory.newSchema(file);
		}
		catch (Exception x) {
			x.printStackTrace();
			throw new Exception(x).fillInStackTrace();
		}
	}
	@After
	public void tearDown() throws Exception {
		schema = null;
	}
	private void doTest(String file) throws Exception {
        Validator validator = schema.newValidator();
        File input = new File("contracts/"+file);
        InputStream in = new FileInputStream(input);
        Source source = new StreamSource(in);
        validator.validate(source);
        Assert.assertTrue(true);

	}
	@Test
	public void test1() throws Exception {
		doTest("net.java.treaty.eclipse.example.clock.dateformatter.contract");
	}
	@Test
	public void test2() throws Exception {
		doTest("net.java.treaty.eclipse.example.clock.dateformatter2.contract");
	}
	@Test
	public void test3() throws Exception {
		doTest("net.java.treaty.eclipse.vocabulary.contract");
	}
	
	@Test
	public void test4() throws Exception {
		doTest("org.eclipse.help.toc.contract");
	}
	@Test
	public void test5() throws Exception {
		doTest("net.java.treaty.eclipse.contract.contract");
	}
	@Test(expected=SAXParseException.class)
	public void test5a() throws Exception {
		doTest("_1_net.java.treaty.eclipse.contract.contract");
	}
	@Test
	public void test6() throws Exception {
		doTest("org.eclipse.ui.actionSets.contract");
	}
}
