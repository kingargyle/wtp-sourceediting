/*******************************************************************************
 * Copyright (c) 2005-2007 Orangevolt (www.orangevolt.com)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Lars Gersmann (www.orangevolt.com) 
 *     
 *******************************************************************************/

package org.eclipse.wst.xml.ui.editor.outline.internal;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.wst.sse.core.internal.provisional.INodeNotifier;
import org.eclipse.wst.sse.ui.internal.contentoutline.IJFaceNodeAdapter;
import org.eclipse.wst.xml.ui.editor.outline.OutlineActivator;
import org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeContentProvider;
import org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeLabelProvider;
import org.eclipse.wst.xml.ui.views.contentoutline.XMLContentOutlineConfiguration;

public class ContentOutlineConfiguration extends XMLContentOutlineConfiguration
{
    boolean customOutline = true;

    protected class ToggleOutlineStyleAction extends Action 
    {
        private TreeViewer fTreeViewer = null;

        public ToggleOutlineStyleAction( TreeViewer viewer) 
        {
            super( "Toggle Outline Style", AS_CHECK_BOX); //$NON-NLS-1$
            setImageDescriptor( OutlineActivator.getDefault().getImageRegistry().getDescriptor( "icon"));
            setChecked( customOutline);
            // setDisabledImageDescriptor(COLLAPSE_D);
            setToolTipText( getText());
            fTreeViewer = viewer;
        }

        public void run() 
        {
            customOutline = !customOutline;
            
            fTreeViewer.refresh();
            
            super.run();
        }
    }
    
    protected class ExpandAllAction extends Action 
    {
        private TreeViewer fTreeViewer = null;

        public ExpandAllAction( TreeViewer viewer) 
        {
            super( "Expand All", AS_PUSH_BUTTON); //$NON-NLS-1$
            setImageDescriptor( OutlineActivator.getDefault().getImageRegistry().getDescriptor( "plus"));
            setToolTipText( getText());
            fTreeViewer = viewer;
        }

        public void run() 
        {
            fTreeViewer.expandAll();
            
            super.run();
        }
    }

    protected class RefreshAction extends Action 
    {
        private TreeViewer fTreeViewer = null;

        public RefreshAction( TreeViewer viewer) 
        {
            super( "Refresh", AS_PUSH_BUTTON); //$NON-NLS-1$
            setImageDescriptor( OutlineActivator.getDefault().getImageRegistry().getDescriptor( "refresh-outline"));
            setToolTipText( getText());
            fTreeViewer = viewer;
        }

        public void run() 
        {
            fTreeViewer.refresh();
            
            super.run();
        }
    }
    
    protected class PreferencesAction extends Action 
    {
        private TreeViewer fTreeViewer = null;

        public PreferencesAction( TreeViewer viewer) 
        {
            super( "Outline Configuration Preferences", AS_UNSPECIFIED); //$NON-NLS-1$
            setImageDescriptor( OutlineActivator.getDefault().getImageRegistry().getDescriptor( "preferences"));
            setToolTipText( getText());
            fTreeViewer = viewer;
        }

        public void run() 
        {
        	PreferencesUtil.createPreferenceDialogOn( fTreeViewer.getControl().getShell(), "org.eclipse.wst.xml.ui.editor.outline.templates", new String[0], null).open();
        }
    }

