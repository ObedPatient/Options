package rw.evolve.eprocurement.schemes_option.utils;

import java.util.Random;

public class SchemeOptionIdGenerator {
        public static String generateId() {
            return String.format(
                    "SCHEME_OPT_%s_%s",
                    DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                    (new Random()).nextLong(1, 10_000_000)
            );
        }
}
