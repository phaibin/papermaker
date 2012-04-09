package com.jje.las.analysis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.junit.Assert;
import org.junit.Test;

import com.jje.las.action.log.Log;

public class Log4jParserTest {

    @Test
    public void parseSimple() throws Exception {
        // log4j pattern %d [%t] %-7p %10c{1} - %m%n
        String[] samples = { "1999-11-27 15:49:37,459 [thread-x] ERROR mypackage - Catastrophic system failure" };

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
    public void parseSimpleTwo() throws Exception {
        // log4j pattern %d [%t] %-7p %10c{1} - %m%n
        // String regex =
        // "(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:\\d{2}:\\d{2},\\d{3}) ([^ ]*) \\[(.*)\\] - (.*)$";
        String samples = "2012-02-09 14:31:43,288 INFO [com.jje.autorental.order.esb.OrderDispatchResource] - 执行同步子订单调度信息, resource url :/autorental/order/syncOrderDispatch";
        Log4JCommand cmd = new Log4JCommand();
        Log nl = new Log();
        Context ctx = new ContextBase();
        ctx.put("currentLine", samples);
        ctx.put("newLog", nl);
        Assert.assertTrue(cmd.execute(ctx));
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleFormat.parse("2012-02-09 14:31:43,288");

        Assert.assertEquals(date, nl.getLogTime());
        Assert.assertEquals("INFO", nl.getPriority());
        Assert.assertEquals("com.jje.autorental.order.esb.OrderDispatchResource", nl.getClassName());
        Assert.assertEquals("执行同步子订单调度信息, resource url :/autorental/order/syncOrderDispatch", nl.getMessage());

    }

}
