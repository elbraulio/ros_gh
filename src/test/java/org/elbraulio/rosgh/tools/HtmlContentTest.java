package org.elbraulio.rosgh.tools;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class HtmlContentTest {
    @Test
    public void brainless(){
        assertEquals("tags", new HtmlContent("tags").toString());
    }
}