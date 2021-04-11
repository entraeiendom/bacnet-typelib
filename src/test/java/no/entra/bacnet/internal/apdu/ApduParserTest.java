package no.entra.bacnet.internal.apdu;

import no.entra.bacnet.apdu.Apdu;
import no.entra.bacnet.bvlc.Bvlc;
import no.entra.bacnet.internal.bvlc.BvlcParser;
import no.entra.bacnet.internal.npdu.NpduParser;
import no.entra.bacnet.internal.parseandmap.ParserResult;
import no.entra.bacnet.npdu.Npdu;
import no.entra.bacnet.octet.Octet;
import no.entra.bacnet.services.ConfirmedServiceChoice;
import no.entra.bacnet.services.UnconfirmedServiceChoice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApduParserTest {

    @Test
    void iamService()  {
        String iamApdu = "1000c40200020f22040091002105";
        ParserResult<Apdu> parserResult = ApduParser.parse(iamApdu);
        Apdu apdu = parserResult.getParsedObject();
        assertNotNull(apdu);
        assertEquals(MessageType.UnconfirmedRequest, apdu.getMessageType());
        assertEquals(UnconfirmedServiceChoice.IAm, apdu.getServiceChoice());
        assertEquals("",parserResult.getUnparsedHexString());
    }

    /*
    @Test
    void readPropertiesObjectNameProtocolVersionRevision()  {
        String apduHexString = "30010e0c020000081e294d4e75060046574643554f29624e21014f298b4e210e4f1f";
        ParserResult<Apdu> parserResult = ApduParser.parse(apduHexString);
        Apdu apdu = parserResult.getParsedObject();
        assertNotNull(apdu);
        assertEquals(MessageType.ComplexAck, apdu.getMessageType());
        assertEquals(1, apdu.getInvokeId());
        assertEquals(ConfirmedServiceChoice.ReadPropertyMultiple, apdu.getServiceChoice());
        assertEquals("0c020000081e294d4e75060046574643554f29624e21014f298b4e210e4f1f",parserResult.getUnparsedHexString());
    }

 @Test
    void verifyIHaveResponse() {
        //See page 984
        /*
        FIXME See bug report https://github.com/entraeiendom/bacnet-2-json/issues/5

        String unconfirmedRequest = "8104001e0a3f0010bac001080961010b1001c40200000bc403c000007100";
        BvlcResult bvlcResult = BvlcParser.parse(unconfirmedRequest);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        String unprocessedHexString = npduResult.getUnprocessedHexString();
        assertEquals("1001c40200000bc403c000007100", unprocessedHexString);
        unprocessedHexString = "1001c40200000bc403c000007100";
        Service service = ServiceParser.fromApduHexString(unprocessedHexString);
        assertNotNull(service);
        assertEquals(PduType.UnconfirmedRequest, service.getPduType());
        assertEquals(UnconfirmedServiceChoice.IHave, service.getServiceChoice());
        //I have device, 11
        ConfigurationRequest request = UnconfirmedService.tryToUnderstandUnconfirmedRequest(service);
        assertNotNull(request);
}
 @Test
    void verifyConfirmedServiceTest() {
        String unknownPduType = "8104001e0a3f0010bac001080961010f1001c40200000fc403c00000710045003000350035002d004e00430045003300300031002f00500072006f006700720061006d006d0069006e0067002e0045006e0065007200670069002e003400330033003300300031002e002d004f0045003000300034005f004d004f004d0072006d006500200031002000650074006700670067005f0046004600310031002e0044006100670042007600690031002d00310031002e004400610067004200760076007600760000000";
        BvlcResult bvlcResult = BvlcParser.parse(unknownPduType);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        Service service = ServiceParser.fromApduHexString(npduResult.getUnprocessedHexString());
        assertNotNull(service);
        assertTrue(service instanceof ConfirmedService);
        ConfigurationRequest request = ConfirmedService.tryToUnderstandConfirmedRequest(service);
        assertNotNull(request);
}
     */

    @Test
    void  getAlarmSummaryWithInvokeId()  {
        String alarmSummaryApduHex = "300103c4000000029103820560c40000000391048205e0";
        ParserResult<Apdu> parserResult = ApduParser.parse(alarmSummaryApduHex);
        assertTrue(parserResult.isParsedOk());
        Apdu apdu = parserResult.getParsedObject();
        assertNotNull(apdu);
        assertEquals(MessageType.ComplexAck, apdu.getMessageType());
        assertEquals(ConfirmedServiceChoice.GetAlarmSummary, apdu.getServiceChoice());
        assertEquals(1, apdu.getInvokeId());
        assertEquals("",parserResult.getUnparsedHexString());
    }

    @Test
    void readPropertyMultiple()  {
        String complexAckHex = "810a00b5010030030e0c002dc6ef1e29554e4441b1500000";
        String apduHexString = "30030e0c002dc6ef1e29554e4441b1500000";
        ParserResult<Apdu> parserResult = ApduParser.parse(apduHexString);
        assertTrue(parserResult.isParsedOk());
        Apdu apdu = parserResult.getParsedObject();
        assertNotNull(apdu);
        assertEquals(MessageType.ComplexAck, apdu.getMessageType());
        assertEquals(3, apdu.getInvokeId());
        assertEquals(ConfirmedServiceChoice.ReadPropertyMultiple, apdu.getServiceChoice());
    }

    @Test
    void readPropertyMultipleWithInvokeId() {
        String complexAckApduHex = "30020e0c00000021e29554e44422933334f1f";
        ParserResult<Apdu> parserResult = ApduParser.parse(complexAckApduHex);
        assertTrue(parserResult.isParsedOk());
        Apdu apdu = parserResult.getParsedObject();
        assertNotNull(apdu);
        assertEquals(MessageType.ComplexAck, apdu.getMessageType());
        assertEquals(2, apdu.getInvokeId());
        assertEquals(ConfirmedServiceChoice.ReadPropertyMultiple, apdu.getServiceChoice());
    }

    @Test
    void segmentedReadProperty() {
        String complexAckHex = "810a02010104381f03040c0cc404edcc0dc404ed";
        ParserResult<Bvlc> bvlcParserResult = BvlcParser.parse(complexAckHex);
        ParserResult<Npdu> npduParserResult = NpduParser.parse(bvlcParserResult.getUnparsedHexString());
        assertTrue(npduParserResult.isParsedOk());
        String apduHexString = "381f03040c0cc404edcc0dc404ed";
        ParserResult<Apdu> parserResult = ApduParser.parse(apduHexString);
        assertTrue(parserResult.isParsedOk());
        Apdu apdu = parserResult.getParsedObject();
        assertNotNull(apdu);
        assertEquals(MessageType.ComplexAck, apdu.getMessageType());
        assertEquals(31, apdu.getInvokeId());
        assertEquals(ConfirmedServiceChoice.ReadProperty, apdu.getServiceChoice());
    }

    @Test
    void findServiceChoice() {
        assertEquals(UnconfirmedServiceChoice.IAm, ApduParser.findServiceChoice(MessageType.UnconfirmedRequest, Octet.fromHexString("00")));
        assertEquals(ConfirmedServiceChoice.AcknowledgeAlarm, ApduParser.findServiceChoice(MessageType.ConfirmedRequest, Octet.fromHexString("00")));
        assertNull(ApduParser.findServiceChoice(MessageType.SegmentACK, Octet.fromHexString("00")));
        assertThrows(IllegalArgumentException.class, () -> ApduParser.findServiceChoice(null, Octet.fromHexString("00")));
    }

    @Test
    void expectServiceChoiceOctet() {

        assertTrue(ApduParser.expectServiceChoiceOctet(MessageType.Error));
        assertTrue(ApduParser.expectServiceChoiceOctet(MessageType.ComplexAck));
        assertTrue(ApduParser.expectServiceChoiceOctet(MessageType.ConfirmedRequest));
        assertTrue(ApduParser.expectServiceChoiceOctet(MessageType.SimpleAck));
        assertTrue(ApduParser.expectServiceChoiceOctet(MessageType.UnconfirmedRequest));
        assertFalse(ApduParser.expectServiceChoiceOctet(MessageType.Abort));
        assertFalse(ApduParser.expectServiceChoiceOctet(MessageType.Reject));
        assertFalse(ApduParser.expectServiceChoiceOctet(MessageType.SegmentACK));
    }
}