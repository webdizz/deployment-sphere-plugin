package com.epam.grandhackathon.deployment.sphere.plugin.metadata.util;

import org.joda.time.DateTime;

public final class DateFormatUtil {

    private DateFormatUtil() {

    }

    public static String formatDate(final DateTime dateTime) {
        return dateTime.toString("kk:mm:ss MMMM dd, yyyy");
    }

}
