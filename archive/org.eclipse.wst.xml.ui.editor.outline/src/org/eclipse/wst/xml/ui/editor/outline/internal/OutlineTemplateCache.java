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

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateException;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.wst.sse.ui.internal.contentoutline.ConfigurableContentOutlinePage;
import org.eclipse.wst.xml.ui.editor.outline.OutlineActivator;
import org.eclipse.wst.xml.ui.editor.outline.internal.model.ElementMatcher;
import org.eclipse.wst.xml.ui.editor.outline.internal.model.OutlineTemplateModel;
import org.w3c.dom.Element;

public class OutlineTemplateCache {
	static Map<ElementMatcher,OutlineTemplateModel> templates = new HashMap<ElementMatcher,OutlineTemplateModel>();
	
	private static final OutlineTemplateCache instance = new OutlineTemplateCache();
	
	private OutlineTemplateParser parser = new OutlineTemplateParser();
	
	private OutlineTemplateCache() {
		reload();
	}
	
	public static final OutlineTemplateCache getInstance() {
		return instance;
	}
	
	public void reload() {
		templates.clear();
		
		Template[] ta = OutlineActivator.getDefault().getTemplateStore().getTemplates( OutlineTemplateContextType.ID);
		for( Template t : ta) {
			try {
				templates.put( new ElementMatcher( t.getName(), t.getDescription()), parser.parse( t.getPattern()));
			} catch (TemplateException ex) {
				OutlineActivator.getDefault().log( MessageFormat.format( "Failed to instantiate template {0} = {1}", t.getName(), t.getPattern()), ex);
			}
		}
		
			try {
				/*
				 * update all outlines
				 */
				IContentType XML_CONTENT_TYPE= Platform.getContentTypeManager().getContentType( "org.eclipse.core.runtime.xml");
				IEditorReference[] ers = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
				for( IEditorReference er : ers==null ? new IEditorReference[0] : ers) {
					IFile file = (IFile)er.getEditor( true).getEditorInput().getAdapter( IFile.class);
					if( file!=null) {
						IContentType contentType = Platform.getContentTypeManager().findContentTypeFor( file.getName());
						if( contentType!=null && contentType.isKindOf( XML_CONTENT_TYPE)) {
							IContentOutlinePage contentOutlinePage = (IContentOutlinePage)er.getEditor( true).getAdapter( IContentOutlinePage.class);
							
							if( contentOutlinePage instanceof ConfigurableContentOutlinePage) {
								ConfigurableContentOutlinePage configurableContentOutlinePage = (ConfigurableContentOutlinePage)contentOutlinePage;
								
								if( configurableContentOutlinePage.getConfiguration() instanceof org.eclipse.wst.xml.ui.editor.outline.internal.ContentOutlineConfiguration) {
									((org.eclipse.wst.xml.ui.editor.outline.internal.ContentOutlineConfiguration)configurableContentOutlinePage.getConfiguration()).updateTreeViewer();
								}
							}
						}
					}
				}
			} catch(Exception ex) {
				OutlineActivator.getDefault().log( "problem updating open xml outlines ", ex);
			}
	}
	
	OutlineTemplateModel findMatching( Element root) {
		for( ElementMatcher em : templates.keySet()) {
			if( em.matches( root)) {
				return templates.get( em);
			}
		}
		
		return null;
	}
}
