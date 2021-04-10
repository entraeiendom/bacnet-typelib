package no.entra.bacnet.internal.bvlc;

import no.entra.bacnet.bvlc.Bvlc;
import no.entra.bacnet.parseandmap.ParserResult;
import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BvlcParserTest {

    public static final String bacnetHexString = "81040018092f510cbac00120ffff00ff10080a07ae1a07ae";

    @Test
    void parse() throws UnknownHostException {
        ParserResult<Bvlc> result = BvlcParser.parse(bacnetHexString);
        assertNotNull(result);
        Bvlc bvlc = result.getParsedObject();
        assertNotNull(bvlc);
        assertEquals(BvlcFunction.ForwardedNpdu, bvlc.getFunction());
        assertEquals(24, bvlc.getFullMessageLength());
        assertEquals("9.47.81.12", bvlc.getOriginatingDeviceIp());
        assertEquals(47808, bvlc.getPort());
        assertEquals("0120ffff00ff10080a07ae1a07ae", result.getUnparsedHexString());

    }
}