/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.viz.example;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import net.java.treaty.Resource;
import net.java.treaty.viz.ContractView;

/**
 * Customised contract view showing how some layout options can be changed
 * in subclasses.
 * @author jens dietrich
 */

public class CustomContractView extends ContractView {

	@Override
	protected Icon getIcon(Resource r) {
		boolean var = r.getName()==null;
		String t = r.getType().toString();
		if (t.equals("http://www.treaty.org/junit#TestCase")) 
			return loadIcon(var?"junit_var.gif":"junit.gif");
		else if (t.equals("http://www.treaty.org/java#InstantiableClass")) 
			return loadIcon(var?"class_var.gif":"class.gif");
		else if (t.equals("http://www.treaty.org/java#AbstractType")) 
			return loadIcon(var?"class_var.gif":"class.gif");
		else if (t.equals("http://www.treaty.org/xml#XMLSchema")) 
			return loadIcon(var?"xsd_var.gif":"xsd.gif");
		else if (t.equals("http://www.treaty.org/xml#XMLInstance")) 
			return loadIcon(var?"xml_var.gif":"xml.gif");
		else if (t.equals("http://www.treaty.org/xml#DTD")) 
			return loadIcon(var?"dtd_var.gif":"dtd.gif");
		return super.getIcon(r);
	}
	
	private Icon loadIcon(String name) {
		return new ImageIcon("example-icons/"+ name);
	}
	

}
