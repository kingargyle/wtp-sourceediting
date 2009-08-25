package org.w3c.domts;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;

/**
 * The main plugin class to be used in the desktop.
 */
public class SSEModelXMLTestsPlugin extends Plugin {
	//The shared instance.
	private static SSEModelXMLTestsPlugin plugin;

	/**
	 * The constructor.
	 */
	public SSEModelXMLTestsPlugin() {
		super();
		plugin = this;
	}

	/**
	 * Returns the shared instance.
	 */
	public static SSEModelXMLTestsPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the string from the plugin's resource bundle,
	 * or 'key' if not found.
	 */
	public static String getResourceString(String key) {
		return key;
	}

	/**
	 * Returns the plugin's resource bundle,
	 */
	public ResourceBundle getResourceBundle() {
		return null;
	}

	public static URL getInstallLocation() {
		URL installLocation = Platform.getBundle("org.eclipse.wst.xml.core.tests").getEntry("/");
		URL resolvedLocation = null;
		try {
			resolvedLocation = FileLocator.resolve(installLocation);
		}
		catch (IOException e) {
			// impossible
			throw new Error(e);
		}
		return resolvedLocation;
	}

	public static File getTestFile(String filepath) {
		URL installURL = getInstallLocation();
		//String scheme = installURL.getProtocol();
		String path = installURL.getPath();
		String location = path + filepath;
		File result = new File(location);
		return result;
	}
}
