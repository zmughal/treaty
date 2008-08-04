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
import java.util.List;
import net.java.treaty.*;
import net.java.treaty.eclipse.*;
import net.java.treaty.VerificationResult;
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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import static net.java.treaty.eclipse.Constants.VERIFICATION_RESULT;
import static net.java.treaty.eclipse.Constants.VERIFICATION_EXCEPTION;


/**
 * Contract viewer.
 * @author Jens Dietrich
 */

public class ContractView extends ViewPart {
	
	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action actRefresh;
	private Action actVerify;
	// this is the model displayed - a list of plugins with contracts
	private Collection<EclipsePlugin> plugins = null;

	private void initModel() {
		plugins = new Builder().extractContracts();
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

	class ViewContentProvider implements IStructuredContentProvider, ITreeContentProvider {
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
			if (plugins==null) {
				initModel();
			}
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
				x.setContract(instantiatedContract);
				TreeParent node2 = new TreeParent(instantiatedContract);
				node.addChild(node2);
				addNodes(node2,instantiatedContract);
			}
			catch (Exception t) {
				SimpleContract d = new SimpleContract() ; // dummy contract
				x.setContract(d);
				d.addProperty(VERIFICATION_RESULT, VerificationResult.FAILURE);
				d.addProperty(VERIFICATION_EXCEPTION, t);
				// TODO
				TreeObject errorObject = new TreeObject(d);
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
			if (c instanceof RelationshipCondition) {
				RelationshipCondition cc = (RelationshipCondition)c;
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
	class ViewLabelProvider implements ITableLabelProvider {
		// FIXME here we have hard coded dependencies to vocabulary extensions
		// the icons should be supplied by the respective plugins
		private static final String TYPE_JAVA_CLASS = "http://www.massey.ac.nz/se/plugincontracts/java/InstantiableClass";
		private static final String TYPE_JUNIT= "http://www.massey.ac.nz/se/plugincontracts/junit/TestCase";
		private static final String TYPE_JAVA_INTERFACE = "http://www.massey.ac.nz/se/plugincontracts/java/AbstractType";
		
		// icons
		private Image ICON_PLUGIN = getImageDescriptor("icons/plugin.gif").createImage();
		private Image ICON_EXTENSION = getImageDescriptor("icons/extension.gif").createImage();
		private Image ICON_EXTENSIONPOINT = getImageDescriptor("icons/extensionpoint.gif").createImage();
		private Image ICON_CONTRACT = getImageDescriptor("icons/contract.gif").createImage();
		private Image ICON_CONSTRAINT = getImageDescriptor("icons/constraint.gif").createImage();
		private Image ICON_CONSTANT = getImageDescriptor("icons/constant.png").createImage();
		private Image ICON_VARIABLE = getImageDescriptor("icons/variable.png").createImage();
		private Image ICON_LINK = getImageDescriptor("icons/link.gif").createImage();
		private Image ICON_CLASS = getImageDescriptor("icons/class.gif").createImage();
		private Image ICON_CLASS_VAR = getImageDescriptor("icons/class_var.gif").createImage();
		private Image ICON_INTERFACE = getImageDescriptor("icons/interface.gif").createImage();
		private Image ICON_INTERFACE_VAR = getImageDescriptor("icons/interface_var.gif").createImage();
		private Image ICON_JUNIT = getImageDescriptor("icons/junit.gif").createImage();
		private Image ICON_STATUS_FAILURE = getImageDescriptor("icons/status_failure.gif").createImage();
		private Image ICON_STATUS_SUCCESS = getImageDescriptor("icons/status_success.gif").createImage();
		private Image ICON_STATUS_OPEN = getImageDescriptor("icons/status_open.gif").createImage();
		   
		private String toString(RelationshipCondition c) {
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

		public String getColumnText(Object n, int col) {
			
			Object obj = ((TreeObject)n).getObject();
			if (col==1) return getStatus(obj);
			
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
			else if (obj instanceof RelationshipCondition) {
				return toString((RelationshipCondition)obj);
			}
			else if (obj instanceof PropertyCondition) {
				return toString((PropertyCondition)obj);
			}
			else if (obj instanceof ComplexCondition) {
				return ((ComplexCondition)obj).getConnective();
			}
			return obj.toString();
		}
		// get the load/verification status as string
		private String getStatus(Object n) {
			if (n instanceof Constraint) {
				Constraint c = (Constraint)n;
				Object status = c.getAnnotation(VERIFICATION_RESULT);
				if (!c.isInstantiated()) {
					return "not instantiated";
				}
				if (status==VerificationResult.FAILURE) {
					return "failure";
				}
				else if (status==VerificationResult.SUCCESS) {
					return "success";
				}
				else {
					return "not verified";
				}
			}
			return "";
		}
		// get the load/verification status icon
		private Image getStatusIcon(Object n) {
			if (n instanceof Constraint) {
				Constraint c = (Constraint)n;
				Object status = c.getAnnotation(VERIFICATION_RESULT);
				if (!c.isInstantiated()) {
					return null;
				}
				if (status==VerificationResult.FAILURE) {
					return ICON_STATUS_FAILURE;
				}
				else if (status==VerificationResult.SUCCESS) {
					return ICON_STATUS_SUCCESS;
				}
				else {
					return ICON_STATUS_OPEN;
				}
			}
			return null;
		}
		public Image getColumnImage(Object n, int col) {
			Object obj = ((TreeObject)n).getObject();
			if (col==1) {
				return getStatusIcon(obj);
			}
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
			else if (obj instanceof RelationshipCondition) {
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
				else if (TYPE_JUNIT.equals(type) && !var) {
					return ICON_JUNIT;
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
		@Override
		public void addListener(ILabelProviderListener arg0) {
			
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
			ICON_JUNIT.dispose();
			ICON_STATUS_FAILURE.dispose();
			ICON_STATUS_SUCCESS.dispose();
			ICON_STATUS_OPEN.dispose();
		}
		@Override
		public boolean isLabelProperty(Object arg0, String arg1) {
			return false;
		}
		@Override
		public void removeListener(ILabelProviderListener arg0) {
			
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
		
		TreeColumn col2 = new TreeColumn(viewer.getTree(), SWT.LEFT);
	    col2.setText("plugin contracts");
	    col2.setWidth(600);
	    
	    TreeColumn col1 = new TreeColumn(viewer.getTree(), SWT.LEFT);
	    col1.setText("status");
	    col1.setWidth(200);

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
				actReset();
			}
		};
		actRefresh.setText("reset");
		actRefresh.setImageDescriptor(getImageDescriptor("icons/refresh.gif"));
		actRefresh.setToolTipText("Reloads all contracts and resets verification status of all contracts");
		
		actVerify = new Action() {
			public void run() {
				actVerify();
			}
		};
		actVerify.setText("verify");
		actVerify.setImageDescriptor(getImageDescriptor("icons/verify.gif"));
		actVerify.setToolTipText("runs verification");
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
	/**
	 * Reset model, reload all contracts.
	 */
	private void actReset() {
		this.initModel();
		this.viewer.setContentProvider(new ViewContentProvider());
		viewer.setInput(getViewSite());
	}
	/**
	 * Run verification.
	 */
	private void actVerify () {
		// collect contracts
		final List<Contract> contracts = new ArrayList<Contract>();
		for (EclipsePlugin p:plugins) {
			contracts.addAll(p.getInstantiatedContracts());
		}
		final VerificationReport dummyReport = new VerificationReport() {
			@Override
			public Contract getContract() {
				return null;
			}
			@Override
			public void log(Object context, VerificationResult result,String... remarks) {
				if (context instanceof PropertySupport) {
					((PropertySupport)context).addProperty(VERIFICATION_RESULT,result);
				}
			}
			@Override
			public void setContract(Contract contract) {				
			}			
		};
	    final Job job = new Job("Treaty component verification") {
	         protected IStatus run(IProgressMonitor monitor) {
	        	monitor.beginTask("run verification", (2*contracts.size())+3);
	        	
	        	monitor.subTask("Reset verification status");
	        	for (Contract c:contracts) {
	        		resetVerificationStatus(c);
	        	}
	        	updateTree(false);
	        	if (monitor.isCanceled()) return Status.CANCEL_STATUS;
	        	
	        	monitor.subTask("loading installed vocabularies");
	        	EclipseVerifier verifier = new EclipseVerifier();
	        	monitor.worked(3);
	        	
	        	// loading resources
	        	for (Contract c:contracts) {
	        		//System.out.println("loading resources in " + c);
	        		try {
						loadResources(c,verifier);
					} catch (ResourceLoaderException e) {
						c.addProperty(VERIFICATION_RESULT,VerificationResult.FAILURE);
						c.addProperty(VERIFICATION_EXCEPTION,e);
					}
	        		monitor.worked(1);
	        		if (monitor.isCanceled()) return Status.CANCEL_STATUS;
	        	}
	        	
	        	monitor.subTask("checking contracts");
	        	for (Contract c:contracts) {
	        		System.out.println("checking contract " + c);
	        		c.check(dummyReport, verifier);
	        		monitor.worked(1);
	        		updateTree(false);
	        		if (monitor.isCanceled()) return Status.CANCEL_STATUS;
	        	}
	        	return Status.OK_STATUS;	        	 
	         }


	    };
	    job.addJobChangeListener(new IJobChangeListener() {

			@Override
			public void aboutToRun(IJobChangeEvent e) {}

			@Override
			public void awake(IJobChangeEvent e) {}

			@Override
			public void done(IJobChangeEvent e) {
				updateTree(false);
			}

			@Override
			public void running(IJobChangeEvent e) {
			}

			@Override
			public void scheduled(IJobChangeEvent e) {}

			@Override
			public void sleeping(IJobChangeEvent e) {}
		});
	    
	    job.schedule();

	}
	private void resetVerificationStatus(Contract c) {
		c.removeProperty(VERIFICATION_RESULT);
		c.removeProperty(VERIFICATION_EXCEPTION);
		if (c instanceof AggregatedContract) {
			AggregatedContract ac = (AggregatedContract)c;
			for (Contract part:ac.getParts()) {
				resetVerificationStatus(part);
			}
		}
		else if (c instanceof SimpleContract) {
			SimpleContract sc = (SimpleContract)c;
			for (AbstractCondition cond:sc.getConstraints()) {
				resetVerificationStatus(cond);
			}
		}
	}

	private void resetVerificationStatus(AbstractCondition c) {
		c.removeProperty(VERIFICATION_RESULT);
		c.removeProperty(VERIFICATION_EXCEPTION);
		if (c instanceof ComplexCondition) {
			ComplexCondition cc = (ComplexCondition)c;
			for (AbstractCondition part:cc.getParts()) {
				resetVerificationStatus(part);
			}
		}
	}

	// refreshes the tree labels
	private void updateTree(boolean sync) {
		Runnable r = new Runnable() {
			public void run() {
				viewer.refresh(true);
			}
		};
		if (sync) viewer.getTree().getDisplay().syncExec(r);
		else viewer.getTree().getDisplay().asyncExec(r);
	}
	private void loadResources(Contract c,ResourceLoader l) throws ResourceLoaderException {
		if (c instanceof AggregatedContract) {
			for (Contract part:((AggregatedContract)c).getParts()) {
				loadResources(part,l);
			}
		}
		else if (c instanceof SimpleContract) {
			SimpleContract sc = (SimpleContract)c;
			for (Resource r:sc.getConsumerResources()) {
				loadResource(sc,l,c.getConsumer(),r);
			}
			for (Resource r:sc.getSupplierResources()) {
				loadResource(sc,l,c.getSupplier(),r);
			}
		}
	}
	private void loadResource(SimpleContract sc,ResourceLoader l,Connector con,Resource r) throws ResourceLoaderException {
			if (!r.isLoaded()) {
					Object value = l.load(r.getType(), r.getName(), con);
					r.setValue(value);
			}
	}
}