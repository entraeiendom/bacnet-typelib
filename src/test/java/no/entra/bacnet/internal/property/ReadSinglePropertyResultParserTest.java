package no.entra.bacnet.internal.property;

import no.entra.bacnet.device.DeviceId;
import no.entra.bacnet.internal.parseandmap.ParserResult;
import no.entra.bacnet.internal.properties.PropertyIdentifier;
import no.entra.bacnet.services.BacnetParserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReadSinglePropertyResultParserTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void parseSupportedServices() throws BacnetParserException {
        String hexString = "0c0200000819613e850707000bc000f8003f";
        ParserResult<ReadSinglePropertyResult> parserResult = ReadSinglePropertyResultParser.parse(hexString);
        assertNotNull(parserResult);
        assertEquals(new DeviceId(8),parserResult.getParsedObject().getObjectId());
        assertEquals(PropertyIdentifier.ProtocolServicesSupported, parserResult.getParsedObject().getPropertyIdentifier());
        assertEquals("00000111000000000000101111000000000000001111100000000000", parserResult.getParsedObject().getValue());
    }
}