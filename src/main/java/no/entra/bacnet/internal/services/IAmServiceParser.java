package no.entra.bacnet.internal.services;

import no.entra.bacnet.internal.objects.Segmentation;
import no.entra.bacnet.internal.octet.OctetReader;
import no.entra.bacnet.internal.parseandmap.ParserResult;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.octet.Octet;
import no.entra.bacnet.services.BacnetParserException;
import no.entra.bacnet.services.IAmService;
import no.entra.bacnet.vendor.Vendor;

import static no.entra.bacnet.utils.HexUtils.toInt;

public class IAmServiceParser {

    private static final Octet IAM_START_TAG = Octet.fromHexString("c4");

    public static ParserResult<IAmService> parse(String hexString) throws BacnetParserException {
        ParserResult<IAmService> parserResult = new ParserResult<>();
        parserResult.setInitialHexString(hexString);
        IAmService iAmService = new IAmService();
        parserResult.setParsedObject(iAmService);

        OctetReader iamReader = new OctetReader(hexString);
        if (!iamReader.hasNext() || !iamReader.next().equals(IAM_START_TAG)) {
            parserResult.setUnparsedHexString(iamReader.unprocessedHexString());
            parserResult.setErrorMessage("IAm must start with SD-IAM_START_TAG (c4).");
            parserResult.setParsedOk(false);
            throw new BacnetParserException("IAm must start with SD-IAM_START_TAG . (c4)", parserResult);
        }

//        Octet objectIdOctet = iamReader.next();
//        char idLengthChar = objectIdOctet.getSecondNibble();
//        int idLength = toInt(idLengthChar);
        String objectIdHexString = iamReader.next(4);
        ObjectId objectId = ObjectId.fromHexString(objectIdHexString);
//        Octet objectTypeOctet = iamReader.next();
//        ObjectType objectType = ObjectType.fromOctet(objectTypeOctet);
//        String instanceNumberOctet = iamReader.next(idLength -1);
//        Integer instanceNumber = toInt(instanceNumberOctet);
//        ObjectId objectId = new ObjectId(objectType, instanceNumber);
        iAmService.setObjectId(objectId);


        //MaxADPULengthAccepted
        Octet adpuLengthOctet = iamReader.next();
        char maxPdpuLengthChar = adpuLengthOctet.getSecondNibble();
        int octetCount = toInt(maxPdpuLengthChar);
        String maxPduLengthString = iamReader.next(octetCount);
        Integer maxAdpuLength = toInt(maxPduLengthString);
        iAmService.setMaxADPULengthAccepted(maxAdpuLength);

        //SegmentationSupported
        Octet acceptSegmentationOctet = iamReader.next();
        char segLengthChar = acceptSegmentationOctet.getSecondNibble();
        int segLength = toInt(segLengthChar);
        if (segLength == 1) {
            Octet segmentationOctet = iamReader.next();
            Segmentation segmentation = Segmentation.fromOctet(segmentationOctet);
            iAmService.setSegmentation(segmentation);
        }

        //Vendor
        Octet vendorOctet = iamReader.next();
        char vendorLengthChar = vendorOctet.getSecondNibble();
        int vendorLength = toInt(vendorLengthChar);
        if (vendorLength == 1) {
            Octet vendorId = iamReader.next();
            Vendor vendor = new Vendor();
            vendor.setVendorId(vendorId.toString());
            if (vendorId.equals(Octet.fromHexString("05"))) {
                vendor.setName("Johnson Controls, Inc");
            }
            iAmService.setVendor(vendor);
        }
        return parserResult;
    }
}
