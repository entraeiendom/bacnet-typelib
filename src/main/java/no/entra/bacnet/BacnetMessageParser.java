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
        Npdu npdu = null;
        boolean expectingReply = false;
        if (npduResult.isParsedOk()) {
            npdu = npduResult.getParsedObject();
            expectingReply = npdu.isExpectingResponse();
        }
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
            if (serviceParserResult != null) { // && serviceParserResult.isParsedOk()) {
                ParserResult<Service> finalServiceParserResult = serviceParserResult;
                boolean finalExpectingReply = expectingReply;
                Npdu finalNpdu = npdu;
                bacnetResponse = new BacnetResponse() {

                    @Override
                    public int statusCode() {
                        if (finalServiceParserResult.isParsedOk()) {
                            return 200;
                        } else {
                            return 500;
                        }
                    }

                    @Override
                    public Service getService() {
                        return finalServiceParserResult.getParsedObject();
                    }

                    @Override
                    public Integer getInvokeId() {
                        return apdu.getInvokeId();
                    }

                    @Override
                    public Npdu getNpdu() {
                        return finalNpdu;
                    }

                    @Override
                    public Apdu getApdu() {
                        return apdu;
                    }

                    @Override
                    public boolean expectingReply() {
                        return finalExpectingReply;
                    }

                    @Override
                    public boolean isSegmented() {
                        return apdu.isSegmented();
                    }

                    @Override
                    public boolean moreSegmentsFollows() {
                        return apdu.isHasMoreSegments();
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
