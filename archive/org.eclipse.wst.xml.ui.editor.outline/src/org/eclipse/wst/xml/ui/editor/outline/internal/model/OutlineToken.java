/*******************************************************************************
 * Copyright (c) 2005-2007 Orangevolt (www.orangevolt.com)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Lars Gersmann (www.orangevolt.com) 
 *     
 *******************************************************************************/

package org.eclipse.wst.xml.ui.editor.outline.internal.model;

/** 
 * handles a plain text token
 */

public class OutlineToken {
	String key;
	
	public OutlineToken( String key) {
		this.key = key;
	} 
	
	public String apply( Object o) {
		return key;
	}
	
	@Override
	public String toString() {
		return key;
	}
}