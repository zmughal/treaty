package net.java.treaty.dynamic;

import java.util.concurrent.locks.Condition;

/**
 * <p>
 * Declares the different {@link PointOfEcexution} time, at which
 * {@link Condition}s of a {@link IDynamicContractVocabulary} can be verified.
 * </p>
 * 
 * <ul>
 * <li><code>MANUAL_VERIFICATION</code>: The user wants to verify the
 * {@link Condition} (caused by an external action). <strong>This is the default
 * value.</strong></li>
 * <li><code>COMPONENT_LOAD_TIME</code>: A {@link Condition} shall be verified
 * during a components load time.</li>
 * <li><code>COMPONENT_UNLOAD_TIME</code>: A {@link Condition} shall be verified
 * during a components unload time.</li>
 * <li><code>INTERFACE_INVOCATION</code>: A {@link Condition} shall be verified
 * before an interface's method invocation.</li>
 * <li><code>INTERFACE_INVOCATION_RETURN</code>: A {@link Condition} shall be
 * verified after an interface's method invocation.</li>
 * </ul>
 * 
 * @author Claas Wilke
 */
public enum LifecycleEvent {
	MANUAL_VERIFICATION, COMPONENT_LOAD_TIME, COMPONENT_UNLOAD_TIME,
	INTERFACE_INVOCATION, INTERFACE_INVOCATION_RETURN
};