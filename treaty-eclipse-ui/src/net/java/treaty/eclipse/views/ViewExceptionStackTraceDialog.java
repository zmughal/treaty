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

import java.io.PrintWriter;
import java.io.StringWriter;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * <p>
 * A {@link SimpleViewerDialog} to display the exception stack trace.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class ViewExceptionStackTraceDialog extends SimpleViewerDialog {

	/** The {@link Throwable} that shall be displayed. */
	private Throwable throwable = null;

	/**
	 * <p>
	 * Creates a new {@link ViewExceptionStackTraceDialog}.
	 * </p>
	 * 
	 * @param parentShell
	 *          The {@link Shell} used as parent.
	 * @param throwable
	 *          The {@link Throwable} that shall be displayed.
	 */
	public ViewExceptionStackTraceDialog(Shell parentShell, Throwable throwable) {

		super(parentShell);
		this.throwable = throwable;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.eclipse.views.SimpleViewerDialog#getTitle()
	 */
	@Override
	protected String getTitle() {

		return "Verification Exception Details";
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.eclipse.views.SimpleViewerDialog#initText(org.eclipse.swt
	 * .widgets.Text)
	 */
	@Override
	protected void initText(Text text) {

		StringWriter stringWriter;
		stringWriter = new StringWriter();

		PrintWriter printWriter;
		printWriter = new PrintWriter(stringWriter);

		this.throwable.printStackTrace(printWriter);
		printWriter.close();

		text.setText(stringWriter.toString());
	}
}