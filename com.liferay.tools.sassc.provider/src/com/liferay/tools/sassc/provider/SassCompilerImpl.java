package com.liferay.tools.sassc.provider;

import com.liferay.tools.sassc.api.SassCompiler;
import com.liferay.tools.sassc.api.SassCompilationException;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;

import java.io.File;

import org.osgi.service.component.annotations.Component;

import sass.OutputStyle;
import sass.SassLibrary;
import sass.SourceComments;
import sass.sass_context;
import sass.sass_file_context;
import osgi.enroute.debug.api.Debug;

@Component(
		name = "com.liferay.tools.sassc",
		property = {
				Debug.COMMAND_SCOPE + "=test",
				Debug.COMMAND_FUNCTION + "=sassc"
		}
)
public class SassCompilerImpl implements SassCompiler {

	static SassLibrary libsass = SassLibrary.INSTANCE;

	public String compile(String input, String includePath, String imgPath)
		throws SassCompilationException {

		sass_context ctx = null;

		try {
			ctx = libsass.sass_new_context();

			ctx.source_string = str(input);

			ctx.options.include_paths = str(includePath);
			ctx.options.image_path = str(imgPath);
			ctx.options.source_comments = SourceComments.NONE.value();
			ctx.options.output_style = OutputStyle.EXPANDED.value();

			libsass.sass_compile(ctx);

			if (ctx.error_status != 0) {
				throw new SassCompilationException(
					ctx.error_message.getString(0));
			}

			if (ctx.output_string == null || ctx.output_string.getString(0) == null) {
				throw new SassCompilationException("libsass returned null");
			}

			String output = ctx.output_string.getString(0);

			return output;
		} finally {
			try {
				if (ctx != null) {
					libsass.sass_free_context(ctx);
				}
			} catch (Throwable t) {
				throw new SassCompilationException(t);
			}
		}
	}

	public String compileFile(
			String inputFile, String includePath, String imgPath)
		throws SassCompilationException {

		sass_file_context ctx = null;

		try {
			ctx = libsass.sass_new_file_context();

			ctx.input_path = str(inputFile);
			ctx.output_path = str("");

			String includePaths =
				includePath+File.pathSeparator+new File(inputFile).getParent();

			ctx.options.include_paths = str(includePaths);
			ctx.options.image_path = str(imgPath);
			ctx.options.source_comments = SourceComments.NONE.value();
			ctx.source_map_string = str("");
			ctx.options.output_style = OutputStyle.EXPANDED.value();

			libsass.sass_compile_file(ctx);

			if (ctx.error_status != 0) {
				throw new SassCompilationException(
					ctx.error_message.getString(0));
			}

			if (ctx.output_string == null || ctx.output_string.getString(0) == null) {
				throw new SassCompilationException("libsass returned null");
			}

			String output = ctx.output_string.getString(0);

			return output;
		} finally {
			try {
				if (ctx != null)libsass.sass_free_file_context(ctx);
			} catch (Throwable t) {
				throw new SassCompilationException(t);
			}
		}
	}

	public String sassc(String input, String includePath, String imgPath)
			throws SassCompilationException {
		return compile(input, includePath, imgPath);
	}


	/**
	 * converts a string to a pointer
	 * @param string
	 * @return
	 */
	private Pointer str(String string) {
		Memory mem = new Memory(string.length() + 10);
		mem.setString(0, string);
		return mem;
	}
}
