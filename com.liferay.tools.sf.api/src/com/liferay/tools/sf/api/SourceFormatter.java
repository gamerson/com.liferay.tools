package com.liferay.tools.sf.api;

public interface SourceFormatter {

	public void format() throws Throwable;

	public void format(String filename) throws Exception;

}
