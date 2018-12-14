package com.elbraulio.rosgh.tools;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class CanRequestTest {

    final int requestPerInterval = 6;
    final double intervalMinutes = 0.05;

    @Test
    public void cantExceedRequestLimitPerInterval()
            throws InterruptedException {
        CanRequest cr = new CanRequest(requestPerInterval, intervalMinutes);
        final long start = System.currentTimeMillis();
        int count = 0;
        while (System.currentTimeMillis() - start <= intervalMinutes*60000){
            Thread.sleep(100);
            cr.waitForRate();
            count++;
        }
        assertThat(count, is(requestPerInterval));
    }

    @Test
    public void shouldContinueAfterWait() throws InterruptedException {
        CanRequest cr = new CanRequest(requestPerInterval, intervalMinutes);
        final long start = System.currentTimeMillis();
        int count = 0;
        while (System.currentTimeMillis() - start <= 3450){
            Thread.sleep(400);
            cr.waitForRate();
            count++;
        }
        assertThat(count, is(7));
    }
}