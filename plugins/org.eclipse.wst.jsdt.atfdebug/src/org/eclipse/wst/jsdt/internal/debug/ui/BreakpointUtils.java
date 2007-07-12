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
package org.eclipse.wst.jsdt.internal.debug.ui;


import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.wst.jsdt.core.ICompilationUnit;
import org.eclipse.wst.jsdt.core.IField;
import org.eclipse.wst.jsdt.core.IJavaElement;
import org.eclipse.wst.jsdt.core.IMember;
import org.eclipse.wst.jsdt.core.IMethod;
import org.eclipse.wst.jsdt.core.IType;
import org.eclipse.wst.jsdt.core.JavaCore;
import org.eclipse.wst.jsdt.core.JavaModelException;

import org.eclipse.atf.mozilla.ide.debug.model.JSLineBreakpoint;


/**
 * Utility class for Java breakpoints 
 */
public class BreakpointUtils {
	
	
	
	/**
	 * Marker attribute storing the handle id of the 
	 * Java element associated with a Java breakpoint
	 */
	private static final String HANDLE_ID = JDIDebugUIPlugin.getUniqueIdentifier() + ".JAVA_ELEMENT_HANDLE_ID"; //$NON-NLS-1$

	/**
	 * Marker attribute used to denote a run to line breakpoint
	 */
	private static final String RUN_TO_LINE =  JDIDebugUIPlugin.getUniqueIdentifier() + ".run_to_line"; //$NON-NLS-1$
	
	/**
	 * Marker attribute used to denote the start of the region within a Java
	 * member that the breakpoint is located within.
	 */
	private static final String MEMBER_START =  JDIDebugUIPlugin.getUniqueIdentifier() + ".member_start"; //$NON-NLS-1$
	
	/**
	 * Marker attribute used to denote the end of the region within a Java
	 * member that the breakpoint is located within.
	 */
	private static final String MEMBER_END =  JDIDebugUIPlugin.getUniqueIdentifier() + ".member_end"; //$NON-NLS-1$
	
	/**
	 * Returns the resource on which a breakpoint marker should
	 * be created for the given member. The resource returned is the 
	 * associated file, or workspace root in the case of a binary in 
	 * an external archive.
	 * 
	 * @param member member in which a breakpoint is being created
	 * @return resource the resource on which a breakpoint marker
	 *  should be created
	 */
	public static IResource getBreakpointResource(IJavaElement member) {
		ICompilationUnit cu = null;
		if (member instanceof IMember) 
			cu=((IMember)member).getCompilationUnit() ;
		else if (member instanceof ICompilationUnit) 
			cu=(ICompilationUnit)member;
		if (cu != null && cu.isWorkingCopy()) {
			member =  member.getPrimaryElement();
		}
		IResource res = member.getResource();
		if (res == null) {
			res = ResourcesPlugin.getWorkspace().getRoot();
		}
		else if(!res.getProject().exists()) {
			res = ResourcesPlugin.getWorkspace().getRoot();
		}
		return res;
	} 
	
	/**
	 * Adds attributes to the given attribute map:<ul>
	 * <li>Java element handle id</li>
	 * <li>Attributes defined by <code>JavaCore</code></li>
	 * </ul>
	 * 
	 * @param attributes the attribute map to use
	 * @param element the Java element associated with the breakpoint
	 * @exception CoreException if an exception occurs configuring
	 *  the marker
	 */
	public static void addJavaBreakpointAttributes(Map attributes, IJavaElement element) {
		String handleId = element.getHandleIdentifier();
		attributes.put(HANDLE_ID, handleId);
		JavaCore.addJavaElementMarkerAttributes(attributes, element);		
	}
	
