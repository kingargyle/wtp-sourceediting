/*******************************************************************************
 * Copyright (c) 2004, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     
 *******************************************************************************/
package org.eclipse.wst.html.ui.examples.editors.internal;

import org.eclipse.ui.plugin.AbstractUIPlugin;

public class EditorExamplesPlugin extends AbstractUIPlugin {
	// The shared instance.
	private static EditorExamplesPlugin plugin;

	/**
	 * Returns the shared instance.
	 */
	public static EditorExamplesPlugin getDefault() {
		return plugin;
	}

	/**
	 * The constructor.
	 */
	public EditorExamplesPlugin() {
		super();
		plugin = this;
	}
}
