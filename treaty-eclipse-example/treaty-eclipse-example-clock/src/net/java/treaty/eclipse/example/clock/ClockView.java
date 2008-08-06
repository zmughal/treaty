/*
 * Copyright (C) 2008 Jens Dietrich
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.*;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.jface.action.*;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.osgi.framework.Bundle;


/**
 * Simple clock view. The formats are supplied by other plugins.
 * @author Jens Dietrich
 */

public class ClockView extends ViewPart {
	private TableViewer viewer;
	private Action actStartClock;
	private Action actStopClock;
	private Label label;
	private boolean onOff;
	private Thread clockThread;	
	private DateFormatter dateFormatter = null;
	private List<DateFormatProvider> providers = new ArrayList<DateFormatProvider>();	
	private static final String STOP_MSG = "press \"Start Clock\" to display date and time";

	
	class DateFormatProvider {
		IExtension provider = null;
		DateFormatter formatter = null;
		String name = null;
		URL format = null;
		String type = null;
		String resource = null;
	} 
	
	/*
	 * The content provider class is rlist.add(inst);esponsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			providers.clear();
			findDateFormatter();
			findFormatDefs();
			return providers.toArray(new DateFormatProvider[providers.size()]);
		}
	}
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			if (obj !=null & obj instanceof DateFormatProvider){
				DateFormatProvider p = (DateFormatProvider)obj;
				if (index==0) {
					return p.name;
				}
				if (index==1){
					return p.type;
				}
				if (index==3) {
					return p.provider.getContributor().getName();
				}
				if (index==2) {
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

	/**
	 * The constructor.
	 */
	public ClockView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		GridLayout gridLayout = new GridLayout();
		parent.setLayout(gridLayout);		
		gridLayout.numColumns = 1;
		
