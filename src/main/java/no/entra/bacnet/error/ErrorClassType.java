package no.entra.bacnet.error;

import no.entra.bacnet.octet.Octet;

import static java.lang.Integer.parseInt;

public enum ErrorClassType {
    device(0),
    object(1),
    property(2),
    resources(3),
    security(4),
    services(5),
    vt(6),
    communication(7);
    private int ErrorClassTypeInt;

    public static ErrorClassType fromErrorClassTypeInt(int ErrorClassTypeInt) {
        for (ErrorClassType type : values()) {
            if (type.getErrorClassTypeInt() == ErrorClassTypeInt) {
                return type;
            }
        }
        return null;
    }

    public static ErrorClassType fromChar(char nibble) throws NumberFormatException {
        int ErrorClassTypeInt = parseInt(String.valueOf(nibble), 16);
        ErrorClassType ErrorClassType = fromErrorClassTypeInt(ErrorClassTypeInt);
        return ErrorClassType;
    }

    public static ErrorClassType fromOctet(Octet ErrorClassTypeOctet) throws NumberFormatException {
        if (ErrorClassTypeOctet == null) {
            return null;
        }
        Integer ErrorClassTypeInt = parseInt(ErrorClassTypeOctet.toString(), 16);
        ErrorClassType ErrorClassType = fromErrorClassTypeInt(ErrorClassTypeInt.intValue());
        return ErrorClassType;
    }


    public int getErrorClassTypeInt() {
        return ErrorClassTypeInt;
    }

    // enum constructor - cannot be public or protected
    private ErrorClassType(int ErrorClassTypeInt) {
        this.ErrorClassTypeInt = ErrorClassTypeInt;
    }
}
