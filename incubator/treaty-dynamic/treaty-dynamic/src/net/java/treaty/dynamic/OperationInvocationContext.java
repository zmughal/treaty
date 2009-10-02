package net.java.treaty.dynamic;

/**
 * <p>
 * The {@link OperationInvocationContext} represents an {@link IRuntimeContext}
 * containing the information of a methods invocation. It contains the method's
 * signature, the source the method shall be invoked on, the method's arguments
 * and eventually the invocation result.
 * </p>
 * 
 * @author Claas Wilke
 */
public class OperationInvocationContext implements IRuntimeContext {

	/**
	 * The arguments of the method as an array of {@link Object}s.
	 */
	private Object[] myArguments;

	/**
	 * The types of the argument of the method as an array of {@link String}s
	 * containing their fully qualified name.
	 */
	private String[] myArgumentTypes;

	/**
	 * The signature (the qualified name as a {@link String}) of the method that
	 * shall be or has been invoked.
	 */
	private String myOperationSignature;

	/**
	 * The {@link Object} on which the method of this context shall be invoked.
	 */
	private Object mySource;

	/**
	 * The result (as an {@link Object}) of the methods invocation. This values is
	 * only set if this {@link OperationInvocationContext} is used as a
	 * {@link IRuntimeContext} of a
	 * {@link LifecycleEvent#INTERFACE_INVOCATION_RETURN}. Else the
	 * <code>result</code> is <code>null</code>.
	 */
	private Object myResult;

	/**
	 * <p>
	 * Creates a new {@link OperationInvocationContext} with an empty
	 * <code>result</code>.
	 * </p>
	 * 
	 * @param methodSignature
	 *          The signature (the qualified name as a {@link String}) of the
	 *          method that shall be or has been invoked.
	 * @param source
	 *          The {@link Object} on which the method of this context shall be
	 *          invoked.
	 * @param arguments
	 *          The arguments of the method as an array of {@link Object}s.
	 * @param argumentTypes
	 *          The types of the argument of the method as an array of
	 *          {@link String}s containing their fully qualified name.
	 */
	public OperationInvocationContext(String methodSignature, Object source,
			Object[] arguments, String[] argumentTypes) {

		new OperationInvocationContext(methodSignature, source, arguments,
				argumentTypes, null);
	}

	/**
	 * <p>
	 * Creates a new {@link OperationInvocationContext}.
	 * </p>
	 * 
	 * @param methodSignature
	 *          The signature (the qualified name as a {@link String}) of the
	 *          method that shall be or has been invoked.
	 * @param source
	 *          The {@link Object} on which the method of this context shall be
	 *          invoked.
	 * @param arguments
	 *          The arguments of the method as an array of {@link Object}s.
	 * @param argumentTypes
	 *          The types of the argument of the method as an array of
	 *          {@link String}s containing their fully qualified name.
	 * @param result
	 *          The result (as an {@link Object}) of the methods invocation. This
	 *          values is only set if this {@link OperationInvocationContext} is
	 *          used as a {@link IRuntimeContext} of a
	 *          {@link LifecycleEvent#INTERFACE_INVOCATION_RETURN}. Else the
	 *          <code>result</code> is <code>null</code>.
	 */
	public OperationInvocationContext(String methodSignature, Object source,
			Object[] arguments, String[] argumentTypes, Object result) {

		super();

		this.myOperationSignature = methodSignature;
		this.mySource = source;
		this.myArguments = arguments;
		this.myArgumentTypes = argumentTypes;
		this.myResult = result;
	}

	/**
	 * <p>
	 * Returns the arguments of the method as an array of {@link Object}s.
	 * </p>
	 * 
	 * @return The arguments of the method as an array of {@link Object}s.
	 */
	public Object[] getArguments() {

		/*
		 * Eventually initialize the arguments to avoid an NullPointerException.
		 */
		if (this.myArguments == null) {
			this.myArguments = new Object[0];
		}
		// no else.

		return myArguments;
	}

	/**
	 * <p>
	 * Returns the types of the argument of the method as an array of
	 * {@link String}s containing their fully qualified name.
	 * </p>
	 * 
	 * @return The types of the argument of the method as an array of
	 *         {@link String}s containing their fully qualified name.
	 */
	public String[] getArgumentsTypes() {

		/*
		 * Eventually initialize the arguments to avoid an NullPointerException.
		 */
		if (this.myArgumentTypes == null) {
			this.myArgumentTypes = new String[0];
		}
		// no else.

		return this.myArgumentTypes;
	}

	/**
	 * <p>
	 * Returns the signature (the qualified name as a {@link String}) of the
	 * method that shall be or has been invoked.
	 * </p>
	 * 
	 * @return The signature (the qualified name as a {@link String}) of the
	 *         method that shall be or has been invoked.
	 */
	public String getMethodSignature() {

		return this.myOperationSignature;
	}

	/**
	 * <p>
	 * Returns the {@link Object} on which the method of this context shall be
	 * invoked.
	 * </p>
	 * 
	 * @return The {@link Object} on which the method of this context shall be
	 *         invoked.
	 */
	public Object getSource() {

		return this.mySource;
	}

	/**
	 * <p>
	 * Returns the result (as an {@link Object}) of the methods invocation. This
	 * values is only set if this {@link OperationInvocationContext} is used as a
	 * {@link IRuntimeContext} of a
	 * {@link LifecycleEvent#INTERFACE_INVOCATION_RETURN}. Else the
	 * <code>result</code> is <code>null</code>.
	 * </p>
	 * 
	 * @return The result (as an {@link Object}) of the methods invocation. This
	 *         values is only set if this {@link OperationInvocationContext} is
	 *         used as a {@link IRuntimeContext} of a
	 *         {@link LifecycleEvent#INTERFACE_INVOCATION_RETURN}. Else the
	 *         <code>result</code> is <code>null</code>.
	 */
	public Object getResult() {

		return this.myResult;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		String result;

		result = OperationInvocationContext.class.getSimpleName();
		result += "[";
		result += "signature = " + this.myOperationSignature + ", ";
		result += "argumentTypes = " + this.myArgumentTypes + ", ";
		result += "source = " + this.mySource + ", ";
		result += "arguments = " + this.myArguments + ", ";
		result += "result = " + this.myResult;
		result += "]";

		return result;
	}
}