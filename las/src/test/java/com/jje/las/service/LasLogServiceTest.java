package com.jje.las.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.las.action.log.Log;
import com.jje.las.action.log.LogQueryForm;
import com.jje.las.action.log.LogQueryResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/spring/servlet-context.xml"})
@Ignore
public class LasLogServiceTest {

	
	private  static final java.text.SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 @Autowired
	LasLogService service;
	 
	 @Test
	  public void query() throws IOException, ParseException{
		 
		 LogQueryForm form =new LogQueryForm();
		 form.setBegin(format.parse("2012-01-10 13:36:17"));
		 form.setEnd(format.parse("2012-01-29 13:36:09"));
		 form.setPriority("DEBUG");
		 LogQueryResult res =service.query(form);
		 for(Log g:res.getList())
		 {
			 System.out.println(g.getLogTime().toLocaleString());
		 }
	 }
	
}
