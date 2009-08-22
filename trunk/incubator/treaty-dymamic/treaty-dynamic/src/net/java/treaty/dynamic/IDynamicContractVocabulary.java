package net.java.treaty.dynamic;

import net.java.treaty.ContractVocabulary;
import net.java.treaty.ExistsCondition;
import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.VerificationException;

/**
 * <p>
 * This Interface extends the {@link ContractVocabulary} of Treaty with support
 * for dynamic vocabularies. IDynamicContractVocabulary can be used to check a
 * contract of this Vocabulary with a certain {@link IRuntimeContext} at a
 * certain point of time during component execution.
 * </p>
 * 
 * @author Claas Wilke
 */
public interface IDynamicContractVocabulary extends ContractVocabulary {

	/**
	 * <p>
	 * Checks a {@link ExistsCondition} provided by this
	 * {@link ContractVocabulary} at a certain point during execution with a
	 * certain {@link IRuntimeContext}.
	 * </p>
	 * 
	 * @see net.java.treaty.Verifier#check(net.java.treaty.ExistsCondition)
	 */
	public void check(ExistsCondition relationshipCondition,
			LifecycleEvent pointOfExecution, IRuntimeContext runtimeContext)
			throws VerificationException;

	/**
	 * <p>
	 * Checks a {@link PropertyCondition} provided by this
	 * {@link ContractVocabulary} at a certain point during execution with a
	 * certain {@link IRuntimeContext}.
	 * </p>
	 * 
	 * @see net.java.treaty.Verifier#check(net.java.treaty.PropertyCondition)
	 */
	public void check(PropertyCondition relationshipCondition,
			LifecycleEvent pointOfExecution, IRuntimeContext runtimeContext)
			throws VerificationException;

	/**
	 * <p>
	 * Checks a {@link RelationshipCondition} provided by this
	 * {@link ContractVocabulary} at a certain point during execution with a
	 * certain {@link IRuntimeContext}.
	 * </p>
	 * 
	 * @see net.java.treaty.Verifier#check(net.java.treaty.RelationshipCondition)
	 */
	public void check(RelationshipCondition relationshipCondition,
			LifecycleEvent pointOfExecution, IRuntimeContext runtimeContext)
			throws VerificationException;
}