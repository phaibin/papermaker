package com.jje.las.util;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.las.action.log.Log;
import com.jje.las.service.LasService;
import com.mongodb.BasicDBObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/spring/servlet-context.xml"})
public class ConvertTest {

	
	LasService service;
	@Test
	public  void parseDBObject() 
	{
		Log log=new Log();
		log.setClassName("com.jje.las.action.log.Log");
		log.setDetail("detail");
		Date dt =new Date();
		log.setLogTime(dt);
		
		BasicDBObject db =Convert.parseDBObject(log);
		System.out.println(db.toString());
	}
	
}
