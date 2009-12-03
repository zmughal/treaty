/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.viz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import test.net.java.treaty.vocabulary.TestVocabulary;
import net.java.treaty.Contract;
import net.java.treaty.ContractReader;
import net.java.treaty.vocabulary.CompositeContractOntology;
import net.java.treaty.vocabulary.builtins.BasicOpVocabulary;
import net.java.treaty.vocabulary.builtins.owl.OWLVocabulary;
import net.java.treaty.xml.XMLContractReader;

/**
 * Dialog based contract viewer.
 * @author jens dietrich
 */

public class ContractViewer extends JDialog {
	
	protected ContractView contractView = null;
	private JSlider colSizeSlider = new JSlider();
	private JSlider rowSizeSlider = new JSlider();
	private JPanel northPanel = new JPanel();
	private JPanel southPanel = new JPanel();

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		ContractViewer v = new ContractViewer();
		v.setSize(800,600);
		v.setLocation(200,100);
		// read contract
		CompositeContractOntology voc = null;
		voc = new CompositeContractOntology();
		voc.add(new BasicOpVocabulary(), "net.java.treaty");
		voc.add(new OWLVocabulary(), "net.java.treaty");
		voc.add(new TestVocabulary(), "net.java.treaty");
		InputStream in = new FileInputStream(new File("testdata/contract1.contract"));
		ContractReader reader = new XMLContractReader();
		Contract contract = reader.read(in, voc);
		v.setContract(contract);
		
		v.setVisible(true);
	}

	public ContractViewer() {
		super();
		init();
	}

	public ContractViewer(Dialog owner, boolean modal) {
		super(owner, modal);
		init();
	}

	public ContractViewer(Dialog owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		init();
	}

	public ContractViewer(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		init();
	}

	public ContractViewer(Dialog owner, String title) {
		super(owner, title);
		init();
	}

	public ContractViewer(Dialog owner) {
		super(owner);
		init();
	}

	public ContractViewer(Frame owner, boolean modal) {
		super(owner, modal);
		init();
	}

	public ContractViewer(Frame arg0, String arg1, boolean arg2,
			GraphicsConfiguration arg3) {
		super(arg0, arg1, arg2, arg3);
		init();
	}

	public ContractViewer(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		init();
	}

	public ContractViewer(Frame owner, String title) {
		super(owner, title);
		init();
	}

	public ContractViewer(Frame owner) {
		super(owner);
		init();
	}

	public ContractViewer(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		init();
	}

	public ContractViewer(Window owner, String title,
			ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		init();
	}

	public ContractViewer(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		init();
	}

	public ContractViewer(Window owner, String title) {
		super(owner, title);
		init();
	}

	public ContractViewer(Window owner) {
		super(owner);
		init();
	}

	private void init() {
		initContractView();
		
		JPanel contentPane = new JPanel(new BorderLayout());
		JPanel mainPane = new JPanel(new BorderLayout());
		mainPane.setBackground(Color.WHITE);
		contentPane.add(mainPane,BorderLayout.CENTER);
		mainPane.add(contractView,BorderLayout.CENTER);
		this.setContentPane(contentPane);
		
		configureNorthPanel(northPanel);
		contentPane.add(northPanel,BorderLayout.NORTH);
		
		JPanel northPane2 = new JPanel(new BorderLayout());
		northPane2.setOpaque(false);
		northPane2.setBorder(BorderFactory.createEmptyBorder(0,0,0,5));
		mainPane.add(northPane2,BorderLayout.NORTH);
		this.colSizeSlider.setOrientation(JSlider.HORIZONTAL);
		this.colSizeSlider.setMinimum(10);
		this.colSizeSlider.setMaximum(200);
		this.colSizeSlider.setOpaque(false);
		this.colSizeSlider.setValue(this.contractView.getColumnWidth());
		this.colSizeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				contractView.setColumnWidth(colSizeSlider.getValue());
			}
		});
		northPane2.add(this.colSizeSlider,BorderLayout.EAST);
		this.contractView.addPropertyChangeListener("columnWidth",new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				colSizeSlider.setValue((Integer)e.getNewValue());
			}});

		
		JPanel eastPane = new JPanel(new BorderLayout());
		eastPane.setOpaque(false);
		mainPane.add(eastPane,BorderLayout.EAST);
		this.rowSizeSlider.setOpaque(false);
		this.rowSizeSlider.setOrientation(JSlider.VERTICAL);
		this.rowSizeSlider.setMinimum(10);
		this.rowSizeSlider.setMaximum(200);
		this.rowSizeSlider.setValue(this.contractView.getRowHeight());
		this.rowSizeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				contractView.setRowHeight(rowSizeSlider.getValue());
			}
		});
		eastPane.add(this.rowSizeSlider,BorderLayout.NORTH);
		this.contractView.addPropertyChangeListener("rowHeight",new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				rowSizeSlider.setValue((Integer)e.getNewValue());
			}});
		
		
		configureSouthPanel(southPanel);
		contentPane.add(southPanel,BorderLayout.SOUTH);
		
		// options
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Options");
		menu.setMnemonic(KeyEvent.VK_O);
		menuBar.add(menu);
		final JCheckBoxMenuItem opt1 = new JCheckBoxMenuItem("merge equal nodes");
		menu.add(opt1);
		opt1.setSelected(this.contractView.isMergeEqualNodes());
		opt1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contractView.setMergeEqualNodes(opt1.isSelected());
			}
		});
		final JCheckBoxMenuItem opt2 = new JCheckBoxMenuItem("remove binary connectives with only one part");
		menu.add(opt2);
		opt2.setSelected(this.contractView.isRemoveBinConnectivesWithOneChild());
		opt2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contractView.setRemoveBinConnectivesWithOneChild(opt1.isSelected());
			}
		});

		this.setJMenuBar(menuBar);
		
		
	}
	protected void configureSouthPanel(JPanel pane) {
		pane.setLayout(new BorderLayout());
		pane.setOpaque(true);
		JButton exitButton = new JButton("close");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}});
		pane.add(exitButton,BorderLayout.EAST);
		
		JTextArea helpText = new JTextArea();
		helpText.setEditable(false);
		helpText.setText("Drag mouse to pan, use mouse wheel to zoom, Ctrl+Drag to stretch horizontally and vertically");
		pane.add(helpText,BorderLayout.CENTER);
	}

	protected void configureNorthPanel(JPanel pane) {
		pane.setLayout(new BorderLayout());
		pane.setOpaque(true);
		JTextArea helpText = new JTextArea();
		helpText.setEditable(false);
		helpText.setText("More text goes here");
		pane.add(helpText,BorderLayout.CENTER);
	}

	// sets the actual contract view component, can be overridden
	protected void initContractView() {
		contractView = new ContractView();
	}

	public void setContract(Contract c) {
		this.contractView.setModel(c);
	}
}
