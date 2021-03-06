package no.entra.bacnet.octet;

import java.util.Arrays;

import static no.entra.bacnet.utils.HexMatcher.isValidHexChar;


/**
 * String consisting of two hex chars.
 */
public class Octet {

    private final char[] octet;

    /**
     *
     * @param hexString 0-9a-f only lower case of hex is accepted.
     */
    public Octet(String hexString) {
        if (hexString != null && hexString.length() == 2) {
            if (isValidHexChar(hexString.charAt(0)) && isValidHexChar(hexString.charAt(1))) {
                octet = new char[]{hexString.charAt(0), hexString.charAt(1)};
            } else {
                throw new IllegalArgumentException("hexString may only be two character long. Each 0-f");
            }
        } else {
            throw new IllegalArgumentException("hexString may only be two character long. Each 0-f");
        }
    }

    /**
     *
     * @param nibble1 0-9a-f only lower case is accepted.
     * @param nibble2 0-9a-f only lower case is accepted.
     */
    public Octet(char nibble1, char nibble2) {
        if (isValidHexChar(nibble1) && isValidHexChar(nibble2)) {
            octet = new char[]{nibble1, nibble2};
        } else {
            throw new IllegalArgumentException("nibble1 and nibble2 may only contain numbers 0-9 and letters a-f.");
        }
    }

    public char[] getOctet() {
        return octet;
    }

    public char getFirstNibble() {
        return  octet[0];
    }

    public char getSecondNibble() {
        return  octet[1];
    }

    /**
     *
     * @param hexString two char long
     * @return valid octet, if present.
     */
    public static Octet fromHexString(String hexString) {
        return new Octet(hexString);
    }

    public int toInt() {
        return Integer.parseInt(toString(), 16);
    }

    @Override
    public String toString() {
        return String.valueOf(octet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Octet octet1 = (Octet) o;
        return Arrays.equals(getOctet(), octet1.getOctet());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getOctet());
    }
}
