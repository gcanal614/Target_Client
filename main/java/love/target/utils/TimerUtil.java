package love.target.utils;

public class TimerUtil {
    private long lastMS;

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public boolean hasReached(double milliseconds) {
        return (double)(this.getCurrentMS() - this.lastMS) >= milliseconds;
    }

    public void reset() {
        this.lastMS = this.getCurrentMS();
    }

    public final long getElapsedTime() {
        return this.getCurrentMS() - this.lastMS;
    }

    public long getLastMS() {
        return this.lastMS;
    }
}
