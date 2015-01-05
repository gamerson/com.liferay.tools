package com.liferay.tools.jsc.api;

public interface SassCompiler {

	public String compile(String input, String includePath, String imgPath)
			throws SassCompilationException;

	public String compileFile(String inputFile, String includePath,
			String imgPath) throws SassCompilationException;
}
