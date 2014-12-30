package com.liferay.tools.sf.provider;

import org.osgi.service.component.annotations.Component;

import com.liferay.tools.sf.api.SourceFormatter;

/**
 *
 */
@Component(name = "com.liferay.tools.sf")
public class SourceFormatterImpl implements SourceFormatter {

	@Override
	public void format() throws Exception {
		System.out.println("formatting everything");
	}

	@Override
	public void format(String filename) throws Exception {
		System.out.println("formatting just " + filename);
	}

}
