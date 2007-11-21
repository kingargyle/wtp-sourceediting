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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.w3c.dom.Element;

public class OutlineTemplateRule extends ArrayList<List<OutlineToken>>{
	public String getLabel( Element element) {
		nextLabelAST :
		for( List<OutlineToken> tokens : this) {			
			StringBuilder sb = new StringBuilder();
			for( OutlineToken token : tokens) {
				String s;
				if( (s=token.apply( element))!=null) {
					sb.append( s);
				}
				else {
					// System.out.println( "token " + token + "returned null");
					continue nextLabelAST; 
				}
					
			}
			
			return sb.toString();
		}
	
		return null;
	}
	
	public Image getImage( Element element) {
		return null;
	}
}
