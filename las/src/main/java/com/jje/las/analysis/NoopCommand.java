package com.jje.las.analysis;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.jje.las.action.log.Log;

public class NoopCommand implements Command {

    public boolean execute(Context context) throws Exception {
        boolean isComplete = false;
        context.remove("newLog");
        Log last = (Log)context.get("lastLog");
        String line = context.get("currentLine").toString();
        if(last != null ){
            last.appendDetail(line);
            isComplete = true;
        }
        return isComplete;
    }

}
