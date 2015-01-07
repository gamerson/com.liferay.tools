package com.liferay.tools.sf.provider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Carlos Sierra Andrés
 * @author André de Oliveira
 */
public class JSPImportsFormatter extends ImportsFormatter {

	@Override
	protected ImportPackage createImportPackage(String line) {
		Matcher matcher = _jspImportPattern.matcher(line);

		if (matcher.find()) {
			return new ImportPackage(matcher.group(1), false, line);
		}

		return null;
	}

	private static final Pattern _jspImportPattern = Pattern.compile(
		"import=\"([^\\s\"]+)\"");

}