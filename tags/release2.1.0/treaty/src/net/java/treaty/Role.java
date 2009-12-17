/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */
package net.java.treaty;

/**
 * TODO Claas: Recommend renaming into ConnectorContractRole or something like
 * that.
 * 
 * <p>
 * The different Roles {@link Connector}s can play in a {@link Contract}.
 * </p>
 * 
 * @author Jens Dietrich
 */
public enum Role {

	/**
	 * A {@link Connector} playing the {@link Role#CONSUMER} role consumes a
	 * service or data.
	 */
	CONSUMER,

	/**
	 * A {@link Connector} playing the {@link Role#LEGISLATOR} role defines an
	 * external {@link Contract} on other {@link Connector}s playing the
	 * {@link Role#CONSUMER} and {@link Role#SUPPLIER} role.
	 */
	LEGISLATOR,

	/**
	 * A {@link Connector} playing the {@link Role#SUPPLIER} role provides a
	 * service or data.
	 */
	SUPPLIER
}