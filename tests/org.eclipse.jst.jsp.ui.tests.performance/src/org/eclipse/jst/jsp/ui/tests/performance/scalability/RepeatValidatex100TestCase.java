package org.eclipse.jst.jsp.ui.tests.performance.scalability;

import org.eclipse.wst.common.tests.performance.internal.scalability.RepeatRunValidatorTestCase;

public class RepeatValidatex100TestCase extends RepeatRunValidatorTestCase
{
  protected int getRepeatCount()
  {
    return 100;
  }

  protected String getValidatorId()
  {
    return "org.eclipse.jst.jsp.core.internal.validation.JSPValidator";
  }

  protected String getBundleId()
  {
    return "org.eclipse.jst.jsp.ui.tests.performance";
  }

  protected String getFilePath()
  {
    return "data/100KB.jsp";
  }

  public void testValidatex100()
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