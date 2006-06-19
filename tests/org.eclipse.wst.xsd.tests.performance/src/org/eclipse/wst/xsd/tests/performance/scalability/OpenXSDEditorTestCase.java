/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.wst.xsd.tests.performance.scalability;

import org.eclipse.wst.common.tests.performance.internal.scalability.OpenEditorTestCase;
import org.eclipse.wst.xsd.tests.performance.XSDPerformancePlugin;
import org.eclipse.wst.xsd.ui.internal.editor.XSDEditorPlugin;

public abstract class OpenXSDEditorTestCase extends OpenEditorTestCase
{
  protected String getEditorId()
  {
    return XSDEditorPlugin.EDITOR_ID;
  }

  protected String getBundleId()
  {
    return XSDPerformancePlugin.BUNDLE_ID;
  }
}
