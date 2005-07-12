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
package org.eclipse.wst.javascript.ui.internal.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewerExtension2;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.editors.text.FileDocumentProvider;
import org.eclipse.ui.editors.text.StorageDocumentProvider;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.ide.IDEActionFactory;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.ui.texteditor.ChainedPreferenceStore;
import org.eclipse.ui.texteditor.DefaultRangeIndicator;
import org.eclipse.ui.texteditor.IAbstractTextEditorHelpContextIds;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IElementStateListener;
import org.eclipse.ui.texteditor.IStatusField;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;
import org.eclipse.ui.texteditor.ResourceAction;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;
import org.eclipse.ui.texteditor.TextOperationAction;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.wst.javascript.core.internal.contenttype.ContentTypeIdForJavaScript;
import org.eclipse.wst.javascript.ui.internal.common.JSSourceViewerConfiguration;
import org.eclipse.wst.javascript.ui.internal.views.contentoutline.JSContentOutlinePage;
import org.eclipse.wst.sse.core.internal.encoding.CommonEncodingPreferenceNames;
import org.eclipse.wst.sse.core.internal.encoding.ContentBasedPreferenceGateway;
import org.eclipse.wst.sse.core.internal.provisional.exceptions.SourceEditingRuntimeException;
import org.eclipse.wst.sse.core.internal.util.StringUtils;
import org.eclipse.wst.sse.ui.internal.ExtendedConfigurationBuilder;
import org.eclipse.wst.sse.ui.internal.ExtendedEditorActionBuilder;
import org.eclipse.wst.sse.ui.internal.ExtendedEditorDropTargetAdapter;
import org.eclipse.wst.sse.ui.internal.IExtendedContributor;
import org.eclipse.wst.sse.ui.internal.IPopupMenuContributor;
import org.eclipse.wst.sse.ui.internal.SSEUIPlugin;
import org.eclipse.wst.sse.ui.internal.StructuredResourceMarkerAnnotationModel;
import org.eclipse.wst.sse.ui.internal.actions.ActionDefinitionIds;
import org.eclipse.wst.sse.ui.internal.actions.StructuredTextEditorActionConstants;
import org.eclipse.wst.sse.ui.internal.debug.BreakpointRulerAction;
import org.eclipse.wst.sse.ui.internal.debug.EditBreakpointAction;
import org.eclipse.wst.sse.ui.internal.debug.ManageBreakpointAction;
import org.eclipse.wst.sse.ui.internal.debug.ToggleBreakpointAction;
import org.eclipse.wst.sse.ui.internal.debug.ToggleBreakpointsTarget;
import org.eclipse.wst.sse.ui.internal.editor.IHelpContextIds;
import org.eclipse.wst.sse.ui.internal.extension.BreakpointProviderBuilder;
import org.eclipse.wst.sse.ui.internal.preferences.EditorPreferenceNames;
import org.eclipse.wst.sse.ui.internal.provisional.extensions.ConfigurationPointCalculator;
import org.eclipse.wst.sse.ui.internal.provisional.extensions.ISourceEditingTextTools;
import org.eclipse.wst.sse.ui.internal.provisional.extensions.breakpoint.IExtendedStorageEditorInput;

public class JSEditor extends TextEditor {
	// local adapter
	private class ShowInTargetLister implements IShowInTargetList {
		public String[] getShowInTargetIds() {
			return fShowInTargetIds;
		}
	}

	private class SourceEditingTextTools implements ISourceEditingTextTools {

		public int getCaretOffset() {
			return 0;
		}

		public IDocument getDocument() {
			return getDocumentProvider().getDocument(getEditorInput());
		}

		public IEditorPart getEditorPart() {
			return JSEditor.this.getEditorPart();
		}

		public ITextSelection getSelection() {
			ITextSelection selection = null;
			ISelection s = getSelectionProvider().getSelection();
			if (s instanceof ITextSelection) {
				selection = (ITextSelection) s;
			}
			else {
				selection = TextSelection.emptySelection();
			}
			return selection;
		}
	}

