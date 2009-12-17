/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

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