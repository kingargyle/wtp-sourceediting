package org.eclipse.wst.html.ui.tests.performance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.test.performance.Performance;
import org.eclipse.test.performance.PerformanceTestCase;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.exceptions.ResourceAlreadyExists;
import org.eclipse.wst.sse.core.internal.provisional.exceptions.ResourceInUse;

public class ContentDescriberForHTMLTest extends PerformanceTestCase {
	public void testModelLeak() throws InterruptedException, UnsupportedEncodingException, IOException, ResourceInUse, ResourceAlreadyExists, CoreException {
		for (int i = 0; i < 20; i++) {
			System.gc();
			Thread.sleep(100);
			System.gc();
			Thread.sleep(100);
			System.gc();
			Thread.sleep(100);

			startMeasuring();
			String filename = "testfiles/html/500KB.html";
			IStructuredModel model = StructuredModelManager.getModelManager().getModelForRead(filename, getClass().getResourceAsStream(filename), null);
			if (model != null) {
				File tmpFile = File.createTempFile("temp", ".htm");
				IStructuredModel tmpModel = model.copy(tmpFile.getPath());
				model.releaseFromRead();
				model = null;

				if (tmpModel != null) {
					tmpModel.save(new FileOutputStream(tmpFile));
					tmpModel.releaseFromEdit();
					tmpModel = null;
				}
				System.gc();
				Thread.sleep(100);
				System.gc();
				Thread.sleep(100);
				System.gc();
				Thread.sleep(100);
				stopMeasuring();
			}
		}
		commitMeasurements();
		assertPerformance();
	}

	protected void setUp() throws Exception {
		Performance performance = Performance.getDefault();
		fPerformanceMeter = performance.createPerformanceMeter(performance.getDefaultScenarioId(this));
	}
}
