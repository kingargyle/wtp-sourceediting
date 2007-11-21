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

package org.eclipse.wst.xml.ui.editor.outline.internal.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class OutlineTemplateModel extends HashMap<ElementMatcher,OutlineTemplateRule> {
	public OutlineTemplateRule findMatching( Element element) {
		for( ElementMatcher em : keySet()) {
			if( em.matches( element)) {
				return get( em);
			}
		}
		
		return null;
	}
	
	public List<Node> findNearestMatchingElements( Element context)
    {
        List<Node> list = new ArrayList<Node>();
        
        Node child=context.getFirstChild();
        while( child!=null)
        {
            if( child.getNodeType()==Node.ELEMENT_NODE)
            {
                Element element = (Element)child;
                
                List<Node> cl = findNearestMatchingElements( element);
                
                for( ElementMatcher em : keySet()) {
                    if( em.matches( element))
                    {
                        list.add( element);
                        
                        break;
                    }
                    else
                    {                        
                        for( Iterator<Node> iterator = cl.iterator(); iterator.hasNext();)
                        {
                            Element childElement = (Element)iterator.next();
                            if( !list.contains( childElement))
                                list.add( childElement);
                        }                       
                    }    
                }
            }                
            
            child = child.getNextSibling();
        }
        
        return list;
    }
}
