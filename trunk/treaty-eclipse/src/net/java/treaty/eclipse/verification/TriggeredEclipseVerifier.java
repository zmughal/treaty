/*
 * Copyright (C) 2009 Jens Dietrich
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package net.java.treaty.eclipse.verification;

import static net.java.treaty.eclipse.Constants.VERIFICATION_EXCEPTION;
import net.java.treaty.Connector;
import net.java.treaty.Contract;
import net.java.treaty.Resource;
import net.java.treaty.ResourceLoader;
import net.java.treaty.ResourceLoaderException;
import net.java.treaty.VerificationPolicy;
import net.java.treaty.eclipse.Logger;
import net.java.treaty.trigger.verification.AbstractTriggeredVerifier;

public class TriggeredEclipseVerifier extends AbstractTriggeredVerifier {

	/** The singleton instance of the {@link TriggeredEclipseVerifier}. */
	public static final TriggeredEclipseVerifier INSTANCE =
			new TriggeredEclipseVerifier();

	/**
	 * <p>
	 * Private constructor for singleton pattern.
	 * </p>
	 */
	public TriggeredEclipseVerifier() {

		/* FIXME Claas: This should be done dynamically via an extension point. */
		this.addListener(TriggeredVerifierResultLogger.INSTANCE);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.java.treaty.event.verification.AbstractTriggeredVerifier#verify(net
	 * .java.treaty.Contract)
	 */
	protected boolean verify(Contract contract) {

		boolean result;

		EclipseVerificationReport eclipseVerificationReport;
		eclipseVerificationReport = new EclipseVerificationReport();
		eclipseVerificationReport.setContract(contract);

		EclipseVerifier eclipseVerifier;
		eclipseVerifier = new EclipseVerifier();

		try {
			this.loadResources(contract, eclipseVerifier);

			result =
					contract.check(eclipseVerificationReport, eclipseVerifier,
							VerificationPolicy.DETAILED);
		} catch (ResourceLoaderException e) {

			Logger.error(
					"Unexpected ResourceLoaderException during verificatio of Contract "
							+ contract, e);
			result = false;
		}

		return result;
	}

	/**
	 * <p>
	 * A helper method that loads the {@link Resource}s required to verify a given
	 * {@link Contract}.
	 * </p>
	 * 
	 * @param contract
	 *          The {@link Contract} whose {@link Resource}s shall be loaded.
	 * @param loader
	 *          The {@link ResourceLoader} used to load the {@link Resource}s.
	 * @throws ResourceLoaderException
	 *           Thrown, if the loading fails.
	 */
	private void loadResources(Contract contract, ResourceLoader loader)
			throws ResourceLoaderException {

		/* Probably load consumer resources. */
		for (Resource resource : contract.getConsumerResources()) {
			this.loadResource(loader, contract.getConsumer(), resource);
		}
		// end for.

		/* Probably load supplier resources. */
		for (Resource resource : contract.getSupplierResources()) {
			this.loadResource(loader, contract.getSupplier(), resource);
		}
		// end for.

		/* Probably load external resources (from legislator plug-ins). */
		for (Resource resource : contract.getExternalResources()) {
			loadResource(loader, resource.getOwner(), resource);
		}
		// end for.
	}

	/**
	 * <p>
	 * Loads a given {@link Resource} from a given {@link Connector} using a given
	 * {@link ResourceLoader}.
	 * </p>
	 * 
	 * @param loader
	 *          The {@link ResourceLoader} used to load the {@link Resource}.
	 * @param connector
	 *          The {@link Connector} from that the {@link Resource} shall be
	 *          loaded.
	 * @param resource
	 *          The {@link Resource} that shall be loaded.
	 */
	private void loadResource(ResourceLoader loader, Connector connector,
			Resource resource) {

		/* Check if the resource has been loaded already. */
		if (resource.isProvided() && !resource.isLoaded()) {

			/* Try to load the resource. */
			try {
				Object value;

				value = loader.load(resource.getType(), resource.getName(), connector);
				resource.setValue(value);
			}

			catch (ResourceLoaderException x) {
				resource.setProperty(VERIFICATION_EXCEPTION, x);
			}
			// end catch.
		}
		// no else.
	}
}