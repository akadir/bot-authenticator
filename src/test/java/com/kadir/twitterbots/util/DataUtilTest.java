package com.kadir.twitterbots.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author akadir
 * Date: 21/12/2018
 * Time: 19:53
 */
public class DataUtilTest {

    @Test
    public void isNullOrEmptyTest() {
        Assert.assertEquals(true, DataUtil.isNullOrEmpty(null));

        String emptyString = "";
        Assert.assertEquals(true, DataUtil.isNullOrEmpty(emptyString));

        String s = "not null and empty";
        Assert.assertEquals(false, DataUtil.isNullOrEmpty(s));
    }
}
