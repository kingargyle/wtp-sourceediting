package org.eclipse.jst.jsp.ui.tests.performance.scalability;

import org.eclipse.wst.common.tests.performance.internal.scalability.RunValidatorTestCase;

public class Validate200KBFileTestCase extends RunValidatorTestCase
{
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
    return "data/200KB.jsp";
  }

  public void testOpen200KBFile()
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