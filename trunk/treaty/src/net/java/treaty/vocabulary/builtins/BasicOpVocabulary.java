/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.vocabulary.builtins;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import net.java.treaty.Connector;
import net.java.treaty.ContractVocabulary;
import net.java.treaty.ExistsCondition;
import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.ResourceLoaderException;
import net.java.treaty.TreatyException;
import net.java.treaty.VerificationException;

/**
 * Vocabulary for basic operations.
 * @author jens
 */
public class BasicOpVocabulary implements ContractVocabulary {

	private URI default_domain,default_range;

	private boolean definesProperty(URI uri) {
		return BuiltInOperators.INSTANCE.getInstance(uri)!=null;
	}
	@Override
	public boolean checkDomain(URI relationshipOrProperty, URI domain) throws TreatyException {
		return this.definesProperty(relationshipOrProperty); // does not matter if it is defined here
	}

	@Override
	public boolean checkRange(URI relationshipOrProperty, URI range) throws TreatyException {
		return this.definesProperty(relationshipOrProperty); // does not matter if it is defined here
	}

	@Override
	public URI getDomain(URI relationshipOrProperty) throws TreatyException {
		try {
			if (default_domain==null) {
				// everything goes, types are not enforced for built-in ops
				default_domain = new URI("http://www.w3.org/2002/07/owl#");
			}		
			return default_domain;
		} catch (URISyntaxException e) {
			throw new TreatyException();
		} 
	}

	@Override
	public Collection<URI> getProperties() throws TreatyException {
		return BuiltInOperators.INSTANCE.getOpIds();
	}

	@Override
	public URI getRange(URI relationshipOrProperty) throws TreatyException {
		try {
			if (default_range==null) {
				// everything goes, types are not enforced for built-in ops
				default_range = new URI("http://www.w3.org/2002/07/owl#");
			}		
			return default_range;
		} catch (URISyntaxException e) {
			throw new TreatyException();
		} 
	}

	@Override
	public Collection<URI> getRelationships() throws TreatyException {
		return Collections.EMPTY_SET;
	}

	@Override
	public Collection<URI> getSubProperties(URI relationshipOrProperty) throws TreatyException {
		return Collections.EMPTY_SET;
	}

	@Override
	public Collection<URI> getSubtypes(URI type) throws TreatyException {
		return Collections.EMPTY_SET;
	}

	@Override
	public Collection<URI> getSuperProperties(URI relationshipOrProperty) throws TreatyException {
		return Collections.EMPTY_SET;
	}

	@Override
	public Collection<URI> getSupertypes(URI type) throws TreatyException {
		return Collections.EMPTY_SET;
	}

	@Override
	public Collection<URI> getTypes() throws TreatyException {
		return Collections.EMPTY_SET;
	}

	@Override
	public void check(RelationshipCondition relationshipCondition) 	throws VerificationException {
		throw new VerificationException("The basic operations vocabularies does not define relationships");
	}

	@Override
	public void check(PropertyCondition condition) throws VerificationException {
		URI uri = condition.getOperator();
		BuiltInOperator op = BuiltInOperators.INSTANCE.getInstance(uri);
		if (op==null)
			throw new VerificationException("Cannot check condition, this is not a built in opperation: "+op);
		Object res = condition.getResource().getValue();
		if (!op.compare(res, condition.getValue()) )
			throw new VerificationException("verification failed for " + condition);
	}

	@Override
	public void check(ExistsCondition relationshipCondition) throws VerificationException {
		throw new VerificationException("The basic operations vocabularies does not define types");
	}

	@Override
	public Object load(URI type, String name, Connector connector) throws ResourceLoaderException {
		throw new ResourceLoaderException("The basic operations vocabularies does not define types");
	}

}
