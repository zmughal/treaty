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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.java.treaty.*;
import net.java.treaty.eclipse.*;
import net.java.treaty.eclipse.jobs.VerificationJob;
import net.java.treaty.eclipse.jobs.VerificationJobListener;
import net.java.treaty.VerificationResult;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.*;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
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
	private Action actVerifyAll;
	private Action actVerifySelected;
	private Action actPrintStackTrace;
	private Action actShowContractSource;
	// this is the model displayed - a list of plugins with contracts
	private Collection<EclipsePlugin> plugins = null;
	private Map<String,Image> icons = new HashMap<String,Image>();
	// strings 
	private static final String LABEL_INSTANCES = "contract instances";

	private void initModel() {
		ContractRepository.reset();
		plugins = ContractRepository.getDefault().getPluginsWithContracts();
	}
	
	enum OwnerType {
		extension, 
		extensionpoint
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
				node = new TreeParent(LABEL_INSTANCES);
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
				d.setProperty(VERIFICATION_RESULT, VerificationResult.FAILURE);
				d.setProperty(VERIFICATION_EXCEPTION, t);
				Logger.error("Error loading contract", t);
			}
		}
		
		private void addNodes(TreeParent parent,SimpleContract contract) {
			Map<Resource,OwnerType> ownerTypes = new HashMap<Resource,OwnerType>();
			for (Resource r:contract.getConsumerResources()) {
				ownerTypes.put(r,OwnerType.extensionpoint);				
			}
			for (Resource r:contract.getSupplierResources()) {
				ownerTypes.put(r,OwnerType.extension);				
			}
			
			TreeParent rNode = new TreeParent("extension point resources");
			parent.addChild(rNode);
			for (Resource r:contract.getConsumerResources()) {
				addResourceNode(rNode,r,ownerTypes);				
			}
			rNode = new TreeParent("extension resources");
			parent.addChild(rNode);
			for (Resource r:contract.getSupplierResources()) {
				addResourceNode(rNode,r,ownerTypes);	
			}
			
			for (AbstractCondition c:contract.getConstraints()) {
				// top level conjunction displayed as set
				if (c instanceof Conjunction) {
					for (AbstractCondition c2:((Conjunction)c).getParts()) {
						addNodes(parent,c2,ownerTypes);
					}
				}
				else addNodes(parent,c,ownerTypes);
			}
		}
		private void addResourceNode(TreeParent parent,Resource r,Map<Resource,OwnerType> ownerTypes) {
			TreeParent node = new TreeParent(r);
			parent.addChild(node);
			addNodes(node,r,ownerTypes);	
		}
		private void addNodes(TreeParent parent,AbstractCondition c,Map<Resource,OwnerType> ownerTypes) {
			if (c instanceof RelationshipCondition) {
				RelationshipCondition cc = (RelationshipCondition)c;
				TreeParent node = new TreeParent(cc);
				parent.addChild(node);
				addResourceNode(node,cc.getResource1(),ownerTypes);
				node.addChild(new TreeObject(new KeyValueNode("relationship",cc.getRelationship().toString())));
				addResourceNode(node,cc.getResource2(),ownerTypes);
			}
			else if (c instanceof PropertyCondition) {
				PropertyCondition cc = (PropertyCondition)c;
				TreeParent node = new TreeParent(cc);
				parent.addChild(node);
				addResourceNode(node,cc.getResource(),ownerTypes);
				node.addChild(new TreeObject(new KeyValueNode("property",cc.getProperty().toString())));
				node.addChild(new TreeObject(new KeyValueNode("value",""+cc.getValue())));
			}
			else if (c instanceof ExistsCondition) {
				ExistsCondition ec = (ExistsCondition)c;
				TreeParent node = new TreeParent(ec);
				parent.addChild(node);
				addResourceNode(node,ec.getResource(),ownerTypes);
			}
			else if (c instanceof ComplexCondition) {
				ComplexCondition cc = (ComplexCondition)c;
				TreeParent node = new TreeParent(cc);
				parent.addChild(node);
				for (AbstractCondition part:cc.getParts()) {
					addNodes(node,part,ownerTypes);
				}
			}
		}
		private void addNodes(TreeParent parent,Resource r,Map<Resource,OwnerType> ownerTypes) {
			parent.addChild(new TreeObject(new KeyValueNode("id",r.getId())));
			parent.addChild(new TreeObject(new KeyValueNode("type",r.getType().toString())));			
			if (r.getName()!=null) {
				parent.addChild(new TreeObject(new KeyValueNode("name",r.getName())));
			}
			else if (r.getRef()!=null) {
				parent.addChild(new TreeObject(new KeyValueNode("reference",r.getRef())));
			}
			OwnerType otype = ownerTypes.get(r);
			if (otype!=null) {
				parent.addChild(new TreeObject(new KeyValueNode("provided by",otype.toString())));			
			}
			
			// parent.addChild(new TreeObject(new KeyValueNode("value",""+r.getValue())));
		}
	}
	class ViewLabelProvider implements ITableLabelProvider {
		   
		private String toString(RelationshipCondition c) {
			StringBuffer buf = new StringBuffer();
			buf.append(toString(c.getResource1()));
			buf.append(' ');
			String p = c.getRelationship().toString();
			p = p.substring(p.lastIndexOf('#')+1); // last token
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
			p = p.substring(p.lastIndexOf('#')+1); // last token
			buf.append(p);
			buf.append(' ');
			buf.append(c.getValue());
			return buf.toString();
		}
		private String toString(ExistsCondition c) {
			StringBuffer buf = new StringBuffer();
			buf.append(toString(c.getResource()));
			buf.append(" must exist");
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
			else if (obj instanceof ExistsCondition) {
				return toString((ExistsCondition)obj);
			}
			else if (obj instanceof ComplexCondition) {
				return ((ComplexCondition)obj).getConnective();
			}
			return obj.toString();
		}
		// get the load/verification status as string
		private String getStatus(Object n) {
			if (n instanceof Annotatable) {
				Annotatable c = (Annotatable)n;
				Object status = c.getProperty(VERIFICATION_RESULT);
				if (c instanceof Constraint && !((Constraint)c).isInstantiated()) {
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
			if (n instanceof Annotatable) {
				Annotatable c = (Annotatable)n;
				Object status = c.getProperty(VERIFICATION_RESULT);
				if (c instanceof Constraint && !((Constraint)c).isInstantiated()) {
					return getIcon("status_notinstantiated.gif");
				}
				if (status==VerificationResult.FAILURE) {
					return getIcon("status_failure.gif");
				}
				else if (status==VerificationResult.SUCCESS) {
					return getIcon("status_success.gif");
				}
				else {
					return getIcon("status_open.gif");
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
					return getIcon("extension.gif");
				}
				else if (c.getType()==ConnectorType.CONSUMER) {
					return getIcon("extensionpoint.gif");
				}
			}
			else if (obj instanceof Component) {
				return getIcon("plugin.gif");
			}			
			else if (obj instanceof Contract) {
				return getIcon("contract.gif");
			}
			else if (obj instanceof AbstractCondition && !(obj instanceof ComplexCondition)) {
				return getIcon("constraint.gif");
			}
			else if (obj instanceof Resource) {
				boolean var = ((Resource)obj).getName()==null;
				Image icon = IconProvider.findIcon(((Resource)obj).getType(), var);
				if (icon!=null) {
					return icon;
				}
				else {
					return var?getIcon("variable.png"):getIcon("constant.png");
				}
			}
			else if (obj instanceof KeyValueNode && (((KeyValueNode)obj).key.equals("relationship"))) { // relationships
				return getIcon("link.gif");
			}

			else if (n instanceof TreeParent) {
				return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
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
	    
	    Rectangle bounds = viewer.getTree().getDisplay().getBounds();
	    col2.setWidth(Math.max(600,bounds.width-400));
	    
	    TreeColumn col1 = new TreeColumn(viewer.getTree(), SWT.LEFT);
	    col1.setText("status");
	    col1.setWidth(150);

		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(getViewSite());
		
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent e) {
				Object obj = getSelectedObject();
				boolean f = obj!=null && obj instanceof PropertySupport && ((PropertySupport)obj).getProperty(Constants.VERIFICATION_EXCEPTION)!=null;
				actPrintStackTrace.setEnabled(f);
				actShowContractSource.setEnabled(getSelectedContract()!=null);
				
				List<Contract> iConstracts = getSelectedInstantiatedContracts();
				actVerifySelected.setEnabled(iConstracts!=null&&!iConstracts.isEmpty());
			}});
		
		makeActions();
		hookContextMenu();
		contributeToActionBars();
	}

	private Object getSelectedObject() {
		TreeItem[] sel = viewer.getTree().getSelection();
		if (sel==null || sel.length==0) {
			return null;
		}
		return ((TreeObject)sel[0].getData()).getObject();
	}
	private SimpleContract getSelectedContract() {
		TreeItem[] sel = viewer.getTree().getSelection();
		if (sel==null || sel.length==0) {
			return null;
		}
		TreeObject to = (TreeObject)sel[0].getData();
		return findContract(to);
	}
	
	private SimpleContract findContract(TreeObject to) {
		Object obj = to.getObject();
		TreeObject parent = to.getParent();
		if (obj instanceof SimpleContract) {
			return (SimpleContract)obj;
		}
		else if (parent!=null) {
			return findContract(parent);
		}
		return null;
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
		manager.add(actVerifyAll);
		manager.add(actVerifySelected);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(actRefresh);
		manager.add(actVerifyAll);
		manager.add(actVerifySelected);
		manager.add(actPrintStackTrace);
		manager.add(actShowContractSource);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(actRefresh);
		manager.add(actVerifyAll);
		manager.add(actVerifySelected);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		actPrintStackTrace = new Action() {
			public void run() {
				actPrintStackTrace();
			}
		};
		actPrintStackTrace.setText("display verification exception details");
		actPrintStackTrace.setToolTipText("Displays the verification exception stack trace");
		actPrintStackTrace.setEnabled(false);
		
		actRefresh = new Action() {
			public void run() {
				actReset();
			}
		};
		actRefresh.setText("reset");
		actRefresh.setImageDescriptor(getImageDescriptor("icons/refresh.gif"));
		actRefresh.setToolTipText("Reloads all contracts and resets verification status of all contracts");
		
		actVerifyAll = new Action() {
			public void run() {
				actVerifyAll();
			}
		};
		actVerifyAll.setText("verify all");
		actVerifyAll.setImageDescriptor(getImageDescriptor("icons/verify_all.gif"));
		actVerifyAll.setToolTipText("run verification for all contracts");
		
		actVerifySelected = new Action() {
			public void run() {
				actVerifySelected();
			}
		};
		actVerifySelected.setText("verify selection");
		actVerifySelected.setImageDescriptor(getImageDescriptor("icons/verify_sel.gif"));
		actVerifySelected.setToolTipText("runs verification for selected contracts");
		actVerifySelected.setEnabled(false);
		
		actShowContractSource = new Action() {
			public void run() {
				actShowContractSource();
			}
		};
		actShowContractSource.setEnabled(false);
		actShowContractSource.setText("display contract source");
		actShowContractSource.setToolTipText("displays the XML source code of the contract");
	}

	private void actShowContractSource() {
		SimpleContract contract = getSelectedContract();
		if (contract!=null) {
			try {
				URL url = contract.getLocation();
				new ViewContractSourceDialog(new Shell(),url).open();
			}
			catch (Exception x) {}
		}
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
	 * Print the verification exception stack trace.
	 */
	private void actPrintStackTrace() {
		Object obj = this.getSelectedObject();
		if (obj!=null && obj instanceof PropertySupport && ((PropertySupport)obj).getProperty(Constants.VERIFICATION_EXCEPTION)!=null){
			Exception x = (Exception)((PropertySupport)obj).getProperty(Constants.VERIFICATION_EXCEPTION);
			if (x!=null) {
				try {
					new ViewExceptionStackTraceDialog(new Shell(),x).open();
				}
				catch (Exception xx) {}
			}
		}
	}
	private List<Contract> getSelectedInstantiatedContracts() {
		List<Contract> contracts = new ArrayList<Contract>();
		SimpleContract c = this.getSelectedContract();
		if (c!=null) {
			if (c.isInstantiated()) {
				contracts.add(c);
			}
			else {
				EclipseExtensionPoint xp = (EclipseExtensionPoint)c.getConsumer();
				addIContract(xp,contracts);
			}
		}
		else {
			TreeItem[] sel = viewer.getTree().getSelection();
			if (sel==null || sel.length==0) {
				return contracts;
			}
			Object obj =((TreeObject)sel[0].getData()).getObject(); // selected object
			TreeItem parent = sel[0].getParentItem();

			if (obj instanceof EclipseExtension) {
				addIContract((EclipseExtension)obj,contracts);
			}
			else if (obj instanceof EclipseExtensionPoint) {
				addIContract((EclipseExtensionPoint)obj,contracts);
			}
			else if (obj instanceof EclipsePlugin) {				
				EclipsePlugin p = (EclipsePlugin)obj;
				if (parent!=null && LABEL_INSTANCES.equals(parent.getData().toString())) {
					// folder for instances
					for (EclipseExtension x:p.getExtensions()) {
						addIContract(x,contracts);
					}
				}
				else {
					// parent of extension points
					for (EclipseExtensionPoint xp:p.getExtensionPoints()) {
						addIContract(xp,contracts);
					}
				}
			}
		}
		
		return contracts;
	}
	private void addIContract(EclipseExtension x,List<Contract> contracts) {
		Contract con = x.getContract();
		if (con!=null && con.isInstantiated()) {
			contracts.add(con);
		}	
	}
	private void addIContract(EclipseExtensionPoint xp,List<Contract> contracts) {
		for (EclipseExtension x:xp.getExtensions()) {
			addIContract(x,contracts);
		}
	}
	/**
	 * Run verification for selected contracts.
	 */
	private void actVerifySelected () {
		List<Contract> contracts = getSelectedInstantiatedContracts();
		if (contracts!=null && !contracts.isEmpty()) {
			verify(contracts);
		}
	}
	/**
	 * Run verification for all contracts.
	 */
	private void actVerifyAll () {
		// collect contracts
		final List<Contract> contracts = new ArrayList<Contract>();
		for (EclipsePlugin p:plugins) {
			contracts.addAll(p.getInstantiatedContracts());
		}
		verify(contracts);
	}
	/**
	 * Run verification.
	 */
	private void verify (List<Contract> contracts) {
		final VerificationReport verReport = new VerificationReport() {
			Contract contract = null;
			public Contract getContract() {
				return contract;
			}
			public void log(Object context, VerificationResult result,String... remarks) {
				if (context instanceof AbstractCondition) {
					((AbstractCondition)context).setProperty(VERIFICATION_RESULT,result);
				}
			}
			public void setContract(Contract contract) {
				this.contract=contract;
			}			
		};
		VerificationJobListener vListener = new VerificationJobListener() {
			public void verificationStatusChanged() {
				updateTree(false);
			}			
		};
	    IJobChangeListener jListener = new IJobChangeListener() {
	    	public void aboutToRun(IJobChangeEvent e) {}			
			public void awake(IJobChangeEvent e) {}			
			public void done(IJobChangeEvent e) {
				updateTree(false);
				VerificationJob vJob = (VerificationJob)e.getJob();
				reportVerificationResult(vJob.getDoneContracts(),vJob.getFailedContracts());
			}			
			public void running(IJobChangeEvent e) {}			
			public void scheduled(IJobChangeEvent e) {}			
			public void sleeping(IJobChangeEvent e) {}
		};
		ContractRepository.getDefault().verify(contracts,verReport,vListener,jListener);

	}
	private void reportVerificationResult(List<Contract> allContracts, List<Contract> failedContracts) {
		int c = allContracts.size();
		int f = failedContracts.size();
		String message = null;
		if (failedContracts.size()==0) {
			message = ""+c+" contract instances have been verified successfully";
		}
		else {
			message = ""+c+" contract instances checked, verification has failed for "
			+f+" contract instances. Verification status annotations have been added to the contract view.";	
		}
		final String m = message;
		Runnable r = new Runnable() {
			public void run() {			
				MessageDialog.openInformation(
					viewer.getControl().getShell(),
					"Verification result",
					m);
			}
		};
		viewer.getControl().getDisplay().syncExec(r);
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
	private Image getIcon(String name) {
		String path = "icons/"+name;
		Image icon = icons.get(path);
		if (icon==null) {
			icon = getImageDescriptor(path).createImage();
			icons.put(path,icon);
		}
		return icon;
	}

	@Override
	public void dispose() {
		for (Image icon:icons.values()) {
			icon.dispose();
		}
		super.dispose();
	}
	
}