    public void updateTreeViewer() {
    	treeViewer.refresh();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.wst.sse.ui.views.contentoutline.StructuredContentOutlineConfiguration#createToolbarContributions(org.eclipse.jface.viewers.TreeViewer)
     */
    protected IContributionItem[] createToolbarContributions(TreeViewer viewer)
    {
        IContributionItem[] items;
        IContributionItem toggleOutlineItem = new ActionContributionItem(new ToggleOutlineStyleAction(viewer));
        IContributionItem refeshItem = new ActionContributionItem(new RefreshAction(viewer));
        IContributionItem expandAllItem = new ActionContributionItem(new ExpandAllAction(viewer));
        items = super.createToolbarContributions(viewer);
        if (items == null) 
        {
            items = new IContributionItem[]{toggleOutlineItem};
        } 
        else 
        {
            IContributionItem[] combinedItems = new IContributionItem[items.length + 5];
            System.arraycopy(items, 0, combinedItems, 5, items.length);
            combinedItems[0] = toggleOutlineItem;
            combinedItems[1] = new Separator();            
            combinedItems[2] = refeshItem;
            combinedItems[3] = new Separator();
            combinedItems[4] = expandAllItem;
            //          combinedItems[items.length + 1] = toggleLinkItem;
            items = combinedItems;
        }
        return items;
    }
    
    TreeViewer treeViewer = null;
    
    @Override
    protected IContributionItem[] createMenuContributions(TreeViewer viewer) {
    	IContributionItem[] items= super.createMenuContributions(viewer);
    	
    	this.treeViewer = viewer;
    	
    	IContributionItem preferencesContributionItem = new ActionContributionItem( new PreferencesAction( viewer));
        IContributionItem[] combinedItems = new IContributionItem[items.length + 1];
        System.arraycopy(items, 0, combinedItems, 1, items.length);
        combinedItems[0] = preferencesContributionItem;
    	return combinedItems;
    }
    
    public OverlayJFaceNodeAdapterFactory adapterFactory = null;
    
    public class OverlayJFaceNodeContentProvider extends JFaceNodeContentProvider  {

    	protected IJFaceNodeAdapter getAdapter(Object adaptable) {

    		if (adaptable instanceof INodeNotifier)
    		{
	    		return (IJFaceNodeAdapter)(getAdapterFactory().createAdapter((INodeNotifier)adaptable));
    		}
    		return super.getAdapter(adaptable);
    	}
    };
    
    public OverlayJFaceNodeAdapterFactory getAdapterFactory() {
    	if (adapterFactory == null) adapterFactory = new OverlayJFaceNodeAdapterFactory(this);
    	return adapterFactory;
    }
    
    private IContentProvider fContentProvider = null;
    
    /* (non-Javadoc)
	 * @see org.eclipse.wst.xml.ui.views.contentoutline.XMLContentOutlineConfiguration#getContentProvider(org.eclipse.jface.viewers.TreeViewer)
	 */
	public IContentProvider getContentProvider(TreeViewer viewer) {
		
		if (! customOutline)
			return super.getContentProvider(viewer);

		if (fContentProvider == null) {
			fContentProvider = new OverlayJFaceNodeContentProvider();
		}
		return fContentProvider;
	}

	public class XSLTAttributeShowingLabelProvider extends JFaceNodeLabelProvider {

		private ILabelProvider delegate= null;
		
		public XSLTAttributeShowingLabelProvider(ILabelProvider delegate)
		{
			this.delegate = delegate;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeLabelProvider#getAdapter(java.lang.Object)
		 */
		protected IJFaceNodeAdapter getAdapter(Object adaptable) {
			if (isCustomOutlineStyle() && adaptable instanceof INodeNotifier) {
	    		return (IJFaceNodeAdapter)(getAdapterFactory().createAdapter((INodeNotifier)adaptable));
			}
			return super.getAdapter(adaptable);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeLabelProvider#getImage(java.lang.Object)
		 */
		public Image getImage(Object element) {
			return isCustomOutlineStyle() ? getAdapter(element).getLabelImage(element) : delegate.getImage(element);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeLabelProvider#getText(java.lang.Object)
		 */
		public String getText(Object element) {
			return isCustomOutlineStyle() ? getAdapter(element).getLabelText(element) : delegate.getText(element);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
		 */
		public boolean isLabelProperty(Object element, String property) {
			return super.isLabelProperty(element, property);
		}
	}
	
	private ILabelProvider myLabelProvider = null;
	
	/* (non-Javadoc)
     * @see org.eclipse.wst.xml.ui.views.contentoutline.XMLContentOutlineConfiguration#getLabelProvider(org.eclipse.jface.viewers.TreeViewer)
     */
    public ILabelProvider getLabelProvider(TreeViewer viewer)
    {
    	if (myLabelProvider == null) myLabelProvider = new XSLTAttributeShowingLabelProvider(super.getLabelProvider(viewer));
    	
    	return myLabelProvider;
    }

    public boolean isCustomOutlineStyle()
    {
        return customOutline;
    }
}
