package no.entra.bacnet.internal.apdu;

import no.entra.bacnet.octet.Octet;

public enum MessageType {
    ConfirmedRequest('0'),
    UnconfirmedRequest('1'),
    SimpleAck('2'),
    ComplexAck('3'),
    SegmentACK('4'),
    Error('5'),
    Reject('6'),
    Abort('7');

    private final char pduTypeChar;

    public static MessageType fromPduTypeChar(char pduTypeChar) {
        for (MessageType type : values()) {
            if (type.getPduTypeChar() == pduTypeChar) {
                return type;
            }
        }
        return null;
    }

    public static MessageType fromOctet(Octet pduTypeOctet) {
        if (pduTypeOctet == null) {
            return null;
        }
        return fromPduTypeChar(pduTypeOctet.getFirstNibble());
    }


    public char getPduTypeChar() {
        return pduTypeChar;
    }

    // enum constructor - cannot be public or protected
    MessageType(char pduTypeChar) {
        this.pduTypeChar = pduTypeChar;
    }

}
