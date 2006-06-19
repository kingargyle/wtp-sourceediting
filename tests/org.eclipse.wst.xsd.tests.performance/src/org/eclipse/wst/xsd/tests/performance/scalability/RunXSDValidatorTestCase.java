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

import org.eclipse.wst.common.tests.performance.internal.scalability.RunValidatorTestCase;
import org.eclipse.wst.xsd.tests.performance.XSDPerformancePlugin;

public abstract class RunXSDValidatorTestCase extends RunValidatorTestCase
{
  public RunXSDValidatorTestCase()
  {
    super();
  }

  protected String getValidatorId()
  {
      return XSDPerformancePlugin.XSD_VALIDATOR_ID;
  }

  protected String getBundleId()
  {
      return XSDPerformancePlugin.BUNDLE_ID;
  }
}
