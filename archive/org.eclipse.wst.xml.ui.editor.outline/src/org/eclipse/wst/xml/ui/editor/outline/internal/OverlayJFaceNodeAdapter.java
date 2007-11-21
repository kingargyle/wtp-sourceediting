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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.xml.ui.editor.outline.internal.model.OutlineTemplateModel;
import org.eclipse.wst.xml.ui.editor.outline.internal.model.OutlineTemplateRule;
import org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeAdapter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class OverlayJFaceNodeAdapter extends JFaceNodeAdapter
{
	OverlayJFaceNodeAdapterFactory adapterFactory;
	
    public OverlayJFaceNodeAdapter(OverlayJFaceNodeAdapterFactory adapterFactory) 
    {
        super( adapterFactory);
		this.adapterFactory = adapterFactory;
    }
    
    public Object[] getChildren(Object object) 
    {
        if( !(adapterFactory.getConfiguration().isCustomOutlineStyle()))
        {
            return super.getChildren( object);
        }    
        
        // (pa) 20021217
        // cmvc defect 235554
        // performance enhancement: using child.getNextSibling() rather than
        // nodeList(item) for O(n) vs. O(n*n)
        //
        
        Node node = (Node) object;
        
        Document document = (node instanceof Document) ? (Document)node : node.getOwnerDocument();
        Element rootElement = document.getDocumentElement();
            
        List<Node> v = new ArrayList<Node>( node.getChildNodes().getLength());
		
		if( rootElement!=null)
		{
			OutlineTemplateModel model = OutlineTemplateCache.getInstance().findMatching( rootElement);
			
	        for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) 
			{
	            Node n = child;
	            
	            if( model!=null)
	            {
	                if(n.getNodeType()==Node.ELEMENT_NODE)
	                {    
	                    Element element = (Element)n;    
	    
	                    if( element.equals( rootElement) || model.findMatching( element)!=null)
	                    {
	                        v.add(n);
	                    }
	                    else
	                    {
	                        List<Node> list = model.findNearestMatchingElements( element);
	                        v.addAll( list);
	                    }    
	                }
	            }   
	            else if (n.getNodeType() != Node.TEXT_NODE)
	            {
	                v.add( n);
	            }    
	        }			
		}
		
        return v.toArray();
    }
   
    /* (non-Javadoc)
     * @see org.eclipse.wst.xml.ui.views.contentoutline.JFaceNodeAdapter#getLabelText(java.lang.Object)
     */
    public String getLabelText(Object object)
    {
        if( !(adapterFactory.getConfiguration().isCustomOutlineStyle()))
        {
            return super.getLabelText( object);
        }    

        Node node = (Node) object;
        
        Document document = (node instanceof Document) ? (Document)node : node.getOwnerDocument();
        Element rootElement = document.getDocumentElement();
        
        OutlineTemplateModel model= OutlineTemplateCache.getInstance().findMatching( rootElement);
        if( model!=null)
        {
            if(node.getNodeType()==Node.ELEMENT_NODE)
            {    
                Element element = (Element)node;    

                OutlineTemplateRule matchingRule=null;
                if( (matchingRule=model.findMatching( element))!=null)
                {
                    String label = matchingRule.getLabel( element);
                    
                    return label!=null ? label : super.getLabelText(node);
                }
                else if( element.equals( rootElement))
					
                    return "<" + super.getLabelText( node) + "> :: the root element is not defined in outline configuration";
            }
        }   
        else if (node.getNodeType() != Node.TEXT_NODE)
        {
            return super.getLabelText( node);
        }    
        
        return null;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.wst.xml.ui.views.contentoutline.JFaceNodeAdapter#getLabelImage(java.lang.Object)
     */
    public Image getLabelImage(Object object)
    {
    	/*
        if( !((OverlayJFaceNodeAdapterFactory)adapterFactory).getConfiguration().isCustomOutlineStyle())
        {
            return super.getLabelImage( object);
        }    

        Node node = (Node) object;
        
        Document document = (node instanceof Document) ? (Document)node : node.getOwnerDocument();
        Element rootElement = document.getDocumentElement();

        OutlineTemplateModel model= OutlineTemplateCache.getInstance().findMatching( rootElement);
        if( model!=null)
        {
            if(node.getNodeType()==Node.ELEMENT_NODE)
            {    
                Element element = (Element)node;    

                OutlineTemplateRule matchingRule=null;
                if( (matchingRule=model.findMatching( element))!=null)
                {
                    Image image = matchingRule.getImage( element);
                    
                    return image!=null ? image : super.getLabelImage( node);
                }
                else if( element.equals( rootElement))
                    return super.getLabelImage( node);
            }
        }   
        else if (node.getNodeType() != Node.TEXT_NODE)
        {
            return super.getLabelImage( node);
        }    
        */
        return super.getLabelImage( object);
    }
}
