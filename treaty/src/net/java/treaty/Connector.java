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
 * <p>
 * This interface abstracts from {@link ConnectorType#CONSUMER}s (extension
 * points) and {@link ConnectorType#SUPPLIER}s (extensions).
 * </p>
 * 
 * @author Jens Dietrich
 */
public interface Connector extends Annotatable {

	/**
	 * <p>
	 * Returns the {@link Connector}s ID. For instance, this could be the ID of
	 * the eclipse extension point or extension wrapped by this {@link Connector}.
	 * </p>
	 * 
	 * @return The {@link Connector}s ID.
	 */
	String getId();

	/**
	 * <p>
	 * Returns the {@link Component} owning this {@link Connector}.
	 * </p>
	 * 
	 * @return The {@link Component} owning this {@link Connector}.
	 */
	Component getOwner();

	/**
	 * <p>
	 * Returns the {@link ConnectorType} of this {@link Connector}.
	 * </p>
	 * 
	 * @return The {@link ConnectorType} of this {@link Connector}.
	 */
	ConnectorType getType();
}