package org.eclipse.wst.xsd.tests.performance.scalability;

import org.eclipse.core.resources.IProject;
import org.eclipse.wst.common.tests.performance.internal.scalability.RunValidatorTestCase;

public class Validate100XSDsCrossDependsTestCase extends RunValidatorTestCase {

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
	    for(int i = 1; i < 100; i++)
	    {
	      copyFile(bundleId, "data/100XSDsCrossDepends/sample" + i +".xsd", project);
	    }
	    super.setUp();
	}

	protected String getFilePath()
	{
	    return "data/100XSDsCrossDepends/sample0.xsd";
	}
	
	public void testValidate100XSDsCrossDepends()
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

