package com.jje.las.analysis.command;

import com.jje.las.action.log.Log;

public class CreateLogCommand extends AbstractLasCommand {

    @Override
    @SuppressWarnings("unchecked")
    public boolean perform(LasContext context) throws Exception {
        boolean isComplete =false;
        
        Log l = new Log();
        l.setLogFrom(((LasContext)context).getFileFrom());
        context.put("newLog", l);

        return isComplete;
    }

}
