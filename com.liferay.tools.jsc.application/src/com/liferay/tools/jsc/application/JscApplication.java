package com.liferay.tools.jsc.application;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.tools.jsc.api.SassCompilationException;
import com.liferay.tools.jsc.api.SassCompiler;

import osgi.enroute.debug.api.Debug;

@Component(
		name="com.liferay.tools.jsc",
		service= {
				JscApplication.class,
				Runnable.class
		},
		property = {
			Debug.COMMAND_SCOPE + "=jsc",
			Debug.COMMAND_FUNCTION + "=compile",
			"main.thread=true"
		}
)
public class JscApplication implements Runnable {

	private SassCompiler jsc;
	private String[] args;

	public String compile(String input, String includePath, String imgPath)
			throws SassCompilationException {
		return jsc.compile(input, includePath, imgPath);
	}

	@Reference
	void setSassCompiler(SassCompiler jsc) {
		this.jsc = jsc;
	}

	@Override
	public void run() {
		if(args != null && args.length > 0) {
			try {
				System.out.println(jsc.compile(args[0], "", ""));
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
