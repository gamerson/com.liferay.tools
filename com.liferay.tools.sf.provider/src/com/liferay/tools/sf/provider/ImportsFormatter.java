package com.liferay.tools.sf.provider;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.IOException;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author André de Oliveira
 */
public abstract class ImportsFormatter {

	public String format(String imports) throws IOException {
		if (imports.contains("/*") || imports.contains("*/") ||
			imports.contains("//")) {

			return imports + "\n";
		}

		Set<ImportPackage> importPackages = new TreeSet<ImportPackage>();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(imports));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			ImportPackage importPackage = createImportPackage(line);

			if (importPackage != null) {
				importPackages.add(importPackage);
			}
		}

		StringBundler sb = new StringBundler(3 * importPackages.size());

		ImportPackage previousImportPackage = null;

		for (ImportPackage importPackage : importPackages) {
			if ((previousImportPackage != null) &&
				!importPackage.isGroupedWith(previousImportPackage)) {

				sb.append("\n");
			}

			sb.append(importPackage.getLine());
			sb.append("\n");

			previousImportPackage = importPackage;
		}

		return sb.toString();
	}

	protected abstract ImportPackage createImportPackage(String line);

}