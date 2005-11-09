package org.eclipse.wst.xsd.tests.performance.scalability;

import org.eclipse.wst.common.tests.performance.internal.scalability.OpenEditorTestCase;
import org.eclipse.wst.xsd.ui.internal.XSDEditorPlugin;

public class Open100KBFileTestCase extends OpenEditorTestCase
{
  protected void setUp() throws Exception
  {
    super.setUp();
    XSDEditorPlugin.getPlugin().setDefaultPage(XSDEditorPlugin.GRAPH_PAGE);
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

  public void testOpen100KBFile()
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