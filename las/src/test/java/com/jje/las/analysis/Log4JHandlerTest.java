package com.jje.las.analysis;

import com.jje.las.action.log.Log;
import com.jje.las.action.log.LogQueryForm;
import com.jje.las.action.log.LogQueryResult;
import com.jje.las.service.LasLogService;
import java.io.*;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ResourceUtils;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/spring/servlet-context.xml"})
public class Log4JHandlerTest {
        
    @Autowired
    Log4JHandler handler;
    
    @Test
    public void parseSimple() throws IOException{
        File file = ResourceUtils.getFile("classpath:log4jSimple.log");
        LasLogService mockHandler = mock(LasLogService.class);
        ArgumentCaptor<Log> argument = ArgumentCaptor.forClass(Log.class);
        handler.setHandle(mockHandler);
        FileInputStream fis = new FileInputStream(file);
        handler.parser(fis, "hbp.log");
        fis.close();
        verify(mockHandler, atLeast(33)).insert(argument.capture());
        verify(mockHandler, atMost(33)).insert(argument.capture());
    }
    
    @Test
    public void parseWithError() throws FileNotFoundException, IOException{
        File file = ResourceUtils.getFile("classpath:log4jWithException.log");
        LasLogService mockHandler = mock(LasLogService.class);
        ArgumentCaptor<Log> argument = ArgumentCaptor.forClass(Log.class);
        handler.setHandle(mockHandler);
        FileInputStream fis = new FileInputStream(file);
        handler.parser(fis, "hbp.log");
        fis.close();
        verify(mockHandler, atLeast(5)).insert(argument.capture());
        verify(mockHandler, atMost(5)).insert(argument.capture());
    }
    
    
    
    @Test
    public void parseLog4j() throws IOException{
        File file = ResourceUtils.getFile("classpath:log4j.log");
        LasLogService mockHandler = mock(LasLogService.class);
        ArgumentCaptor<Log> argument = ArgumentCaptor.forClass(Log.class);
        handler.setHandle(mockHandler);
        FileInputStream fis = new FileInputStream(file);
        handler.parser(fis, "hbp.log");
        fis.close();
        verify(mockHandler, atLeast(3)).insert(argument.capture());
        verify(mockHandler, atMost(3)).insert(argument.capture());
    }

}
