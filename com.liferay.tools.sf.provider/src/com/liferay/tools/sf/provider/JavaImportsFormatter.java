package com.liferay.tools.sf.provider;

import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Carlos Sierra Andrés
 * @author André de Oliveira
 */
public class JavaImportsFormatter extends ImportsFormatter {

	@Override
	protected ImportPackage createImportPackage(String line) {
		Matcher matcher = _javaImportPattern.matcher(line);

		if (!matcher.find()) {
			return null;
		}

		boolean isStatic = false;

		if (Validator.isNotNull(matcher.group(1))) {
			isStatic = true;
		}

		String importString = matcher.group(2);

		return new ImportPackage(importString, isStatic, line);
	}

	private static final Pattern _javaImportPattern = Pattern.compile(
		"import( static)? ([^;]+);");

}