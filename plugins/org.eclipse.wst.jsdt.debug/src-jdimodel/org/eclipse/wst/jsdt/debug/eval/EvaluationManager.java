/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.jsdt.debug.eval;

 
import java.io.File;

import org.eclipse.jsdt.core.IJavaProject;
import org.eclipse.wst.jsdt.debug.core.IJavaDebugTarget;
import org.eclipse.wst.jsdt.internal.debug.eval.LocalEvaluationEngine;
import org.eclipse.wst.jsdt.internal.debug.eval.ast.engine.ASTEvaluationEngine;

/**
 * The evaluation manager provides factory methods for
 * creating evaluation engines.
 * <p>
 * Clients are not intended subclass or instantiate this
 * class.
 * </p>
 * @see org.eclipse.wst.jsdt.debug.eval.IEvaluationEngine
 * @see org.eclipse.wst.jsdt.debug.eval.IClassFileEvaluationEngine
 * @see org.eclipse.wst.jsdt.debug.eval.IAstEvaluationEngine
 * @see org.eclipse.wst.jsdt.debug.eval.IEvaluationResult
 * @see org.eclipse.wst.jsdt.debug.eval.IEvaluationListener
 * @since 2.0
 */
public class EvaluationManager {
		
	/**
	 * Not to be instantiated
	 */
	private EvaluationManager() {
	}
				
	/**
	 * Creates and returns a new evaluation engine that
	 * performs evaluations for local Java applications
	 * by deploying class files.
	 * 
	 * @param project the Java project in which expressions
	 *  are to be compiled
	 * @param target the Java debug target in which expressions
	 *  are to be evaluated
	 * @param directory the directory where support class files
	 *  are deployed to assist in the evaluation. The directory
	 *  must exist.
	 * @return an evaluation engine
	 */
	public static IClassFileEvaluationEngine newClassFileEvaluationEngine(IJavaProject project, IJavaDebugTarget target, File directory) {
		return new LocalEvaluationEngine(project, target, directory);
	}
	 
	/**
	 * Creates and returns a new evaluation engine that performs evaluations by
	 * compiling expressions into abstract syntax trees (ASTs), and interpreting
	 * the AST over a JDI connection. This type of evaluation engine is capable of
	 * performing remote evaluations.
	 * 
	 * @param project the Java project in which expressions are to be compiled
	 * @param target the Java debug target in which expressions are to be evaluated
	 * @return an evaluation engine
	 */
	public static IAstEvaluationEngine newAstEvaluationEngine(IJavaProject project, IJavaDebugTarget target) {
		return new ASTEvaluationEngine(project, target);
	}
}

