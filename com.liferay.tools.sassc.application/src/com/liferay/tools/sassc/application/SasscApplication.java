package com.liferay.tools.sassc.application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.tools.sassc.api.SassCompilationException;
import com.liferay.tools.sassc.api.SassCompiler;

import osgi.enroute.debug.api.Debug;

@Component(
		name="com.liferay.tools.sassc",
		service=SasscApplication.class,
		property = {
			Debug.COMMAND_SCOPE + "=sassc",
			Debug.COMMAND_FUNCTION + "=compile"
		}
)
public class SasscApplication {

	private SassCompiler sassc;

	public String compile(String input, String includePath, String imgPath)
			throws SassCompilationException {
		return sassc.compile(input, includePath, imgPath);
	}

	@Reference
	void setSassCompiler(SassCompiler sassc) {
		this.sassc = sassc;
	}

}
