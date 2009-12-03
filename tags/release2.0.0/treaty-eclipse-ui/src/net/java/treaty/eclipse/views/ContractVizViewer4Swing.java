/*
 * Copyright (C)  2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.views;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import net.java.treaty.Contract;
import net.java.treaty.viz.ContractViewer;

/**
 * Swing based contract viewer. More stable than the hybrid SWT.Swing viewer, in particular on Mac/Linux.
 * @author Jens Dietrich
 *
 */
public class ContractVizViewer4Swing extends ContractViewer {
	
	

	public ContractVizViewer4Swing(Frame owner, boolean modal) {
		super(owner, modal);
	}

	public static void show(Contract c) {
		// setting modal = true crashes application on Mac, seems to be SWT related problem
		ContractVizViewer4Swing viewer = new ContractVizViewer4Swing(new Frame(),false);
		viewer.setTitle("Contract Vizualisation (BETA)");
		viewer.setContract(c);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		viewer.setSize(Math.min(1024, screen.width - 200), Math.min(768,screen.height - 200));
		viewer.setLocation(100, 100);
		viewer.setVisible(true);
		
	}

	@Override
	protected void initContractView() {
		this.contractView = new ContractVizView();
	}
	
	
}
