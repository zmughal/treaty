/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.vocabulary.java.tests;

import java.net.URI;

import net.java.treaty.ExistsCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.VerificationException;
import net.java.treaty.Verifier;
import net.java.treaty.eclipse.EclipseConnector;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.EclipseVerifier;
import net.java.treaty.eclipse.vocabulary.java.Activator;
import net.java.treaty.eclipse.vocabulary.java.JavaVocabulary;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.junit.*;
import org.osgi.framework.Bundle;
/**
 * Tests for Java vocabulary.
 * @author Jens Dietrich
 */
public class Tests {
	private JavaVocabulary VOC = null;
	private EclipsePlugin plugin = null;
	private EclipseConnector connector = null;
	@Before
	public void setUp() throws Exception {
		VOC = new JavaVocabulary();
		String id = Activator.PLUGIN_ID;
		Bundle bundle = org.eclipse.core.runtime.Platform.getBundle(id);
		plugin = new EclipsePlugin(bundle);
		IExtensionRegistry registry = org.eclipse.core.runtime.Platform.getExtensionRegistry();
		IExtension x = registry.getExtension("net.java.treaty.eclipse.vocabulary.java");
		connector = new EclipseExtension(plugin,x);
	}

	@After
	public void tearDown() throws Exception {
		VOC = null;
		plugin = null;
		connector = null;
	}
	
	private Resource getResource(String type,String clazz) throws Exception {
		Resource r = new Resource();
		r.setType(new URI(type));
		r.setName(clazz);
		Object value = VOC.load(r.getType(),r.getName(), connector);
		r.setValue(value);
		return r;
	}
	
	@Test
	public void testTypes1() throws Exception {
		Resource r = this.getResource(VOC.ABSTRACT_TYPE, Class1.class.getName());
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}
	@Test(expected = VerificationException.class)
	public void testTypes2() throws Exception {
		Resource r = this.getResource(VOC.INSTANTIABLE_CLASS, Class1.class.getName());
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}
	@Test
	public void testTypes3() throws Exception {
		Resource r = this.getResource(VOC.ABSTRACT_TYPE, Interface1.class.getName());
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}
	@Test(expected = VerificationException.class)
	public void testTypes4() throws Exception {
		Resource r = this.getResource(VOC.INSTANTIABLE_CLASS, Interface1.class.getName());
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}
	@Test(expected = VerificationException.class)
	public void testTypes5() throws Exception {
		Resource r = this.getResource(VOC.ABSTRACT_TYPE, Class2.class.getName());
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}
	@Test(expected = VerificationException.class)
	public void testTypes6() throws Exception {
		Resource r = this.getResource(VOC.INSTANTIABLE_CLASS, Class2.class.getName());
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}
	@Test(expected = VerificationException.class)
	public void testTypes7() throws Exception {
		Resource r = this.getResource(VOC.ABSTRACT_TYPE, Class3.class.getName());
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}
	@Test(expected = VerificationException.class)
	public void testTypes8() throws Exception {
		Resource r = this.getResource(VOC.INSTANTIABLE_CLASS, Class3.class.getName());
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}
	@Test(expected = VerificationException.class)
	public void testTypes9() throws Exception {
		Resource r = this.getResource(VOC.ABSTRACT_TYPE, Class4.class.getName());
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}
	@Test
	public void testTypes10() throws Exception {
		Resource r = this.getResource(VOC.INSTANTIABLE_CLASS, Class4.class.getName());
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}
	@Test
	public void testRelationship1() throws Exception {
		Resource r1 = this.getResource(VOC.INSTANTIABLE_CLASS, Class4.class.getName());
		Resource r2 = this.getResource(VOC.ABSTRACT_TYPE, Interface1.class.getName());
		RelationshipCondition rel = new RelationshipCondition();
		rel.setResource1(r1);
		rel.setResource2(r2);
		rel.setRelationship(new URI(VOC.IMPLEMENTS));
		VOC.check(rel);
	}
	@Test(expected = VerificationException.class)
	public void testRelationship2() throws Exception {
		Resource r1 = this.getResource(VOC.INSTANTIABLE_CLASS, Class4.class.getName());
		Resource r2 = this.getResource(VOC.ABSTRACT_TYPE, Class1.class.getName());
		RelationshipCondition rel = new RelationshipCondition();
		rel.setResource1(r1);
		rel.setResource2(r2);
		rel.setRelationship(new URI(VOC.IMPLEMENTS));
		VOC.check(rel);
	}
	@Test
	public void testRelationship3() throws Exception {
		Resource r1 = this.getResource(VOC.INSTANTIABLE_CLASS, Class5.class.getName());
		Resource r2 = this.getResource(VOC.ABSTRACT_TYPE, Class1.class.getName());
		RelationshipCondition rel = new RelationshipCondition();
		rel.setResource1(r1);
		rel.setResource2(r2);
		rel.setRelationship(new URI(VOC.IMPLEMENTS));
		VOC.check(rel);
	}
	
}
