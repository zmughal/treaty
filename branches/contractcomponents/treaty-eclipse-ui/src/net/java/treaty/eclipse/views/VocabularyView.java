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

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import net.java.treaty.eclipse.*;
import net.java.treaty.eclipse.ui.Activator;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.part.*;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.core.runtime.IAdaptable;
import org.semanticweb.owl.model.*;


/**
 * Contract viewer.
 * @author Jens Dietrich
 */

public class VocabularyView extends ViewPart {
	
	private TreeViewer viewer;
	private StyledText text;
	private DrillDownAdapter drillDownAdapter;
	private Action actRefresh;
	
	private Image ICON_PLUGIN = this.getImageDescriptor("icons/plugin.gif").createImage();
	private Image ICON_PREDICATE = this.getImageDescriptor("icons/owl_property.gif").createImage();
	private Image ICON_TYPE = this.getImageDescriptor("icons/owl_class.gif").createImage();
	
	class KeyValueNode {
		String key,value = null;
		public KeyValueNode(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		} 	
	}  
	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
	class TreeObject implements IAdaptable {
		private Object object;
		private TreeParent parent;
		
		public TreeObject(Object object) {
			this.object = object;
		}
		public Object getObject() {
			return object;
		}
		public void setParent(TreeParent parent) {
			this.parent = parent;
		}
		public TreeParent getParent() {
			return parent;
		}
		public String toString() {
			return getObject().toString();
		}
		public Object getAdapter(Class key) {
			return null;
		}
	}
	
	class TreeParent extends TreeObject {
		private ArrayList children;
		public TreeParent(Object object) {
			super(object);
			children = new ArrayList();
		}
		public void addChild(TreeObject child) {
			children.add(child);
			child.setParent(this);
		}
		public void removeChild(TreeObject child) {
			children.remove(child);
			child.setParent(null);
		}
		public TreeObject [] getChildren() {
			return (TreeObject [])children.toArray(new TreeObject[children.size()]);
		}
		public boolean hasChildren() {
			return children.size()>0;
		}
	}

	class ViewContentProvider implements IStructuredContentProvider, 
										   ITreeContentProvider {
		private TreeParent invisibleRoot;

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			if (parent.equals(getViewSite())) {
				if (invisibleRoot==null) initialize();
				return getChildren(invisibleRoot);
			}
			return getChildren(parent);
		}
		public Object getParent(Object child) {
			if (child instanceof TreeObject) {
				return ((TreeObject)child).getParent();
			}
			return null;
		}
		public Object [] getChildren(Object parent) {
			if (parent instanceof TreeParent) {
				return ((TreeParent)parent).getChildren();
			}
			return new Object[0];
		}
		public boolean hasChildren(Object parent) {
			if (parent instanceof TreeParent)
				return ((TreeParent)parent).hasChildren();
			return false;
		}
	/*
	 * We will set up a dummy model to initialize tree hierarchy.
	 * In a real code, you will connect to a real model and
	 * expose its hierarchy.
	 */
		private void initialize() {		
			invisibleRoot = new TreeParent("");
			Vocabulary vocabulary = Vocabulary.getDefault();
			addNodes(invisibleRoot,vocabulary.getOntology());
		}

