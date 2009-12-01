/*
 * Copyright (C) 2008-2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.views;

import static net.java.treaty.eclipse.Constants.VERIFICATION_RESULT;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.java.treaty.Contract;
import net.java.treaty.VerificationResult;
import net.java.treaty.eclipse.Constants;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * <p>
 * The {@link ContractVizViewer} adapts the AWT
 * {@link ContractVisualizationView} as a Eclipse SWT view.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class ContractVizViewer extends Dialog {

	/**
	 * <p>
	 * A helper interface to describe action Commands in the tool bar.
	 * </p>
	 * 
	 * @author Jens Dietrich
	 * 
	 * @param <T>
	 *          The type of the value changed by this {@link Command}.
	 */
	private interface Command<T> {

		void apply(T value);
	}

	/** The {@link Contract} of this {@link ContractVizViewer}. */
	private Contract myContract = null;

	/** The {@link ContractVisualizationView} of this {@link ContractVizViewer}. */
	private ContractVisualizationView myContractVisualizationView;

	/** The tool bar (a {@link JPanel}) of this {@link ContractVizViewer}. */
	private JPanel myToolbar = new JPanel(new GridLayout(2, 2));

	/** The status line (a {@link JPanel}) of this {@link ContractVizViewer}. */
	private JPanel myStatusLine = new JPanel(new GridLayout(2, 1));

	/**
	 * <p>
	 * Creates a new {@link ContractVizViewer}
	 * </p>
	 * 
	 * @param parentShell
	 *          The parent {@link Shell} of this {@link ContractVizViewer}.
	 */
	public ContractVizViewer(Shell parentShell, Contract contract) {

		super(parentShell);

		this.setShellStyle(SWT.RESIZE | SWT.TITLE);
		this.myContract = contract;
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

		newShell.setText(this.getTitle());

		Dimension screen;
		screen = Toolkit.getDefaultToolkit().getScreenSize();

		newShell.setSize(Math.min(1024, screen.width - 200), Math.min(768,
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

		this.createButton(parent, IDialogConstants.OK_ID, "Close", false);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {

		/* Reduces flicker during window resizing (at least on Windows OSs. */
		System.setProperty("sun.awt.noerasebackground", "true");

		GridData gridData;
		gridData = new GridData();

		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;

		Composite composite;
		composite = new Composite(parent, SWT.EMBEDDED | SWT.NO_BACKGROUND);
		composite.setLayoutData(gridData);

		Frame frame = SWT_AWT.new_Frame(composite);

		/* Add CVV, tool bar and status line to the frame. */
		this.myContractVisualizationView = new ContractVisualizationView();
		this.myContractVisualizationView.setModel(this.myContract);

		frame.add(new JScrollPane(this.myContractVisualizationView),
				BorderLayout.CENTER);

		frame.add(this.myToolbar, BorderLayout.NORTH);
		this.initializeToolbar();

		frame.add(this.myStatusLine, BorderLayout.SOUTH);
		this.initializeStatusLine();

		return composite;
	}

	/**
	 * <p>
	 * Returns the title of this {@link ContractVizViewer}.
	 * </p>
	 * 
	 * @return The title of this {@link ContractVizViewer}.
	 */
	protected String getTitle() {

		StringBuffer buffer;
		buffer = new StringBuffer();

		buffer.append("Contract Visualization");
		buffer.append(" (BETA)");

		return buffer.toString();
	}

	/**
	 * <p>
	 * A helper method that adds a {@link JCheckBox} to alter a {@link Boolean}
	 * value to the tool bar.
	 * </p>
	 * 
	 * @param string
	 *          The label of the {@link JCheckBox}.
	 * @param initialValue
	 *          The initial value of the {@link JCheckBox}.
	 * @param command
	 *          The {@link Command} that is used to alter the value.
	 */
	private void addBooleanPropertyCommand(String string, boolean initialValue,
			final Command<Boolean> command) {

		JPanel jPane;
		jPane = new JPanel(new BorderLayout(5, 5));
		jPane.add(new JLabel(string + ":", JLabel.RIGHT), BorderLayout.CENTER);

		final JCheckBox checkBox;
		checkBox = new JCheckBox();

		jPane.add(checkBox, BorderLayout.EAST);

		checkBox.setSelected(initialValue);
		checkBox.addActionListener(new ActionListener() {

			/*
			 * (non-Javadoc)
			 * @see
			 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
			 * )
			 */
			public void actionPerformed(ActionEvent e) {

				command.apply(checkBox.isSelected());
			}
		});

		myToolbar.add(jPane);
	}

	/**
	 * <p>
	 * A helper method that initializes the status line of this
	 * {@link ContractVizViewer}.
	 * </p>
	 */
	private void initializeStatusLine() {

		JTextArea contractVerificationStatus;
		contractVerificationStatus = new JTextArea();

		contractVerificationStatus.setBackground(this.myStatusLine.getBackground());
		contractVerificationStatus.setFont(contractVerificationStatus.getFont()
				.deriveFont(Font.BOLD));

		if (!this.myContract.isInstantiated()) {
			contractVerificationStatus
					.setText("This Contract is not instantiated and thus cannot be verified.");
		}

		else {

			Object verificationResult;
			verificationResult = this.myContract.getProperty(VERIFICATION_RESULT);

			if (verificationResult == null) {
				contractVerificationStatus
						.setText("This Contract has not been verified yet.");
				contractVerificationStatus.setForeground(Color.GRAY);
			}

			else if (verificationResult == VerificationResult.FAILURE) {
				StringBuffer buffer;
				buffer = new StringBuffer();

				buffer.append("The verification of this Contract failed.");

				/* Probably append an exception message. */
				if (this.myContract.getProperty(Constants.VERIFICATION_EXCEPTION) != null
						&& this.myContract.getProperty(Constants.VERIFICATION_EXCEPTION) instanceof Exception) {

					Exception exception =
							(Exception) this.myContract
									.getProperty(Constants.VERIFICATION_EXCEPTION);

					buffer.append(" Reason: ");

					if (exception.getMessage() != null
							&& exception.getMessage().length() > 0) {
						buffer.append(exception.getMessage());
					}

					else {
						buffer.append(exception.getClass().getSimpleName());
					}
					// end else.
				}
				// no else.

				contractVerificationStatus.setText(buffer.toString());
				contractVerificationStatus.setForeground(Color.RED);
			}

			else if (verificationResult == VerificationResult.SUCCESS) {
				contractVerificationStatus
						.setText("This Contract has been verified successfully.");
				contractVerificationStatus.setForeground(Color.GREEN);
			}

			else {
				contractVerificationStatus
						.setText("The verification of this Contract resulted in an unknown state.");
				contractVerificationStatus.setForeground(Color.GRAY);
			}
			// end else.
		}
		// end else.

		this.myStatusLine.add(contractVerificationStatus);

		JTextArea userHelp;
		userHelp = new JTextArea();

		userHelp.setBackground(this.myStatusLine.getBackground());
		userHelp.setFont(userHelp.getFont().deriveFont(Font.ITALIC));

		userHelp
				.setText("You can move the graph by pressing the left mouse button and "
						+ " moving the mouse. \nYou can alter the graph's size by pressing "
						+ "CTRL and the left mouse button and moving the mouse. \n"
						+ "Tooltips will give you further information about graph elements "
						+ "during mouse over.");

		this.myStatusLine.add(userHelp);
	}

	/**
	 * <p>
	 * A helper method that initializes the toolbar.
	 * </p>
	 */
	private void initializeToolbar() {

		JTextArea consumerText;
		consumerText = new JTextArea();

		consumerText.setBackground(this.myToolbar.getBackground());
		consumerText.setFont(consumerText.getFont().deriveFont(Font.BOLD));

		if (this.myContract.getConsumer() != null) {
			consumerText
					.setText("Consumer: " + this.myContract.getConsumer().getId());
		}

		else {
			consumerText.setText("No Consumer bound to Contract.");
		}

		this.myToolbar.add(consumerText);

		this.addBooleanPropertyCommand("Merge equal Resources",
				this.myContractVisualizationView.isMergeEqualNodes(),
				new Command<Boolean>() {

					public void apply(Boolean aBoolean) {

						myContractVisualizationView.setMergeEqualNodes(aBoolean);
					}
				});

		JTextArea supplierText;
		supplierText = new JTextArea();

		supplierText.setBackground(this.myToolbar.getBackground());
		supplierText.setFont(supplierText.getFont().deriveFont(Font.BOLD));

		if (this.myContract.getSupplier() != null) {
			supplierText
					.setText("Supplier: " + this.myContract.getSupplier().getId());
		}

		else {
			supplierText.setText("No Supplier bound to Contract.");
		}

		this.myToolbar.add(supplierText);

		this.addBooleanPropertyCommand("Remove redundant Connectives",
				this.myContractVisualizationView.isRemoveBinConnectivesWithOneChild(),
				new Command<Boolean>() {

					public void apply(Boolean aBoolean) {

						myContractVisualizationView
								.setRemoveBinConnectivesWithOneChild(aBoolean);
					}
				});
	}
}