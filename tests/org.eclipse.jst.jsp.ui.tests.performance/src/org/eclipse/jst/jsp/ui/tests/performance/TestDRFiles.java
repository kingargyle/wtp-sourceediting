/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
/*
 * Created on Jun 16, 2003
 *
 */
package org.eclipse.jst.jsp.ui.tests.performance;

import java.io.IOException;

public class TestDRFiles extends AbstractTestPerformance {
	public TestDRFiles() {
		super();
		fTrials = 1;
		collectGarbage = true;
		testStructuredModel = true; // false == test xerces
		runOnce = true;
	}

	// these were put in to one method, to gaurentee the 
	// order they were executed in. (other wise J9 execuated them
	// "in order" but Sov. executed them in opposite order.
	public void testModels() throws IOException {
		if (runOnce) {
			doTestStructuredModel_500kfile();
		}
		else {
			//			doTestStructuredModel_1kfile();
			doTestStructuredModel_10kfile();
			doTestStructuredModel_50kfile();
			//			doTestStructuredModel_75kfile();
			doTestStructuredModel_100kfile();
			//			doTestStructuredModel_150kfile();
			//			doTestStructuredModel_200kfile();
			doTestStructuredModel_250kfile();
			//			doTestStructuredModel_300kfile();
			//			doTestStructuredModel_350kfile();
			//			doTestStructuredModel_400kfile();
			//			doTestStructuredModel_450kfile();
			doTestStructuredModel_500kfile();
			//			doTestStructuredModel_550kfile();
			//			doTestStructuredModel_600kfile();
			//			doTestStructuredModel_800kfile();
			doTestStructuredModel_1000kfile();
			//			doTestStructuredModel_1500kfile();
			doTestStructuredModel_2000kfile();
			doTestStructuredModel_3000kfile();
		}
	}
	/**
	 * Wraps the regular model test w/ PerformanceMeter measurements.
	 * 
	 * @param testFileLocation
	 * @throws IOException
	 */
	private void doPerfModelTest(String testFileLocation) throws IOException  {
		
		startMeasuring();
		
		doStructuredModelTest(testFileLocation);
		
		stopMeasuring();
		commitMeasurements();
		assertPerformance();
	}
	
	 void doTestStructuredModel_1kfile() throws IOException {
		doPerfModelTest("testfiles/DRTestFiles/1kfile.xml");		
	}

	private void doTestStructuredModel_10kfile() throws IOException {
		doPerfModelTest("testfiles/DRTestFiles/10kfile.xml");
	}

	private void doTestStructuredModel_50kfile() throws IOException {
		doPerfModelTest("testfiles/DRTestFiles/50kfile.xml");
	}

	 void doTestStructuredModel_75kfile() throws IOException {
		doPerfModelTest("testfiles/DRTestFiles/75kfile.xml");
	}

	private void doTestStructuredModel_100kfile() throws IOException {
		doPerfModelTest("testfiles/DRTestFiles/100kfile.xml");
	}

	 void doTestStructuredModel_150kfile() throws IOException {
		doPerfModelTest("testfiles/DRTestFiles/150kfile.xml");
	}

	 void doTestStructuredModel_200kfile() throws IOException {
		doPerfModelTest("testfiles/DRTestFiles/200kfile.xml");
	}

	private void doTestStructuredModel_250kfile() throws IOException {
		doPerfModelTest("testfiles/DRTestFiles/250kfile.xml");
	}

	 void doTestStructuredModel_300kfile() throws IOException {
		doPerfModelTest("testfiles/DRTestFiles/300kfile.xml");
	}

	 void doTestStructuredModel_350kfile() throws IOException {
		doPerfModelTest("testfiles/DRTestFiles/350kfile.xml");
	}

	 void doTestStructuredModel_400kfile() throws IOException {
		doPerfModelTest("testfiles/DRTestFiles/400kfile.xml");
	}

	 void doTestStructuredModel_450kfile() throws IOException {
		doPerfModelTest("testfiles/DRTestFiles/450kfile.xml");
	}

	private void doTestStructuredModel_500kfile() throws IOException {
		doPerfModelTest("testfiles/DRTestFiles/500kfile.xml");
	}

	 void doTestStructuredModel_550kfile() throws IOException {
		doPerfModelTest("testfiles/DRTestFiles/550kfile.xml");
	}

	 void doTestStructuredModel_600kfile() throws IOException {
		doPerfModelTest("testfiles/DRTestFiles/600kfile.xml");
	}

	 void doTestStructuredModel_800kfile() throws IOException {
		doPerfModelTest("testfiles/DRTestFiles/800kfile.xml");
	}

	private void doTestStructuredModel_1000kfile() throws IOException {
		doPerfModelTest("testfiles/DRTestFiles/1000kfile.xml");
	}

	 void doTestStructuredModel_1500kfile() throws IOException {
		doPerfModelTest("testfiles/DRTestFiles/1500kfile.xml");
	}

	private void doTestStructuredModel_2000kfile() throws IOException {
		doPerfModelTest("testfiles/DRTestFiles/2000kfile.xml");
	}

	private void doTestStructuredModel_3000kfile() throws IOException {
		doPerfModelTest("testfiles/DRTestFiles/3000kfile.xml");
	}

	/**
	 * @param args
	 * @return
	 */
	public Object runMain(Object args) {
		Object result = null;
		try {
			testModels();
		}
		catch (IOException e) {
			result = e;
		}
		return result;
	}
}