		label = new Label(parent, SWT.NONE);
		label.setText(STOP_MSG);
		GridData gd1 = new GridData();
		gd1.horizontalAlignment = GridData.FILL;
		gd1.grabExcessHorizontalSpace = true;
		gd1.grabExcessVerticalSpace = false;
		label.setLayoutData(gd1);
		
		
		Label separator= new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.LINE_SOLID);
		separator.setLayoutData(gd1);
		
		Label label2 = new Label(parent, SWT.NONE);
		label2.setText("select format:");
		label2.setLayoutData(gd1);
		
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CENTER);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.getTable().setHeaderVisible(true);
		
		TableColumn col1 = new TableColumn(viewer.getTable(), SWT.LEFT);
		col1.setText("date renderer");
		col1.setWidth(180);
		
		TableColumn col2 = new TableColumn(viewer.getTable(), SWT.LEFT);
		col2.setText("type");
		col2.setWidth(250);
		
		TableColumn col3 = new TableColumn(viewer.getTable(), SWT.LEFT);
		col3.setText("resource");
		col3.setWidth(200);
		
		TableColumn col4 = new TableColumn(viewer.getTable(), SWT.LEFT);
		col4.setText("provider");
		col4.setWidth(200);
		
		viewer.setInput(getViewSite());
		
		GridData gd2 = new GridData();
		gd2.horizontalAlignment = GridData.FILL;
		gd2.verticalAlignment = GridData.FILL;
		gd2.grabExcessHorizontalSpace = true;
		gd2.grabExcessVerticalSpace = true;
		viewer.getTable().setLayoutData(gd2);
		
		viewer.addSelectionChangedListener(new ISelectionChangedListener(){

			@Override
			public void selectionChanged(SelectionChangedEvent e) {
				int idx = viewer.getTable().getSelectionIndex();
				if (idx>-1) {
					DateFormatProvider p = providers.get(idx);
					dateFormatter=p.formatter;

				}
				else {
					dateFormatter = null;
				}
			}});
		
		makeActions();
		hookContextMenu();
		contributeToActionBars();
		
		
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
		manager.add(actStartClock);
		manager.add(new Separator());
		manager.add(actStopClock);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(actStartClock);
		manager.add(actStopClock);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(actStartClock);
		manager.add(actStopClock);
	}

	private void makeActions() {
		actStartClock = new Action() {
			public void run() {
				startClock();
			}
		};
		actStartClock.setText("Start Clock");
		actStartClock.setToolTipText("Starts the clock in a view");
		//actStartClock.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		actStopClock = new Action() {
			public void run() {
				stopClock();
			}
		};
		actStopClock.setText("Stop Clock");
		actStopClock.setToolTipText("Stops the clock");
		//actStopClock.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	private void stopClock() {		
		onOff = false;
		clockThread = null;	
		label.setText(STOP_MSG);
	}
	private void startClock() {
		onOff = true;
		Runnable run = new Runnable() {
			public void run() {
				while (onOff) {					
					String d = dateFormatter==null?new Date().toString():dateFormatter.format(new Date());
					displayTime(d);
				}
			}
		};
		
		clockThread = new Thread(run);
		clockThread.start();
	}
	
	 private void findDateFormatter(){
		 String extensionPointId = "net.java.treaty.eclipse.example.clock.dateformatter";	
		 String implementationClassAttributeName = "class";
			IExtensionRegistry registry = Platform.getExtensionRegistry();
			IExtensionPoint point = registry.getExtensionPoint(extensionPointId);
			if (point == null) 
				System.out.println("Cannot find extension point " + extensionPointId);
			IExtension[] extensions = point.getExtensions();
			if (extensions == null) 
				System.out.println("No extensions found for " + extensionPointId);
			for (IExtension x:extensions) {
				IConfigurationElement[] attributes = x.getConfigurationElements();
				String pluginId = x.getNamespace();
				for (int j=0;j<attributes.length;j++) {
					IConfigurationElement p = attributes[j];
					String clazz = p.getAttribute(implementationClassAttributeName);
					if (clazz!=null) {
						try {
							Class c = Platform.getBundle(pluginId).loadClass(clazz);
							DateFormatter inst = (DateFormatter)c.newInstance();
							DateFormatProvider provider = new DateFormatProvider();
							provider.formatter = inst;
							provider.name = inst.getName();
							provider.type = "date formatter implementation";
							provider.provider = x;
							provider.resource = c.getName();
							providers.add(provider);
						}
						catch (Exception ex) {
							System.err.println("Error loading extension for extension point\n"+extensionPointId);
							ex.printStackTrace();
						}
					}
				}
			}
			
	 }
	 private void findFormatDefs(){
		 String extensionPointId = "net.java.treaty.eclipse.example.clock.dateformatter";	
		 String formatDef = "formatdef";
		 List<URL> list = new ArrayList<URL>();
			IExtensionRegistry registry = Platform.getExtensionRegistry();
			IExtensionPoint point = registry.getExtensionPoint(extensionPointId);
			if (point == null) 
				System.out.println("Cannot find extension point " + extensionPointId);
			IExtension[] extensions = point.getExtensions();
			if (extensions == null) 
				System.out.println("No extensions found for " + extensionPointId);
			for (IExtension x:extensions) {
				IConfigurationElement[] attributes = x.getConfigurationElements();
				for (int j=0;j<attributes.length;j++) {
					IConfigurationElement p = attributes[j];
					String format = p.getAttribute(formatDef);
					if (format!=null){
						String owner = x.getContributor().getName();
						Bundle bundle = Platform.getBundle(owner);
						URL url = bundle.getResource(format);
						if (url!=null) {
							DateFormatProvider provider = new DateFormatProvider();
							provider.format = url;
							provider.name = url.getPath();
							provider.type = "XML-defined date format";
							provider.provider = x;
							provider.resource = url.getPath();
							DateFormatter formatter = loadFormatter(url);
							if (formatter!=null) {
								provider.formatter=formatter;
								providers.add(provider);
							}
						}
					}
				}
			}
	 }

	private DateFormatter loadFormatter(URL url) {
		try {
			InputStream in = url.openStream();
			Document doc = new SAXBuilder().build(in);		
			Element root = doc.getRootElement();
			String format = root.getAttributeValue("formatstring");
			in.close();
	    	return new ConfigurableDateFormatter(format);
		}
		catch (Exception x) {
			x.printStackTrace();
			return null;
		}
	}

	@Override
	public void dispose() {
		this.stopClock();
		super.dispose();
	}
	
	private void displayTime(final String d) {
	   if (label.isDisposed()) return;
	   label.getDisplay().syncExec (new Runnable () {
		      public void run () {
		         if (!label.isDisposed() && onOff) {
		        	 label.setText(d);
		         }
		      }
		});
	}
	 
}