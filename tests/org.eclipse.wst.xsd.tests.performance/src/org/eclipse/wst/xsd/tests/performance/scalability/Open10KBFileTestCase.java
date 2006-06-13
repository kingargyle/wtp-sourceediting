package org.eclipse.wst.xsd.tests.performance.scalability;

import org.eclipse.wst.common.tests.performance.internal.scalability.OpenEditorTestCase;

public class Open10KBFileTestCase extends OpenEditorTestCase
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