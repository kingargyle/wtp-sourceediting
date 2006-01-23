package org.eclipse.wst.xsd.tests.performance.scalability;

import org.eclipse.core.resources.IProject;
import org.eclipse.wst.common.tests.performance.internal.scalability.RunValidatorTestCase;

public class Validate1000XSDsCrossDependsTestCase extends RunValidatorTestCase {

	protected String getValidatorId()
	{
	    return "org.eclipse.wst.xsd.ui.internal.validation.Validator";
	}

	protected String getBundleId()
	{
	    return "org.eclipse.wst.xsd.tests.performance";
	}
	
	protected void setUp() throws Exception
	{
	    IProject project = createProject("sp");
	    String bundleId = getBundleId();
	    for(int i = 1; i < 1000; i++)
	    {
	      copyFile(bundleId, "data/1000XSDsCrossDepends/sample" + i +".xsd", project);
	    }
	    super.setUp();
	}

	protected String getFilePath()
	{
	    return "data/1000XSDsCrossDepends/sample0.xsd";
	}
	
	public void testValidate1000XSDsCrossDepends()
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
