package org.eclipse.wst.xsd.tests.performance.scalability;

import org.eclipse.wst.common.tests.performance.internal.scalability.OpenEditorTestCase;
import org.eclipse.wst.xsd.ui.internal.XSDEditorPlugin;

public class Open1KBFileTestCase extends OpenEditorTestCase
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
    return "data/1KB.xsd";
  }

  public void testOpen1KBFile()
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