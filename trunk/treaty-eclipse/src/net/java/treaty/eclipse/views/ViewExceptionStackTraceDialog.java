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
 * Dialog to display the exception stack trace.
 * @author Jens Dietrich
 */

public class ViewExceptionStackTraceDialog extends SimpleViewerDialog {
	private Throwable exception = null;
	/**
	 * Constructor.
	 * @param parentShell
	 * @param exception
	 */
	public ViewExceptionStackTraceDialog(Shell parentShell,Throwable exception) {
		super(parentShell);
		this.exception = exception;
	}
	@Override
	protected String getTitle() {
		return "Verification exception details";
	}
	@Override
	protected void initText(Text text) {
		StringWriter s = new StringWriter();
		PrintWriter out = new PrintWriter(s);
		exception.printStackTrace(out);
		out.close();
		text.setText(s.toString());
	} 
}
