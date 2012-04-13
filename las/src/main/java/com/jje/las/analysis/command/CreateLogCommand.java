package com.jje.las.analysis.command;

import com.jje.las.domain.Log;

public class CreateLogCommand extends AbstractLasCommand {

    @Override
    @SuppressWarnings("unchecked")
    public boolean perform(LasContext context) throws Exception {
        boolean isComplete =false;
        
        Log l = new Log();
        l.setLogFrom(context.getFileFrom());
        l.setPriority("OTHER");
        context.put("newLog", l);

        return isComplete;
    }

}
