package timer;

import java.util.concurrent.TimeUnit;

public class Timer {
    private long time;

    public Timer() {
        time = System.currentTimeMillis();
    }

    public void start() {
        time = System.currentTimeMillis();
    }

    public long getElapsedTimeInMillis() {
        return System.currentTimeMillis() - time;
    }

    public long getElapsedTimeInSecond() {
        return TimeUnit.MILLISECONDS.toSeconds(getElapsedTimeInMillis());
    }

    public long getElapsedTimeInMinutes() {
        return TimeUnit.MILLISECONDS.toMinutes(getElapsedTimeInMillis());
    }

    public long getElapsedTimeInHours() {
        return TimeUnit.MILLISECONDS.toHours(getElapsedTimeInMillis());
    }
}
