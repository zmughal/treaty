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
	
	private static Resource[] EmptyResourceArray = {};
}
