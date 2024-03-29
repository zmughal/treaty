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
 * <p>
 * Describes the different roles {@link Connector}s can have. Components can
 * consume services and resources, and can supply them via {@link Connector}s.
 * </p>
 * 
 * @author Jens Dietrich
 */
public enum ConnectorType {
	CONSUMER, SUPPLIER
}