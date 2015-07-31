If new bundles are installed to Eclipse dynamically, the Treaty ContractRegistry should be updated dynamically as well. Unfortunately, the standard way Eclipse updates its ExtensionRegistry after the installation of new bundles is by recommending a restart of the whole Eclipse system. Nevertheless, it is possible to refresh the bundle installation dynamically. How this can be done is shortly explained in the following.

# Install a bundle dynamically without restarting Eclipse #

1. Start Eclipse via command line or shortcut and adding the -console argument to the target. Eclipse should now start with a OSGi console window.

2. Either drop your new bundle into the **<eclipse root>/dropins** folder or install it via an Eclipse Update Site (**<eclipse root>** represents the absolute path to the directory into which your Eclipse distribution has been installed).

3. If you dropped the bundle into the **<eclipse root>/dropins** folder, you have to install it manually by typing the following command into the OSGi console (**<bundle.jar>** represents the file name of the bundle you dropped into the directory):

**osgi> install 'file:<eclipse root>/dropins/<bundle.jar>'**

If you installed the bundle via an Eclipse Update Site you should type the following command instead:

**osgi> install 'file:<eclipse root>/plugins/<bundle.jar>'**

The bundle should be installed and the console should print you a **<bundle id>** used for the newly installed bundle. E.g., **Bundle id is 102**.

4. Refresh the bundle by typing the following command into the OSGi console:

**osgi> refresh <bundle id>**

The Eclipse ExtensionRegistry and the Treaty ContractRegistry should be updated now and any contract defined by the newly installed bundle (or new extensions that are contracted) should appear in the Treaty Contract View.

# Uninstall a bundle dynamically without restarting Eclipse #
The same should work when uninstalling bundles using the following command:

**osgi> uninstall <bundle id>**

If the uninstalled bundle provided an extension of an extension point belonging to another bundle, this other bundle must be refreshed before the changes become visible:

**osgi> uninstall <extension provider bundle id>**

**osgi> refresh <extension point consumer bundle id>**