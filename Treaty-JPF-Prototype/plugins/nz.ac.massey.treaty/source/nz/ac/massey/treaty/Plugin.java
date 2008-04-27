/*
 * Copyright (C) 2008 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package nz.ac.massey.treaty;

import java.util.Iterator;

import nz.ac.massey.treaty.verification.ContractVerifier;
import nz.ac.massey.treaty.verification.ContractVocabulary;

import org.java.plugin.boot.Application;
import org.java.plugin.registry.Extension;
import org.java.plugin.registry.ExtensionPoint;
import org.java.plugin.registry.Extension.Parameter;
import org.java.plugin.util.ExtendedProperties;


/**
 * Activator for this plugin.
 * @author <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</A>
 * @version 0.1 <27/04/2008>
 * @since 0.1
 */


public final class Plugin extends org.java.plugin.Plugin implements Application {
	
    /**
     * This plug-in ID.
     */
    public static final String PLUGIN_ID = "nz.ac.massey.treaty";

    /**
     * @see org.java.plugin.Plugin#doStart()
     */
    protected void doStart() throws Exception {
    	
    	//BasicConfigurator.configure();
    	
    	// load vocabulary to validate
        ExtensionPoint xp =  getManager().getRegistry().getExtensionPoint(getDescriptor().getId(), "verification");
        for (Iterator it = xp.getConnectedExtensions().iterator();it.hasNext();) {
            Extension ext = (Extension) it.next();            
            Parameter nameParam = ext.getParameter("name");
            
            try {
	            getManager().activatePlugin(ext.getDeclaringPluginDescriptor().getId());
	            ClassLoader classLoader = getManager().getPluginClassLoader(ext.getDeclaringPluginDescriptor());
	            Class clazz = classLoader.loadClass(ext.getParameter("class").valueAsString());
	            ContractVocabulary vocabulary = (ContractVocabulary) clazz.newInstance();
	            ContractLogger.LOGGER.info("Contract vocabulary loaded: "+nameParam.rawValue());
	            ContractVerifier.getInstance().install(vocabulary);
            }
            catch (Exception x) {
            	x.printStackTrace();
            }
        }
 
    }

    /**
     * @see org.java.plugin.Plugin#doStop()
     */
    protected void doStop() throws Exception {
        // no-op
    }

    /**
     * @see org.java.plugin.boot.ApplicationPlugin#initApplication(
     *      ExtendedProperties, String[])
     */
    protected Application initApplication(final ExtendedProperties config,final String[] args) throws Exception {
      
        return this;
    }

    /**
     * @see org.java.plugin.boot.Application#startApplication()
     */
    public void startApplication() {
    	// load vocabulary to validate
        ExtensionPoint xp =  getManager().getRegistry().getExtensionPoint(getDescriptor().getId(), "validation");
        for (Iterator it = xp.getConnectedExtensions().iterator();it.hasNext();) {
            Extension ext = (Extension) it.next();            
            Parameter nameParam = ext.getParameter("name");
            
            try {
	            getManager().activatePlugin(ext.getDeclaringPluginDescriptor().getId());
	            ClassLoader classLoader = getManager().getPluginClassLoader(ext.getDeclaringPluginDescriptor());
	            Class clazz = classLoader.loadClass(ext.getParameter("class").valueAsString());
	            ContractVocabulary vocabulary = (ContractVocabulary) clazz.newInstance();
	            System.out.println("Contract vocabulary loaded: "+nameParam.rawValue());
	            ContractVerifier.getInstance().install(vocabulary);
            }
            catch (Exception x) {
            	x.printStackTrace();
            }
        }
 
    }
    

}
