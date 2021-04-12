package no.entra.bacnet.utils;

import java.util.regex.Pattern;

public class HexMatcher {

    public static final Pattern REGEX_PATTERN = Pattern.compile("^[0-9a-f]+$");

    public static boolean isValidHex(String hexString) throws IllegalArgumentException {
        if (hexString == null || hexString.isEmpty()) {
            throw new IllegalArgumentException("HexString must have content. Is Empty.");
        }

        if (!REGEX_PATTERN.matcher(hexString).matches()) {
            throw new IllegalArgumentException("HexString may only contain 0-9a-f. Content is: " + hexString);
        }
        return true;
    }

    public static boolean isValidHexChar(char nibble) {
        String nibbleString = String.valueOf(nibble);
        return REGEX_PATTERN.matcher(nibbleString).matches();
    }
}
