package com.jje.las.analysis.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jje.las.domain.Log;
import com.jje.las.util.SafeSimpleDateFormat;

public class Log4JCommand extends AbstractLasCommand{
    
    String defaultRegex = "(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:\\d{2}:\\d{2},\\d{3}) ([^ ]*) \\[(.*)\\] - (.*)$";
    String dateFormat = "yyyy-MM-dd HH:mm:ss";

    SafeSimpleDateFormat simpleFormat;
    Pattern pattern;

    public Log4JCommand() {
        super();
        pattern = Pattern.compile(defaultRegex);
        simpleFormat = new SafeSimpleDateFormat(dateFormat);
    }

    @Override
    public boolean perform(LasContext context) throws Exception {
        boolean isComplete = false;
        Log newLog = context.getNewLog();
        String line = context.getCurrentLine();
        String from = context.getFileFrom();
        if(from != null){
            int idx = from.indexOf(".log");
            if(idx != -1){
                String module = from.substring(0, idx);
                newLog.setModule(module);
            }
        }
        Matcher m = pattern.matcher(line);
        if (m.matches() && m.groupCount() == 5) {
            String dtStr = m.group(1) + " " + m.group(2);
            try {
                newLog.setLogTime(simpleFormat.parse(dtStr));
            } catch (Exception e) {
                throw e;
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
