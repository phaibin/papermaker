package com.jje.las;

import java.io.FileNotFoundException;
import java.net.URL;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/spring/servlet-context.xml"})
public abstract class LasBaseSpringTest {
    
    @BeforeClass
    public static void init() throws FileNotFoundException{
        String classFile = "log4j.properties";
        URL url = LasBaseSpringTest.class.getClassLoader().getResource(classFile);
        String file = url.getFile();
        int idx = file.indexOf(classFile);
        file = file.substring(0, idx);
        System.setProperty("las_home", file);
    }
    
}
