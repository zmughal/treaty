/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package test.net.java.treaty.vocabulary.builtins.xml;

import java.net.URI;

import net.java.treaty.ExistsCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.VerificationException;
import net.java.treaty.vocabulary.builtins.xml.XMLVocabulary;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Tests for the {@link XMLVocabulary}.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class XMLVocabularyVerificationTests {

	private XMLVocabulary VOC = null;

	@Before
	public void setUp() throws Exception {
		VOC = new XMLVocabulary();
	}

	@After
	public void tearDown() throws Exception {
		VOC = null;
	}

	private Resource getResource(String type, String name) throws Exception {
		Resource r = new Resource();
		r.setType(new URI(type));
		r.setName(name);
		Object value = XMLVocabularyVerificationTests.class.getResource("/test/net/java/treaty/vocabulary/builtins/xml"+name);
		r.setValue(value);
		return r;
	}

	@Test
	public void testTypes1() throws Exception {
		Resource r = this.getResource(XMLVocabulary.TYPE_NAME_XML_SCHEMA, "/testdata/dateformat.xsd");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}

	@Test
	public void testTypes2() throws Exception {
		Resource r = this.getResource(XMLVocabulary.TYPE_NAME_XML_INSTANCE, "/testdata/dateformat.xsd");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}

	@Test(expected = VerificationException.class)
	public void testTypes3() throws Exception {
		Resource r = this.getResource(XMLVocabulary.TYPE_NAME_XML_SCHEMA, "/testdata/dateformat1.xml");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}

	@Test
	public void testTypes4() throws Exception {
		Resource r = this.getResource(XMLVocabulary.TYPE_NAME_XML_INSTANCE, "/testdata/dateformat1.xml");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}

	@Test(expected = VerificationException.class)
	public void testTypes5() throws Exception {
		Resource r = this.getResource(XMLVocabulary.TYPE_NAME_XML_SCHEMA, "/testdata/dateformat2.xml");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}

	@Test
	public void testTypes6() throws Exception {
		Resource r = this.getResource(XMLVocabulary.TYPE_NAME_XML_INSTANCE, "/testdata/dateformat2.xml");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}

	@Test(expected = VerificationException.class)
	public void testTypes7() throws Exception {
		Resource r = this.getResource(XMLVocabulary.TYPE_NAME_XML_SCHEMA, "/testdata/dateformat3.xml");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}

	@Test(expected = VerificationException.class)
	public void testTypes8() throws Exception {
		Resource r = this.getResource(XMLVocabulary.TYPE_NAME_XML_INSTANCE, "testdata/dateformat3.xml");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}

	@Test
	public void testTypes9() throws Exception {
		Resource r = this.getResource(XMLVocabulary.TYPE_NAME_DTD, "/testdata/peoplelist.dtd");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}

	@Test(expected = VerificationException.class)
	public void testTypes10() throws Exception {
		Resource r = this.getResource(XMLVocabulary.TYPE_NAME_DTD, "/testdata/peoplelist.nodtd");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}

	@Test
	public void testRelationship1() throws Exception {
		Resource r1 = this.getResource(XMLVocabulary.TYPE_NAME_XML_SCHEMA, "/testdata/dateformat.xsd");
		Resource r2 = this.getResource(XMLVocabulary.TYPE_NAME_XML_INSTANCE,"/testdata/dateformat1.xml");
		RelationshipCondition rel = new RelationshipCondition();
		rel.setResource1(r2);
		rel.setResource2(r1);
		rel.setRelationship(new URI(XMLVocabulary.RELATIONSHIP_NAME_INSTANTIATES_XML_SCHEMA));
		VOC.check(rel);
	}

	@Test(expected = VerificationException.class)
	public void testRelationship2() throws Exception {

		Resource r1 = this.getResource(XMLVocabulary.TYPE_NAME_XML_SCHEMA, "/testdata/dateformat.xsd");
		Resource r2 = this.getResource(XMLVocabulary.TYPE_NAME_XML_INSTANCE,"/testdata/dateformat2.xml");
		RelationshipCondition rel = new RelationshipCondition();
		rel.setResource1(r2);
		rel.setResource2(r1);
		rel.setRelationship(new URI(XMLVocabulary.RELATIONSHIP_NAME_INSTANTIATES_XML_SCHEMA));
		VOC.check(rel);
	}

	@Test(expected = VerificationException.class)
	public void testRelationship3() throws Exception {
		Resource r1 = this.getResource(XMLVocabulary.TYPE_NAME_XML_SCHEMA, "/testdata/dateformat.xsd");
		Resource r2 = this.getResource(XMLVocabulary.TYPE_NAME_XML_INSTANCE,"/testdata/dateformat3.xml");
		RelationshipCondition rel = new RelationshipCondition();
		rel.setResource1(r2);
		rel.setResource2(r1);
		rel.setRelationship(new URI(XMLVocabulary.RELATIONSHIP_NAME_INSTANTIATES_XML_SCHEMA));
		VOC.check(rel);
	}

	@Test
	public void testRelationshipDTD1() throws Exception {
		Resource r1 = this.getResource(XMLVocabulary.TYPE_NAME_DTD, "/testdata/peoplelist.dtd");
		Resource r2 = this.getResource(XMLVocabulary.TYPE_NAME_XML_INSTANCE,"/testdata/peoplelist1.xml");
		RelationshipCondition rel = new RelationshipCondition();
		rel.setResource1(r2);
		rel.setResource2(r1);
		rel.setRelationship(new URI(XMLVocabulary.RELATIONSHIP_NAME_INSTANTIATES_DTD));
		VOC.check(rel);
	}

	@Test(expected = VerificationException.class)
	public void testRelationshipDTD2() throws Exception {
		Resource r1 = this.getResource(XMLVocabulary.TYPE_NAME_DTD, "/testdata/peoplelist.dtd");
		Resource r2 = this.getResource(XMLVocabulary.TYPE_NAME_XML_INSTANCE,"/testdata/dateformat3.xml");
		RelationshipCondition rel = new RelationshipCondition();
		rel.setResource1(r2);
		rel.setResource2(r1);
		rel.setRelationship(new URI(XMLVocabulary.RELATIONSHIP_NAME_INSTANTIATES_DTD));
		VOC.check(rel);
	}
}