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

import java.net.URI;
import java.util.ArrayList;
import java.util.Set;

import net.java.treaty.TreatyException;
import net.java.treaty.action.ActionVocabulary;
import net.java.treaty.eclipse.Logger;
import net.java.treaty.eclipse.action.EclipseActionRegistry;
import net.java.treaty.eclipse.trigger.EclipseTriggerRegistry;
import net.java.treaty.eclipse.ui.Activator;
import net.java.treaty.trigger.TriggerRegistryListener;
import net.java.treaty.trigger.TriggerVocabulary;

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
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
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

/**
 * <p>
 * Viewer to display all available Triggers and Actions.
 * </p>
 * 
 * @author Claas Wilke
 */
public class TriggerActionView extends ViewPart implements
		TriggerRegistryListener {

	/**
	 * <p>
	 * A {@link TreeParent} is a {@link TriggerOrAction} that can contain other
	 * {@link TriggerOrAction}s.
	 * </p>
	 * 
	 * @author Jens Dietrich
	 */
	private class TreeParent extends TriggerOrAction {

		/** The children {@link TriggerOrAction}s of this {@link TreeParent}. */
		private ArrayList<TriggerOrAction> myChildren;

		/**
		 * <p>
		 * Creates a new {@link TreeParent}.
		 * </p>
		 * 
		 * @param uri
		 *          The {@link URI} of this {@link TriggerOrAction}.
		 * @param type
		 *          The type of this {@link TriggerOrAction}.
		 * @param owner
		 *          The owner of this {@link TriggerOrAction} (a vocabulary
		 *          represented by a {@link String}).
		 */
		public TreeParent(URI uri, TriggerOrActionType type, String owner) {

			super(uri, type, owner);

			this.myChildren = new ArrayList<TriggerOrAction>();
		}

		/**
		 * <p>
		 * Adds a {@link TriggerOrAction} as child to this {@link TreeParent}.
		 * </p>
		 * 
		 * @param child
		 *          The {@link TriggerOrAction} that shall be added.
		 */
		public void addChild(TriggerOrAction child) {

			this.myChildren.add(child);
			child.setParent(this);
		}

		/**
		 * <p>
		 * Returns all children {@link TriggerOrAction}s of this {@link TreeParent}.
		 * </p>
		 * 
		 * @return All children {@link TriggerOrAction}s of this {@link TreeParent}.
		 */
		public TriggerOrAction[] getChildren() {

			return this.myChildren.toArray(new TriggerOrAction[0]);
		}

		/**
		 * <p>
		 * Checks whether or not this {@link TreeParent} has {@link TriggerOrAction}
		 * s as children.
		 * </p>
		 * 
		 * @return Whether or not this {@link TreeParent} has
		 *         {@link TriggerOrAction}s as children.
		 */
		public boolean hasChildren() {

			return this.myChildren.size() > 0;
		}
	}

	/**
	 * <p>
	 * A trigger or Action displayed in this {@link TriggerActionView}.
	 * </p>
	 * 
	 * @author Jens Dietrich
	 */
	private class TriggerOrAction implements IAdaptable {

		/**
		 * The owner of this {@link TriggerOrAction} (a vocabulary represented by a
		 * {@link String}).
		 */
		private String owner;

		/** The {@link TreeParent} of this {@link TriggerOrAction}. */
		private TreeParent parent;

		/** The type of this {@link TriggerOrAction}. */
		private TriggerOrActionType type;

		/** The {@link URI} of this {@link TriggerOrAction}. */
		private URI uri;

		/**
		 * <p>
		 * Creates a new {@link TriggerOrAction}.
		 * </p>
		 * 
		 * @param uri
		 *          The {@link URI} of this {@link TriggerOrAction}.
		 * @param type
		 *          The type of this {@link TriggerOrAction}.
		 * @param owner
		 *          The owner of this {@link TriggerOrAction} (a vocabulary
		 *          represented by a {@link String}).
		 */
		public TriggerOrAction(URI uri, TriggerOrActionType type, String owner) {

			this.uri = uri;
			this.type = type;
			this.owner = owner;
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

			StringBuffer buffer;
			buffer = new StringBuffer();

			buffer.append(this.getClass().getSimpleName());
			buffer.append("[");
			buffer.append(this.uri);
			buffer.append(",");
			buffer.append(this.type);
			buffer.append(",");
			buffer.append(this.owner);
			buffer.append("]");

			return buffer.toString();
		}

		/**
		 * <p>
		 * Returns the owner of this {@link TriggerOrAction} (a vocabulary
		 * represented by a {@link String}).
		 * </p>
		 * 
		 * @return The owner of this {@link TriggerOrAction} (a vocabulary
		 *         represented by a {@link String}).
		 */
		public String getOwner() {

			return this.owner;
		}

		/**
		 * <p>
		 * Returns the {@link TreeParent} of this {@link TriggerOrAction}.
		 * </p>
		 * 
		 * @return The {@link TreeParent} of this {@link TriggerOrAction}.
		 */
		public Object getParent() {

			return this.parent;
		}

		/**
		 * <p>
		 * Returns the type of this {@link TriggerOrAction}.
		 * </p>
		 * 
		 * @return The type of this {@link TriggerOrAction}.
		 */
		public TriggerOrActionType getType() {

			return this.type;
		}

		/**
		 * <p>
		 * Returns the {@link URI} of this {@link TriggerOrAction}.
		 * </p>
		 * 
		 * @return The {@link URI} of this {@link TriggerOrAction}.
		 */
		public URI getUri() {

			return this.uri;
		}

		/**
		 * <p>
		 * Sets the {@link TreeParent} of this {@link TriggerOrAction}.
		 * </p>
		 * 
		 * @param treeParent
		 *          The {@link TreeParent} of this {@link TriggerOrAction}.
		 */
		public void setParent(TreeParent treeParent) {

			this.parent = treeParent;
		}
	}

	/** Represents the Types of {@link TriggerOrAction}s. */
	public enum TriggerOrActionType {
		Trigger, Action
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

		/** Maximum depth of the tree. Used to avoid cycles. */
		private static final int MAX_DEPTH = 42;

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

				result = getChildren(this.invisibleRoot);
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

			if (child instanceof TriggerOrAction) {
				result = ((TriggerOrAction) child).getParent();
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
		 * Initializes the model from the ContractVocabularyRegistry.
		 * </p>
		 */
		private void initialize() {

			invisibleRoot = new TreeParent(null, null, "");
			this.addTriggerNodes(invisibleRoot, EclipseTriggerRegistry.INSTANCE
					.getTriggerVocabularies());
			this.addActionNodes(invisibleRoot, EclipseActionRegistry.INSTANCE
					.getActionVocabularies());
		}

		/**
		 * <p>
		 * Adds the node for a given {@link Set} of {@link ActionVocabulary} and its
		 * children nodes to a given {@link TreeParent}.
		 * </p>
		 * 
		 * @param parent
		 *          The {@link TreeParent} to which the node shall be added.
		 * @param actionVocabularies
		 *          The {@link ActionVocabulary}s for that nodes shall be added.
		 */
		private void addActionNodes(TreeParent parent,
				Set<ActionVocabulary> actionVocabularies) {

			for (ActionVocabulary actionVocabulary : actionVocabularies) {

				/* Add all triggers of the vocabulary. */
				try {
					for (URI trigger : actionVocabulary.getActions()) {

						this.addActionNodes(parent, trigger, actionVocabulary);
					}
				}

				catch (TreatyException e) {
					Logger.error(
							"Unexpected TreatyException during update of LoggerActionView.",
							e);
				}
				// end for.
			}
			// end for.
		}

		/**
		 * <p>
		 * Adds a node for a given trigger.
		 * </p>
		 * 
		 * @param parent
		 *          The owning {@link TreeParent}.
		 * @param type
		 *          The trigger type {@link URI}.
		 * @param actionVocabulary
		 *          The owning {@link ActionVocabulary}.
		 */
		private void addActionNodes(TreeParent parent, URI type,
				ActionVocabulary actionVocabulary) {

			TriggerOrAction node;
			node =
					new TriggerOrAction(type, TriggerOrActionType.Action,
							actionVocabulary.toString());

			parent.addChild(node);
		}

		/**
		 * <p>
		 * Adds the node for a given {@link Set} of {@link TriggerVocabulary} and
		 * its children nodes to a given {@link TreeParent}.
		 * </p>
		 * 
		 * @param parent
		 *          The {@link TreeParent} to which the node shall be added.
		 * @param triggerVocabularies
		 *          The {@link TriggerVocabulary}s for that nodes shall be added.
		 */
		private void addTriggerNodes(TreeParent parent,
				Set<TriggerVocabulary> triggerVocabularies) {

			for (TriggerVocabulary triggerVocabulary : triggerVocabularies) {

				/* Add all triggers of the vocabulary. */
				try {
					for (URI trigger : triggerVocabulary.getTriggers()) {

						this.addTriggerNode(parent, trigger, triggerVocabulary, 0);
					}
				}

				catch (TreatyException e) {
					Logger.error(
							"Unexpected TreatyException during LoggerActionView update.", e);
				}
				// end for.
			}
			// end for.
		}

		/**
		 * <p>
		 * Adds a node for a given trigger.
		 * </p>
		 * 
		 * @param parent
		 *          The owning {@link TreeParent}.
		 * @param type
		 *          The trigger type {@link URI}.
		 * @param triggerVocabulary
		 *          The owning {@link TriggerVocabulary}.
		 * @param depth
		 *          The current depth of the tree.
		 */
		private void addTriggerNode(TreeParent parent, URI type,
				TriggerVocabulary triggerVocabulary, int depth) {

			if (depth <= MAX_DEPTH) {
				try {

					Set<URI> subTriggers;
					subTriggers = triggerVocabulary.getSubTriggers(type);

					if (subTriggers.size() == 0) {
						TriggerOrAction node;
						node =
								new TriggerOrAction(type, TriggerOrActionType.Trigger,
										triggerVocabulary.toString());

						parent.addChild(node);
					}

					else {
						TreeParent node;
						node =
								new TreeParent(type, TriggerOrActionType.Trigger,
										triggerVocabulary.toString());
						for (URI subTrigger : subTriggers) {
							this.addTriggerNode(node, subTrigger, triggerVocabulary, depth++);
						}

						parent.addChild(node);
					}
				}

				catch (TreatyException e) {
					Logger
							.error(
									"Unexpected TreatyEception during update of LoggerActionView.",
									e);
				}
			}
			// no else (max depth reached).
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
			TriggerOrAction triggerOrAction;

			result = null;
			triggerOrAction = ((TriggerOrAction) treeObject);

			if (collumn == 0) {

				if (triggerOrAction.getType() == TriggerOrActionType.Trigger) {
					result = ICON_TRIGGER;
				}

				else {
					result = ICON_ACTION;
				}
				// end else.
			}

			else if (collumn == 1) {

				try {
					if (triggerOrAction.getType() == TriggerOrActionType.Action) {
						if (EclipseActionRegistry.INSTANCE
								.isDefaultOnFailure(triggerOrAction.getUri())
								&& EclipseActionRegistry.INSTANCE
										.isDefaultOnSuccess(triggerOrAction.getUri())
								&& EclipseActionRegistry.INSTANCE
										.isBeforeAction(triggerOrAction.getUri())
								&& EclipseActionRegistry.INSTANCE.isAfterAction(triggerOrAction
										.getUri())) {
							result = ICON_YES;
						}

						else if (EclipseActionRegistry.INSTANCE
								.isDefaultOnFailure(triggerOrAction.getUri())
								|| EclipseActionRegistry.INSTANCE
										.isDefaultOnSuccess(triggerOrAction.getUri())
								|| EclipseActionRegistry.INSTANCE
										.isBeforeAction(triggerOrAction.getUri())
								|| EclipseActionRegistry.INSTANCE.isAfterAction(triggerOrAction
										.getUri())) {
							result = ICON_PARTS;
						}

						else {
							result = ICON_NO;
						}
					}

					else if (triggerOrAction.getType() == TriggerOrActionType.Trigger) {
						try {
							if (EclipseTriggerRegistry.INSTANCE
									.isDefaultTrigger(triggerOrAction.getUri())) {
								result = ICON_YES;
							}

							else {
								result = ICON_NO;
							}
						}

						catch (TreatyException e) {
							result = ICON_NO;
						}
					}
					// no else.
				}

				catch (TreatyException e) {
					Logger.error(
							"Unexpected TreatyException during update of LoggerActionView.",
							e);
				}
			}

			else if (collumn == 2) {
				result = ICON_VOCABULARY;
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
			TriggerOrAction triggerOrAction;

			triggerOrAction = ((TriggerOrAction) treeObject);

			if (collumn == 0) {

				result = triggerOrAction.getUri().toString();
			}

			else if (collumn == 1) {

				if (triggerOrAction.getType() == TriggerOrActionType.Action) {

					try {
						StringBuffer buffer;
						buffer = new StringBuffer();

						if (EclipseActionRegistry.INSTANCE.isBeforeAction(triggerOrAction
								.getUri())) {
							buffer.append("before");
						}
						// no else.

						if (EclipseActionRegistry.INSTANCE.isAfterAction(triggerOrAction
								.getUri())) {

							if (buffer.length() > 0) {
								buffer.append(" / ");
							}
							// no else.

							buffer.append("after");
						}
						// no else.

						if (EclipseActionRegistry.INSTANCE
								.isDefaultOnFailure(triggerOrAction.getUri())) {

							if (buffer.length() > 0) {
								buffer.append(" / ");
							}
							// no else.

							buffer.append("onFailure");
						}
						// no else.

						if (EclipseActionRegistry.INSTANCE
								.isDefaultOnSuccess(triggerOrAction.getUri())) {

							if (buffer.length() > 0) {
								buffer.append(" / ");
							}
							// no else.

							buffer.append("onSuccess");
						}
						// no else.

						if (buffer.length() == 0) {
							buffer.append("no");
						}
						// no else.

						result = buffer.toString();
					}

					catch (TreatyException e) {
						Logger
								.error(
										"Unexpected TreatyException during update of LoggerActionView.",
										e);
						result = "";
					}
				}

				else if (triggerOrAction.getType() == TriggerOrActionType.Trigger) {

					try {
						if (EclipseTriggerRegistry.INSTANCE
								.isDefaultTrigger(triggerOrAction.getUri())) {
							result = "yes";
						}

						else {
							result = "no";
						}
					}

					catch (TreatyException e) {
						result = "no";
					}
				}

				else {
					result = "";
				}
			}

			else if (collumn == 2) {

				result = triggerOrAction.getOwner();
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

	/** Icon used to display actions. */
	private final Image ICON_ACTION =
			this.getImageDescriptor("icons/action.gif").createImage();

	/** Icon used to display owning vocabularies. */
	private final Image ICON_VOCABULARY =
			this.getImageDescriptor("icons/vocabulary.gif").createImage();

	/** Icon used to display triggers. */
	private final Image ICON_TRIGGER =
			this.getImageDescriptor("icons/trigger.gif").createImage();

	/** Icon used to enable default trigger or action. */
	private final Image ICON_YES =
			this.getImageDescriptor("icons/status_success.gif").createImage();

	/** Icon used to enable partly default trigger or action. */
	private final Image ICON_PARTS =
			this.getImageDescriptor("icons/status_open.gif").createImage();

	/** Icon used to enable no default trigger or action. */
	private final Image ICON_NO =
			this.getImageDescriptor("icons/status_failure.gif").createImage();

	/** The {@link Action} used to refresh this {@link TriggerActionView}. */
	private Action actionRefresh;

	/**
	 * The {@link Action} to show the details for an entry in the
	 * {@link TriggerActionView}.
	 */
	private Action actionShowDetails;

	/** The {@link DrillDownAdapter} of this {@link TriggerActionView}. */
	private DrillDownAdapter drillDownAdapter;

	/** The {@link TreeViewer} of this View. */
	private TreeViewer viewer;

	/**
	 * <p>
	 * Creates a new {@link TriggerActionView}.
	 * </p>
	 */
	public TriggerActionView() {

		/* Register as listener of the VocabularyRegistry. */
		EclipseTriggerRegistry.INSTANCE.addListener(this);
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
		tabItem1.setText("Triggers and Actions");

		this.viewer =
				new TreeViewer(tabFolder, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL
						| SWT.FULL_SELECTION);
		this.drillDownAdapter = new DrillDownAdapter(this.viewer);

		this.viewer.getTree().setHeaderVisible(true);

		TreeColumn column1;
		column1 = new TreeColumn(this.viewer.getTree(), SWT.LEFT);
		column1.setText("Tigger or Action");
		column1.setWidth(350);

		TreeColumn column2 = new TreeColumn(this.viewer.getTree(), SWT.LEFT);
		column2.setText("default");
		column2.setWidth(150);

		TreeColumn column3 = new TreeColumn(this.viewer.getTree(), SWT.LEFT);
		column3.setText("Contributed by");
		column3.setWidth(300);

		this.viewer.setContentProvider(new ViewContentProvider());
		this.viewer.setLabelProvider(new ViewLabelProvider());
		this.viewer.setInput(getViewSite());

		this.makeActions();
		this.hookContextMenu();
		this.contributeToActionBars();

		tabItem1.setControl(viewer.getControl());
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
	 * @see
	 * net.java.treaty.eclipse.trigger.TriggerRegistryListener#triggerVocabularyAdded
	 * (net.java.treaty.trigger.TriggerVocabulary)
	 */
	public void triggerVocabularyAdded(TriggerVocabulary triggerVocabulary) {

		this.updateView();
	}

	/*
	 * (non-Javadoc)
	 * @seenet.java.treaty.eclipse.trigger.TriggerRegistryListener#
	 * triggerVocabularyRemoved(net.java.treaty.trigger.TriggerVocabulary)
	 */
	public void triggerVocabularyRemoved(TriggerVocabulary triggerVocabulary) {

		this.updateView();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {

		EclipseTriggerRegistry.INSTANCE.removeListener(this);

		/* Dispose the icons. */
		ICON_ACTION.dispose();
		ICON_VOCABULARY.dispose();
		ICON_TRIGGER.dispose();
		ICON_YES.dispose();
		ICON_PARTS.dispose();
		ICON_NO.dispose();

		super.dispose();
	}

	/**
	 * <p>
	 * Refreshes the {@link TriggerActionView} by reloading the Vocabulary.
	 * </p>
	 */
	private void actionRefresh() {

		/* Update the viewer. */
		this.viewer.setContentProvider(new ViewContentProvider());
		this.viewer.setInput(this.getViewSite());
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

		manager.add(this.actionRefresh);
		manager.add(this.actionShowDetails);

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

		manager.add(this.actionRefresh);
		manager.add(this.actionShowDetails);

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

				TriggerActionView.this.fillContextMenu(manager);
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
	 * {@link TriggerActionView}.
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

		this.actionRefresh.setText("Refresh");
		this.actionRefresh.setToolTipText("Reload Triggers and Actions");
		this.actionRefresh.setImageDescriptor(this
				.getImageDescriptor("icons/refresh.gif"));

		this.actionShowDetails = new Action() {

			/*
			 * (non-Javadoc)
			 * @see org.eclipse.jface.action.Action#run()
			 */
			public void run() {

				showDetails();
			}
		};

		this.actionShowDetails.setText("Show details");
		this.actionShowDetails
				.setToolTipText("Shows details for a trigger or action.");
	}

	/**
	 * <p>
	 * Action behavior to show the details for an entry in the
	 * {@link TriggerActionView}.
	 * </p>
	 */
	private void showDetails() {

		ITreeSelection selection;
		selection = (ITreeSelection) this.viewer.getSelection();

		StringBuffer detailsBuffer;
		detailsBuffer = new StringBuffer();

		if (selection != null) {
			Object selectedEntry;
			selectedEntry = selection.getFirstElement();

			if (selectedEntry instanceof TriggerOrAction) {

				TriggerOrAction triggerOrAction;
				triggerOrAction = (TriggerOrAction) selectedEntry;

				switch (triggerOrAction.type) {

				case Action:

					detailsBuffer.append("Action: " + triggerOrAction.getUri().toString()
							+ "\n\n");

					try {
						String description;
						description =
								EclipseActionRegistry.INSTANCE.getDescription(triggerOrAction
										.getUri());

						if (description != null && description.length() > 0) {
							detailsBuffer.append("Description:\n" + description + "\n");
						}
						// no else.

						if (EclipseActionRegistry.INSTANCE.isBeforeAction(triggerOrAction
								.getUri())) {
							detailsBuffer.append("\nCaused before each verification.");
						}
						// no else.

						if (EclipseActionRegistry.INSTANCE.isAfterAction(triggerOrAction
								.getUri())) {
							detailsBuffer.append("\nCaused after each verification.");
						}
						// no else.

						if (EclipseActionRegistry.INSTANCE
								.isDefaultOnFailure(triggerOrAction.getUri())) {
							detailsBuffer
									.append("\nCaused after failed verification of every contract.");
						}
						// no else.

						if (EclipseActionRegistry.INSTANCE
								.isDefaultOnSuccess(triggerOrAction.getUri())) {
							detailsBuffer
									.append("\nCaused after successfull verification of every contract.");
						}
						// no else.
						
						detailsBuffer
						.append("\nCaused after failed or successfull verification when explicitely declared in the contract.");
					}

					catch (TreatyException e) {
						/* Do nothing. */
					}

					break;

				case Trigger:
					
					detailsBuffer.append("Trigger: " + triggerOrAction.getUri().toString()
							+ "\n\n");

					try {
						String description;
						description =
								EclipseTriggerRegistry.INSTANCE.getDescription(triggerOrAction
										.getUri());

						if (description != null && description.length() > 0) {
							detailsBuffer.append("Description:\n" + description + "\n");
						}
						// no else.

						if (EclipseTriggerRegistry.INSTANCE.isDefaultTrigger(triggerOrAction
								.getUri())) {
							detailsBuffer.append("\nCan trigger verification of every contract.");
						}
						
						else  {
							detailsBuffer.append("\nTriggers a contract's verification only if declared in the contract.");
						}
					}

					catch (TreatyException e) {
						/* Do nothing. */
					}

					break;

				// no default.
				}
				// end switch.
			}

			else {
				detailsBuffer.append("Unknown type of selected element.");
			}
		}

		else {
			detailsBuffer.append("No Entry selected to show details.");
		}

		MessageDialog.openInformation(this.viewer.getControl().getShell(),
				"Entry details", detailsBuffer.toString());
	}

	/**
	 * <p>
	 * Updates the view after adding/removing of Triggers or Actions.
	 * </p>
	 */
	private void updateView() {

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
}