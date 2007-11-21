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

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * handles an xpath token
 */
public class XPathOutlineToken extends OutlineToken {
	
	static private XPathFactory factory = XPathFactory.newInstance();
	private XPathExpression xpath = null;

	/**
	 * throws an {@link IllegalArgumentException} in case the the xpath expression could not be compiled
	 * 
	 * @param key
	 */
	public XPathOutlineToken( String key) {
		super( key);
		try {
			xpath = factory.newXPath().compile( key);
		} catch (XPathExpressionException ex) {
			throw new IllegalArgumentException( MessageFormat.format( "could not compile xpath expression from string {0} : {1}", key, ex.getMessage()), ex);
		}
	}

	/**
	 * @return null if evaluation result was null or an empty string
	 * 
	 * @throws IllegalArgumentException in case the xpath expression could not be applied to the 
	 * given argument
	 * @throws RuntimeException if xpath expression could not be compiled
	 */
	@Override
	public String apply(Object o) {
		if( xpath==null) {
			throw new RuntimeException( MessageFormat.format( "no valid xpath expression {0}", key));
		}
		
		try {
			String s = (String)xpath.evaluate( o, XPathConstants.STRING);
			return s==null || s.length()==0 ? null : s;
		} catch (XPathExpressionException ex) {
			throw new IllegalArgumentException( MessageFormat.format( "could not evaluate compiled xpath expression {0} to object : {1}", key, o), ex);
		}
	}
}
