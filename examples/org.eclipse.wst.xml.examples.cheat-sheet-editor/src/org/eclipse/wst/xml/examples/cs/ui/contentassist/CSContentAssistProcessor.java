/*******************************************************************************
 * Copyright (c) 2001, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jens Lukowski/Innoopract - initial API and implementation
 *     
 *******************************************************************************/
package org.eclipse.wst.xml.examples.cs.ui.contentassist;



import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionList;
import org.eclipse.wst.sse.ui.internal.contentassist.CustomCompletionProposal;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.eclipse.wst.xml.core.internal.regions.DOMRegionContext;
import org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest;
import org.eclipse.wst.xml.ui.internal.contentassist.XMLContentAssistProcessor;
import org.eclipse.wst.xml.ui.internal.contentassist.XMLRelevanceConstants;
import org.eclipse.wst.xml.ui.internal.editor.XMLEditorPluginImageHelper;
import org.eclipse.wst.xml.ui.internal.editor.XMLEditorPluginImages;

public class CSContentAssistProcessor extends XMLContentAssistProcessor implements IPropertyChangeListener {
	private IType[] cachedClasses;

	public CSContentAssistProcessor() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.xml.ui.contentassist.AbstractContentAssistProcessor#addAttributeValueProposals(org.eclipse.wst.xml.ui.contentassist.ContentAssistRequest)
	 */
	protected void addAttributeValueProposals(ContentAssistRequest contentAssistRequest) {
		IDOMNode node = (IDOMNode) contentAssistRequest.getNode();

		// Find the attribute region and name for which this position should
		// have a value proposed
		IStructuredDocumentRegion open = node.getFirstStructuredDocumentRegion();
		ITextRegionList openRegions = open.getRegions();
		int i = openRegions.indexOf(contentAssistRequest.getRegion());
		if (i < 0)
			return;
		ITextRegion nameRegion = null;
		while (i >= 0) {
			nameRegion = openRegions.get(i--);
			if (nameRegion.getType() == DOMRegionContext.XML_TAG_ATTRIBUTE_NAME)
				break;
		}

		String matchString = contentAssistRequest.getMatchString();
		if (matchString == null) {
			matchString = ""; //$NON-NLS-1$
		}
		if (matchString.length() > 0 && (matchString.startsWith("\"") || matchString.startsWith("'"))) {//$NON-NLS-2$//$NON-NLS-1$
			matchString = matchString.substring(1);
		}

		// the name region is REQUIRED to do anything useful
		if (nameRegion != null) {
			String attributeName = open.getText(nameRegion);
			String proposedInfo = "info";

			if ("action".equals(node.getNodeName())) {
				if ("pluginId".equals(attributeName)) {
					// get all registered plugin ids
					String[] ns = Platform.getExtensionRegistry().getNamespaces();
					for (int j = 0; j < ns.length; j++) {
						String pluginId = ns[j];
						if (pluginId.startsWith(matchString)) {
							CustomCompletionProposal proposal = new CustomCompletionProposal("\"" + pluginId + "\"", //$NON-NLS-2$//$NON-NLS-1$
										contentAssistRequest.getReplacementBeginPosition(), contentAssistRequest.getReplacementLength(), pluginId.length() + 1, XMLEditorPluginImageHelper.getInstance().getImage(XMLEditorPluginImages.IMG_OBJ_ATTRIBUTE), "\"" + pluginId + "\"", null, proposedInfo, XMLRelevanceConstants.R_XML_ATTRIBUTE_VALUE);
							contentAssistRequest.addProposal(proposal);
						}
					}
				}
				else if ("class".equals(attributeName)) {
					// get all classes
					IResource resource = getResource(contentAssistRequest);
					if (resource != null) {
						IJavaProject project = JavaCore.create(resource.getProject());
						try {
							IType cheatsheetInterface = project.findType("org.eclipse.jface.action.IAction");
							if (cheatsheetInterface != null) {
								ITypeHierarchy hier = cheatsheetInterface.newTypeHierarchy(project, new NullProgressMonitor());
								IType[] classes = hier.getAllSubtypes(cheatsheetInterface);

								if (classes.length == 0) {
									// nothing has changed, use cached
									// instance instead
									classes = cachedClasses;
								}
								else {
									cachedClasses = classes;
								}
								for (int j = 0; j < classes.length; j++) {
									IType type = classes[j];
									if (!Flags.isAbstract(type.getFlags())) {
										String name = type.getFullyQualifiedName();
										if (name.startsWith(matchString)) {
											CustomCompletionProposal proposal = new CustomCompletionProposal("\"" + name + "\"", //$NON-NLS-2$//$NON-NLS-1$
														contentAssistRequest.getReplacementBeginPosition(), contentAssistRequest.getReplacementLength(), name.length() + 1, XMLEditorPluginImageHelper.getInstance().getImage(XMLEditorPluginImages.IMG_OBJ_ATTRIBUTE), "\"" + name + "\"", null, proposedInfo, XMLRelevanceConstants.R_XML_ATTRIBUTE_VALUE);
											contentAssistRequest.addProposal(proposal);
										}
									}
								}
							}
						}
						catch (CoreException e) {
							e.printStackTrace();
						}
					}
				}
			}
			if (contentAssistRequest != null) {
				super.addAttributeValueProposals(contentAssistRequest);
			}
		}
	}

	/**
	 * Returns project request is in
	 * 
	 * @param request
	 * @return
	 */
	private IResource getResource(ContentAssistRequest request) {
		IResource resource = null;
		String baselocation = null;

		if (request != null) {
			IStructuredDocumentRegion region = request.getDocumentRegion();
			if (region != null) {
				IDocument document = region.getParentDocument();
				IStructuredModel model = null;
				try {
					model = StructuredModelManager.getModelManager().getExistingModelForRead(document);
					if (model != null) {
						baselocation = model.getBaseLocation();
					}
				}
				finally {
					if (model != null)
						model.releaseFromRead();
				}
			}
		}

		if (baselocation != null) {
			// copied from JSPTranslationAdapter#getJavaProject
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IPath filePath = new Path(baselocation);
			if (filePath.segmentCount() > 0) {
				resource = root.getProject(filePath.segment(0));
			}
		}
		return resource;
	}
}
