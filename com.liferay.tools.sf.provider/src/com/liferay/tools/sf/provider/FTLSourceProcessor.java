package com.liferay.tools.sf.provider;

import java.io.File;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class FTLSourceProcessor extends BaseSourceProcessor {

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		return trimContent(content, false);
	}

	@Override
	protected void format() throws Exception {
		String[] excludes = new String[] {
			"**\\journal\\dependencies\\template.ftl",
			"**\\servicebuilder\\dependencies\\props.ftl"
		};
		String[] includes = new String[] {"**\\*.ftl"};

		List<String> fileNames = getFileNames(excludes, includes);

		for (String fileName : fileNames) {
			format(fileName);
		}
	}

}