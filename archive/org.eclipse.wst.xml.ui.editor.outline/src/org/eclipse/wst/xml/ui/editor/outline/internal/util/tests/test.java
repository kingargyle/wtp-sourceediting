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

package org.eclipse.wst.xml.ui.editor.outline.internal.util.tests;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.wst.xml.ui.editor.outline.internal.model.OutlineToken;
import org.eclipse.wst.xml.ui.editor.outline.internal.model.XPathOutlineToken;
import org.eclipse.wst.xml.ui.editor.outline.internal.util.AntTokenParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class test {
	public static void main(String[] args) throws Exception {
		
		String label = "${text}|${@label}|${@xreflabel}|${@id}";
		
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( test.class.getResourceAsStream( "test.xml"));
		Element root = doc.getDocumentElement();
		
		String[] labels = label.split( "[^\\\\]\\|");
		List<List<OutlineToken>> labelASTs = new ArrayList<List<OutlineToken>>();
		
		for( String s : labels) {
			System.out.println( "tokenize label " + s);
			
			AntTokenParser p = new AntTokenParser( s) {
				@Override
				public Object createTextObject(String text) {
					return new OutlineToken(text);
				}
				
				@Override
				public Object createTokenObject(String key) {
					return new XPathOutlineToken( key);
				}
			};
		
			List<OutlineToken> labelAST = new ArrayList<OutlineToken>();			
			while( p.hasMoreTokens()) {
				labelAST.add( (OutlineToken)p.nextToken());
			}	
			labelASTs.add( labelAST);
		}
				
		nextLabelAST :
		for( List<OutlineToken> tokens : labelASTs) {			
			StringBuilder sb = new StringBuilder();
			for( OutlineToken token : tokens) {
				String s;
				if( (s=token.apply( root))!=null) {
					sb.append( s);
				}
				else {
					System.out.println( "token " + token + "returned null");
					continue nextLabelAST; 
				}
					
			}
			
			System.out.println( "sucessed label : " + sb.toString());
			return;
		}
		
		System.out.println( "no label successed.");
	}
}
