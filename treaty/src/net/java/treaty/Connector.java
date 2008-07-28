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
 * This interface abstracts from extension points (consuming) and extensions (providing).  
 * @author Jens Dietrich
 */

public interface Connector<C extends Component> {
	/**
	 * Indicates the connector type.
	 * @return
	 */
	ConnectorType getType();
	/**
	 * Get the connector id.
	 * For instance, this could be the id of the eclipse extension point or extension.
	 * @return
	 */
	String getId();
	/**
	 * Get the component that owns this connector.
	 * @return
	 */
	C getOwner();

}
