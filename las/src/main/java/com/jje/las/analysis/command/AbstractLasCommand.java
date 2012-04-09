package com.jje.las.analysis.command;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractLasCommand implements Command {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public AbstractLasCommand() {
        super();
    }

    public boolean execute(Context context) throws Exception {
        boolean r = perform((LasContext)context);
        if(logger.isDebugEnabled())
            logger.debug("execute "+getClass()+" command : " + r);    
        return r;
    }
    
    public abstract boolean perform(LasContext context) throws Exception;

}
