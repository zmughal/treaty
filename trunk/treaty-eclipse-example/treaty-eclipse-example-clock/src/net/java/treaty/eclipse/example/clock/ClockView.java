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


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
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


/**
 * Simple clock view. The formats are supplied by other plugins.
 * @author Jens Dietrich
 */

public class ClockView extends ViewPart {
	private TableViewer viewer;
	private Action action1;
	private Action action2;
	private Label label;
	private boolean onOff;
	private Thread clockThread;
	
	private DateFormatter dateFormatter = null;
	private DateFormatter[] availableDateFormatter = null;
	private static final String STOP_MSG = "press \"Start Clock\" to display date and time";

	/*
	 * The content provider class is responsible for
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
			List<DateFormatter> dateFormatter = findDateFormatter();
			availableDateFormatter = dateFormatter.toArray(new DateFormatter[dateFormatter.size()]);
			return availableDateFormatter;
		}
	}
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return getText(((DateFormatter)obj).getName());
		}
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}
		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().
					getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
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
					dateFormatter = availableDateFormatter[idx];
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
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				startClock();
			}
		};
		action1.setText("Start Clock");
		action1.setToolTipText("Starts the clock in a view");
		//action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		action2 = new Action() {
			public void run() {
				stopClock();
			}
		};
		action2.setText("Stop Clock");
		action2.setToolTipText("Stops the clock");
		//action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

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
	
	 private List<DateFormatter> findDateFormatter(){
		 String extensionPointId = "net.java.treaty.eclipse.example.clock.dateformatter";	
		 String implementationClassAttributeName = "class";
		 List<DateFormatter> list = new ArrayList<DateFormatter>();
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
					try {
						Class c = Platform.getBundle(pluginId).loadClass(clazz);
						DateFormatter inst = (DateFormatter)c.newInstance();
						list.add(inst);
					}
					catch (Exception ex) {
						System.err.println("Error loading extension for extension point\n"+extensionPointId);
						ex.printStackTrace();
					}
				}
			}
			
			return list;
	 }

	@Override
	public void dispose() {
		this.stopClock();
		super.dispose();
	}
	
	private void displayTime(final String d) {
	   label.getDisplay().syncExec (new Runnable () {
		      public void run () {
		         if (!label.isDisposed() && onOff) {
		        	 label.setText(d);
		         }
		      }
		});
	}
	 
}