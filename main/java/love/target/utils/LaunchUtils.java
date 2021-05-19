package love.target.utils;

public class LaunchUtils {
    private static long starting_time;

    public static void setStarting_time() {
        starting_time = System.currentTimeMillis();
    }

    public static long getLaunchTime() {
        long launchTime = System.currentTimeMillis() - starting_time;

        if (launchTime < 0) {
            System.out.println("我擦 时光回溯 兄弟们我冲了");
        }

        return launchTime;
    }
}
