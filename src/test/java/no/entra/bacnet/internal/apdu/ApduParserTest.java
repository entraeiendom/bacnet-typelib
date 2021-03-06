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
    void whoIsService()  {
        String whoIsHexString = "810400180a3f510cbac00120ffff00ff10080a07ae1a07ae";
        String whoIsApdu = "10080a07ae1a07ae";
        ParserResult<Apdu> parserResult = ApduParser.parse(whoIsApdu);
        Apdu apdu = parserResult.getParsedObject();
        assertNotNull(apdu);
        assertEquals(MessageType.UnconfirmedRequest, apdu.getMessageType());
        assertEquals(UnconfirmedServiceChoice.WhoIs, apdu.getServiceChoice());
        assertEquals("0a07ae1a07ae",parserResult.getUnparsedHexString());
    }
    @Test
    void iamService()  {
        String iamApdu = "1000c40200020f22040091002105";
        ParserResult<Apdu> parserResult = ApduParser.parse(iamApdu);
        Apdu apdu = parserResult.getParsedObject();
        assertNotNull(apdu);
        assertEquals(MessageType.UnconfirmedRequest, apdu.getMessageType());
        assertEquals(UnconfirmedServiceChoice.IAm, apdu.getServiceChoice());
        assertEquals("c40200020f22040091002105",parserResult.getUnparsedHexString());
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
        assertEquals("c4000000029103820560c40000000391048205e0",parserResult.getUnparsedHexString());
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
    void findReadPropertiesMultipleService() {
        String bacnetHexString = "810a054f010030470e0c020000081e294d4e75060046574643554f291c4e751800465720536572696573204261636e6574204465766963654f29755e910291205f29555e910291205f1f0c008000001e294d4e7514005549315f5a6f6e6554656d70657261747572654f291c4e750f00416e616c6f672056616c756520304f29754e915f4f29554e4441b3332c4f1f0c008000011e294d4e7519005549325f44697363686172676554656d70657261747572654f291c4e750f00416e616c6f672056616c756520314f29754e915f4f29554e4441acccf84f1f0c008000021e294d4e751300554f315f537570706c7946616e53706565644f291c4e750f00416e616c6f672056616c756520324f29754e915f4f29554e44000000004f1f0c008000031e294d4e751100554f325f436f6f6c696e6756616c76654f291c4e750f00416e616c6f672056616c756520334f29754e915f4f29554e44000000004f1f0c008000041e294d4e751100554f335f48656174696e6756616c76654f291c4e750f00416e616c6f672056616c756520344f29754e915f4f29554e4442b90d164f1f0c008000051e294d4e7512004f63637570616e6379536574706f696e744f291c4e750f00416e616c6f672056616c756520354f29754e915f4f29554e4441f000004f1f0c008000061e294d4e751400556e6f63637570616e6379536574706f696e744f291c4e750f00416e616c6f672056616c756520364f29754e915f4f29554e44415000004f1f0c008000071e294d4e751900456666656374697665436f6f6c696e67536574706f696e744f291c4e750f00416e616c6f672056616c756520374f29754e915f4f29554e4441f800004f1f0c008000081e294d4e75190045666665637469766548656174696e67536574706f696e744f291c4e750f00416e616c6f672056616c756520384f29754e915f4f29554e4441b000004f1f0c008000091e294d4e750f00416e616c6f672056616c756520394f291c4e750f00416e616c6f672056616c756520394f29754e915f4f29554e44000000004f1f0c0080000a1e294d4e751000416e616c6f672056616c75652031304f291c4e751000416e616c6f672056616c75652031304f29754e915f4f29554e44000000004f1f0c0080000b1e294d4e751000416e616c6f672056616c75652031314f291c4e751000416e616c6f672056616c75652031314f29754e915f4f29554e44000000004f1f0c0080000c1e294d4e751000416e616c6f672056616c75652031324f291c4e751000416e616c6f672056616c75652031324f29754e915f4f29554e44000000004f1f0c0080000d1e294d4e751000416e616c6f672056616c75652031334f291c4e751000416e616c6f672056616c75652031334f29754e915f4f29554e44000000004f1f0c0080000e1e294d4e751000416e616c6f672056616c75652031344f291c4e751000416e616c6f672056616c75652031344f29754e915f4f29554e44000000004f1f0c0080000f1e294d4e751000416e616c6f672056616c75652031354f291c4e751000416e616c6f672056616c75652031354f29754e915f4f29554e44000000004f1f0c008000101e294d4e751000416e616c6f672056616c75652031364f291c4e751000416e616c6f672056616c75652031364f29754e915f4f29554e44000000004f1f0c008000111e294d4e751000416e616c6f672056616c75652031374f291c4e751000416e616c6f672056616c75652031374f29754e915f4f29554e44000000004f1f0c008000121e294d4e751000416e616c6f672056616c75652031384f291c4e751000416e616c6f672056616c75652031384f29754e915f4f29554e44000000004f1f";
        String apdHexString = "30470e0c020000081e294d4e75060046574643554f291c4e751800465720536572696573204261636e6574204465766963654f29755e910291205f29555e910291205f1f0c008000001e294d4e7514005549315f5a6f6e6554656d70657261747572654f291c4e750f00416e616c6f672056616c756520304f29754e915f4f29554e4441b3332c4f1f0c008000011e294d4e7519005549325f44697363686172676554656d70657261747572654f291c4e750f00416e616c6f672056616c756520314f29754e915f4f29554e4441acccf84f1f0c008000021e294d4e751300554f315f537570706c7946616e53706565644f291c4e750f00416e616c6f672056616c756520324f29754e915f4f29554e44000000004f1f0c008000031e294d4e751100554f325f436f6f6c696e6756616c76654f291c4e750f00416e616c6f672056616c756520334f29754e915f4f29554e44000000004f1f0c008000041e294d4e751100554f335f48656174696e6756616c76654f291c4e750f00416e616c6f672056616c756520344f29754e915f4f29554e4442b90d164f1f0c008000051e294d4e7512004f63637570616e6379536574706f696e744f291c4e750f00416e616c6f672056616c756520354f29754e915f4f29554e4441f000004f1f0c008000061e294d4e751400556e6f63637570616e6379536574706f696e744f291c4e750f00416e616c6f672056616c756520364f29754e915f4f29554e44415000004f1f0c008000071e294d4e751900456666656374697665436f6f6c696e67536574706f696e744f291c4e750f00416e616c6f672056616c756520374f29754e915f4f29554e4441f800004f1f0c008000081e294d4e75190045666665637469766548656174696e67536574706f696e744f291c4e750f00416e616c6f672056616c756520384f29754e915f4f29554e4441b000004f1f0c008000091e294d4e750f00416e616c6f672056616c756520394f291c4e750f00416e616c6f672056616c756520394f29754e915f4f29554e44000000004f1f0c0080000a1e294d4e751000416e616c6f672056616c75652031304f291c4e751000416e616c6f672056616c75652031304f29754e915f4f29554e44000000004f1f0c0080000b1e294d4e751000416e616c6f672056616c75652031314f291c4e751000416e616c6f672056616c75652031314f29754e915f4f29554e44000000004f1f0c0080000c1e294d4e751000416e616c6f672056616c75652031324f291c4e751000416e616c6f672056616c75652031324f29754e915f4f29554e44000000004f1f0c0080000d1e294d4e751000416e616c6f672056616c75652031334f291c4e751000416e616c6f672056616c75652031334f29754e915f4f29554e44000000004f1f0c0080000e1e294d4e751000416e616c6f672056616c75652031344f291c4e751000416e616c6f672056616c75652031344f29754e915f4f29554e44000000004f1f0c0080000f1e294d4e751000416e616c6f672056616c75652031354f291c4e751000416e616c6f672056616c75652031354f29754e915f4f29554e44000000004f1f0c008000101e294d4e751000416e616c6f672056616c75652031364f291c4e751000416e616c6f672056616c75652031364f29754e915f4f29554e44000000004f1f0c008000111e294d4e751000416e616c6f672056616c75652031374f291c4e751000416e616c6f672056616c75652031374f29754e915f4f29554e44000000004f1f0c008000121e294d4e751000416e616c6f672056616c75652031384f291c4e751000416e616c6f672056616c75652031384f29754e915f4f29554e44000000004f1f";
        ParserResult<Apdu> parserResult = ApduParser.parse(apdHexString);
        Apdu apdu = parserResult.getParsedObject();
        assertEquals(71, apdu.getInvokeId());
        assertEquals(ConfirmedServiceChoice.ReadPropertyMultiple, apdu.getServiceChoice());
        String expectedUnparsedHexString = "0c020000081e294d4e75060046574643554f291c4e751800465720536572696573204261636e6574204465766963654f29755e910291205f29555e910291205f1f0c008000001e294d4e7514005549315f5a6f6e6554656d70657261747572654f291c4e750f00416e616c6f672056616c756520304f29754e915f4f29554e4441b3332c4f1f0c008000011e294d4e7519005549325f44697363686172676554656d70657261747572654f291c4e750f00416e616c6f672056616c756520314f29754e915f4f29554e4441acccf84f1f0c008000021e294d4e751300554f315f537570706c7946616e53706565644f291c4e750f00416e616c6f672056616c756520324f29754e915f4f29554e44000000004f1f0c008000031e294d4e751100554f325f436f6f6c696e6756616c76654f291c4e750f00416e616c6f672056616c756520334f29754e915f4f29554e44000000004f1f0c008000041e294d4e751100554f335f48656174696e6756616c76654f291c4e750f00416e616c6f672056616c756520344f29754e915f4f29554e4442b90d164f1f0c008000051e294d4e7512004f63637570616e6379536574706f696e744f291c4e750f00416e616c6f672056616c756520354f29754e915f4f29554e4441f000004f1f0c008000061e294d4e751400556e6f63637570616e6379536574706f696e744f291c4e750f00416e616c6f672056616c756520364f29754e915f4f29554e44415000004f1f0c008000071e294d4e751900456666656374697665436f6f6c696e67536574706f696e744f291c4e750f00416e616c6f672056616c756520374f29754e915f4f29554e4441f800004f1f0c008000081e294d4e75190045666665637469766548656174696e67536574706f696e744f291c4e750f00416e616c6f672056616c756520384f29754e915f4f29554e4441b000004f1f0c008000091e294d4e750f00416e616c6f672056616c756520394f291c4e750f00416e616c6f672056616c756520394f29754e915f4f29554e44000000004f1f0c0080000a1e294d4e751000416e616c6f672056616c75652031304f291c4e751000416e616c6f672056616c75652031304f29754e915f4f29554e44000000004f1f0c0080000b1e294d4e751000416e616c6f672056616c75652031314f291c4e751000416e616c6f672056616c75652031314f29754e915f4f29554e44000000004f1f0c0080000c1e294d4e751000416e616c6f672056616c75652031324f291c4e751000416e616c6f672056616c75652031324f29754e915f4f29554e44000000004f1f0c0080000d1e294d4e751000416e616c6f672056616c75652031334f291c4e751000416e616c6f672056616c75652031334f29754e915f4f29554e44000000004f1f0c0080000e1e294d4e751000416e616c6f672056616c75652031344f291c4e751000416e616c6f672056616c75652031344f29754e915f4f29554e44000000004f1f0c0080000f1e294d4e751000416e616c6f672056616c75652031354f291c4e751000416e616c6f672056616c75652031354f29754e915f4f29554e44000000004f1f0c008000101e294d4e751000416e616c6f672056616c75652031364f291c4e751000416e616c6f672056616c75652031364f29754e915f4f29554e44000000004f1f0c008000111e294d4e751000416e616c6f672056616c75652031374f291c4e751000416e616c6f672056616c75652031374f29754e915f4f29554e44000000004f1f0c008000121e294d4e751000416e616c6f672056616c75652031384f291c4e751000416e616c6f672056616c75652031384f29754e915f4f29554e44000000004f1f";
        assertEquals(expectedUnparsedHexString, parserResult.getUnparsedHexString());
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

    @Test
    void segmentAckTest() {
        String hexString = "40290009";
        ParserResult<Apdu> parserResult = ApduParser.parse(hexString);
        Apdu apdu = parserResult.getParsedObject();
        assertFalse(apdu.getSenderIsServer());
        assertEquals(41, apdu.getInvokeId());
        assertEquals(0, apdu.getSequenceNumber());
        assertEquals(9, apdu.getProposedWindowSize());
    }

    @Test
    void confirmedCovNotificationRequestTest() {
        String hexString = "0005f90109011c020000082c0080000139004e09552e4441b9335c2f096f2e8204002f4f";
        ParserResult<Apdu> parserResult = ApduParser.parse(hexString);
        Apdu apdu = parserResult.getParsedObject();
        assertFalse(apdu.getSenderIsServer());
        assertEquals(249, apdu.getInvokeId());
        assertEquals(ConfirmedServiceChoice.ConfirmedCovNotification, apdu.getServiceChoice());

    }
}