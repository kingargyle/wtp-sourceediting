package org.eclipse.wst.html.ui.tests.performance.scalability;

import org.eclipse.wst.common.tests.performance.internal.scalability.RepeatOpenEditorTestCase;

public class RepeatOpenEditorx50TestCase extends RepeatOpenEditorTestCase
{
  protected String getEditorId()
  {
    return "org.eclipse.wst.html.core.htmlsource.source";
  }

  protected String getBundleId()
  {
    return "org.eclipse.wst.html.ui.tests.performance";
  }

  protected String getFilePath()
  {
    return "data/100KB.html";
  }

  protected int getRepeatCount()
  {
    return 50;
  }

  public void testOpenx50()
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