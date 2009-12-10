/*
 * Copyright (C) 2008-2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.example.clock;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.osgi.framework.Bundle;

/**
 * <p>
 * Simple clock view. The formats are supplied by other plug-ins.
 * </p>
 * 
 * @author Jens Dietrich, Claas Wilke
 */
public class ClockView extends ViewPart {

	/**
	 * <p>
	 * A {@link DateFormatProvider} provides a {@link DateFormatter} either
	 * defined by an XML file or a {@link DateFormatter} implementation.
	 * </p>
	 * 
	 * @author Jens Dietrich, Claas Wilke
	 */
	private class DateFormatProvider {

		/** The name of this {@link DateFormatProvider}. */
		public String name = null;

		/** The type of this {@link DateFormatProvider}. */
		public String type = null;

		/** The {@link IExtension} proving the {@link DateFormatter}. */
		public IExtension provider = null;

		/**
		 * The resource (an XML file's or a class' name) of this
		 * {@link DateFormatProvider}.
		 */
		public String resource = null;

		/** The {@link DateFormatter} of this {@link DateFormatProvider}. */
		private DateFormatter dateFormatter = null;

		/**
		 * <p>
		 * Returns the {@link DateFormatter} of this {@link DateFormatProvider}.
		 * Probably, the {@link DateFormatter} is initialized before.
		 * </p>
		 * 
		 * @return The {@link DateFormatter} of this {@link DateFormatProvider}.
		 */
		public DateFormatter getDateFormatter() {

			/* Probably initialize the dateFormatter. */
			if (this.dateFormatter == null && this.provider.isValid()) {

				Bundle bundle;
				bundle =
						org.eclipse.core.runtime.Platform.getBundle(this.provider
								.getContributor().getName());

				try {
					Class<?> clazz;
					clazz = bundle.loadClass(this.resource);

					if (DateFormatter.class.isAssignableFrom(clazz)) {
						this.dateFormatter = (DateFormatter) clazz.newInstance();
					}
					// no else (wrong type of class).
				}
				// end try.

				catch (Exception e) {
					e.printStackTrace();
				}
				// end catch.
			}
			// no else.

			return this.dateFormatter;
		}
	}

	/*
	 * The content provider class is rlist.add(inst);esponsible for providing
	 * objects to the view. It can wrap existing objects in adapters or simply
	 * return objects as-is. These objects may be sensitive to the current input
	 * of the view, or ignore it and always show the same content (like Task List,
	 * for example).
	 */

	class ViewContentProvider implements IStructuredContentProvider {

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {

		}

		public void dispose() {

		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java
		 * .lang.Object)
		 */
		public Object[] getElements(Object parent) {

			dateFormatProviders.clear();
			findJavaDateFormatters();
			findXMLDateFormatters();
			return dateFormatProviders
					.toArray(new DateFormatProvider[dateFormatProviders.size()]);
		}
	}

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {

		public String getColumnText(Object obj, int index) {

			if (obj != null & obj instanceof DateFormatProvider) {
				DateFormatProvider p = (DateFormatProvider) obj;
				if (index == 0) {
					return p.name;
				}
				if (index == 1) {
					return p.type;
				}
				if (index == 3) {
					return p.provider.getContributor().getName();
				}
				if (index == 2) {
					return p.resource;
				}
				return "?";
			}
			return "";
		}

		public Image getColumnImage(Object obj, int index) {

			return null;
		}
	}

	/** Id of the {@link IExtensionPoint} to register {@link DateFormatter}s. */
	private static final String DATEFORMATTER_EXTENSION_POINT_ID =
			"net.java.treaty.eclipse.example.clock.dateformatter";

	/** Constant for message to start the clock when clock is stopped. */
	private static final String STOP_MSG =
			"press \"Start Clock\" to display date and time";

	/** {@link Action} to start the clock. */
	private Action actionStartClock;

	/** {@link Action} to stop the clock. */
	private Action actionStopClock;

	/** {@link Thread} to executed the clock. */
	private Thread clockThread;

	/** Currently selected {@link DateFormatProvider}. */
	private DateFormatProvider dateFormatProvider = null;

	/** Available {@link DateFormatProvider}s. */
	private List<DateFormatProvider> dateFormatProviders =
			new ArrayList<DateFormatProvider>();

	/** The {@link Label} of this {@link ClockView} to display the date and time. */
	private Label dateLabel;

	/** Indicates whether or not the clock is on. */
	private boolean isClockOn;

	/** The {@link TableViewer} to show available {@link DateFormatter}s. */
	private TableViewer viewer;

	/**
	 * <p>
	 * Creates a new {@link ClockView}.
	 * </p>
	 */
	public ClockView() {

		super();
	}

