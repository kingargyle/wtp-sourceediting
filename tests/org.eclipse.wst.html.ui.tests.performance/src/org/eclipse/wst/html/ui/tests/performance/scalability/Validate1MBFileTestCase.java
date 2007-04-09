package org.eclipse.wst.html.ui.tests.performance.scalability;

import org.eclipse.wst.common.tests.performance.internal.scalability.RunValidatorTestCase;

public class Validate1MBFileTestCase extends RunValidatorTestCase
{
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
    return "data/1MB.html";
  }

  public void testOpen1MBFile()
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