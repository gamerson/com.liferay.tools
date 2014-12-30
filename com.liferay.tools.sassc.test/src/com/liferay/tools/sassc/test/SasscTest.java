package com.liferay.tools.sassc.test;

import org.junit.Assert;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import com.liferay.tools.sassc.api.SassCompiler;

public class SasscTest {

    private final BundleContext context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();

    @Test
    public void testSassc() throws Exception {
    	Assert.assertNotNull(context);
    	SassCompiler sassc = getService(SassCompiler.class);
		Assert.assertNotNull(sassc);

		String expected = ".foo { background: white; } .foo .bar { margin-top: 10px; }";

		Assert.assertEquals(expected,
				stripNewLines(sassc.compile(
						".foo { background: white; .bar { margin-top: 10px; } }", "", "")));
    }

    <T> T getService(Class<T> clazz) throws InterruptedException {
    	ServiceTracker<T,T> st = new ServiceTracker<>(context, clazz, null);
    	st.open();
    	return st.waitForService(1000);
    }

    private String stripNewLines(String str) {
		return str.replaceAll("\\n|\\r", "").replaceAll("\\s+", " ");
	}
}
