/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.vocabulary.xml.tests;

import java.net.URI;

import net.java.treaty.ExistsCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.VerificationException;
import net.java.treaty.eclipse.EclipseConnector;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.vocabulary.xml.Activator;
import net.java.treaty.eclipse.vocabulary.xml.XMLVocabulary;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.junit.*;
import org.osgi.framework.Bundle;
/**
 * Tests for Java vocabulary.
 * @author Jens Dietrich
 */
public class Tests {
	private XMLVocabulary VOC = null;
	private EclipsePlugin plugin = null;
	private EclipseConnector connector = null;
	@Before
	public void setUp() throws Exception {
		VOC = new XMLVocabulary();
		String id = Activator.PLUGIN_ID;
		Bundle bundle = org.eclipse.core.runtime.Platform.getBundle(id);
		plugin = new EclipsePlugin(bundle);
		IExtensionRegistry registry = org.eclipse.core.runtime.Platform.getExtensionRegistry();
		IExtension x = registry.getExtension("net.java.treaty.eclipse.vocabulary.xml");
		connector = new EclipseExtension(plugin,x);
	}

	@After
	public void tearDown() throws Exception {
		VOC = null;
		plugin = null;
		connector = null;
	}
	
	private Resource getResource(String type,String name) throws Exception {
		Resource r = new Resource();
		r.setType(new URI(type));
		r.setName(name);		
		String id = Activator.PLUGIN_ID;
		Bundle bundle = org.eclipse.core.runtime.Platform.getBundle(id);
		// note: load does not work, Bundle#getEntry does not load resources from fragments
		// but getResource does
		// this seems to contradict http://www.eclipsezone.com/eclipse/forums/t101557.rhtml
		Object value = bundle.getResource(name);
		r.setValue(value);
		return r;
	}
	
	@Test
	public void testTypes1() throws Exception {
		Resource r = this.getResource(VOC.SCHEMA,"/testdata/dateformat.xsd");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}
	@Test
	public void testTypes2() throws Exception {
		Resource r = this.getResource(VOC.INSTANCE,"testdata/dateformat.xsd");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}
	@Test(expected = VerificationException.class)
	public void testTypes3() throws Exception {
		Resource r = this.getResource(VOC.SCHEMA,"/testdata/dateformat1.xml");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}
	@Test
	public void testTypes4() throws Exception {
		Resource r = this.getResource(VOC.INSTANCE,"testdata/dateformat1.xml");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}
	@Test(expected = VerificationException.class)
	public void testTypes5() throws Exception {
		Resource r = this.getResource(VOC.SCHEMA,"/testdata/dateformat2.xml");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}
	@Test
	public void testTypes6() throws Exception {
		Resource r = this.getResource(VOC.INSTANCE,"testdata/dateformat2.xml");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}
	
	
	@Test(expected = VerificationException.class)
	public void testTypes7() throws Exception {
		Resource r = this.getResource(VOC.SCHEMA,"/testdata/dateformat3.xml");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}
	@Test(expected = VerificationException.class)
	public void testTypes8() throws Exception {
		Resource r = this.getResource(VOC.INSTANCE,"testdata/dateformat3.xml");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}
	
	@Test
	public void testTypes9() throws Exception {
		Resource r = this.getResource(VOC.DTD,"testdata/peoplelist.dtd");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}
	
	@Test(expected = VerificationException.class)
	public void testTypes10() throws Exception {
		Resource r = this.getResource(VOC.DTD,"testdata/peoplelist.nodtd");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}
	
	@Test
	public void testRelationship1() throws Exception {
		Resource r1 = this.getResource(VOC.SCHEMA,"/testdata/dateformat.xsd");
		Resource r2 = this.getResource(VOC.INSTANCE,"/testdata/dateformat1.xml");
		RelationshipCondition rel = new RelationshipCondition();
		rel.setResource1(r2);
		rel.setResource2(r1);
		rel.setRelationship(new URI(VOC.INSTANTIATES));
		VOC.check(rel);
	}
	
	@Test(expected = VerificationException.class)
	public void testRelationship2() throws Exception {
		Resource r1 = this.getResource(VOC.SCHEMA,"/testdata/dateformat.xsd");
		Resource r2 = this.getResource(VOC.INSTANCE,"/testdata/dateformat2.xml");
		RelationshipCondition rel = new RelationshipCondition();
		rel.setResource1(r2);
		rel.setResource2(r1);
		rel.setRelationship(new URI(VOC.INSTANTIATES));
		VOC.check(rel);
	}
	
	@Test(expected = VerificationException.class)
	public void testRelationship3() throws Exception {
		Resource r1 = this.getResource(VOC.SCHEMA,"/testdata/dateformat.xsd");
		Resource r2 = this.getResource(VOC.INSTANCE,"/testdata/dateformat3.xml");
		RelationshipCondition rel = new RelationshipCondition();
		rel.setResource1(r2);
		rel.setResource2(r1);
		rel.setRelationship(new URI(VOC.INSTANTIATES));
		VOC.check(rel);
	}
	
	@Test
	public void testRelationshipDTD1() throws Exception {
		Resource r1 = this.getResource(VOC.DTD,"/testdata/peoplelist.dtd");
		Resource r2 = this.getResource(VOC.INSTANCE,"/testdata/peoplelist1.xml");
		RelationshipCondition rel = new RelationshipCondition();
		rel.setResource1(r2);
		rel.setResource2(r1);
		rel.setRelationship(new URI(VOC.INSTANTIATES_DTD));
		VOC.check(rel);
	}
	@Test(expected = VerificationException.class)
	public void testRelationshipDTD2() throws Exception {
		Resource r1 = this.getResource(VOC.DTD,"/testdata/peoplelist.dtd");
		Resource r2 = this.getResource(VOC.INSTANCE,"/testdata/dateformat3.xml");
		RelationshipCondition rel = new RelationshipCondition();
		rel.setResource1(r2);
		rel.setResource2(r1);
		rel.setRelationship(new URI(VOC.INSTANTIATES_DTD));
		VOC.check(rel);
	}
	
}
