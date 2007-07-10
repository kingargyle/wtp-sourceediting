/*******************************************************************************
 * Copyright (c) 2004, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.javascript.core.tests;

import junit.framework.TestCase;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.wst.javascript.core.internal.JavaScriptCorePlugin;

public class VerifyCorePluginTests extends TestCase {

	public void testPluginExists() {
		assertTrue(true);
		Plugin plugin = null;
		try {
			plugin = JavaScriptCorePlugin.getDefault();
		} catch (Exception e) {
			plugin = null;
		}
		assertNotNull("JavaScript Core plugin could not be instantiated", plugin);

	}
}
