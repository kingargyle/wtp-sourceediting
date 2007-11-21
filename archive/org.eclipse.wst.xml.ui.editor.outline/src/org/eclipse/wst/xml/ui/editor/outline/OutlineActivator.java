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

package org.eclipse.wst.xml.ui.editor.outline;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.ui.editors.text.templates.ContributionContextTypeRegistry;
import org.eclipse.ui.editors.text.templates.ContributionTemplateStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.wst.xml.ui.editor.outline.internal.OutlineTemplateContextType;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class OutlineActivator extends AbstractUIPlugin {
	
	TemplateStore templateStore = null;
	ContributionContextTypeRegistry contextTypeRegistry = null;
	
	// The shared instance
	private static OutlineActivator plugin;
	
	/**
	 * The constructor
	 */
	public OutlineActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static OutlineActivator getDefault() {
		return plugin;
	}

	public TemplateStore getTemplateStore() 
	{
		if (templateStore == null) 
		{
			templateStore= new ContributionTemplateStore( getContextTypeRegistry(), getDefault().getPreferenceStore(), "templates");
			try 
			{
				templateStore.load();
			} 
			catch (IOException ex) 
			{
				log( "loading templates failed.", ex);
			}
		}

		return templateStore;
	}	
	
	public ContextTypeRegistry getContextTypeRegistry() 
	{
		if( contextTypeRegistry == null) 
		{
			contextTypeRegistry= new ContributionContextTypeRegistry();
			contextTypeRegistry.addContextType( OutlineTemplateContextType.ID);
		}
		return contextTypeRegistry;
	}
	
	public void log( String message, Throwable ex) 
	{
		getLog().log(new Status(IStatus.ERROR, getBundle().getSymbolicName(), IStatus.ERROR, message, ex));
	}
	
	/* (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#initializeImageRegistry(org.eclipse.jface.resource.ImageRegistry)
     */
    protected void initializeImageRegistry(ImageRegistry reg)
    {
        super.initializeImageRegistry(reg);

        try
        {
            URL baseURL = getBundle().getEntry( "/icons/"); //$NON-NLS-1$
            reg.put( "icon", ImageDescriptor.createFromURL( new URL( baseURL, "xslt-16.gif"))); //$NON-NLS-1$ //$NON-NLS-2$
            reg.put( "plus", ImageDescriptor.createFromURL( new URL( baseURL, "plus.gif"))); //$NON-NLS-1$ //$NON-NLS-2$
            reg.put( "refresh-outline", ImageDescriptor.createFromURL( new URL( baseURL, "refresh-outline.gif"))); //$NON-NLS-1$ //$NON-NLS-2$
            reg.put( "preferences", ImageDescriptor.createFromURL( new URL( baseURL, "preferences.gif"))); //$NON-NLS-1$ //$NON-NLS-2$
        }
        catch (MalformedURLException ex)
        {
            log( "Failed to initialize image registry.", ex);
        }
    }
}
