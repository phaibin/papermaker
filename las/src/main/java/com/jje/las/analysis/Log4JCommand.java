package com.jje.las.analysis;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.jje.las.action.log.Log;

public class Log4JCommand implements Command {
    String defaultRegex = "(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:\\d{2}:\\d{2},\\d{3}) ([^ ]*) \\[(.*)\\] - (.*)$";
    String dateFormat = "yyyy-MM-dd HH:mm:ss";
    
    SimpleDateFormat simpleFormat;
    Pattern pattern ;

    public Log4JCommand() {
        super();
        pattern = Pattern.compile(defaultRegex);
        simpleFormat = new SimpleDateFormat(dateFormat);
    }

    public boolean execute(Context ctx) throws Exception {
        boolean isComplete = false;
        LasContext context = (LasContext)ctx;
        String line = context.getCurrentLine();
        String fileFrom = context.getFileFrom();
        Log newLog = context.getNewLog();
        newLog.setLogFrom(fileFrom);
        Matcher m = pattern.matcher(line);
        if (m.matches() && m.groupCount() == 5) {
            try {
                newLog.setLogTime(simpleFormat.parse(m.group(1) + " " + m.group(2)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            newLog.setPriority(m.group(3));
            newLog.setClassName(m.group(4));
            newLog.setMessage(m.group(5));
            newLog.setRaw(line);
            isComplete = true;
        } 
        return isComplete;
    }

}
