package no.entra.bacnet.apdu;

import no.entra.bacnet.octet.Octet;

public enum ApduType {
    ConfirmedRequest('0'),
    UnconfirmedRequest('1'),
    SimpleAck('2'),
    ComplexAck('3'),
    SegmentACK('4'),
    Error('5'),
    Reject('6'),
    Abort('7');

    private final char pduTypeChar;

    public static ApduType fromPduTypeChar(char pduTypeChar) {
        for (ApduType type : values()) {
            if (type.getPduTypeChar() == pduTypeChar) {
                return type;
            }
        }
        return null;
    }

    public static ApduType fromOctet(Octet pduTypeOctet) {
        if (pduTypeOctet == null) {
            return null;
        }
        return fromPduTypeChar(pduTypeOctet.getFirstNibble());
    }


    public char getPduTypeChar() {
        return pduTypeChar;
    }

    // enum constructor - cannot be public or protected
    ApduType(char pduTypeChar) {
        this.pduTypeChar = pduTypeChar;
    }

}
