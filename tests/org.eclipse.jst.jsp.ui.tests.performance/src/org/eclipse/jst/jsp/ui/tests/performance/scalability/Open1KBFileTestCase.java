/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.jsp.ui.tests.performance.scalability;

import org.eclipse.wst.common.tests.performance.internal.scalability.OpenEditorTestCase;

public class Open1KBFileTestCase extends OpenEditorTestCase
{
  protected String getEditorId()
  {
    return "org.eclipse.jst.jsp.core.jspsource.source";
  }

  protected String getBundleId()
  {
    return "org.eclipse.jst.jsp.ui.tests.performance";
  }

  protected String getFilePath()
  {
    return "data/1KB.jsp";
  }

  public void testOpen1KBFile()
  {
    try
    {
      super.execute();
    }
    catch (Exception t)
    {
      fail(t.getMessage());
    }
  }
}