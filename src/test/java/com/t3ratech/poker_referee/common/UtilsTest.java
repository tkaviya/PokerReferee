package com.t3ratech.poker_referee.common;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

/***************************************************************************
 * Created:     09 / 12 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@Test
public class UtilsTest {

    @Test
    public void testIsNumeric() {
        System.out.println("RUNNING TEST: Utils.isNumeric");
        Assert.assertFalse(Utils.isNumeric(null));
        Assert.assertFalse(Utils.isNumeric("test string"));
        Assert.assertTrue(Utils.isNumeric("0"));
        Assert.assertTrue(Utils.isNumeric("12.0"));
        Assert.assertTrue(Utils.isNumeric("6663.11"));
        Assert.assertTrue(Utils.isNumeric("-6663.11"));
        Assert.assertTrue(Utils.isNumeric(-99999999999999999999999999999999.99999999999999999999999999999999));
        Assert.assertTrue(Utils.isNumeric("-99999999999999999999999999999999.99999999999999999999999999999999"));
        Assert.assertTrue(Utils.isNumeric("-0"));
        Assert.assertTrue(Utils.isNumeric(new BigDecimal(0)));
        Assert.assertTrue(Utils.isNumeric(new BigDecimal(0.4)));
        Assert.assertTrue(Utils.isNumeric((float) 0.4));
        Assert.assertTrue(Utils.isNumeric(1L));
        Assert.assertFalse(Utils.isNumeric(new Object()));
        Assert.assertFalse(Utils.isNumeric(""));
    }
}
