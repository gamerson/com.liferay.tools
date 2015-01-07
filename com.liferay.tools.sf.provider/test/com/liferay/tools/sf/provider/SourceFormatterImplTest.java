package com.liferay.tools.sf.provider;

import org.junit.Test;

/**
 * @author Hugo Huijser
 */
public class SourceFormatterImplTest {

	@Test
	public void testSourceFormatterImpl() throws Throwable {
		SourceFormatterImpl sf = new SourceFormatterImpl(
			false, true, true, false);

		sf.format();
	}

}