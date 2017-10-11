package com.assignment.wordcount.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUitlity {

	private static final int BUFFER_SIZE = 4096;
    /**
     * This  method of FileUtility unzip the zip file to give directory location
     * @param inputStream
     * @param destLocation
     * @throws IOException
     */
	public static void unzipFile(InputStream inputStream, String destLocation) throws IOException {

		ZipInputStream zipinputstream = new ZipInputStream(inputStream);
		ZipEntry zipentry = zipinputstream.getNextEntry();
		if(zipentry == null) {
			throw new IllegalArgumentException("Either not a zip file or it is empty");
		}
		File dir = new File(destLocation);
		dir.mkdirs();
		while (zipentry != null) {
			String filePath = dir + System.getProperty("file.separator") + zipentry.getName();
			if (!zipentry.isDirectory()) {
				File parent = new File(filePath).getParentFile();
				if(!parent.exists()) {
					parent.mkdirs();
				}
				writeFile(zipinputstream, filePath);
			} else {
				dir = new File(filePath);
				dir.mkdir();
			}
			zipinputstream.closeEntry();
			zipentry = zipinputstream.getNextEntry();
		}
		zipinputstream.close();
	}
    /**
     * Delete the directory
     * @param path
     */
	public static void delete(String path) {
		File file = new File(path);
		file.delete();
	}
	
    /**
     * Write ZipInputStream to given path location
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
	public static void writeFile(ZipInputStream zipIn, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}

}
