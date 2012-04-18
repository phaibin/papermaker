package com.jje.logrotate.cli;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static joptsimple.util.DateConverter.*;
import java.io.File;
import java.io.IOException;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import org.junit.Test;

public class JOptTest {

    @Test
    public void supportsLongOptionsWithArgumentsAndAbbreviations() {
        OptionParser parser = new OptionParser();
        parser.accepts("flag");
        parser.accepts("count").withRequiredArg();
        parser.accepts("level").withOptionalArg();

        OptionSet options = parser.parse("-flag", "--co", "3", "--lev");

        assertTrue(options.has("flag"));

        assertTrue(options.has("count"));
        assertTrue(options.hasArgument("count"));
        assertEquals("3", options.valueOf("count"));
        assertEquals(asList("3"), options.valuesOf("count"));

        assertTrue(options.has("level"));
        assertFalse(options.hasArgument("level"));
        assertNull(options.valueOf("level"));
        assertEquals(emptyList(), options.valuesOf("level"));
    }

    @Test
    public void allowsTypesafeRetrievalOfOptionArguments() {
        OptionParser parser = new OptionParser();
        OptionSpec<Integer> count = parser.accepts("count").withRequiredArg().ofType(Integer.class);
        OptionSpec<File> file = parser.accepts("file").withOptionalArg().ofType(File.class);
        OptionSpec<Void> verbose = parser.accepts("verbose");

        OptionSet options = parser.parse("--count", "3", "--file", "/tmp", "--verbose");

        assertTrue(options.has(verbose));

        assertTrue(options.has(count));
        assertTrue(options.hasArgument(count));
        Integer expectedCount = 3;
        assertEquals(expectedCount, options.valueOf(count));
        assertEquals(expectedCount, count.value(options));
        assertEquals(asList(expectedCount), options.valuesOf(count));
        assertEquals(asList(expectedCount), count.values(options));

        assertTrue(options.has(file));
        assertTrue(options.hasArgument(file));
        File expectedFile = new File("/tmp");
        assertEquals(expectedFile, options.valueOf(file));
        assertEquals(expectedFile, file.value(options));
        assertEquals(asList(expectedFile), options.valuesOf(file));
        assertEquals(asList(expectedFile), file.values(options));
    }

    @Test(expected = OptionException.class)
    public void allowsSpecificationOfRequiredOptions() {
        OptionParser parser = new OptionParser() {
            {
                accepts("userid").withRequiredArg().required();
                accepts("password").withRequiredArg().required();
            }
        };

        parser.parse("--userid", "bob");
    }

    @Test
    public void help() {
        OptionParser parser = new OptionParser() {
            {
                accepts("c").withRequiredArg().ofType(Integer.class).describedAs("count").defaultsTo(1);
                accepts("q").withOptionalArg().ofType(Double.class).describedAs("quantity");
                accepts("d", "some date").withRequiredArg().required().withValuesConvertedBy(datePattern("MM/dd/yy"));
                acceptsAll(asList("v", "talkative", "chatty"), "be more verbose");
                accepts("output-file").withOptionalArg().ofType(File.class).describedAs("file");
                acceptsAll(asList("h", "?"), "show help");
                acceptsAll(asList("cp", "classpath")).withRequiredArg()
                        .describedAs("path1" + File.pathSeparatorChar + "path2:...").ofType(File.class)
                        .withValuesSeparatedBy(File.pathSeparatorChar);
            }
        };

        try {
            parser.printHelpOn(System.out);
        } catch (IOException e) {
            fail();
        }
    }
}
