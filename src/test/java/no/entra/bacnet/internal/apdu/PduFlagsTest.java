package no.entra.bacnet.internal.apdu;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static no.entra.bacnet.internal.apdu.PduFlags.*;
import static no.entra.bacnet.utils.HexUtils.toBitString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

class PduFlagsTest {
    private static final Logger log = getLogger(PduFlagsTest.class);

    @Test
    void isSegmentedTest() {
        log.debug("b: {}", toBitString('b'));
        assertTrue(isSegmented('b'));
    }

    @Test
    void requestSeriesOfResponses() {
        char pduFlag = '2';
        assertFalse(isSegmented(pduFlag));
        assertFalse(hasMoreSegments(pduFlag));
        assertTrue((willAcceptSegmentedResponse(pduFlag)));
    }

    @Test
    void seriesOfResponses() {
        char pduFlag = 'c';
        assertTrue(isSegmented(pduFlag));
        assertTrue(hasMoreSegments(pduFlag));
        pduFlag = '8'; //Last message in series of responses.
        assertTrue(isSegmented(pduFlag));
        assertFalse(hasMoreSegments(pduFlag));
    }
}