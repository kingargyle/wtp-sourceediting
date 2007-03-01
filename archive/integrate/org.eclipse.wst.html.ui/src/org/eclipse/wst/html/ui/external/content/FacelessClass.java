package org.eclipse.wst.html.ui.external.content;

import java.lang.reflect.Method;

import java.util.Vector;

public class FacelessClass {
	
	private static Vector debugTable = new Vector();
	
	
	public static Object executeMethod(Object targetObject,String methodName, Object[] methodParamaters){
		Class[] parameterTypes;
		
		if(methodParamaters!=null && methodParamaters.length>0 ){
			 parameterTypes = new Class[methodParamaters.length];
			 
			 for(int i=0;i<methodParamaters.length;i++){
				 parameterTypes[i] = methodParamaters.getClass();
			 }
		 }else{
			 parameterTypes = new Class[0];
		 }
		 Object result;
		 
		 Class objectsClass = targetObject.getClass();
		 
		 try {
                         
            Method thisMethod = objectsClass.getDeclaredMethod(methodName, parameterTypes);
            thisMethod.setAccessible(true);
            result =  thisMethod.invoke(targetObject, methodParamaters);
            return result;
            
        } catch(Exception e){
        	if(debugTable.contains(targetObject)) System.out.println(e);
        }

        return null;
	
	}
	
	public static void showDebugFor(Object targetObject){
		debugTable.add(targetObject);
	}
	
	public static void hideDebugFor(Object targetObject){
		int index = debugTable.indexOf(targetObject);
		if(index>=0) debugTable.remove(index);
	}

}
