package org.elbraulio.rosgh.tools;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class IteratorAsList<T> implements CanBeList<T> {
    private final Iterator<T> iterator;

    public IteratorAsList(final Iterator<T> iterator) {

        this.iterator = iterator;
    }

    @Override
    public List<T> asList() {
        final List<T> list = new LinkedList<>();
        this.iterator.forEachRemaining(t -> list.add(t));
        return list;
    }

    @Override
    public List<T> take(final int length) {
        final List<T> list = new LinkedList<>();
        while(this.iterator.hasNext()){
            if(list.size() == length){
                break;
            }
            list.add(this.iterator.next());
        }
        return list;
    }
}
