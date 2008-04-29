/*
 * Copyright (C) 2008 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package nz.ac.massey.treaty.example;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import nz.ac.massey.treaty.verification.ContractVerifier;

import org.java.plugin.boot.Application;
import org.java.plugin.boot.ApplicationPlugin;
import org.java.plugin.boot.Boot;
import org.java.plugin.registry.Extension;
import org.java.plugin.registry.ExtensionPoint;
import org.java.plugin.registry.Extension.Parameter;
import org.java.plugin.util.ExtendedProperties;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * Activator for this plugin.
 * @author <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</A>
 * @version 0.1 <27/04/2008>
 * @since 0.1
 */


public final class Plugin extends ApplicationPlugin implements Application {
    /**
     * This plug-in ID.
     */
    public static final String PLUGIN_ID = "nz.ac.massey.treaty.example.example";
    
    private JFrame frame;    


    /**
     * @see org.java.plugin.Plugin#doStart()
     */
    protected void doStart() throws Exception {
        // no-op
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
    	
    	// ui
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    protected void createAndShowGUI() {
        frame = new JFrame("Treaty Example");
        
        final List<DateFormatter> formatters = new ArrayList<DateFormatter>();
        final List<String> names = new ArrayList<String>();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosed(final WindowEvent e) {
                exit();
            }
        });
        ExtensionPoint xp =  getManager().getRegistry().getExtensionPoint(getDescriptor().getId(), "dateformatter");
        for (Iterator it = xp.getConnectedExtensions().iterator();it.hasNext();) {
            Extension ext = (Extension) it.next();            
            Parameter nameParam = ext.getParameter("name");
            
            try {
	            getManager().activatePlugin(ext.getDeclaringPluginDescriptor().getId());
	            ClassLoader classLoader = getManager().getPluginClassLoader(ext.getDeclaringPluginDescriptor());
	            Parameter param = ext.getParameter("class");
	            if (param!=null) {
	            	Class clazz = classLoader.loadClass(param.valueAsString());
	            	DateFormatter formatter = (DateFormatter) clazz.newInstance();
	            	formatters.add(formatter);
	            	names.add(nameParam.valueAsString());
	            }
	            else if ((param=ext.getParameter("formatdef"))!=null) {
	            	// load format string from xml file 
	            	try {
	            		String xml = param.rawValue(); // the name of the xml doc
	            		String path = ext.getDeclaringPluginDescriptor().getLocation().toString().replace("plugin.xml",xml);
	            		URL url = new URL(path);
	            		InputStream in = url.openStream();
	            		Document doc = new SAXBuilder().build(in);
	            		Element root = doc.getRootElement();
	            		String format = root.getAttributeValue("formatstring");
	            		in.close();
		            	DateFormatter formatter = new ConfigurableDateFormatter(format);
		            	formatters.add(formatter);
		            	names.add(nameParam.valueAsString());        		
	            	}
	            	catch (Exception x) {
	            		x.printStackTrace();
	            	}
	            }
	            else 
	            	System.out.println("plugin does neither provide a class nor format definition");
            }
            catch (Exception x) {
            	x.printStackTrace();
            }
        }

        // build UI
        JPanel pane = new JPanel(new BorderLayout());
        pane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        final JTextField label = new JTextField("The date is " + new Date().toString());
        label.setEditable(false);
        final JList list = new JList(names.toArray());
        list.setBorder(BorderFactory.createTitledBorder("Select format:"));
        list.addListSelectionListener(
        	new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent e) {
					DateFormatter formatter = formatters.get(list.getSelectedIndex());
					if (formatter==null)
						label.setText("?");
					else
						label.setText("The date is " + formatter.format(new Date()));
				}        		
        	}	
        );

        pane.add(new JScrollPane(list),BorderLayout.CENTER);
        pane.add(label,BorderLayout.SOUTH);
        
        // toolbar
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.add(
        	new AbstractAction("exit") {
				public void actionPerformed(ActionEvent e) {
					exit();
				}        		
        	}	
        );
        JButton but = toolbar.add(
            new AbstractAction("verify system integrity") {
				public void actionPerformed(ActionEvent e) {
					ContractVerifier.getInstance().verify(getManager());
				}        		
        	}	
        );       
        but.setToolTipText("Check all component contracts in current configuration");
        
        frame.setContentPane(pane);
        pane.add(toolbar,BorderLayout.NORTH);
        frame.setSize(300,200);
        frame.setLocation(200,200);
        frame.setVisible(true);
    }

    
    private void exit() {
        try {
            JOptionPane.getRootFrame().dispose();
            Boot.stopApplication(Plugin.this);
        } catch (Exception ex) {
            // ignore
        }
        System.exit(0);
    }
}
