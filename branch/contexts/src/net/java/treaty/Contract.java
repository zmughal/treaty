/*
 * Copyright (C) 2008 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty;

/**
 * Contract interface.  
 * @author Jens Dietrich
 */

public interface Contract extends Constraint {
	
	public abstract Connector getConsumer();

	public abstract Connector getSupplier();

	/**
	 * Instantiate this contract with a supplier.
	 * A new contract will be returned that has no supplier variables. 
	 * @param connector the supplier
	 * @param loader the loader
	 * @param contextManager the context manager
	 * @return a new contract
	 * @throws InvalidContractException
	 */
	public abstract Contract bindSupplier(Connector connector,ResourceManager loader,ContextManager contextManager) throws TreatyException;

	/**
	 * Instantiate this contract with a consumer.
	 * A new contract will be returned that has no consumer variables. 
	 * @param connector the consumer
	 * @param loader the loader
	 * @param contextManager the context manager
	 * @return a new contract
	 * @throws InvalidContractException
	 */
	public abstract Contract bindConsumer(Connector connector,ResourceManager loader,ContextManager contextManager) throws TreatyException;	

}