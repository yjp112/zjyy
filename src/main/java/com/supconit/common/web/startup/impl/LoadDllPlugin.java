package com.supconit.common.web.startup.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.supconit.common.utils.nativelib.NativeLibraryLoader;
import com.supconit.common.web.startup.IStartupPlugin;

/**
 * 加载dll文件
 * 
 * @author dingyg
 *
 */
public class LoadDllPlugin implements IStartupPlugin {
	private transient static final Logger log = LoggerFactory.getLogger(LoadDllPlugin.class);
	private static final String DLL_DIR = "natives";

	@Override
	public void startup() {
		try {
			URL url=LoadDllPlugin.class.getClassLoader().getResource(DLL_DIR);
			String dllPath=URLDecoder.decode(url.getFile(),"UTF-8").substring(1);
			NativeLibraryLoader.addDir(dllPath);
			File dir=new File(dllPath);
			if(!dir.isDirectory()){
				return;
			}
			File[] dllFile=dir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if(!name.endsWith(".dll")){
						return false;
					}
					return true;
				}
			});
			for (File dll : dllFile) {
				try {
					Runtime.getRuntime().loadLibrary(dll.getName().replaceAll(".dll", ""));
				} catch (Exception e) {
					log.error("加载["+dll.getName()+"]失败!",e);
				}
			}
		} catch (IOException e) {
			log.error("加载dll文件失败!",e);
		}
	}
	
	public static void main(String[] args) {
		new LoadDllPlugin().startup();
	}
}
