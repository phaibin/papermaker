package com.jje.las.analysis;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.jje.las.analysis.command.LasContext;
import com.jje.las.analysis.command.Log4JCommand;
import com.jje.las.domain.Log;

public class Log4jCommandTest {

    @Test
    public void parseSimpleTwo() throws Exception {
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
    
    @Test
    public void parseSimple() throws Exception{
        String s = "2012-01-10 19:40:14,091 WARN [com.mchange.v2.c3p0.DriverManagerDataSource] - Could not load driverClass org.h2.Driver";
        Log4JCommand cmd = new Log4JCommand();
        Log nl = new Log();
        LasContext ctx = new LasContext();
        ctx.setCurrentLine(s).setNewLog(nl);
        Assert.assertTrue(cmd.execute(ctx));
    }

}
