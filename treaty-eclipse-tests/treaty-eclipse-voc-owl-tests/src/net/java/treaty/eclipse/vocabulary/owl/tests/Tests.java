/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.vocabulary.owl.tests;

import java.net.URI;
import net.java.treaty.ExistsCondition;
import net.java.treaty.Resource;
import net.java.treaty.VerificationException;
import net.java.treaty.eclipse.EclipseConnector;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.vocabulary.owl.Activator;
import net.java.treaty.eclipse.vocabulary.owl.OWLVocabulary;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.junit.*;
import org.osgi.framework.Bundle;

/**
 * Tests for Java vocabulary.
 * 
 * @author Jens Dietrich
 * @deprecated The {@link OWLVocabulary} plug-in and its tests have been
 *             deprecated since the Treaty core now supports a built-in OWL
 *             vocabulary itself.
 */
@Deprecated
public class Tests {

	private OWLVocabulary VOC = null;
	private EclipsePlugin plugin = null;
	private EclipseConnector connector = null;

	@Before
	public void setUp() throws Exception {

		VOC = new OWLVocabulary();
		String id = Activator.PLUGIN_ID;
		Bundle bundle = org.eclipse.core.runtime.Platform.getBundle(id);
		plugin = new EclipsePlugin(bundle);
		IExtensionRegistry registry =
				org.eclipse.core.runtime.Platform.getExtensionRegistry();
		IExtension x =
				registry.getExtension("net.java.treaty.eclipse.vocabulary.owl");
		connector = new EclipseExtension(plugin, x);
	}

	@After
	public void tearDown() throws Exception {

		VOC = null;
		plugin = null;
		connector = null;
	}

	private Resource getResource(String type, String name) throws Exception {

		Resource r = new Resource();
		r.setType(new URI(type));
		r.setName(name);
		String id = Activator.PLUGIN_ID;
		Bundle bundle = org.eclipse.core.runtime.Platform.getBundle(id);
		// note: load does not work, Bundle#getEntry does not load resources from
		// fragments
		// but getResource does
		// this seems to contradict
		// http://www.eclipsezone.com/eclipse/forums/t101557.rhtml
		Object value = bundle.getResource(name);
		r.setValue(value);
		return r;
	}

	@Test
	public void testTypes1() throws Exception {

		Resource r = this.getResource(VOC.ONTOLOGY, "/testdata/owl.owl");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}

	@Test(expected = VerificationException.class)
	public void testTypes2() throws Exception {

		Resource r = this.getResource(VOC.ONTOLOGY, "/testdata/owl-i-1.owl");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}

	@Test(expected = VerificationException.class)
	public void testTypes3() throws Exception {

		Resource r = this.getResource(VOC.ONTOLOGY, "/testdata/doesnotexist.owl");
		ExistsCondition c = new ExistsCondition();
		c.setResource(r);
		VOC.check(c);
	}

}
