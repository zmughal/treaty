package test.net.java.treaty.script;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import static org.junit.Assert.*;

import net.java.treaty.Conjunction;
import net.java.treaty.Contract;
import net.java.treaty.ExistsCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.TreatyException;
import net.java.treaty.XDisjunction;
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
				"consumer-resource Interface type=http://www.treaty.org/java#AbstractType name=net.java.treaty.eclipse.example.clock.DateFormatter\n" +
				"consumer-resource QoSTests type=http://www.treaty.org/junit#TestCase name=net.java.treaty.eclipse.example.clock.DateFormatterPerformanceTests\n" +
				"consumer-resource FunctionalTests type=http://www.treaty.org/junit#TestCase name=net.java.treaty.eclipse.example.clock.DateFormatterFunctionalTests\n" +
				"consumer-resource DateFormatDef type=http://www.treaty.org/xml#XMLSchema name=/dateformat.xsd\n" +
				"supplier-resource Formatter type=http://www.treaty.org/java#InstantiableClass ref=serviceprovider/@class\n" +
				"supplier-resource FormatString type=http://www.treaty.org/xml#XMLInstance ref=serviceprovider/@formatdef\n" +
				// "external-resource DateFormatDef2 type=http://www.treaty.org/xml#XMLSchema name=/dateformat.xsd\n" +
				"constraint (\n" +
				"    (\n" +
				"        Formatter http://www.treaty.org/java#implements Interface and mustexist Formatter\n" +
				"        and Formatter http://www.treaty.org/junit#verifies FunctionalTests \n" +
				"        and Formatter http://www.treaty.org/junit#verifies QoSTests\n" +
				"    )\n" +
				"    xor FormatString http://www.treaty.org/xml#instantiates DateFormatDef\n" +
				")\n" +
				"onfailure action:osgi.uninstall.$bundle\n" +
				"onfailure action:osgi.log.debug.$bundle\n" +
				"onsuccess action:osgi.log.debug.$bundle\n"
			);
		
		assertEquals("jens",contract.getProperty("dc:author"));
		assertEquals("30/10/09",contract.getProperty("dc:date"));

		assertEquals(new URI("event:osgi.activated"),contract.getTriggers().get(0));
		assertEquals(new URI("event:osgi.updated"),contract.getTriggers().get(1));
		assertEquals(2,contract.getTriggers().size());
		
		assertEquals(new URI("action:osgi.log.debug.$bundle"),contract.getOnVerificationSucceedsActions().get(0));
		assertEquals(1,contract.getOnVerificationSucceedsActions().size());
		
		assertEquals(new URI("action:osgi.uninstall.$bundle"),contract.getOnVerificationFailsActions().get(0));
		assertEquals(new URI("action:osgi.log.debug.$bundle"),contract.getOnVerificationFailsActions().get(1));
		assertEquals(2,contract.getOnVerificationFailsActions().size());
		
		assertEquals(4,contract.getConsumerResources().size());
		
		Resource r0 = findResource(contract.getConsumerResources(),"Interface");
		assertNotNull(r0);
		assertEquals(new URI("http://www.treaty.org/java#AbstractType"),r0.getType());
		assertEquals("net.java.treaty.eclipse.example.clock.DateFormatter",r0.getName());
		
		Resource r1 = findResource(contract.getConsumerResources(),"QoSTests");
		assertNotNull(r1);
		assertEquals(new URI("http://www.treaty.org/junit#TestCase"),r1.getType());
		assertEquals("net.java.treaty.eclipse.example.clock.DateFormatterPerformanceTests",r1.getName());		
		
		Resource r2= findResource(contract.getConsumerResources(),"FunctionalTests");
		assertNotNull(r2);
		assertEquals(new URI("http://www.treaty.org/junit#TestCase"),r2.getType());
		assertEquals("net.java.treaty.eclipse.example.clock.DateFormatterFunctionalTests",r2.getName());			
	
		Resource r3= findResource(contract.getConsumerResources(),"DateFormatDef");
		assertNotNull(r3);
		assertEquals(new URI("http://www.treaty.org/xml#XMLSchema"),r3.getType());
		assertEquals("/dateformat.xsd",r3.getName());
		
		assertEquals(2,contract.getSupplierResources().size());
		
		Resource r4 = findResource(contract.getSupplierResources(),"Formatter");
		assertNotNull(r4);
		assertEquals(new URI("http://www.treaty.org/java#InstantiableClass"),r4.getType());
		assertEquals("serviceprovider/@class",r4.getRef());
	
		Resource r5 = findResource(contract.getSupplierResources(),"FormatString");
		assertNotNull(r5);
		assertEquals(new URI("http://www.treaty.org/xml#XMLInstance"),r5.getType());
		assertEquals("serviceprovider/@formatdef",r5.getRef());
		
		assertEquals(1,contract.getConstraints().size());
		assertTrue(contract.getConstraints().get(0) instanceof XDisjunction);
		
		XDisjunction xor = (XDisjunction)contract.getConstraints().get(0);
		assertEquals(2,xor.getParts().size());
		assertTrue(xor.getParts().get(0) instanceof Conjunction);
		assertTrue(xor.getParts().get(1) instanceof RelationshipCondition);
		
		Conjunction and = (Conjunction)xor.getParts().get(0);
		assertEquals(4,and.getParts().size());
		assertTrue(and.getParts().get(0) instanceof RelationshipCondition);
		assertTrue(and.getParts().get(1) instanceof ExistsCondition);
		assertTrue(and.getParts().get(2) instanceof RelationshipCondition);
		assertTrue(and.getParts().get(3) instanceof RelationshipCondition);
		
		RelationshipCondition rel0 = (RelationshipCondition)and.getParts().get(0);
		assertEquals("Formatter",rel0.getResource1().getId());
		assertEquals("Interface",rel0.getResource2().getId());
		assertEquals(new URI("http://www.treaty.org/java#implements"),rel0.getRelationship());
		
		ExistsCondition x1 = (ExistsCondition)and.getParts().get(1);
		assertEquals("Formatter",x1.getResource().getId());
		
		RelationshipCondition rel2 = (RelationshipCondition)and.getParts().get(2);
		assertEquals("Formatter",rel2.getResource1().getId());
		assertEquals("FunctionalTests",rel2.getResource2().getId());
		assertEquals(new URI("http://www.treaty.org/junit#verifies"),rel2.getRelationship());
		
		RelationshipCondition rel3 = (RelationshipCondition)and.getParts().get(3);
		assertEquals("Formatter",rel3.getResource1().getId());
		assertEquals("QoSTests",rel3.getResource2().getId());
		assertEquals(new URI("http://www.treaty.org/junit#verifies"),rel3.getRelationship());
		
		RelationshipCondition rel4 = (RelationshipCondition)xor.getParts().get(1);
		assertEquals("FormatString",rel4.getResource1().getId());
		assertEquals("DateFormatDef",rel4.getResource2().getId());
		assertEquals(new URI("http://www.treaty.org/xml#instantiates"),rel4.getRelationship());
		
		  
	}

	@Test
	public void testScriptWithExternalResources() throws Exception {
		Contract contract = TreatyScript.fromString(
			"external-resource toc_dtd type=http://www.treaty.org/xml#DTD name=contract_resources/org.eclipse.help.toc/toc.dtd\n" +
			"supplier-resource toc type=http://www.treaty.org/xml#XMLInstance ref=/toc/@file\n" +
			"constraint toc http://www.treaty.org/xml#instantiatesDTD toc_dtd"
		);
		
		assertEquals(0,contract.getTriggers().size());
		assertEquals(0,contract.getOnVerificationFailsActions().size());
		assertEquals(0,contract.getOnVerificationSucceedsActions().size());
		
		assertEquals(1,contract.getExternalResources().size());
		assertEquals(1,contract.getSupplierResources().size());
		assertEquals(0,contract.getConsumerResources().size());
		
		Resource r = contract.getExternalResources().iterator().next();
		assertEquals("toc_dtd",r.getId());
		assertEquals(new URI("http://www.treaty.org/xml#DTD "),r.getType());
		assertEquals("contract_resources/org.eclipse.help.toc/toc.dtd",r.getName());
		
		assertEquals(1,contract.getConstraints().size());
		assertTrue(contract.getConstraints().get(0) instanceof RelationshipCondition);
		
		RelationshipCondition rel = (RelationshipCondition)contract.getConstraints().get(0);
		assertEquals(new URI("http://www.treaty.org/xml#instantiatesDTD"),rel.getRelationship());
		assertEquals("toc",rel.getResource1().getId());
		assertEquals("toc_dtd",rel.getResource2().getId());
	}
	// constraint references Formatter2 that is never defined as a resource
	@Test(expected=TreatyException.class)
	public void testInconsistentResourceRefs1() throws Exception {
		Contract contract = TreatyScript.fromString(
				"// example contract used in prototype\n" +
				"@dc:author=jens\n" +
				"@dc:date=30/10/09\n" +
				"on event:osgi.activated\n" +
				"on event:osgi.updated\n" +
				"consumer-resource Interface type=http://www.treaty.org/java#AbstractType name=net.java.treaty.eclipse.example.clock.DateFormatter\n" +
				"consumer-resource QoSTests type=http://www.treaty.org/junit#TestCase name=net.java.treaty.eclipse.example.clock.DateFormatterPerformanceTests\n" +
				"consumer-resource FunctionalTests type=http://www.treaty.org/junit#TestCase name=net.java.treaty.eclipse.example.clock.DateFormatterFunctionalTests\n" +
				"consumer-resource DateFormatDef type=http://www.treaty.org/xml#XMLSchema name=/dateformat.xsd\n" +
				"supplier-resource Formatter type=http://www.treaty.org/java#InstantiableClass ref=serviceprovider/@class\n" +
				"supplier-resource FormatString type=http://www.treaty.org/xml#XMLInstance ref=serviceprovider/@formatdef\n" +
				"constraint (\n" +
				"    (\n" +
				"        Formatter http://www.treaty.org/java#implements Interface and mustexist Formatter\n" +
				"        and Formatter http://www.treaty.org/junit#verifies FunctionalTests \n" +
				"        and Formatter2 http://www.treaty.org/junit#verifies QoSTests\n" +
				"    )\n" +
				"    xor FormatString http://www.treaty.org/xml#instantiates DateFormatDef\n" +
				")\n" +
				"onfailure action:osgi.uninstall.$bundle\n" +
				"onfailure action:osgi.log.debug.$bundle\n" +
				"onsuccess action:osgi.log.debug.$bundle\n"
			);
	}
	
	private Resource findResource(Collection<Resource> resources,String id) {
		for (Resource r:resources) {
			if (r.getId().equals(id)) return r;
		}
		return null;
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
