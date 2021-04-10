package no.entra.bacnet.npdu;

import no.entra.bacnet.octet.Octet;
import no.entra.bacnet.octet.OctetReader;
import no.entra.bacnet.parseandmap.ParserResult;
import no.entra.bacnet.utils.HexUtils;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class NpduParser {
    private static final Logger log = getLogger(NpduParser.class);

    public static ParserResult<Npdu> parse(String bacnetHexString) {
        ParserResult<Npdu> result = new ParserResult<>();
        OctetReader npduReader = new OctetReader(bacnetHexString);
        if (npduReader == null) {
            return null;
        }
        Octet version = npduReader.next();
        if (version == null || !version.equals(Npdu.version)) {
            return null;
        }
        Octet controlOctet = npduReader.next();
        Npdu npdu = new NpduBuilder(controlOctet).build();
        result.setParsedObject(npdu);

        if (npdu.isDestinationAvailable()) {
            result = addDestinationSpecifierInfo(result, npduReader);
            if (result.isParsedOk()) {
                npdu = result.getParsedObject();
                npduReader = new OctetReader(result.getUnparsedHexString());
            }
        }
        if (npdu.isSourceAvailable()) {
            result = addSourceSpecifierInfo(result, npduReader);
            if (result.isParsedOk()) {
                npdu = result.getParsedObject();
                npduReader = new OctetReader(result.getUnparsedHexString());
            }
        }
//        if (result == null) {
//            String unprocessedHexString = npduReader.unprocessedHexString();
//            result = new NpduResult(npdu, unprocessedHexString);
//        }
        return result;
    }

    static ParserResult<Npdu> addSourceSpecifierInfo(ParserResult<Npdu> result, OctetReader npduReader) {
//        NpduResult result = null;
        Npdu npdu = result.getParsedObject();
        Octet[] sourceNetworkAddress = npduReader.nextOctets(2);
        npdu.setSourceNetworkAddress(sourceNetworkAddress);
        Octet sourceMacLayerAddressNumberOfOctets = npduReader.next();
        int readOctetLength = HexUtils.toInt(sourceMacLayerAddressNumberOfOctets);
        Octet[] sourceMacLayerAddress = npduReader.nextOctets(readOctetLength);
        npdu.setSourceMacLayerAddress(sourceMacLayerAddress);
//        Octet hopCount = npduReader.next();
//        npdu.setHopCount(hopCount);
        result.setParsedOk(true);
        result.setParsedObject(npdu);
        String unprocessedHexString = npduReader.unprocessedHexString();
        result.setUnparsedHexString(unprocessedHexString);
        return result;
    }
    static ParserResult<Npdu> addDestinationSpecifierInfo(ParserResult<Npdu> result, OctetReader npduReader) {
//        NpduResult result = null;
        Npdu npdu = result.getParsedObject();
        Octet[] destinationNetworkAddress = npduReader.nextOctets(2);
        npdu.setDestinationNetworkAddress(destinationNetworkAddress);
        Octet destinationMacLayerAddress = npduReader.next();
        npdu.setDestinationMacLayerAddress(destinationMacLayerAddress);
        Octet hopCount = npduReader.next();
        npdu.setHopCount(hopCount);
        String unprocessedHexString = npduReader.unprocessedHexString();
        result.setParsedOk(true);
        result.setParsedObject(npdu);
        result.setUnparsedHexString(unprocessedHexString);
        return result;
    }

}
