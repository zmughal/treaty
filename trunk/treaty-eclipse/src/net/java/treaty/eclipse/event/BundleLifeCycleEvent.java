/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.event;

import java.net.URI;

import net.java.treaty.event.LifeCycleEvent;

import org.osgi.framework.BundleEvent;

/**
 * <p>
 * An {@link LifeCycleEvent} that is sent when a component has been started
 * (wraps a {@link BundleEvent} .
 * </p>
 * 
 * @author Claas Wilke
 */
public class BundleLifeCycleEvent implements LifeCycleEvent {

	/** The wrapped {@link BundleEvent} of this {@link BundleLifeCycleEvent}. */
	private BundleEvent myWrappedBundleEvent;

	/**
	 * <p>
	 * Creates a new {@link BundleLifeCycleEvent}.
	 * </p>
	 * 
	 * @param bundleEvent
	 *          The {@link BundleEvent} that shall be wrapped by this
	 *          {@link BundleLifeCycleEvent}.
	 * 
	 * @throws IllegalArgumentException
	 *           Thrown, if the given {@link BundleEvent} is <code>null</code>.
	 */
	public BundleLifeCycleEvent(BundleEvent bundleEvent)
			throws IllegalArgumentException {

		if (bundleEvent == null) {
			throw new IllegalArgumentException("Given BundleEvent must not be null.");
		}
		// no else.

		this.myWrappedBundleEvent = bundleEvent;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.event.ILifeCycleEvent#getComponentID()
	 */
	public String getComponentID() {

		return this.myWrappedBundleEvent.getBundle().getSymbolicName();
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.event.ILifeCycleEvent#getType()
	 */
	public URI getType() {

		// FIXME Claas: Return a real URI here as soon as possible.
		return null;
	}
}