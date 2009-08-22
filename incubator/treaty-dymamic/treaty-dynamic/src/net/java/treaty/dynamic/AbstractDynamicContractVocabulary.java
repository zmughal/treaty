package net.java.treaty.dynamic;

import net.java.treaty.ContractVocabulary;
import net.java.treaty.ExistsCondition;
import net.java.treaty.PropertyCondition;
import net.java.treaty.RelationshipCondition;
import net.java.treaty.VerificationException;

/**
 * <p>
 * An abstract implementation of the {@link IDynamicContractVocabulary}. The
 * {@link AbstractDynamicContractVocabulary} adapts the check methods supported
 * by non dynamic {@link ContractVocabulary}s to the
 * {@link IDynamicContractVocabulary}. Thus, an
 * {@link IDynamicContractVocabulary} can also be used as a non-dynamic
 * {@link ContractVocabulary}.
 * </p>
 * 
 * @author Claas Wilke
 */
public abstract class AbstractDynamicContractVocabulary implements
		IDynamicContractVocabulary {

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.RelationshipCondition)
	 */
	@Override
	public void check(RelationshipCondition relationshipCondition)
			throws VerificationException {

		this.check(relationshipCondition, LifecycleEvent.MANUAL_VERIFICATION,
				null);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.PropertyCondition)
	 */
	@Override
	public void check(PropertyCondition propertyCondition)
			throws VerificationException {

		this.check(propertyCondition, LifecycleEvent.MANUAL_VERIFICATION, null);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.Verifier#check(net.java.treaty.ExistsCondition)
	 */
	@Override
	public void check(ExistsCondition existsCondition)
			throws VerificationException {

		this.check(existsCondition, LifecycleEvent.MANUAL_VERIFICATION, null);
	}
}