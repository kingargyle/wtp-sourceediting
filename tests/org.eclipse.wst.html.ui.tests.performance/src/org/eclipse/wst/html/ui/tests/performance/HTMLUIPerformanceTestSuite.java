package org.eclipse.wst.html.ui.tests.performance;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author pavery
 */
public class HTMLUIPerformanceTestSuite extends TestSuite {
	public HTMLUIPerformanceTestSuite() {
		super("HTML UI Performance Tests");
		
		addTest(new TestSuite(BasicPluginTests.class));
	}
	
	public static Test suite() {
		return new HTMLUIPerformanceTestSuite();
	}
}