	/**
	 * <p>
	 * This is a call-back that will allow us to create the viewer and initialize
	 * it.
	 * </p>
	 */
	public void createPartControl(Composite parent) {

		GridLayout gridLayout;
		gridLayout = new GridLayout();
		parent.setLayout(gridLayout);
		gridLayout.numColumns = 1;

		this.dateLabel = new Label(parent, SWT.NONE);
		this.dateLabel.setText(STOP_MSG);

		GridData gridData1;
		gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.grabExcessVerticalSpace = false;
		this.dateLabel.setLayoutData(gridData1);

		Label separator;
		separator =
				new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.LINE_SOLID);
		separator.setLayoutData(gridData1);

		Label label2 = new Label(parent, SWT.NONE);
		label2.setText("select format:");
		label2.setLayoutData(gridData1);

		this.viewer =
				new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL
						| SWT.CENTER | SWT.FULL_SELECTION);
		this.viewer.setContentProvider(new ViewContentProvider());
		this.viewer.setLabelProvider(new ViewLabelProvider());
		this.viewer.getTable().setHeaderVisible(true);

		TableColumn column1;
		column1 = new TableColumn(viewer.getTable(), SWT.LEFT);
		column1.setText("Date Renderer");
		column1.setWidth(180);

		TableColumn column2;
		column2 = new TableColumn(viewer.getTable(), SWT.LEFT);
		column2.setText("Type");
		column2.setWidth(250);

		TableColumn column3;
		column3 = new TableColumn(viewer.getTable(), SWT.LEFT);
		column3.setText("Resource");
		column3.setWidth(200);

		TableColumn column4;
		column4 = new TableColumn(viewer.getTable(), SWT.LEFT);
		column4.setText("Provider");
		column4.setWidth(200);

		this.viewer.setInput(getViewSite());

		GridData gridData2;
		gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.verticalAlignment = GridData.FILL;
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.grabExcessVerticalSpace = true;
		this.viewer.getTable().setLayoutData(gridData2);

		this.viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			/*
			 * (non-Javadoc)
			 * @see
			 * org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged
			 * (org.eclipse.jface.viewers.SelectionChangedEvent)
			 */
			public void selectionChanged(SelectionChangedEvent e) {

				int index;
				index = viewer.getTable().getSelectionIndex();

				if (index > -1) {
					dateFormatProvider = dateFormatProviders.get(index);
				}

				else {
					dateFormatProvider = null;
				}
				// end else.
			}
		});

		this.makeActions();
		this.hookContextMenu();
		this.contributeToActionBars();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	public void setFocus() {

		this.viewer.getControl().setFocus();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	public void dispose() {

		this.stopClock();

		super.dispose();
	}

	/**
	 * <p>
	 * Displays a given date.
	 * </p>
	 * 
	 * @param date
	 *          The date as a {@link String} that shall be displayed.
	 */
	private void displayDate(final String date) {

		if (!this.dateLabel.isDisposed()) {

			this.dateLabel.getDisplay().syncExec(new Runnable() {

				/*
				 * (non-Javadoc)
				 * @see java.lang.Runnable#run()
				 */
				public void run() {

					if (!dateLabel.isDisposed() && isClockOn) {
						dateLabel.setText(date);
					}
					// no else.
				}
			});
		}
		// no else.
	}

	/**
	 * <p>
	 * Instantiates the {@link DateFormatter} instances providing an
	 * implementation of the {@link DateFormatter} interface.
	 * </p>
	 */
	private void findJavaDateFormatters() {

		String classAttributeName;
		classAttributeName = "class";

		IExtensionRegistry extensionRegistry;
		extensionRegistry = Platform.getExtensionRegistry();

		IExtensionPoint extensionPoint;
		extensionPoint =
				extensionRegistry.getExtensionPoint(DATEFORMATTER_EXTENSION_POINT_ID);

		if (extensionPoint == null) {
			System.out.println("Cannot find extension point "
					+ DATEFORMATTER_EXTENSION_POINT_ID);
		}
		// no else.

		IExtension[] extensions;
		extensions = extensionPoint.getExtensions();

		if (extensions == null) {
			System.out.println("No extensions found for "
					+ DATEFORMATTER_EXTENSION_POINT_ID);
		}
		// no else.

		for (IExtension extension : extensions) {

			IConfigurationElement[] attributes;
			attributes = extension.getConfigurationElements();

			for (int index = 0; index < attributes.length; index++) {

				IConfigurationElement attribute;
				attribute = attributes[index];

				String clazz;
				clazz = attribute.getAttribute(classAttributeName);

				if (clazz != null) {

					try {

						/*
						 * Do not initialize the class, this causes bundle activation. This
						 * is done later on.
						 */
						DateFormatProvider formatProvider;
						formatProvider = new DateFormatProvider();

						// formatProvider.formatter = dateFormatter;
						formatProvider.name = clazz.substring(clazz.lastIndexOf(".") + 1);
						formatProvider.type = "DateFormatter implementation";
						formatProvider.provider = extension;
						formatProvider.resource = clazz;

						this.dateFormatProviders.add(formatProvider);
					}
					// end try.

					catch (Exception ex) {
						System.err.println("Error loading extension for extension point\n"
								+ DATEFORMATTER_EXTENSION_POINT_ID);
						ex.printStackTrace();
					}
					// end catch.
				}
				// no else (class attribute is null).
			}
			// end for (attributes).
		}
		// end for (extensions).

	}

	/**
	 * <p>
	 * Helper method that searches for date formatters providing an XML file for
	 * date formatting.
	 * </p>
	 */
	private void findXMLDateFormatters() {

		String formatDef;
		formatDef = "formatdef";

		IExtensionRegistry extensionRegistry;
		extensionRegistry = Platform.getExtensionRegistry();

		IExtensionPoint extensionPoint;
		extensionPoint =
				extensionRegistry.getExtensionPoint(DATEFORMATTER_EXTENSION_POINT_ID);

		if (extensionPoint == null) {
			System.out.println("Cannot find extension point "
					+ DATEFORMATTER_EXTENSION_POINT_ID);
		}
		// no else.

		IExtension[] extensions;
		extensions = extensionPoint.getExtensions();

		if (extensions == null) {
			System.out.println("No extensions found for "
					+ DATEFORMATTER_EXTENSION_POINT_ID);
		}
		// no else.

		for (IExtension extension : extensions) {

			IConfigurationElement[] attributes;
			attributes = extension.getConfigurationElements();

			for (int index = 0; index < attributes.length; index++) {

				IConfigurationElement attribute;
				attribute = attributes[index];

				String format;
				format = attribute.getAttribute(formatDef);

				if (format != null) {

					String owner;
					owner = extension.getContributor().getName();

					Bundle bundle;
					bundle = Platform.getBundle(owner);

					URL url;
					url = bundle.getResource(format);

					if (url != null) {
						DateFormatProvider provider;
						provider = new DateFormatProvider();

						provider.name = url.getPath();
						provider.type = "XML-defined date format";
						provider.provider = extension;
						provider.resource = url.getPath();

						DateFormatter formatter;
						formatter = loadXMLDateFormatter(url);

						if (formatter != null) {
							provider.dateFormatter = formatter;
							dateFormatProviders.add(provider);
						}
						// no else.
					}
					// no else (url is null).
				}
				// no else (format is null).
			}
			// end for (configuration elements).
		}
		// end for (extensions).
	}

	/**
	 * <p>
	 * Loads a {@link DateFormatter} described by a given XML document (via a
	 * {@link URL}).
	 * </p>
	 * 
	 * @param url
	 *          The {@link URL} of the {@link DateFormatter} that shall be
	 *          initialized.
	 * @return The initialized {@link DateFormatter}.
	 */
	private DateFormatter loadXMLDateFormatter(URL url) {

		DateFormatter result;

		try {
			InputStream in;
			in = url.openStream();

			Document document;
			document = new SAXBuilder().build(in);

			Element root;
			root = document.getRootElement();

			String format;
			format = root.getAttributeValue("formatstring");

			in.close();

			result = new ConfigurableDateFormatter(format);
		}
		// end try.

		catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		// end catch.

		return result;
	}

	/**
	 * <p>
	 * Starts the {@link Thread} that runs the clock.
	 * </p>
	 */
	private void startClock() {

		this.isClockOn = true;

		Runnable runnable;

		runnable = new Runnable() {

			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			public void run() {

				while (isClockOn) {

					String date;
					if (dateFormatProvider != null) {

						if (dateFormatProvider.getDateFormatter() != null) {
							date = dateFormatProvider.getDateFormatter().format(new Date());
						}

						else {
							date = new Date().toString();
						}
						// end else.
					}

					else {
						date = new Date().toString();
					}
					// end else.

					displayDate(date);
				}
			}
		};

		this.clockThread = new Thread(runnable);
		this.clockThread.start();
	}

	/**
	 * <p>
	 * Stops the clock running.
	 * </p>
	 */
	private void stopClock() {

		isClockOn = false;
		clockThread = null;

		/* Avoids error during dispose. */
		if (!dateLabel.isDisposed()) {
			dateLabel.setText(STOP_MSG);
		}
		// no else.
	}

	private void hookContextMenu() {

		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {

			public void menuAboutToShow(IMenuManager manager) {

				ClockView.this.fillContextMenu(manager);
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

		manager.add(actionStartClock);
		manager.add(new Separator());
		manager.add(actionStopClock);
	}

	private void fillContextMenu(IMenuManager manager) {

		manager.add(actionStartClock);
		manager.add(actionStopClock);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {

		manager.add(actionStartClock);
		manager.add(actionStopClock);
	}

	private void makeActions() {

		actionStartClock = new Action() {

			public void run() {

				startClock();
			}
		};
		actionStartClock.setText("Start Clock");
		actionStartClock.setToolTipText("Starts the clock in a view");
		// actStartClock.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		actionStopClock = new Action() {

			public void run() {

				stopClock();
			}
		};
		actionStopClock.setText("Stop Clock");
		actionStopClock.setToolTipText("Stops the clock");
		// actStopClock.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

	}
}