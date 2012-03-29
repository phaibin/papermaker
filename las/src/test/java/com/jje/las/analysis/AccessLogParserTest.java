package com.jje.las.analysis;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccessLogParserTest {

    @Test
    public void perform(){
        String message = "172.24.88.88 - - [24/Dec/2011:13:16:29 +0800] \"GET /resource/hotel/images/booking/loading.gif HTTP/1.1\" 200 1553";
        String regex = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}) - - \\[(.*)\\] \"(.*)\" (\\d{1,3}) (\\d{1,8})";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(message);
        Assert.assertTrue(matcher.matches());
        Assert.assertEquals(5, matcher.groupCount());
        Assert.assertEquals("172.24.88.88", matcher.group(1));
        Assert.assertEquals("24/Dec/2011:13:16:29 +0800", matcher.group(2));
        Assert.assertEquals("GET /resource/hotel/images/booking/loading.gif HTTP/1.1", matcher.group(3));
        Assert.assertEquals("200", matcher.group(4));
        Assert.assertEquals("1553", matcher.group(5));

    }

}
