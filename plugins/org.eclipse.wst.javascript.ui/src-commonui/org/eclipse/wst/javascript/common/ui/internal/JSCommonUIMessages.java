/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     
 *******************************************************************************/
package org.eclipse.wst.javascript.common.ui.internal;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class JSCommonUIMessages {
	// Resource bundle.
	private static ResourceBundle fResourceBundle;
	private static final String KEY_PREFIX = "%"; //$NON-NLS-1$
	private static final String KEY_DOUBLE_PREFIX = "%%"; //$NON-NLS-1$

	/**
	 * Returns the plugin's resource bundle,
	 */
	public static ResourceBundle getResourceBundle() {
		try {
			if (fResourceBundle == null)
				fResourceBundle = ResourceBundle.getBundle("org.eclipse.wst.javascript.common.ui.internal.JSCommonUIPluginResources");
		} catch (MissingResourceException x) {
			fResourceBundle = null;
		}
		return fResourceBundle;
	}

	/**
	 * Returns the string from the plugin's resource bundle, or 'key' if not
	 * found.
	 */
	public static String getResourceString(String value) {
		String s = value.trim();
		if (!s.startsWith(KEY_PREFIX, 0))
			return s;
		if (s.startsWith(KEY_DOUBLE_PREFIX, 0))
			return s.substring(1);

		int ix = s.indexOf(' ');
		String key = ix == -1 ? s : s.substring(0, ix);

		ResourceBundle bundle = getResourceBundle();
		try {
			return (bundle != null) ? bundle.getString(key.substring(1)) : key;
		} catch (MissingResourceException e) {
			return key;
		}
	}

	public static String getResourceString(String key, Object[] args) {

		try {
			return MessageFormat.format(getResourceString(key), args);
		} catch (IllegalArgumentException e) {
			return getResourceString(key);
		}

	}
}
