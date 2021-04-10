package no.entra.bacnet;

import no.entra.bacnet.bvlc.Bvlc;
import no.entra.bacnet.internal.bvlc.BvlcParser;
import no.entra.bacnet.internal.npdu.NpduParser;
import no.entra.bacnet.npdu.Npdu;
import no.entra.bacnet.parseandmap.ParserResult;

public class BacnetMessageParser {

    public static BacnetMessage parse(String fullBacnetHexString) {
        ParserResult<Bvlc> bvlcResult = BvlcParser.parse(fullBacnetHexString);
        String hexString = bvlcResult.getUnparsedHexString();
        ParserResult<Npdu> npduRestult = NpduParser.parse(hexString);
        return null;
    }
}
