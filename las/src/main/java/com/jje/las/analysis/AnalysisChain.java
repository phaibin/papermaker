package com.jje.las.analysis;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.chain.Chain;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public class AnalysisChain implements Chain {

    private final List<Command> commands = new ArrayList<Command>();
    
    public AnalysisChain(){
        addCommand(new CreateLogCommand());
        addCommand(new Log4JCommand());
        addCommand(new NoopCommand());
    }
    
    public void addCommand(Command command) {
        if (command == null) {
            throw new IllegalArgumentException();
        }
        commands.add( command );
    }

    public boolean execute(Context context) throws Exception {
        if (context == null) {
            throw new IllegalArgumentException("Can't execute a null context");
        }

        boolean isSuccess = false;
        for(Command cmd : commands){
            boolean success = cmd.execute(context);
            if(success){
                isSuccess = success;
                break;
            }
        }

        return isSuccess;
    }

}
