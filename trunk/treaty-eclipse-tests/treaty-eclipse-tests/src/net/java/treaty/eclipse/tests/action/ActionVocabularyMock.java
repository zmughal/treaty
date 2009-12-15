package net.java.treaty.eclipse.tests.action;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.java.treaty.Contract;
import net.java.treaty.TreatyException;
import net.java.treaty.VerificationReport;
import net.java.treaty.VerificationResult;
import net.java.treaty.action.ActionVocabulary;
import net.java.treaty.eclipse.Logger;

public class ActionVocabularyMock implements ActionVocabulary {

	/** The name space's name of the {@link LoggerActionVocabulary}. */
	public static final String NAME_SPACE_NAME =
			"http://www.treaty.org/action/test";

	/** The name of an action type. */
	public static final String ACTION_TYPE_AFTER =
			NAME_SPACE_NAME + "#ActionAfter";

	/** The name of an action type. */
	public static final String ACTION_TYPE_BEFORE =
			NAME_SPACE_NAME + "#ActionBefore";

	/** The name of an action type. */
	public static final String ACTION_TYPE_DEFAULT_ON_FAILURE =
			NAME_SPACE_NAME + "#ActionOnFailure";

	/** The name of an action type. */
	public static final String ACTION_TYPE_DEFAULT_ON_SUCCESS =
			NAME_SPACE_NAME + "#ActionOnSuccess";

	/** The name of an action type. */
	public static final String ACTION_TYPE_TEST1 =
			NAME_SPACE_NAME + "#TestAction1";

	/**
	 * The action types of this {@link ActionVocabulary} as a {@link Map} of
	 * {@link URI}s identified by their {@link String} representation.
	 */
	private Map<String, URI> actionTypes;

	/**
	 * <p>
	 * Creates a new {@link ActionVocabularyMock}.
	 * </p>
	 */
	public ActionVocabularyMock() {

		this.initialize();
	}

	/**
	 * <p>
	 * Initializes the {@link ActionVocabularyMock}.
	 * </p>
	 */
	private void initialize() {

		/* Probably initialize the action Types */
		if (this.actionTypes == null) {

			this.actionTypes = new HashMap<String, URI>();

			try {
				this.actionTypes.put(ACTION_TYPE_AFTER, new URI(ACTION_TYPE_AFTER));
				this.actionTypes.put(ACTION_TYPE_BEFORE, new URI(ACTION_TYPE_BEFORE));
				this.actionTypes.put(ACTION_TYPE_DEFAULT_ON_FAILURE, new URI(
						ACTION_TYPE_DEFAULT_ON_FAILURE));
				this.actionTypes.put(ACTION_TYPE_DEFAULT_ON_SUCCESS, new URI(
						ACTION_TYPE_DEFAULT_ON_SUCCESS));
				this.actionTypes.put(ACTION_TYPE_TEST1, new URI(ACTION_TYPE_TEST1));
			}

			catch (URISyntaxException e) {
				Logger.warn("Error during initialization of ActionVocabularyMock. "
						+ "Probably some action types are not available.", e);
			}
			// end catch.
		}
		// no else (already initialized).
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#after(java.net.URI,
	 * java.net.URI, java.util.Set)
	 */
	public void after(URI triggerType, URI actionType,
			Set<VerificationReport> verificationReports) {

		if (this.actionTypes.get(ACTION_TYPE_AFTER).equals(actionType)) {
			ActionTest.afterActionWasCalled = true;
		}
		// no else.
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#before(java.net.URI,
	 * java.net.URI, java.util.Set)
	 */
	public void before(URI triggerType, URI actionType,
			Set<Contract> contractsToVerify) {

		if (this.actionTypes.get(ACTION_TYPE_BEFORE).equals(actionType)) {
			ActionTest.beforeActionWasCalled = true;
		}
		// no else.
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#getActionTypes()
	 */
	public Set<URI> getActionTypes() {

		return new HashSet<URI>(actionTypes.values());
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#getDescription(java.net.URI)
	 */
	public String getDescription(URI actionType) throws TreatyException {

		return "";
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#isAfterAction(java.net.URI)
	 */
	public boolean isAfterAction(URI actionType) {

		return this.actionTypes.get(ACTION_TYPE_AFTER).equals(actionType);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#isBeforeAction(java.net.URI)
	 */
	public boolean isBeforeAction(URI actionType) {

		return this.actionTypes.get(ACTION_TYPE_BEFORE).equals(actionType);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.action.ActionVocabulary#isDefaultOnFailure(java.net.URI)
	 */
	public boolean isDefaultOnFailure(URI actionType) {

		return this.actionTypes.get(ACTION_TYPE_DEFAULT_ON_FAILURE).equals(
				actionType);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.action.ActionVocabulary#isDefaultOnSuccess(java.net.URI)
	 */
	public boolean isDefaultOnSuccess(URI actionType) {

		return this.actionTypes.get(ACTION_TYPE_DEFAULT_ON_SUCCESS).equals(
				actionType);
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.action.ActionVocabulary#perform(java.net.URI,
	 * java.net.URI, net.java.treaty.VerificationReport)
	 */
	public void perform(URI triggerType, URI actionType,
			VerificationReport verificationReport) {

		if (VerificationResult.SUCCESS == verificationReport
				.getVerificationResult()) {

			if (this.actionTypes.get(ACTION_TYPE_DEFAULT_ON_SUCCESS).equals(
					actionType)) {
				ActionTest.onSuccessActionWasCalled = true;
			}

			else if (this.actionTypes.get(ACTION_TYPE_TEST1).equals(actionType)) {
				ActionTest.testAction1WasCalledOnSuccess = true;
			}
			// no else.
		}

		else {
			if (this.actionTypes.get(ACTION_TYPE_DEFAULT_ON_FAILURE).equals(
					actionType)) {
				ActionTest.onFailureActionWasCalled = true;
			}

			else if (this.actionTypes.get(ACTION_TYPE_TEST1).equals(actionType)) {
				ActionTest.testAction1WasCalledOnFailure = true;
			}
			// no else.
		}
	}
}