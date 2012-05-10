package com.jje.las.analysis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import com.jje.las.analysis.command.AccessCommand;
import com.jje.las.analysis.command.LasContext;
import com.jje.las.domain.Log;

public class AccessLogCommandTest {

    @Test
    public void parseSimple() throws Exception {
        String message = "172.24.88.88 - - [24/Dec/2011:13:16:29 +0800] \"GET /resource/hotel/images/booking/loading.gif HTTP/1.1\" 200 1553";
        AccessCommand cmd = new AccessCommand();
        Log nl = new Log();
        LasContext ctx = new LasContext();
        ctx.setCurrentLine(message).setNewLog(nl);
        SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
        Date date = simpleFormat.parse("24/Dec/2011:13:16:29 +0800");
        Assert.assertTrue(cmd.execute(ctx));
        Assert.assertEquals(date, nl.getLogTime());
        Assert.assertEquals("ACCESS", nl.getPriority());
        Assert.assertEquals("", nl.getClassName());
        Assert.assertEquals("200 1553", nl.getResult());
        Assert.assertEquals("172.24.88.88", nl.getIp());
        Assert.assertEquals("GET /resource/hotel/images/booking/loading.gif HTTP/1.1", nl.getMessage());

    }


}
