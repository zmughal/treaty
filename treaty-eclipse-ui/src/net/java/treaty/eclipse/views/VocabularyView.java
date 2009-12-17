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

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

import net.java.treaty.eclipse.VocabularyRegistry;
import net.java.treaty.eclipse.ui.Activator;
import net.java.treaty.vocabulary.CompositeContractOntologyListener;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.RDFWriter;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.xmloutput.impl.Basic;

/**
 * <p>
 * Contract viewer to display the complete vocabulary of the currently running
 * Treaty framework.
 * </p>
 * 
 * @author Jens Dietrich
 */
public class VocabularyView extends ViewPart implements
		CompositeContractOntologyListener {

	/**
	 * <p>
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view, or
	 * ignore it and always show the same content (like Task List, for example).
	 * </p>
	 * 
	 * @author Jens Dietrich
	 */
	private class TreeObject implements IAdaptable {

		/** The Object of this {@link TreeObject}. */
		private Object object;

		/** The {@link TreePath} of this {@link TreeObject}. */
		private TreeParent parent;

		/**
		 * <p>
		 * Creates a new {@link TreeObject}.
		 * </p>
		 * 
		 * @param object
		 *          The {@link Object} that shall be adapted.
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

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {

			return this.getObject().toString();
		}

		/**
		 * <p>
		 * Returns the adapted {@link Object}.
		 * </p>
		 * 
		 * @return The adapted {@link Object}.
		 */
		public Object getObject() {

			return this.object;
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

		/**
		 * <p>
		 * Returns the {@link TreeParent} of this {@link TreeObject}.
		 * </p>
		 * 
		 * @return The {@link TreeParent} of this {@link TreeObject}.
		 */
		public TreeParent getParent() {

			return this.parent;
		}
	}

	/**
	 * <p>
	 * A {@link TreeParent} is a {@link TreeObject} that can contain other
	 * {@link TreeObject}s.
	 * </p>
	 * 
	 * @author Jens Dietrich
	 */
	private class TreeParent extends TreeObject {

		/** The children {@link TreeObject}s of this {@link TreeParent}. */
		private ArrayList<TreeObject> myChildren;

		/**
		 * <p>
		 * Creates a new {@link TreeParent}.
		 * </p>
		 * 
		 * @param object
		 *          The {@link Object} that shall be adapted.
		 */
		public TreeParent(Object object) {

			super(object);

			this.myChildren = new ArrayList<TreeObject>();
		}

		/**
		 * <p>
		 * Adds a {@link TreeObject} as child to this {@link TreeParent}.
		 * </p>
		 * 
		 * @param child
		 *          The {@link TreeObject} that shall be added.
		 */
		public void addChild(TreeObject child) {

			this.myChildren.add(child);
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

			return this.myChildren.toArray(new TreeObject[0]);
		}

		/**
		 * <p>
		 * Checks whether or not this {@link TreeParent} has {@link TreeObject}s as
		 * children.
		 * </p>
		 * 
		 * @return Whether or not this {@link TreeParent} has {@link TreeObject}s as
		 *         children.
		 */
		public boolean hasChildren() {

			return this.myChildren.size() > 0;
		}
	}

	/**
	 * <p>
	 * A helper class used to provided the content of the {@link Object}s that
	 * shall be displayed.
	 * </p>
	 * 
	 * @author Jens Dietrich
	 */
	private class ViewContentProvider implements IStructuredContentProvider,
			ITreeContentProvider {

		/** The maximum depth of the tree. Required to avoid cycles. */
		private final int MAX_TREE_DEPTH = 42;

		/** The root {@link TreeParent} object of the view. */
		private TreeParent invisibleRoot;

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
		 * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
		 * Object)
		 */
		public Object[] getChildren(Object parent) {

			Object[] result;

			if (parent instanceof TreeParent) {
				result = ((TreeParent) parent).getChildren();
			}

			else {
				result = new Object[0];
			}

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

			if (parent.equals(getViewSite())) {

				/* Probably create the root node. */
				if (this.invisibleRoot == null) {
					this.initialize();
				}
				// no else.

				result = getChildren(invisibleRoot);
			}

			else {
				result = this.getChildren(parent);
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

			if (child instanceof TreeObject) {
				result = ((TreeObject) child).getParent();
			}

			else {
				result = null;
			}

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

			if (parent instanceof TreeParent) {
				result = ((TreeParent) parent).hasChildren();
			}

			else {
				result = false;
			}

			return result;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
		 * .viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {

			/* Do nothing. */
		}

		/**
		 * <p>
		 * Adds the node for a given {@link OntModel} and its children nodes to a
		 * given {@link TreeParent}.
		 * </p>
		 * 
		 * @param parent
		 *          The {@link TreeParent} to which the node shall be added.
		 * @param ontology
		 *          The {@link OntModel} for that a node shall be added.
		 */
		private void addNodes(TreeParent parent, OntModel ontology) {

			/* Add all classes of the ontology. */
			Iterator<OntClass> ontClassIterator;
			ontClassIterator = ontology.listClasses();

			while (ontClassIterator.hasNext()) {

				this.addNodes(parent, ontClassIterator.next(), 0);
			}
			// end for.

			/* Add all properties of the ontology. */
			Iterator<OntProperty> ontPropertyIterator;
			ontPropertyIterator = ontology.listAllOntProperties();

			while (ontPropertyIterator.hasNext()) {

				this.addNodes(parent, ontPropertyIterator.next(), 0);
			}
			// end while.

			/* Add all datatype properties of the ontology. */
			Iterator<DatatypeProperty> datatypePropertyIterator;
			datatypePropertyIterator = ontology.listDatatypeProperties();

			while (datatypePropertyIterator.hasNext()) {

				this.addNodes(parent, datatypePropertyIterator.next(), 0);
			}
			// end while.
		}

		/**
		 * <p>
		 * A helper method that adds a node for a given {@link OntClass} and its
		 * subclasses to a given {@link TreeParent}.
		 * </p>
		 * 
		 * @param parent
		 *          The {@link TreeParent} to that the node(s) shall be added.
		 * @param ontClass
		 *          The {@link OntClass} for which a node shall be added.
		 * @param level
		 *          The depth at which the {@link OntClass} shall be added (used to
		 *          break cycles at a certain depth).
		 */
		private void addNodes(TreeParent parent, OntClass ontClass, int level) {

			/*
			 * Only adapt elements that have an owner annotation. Otherwise, the
			 * element is a core element of RDF itself and should not be displayed.
			 */
			if (level <= MAX_TREE_DEPTH && this.hasOwnerAnnotation(ontClass)) {

				Iterator<OntClass> subClassIterator;
				subClassIterator = ontClass.listSubClasses(true);

				/* Probably add children as well. */
				if (subClassIterator.hasNext()) {
					TreeParent node;
					node = new TreeParent(ontClass);

					while (subClassIterator.hasNext()) {
						this.addNodes(node, subClassIterator.next(), level + 1);
					}
					// end while.

					parent.addChild(node);
				}

				else {
					TreeObject node;
					node = new TreeObject(ontClass);

					parent.addChild(node);
				}
				// end else.
			}
			// no else.
		}

		/**
		 * <p>
		 * A helper method that adds a node for a given {@link OntProperty} and its
		 * sub-properties to a given {@link TreeParent}.
		 * </p>
		 * 
		 * @param parent
		 *          The {@link TreeParent} to that the node(s) shall be added.
		 * @param ontProperty
		 *          The {@link OntProperty} for which a node shall be added.
		 * @param level
		 *          The depth at which the {@link OntClass} shall be added (used to
		 *          break cycles at a certain depth).
		 */
		private void addNodes(TreeParent parent, OntProperty ontProperty, int level) {

			/*
			 * Only adapt elements that have an owner annotation. Otherwise, the
			 * element is a core element of RDF itself and should not be displayed.
			 */
			if (level <= MAX_TREE_DEPTH && this.hasOwnerAnnotation(ontProperty)) {

				Iterator<? extends OntProperty> subPropertyIterator;
				subPropertyIterator = ontProperty.listSubProperties(true);

				TreeParent node;
				node = new TreeParent(ontProperty);

				/* Probably add children as well. */
				if (subPropertyIterator.hasNext()) {

					while (subPropertyIterator.hasNext()) {
						this.addNodes(node, subPropertyIterator.next(), level + 1);
					}
					// end while.
				}
				// no else.

				parent.addChild(node);
			}
			// no else.
		}

		/**
		 * <p>
		 * Initializes the model from the ContractVocabularyRegistry.
		 * </p>
		 */
		private void initialize() {

			invisibleRoot = new TreeParent("");
			addNodes(invisibleRoot, VocabularyRegistry.INSTANCE.getOntology());
		}

		/**
		 * <p>
		 * A helper method that checks if a given {@link Resource} has an owner
		 * annotation.
		 * </p>
		 * 
		 * @param resource
		 *          The {@link Resource} that shall be checked.
		 * @return <code>true</code> if the given {@link Resource} has an owner
		 *         annotation.
		 */
		private boolean hasOwnerAnnotation(Resource resource) {

			boolean result;

			try {
				URI resourceURI;
				resourceURI = new URI(resource.getURI());

				String ownerAnnotation;
				ownerAnnotation =
						VocabularyRegistry.INSTANCE.getOwnerAnnotation(resourceURI);

				result = ownerAnnotation != null && ownerAnnotation.length() > 0;
			}

			catch (URISyntaxException e) {
				result = false;
			}

			return result;
		}
	}

	/**
	 * <p>
	 * The {@link ViewLabelProvider} is used to display the content on the view.
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

			/* Remains empty. */
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang
		 * .Object, int)
		 */
		public Image getColumnImage(Object treeObject, int collumn) {

			Image result;
			Object object;

			result = null;
			object = ((TreeObject) treeObject).getObject();

			if (collumn == 0 && object instanceof OntClass) {
				result = ICON_TYPE;
			}

			else if (collumn == 0 && object instanceof DatatypeProperty) {
				result = ICON_DATATYPE_PROPERTY;
			}

			else if (collumn == 0 && object instanceof OntProperty) {
				result = ICON_PREDICATE;
			}

			else if (collumn == 1) {
				String text;
				text = this.getColumnText(treeObject, collumn);

				if (text != null && text.length() > 0) {
					result = ICON_PLUGIN;
				}
				// no else.
			}

			else if (collumn == 2) {
				String text;
				text = this.getColumnText(treeObject, collumn);

				if (text != null && text.length() > 0) {
					result = ICON_TYPE;
				}
				// no else.
			}
			// no else.

			return result;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang
		 * .Object, int)
		 */
		public String getColumnText(Object treeObject, int collumn) {

			String result;
			Object object;

			object = ((TreeObject) treeObject).getObject();

			if (collumn == 0 && object instanceof Resource) {

				result = ((Resource) object).getURI();

				if (result == null) {
					result = object.toString();
				}
				// no else.
			}

			else if (collumn == 1 && object instanceof Resource) {

				Resource resource;
				resource = (Resource) object;

				try {
					URI resourceURI;
					resourceURI = new URI(resource.getURI());

					result = VocabularyRegistry.INSTANCE.getOwnerAnnotation(resourceURI);
				}

				catch (URISyntaxException e) {
					result = "";
				}
			}

			else if (collumn == 2 && object instanceof OntProperty) {

				OntProperty ontProperty;
				ontProperty = (OntProperty) object;

				StringBuffer buffer;
				buffer = new StringBuffer();

				Iterator<? extends OntResource> rangeIterator;
				rangeIterator = ontProperty.listRange();

				while (rangeIterator.hasNext()) {

					OntResource range;
					range = rangeIterator.next();

					if (buffer.length() > 0) {
						buffer.append(',');
					}
					// no else.

					buffer.append(range.getURI());
				}
				// end while.

				result = buffer.toString();
			}

			else {
				result = "";
			}

			return result;
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
		 */
		public void dispose() {

			/* Remains empty. */
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang
		 * .Object, java.lang.String)
		 */
		public boolean isLabelProperty(Object arg0, String arg1) {

			return false;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse
		 * .jface.viewers.ILabelProviderListener)
		 */
		public void removeListener(ILabelProviderListener arg0) {

			/* Remains empty. */
		}
	}

	/** Icon used to display plug-ins. */
	private final Image ICON_PLUGIN =
			this.getImageDescriptor("icons/plugin.gif").createImage();

	/** Icon used to display predicates. */
	private final Image ICON_PREDICATE =
			this.getImageDescriptor("icons/owl_property.gif").createImage();

	/** Icon used to display datatype properties. */
	private final Image ICON_DATATYPE_PROPERTY =
			this.getImageDescriptor("icons/owl_datatypeproperty.gif").createImage();

	/** Icon used to display types. */
	private final Image ICON_TYPE =
			this.getImageDescriptor("icons/owl_class.gif").createImage();

	/** The {@link Action} used to refresh this {@link VocabularyView}. */
	private Action actionRefresh;

	/** The {@link DrillDownAdapter} of this {@link VocabularyView}. */
	private DrillDownAdapter drillDownAdapter;

	/**
	 * A {@link StyledText} belonging to this {@link VocabularyView} that is used
	 * to display the whole ontology as text file.
	 */
	private StyledText text;

	/** The {@link TreeViewer} of this View. */
	private TreeViewer viewer;

	/**
	 * <p>
	 * Creates a new {@link VocabularyView}.
	 * </p>
	 */
	public VocabularyView() {

		/* Register as listener of the VocabularyRegistry. */
		VocabularyRegistry.INSTANCE.addListener(this);
	}

	/**
	 * <p>
	 * This is a call-back that will allow us to create the viewer and initialize
	 * it.
	 * <p>
	 * 
	 * @parent The parent {@link Composite} of the viewer.
	 */
	public void createPartControl(Composite parent) {

		final TabFolder tabFolder = new TabFolder(parent, SWT.BORDER);

		TabItem tabItem1;
		tabItem1 = new TabItem(tabFolder, SWT.NULL);
		tabItem1.setText("Types and Properties");

		this.viewer =
				new TreeViewer(tabFolder, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL
						| SWT.FULL_SELECTION);
		this.drillDownAdapter = new DrillDownAdapter(this.viewer);

		this.viewer.getTree().setHeaderVisible(true);

		TreeColumn column1;
		column1 = new TreeColumn(this.viewer.getTree(), SWT.LEFT);
		column1.setText("Type or Property");
		column1.setWidth(350);

		TreeColumn column2 = new TreeColumn(this.viewer.getTree(), SWT.LEFT);
		column2.setText("Contributed by");
		column2.setWidth(300);

		TreeColumn column3 = new TreeColumn(this.viewer.getTree(), SWT.LEFT);
		column3.setText("Property Type (Range)");
		column3.setWidth(200);

		this.viewer.setContentProvider(new ViewContentProvider());
		this.viewer.setLabelProvider(new ViewLabelProvider());
		this.viewer.setInput(getViewSite());

		this.makeActions();
		this.hookContextMenu();
		this.contributeToActionBars();

		tabItem1.setControl(viewer.getControl());

		TabItem tabItem2;
		tabItem2 = new TabItem(tabFolder, SWT.NULL);
		tabItem2.setText("merged ontology (owl)");

		this.text =
				new StyledText(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		this.text.setText(this.getOntologyAsRDF());
		tabItem2.setControl(this.text);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	public void setFocus() {

		/* Passing the focus request to the viewer's control. */
		this.viewer.getControl().setFocus();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.vocabulary.CompositeContractOntologyListener#update()
	 */
	public void update() {

		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			public void run() {

				actionRefresh();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {

		/* Un-register as listener of the VocabularyRegistry. */
		VocabularyRegistry.INSTANCE.removeListener(this);

		/* Dispose the icons. */
		ICON_PLUGIN.dispose();
		ICON_PREDICATE.dispose();
		ICON_TYPE.dispose();
		ICON_DATATYPE_PROPERTY.dispose();

		super.dispose();
	}

	/**
	 * <p>
	 * Refreshes the {@link VocabularyView} by reloading the Vocabulary.
	 * </p>
	 */
	private void actionRefresh() {

		/* Update the icon provider list. */
		IconProvider.initializeProviderList();

		/* Update the viewer. */
		this.viewer.setContentProvider(new ViewContentProvider());
		this.viewer.setInput(this.getViewSite());

		this.text.setText(this.getOntologyAsRDF());
	}

	/**
	 * <p>
	 * A helper method to initialize the pull down menu and the tool bar with the
	 * same elements as the action bar.
	 * </p>
	 */
	private void contributeToActionBars() {

		IActionBars bars = getViewSite().getActionBars();

		this.fillLocalPullDown(bars.getMenuManager());
		this.fillLocalToolBar(bars.getToolBarManager());
	}

	/**
	 * <p>
	 * A helper method to fill the context menu with actions.
	 * </p>
	 * 
	 * @param manager
	 *          The {@link IMenuManager} used to add the actions.
	 */
	private void fillContextMenu(IMenuManager manager) {

		manager.add(actionRefresh);
		manager.add(new Separator());

		this.drillDownAdapter.addNavigationActions(manager);

		/* Other plug-ins can contribute there actions here. */

		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	/**
	 * <p>
	 * A helper method that fills the pull down menu with actions.
	 * </p>
	 * 
	 * @param manager
	 *          The {@link IMenuManager} used to add the actions.
	 */
	private void fillLocalPullDown(IMenuManager manager) {

		manager.add(actionRefresh);
	}

	/**
	 * <p>
	 * A helper method that fills the ToolBar with its actions.
	 * </p>
	 * 
	 * @param manager
	 *          The {@link IToolBarManager} used to add the actions.
	 */
	private void fillLocalToolBar(IToolBarManager manager) {

		manager.add(this.actionRefresh);
		manager.add(new Separator());

		this.drillDownAdapter.addNavigationActions(manager);
	}

	/**
	 * <p>
	 * Returns an {@link ImageDescriptor} for a given path.
	 * </p>
	 * 
	 * @param path
	 *          The path as a String.
	 * @return An {@link ImageDescriptor} for a given path.
	 */
	private ImageDescriptor getImageDescriptor(String path) {

		return AbstractUIPlugin
				.imageDescriptorFromPlugin(Activator.PLUGIN_ID, path);
	}

	/**
	 * <p>
	 * Returns an RDF representation of the ontology as a {@link String}.
	 * </p>
	 * 
	 * @return An RDF representation of the ontology as a {@link String}.
	 */
	private String getOntologyAsRDF() {

		RDFWriter rdfWriter;
		rdfWriter = new Basic();

		OutputStream outputStream;
		outputStream = new ByteArrayOutputStream();

		rdfWriter.write(VocabularyRegistry.INSTANCE.getOntology(), outputStream,
				null);

		return outputStream.toString();
	}

	/**
	 * <p>
	 * A helper method to hook in the Context Menu.
	 * </p>
	 */
	private void hookContextMenu() {

		MenuManager menuManager;

		menuManager = new MenuManager("#PopupMenu");
		menuManager.setRemoveAllWhenShown(true);

		menuManager.addMenuListener(new IMenuListener() {

			/*
			 * (non-Javadoc)
			 * @see
			 * org.eclipse.jface.action.IMenuListener#menuAboutToShow(org.eclipse.
			 * jface.action.IMenuManager)
			 */
			public void menuAboutToShow(IMenuManager manager) {

				VocabularyView.this.fillContextMenu(manager);
			}
		});

		Menu menu;

		menu = menuManager.createContextMenu(this.viewer.getControl());
		this.viewer.getControl().setMenu(menu);
		this.getSite().registerContextMenu(menuManager, this.viewer);
	}

	/**
	 * <p>
	 * A helper method to create the {@link Action}s required for this
	 * {@link VocabularyView}.
	 * </p>
	 */
	private void makeActions() {

		this.actionRefresh = new Action() {

			/*
			 * (non-Javadoc)
			 * @see org.eclipse.jface.action.Action#run()
			 */
			public void run() {

				actionRefresh();
			}
		};

		this.actionRefresh.setText("refresh");
		this.actionRefresh.setToolTipText("reload vocabulary");
		this.actionRefresh.setImageDescriptor(this
				.getImageDescriptor("icons/refresh.gif"));
	}
}