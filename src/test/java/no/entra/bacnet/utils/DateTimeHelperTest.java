package no.entra.bacnet.utils;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static no.entra.bacnet.utils.DateTimeHelper.fromIso8601Json;
import static no.entra.bacnet.utils.DateTimeHelper.iso8601DateTime;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateTimeHelperTest {

    @Test
    void testIso8601DateTimeTest() {
        String isoDateTime = iso8601DateTime();
        assertNotNull(Instant.parse(isoDateTime));
        assertTrue( isoDateTime.endsWith("Z"), "Ensure nanos are not included in: " + isoDateTime);
        String[] timesegments = isoDateTime.split("\\.");
        String lastSegment = timesegments[timesegments.length -1];

        assertTrue( lastSegment.length() < 5, "Ensure nanos are not included in: " + isoDateTime);
    }

    @Test
    void fromIso8601JsonTest() {
//        yyyy-MM-dd'T'HH:mm:ss.SSSZ
        String dateTime = "2021-03-30T06:55:20.716Z";
        assertNotNull(fromIso8601Json(dateTime));
    }
}