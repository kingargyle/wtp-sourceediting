/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.wst.javascript.ui.internal.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.wst.sse.ui.internal.IReleasable;

/**
 * This special content assistant will properly dispose of content assist
 * processors if they are IReleasable.
 */
class JavascriptContentAssistant extends ContentAssistant {
	private Map fProcessors;

	public void setContentAssistProcessor(IContentAssistProcessor processor, String contentType) {
		super.setContentAssistProcessor(processor, contentType);
		if (fProcessors == null)
			fProcessors = new HashMap();

		if (processor == null)
			fProcessors.remove(contentType);
		else
			fProcessors.put(contentType, processor);
	}

	public void uninstall() {
		// dispose of all content assist processors
		if (fProcessors != null && !fProcessors.isEmpty()) {
			Collection collection = fProcessors.values();
			Iterator iter = collection.iterator();
			while (iter.hasNext()) {
				Object processor = iter.next();
				if (processor instanceof IReleasable) {
					((IReleasable) processor).release();
				}
			}
			fProcessors.clear();
		}
		super.uninstall();
	}
}
