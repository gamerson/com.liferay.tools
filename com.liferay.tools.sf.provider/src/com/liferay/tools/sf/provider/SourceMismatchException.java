package com.liferay.tools.sf.provider;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author André de Oliveira
 * @author Hugo Huijser
 */
public class SourceMismatchException extends PortalException {

	public SourceMismatchException(
		String fileName, String originalSource, String formattedSource) {

		_fileName = fileName;
		_originalSource = originalSource;
		_formattedSource = formattedSource;
	}

	public String getFileName() {
		return _fileName;
	}

	public String getFormattedSource() {
		return _formattedSource;
	}

	public String getOriginalSource() {
		return _originalSource;
	}

	private final String _fileName;
	private final String _formattedSource;
	private final String _originalSource;

}