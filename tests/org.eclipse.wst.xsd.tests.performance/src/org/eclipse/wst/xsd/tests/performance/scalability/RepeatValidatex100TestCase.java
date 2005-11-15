package org.eclipse.wst.xsd.tests.performance.scalability;

import org.eclipse.wst.common.tests.performance.internal.scalability.RepeatRunValidatorTestCase;

public class RepeatValidatex100TestCase extends RepeatRunValidatorTestCase
{
  protected int getRepeatCount()
  {
    return 100;
  }

  protected String getValidatorId()
  {
    return "org.eclipse.wst.xsd.ui.internal.validation.Validator";
  }

  protected String getBundleId()
  {
    return "org.eclipse.wst.xsd.tests.performance";
  }

  protected String getFilePath()
  {
    return "data/100KB.xsd";
  }

  public void testValidatex100()
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