package com.jje.las.analysis.command;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jje.las.action.log.Log;

public class WeblogicCommand extends AbstractLasCommand{
    
    String defaultRegex = "<([^<>]*)> <([^<>]*)> <([^<>]*)> <([^<>]*)> <([^<>]*)>";
    String dateFormat = "MMM dd, yyyy HH:mm:ss aaa z";

    SimpleDateFormat simpleFormat;
    Pattern pattern;

    public WeblogicCommand() {
        super();
        pattern = Pattern.compile(defaultRegex);
        simpleFormat = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
    }

    @Override
    public boolean perform(LasContext context) throws Exception {
        boolean isComplete = false;
        String line = context.getCurrentLine();
        Log newLog = context.getNewLog();
        Matcher m = pattern.matcher(line.trim());
        if (m.matches() && m.groupCount() == 5) {
            newLog.setLogTime(simpleFormat.parse(m.group(1) ));
            newLog.setPriority(m.group(2));
            newLog.setModule(m.group(3));
            newLog.setThread(m.group(4));
            newLog.setMessage(m.group(5));
            newLog.setRaw(line);
            isComplete = true;
        }
        return isComplete;
    }

}
