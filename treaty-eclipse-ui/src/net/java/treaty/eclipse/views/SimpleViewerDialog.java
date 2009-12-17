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
 * <p>
 * Abstract superclass for simple pop-up text dialogs.
 * </p>
 * 
 * @author Jens Dietrich
 */
public abstract class SimpleViewerDialog extends Dialog {

	/** The {@link Text} of this {@link SimpleViewerDialog}. */
	private Text text = null;

	/**
	 * <p>
	 * Creares a new {@link SimpleViewerDialog}
	 * </p>
	 * 
	 * @param parentShell
	 *          The parent {@link Shell} of this {@link SimpleViewerDialog}.
	 */
	public SimpleViewerDialog(Shell parentShell) {

		super(parentShell);

		this.setShellStyle(SWT.RESIZE | SWT.TITLE);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.
	 * Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {

		super.configureShell(newShell);

		newShell.setText(getTitle());

		Dimension screen;
		screen = Toolkit.getDefaultToolkit().getScreenSize();

		newShell.setSize(Math.min(600, screen.width - 200), Math.min(800,
				screen.height - 200));
		newShell.setLocation(100, 100);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.
	 * swt.widgets.Composite)
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
	
		createButton(parent, IDialogConstants.OK_ID, "Close", false);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {

		GridData gridData;
		gridData = new GridData();

		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;

		this.text =
				new Text(parent, SWT.MULTI | SWT.READ_ONLY | SWT.H_SCROLL
						| SWT.V_SCROLL);
		this.text.setLayoutData(gridData);
		this.initText(this.text);

		return this.text;
	}

	/**
	 * <p>
	 * Initializes the {@link Text} of this {@link SimpleViewerDialog} with a
	 * given {@link Text}.
	 * </p>
	 * 
	 * @param text
	 *          The {@link Text} used for initializing.
	 */
	protected abstract void initText(Text text);

	/**
	 * <p>
	 * Returns the title of this {@link SimpleViewerDialog}.
	 * </p>
	 * 
	 * @return The title of this {@link SimpleViewerDialog}.
	 */
	protected abstract String getTitle();
}