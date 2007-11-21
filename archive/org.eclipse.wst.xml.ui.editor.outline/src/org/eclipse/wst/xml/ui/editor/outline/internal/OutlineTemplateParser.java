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

package org.eclipse.wst.xml.ui.editor.outline.internal;

import java.io.IOException;
import java.io.StringBufferInputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.jface.text.templates.TemplateException;
import org.eclipse.wst.xml.ui.editor.outline.OutlineActivator;
import org.eclipse.wst.xml.ui.editor.outline.internal.model.ElementMatcher;
import org.eclipse.wst.xml.ui.editor.outline.internal.model.OutlineTemplateModel;
import org.eclipse.wst.xml.ui.editor.outline.internal.model.OutlineTemplateRule;
import org.eclipse.wst.xml.ui.editor.outline.internal.model.OutlineToken;
import org.eclipse.wst.xml.ui.editor.outline.internal.model.XPathOutlineToken;
import org.eclipse.wst.xml.ui.editor.outline.internal.util.AntTokenParser;
import org.eclipse.wst.xml.ui.editor.outline.internal.util.Tracing;

public class OutlineTemplateParser {	
	
	public static final String TRACE_ID = "parser";
	
	protected void trace( String message, Object... messageParams) {
		Tracing.trace( OutlineActivator.getDefault(), TRACE_ID, message, messageParams);
	}
	
	public OutlineTemplateModel parse( String pattern) throws TemplateException {
		try {
			OutlineTemplateModel model = new OutlineTemplateModel();
			
			Properties p = new Properties();
			p.load( new StringBufferInputStream( pattern));
			
			for( Object key : p.keySet()) {
				String elementNamePattern = (String)key;
				String labelPattern = p.getProperty( elementNamePattern);
				
				String[] labels = labelPattern.split( "\\|");
				OutlineTemplateRule labelASTs = new OutlineTemplateRule();
				
				for( String s : labels) {
					trace( "tokenize label " + s);	
					
					AntTokenParser tokenizer = new AntTokenParser( s) {
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
					while( tokenizer.hasMoreTokens()) {
						labelAST.add( (OutlineToken)tokenizer.nextToken());
					}	
					labelASTs.add( labelAST);
				}
				
				model.put( new ElementMatcher( elementNamePattern, labelPattern), labelASTs);
			}
			
			return model;
		}
		catch( RuntimeException ex) {
			throw new TemplateException( MessageFormat.format( "Failed to parse template : {0}", ex.getMessage()), ex);
		} 
		catch (IOException ex) {
			throw new TemplateException( "Failed to load template. its not java properties file syntax conform", ex);
		}
	}
}
