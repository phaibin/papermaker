package com.jje.las.analysis.command;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.jje.las.action.log.Log;

public class NoopCommand implements Command {

    public boolean execute(Context ctx) throws Exception {
        boolean isComplete = false;
        LasContext context = (LasContext)ctx;
        context.removeNewLog();
        Log last = context.getLastLog();
        String line = context.getCurrentLine();
        if(last != null ){
            last.appendDetail(line);
            isComplete = true;
        }
        return isComplete;
    }

}