	// DocumentProvider for StorageEditorInputs - supports
	// IExtendedStorageEditorInput notifications and custom
	// ResourceAnnotationModels
	class StorageInputDocumentProvider extends StorageDocumentProvider implements IElementStateListener {
		protected IAnnotationModel createAnnotationModel(Object element) throws CoreException {
			IAnnotationModel model = null;
			IStorageEditorInput storageInput = (IStorageEditorInput) element;
			String contentType = getInputContentType(element);
			String ext = BreakpointRulerAction.getFileExtension(storageInput);
			IResource res = BreakpointProviderBuilder.getInstance().getResource(storageInput, contentType, ext);
			String id = storageInput.getName();
			if (storageInput.getStorage() != null)
				id = storageInput.getStorage().getFullPath().toString();
			if (res != null)
				model = new StructuredResourceMarkerAnnotationModel(res, id);
			else
				model = new StructuredResourceMarkerAnnotationModel(ResourcesPlugin.getWorkspace().getRoot(), id);

			return model;
		}

		protected ElementInfo createElementInfo(Object element) throws CoreException {
			if (element instanceof IExtendedStorageEditorInput) {
				((IExtendedStorageEditorInput) element).addElementStateListener(this);
			}
			return super.createElementInfo(element);
		}

		protected void disposeElementInfo(Object element, ElementInfo info) {
			if (element instanceof IExtendedStorageEditorInput) {
				((IExtendedStorageEditorInput) element).removeElementStateListener(this);
			}
			super.disposeElementInfo(element, info);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.ui.editors.text.StorageDocumentProvider#doSaveDocument(org.eclipse.core.runtime.IProgressMonitor,
		 *      java.lang.Object, org.eclipse.jface.text.IDocument, boolean)
		 */
		protected void doSaveDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite) throws CoreException {
			// untested
			new FileDocumentProvider().saveDocument(monitor, element, document, overwrite);
		}

		public void elementContentAboutToBeReplaced(Object element) {
			fireElementContentAboutToBeReplaced(element);
		}

		public void elementContentReplaced(Object element) {
			fireElementContentReplaced(element);
		}

		public void elementDeleted(Object element) {
			fireElementDeleted(element);
		}

		public void elementDirtyStateChanged(Object element, boolean isDirty) {
			fireElementDirtyStateChanged(element, isDirty);
		}

