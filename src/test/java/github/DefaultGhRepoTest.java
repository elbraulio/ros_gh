package github;

import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class DefaultGhRepoTest {

    private DefaultGhRepo defaultObject;
    private final int id = 0;
    private final String name = "ros_gh";
    private final String fullName = "Braulio Lopez";
    private final int ownerId = 1;
    private final String description = "Recomendation System";
    private final String source = "source";

    @Before
    public void setUp(){
        defaultObject = new DefaultGhRepo(
                id, name, fullName, ownerId, description, source
        );
    }

    @Test
    public void equals() {
        assertThat(
                defaultObject,
                is(
                        new DefaultGhRepo(
                                id, name, fullName, ownerId, description, source
                        )
                )
        );
    }

    @Test
    public void AllMethods(){
        assertEquals(id, defaultObject.dbId());
        assertEquals(description, defaultObject.description());
        assertEquals(fullName, defaultObject.fullName());
        assertEquals(name, defaultObject.name());
        // TODO: 21-09-18 why this number is a String?
        assertEquals("1", defaultObject.owner());
    }
}