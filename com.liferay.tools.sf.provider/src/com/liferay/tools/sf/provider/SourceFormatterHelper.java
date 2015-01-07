package com.liferay.tools.sf.provider;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Igor Spasic
 * @author Brian Wing Shun Chan
 */
public class SourceFormatterHelper {

	public SourceFormatterHelper(boolean useProperties) {
		_useProperties = useProperties;
	}

	public void close() throws IOException {
		if (!_useProperties) {
			return;
		}

		String newPropertiesContent = PropertiesUtil.toString(_properties);

		if (!_propertiesContent.equals(newPropertiesContent)) {
			FileUtil.write(_propertiesFile, newPropertiesContent);
		}
	}

	public void init() throws IOException {
		if (!_useProperties) {
			return;
		}

		File basedirFile = new File("./");

		String basedirAbsolutePath = StringUtil.replace(
			basedirFile.getAbsolutePath(), new String[] {".", ":", "/", "\\"},
			new String[] {"_", "_", "_", "_"});

		String propertiesFileName =
			System.getProperty("java.io.tmpdir") + "/SourceFormatter." +
				basedirAbsolutePath;

		_propertiesFile = new File(propertiesFileName);

		if (_propertiesFile.exists()) {
			_propertiesContent = FileUtil.read(_propertiesFile);

			PropertiesUtil.load(_properties, _propertiesContent);
		}
	}

	public void printError(String fileName, File file) {
		printError(fileName, file.toString());
	}

	public void printError(String fileName, String message) {
		if (_useProperties) {
			String encodedFileName = StringUtil.replace(
				fileName, StringPool.BACK_SLASH, StringPool.SLASH);

			_properties.remove(encodedFileName);
		}

		System.out.println(message);
	}

	public List<String> scanForFiles(DirectoryScanner directoryScanner) {
		directoryScanner.scan();

		String[] fileNamesArray = directoryScanner.getIncludedFiles();

		if (!_useProperties) {
			return ListUtil.toList(fileNamesArray);
		}

		List<String> fileNames = new ArrayList<String>(fileNamesArray.length);

		for (String fileName : fileNamesArray) {
			File file = new File(fileName);

			String encodedFileName = StringUtil.replace(
				fileName, StringPool.BACK_SLASH, StringPool.SLASH);

			long timestamp = GetterUtil.getLong(
				_properties.getProperty(encodedFileName));

			if (timestamp < file.lastModified()) {
				fileNames.add(fileName);

				_properties.setProperty(
					encodedFileName, String.valueOf(file.lastModified()));
			}
		}

		return fileNames;
	}

	private final Properties _properties = new Properties();
	private String _propertiesContent = StringPool.BLANK;
	private File _propertiesFile;
	private final boolean _useProperties;

}