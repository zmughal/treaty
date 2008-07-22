package net.java.treaty;

import net.java.treaty.verification.ConditionVerifier;
import net.java.treaty.verification.ResourceLoader;
import net.java.treaty.verification.VerificationReport;

public interface Contract {

	public abstract void accept(ContractVisitor visitor);

	/**
	 * Instantiate this contract with a supplier.
	 * A new contract will be returned that has no supplier variables. 
	 * @param connector the supplier
	 * @param loader the loader
	 * @return a new contract
	 * @throws InvalidContractException
	 */
	public abstract Contract bindSupplier(Connector connector,ResourceLoader loader) throws InvalidContractException;

	/**
	 * Instantiate this contract with a consumer.
	 * A new contract will be returned that has no consumer variables. 
	 * @param connector the consumer
	 * @param loader the loader
	 * @return a new contract
	 * @throws InvalidContractException
	 */
	public abstract Contract bindConsumer(Connector connector,
			ResourceLoader loader) throws InvalidContractException;

	/**
	 * Check this contract using a validator. Add the results to the report.
	 * @param report
	 * @param validator
	 * @return
	 */
	public abstract boolean check(VerificationReport report,
			ConditionVerifier validator);

	public abstract String getLocation();

	public abstract Connector getConsumer();

	public abstract Connector getSupplier();

}