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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


/**
 * Dialog to display raw contract definitions.
 * @author Jens Dietrich
 */

public class ViewContractSourceDialog extends SimpleViewerDialog {
	private URL contractURL = null;
	/**
	 * Constructor.
	 * @param parentShell
	 * @param url
	 */
	public ViewContractSourceDialog(Shell parentShell,URL contractURL) {
		super(parentShell);
		this.contractURL = contractURL;
	}
	@Override
	protected String getTitle() {
		return this.contractURL.toString();
	}
	@Override
	protected void initText(Text text) {
		try {
			String SEP = System.getProperty("line.separator");
			StringBuffer buf = new StringBuffer();
			InputStreamReader isr = new InputStreamReader(this.contractURL.openStream());
			BufferedReader bsr = new BufferedReader(isr);
			String line = null;
			while ((line=bsr.readLine())!=null) {
				buf.append(line);
				buf.append(SEP);
			}
			text.setText(buf.toString());
			bsr.close();
		} catch (IOException e) {
			text.setText("Error - cannot load contract");
			e.printStackTrace();
		}
	} 
}
