/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     
 *******************************************************************************/

package testStartup;

import org.eclipse.core.runtime.IPlatformRunnable;

public class TestStart implements IPlatformRunnable {

	static char BELL = (char) 7;
	
	public Object run(Object args) throws Exception {
		System.out.println();
		System.out.println();
		System.out.println("                 Hello from an Eclipse App");
		System.out.println();
		System.out.println();		
		System.out.println();
		return IPlatformRunnable.EXIT_OK;
	}

}
