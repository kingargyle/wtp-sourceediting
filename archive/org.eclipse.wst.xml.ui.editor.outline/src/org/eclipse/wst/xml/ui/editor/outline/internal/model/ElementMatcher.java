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

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Element;

public class ElementMatcher {
	public String regex;
	public String description;
	
	public Pattern pattern=null;
	
	public ElementMatcher( String regex, String description) {
		this.regex = regex;
		this.description = description;
		
		pattern = Pattern.compile( regex);
	}
	
	public boolean matches( Element rootElement) {
		String s = rootElement.getLocalName();
		return s!=null && pattern.matcher( s).matches();
	}
	
	@Override
	public String toString() {
		return MessageFormat.format( "xpath-key {0}={1}", regex, description);
	}
}
