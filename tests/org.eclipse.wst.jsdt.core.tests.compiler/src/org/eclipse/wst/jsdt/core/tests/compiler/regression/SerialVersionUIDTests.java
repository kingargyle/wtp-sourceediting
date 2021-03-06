/*******************************************************************************
 * Copyright (c) 2006, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
/*
 * Here we focus on various aspects of the runtime behavior of the generated 
 * code.
 */
package org.eclipse.wst.jsdt.core.tests.compiler.regression;

import java.util.Map;

import junit.framework.Test;

public class SerialVersionUIDTests extends AbstractRegressionTest {

public SerialVersionUIDTests(String name) {
	super(name);
}
// Static initializer to specify tests subset using TESTS_* static variables
// All specified tests which does not belong to the class are skipped...
// Only the highest compliance level is run; add the VM argument
// -Dcompliance=1.4 (for example) to lower it if needed
static {
//		TESTS_NAMES = new String[] { "test0001" };
//	 	TESTS_NUMBERS = new int[] { 1 };   
//		TESTS_RANGE = new int[] { 1, -1 }; 
}
public static Test suite() {
	return buildAllCompliancesTestSuite(testClass());
}
public static Class testClass() {
	return SerialVersionUIDTests.class;
}
protected Map getCompilerOptions() {
	Map options = super.getCompilerOptions();
//	options.put(CompilerOptions.OPTION_ReportMissingSerialVersion, CompilerOptions.ERROR);
	return options;
}
// https://bugs.eclipse.org/bugs/show_bug.cgi?id=101476
public void test001() {
	this.runConformTest(
		new String[] {
			"X.java",
			"import java.io.Serializable;\n" + 
			"\n" + 
			"public class X implements Serializable {\n" + 
			"	private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException {}\n" + 
			"	private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException {}\n" + 
			"}"
		},
		""
	);
}
//https://bugs.eclipse.org/bugs/show_bug.cgi?id=101476
public void test002() {
	this.runConformTest(
		new String[] {
			"X.java",
			"public class X implements java.io.Externalizable {\n" + 
			"	public void writeExternal(java.io.ObjectOutput out) throws java.io.IOException {}\n" + 
			"	public void readExternal(java.io.ObjectInput in) throws java.io.IOException, ClassNotFoundException {}\n" + 
			"}"
		},
		""
	);
}
//https://bugs.eclipse.org/bugs/show_bug.cgi?id=101476
public void test003() {
	this.runNegativeTest(
		new String[] {
			"X.java",
			"public class X implements java.io.Serializable {\n" + 
			"	private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException {}\n" + 
			"}"
		},
		"----------\n" + 
		"1. ERROR in X.java (at line 1)\n" + 
		"	public class X implements java.io.Serializable {\n" + 
		"	             ^\n" + 
		"The serializable class X does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n"
	);
}
//https://bugs.eclipse.org/bugs/show_bug.cgi?id=101476
public void test004() {
	this.runNegativeTest(
		new String[] {
			"X.java",
			"public class X implements java.io.Serializable {\n" + 
			"	private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException {}\n" + 
			"}"
		},
		"----------\n" + 
		"1. ERROR in X.java (at line 1)\n" + 
		"	public class X implements java.io.Serializable {\n" + 
		"	             ^\n" + 
		"The serializable class X does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n"
	);
}
//https://bugs.eclipse.org/bugs/show_bug.cgi?id=101476
public void test005() {
	this.runNegativeTest(
		new String[] {
			"X.java",
			"public class X implements java.io.Serializable {\n" + 
			"}"
		},
		"----------\n" + 
		"1. ERROR in X.java (at line 1)\n" + 
		"	public class X implements java.io.Serializable {\n" + 
		"	             ^\n" + 
		"The serializable class X does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n"
	);
}
//https://bugs.eclipse.org/bugs/show_bug.cgi?id=101476
public void test006() {
	this.runConformTest(
		new String[] {
			"X.java",
			"public class X implements java.io.Serializable {\n" + 
			"	Object writeReplace() throws java.io.ObjectStreamException { return null;}\n" + 
			"}"
		},
		""
	);
}
}
