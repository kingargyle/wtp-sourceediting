package org.eclipse.wst.html.ui.tests.performance;

import org.eclipse.test.performance.PerformanceTestCase;
import org.eclipse.wst.html.ui.internal.HTMLUIPlugin;

/**
 * @author pavery
 */
public class BasicPluginTests extends PerformanceTestCase {
	
	public void testInitializePlugin() {
		// just a dummy first test
		startMeasuring();
		stopMeasuring();
		HTMLUIPlugin.getDefault();
		commitMeasurements();
		assertPerformance();
	}
}
