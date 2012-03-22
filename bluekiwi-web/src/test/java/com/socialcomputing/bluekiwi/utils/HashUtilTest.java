/**
 * 
 */
package com.socialcomputing.bluekiwi.utils;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author "Jonathan Dray <jonathan@social-computing.com>"
 *
 */
public class HashUtilTest {

	/**
	 * Test method for {@link com.socialcomputing.bluekiwi.utils.HashUtil#getPHPSha1(java.lang.String)}.
	 */
	@Test
	public void testGetPHPSha1() {
		String sampleText = "thisisateststringwitha&";
		assertEquals("d78f3c543b134981ef32f8382d21d45b35a6d6f9", HashUtil.getPHPSha1(sampleText));
	}
}
