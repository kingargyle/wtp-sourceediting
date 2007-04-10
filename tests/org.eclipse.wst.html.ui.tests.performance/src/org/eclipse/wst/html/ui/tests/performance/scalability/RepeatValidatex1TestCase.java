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
package org.eclipse.wst.html.ui.tests.performance.scalability;

import org.eclipse.wst.common.tests.performance.internal.scalability.RepeatRunValidatorTestCase;

public class RepeatValidatex1TestCase extends RepeatRunValidatorTestCase
{
  protected int getRepeatCount()
  {
    return 1;
  }

  protected String getValidatorId()
  {
    return "org.eclipse.wst.html.internal.validation.HTMLValidator";
  }

  protected String getBundleId()
  {
    return "org.eclipse.wst.html.ui.tests.performance";
  }

  protected String getFilePath()
  {
    return "data/100KB.html";
  }

  public void testValidatex1()
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