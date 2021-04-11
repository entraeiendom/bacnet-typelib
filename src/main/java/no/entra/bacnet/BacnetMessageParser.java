package no.entra.bacnet;

import no.entra.bacnet.apdu.Apdu;
import no.entra.bacnet.bvlc.Bvlc;
import no.entra.bacnet.internal.apdu.ApduParser;
import no.entra.bacnet.internal.bvlc.BvlcParser;
import no.entra.bacnet.internal.npdu.NpduParser;
import no.entra.bacnet.internal.parseandmap.ParserResult;
import no.entra.bacnet.npdu.Npdu;
import no.entra.bacnet.services.ServiceChoice;

public class BacnetMessageParser {

    public static BacnetResponse parse(String fullBacnetHexString) {
        ParserResult<Bvlc> bvlcResult = BvlcParser.parse(fullBacnetHexString);
        String hexString = bvlcResult.getUnparsedHexString();
        ParserResult<Npdu> npduResult = NpduParser.parse(hexString);
        hexString = npduResult.getUnparsedHexString();
        ParserResult<Apdu> apduResult = null;
        apduResult = ApduParser.parse(hexString);
        if (apduResult.isParsedOk()) {
            Apdu apdu = apduResult.getParsedObject();
            ServiceChoice serviceChoice = apdu.getServiceChoice();
//            serviceChoice.isRequiringResponse(); //!= WhoIs, WhoHas ++ broadcasts
//            switch (serviceChoice) {
//                complexAck and readPropertiesMultiple
//            }
        }
        return null;
    }
}
