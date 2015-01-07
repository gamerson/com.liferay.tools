package com.liferay.tools.sf.provider;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class SQLSourceProcessor extends BaseSourceProcessor {

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			String previousLineSqlCommand = StringPool.BLANK;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = trimLine(line, false);

				if (Validator.isNotNull(line) &&
					!line.startsWith(StringPool.TAB)) {

					String sqlCommand = StringUtil.split(
						line, CharPool.SPACE)[0];

					if (Validator.isNotNull(previousLineSqlCommand) &&
						!previousLineSqlCommand.equals(sqlCommand)) {

						sb.append("\n");
					}

					previousLineSqlCommand = sqlCommand;
				}
				else {
					previousLineSqlCommand = StringPool.BLANK;
				}

				String strippedQuotesLine = stripQuotes(
					line, CharPool.APOSTROPHE);

				if (strippedQuotesLine.contains(StringPool.QUOTE)) {
					line = StringUtil.replace(
						line, StringPool.QUOTE, StringPool.APOSTROPHE);
				}

				sb.append(line);
				sb.append("\n");
			}
		}

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		return content;
	}

	@Override
	protected void format() throws Exception {
		String[] includes = new String[] {"**\\sql\\*.sql"};

		List<String> fileNames = getFileNames(new String[0], includes);

		for (String fileName : fileNames) {
			format(fileName);
		}
	}

}