package com.jje.las;

import org.junit.After;
import org.junit.Before;

import de.flapdoodle.embedmongo.MongoDBRuntime;
import de.flapdoodle.embedmongo.MongodExecutable;
import de.flapdoodle.embedmongo.MongodProcess;
import de.flapdoodle.embedmongo.config.MongodConfig;
import de.flapdoodle.embedmongo.distribution.Version;

//TODO: DEPRECATE because its can not init multiply, use integration test instead
public abstract class AbstractMongoOMTest extends LasBaseSpringTest {
    private MongodExecutable _mongodExe;
    private MongodProcess _mongod;

    @Before
    public void setUp() throws Exception {
        MongoDBRuntime runtime = MongoDBRuntime.getDefaultInstance();
        _mongodExe = runtime.prepare(new MongodConfig(Version.V2_0, 27017, false));
        _mongod=_mongodExe.start();

    }

    @After
    public void tearDown() throws Exception {
        _mongod.stop();
        _mongodExe.cleanup();
    }



}