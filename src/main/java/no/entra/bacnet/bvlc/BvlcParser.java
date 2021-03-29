package no.entra.bacnet.bvlc;

import no.entra.bacnet.octet.Octet;
import no.entra.bacnet.octet.OctetReader;

public class BvlcParser {

    public static no.entra.bacnet.bvlc.BvlcResult parse(String bacnetHexString) {
        no.entra.bacnet.bvlc.BvlcResult result = null;
        OctetReader bvlcReader = new OctetReader(bacnetHexString);
        if (bvlcReader == null) {
            return null;
        }
        Octet type = bvlcReader.next();
        if (type == null || !type.equals(Octet.fromHexString("81"))) {
            return null;
        }
        Octet functionOctet = bvlcReader.next();
        no.entra.bacnet.bvlc.BvlcFunction function = no.entra.bacnet.bvlc.BvlcFunction.fromOctet(functionOctet);
        Octet[] messageLength = bvlcReader.nextOctets(2); //Length is two octets
        no.entra.bacnet.bvlc.Bvlc bvlc = new BvlcBuilder(function).withMessageLength(messageLength).build();
        result = new no.entra.bacnet.bvlc.BvlcResult(bvlc, bvlcReader.unprocessedHexString());

        if (bvlc.getFunction().equals(no.entra.bacnet.bvlc.BvlcFunction.ForwardedNpdu)) {
            //Add BBMD forwarding info for messages routed between Bacnet subnets.
            result = addForwardingInfo(bvlc, bvlcReader);
        }
        return result;
    }

    static no.entra.bacnet.bvlc.BvlcResult addForwardingInfo(no.entra.bacnet.bvlc.Bvlc bvlc, OctetReader bvlcReader) {
        no.entra.bacnet.bvlc.BvlcResult result = null;
        Octet[] originatingAddressOctet = bvlcReader.nextOctets(4); //Specified in spec.
        Octet[] port = bvlcReader.nextOctets(2);
        bvlc.setOriginatingDeviceIp(originatingAddressOctet);
        bvlc.setPort(port);
        String unprocessedBacnetHexString = bvlcReader.unprocessedHexString();
        result = new no.entra.bacnet.bvlc.BvlcResult(bvlc, unprocessedBacnetHexString);

        return result;
    }
}
