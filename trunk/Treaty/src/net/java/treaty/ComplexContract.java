package net.java.treaty;

import net.java.treaty.verification.ConditionVerifier;
import net.java.treaty.verification.ResourceLoader;
import net.java.treaty.verification.VerificationReport;

public class ComplexContract implements Contract{
	
	private Contract[] parts = null;
	

	public ComplexContract(Contract[] parts) {
		super();
		this.parts = parts;
	}

	public void accept(ContractVisitor visitor) {
		boolean f = visitor.visit(this);
		if (f) {
			for (Contract c:partsContract) {
				if (visitor.visitContract(c)) {
					c.accept(visitor);
					visitor.endVisit(c);
				}
			}
		}
		visitor.endVisit(this);
		
	}

	/**
	 * Instantiate this contract with a supplier.
	 * A new contract will be returned that has no supplier variables. 
	 * @param connector the supplier
	 * @param loader the loader
	 * @return a new contract
	 * @throws InvalidContractException
	 */
	public ComplexContract bindSupplier(Connector connector,ResourceLoader loader) throws InvalidContractException {
		Contract[] boundParts = new Contract[parts.length];
		for (int i=0;i<parts.length;i++) {
			boundParts[i] = parts[i].bindSupplier(connector,loader);
		}
		return new ComplexContract(boundParts);
	}

	/**
	 * Instantiate this contract with a consumer.
	 * A new contract will be returned that has no consumer variables. 
	 * @param connector the consumer
	 * @param loader the loader
	 * @return a new contract
	 * @throws InvalidContractException
	 */
	public ComplexContract bindConsumer(Connector connector,ResourceLoader loader) throws InvalidContractException{
		Contract[] boundParts = new Contract[parts.length];
		for (int i=0;i<parts.length;i++) {
			boundParts[i] = parts[i].bindConsumer(connector,loader);
		}
		return new ComplexContract(boundParts);
	}

	/**
	 * Check this contract using a verifier. Add the results to the report.
	 * @param report
	 * @param verifier
	 * @return
	 */
	public boolean check(VerificationReport report,ConditionVerifier verifier) {
		boolean result = true;
		for (Contract part:parts) {
			result = result&&part.check(report, verifier);
		}
		return result;
	}

	public String getLocation() {
		StringBuffer b = new StringBuffer(); 
		for (int i=0;i<parts.length;i++) {
			if (i>0) b.append("");
		}
	}

	public abstract Connector getConsumer();

	public abstract Connector getSupplier();

	public Contract[] getParts() {
		return parts;
	}

}