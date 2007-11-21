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

import org.eclipse.wst.sse.core.internal.provisional.INodeAdapter;
import org.eclipse.wst.sse.core.internal.provisional.INodeAdapterFactory;
import org.eclipse.wst.sse.core.internal.provisional.INodeNotifier;
import org.eclipse.wst.sse.ui.internal.contentoutline.IJFaceNodeAdapter;
import org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeAdapterFactory;

public class OverlayJFaceNodeAdapterFactory extends JFaceNodeAdapterFactory
{
    ContentOutlineConfiguration configuration;
    
    public OverlayJFaceNodeAdapterFactory( ContentOutlineConfiguration configuration)
    {        
        super( IJFaceNodeAdapter.class, true);
        this.configuration = configuration;
    }
    
	/**
	 * @see JFaceNodeAdapterFactory#copy()
	 */
	public INodeAdapterFactory copy()
	{
		return new OverlayJFaceNodeAdapterFactory( configuration);
	}
	
    /* (non-Javadoc)
     * @see org.eclipse.wst.xml.ui.views.contentoutline.JFaceNodeAdapterFactory#createAdapter(org.eclipse.wst.sse.core.INodeNotifier)
     */
    protected INodeAdapter createAdapter(INodeNotifier node)
    {
        if (singletonAdapter == null) 
		{
            // create the JFaceNodeAdapter
            singletonAdapter = new OverlayJFaceNodeAdapter( this);
            initAdapter( singletonAdapter, node);
        }
        return singletonAdapter;
    }
    
    
    public ContentOutlineConfiguration getConfiguration()
    {
        return configuration;
    }    
}
