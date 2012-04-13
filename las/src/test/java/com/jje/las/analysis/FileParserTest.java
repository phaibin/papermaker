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
import com.jje.las.domain.Log;
import com.jje.las.service.LasService;

public class FileParserTest extends LasBaseSpringTest{
        
    @Autowired
    FileParserHandler handler;
    
    @Test
    public void parseSimple() throws Exception{
        File file = ResourceUtils.getFile("classpath:log4jSimple.log");
        LasService mockHandler = mock(LasService.class);
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
        LasService mockHandler = mock(LasService.class);
        ArgumentCaptor<Log> argument = ArgumentCaptor.forClass(Log.class);
        handler.setHandle(mockHandler);
        handler.parser(file, "hbp.log");
        verify(mockHandler, atLeast(6)).insert(argument.capture());
        verify(mockHandler, atMost(6)).insert(argument.capture());
    }
    
    
    
    @Test
    public void parseLog4j() throws Exception{
        File file = ResourceUtils.getFile("classpath:log4j.log");
        LasService mockHandler = mock(LasService.class);
        ArgumentCaptor<Log> argument = ArgumentCaptor.forClass(Log.class);
        handler.setHandle(mockHandler);
        handler.parser(file, "hbp.log");
        verify(mockHandler, atLeast(4)).insert(argument.capture());
        verify(mockHandler, atMost(4)).insert(argument.capture());
    }
    
    @Test
    public void parseAccessLog() throws Exception{
        File file = ResourceUtils.getFile("classpath:access.log");
        LasService mockHandler = mock(LasService.class);
        ArgumentCaptor<Log> argument = ArgumentCaptor.forClass(Log.class);
        handler.setHandle(mockHandler);
        handler.parser(file, "accesss.log");
        verify(mockHandler, atLeast(24)).insert(argument.capture());
        verify(mockHandler, atMost(24)).insert(argument.capture());
    }

    @Test
    public void parseWeblogicLog() throws Exception{
        File file = ResourceUtils.getFile("classpath:weblogic.out.log");
        LasService mockHandler = mock(LasService.class);
        ArgumentCaptor<Log> argument = ArgumentCaptor.forClass(Log.class);
        handler.setHandle(mockHandler);
        handler.parser(file, "weblogic.out.log");
        verify(mockHandler, atLeast(7)).insert(argument.capture());
        verify(mockHandler, atMost(7)).insert(argument.capture());
    }

    @Test
    public void parseComplexLog() throws Exception{
        File file = ResourceUtils.getFile("classpath:all.log");
        LasService mockHandler = mock(LasService.class);
        ArgumentCaptor<Log> argument = ArgumentCaptor.forClass(Log.class);
        handler.setHandle(mockHandler);
        handler.parser(file, "all.log");
        verify(mockHandler, atLeast(10)).insert(argument.capture());
        verify(mockHandler, atMost(10)).insert(argument.capture());
    }

}
