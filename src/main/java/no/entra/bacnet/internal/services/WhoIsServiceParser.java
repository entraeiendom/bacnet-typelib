package no.entra.bacnet.internal.services;

import no.entra.bacnet.internal.apdu.SDContextTag;
import no.entra.bacnet.internal.octet.OctetReader;
import no.entra.bacnet.internal.parseandmap.ParserResult;
import no.entra.bacnet.services.BacnetParserException;
import no.entra.bacnet.services.WhoIsService;

import static no.entra.bacnet.utils.HexUtils.toInt;

public class WhoIsServiceParser {

    public static ParserResult<WhoIsService> parse(String hexString) throws BacnetParserException {
        ParserResult<WhoIsService> parserResult = new ParserResult<>();
        parserResult.setInitialHexString(hexString);
        WhoIsService whoIsService = new WhoIsService();
        parserResult.setParsedObject(whoIsService);

        OctetReader whoIsReader = new OctetReader(hexString);
        if (whoIsReader.hasNext()) {
            SDContextTag sdContectTag = new SDContextTag(whoIsReader.next());
            int length = sdContectTag.findLength();
            String lowRangeHex = whoIsReader.next(length);
            Integer lowRange = toInt(lowRangeHex);
            whoIsService.setLowRangeLimit(lowRange);
            if (whoIsReader.hasNext()) {
                sdContectTag = new SDContextTag(whoIsReader.next());
                length = sdContectTag.findLength();
                String highRangeHex = whoIsReader.next(length);
                Integer highRange = toInt(highRangeHex);
                whoIsService.setHighRangeLimit(highRange);
            }
        }
        return parserResult;
    }
}
