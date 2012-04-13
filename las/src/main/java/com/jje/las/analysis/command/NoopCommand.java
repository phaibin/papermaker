package com.jje.las.analysis.command;

import com.jje.las.domain.Log;

public class NoopCommand extends AbstractLasCommand {

    @Override
    public boolean perform(LasContext context) throws Exception {
        boolean isComplete = false;
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
