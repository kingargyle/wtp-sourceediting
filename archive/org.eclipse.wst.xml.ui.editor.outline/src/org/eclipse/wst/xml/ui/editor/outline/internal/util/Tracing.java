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

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;

public class Tracing 
{
	static Map<String,String> traceOptionCache = new HashMap<String,String>();
	static SimpleDateFormat format = new SimpleDateFormat( "ss-SSS");
	
	public static boolean isEnabled( Plugin plugin, String option)
	{
		String value = (String)traceOptionCache.get( option);
		
		if( value==null)
		{
			try
			{
				value = Platform.getDebugOption( plugin.getBundle().getSymbolicName() + "/" + option);
			} 
			catch(NullPointerException ex) 	// may happen if platform is shutting down 
			{
				value = "false";
			}
			traceOptionCache.put( option, value);
		}
		
		return ("true").equalsIgnoreCase( (String)value);
	}
	
	public static void trace( Plugin plugin, String option, String message, Object... messageParams)
	{
		if( isEnabled( plugin, option))
		{
			System.out.print( '[');
			System.out.print( plugin.getBundle().getSymbolicName());
			System.out.print( '/');
			System.out.print( option);
			System.out.print( "::" + format.format( new Date()) + "] ");
			System.out.print( "::trace] ");
			System.out.println( messageParams.length==0 ? message : MessageFormat.format( message, messageParams));
			System.out.flush();
		}
	}
}
