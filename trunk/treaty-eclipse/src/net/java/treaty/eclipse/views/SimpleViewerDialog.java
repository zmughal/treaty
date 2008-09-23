/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.views;

import java.awt.Dimension;
import java.awt.Toolkit;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


/**
 * Abstract superclass for simple pop-up text dialogs.
 * @author Jens Dietrich
 */

public abstract class SimpleViewerDialog extends Dialog {
	private Text text = null;
	/**
	 * Constructor.
	 * @param parentShell
	 * @param url
	 */
	public SimpleViewerDialog(Shell parentShell) {
		super(parentShell);
		this.setShellStyle(SWT.RESIZE | SWT.TITLE);
	}
	/**
	 * Create the dialog area.
	 */
	protected Control createDialogArea(Composite parent) {	 
		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalAlignment = GridData.FILL;
		gd.verticalAlignment = GridData.FILL;				
		text = new Text(parent,SWT.MULTI | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL);
		text.setLayoutData(gd);		
		initText(text);		
		return text;
	}
	
	protected abstract void initText(Text text) ;
	
	protected abstract String getTitle();
	
	
	protected void configureShell(Shell newShell) {
	      super.configureShell(newShell);
	      newShell.setText(getTitle());
	      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	      newShell.setSize(Math.min(600,screen.width-200),Math.min(800,screen.height-200));
	      newShell.setLocation(100,100);
	}
	protected void createButtonsForButtonBar(Composite parent) {
		    createButton(parent, IDialogConstants.OK_ID, "Close", false);
	}
 
}
