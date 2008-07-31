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

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import net.java.treaty.*;
import net.java.treaty.eclipse.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.part.*;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.core.runtime.IAdaptable;
import org.osgi.framework.Bundle;


/**
 * Contract viewer.
 * @author Jens Dietrich
 */

public class ContractView extends ViewPart {
	
	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action actRefresh;
	private Action actVerify;
	
	private Image ICON_PLUGIN = this.getImageDescriptor("icons/plugin.gif").createImage();
	private Image ICON_EXTENSION = this.getImageDescriptor("icons/extension.gif").createImage();
	private Image ICON_EXTENSIONPOINT = this.getImageDescriptor("icons/extensionpoint.gif").createImage();
	private Image ICON_CONTRACT = this.getImageDescriptor("icons/contract.gif").createImage();
	private Image ICON_CONSTRAINT = this.getImageDescriptor("icons/constraint.gif").createImage();
	private Image ICON_CONSTANT = this.getImageDescriptor("icons/constant.png").createImage();
	private Image ICON_VARIABLE = this.getImageDescriptor("icons/variable.png").createImage();
	private Image ICON_LINK = this.getImageDescriptor("icons/link.gif").createImage();
	private Image ICON_CLASS = this.getImageDescriptor("icons/class.gif").createImage();
	private Image ICON_CLASS_VAR = this.getImageDescriptor("icons/class_var.gif").createImage();
	private Image ICON_INTERFACE = this.getImageDescriptor("icons/interface.gif").createImage();
	private Image ICON_INTERFACE_VAR = this.getImageDescriptor("icons/interface_var.gif").createImage();

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
			addNodes(invisibleRoot);
		}
		

		
		private void addNodes(TreeParent parent) {
			Collection<EclipsePlugin> plugins = new Builder().extractContracts();
			for (EclipsePlugin plugin:plugins) {
				TreeParent node = new TreeParent(plugin);
				parent.addChild(node);					
				addNodes(node,plugin);
			}			
		}
		
		private void addNodes(TreeParent parent,EclipsePlugin plugin) {
			Collection<EclipsePlugin> plugins = new Builder().extractContracts();
			for (EclipseExtensionPoint xp:plugin.getExtensionPoints()) {
				TreeParent node = new TreeParent(xp);
				parent.addChild(node);	
				addNodes(node,xp);
			}					
		}
		
		private void addNodes(TreeParent parent,EclipseExtensionPoint xp) {
			Contract c = xp.getContract();				
			if (c instanceof SimpleContract) {
				TreeParent node = new TreeParent(c);
				parent.addChild(node);
				addNodes(node,(SimpleContract)c);
				
				// extensions
				node = new TreeParent("contract instances");
				parent.addChild(node);
				for (EclipseExtension x:xp.getExtensions()) {
					TreeParent node2 = new TreeParent(x.getOwner());
					node.addChild(node2);
					addNodes(node2,x,(SimpleContract)c);	
				}
			}		
			

			
		}
		private void addNodes(TreeParent parent,EclipseExtension x,SimpleContract c) {			
			TreeParent node = new TreeParent(x);
			parent.addChild(node);
			try {
				SimpleContract instantiatedContract = (SimpleContract)c.bindSupplier(x, new EclipseResourceManager());
				addNodes(node,instantiatedContract);
			}
			catch (Exception t) {
				TreeObject errorObject = new TreeObject("exception instantiating contract for this plugin");
				parent.addChild(errorObject);
				t.printStackTrace();
			}
		}
		
		private void addNodes(TreeParent parent,SimpleContract contract) {
			
			TreeParent rNode = new TreeParent("extension point resources");
			parent.addChild(rNode);
			for (Resource r:contract.getConsumerResources()) {
				addResourceNode(rNode,r);				
			}
			rNode = new TreeParent("extension resources");
			parent.addChild(rNode);
			for (Resource r:contract.getSupplierResources()) {
				addResourceNode(rNode,r);	
			}
			
			rNode = new TreeParent("constraints");
			parent.addChild(rNode);
			for (AbstractCondition c:contract.getConstraints()) {
				// top level conjunction displayed as set
				if (c instanceof Conjunction) {
					for (AbstractCondition c2:((Conjunction)c).getParts()) {
						addNodes(rNode,c2);
					}
				}
				else addNodes(rNode,c);
			}
		}
		private void addResourceNode(TreeParent parent,Resource r) {
			TreeParent node = new TreeParent(r);
			parent.addChild(node);
			addNodes(node,r);	
		}
		private void addNodes(TreeParent parent,AbstractCondition c) {
			if (c instanceof Condition) {
				Condition cc = (Condition)c;
				TreeParent node = new TreeParent(cc);
				parent.addChild(node);
				addResourceNode(node,cc.getResource1());
				node.addChild(new TreeObject(new KeyValueNode("relationship",cc.getRelationship().toString())));
				addResourceNode(node,cc.getResource2());
			}
			else if (c instanceof PropertyCondition) {
				PropertyCondition cc = (PropertyCondition)c;
				TreeParent node = new TreeParent(cc);
				parent.addChild(node);
				addResourceNode(node,cc.getResource());
				node.addChild(new TreeObject(new KeyValueNode("property",cc.getProperty().toString())));
				node.addChild(new TreeObject(new KeyValueNode("value",""+cc.getValue())));
			}
			else if (c instanceof ComplexCondition) {
				ComplexCondition cc = (ComplexCondition)c;
				TreeParent node = new TreeParent(cc);
				parent.addChild(node);
				for (AbstractCondition part:cc.getParts()) {
					addNodes(node,part);
				}
			}
		}
		private void addNodes(TreeParent parent,Resource r) {
			parent.addChild(new TreeObject(new KeyValueNode("id",r.getId())));
			parent.addChild(new TreeObject(new KeyValueNode("type",r.getType().toString())));			
			if (r.getName()!=null) {
				parent.addChild(new TreeObject(new KeyValueNode("name",r.getName())));
			}
			else if (r.getRef()!=null) {
				parent.addChild(new TreeObject(new KeyValueNode("reference",r.getRef())));
			}
			
			// parent.addChild(new TreeObject(new KeyValueNode("value",""+r.getValue())));
		}
	}
	class ViewLabelProvider extends LabelProvider {
		
		private static final String TYPE_JAVA_CLASS = "http://www.massey.ac.nz/se/plugincontracts/java/InstantiableClass";
		private static final String TYPE_JAVA_INTERFACE = "http://www.massey.ac.nz/se/plugincontracts/java/AbstractType";
		
		private String toString(Condition c) {
			StringBuffer buf = new StringBuffer();
			buf.append(toString(c.getResource1()));
			buf.append(' ');
			String p = c.getRelationship().toString();
			p = p.substring(p.lastIndexOf('/')+1); // last token
			buf.append(p);
			buf.append(' ');
			buf.append(toString(c.getResource2()));
			return buf.toString();
		}
		private String toString(PropertyCondition c) {
			StringBuffer buf = new StringBuffer();
			buf.append(toString(c.getResource()));
			buf.append(' ');
			String p = c.getProperty().toString();
			p = p.substring(p.lastIndexOf('/')+1); // last token
			buf.append(p);
			buf.append(' ');
			buf.append(c.getValue());
			return buf.toString();
		}
		private String toString(Resource r) {
			if (r.getName()!=null) {
				return r.getName();
			}
			else {
				return r.getId();
			}
		}

		public String getText(Object n) {
			Object obj = ((TreeObject)n).getObject();
			if (obj instanceof Connector) {
				return ((Connector)obj).getId();
			}
			else if (obj instanceof Component) {
				return ((Component)obj).getId();
			}
			else if (obj instanceof SimpleContract) {
				URL url = ((SimpleContract)obj).getLocation();
				if (url==null) {
					return "a contract";
				}
				else {
					return url.getPath(); // context is defined by parent node
				}
			}
			else if (obj instanceof Resource) {
				Resource r = (Resource)obj;
				return toString(r);
			}
			else if (obj instanceof KeyValueNode) {
				KeyValueNode kvn = (KeyValueNode)obj;
				return kvn.key + ": " + kvn.value;
			}
			else if (obj instanceof Condition) {
				return toString((Condition)obj);
			}
			else if (obj instanceof PropertyCondition) {
				return toString((PropertyCondition)obj);
			}
			else if (obj instanceof ComplexCondition) {
				return ((ComplexCondition)obj).getConnective();
			}
			return obj.toString();
		}
		public Image getImage(Object n) {
			Object obj = ((TreeObject)n).getObject();
			
			if (obj instanceof Connector) {
				Connector c = (Connector)obj;
				if (c.getType()==ConnectorType.SUPPLIER) {
					return ICON_EXTENSION;
				}
				else if (c.getType()==ConnectorType.CONSUMER) {
					return ICON_EXTENSIONPOINT;
				}
			}
			else if (obj instanceof Component) {
				return ICON_PLUGIN;
			}			
			else if (obj instanceof Contract) {
				return ICON_CONTRACT;
			}
			else if (obj instanceof Condition) {
				return ICON_CONSTRAINT;
			}
			else if (obj instanceof Resource) {
				boolean var = ((Resource)obj).getName()==null;
				String type = ((Resource)obj).getType().toString();
				if (TYPE_JAVA_CLASS.equals(type)) {
					return var?ICON_CLASS_VAR:ICON_CLASS;
				}
				else if (TYPE_JAVA_INTERFACE.equals(type)) {
					return var?ICON_INTERFACE_VAR:ICON_INTERFACE;
				}
				else {
					return var?ICON_VARIABLE:ICON_CONSTANT;
				}
			}
			else if (obj instanceof KeyValueNode && (((KeyValueNode)obj).key.equals("relationship"))) { // relationships
				return ICON_LINK;
			}

			else if (n instanceof TreeParent) {
				return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
			}
			
			
			return null;
		}
		

	}

	/**
	 * The constructor.
	 */
	public ContractView() {
	}

	public ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, path); 
	}
	
	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);

		
		viewer.getTree().setHeaderVisible(true);
		
		TreeColumn col = new TreeColumn(viewer.getTree(), SWT.LEFT);
	    col.setText("Column 1");
	    col.setWidth(200);
	    
	    TreeColumn col2 = new TreeColumn(viewer.getTree(), SWT.LEFT);
	    col2.setText("Column 2");
	    col2.setWidth(200);
	    
	    
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(getViewSite());
		
	    
		makeActions();
		hookContextMenu();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				ContractView.this.fillContextMenu(manager);
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
		manager.add(new Separator());
		manager.add(actVerify);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(actRefresh);
		manager.add(actVerify);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(actRefresh);
		manager.add(actVerify);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		actRefresh = new Action() {
			public void run() {
				showMessage("Action 1 executed");
			}
		};
		actRefresh.setText("Action 1");
		actRefresh.setToolTipText("Action 1 tooltip");
		actRefresh.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		actVerify = new Action() {
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		actVerify.setText("Action 2");
		actVerify.setToolTipText("Action 2 tooltip");
		actVerify.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
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
		ICON_CONTRACT.dispose();
		ICON_CONSTRAINT.dispose();
		ICON_EXTENSION.dispose();
		ICON_EXTENSIONPOINT.dispose();
		ICON_CONSTANT.dispose();
		ICON_VARIABLE.dispose();
		ICON_LINK.dispose();
		ICON_CLASS.dispose();
		ICON_CLASS_VAR.dispose();
		ICON_INTERFACE.dispose();
		ICON_INTERFACE_VAR.dispose();
		super.dispose();
	}
}