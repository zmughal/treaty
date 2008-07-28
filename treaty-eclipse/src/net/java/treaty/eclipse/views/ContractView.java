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


/**
 * Contract viewer.
 * @author Jens Dietrich
 */

public class ContractView extends ViewPart {
	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action action1;
	private Action action2;
	
	private Image ICON_PLUGIN = this.getImageDescriptor("icons/plugin.gif").createImage();
	private Image ICON_EXTENSION = this.getImageDescriptor("icons/extension.gif").createImage();
	private Image ICON_EXTENSIONPOINT = this.getImageDescriptor("icons/extensionpoint.gif").createImage();
	private Image ICON_CONTRACT = this.getImageDescriptor("icons/contract.gif").createImage();
	private Image ICON_CONSTRAINT = this.getImageDescriptor("icons/constraint.gif").createImage();
	private Image ICON_RESOURCE = this.getImageDescriptor("icons/resource.gif").createImage();

	class ExtensionPointResource {
		Resource r = null;
		public ExtensionPointResource(Resource r) {
			super();
			this.r = r;
		}		
	}  
	class ExtensionResource {
		Resource r = null;
		public ExtensionResource(Resource r) {
			super();
			this.r = r;
		}		
	}  
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
			}				
		}
		
		private void addNodes(TreeParent parent,SimpleContract contract) {
			TreeParent rNode = new TreeParent("extension point resources");
			parent.addChild(rNode);
			for (Resource r:contract.getConsumerResources()) {
				TreeParent node = new TreeParent(new ExtensionPointResource(r));
				rNode.addChild(node);
				addNodes(node,r);				
			}
			rNode = new TreeParent("extension resources");
			parent.addChild(rNode);
			for (Resource r:contract.getSupplierResources()) {
				TreeParent node = new TreeParent(new ExtensionResource(r));
				rNode.addChild(node);
				addNodes(node,r);	
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
		private void addNodes(TreeParent parent,AbstractCondition c) {
			if (c instanceof Condition) {
				Condition cc = (Condition)c;
				parent.addChild(new TreeObject(new KeyValueNode("resource 1",cc.getResource1().getId())));
				parent.addChild(new TreeObject(new KeyValueNode("predicate",cc.getRelationship().toString())));
				parent.addChild(new TreeObject(new KeyValueNode("resource 2",cc.getResource1().getId())));
			}
			else if (c instanceof ComplexCondition) {
				
			}
		}
		private void addNodes(TreeParent parent,Resource r) {
			parent.addChild(new TreeObject(new KeyValueNode("type",r.getType().toString())));
			parent.addChild(new TreeObject(new KeyValueNode("id",r.getType().toString())));
			if (r.getName()!=null) {
				parent.addChild(new TreeObject(new KeyValueNode("name",r.getName())));
			}
			else if (r.getRef()!=null) {
				parent.addChild(new TreeObject(new KeyValueNode("ref",r.getRef())));
			}
			
			// parent.addChild(new TreeObject(new KeyValueNode("value",""+r.getValue())));
		}
	}
	class ViewLabelProvider extends LabelProvider {

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
					return url.toString();
				}
			}
			else if (obj instanceof ExtensionResource) {
				return ((ExtensionResource)obj).r.getId();
			}
			else if (obj instanceof ExtensionPointResource) {
				return ((ExtensionPointResource)obj).r.getId();
			}
			else if (obj instanceof KeyValueNode) {
				KeyValueNode kvn = (KeyValueNode)obj;
				return kvn.key + ": " + kvn.value;
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
			else if (obj instanceof ExtensionResource) {
				return ICON_RESOURCE;
			}
			else if (obj instanceof ExtensionPointResource) {
				return ICON_RESOURCE;
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
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				showMessage("Action 1 executed");
			}
		};
		action1.setText("Action 1");
		action1.setToolTipText("Action 1 tooltip");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		action2 = new Action() {
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
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
		ICON_RESOURCE.dispose();
		super.dispose();
	}
}