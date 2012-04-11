package com.jje.las.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import junit.framework.Assert;

import org.junit.Test;

public class CalendarTest {

    @Test
    public void today() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String today = df.format(new Date());
        Calendar calendar = Calendar.getInstance();
        String ctoday = String.format("%1$tY%1$tm%1$te", calendar);
        Assert.assertEquals(today, ctoday);
   }
   
    @SuppressWarnings("deprecation")
    @Test
    public void parse() throws Exception{
        String date = "Thu Feb 09 18:32:42 CST 2012";
        SimpleDateFormat df = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        Date d = df.parse(date);
        Assert.assertEquals(1900+d.getYear(), 2012);
    }
    
}
