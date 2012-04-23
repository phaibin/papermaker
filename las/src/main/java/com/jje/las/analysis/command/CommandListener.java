package com.jje.las.analysis.command;

public interface CommandListener {

    void before(LasContext ctx);
    
    void after(LasContext ctx);
}
