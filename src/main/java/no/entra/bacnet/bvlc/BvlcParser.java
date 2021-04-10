package no.entra.bacnet.bvlc;

import no.entra.bacnet.internal.octet.Octet;
import no.entra.bacnet.internal.octet.OctetReader;
import no.entra.bacnet.parseandmap.ParserResult;

public class BvlcParser {

    public static ParserResult<Bvlc> parse(String bacnetHexString) {
        ParserResult result = new ParserResult();
        result.setInitialHexString(bacnetHexString);
        OctetReader bvlcReader = new OctetReader(bacnetHexString);
        if (bvlcReader == null) {
            return null;
        }
        Octet type = bvlcReader.next();
        if (type == null || !type.equals(Octet.fromHexString("81"))) {
            return null;
        }
        Octet functionOctet = bvlcReader.next();
        BvlcFunction function = BvlcFunction.fromOctet(functionOctet);
        Octet[] messageLength = bvlcReader.nextOctets(2); //Length is two octets
        Bvlc bvlc = new BvlcBuilder(function).withMessageLength(messageLength).build();

        result.setParsedObject(bvlc); // = new BvlcResult(bvlc, bvlcReader.unprocessedHexString());
        result.setUnparsedHexString(bvlcReader.unprocessedHexString());
        result.setParsedOk(true);
        if (bvlc.getFunction().equals(BvlcFunction.ForwardedNpdu)) {
            //Add BBMD forwarding info for messages routed between Bacnet subnets.
            result = addForwardingInfo(result, bvlcReader);
        }
        return result;
    }

    static ParserResult<Bvlc> addForwardingInfo(ParserResult<Bvlc> result, OctetReader bvlcReader) {

        Octet[] originatingAddressOctet = bvlcReader.nextOctets(4); //Specified in spec.
        Octet[] port = bvlcReader.nextOctets(2);
        Bvlc bvlc = result.getParsedObject();
        bvlc.setOriginatingDeviceIp(originatingAddressOctet);
        bvlc.setPort(port);
        String unprocessedBacnetHexString = bvlcReader.unprocessedHexString();
        result.setParsedOk(true);
        result.setParsedObject(bvlc);
        result.setUnparsedHexString(unprocessedBacnetHexString);

        return result;
    }
}
