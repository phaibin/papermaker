package com.jje.las.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import com.jje.las.LasBaseSpringTest;
import com.jje.las.action.log.LogQueryForm;
import com.jje.las.action.log.LogQueryResult;
import com.jje.las.analysis.FileParserHandler;
import com.jje.las.analysis.IAction;
import com.jje.las.domain.MonitFile;

@Ignore //TODO need mongod start
public class LasLogServiceTest extends LasBaseSpringTest {

    private static final java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    LasService service;
    @Autowired
    FileParserHandler handler;
    
    @Test
    @Ignore//Only one method support. see below TODO: 1 
    public void query_Validator_Enable() throws IOException, ParseException {
        LogQueryForm form = new LogQueryForm();
        form.setBegin(format.parse("2012-01-10 13:36:17"));
        form.setEnd(format.parse("2012-01-29 13:36:09"));
        form.setPriority("DEBUG");
        form.setLogFrom("abc");
        try {
            service.query(form.getBegin(), form.getEnd(), form.getPriority(), form.getLogFrom(), form.getPage(),
                    form.getPageSize());
            Assert.fail();
        } catch (Exception e) {
        }
    }

    @Test//TODO: 1. run integration test separate, mongod maybe lock when init setup & teardown
    public void queryLog() throws Exception {
        File file = ResourceUtils.getFile("classpath:testData.log");
        MonitFile mfile = new MonitFile(file.getPath(), "log4j");
        handler.handleLogFile(mfile, new IAction() {

            public void perform(MonitFile mfile) {
                System.out.println("======="+mfile.getPath());
            }
        });

        LogQueryForm form = new LogQueryForm();
        form.setBegin(format.parse("2012-02-09 14:31:00"));
        form.setEnd(format.parse("2012-02-09 14:37:59"));
        form.setPriority("ERROR");
        form.setLogFrom("testData.log");
        LogQueryResult queryResult = service.query(form.getBegin(), form.getEnd(), form.getPriority(), "",
                form.getPage(), form.getPageSize());
        Assert.assertEquals(4, queryResult.getList().size());

        form.setPriority("DEBUG");
        queryResult = service.query(form.getBegin(), form.getEnd(), form.getPriority(), "", form.getPage(),
                form.getPageSize());
        Assert.assertEquals(3, queryResult.getList().size());

        form.setPriority("INFO");
        queryResult = service.query(form.getBegin(), form.getEnd(), form.getPriority(), "", form.getPage(),
                form.getPageSize());
        Assert.assertEquals(4, queryResult.getList().size());

    }

}
