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

import net.java.treaty.dynamic.LifecycleEvent;
import net.java.treaty.dynamic.OperationInvocationContext;
import tudresden.ocl20.pivot.modelbus.modelinstance.IModelInstance;

/**
 * <p>
 * The interface for factories that adapt a given
 * {@link OperationInvocationContext} to to {@link OclInterpretationContext}.
 * This services must be implemented for every {@link IModelInstance} type
 * independently.
 * </p>
 * 
 * @author Claas Wilke
 */
public interface IContextAdapterService {

	/**
	 * <p>
	 * Adapts a given {@link OperationInvocationContext} to an
	 * {@link OclInterpretationContext}.
	 * </p>
	 * 
	 * @param runtimeContext
	 *          The {@link OperationInvocationContext} that shall be adapted.
	 * @param pointOfExecution
	 *          The {@link LifecycleEvent} of this
	 *          {@link OclInterpretationContext}.
	 * @return The adapted {@link OclInterpretationContext}.
	 */
	public OclInterpretationContext adaptContext(
			OperationInvocationContext runtimeContext,
			LifecycleEvent pointOfExecution);
}