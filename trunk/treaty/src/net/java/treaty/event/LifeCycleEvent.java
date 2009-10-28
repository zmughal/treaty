package net.java.treaty.event;

import java.net.URI;

/**
 * <p>
 * This interface represents different events, that can occur during a
 * component's life cycle. ILifeCycleEvents are use to trigger the verification
 * mechanism of Treaty.
 * </p>
 * 
 * @author Claas Wilke
 * 
 */
public interface LifeCycleEvent {

	/**
	 * <p>
	 * Returns the unique ID of the component, this {@link LifeCycleEvent}
	 * belongs to.
	 * </p>
	 * 
	 * @return The unique ID of the component, this {@link LifeCycleEvent}
	 *         belongs to.
	 */
	public String getComponentID();

	/**
	 * <p>
	 * Returns the type of this {@link LifeCycleEvent}.
	 * </p>
	 * 
	 * @return The type of this {@link LifeCycleEvent}.
	 */
	public URI getType();
}