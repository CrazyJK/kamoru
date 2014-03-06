package jk.kamoru.test.commons;

import jk.kamoru.util.StringUtils;
import junit.framework.Assert;

import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void contains() {
		Assert.assertTrue(StringUtils.containsAny("abc def hig", "abc"));
		Assert.assertEquals(StringUtils.split("a, b, c, d,", ",", 5).length, 4);
		Assert.assertEquals(StringUtils.split("a, b, c, d, ", ",", 5).length, 5);
		Assert.assertEquals(StringUtils.split("a, b, c, d", ",", 3)[2], " c, d");
//		Assert..assertEquals(StringUtils.split(null, ",", 1).length, 1);
		Assert.assertEquals(StringUtils.split("", ",", 3).length, 0);
		
	}
}
