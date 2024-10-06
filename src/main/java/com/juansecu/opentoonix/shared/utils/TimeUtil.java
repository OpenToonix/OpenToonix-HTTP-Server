package com.juansecu.opentoonix.shared.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimeUtil {
    private TimeUtil() {}

    public static long localDateTimeToMilliseconds(final LocalDateTime localDateTime) {
        final Date date = Date.from(
            localDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant()
        );

        return date.getTime();
    }
}
