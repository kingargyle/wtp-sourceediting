package org.eclipse.wst.sse.ui.tests.performance;

import org.eclipse.test.performance.PerformanceTestCase;
import org.eclipse.wst.sse.ui.EditorPlugin;

/**
 * @author pavery
 */
public class BasicPluginTests extends PerformanceTestCase {
	
	public void testInitializePlugin() {
		// just a dummy first test
		startMeasuring();
		stopMeasuring();
		EditorPlugin.getDefault();
		commitMeasurements();
		assertPerformance();
	}
}
