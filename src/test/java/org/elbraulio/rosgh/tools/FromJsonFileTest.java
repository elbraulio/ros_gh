package org.elbraulio.rosgh.tools;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class FromJsonFileTest {
    @Test
    public void fromFile() throws FileNotFoundException {
        final String path = "src/test/resources/github/groovy.json";
        assertEquals(571, new FromJsonFile(path).repoList().size());
    }
}