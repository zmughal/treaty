package net.java.treaty.vocabulary;

/**
 * <p>
 * An interface {@link Class}es must implement if they want to listen to the
 * {@link CompositeContractOntology}.
 * </p>
 * 
 * @author Claas Wilke
 */
public interface CompositeContractOntologyListener {

	/**
	 * <p>
	 * Updates the state of this {@link CompositeContractOntologyListener}, after
	 * the {@link CompositeContractOntology} has been changed.
	 * </p>
	 */
	public void update();
}