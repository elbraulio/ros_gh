package tools;

import java.util.Stack;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class StackWithInitialValues<T> {

    private final T[] initialValues;

    public StackWithInitialValues(final T ... initialValues){

        this.initialValues = initialValues;
    }

    public Stack<T> stack() {
        final Stack<T> stack = new Stack<>();
        for(final T value : this.initialValues){
            stack.push(value);
        }
        return stack;
    }
}
