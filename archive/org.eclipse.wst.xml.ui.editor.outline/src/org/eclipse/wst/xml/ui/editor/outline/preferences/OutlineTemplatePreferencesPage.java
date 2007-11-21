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

package org.eclipse.wst.xml.ui.editor.outline.preferences;

import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.texteditor.templates.TemplatePreferencePage;
import org.eclipse.wst.xml.ui.editor.outline.OutlineActivator;
import org.eclipse.wst.xml.ui.editor.outline.internal.OutlineTemplateCache;

public class OutlineTemplatePreferencesPage extends TemplatePreferencePage implements IWorkbenchPreferencePage {
	public OutlineTemplatePreferencesPage() {
		setPreferenceStore( OutlineActivator.getDefault().getPreferenceStore());
		setTemplateStore( OutlineActivator.getDefault().getTemplateStore());
		setContextTypeRegistry( OutlineActivator.getDefault().getContextTypeRegistry());
	}
	
	@Override
	public boolean performOk() {
		boolean b =  super.performOk();
		
		OutlineTemplateCache.getInstance().reload();
		
		return b;
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		
		OutlineTemplateCache.getInstance().reload();
	}
}
