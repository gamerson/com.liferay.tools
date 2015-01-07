package com.liferay.tools.sf.provider;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public interface SourceProcessor {

	public void format(
			boolean useProperties, boolean printErrors, boolean autoFix)
		throws Exception;

	public String format(
			String fileName, boolean useProperties, boolean printErrors,
			boolean autoFix)
		throws Exception;

	public List<String> getErrorMessages();

	public SourceMismatchException getFirstSourceMismatchException();

}