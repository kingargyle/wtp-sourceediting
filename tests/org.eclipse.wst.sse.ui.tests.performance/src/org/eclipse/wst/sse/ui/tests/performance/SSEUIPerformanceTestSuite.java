package org.eclipse.wst.sse.ui.tests.performance;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author pavery
 */
public class SSEUIPerformanceTestSuite extends TestSuite {
	public SSEUIPerformanceTestSuite() {
		super("XML UI Performance Tests");
		
		addTest(new TestSuite(BasicPluginTests.class));
	}
	
	public static Test suite() {
		return new SSEUIPerformanceTestSuite();
	}
}
