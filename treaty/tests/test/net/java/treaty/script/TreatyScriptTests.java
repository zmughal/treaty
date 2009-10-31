package test.net.java.treaty.script;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import static org.junit.Assert.*;

import net.java.treaty.Conjunction;
import net.java.treaty.Contract;
import net.java.treaty.ExistsCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.script.TreatyScript;

public class TreatyScriptTests {
	@Test
	public void testSimpleScript() throws Exception {
		Contract contract = TreatyScript.fromString(
			"// this is a contract\n" +
			"@author=jens\n" +
			"on event:osgi.activated\n" +
			"on event:osgi.updated\n" +
			"consumer-resource Interface type=http://www.treaty.org/java#AbstractType name=net.java.treaty.eclipse.example.clock.DateFormatter\n" +
			"supplier-resource Formatter type=http://www.treaty.org/java#InstantiableClass ref=serviceprovider/@class\n" +
			"constraint Formatter http://www.treaty.org/java#implements Interface and mustexist Formatter\n" +
			"onfailure action:osgi.uninstall.$bundle\n" +
			"onsuccess action:osgi.log.debug.$bundle\n"
		);
		
		// Annotations
		assertEquals("jens", contract.getProperty("author"));
		
		// Triggers
		assertEquals(new URI("event:osgi.activated"), contract.getTriggers().get(0));
		assertEquals(new URI("event:osgi.updated"), contract.getTriggers().get(1));
		
		// Consumer Resource
		Resource[] consumerResources = contract.getConsumerResources().toArray(EmptyResourceArray);
		
		assertEquals("Interface", consumerResources[0].getId());
		assertEquals(new URI("http://www.treaty.org/java#AbstractType"), consumerResources[0].getType());
		assertEquals("net.java.treaty.eclipse.example.clock.DateFormatter", consumerResources[0].getName());
		
		// Supplier Resource
		Resource[] supplierResources = contract.getSupplierResources().toArray(EmptyResourceArray);
		
		assertEquals("Formatter", supplierResources[0].getId());
		assertEquals(new URI("http://www.treaty.org/java#InstantiableClass"), supplierResources[0].getType());
		assertEquals("serviceprovider/@class", supplierResources[0].getRef());
		
		// Verification Actions
		assertEquals(new URI("action:osgi.uninstall.$bundle"), contract.getOnVerificationFailsActions().get(0));
		assertEquals(new URI("action:osgi.log.debug.$bundle"), contract.getOnVerificationSucceedsActions().get(0));
		
		// Constraints
		final AtomicInteger constraintsRemaining = new AtomicInteger(2);
		
		Conjunction conjunction = (Conjunction)contract.getConstraints().get(0);
		conjunction.accept(new ContractValidator() {
			@Override
			public boolean visit(ExistsCondition condition) {
				assertEquals("Formatter", condition.getResource().getId());
				
				constraintsRemaining.decrementAndGet();
				
				return true;
			}
			
			@Override
			public boolean visit(RelationshipCondition relationshipCondition) {
				assertEquals("Formatter", relationshipCondition.getResource1().getId());
				assertEquals("Interface", relationshipCondition.getResource2().getId());
				
				try {
					assertEquals(new URI("http://www.treaty.org/java#implements"), relationshipCondition.getRelationship());	
				} catch (URISyntaxException e) {
					return true;
				}
				
				constraintsRemaining.decrementAndGet();
				
				return true;
			}
		});
		
		assertEquals("The expected number of constraints wern't visited.", 0, constraintsRemaining.get());
		
	}
	// script for the clock example
	@Test
	public void testClockScript() throws Exception {
		Contract contract = TreatyScript.fromString(
				"// example contract used in prototype\n" +
				"@dc:author=jens\n" +
				"@dc:date=30/10/09\n" +
				"on event:osgi.activated\n" +
				"on event:osgi.updated\n" +
				"consumer-resource Interface type=http://www.treaty.org/junit#TestCase name=net.java.treaty.eclipse.example.clock.DateFormatterPerformanceTests\n" +
				"consumer-resource QoSTests type=http://www.treaty.org/java#AbstractType name=net.java.treaty.eclipse.example.clock.DateFormatter\n" +
				"consumer-resource FunctionalTests type=http://www.treaty.org/junit#TestCase name=net.java.treaty.eclipse.example.clock.DateFormatterFunctionalTests\n" +
				"consumer-resource DateFormatDef type=http://www.treaty.org/xml#XMLSchema name=/dateformat.xsd\n" +
				"supplier-resource Formatter type=http://www.treaty.org/java#InstantiableClass ref=serviceprovider/@class\n" +
				"supplier-resource FormatString type=http://www.treaty.org/xml#XMLInstance ref=serviceprovider/@formatdef\n" +
				"constraint (\n" +
				"    (\n" +
				"        Formatter http://www.treaty.org/java#implements Interface and mustexist Formatter\n" +
				"        and Formatter http://www.treaty.org/junit#verifies FunctionalTests \n" +
				"        and Formatter http://www.treaty.org/junit#verifies QoSTests\n" +
				"    )\n" +
				"    xor FormatString http://www.treaty.org/xml#instantiates DateFormatDef\n" +
				")\n" +
				"onfailure action:osgi.uninstall.$bundle\n" +
				"onsuccess action:osgi.log.debug.$bundle\n"
			);
		assertTrue(true);
	}
	
	@Test
	public void testSpecialCharsInResourceName() throws Exception {
		Contract contract = TreatyScript.fromString(
			"consumer-resource DateFormatDef type=http://www.treaty.org/xml#XMLSchema name=/dateformat.xsd\n"
		);
		
		String s = "constraint ((Formatter http://www.treaty.org/java#implements Interface and mustexist Formatter\n" +
		"and Formatter http://www.treaty.org/junit#verifies FunctionalTests \n" +
		"and Formatter http://www.treaty.org/junit#verifies QoSTests)\n" +
		"xor FormatString http://www.treaty.org/xml#instantiates DateFormatDef\n";
		
		// Consumer Resource
		Resource[] consumerResources = contract.getConsumerResources().toArray(EmptyResourceArray);
		
		assertEquals("DateFormatDef", consumerResources[0].getId());
		assertEquals(new URI("http://www.treaty.org/xml#XMLSchema"), consumerResources[0].getType());
		assertEquals("/dateformat.xsd", consumerResources[0].getName());
	}
	

	
	@Test
	public void testMultilineConstraints() throws Exception {
		Contract contract = TreatyScript.fromString(
			"consumer-resource Interface type=http://www.treaty.org/java#AbstractType name=net.java.treaty.eclipse.example.clock.DateFormatter\n" +
			"supplier-resource Formatter type=http://www.treaty.org/java#InstantiableClass ref=serviceprovider/@class\n" +
			"constraint (\n" +
			"  Formatter http://www.treaty.org/java#implements Interface\n" +
			"  and Formatter http://www.treaty.org/junit#verifies Interface\n" +
			"  and Formatter http://www.treaty.org/junit#verifies Interface\n" +
			")\n"
		);
		
		Conjunction conjunction = (Conjunction)contract.getConstraints().get(0);
	}
	
	private static Resource[] EmptyResourceArray = {};
}
