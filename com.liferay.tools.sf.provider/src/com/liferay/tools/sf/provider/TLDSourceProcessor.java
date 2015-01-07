package com.liferay.tools.sf.provider;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class TLDSourceProcessor extends BaseSourceProcessor {

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		content = trimContent(content, false);

		return StringUtil.replace(content, "\n\n\n", "\n\n");
	}

	@Override
	protected void format() throws Exception {
		String[] excludes = new String[] {"**\\WEB-INF\\tld\\**"};
		String[] includes = new String[] {"**\\*.tld"};

		List<String> fileNames = getFileNames(excludes, includes);

		for (String fileName : fileNames) {
			format(fileName);
		}
	}

}