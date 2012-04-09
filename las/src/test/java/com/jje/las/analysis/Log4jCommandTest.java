package com.jje.las.analysis;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.jje.las.action.log.Log;
import com.jje.las.analysis.command.LasContext;
import com.jje.las.analysis.command.Log4JCommand;

public class Log4jCommandTest {

    @Test
    public void parseSimpleTwo() throws Exception {
        // log4j pattern %d [%t] %-7p %10c{1} - %m%n
        // String regex =
        // "(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:\\d{2}:\\d{2},\\d{3}) ([^ ]*) \\[(.*)\\] - (.*)$";
        String samples = "2012-02-09 14:31:43,288 INFO [com.jje.autorental.order.esb.OrderDispatchResource] - 执行同步子订单调度信息, resource url :/autorental/order/syncOrderDispatch";
        Log4JCommand cmd = new Log4JCommand();
        Log nl = new Log();
        LasContext ctx = new LasContext();
        ctx.setCurrentLine(samples).setNewLog(nl);
        Assert.assertTrue(cmd.execute(ctx));
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleFormat.parse("2012-02-09 14:31:43,288");

        Assert.assertEquals(date, nl.getLogTime());
        Assert.assertEquals("INFO", nl.getPriority());
        Assert.assertEquals("com.jje.autorental.order.esb.OrderDispatchResource", nl.getClassName());
        Assert.assertEquals("执行同步子订单调度信息, resource url :/autorental/order/syncOrderDispatch", nl.getMessage());

    }

}
