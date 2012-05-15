package com.jje.las.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This class implements a Thread-Safe (re-entrant) SimpleDateFormat
 * class.  It does this by using a ThreadLocal that holds a Map, instead
 * of the traditional approach to hold the SimpleDateFormat in a ThreadLocal.
 *
 * Each ThreadLocal holds a single HashMap containing SimpleDateFormats, keyed
 * by a String format (e.g. "yyyy/M/d", etc.), for each new SimpleDateFormat
 * instance that was created within the threads execution context.
 *
 * @author John DeRegnaucourt
 */
public class SafeSimpleDateFormat {
    private final String _format;
    private Locale _locale = null;
    private static final ThreadLocal<Map<String, SimpleDateFormat>> _dateFormats = new ThreadLocal<Map<String, SimpleDateFormat>>()
    {
        public HashMap<String, SimpleDateFormat> initialValue()
        {
            return new HashMap<String, SimpleDateFormat>();
        }
    };

    private SimpleDateFormat getDateFormat(String format)
    {
        Map<String, SimpleDateFormat> formatters = _dateFormats.get();
        SimpleDateFormat formatter = formatters.get(format);
        if (formatter == null)
        {
            if(_locale != null){
                formatter = new SimpleDateFormat(format, _locale);
            }
            else{
                formatter = new SimpleDateFormat(format);
            }
            formatters.put(format, formatter);
        }
        return formatter;
    }

    public SafeSimpleDateFormat(String format)
    {
        _format = format;
    }

    public SafeSimpleDateFormat(String format, Locale l)
    {
        _format = format;
        _locale = l;
    }

    public String format(Date date)
    {
        return getDateFormat(_format).format(date);
    }

    public String format(Object date)
    {
        return getDateFormat(_format).format(date);
    }

    public Date parse(String day) throws ParseException
    {
        return getDateFormat(_format).parse(day);
    }

}
