package no.entra.bacnet.bvlc;

import no.entra.bacnet.internal.bvlc.BvlcFunction;
import no.entra.bacnet.octet.Octet;

import java.util.Arrays;

import static no.entra.bacnet.utils.HexUtils.intToHexString;

public class Bvlc {
    public static final int BVLC_OCTET_LENGTH = 4;
    private final Octet type = Octet.fromHexString("81");
    private final BvlcFunction function;
    private int bvlcLength = -1;
    private int fullMessageLength = -1;
    private Octet[] originatingDeviceIp = null;
    private Octet[] port = null;

    public Bvlc(Octet function) {
        this.function = BvlcFunction.fromOctet(function);
    }

    public Bvlc(BvlcFunction function, int fullMessageOctetLength) {
        this.function = function;
        this.fullMessageLength = fullMessageOctetLength;
    }

    public String getType() {
        return type.toString();
    }

    public BvlcFunction getFunction() {
        return function;
    }

    public int getBvlcLength() {
        return bvlcLength;
    }

    public void setBvlcLength(int bvlcLength) {
        this.bvlcLength = bvlcLength;
    }

    public int getFullMessageLength() {
        return fullMessageLength;
    }

    public void setFullMessageLength(Octet[] fullMessageLength) {
        this.fullMessageLength = findExpectdNumberOfOctetsInBvll(fullMessageLength);
    }

    public String getOriginatingDeviceIp() {
        String original = null;
        if (originatingDeviceIp != null && originatingDeviceIp.length == 4) {
            original = "" + originatingDeviceIp[0].toInt() + "."
                    + originatingDeviceIp[1].toInt() + "." +
                    originatingDeviceIp[2].toInt() + "." +
                    originatingDeviceIp[3].toInt();
        }
        return original;
    }

    public void setOriginatingDeviceIp(Octet[] originatingDeviceIp) {
        this.originatingDeviceIp = originatingDeviceIp;
    }

    public int getPort() {
        int originalPort = -1;
        if (port != null && port.length == 2) {
            originalPort = findExpectdNumberOfOctetsInBvll(port);
        }
        return originalPort;
    }

    public void setPort(Octet[] port) {
        this.port = port;
    }

    public static int findExpectdNumberOfOctetsInBvll(Octet[] messageLength) {
        String messageLengthString = messageLength[0].toString() + messageLength[1].toString();
        return findExpectdNumberOfOctetsInBvll(messageLengthString);
    }

    public static int findExpectdNumberOfOctetsInBvll(String messageLength) {
        int length = Integer.parseInt(messageLength, 16);
        return length;
    }

    public String toHexString() {
        String hexString = type.toString() + function.getBvlcFunctionHex() + intToHexString(fullMessageLength,4);
        return hexString;
    }

    @Override
    public String toString() {
        return "Bvlc{" +
                "type=" + type +
                ", function=" + function +
                ", bvlcLength=" + bvlcLength +
                ", fullMessageLength=" + fullMessageLength +
                ", originatingDeviceIp=" + Arrays.toString(originatingDeviceIp) +
                ", port=" + Arrays.toString(port) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bvlc bvlc = (Bvlc) o;
        return getBvlcLength() == bvlc.getBvlcLength() &&
                getFullMessageLength() == bvlc.getFullMessageLength() &&
                getType().equals(bvlc.getType()) &&
                getFunction() == bvlc.getFunction() &&
                ((getOriginatingDeviceIp() == null && bvlc.getOriginatingDeviceIp() == null)) || getOriginatingDeviceIp().equals(bvlc.getOriginatingDeviceIp()) &&
                getPort() == bvlc.getPort();
    }
}
