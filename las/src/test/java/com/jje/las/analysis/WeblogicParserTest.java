package com.jje.las.analysis;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeblogicParserTest {

    @Test
    public void perform(){
        String message = "<Feb 23, 2012 9:23:16 AM CST> <Notice> <WebLogicServer> <BEA-000365> <Server state changed to ADMIN>";
        String regex = "<([^<>]*)> <([^<>]*)> <([^<>]*)> <([^<>]*)> <([^<>]*)>";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(message);
        Assert.assertTrue(matcher.matches());
        Assert.assertEquals(5, matcher.groupCount());
        Assert.assertEquals("Feb 23, 2012 9:23:16 AM CST", matcher.group(1));
        Assert.assertEquals("Notice", matcher.group(2));
        Assert.assertEquals("WebLogicServer", matcher.group(3));
        Assert.assertEquals("BEA-000365", matcher.group(4));
        Assert.assertEquals("Server state changed to ADMIN", matcher.group(5));

    }

}
