package com.jje.las.util;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class DateTimeEditor extends PropertyEditorSupport {

    private static Logger logger = LoggerFactory.getLogger(DateTimeEditor.class);

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void setAsText(String text) throws IllegalArgumentException {

        if (StringUtils.hasLength(text)) {
            try {
                setValue(format.parse(text));
            } catch (ParseException e) {
                logger.error("date convert error. ", e);
            }
        }
    }

    /**
     * Format the Date as String, using the specified DateFormat.
     */
    @Override
    public String getAsText() {
        if (null != getValue()) {
            Date d = (Date) getValue();
            return format.format(d);
        }
        return "";
    }
}
