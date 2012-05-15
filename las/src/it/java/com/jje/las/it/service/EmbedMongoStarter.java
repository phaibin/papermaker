package com.jje.las.it.service;

import de.flapdoodle.embedmongo.MongoDBRuntime;
import de.flapdoodle.embedmongo.MongodExecutable;
import de.flapdoodle.embedmongo.MongodProcess;
import de.flapdoodle.embedmongo.config.MongodConfig;
import de.flapdoodle.embedmongo.distribution.Version;

public class EmbedMongoStarter {
    private static MongodExecutable _mongodExe;
    private static MongodProcess _mongod;

    public static void main(String[] args) {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void init() throws Exception {
        MongoDBRuntime runtime = MongoDBRuntime.getDefaultInstance();
        _mongodExe = runtime.prepare(new MongodConfig(Version.V2_0, 27017, false));
        _mongod=_mongodExe.start();
        System.out.println("start mongo embeded server");
    }

    /**
     * @deprecated use mvn exec plugin, can not invoke this method, so .... 
     * @throws Exception
     */
    public void destory() throws Exception {
        _mongod.stop();
        _mongodExe.cleanup();
        System.out.println("stop mongo embeded server");
    }

}
