package com.jje.logrotate.exec;

import static org.junit.Assert.fail;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.OS;
import org.junit.Test;

public class CommandExecTest {

    @Test
    public void execls() {
        ExecuteWatchdog watchdog = new ExecuteWatchdog(30000);
        Executor exec = new DefaultExecutor();
        exec.setWatchdog(watchdog);
        String line = "echo abc";
        if (OS.isFamilyWindows()) {
            line = "cmd.exe /C echo abc";
        }
        CommandLine cl = CommandLine.parse(line);
        try {
            int exitvalue = exec.execute(cl);
            if (exec.isFailure(exitvalue) && watchdog.killedProcess()) {
                fail();
            }
        } catch (Exception e) {
            fail();
        }
    }
}
