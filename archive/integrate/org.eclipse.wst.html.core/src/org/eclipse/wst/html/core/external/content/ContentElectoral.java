
package org.eclipse.wst.html.core.external.content;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;
import java.util.Hashtable;

public class ContentElectoral {
	
	Hashtable configHelpers = new Hashtable();
	Hashtable viewerMap = new Hashtable();
	
	String cachedType;
	Object cachedConfigObject;
	
	public ContentElectoral(){
		
	}
	
	public void mergeElectoral(ContentElectoral newElements){
		Enumeration keys = newElements.viewerMap.keys();
		
		while(keys.hasMoreElements()){
			Object key = keys.nextElement();
			Object value = newElements.viewerMap.get(key);
			addCanidate((OrderedContentType)key,value);
		}
	}
	
	public void addCanidate(OrderedContentType type, Object configurationObject ){
		if(type==null || configurationObject==null) return;
		if(configHelpers.get(type.getContentID())==null){
			Vector v = new Vector();
			v.add(type);
			configHelpers.put(type.getContentID(), v);
		}else{
			((Vector)configHelpers.get(type.getContentID())).add(type);
		}
		
		viewerMap.put(type, configurationObject);
	}
	
	private Object getCanidate(OrderedContentType ofType){
		if(ofType==null) return null;
		cachedConfigObject = viewerMap.get(ofType);
		cachedType = ofType.getContentID();
		return cachedConfigObject;
	}

	public Object getWinner(String ofType){
		if(ofType==null) return null;
		/* Forces election from OrderedContentTypes */
		if(cachedType!=null && cachedType.equals(ofType)) return cachedConfigObject;
		
		Vector helpers = ((Vector)configHelpers.get(ofType));
		if(helpers!=null && !helpers.isEmpty() ){
			Vector orderedHelpers =(Vector)helpers.clone();
			Collections.sort(orderedHelpers);
			return getCanidate((OrderedContentType)orderedHelpers.get(0));
		}
		return null;
	}
	
	public String[] getArrayOfTypes(){
		// Returns a unique array of types, removing duplicates
		Enumeration e = configHelpers.keys();
		Vector v = new Vector();
		
		while(e.hasMoreElements())
			v.add(e.nextElement());
		
		return (String[])v.toArray(new String[0]);
	}
}
