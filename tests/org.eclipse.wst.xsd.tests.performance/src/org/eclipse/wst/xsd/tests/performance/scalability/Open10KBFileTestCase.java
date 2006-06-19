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

public class Open10KBFileTestCase extends OpenXSDEditorTestCase
{
  protected void setUp() throws Exception
  {
    super.setUp();
  }

  protected String getFilePath()
  {
    return "data/10KB.xsd";
  }

  public void testOpen10KBFile()
  {
    try
    {
      super.execute();
    }
    catch (Throwable t)
    {
      fail(t.getMessage());
    }
  }
}