		private void addNodes(TreeParent parent,OWLOntology ontology) {
				addNodes(parent,ontology,0);
		}
		private void addNodes(TreeParent parent, OWLOntology ontology,int level) {
			// MAX LEVEL to handle loops
			if (level>=42) {				
				return;
			}
			
			if (level==0) {
				// add classes without superclass axioms
				for (OWLClass c:ontology.getReferencedClasses()) {
					Collection<OWLSubClassAxiom> sca =ontology.getSubClassAxiomsForLHS(c) ;
					// TODO check if owl:Thing is declared as superclass
					boolean toplevel = sca.size()==0;
					if (!toplevel && sca.size()==1) {
						OWLDescription sc = sca.iterator().next().getSuperClass();
						toplevel = sc.isOWLThing();
					}
					if (toplevel && !c.isOWLThing()) {
						TreeParent node = new TreeParent(c);
						parent.addChild(node);
						addNodes(node,ontology,level+1);
					}
				}
			}
			else {
				OWLClass superClass = (OWLClass)parent.getObject();
				for (OWLClass c:ontology.getReferencedClasses()) {
					for (OWLSubClassAxiom ax:ontology.getSubClassAxiomsForLHS(c)) {
						if (superClass.equals(ax.getSuperClass())) {
							TreeParent node = new TreeParent(c);
							parent.addChild(node);
							addNodes(node,ontology,level+1);
						}
					}
				}
				// add properties
				for (OWLObjectProperty p:ontology.getReferencedObjectProperties()) {
					for (OWLDescription d:p.getDomains(ontology)) {
						if (d.equals(superClass)) {
							TreeObject node = new TreeObject(p);
							parent.addChild(node);	
						}
					}
				}
			}
			
		}
		
	}
	class ViewLabelProvider implements ITableLabelProvider {
		public URI LABEL_ANNOTATION = URI.create("http://www.w3.org/2000/01/rdf-schema#label");
		public String getColumnText(Object n, int col) {
			Object obj = ((TreeObject)n).getObject();
			if (col==0 && obj instanceof OWLNamedObject) {
				URI uri = ((OWLNamedObject)obj).getURI();
				return uri==null?obj.toString():uri.toString();
			}
			else if (col==1 && obj instanceof OWLEntity) {
				OWLEntity c = (OWLEntity)obj;
				for (OWLAnnotation ann:c.getAnnotations(Vocabulary.getDefault().getOntology(),LABEL_ANNOTATION)){
					String value = ann.getAnnotationValue()==null?null:ann.getAnnotationValue().toString();
					if (value!=null && value.startsWith(Vocabulary.OWNER_ANNOTATION)) {
						return value.substring(Vocabulary.OWNER_ANNOTATION.length()+1);
					}
				}
				return "";
			}
			else if (col==2 && obj instanceof OWLObjectProperty) {
				OWLObjectProperty p = (OWLObjectProperty)obj;
				StringBuffer b = new StringBuffer();
				for (OWLDescription r:p.getRanges(Vocabulary.getDefault().getOntology())) {
					if (b.length()>0) {
						b.append(',');
					}
					if (r instanceof OWLNamedObject) {
						b.append(((OWLNamedObject)r).getURI());
					}
					else {
						b.append(r);
					}
				}
				return b.toString();
			}
			else if (col==2 && obj instanceof OWLDataProperty) {
				OWLDataProperty p = (OWLDataProperty)obj;
				StringBuffer b = new StringBuffer();
				for (OWLDataRange r:p.getRanges(Vocabulary.getDefault().getOntology())) {
					if (b.length()>0) {
						b.append(',');
					}
					if (r instanceof OWLNamedObject) {
						b.append(((OWLNamedObject)r).getURI());
					}
					else {
						b.append(r);
					}
				}
				return b.toString();
			}
			else return "";
		}
		public Image getColumnImage(Object n, int col) {
			Object obj = ((TreeObject)n).getObject();
			if (col==0 && obj instanceof OWLClass) {
				return ICON_TYPE;
			}
			else if (col==0 && obj instanceof OWLObjectProperty) {
				return ICON_PREDICATE;
			}
			else if (col==1) {
				String txt = getColumnText(n,col);
				if (txt!=null && txt.length()>0) {
					return ICON_PLUGIN;
				}				
			}
			else if (col==2) {
				String txt = getColumnText(n,col);
				if (txt!=null && txt.length()>0) {
					return ICON_TYPE;
				}				
			}

			return null;
		}
		public void addListener(ILabelProviderListener arg0) {
			
		}
		public boolean isLabelProperty(Object arg0, String arg1) {
			return false;
		}
		public void removeListener(ILabelProviderListener arg0) {
			
		}
		public void dispose() {
		}
	}

	/**
	 * The constructor.
	 */
	public VocabularyView() {
	}

	public ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, path); 
	}
	
	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		
		final TabFolder tabFolder = new TabFolder(parent, SWT.BORDER);
		
		TabItem tabItem1 = new TabItem(tabFolder, SWT.NULL);
	    tabItem1.setText("types and properties");

	    viewer = new TreeViewer(tabFolder, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);	    

		viewer.getTree().setHeaderVisible(true);		
		TreeColumn col1 = new TreeColumn(viewer.getTree(), SWT.LEFT);
	    col1.setText("type or property");
	    col1.setWidth(350);
		TreeColumn col2 = new TreeColumn(viewer.getTree(), SWT.LEFT);
	    col2.setText("contributed by"); 
	    col2.setWidth(300);
		TreeColumn col3 = new TreeColumn(viewer.getTree(), SWT.LEFT);
	    col3.setText("property type (range)"); 
	    col3.setWidth(200);
	    
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
	    viewer.setInput(getViewSite());	
	    
		makeActions();
		hookContextMenu();
		contributeToActionBars();
		
		tabItem1.setControl(viewer.getControl());
		
		TabItem tabItem2 = new TabItem(tabFolder, SWT.NULL);
	    tabItem2.setText("merged ontology (owl)");	
	    text = new StyledText(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
	    text.setText(Vocabulary.getDefault().getOWL());
	    tabItem2.setControl(text);
		
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				VocabularyView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(actRefresh);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(actRefresh);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(actRefresh);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		actRefresh = new Action() {
			public void run() {
				text.setText("");
				Vocabulary.reset();
				Vocabulary.getDefault();
				viewer.setContentProvider(new ViewContentProvider());
				text.setText(Vocabulary.getDefault().getOWL());
			}
		};
		actRefresh.setText("refresh");
		actRefresh.setToolTipText("reload vocabulary");
		actRefresh.setImageDescriptor(this.getImageDescriptor("icons/refresh.gif"));
		
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Plugin Contracts",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	@Override
	public void dispose() {
		ICON_PLUGIN.dispose();
		ICON_PREDICATE.dispose();
		ICON_TYPE.dispose();
		super.dispose();
	}
}