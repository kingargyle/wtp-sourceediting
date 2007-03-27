/* Copyright 2007 IBM and others
 * 
 * Author Bradley Childs (childsb@us.ibm.com)
 * 
 * Brute force file merging installation handler.  Looks at each included plugin fragments 
 * host plugin, and replace the class libraries in the host components jar file.
 * 
 * Requires fragments packaged as jars and host primary runtime packaged as jars.
 */
package org.eclipse.update.splice.core.handler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.Platform;

import org.eclipse.update.configuration.IConfiguredSite;
import org.eclipse.update.core.ContentReference;
import org.eclipse.update.core.IFeature;
import org.eclipse.update.core.IFeatureContentConsumer;
import org.eclipse.update.core.IFeatureReference;
import org.eclipse.update.core.IImport;
import org.eclipse.update.core.IIncludedFeatureReference;
import org.eclipse.update.core.IPluginEntry;
import org.eclipse.update.core.SiteManager;
import org.eclipse.update.core.Utilities;
import org.eclipse.update.core.VersionedIdentifier;
import org.eclipse.update.internal.core.DeltaInstallHandler;
import org.eclipse.update.internal.core.LocalSite;
import org.eclipse.update.internal.core.UpdateCore;
import org.eclipse.update.internal.operations.UpdateUtils;


public class MergingInstallHandler extends DeltaInstallHandler{
	
	
	private final String protectedFiles[] = {"META-INF/manifest.mf","plugin.xml","fragment.xml","eclipse.inf"};

	protected String[] getProtectedFiles(){
		return protectedFiles;
	}
		
	class cachedMergingJar{
		Hashtable jarEntries = new Hashtable();
		class elementFilePair{
			ZipEntry zipEntry;
			File file;
			elementFilePair(ZipEntry element, File file){this.zipEntry = element;this.file=file;}
		}
		
		void mergeWithJar(File jarFile)throws IOException{
			JarFile jf = null;
			try {
				jf = new JarFile(jarFile);
				
				Enumeration e = jf.entries();	
				while(e.hasMoreElements()) {
					ZipEntry je = (ZipEntry)e.nextElement();
					
					/* if a file exists we overwrite unless its in the protected file list */
					Boolean addFile = true;
					for(int k = 0;(k<getProtectedFiles().length && jarEntries.containsKey(je.getName()));k++){
						if(getProtectedFiles()[k].equalsIgnoreCase(je.getName())) addFile=false;
					}
					if(addFile) jarEntries.put(je.getName(), new elementFilePair(je,jarFile));	
				}
			} catch (IOException e) {
				throw e;
			}finally{
				if(jf!=null) jf.close();
			}
		}
		
		File flushToTempFile(File destinationDirectory) throws IOException{
			File tempFile = Utilities.createLocalFile(destinationDirectory, "46and2" +  "-" + (new Date()).getTime() + ".jar");
    		JarOutputStream jarTempOutputStream = new JarOutputStream(new FileOutputStream(tempFile));
			Enumeration e = jarEntries.elements();
			try{
    			while(e.hasMoreElements()){
    				elementFilePair element = (elementFilePair)e.nextElement();
    				JarFile tempJarFileRef = null;
    				tempJarFileRef = new JarFile(element.file);
    				InputStream io = tempJarFileRef.getInputStream(element.zipEntry);
    				
    				byte b[] = new byte[4096];
    				int read = 0;
    				
    				try {
    					jarTempOutputStream.putNextEntry(element.zipEntry);
    					while( ( read = io.read(b, 0, 4096)) != -1) {
    						jarTempOutputStream.write(b, 0, read);
    					}
    				} catch (IOException ze) {
    					
    					throw ze;
    					
    				}catch(Exception ex){
    					System.out.println(ex);	
    				}finally{
    					if(io!=null) io.close();
    					if(tempJarFileRef!=null) tempJarFileRef.close();
    				
    				}		
    						
    			}
			}finally{
				if(jarTempOutputStream!=null) jarTempOutputStream.close();
			}
			return tempFile;
		}
		public void finalize(){
			if(jarEntries!=null) jarEntries.clear();
		}
	}

