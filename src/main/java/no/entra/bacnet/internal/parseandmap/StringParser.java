package no.entra.bacnet.internal.parseandmap;

import no.entra.bacnet.internal.octet.OctetReader;
import no.entra.bacnet.octet.Octet;
import no.entra.bacnet.utils.HexUtils;
import org.slf4j.Logger;

import static java.lang.Integer.parseInt;
import static org.slf4j.LoggerFactory.getLogger;

public class StringParser {
    private static final Logger log = getLogger(StringParser.class);

    public static ParserResult<String> parseCharStringExtended(String hexString) {
//        log.trace("Find Text from: {}", hexString);
        OctetReader stringReader = new OctetReader(hexString);
        Octet valueLength = stringReader.next();
        int valueOctetLength = parseInt(String.valueOf(valueLength), 16);
        Octet encoding = stringReader.next();
        String objectNameHex = stringReader.next(valueOctetLength - 1);
//        log.trace("ObjectNameHex: {}", objectNameHex);
        String text = HexUtils.parseExtendedValue(encoding, objectNameHex);
//        log.debug("The rest: {}", stringReader.unprocessedHexString());
        int numberOfOctetsRead = valueOctetLength + 1;
        ParserResult result = new ParserResult(text, numberOfOctetsRead);
        return result;
    }

    public static ParserResult<String> parseCharString(String hexString, int valueOctetLength) {
//        log.trace("Find Text from: {}", hexString);
        OctetReader stringReader = new OctetReader(hexString);

        Octet encoding = stringReader.next();
        String text = null;
        int numberOfOctetsRead = 0;
        if (valueOctetLength > 1) {
            String objectNameHex = stringReader.next(valueOctetLength - 1);
//        log.trace("ObjectNameHex: {}", objectNameHex);
            text = HexUtils.parseExtendedValue(encoding, objectNameHex);
//        log.debug("The rest: {}", stringReader.unprocessedHexString());
            numberOfOctetsRead = valueOctetLength + 1;
        } else {
            text = "";
            numberOfOctetsRead = 1;
        }
        ParserResult result = new ParserResult(text, numberOfOctetsRead);
        return result;
    }
}
