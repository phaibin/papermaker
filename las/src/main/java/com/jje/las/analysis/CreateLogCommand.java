package com.jje.las.analysis;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.jje.las.action.log.Log;

public class CreateLogCommand implements Command {

    @SuppressWarnings("unchecked")
    public boolean execute(Context context) throws Exception {
        boolean isComplete =false;
        
        String fileFrom = (String)context.get("fileFrom");
        Log l = new Log();
        l.setLogFrom(fileFrom);
        context.put("newLog", l);

        return isComplete;
    }

}
