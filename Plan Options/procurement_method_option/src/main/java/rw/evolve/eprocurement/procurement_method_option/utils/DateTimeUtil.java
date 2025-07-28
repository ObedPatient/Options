/**
 * Utility class for generating timestamps in the e-procurement system.
 * Provides methods to obtain formatted timestamps for use in ID generation.
 */
package rw.evolve.eprocurement.procurement_method_option.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    /**
     * Generates a timestamp representing the current date and time.
     * The timestamp    - is formatted as "yyyyMMddHHmmssSSS" (e.g., 20250724120830123),
     * where yyyy       - is the year, MM is the month, dd is the day, HH is the hour (24-hour),
     * mm               - is the minute, ss is the second, and SSS is the millisecond.
     * @return          - A formatted timestamp as a String.
     */
    public static String getTimeStamp() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    }
}
