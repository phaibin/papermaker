package com.jje.las.util;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.jje.las.service.LasLogService;

public class DateTimeEditor extends PropertyEditorSupport {

	private static Logger logger = Logger.getLogger(DateTimeEditor.class.getName());
	private static final SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
    public void setAsText(String text) throws IllegalArgumentException {  
       
		if(StringUtils.hasLength(text))
		{
		   try {
			setValue(format.parse(text));
		   } catch (ParseException e) {
			logger.debug("日期转换错误："+e);
		   }
		}
    }  
    /** 
     * Format the Date as String, using the specified DateFormat. 
     */  
    @Override  
    public String getAsText() {  
    	if(null!=getValue())
    	{
    	   Date d =(Date)getValue();
           return format.format(d);
    	}
    	return "";
    }  
}
