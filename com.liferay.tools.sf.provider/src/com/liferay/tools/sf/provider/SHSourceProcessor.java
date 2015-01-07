package com.liferay.tools.sf.provider;

import java.io.File;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class SHSourceProcessor extends BaseSourceProcessor {

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		return content;
	}

	@Override
	protected void format() throws Exception {
		String[] includes = new String[] {"**\\*.sh"};

		List<String> fileNames = getFileNames(new String[0], includes);

		for (String fileName : fileNames) {
			format(fileName);
		}
	}

}