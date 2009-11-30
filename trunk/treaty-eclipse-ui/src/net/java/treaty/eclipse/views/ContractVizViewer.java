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
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
	private JPanel myToolbar = new JPanel(new GridLayout(1, 4));

	/** The status line (a {@link JPanel}) of this {@link ContractVizViewer}. */
	private JPanel myStatusLine = new JPanel(new GridLayout(1, 1));

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

		/* Create the CV View. */
		this.myContractVisualizationView = new ContractVisualizationView();
		this.myContractVisualizationView.setModel(this.myContract);

		/* Add tool bar, CVV and status line to the frame. */
		frame.add(this.myToolbar, BorderLayout.NORTH);
		this.initializeToolbar();

		frame.add(new JScrollPane(this.myContractVisualizationView),
				BorderLayout.CENTER);

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
		buffer.append(" - ");

		if (this.myContract.getConsumer() != null) {
			buffer.append("Consumer: " + this.myContract.getConsumer().getId());
		}

		else {
			buffer.append("Unbound Consumer.");
		}

		buffer.append(", ");

		if (this.myContract.getSupplier() != null) {
			buffer.append("Supplier: " + this.myContract.getSupplier().getId());
		}

		else {
			buffer.append("Unbound Supplier.");
		}

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
	 * A helper method that adds a {@link JSlider} to alter a {@link Integer}
	 * value to the tool bar.
	 * </p>
	 * 
	 * @param string
	 *          The label of the {@link JSlider}.
	 * @param initialValue
	 *          The initial value of the {@link JSlider}.
	 * @param min
	 *          The minimal value of the {@link JSlider}.
	 * @param max
	 *          The maximal value of the {@link JSlider}.
	 * @param command
	 *          The {@link Command} that is used to alter the value.
	 */
	private void addIntegerPropertyCommand(String string, int initialValue,
			int min, int max, final Command<Integer> command) {

		JPanel jPane;

		jPane = new JPanel(new GridLayout(1, 1, 5, 5));
		jPane.add(new JLabel(string + ":", JLabel.RIGHT));

		final JSlider slider;
		slider = new JSlider();

		slider.setMinimum(min);
		slider.setMaximum(max);
		slider.setValue(initialValue);
		slider.setMinorTickSpacing(10);
		slider.setMajorTickSpacing(50);
		slider.createStandardLabels(50);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setPaintTrack(true);

		jPane.add(slider);

		slider.addChangeListener(new ChangeListener() {

			/*
			 * (non-Javadoc)
			 * @see
			 * javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent
			 * )
			 */
			public void stateChanged(ChangeEvent e) {

				command.apply(slider.getValue());
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

		JTextArea jTextArea;
		jTextArea = new JTextArea();

		jTextArea.setBackground(this.myStatusLine.getBackground());
		jTextArea.setFont(jTextArea.getFont().deriveFont(Font.BOLD));

		if (!this.myContract.isInstantiated()) {
			jTextArea
					.setText("This Contract is not instantiated and thus cannot be verified.");
		}

		else {

			Object verificationResult;
			verificationResult = this.myContract.getProperty(VERIFICATION_RESULT);

			if (verificationResult == null) {
				jTextArea.setText("This Contract has not been verified yet.");
				jTextArea.setForeground(Color.GRAY);
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

				jTextArea.setText(buffer.toString());
				jTextArea.setForeground(Color.RED);
			}

			else if (verificationResult == VerificationResult.SUCCESS) {
				jTextArea.setText("This Contract has been verified successfully.");
				jTextArea.setForeground(Color.GREEN);
			}

			else {
				jTextArea
						.setText("The verification of this Contract resulted in an unknown state.");
				jTextArea.setForeground(Color.GRAY);
			}
			// end else.
		}
		// end else.

		this.myStatusLine.add(jTextArea);
	}

	/**
	 * <p>
	 * A helper method that initializes the toolbar.
	 * </p>
	 */
	private void initializeToolbar() {

		this.addBooleanPropertyCommand("Merge equal Resources",
				this.myContractVisualizationView.isMergeEqualNodes(),
				new Command<Boolean>() {

					public void apply(Boolean aBoolean) {

						myContractVisualizationView.setMergeEqualNodes(aBoolean);
					}
				});

		this.addBooleanPropertyCommand("Remove reduntant Connectives",
				this.myContractVisualizationView.isRemoveBinConnectivesWithOneChild(),
				new Command<Boolean>() {

					public void apply(Boolean aBoolean) {

						myContractVisualizationView
								.setRemoveBinConnectivesWithOneChild(aBoolean);
					}
				});

		this.addIntegerPropertyCommand("Column Width",
				this.myContractVisualizationView.getColumnWidth(), 0, 200,
				new Command<Integer>() {

					public void apply(Integer integer) {

						myContractVisualizationView.setColumnWidth(integer);
					}
				});

		this.addIntegerPropertyCommand("Row Height",
				this.myContractVisualizationView.getRowHeight(), 0, 200,
				new Command<Integer>() {

					public void apply(Integer integer) {

						myContractVisualizationView.setRowHeight(integer);
					}
				});
	}
}