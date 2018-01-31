package com.supconit.common.utils.nativelib;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * 辅助加载dll文件
 * 
 */
public final class NativeLibraryLoader {

	/**add path to java.library.path
	 * @param dirPath
	 * @throws IOException
	 */
	public static void addDir(String dirPath) throws IOException {
		try {
			Field field = ClassLoader.class.getDeclaredField("usr_paths");
			field.setAccessible(true);
			String[] paths = (String[]) field.get(null);
			for (int i = 0; i < paths.length; i++) {
				//System.out.println((i+1)+": "+paths[i]);
				if (dirPath.equals(paths[i])) {
					return;
				}
			}
			String[] tmp = new String[paths.length + 1];
			System.arraycopy(paths, 0, tmp, 1, paths.length);
			tmp[0] = dirPath;
			field.set(null, tmp);
		} catch (IllegalAccessException e) {
			throw new IOException(
					"Failed to get permissions to set library path");

		} catch (NoSuchFieldException e) {
			throw new IOException(
					"Failed to get field handle to set library path");
		}

	}
	public static void main(String[] args) throws IOException {
		String s="c:/native";
		NativeLibraryLoader.addDir(s);
	}
}
