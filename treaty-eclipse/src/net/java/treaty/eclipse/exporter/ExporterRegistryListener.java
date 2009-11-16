/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.exporter;

/**
 * <p>
 * Interface used to listen to the {@link ExporterRegistry}.
 * </p>
 * 
 * @author Claas Wilke
 */
public interface ExporterRegistryListener {

	/**
	 * <p>
	 * Method called, if an {@link Exporter} has been added to the
	 * {@link ExporterRegistry}.
	 * </p>
	 * 
	 * @param exporter
	 *          The {@link Exporter} that has been added to the
	 *          {@link ExporterRegistry}.
	 */
	public void exporterAdded(Exporter exporter);

	/**
	 * <p>
	 * Method called, if an {@link Exporter} has been removed from the
	 * {@link ExporterRegistry}.
	 * </p>
	 * 
	 * @param exporter
	 *          The {@link Exporter} that has been removed from the
	 *          {@link ExporterRegistry}.
	 */
	public void exporterRemoved(Exporter exporter);
}