	/**
	 * Adds attributes to the given attribute map:<ul>
	 * <li>Java element handle id</li>
	 * <li>Member start position</li>
	 * <li>Member end position</li>
	 * <li>Attributes defined by <code>JavaCore</code></li>
	 * </ul>
	 * 
	 * @param attributes the attribute map to use
	 * @param element the Java element associated with the breakpoint
	 * @param memberStart the start position of the Java member that the breakpoint is positioned within
	 * @param memberEnd the end position of the Java member that the breakpoint is positioned within
	 * @exception CoreException if an exception occurs configuring
	 *  the marker
	 */
	public static void addJavaBreakpointAttributesWithMemberDetails(Map attributes, IJavaElement element, int memberStart, int memberEnd) {
		addJavaBreakpointAttributes(attributes, element);
		attributes.put(MEMBER_START, new Integer(memberStart));
		attributes.put(MEMBER_END, new Integer(memberEnd));
	}
	
	/**
	 * Adds attributes to the given attribute map to make the
	 * breakpoint a run-to-line breakpoint:<ul>
	 * <li>PERSISTED = false</li>
	 * <li>RUN_TO_LINE = true</li>
	 * </ul>
	 * 
	 * @param attributes the attribute map to use
	 * @param element the Java element associated with the breakpoint
	 * @exception CoreException if an exception occurs configuring
	 *  the marker
	 */
	public static void addRunToLineAttributes(Map attributes) {
		attributes.put(IBreakpoint.PERSISTED, Boolean.FALSE);
		attributes.put(RUN_TO_LINE, Boolean.TRUE);
	}	
	
	/**
	 * Returns whether the given breakpoint is a run to line
	 * breakpoint
	 * 
	 * @param breakpoint line breakpoint
	 * @return whether the given breakpoint is a run to line
	 *  breakpoint
	 */
	public static boolean isRunToLineBreakpoint(JSLineBreakpoint breakpoint) {
		return breakpoint.getMarker().getAttribute(RUN_TO_LINE, false);
	}

	public static boolean doesBreakpointExist(IResource res, int lineNumber) {
	IBreakpointManager manager = DebugPlugin.getDefault().getBreakpointManager();
	IBreakpoint[] breakpoints = manager.getBreakpoints();
	for (int i = 0; i < breakpoints.length; i++) {
		if (!(breakpoints[i] instanceof JSLineBreakpoint))
			continue;
		JSLineBreakpoint breakpoint = (JSLineBreakpoint) breakpoints[i];
		try {
			if (breakpoint.getMarker().getResource().equals(res) && breakpoint.getLineNumber() == lineNumber) {
				return true;
			}
		}
		catch (CoreException e) {
			return true;
		}
	}
	return false;
}

	public static  JSLineBreakpoint lineBreakpointExists(IResource resource, String typeName, int lineNumber)
	{
		IBreakpointManager manager = DebugPlugin.getDefault().getBreakpointManager();
		IBreakpoint[] breakpoints = manager.getBreakpoints();
		for (int i = 0; i < breakpoints.length; i++) {
			if (!(breakpoints[i] instanceof JSLineBreakpoint))
				continue;
			JSLineBreakpoint breakpoint = (JSLineBreakpoint) breakpoints[i];
			try {
				if (breakpoint.getMarker().getResource().equals(resource) && breakpoint.getLineNumber() == lineNumber) {
					return breakpoint;
				}
			}
			catch (CoreException e) {
				return null;
			}
		}
		return null;
	}

	public static JSLineBreakpoint createLineBreakpoint(IDocument document, IEditorInput input,
			int lineNumber, int charStart, int charEnd, int hitCount,
			boolean register, Map attributes) {
		IResource resource = (IResource) input.getAdapter(IFile.class);
		if (resource == null) {
			resource = ResourcesPlugin.getWorkspace().getRoot();
		}
		if (resource != null && input instanceof IFileEditorInput) {
			return new JSLineBreakpoint(resource, lineNumber, -1, -1, register, attributes);
		} else if (input instanceof IStorageEditorInput) {
			resource = ResourcesPlugin.getWorkspace().getRoot();
			String id = input.getName();
			attributes.put("org.eclipse.wst.sse.ui.extensions.breakpoint.path", id);
			return new JSLineBreakpoint(resource, lineNumber, -1, -1, register, attributes);
		}
		return null;
	}
	
}
