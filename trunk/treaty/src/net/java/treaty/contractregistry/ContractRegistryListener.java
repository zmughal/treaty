/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.contractregistry;

/**
 * <p>
 * A listener interface that can be used to listen to the
 * {@link ContractRegistry}.
 * </p>
 * 
 * @author Claas Wilke
 */
public interface ContractRegistryListener {

	/**
	 * <p>
	 * This method is used to informs the {@link ContractRegistryListener} that
	 * the {@link ContractRegistry} has been changed. All update functionality
	 * should be implemented or triggered inside this method.
	 * </p>
	 */
	public void update();
}