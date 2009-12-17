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

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import net.java.treaty.Contract;

/**
 * <p>
 * The {@link Exporter} is responsible to export instantiated contracts,
 * including verification results.
 * </p>
 * 
 * @author Jens Dietrich
 */
public abstract class Exporter {

	/**
	 * <p>
	 * Exports a given {@link Collection} of {@link Contract}s into a given
	 * {@link File}.
	 * </p>
	 * 
	 * @param contracts
	 *          The {@link Contract}s that shall be exported.
	 * @param file
	 *          The {@link File} into that the {@link Contract}s shall be
	 *          exported.
	 * @throws IOException
	 *           Thrown, if an IOException during export occurs.
	 */
	public abstract void export(Collection<Contract> contracts, File file)
			throws IOException;

	/**
	 * <p>
	 * Returns the name of this {@link Exporter}.
	 * </p>
	 * 
	 * @return The name of this {@link Exporter}.
	 */
	public abstract String getName();

	public abstract boolean exportToFolder();

	// this is only used if exportToFolder() returns false
	public abstract String[] getFilterExtensions();

	public abstract String[] getFilterNames();
}