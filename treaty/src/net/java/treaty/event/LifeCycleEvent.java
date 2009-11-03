package net.java.treaty.event;

import java.net.URI;

import net.java.treaty.Component;

/**
 * <p>
 * {@link LifeCycleEvent} represents different events, that can occur during a
 * component's life cycle. {@link LifeCycleEvent}s are use to trigger the
 * verification mechanism of Treaty.
 * </p>
 * 
 * @author Claas Wilke
 * 
 */
public class LifeCycleEvent {

	/** The {@link Component}, to which this {@link LifeCycleEvent} belongs to. */
	private Component myComponent;

	/** The type of this {@link LifeCycleEvent}. */
	private URI myType;

	/**
	 * <p>
	 * Creates a new {@link LifeCycleEvent}.
	 * </p>
	 * 
	 * @param component
	 *          The {@link Component}, to which this {@link LifeCycleEvent}
	 *          belongs to.
	 * @param type
	 *          The type of this {@link LifeCycleEvent}.
	 */
	public LifeCycleEvent(Component component, URI type) {

		this.myComponent = component;
		this.myType = type;
	}

	/**
	 * <p>
	 * Returns {@link Component}, to which this {@link LifeCycleEvent} belongs to.
	 * </p>
	 * 
	 * @return The {@link Component}, to which this {@link LifeCycleEvent} belongs
	 *         to.
	 */
	public Component getComponent() {

		return this.myComponent;
	}

	/**
	 * <p>
	 * Returns the type of this {@link LifeCycleEvent}.
	 * </p>
	 * 
	 * @return The type of this {@link LifeCycleEvent}.
	 */
	public URI getType() {

		return this.myType;
	}
}