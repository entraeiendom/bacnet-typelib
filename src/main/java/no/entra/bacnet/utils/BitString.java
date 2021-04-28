package no.entra.bacnet.utils;

public class BitString {

    private boolean rightToLeft = true;
    private final Boolean[] bits;

    public BitString(int length) {
        bits = new Boolean[length];
        for (int i = 0; i < length; i++) {
            bits[i] = false;
        }
    }

    /**
     *
     * @param position start counting on 1 from left to right, or right to left. see: isRightToLeft()
     */
    public void setBit(int position) {
        if (position > 0) {
            bits[position-1] = true;
        }
    }
    /**
     *
     * @param position start counting on 1 from left to right, or right to left. see: isRightToLeft()
     */
    public void unsetBit(int position) {
        if (position > 0) {
            bits[position -1] = false;
        }
    }

    public boolean isBitSet(int position) {
        return bits[position];
    }

    public boolean isRightToLeft() {
        return rightToLeft;
    }

    public String toHexString() {
        String hexString = "";
        int decimal = toInt();
        hexString = Integer.toString(decimal,16);
        return hexString;
    }

    public int toInt() {
        String bitString = "";
        if (rightToLeft) {
            for (int i = bits.length -1; i >= 0; i--) {
                if (isBitSet(i)) {
                    bitString = bitString + "1";
                } else {
                    bitString = bitString + "0";
                }
            }
        } else {
            for (int i = 0; i < bits.length; i++) {
                if (isBitSet(i)) {
                    bitString = "1" + bitString;
                } else {
                    bitString = "0" + bitString;
                }
            }
        }
        int decimal = Integer.parseInt(bitString,2);
        return decimal;
    }

    public char toChar() {
        if (toInt() < 16) {
            String hexString = toHexString();
            char[] chars = hexString.toCharArray();
            Character nibble = chars[0];
            return nibble;
        } else {
            throw new IllegalStateException("BitString must have value less than 16 or \"10000\". Current is: " + toString());
        }
    }

    @Override
    public String toString() {
        String bitString = "";
        if (rightToLeft) {
            for (int i = bits.length -1 ; i >= 0; i--) {
                if (isBitSet(i)) {
                    bitString = bitString + "1";
                } else {
                    bitString = bitString + "0";
                }
            }
        } else {
            for (int i = 0; i < bits.length; i++) {
                if (isBitSet(i)) {
                    bitString = "1" + bitString;
                } else {
                    bitString = "0" + bitString;
                }
            }
        }
        return bitString;
    }
}
