package com.liferay.tools.sassc.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.liferay.tools.sassc.api.SassCompiler;

public class SassCompilerImplTest extends SassLibraryTests {

	@Test
	public void testCompile() throws Exception {
		SassCompiler compiler = new SassCompilerImpl();
		assertNotNull(compiler);

		String output = compiler.compile(
			".foo { background: white; .bar { margin-top: 10px; } }", "", "" );
		assertNotNull(output);
		assertEquals(
			".foo { background: white; } .foo .bar { margin-top: 10px; }",
			stripNewLines(output));
	}
}
