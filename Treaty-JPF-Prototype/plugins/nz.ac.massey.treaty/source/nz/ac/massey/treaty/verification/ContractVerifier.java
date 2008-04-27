/*
 * Copyright (C) 2008 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package nz.ac.massey.treaty.verification;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;

import nz.ac.massey.treaty.Condition;
import nz.ac.massey.treaty.Contract;
import nz.ac.massey.treaty.ContractLogger;

import org.java.plugin.*;
import org.java.plugin.registry.*;

/**
 * Verifies contracts between extensions and extension points.
 * @author <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</A>
 * @version 0.1 <27/04/2008>
 * @since 0.1
 */

public class ContractVerifier implements ConditionVerifier, ResourceLoader {
	
	private static ContractVerifier instance = new ContractVerifier();
	
	private List<ContractVocabulary> vocabularies = new ArrayList<ContractVocabulary>();
	private Map<URI,ContractVocabulary> vocabulariesByPredicate = new HashMap<URI,ContractVocabulary>();
	private Map<URI,ResourceLoader> resourceLoadersByType = new HashMap<URI,ResourceLoader>();
	
	private ContractVerifier() {
		super();
	}
	
	
	/**
	 * Validates contracts between extensions and extension points.
	 * The results are written to a file validationresults.xml . 
	 * @param manager
	 */
	public void validate(PluginManager manager) {
		PluginRegistry registry = manager.getRegistry();
		Collection<PluginDescriptor> descriptors = registry.getPluginDescriptors();
		// build contracts
		Map<String,Contract> contracts = new HashMap<String,Contract>();
		for (PluginDescriptor descr:descriptors) {
			//System.out.println("Analysing plugin: " + descr.getId());
			//System.out.println(" - location: " + descr.getLocation());
			Collection<ExtensionPoint> xps = descr.getExtensionPoints();			
			for (ExtensionPoint xp:xps) {
				// building contract
				try {
					//System.out.println(" - extension point contract location: " + getContractLocation(descr,xp));
					URL contractLocation = getContractLocation(descr,xp);
					Contract contract = new ContractReader().read(xp,contractLocation.openStream());
					contract.setLocation(contractLocation.toString());
					contract.setExtensionPointId(xp.getId());
					contract.setPluginId(descr.getId());
					ContractLogger.LOGGER.info("Contract found: " + contractLocation);
					// new ContractPrinter().print(contract);
					contracts.put(xp.getId(),contract);
					// load extension point resources
					contract.loadExtensionPointResources(xp,manager.getPlugin(descr.getId()),this);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		// validate contracts
		Map<Contract,VerificationReport> results = new LinkedHashMap<Contract,VerificationReport>();
		for (PluginDescriptor descr:descriptors) {
			Plugin plugin;
			try {
				plugin = manager.getPlugin(descr.getId());

				Collection<Extension> xs = descr.getExtensions();
				for (Extension x:xs) {
					try {
						VerificationReport report = new VerificationReport();
						String xpid = x.getExtendedPointId();
						Contract contract = contracts.get(xpid);
						ContractLogger.LOGGER.info("Instantiating contract " + contract+" for extension in plugin " + plugin.getDescriptor().getId());
						Contract c = contract.bind(x,plugin,this);
						report.setContract(c);
						report.setExtensionId(x.getId());
						report.setExtensionPointId(xpid);
						boolean result = c.check(report,this);
						results.put(c,report);
						ContractLogger.LOGGER.info("Check contract, result: "+result+" - see generated report for details");
					} catch (Exception e) {
						ContractLogger.LOGGER.error("An exception occured while checking a contract ",e);
					}
					
				} 
				
			}
			catch (PluginLifecycleException e1) {
				e1.printStackTrace();
			}
		}
		
		// print results
		try {
			String url = new VerificationResultPrinter().print(results);
			ContractLogger.LOGGER.info("Verification report written to: " + url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public URL getContractLocation(PluginDescriptor plugin,ExtensionPoint xp) throws MalformedURLException {
		String url = plugin.getLocation().toString();
		url = url.replace("plugin.xml",xp.getId()+".contract");
		return new URL(url);
	}



	public static ContractVerifier getInstance() {
		return instance;
	}



	public void install(ContractVocabulary vocabulary) {
		vocabularies.add(vocabulary);
		for (URI uri:vocabulary.getContributedPredicates()) {
			// TODO check for clashes
			vocabulariesByPredicate.put(uri,vocabulary);
		}
		for (URI uri:vocabulary.getContributedTypes()) {
			// TODO check for clashes
			this.resourceLoadersByType.put(uri,vocabulary);
		}
	}



	public void check(Condition condition) throws VerificationException {
		URI predicate = condition.getRelationship();
		ContractVocabulary vocabulary = this.vocabulariesByPredicate.get(predicate);
		if (vocabulary==null)
			throw new VerificationException("Cannot validate condition, this predicate is not part of any registered vocabulary: " + predicate);
		else
			vocabulary.check(condition);
	}



	public Object load(URI type, String name, Plugin plugin) throws VerificationException {
		ResourceLoader loader = this.resourceLoadersByType.get(type);
		return loader.load(type,name,plugin);
	}



}
