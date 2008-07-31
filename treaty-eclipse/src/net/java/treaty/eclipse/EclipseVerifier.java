/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse;

/**
 * Verifier for eclipse.
 * @author Jens Dietrich
 */

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.verification.Verifier;
import net.java.treaty.verification.ContractVocabulary;
import net.java.treaty.verification.VerificationException;
import net.java.treaty.verification.VerificationResult;

public class EclipseVerifier implements Verifier {
	private List<ContractVocabulary> vocContributions = null;
	public EclipseVerifier () {
		super();
		loadVocabularyContributions();
	}
	private void loadVocabularyContributions() {
		vocContributions = new ArrayList<ContractVocabulary>();
		IExtensionPoint xp = Platform.getExtensionRegistry().getExtensionPoint("net.java.treaty.eclipse.vocabulary");
		for (IExtension x:xp.getExtensions()) {
			IConfigurationElement[] attributes = x.getConfigurationElements();	
			String pluginId = x.getContributor().getName();
			for (int j=0;j<attributes.length;j++) {
				IConfigurationElement p = attributes[j];
				String clazz = p.getAttribute("class");
				try {
					Class c = Platform.getBundle(pluginId).loadClass(clazz);
					vocContributions.add((ContractVocabulary)c.newInstance());
				}
				catch (Exception ex) {
					// FIXME
					ex.printStackTrace();
				}
			}
		}
	}

	
	@Override
	public void check(RelationshipCondition condition) throws VerificationException {
		URI uri = condition.getRelationship();
		try {
			ContractVocabulary voc = this.findVocabulary(uri);
			voc.check(condition); 
			System.out.println("checked ok: " + condition);
		}
		catch (VerificationException x) {
			condition.addProperty(Constants.VERIFICATION_RESULT,VerificationResult.FAILURE);
			condition.addProperty(Constants.VERIFICATION_EXCEPTION, x);
			throw x;
		}
		return;
	}
	
	@Override
	public void check(PropertyCondition condition) throws VerificationException {
		URI uri = condition.getProperty();
		try {
			ContractVocabulary voc = this.findVocabulary(uri);
			voc.check(condition); 
			System.out.println("checked ok: " + condition);
		}
		catch (VerificationException x) {
			condition.addProperty(Constants.VERIFICATION_RESULT,VerificationResult.FAILURE);
			condition.addProperty(Constants.VERIFICATION_EXCEPTION, x);
			throw x;
		}
		return;
	}
	
	private ContractVocabulary findVocabulary(URI uri)  throws VerificationException {	
		for (ContractVocabulary voc:vocContributions) {
			if (voc.getContributedPredicates().contains(uri)) {
				return voc;
			}
		}
		throw new VerificationException("No vocabulary found to check condition with predicate " + uri);
	}
	


}
