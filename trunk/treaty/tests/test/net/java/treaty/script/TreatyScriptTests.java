package test.net.java.treaty.script;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.Collection;

import net.java.treaty.ConjunctiveCondition;
import net.java.treaty.Contract;
import net.java.treaty.ContractVocabulary;
import net.java.treaty.ExistsCondition;
import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.TreatyException;
import net.java.treaty.script.TreatyScript;
import net.java.treaty.vocabulary.CompositeContractOntology;
import net.java.treaty.vocabulary.builtins.BasicOpVocabulary;
import net.java.treaty.vocabulary.builtins.java.JavaVocabulary;
import net.java.treaty.vocabulary.builtins.xml.XMLVocabulary;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TreatyScriptTests {

	private ContractVocabulary builtinVocabulary = null;
	private ContractVocabulary javaVocabulary = null;
	private ContractVocabulary xmlVocabulary = null;

	private CompositeContractOntology compositeVocabulary = null;

	@Before
	public void setUp() throws Exception {

		builtinVocabulary = new BasicOpVocabulary();
		javaVocabulary = new JavaVocabulary();
		xmlVocabulary = new XMLVocabulary();

		compositeVocabulary = new CompositeContractOntology();
		compositeVocabulary.add(builtinVocabulary, "net.java.treaty");
		compositeVocabulary.add(javaVocabulary, "net.java.treaty");
		compositeVocabulary.add(xmlVocabulary, "net.java.treaty");
	}

	@After
	public void tearDown() {

		compositeVocabulary = null;
		xmlVocabulary = null;
		javaVocabulary = null;
		builtinVocabulary = null;
	}

	@Test
	public void testSimpleScript() throws Exception {

		Contract contract =
				TreatyScript
						.fromString(
								"// A simple contract\n"
										+ "@author=jens\n"
										+ "on event:osgi.activated\n"
										+ "on event:osgi.updated\n"
										+ "consumer-resource Interface type=http://www.treaty.org/java#AbstractType name=net.java.treaty.eclipse.example.clock.DateFormatter\n"
										+ "supplier-resource Formatter type=http://www.treaty.org/java#InstantiableClass ref=serviceprovider/@class\n"
										+ "constraint Formatter http://www.treaty.org/java#implements Interface and mustexist Formatter\n"
										+ "onfailure action:osgi.uninstall.$bundle\n"
										+ "onsuccess action:osgi.log.debug.$bundle\n",
								javaVocabulary);

		assertEquals("jens", contract.getProperty("author"));

		assertEquals(2, contract.getTriggers().size());

		assertEquals(new URI("event:osgi.activated"), contract.getTriggers().get(0));
		assertEquals(new URI("event:osgi.updated"), contract.getTriggers().get(1));

		assertEquals(1, contract.getConsumerResources().size());

		Resource r0 = findResource(contract.getConsumerResources(), "Interface");
		assertNotNull(r0);
		assertEquals(new URI("http://www.treaty.org/java#AbstractType"), r0
				.getType());
		assertEquals("net.java.treaty.eclipse.example.clock.DateFormatter", r0
				.getName());

		assertEquals(1, contract.getSupplierResources().size());

		Resource r1 = findResource(contract.getSupplierResources(), "Formatter");
		assertNotNull(r1);
		assertEquals(new URI("http://www.treaty.org/java#InstantiableClass"), r1
				.getType());
		assertEquals("serviceprovider/@class", r1.getRef());

		assertEquals(1, contract.getOnVerificationFailsActions().size());
		assertEquals(new URI("action:osgi.uninstall.$bundle"), contract
				.getOnVerificationFailsActions().get(0));

		assertEquals(1, contract.getOnVerificationSucceedsActions().size());
		assertEquals(new URI("action:osgi.log.debug.$bundle"), contract
				.getOnVerificationSucceedsActions().get(0));

		ConjunctiveCondition conjunction =
				(ConjunctiveCondition) contract.getConstraints().get(0);
		assertEquals(2, conjunction.getParts().size());
		assertTrue(conjunction.getParts().get(0) instanceof RelationshipCondition);
		assertTrue(conjunction.getParts().get(1) instanceof ExistsCondition);

		RelationshipCondition relationshipCondition =
				(RelationshipCondition) conjunction.getParts().get(0);
		assertEquals(new URI("http://www.treaty.org/java#implements"),
				relationshipCondition.getRelationship());
		assertEquals("Formatter", relationshipCondition.getResource1().getId());
		assertEquals("Interface", relationshipCondition.getResource2().getId());

		ExistsCondition existsCondition =
				(ExistsCondition) conjunction.getParts().get(1);
		assertEquals("Formatter", existsCondition.getResource().getId());
	}

	@Test
	public void testUseOfExternalResources() throws Exception {

		Contract contract =
				TreatyScript
						.fromString(
								"external-resource toc_dtd type=http://www.treaty.org/xml#DTD name=contract_resources/org.eclipse.help.toc/toc.dtd\n"
										+ "supplier-resource toc type=http://www.treaty.org/xml#XMLInstance ref=/toc/@file\n"
										+ "constraint toc http://www.treaty.org/xml#instantiatesDTD toc_dtd\n",
								xmlVocabulary);

		assertEquals(0, contract.getTriggers().size());
		assertEquals(0, contract.getOnVerificationFailsActions().size());
		assertEquals(0, contract.getOnVerificationSucceedsActions().size());

		assertEquals(1, contract.getExternalResources().size());
		assertEquals(1, contract.getSupplierResources().size());
		assertEquals(0, contract.getConsumerResources().size());

		Resource r = contract.getExternalResources().iterator().next();
		assertEquals("toc_dtd", r.getId());
		assertEquals(new URI("http://www.treaty.org/xml#DTD"), r.getType());
		assertEquals("contract_resources/org.eclipse.help.toc/toc.dtd", r.getName());

		assertEquals(1, contract.getConstraints().size());
		assertTrue(contract.getConstraints().get(0) instanceof RelationshipCondition);

		RelationshipCondition rel =
				(RelationshipCondition) contract.getConstraints().get(0);
		assertEquals(new URI("http://www.treaty.org/xml#instantiatesDTD"), rel
				.getRelationship());
		assertEquals("toc", rel.getResource1().getId());
		assertEquals("toc_dtd", rel.getResource2().getId());
	}

	@Test(expected = TreatyException.class)
	public void testUseOfUndeclaredResource() throws Exception {

		TreatyScript
				.fromString(
						"constraint Formatter http://www.treaty.org/java#implements Interface and mustexist Formatter",
						javaVocabulary);
	}

	@Test
	public void testUseOfForwardSlashInResourceName() throws Exception {

		Contract contract =
				TreatyScript
						.fromString(
								"consumer-resource DateFormatDef type=http://www.treaty.org/xml#XMLSchema name=/dateformat.xsd",
								xmlVocabulary);

		Resource r0 =
				findResource(contract.getConsumerResources(), "DateFormatDef");
		assertNotNull(r0);
		assertEquals(new URI("http://www.treaty.org/xml#XMLSchema"), r0.getType());
		assertEquals("/dateformat.xsd", r0.getName());
	}

	@Test
	public void testUseOfMultilineConstraints() throws Exception {

		TreatyScript
				.fromString(
						"consumer-resource Interface type=http://www.treaty.org/java#AbstractType name=net.java.treaty.eclipse.example.clock.DateFormatter\n"
								+ "supplier-resource Formatter type=http://www.treaty.org/java#InstantiableClass ref=serviceprovider/@class\n"
								+ "constraint (\n"
								+ "    Formatter http://www.treaty.org/java#implements Interface\n"
								+ "    and Formatter http://www.treaty.org/java#implements Interface\n"
								+ "    and Formatter http://www.treaty.org/java#implements Interface\n"
								+ ")", javaVocabulary);
	}

	@Test
	public void testUseOfPropertyConstraintWithDecimalLiteral() throws Exception {

		Contract contract =
				TreatyScript
						.fromString(
								"consumer-resource FastViewRatio type=http://www.treaty.org/java#JavaType name=java.lang.Double\n"
										+ "constraint FastViewRatio <= 0.05", compositeVocabulary);

		Resource r0 =
				findResource(contract.getConsumerResources(), "FastViewRatio");
		assertNotNull(r0);
		assertEquals(new URI("http://www.treaty.org/java#JavaType"), r0.getType());
		assertEquals("java.lang.Double", r0.getName());

		assertEquals(1, contract.getConstraints().size());
		assertTrue(contract.getConstraints().get(0) instanceof PropertyCondition);

		PropertyCondition prop =
				(PropertyCondition) contract.getConstraints().get(0);
		assertEquals("FastViewRatio", prop.getResource().getId());
		assertEquals(new URI("http://www.treaty.org/builtin/#lte"), prop
				.getOperator());
		assertEquals(0.05, prop.getValue());
	}

	/**
	 * <p>
	 * Helper method to find a {@link Resource} in a {@link Collection} of
	 * {@link Resource}s.
	 * 
	 * @param resources
	 *          The {@link Collection} of the {@link Resource}s.
	 * @param id
	 *          The id of the {@link Resource} that shall be found.
	 * @return The found {@link Resource} or <code>null</code>.
	 */
	private Resource findResource(Collection<Resource> resources, String id) {

		Resource result;
		result = null;

		for (Resource resource : resources) {

			if (resource.getId().equals(id)) {
				result = resource;
				break;
			}
			// no else.
		}
		// end for.

		return result;
	}
}