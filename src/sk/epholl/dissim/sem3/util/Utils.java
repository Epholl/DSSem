package sk.epholl.dissim.sem3.util;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by Tomáš on 13.04.2016.
 */
public class Utils {

    public static double kmhToMs(double kmh) {
        return kmh / 3.6;
    }

    public static double hoursToSeconds(double hours) {
        return hours * 3600;
    }

    public static double minutesToSeconds(double minutes) {
        return minutes * 60;
    }

    public static double secondsToHours(double seconds) {
        return seconds / 3600;
    }

    public static double secondsToMinutes(double seconds) {
        return seconds / 60;
    }

    public static double secondsUntilNextTime(LocalTime start, LocalTime end) {
        long seconds = ChronoUnit.SECONDS.between(start, end);

        if (seconds < 0) {
            seconds += (60 * 60 * 24);
        }

        return seconds;
    }

    public static boolean timeInInterval(LocalTime startTime, LocalTime endTime, LocalTime compared) {
        return startTime.equals(compared)
                || (compared.isAfter(startTime) && endTime.isAfter(compared));
    }
}