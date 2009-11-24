/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */


package net.java.treaty.viz.example;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.java.treaty.Contract;
import net.java.treaty.ContractReader;
import net.java.treaty.viz.ContractView;
import net.java.treaty.vocabulary.CompositeContractOntology;
import net.java.treaty.vocabulary.builtins.BasicOpVocabulary;
import net.java.treaty.vocabulary.builtins.owl.OWLVocabulary;
import net.java.treaty.xml.XMLContractReader;
import test.net.java.treaty.vocabulary.TestVocabulary;

/**
 * Test app for contract viewer.
 * @author jens dietrich
 */
public class ContractViewTester extends JFrame {
	
	private JPanel mainPane = new JPanel(new BorderLayout(2,2));
	private JPanel toolbar = new JPanel(new GridLayout(2,3));
	private JPanel toolbar2 = new JPanel(new BorderLayout());
	private ContractView viewer = new CustomContractView();
	
	interface Command<T> {
		void apply(T value);
	}
	
	public ContractViewTester() throws HeadlessException {
		super();
		init();
	}

	public ContractViewTester(GraphicsConfiguration gc) {
		super(gc);
		init();
	}

	public ContractViewTester(String title, GraphicsConfiguration gc) {
		super(title, gc);
		init();
	}

	public ContractViewTester(String title) throws HeadlessException {
		super(title);
		init();
	}

	private void init() {
		this.setContentPane(mainPane);
		mainPane.add(toolbar,BorderLayout.NORTH);
		mainPane.add(new JScrollPane(viewer),BorderLayout.CENTER);
		mainPane.add(toolbar2,BorderLayout.SOUTH);
		
		addBooleanProperty(
				"merge equal resources",viewer.isMergeEqualNodes(),
				new Command<Boolean>() {
					@Override
					public void apply(Boolean b) {
						viewer.setMergeEqualNodes(b);
					}
				} 
		);
		addIntProperty(
				"column width",viewer.getColumnWidth(),0,200,
				new Command<Integer>() {
					@Override
					public void apply(Integer v) {
						viewer.setColumnWidth(v);
					}
				} 
		);
		addIntProperty(
				"row height",viewer.getRowHeight(),0,200,
				new Command<Integer>() {
					@Override
					public void apply(Integer v) {
						viewer.setRowHeight(v);
					}
				} 
		);
		addIntProperty(
				"left offset",viewer.getLeftOffset(),0,200,
				new Command<Integer>() {
					@Override
					public void apply(Integer v) {
						viewer.setLeftOffset(v);
					}
				} 
		);
		addIntProperty(
				"top offset",viewer.getTopOffset(),0,200,
				new Command<Integer>() {
					@Override
					public void apply(Integer v) {
						viewer.setTopOffset(v);
					}
				} 
		);
		
		// combo box to load contracts
		File dataFolder = new File("testdata");
		final File[] files = dataFolder.listFiles(new FileFilter(){
			@Override
			public boolean accept(File f) {
				return !f.isDirectory() && f.getAbsolutePath().endsWith(".contract");
			}});
		Arrays.sort(files);
		final JComboBox cbx = new JComboBox(files);
		this.toolbar2.add(new JLabel("contracts:"),BorderLayout.WEST);
		this.toolbar2.add(cbx,BorderLayout.CENTER);
		JButton loadButton = new JButton("load");
		this.toolbar2.add(loadButton,BorderLayout.EAST);
		loadButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int i = cbx.getSelectedIndex();
						try {
							loadContract(files[i]);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
		);
		
		// load first file
		try {
			this.loadContract(files[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addBooleanProperty(String string, boolean b,final Command<Boolean> command) {
		JPanel pane = new JPanel(new BorderLayout());
		pane.add(new JLabel(string),BorderLayout.WEST);
		final JCheckBox chkBox = new JCheckBox();
		pane.add(chkBox,BorderLayout.CENTER);
		chkBox.setSelected(b);
		chkBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				command.apply(chkBox.isSelected());
			}});
		toolbar.add(pane);
	}
	
	private void addIntProperty(String string, int v,int min,int max,final Command<Integer> command) {
		JPanel pane = new JPanel(new BorderLayout());
		pane.add(new JLabel(string),BorderLayout.WEST);
		final JSlider slider = new JSlider();
		slider.setMinimum(min);
		slider.setMaximum(max);
		slider.setValue(v);
		slider.setMinorTickSpacing(10);
		slider.setMajorTickSpacing(50);
		slider.createStandardLabels(50);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setPaintTrack(true);
		pane.add(slider,BorderLayout.CENTER);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				command.apply(slider.getValue());
			}});
		toolbar.add(pane);
	}

	public static void main(String[] args) throws Exception {
		
		ContractViewTester f = new ContractViewTester();
		f.setSize(1000,700);
		f.setLocation(100,100);
		f.setVisible(true);
		//f.revalidate();
		//f.repaint();
	}
	
	private void loadContract(File f) throws Exception {
		CompositeContractOntology voc = null;
		voc = new CompositeContractOntology();
		voc.add(new BasicOpVocabulary(), "net.java.treaty");
		voc.add(new OWLVocabulary(), "net.java.treaty");
		voc.add(new TestVocabulary(), "net.java.treaty");
		InputStream in = new FileInputStream(f);
		ContractReader reader = new XMLContractReader();
		Contract contract = reader.read(in, voc);
		viewer.setModel(contract);
	}
}
