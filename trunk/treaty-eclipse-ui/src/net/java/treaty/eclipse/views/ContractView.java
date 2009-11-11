/*
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.views;

import static net.java.treaty.eclipse.Constants.VERIFICATION_EXCEPTION;
import static net.java.treaty.eclipse.Constants.VERIFICATION_RESULT;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.java.treaty.AbstractCondition;
import net.java.treaty.Annotatable;
import net.java.treaty.ComplexCondition;
import net.java.treaty.Component;
import net.java.treaty.Conjunction;
import net.java.treaty.Connector;
import net.java.treaty.ConnectorType;
import net.java.treaty.Contract;
import net.java.treaty.ExistsCondition;
import net.java.treaty.Negation;
import net.java.treaty.PropertyCondition;
import net.java.treaty.PropertySupport;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.Resource;
import net.java.treaty.VerificationReport;
import net.java.treaty.VerificationResult;
import net.java.treaty.contractregistry.ContractRegistryListener;
import net.java.treaty.eclipse.Constants;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.EclipseExtensionPoint;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.Exporter;
import net.java.treaty.eclipse.Logger;
import net.java.treaty.eclipse.contractregistry.EclipseContractRegistry;
import net.java.treaty.eclipse.jobs.VerificationJob;
import net.java.treaty.eclipse.jobs.VerificationJobListener;
import net.java.treaty.eclipse.ui.Activator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * <p>
 * Contract viewer of Eclipse Treaty implementation.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class ContractView extends ViewPart implements ContractRegistryListener {

	/**
	 * <p>
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view, or
	 * ignore it and always show the same content (like Task List, for example).
	 * </p>
	 * 
	 * <p>
	 * TreeObject implements such a wrapper.
	 * </p>
	 * 
	 * @author Jens Dietrich
	 */
	private class TreeObject implements IAdaptable {

		/** The wrapped object of this {@link TreeObject}. */
		private Object object;

		/** The {@link TreeParent} of this {@link TreeObject} (if any). */
		private TreeParent parent;

		/**
		 * <p>
		 * Creates a new {@link TreeObject} wrapper for a given {@link Object}.
		 * </p>
		 * 
		 * @param object
		 *          The {@link Object} that shall be wrapped.
		 */
		public TreeObject(Object object) {

			this.object = object;
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
		 */
		@SuppressWarnings("unchecked")
		public Object getAdapter(Class key) {

			return null;
		}

		/**
		 * <p>
		 * Returns the wrapped Object of this {@link TreeObject}.
		 * </p>
		 * 
		 * @return The wrapped Object of this {@link TreeObject}.
		 */
		public Object getObject() {

			return this.object;
		}

		/**
		 * <p>
		 * Returns the {@link TreeParent} of this {@link TreeObject}.
		 * </p>
		 * 
		 * @return The {@link TreeParent} of this {@link TreeObject}.
		 */
		public TreeParent getParent() {

			return parent;
		}

		/**
		 * <p>
		 * Sets the {@link TreeParent} of this {@link TreeObject}.
		 * </p>
		 * 
		 * @param parent
		 *          The {@link TreeParent} of this {@link TreeObject}.
		 */
		public void setParent(TreeParent parent) {

			this.parent = parent;
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {

			return getObject().toString();
		}
	}

	/**
	 * <p>
	 * {@link TreeParent}s are {@link TreeObject}s that contain other
	 * {@link TreeObject}s.
	 * </p>
	 * 
	 * @author Jens Dietrich
	 */
	private class TreeParent extends TreeObject {

		/** The children {@link TreeObject}s of this {@link TreeParent}. */
		private ArrayList<TreeObject> children;

		/**
		 * <p>
		 * Creates a new {@link TreeParent} for a given {@link Object}.
		 * </p>
		 * 
		 * @param object
		 *          The {@link Object} that shall be wrapped.
		 */
		public TreeParent(Object object) {

			super(object);

			this.children = new ArrayList<TreeObject>();
		}

		/**
		 * <p>
		 * Adds a given {@link TreeObject} as child to this {@link TreeParent}.
		 * </p>
		 * 
		 * @param child
		 *          The {@link TreeObject} that shall be added.
		 */
		public void addChild(TreeObject child) {

			this.children.add(child);
			child.setParent(this);
		}

		/**
		 * <p>
		 * Returns all children {@link TreeObject}s of this {@link TreeParent}.
		 * </p>
		 * 
		 * @return All children {@link TreeObject}s of this {@link TreeParent}.
		 */
		public TreeObject[] getChildren() {

			return (TreeObject[]) this.children.toArray(new TreeObject[this.children
					.size()]);
		}

		/**
		 * <p>
		 * Returns <code>true</code> if this {@link TreeParent} has children.
		 * </p>
		 * 
		 * @return <code>true</code> if this {@link TreeParent} has children.
		 */
		public boolean hasChildren() {

			return children.size() > 0;
		}
	}

	/**
	 * <p>
	 * The {@link ViewContentProvider} is used to provide the content of all
	 * elements that shall be displayed in this {@link ContractView}.
	 * </p>
	 * 
	 * @author Jens Dietrich
	 */
	private class ViewContentProvider implements IStructuredContentProvider,
			ITreeContentProvider {

		/** The root {@link TreeParent} of the view. */
		private TreeParent invisibleRoot;

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		public void dispose() {

			/* Remains empty. */
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
		 * Object)
		 */
		public Object[] getChildren(Object parent) {

			Object[] result;

			/* Check if the given parent is a TreeParent. */
			if (parent instanceof TreeParent) {

				/* Delegate operation. */
				result = ((TreeParent) parent).getChildren();
			}

			/* Else return an empty array. */
			else {
				result = new Object[0];
			}
			// end else.

			return result;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java
		 * .lang.Object)
		 */
		public Object[] getElements(Object parent) {

			Object[] result;

			/* If the parent is the view, return the root object's children. */
			if (parent.equals(getViewSite())) {

				/* Probably initialize the root object. */
				if (this.invisibleRoot == null) {
					initialize();
				}
				// no else.

				result = this.getChildren(invisibleRoot);
			}

			/* Else return the children of the given parent. */
			else {
				result = getChildren(parent);
			}

			return result;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object
		 * )
		 */
		public Object getParent(Object child) {

			Object result;
			result = null;

			/* For TreeObjects return their parents. */
			if (child instanceof TreeObject) {
				result = ((TreeObject) child).getParent();
			}
			// no else.

			return result;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.
		 * Object)
		 */
		public boolean hasChildren(Object parent) {

			boolean result;
			result = false;

			if (parent instanceof TreeParent) {
				result = ((TreeParent) parent).hasChildren();
			}
			// no else.

			return result;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
		 * .viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {

			/* Remains empty. */
		}

		/**
		 * <p>
		 * Initializes this {@link ViewContentProvider}.
		 * </p>
		 */
		private void initialize() {

			/* Create an empty root object. */
			this.invisibleRoot = new TreeParent("");
			this.addPluginNodes(this.invisibleRoot);
		}

		/**
		 * <p>
		 * A helper method that adds nodes for all {@link EclipsePlugin}s (and
		 * recursively for their children) to a given {@link TreeParent}.
		 * </p>
		 * 
		 * @param parent
		 *          The {@link TreeParent} to which the nodes shall be added.
		 */
		private void addPluginNodes(TreeParent parent) {

			for (EclipsePlugin plugin : plugins) {
				TreeParent node;
				node = new TreeParent(plugin);

				parent.addChild(node);
				this.addExtensionPointNodes(node, plugin);
			}
		}

		/**
		 * <p>
		 * A helper method that adds nodes for all {@link EclipseExtensionPoint}s
		 * (and recursively for their children) from a given {@link EclipsePlugin}
		 * to a given {@link TreeParent}.
		 * </p>
		 * 
		 * @param parent
		 *          The {@link TreeParent} to which the nodes shall be added.
		 * @param plugin
		 *          The {@link EclipsePlugin} whose {@link EclipseExtensionPoint}s
		 *          shall be added.
		 */
		private void addExtensionPointNodes(TreeParent parent, EclipsePlugin plugin) {

			for (EclipseExtensionPoint eclipseExtensionPoint : plugin
					.getExtensionPoints()) {

				if (eclipseExtensionPoint.hasContracts()) {
					TreeParent node;
					node = new TreeParent(eclipseExtensionPoint);

					parent.addChild(node);
					this.addDefinedContractNodes(node, eclipseExtensionPoint);
				}
				// no else.
			}
			// end for.
		}

		/**
		 * <p>
		 * A helper method that adds nodes for all defined {@link Contracts}s (and
		 * recursively for their children) from a given
		 * {@link EclipseExtensionPoint} to a given {@link TreeParent}.
		 * </p>
		 * 
		 * @param parent
		 *          The {@link TreeParent} to which the nodes shall be added.
		 * @param eclipseExtensionPoint
		 *          The {@link EclipseExtensionPoint} whose {@link Contracts} s
		 *          shall be added.
		 */
		private void addDefinedContractNodes(TreeParent parent,
				EclipseExtensionPoint eclipseExtensionPoint) {

			/* Iterate through all contracts of the extension point. */
			for (Contract contract : eclipseExtensionPoint.getContracts()) {

				TreeParent node;
				node = new TreeParent(contract);

				/* Add the contract node. */
				parent.addChild(node);
				this.addContractContentNodes(node, contract);

				/* Add a node for instantiating extensions. */
				node = new TreeParent(LABEL_INSTANCES);
				parent.addChild(node);

				/* Add children nodes for all extensions that instantiate the contract. */
				for (EclipseExtension eclipseExtensions : eclipseExtensionPoint
						.getExtensions()) {
					// TreeParent node2 = new TreeParent(x.getOwner());
					// node.addChild(node2);
					this.addContractedExtensionNodes(node, eclipseExtensions);
				}
				// end for (extensions).
			}
			// end for (contracts).
		}

		/**
		 * <p>
		 * A helper method that adds nodes for all {@link Contract}s (and
		 * recursively for their children) from a given {@link EclipseExtension} to
		 * a given {@link TreeParent}.
		 * </p>
		 * 
		 * @param parent
		 *          The {@link TreeParent} to which the nodes shall be added.
		 * @param eclipseExtesnsion
		 *          The {@link EclipseExtension} whose {@link Contracts} s shall be
		 *          added.
		 */
		private void addContractedExtensionNodes(TreeParent parent,
				EclipseExtension eclipseExtesnsion) {

			TreeParent node;
			node = new TreeParent(eclipseExtesnsion);

			parent.addChild(node);

			for (Contract contract : eclipseExtesnsion.getContracts()) {
				TreeParent node2;
				node2 = new TreeParent(contract);

				node.addChild(node2);
				this.addInstantiatedContractNodes(node2, contract);
			}
			// end for.
		}

		/**
		 * <p>
		 * A helper method that adds nodes for a {@link Contract}s instantiated by
		 * an {@link EclipseExtension} to a given {@link TreeParent}.
		 * </p>
		 * 
		 * @param parent
		 *          The {@link TreeParent} to which the nodes shall be added.
		 * @param contract
		 *          The {@link Contracts} that shall be added.
		 */
		private void addInstantiatedContractNodes(TreeParent parent,
				Contract contract) {

			TreeParent node;
			node = new TreeParent(contract);

			parent.addChild(node);
			this.addContractContentNodes(node, contract);
		}

		/**
		 * <p>
		 * A helper method that adds nodes for all content (and recursively for
		 * their children) from a given {@link Contract} to a given
		 * {@link TreeParent}.
		 * </p>
		 * 
		 * @param parent
		 *          The {@link TreeParent} to which the nodes shall be added.
		 * @param contract
		 *          The {@link Contracts} whose content shall be added.
		 */
		private void addContractContentNodes(TreeParent parent, Contract contract) {

			Map<Resource, OwnerType> ownerTypes;
			ownerTypes = new HashMap<Resource, OwnerType>();

			for (Resource r : contract.getConsumerResources()) {
				ownerTypes.put(r, OwnerType.extensionpoint);
			}

			for (Resource r : contract.getSupplierResources()) {
				ownerTypes.put(r, OwnerType.extension);
			}

			for (Resource r : contract.getExternalResources()) {
				ownerTypes.put(r, OwnerType.thirdparty);
			}

			TreeParent contractRootNode = null;

			/* Probably add extension point resources. */
			if (contract.getConsumerResources().size() > 0) {

				contractRootNode =
						new TreeParent(getResourceLabel("extension point resources",
								contract.getConsumer()));
				parent.addChild(contractRootNode);

				for (Resource r : contract.getConsumerResources()) {
					this.addResourceNode(contractRootNode, r, ownerTypes);
				}
			}
			// no else.

			/* Probably add extension resources. */
			if (contract.getSupplierResources().size() > 0) {
				contractRootNode =
						new TreeParent(getResourceLabel("extension resources", contract
								.getSupplier()));
				parent.addChild(contractRootNode);

				for (Resource r : contract.getSupplierResources()) {
					this.addResourceNode(contractRootNode, r, ownerTypes);
				}
			}
			// no else.

			/* Probably add external resources. */
			if (contract.getExternalResources().size() > 0) {

				contractRootNode =
						new TreeParent(getResourceLabel("third party resources", contract
								.getOwner()));
				parent.addChild(contractRootNode);

				for (Resource r : contract.getExternalResources()) {
					this.addResourceNode(contractRootNode, r, ownerTypes);
				}
			}
			// no else.

			/* Add nodes for the contracts conditions. */
			for (AbstractCondition c : contract.getConstraints()) {

				/* The top level conjunction is displayed as set. */
				if (c instanceof Conjunction) {

					for (AbstractCondition c2 : ((Conjunction) c).getParts()) {
						this.addConditionNodes(parent, c2, ownerTypes);
					}
				}

				else {
					this.addConditionNodes(parent, c, ownerTypes);
				}
				// end else.
			}
			// end for.
		}

		/**
		 * <p>
		 * A helper method that add a node for a given {@link Resource}.
		 * </p>
		 * 
		 * @param parent
		 *          The {@link TreeParent} to that the node shall be added.
		 * @param resource
		 *          The {@link Resource} for that a node shall be added.
		 * @param ownerTypes
		 *          A Map containing all {@link Resource}s belonging to this
		 *          {@link Resource}.
		 */
		private void addResourceNode(TreeParent parent, Resource resource,
				Map<Resource, OwnerType> ownerTypes) {

			if (resource == null) {
				TreeObject node = new TreeObject(resource);
				parent.addChild(node);
			}

			else {
				TreeParent node = new TreeParent(resource);
				parent.addChild(node);

				this.addConditionPartNodes(node, resource, ownerTypes);
			}
		}

		/**
		 * <p>
		 * A helper method that adds nodes for a given {@link Map} of resources and
		 * a given {@link AbstractCondition} to a given {@link TreeParent}.
		 * </p>
		 * 
		 * @param parent
		 *          The {@link TreeParent} to that the nodes shall be added.
		 * @param condition
		 *          The {@link AbstractCondition}.
		 * @param ownerTypes
		 *          The given {@link Resource}s and {@link OwnerType}s.
		 */
		private void addConditionNodes(TreeParent parent,
				AbstractCondition condition, Map<Resource, OwnerType> ownerTypes) {

			if (condition instanceof RelationshipCondition) {

				RelationshipCondition relationshipCondition;
				relationshipCondition = (RelationshipCondition) condition;

				TreeParent node;
				node = new TreeParent(relationshipCondition);

				parent.addChild(node);

				this.addResourceNode(node, relationshipCondition.getResource1(),
						ownerTypes);
				node.addChild(new TreeObject(new KeyValueNode("relationship",
						relationshipCondition.getRelationship().toString())));

				this.addResourceNode(node, relationshipCondition.getResource2(),
						ownerTypes);
			}

			else if (condition instanceof PropertyCondition) {

				PropertyCondition propertyCondition;
				propertyCondition = (PropertyCondition) condition;

				TreeParent node;
				node = new TreeParent(propertyCondition);

				parent.addChild(node);
				this.addResourceNode(node, propertyCondition.getResource(), ownerTypes);

				Iterator<String> propertyNameIterator;
				propertyNameIterator = propertyCondition.getPropertyNames();

				while (propertyNameIterator.hasNext()) {
					String propertyName;
					propertyName = propertyNameIterator.next();

					node.addChild(new TreeObject(new KeyValueNode("property",
							propertyName)));
				}
				// end while.

				node.addChild(new TreeObject(new KeyValueNode("op", propertyCondition
						.getOperator().toString())));
				node.addChild(new TreeObject(new KeyValueNode("value", ""
						+ propertyCondition.getValue())));
			}

			else if (condition instanceof ExistsCondition) {

				ExistsCondition existsCondition;
				existsCondition = (ExistsCondition) condition;

				TreeParent node;
				node = new TreeParent(existsCondition);

				parent.addChild(node);
				this.addResourceNode(node, existsCondition.getResource(), ownerTypes);
			}

			else if (condition instanceof ComplexCondition) {

				ComplexCondition complexCondition;
				complexCondition = (ComplexCondition) condition;

				TreeParent node;
				node = new TreeParent(complexCondition);

				parent.addChild(node);

				for (AbstractCondition part : complexCondition.getParts()) {
					this.addConditionNodes(node, part, ownerTypes);
				}
			}

			else if (condition instanceof Negation) {
				Negation negation;
				negation = (Negation) condition;

				TreeParent node;
				node = new TreeParent(negation);

				parent.addChild(node);
				this
						.addConditionNodes(node, negation.getNegatedCondition(), ownerTypes);
			}
		}

		/**
		 * <p>
		 * A helper method that adds nodes to a given {@link TreeParent} for a given
		 * {@link Resource} which is part of an {@link AbstractCondition}.
		 * 
		 * @param parent
		 *          The {@link TreeParent} to that the nodes shall be added.
		 * @param resource
		 *          The {@link Resource} that shall be added.
		 * @param ownerTypes
		 *          A {@link Map} of {@link Resource}s and {@link OwnerType}s.
		 */
		private void addConditionPartNodes(TreeParent parent, Resource resource,
				Map<Resource, OwnerType> ownerTypes) {

			parent.addChild(new TreeObject(new KeyValueNode("id", resource.getId())));
			parent.addChild(new TreeObject(new KeyValueNode("type", resource
					.getType().toString())));
			parent.addChild(new TreeObject(new KeyValueNode("name", resource
					.getName())));
			parent.addChild(new TreeObject(new KeyValueNode("reference", resource
					.getRef())));

			// parent.addChild(new TreeObject(new
			// KeyValueNode("value",""+r.getValue())));

			OwnerType otype;
			otype = ownerTypes.get(resource);

			if (otype != null) {
				parent.addChild(new TreeObject(new KeyValueNode("provided by", otype
						.toString())));
			}

			// parent.addChild(new TreeObject(new
			// KeyValueNode("value",""+r.getValue())));
		}

		/**
		 * <p>
		 * Returns a new {@link String} that is append to a given {@link String} and
		 * describes a given {@link Connector}.
		 * </p>
		 * 
		 * @param string
		 *          The {@link String} to that the result should be appended.
		 * @param connector
		 *          The {@link Connector} that shall be described.
		 * @return The gerated descriptional {@link String}.
		 */
		private String getResourceLabel(String string, Connector connector) {

			StringBuffer buffer;
			buffer = new StringBuffer(string);

			if (connector != null) {
				Component component;
				component = connector.getOwner();

				if (component != null) {
					buffer.append(" (defined in ");
					buffer.append(component.getId());
					buffer.append(')');
				}
				// no else.
			}
			// no else.

			return buffer.toString();
		}
	}

	/**
	 * The {@link Action} that can be used to refresh the {@link ContractView}
	 * manually.
	 */
	private Action actionRefresh;

	/**
	 * The {@link TreeViewer} used to display the {@link Contract}s and their
	 * {@link EclipsePlugin}s.
	 */
	private TreeViewer viewer;

	/**
	 * <p>
	 * Creates a new {@link ContractView}.
	 * </p>
	 */
	public ContractView() {

	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {

		/* Remove disconnect from Contract Registry. */
		EclipseContractRegistry.getInstance().removeContractRegistryListener(this);

		/* Dispose all icons. */
		for (Image icon : icons.values()) {
			icon.dispose();
		}

		super.dispose();
	}

	/**
	 * <p>
	 * Passing the focus request to the viewer's control.
	 * </p>
	 */
	public void setFocus() {

		this.viewer.getControl().setFocus();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.contractregistry.ContractRegistryListener#update()
	 */
	public void update() {

		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			public void run() {

				actionReloadContracts();
			}
		});
	}

	/**
	 * <p>
	 * Returns the {@link ImageDescriptor} for a given path.
	 * </p>
	 * 
	 * @param path
	 *          The path whose {@link ImageDescriptor} shall be returned.
	 * @return The {@link ImageDescriptor} for a given path.
	 */
	public ImageDescriptor getImageDescriptor(String path) {

		return AbstractUIPlugin
				.imageDescriptorFromPlugin(Activator.PLUGIN_ID, path);
	}

	/**
	 * <p>
	 * Behaviour of the {@link Action} to export all instantiated {@link Contract}
	 * s by using a given {@link Exporter}.
	 * </p>
	 * 
	 * @param exporter
	 *          The {@link Exporter} that shall be used for export.
	 */
	private void actionExport(Exporter exporter) {

		Collection<Contract> contracts;
		contracts =
				EclipseContractRegistry.getInstance().getInstantiatedContracts();

		/* Try to export. */
		try {
			String fileName;
			fileName = null;

			/* Probably export to a folder. */
			if (exporter.exportToFolder()) {

				DirectoryDialog directoryDialog;

				directoryDialog =
						new DirectoryDialog(this.getViewSite().getShell(), SWT.OPEN);
				directoryDialog.setText("Select a target folder for export.");

				fileName = directoryDialog.open();
			}

			/* Else export to a file. */
			else {
				FileDialog fileDialog;

				fileDialog = new FileDialog(this.getViewSite().getShell(), SWT.OPEN);
				fileDialog.setFilterExtensions(exporter.getFilterExtensions());
				fileDialog.setFilterNames(exporter.getFilterNames());
				fileDialog.setText("Select a file for export.");

				fileName = fileDialog.open();
			}
			// no else.

			/* Probably export the contracts. */
			if (fileName != null) {
				exporter.export(contracts, new File(fileName));
			}
			// no else.
		}
		// end try.

		catch (IOException e) {
			Logger.error("Exception exporting contracts", e);
		}
		// end catch.
	}

	/**
	 * <p>
	 * Updates the GUI by requesting the {@link EclipseContractRegistry} for a new
	 * version of the current {@link Contract} model.
	 * </p>
	 */
	private void actionReloadContracts() {

		/* Load the contracted plug-ins. */
		this.plugins =
				EclipseContractRegistry.getInstance().getContractedEclipsePlugins();

		/* Update the viewer. */
		this.viewer.setContentProvider(new ViewContentProvider());
		this.viewer.setInput(getViewSite());

		this.switchActions(true);
	}

	/**
	 * <p>
	 * A helper method that verifies and refreshes all {@link Contract}s, if the
	 * given flag is <code>true</code>.
	 * </p>
	 * 
	 * @param on
	 *          Indicates whether or not all {@link Contract}s shall be verified
	 *          and refreshed.
	 */
	private void switchActions(boolean on) {

		/* Probably verify and refresh. */
		this.actVerifyAll.setEnabled(on);
		this.actionRefresh.setEnabled(on);

		for (Action act : this.actsExport) {
			act.setEnabled(on);
		}

		/* Probably verify selected, instantiated contracts. */
		List<Contract> instantiatedConstracts;
		instantiatedConstracts = getSelectedInstantiatedContracts();

		this.actVerifySelected.setEnabled(on && instantiatedConstracts != null
				&& !instantiatedConstracts.isEmpty());
	}

	/**
	 * <p>
	 * Runs the verification for a given {@link List} of {@link Contract}s.
	 * </p>
	 * 
	 * @param contracts
	 *          The {@link Contract}s that shall be verified.
	 * @param disableActions
	 *          TODO
	 */
	private void verify(List<Contract> contracts, boolean disableActions) {

		/* The {@link VerificationReport} of the verification. */
		final VerificationReport verReport = new VerificationReport() {

			/** The {@link Contract} of this {@link VerificationReport}. */
			private Contract contract = null;

			/*
			 * (non-Javadoc)
			 * @see net.java.treaty.VerificationReport#getContract()
			 */
			public Contract getContract() {

				return this.contract;
			}

			/*
			 * (non-Javadoc)
			 * @see net.java.treaty.VerificationReport#log(java.lang.Object,
			 * net.java.treaty.VerificationResult, java.lang.String[])
			 */
			public void log(Object context, VerificationResult result,
					String... remarks) {

				if (context instanceof Annotatable) {
					((Annotatable) context).setProperty(VERIFICATION_RESULT, result);
				}
				// no else.
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * net.java.treaty.VerificationReport#setContract(net.java.treaty.Contract
			 * )
			 */
			public void setContract(Contract contract) {

				this.contract = contract;
			}
		};

		/* A {@link VerificationJobListener} used during verification. */
		VerificationJobListener vListener = new VerificationJobListener() {

			/*
			 * (non-Javadoc)
			 * @seenet.java.treaty.eclipse.jobs.VerificationJobListener#
			 * verificationStatusChanged()
			 */
			public void verificationStatusChanged() {

				updateTree();
			}
		};

		/* A {@link IJobChangeListener} used during verification. */
		IJobChangeListener jListener = new IJobChangeListener() {

			/*
			 * (non-Javadoc)
			 * @see
			 * org.eclipse.core.runtime.jobs.IJobChangeListener#aboutToRun(org.eclipse
			 * .core.runtime.jobs.IJobChangeEvent)
			 */
			public void aboutToRun(IJobChangeEvent e) {

				/* Do nothing. */
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * org.eclipse.core.runtime.jobs.IJobChangeListener#awake(org.eclipse.
			 * core.runtime.jobs.IJobChangeEvent)
			 */
			public void awake(IJobChangeEvent e) {

				/* Do nothing. */
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * org.eclipse.core.runtime.jobs.IJobChangeListener#done(org.eclipse.core
			 * .runtime.jobs.IJobChangeEvent)
			 */
			public void done(IJobChangeEvent e) {

				VerificationJob verificationJob;

				updateTree();

				verificationJob = (VerificationJob) e.getJob();
				reportVerificationResult(verificationJob.getDoneContracts(),
						verificationJob.getFailedContracts());

				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

					/*
					 * (non-Javadoc)
					 * @see java.lang.Runnable#run()
					 */
					public void run() {

						switchActions(true);
					}
				});
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * org.eclipse.core.runtime.jobs.IJobChangeListener#running(org.eclipse
			 * .core.runtime.jobs.IJobChangeEvent)
			 */
			public void running(IJobChangeEvent e) {

				/* Do nothing. */
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * org.eclipse.core.runtime.jobs.IJobChangeListener#scheduled(org.eclipse
			 * .core.runtime.jobs.IJobChangeEvent)
			 */
			public void scheduled(IJobChangeEvent e) {

				/* Do nothing. */
			}

			/*
			 * (non-Javadoc)
			 * @see
			 * org.eclipse.core.runtime.jobs.IJobChangeListener#sleeping(org.eclipse
			 * .core.runtime.jobs.IJobChangeEvent)
			 */
			public void sleeping(IJobChangeEvent e) {

				/* Do nothing. */
			}
		};

		/* Probably disable the actions. */
		if (disableActions) {
			this.switchActions(false);
		}

		/* Verify the contracts. */
		EclipseContractRegistry.getInstance().verify(contracts, verReport,
				vListener, jListener);
	}

	private DrillDownAdapter drillDownAdapter;

	private Action actVerifyAll;
	private Action actVerifySelected;
	private Action actPrintStackTrace;
	private Action actShowContractSource;
	private List<Action> actsExport;
	// this is the model displayed - a list of plugins with contracts
	private Collection<EclipsePlugin> plugins = null;
	private Map<String, Image> icons = new HashMap<String, Image>();

	private List<Exporter> exporters = null;
	// strings
	private static final String LABEL_INSTANCES = "contract instances";

	enum OwnerType {
		extension, extensionpoint, thirdparty
	}

	class KeyValueNode {

		String key, value = null;

		public KeyValueNode(String key, String value) {

			super();
			this.key = key;
			this.value = value;
		}
	}

	class DummyViewContentProvider implements IStructuredContentProvider,
			ITreeContentProvider {

		private Object ROOT = new Object();
		private String INITIALIZING = "initializing, please wait ..";

		@Override
		public Object[] getElements(Object inputElement) {

			if (inputElement.equals(getViewSite())) {
				return getChildren(ROOT);
			}
			return getChildren(inputElement);
		}

		@Override
		public void dispose() {

		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

		}

		@Override
		public Object[] getChildren(Object parent) {

			if (parent == ROOT) {
				return new String[] { INITIALIZING };
			}
			return new Object[] {};
		}

		@Override
		public Object getParent(Object element) {

			if (element == INITIALIZING)
				return ROOT;
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {

			return element == ROOT;
		}
	};

	/**
	 * <p>
	 * Used to provide the labels for the {@link TreeViewer}.
	 * </p>
	 * 
	 * @author Jens Dietrich
	 */
	private class ViewLabelProvider implements ITableLabelProvider {

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang
		 * .Object, int)
		 */
		public String getColumnText(Object n, int col) {

			if (!(n instanceof TreeObject)) {
				return col == 0 ? n.toString() : "";
			}

			Object obj = ((TreeObject) n).getObject();
			if (col == 1)
				return getStatus(obj);

			if (obj instanceof EclipseExtensionPoint) {
				return ((Connector) obj).getId();
			}
			else if (obj instanceof EclipseExtension) {
				EclipseExtension x = (EclipseExtension) obj;
				StringBuffer b =
						new StringBuffer().append(x.getOwner().getId()).append('/');
				String id = x.getId();
				if (id == null) {
					b.append("anonymous extension");
				}
				else {
					b.append(x.getId());
				}
				return b.toString();
			}
			else if (obj instanceof Component) {
				return ((Component) obj).getId();
			}
			else if (obj instanceof Contract) {
				Contract c = (Contract) obj;
				URL url = c.getLocation();
				if (url == null) {
					return "a contract";
				}
				else {
					// check whether contract has been provided by third party
					if (c.getOwner() != null && !c.getConsumer().equals(c.getOwner())) {
						Connector conn = c.getOwner();
						Component comp = conn.getOwner();
						String id = comp.getId();
						return id + url.getPath();
					}
					return url.getPath(); // context is defined by parent node
				}
			}
			else if (obj instanceof Resource) {
				Resource r = (Resource) obj;
				return toString(r);
			}
			else if (obj instanceof KeyValueNode) {
				KeyValueNode kvn = (KeyValueNode) obj;
				return kvn.key + ": " + kvn.value;
			}
			else if (obj instanceof RelationshipCondition) {
				return toString((RelationshipCondition) obj);
			}
			else if (obj instanceof PropertyCondition) {
				return toString((PropertyCondition) obj);
			}
			else if (obj instanceof ExistsCondition) {
				return toString((ExistsCondition) obj);
			}
			else if (obj instanceof ComplexCondition) {
				return ((ComplexCondition) obj).getConnective();
			}
			else if (obj instanceof Negation) {
				return "not";
			}
			return obj.toString();
		}

		/**
		 * <p>
		 * Returns the verification status of a given {@link Object} as a
		 * {@link String}.
		 * </p>
		 * 
		 * @param object
		 *          The {@link Object} whose status shall be returned.
		 * @return The verification status as a {@link String}.
		 */
		private String getStatus(Object object) {

			String result;
			result = "";

			if (object instanceof Annotatable) {

				Annotatable annotatable;
				Object status;

				annotatable = (Annotatable) object;
				status = annotatable.getProperty(VERIFICATION_RESULT);

				if (status == VerificationResult.FAILURE) {
					result = "failure";
				}

				else if (status == VerificationResult.SUCCESS) {
					result = "success";
				}

				else {
					result = "not verified";
				}
				// no else.
			}
			return result;
		}

		private String toString(RelationshipCondition c) {

			StringBuffer buf = new StringBuffer();
			buf.append(toString(c.getResource1()));
			buf.append(' ');
			String p = c.getRelationship().toString();
			p = p.substring(p.lastIndexOf('#') + 1); // last token
			buf.append(p);
			buf.append(' ');
			buf.append(toString(c.getResource2()));
			return buf.toString();
		}

		private String toString(PropertyCondition c) {

			StringBuffer buf = new StringBuffer();
			buf.append(toString(c.getResource()));
			buf.append(' ');

			Iterator<String> propertyNameIterator;
			propertyNameIterator = c.getPropertyNames();

			while (propertyNameIterator.hasNext()) {
				buf.append(c.getProperty(propertyNameIterator.next()));
				buf.append(' ');
			}
			// end while

			buf.append(' ');
			buf.append(c.getOperator().toString());
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

			if (r.getName() != null) {
				boolean loadProblem =
						r.getValue() == null
								&& r.getProperty(VERIFICATION_EXCEPTION) != null;
				return loadProblem ? "!" + r.getName() : r.getName();
			}
			else {
				return r.getId();
			}
		}

		// get the load/verification status icon
		private Image getStatusIcon(Object n) {

			if (n instanceof Annotatable) {
				Annotatable c = (Annotatable) n;
				Object status = c.getProperty(VERIFICATION_RESULT);
				/**
				 * if (c instanceof Constraint && !((Constraint)c).isInstantiated()) {
				 * return getIcon("status_notinstantiated.gif"); }
				 */
				if (status == VerificationResult.FAILURE) {
					return getIcon("status_failure.gif");
				}
				else if (status == VerificationResult.SUCCESS) {
					return getIcon("status_success.gif");
				}
				else {
					return getIcon("status_open.gif");
				}
			}
			return null;
		}

		public Image getColumnImage(Object n, int col) {

			if (!(n instanceof TreeObject)) {
				return null;
			}

			Object obj = ((TreeObject) n).getObject();
			if (col == 1) {
				return getStatusIcon(obj);
			}
			if (obj instanceof Connector) {
				Connector c = (Connector) obj;
				if (c.getType() == ConnectorType.SUPPLIER) {
					return getIcon("extension.gif");
				}
				else if (c.getType() == ConnectorType.CONSUMER) {
					return getIcon("extensionpoint.gif");
				}
			}
			else if (obj instanceof Component) {
				return getIcon("plugin.gif");
			}
			else if (obj instanceof Contract) {
				return getIcon("contract.gif");
			}
			else if (obj instanceof AbstractCondition
					&& !(obj instanceof ComplexCondition)) {
				return getIcon("constraint.gif");
			}
			else if (obj instanceof Resource) {
				boolean var = ((Resource) obj).getName() == null;
				Image icon = IconProvider.findIcon(((Resource) obj).getType(), var);
				if (icon != null) {
					return icon;
				}
				else {
					return var ? getIcon("variable.png") : getIcon("constant.png");
				}
			}
			else if (obj instanceof KeyValueNode
					&& (((KeyValueNode) obj).key.equals("relationship"))) { // relationships
				return getIcon("link.gif");
			}

			else if (n instanceof TreeParent) {
				return PlatformUI.getWorkbench().getSharedImages().getImage(
						ISharedImages.IMG_OBJ_FOLDER);
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
	 * <p>
	 * This is a call-back that will allow us to create the viewer and initialize
	 * it.
	 * </p>
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {

		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);

		viewer.getTree().setHeaderVisible(true);

		TreeColumn col2 = new TreeColumn(viewer.getTree(), SWT.LEFT);
		col2.setText("plugin contracts");

		Rectangle bounds = viewer.getTree().getDisplay().getBounds();
		col2.setWidth(Math.max(600, bounds.width - 400));

		TreeColumn col1 = new TreeColumn(viewer.getTree(), SWT.LEFT);
		col1.setText("status");
		col1.setWidth(150);

		viewer.setLabelProvider(new ViewLabelProvider());

		viewer.setContentProvider(new DummyViewContentProvider());
		viewer.setInput(getViewSite());

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent e) {

				Object obj = getSelectedObject();
				boolean f =
						obj != null
								&& obj instanceof PropertySupport
								&& ((PropertySupport) obj)
										.getProperty(Constants.VERIFICATION_EXCEPTION) != null;
				actPrintStackTrace.setEnabled(f);
				actShowContractSource.setEnabled(getSelectedContract() != null);

				List<Contract> iConstracts = getSelectedInstantiatedContracts();
				actVerifySelected.setEnabled(iConstracts != null
						&& !iConstracts.isEmpty());
			}
		});

		makeActions();
		hookContextMenu();
		contributeToActionBars();

		actionReloadContracts(); // background initialization

		/* Register as listener of the contract registry. */
		EclipseContractRegistry.getInstance().addContractRegistryListener(this);
	}

	private Object getSelectedObject() {

		TreeItem[] sel = viewer.getTree().getSelection();
		if (sel == null || sel.length == 0) {
			return null;
		}
		else if (sel[0].getData() instanceof TreeObject) {
			return ((TreeObject) sel[0].getData()).getObject();
		}
		else {
			return sel[0].getData();
		}
	}

	private Contract getSelectedContract() {

		TreeItem[] sel = viewer.getTree().getSelection();
		if (sel == null || sel.length == 0) {
			return null;
		}
		TreeObject to = (TreeObject) sel[0].getData();
		return findContract(to);
	}

	private Contract findContract(TreeObject to) {

		Object obj = to.getObject();
		TreeObject parent = to.getParent();
		if (obj instanceof Contract) {
			return (Contract) obj;
		}
		else if (parent != null) {
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

		manager.add(actionRefresh);
		manager.add(new Separator());
		manager.add(actVerifyAll);
		manager.add(actVerifySelected);
		manager.add(new Separator());
		for (Action actExport : this.actsExport) {
			manager.add(actExport);
		}
	}

	private void fillContextMenu(IMenuManager manager) {

		manager.add(actionRefresh);
		manager.add(actVerifyAll);
		manager.add(actVerifySelected);
		manager.add(actPrintStackTrace);
		manager.add(actShowContractSource);
		manager.add(new Separator());
		for (Action actExport : this.actsExport) {
			manager.add(actExport);
		}
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {

		manager.add(actionRefresh);
		manager.add(actVerifyAll);
		manager.add(actVerifySelected);
		manager.add(new Separator());
		for (Action actExport : this.actsExport) {
			manager.add(actExport);
		}
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
		actPrintStackTrace
				.setToolTipText("Displays the verification exception stack trace");
		actPrintStackTrace.setEnabled(false);

		actionRefresh = new Action() {

			public void run() {

				actionReloadContracts();
			}
		};
		actionRefresh.setText("reset");
		actionRefresh.setImageDescriptor(getImageDescriptor("icons/refresh.gif"));
		actionRefresh
				.setToolTipText("Reloads all contracts and resets verification status of all contracts");

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
		actVerifySelected
				.setImageDescriptor(getImageDescriptor("icons/verify_sel.gif"));
		actVerifySelected
				.setToolTipText("runs verification for selected contracts");
		actVerifySelected.setEnabled(false);

		actShowContractSource = new Action() {

			public void run() {

				actShowContractSource();
			}
		};
		actShowContractSource.setEnabled(false);
		actShowContractSource.setText("display contract source");
		actShowContractSource
				.setToolTipText("displays the XML source code of the contract");

		class ExportAction extends Action {

			private Exporter exporter = null;

			ExportAction(Exporter x) {

				super();
				this.exporter = x;
			}

			public void run() {

				actionExport(exporter);
			}

		}
		;
		this.actsExport = new ArrayList<Action>();
		for (Exporter exporter : this.getExporters()) {
			Action actExport = new ExportAction(exporter);
			actExport.setEnabled(true);
			actExport.setText(exporter.getName());
			actExport.setImageDescriptor(getImageDescriptor("icons/export.gif"));
			actExport
					.setToolTipText("export instantiated contracts and verification results (if available), exporter used: "
							+ exporter.getName());
			actsExport.add(actExport);
		}
	}

	private List<Exporter> getExporters() {

		if (exporters == null) {
			exporters = Exporter.getInstances();
		}
		return exporters;
	}

	private void actShowContractSource() {

		Contract contract = getSelectedContract();
		if (contract != null) {
			try {
				URL url = contract.getLocation();
				new ViewContractSourceDialog(new Shell(), url).open();
			} catch (Exception x) {
			}
		}
	}

	/**
	 * Print the verification exception stack trace.
	 */
	private void actPrintStackTrace() {

		Object obj = this.getSelectedObject();
		if (obj != null
				&& obj instanceof PropertySupport
				&& ((PropertySupport) obj)
						.getProperty(Constants.VERIFICATION_EXCEPTION) != null) {
			Exception x =
					(Exception) ((PropertySupport) obj)
							.getProperty(Constants.VERIFICATION_EXCEPTION);
			if (x != null) {
				try {
					new ViewExceptionStackTraceDialog(new Shell(), x).open();
				} catch (Exception xx) {
				}
			}
		}
	}

	private List<Contract> getSelectedInstantiatedContracts() {

		List<Contract> contracts = new ArrayList<Contract>();
		Contract c = this.getSelectedContract();
		if (c != null) {
			if (c.isInstantiated()) {
				contracts.add(c);
			}
			else {
				EclipseExtensionPoint xp = (EclipseExtensionPoint) c.getConsumer();
				addIContract(xp, contracts);
			}
		}
		else {
			TreeItem[] sel = viewer.getTree().getSelection();
			if (sel == null || sel.length == 0) {
				return contracts;
			}
			Object obj = ((TreeObject) sel[0].getData()).getObject(); // selected
			// object
			TreeItem parent = sel[0].getParentItem();

			if (obj instanceof EclipseExtension) {
				addIContract((EclipseExtension) obj, contracts);
			}
			else if (obj instanceof EclipseExtensionPoint) {
				addIContract((EclipseExtensionPoint) obj, contracts);
			}
			else if (obj instanceof EclipsePlugin) {
				EclipsePlugin p = (EclipsePlugin) obj;
				if (parent != null
						&& LABEL_INSTANCES.equals(parent.getData().toString())) {
					// folder for instances
					for (EclipseExtension x : p.getExtensions()) {
						addIContract(x, contracts);
					}
				}
				else {
					// parent of extension points
					for (EclipseExtensionPoint xp : p.getExtensionPoints()) {
						addIContract(xp, contracts);
					}
				}
			}
		}

		return contracts;
	}

	private void addIContract(EclipseExtension x, List<Contract> contracts) {

		for (Contract con : x.getContracts()) {
			if (con != null && con.isInstantiated()) {
				contracts.add(con);
			}
		}
	}

	private void addIContract(EclipseExtensionPoint xp, List<Contract> contracts) {

		for (EclipseExtension x : xp.getExtensions()) {
			addIContract(x, contracts);
		}
	}

	/**
	 * Run verification for selected contracts.
	 */
	private void actVerifySelected() {

		List<Contract> contracts = getSelectedInstantiatedContracts();
		if (contracts != null && !contracts.isEmpty()) {
			verify(contracts, false);
		}
	}

	/**
	 * Run verification for all contracts.
	 */
	private void actVerifyAll() {

		// collect contracts
		final List<Contract> contracts = new ArrayList<Contract>();
		for (EclipsePlugin p : plugins) {
			contracts.addAll(p.getInstantiatedContracts());
		}
		verify(contracts, true);
	}

	private void reportVerificationResult(List<Contract> allContracts,
			List<Contract> failedContracts) {

		int c = allContracts.size();
		int f = failedContracts.size();
		String message = null;
		if (failedContracts.size() == 0) {
			message = "" + c + " contract instances have been verified successfully";
		}
		else {
			message =
					""
							+ c
							+ " contract instances checked, verification has failed for "
							+ f
							+ " contract instances. Verification status annotations have been added to the contract view.";
		}
		final String m = message;
		Runnable r = new Runnable() {

			public void run() {

				MessageDialog.openInformation(viewer.getControl().getShell(),
						"Verification result", m);
			}
		};
		viewer.getControl().getDisplay().syncExec(r);
	}

	// refreshes the tree labels
	private void updateTree() {

		Runnable r = new Runnable() {

			public void run() {

				viewer.refresh(true);
			}
		};
		viewer.getTree().getDisplay().asyncExec(r);
	}

	private Image getIcon(String name) {

		String path = "icons/" + name;
		Image icon = icons.get(path);
		if (icon == null) {
			icon = getImageDescriptor(path).createImage();
			icons.put(path, icon);
		}
		return icon;
	}
}