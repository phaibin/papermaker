package com.jje.las.analysis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import com.jje.las.analysis.command.LasContext;
import com.jje.las.analysis.command.WeblogicCommand;
import com.jje.las.domain.Log;

public class WeblogicCommandTest {

    @Test
    public void perform() throws Exception{
        String message = "<Feb 23, 2012 9:21:57 AM CST> <Notice> <WebLogicServer> <BEA-000365> <Server state changed to STARTING> ";
        WeblogicCommand cmd = new WeblogicCommand();
        Log nl = new Log();
        LasContext ctx = new LasContext();
        ctx.setCurrentLine(message).setNewLog(nl);
        SimpleDateFormat simpleFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss aaa z", Locale.ENGLISH);
        Date date = simpleFormat.parse("Feb 23, 2012 9:21:57 AM CST");
        Assert.assertTrue(cmd.execute(ctx));
        Assert.assertEquals(date, nl.getLogTime());
        Assert.assertEquals("Notice", nl.getPriority());
        Assert.assertEquals("WebLogicServer", nl.getModule());
        Assert.assertEquals("BEA-000365", nl.getThread());
        Assert.assertEquals("Server state changed to STARTING", nl.getMessage());

    }

}
