/*
 * Copyright (C) 2008-2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty;

import java.io.InputStream;

/**
 * <p>
 * Interface to read {@link Contract}s from streams.
 * </p>
 * 
 * @author Jens Dietrich
 */
public interface ContractReader {

	/**
	 * <p>
	 * Reads a {@link ContractReaderException} from an {@link InputStream}.
	 * </p>
	 * 
	 * @param in
	 *          The {@link InputStream} used to read the {@link Contract}.
	 * @param vocabulary
	 *          The {@link ContractVocabulary} used to read the {@link Contract}.
	 * @return The read {@link Contract}.
	 * @throws TreatyException
	 *           Thrown, if the reading procedures fails.
	 */
	public Contract read(InputStream in, ContractVocabulary vocabulary)
			throws TreatyException;
}