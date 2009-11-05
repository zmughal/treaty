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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import net.java.treaty.Contract;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * <p>
 * A {@link SimpleViewerDialog} to display raw contract definitions.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class ViewContractSourceDialog extends SimpleViewerDialog {

	/** The {@link URL} of the {@link Contract} that shall be displayed. */
	private URL contractURL = null;

	/**
	 * <p>
	 * Creates a new {@link ViewContractSourceDialog}.
	 * </p>
	 * 
	 * @param parentShell
	 *          The {@link Shell} used as parent.
	 * @param url
	 *          The {@link URL} of the {@link Contract} that shall be displayed.
	 */
	public ViewContractSourceDialog(Shell parentShell, URL contractURL) {

		super(parentShell);
		this.contractURL = contractURL;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.eclipse.views.SimpleViewerDialog#getTitle()
	 */
	@Override
	protected String getTitle() {

		return this.contractURL.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.eclipse.views.SimpleViewerDialog#initText(org.eclipse.swt
	 * .widgets.Text)
	 */
	@Override
	protected void initText(Text text) {

		try {
			String separatorPropertyName;
			separatorPropertyName = System.getProperty("line.separator");

			StringBuffer buffer;
			buffer = new StringBuffer();

			InputStreamReader iputStreamReader;
			iputStreamReader = new InputStreamReader(this.contractURL.openStream());

			BufferedReader bufferedReader;
			bufferedReader = new BufferedReader(iputStreamReader);

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				buffer.append(line);
				buffer.append(separatorPropertyName);
			}
			// end while.

			text.setText(buffer.toString());
			bufferedReader.close();
		}
		// end try.

		catch (IOException e) {

			text.setText("Error - cannot load contract");
			e.printStackTrace();
		}
		// end catch.
	}
}