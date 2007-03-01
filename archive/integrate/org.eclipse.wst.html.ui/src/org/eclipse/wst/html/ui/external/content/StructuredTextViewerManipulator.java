package org.eclipse.wst.html.ui.external.content;

import java.lang.reflect.Method;

import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.information.IInformationProvider;
import org.eclipse.jface.text.source.ISourceViewer;

public class StructuredTextViewerManipulator {
	/* since nothing is really standard/interface, we'll use reflection to bypass protected methods
	 * 
	 */
	private Object textViewer;
	private Class viewerClass;
	
	public StructuredTextViewerManipulator(Object StructuredTextViewerConfiguration){
		textViewer = StructuredTextViewerConfiguration;
		viewerClass = textViewer.getClass();
	}
	
	public IAutoEditStrategy[] getAutoEditStrategies(ISourceViewer sourceViewer, String contentType){
		 String functionName = "getAutoEditStrategies";
		 Class[] parameterTypes = new Class[] {ISourceViewer.class,String.class};
		 Object[] arguments = new Object[] {sourceViewer,contentType};
		 Object result;
		 
		 try {
                         
            Method thisMethod = viewerClass.getDeclaredMethod(functionName, parameterTypes);
            thisMethod.setAccessible(true);
            result =  thisMethod.invoke(textViewer, arguments);
            return (IAutoEditStrategy[])result;
            
        } catch(Exception e){
        	// Reflection No likey..........
        }

        return null;
	}
	
	public IContentAssistProcessor[] getContentAssistProcessors(ISourceViewer sourceViewer, String partitionType) {
		 String functionName = "getContentAssistProcessors";
		 Class[] parameterTypes = new Class[] {ISourceViewer.class,String.class};
		 Object[] arguments = new Object[] {sourceViewer,partitionType};
		 Object result;
		 
		 try {
               
           Method thisMethod = viewerClass.getDeclaredMethod(functionName, parameterTypes);
           thisMethod.setAccessible(true);
           result =  thisMethod.invoke(textViewer, arguments);
           return (IContentAssistProcessor[])result;
           
       } catch(Exception e){
       	// Reflection No likey..........
    	   
    	   System.out.println("Exception : " + e);
       }

       return null;
	}
	
	public IInformationProvider getInformationProvider(ISourceViewer sourceViewer, String partitionType){
		 String functionName = "getInformationProvider";
		 Class[] parameterTypes = new Class[] {ISourceViewer.class,String.class};
		 Object[] arguments = new Object[] {sourceViewer,partitionType};
		 Object result;
		 
		 try {
                        
           Method thisMethod = viewerClass.getDeclaredMethod(functionName, parameterTypes);
           thisMethod.setAccessible(true);
           result =  thisMethod.invoke(textViewer, arguments);
           return (IInformationProvider)result;
           
       } catch(Exception e){
       	// Reflection No likey..........
       }

       return null;
		
		
	
	}

}