	protected void overlayPlugin(
			IPluginEntry oldPlugin,
			IFeature oldFeature,
			IPluginEntry newPlugin,
			IFeature newFeature,
			IFeatureContentConsumer newConsumer) throws IOException, CoreException {

			ContentReference[] oldJarReferences =	oldFeature.getFeatureContentProvider().getPluginEntryArchiveReferences(oldPlugin, monitor);
			ContentReference[] newJarRefrences= 	newFeature.getFeatureContentProvider().getPluginEntryArchiveReferences(newPlugin, monitor);
			
			File tempDir = Utilities.createWorkingDirectory();
			for(int i = 0;i<oldJarReferences.length;i++){
				cachedMergingJar tempMergableJar = new cachedMergingJar();
	    		tempMergableJar.mergeWithJar(oldJarReferences[i].asFile());
	    		JarFile oldJar = new JarFile(oldJarReferences[i].asFile());
	    		
	    		//Manifest primaryManifest = oldJar.getManifest();
	    		
				for(int j = 0;j<newJarRefrences.length;j++){
					tempMergableJar.mergeWithJar(newJarRefrences[j].asFile());
				}
				
				File newTempJarFile = tempMergableJar.flushToTempFile(tempDir);
				
				File oldJarFile = oldJarReferences[i].asFile();
				File oldJarFileBackup = new File(oldJarFile.toURI() + ".original");
				oldJarFile.delete();
				oldJarFile.createNewFile();
				copyFile(newTempJarFile, oldJarFile);	
			}
	}
	
	public static void copyFile(File src, File dst) throws IOException {
		InputStream in=null;
		OutputStream out=null;
		try {
			in = new BufferedInputStream(new FileInputStream(src));
			out = new BufferedOutputStream(new FileOutputStream(dst));		
			byte[] buffer = new byte[4096];
			int len;
			while ((len=in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
				}
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
				}
		}
	}
	public void completeInstall(IFeatureContentConsumer consumer)	throws CoreException {
    	try {
    		if (pluginEntries == null || !feature.isPatch()) throw new CoreException(new Status(Status.ERROR,"none","aborting"));

    		for (int i = 0; i < pluginEntries.length; i++) {
    			//IPluginEntry newPlugin = pluginEntries[i];
    			Hashtable targetReplacePlugins = getTargetPatchablePlugins(pluginEntries[i]);
    			if (targetReplacePlugins == null) continue;
    			try {
    				Enumeration keys = targetReplacePlugins.keys();
    				while(keys.hasMoreElements()){
    					IFeature oldFeature = (IFeature)keys.nextElement();
    					IPluginEntry oldPlugin = (IPluginEntry)targetReplacePlugins.get(oldFeature);
    					overlayPlugin(oldPlugin, oldFeature,pluginEntries[i], feature, consumer);
    				}
    			} catch (IOException e) {
    				throw new CoreException(
    					new Status(
    						Status.ERROR,
    						UpdateUtils.getPluginId(),
    						1,
    						"", //$NON-NLS-1$
    						e));
    			}
    		}
    	} finally {
    		//if (consumer != null)consumer.close();
    	}
    }
	
	
	
	protected Hashtable getTargetPatchablePlugins(IPluginEntry patch) throws CoreException {
		/* search all sites, and configurations for target plugins to update */
		IConfiguredSite[]    sites = SiteManager.getLocalSite().getCurrentConfiguration().getConfiguredSites();
		Hashtable targetPlugins = new Hashtable();
		VersionedIdentifier patchParentVi = getParentVersionIdentifier(patch);
		for(int i=0;i<sites.length;i++){
			IFeatureReference[]  featureRefs = sites[i].getConfiguredFeatures();
			for(int j = 0;j<featureRefs.length;j++){
				IFeature aFeature = featureRefs[j].getFeature(monitor);
				IPluginEntry[] aFeaturesPlugins = aFeature.getPluginEntries();
				for(int k =0;k<aFeaturesPlugins.length;k++){
					if (aFeaturesPlugins[k].getVersionedIdentifier().getIdentifier().equals(patchParentVi.getIdentifier()) && aFeaturesPlugins[k].getVersionedIdentifier().getVersion().isCompatibleWith(patchParentVi.getVersion()))
						targetPlugins.put(aFeature,aFeaturesPlugins[k]);
				}
			}
		}
		
		return targetPlugins;
			
	}
	public static VersionedIdentifier getParentVersionIdentifier(IPluginEntry hostPlugin) {
		String parentString = (String) Platform.getBundle(hostPlugin.getVersionedIdentifier().getIdentifier()).getHeaders().get("Fragment-Host");
    	if(parentString!=null){
    		String parentId = parentString.substring(0, parentString.indexOf(';'));
    		String version = parentString.substring(parentString.indexOf("bundle-version=\"")+16,parentString.length()-1);
    		
    		VersionedIdentifier parentVersion =new VersionedIdentifier(parentId,version);
    		return parentVersion;
    	}
    	return null;
    }	
}
