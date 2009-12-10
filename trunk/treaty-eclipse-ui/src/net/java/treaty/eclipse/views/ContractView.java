/*
 * Copyright (C) 2009 Jens Dietrich
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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import net.java.treaty.Role;
import net.java.treaty.TreatyException;
import net.java.treaty.VerificationReport;
import net.java.treaty.VerificationResult;
import net.java.treaty.contractregistry.ContractRegistryListener;
import net.java.treaty.eclipse.Constants;
import net.java.treaty.eclipse.EclipseExtension;
import net.java.treaty.eclipse.EclipseExtensionPoint;
import net.java.treaty.eclipse.EclipsePlugin;
import net.java.treaty.eclipse.Logger;
import net.java.treaty.eclipse.action.EclipseActionRegistry;
import net.java.treaty.eclipse.contractregistry.EclipseContractRegistry;
import net.java.treaty.eclipse.exporter.Exporter;
import net.java.treaty.eclipse.exporter.ExporterRegistry;
import net.java.treaty.eclipse.exporter.ExporterRegistryListener;
import net.java.treaty.eclipse.trigger.EclipseTriggerRegistry;
import net.java.treaty.eclipse.ui.Activator;
import net.java.treaty.eclipse.ui.actions.UIActionVocabulary;
import net.java.treaty.eclipse.ui.triggers.ManualTriggerVocabulary;

import org.eclipse.core.runtime.IAdaptable;
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
public class ContractView extends ViewPart implements ContractRegistryListener,
		ExporterRegistryListener {

	/**
	 * <p>
	 * The {@link DummyViewContentProvider} is used during startup until the
	 * {@link ContractView} has been initialized.
	 * </p>
	 * 
	 * @author Jens Dietrich
	 */
	private class DummyViewContentProvider implements IStructuredContentProvider,
			ITreeContentProvider {

		/** The root {@link Object}. */
		private Object ROOT = new Object();

		/** The initial message. */
		private String INITIALIZING = "Initializing, please wait ...";

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java
		 * .lang.Object)
		 */
		public Object[] getElements(Object inputElement) {

			if (inputElement.equals(getViewSite())) {
				return getChildren(ROOT);
			}
			// no else.

			return getChildren(inputElement);
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		public void dispose() {

			/* Do nothing. */
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
		 * .viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

			/* Do nothing. */
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
		 * Object)
		 */
		public Object[] getChildren(Object parent) {

			if (parent == ROOT) {
				return new String[] { INITIALIZING };
			}

			return new Object[] {};
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object
		 * )
		 */
		public Object getParent(Object element) {

			if (element == INITIALIZING)
				return ROOT;

			return null;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.
		 * Object)
		 */
		public boolean hasChildren(Object element) {

			return element == ROOT;
		}
	}

	/**
	 * <p>
	 * Action to export {@link Contract}s and their results.
	 * </p>
	 * 
	 * @author Jens Dietrich.
	 */
	private class ExportAction extends Action {

		/** The {@link Exporter} of this {@link ExportAction}. */
		private Exporter myExporter = null;

		/**
		 * <p>
		 * Creates a new {@link ExportAction}.
		 * </p>
		 * 
		 * @param exporter
		 *          The {@link Exporter} of this {@link ExportAction}.
		 */
		ExportAction(Exporter exporter) {

			super();

			this.myExporter = exporter;
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.jface.action.Action#run()
		 */
		public void run() {

			actionExport(this.myExporter);
		}
	}

	/**
	 * <p>
	 * Represents nodes that consist of a key and a value.
	 * </p>
	 * 
	 * @author Jens Dietrich
	 */
	private class KeyValueNode {

		/** The key of this {@link KeyValueNode}. */
		private String key = null;

		/** The value of this {@link KeyValueNode}. */
		private String value = null;

		/**
		 * <p>
		 * Creates a new {@link KeyValueNode}.
		 * </p>
		 * 
		 * @param key
		 *          The key of this {@link KeyValueNode}.
		 * @param value
		 *          The value of this {@link KeyValueNode}.
		 */
		public KeyValueNode(String key, String value) {

			super();

			this.key = key;
			this.value = value;
		}
	}

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
		private List<TreeObject> children = new ArrayList<TreeObject>();

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

			if (children == null)
				return new TreeObject[0];
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

			List<EclipsePlugin> eclipsePlugins;
			eclipsePlugins = new ArrayList<EclipsePlugin>(myContractedPlugins);
			Collections.sort(eclipsePlugins);

			for (EclipsePlugin plugin : eclipsePlugins) {
				TreeParent node;
				node = new TreeParent(plugin);

				parent.addChild(node);
				this.addExtensionPointNodes(node, plugin);
			}
			// end for.
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

			List<Contract> contracts;
			contracts = new ArrayList<Contract>(eclipseExtensionPoint.getContracts());
			Collections.sort(contracts);

			/* Iterate through all contracts of the extension point. */
			for (Contract contract : contracts) {

				TreeParent node;
				node = new TreeParent(contract);

				/* Add the contract node. */
				parent.addChild(node);
				this.addContractContentNodes(node, contract);

				/* Add a node for instantiating extensions. */
				node = new TreeParent(LABEL_INSTANCES);
				parent.addChild(node);

				/* Add children nodes for all extensions that instantiate the contract. */
				List<EclipseExtension> eclipseExtensions;
				eclipseExtensions =
						new ArrayList<EclipseExtension>(eclipseExtensionPoint
								.getExtensions());
				Collections.sort(eclipseExtensions);

				for (EclipseExtension eclipseExtension : eclipseExtensions) {
					// TreeParent node2 = new TreeParent(x.getOwner());
					// node.addChild(node2);
					this.addContractedExtensionNodes(node, eclipseExtension);
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
		 * @param eclipseExtension
		 *          The {@link EclipseExtension} whose {@link Contracts} s shall be
		 *          added.
		 */
		private void addContractedExtensionNodes(TreeParent parent,
				EclipseExtension eclipseExtension) {

			TreeParent node;
			node = new TreeParent(eclipseExtension);

			parent.addChild(node);

			List<Contract> contracts;
			contracts = new ArrayList<Contract>(eclipseExtension.getContracts());
			Collections.sort(contracts);

			for (Contract contract : contracts) {
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

			Map<Resource, Role> ownerTypes;
			ownerTypes = new HashMap<Resource, Role>();

			for (Resource r : contract.getConsumerResources()) {
				ownerTypes.put(r, Role.CONSUMER);
			}

			for (Resource r : contract.getSupplierResources()) {
				ownerTypes.put(r, Role.SUPPLIER);
			}

			for (Resource r : contract.getExternalResources()) {
				ownerTypes.put(r, Role.LEGISLATOR);
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
				Map<Resource, Role> ownerTypes) {

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
				AbstractCondition condition, Map<Resource, Role> ownerTypes) {

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
				Map<Resource, Role> ownerTypes) {

			parent.addChild(new TreeObject(new KeyValueNode("id", resource.getId())));
			parent.addChild(new TreeObject(new KeyValueNode("type", resource
					.getType().toString())));
			parent.addChild(new TreeObject(new KeyValueNode("name", resource
					.getName())));
			parent.addChild(new TreeObject(new KeyValueNode("reference", resource
					.getRef())));

			// parent.addChild(new TreeObject(new
			// KeyValueNode("value",""+r.getValue())));

			Role role;
			role = ownerTypes.get(resource);

			if (role != null) {
				parent.addChild(new TreeObject(new KeyValueNode("provided by", role
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
		 * org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.
		 * jface.viewers.ILabelProviderListener)
		 */
		public void addListener(ILabelProviderListener arg0) {

			/* Do nothing. */
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
		 */
		public void dispose() {

			/* Do nothing. */
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang
		 * .Object, int)
		 */
		public Image getColumnImage(Object node, int column) {

			Image result;
			result = null;

			if (node instanceof TreeObject) {

				Object adaptedObject;
				adaptedObject = ((TreeObject) node).getObject();

				if (column == STATUS_COLUMN) {
					result = this.getStatusIcon(adaptedObject);
				}

				else if (column == LABEL_COLUMN) {

					if (adaptedObject instanceof Connector) {
						Connector connector;
						connector = (Connector) adaptedObject;

						if (connector.getType() == ConnectorType.SUPPLIER) {
							result = getIcon("extension.gif");
						}

						else if (connector.getType() == ConnectorType.CONSUMER) {
							result = getIcon("extensionpoint.gif");
						}
						// no else.
					}

					else if (adaptedObject instanceof Component) {
						result = getIcon("plugin.gif");
					}

					else if (adaptedObject instanceof Contract) {
						result = getIcon("contract.gif");
					}

					else if (adaptedObject instanceof AbstractCondition
							&& !(adaptedObject instanceof ComplexCondition)) {
						result = getIcon("constraint.gif");
					}

					else if (adaptedObject instanceof Resource) {
						boolean isVariable;
						isVariable = ((Resource) adaptedObject).getName() == null;

						Image icon;
						icon =
								IconProvider.findIcon(((Resource) adaptedObject).getType(),
										isVariable);

						if (icon != null) {
							result = icon;
						}

						/* Probably use a default image. */
						else {

							if (isVariable) {
								result = getIcon("variable.png");
							}

							else {
								getIcon("constant.png");
							}
						}
						// end else.
					}

					else if (adaptedObject instanceof KeyValueNode
							&& (((KeyValueNode) adaptedObject).key.equals("relationship"))) {

						/* (Relationship). */
						result = getIcon("link.gif");
					}

					else if (node instanceof TreeParent) {
						return PlatformUI.getWorkbench().getSharedImages().getImage(
								ISharedImages.IMG_OBJ_FOLDER);
					}
					// no else.
				}
				// no else (unknown column).
			}
			// no else (node is no TreeObject, return null).

			return result;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang
		 * .Object, int)
		 */
		public String getColumnText(Object node, int column) {

			String result;

			if (!(node instanceof TreeObject)) {
				if (column == LABEL_COLUMN) {
					result = node.toString();
				}

				else {
					result = "";
				}
			}

			else {
				Object adaptedObject;
				adaptedObject = ((TreeObject) node).getObject();

				if (column == STATUS_COLUMN) {
					return this.getStatus(adaptedObject);
				}

				else if (column == LABEL_COLUMN) {

					if (adaptedObject instanceof EclipseExtensionPoint) {
						result = ((Connector) adaptedObject).getId();
					}

					else if (adaptedObject instanceof EclipseExtension) {
						EclipseExtension eclipseExtension;
						eclipseExtension = (EclipseExtension) adaptedObject;

						StringBuffer buffer;
						buffer = new StringBuffer();
						buffer.append(eclipseExtension.getOwner().getId()).append('/');

						String id;
						id = eclipseExtension.getId();

						if (id == null) {
							buffer.append("Anonymous Extension");
						}

						else {
							buffer.append(eclipseExtension.getId());
						}

						result = buffer.toString();
					}

					else if (adaptedObject instanceof Component) {
						result = ((Component) adaptedObject).getId();
					}

					else if (adaptedObject instanceof Contract) {
						Contract contract;
						contract = (Contract) adaptedObject;

						URL contractUrl;
						contractUrl = contract.getLocation();

						if (contractUrl == null) {
							return "A Contract";
						}

						else {
							/*
							 * Check whether or not the contract has been provided by third
							 * party.
							 */
							if (contract.getOwner() != null
									&& !contract.getConsumer().equals(contract.getOwner())) {
								Connector connector;
								connector = contract.getOwner();

								Component component;
								component = connector.getOwner();

								String componentID;
								componentID = component.getId();

								result = componentID + contractUrl.getPath();
							}

							/* Else the context is defined by parent node. */
							else {
								result = contractUrl.getPath();
							}
						}
						// end else (contractURL != null).
					}

					else if (adaptedObject instanceof Resource) {
						Resource resource;
						resource = (Resource) adaptedObject;

						result = this.toString(resource);
					}

					else if (adaptedObject instanceof KeyValueNode) {
						KeyValueNode keyValueNode;
						keyValueNode = (KeyValueNode) adaptedObject;

						result = keyValueNode.key + ": " + keyValueNode.value;
					}

					else if (adaptedObject instanceof RelationshipCondition) {
						result = this.toString((RelationshipCondition) adaptedObject);
					}

					else if (adaptedObject instanceof PropertyCondition) {
						result = this.toString((PropertyCondition) adaptedObject);
					}

					else if (adaptedObject instanceof ExistsCondition) {
						result = this.toString((ExistsCondition) adaptedObject);
					}

					else if (adaptedObject instanceof ComplexCondition) {
						result = ((ComplexCondition) adaptedObject).getConnective();
					}

					else if (adaptedObject instanceof Negation) {
						result = "not";
					}

					else {
						return adaptedObject.toString();
					}
					// end else (instanceof check).
				}

				/* (unknown column). */
				else {
					result = "";
				}
			}
			// end else (node is TreeObject).

			return result;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang
		 * .Object, java.lang.String)
		 */
		public boolean isLabelProperty(Object arg0, String arg1) {

			/* Do nothing. */
			return false;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse
		 * .jface.viewers.ILabelProviderListener)
		 */
		public void removeListener(ILabelProviderListener arg0) {

			/* Do nothing. */
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

				else if (status == VerificationResult.UNKNOWN) {
					result = "unknown";
				}

				else {
					result = "not verified";
				}
				// no else.
			}
			return result;
		}

		/**
		 * <p>
		 * Returns a {@link String} representation of a given
		 * {@link ExistsCondition}.
		 * </p>
		 * 
		 * @param condition
		 *          The {@link ExistsCondition} whose {@link String} representation
		 *          shall be returned.
		 * @return A {@link String} representation of a given
		 *         {@link ExistsCondition}.
		 */
		private String toString(ExistsCondition condition) {

			StringBuffer buf = new StringBuffer();
			buf.append(toString(condition.getResource()));
			buf.append(" must exist");
			return buf.toString();
		}

		/**
		 * <p>
		 * Returns a {@link String} representation of a given
		 * {@link PropertyCondition}.
		 * </p>
		 * 
		 * @param condition
		 *          The {@link PropertyCondition} whose {@link String}
		 *          representation shall be returned.
		 * @return A {@link String} representation of a given
		 *         {@link PropertyCondition}.
		 */
		private String toString(PropertyCondition condition) {

			StringBuffer buf = new StringBuffer();
			buf.append(toString(condition.getResource()));
			buf.append(' ');

			Iterator<String> propertyNameIterator;
			propertyNameIterator = condition.getPropertyNames();

			while (propertyNameIterator.hasNext()) {
				buf.append(condition.getProperty(propertyNameIterator.next()));
				buf.append(' ');
			}
			// end while

			buf.append(' ');
			buf.append(condition.getOperator().toString());
			buf.append(' ');
			buf.append(condition.getValue());
			return buf.toString();
		}

		/**
		 * <p>
		 * Returns a {@link String} representation of a given
		 * {@link RelationshipCondition}.
		 * </p>
		 * 
		 * @param condition
		 *          The {@link RelationshipCondition} whose {@link String}
		 *          representation shall be returned.
		 * @return A {@link String} representation of a given
		 *         {@link RelationshipCondition}.
		 */
		private String toString(RelationshipCondition condition) {

			StringBuffer buf = new StringBuffer();
			buf.append(toString(condition.getResource1()));
			buf.append(' ');
			String p = condition.getRelationship().toString();
			p = p.substring(p.lastIndexOf('#') + 1); // last token
			buf.append(p);
			buf.append(' ');
			buf.append(toString(condition.getResource2()));
			return buf.toString();
		}

		/**
		 * <p>
		 * Returns a {@link String} representation of a given {@link Resource}.
		 * </p>
		 * 
		 * @param resource
		 *          The {@link Resource} whose {@link String} representation shall
		 *          be returned.
		 * @return A {@link String} representation of a given {@link Resource}.
		 */
		private String toString(Resource resource) {

			if (resource.getName() != null) {
				boolean loadProblem =
						resource.getValue() == null
								&& resource.getProperty(VERIFICATION_EXCEPTION) != null;
				return loadProblem ? "!" + resource.getName() : resource.getName();
			}
			else {
				return resource.getId();
			}
		}

		/**
		 * <p>
		 * Returns the status icon (verification result) of a given node.
		 * </p>
		 * 
		 * @param node
		 *          The node whose {@link Image} shall be returned.
		 * @return The status icon (verification result) of a given node.
		 */
		private Image getStatusIcon(Object node) {

			Image result;
			result = null;

			if (node instanceof Annotatable) {
				Annotatable annotatable;
				annotatable = (Annotatable) node;

				Object status;
				status = annotatable.getProperty(VERIFICATION_RESULT);

				// if (c instanceof Constraint && !((Constraint)c).isInstantiated()) {
				// return getIcon("status_notinstantiated.gif"); }

				if (status == VerificationResult.FAILURE) {
					result = getIcon("status_failure.gif");
				}

				else if (status == VerificationResult.SUCCESS) {
					result = getIcon("status_success.gif");
				}

				else {
					result = getIcon("status_open.gif");
				}
				// end else.
			}
			// no else.

			return result;
		}
	}

	/** The number of the column used to display the {@link Contract}'s labels. */
	private static int LABEL_COLUMN = 0;

	/** Label used to display contract instances in the {@link ContractView}. */
	private static final String LABEL_INSTANCES = "Contract Instances";

	/**
	 * The number of the column used to display the {@link Contract}'s
	 * verification status.
	 */
	private static int STATUS_COLUMN = 1;

	/**
	 * A boolean that specifies whether or not the {@link ContractView} has been
	 * updated after the {@link EclipseContractRegistry} changed for the last
	 * time.
	 */
	private boolean isUpdated;

	/** {@link Action} to print the current stack trace. */
	private Action myActionPrintStackTrace;

	/**
	 * The {@link Action} that can be used to refresh the {@link ContractView}
	 * manually.
	 */
	private Action myActionRefresh;

	/** {@link Action} to display a {@link Contract}'s source code. */
	private Action myActionShowContractSource;

	/** {@link Action} to display a {@link Contract}'s vizualisation. */
	private Action myActionShowContractVizualisation;

	/**
	 * {@link Action} to verify all {@link Contract}s provided by this
	 * {@link ContractView}.
	 */
	private Action myActionVerifyAllContracts;

	/** {@link Action} to verify currently selected {@link Contract}s. */
	private Action myActionVerifySelectedContracts;

	/** {@link Action}s to export {@link Contract}s from the {@link ContractView}. */
	private Map<Exporter, ExportAction> myActionsExport =
			new HashMap<Exporter, ExportAction>();

	/** The {@link MenuManager} of the context menu. */
	private MenuManager myContextMenuManager;

	/**
	 * All {@link EclipsePlugin}s that have {@link EclipseExtensionPoint}s that
	 * are contracted by {@link Contract}s.
	 */
	private Collection<EclipsePlugin> myContractedPlugins = null;

	/** The {@link DrillDownAdapter} of this {@link ContractView}. */
	private DrillDownAdapter myDrillDownAdapter;

	/** The icons of this {@link ContractView} must be stored to be disposed. */
	private Map<String, Image> myIcons = new HashMap<String, Image>();

	/**
	 * The {@link TreeViewer} used to display the {@link Contract}s and their
	 * {@link EclipsePlugin}s.
	 */
	private TreeViewer myViewer;

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

		/* Un-Register as contract view of the UIActions. */
		try {
			UIActionVocabulary actionVocabulary;
			actionVocabulary =
					(UIActionVocabulary) EclipseActionRegistry.INSTANCE
							.getActionVocabulary(new URI(
									UIActionVocabulary.ACTION_TYPE_SHOW_VERIFICATION_RESULT));

			actionVocabulary.setContractView(null);
		}

		catch (URISyntaxException e1) {
			Logger.warn(
					"ContractView could not be un-registered at the UIActionVocabulary. "
							+ "UI might not work as excepted.", e1);
		}

		catch (TreatyException e) {
			Logger.warn(
					"ContractView could not be un-registered at the UIActionVocabulary. "
							+ "UI might not work as excepted.", e);
		}

		/* Disconnect from Contract Registry. */
		EclipseContractRegistry.getInstance().removeContractRegistryListener(this);

		/* Disconnect from ExporterRegistry. */
		ExporterRegistry.INSTANCE.removeExporterRegistryListener(this);

		/* Dispose all icons. */
		for (Image icon : myIcons.values()) {
			icon.dispose();
		}

		super.dispose();
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

		this.myViewer =
				new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL
						| SWT.FULL_SELECTION);

		this.myViewer.getTree().setHeaderVisible(true);

		TreeColumn columnStatus;
		TreeColumn columnLabel;

		if (LABEL_COLUMN < STATUS_COLUMN) {
			columnLabel = new TreeColumn(myViewer.getTree(), SWT.LEFT);
			columnStatus = new TreeColumn(myViewer.getTree(), SWT.LEFT);
		}

		else {
			columnStatus = new TreeColumn(myViewer.getTree(), SWT.LEFT);
			columnLabel = new TreeColumn(myViewer.getTree(), SWT.LEFT);
		}

		Rectangle bounds;
		bounds = myViewer.getTree().getDisplay().getBounds();

		columnLabel.setText("Plug-in Contracts");
		columnLabel.setWidth(Math.min(600, bounds.width - 200));

		columnStatus.setText("Status");
		columnStatus.setWidth(150);

		this.myViewer.setLabelProvider(new ViewLabelProvider());

		this.myViewer.setContentProvider(new DummyViewContentProvider());
		this.myViewer.setInput(getViewSite());

		this.myViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			/*
			 * (non-Javadoc)
			 * @see
			 * org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged
			 * (org.eclipse.jface.viewers.SelectionChangedEvent)
			 */
			public void selectionChanged(SelectionChangedEvent e) {

				Object selectedObject;
				selectedObject = getSelectedObject();

				/*
				 * Probably enable actions to display source code and verification
				 * exception of selection.
				 */
				boolean isActionAvailable =
						selectedObject != null
								&& selectedObject instanceof PropertySupport
								&& ((PropertySupport) selectedObject)
										.getProperty(Constants.VERIFICATION_EXCEPTION) != null;

				myActionPrintStackTrace.setEnabled(isActionAvailable);
				myActionShowContractSource.setEnabled(getSelectedContract() != null);
				myActionShowContractVizualisation
						.setEnabled(getSelectedContract() != null);

				Set<Contract> selectedInstantiatedConstracts;
				selectedInstantiatedConstracts = getSelectedInstantiatedContracts();

				myActionVerifySelectedContracts
						.setEnabled(selectedInstantiatedConstracts != null
								&& !selectedInstantiatedConstracts.isEmpty());
			}
		});

		this.makeActions();
		this.hookContextMenu();
		this.contributeToActionBars();

		/* Background initialization. */
		this.actionReloadContracts();

		/* Register as listener of the contract registry. */
		EclipseContractRegistry.getInstance().addContractRegistryListener(this);

		/* Register as listener of the ExporterRegistry. */
		ExporterRegistry.INSTANCE.addExporterRegistryListener(this);

		/* Register as contract view of the UIActions. */
		try {
			UIActionVocabulary actionVocabulary;
			actionVocabulary =
					(UIActionVocabulary) EclipseActionRegistry.INSTANCE
							.getActionVocabulary(new URI(
									UIActionVocabulary.ACTION_TYPE_SHOW_VERIFICATION_RESULT));

			actionVocabulary.setContractView(this);
		}

		catch (URISyntaxException e1) {
			Logger.warn(
					"ContractView could not be registered at the UIActionVocabulary. "
							+ "UI might not work as excepted.", e1);
		}

		catch (TreatyException e) {
			Logger.warn(
					"ContractView could not be registered at the UIActionVocabulary. "
							+ "UI might not work as excepted.", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.eclipse.exporter.ExporterRegistryListener#exporterAdded
	 * (net.java.treaty.eclipse.exporter.Exporter)
	 */
	public void exporterAdded(Exporter exporter) {

		if (!this.myActionsExport.containsKey(exporter)) {

			this.createExportAction(exporter);

			/* Update the menus. */
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

				/*
				 * (non-Javadoc)
				 * @see java.lang.Runnable#run()
				 */
				public void run() {

					hookContextMenu();
					contributeToActionBars();
				}
			});
		}
		// no else (action already exists).
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.eclipse.exporter.ExporterRegistryListener#exporterRemoved
	 * (net.java.treaty.eclipse.exporter.Exporter)
	 */
	public void exporterRemoved(Exporter exporter) {

		if (this.myActionsExport.containsKey(exporter)) {

			this.myActionsExport.remove(exporter);

			/* Update the menus. */
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

				/*
				 * (non-Javadoc)
				 * @see java.lang.Runnable#run()
				 */
				public void run() {

					hookContextMenu();
					contributeToActionBars();
				}
			});
		}
		// no else (action already removed).
	}

	/**
	 * <p>
	 * Passing the focus request to the viewer's control.
	 * </p>
	 */
	public void setFocus() {

		this.myViewer.getControl().setFocus();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.contractregistry.ContractRegistryListener#update()
	 */
	public void update() {

		/* Set the flag before doing anything. */
		this.isUpdated = false;

		/*
		 * Each update causes a thread. But each thread that is executed afterwards
		 * the flag has been set to true again will not do anything.
		 */
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
	 * Displays the verification result for a given {@link List} of verified
	 * {@link Contract}s and a given {@link List} of violated {@link Contract}s.
	 * </p>
	 * 
	 * @param verificationReports
	 *          The {@link VerificationReport}s of all {@link Contract}s that have
	 *          been verified.
	 */
	public void reportVerificationResult(
			Collection<VerificationReport> verificationReports) {

		int totalContractCount;
		totalContractCount = verificationReports.size();

		int failedContractCount;
		failedContractCount = 0;

		for (VerificationReport verificationReport : verificationReports) {

			if (verificationReport.getVerificationResult() != VerificationResult.SUCCESS) {
				failedContractCount++;
			}
			// no else.
		}
		// end for.

		String message = null;

		if (failedContractCount == 0) {
			message =
					totalContractCount
							+ " Contract instances have been verified successfully.";
		}

		else {
			message =
					totalContractCount
							+ " Contract instances checked, verification has failed for "
							+ failedContractCount
							+ " Contract instances. Verification status annotations have "
							+ "been added to the Contract View.";
		}
		// end else.

		final String msg;
		msg = message;

		Runnable runnable;
		runnable = new Runnable() {

			public void run() {

				MessageDialog.openInformation(myViewer.getControl().getShell(),
						"Verification result", msg);
			}
		};

		myViewer.getControl().getDisplay().syncExec(runnable);
	}

	/**
	 * <p>
	 * Behavior of the {@link Action} to export all instantiated {@link Contract}
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

				/* Show the result. */
				/* FIXME Claas: Probably Exporters should work as jobs as well. */
				Runnable runnable;
				runnable = new Runnable() {

					public void run() {

						MessageDialog.openInformation(myViewer.getControl().getShell(),
								"Contract Export", "Contract export finished successfully.");
					}
				};

				myViewer.getControl().getDisplay().syncExec(runnable);
			}
			// no else.
		}
		// end try.

		catch (IOException e) {
			Logger.error("Exception exporting contracts", e);

			/* Show the result. */
			final String msg;
			msg = e.getMessage();

			Runnable runnable;
			runnable = new Runnable() {

				public void run() {

					MessageDialog.openInformation(myViewer.getControl().getShell(),
							"Contract Export", "Contract export failed. Reason: " + msg);
				}
			};

			myViewer.getControl().getDisplay().syncExec(runnable);
		}
		// end catch.
	}

	/**
	 * <p>
	 * Prints the verification exception stack trace. For the selected object in
	 * the {@link ContractView}.
	 * </p>
	 */
	private void actionPrintStackTrace() {

		Object selectedObject;
		selectedObject = this.getSelectedObject();

		if (selectedObject != null
				&& selectedObject instanceof PropertySupport
				&& ((PropertySupport) selectedObject)
						.getProperty(Constants.VERIFICATION_EXCEPTION) != null) {

			Exception exception =
					(Exception) ((PropertySupport) selectedObject)
							.getProperty(Constants.VERIFICATION_EXCEPTION);

			if (exception != null) {

				try {
					new ViewExceptionStackTraceDialog(this.getViewSite().getShell(),
							exception).open();
				}

				catch (Exception e) {
					Logger.error("Exception during display of VerificationException.", e);
				}
				// end catch.
			}
			// no else.
		}
		// no else.
	}

	/**
	 * <p>
	 * Updates the GUI by requesting the {@link EclipseContractRegistry} for a new
	 * version of the current {@link Contract} model.
	 * </p>
	 */
	private synchronized void actionReloadContracts() {

		if (!this.isUpdated) {

			/*
			 * It is very important that this flag is set before the contracts are
			 * updated to avoid unwanted side effects.
			 */
			this.isUpdated = true;

			/* Load the contracted plug-ins. */
			this.myContractedPlugins =
					EclipseContractRegistry.getInstance().getContractedEclipsePlugins();

			/* Update the viewer. */
			this.myViewer.setContentProvider(new ViewContentProvider());
			this.myViewer.setInput(getViewSite());

			this.switchActions(true);
		}
		// no else.
	}

	/**
	 * <p>
	 * Shows a dialog window to display the source code of a selected
	 * {@link Contract}.
	 * </p>
	 */
	private void actionShowContractSource() {

		Contract contract;
		contract = this.getSelectedContract();

		if (contract != null) {
			try {
				URL contractURL;
				contractURL = contract.getLocation();

				new ViewContractSourceDialog(new Shell(), contractURL).open();
			}

			catch (Exception e) {
				Logger
						.error("Error occurred during display of contract source code", e);
			}
			// end catch.
		}
		// no else.
	}

	/**
	 * <p>
	 * Shows a dialog window to display the visualization of a selected
	 * {@link Contract}.
	 * </p>
	 */
	private void actionShowContractVizualisation() {

		Contract contract;
		contract = this.getSelectedContract();

		if (contract != null) {

			try {
				ContractVizViewer4Swing.show(contract);
			}

			catch (Exception e) {
				Logger.error("Error occurred during display of contract vizualisation",
						e);
			}
			// end catch.
		}
		// no else.
	}

	/**
	 * <p>
	 * Runs the verification of all {@link Contract}s.
	 * </p>
	 */
	private void actionVerifyAllContracts() {

		try {
			URI triggerVerifyAll;
			triggerVerifyAll =
					new URI(ManualTriggerVocabulary.TRIGGER_TYPE_VERIFY_ALL);

			ManualTriggerVocabulary manualTriggerVocabulary;
			manualTriggerVocabulary =
					(ManualTriggerVocabulary) EclipseTriggerRegistry.INSTANCE
							.getTriggerVocabulary(triggerVerifyAll);

			manualTriggerVocabulary.fireTriggerVerifyAll();
		}
		// end try.

		catch (URISyntaxException e) {
			Logger.error(
					"Unexpected exception during verification of all Contracts.", e);
		}

		catch (TreatyException e) {
			Logger.error(
					"Unexpected exception during verification of all Contracts.", e);
		}
		// end catch.
	}

	/**
	 * <p>
	 * Runs the verification for the currently selected {@link Contract}s.
	 * </p>
	 */
	private void actionVerifySelectedContracts() {

		Set<Contract> contracts;
		contracts = this.getSelectedInstantiatedContracts();

		if (contracts != null && !contracts.isEmpty()) {

			try {
				URI triggerVerifyAll;
				triggerVerifyAll =
						new URI(ManualTriggerVocabulary.TRIGGER_TYPE_VERIFY_SELECTED);

				ManualTriggerVocabulary manualTriggerVocabulary;
				manualTriggerVocabulary =
						(ManualTriggerVocabulary) EclipseTriggerRegistry.INSTANCE
								.getTriggerVocabulary(triggerVerifyAll);

				manualTriggerVocabulary.fireTriggerVerifySelected(contracts);
			}
			// end try.

			catch (URISyntaxException e) {
				Logger.error(
						"Unexpected exception during verification of selected Contracts.",
						e);
			}

			catch (TreatyException e) {
				Logger.error(
						"Unexpected exception during verification of all Contracts.", e);
			}
			// end catch.
		}
		// no else.
	}

	/**
	 * <p>
	 * A helper method that collects all instantiated {@link Contract}s of a given
	 * {@link EclipseExtensionPoint} and adds them to a given {@link Collection}.
	 * </p>
	 * 
	 * @param eclipseExtensionPoint
	 *          The extension point whose {@link Contract}s shall be added.
	 * @param contractList
	 *          The {@link Collection} to that the {@link Contract}s shall be
	 *          added.
	 */
	private void collectInstantiatedContracts(
			EclipseExtensionPoint eclipseExtensionPoint,
			Collection<Contract> contractList) {

		for (EclipseExtension eclipseExtension : eclipseExtensionPoint
				.getExtensions()) {
			this.collectInstantiatedContract(eclipseExtension, contractList);
		}
		// end for.
	}

	/**
	 * <p>
	 * A helper method that collects all instantiated {@link Contract}s of a given
	 * {@link EclipseExtension} and adds them to a given {@link Collection}.
	 * </p>
	 * 
	 * @param eclipseExtension
	 *          The extension point whose {@link Contract}s shall be added.
	 * @param contractList
	 *          The {@link Collection} to that the {@link Contract}s shall be
	 *          added.
	 */
	private void collectInstantiatedContract(EclipseExtension eclipseExtension,
			Collection<Contract> contractList) {

		for (Contract contract : eclipseExtension.getContracts()) {

			if (contract != null && contract.isInstantiated()) {
				contractList.add(contract);
			}
			// no else.

			/* Probably set annotation here. */
		}
		// end for.
	}

	/**
	 * <p>
	 * Initializes the pull down menu and the tool bar according to the context
	 * menu.
	 * </p>
	 */
	private void contributeToActionBars() {

		IActionBars bars = getViewSite().getActionBars();
		this.fillLocalPullDown(bars.getMenuManager());
		this.fillLocalToolBar(bars.getToolBarManager());
	}

	private void createExportAction(Exporter exporter) {

		/* Probably create the action. */
		if (!this.myActionsExport.containsKey(exporter)) {

			ExportAction exportAction;
			exportAction = new ExportAction(exporter);

			exportAction.setEnabled(true);
			exportAction.setText(exporter.getName());
			exportAction.setImageDescriptor(getImageDescriptor("icons/export.gif"));
			exportAction
					.setToolTipText("Export instantiated contracts and verification results (if available), exporter used: "
							+ exporter.getName());

			this.myActionsExport.put(exporter, exportAction);
		}
		// no else (action already exists).
	}

	/**
	 * <p>
	 * Initializes the context menu with its {@link Action}s.
	 * </p>
	 * 
	 * @param manager
	 *          The {@link IMenuManager} of the context menu.
	 */
	private void fillContextMenu(IMenuManager manager) {

		manager.add(this.myActionRefresh);
		manager.add(this.myActionVerifyAllContracts);
		manager.add(this.myActionVerifySelectedContracts);
		manager.add(this.myActionPrintStackTrace);
		manager.add(this.myActionShowContractSource);
		manager.add(this.myActionShowContractVizualisation);

		manager.add(new Separator());

		for (Action actExport : this.myActionsExport.values()) {
			manager.add(actExport);
		}

		manager.add(new Separator());

		this.myDrillDownAdapter.addNavigationActions(manager);

		/* Other plug-ins can contribute there actions here. */
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	/**
	 * <p>
	 * Initializes the pull down menu.
	 * </p>
	 * 
	 * @param manager
	 *          The {@link IMenuManager} used to initialize.
	 */
	private void fillLocalPullDown(IMenuManager manager) {

		manager.removeAll();

		manager.add(this.myActionRefresh);

		manager.add(new Separator());

		manager.add(this.myActionVerifyAllContracts);
		manager.add(this.myActionVerifySelectedContracts);

		manager.add(new Separator());

		for (Action actExport : this.myActionsExport.values()) {
			manager.add(actExport);
		}
		// end for.
	}

	/**
	 * <p>
	 * A helper method that fills the tool bar with icons for the {@link Action}s
	 * of the {@link ContractView}.
	 * </p>
	 * 
	 * @param toolBarManager
	 *          The {@link IToolBarManager} to that the {@link Action}s shall be
	 *          added.
	 */
	private void fillLocalToolBar(IToolBarManager toolBarManager) {

		toolBarManager.removeAll();

		toolBarManager.add(this.myActionRefresh);
		toolBarManager.add(this.myActionVerifyAllContracts);
		toolBarManager.add(this.myActionVerifySelectedContracts);

		toolBarManager.add(new Separator());

		for (Action actExport : this.myActionsExport.values()) {
			toolBarManager.add(actExport);
		}
		// end for.

		toolBarManager.add(new Separator());

		this.myDrillDownAdapter = new DrillDownAdapter(this.myViewer);
		this.myDrillDownAdapter.addNavigationActions(toolBarManager);
	}

	/**
	 * <p>
	 * Returns the {@link Contract} for a given {@link TreeObject}.
	 * </p>
	 * 
	 * @param treeObject
	 *          The {@link TreeObject} whose {@link Contract} shall be returned.
	 * @return The {@link Contract} for a given {@link TreeObject}.
	 */
	private Contract findContract(TreeObject treeObject) {

		Object adaptedObject = treeObject.getObject();

		// check whether this is a contract
		if (adaptedObject instanceof Contract) {
			return (Contract) adaptedObject;
		}

		// check whether the first child and only this is a contract
		// this will pick contracts when parents (extensions and ext points)
		// are selected
		if (treeObject instanceof TreeParent) {
			TreeObject[] children = ((TreeParent) treeObject).getChildren();
			if (children != null && children.length > 0
					&& children[0].getObject() instanceof Contract) {
				Contract contract = (Contract) children[0].getObject();
				boolean isUniqueSelection = true;
				for (int i = 1; i < children.length; i++) {
					isUniqueSelection =
							isUniqueSelection
									&& !(children[i].getObject() instanceof Contract);
				}
				if (isUniqueSelection)
					return contract;
			}
		}

		TreeObject parent = treeObject.getParent();
		if (parent != null) {
			return this.findContract(parent);
		}

		return null;
	}

	/**
	 * <p>
	 * Returns the {@link Image} for a given name. Probably the {@link Image} will
	 * be initialized.
	 * </p>
	 * 
	 * @param name
	 *          The name whose {@link Image} shall be returned.
	 * @return The {@link Image} to the given name.
	 */
	private Image getIcon(String name) {

		Image result;

		String path;
		path = "icons/" + name;

		result = this.myIcons.get(path);

		if (result == null) {
			result = getImageDescriptor(path).createImage();
			this.myIcons.put(path, result);
		}
		// no else.

		return result;
	}

	/**
	 * <p>
	 * Returns the currently in this {@link ContractView} selected
	 * {@link Contract}.
	 * </p>
	 * 
	 * @return The currently selected {@link Contract}.
	 */
	private Contract getSelectedContract() {

		Contract result;

		TreeItem[] selection;
		selection = myViewer.getTree().getSelection();

		if (selection == null || selection.length == 0) {
			result = null;
		}

		else {
			TreeObject treeObject;
			treeObject = (TreeObject) selection[0].getData();

			result = this.findContract(treeObject);
		}

		return result;
	}

	/**
	 * <p>
	 * Returns the instantiated {@link Contract}s that are part of the currently
	 * selected items in this {@link ContractView}.
	 * </p>
	 * 
	 * @return The instantiated {@link Contract}s that are part of the currently
	 *         selected items in this {@link ContractView}.
	 */
	private Set<Contract> getSelectedInstantiatedContracts() {

		Set<Contract> result;
		result = new HashSet<Contract>();

		Contract selectedContract;
		selectedContract = this.getSelectedContract();

		/* Probably use the selected contract. */
		if (selectedContract != null) {

			/* If the contract is instantiated, return it. */
			if (selectedContract.isInstantiated()) {
				result.add(selectedContract);
			}

			/* Else collect its instances. */
			else {

				EclipseExtensionPoint eclipseExtensionPoint;
				eclipseExtensionPoint =
						(EclipseExtensionPoint) selectedContract.getConsumer();

				this.collectInstantiatedContracts(eclipseExtensionPoint, result);
			}
			// end else.
		}

		/* Else try to find selected contracts. */
		else {

			TreeItem[] selection;
			selection = this.myViewer.getTree().getSelection();

			/* Check if the selection is empty. */
			if (selection != null && selection.length > 0) {

				Object selectedObject;
				selectedObject = ((TreeObject) selection[0].getData()).getObject();

				TreeItem parent;
				parent = selection[0].getParentItem();

				if (selectedObject instanceof EclipseExtension) {
					this.collectInstantiatedContract((EclipseExtension) selectedObject,
							result);
				}

				else if (selectedObject instanceof EclipseExtensionPoint) {
					this.collectInstantiatedContracts(
							(EclipseExtensionPoint) selectedObject, result);
				}

				else if (selectedObject instanceof EclipsePlugin) {

					EclipsePlugin eclipsePlugin;
					eclipsePlugin = (EclipsePlugin) selectedObject;

					if (parent != null
							&& LABEL_INSTANCES.equals(parent.getData().toString())) {

						/* Folder for instances selected. */
						for (EclipseExtension eclipseExtension : eclipsePlugin
								.getExtensions()) {
							this.collectInstantiatedContract(eclipseExtension, result);
						}
						// end for.
					}

					else {

						/* parent of extension points. */
						for (EclipseExtensionPoint eclipseExtensionPoint : eclipsePlugin
								.getExtensionPoints()) {
							this.collectInstantiatedContracts(eclipseExtensionPoint, result);
						}
						// end for.
					}
					// end else.
				}
				// no else.
			}
			// no else (empty selection).
		}
		// end else.

		return result;
	}

	/**
	 * <p>
	 * Returns the currently in the {@link ContractView} selected {@link Object}.
	 * Please note that this method already returns the adapted {@link Object},
	 * and not the {@link TreeObject}.
	 * </p>
	 * 
	 * @return The currently selected {@link Object}.
	 */
	private Object getSelectedObject() {

		Object result;

		TreeItem[] selection;
		selection = myViewer.getTree().getSelection();

		if (selection == null || selection.length == 0) {
			result = null;
		}

		else if (selection[0].getData() instanceof TreeObject) {
			result = ((TreeObject) selection[0].getData()).getObject();
		}

		else {
			result = selection[0].getData();
		}

		return result;
	}

	/**
	 * <p>
	 * Initializes the context menu of this {@link ContractView}.
	 * </p>
	 */
	private void hookContextMenu() {

		this.myContextMenuManager = new MenuManager("#PopupMenu");
		this.myContextMenuManager.setRemoveAllWhenShown(true);

		this.myContextMenuManager.addMenuListener(new IMenuListener() {

			public void menuAboutToShow(IMenuManager manager) {

				ContractView.this.fillContextMenu(manager);
			}
		});

		Menu menu;
		menu =
				this.myContextMenuManager.createContextMenu(this.myViewer.getControl());

		this.myViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(this.myContextMenuManager, this.myViewer);
	}

	/**
	 * <p>
	 * Creates all {@link Action}s of the {@link ContractView}.
	 * </p>
	 */
	private void makeActions() {

		/* Action to print the stack trace. */
		this.myActionPrintStackTrace = new Action() {

			public void run() {

				actionPrintStackTrace();
			}
		};

		this.myActionPrintStackTrace
				.setText("Display verification exception details.");
		this.myActionPrintStackTrace
				.setToolTipText("Displays the verification exception stack trace.");
		this.myActionPrintStackTrace.setEnabled(false);

		this.myActionRefresh = new Action() {

			public void run() {

				actionReloadContracts();
			}
		};

		this.myActionRefresh.setText("Reset");
		this.myActionRefresh
				.setImageDescriptor(getImageDescriptor("icons/refresh.gif"));
		this.myActionRefresh
				.setToolTipText("Reloads all contracts and resets verification status of all contracts.");

		/* Action to verify all contracts. */
		this.myActionVerifyAllContracts = new Action() {

			public void run() {

				actionVerifyAllContracts();
			}
		};

		this.myActionVerifyAllContracts.setText("Verify all contracts");
		this.myActionVerifyAllContracts
				.setImageDescriptor(getImageDescriptor("icons/verify_all.gif"));
		this.myActionVerifyAllContracts
				.setToolTipText("Run verification for all contracts.");

		/* Action to verify selected contracts. */
		this.myActionVerifySelectedContracts = new Action() {

			public void run() {

				actionVerifySelectedContracts();
			}
		};

		this.myActionVerifySelectedContracts.setText("Verify selected contracts");
		this.myActionVerifySelectedContracts
				.setImageDescriptor(getImageDescriptor("icons/verify_sel.gif"));
		this.myActionVerifySelectedContracts
				.setToolTipText("Runs verification for selected contracts.");
		this.myActionVerifySelectedContracts.setEnabled(false);

		/* Action to show a contracts source code. */
		this.myActionShowContractSource = new Action() {

			public void run() {

				actionShowContractSource();
			}
		};

		this.myActionShowContractSource.setEnabled(false);
		this.myActionShowContractSource.setText("Display contract source");
		this.myActionShowContractSource
				.setToolTipText("Displays the source code of the contract.");

		/* Action to show a contracts vizualisation. */
		this.myActionShowContractVizualisation = new Action() {

			public void run() {

				actionShowContractVizualisation();
			}
		};

		this.myActionShowContractVizualisation.setEnabled(false);
		this.myActionShowContractVizualisation
				.setText("Display contract vizualisation");
		this.myActionShowContractVizualisation
				.setToolTipText("Vizualises the selected contract.");

		/* Create an export action for each exporter. */
		for (Exporter exporter : ExporterRegistry.INSTANCE.getExporters()) {

			this.createExportAction(exporter);
		}
		// end for.
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
		this.myActionVerifyAllContracts.setEnabled(on);
		this.myActionRefresh.setEnabled(on);

		for (Action act : this.myActionsExport.values()) {
			act.setEnabled(on);
		}

		/* Probably verify selected, instantiated contracts. */
		Set<Contract> instantiatedConstracts;
		instantiatedConstracts = getSelectedInstantiatedContracts();

		this.myActionVerifySelectedContracts.setEnabled(on
				&& instantiatedConstracts != null && !instantiatedConstracts.isEmpty());
	}
}