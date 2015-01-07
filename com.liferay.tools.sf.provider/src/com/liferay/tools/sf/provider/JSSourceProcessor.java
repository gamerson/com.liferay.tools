package com.liferay.tools.sf.provider;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSSourceProcessor extends BaseSourceProcessor {

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		String newContent = trimContent(content, false);

		newContent = StringUtil.replace(
			newContent,
			new String[] {
				"else{", "for(", "function (", "if(", "while(", "){\n",
				"= new Array();", "= new Object();"
			},
			new String[] {
				"else {", "for (", "function(", "if (", "while (", ") {\n",
				"= [];", "= {};"
			});

		while (true) {
			Matcher matcher = _multipleVarsOnSingleLinePattern.matcher(
				newContent);

			if (!matcher.find()) {
				break;
			}

			String match = matcher.group();

			int pos = match.indexOf("var ");

			StringBundler sb = new StringBundler(4);

			sb.append(match.substring(0, match.length() - 2));
			sb.append(StringPool.SEMICOLON);
			sb.append("\n");
			sb.append(match.substring(0, pos + 4));

			newContent = StringUtil.replace(newContent, match, sb.toString());
		}

		if (newContent.endsWith("\n")) {
			newContent = newContent.substring(0, newContent.length() - 1);
		}

		checkLanguageKeys(fileName, newContent, languageKeyPattern);

		if (newContent.contains("debugger.")) {
			processErrorMessage(fileName, "debugger " + fileName);
		}

		return newContent;
	}

	@Override
	protected void format() throws Exception {
		String[] excludes = {
			"**\\js\\aui\\**", "**\\js\\editor\\**", "**\\js\\jquery\\**",
			"**\\js\\lodash\\**", "**\\js\\misc\\**", "**\\r2.js",
			"**\\tools\\**", "**\\VAADIN\\**"
		};
		String[] includes = {"**\\*.js"};

		List<String> fileNames = getFileNames(excludes, includes);

		for (String fileName : fileNames) {
			format(fileName);
		}
	}

	private final Pattern _multipleVarsOnSingleLinePattern = Pattern.compile(
		"\t+var \\w+\\, ");

}