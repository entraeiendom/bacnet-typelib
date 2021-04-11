package no.entra.bacnet;

import no.entra.bacnet.apdu.Apdu;
import no.entra.bacnet.bvlc.Bvlc;
import no.entra.bacnet.internal.apdu.ApduParser;
import no.entra.bacnet.internal.apdu.MessageType;
import no.entra.bacnet.internal.bvlc.BvlcParser;
import no.entra.bacnet.internal.npdu.NpduParser;
import no.entra.bacnet.internal.parseandmap.ParserResult;
import no.entra.bacnet.internal.services.ServiceParser;
import no.entra.bacnet.npdu.Npdu;
import no.entra.bacnet.services.AbortService;
import no.entra.bacnet.services.Service;
import no.entra.bacnet.services.ServiceChoice;

public class BacnetMessageParser {

    public static BacnetResponse parse(String fullBacnetHexString) {
        BacnetResponse bacnetResponse = null;
        ParserResult<Bvlc> bvlcResult = BvlcParser.parse(fullBacnetHexString);
        String hexString = bvlcResult.getUnparsedHexString();
        ParserResult<Npdu> npduResult = NpduParser.parse(hexString);
        hexString = npduResult.getUnparsedHexString();
        ParserResult<Apdu> apduResult = null;
        apduResult = ApduParser.parse(hexString);
        ParserResult<Service> serviceParserResult = null;
        if (apduResult.isParsedOk()) {
            Apdu apdu = apduResult.getParsedObject();
            String unparsedHexString = apduResult.getUnparsedHexString();
            ServiceChoice serviceChoice = apdu.getServiceChoice();
            if (serviceChoice == null) {
                if (apdu.getMessageType().equals(MessageType.Abort)) {
                    AbortService abortService = new AbortService();
                    abortService.setInvokeId(apdu.getInvokeId());
                    abortService.setAbortReason(apdu.getAbortReason());
                    serviceParserResult = new ParserResult<>();
                    serviceParserResult.setParsedObject(abortService);
                    serviceParserResult.setParsedOk(true);
                }
            } else {
                serviceParserResult = ServiceParser.parse(serviceChoice, unparsedHexString);
            }
            if (serviceParserResult != null && serviceParserResult.isParsedOk()) {
                ParserResult<Service> finalServiceParserResult = serviceParserResult;
                bacnetResponse = new BacnetResponse() {

                    @Override
                    public int statusCode() {
                        return 0;
                    }

                    @Override
                    public Service getService() {
                        return finalServiceParserResult.getParsedObject();
                    }
                };
            }
//            serviceChoice.isRequiringResponse(); //!= WhoIs, WhoHas ++ broadcasts
//            switch (serviceChoice) {
//                complexAck and readPropertiesMultiple
//            }
        }
        return bacnetResponse;
    }
}
