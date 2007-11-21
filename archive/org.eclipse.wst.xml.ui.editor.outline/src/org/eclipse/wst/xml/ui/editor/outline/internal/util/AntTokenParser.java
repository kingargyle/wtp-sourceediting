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

package org.eclipse.wst.xml.ui.editor.outline.internal.util;

/**
 * <pre>
 * hello ${user}, current date is ${date}
 * </pre>
 * 
 * <pre>
 * HashMap ht = new HashMap();
 * ht.put( "date", new Date());
 * ht.put( "user", "alvaro");
 * 
 * System.out.println( AntTokenParser.substitute( "hello ${user}, current date is ${date}"));
 * </pre>
 * 
 * <pre>
 * AntTokenParser parser = new AntTokenParser( "hello ${user}, current date is ${date}");
 * while( parser.hasMoreTokens())
 * {
 * 		Object token = parser.nextToken();
 * 		if( token instanceof AntToken)
 * 		{
 * 			if( token.equals( "date"))
 * 				System.out.print( new Date());
 *			else if( token.equals( "user"))
 * 				System.out.print( "alvaro");	
 * 		}
 * 		else
 * 			System.out.print( token);
 * }
 * </pre>
 */
import java.util.*;

public class AntTokenParser
{
	String origString;

    StringBuffer finalString = new StringBuffer();
    int index = 0;
    int i = 0;
    String key = null;    
	
	/**
	 * Creates a parser instance for traversing the string for ant tokens
	 * 
	 * @param string to parse
	 */
	public AntTokenParser( String origString)
	{
		this.origString = origString;	
		
		index = origString.indexOf("${", i);
	}

	/**
	 * @return boolean returns true if more tokens are available
	 */
	public boolean hasMoreTokens()
	{
		return index>-1 || i<=origString.length()-1;
	}
	
	/**
	 * @return Object returns the next token of throws NoSuchElementException
	 * if no more tokens are available
	 */
	public Object nextToken()
	{
		if( hasMoreTokens())
		{
            if( index==-1)
            {
            	String text = origString.substring(i);
            	i = origString.length();
            	return createTextObject( text);
            }
            key = origString.substring(index + 2, origString.indexOf("}", index + 3));
            
            if( i!=index)
            {
            	String text = origString.substring( i, index);
            	i=index;
            	return createTextObject( text);
            }
            else 
            {
            	i = index + 3 + key.length();	
            	index = origString.indexOf("${", i);
				return createTokenObject( key);            
            }
		}		
		return new NoSuchElementException( "no more elements");
	}
	
	/**
	 * may be overwritten to return custom token instances 
	 * 
	 * @param key
	 */
	public Object createTokenObject( String key) {
		return new AntToken( key);
	} 
	
	/**
	 * may be overwritten to return custom text object instances 
	 * 
	 * @param key
	 */
	public Object createTextObject( String text) {
		return text;
	}
	
	
	/**
	 * Substitutes the objects from keys with the keys in origString
	 * @param origString string to parse
	 * @param keys the keys to substitute
	 * @return String the substituted string
	 * @throws Exception when parse string is not valid
	 */
	public static String substitute(String origString, HashMap keys) throws Exception 
	{
        StringBuffer finalString = new StringBuffer();
        int index = 0;
        int i = 0;
        String key = null;
        while ((index = origString.indexOf("${", i)) > -1) {
            key = origString.substring(index + 2, origString.indexOf("}", 
                                       index + 3));
            finalString.append (origString.substring(i, index));
            if (keys.containsKey(key)) {
                finalString.append (keys.get(key));
            } else {
                finalString.append ("${");
                finalString.append (key);
                finalString.append ("}");
            }
            i = index + 3 + key.length();
        }
        finalString.append (origString.substring(i));
        return finalString.toString();
    }		
}

