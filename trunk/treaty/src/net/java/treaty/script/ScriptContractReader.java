package net.java.treaty.script;

import java.io.InputStream;

import net.java.treaty.Contract;
import net.java.treaty.ContractReader;
import net.java.treaty.ResourceManager;
import net.java.treaty.TreatyException;

public class ScriptContractReader implements ContractReader {

	private ResourceManager resourceManager;

	public ScriptContractReader(ResourceManager resourceManager) {

		super();
		this.resourceManager = resourceManager;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractReader#getLoader()
	 */
	public ResourceManager getLoader() {

		return resourceManager;
	}

	/*
	 * (non-Javadoc)
	 * @see net.java.treaty.ContractReader#read(java.io.InputStream)
	 */
	public Contract read(InputStream in) throws TreatyException {

		return TreatyScript.fromInputStream(in);
	}
}