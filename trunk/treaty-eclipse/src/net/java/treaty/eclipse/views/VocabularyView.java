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
import net.java.treaty.eclipse.*;
import net.java.treaty.verification.ContractVocabulary;
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
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;


/**
 * Contract viewer.
 * @author Jens Dietrich
 */

public class VocabularyView extends ViewPart {
	
	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action actRefresh;
	
	private Image ICON_PLUGIN = this.getImageDescriptor("icons/plugin.gif").createImage();
	private Image ICON_EXTENSION = this.getImageDescriptor("icons/extension.gif").createImage();
	private Image ICON_PREDICATE = this.getImageDescriptor("icons/link.gif").createImage();
	private Image ICON_TYPE = this.getImageDescriptor("icons/class.gif").createImage();
	
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
			IExtensionRegistry registry = org.eclipse.core.runtime.Platform.getExtensionRegistry();
			IExtensionPoint xp = registry.getExtensionPoint("net.java.treaty.eclipse.vocabulary");
			for (IExtension x:xp.getExtensions()) {
				TreeParent node = new TreeParent(x.getContributor());
				parent.addChild(node);	
				addNodes(node,x);								
			}		
		}
		private void addNodes(TreeParent parent,IExtension x) {
			ContractVocabulary voc = loadVocabulary(x);
			if (voc!=null) {	
				addNodes(parent,voc);	
			}
		}
		private void addNodes(TreeParent parent,ContractVocabulary voc) {
			TreeParent node = new TreeParent("contributed types");
			parent.addChild(node);	
			for (URI type:voc.getContributedTypes()) {
				TreeObject n = new TreeObject(type);
				node.addChild(n);
			};
			node = new TreeParent("contributed predicates");
			parent.addChild(node);	
			for (URI type:voc.getContributedPredicates()) {
				TreeObject n = new TreeObject(type);
				node.addChild(n);
			};
		}
		
		private ContractVocabulary loadVocabulary(IExtension x) {
			IConfigurationElement[] attributes = x.getConfigurationElements();
			String pluginId = x.getContributor().getName();
			for (int j=0;j<attributes.length;j++) {
				IConfigurationElement p = attributes[j];
				String clazz = p.getAttribute("class");
				// FIXME
				System.out.println(clazz);
				try {
					Class c = Platform.getBundle(pluginId).loadClass(clazz);
					return (ContractVocabulary)c.newInstance();
				}
				catch (Exception ex) {
					// FIXME
					ex.printStackTrace();
				}
			}
			return null;
		}
		
		
	}
	class ViewLabelProvider extends LabelProvider {
		
		public String getText(Object n) {
			Object obj = ((TreeObject)n).getObject();
			if (obj instanceof IExtension) {
				String id = ((IExtension)obj).getUniqueIdentifier();
				return id==null?obj.toString():id;
			}
			else if (obj instanceof IContributor) {
				return ((IContributor)obj).getName();
			}
			else return obj.toString();
		}
		public Image getImage(Object n) {
			Object obj = ((TreeObject)n).getObject();
			
			if (obj instanceof IContributor) {
				return ICON_PLUGIN;
			}		
			else if (obj instanceof IExtension) {
				return ICON_EXTENSION;
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
				viewer.setContentProvider(new ViewContentProvider());
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
		ICON_EXTENSION.dispose();
		ICON_PREDICATE.dispose();
		ICON_TYPE.dispose();
		super.dispose();
	}
}