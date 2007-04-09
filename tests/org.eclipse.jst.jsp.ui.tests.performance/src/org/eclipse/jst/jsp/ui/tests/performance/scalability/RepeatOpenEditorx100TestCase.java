package org.eclipse.jst.jsp.ui.tests.performance.scalability;

import org.eclipse.wst.common.tests.performance.internal.scalability.RepeatOpenEditorTestCase;

public class RepeatOpenEditorx100TestCase extends RepeatOpenEditorTestCase
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
    return "data/100KB.jsp";
  }

  protected int getRepeatCount()
  {
    return 100;
  }

  public void testOpenx100()
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