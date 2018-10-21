package org.elbraulio.rosgh.tools;

import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class IteratorAsListTest {

    @Test
    public void emptyList(){
        assertThat(
                new IteratorAsList<>(new FakeIterable()).asList(),
                is(new LinkedList<>())
        );
    }

    @Test
    public void takeFromEmptyList(){
        assertThat(
                new IteratorAsList<>(new FakeIterable()).take(1),
                is(new LinkedList<>())
        );
    }

    @Test
    public void listWithElements(){
        assertThat(
                new IteratorAsList<>(
                        new FakeIterable(new int[]{1, 2, 3})
                ).asList().get(1),
                is(2)
        );
    }

    @Test
    public void takeWithElements(){
        assertThat(
                new IteratorAsList<>(
                        new FakeIterable(new int[]{1, 2, 3})
                ).take(2).get(1),
                is(2)
        );
    }

    final class FakeIterable implements Iterator<Integer>{

        private final int[] items;
        private int index;

        FakeIterable(){
            this(new int[]{});
        }

        FakeIterable(int ... items){
            this(0, items);
        }

        FakeIterable(int index, int ... items){


            this.items = items;
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return this.index < this.items.length;
        }

        @Override
        public Integer next() {
            return this.items[this.index++];
        }
    }
}