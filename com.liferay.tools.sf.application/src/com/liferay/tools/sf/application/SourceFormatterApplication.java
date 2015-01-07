package com.liferay.tools.sf.application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osgi.enroute.debug.api.Debug;

import com.liferay.tools.sf.api.SourceFormatter;

@Component(
		name = "com.liferay.tools.sf",
		service = SourceFormatterApplication.class,
		property = {
			Debug.COMMAND_SCOPE + "=sf",
			Debug.COMMAND_FUNCTION + "=format"
		}
)
public class SourceFormatterApplication {

	private SourceFormatter sf;

	public void format() throws Throwable {
		sf.format();
	}

	public void format(String filename) throws Exception {
		sf.format(filename);
	}

	@Reference
	public void setSourceFormatter(SourceFormatter sf) {
		this.sf = sf;
	}

}
