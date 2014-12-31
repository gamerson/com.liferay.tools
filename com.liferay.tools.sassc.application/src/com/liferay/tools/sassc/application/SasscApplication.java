package com.liferay.tools.sassc.application;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.tools.sassc.api.SassCompilationException;
import com.liferay.tools.sassc.api.SassCompiler;

import osgi.enroute.debug.api.Debug;

@Component(
		name="com.liferay.tools.sassc",
		service= {
				SasscApplication.class,
				Runnable.class
		},
		property = {
			Debug.COMMAND_SCOPE + "=sassc",
			Debug.COMMAND_FUNCTION + "=compile",
			"main.thread=true"
		}
)
public class SasscApplication implements Runnable {

	private SassCompiler sassc;
	private String[] args;

	public String compile(String input, String includePath, String imgPath)
			throws SassCompilationException {
		return sassc.compile(input, includePath, imgPath);
	}

	@Reference
	void setSassCompiler(SassCompiler sassc) {
		this.sassc = sassc;
	}

	@Override
	public void run() {
		if(args != null && args.length > 0) {
			try {
				System.out.println(sassc.compile(args[0], "", ""));
			} catch (SassCompilationException e) {
				e.printStackTrace();
			}
		}
	}

	@Reference
	void setDone(Object done, Map<String, Object> parameters) {
		args = (String[]) parameters.get("launcher.arguments");
	}

}
