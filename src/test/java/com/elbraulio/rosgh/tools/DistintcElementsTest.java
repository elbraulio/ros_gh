package com.elbraulio.rosgh.tools;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class DistintcElementsTest {
    @Test
    public void listFilledWithCopiesFromSameString() {
        final List<String> copies = new ArrayList<>();
        final String constant = "a constant";
        final List<String> result = new ArrayList<>();
        result.add(constant);
        int i = 10;
        while (i-- > 0){
            copies.add(constant);
        }
        MatcherAssert.assertThat(
                new DistintcElements(copies).list(),
                Matchers.is(result)
        );
    }

    @Test
    public void multipleCopiesFromDiferentStrings(){
        final List<String> copies = new ArrayList<>();
        copies.add("one");
        copies.add("two");
        copies.add("two");
        copies.add("four");
        copies.add("four");
        copies.add("five");
        final List<String> result = new ArrayList<>();
        result.add("one");
        result.add("two");
        result.add("four");
        result.add("five");
        MatcherAssert.assertThat(
                new DistintcElements(copies).list().size(),
                Matchers.is(result.size())
        );
    }
}