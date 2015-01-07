package com.liferay.tools.sf.provider;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Writer;

/**
 * @author Hugo Huijser
 */
public class FileUtil {

	public static boolean exists(String fileName) {
		File file = new File(fileName);

		return file.exists();
	}

	public static String getAbsolutePath(File file) {
		return StringUtil.replace(
			file.getAbsolutePath(), CharPool.BACK_SLASH, CharPool.SLASH);
	}

	public static byte[] getBytes(File file) throws IOException {
		if ((file == null) || !file.exists()) {
			return null;
		}

		try (RandomAccessFile randomAccessFile = new RandomAccessFile(
				file, "r")) {

			byte[] bytes = new byte[(int)randomAccessFile.length()];

			randomAccessFile.readFully(bytes);

			return bytes;
		}
	}

	public static String getExtension(String fileName) {
		if (fileName == null) {
			return null;
		}

		int pos = fileName.lastIndexOf(CharPool.PERIOD);

		if (pos > 0) {
			return StringUtil.toLowerCase(
				fileName.substring(pos + 1, fileName.length()));
		}
		else {
			return StringPool.BLANK;
		}
	}

	public static String read(File file) throws IOException {
		byte[] bytes = getBytes(file);

		if (bytes == null) {
			return null;
		}

		String s = new String(bytes, StringPool.UTF8);

		return StringUtil.replace(
			s, StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE);
	}

	public static String read(String fileName) throws IOException {
		File file = new File(fileName);

		return read(file);
	}

	public static void write(File file, String s) throws IOException {
		if (s == null) {
			return;
		}

		mkdirsParentFile(file);

		try (Writer writer = new OutputStreamWriter(
				new FileOutputStream(file, false), StringPool.UTF8)) {

			writer.write(s);
		}
	}

	protected static void mkdirsParentFile(File file) {
		File parentFile = file.getParentFile();

		if (parentFile == null) {
			return;
		}

		try {
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
		}
		catch (SecurityException se) {

			// We may have the permission to write a specific file without
			// having the permission to check if the parent file exists

		}
	}

}