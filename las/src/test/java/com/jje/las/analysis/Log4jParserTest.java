package com.jje.las.analysis;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Assert;
import org.junit.Test;

public class Log4jParserTest {

    @Test
    public void parseSimple() {
        // log4j pattern %d [%t] %-7p %10c{1} - %m%n
        String[] samples = {
            "1999-11-27 15:49:37,459 [thread-x] ERROR mypackage - Catastrophic system failure"
        };

        String regex = "(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:\\d{2}:\\d{2},\\d{3}) \\[(.*)\\] ([^ ]*) ([^ ]*) - (.*)$";

        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(samples[0]);

        if (m.matches() && m.groupCount() == 6) {
            String date = m.group(1);
            String time = m.group(2);
            String threadId = m.group(3);
            String priority = m.group(4);
            String category = m.group(5);
            String message = m.group(6);

            Assert.assertEquals("1999-11-27", date);
            Assert.assertEquals("15:49:37,459", time);
            Assert.assertEquals("thread-x", threadId);
            Assert.assertEquals("ERROR", priority);
            Assert.assertEquals("mypackage", category);
            Assert.assertEquals("Catastrophic system failure", message);
        }

    }
    
        @Test
    public void parseSimpleTwo() {
        // log4j pattern %d [%t] %-7p %10c{1} - %m%n
        String[] samples = {
            "2012-02-09 14:31:43,288 INFO [com.jje.autorental.order.esb.OrderDispatchResource] - 执行同步子订单调度信息, resource url :/autorental/order/syncOrderDispatch"
        };

        String regex = "(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:\\d{2}:\\d{2},\\d{3}) ([^ ]*) \\[(.*)\\] - (.*)$";

        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(samples[0]);

        if (m.matches() && m.groupCount() == 5) {
            String date = m.group(1);
            String time = m.group(2);
            String priority = m.group(3);
            String category = m.group(4);
            String message = m.group(5);

            Assert.assertEquals("2012-02-09", date);
            Assert.assertEquals("14:31:43,288", time);
            Assert.assertEquals("INFO", priority);
            Assert.assertEquals("com.jje.autorental.order.esb.OrderDispatchResource", category);
            Assert.assertEquals("执行同步子订单调度信息, resource url :/autorental/order/syncOrderDispatch", message);
        }

    }


}
