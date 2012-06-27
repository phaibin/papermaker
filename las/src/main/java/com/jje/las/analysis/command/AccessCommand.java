package com.jje.las.analysis.command;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jje.las.domain.Log;

public class AccessCommand extends AbstractLasCommand {
    String defaultRegex = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}) - - \\[(.*)\\] \"(.*)\" (\\d{1,3}) (\\d{1,8})";
    String dateFormat = "dd/MMM/yyyy:HH:mm:ss Z";

    Pattern pattern;

    public AccessCommand() {
        super();
        pattern = Pattern.compile(defaultRegex);
    }

    @Override
    public boolean perform(LasContext ctx) throws Exception {
        boolean isComplete = false;
        String line = ctx.getCurrentLine();
        Log newLog = ctx.getNewLog();
        Matcher m = pattern.matcher(line);
        if (m.matches() && m.groupCount() == 5) {
        	SimpleDateFormat simpleFormat = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
            newLog.setLogTime(simpleFormat.parse(m.group(2)));
            newLog.setIp(m.group(1));
            newLog.setResult(m.group(4) + " " + m.group(5));
            newLog.setPriority("ACCESS");
            newLog.setModule("access");
            newLog.setClassName("");
            newLog.setMessage(m.group(3));
            newLog.setRaw(line);
            isComplete = true;
        }
        return isComplete;
    }

}
