/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.jsdt.debug.ui;

 
import org.eclipse.wst.jsdt.internal.debug.ui.JDIDebugUIPlugin;
 
public interface IJavaDebugUIConstants {
	/**
	 * Plug-in identifier for the Java Debug UI
	 */
	public static final String PLUGIN_ID = JDIDebugUIPlugin.getUniqueIdentifier();

	/**
	 * Status code indicating an unexpected internal error (value <code>150</code>).
	 */
	public static final int INTERNAL_ERROR = 150;

}