		public void elementMoved(Object originalElement, Object movedElement) {
			fireElementMoved(originalElement, movedElement);
		}
	}

	/** This editor's supported brackets */
	protected final static char[] BRACKETS = {'{', '}', '(', ')', '[', ']'};

	/**
	 * Constant for representing an error status. This is considered a value
	 * object.
	 */
	static final protected IStatus STATUS_ERROR = new Status(IStatus.ERROR, "JSEditorPlugin.ID", IStatus.INFO, "ERROR", null); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * Constant for representing an ok status. This is considered a value
	 * object.
	 */
	static final protected IStatus STATUS_OK = new Status(IStatus.OK, "JSEditorPlugin.ID", IStatus.OK, "OK", null); //$NON-NLS-1$ //$NON-NLS-2$

	/** This editor's bracket matcher */
	protected JavaPairMatcher fBracketMatcher = new JavaPairMatcher(BRACKETS);
	private JSContentOutlinePage fContentOutlinePage = null;
	private IEditorPart fEditorPart = null;
	private JSLineStyleListener fLineStyleListener = null;

	String[] fShowInTargetIds = new String[]{IPageLayout.ID_RES_NAV};
	private IShowInTargetList fShowInTargetListAdapter = new ShowInTargetLister();
	private ISourceEditingTextTools fSourceEditingTextTools = new SourceEditingTextTools();

	IDocumentProvider fStorageInputDocumentProvider = null;
	private static final String UNDERSCORE = "_"; //$NON-NLS-1$

	protected void addContextMenuActions(IMenuManager menu) {
	}

	protected void addExtendedContextMenuActions(IMenuManager menu) {
		IEditorActionBarContributor c = getEditorSite().getActionBarContributor();
		if (c instanceof IPopupMenuContributor) {
			((IPopupMenuContributor) c).contributeToPopupMenu(menu);
		}
		else {
			ExtendedEditorActionBuilder builder = new ExtendedEditorActionBuilder();
			IExtendedContributor pmc = builder.readActionExtensions(getConfigurationPoints());
			if (pmc != null) {
				pmc.setActiveEditor(this);
				pmc.contributeToPopupMenu(menu);
			}
		}
	}

	/**
	 * Instead of us closing directly, we have to close with our containing
	 * (multipage) editor, if it exists.
	 */
	public void close(final boolean save) {

		if (getEditorPart() != null) {
			Display display = getSite().getShell().getDisplay();

			display.asyncExec(new Runnable() {
				public void run() {
					getSite().getPage().closeEditor(getEditorPart(), save);
				}
			});
		}
		else {
			super.close(save);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.ExtendedTextEditor#configureSourceViewerDecorationSupport(org.eclipse.ui.texteditor.SourceViewerDecorationSupport)
	 */
	protected void configureSourceViewerDecorationSupport(SourceViewerDecorationSupport support) {
		support.setCharacterPairMatcher(fBracketMatcher);
		support.setMatchingCharacterPainterPreferenceKeys(EditorPreferenceNames.MATCHING_BRACKETS, EditorPreferenceNames.MATCHING_BRACKETS_COLOR);
		// pa_TODO we should inherit values from either the text pref page or
		// java pref page for annotations
		super.configureSourceViewerDecorationSupport(support);
	}

	protected void createActions() {
		super.createActions();

		// override the cut/paste/delete action to make them run on read-only
		// files
		ResourceAction action = new TextOperationAction(JavaScriptUIMessages.getResourceBundle(), "Editor_Cut_", this, ITextOperationTarget.CUT, true); //$NON-NLS-1$
		action.setHelpContextId(IAbstractTextEditorHelpContextIds.CUT_ACTION);
		action.setActionDefinitionId(IWorkbenchActionDefinitionIds.CUT);
		setAction(ITextEditorActionConstants.CUT, action);

		action = new TextOperationAction(JavaScriptUIMessages.getResourceBundle(), "Editor_Paste_", this, ITextOperationTarget.PASTE, true); //$NON-NLS-1$
		action.setHelpContextId(IAbstractTextEditorHelpContextIds.PASTE_ACTION);
		action.setActionDefinitionId(IWorkbenchActionDefinitionIds.PASTE);
		setAction(ITextEditorActionConstants.PASTE, action);

		action = new TextOperationAction(JavaScriptUIMessages.getResourceBundle(), "Editor_Delete_", this, ITextOperationTarget.DELETE, true); //$NON-NLS-1$
		action.setHelpContextId(IAbstractTextEditorHelpContextIds.DELETE_ACTION);
		action.setActionDefinitionId(IWorkbenchActionDefinitionIds.DELETE);
		setAction(ITextEditorActionConstants.DELETE, action);

		try {
			ResourceBundle resourceBundle = JavaScriptUIMessages.getResourceBundle();

			// override TextEditor's SAVE action
			// we duplicate the "Save" label, but that's better than depending
			// on private methods
			// MultiPageEditorSaveAction saveAction = new
			// MultiPageEditorSaveAction(resourceBundle,
			// JSEditorActionConstants.ACTION_NAME_SAVE +
			// JSEditorActionConstants.DOT, this);
			// if (getEditorPart() != null)
			// saveAction.setEditorPart(getEditorPart());
			// setAction(ITextEditorActionConstants.SAVE, saveAction);

			IAction contentAssistAction = new TextOperationAction(resourceBundle, JSEditorActionConstants.ACTION_NAME_CONTENT_ASSIST_PROPOSAL + UNDERSCORE, this, ISourceViewer.CONTENTASSIST_PROPOSALS, true);
			IWorkbenchHelpSystem helpSystem = JSEditorPlugin.getDefault().getWorkbench().getHelpSystem();
			helpSystem.setHelp(contentAssistAction, IHelpContextIds.CONTMNU_CONTENTASSIST_HELPID);
			contentAssistAction.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
			setAction(JSEditorActionConstants.ACTION_NAME_CONTENT_ASSIST_PROPOSAL, contentAssistAction);

			// StructuredTextEditor Action - add breakpoints
			IAction breakpointAction = new ToggleBreakpointAction(this, getVerticalRuler());
			setAction(ActionDefinitionIds.TOGGLE_BREAKPOINTS, breakpointAction);
			// StructuredTextEditor Action - manage breakpoints
			breakpointAction = new ManageBreakpointAction(this, getVerticalRuler());
			setAction(ActionDefinitionIds.MANAGE_BREAKPOINTS, breakpointAction);
			// StructuredTextEditor Action - edit breakpoints
			breakpointAction = new EditBreakpointAction(this, getVerticalRuler());
			setAction(ActionDefinitionIds.EDIT_BREAKPOINTS, breakpointAction);

			// override ruler double click action
			String ext = BreakpointRulerAction.getFileExtension(getEditorInput());
			if (BreakpointProviderBuilder.getInstance().isAvailable(getInputContentType(getEditorInput()), ext)) {
				setAction(ITextEditorActionConstants.RULER_DOUBLE_CLICK, getAction(ActionDefinitionIds.TOGGLE_BREAKPOINTS));
			}
			else {
				// The Default Text Editor uses editorContribution to perform
				// this
				// mapping, but since it relies on the IEditorSite ID, it
				// can't be
				// relied on for MultiPageEditorParts. Instead, force the
				// action
				// registration manually.
//				setAction(ITextEditorActionConstants.RULER_DOUBLE_CLICK, new MarkerRulerAction(JavaScriptUIMessages.getResourceBundle(), "Editor.ManageBookmarks.", this, getVerticalRuler(), IMarker.BOOKMARK, true)); //$NON-NLS-1$
				// add bookmark action is already registered in AbstractDecoratedTextEditor, so just get it
				setAction(ITextEditorActionConstants.RULER_DOUBLE_CLICK, getAction(IDEActionFactory.BOOKMARK.getId()));
			}


			// action= new
			// TextOperationAction(ResourceHandler.getResourceBundle(),
			// JSEditorActionConstants.ACTION_NAME_INFORMATION +
			// JSEditorActionConstants.DOT, this, ISourceViewer.INFORMATION,
			// true);
			// action.setActionDefinitionId(XMLEditorActionDefinitionIds.INFORMATION);
			// setAction(JSEditorActionConstants.ACTION_NAME_INFORMATION,
			// action); //$NON-NLS-1$

			// For error handling test only!!!==========
			// Uncomment the following line of code to simulate a
			// MissingResourceException.
			// throw new java.util.MissingResourceException("Resource bundle
			// not found.", resourceBundle.getClass().toString(),
			// ACTION_NAME_CONTENT_ASSIST_PROPOSAL);
			// For error handling test only!!!==========
		}
		catch (MissingResourceException exception) {
			throw new SourceEditingRuntimeException(exception, JavaScriptUIMessages.An_error_has_occurred_when_ERROR_); //$NON-NLS-1$ = ...
			// ... "An error has occurred when retrieving resources for the
			// source editor. The Eclipse Workbench installation may have been
			// corrupted."
		}
	}

	/**
	 * Create a preference store that combines the source editor preferences
	 * with the base editor's preferences.
	 * 
	 * @return IPreferenceStore
	 */
	private IPreferenceStore createCombinedPreferenceStore() {
		IPreferenceStore sseEditorPrefs = SSEUIPlugin.getDefault().getPreferenceStore();
		IPreferenceStore baseEditorPrefs = EditorsUI.getPreferenceStore();
		return new ChainedPreferenceStore(new IPreferenceStore[]{sseEditorPrefs, baseEditorPrefs});
	}

	/**
	 * Loads the Show In Target IDs from the Extended Configuration extension
	 * point.
	 * 
	 */
	protected String[] createShowInTargetIds() {
		List allIds = new ArrayList(0);
		ExtendedConfigurationBuilder builder = ExtendedConfigurationBuilder.getInstance();
		String[] configurationIds = getConfigurationPoints();
		for (int i = 0; i < configurationIds.length; i++) {
			String[] definitions = builder.getDefinitions("showintarget", configurationIds[i]); //$NON-NLS-1$
			for (int j = 0; j < definitions.length; j++) {
				String someIds = definitions[j];
				if (someIds != null && someIds.length() > 0) {
					String[] ids = StringUtils.unpack(someIds);
					for (int k = 0; k < ids.length; k++) {
						// trim, just to keep things clean
						String id = ids[k].trim();
						if (!allIds.contains(id)) {
							allIds.add(id);
						}
					}
				}
			}
		}

		if (!allIds.contains(IPageLayout.ID_RES_NAV)) {
			allIds.add(IPageLayout.ID_RES_NAV);
		}
		return (String[]) allIds.toArray(new String[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.AbstractDecoratedTextEditor#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		initializeDrop(getSourceViewer().getTextWidget());
	}

	protected ISourceViewer createSourceViewer(Composite parent, IVerticalRuler verticalRuler, int styles) {
		fAnnotationAccess = createAnnotationAccess();
		fOverviewRuler = createOverviewRuler(getSharedColors());

		// create source viewer
		SourceViewer sourceViewer = new SourceViewer(parent, verticalRuler, fOverviewRuler, true, styles);

		// setup line style listener
		JSLineStyleListener lineStyleListener = getLineStyleListener();
		if (lineStyleListener != null) {
			lineStyleListener.setSourceViewer(sourceViewer);
			StyledText textWidget = sourceViewer.getTextWidget();
			textWidget.addLineStyleListener(lineStyleListener);
		}

		// set source viewer configuration
		setSourceViewerConfiguration();

		// ensure source viewer decoration support has been created and
		// configured
		getSourceViewerDecorationSupport(sourceViewer);

		// return source viewer
		return sourceViewer;
	}

	public void dispose() {
		JSLineStyleListener lineStyleListener = getLineStyleListener();
		if (lineStyleListener != null) {
			lineStyleListener.dispose();
			lineStyleListener = null;
		}

		super.dispose();
	}

	public void doSave(IProgressMonitor progressMonitor) {
		handleConvertLineDelimiters();

		super.doSave(progressMonitor);
	}

	public void doSaveAs() {
		handleConvertLineDelimiters();

		super.doSaveAs();
	}

	protected void doSetInput(IEditorInput input) throws CoreException {
		// must release caches, etc., before setting new input
		StyledText textWidget = null;
		if (getSourceViewer() != null)
			textWidget = getSourceViewer().getTextWidget();

		if (fLineStyleListener != null) {
			if (textWidget != null)
				textWidget.removeLineStyleListener(fLineStyleListener);
			fLineStyleListener.dispose();
			fLineStyleListener = null;
		}

		try {
			super.doSetInput(input);

			// currently this only works if createpartcontrol has not been
			// called yet
			String contentType = getInputContentType(input);
			setEditorContextMenuId(contentType + ".source.EditorContext"); //$NON-NLS-1$
			setRulerContextMenuId(contentType + ".source.RulerContext"); //$NON-NLS-1$
			setHelpContextId(contentType + ".source.HelpId"); //$NON-NLS-1$
			// allows help to be set at any time (not just on
			// AbstractTextEditor's
			// creation)
			if ((getHelpContextId() != null) && (getSourceViewer() != null) && (getSourceViewer().getTextWidget() != null)) {
				IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();
				helpSystem.setHelp(getSourceViewer().getTextWidget(), getHelpContextId());
			}

			JSLineStyleListener lineStyleListener = getLineStyleListener();
			if (lineStyleListener != null) {
				if (textWidget != null)
					textWidget.addLineStyleListener(lineStyleListener);
			}

			if (fContentOutlinePage != null)
				fContentOutlinePage.setDocument(getDocumentProvider().getDocument(getEditorInput()));

			fShowInTargetIds = createShowInTargetIds();
		}
		catch (CoreException exception) {
			// dispose editor
			dispose();

			throw new CoreException(exception.getStatus());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.AbstractTextEditor#editorContextMenuAboutToShow(org.eclipse.jface.action.IMenuManager)
	 */
	protected void editorContextMenuAboutToShow(IMenuManager menu) {
		super.editorContextMenuAboutToShow(menu);

		addContextMenuActions(menu);
		addExtendedContextMenuActions(menu);
	}

	/**
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class required) {
		// content outline page
		if (IContentOutlinePage.class.equals(required))
			return getContentOutlinePage();
		// Navigate action set menu
		if (IShowInTargetList.class.equals(required))
			return fShowInTargetListAdapter;
		if (IToggleBreakpointsTarget.class.equals(required)) {
			return ToggleBreakpointsTarget.getInstance();
		}
		if (IDocument.class.equals(required)) {
			return getDocumentProvider().getDocument(getEditorInput());
		}
		if (ISourceEditingTextTools.class.equals(required)) {
			return fSourceEditingTextTools;
		}
		return super.getAdapter(required);
	}

	protected String[] getConfigurationPoints() {
		return ConfigurationPointCalculator.getConfigurationPoints(this, getInputContentType(getEditorInput()), ConfigurationPointCalculator.SOURCE, getClass());
	}

	protected IContentOutlinePage getContentOutlinePage() {
		if ((fContentOutlinePage == null) || (fContentOutlinePage.getControl().isDisposed())) {
			IDocument document = getDocumentProvider().getDocument(getEditorInput());
			fContentOutlinePage = new JSContentOutlinePage(document, getSourceViewer());
		}
		return fContentOutlinePage;
	}

	IEditorPart getEditorPart() {
		// unless one has been explicitly set, this is the EditorPart
		if (fEditorPart == null)
			return this;
		return fEditorPart;
	}

	/**
	 * Return the Content Type ID of the input - hardcode for now
	 * 
	 * @param element
	 */
	String getInputContentType(Object element) {
		return ContentTypeIdForJavaScript.ContentTypeID_JAVASCRIPT; //$NON-NLS-1$
	}

	protected JSLineStyleListener getLineStyleListener() {
		if (fLineStyleListener == null) {
			IDocument document = getDocumentProvider().getDocument(getEditorInput());
			fLineStyleListener = new JSLineStyleListener(document, getSourceViewer());
		}
		return fLineStyleListener;
	}

	public ISourceViewer getViewer() {
		return getSourceViewer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.AbstractTextEditor#handlePreferenceStoreChanged(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	protected void handlePreferenceStoreChanged(PropertyChangeEvent event) {
		String property = event.getProperty();
		if (EditorPreferenceNames.EDITOR_TEXT_HOVER_MODIFIERS.equals(property)) {
			updateHoverBehavior();
		}
		super.handlePreferenceStoreChanged(event);
	}

	protected void initializeDrop(Control control) {
		// add drop support
		int operations = DND.DROP_COPY | DND.DROP_MOVE;
		DropTarget dropTarget = new DropTarget(control, operations);

		ExtendedEditorDropTargetAdapter dropTargetAdapter = new ExtendedEditorDropTargetAdapter();
		dropTargetAdapter.setTargetEditor(this);
		dropTargetAdapter.setTargetIDs(getConfigurationPoints());
		dropTargetAdapter.setTextViewer(getSourceViewer());

		dropTarget.setTransfer(dropTargetAdapter.getTransfers());
		dropTarget.addDropListener(dropTargetAdapter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.AbstractDecoratedTextEditor#initializeEditor()
	 */
	protected void initializeEditor() {

		// https://w3.opensource.ibm.com/bugzilla/show_bug.cgi?id=3426
		// setDocumentProvider(new JSTextFileDocumentProvider());
		setRangeIndicator(new DefaultRangeIndicator());
		setHelpContextId(org.eclipse.wst.javascript.ui.internal.common.IHelpContextIds.JS_SOURCEVIEW_HELPID);
		setPreferenceStore(createCombinedPreferenceStore());
		// getCommonPreferenceStore().addPropertyChangeListener(this);

		// enable the base source editor activity when editor opens
		// try {
		// WTPActivityBridge.getInstance().enableActivity(StructuredTextEditor.CORE_SSE_ACTIVITY_ID,
		// true);
		// }
		// catch (Exception t) {
		// // if something goes wrong with enabling activity, just log the
		// // error but dont
		// // have it break the editor
		// Logger.log(Logger.WARNING_DEBUG, t.getMessage(), t);
		// }
	}

	public boolean isEditable() {
		return true;
	}

	protected void rulerContextMenuAboutToShow(IMenuManager menu) {
		menu.add(getAction(ActionDefinitionIds.TOGGLE_BREAKPOINTS));
		menu.add(getAction(ActionDefinitionIds.MANAGE_BREAKPOINTS));
		menu.add(getAction(ActionDefinitionIds.EDIT_BREAKPOINTS));
		menu.add(new Separator());
		super.rulerContextMenuAboutToShow(menu);
	}

	/**
	 * Checks the state of the given editor input if sanity checking is
	 * enabled.
	 * 
	 * @param input
	 *            the editor input whose state is to be checked
	 * @see 2.0
	 */
	protected void sanityCheckState(IEditorInput input) {
		super.sanityCheckState(input);
	}

	/**
	 * Ensure that the correct IDocumentProvider is used. For IFile and Files,
	 * the default provider with a specified AnnotationModelFactory is used.
	 * For StorageEditorInputs, use a custom provider that creates a usable
	 * ResourceAnnotationModel
	 * 
	 * @see org.eclipse.ui.texteditor.AbstractDecoratedTextEditor#setDocumentProvider(org.eclipse.ui.IEditorInput)
	 */
	protected void setDocumentProvider(IEditorInput input) {
		if (input instanceof IStorageEditorInput && !(input instanceof IFileEditorInput)) {
			if (fStorageInputDocumentProvider == null) {
				fStorageInputDocumentProvider = new StorageInputDocumentProvider();
			}
			setDocumentProvider(fStorageInputDocumentProvider);
		}
		else {
			super.setDocumentProvider(input);
		}
	}

	public void setEditorPart(IEditorPart editorPart) {
		fEditorPart = editorPart;
	}

	protected void setSourceViewerConfiguration() {
		SourceViewerConfiguration configuration = super.getSourceViewerConfiguration();

		if ((configuration == null) || !(configuration instanceof JSSourceViewerConfiguration)) {
			configuration = new JSEditorSourceViewerConfiguration(getPreferenceStore());
			setSourceViewerConfiguration(configuration);
		}
	}

	/*
	 * Update the hovering behavior depending on the preferences.
	 */
	private void updateHoverBehavior() {
		SourceViewerConfiguration configuration = getSourceViewerConfiguration();
		String[] types = configuration.getConfiguredContentTypes(getSourceViewer());

		for (int i = 0; i < types.length; i++) {

			String t = types[i];

			ISourceViewer sourceViewer = getSourceViewer();
			if (sourceViewer instanceof ITextViewerExtension2) {
				// Remove existing hovers
				((ITextViewerExtension2) sourceViewer).removeTextHovers(t);

				int[] stateMasks = configuration.getConfiguredTextHoverStateMasks(getSourceViewer(), t);

				if (stateMasks != null) {
					for (int j = 0; j < stateMasks.length; j++) {
						int stateMask = stateMasks[j];
						ITextHover textHover = configuration.getTextHover(sourceViewer, t, stateMask);
						((ITextViewerExtension2) sourceViewer).setTextHover(textHover, t, stateMask);
					}
				}
				else {
					ITextHover textHover = configuration.getTextHover(sourceViewer, t);
					((ITextViewerExtension2) sourceViewer).setTextHover(textHover, t, ITextViewerExtension2.DEFAULT_HOVER_STATE_MASK);
				}
			}
			else
				sourceViewer.setTextHover(configuration.getTextHover(sourceViewer, t), t);
		}
	}

	protected void updateStatusField(String category) {
		super.updateStatusField(category);

		if (category == null)
			return;

		if (StructuredTextEditorActionConstants.STATUS_CATEGORY_OFFSET.equals(category)) {
			IStatusField field = getStatusField(category);
			if (field != null) {
				Point selection = getSourceViewer().getTextWidget().getSelection();
				int offset1 = widgetOffset2ModelOffset(getSourceViewer(), selection.x);
				int offset2 = widgetOffset2ModelOffset(getSourceViewer(), selection.y);
				String text = null;
				if (offset1 != offset2)
					text = "[" + offset1 + "-" + offset2 + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				else
					text = "[ " + offset1 + " ]"; //$NON-NLS-1$ //$NON-NLS-2$
				field.setText(text == null ? fErrorLabel : text);
			}
		}
	}

	private void handleConvertLineDelimiters() {
		if (getDocumentProvider().getDocument(getEditorInput()).getNumberOfLines() > 1) {
			try {
				convertLineDelimiters(getDocumentProvider().getDocument(getEditorInput()));
			}
			catch (CoreException e) {
				throw new SourceEditingRuntimeException(e);
			}
		}
	}

	protected void handleCursorPositionChanged() {
		super.handleCursorPositionChanged();
		updateStatusField(StructuredTextEditorActionConstants.STATUS_CATEGORY_OFFSET);
	}

	/*
	 * Note: This method appears in both ModelManagerImpl and JSEditor (with
	 * just a minor difference). They should be kept the same.
	 */
	private void convertLineDelimiters(IDocument document) throws CoreException {
		String contentTypeId = ContentTypeIdForJavaScript.ContentTypeID_JAVASCRIPT;
		String endOfLineCode = ContentBasedPreferenceGateway.getPreferencesString(contentTypeId, CommonEncodingPreferenceNames.END_OF_LINE_CODE);
		// endOfLineCode == null means the content type does not support this
		// function (e.g. DTD)
		// endOfLineCode == "" means no translation
		if (endOfLineCode != null && endOfLineCode.length() > 0) {
			String lineDelimiterToUse = System.getProperty("line.separator"); //$NON-NLS-1$
			if (endOfLineCode.equals(CommonEncodingPreferenceNames.CR))
				lineDelimiterToUse = CommonEncodingPreferenceNames.STRING_CR;
			else if (endOfLineCode.equals(CommonEncodingPreferenceNames.LF))
				lineDelimiterToUse = CommonEncodingPreferenceNames.STRING_LF;
			else if (endOfLineCode.equals(CommonEncodingPreferenceNames.CRLF))
				lineDelimiterToUse = CommonEncodingPreferenceNames.STRING_CRLF;

			TextEdit multiTextEdit = new MultiTextEdit();
			int lineCount = document.getNumberOfLines();
			try {
				for (int i = 0; i < lineCount; i++) {
					IRegion lineInfo = document.getLineInformation(i);
					int lineStartOffset = lineInfo.getOffset();
					int lineLength = lineInfo.getLength();
					int lineEndOffset = lineStartOffset + lineLength;

					if (i < lineCount - 1) {
						String currentLineDelimiter = document.getLineDelimiter(i);
						if (currentLineDelimiter != null && currentLineDelimiter.compareTo(lineDelimiterToUse) != 0)
							multiTextEdit.addChild(new ReplaceEdit(lineEndOffset, currentLineDelimiter.length(), lineDelimiterToUse));
					}
				}

				if (multiTextEdit.getChildrenSize() > 0)
					multiTextEdit.apply(document);
			}
			catch (BadLocationException exception) {
				throw new SourceEditingRuntimeException(exception);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.AbstractDecoratedTextEditor#collectContextMenuPreferencePages()
	 */
	protected String[] collectContextMenuPreferencePages() {
		List allIds = new ArrayList(0);

		// get contributed preference pages
		ExtendedConfigurationBuilder builder = ExtendedConfigurationBuilder.getInstance();
		String[] configurationIds = getConfigurationPoints();
		for (int i = 0; i < configurationIds.length; i++) {
			String[] definitions = builder.getDefinitions("preferencepages", configurationIds[i]); //$NON-NLS-1$
			for (int j = 0; j < definitions.length; j++) {
				String someIds = definitions[j];
				if (someIds != null && someIds.length() > 0) {
					// supports multiple comma-delimited page IDs in one
					// element
					String[] ids = StringUtils.unpack(someIds);
					for (int k = 0; k < ids.length; k++) {
						// trim, just to keep things clean
						String id = ids[k].trim();
						if (!allIds.contains(id)) {
							allIds.add(id);
						}
					}
				}
			}
		}

		// add pages contributed by super
		String[] superPages = super.collectContextMenuPreferencePages();
		for (int m = 0; m < superPages.length; m++) {
			// trim, just to keep things clean
			String id = superPages[m].trim();
			if (!allIds.contains(id)) {
				allIds.add(id);
			}
		}

		return (String[]) allIds.toArray(new String[0]);
	}

	public int getOrientation() {
		//https://bugs.eclipse.org/bugs/show_bug.cgi?id=88714
		return SWT.LEFT_TO_RIGHT;
	}
}
