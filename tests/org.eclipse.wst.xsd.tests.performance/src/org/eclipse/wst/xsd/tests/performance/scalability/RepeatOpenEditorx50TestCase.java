package org.eclipse.wst.xsd.tests.performance.scalability;

import org.eclipse.wst.common.tests.performance.internal.scalability.RepeatOpenEditorTestCase;

public class RepeatOpenEditorx50TestCase extends RepeatOpenEditorTestCase
{
  protected void setUp() throws Exception
  {
    super.setUp();
  }

  protected String getEditorId()
  {
    return "org.eclipse.wst.xsd.ui.XSDEditor";
  }

  protected String getBundleId()
  {
    return "org.eclipse.wst.xsd.tests.performance";
  }

  protected String getFilePath()
  {
    return "data/100KB.xsd";
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
    catch (Throwable t)
    {
      fail(t.getMessage());
    }
  }
}