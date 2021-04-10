package no.entra.bacnet.parseandmap;

import no.entra.bacnet.internal.octet.Octet;
import no.entra.bacnet.internal.octet.OctetReader;
import no.entra.bacnet.utils.HexUtils;
import org.slf4j.Logger;

import static java.lang.Integer.parseInt;
import static org.slf4j.LoggerFactory.getLogger;

public class StringParser {
    private static final Logger log = getLogger(StringParser.class);

    public static ParserResult<String> parseCharStringExtended(String hexString) {
        log.debug("Find Text from: {}", hexString);
        OctetReader stringReader = new OctetReader(hexString);
        Octet valueLength = stringReader.next();
        int valueOctetLength = parseInt(String.valueOf(valueLength), 16);
        Octet encoding = stringReader.next();
        String objectNameHex = stringReader.next(valueOctetLength - 1);
        log.debug("ObjectNameHex: {}", objectNameHex);
        String text = HexUtils.parseExtendedValue(encoding, objectNameHex);
        log.debug("The rest: {}", stringReader.unprocessedHexString());
        int numberOfOctetsRead = valueOctetLength + 1;
        ParserResult result = new ParserResult(text, numberOfOctetsRead);
        return result;
    }
}
