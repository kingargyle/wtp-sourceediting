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



import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.wst.sse.core.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.text.ITextRegion;
import org.eclipse.wst.sse.core.text.ITextRegionList;
import org.eclipse.wst.sse.ui.internal.contentassist.CustomCompletionProposal;
import org.eclipse.wst.xml.core.document.XMLNode;
import org.eclipse.wst.xml.core.parser.XMLRegionContext;
import org.eclipse.wst.xml.ui.contentassist.ContentAssistRequest;
import org.eclipse.wst.xml.ui.contentassist.XMLContentAssistProcessor;
import org.eclipse.wst.xml.ui.contentassist.XMLRelevanceConstants;
import org.eclipse.wst.xml.ui.internal.editor.XMLEditorPluginImageHelper;
import org.eclipse.wst.xml.ui.internal.editor.XMLEditorPluginImages;

public class CSContentAssistProcessor extends XMLContentAssistProcessor implements IPropertyChangeListener {
	private IEditorPart fEditor;
	private IType[] cachedClasses;
	
	public CSContentAssistProcessor(IEditorPart editor) {
		super();
		fEditor = editor;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.ui.contentassist.AbstractContentAssistProcessor#addAttributeValueProposals(org.eclipse.wst.xml.ui.contentassist.ContentAssistRequest)
	 */
	protected void addAttributeValueProposals(ContentAssistRequest contentAssistRequest) {
		XMLNode node = (XMLNode) contentAssistRequest.getNode();

		// Find the attribute region and name for which this position should have a value proposed
		IStructuredDocumentRegion open = node.getFirstStructuredDocumentRegion();
		ITextRegionList openRegions = open.getRegions();
		int i = openRegions.indexOf(contentAssistRequest.getRegion());
		if (i < 0)
			return;
		ITextRegion nameRegion = null;
		while (i >= 0) {
			nameRegion = openRegions.get(i--);
			if (nameRegion.getType() == XMLRegionContext.XML_TAG_ATTRIBUTE_NAME)
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
									contentAssistRequest.getReplacementBeginPosition(), contentAssistRequest.getReplacementLength(), pluginId.length() + 1, XMLEditorPluginImageHelper.getInstance().getImage(XMLEditorPluginImages.IMG_OBJ_ATTRIBUTE),
									"\""+ pluginId + "\"", null, proposedInfo, XMLRelevanceConstants.R_XML_ATTRIBUTE_VALUE);
							contentAssistRequest.addProposal(proposal);
						}
					}
				} else if ("class".equals(attributeName)) {
					// get all classes
					String ns = "org.eclipse.ui.cheatsheets";
					if (fEditor.getEditorInput() instanceof IFileEditorInput) {
						IFile file = ((IFileEditorInput) fEditor.getEditorInput()).getFile();
						IJavaProject project = JavaCore.create(file.getProject());
						try {
							IType cheatsheetInterface = project.findType("org.eclipse.jface.action.IAction");
							
							if (cheatsheetInterface != null) {
								ITypeHierarchy hier = cheatsheetInterface.newTypeHierarchy(project, new NullProgressMonitor());
								IType[] classes = hier.getAllSubtypes(cheatsheetInterface);
							
								if (classes.length == 0) {
									// nothing has changed, use cached instance instead
									classes = cachedClasses;
								} else {
									cachedClasses = classes;
								}
								for (int j = 0; j < classes.length; j++) {
									IType type = classes[j];
									if (!Flags.isAbstract(type.getFlags())) {
										String name = type.getFullyQualifiedName();
										if (name.startsWith(matchString)) {
											CustomCompletionProposal proposal = new CustomCompletionProposal("\"" + name + "\"", //$NON-NLS-2$//$NON-NLS-1$
													contentAssistRequest.getReplacementBeginPosition(), contentAssistRequest.getReplacementLength(), name.length() + 1, XMLEditorPluginImageHelper.getInstance().getImage(XMLEditorPluginImages.IMG_OBJ_ATTRIBUTE),
													"\"" + name + "\"", null, proposedInfo, XMLRelevanceConstants.R_XML_ATTRIBUTE_VALUE);
											contentAssistRequest.addProposal(proposal);
										}
									}
								}
							}
						} catch (CoreException e) {
							e.printStackTrace();
						}
					}
				}
			}
			if (contentAssistRequest == null) {
				super.addAttributeValueProposals(contentAssistRequest);
			}
		}
	}

}
