/*
Copyright (C) 2009 by Claas Wilke (info@claaswilke.de)

This file is part of the Treaty OCL Vocabulary which adapts Dresden OCL2 for
Eclipse to Treaty.

Dresden OCL2 for Eclipse is free software: you can redistribute it and/or modify 
it under the terms of the GNU Lesser General Public License as published by the 
Free Software Foundation, either version 3 of the License, or (at your option)
any later version.

Dresden OCL2 for Eclipse is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License 
for more details.

You should have received a copy of the GNU Lesser General Public License along 
with Dresden OCL2 for Eclipse. If not, see <http://www.gnu.org/licenses/>.
 */
package net.java.treaty.eclipse.vocabulary.ocl.context;

import net.java.treaty.dynamic.IRuntimeContext;
import net.java.treaty.dynamic.LifecycleEvent;
import net.java.treaty.dynamic.OperationInvocationContext;
import tudresden.ocl20.pivot.modelbus.modelinstance.types.IModelInstanceElement;
import tudresden.ocl20.pivot.pivotmodel.Constraint;
import tudresden.ocl20.pivot.pivotmodel.Operation;
import tudresden.ocl20.pivot.pivotmodel.Parameter;

/**
 * <p>
 * Represents the context of {@link Constraint}s that shall be interpreted.
 * Contains an {@link IModelObject} on which the {@link Constraint}s shall be
 * interpreted and eventually an {@link Operation}, its {@link Parameter} values
 * (arguments) and eventually the <code>result</code> of its invocation.
 * 
 * @author Claas Wilke
 * 
 */
public class OclInterpretationContext implements IRuntimeContext {

	/**
	 * The {@link LifecycleEvent} this {@link OclInterpretationContext} belongs
	 * to.
	 */
	private LifecycleEvent myPointOfExecution;

	/**
	 * The {@link IModelInstanceElement} of this {@link OclInterpretationContext}.
	 */
	private IModelInstanceElement myModelInstanceElement;

	/**
	 * The {@link Operation} of this {@link OclInterpretationContext} or
	 * <code>null</code> if no {@link Operation} belongs to this
	 * {@link OclInterpretationContext}.
	 */
	private Operation myOperation;

	/**
	 * The arguments (as {@link IModelInstanceElement}s) of this
	 * {@link OclInterpretationContext} or an empty array.
	 */
	private IModelInstanceElement[] myArguments;

	/**
	 * The result of the invocation of the {@link Operation} of this
	 * {@link OclInterpretationContext} or <code>null</code>.
	 */
	private IModelInstanceElement myResult;

	/**
	 * <p>
	 * Creates a new {@link OclInterpretationContext}.
	 * </p>
	 * 
	 * @param pointOfExecution
	 *          The {@link LifecycleEvent} this {@link OclInterpretationContext}
	 *          belongs to.
	 * @param modelInstanceElement
	 *          The {@link IModelInstanceElement} of this
	 *          {@link OclInterpretationContext}.
	 * @param operation
	 *          The {@link Operation} of this {@link OclInterpretationContext} or
	 *          <code>null</code> if no {@link Operation} belongs to this
	 *          {@link OclInterpretationContext}.
	 * @param arguments
	 *          The arguments (as {@link IModelObject}s) of this
	 *          {@link OclInterpretationContext} or an empty array.
	 * @param result
	 *          The result of the invocation of the {@link Operation} of this
	 *          {@link OclInterpretationContext} or <code>null</code>.
	 */
	public OclInterpretationContext(LifecycleEvent pointOfExecution,
			IModelInstanceElement modelInstanceElement, Operation operation,
			IModelInstanceElement[] arguments, IModelInstanceElement result) {

		this.myPointOfExecution = pointOfExecution;
		this.myModelInstanceElement = modelInstanceElement;
		this.myOperation = operation;
		this.myArguments = arguments;
		this.myResult = result;
	}

	/**
	 * <p>
	 * Return the arguments (as {@link IModelInstanceElement}s) of this
	 * {@link OclInterpretationContext} or an empty array.
	 * </p>
	 * 
	 * @return The arguments (as {@link IModelInstanceElement}s) of this
	 *         {@link OclInterpretationContext} or an empty array.
	 */
	public IModelInstanceElement[] getArguments() {

		return this.myArguments;
	}

	/**
	 * <p>
	 * Returns the {@link IModelInstanceElement} of this
	 * {@link OclInterpretationContext}.
	 * </p>
	 * 
	 * @return The {@link IModelInstanceElement} of this
	 *         {@link OclInterpretationContext}.
	 */
	public IModelInstanceElement getModelInstanceElement() {

		return this.myModelInstanceElement;
	}

	/**
	 * <p>
	 * Returns the {@link Operation} of this {@link OclInterpretationContext} or
	 * <code>null</code> if no {@link Operation} belongs to this
	 * {@link OclInterpretationContext}.
	 * </p>
	 * 
	 * @return The {@link Operation} of this {@link OclInterpretationContext} or
	 *         <code>null</code> if no {@link Operation} belongs to this
	 *         {@link OclInterpretationContext}.
	 */
	public Operation getOperation() {

		return this.myOperation;
	}

	/**
	 * <p>
	 * Returns the {@link LifecycleEvent} this {@link OclInterpretationContext}
	 * belongs to.
	 * </p>
	 * 
	 * @return The {@link LifecycleEvent} this {@link OclInterpretationContext}
	 *         belongs to.
	 */
	public LifecycleEvent getPointOfExecution() {

		return this.myPointOfExecution;
	}

	/**
	 * <p>
	 * Returns the result of the invocation of the {@link Operation} of this
	 * {@link OclInterpretationContext} or <code>null</code>.
	 * </p>
	 * 
	 * @return The result of the invocation of the {@link Operation} of this
	 *         {@link OclInterpretationContext} or <code>null</code>.
	 */
	public IModelInstanceElement getResult() {

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
		result += "modelObject = " + this.myModelInstanceElement + ", ";
		result += "operation = " + this.myOperation + ", ";
		result += "arguments = " + this.myArguments + ", ";
		result += "result = " + this.myResult + ", ";
		result += "pointOfExecution = " + this.myPointOfExecution;
		result += "]";

		return result;
	}
}