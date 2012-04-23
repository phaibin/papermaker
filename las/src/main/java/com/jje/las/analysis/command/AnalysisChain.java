package com.jje.las.analysis.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.chain.Chain;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalysisChain implements Chain {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<Command> commands = new ArrayList<Command>();

    private List<CommandListener> listeners = new ArrayList<CommandListener>();

    public AnalysisChain() {
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    public void addCommand(Command command) {
        if (command == null) {
            throw new IllegalArgumentException();
        }
        commands.add(command);
    }

    public boolean execute(Context context) throws Exception {
        if (context == null) {
            throw new IllegalArgumentException("Can't execute a null context");
        }

        if (!LasContext.class.isInstance(context)) {
            throw new IllegalArgumentException("context must be LasContext");
        }
        before((LasContext) context);
        boolean isSuccess = perform(context);
        after((LasContext) context);
        return isSuccess;
    }

    private boolean perform(Context context) {
        for (Command cmd : commands) {
            boolean success = false;
            try {
                success = cmd.execute(context);
            } catch (Exception e) {
                logger.error(((LasContext) context).getCurrentLine());
                logger.error("Error execute cmd:", e);
            }
            if (success) {
                return true;
            }
        }
        return false;
    }

    private void before(LasContext context) {
        for (CommandListener cl : listeners) {
            cl.before(context);
        }
    }

    private void after(LasContext context) {
        for (CommandListener cl : listeners) {
            cl.after(context);
        }
    }

    public List<CommandListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<CommandListener> listener) {
        this.listeners = listener;
    }

    public void addListener(CommandListener cl) {
        if (cl == null) {
            throw new IllegalArgumentException();
        }
        listeners.add(cl);
    }

}
