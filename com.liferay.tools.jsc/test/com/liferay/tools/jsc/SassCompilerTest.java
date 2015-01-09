package com.liferay.tools.jsc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class SassCompilerTest extends SassLibraryTests {

	@Test
	public void testCompile() throws Exception {
		SassCompiler compiler = new SassCompiler();
		assertNotNull(compiler);

		String output = compiler.compile(
			".foo { background: white; .bar { margin-top: 10px; } }", "", "" );
		assertNotNull(output);
		assertEquals(
			".foo { background: white; } .foo .bar { margin-top: 10px; }",
			stripNewLines(output));
	}

	@Test
	public void testCompile_app_view_entry() throws Exception {
		SassCompiler compiler = new SassCompiler();
		assertNotNull(compiler);

		String output = compiler.compileFile(
			BASE_RESOURCES + "/compass/_app_view_entry.scss",
			BASE_RESOURCES + "/compass/common", "");
		assertNotNull(output);
		assertEquals(
			stripNewLines(readFileContents(BASE_RESOURCES + "/compass/_app_view_entry.css")),
			stripNewLines(output));
	}

	@Test
	public void testCompileFile() throws Exception {
		SassCompiler compiler = new SassCompiler();
		assertNotNull(compiler);

		String output = compiler.compileFile(
			BASE_RESOURCES + "_asset_category_selector.scss", "", "");
		assertNotNull(output);
		assertEquals(
			stripNewLines(readFileContents(BASE_RESOURCES + "_asset_category_selector.css")),
			stripNewLines(output));
	}

	private static final String BASE_RESOURCES = "bin_test/com/liferay/tools/jsc/resources/";
}
