package org.eclipse.wst.sse.ui.tests.performance;

import org.eclipse.test.performance.PerformanceTestCase;
import org.eclipse.wst.sse.ui.internal.SSEUIPlugin;

/**
 * @author pavery
 */
public class BasicPluginTests extends PerformanceTestCase {

	public void testInitializePlugin() {
		// just a dummy first test
		startMeasuring();
		stopMeasuring();
		SSEUIPlugin.getDefault();
		commitMeasurements();
		assertPerformance();
	}
}
