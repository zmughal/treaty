/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.vocabulary.junit.tests;

import java.net.URI;

import net.java.treaty.ExistsCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.VerificationException;
import net.java.treaty.eclipse.EclipseConnector;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.EclipseVerifier;
import net.java.treaty.eclipse.vocabulary.junit.Activator;
import net.java.treaty.eclipse.vocabulary.junit.JUnitVocabulary;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.junit.*;
import org.osgi.framework.Bundle;

/**
 * Tests for Java vocabulary.
 * 
 * @author Jens Dietrich
 */
public class Tests {

	private JUnitVocabulary VOC = null;
	private EclipsePlugin plugin = null;
	private EclipseConnector connector = null;

	@Before
	public void setUp() throws Exception {

		VOC = JUnitVocabulary.INSTANCE;
		String id = Activator.PLUGIN_ID;
		Bundle bundle = org.eclipse.core.runtime.Platform.getBundle(id);
		plugin = new EclipsePlugin(bundle);
		IExtensionRegistry registry =
				org.eclipse.core.runtime.Platform.getExtensionRegistry();
		IExtension x =
				registry.getExtension("net.java.treaty.eclipse.vocabulary.junit");
		connector = new EclipseExtension(plugin, x);
	}

	@After
	public void tearDown() throws Exception {

		VOC = null;
		plugin = null;
		connector = null;
	}

	private Resource getResource(String type, String clazz) throws Exception {

		Resource r = new Resource();
		r.setType(new URI(type));
		r.setName(clazz);
		// resources must be loaded using the verifier - Java instantiable classes
		// are loaded from another vocabulary!
		Object value =
				new EclipseVerifier().load(r.getType(), r.getName(), connector);
		r.setValue(value);
		return r;
	}

	@Test
	public void testTypes1() throws Exception {

		Resource r =
				this.getResource(JUnitVocabulary.TESTCASE,
						DateFormatterFunctionalTests.class.getName());
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}

	@Test(expected = VerificationException.class)
	// does not have the right constructors
	public void testTypes2() throws Exception {

		Resource r =
				this.getResource(JUnitVocabulary.TESTCASE, Tests.class.getName());
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}

	@Test(expected = VerificationException.class)
	// does not have the right constructors
	public void testTypes3() throws Exception {

		Resource r =
				this.getResource(JUnitVocabulary.TESTCASE, EmptyTestCase.class
						.getName());
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}

	@Test
	public void testRelationship1() throws Exception {

		Resource r1 =
				this.getResource(JUnitVocabulary.TESTCASE,
						DateFormatterFunctionalTests.class.getName());
		Resource r2 =
				this.getResource(JUnitVocabulary.INSTANTIABLE_CLASS,
						ShortDateFormatter.class.getName());
		RelationshipCondition rel = new RelationshipCondition();
		rel.setResource1(r2);
		rel.setResource2(r1);
		rel.setRelationship(new URI(JUnitVocabulary.VERIFIES));
		VOC.check(rel);
	}

	@Test(expected = VerificationException.class)
	public void testRelationship2() throws Exception {

		Resource r1 =
				this.getResource(JUnitVocabulary.TESTCASE,
						DateFormatterFunctionalTests.class.getName());
		Resource r2 =
				this.getResource(JUnitVocabulary.INSTANTIABLE_CLASS,
						TooShortDateFormatter.class.getName());
		RelationshipCondition rel = new RelationshipCondition();
		rel.setResource1(r2);
		rel.setResource2(r1);
		rel.setRelationship(new URI(JUnitVocabulary.VERIFIES));
		VOC.check(rel);
	}
}