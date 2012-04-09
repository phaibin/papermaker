package com.jje.las.analysis;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.File;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import com.jje.las.LasBaseSpringTest;
import com.jje.las.action.log.Log;
import com.jje.las.service.LasLogService;

public class Log4JHandlerTest extends LasBaseSpringTest{
        
    @Autowired
    Log4JHandler handler;
    
    @Test
    public void parseSimple() throws Exception{
        File file = ResourceUtils.getFile("classpath:log4jSimple.log");
        LasLogService mockHandler = mock(LasLogService.class);
        ArgumentCaptor<Log> argument = ArgumentCaptor.forClass(Log.class);
        handler.setHandle(mockHandler);
        handler.parser(file, "hbp.log");
        //actual 33, but insert null first, so 34 times
        verify(mockHandler, atLeast(34)).insert(argument.capture());
        verify(mockHandler, atMost(34)).insert(argument.capture());
    }
    
    @Test
    public void parseWithError() throws Exception{
        File file = ResourceUtils.getFile("classpath:log4jWithException.log");
        LasLogService mockHandler = mock(LasLogService.class);
        ArgumentCaptor<Log> argument = ArgumentCaptor.forClass(Log.class);
        handler.setHandle(mockHandler);
        handler.parser(file, "hbp.log");
        verify(mockHandler, atLeast(6)).insert(argument.capture());
        verify(mockHandler, atMost(6)).insert(argument.capture());
    }
    
    
    
    @Test
    public void parseLog4j() throws Exception{
        File file = ResourceUtils.getFile("classpath:log4j.log");
        LasLogService mockHandler = mock(LasLogService.class);
        ArgumentCaptor<Log> argument = ArgumentCaptor.forClass(Log.class);
        handler.setHandle(mockHandler);
        handler.parser(file, "hbp.log");
        verify(mockHandler, atLeast(4)).insert(argument.capture());
        verify(mockHandler, atMost(4)).insert(argument.capture());
    }

}
