package org.elbraulio.rosgh.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class CanRequest {
    private final List<Long> requestTime;
    private final int requestPerMinute;
    private final long intervalMillis;

    public CanRequest(int requestPerMinute) {
        this(requestPerMinute, 1);
    }

    public CanRequest(int requestPerMinute, double intervalMinutes) {
        this.requestPerMinute = requestPerMinute;
        this.intervalMillis = Long.parseLong(
                String.valueOf(intervalMinutes * 60000).split("\\.")[0]
        );
        this.requestTime = new ArrayList<>();
    }

    public void waitForRate() throws InterruptedException {
        int count = 0;
        final long currentTime = System.currentTimeMillis();
        this.requestTime.add(currentTime);
        int oldestRequest = 0;
        for (int i = requestTime.size() - 1; i >= 0; i--) {
            final long lapsed = currentTime - this.requestTime.get(i);
            if (lapsed < this.intervalMillis) {
                count++;
                oldestRequest = i;
            } else {
                break;
            }
        }

        if (count >= this.requestPerMinute) {
            final long sleep = this.intervalMillis - (
                    currentTime - requestTime.get(oldestRequest)
            );
            System.out.println(
                    "waiting " + sleep + " milliseconds for rate"
            );
            Thread.sleep(sleep);
        }
    }
}
